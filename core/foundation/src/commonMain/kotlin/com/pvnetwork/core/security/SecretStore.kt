package com.pvnetwork.core.security

import com.pvnetwork.core.profile.SecretRef

enum class SecretPurpose {
    PASSWORD,
    PRIVATE_KEY,
    PRE_SHARED_KEY,
    TOKEN,
    CERTIFICATE_CREDENTIAL,
    OTHER,
}

/**
 * Platform security boundary. Production implementations must use OS-protected
 * storage and must not fall back to plaintext files.
 */
interface SecretStore {
    fun put(purpose: SecretPurpose, secret: CharArray): SecretRef

    /**
     * Executes [block] while secret material is available. Implementations should
     * minimize lifetime and clear temporary buffers after the callback returns.
     */
    fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T?

    fun delete(ref: SecretRef): Boolean
}

fun CharArray.clearSecret() {
    fill('\u0000')
}
