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

data class XrayRuntimeDescriptor(
    val implementationId: String,
    val upstreamVersion: String? = null,
    val availableCapabilities: Set<String> = emptySet(),
) {
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
        if (profile.protocolId !in XRAY_PROTOCOLS) {
            issues += error("XRAY_PROTOCOL_UNSUPPORTED", "Xray adapter does not implement ${profile.protocolId}")
            return ProfileValidation(issues)
        }
        if (profile.protocolId !in runtimeFactory.runtimeDescriptor.availableCapabilities) {
            issues += error("XRAY_CAPABILITY_UNAVAILABLE", "selected concrete Xray runtime does not advertise ${profile.protocolId}")
        }

        val applicationProtocol = profile.extensions["xray.application-protocol"]
        if (applicationProtocol != profile.protocolId) {
            issues += error(
                "XRAY_APPLICATION_PROTOCOL_INVALID",
                "profile must declare xray.application-protocol=${profile.protocolId}",
            )
        }

        when (profile.protocolId) {
            VLESS_CAPABILITY -> validateVless(profile, issues)
            VMESS_CAPABILITY -> validateVmess(profile, issues)
            TROJAN_CAPABILITY -> validateTrojan(profile, issues)
            SHADOWSOCKS_CAPABILITY -> validateShadowsocks(profile, issues)
        }

        validateStream(profile, issues)
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) { "Xray profile/runtime not ready: ${validation.issues.joinToString { it.code }}" }
        return runtimeFactory.prepare(profile, secretStore)
    }

    private fun validateVless(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        requireSecret(profile, VLESS_IDENTITY_SECRET_ROLE, "VLESS_IDENTITY_REF_MISSING", "VLESS identity", issues)
        val flow = profile.extensions["xray.flow"]
        if (!flow.isNullOrBlank() && flow !in SUPPORTED_FLOWS) {
            issues += error("XRAY_FLOW_UNSUPPORTED", "Xray flow '$flow' is not supported")
        }
        val security = profile.extensions["xray.security"]
        if (flow == "xtls-rprx-vision" && security !in setOf("tls", "reality")) {
            issues += error("XRAY_VISION_SECURITY_INCOMPATIBLE", "xtls-rprx-vision requires TLS or REALITY security")
        }
    }

    private fun validateVmess(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        requireSecret(profile, VMESS_IDENTITY_SECRET_ROLE, "VMESS_IDENTITY_REF_MISSING", "VMess identity", issues)
        val method = profile.extensions["xray.vmess-security"] ?: "auto"
        if (method !in SUPPORTED_VMESS_SECURITY) {
            issues += error("XRAY_VMESS_SECURITY_UNSUPPORTED", "VMess account security '$method' is not supported")
        }
        if (!profile.extensions["xray.flow"].isNullOrBlank()) {
            issues += error("XRAY_VMESS_FLOW_UNSUPPORTED", "VMess does not accept a VLESS Vision flow")
        }
    }

    private fun validateTrojan(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        requireSecret(profile, TROJAN_PASSWORD_SECRET_ROLE, "TROJAN_PASSWORD_REF_MISSING", "Trojan password", issues)
        if (!profile.extensions["xray.flow"].isNullOrBlank()) {
            issues += error("XRAY_TROJAN_FLOW_REMOVED", "current Xray removes Trojan flow support")
        }
        if (profile.extensions["xray.security"] != "tls") {
            issues += error("XRAY_TROJAN_TLS_REQUIRED", "PVNetwork Trojan support requires TLS; insecure stream security is not accepted")
        }
        if (profile.extensions["xray.server-name"].isNullOrBlank()) {
            issues += error("XRAY_TROJAN_SERVER_NAME_REQUIRED", "Trojan TLS requires an explicit server name")
        }
    }

    private fun validateShadowsocks(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        requireSecret(
            profile,
            SHADOWSOCKS_PASSWORD_SECRET_ROLE,
            "SHADOWSOCKS_PASSWORD_REF_MISSING",
            "Shadowsocks password/key",
            issues,
        )
        val method = profile.extensions["xray.shadowsocks-method"]
        if (method !in SUPPORTED_SHADOWSOCKS_METHODS) {
            issues += error(
                "XRAY_SHADOWSOCKS_METHOD_UNSUPPORTED",
                "Shadowsocks method '${method ?: "<missing>"}' is outside the selected Xray compatibility set",
            )
        }
        if (!profile.extensions["xray.flow"].isNullOrBlank()) {
            issues += error("XRAY_SHADOWSOCKS_FLOW_UNSUPPORTED", "Shadowsocks does not accept a VLESS Vision flow")
        }
    }

    private fun validateStream(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        val security = profile.extensions["xray.security"]
        if (security !in SUPPORTED_STREAM_SECURITY) {
            issues += error("XRAY_SECURITY_UNSUPPORTED", "Xray stream security '${security ?: "<missing>"}' is not supported")
        }
        val transport = profile.extensions["xray.transport"]
        if (transport !in SUPPORTED_TRANSPORTS) {
            issues += error("XRAY_TRANSPORT_UNSUPPORTED", "Xray transport '${transport ?: "<missing>"}' is not supported")
        }
        if (security == "reality" && profile.protocolId != VLESS_CAPABILITY) {
            issues += error("XRAY_REALITY_PROTOCOL_UNSUPPORTED", "PVNetwork exposes REALITY only for VLESS in this M3 slice")
        }
        if (security == "reality" && profile.extensions["xray.reality-public-key"].isNullOrBlank()) {
            issues += error("XRAY_REALITY_PUBLIC_KEY_MISSING", "REALITY requires an explicit public key")
        }
    }

    private fun requireSecret(
        profile: PVProfile,
        role: String,
        code: String,
        label: String,
        issues: MutableList<ValidationIssue>,
    ) {
        if (profile.secretRefs[role] == null) issues += error(code, "$label requires a protected secret reference")
    }

    private fun error(code: String, message: String) = ValidationIssue(code, message, ValidationSeverity.ERROR)

    companion object {
        const val ADAPTER_ID = "pvnetwork-xray"
        const val ADAPTER_VERSION = "0.2.0"

        const val VLESS_CAPABILITY = "vless"
        const val VMESS_CAPABILITY = "vmess"
        const val TROJAN_CAPABILITY = "trojan"
        const val SHADOWSOCKS_CAPABILITY = "shadowsocks"

        const val VLESS_IDENTITY_SECRET_ROLE = "xray.vless.identity"
        const val VMESS_IDENTITY_SECRET_ROLE = "xray.vmess.identity"
        const val TROJAN_PASSWORD_SECRET_ROLE = "xray.trojan.password"
        const val SHADOWSOCKS_PASSWORD_SECRET_ROLE = "xray.shadowsocks.password"

        val XRAY_PROTOCOLS = setOf(VLESS_CAPABILITY, VMESS_CAPABILITY, TROJAN_CAPABILITY, SHADOWSOCKS_CAPABILITY)

        private val SUPPORTED_STREAM_SECURITY = setOf("none", "tls", "reality")
        private val SUPPORTED_TRANSPORTS = setOf("raw", "websocket", "grpc", "xhttp", "mkcp")
        private val SUPPORTED_FLOWS = setOf("xtls-rprx-vision")
        private val SUPPORTED_VMESS_SECURITY = setOf("auto", "aes-128-gcm", "chacha20-poly1305")
        private val SUPPORTED_SHADOWSOCKS_METHODS = setOf(
            "aes-128-gcm",
            "aes-256-gcm",
            "chacha20-ietf-poly1305",
            "xchacha20-ietf-poly1305",
            "2022-blake3-aes-128-gcm",
            "2022-blake3-aes-256-gcm",
            "2022-blake3-chacha20-poly1305",
        )
    }
}
