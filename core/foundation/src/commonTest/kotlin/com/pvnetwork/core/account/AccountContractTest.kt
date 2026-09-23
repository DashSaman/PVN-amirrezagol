package com.pvnetwork.core.account

import com.pvnetwork.core.profile.SecretRef
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class AccountContractTest {

    @Test
    fun sessionReferencesSecretInsteadOfEmbeddingToken() {
        val session = AccountSession(
            accountId = "account-1",
            accessTokenRef = SecretRef("secret-ref-1"),
            expiresAtEpochSeconds = 4_102_444_800L,
        )
        assertEquals("account-1", session.accountId)
        assertEquals("secret-ref-1", session.accessTokenRef.value)
    }

    @Test
    fun sessionRejectsBlankAccountId() {
        assertFailsWith<IllegalArgumentException> {
            AccountSession(
                accountId = " ",
                accessTokenRef = SecretRef("secret-ref-1"),
                expiresAtEpochSeconds = 1L,
            )
        }
    }

    @Test
    fun sessionExpiryIsObservable() {
        val session = AccountSession(
            accountId = "account-1",
            accessTokenRef = SecretRef("secret-ref-1"),
            expiresAtEpochSeconds = 1_000L,
        )
        assertTrue(session.isExpiredAt(999L).not())
        assertTrue(session.isExpiredAt(1_000L))
    }

    @Test
    fun accountAcceptsOptionalDisplayIdentity() {
        val account = Account(accountId = "account-1")
        assertEquals(null, account.displayName)
        assertEquals(null, account.email)
    }
}
