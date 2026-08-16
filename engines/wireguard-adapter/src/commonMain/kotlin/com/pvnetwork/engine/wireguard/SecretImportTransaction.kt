package com.pvnetwork.engine.wireguard

import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore

/** Rolls back secret references created by one import unless explicitly committed. */
internal class SecretImportTransaction(
    private val delegate: SecretStore,
) : SecretStore {
    private val created = mutableListOf<SecretRef>()
    private var committed = false

    override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef {
        check(!committed) { "secret transaction is already committed" }
        val ref = delegate.put(purpose, secret)
        created += ref
        return ref
    }

    override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? =
        delegate.withSecret(ref, block)

    override fun delete(ref: SecretRef): Boolean {
        created.remove(ref)
        return delegate.delete(ref)
    }

    fun commit() {
        committed = true
        created.clear()
    }

    fun rollback() {
        if (committed) return
        created.asReversed().forEach { ref ->
            runCatching { delegate.delete(ref) }
        }
        created.clear()
    }
}
