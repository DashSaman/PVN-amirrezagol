package com.pvnetwork.core.entitlement

/**
 * A device registered against an account for device-limit enforcement and
 * remote revocation. No hardware identifiers are embedded here; the device
 * id is an opaque backend-issued value.
 */
data class DeviceRegistration(
    val deviceId: String,
    val displayName: String? = null,
    val registeredAtEpochSeconds: Long,
    val lastSeenAtEpochSeconds: Long? = null,
    val revoked: Boolean = false,
) {
    init {
        require(deviceId.isNotBlank()) { "device id must not be blank" }
        require(registeredAtEpochSeconds > 0) { "registration timestamp must be positive" }
        if (lastSeenAtEpochSeconds != null) {
            require(lastSeenAtEpochSeconds >= registeredAtEpochSeconds) {
                "last-seen timestamp must not precede registration"
            }
        }
    }
}
