package com.pvnetwork.engine.openvpn

import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.util.Comparator
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class JvmSystemOpenVpnRuntimeTest {
    @Test
    fun systemRuntimeMaterializesProtectedConfigAndCleansItOnStop() {
        val fixture = FakeOpenVpnFixture.create()
        try {
            val store = MemorySecretStore()
            val source = "remote example.invalid 1194 udp"
            val profile = OpenVpnImporter(store).import(source, ProfileId("system-runtime"), "System runtime").canonicalProfile
            val factory = JvmSystemOpenVpnRuntimeFactory(fixture.executable)
            val adapter = OpenVpnAdapter(factory)

            assertTrue(factory.runtimeDescriptor.available)
            assertEquals("OpenVPN 2.6.99 PVNetwork fake", factory.runtimeDescriptor.upstreamVersion)
            assertTrue(adapter.validate(profile).isValid)

            val prepared = adapter.prepare(profile, store)
            val connected = CountDownLatch(1)
            val states = CopyOnWriteArrayList<ConnectionSnapshot>()
            prepared.start { snapshot ->
                states += snapshot
                if (snapshot.state == ConnectionState.CONNECTED) connected.countDown()
            }

            assertTrue(connected.await(5, TimeUnit.SECONDS), "fake OpenVPN runtime did not reach CONNECTED")
            val configPath = fixture.awaitConfigPath()
            val runtimeDirectory = configPath.parent
            assertEquals(source, Files.readString(configPath))
            assertEquals(
                setOf(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE),
                Files.getPosixFilePermissions(configPath),
            )
            assertEquals(
                setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE,
                ),
                Files.getPosixFilePermissions(runtimeDirectory),
            )
            assertTrue(states.any { it.state == ConnectionState.PREPARING })
            assertTrue(states.any { it.state == ConnectionState.CONNECTING })
            assertTrue(states.any { it.state == ConnectionState.ESTABLISHING_TUNNEL })
            assertEquals(ConnectionState.CONNECTED, prepared.snapshot().state)

            prepared.stop { states += it }
            assertEquals(ConnectionState.DISCONNECTED, prepared.snapshot().state)
            assertFalse(Files.exists(configPath))
            assertFalse(Files.exists(runtimeDirectory))
            assertTrue(states.any { it.state == ConnectionState.DISCONNECTING })
            assertTrue(states.any { it.state == ConnectionState.DISCONNECTED })
            assertFalse(states.any { it.state == ConnectionState.ERROR })
        } finally {
            fixture.close()
        }
    }

    @Test
    fun missingProtectedSourceFailsClosedWithoutLaunchingProcess() {
        val fixture = FakeOpenVpnFixture.create()
        try {
            val store = MemorySecretStore()
            val imported = OpenVpnImporter(store).import(
                "remote example.invalid 1194 udp",
                ProfileId("missing-source"),
                "Missing source",
            )
            val adapter = OpenVpnAdapter(JvmSystemOpenVpnRuntimeFactory(fixture.executable))
            val prepared = adapter.prepare(imported.canonicalProfile, store)
            assertTrue(store.delete(imported.config.materials.originalProfileRef))

            val states = CopyOnWriteArrayList<ConnectionSnapshot>()
            prepared.start { states += it }

            assertEquals(ConnectionState.ERROR, prepared.snapshot().state)
            assertTrue(states.any { it.reasonCode == "OPENVPN_SOURCE_SECRET_UNAVAILABLE" })
            assertFalse(Files.exists(fixture.marker))
        } finally {
            fixture.close()
        }
    }

    @Test
    fun executableMustPassVersionProbeBeforeAdvertisingRuntime() {
        val dir = Files.createTempDirectory("pvnetwork-openvpn-bad-probe-")
        try {
            val executable = dir.resolve("openvpn")
            Files.writeString(executable, "#!/bin/sh\nexit 12\n")
            executable.toFile().setExecutable(true, true)
            val factory = JvmSystemOpenVpnRuntimeFactory(executable)
            assertFalse(factory.runtimeDescriptor.available)
            assertEquals(null, factory.runtimeDescriptor.upstreamVersion)
        } finally {
            deleteTree(dir)
        }
    }

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1

        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef =
            SecretRef("secret://jvm-openvpn-test/${nextId++}").also { values[it.value] = secret.copyOf() }

        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val stored = values[ref.value] ?: return null
            val copy = stored.copyOf()
            return try { block(copy) } finally { copy.clearSecret() }
        }

        override fun delete(ref: SecretRef): Boolean =
            values.remove(ref.value)?.let { it.clearSecret(); true } ?: false
    }

    private class FakeOpenVpnFixture(
        val directory: Path,
        val executable: Path,
        val marker: Path,
    ) : AutoCloseable {
        fun awaitConfigPath(): Path {
            repeat(50) {
                if (Files.exists(marker)) {
                    val value = Files.readString(marker).trim()
                    if (value.isNotBlank()) return Path.of(value)
                }
                Thread.sleep(50)
            }
            return assertNotNull(null, "fake OpenVPN runtime did not publish its config path")
        }

        override fun close() = deleteTree(directory)

        companion object {
            fun create(): FakeOpenVpnFixture {
                val directory = Files.createTempDirectory("pvnetwork-openvpn-fake-")
                val executable = directory.resolve("openvpn")
                val marker = directory.resolve("config-path.txt")
                val markerLiteral = shellSingleQuote(marker.toString())
                Files.writeString(
                    executable,
                    """#!/bin/sh
if [ "${'$'}{1:-}" = "--version" ]; then
  echo "OpenVPN 2.6.99 PVNetwork fake"
  exit 0
fi
config=""
while [ "${'$'}#" -gt 0 ]; do
  if [ "${'$'}1" = "--config" ]; then
    config="${'$'}2"
    shift 2
  else
    shift
  fi
done
if [ -z "${'$'}config" ]; then
  exit 21
fi
printf '%s' "${'$'}config" > $markerLiteral
echo "Initialization Sequence Completed"
trap 'exit 0' TERM INT
while :; do sleep 1; done
""",
                )
                executable.toFile().setExecutable(true, true)
                return FakeOpenVpnFixture(directory, executable, marker)
            }
        }
    }

    companion object {
        private fun shellSingleQuote(value: String): String = "'" + value.replace("'", "'\\''") + "'"

        private fun deleteTree(path: Path) {
            if (!Files.exists(path)) return
            Files.walk(path).use { stream ->
                stream.sorted(Comparator.reverseOrder()).forEach { Files.deleteIfExists(it) }
            }
        }
    }
}
