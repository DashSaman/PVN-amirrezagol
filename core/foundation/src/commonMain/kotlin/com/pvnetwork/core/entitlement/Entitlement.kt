package com.pvnetwork.core.entitlement

/**
 * Quota/expiry entitlement state is server-owned truth. The client renders
 * it and may never mutate it into a more permissive local view.
 */
enum class EntitlementStatus {
    ACTIVE,
    EXPIRED,
    REVOKED,
}

data class Entitlement(
    val planId: String,
    val status: EntitlementStatus,
    val expiresAtEpochSeconds: Long? = null,
    val trafficQuotaBytes: Long? = null,
    val trafficUsedBytes: Long? = null,
    val deviceLimit: Int? = null,
    val features: Set<String> = emptySet(),
) {
    init {
        require(planId.isNotBlank()) { "plan id must not be blank" }
        require(trafficQuotaBytes == null || trafficQuotaBytes >= 0) {
            "traffic quota must not be negative"
        }
        require(trafficUsedBytes == null || trafficUsedBytes >= 0) {
            "traffic used must not be negative"
        }
        require(deviceLimit == null || deviceLimit >= 0) {
            "device limit must not be negative"
        }
        if (expiresAtEpochSeconds != null) {
            require(expiresAtEpochSeconds > 0) { "entitlement expiry must be positive" }
        }
    }

    val hasUnlimitedTraffic: Boolean
        get() = trafficQuotaBytes == null

    /**
     * Remaining traffic in bytes, or null when quota is unlimited. May be
     * negative-equivalent zero-clamped: servers can report usage above a
     * reduced quota; the UI must not hide that state.
     */
    val remainingTrafficBytes: Long?
        get() = if (trafficQuotaBytes == null || trafficUsedBytes == null) {
            null
        } else {
            (trafficQuotaBytes - trafficUsedBytes).coerceAtLeast(0L)
        }

    fun isActiveAt(epochSeconds: Long): Boolean =
        status == EntitlementStatus.ACTIVE &&
            (expiresAtEpochSeconds == null || epochSeconds < expiresAtEpochSeconds)
}
