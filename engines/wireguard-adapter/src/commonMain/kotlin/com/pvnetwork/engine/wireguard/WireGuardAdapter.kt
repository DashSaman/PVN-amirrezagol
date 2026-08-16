package com.pvnetwork.engine.wireguard

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

data class WireGuardRuntimeDescriptor(
    val implementationId: String,
    val upstreamVersion: String? = null,
    val available: Boolean,
) {
    init {
        require(implementationId.isNotBlank()) { "runtime implementation id must not be blank" }
    }
}

/** Platform/native runtime boundary. No cryptography or upstream engine is implemented here. */
interface WireGuardRuntimeFactory {
    val runtimeDescriptor: WireGuardRuntimeDescriptor
    fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection
}

/**
 * Product-owned WireGuard adapter shell. It advertises WireGuard capability only
 * when a concrete platform runtime reports itself available; research status is
 * never converted into an implementation capability.
 */
class WireGuardAdapter(
    private val runtimeFactory: WireGuardRuntimeFactory,
) : CoreAdapter {
    override val descriptor: AdapterDescriptor
        get() = AdapterDescriptor(
            id = AdapterId(ADAPTER_ID),
            version = ADAPTER_VERSION,
            capabilities = if (runtimeFactory.runtimeDescriptor.available) {
                setOf(CapabilityId(PROTOCOL_ID))
            } else {
                emptySet()
            },
            upstreamVersion = runtimeFactory.runtimeDescriptor.upstreamVersion,
        )

    override fun validate(profile: PVProfile): ProfileValidation {
        val issues = mutableListOf<ValidationIssue>()
        if (profile.protocolId != PROTOCOL_ID) {
            issues += ValidationIssue(
                code = "WIREGUARD_PROTOCOL_MISMATCH",
                message = "profile protocolId must be $PROTOCOL_ID",
                severity = ValidationSeverity.ERROR,
            )
        }
        if (profile.secretRefs["wireguard.private-key"] == null) {
            issues += ValidationIssue(
                code = "WIREGUARD_PRIVATE_KEY_REF_MISSING",
                message = "WireGuard profile must reference a private key in SecretStore",
                severity = ValidationSeverity.ERROR,
            )
        }
        if (!runtimeFactory.runtimeDescriptor.available) {
            issues += ValidationIssue(
                code = "WIREGUARD_RUNTIME_UNAVAILABLE",
                message = "no concrete WireGuard runtime is available on this platform",
                severity = ValidationSeverity.ERROR,
            )
        }
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) {
            "WireGuard profile/runtime is not ready: ${validation.issues.joinToString { it.code }}"
        }
        return runtimeFactory.prepare(profile, secretStore)
    }

    companion object {
        const val PROTOCOL_ID: String = "wireguard"
        const val ADAPTER_ID: String = "pvnetwork-wireguard"
        const val ADAPTER_VERSION: String = "0.1.0"
    }
}
