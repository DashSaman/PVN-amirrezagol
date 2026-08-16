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
        if (profile.protocolId !in runtimeFactory.runtimeDescriptor.availableCapabilities) {
            issues += ValidationIssue("XRAY_CAPABILITY_UNAVAILABLE", "selected concrete Xray runtime does not advertise ${profile.protocolId}", ValidationSeverity.ERROR)
        }
        if (profile.protocolId == VLESS_CAPABILITY && profile.secretRefs["xray.vless.identity"] == null) {
            issues += ValidationIssue("VLESS_IDENTITY_REF_MISSING", "VLESS profile requires a protected identity reference", ValidationSeverity.ERROR)
        }
        if (profile.secretRefs["xray.original-share-link"] == null) {
            issues += ValidationIssue("XRAY_PROTECTED_SOURCE_MISSING", "imported Xray profile must retain its protected source reference", ValidationSeverity.ERROR)
        }
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) { "Xray profile/runtime not ready: ${validation.issues.joinToString { it.code }}" }
        return runtimeFactory.prepare(profile, secretStore)
    }

    companion object {
        const val ADAPTER_ID = "pvnetwork-xray"
        const val ADAPTER_VERSION = "0.1.0"
        const val VLESS_CAPABILITY = "vless"
    }
}
