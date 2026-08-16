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
            issues += ValidationIssue("OPENVPN_PROTOCOL_MISMATCH", "profile protocolId must be $PROTOCOL_ID", ValidationSeverity.ERROR)
        }
        if (profile.secretRefs["openvpn.original-profile"] == null) {
            issues += ValidationIssue("OPENVPN_PROTECTED_SOURCE_MISSING", "OpenVPN profile must retain its protected source reference", ValidationSeverity.ERROR)
        }
        if (!runtimeFactory.runtimeDescriptor.available) {
            issues += ValidationIssue("OPENVPN_RUNTIME_UNAVAILABLE", "no approved concrete OpenVPN runtime is available on this platform", ValidationSeverity.ERROR)
        }
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) { "OpenVPN profile/runtime is not ready: ${validation.issues.joinToString { it.code }}" }
        return runtimeFactory.prepare(profile, secretStore)
    }

    companion object {
        const val PROTOCOL_ID = "openvpn"
        const val ADAPTER_ID = "pvnetwork-openvpn"
        const val ADAPTER_VERSION = "0.1.0"
    }
}
