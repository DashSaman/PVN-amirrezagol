package com.pvnetwork.engine.mihomo

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

data class MihomoRuntimeDescriptor(
    val implementationId: String,
    val upstreamVersion: String? = null,
    val availableCapabilities: Set<String> = emptySet(),
) {
    init { require(implementationId.isNotBlank()) { "Mihomo runtime id must not be blank" } }
}

interface MihomoRuntimeFactory {
    val runtimeDescriptor: MihomoRuntimeDescriptor
    fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection
}

/**
 * Product-owned M3 adapter for selected protocols implemented by a concrete
 * host-supplied Mihomo runtime. Capability claims are fail-closed and remain
 * distinct from research/source capability observations.
 */
class MihomoAdapter(private val runtimeFactory: MihomoRuntimeFactory) : CoreAdapter {
    override val descriptor: AdapterDescriptor
        get() = AdapterDescriptor(
            id = AdapterId(ADAPTER_ID),
            version = ADAPTER_VERSION,
            capabilities = runtimeFactory.runtimeDescriptor.availableCapabilities.map(::CapabilityId).toSet(),
            upstreamVersion = runtimeFactory.runtimeDescriptor.upstreamVersion,
        )

    override fun validate(profile: PVProfile): ProfileValidation {
        val issues = mutableListOf<ValidationIssue>()
        if (profile.protocolId !in MIHOMO_PROTOCOLS) {
            issues += error("MIHOMO_PROTOCOL_UNSUPPORTED", "Mihomo adapter does not implement ${profile.protocolId}")
            return ProfileValidation(issues)
        }
        if (profile.protocolId !in runtimeFactory.runtimeDescriptor.availableCapabilities) {
            issues += error("MIHOMO_CAPABILITY_UNAVAILABLE", "selected concrete Mihomo runtime does not advertise ${profile.protocolId}")
        }
        if (profile.extensions["mihomo.application-protocol"] != profile.protocolId) {
            issues += error(
                "MIHOMO_APPLICATION_PROTOCOL_INVALID",
                "profile must declare mihomo.application-protocol=${profile.protocolId}",
            )
        }

        when (profile.protocolId) {
            HYSTERIA2_CAPABILITY -> validateHysteria2(profile, issues)
            TUIC_CAPABILITY -> validateTuic(profile, issues)
            ANYTLS_CAPABILITY -> validateAnyTls(profile, issues)
        }
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) { "Mihomo profile/runtime not ready: ${validation.issues.joinToString { it.code }}" }
        return runtimeFactory.prepare(profile, secretStore)
    }

    private fun validateHysteria2(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        requireSecret(profile, HYSTERIA2_PASSWORD_SECRET_ROLE, "MIHOMO_HYSTERIA2_PASSWORD_REF_MISSING", "Hysteria2 password", issues)
        rejectUnsafeTls(profile, issues)
        val obfs = profile.extensions["mihomo.hysteria2.obfs"]
        if (!obfs.isNullOrBlank() && obfs !in HYSTERIA2_OBFS) {
            issues += error("MIHOMO_HYSTERIA2_OBFS_UNSUPPORTED", "Hysteria2 obfs '$obfs' is outside the selected compatibility set")
        }
        if (!obfs.isNullOrBlank() && profile.secretRefs[HYSTERIA2_OBFS_PASSWORD_SECRET_ROLE] == null) {
            issues += error("MIHOMO_HYSTERIA2_OBFS_PASSWORD_REF_MISSING", "Hysteria2 obfs requires a protected obfs password")
        }
    }

    private fun validateTuic(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        requireSecret(profile, TUIC_PASSWORD_SECRET_ROLE, "MIHOMO_TUIC_PASSWORD_REF_MISSING", "TUIC v5 password", issues)
        rejectUnsafeTls(profile, issues)
        val uuid = profile.extensions["mihomo.tuic.uuid"]
        if (uuid.isNullOrBlank() || !UUID_PATTERN.matches(uuid)) {
            issues += error("MIHOMO_TUIC_UUID_INVALID", "TUIC v5 requires an explicit canonical UUID")
        }
        if (!profile.extensions["mihomo.tuic.token"].isNullOrBlank()) {
            issues += error("MIHOMO_TUIC_V4_TOKEN_UNSUPPORTED", "this M3 slice intentionally exposes TUIC v5 UUID/password only")
        }
    }

    private fun validateAnyTls(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        requireSecret(profile, ANYTLS_PASSWORD_SECRET_ROLE, "MIHOMO_ANYTLS_PASSWORD_REF_MISSING", "AnyTLS password", issues)
        rejectUnsafeTls(profile, issues)
        val mutuallyExclusive = listOf("mihomo.anytls.shadow-tls", "mihomo.anytls.restls", "mihomo.anytls.jls")
            .count { profile.extensions[it].equals("true", ignoreCase = true) }
        if (mutuallyExclusive > 1) {
            issues += error("MIHOMO_ANYTLS_SECURITY_MODE_CONFLICT", "AnyTLS ShadowTLS, Restls and JLS modes are mutually exclusive")
        }
        if (mutuallyExclusive > 0) {
            issues += error("MIHOMO_ANYTLS_ADVANCED_SECURITY_NOT_EXPOSED", "advanced AnyTLS security wrappers require a separate validated implementation slice")
        }
    }

    private fun rejectUnsafeTls(profile: PVProfile, issues: MutableList<ValidationIssue>) {
        if (profile.extensions["mihomo.skip-cert-verify"].equals("true", ignoreCase = true)) {
            issues += error("MIHOMO_UNSAFE_TLS_REJECTED", "PVNetwork does not expose skip-cert-verify for Mihomo protocols")
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
        const val ADAPTER_ID = "pvnetwork-mihomo"
        const val ADAPTER_VERSION = "0.1.0"

        const val HYSTERIA2_CAPABILITY = "hysteria2"
        const val TUIC_CAPABILITY = "tuic"
        const val ANYTLS_CAPABILITY = "anytls"
        val MIHOMO_PROTOCOLS = setOf(HYSTERIA2_CAPABILITY, TUIC_CAPABILITY, ANYTLS_CAPABILITY)

        const val HYSTERIA2_PASSWORD_SECRET_ROLE = "mihomo.hysteria2.password"
        const val HYSTERIA2_OBFS_PASSWORD_SECRET_ROLE = "mihomo.hysteria2.obfs-password"
        const val TUIC_PASSWORD_SECRET_ROLE = "mihomo.tuic.password"
        const val ANYTLS_PASSWORD_SECRET_ROLE = "mihomo.anytls.password"

        private val HYSTERIA2_OBFS = setOf("salamander", "gecko")
        private val UUID_PATTERN = Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$")
    }
}
