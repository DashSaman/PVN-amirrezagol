package com.pvnetwork.engine.openvpn

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

class OpenVpnImporterTest {
    @Test
    fun inlinePrivateMaterialAndOriginalSourceStayBehindSecretRefs() {
        val store = MemorySecretStore()
        val privateKey = "PRIVATE-KEY-MATERIAL-MUST-NOT-LEAK"
        val tlsCrypt = "TLS-CRYPT-MATERIAL-MUST-NOT-LEAK"
        val source = """
            client
            dev tun
            proto udp
            remote vpn.example.invalid 1194 udp
            auth-user-pass
            <ca>
            PUBLIC-CA-CERT
            </ca>
            <cert>
            PUBLIC-CLIENT-CERT
            </cert>
            <key>
            $privateKey
            </key>
            <tls-crypt>
            $tlsCrypt
            </tls-crypt>
        """.trimIndent()

        val result = OpenVpnImporter(store).import(source, ProfileId("ovpn-1"), "OpenVPN test")
        assertEquals("openvpn", result.canonicalProfile.protocolId)
        assertEquals("vpn.example.invalid", result.canonicalProfile.endpoint.host)
        assertEquals(1194, result.canonicalProfile.endpoint.port)
        assertTrue(result.config.authUserPassRequired)
        assertEquals(1, result.config.materials.privateKeyRefs.size)
        assertEquals(1, result.config.materials.tlsCryptRefs.size)
        assertTrue(result.warnings.isEmpty())

        val persistedView = result.canonicalProfile.toString() + result.config.toString()
        assertFalse(privateKey in persistedView)
        assertFalse(tlsCrypt in persistedView)
        assertFalse("PUBLIC-CA-CERT" in persistedView)
        assertEquals(source, store.read(result.config.materials.originalProfileRef))
        assertEquals(privateKey, store.read(result.config.materials.privateKeyRefs.single()))
    }

    @Test
    fun unsupportedOrUnresolvedDirectivesCannotBecomeRuntimeSupport() {
        val store = MemorySecretStore()
        val source = """
            client
            remote example.invalid 443 tcp-client
            cipher AES-256-GCM
            redirect-gateway def1
            ca /etc/openvpn/ca.crt
        """.trimIndent()
        val result = OpenVpnImporter(store).import(source, ProfileId("ovpn-2"), "Unsupported")

        assertTrue("cipher" in result.config.unsupportedDirectiveNames)
        assertTrue("redirect-gateway" in result.config.unsupportedDirectiveNames)
        assertTrue(result.warnings.any { it.field == "ca" })
        assertEquals("ca", result.canonicalProfile.extensions["openvpn.unresolved-external-material-names"])
        assertEquals(source, store.read(result.config.materials.originalProfileRef))

        val validation = OpenVpnAdapter(FakeRuntimeFactory(true)).validate(result.canonicalProfile)
        assertFalse(validation.isValid)
        val codes = validation.issues.map { it.code }.toSet()
        assertTrue("OPENVPN_UNSUPPORTED_DIRECTIVES_PRESENT" in codes)
        assertTrue("OPENVPN_EXTERNAL_MATERIAL_UNRESOLVED" in codes)
    }

    @Test
    fun externalAuthUserPassFileIsExplicitlyUnresolved() {
        val result = OpenVpnImporter(MemorySecretStore()).import(
            """
                client
                remote example.invalid 1194 udp
                auth-user-pass credentials.txt
            """.trimIndent(),
            ProfileId("auth-file"),
            "External auth",
        )
        assertEquals("auth-user-pass", result.canonicalProfile.extensions["openvpn.unresolved-external-material-names"])
        val validation = OpenVpnAdapter(FakeRuntimeFactory(true)).validate(result.canonicalProfile)
        assertFalse(validation.isValid)
        assertTrue(validation.issues.any { it.code == "OPENVPN_EXTERNAL_MATERIAL_UNRESOLVED" })
    }

    @Test
    fun failedImportRollsBackProtectedOriginalAndAnyMaterial() {
        val store = MemorySecretStore()
        assertFailsWith<OpenVpnConfigException> {
            OpenVpnImporter(store).import(
                """
                    remote example.invalid not-a-port
                    <key>
                    should-rollback
                    </key>
                """.trimIndent(),
                ProfileId("bad"),
                "Bad",
            )
        }
        assertEquals(0, store.activeCount)
    }

    @Test
    fun adapterNeverTurnsResearchIntoRuntimeCapability() {
        val store = MemorySecretStore()
        val profile = OpenVpnImporter(store).import(
            "remote example.invalid 1194 udp",
            ProfileId("runtime"),
            "Runtime",
        ).canonicalProfile

        val unavailable = OpenVpnAdapter(FakeRuntimeFactory(false))
        assertTrue(unavailable.descriptor.capabilities.isEmpty())
        assertFalse(unavailable.validate(profile).isValid)

        val available = OpenVpnAdapter(FakeRuntimeFactory(true))
        assertTrue(CapabilityId("openvpn") in available.descriptor.capabilities)
        assertTrue(available.validate(profile).isValid)
    }

    private class FakeRuntimeFactory(available: Boolean) : OpenVpnRuntimeFactory {
        override val runtimeDescriptor = OpenVpnRuntimeDescriptor("fake-test-runtime", "test-only", available)
        override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection = object : PreparedConnection {
            override fun start(onState: (ConnectionSnapshot) -> Unit) = onState(ConnectionSnapshot(ConnectionState.PREPARING, OpenVpnAdapter.ADAPTER_ID))
            override fun stop(onState: (ConnectionSnapshot) -> Unit) = onState(ConnectionSnapshot(ConnectionState.DISCONNECTED, OpenVpnAdapter.ADAPTER_ID))
            override fun snapshot() = ConnectionSnapshot(ConnectionState.DISCONNECTED, OpenVpnAdapter.ADAPTER_ID)
        }
    }

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1
        val activeCount get() = values.size
        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef = SecretRef("secret://openvpn-test/${nextId++}").also { values[it.value] = secret.copyOf() }
        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val stored = values[ref.value] ?: return null
            val copy = stored.copyOf()
            return try { block(copy) } finally { copy.clearSecret() }
        }
        override fun delete(ref: SecretRef): Boolean = values.remove(ref.value)?.let { it.clearSecret(); true } ?: false
        fun read(ref: SecretRef): String = withSecret(ref) { it.concatToString() }!!
    }
}
