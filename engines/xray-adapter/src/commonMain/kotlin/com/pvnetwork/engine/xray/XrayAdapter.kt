package com.pvnetwork.engine.xray

import com.pvnetwork.core.adapter.AdapterDescriptor
import com.pvnetwork.core.adapter.AdapterId
import com.pvnetwork.core.adapter.CapabilityId
import com.pvnetwork.core.adapter.CoreAdapter
import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.adapter.ProfileValidation
import com.pvnetwork.core.adapter.ValidationIssue
import com.pvnetwork.core.adapter.ValidationSeverity
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.security.SecretStore

data class XrayRuntimeDescriptor(val implementationId: String, val upstreamVersion: String? = null, val availableCapabilities: Set<String> = emptySet()) {
    init { require(implementationId.isNotBlank()) { "Xray runtime id must not be blank" } }
}

interface XrayRuntimeFactory {
    val runtimeDescriptor: XrayRuntimeDescriptor
    fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection
}

class XrayAdapter(private val runtimeFactory: XrayRuntimeFactory) : CoreAdapter {
    override val descriptor: AdapterDescriptor
        get() = AdapterDescriptor(
            id = AdapterId(ADAPTER_ID),
            version = ADAPTER_VERSION,
            capabilities = runtimeFactory.runtimeDescriptor.availableCapabilities.map(::CapabilityId).toSet(),
            upstreamVersion = runtimeFactory.runtimeDescriptor.upstreamVersion,
        )

    override fun validate(profile: PVProfile): ProfileValidation {
        val issues = mutableListOf<ValidationIssue>()
        if (profile.protocolId != VLESS_CAPABILITY) {
            issues += error("XRAY_PROTOCOL_UNSUPPORTED", "first Xray adapter slice supports only $VLESS_CAPABILITY profiles")
        }
        if (profile.protocolId !in runtimeFactory.runtimeDescriptor.availableCapabilities) {
            issues += error("XRAY_CAPABILITY_UNAVAILABLE", "selected concrete Xray runtime does not advertise ${profile.protocolId}")
        }
        if (profile.protocolId == VLESS_CAPABILITY && profile.secretRefs["xray.vless.identity"] == null) {
            issues += error("VLESS_IDENTITY_REF_MISSING", "VLESS profile requires a protected identity reference")
        }
        if (profile.secretRefs["xray.original-share-link"] == null) {
            issues += error("XRAY_PROTECTED_SOURCE_MISSING", "imported Xray profile must retain its protected source reference")
        }

        if (profile.protocolId == VLESS_CAPABILITY) {
            val applicationProtocol = profile.extensions["xray.application-protocol"]
            if (applicationProtocol != VLESS_CAPABILITY) {
                issues += error("VLESS_APPLICATION_PROTOCOL_INVALID", "VLESS profile must declare xray.application-protocol=vless")
            }

            val security = profile.extensions["xray.security"]
            if (security !in SUPPORTED_SECURITY) {
                issues += error("XRAY_SECURITY_UNSUPPORTED", "Xray security '${security ?: "<missing>"}' is not supported by this adapter slice")
            }

            val transport = profile.extensions["xray.transport"]
            if (transport !in SUPPORTED_TRANSPORTS) {
                issues += error("XRAY_TRANSPORT_UNSUPPORTED", "Xray transport '${transport ?: "<missing>"}' is not supported by this adapter slice")
            }

            val flow = profile.extensions["xray.flow"]
            if (!flow.isNullOrBlank() && flow !in SUPPORTED_FLOWS) {
                issues += error("XRAY_FLOW_UNSUPPORTED", "Xray flow '$flow' is not supported by this adapter slice")
            }
            if (flow == "xtls-rprx-vision" && (transport != "raw" || security !in setOf("tls", "reality"))) {
                issues += error(
                    "XRAY_VISION_TRANSPORT_INCOMPATIBLE",
                    "xtls-rprx-vision is accepted only on the direct raw transport with TLS or REALITY",
                )
            }

            if (security == "reality" && profile.extensions["xray.reality-public-key"].isNullOrBlank()) {
                issues += error("XRAY_REALITY_PUBLIC_KEY_MISSING", "REALITY requires an explicit public key before runtime preparation")
            }
        }
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) { "Xray profile/runtime not ready: ${validation.issues.joinToString { it.code }}" }
        return runtimeFactory.prepare(profile, secretStore)
    }

    private fun error(code: String, message: String) = ValidationIssue(code, message, ValidationSeverity.ERROR)

    companion object {
        const val ADAPTER_ID = "pvnetwork-xray"
        const val ADAPTER_VERSION = "0.1.0"
        const val VLESS_CAPABILITY = "vless"

        private val SUPPORTED_SECURITY = setOf("none", "tls", "reality")
        private val SUPPORTED_TRANSPORTS = setOf("raw", "websocket", "grpc", "xhttp", "mkcp")
        private val SUPPORTED_FLOWS = setOf("xtls-rprx-vision")
    }
}
