package com.pvnetwork.core.adapter

import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.security.SecretStore

data class AdapterId(val value: String) {
    init {
        require(value.isNotBlank()) { "adapter id must not be blank" }
    }
}

data class CapabilityId(val value: String) {
    init {
        require(value.isNotBlank()) { "capability id must not be blank" }
    }
}

data class AdapterDescriptor(
    val id: AdapterId,
    val version: String,
    val capabilities: Set<CapabilityId>,
    val upstreamVersion: String? = null,
) {
    init {
        require(version.isNotBlank()) { "adapter version must not be blank" }
    }
}

enum class ValidationSeverity {
    WARNING,
    ERROR,
}

data class ValidationIssue(
    val code: String,
    val message: String,
    val severity: ValidationSeverity,
)

data class ProfileValidation(
    val issues: List<ValidationIssue> = emptyList(),
) {
    val isValid: Boolean
        get() = issues.none { it.severity == ValidationSeverity.ERROR }
}

/**
 * A prepared connection is ephemeral adapter/platform state. It must never be
 * persisted as the canonical profile.
 */
interface PreparedConnection {
    fun start(onState: (ConnectionSnapshot) -> Unit)
    fun stop(onState: (ConnectionSnapshot) -> Unit)
    fun snapshot(): ConnectionSnapshot
}

/**
 * Product-owned engine boundary. Capability claims come only from a concrete
 * adapter descriptor, never from the research registry.
 */
interface CoreAdapter {
    val descriptor: AdapterDescriptor

    fun validate(profile: PVProfile): ProfileValidation

    fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection
}

class CapabilityRegistry(descriptors: Iterable<AdapterDescriptor>) {
    private val byAdapter: Map<AdapterId, AdapterDescriptor>

    init {
        val items = descriptors.toList()
        require(items.map { it.id }.distinct().size == items.size) {
            "adapter ids must be unique"
        }
        byAdapter = items.associateBy { it.id }
    }

    fun descriptor(id: AdapterId): AdapterDescriptor? = byAdapter[id]

    fun adaptersFor(capability: CapabilityId): List<AdapterDescriptor> =
        byAdapter.values.filter { capability in it.capabilities }.sortedBy { it.id.value }

    fun supportedCapabilities(): Set<CapabilityId> =
        byAdapter.values.flatMap { it.capabilities }.toSet()
}
