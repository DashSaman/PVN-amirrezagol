package com.pvnetwork.engine.openvpn

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

data class OpenVpnRuntimeDescriptor(
    val implementationId: String,
    val upstreamVersion: String? = null,
    val available: Boolean,
) {
    init { require(implementationId.isNotBlank()) { "OpenVPN runtime implementation id must not be blank" } }
}

/** Platform/native runtime boundary; no OpenVPN core is linked by this module. */
interface OpenVpnRuntimeFactory {
    val runtimeDescriptor: OpenVpnRuntimeDescriptor
    fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection
}

class OpenVpnAdapter(
    private val runtimeFactory: OpenVpnRuntimeFactory,
) : CoreAdapter {
    override val descriptor: AdapterDescriptor
        get() = AdapterDescriptor(
            id = AdapterId(ADAPTER_ID),
            version = ADAPTER_VERSION,
            capabilities = if (runtimeFactory.runtimeDescriptor.available) setOf(CapabilityId(PROTOCOL_ID)) else emptySet(),
            upstreamVersion = runtimeFactory.runtimeDescriptor.upstreamVersion,
        )

    override fun validate(profile: PVProfile): ProfileValidation {
        val issues = mutableListOf<ValidationIssue>()
        if (profile.protocolId != PROTOCOL_ID) {
            issues += error("OPENVPN_PROTOCOL_MISMATCH", "profile protocolId must be $PROTOCOL_ID")
        }
        if (profile.secretRefs["openvpn.original-profile"] == null) {
            issues += error("OPENVPN_PROTECTED_SOURCE_MISSING", "OpenVPN profile must retain its protected source reference")
        }
        if (!profile.extensions["openvpn.unsupported-directive-names"].isNullOrBlank()) {
            issues += error(
                "OPENVPN_UNSUPPORTED_DIRECTIVES_PRESENT",
                "profile contains directives not interpreted by this adapter slice; resolve them before runtime preparation",
            )
        }
        if (!profile.extensions["openvpn.unresolved-external-material-names"].isNullOrBlank()) {
            issues += error(
                "OPENVPN_EXTERNAL_MATERIAL_UNRESOLVED",
                "profile references external OpenVPN credential/key material that has not been explicitly resolved into protected storage",
            )
        }
        if (!runtimeFactory.runtimeDescriptor.available) {
            issues += error("OPENVPN_RUNTIME_UNAVAILABLE", "no approved concrete OpenVPN runtime is available on this platform")
        }
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) { "OpenVPN profile/runtime is not ready: ${validation.issues.joinToString { it.code }}" }
        return runtimeFactory.prepare(profile, secretStore)
    }

    private fun error(code: String, message: String) = ValidationIssue(code, message, ValidationSeverity.ERROR)

    companion object {
        const val PROTOCOL_ID = "openvpn"
        const val ADAPTER_ID = "pvnetwork-openvpn"
        const val ADAPTER_VERSION = "0.1.0"
    }
}
