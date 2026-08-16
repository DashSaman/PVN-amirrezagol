package com.pvnetwork.engine.xray

import com.pvnetwork.core.adapter.CapabilityId
import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VlessShareLinkImporterTest {
    @Test
    fun parsesRealityVisionXhttpWithoutPersistingIdentity() {
        val store = MemorySecretStore()
        val identity = "11111111-2222-3333-4444-555555555555"
        val link = "vless://$identity@example.invalid:443?security=reality&type=xhttp&flow=xtls-rprx-vision&sni=cdn.example&fp=chrome&pbk=server-public-key&sid=abcd&path=%2Fedge#My%20VLESS"
        val result = VlessShareLinkImporter(store).import(link, ProfileId("vless-1"))
        assertEquals(XraySecurity.REALITY, result.config.security)
        assertEquals(XrayTransport.XHTTP, result.config.transport)
        assertEquals("xtls-rprx-vision", result.config.flow)
        assertEquals("/edge", result.config.path)
        assertEquals("My VLESS", result.canonicalProfile.displayName)
        assertTrue(result.warnings.isEmpty())
        val persisted = result.canonicalProfile.toString() + result.config.toString()
        assertFalse(identity in persisted)
        assertEquals(identity, store.read(result.config.identityRef))
        assertEquals(link, store.read(result.config.protectedOriginalRef))
    }

    @Test
    fun unknownCombinationFieldsAreWarningsNotFakeSupport() {
        val result = VlessShareLinkImporter(MemorySecretStore()).import(
            "vless://id@example.invalid:443?security=future&type=futureTransport&flow=future-flow&experimental=1",
            ProfileId("vless-2"),
        )
        assertEquals(XraySecurity.UNKNOWN, result.config.security)
        assertEquals(XrayTransport.UNKNOWN, result.config.transport)
        assertTrue(result.warnings.size >= 4)
    }

    @Test
    fun invalidEndpointRollsBackProtectedSource() {
        val store = MemorySecretStore()
        assertFailsWith<VlessShareLinkException> {
            VlessShareLinkImporter(store).import("vless://id@example.invalid:not-port", ProfileId("bad"))
        }
        assertEquals(0, store.activeCount)
    }

    @Test
    fun adapterCapabilityComesOnlyFromConcreteRuntime() {
        val store = MemorySecretStore()
        val profile = VlessShareLinkImporter(store).import("vless://id@example.invalid:443", ProfileId("runtime")).canonicalProfile
        val unavailable = XrayAdapter(FakeRuntimeFactory(emptySet()))
        assertTrue(unavailable.descriptor.capabilities.isEmpty())
        assertFalse(unavailable.validate(profile).isValid)
        val available = XrayAdapter(FakeRuntimeFactory(setOf("vless")))
        assertTrue(CapabilityId("vless") in available.descriptor.capabilities)
        assertTrue(available.validate(profile).isValid)
    }

    private class FakeRuntimeFactory(capabilities: Set<String>) : XrayRuntimeFactory {
        override val runtimeDescriptor = XrayRuntimeDescriptor("fake-xray", "test-only", capabilities)
        override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection = object : PreparedConnection {
            override fun start(onState: (ConnectionSnapshot) -> Unit) = onState(ConnectionSnapshot(ConnectionState.PREPARING, XrayAdapter.ADAPTER_ID))
            override fun stop(onState: (ConnectionSnapshot) -> Unit) = onState(ConnectionSnapshot(ConnectionState.DISCONNECTED, XrayAdapter.ADAPTER_ID))
            override fun snapshot() = ConnectionSnapshot(ConnectionState.DISCONNECTED, XrayAdapter.ADAPTER_ID)
        }
    }

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>(); private var nextId = 1
        val activeCount get() = values.size
        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef = SecretRef("secret://xray-test/${nextId++}").also { values[it.value] = secret.copyOf() }
        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val stored = values[ref.value] ?: return null; val copy = stored.copyOf()
            return try { block(copy) } finally { copy.clearSecret() }
        }
        override fun delete(ref: SecretRef): Boolean = values.remove(ref.value)?.let { it.clearSecret(); true } ?: false
        fun read(ref: SecretRef): String = withSecret(ref) { it.concatToString() }!!
    }
}
