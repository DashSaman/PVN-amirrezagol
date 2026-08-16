package com.pvnetwork.engine.xray

import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore

internal class XraySecretTransaction(private val delegate: SecretStore) : SecretStore {
    private val created = mutableListOf<SecretRef>()
    private var committed = false
    override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef {
        check(!committed) { "Xray secret transaction already committed" }
        return delegate.put(purpose, secret).also(created::add)
    }
    override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? = delegate.withSecret(ref, block)
    override fun delete(ref: SecretRef): Boolean { created.remove(ref); return delegate.delete(ref) }
    fun commit() { committed = true; created.clear() }
    fun rollback() { if (!committed) created.asReversed().forEach { runCatching { delegate.delete(it) } }; created.clear() }
}
