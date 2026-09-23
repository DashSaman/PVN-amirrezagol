package com.pvnetwork.core.account

import com.pvnetwork.core.profile.SecretRef

/**
 * Canonical account identity. Backend/provider-specific profile fields stay
 * behind their own boundaries; only product-level identity belongs here.
 */
data class Account(
    val accountId: String,
    val displayName: String? = null,
    val email: String? = null,
) {
    init {
        require(accountId.isNotBlank()) { "account id must not be blank" }
    }
}

/**
 * An authenticated session references its reusable token through the secret
 * store; the raw token never lives in the canonical model.
 */
data class AccountSession(
    val accountId: String,
    val accessTokenRef: SecretRef,
    val expiresAtEpochSeconds: Long,
) {
    init {
        require(accountId.isNotBlank()) { "account id must not be blank" }
        require(expiresAtEpochSeconds > 0) { "session expiry must be positive" }
    }

    fun isExpiredAt(epochSeconds: Long): Boolean = epochSeconds >= expiresAtEpochSeconds
}
