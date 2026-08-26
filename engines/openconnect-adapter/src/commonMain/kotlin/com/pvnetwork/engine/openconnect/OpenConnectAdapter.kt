package com.pvnetwork.engine.openconnect

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

data class OpenConnectRuntimeDescriptor(
    val implementationId: String,
    val upstreamVersion: String? = null,
    val available: Boolean,
    val supportedProtocols: Set<String> = emptySet(),
) {
    init { require(implementationId.isNotBlank()) { "OpenConnect runtime implementation id must not be blank" } }
}

/** Host/platform runtime boundary. This module never links or bundles OpenConnect. */
interface OpenConnectRuntimeFactory {
    val runtimeDescriptor: OpenConnectRuntimeDescriptor
    fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection
}

/**
 * M4 OpenConnect-compatible slice.
 *
 * The first retained runtime intentionally implements only Cisco-compatible
 * `anyconnect`. Upstream OpenConnect supports additional protocols, but they
 * remain fail-closed here until protocol-specific runtime/interop evidence is
 * retained. Password authentication is supplied through SecretStore and must
 * never be placed on the child process command line.
 */
class OpenConnectAdapter(
    private val runtimeFactory: OpenConnectRuntimeFactory,
) : CoreAdapter {
    override val descriptor: AdapterDescriptor
        get() = AdapterDescriptor(
            id = AdapterId(ADAPTER_ID),
            version = ADAPTER_VERSION,
            capabilities = if (
                runtimeFactory.runtimeDescriptor.available &&
                ANYCONNECT_PROTOCOL in runtimeFactory.runtimeDescriptor.supportedProtocols
            ) setOf(CapabilityId(PROTOCOL_ID)) else emptySet(),
            upstreamVersion = runtimeFactory.runtimeDescriptor.upstreamVersion,
        )

    override fun validate(profile: PVProfile): ProfileValidation {
        val issues = mutableListOf<ValidationIssue>()
        if (profile.protocolId != PROTOCOL_ID) {
            issues += error("OPENCONNECT_PROTOCOL_MISMATCH", "profile protocolId must be $PROTOCOL_ID")
        }

        val selectedProtocol = profile.extensions[PROTOCOL_EXTENSION]?.trim()?.lowercase().orEmpty().ifBlank { ANYCONNECT_PROTOCOL }
        if (selectedProtocol !in UPSTREAM_PROTOCOL_NAMES) {
            issues += error("OPENCONNECT_PROTOCOL_UNKNOWN", "unsupported OpenConnect protocol name: $selectedProtocol")
        } else if (selectedProtocol !in IMPLEMENTED_PROTOCOLS) {
            issues += error(
                "OPENCONNECT_PROTOCOL_NOT_IMPLEMENTED",
                "$selectedProtocol is known upstream but is not enabled by this evidence-backed adapter slice",
            )
        } else if (selectedProtocol !in runtimeFactory.runtimeDescriptor.supportedProtocols) {
            issues += error("OPENCONNECT_RUNTIME_PROTOCOL_UNAVAILABLE", "host OpenConnect runtime does not advertise $selectedProtocol")
        }

        val username = profile.extensions[USERNAME_EXTENSION]
        if (username.isNullOrBlank() || username.any(Char::isISOControl)) {
            issues += error("OPENCONNECT_USERNAME_INVALID", "a non-empty control-character-free username is required")
        }
        if (profile.secretRefs[PASSWORD_SECRET_ROLE] == null) {
            issues += error("OPENCONNECT_PASSWORD_SECRET_MISSING", "password authentication requires a protected password secret")
        }

        val serverPath = profile.extensions[SERVER_PATH_EXTENSION]
        if (serverPath != null && (serverPath.isBlank() || !serverPath.startsWith('/') || serverPath.any(Char::isISOControl))) {
            issues += error("OPENCONNECT_SERVER_PATH_INVALID", "server path must start with / and contain no control characters")
        }
        val userGroup = profile.extensions[USERGROUP_EXTENSION]
        if (userGroup != null && (userGroup.isBlank() || userGroup.any(Char::isISOControl))) {
            issues += error("OPENCONNECT_USERGROUP_INVALID", "usergroup must be non-empty and contain no control characters")
        }

        if (profile.extensions[NO_SYSTEM_TRUST_EXTENSION].equals("true", ignoreCase = true)) {
            issues += error(
                "OPENCONNECT_SYSTEM_TRUST_DISABLE_FORBIDDEN",
                "disabling system certificate trust is not supported by this slice",
            )
        }
        if (profile.extensions[INSECURE_CIPHERS_EXTENSION].equals("true", ignoreCase = true)) {
            issues += error(
                "OPENCONNECT_INSECURE_CIPHERS_FORBIDDEN",
                "legacy insecure cipher enablement is not supported by this slice",
            )
        }
        if (profile.extensions[EXTERNAL_BROWSER_EXTENSION] != null || profile.extensions[CSD_WRAPPER_EXTENSION] != null) {
            issues += error(
                "OPENCONNECT_EXTERNAL_EXECUTION_UNSUPPORTED",
                "external-browser and CSD wrapper execution are outside this runtime slice",
            )
        }
        if (!runtimeFactory.runtimeDescriptor.available) {
            issues += error("OPENCONNECT_RUNTIME_UNAVAILABLE", "no approved host-supplied OpenConnect runtime is available")
        }
        return ProfileValidation(issues)
    }

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val validation = validate(profile)
        check(validation.isValid) { "OpenConnect profile/runtime is not ready: ${validation.issues.joinToString { it.code }}" }
        return runtimeFactory.prepare(profile, secretStore)
    }

    private fun error(code: String, message: String) = ValidationIssue(code, message, ValidationSeverity.ERROR)

    companion object {
        const val PROTOCOL_ID = "openconnect"
        const val ADAPTER_ID = "pvnetwork-openconnect"
        const val ADAPTER_VERSION = "0.1.0"
        const val ANYCONNECT_PROTOCOL = "anyconnect"
        const val PROTOCOL_EXTENSION = "openconnect.protocol"
        const val USERNAME_EXTENSION = "openconnect.username"
        const val SERVER_PATH_EXTENSION = "openconnect.server-path"
        const val USERGROUP_EXTENSION = "openconnect.usergroup"
        const val NO_SYSTEM_TRUST_EXTENSION = "openconnect.no-system-trust"
        const val INSECURE_CIPHERS_EXTENSION = "openconnect.insecure-ciphers"
        const val EXTERNAL_BROWSER_EXTENSION = "openconnect.external-browser"
        const val CSD_WRAPPER_EXTENSION = "openconnect.csd-wrapper"
        const val PASSWORD_SECRET_ROLE = "password"

        val IMPLEMENTED_PROTOCOLS = setOf(ANYCONNECT_PROTOCOL)
        val UPSTREAM_PROTOCOL_NAMES = setOf("anyconnect", "nc", "pulse", "gp", "f5", "fortinet", "array")
    }
}
