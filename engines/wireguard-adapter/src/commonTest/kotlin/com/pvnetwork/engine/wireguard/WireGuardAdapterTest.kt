package com.pvnetwork.engine.wireguard

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

class WireGuardAdapterTest {
    @Test
    fun importMovesSecretsOutOfCanonicalProfileAndPreservesNonSecretConfig() {
        val store = MemorySecretStore()
        val privateKey = "private-key-material-that-must-not-be-persisted"
        val presharedKey = "preshared-key-material-that-must-not-be-persisted"
        val result = WireGuardConfImporter(store).import(
            text = """
                [Interface]
                PrivateKey = $privateKey
                Address = 10.10.0.2/32, fd00::2/128
                DNS = 1.1.1.1
                MTU = 1420

                [Peer]
                PublicKey = public-key-material
                PresharedKey = $presharedKey
                Endpoint = vpn.example.invalid:51820
                AllowedIPs = 0.0.0.0/0, ::/0
                PersistentKeepalive = 25
            """.trimIndent(),
            profileId = ProfileId("wg-1"),
            displayName = "WireGuard test",
        )

        assertEquals("wireguard", result.canonicalProfile.protocolId)
        assertEquals("vpn.example.invalid", result.canonicalProfile.endpoint.host)
        assertEquals(51820, result.canonicalProfile.endpoint.port)
        assertEquals(2, result.canonicalProfile.secretRefs.size)
        assertEquals(listOf("10.10.0.2/32", "fd00::2/128"), result.config.addresses)
        assertEquals(listOf("0.0.0.0/0", "::/0"), result.config.peers.single().allowedIps)
        assertTrue(result.warnings.isEmpty())

        val persistedView = result.canonicalProfile.toString() + result.config.toString()
        assertFalse(privateKey in persistedView)
        assertFalse(presharedKey in persistedView)
        assertEquals(privateKey, store.read(result.config.privateKeyRef))
        assertEquals(presharedKey, store.read(result.config.peers.single().preSharedKeyRef!!))
    }

    @Test
    fun failedImportRollsBackSecretsCreatedByThatImport() {
        val store = MemorySecretStore()
        assertFailsWith<WireGuardConfigException> {
            WireGuardConfImporter(store).import(
                """
                    [Interface]
                    PrivateKey = must-be-rolled-back
                    Address = 10.0.0.999/32
                    [Peer]
                    PublicKey = public
                    Endpoint = example.invalid:51820
                    AllowedIPs = 0.0.0.0/0
                """.trimIndent(),
                ProfileId("rollback"),
                "Rollback",
            )
        }
        assertEquals(0, store.activeCount)
    }

    @Test
    fun importWarnsOnUnsupportedFieldInsteadOfSilentlyClaimingSupport() {
        val result = WireGuardConfImporter(MemorySecretStore()).import(
            text = """
                [Interface]
                PrivateKey = secret
                Address = 10.0.0.2/32
                Table = 123

                [Peer]
                PublicKey = public
                Endpoint = [2001:db8::1]:51820
                AllowedIPs = 10.0.0.0/8
                CustomPeerField = value
            """.trimIndent(),
            profileId = ProfileId("wg-2"),
            displayName = "Warnings",
        )

        assertEquals(2, result.warnings.size)
        assertEquals("2001:db8::1", result.canonicalProfile.endpoint.host)
    }

    @Test
    fun malformedCidrAndMissingEndpointAreRejected() {
        val importer = WireGuardConfImporter(MemorySecretStore())
        assertFailsWith<WireGuardConfigException> {
            importer.import(
                """
                    [Interface]
                    PrivateKey = secret
                    Address = 10.0.0.999/32
                    [Peer]
                    PublicKey = public
                    Endpoint = example.invalid:51820
                    AllowedIPs = 0.0.0.0/0
                """.trimIndent(),
                ProfileId("bad-cidr"),
                "Bad CIDR",
            )
        }
        assertFailsWith<WireGuardConfigException> {
            importer.import(
                """
                    [Interface]
                    PrivateKey = secret
                    Address = 10.0.0.2/32
                    [Peer]
                    PublicKey = public
                    AllowedIPs = 0.0.0.0/0
                """.trimIndent(),
                ProfileId("no-endpoint"),
                "No endpoint",
            )
        }
    }

    @Test
    fun adapterDoesNotAdvertiseResearchAsRuntimeCapability() {
        val unavailable = WireGuardAdapter(FakeRuntimeFactory(available = false))
        assertTrue(unavailable.descriptor.capabilities.isEmpty())

        val result = WireGuardConfImporter(MemorySecretStore()).import(
            """
                [Interface]
                PrivateKey = secret
                Address = 10.0.0.2/32
                [Peer]
                PublicKey = public
                Endpoint = example.invalid:51820
                AllowedIPs = 0.0.0.0/0
            """.trimIndent(),
            ProfileId("wg-runtime"),
            "Runtime",
        )
        assertFalse(unavailable.validate(result.canonicalProfile).isValid)

        val available = WireGuardAdapter(FakeRuntimeFactory(available = true))
        assertTrue(CapabilityId("wireguard") in available.descriptor.capabilities)
        assertTrue(available.validate(result.canonicalProfile).isValid)
    }

    private class FakeRuntimeFactory(available: Boolean) : WireGuardRuntimeFactory {
        override val runtimeDescriptor = WireGuardRuntimeDescriptor(
            implementationId = "fake-test-runtime",
            upstreamVersion = "test-only",
            available = available,
        )

        override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection =
            object : PreparedConnection {
                private var snapshot = ConnectionSnapshot(ConnectionState.DISCONNECTED, WireGuardAdapter.ADAPTER_ID)
                override fun start(onState: (ConnectionSnapshot) -> Unit) {
                    snapshot = ConnectionSnapshot(ConnectionState.PREPARING, WireGuardAdapter.ADAPTER_ID)
                    onState(snapshot)
                }
                override fun stop(onState: (ConnectionSnapshot) -> Unit) {
                    snapshot = ConnectionSnapshot(ConnectionState.DISCONNECTED, WireGuardAdapter.ADAPTER_ID)
                    onState(snapshot)
                }
                override fun snapshot(): ConnectionSnapshot = snapshot
            }
    }

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1
        val activeCount: Int get() = values.size

        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef {
            val ref = SecretRef("secret://test/${nextId++}")
            values[ref.value] = secret.copyOf()
            return ref
        }

        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val stored = values[ref.value] ?: return null
            val temporary = stored.copyOf()
            return try {
                block(temporary)
            } finally {
                temporary.clearSecret()
            }
        }

        override fun delete(ref: SecretRef): Boolean = values.remove(ref.value)?.let {
            it.clearSecret()
            true
        } ?: false

        fun read(ref: SecretRef): String = withSecret(ref) { chars -> chars.concatToString() }!!
    }
}
