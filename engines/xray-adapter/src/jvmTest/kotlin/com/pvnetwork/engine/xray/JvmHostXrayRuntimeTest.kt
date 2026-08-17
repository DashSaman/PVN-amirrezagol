package com.pvnetwork.engine.xray

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
import kotlin.test.assertTrue

class JvmHostXrayRuntimeTest {
    @Test
    fun hostRuntimeValidatesStartsAndCleansProtectedConfig() {
        val fixture = FakeXrayFixture.create()
        try {
            val store = MemorySecretStore()
            val identity = "11111111-1111-4111-8111-111111111111"
            val imported = VlessShareLinkImporter(store).import(
                "vless://$identity@127.0.0.1:8443?security=none&type=raw#Runtime",
                ProfileId("xray-runtime"),
            )
            val factory = JvmHostXrayRuntimeFactory(fixture.executable, socksListenPort = 19080)
            val adapter = XrayAdapter(factory)

            assertEquals(setOf(XrayAdapter.VLESS_CAPABILITY), factory.runtimeDescriptor.availableCapabilities)
            assertTrue(factory.runtimeDescriptor.upstreamVersion?.contains("26.7.28") == true)
            assertTrue(adapter.validate(imported.canonicalProfile).isValid)

            val prepared = adapter.prepare(imported.canonicalProfile, store)
            val connected = CountDownLatch(1)
            val states = CopyOnWriteArrayList<ConnectionSnapshot>()
            prepared.start { snapshot ->
                states += snapshot
                if (snapshot.state == ConnectionState.CONNECTED) connected.countDown()
            }

            assertTrue(connected.await(5, TimeUnit.SECONDS), "fake Xray runtime did not reach engine-ready CONNECTED")
            val validatedConfig = fixture.awaitPath(fixture.validationMarker)
            val runtimeConfig = fixture.awaitPath(fixture.runMarker)
            assertEquals(validatedConfig, runtimeConfig)
            assertTrue(Files.exists(runtimeConfig))
            val json = Files.readString(runtimeConfig)
            assertTrue(json.contains(identity))
            assertTrue(json.contains("\"protocol\":\"vless\""))
            assertTrue(json.contains("\"listen\":\"127.0.0.1\""))
            assertTrue(json.contains("\"port\":19080"))
            assertEquals(
                setOf(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE),
                Files.getPosixFilePermissions(runtimeConfig),
            )
            assertEquals(
                setOf(
                    PosixFilePermission.OWNER_READ,
                    PosixFilePermission.OWNER_WRITE,
                    PosixFilePermission.OWNER_EXECUTE,
                ),
                Files.getPosixFilePermissions(runtimeConfig.parent),
            )
            assertTrue(states.any { it.state == ConnectionState.PREPARING })
            assertTrue(states.any { it.state == ConnectionState.CONNECTING })
            assertTrue(states.any { it.state == ConnectionState.ESTABLISHING_TUNNEL })
            assertEquals(ConnectionState.CONNECTED, prepared.snapshot().state)

            val runtimeDirectory = runtimeConfig.parent
            prepared.stop { states += it }
            assertEquals(ConnectionState.DISCONNECTED, prepared.snapshot().state)
            assertFalse(Files.exists(runtimeConfig))
            assertFalse(Files.exists(runtimeDirectory))
            assertTrue(states.any { it.state == ConnectionState.DISCONNECTING })
            assertTrue(states.any { it.state == ConnectionState.DISCONNECTED })
            assertFalse(states.any { it.state == ConnectionState.ERROR })
        } finally {
            fixture.close()
        }
    }

    @Test
    fun failedXrayConfigTestFailsClosedBeforeLongLivedLaunch() {
        val fixture = FakeXrayFixture.create(validationExit = 23)
        try {
            val store = MemorySecretStore()
            val imported = VlessShareLinkImporter(store).import(
                "vless://22222222-2222-4222-8222-222222222222@127.0.0.1:8443?security=none&type=raw",
                ProfileId("xray-invalid-config"),
            )
            val prepared = XrayAdapter(JvmHostXrayRuntimeFactory(fixture.executable, 19081))
                .prepare(imported.canonicalProfile, store)
            val states = CopyOnWriteArrayList<ConnectionSnapshot>()

            prepared.start { states += it }

            assertEquals(ConnectionState.ERROR, prepared.snapshot().state)
            assertTrue(states.any { it.reasonCode == "XRAY_CONFIG_VALIDATION_FAILED" })
            val configPath = fixture.awaitPath(fixture.validationMarker)
            assertFalse(Files.exists(configPath), "failed validation must remove transient Xray config")
            assertFalse(Files.exists(fixture.runMarker), "long-lived Xray process must not launch after config-test failure")
        } finally {
            fixture.close()
        }
    }

    @Test
    fun missingIdentityFailsClosedWithoutInvokingXrayConfigTest() {
        val fixture = FakeXrayFixture.create()
        try {
            val store = MemorySecretStore()
            val imported = VlessShareLinkImporter(store).import(
                "vless://33333333-3333-4333-8333-333333333333@127.0.0.1:8443?security=none&type=raw",
                ProfileId("xray-missing-secret"),
            )
            val prepared = XrayAdapter(JvmHostXrayRuntimeFactory(fixture.executable, 19082))
                .prepare(imported.canonicalProfile, store)
            assertTrue(store.delete(imported.config.identityRef))
            val states = CopyOnWriteArrayList<ConnectionSnapshot>()

            prepared.start { states += it }

            assertEquals(ConnectionState.ERROR, prepared.snapshot().state)
            assertTrue(states.any { it.reasonCode == "XRAY_IDENTITY_SECRET_UNAVAILABLE" })
            assertFalse(Files.exists(fixture.validationMarker))
            assertFalse(Files.exists(fixture.runMarker))
        } finally {
            fixture.close()
        }
    }

    @Test
    fun executableProbeMustIdentifyVersionLineBeforeAdvertisingVless() {
        val directory = Files.createTempDirectory("pvnetwork-xray-bad-probe-")
        try {
            val executable = directory.resolve("xray")
            Files.writeString(executable, "#!/bin/sh\necho 'not-the-xray-core'\nexit 0\n")
            executable.toFile().setExecutable(true, true)
            val factory = JvmHostXrayRuntimeFactory(executable, 19083)
            assertTrue(factory.runtimeDescriptor.availableCapabilities.isEmpty())
        } finally {
            deleteTree(directory)
        }
    }

    @Test
    fun visionRequiresTlsOrRealitySecurity() {
        val fixture = FakeXrayFixture.create()
        try {
            val store = MemorySecretStore()
            val imported = VlessShareLinkImporter(store).import(
                "vless://44444444-4444-4444-8444-444444444444@example.invalid:443?security=reality&type=xhttp&flow=xtls-rprx-vision&pbk=public&fp=chrome&sni=example.invalid&path=%2Fx",
                ProfileId("xray-invalid-vision-security"),
            )
            val invalidProfile = imported.canonicalProfile.copy(
                extensions = imported.canonicalProfile.extensions + ("xray.security" to "none"),
            )
            val validation = XrayAdapter(JvmHostXrayRuntimeFactory(fixture.executable, 19084))
                .validate(invalidProfile)
            assertFalse(validation.isValid)
            assertTrue(validation.issues.any { it.code == "XRAY_VISION_SECURITY_INCOMPATIBLE" })
        } finally {
            fixture.close()
        }
    }

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1

        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef =
            SecretRef("secret://jvm-xray-test/${nextId++}").also { values[it.value] = secret.copyOf() }

        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val stored = values[ref.value] ?: return null
            val copy = stored.copyOf()
            return try { block(copy) } finally { copy.clearSecret() }
        }

        override fun delete(ref: SecretRef): Boolean =
            values.remove(ref.value)?.let { it.clearSecret(); true } ?: false
    }

    private class FakeXrayFixture(
        val directory: Path,
        val executable: Path,
        val validationMarker: Path,
        val runMarker: Path,
    ) : AutoCloseable {
        fun awaitPath(marker: Path): Path {
            repeat(100) {
                if (Files.exists(marker)) {
                    val value = Files.readString(marker).trim()
                    if (value.isNotBlank()) return Path.of(value)
                }
                Thread.sleep(25)
            }
            error("fake Xray runtime did not publish ${marker.fileName}")
        }

        override fun close() = deleteTree(directory)

        companion object {
            fun create(validationExit: Int = 0): FakeXrayFixture {
                val directory = Files.createTempDirectory("pvnetwork-xray-fake-")
                val executable = directory.resolve("xray")
                val validationMarker = directory.resolve("validation-config.txt")
                val runMarker = directory.resolve("run-config.txt")
                Files.writeString(
                    executable,
                    """#!/bin/sh
if [ "${'$'}{1:-}" = "version" ]; then
  echo "Xray 26.7.28 (Xray, Penetrates Everything.) PVNetwork fake"
  exit 0
fi
if [ "${'$'}{1:-}" != "run" ]; then
  exit 20
fi
shift
config=""
test_mode=0
while [ "${'$'}#" -gt 0 ]; do
  case "${'$'}1" in
    -test) test_mode=1; shift ;;
    -c) config="${'$'}2"; shift 2 ;;
    *) shift ;;
  esac
done
if [ -z "${'$'}config" ] || [ ! -f "${'$'}config" ]; then
  exit 21
fi
if [ "${'$'}test_mode" -eq 1 ]; then
  printf '%s' "${'$'}config" > ${shellSingleQuote(validationMarker.toString())}
  exit $validationExit
fi
printf '%s' "${'$'}config" > ${shellSingleQuote(runMarker.toString())}
echo "2026/08/17 21:00:00 [Info] core: Xray 26.7.28 started"
trap 'exit 0' TERM INT
while :; do sleep 1; done
""",
                )
                executable.toFile().setExecutable(true, true)
                return FakeXrayFixture(directory, executable, validationMarker, runMarker)
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
