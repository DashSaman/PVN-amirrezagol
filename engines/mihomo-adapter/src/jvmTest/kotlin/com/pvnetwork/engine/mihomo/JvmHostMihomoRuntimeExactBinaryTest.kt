package com.pvnetwork.engine.mihomo

import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import java.net.InetAddress
import java.net.ServerSocket
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** Exact-release config/runtime smoke test. It is not remote interoperability evidence. */
class JvmHostMihomoRuntimeExactBinaryTest {
    @Test
    fun exactMihomoFixtureValidatesAndStartsSelectedM3Configs() {
        val executableValue = System.getenv("PVNETWORK_MIHOMO_TEST_EXECUTABLE")?.takeIf(String::isNotBlank) ?: return
        val executable = Path.of(executableValue).toAbsolutePath().normalize()
        assertTrue(Files.isRegularFile(executable) && Files.isExecutable(executable))

        val cases = listOf(
            Case(MihomoAdapter.HYSTERIA2_CAPABILITY, MihomoAdapter.HYSTERIA2_PASSWORD_SECRET_ROLE, "hy2-ci-password"),
            Case(
                MihomoAdapter.TUIC_CAPABILITY,
                MihomoAdapter.TUIC_PASSWORD_SECRET_ROLE,
                "tuic-ci-password",
                mapOf("mihomo.tuic.uuid" to "550e8400-e29b-41d4-a716-446655440000"),
            ),
            Case(MihomoAdapter.ANYTLS_CAPABILITY, MihomoAdapter.ANYTLS_PASSWORD_SECRET_ROLE, "anytls-ci-password"),
        )

        cases.forEach { case ->
            val store = MemorySecretStore()
            val ref = store.putText(case.secret, SecretPurpose.PASSWORD)
            val port = reservePort()
            val factory = JvmHostMihomoRuntimeFactory(executable, port)
            assertEquals(MihomoAdapter.MIHOMO_PROTOCOLS, factory.runtimeDescriptor.availableCapabilities)
            assertTrue(factory.runtimeDescriptor.upstreamVersion?.startsWith("Mihomo Meta v1.19.30 ") == true)
            val profile = PVProfile(
                id = ProfileId("mihomo-exact-${case.protocol}"),
                displayName = "Mihomo exact ${case.protocol}",
                protocolId = case.protocol,
                endpoint = Endpoint("127.0.0.1", 9),
                secretRefs = mapOf(case.role to ref),
                extensions = mapOf(
                    "mihomo.application-protocol" to case.protocol,
                    "mihomo.sni" to "localhost",
                ) + case.extra,
            )
            val adapter = MihomoAdapter(factory)
            assertTrue(adapter.validate(profile).isValid)
            val prepared = adapter.prepare(profile, store)
            val connected = CountDownLatch(1)
            val failed = CountDownLatch(1)
            prepared.start {
                if (it.state == ConnectionState.CONNECTED) connected.countDown()
                if (it.state == ConnectionState.ERROR) failed.countDown()
            }
            assertTrue(connected.await(10, TimeUnit.SECONDS), "${case.protocol} did not start its exact-fixture local runtime; error=${failed.count == 0L}")
            assertEquals(ConnectionState.CONNECTED, prepared.snapshot().state)
            prepared.stop { }
            assertEquals(ConnectionState.DISCONNECTED, prepared.snapshot().state)
        }
    }

    private fun reservePort(): Int = ServerSocket(0, 50, InetAddress.getByName("127.0.0.1")).use { it.localPort }

    private data class Case(val protocol: String, val role: String, val secret: String, val extra: Map<String, String> = emptyMap())

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1

        fun putText(value: String, purpose: SecretPurpose): SecretRef {
            val chars = value.toCharArray()
            return try { put(purpose, chars) } finally { chars.clearSecret() }
        }

        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef =
            SecretRef("secret://mihomo-exact-ci/${nextId++}").also { values[it.value] = secret.copyOf() }

        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val copy = values[ref.value]?.copyOf() ?: return null
            return try { block(copy) } finally { copy.clearSecret() }
        }

        override fun delete(ref: SecretRef): Boolean = values.remove(ref.value)?.let { it.clearSecret(); true } ?: false
    }
}
