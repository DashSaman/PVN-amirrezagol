package com.pvnetwork.engine.xray

import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.ProfileOrigin
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import java.io.BufferedInputStream
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.Comparator
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** Exact-checksum external-Xray CI evidence. No fixture is bundled or promoted. */
class JvmHostXrayRealBinaryInteropTest {
    private val loopback = InetAddress.getByName("127.0.0.1")

    @Test
    fun realVlessRawDataPath() = runProtocolCase(
        protocol = XrayAdapter.VLESS_CAPABILITY,
        credential = "a1b2c3d4-1111-4111-8111-1234567890ab",
        secretRole = XrayAdapter.VLESS_IDENTITY_SECRET_ROLE,
        extraExtensions = emptyMap(),
        serverSettings = { credential -> "{\"clients\":[{\"id\":\"$credential\"}],\"decryption\":\"none\"}" },
    )

    @Test
    fun realVmessRawDataPath() = runProtocolCase(
        protocol = XrayAdapter.VMESS_CAPABILITY,
        credential = "b1b2c3d4-2222-4222-8222-1234567890ab",
        secretRole = XrayAdapter.VMESS_IDENTITY_SECRET_ROLE,
        extraExtensions = mapOf("xray.vmess-security" to "auto"),
        serverSettings = { credential -> "{\"clients\":[{\"id\":\"$credential\",\"security\":\"auto\"}]}" },
    )

    @Test
    fun realShadowsocksRawDataPath() = runProtocolCase(
        protocol = XrayAdapter.SHADOWSOCKS_CAPABILITY,
        credential = "pvnetwork-m3-aead-password",
        secretRole = XrayAdapter.SHADOWSOCKS_PASSWORD_SECRET_ROLE,
        extraExtensions = mapOf("xray.shadowsocks-method" to "aes-128-gcm"),
        serverSettings = { credential -> "{\"method\":\"aes-128-gcm\",\"password\":\"$credential\",\"network\":\"tcp\"}" },
    )

    private fun runProtocolCase(
        protocol: String,
        credential: String,
        secretRole: String,
        extraExtensions: Map<String, String>,
        serverSettings: (String) -> String,
    ) {
        val executableValue = System.getenv("PVNETWORK_XRAY_TEST_EXECUTABLE")?.takeIf(String::isNotBlank) ?: return
        val executable = Path.of(executableValue).toAbsolutePath().normalize()
        assertTrue(Files.isRegularFile(executable) && Files.isExecutable(executable))

        val origin = LocalEchoOrigin(loopback, "$MARKER_PREFIX-$protocol")
        val serverPort = reservePort()
        val socksPort = reservePort()
        val serverDirectory = Files.createTempDirectory("pvnetwork-xray-$protocol-server-")
        val serverConfig = serverDirectory.resolve("server.json")
        var serverProcess: Process? = null
        var prepared: PreparedConnection? = null

        try {
            origin.start()
            Files.writeString(
                serverConfig,
                serverConfigJson(protocol, serverSettings(credential), serverPort),
                StandardCharsets.UTF_8,
            )
            val validation = ProcessBuilder(executable.toString(), "run", "-test", "-c", serverConfig.toString())
                .redirectErrorStream(true).start()
            drain(validation, "pvnetwork-xray-$protocol-server-config-test")
            assertTrue(validation.waitFor(10, TimeUnit.SECONDS), "real Xray $protocol server config validation timed out")
            assertEquals(0, validation.exitValue(), "real Xray rejected the $protocol server config")

            serverProcess = ProcessBuilder(executable.toString(), "run", "-c", serverConfig.toString())
                .redirectErrorStream(true).start()
            drain(serverProcess, "pvnetwork-xray-$protocol-server-output")
            waitForListener(serverPort)

            val secretStore = MemorySecretStore()
            val credentialRef = secretStore.putText(credential, SecretPurpose.TOKEN)
            val profile = PVProfile(
                id = ProfileId("xray-real-$protocol"),
                displayName = "Real $protocol CI",
                protocolId = protocol,
                endpoint = Endpoint("127.0.0.1", serverPort),
                secretRefs = mapOf(secretRole to credentialRef),
                extensions = mapOf(
                    "xray.application-protocol" to protocol,
                    "xray.security" to "none",
                    "xray.transport" to "raw",
                ) + extraExtensions,
                origin = ProfileOrigin.MANUAL,
            )
            val adapter = XrayAdapter(JvmHostXrayRuntimeFactory(executable, socksPort))
            assertTrue(adapter.validate(profile).isValid, adapter.validate(profile).issues.joinToString { it.code })

            prepared = adapter.prepare(profile, secretStore)
            val connected = CountDownLatch(1)
            prepared.start { if (it.state == ConnectionState.CONNECTED) connected.countDown() }
            assertTrue(connected.await(10, TimeUnit.SECONDS), "PVNetwork $protocol runtime did not report readiness")
            waitForListener(socksPort)

            val response = roundTripThroughSocks5(socksPort, origin.port, origin.marker, origin)
            assertEquals("echo:${origin.marker}", response)
            assertTrue(origin.awaitPayload())
            assertEquals(ConnectionState.CONNECTED, prepared.snapshot().state)
        } finally {
            runCatching { prepared?.stop { } }
            terminate(serverProcess)
            origin.close()
            deleteTree(serverDirectory)
        }
    }

    private fun roundTripThroughSocks5(socksPort: Int, originPort: Int, marker: String, origin: LocalEchoOrigin): String {
        Socket().use { socket ->
            socket.soTimeout = 10_000
            socket.connect(InetSocketAddress(loopback, socksPort), 5_000)
            val input = BufferedInputStream(socket.getInputStream())
            val output = socket.getOutputStream()
            output.write(byteArrayOf(0x05, 0x01, 0x00)); output.flush()
            assertEquals(0x05, input.read()); assertEquals(0x00, input.read())
            output.write(byteArrayOf(
                0x05, 0x01, 0x00, 0x01, 127, 0, 0, 1,
                ((originPort ushr 8) and 0xff).toByte(), (originPort and 0xff).toByte(),
            )); output.flush()
            assertEquals(0x05, input.read()); assertEquals(0x00, input.read(), "SOCKS/$marker connect failed")
            input.read()
            when (val atyp = input.read()) {
                0x01 -> readExactly(input, 4)
                0x03 -> readExactly(input, input.read())
                0x04 -> readExactly(input, 16)
                else -> error("unexpected SOCKS address type: $atyp")
            }
            readExactly(input, 2)
            output.write(marker.toByteArray(StandardCharsets.US_ASCII)); output.flush()
            assertTrue(origin.awaitConnection(), "target connection was not established for $marker")
            assertTrue(origin.awaitPayload(), "target did not receive $marker")
            val expected = "echo:$marker".toByteArray(StandardCharsets.US_ASCII)
            val response = ByteArray(expected.size)
            readExactly(input, response)
            return response.toString(StandardCharsets.US_ASCII)
        }
    }

    private fun serverConfigJson(protocol: String, settings: String, port: Int): String =
        "{\"log\":{\"loglevel\":\"info\"},\"inbounds\":[{\"listen\":\"127.0.0.1\",\"port\":$port,\"protocol\":\"$protocol\",\"settings\":$settings,\"streamSettings\":{\"network\":\"raw\",\"security\":\"none\"}}],\"outbounds\":[{\"protocol\":\"freedom\",\"tag\":\"direct\",\"settings\":{\"finalRules\":[{\"action\":\"allow\"}]}}]}"

    private fun readExactly(input: BufferedInputStream, count: Int) {
        var remaining = count
        val buffer = ByteArray(32)
        while (remaining > 0) {
            val read = input.read(buffer, 0, minOf(buffer.size, remaining))
            check(read > 0) { "unexpected EOF" }
            remaining -= read
        }
    }

    private fun readExactly(input: BufferedInputStream, destination: ByteArray) {
        var offset = 0
        while (offset < destination.size) {
            val read = input.read(destination, offset, destination.size - offset)
            check(read > 0) { "unexpected EOF before proxied response" }
            offset += read
        }
    }

    private fun waitForListener(port: Int) {
        repeat(100) {
            try {
                Socket().use { it.connect(InetSocketAddress(loopback, port), 100) }
                return
            } catch (_: Exception) { Thread.sleep(50) }
        }
        error("127.0.0.1:$port did not become ready")
    }

    private fun reservePort(): Int = ServerSocket(0, 50, loopback).use { it.localPort }

    private fun drain(process: Process, name: String) {
        thread(name = name, isDaemon = true) {
            runCatching { process.inputStream.bufferedReader().useLines { lines -> lines.forEach { _ -> } } }
        }
    }

    private fun terminate(process: Process?) {
        process ?: return
        if (!process.isAlive) return
        process.destroy()
        if (!process.waitFor(3, TimeUnit.SECONDS)) {
            process.destroyForcibly(); process.waitFor(3, TimeUnit.SECONDS)
        }
    }

    private fun deleteTree(path: Path) {
        if (!Files.exists(path)) return
        Files.walk(path).use { it.sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists) }
    }

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1

        fun putText(value: String, purpose: SecretPurpose): SecretRef {
            val chars = value.toCharArray()
            return try { put(purpose, chars) } finally { chars.clearSecret() }
        }

        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef =
            SecretRef("secret://xray-real-ci/${nextId++}").also { values[it.value] = secret.copyOf() }

        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val copy = values[ref.value]?.copyOf() ?: return null
            return try { block(copy) } finally { copy.clearSecret() }
        }

        override fun delete(ref: SecretRef): Boolean = values.remove(ref.value)?.let { it.clearSecret(); true } ?: false
    }

    private class LocalEchoOrigin(private val loopback: InetAddress, val marker: String) : AutoCloseable {
        private val server = ServerSocket(0, 50, loopback)
        private val connectionObserved = CountDownLatch(1)
        private val payloadObserved = CountDownLatch(1)
        private var worker: Thread? = null
        val port: Int get() = server.localPort

        fun start() {
            worker = thread(name = "pvnetwork-xray-real-echo-$marker", isDaemon = true) {
                runCatching {
                    server.accept().use { socket ->
                        socket.soTimeout = 10_000
                        connectionObserved.countDown()
                        val expected = marker.toByteArray(StandardCharsets.US_ASCII)
                        val received = ByteArray(expected.size)
                        var offset = 0
                        while (offset < received.size) {
                            val read = socket.getInputStream().read(received, offset, received.size - offset)
                            check(read > 0)
                            offset += read
                        }
                        check(received.contentEquals(expected))
                        payloadObserved.countDown()
                        socket.getOutputStream().write("echo:$marker".toByteArray(StandardCharsets.US_ASCII))
                        socket.getOutputStream().flush()
                    }
                }
            }
        }

        fun awaitConnection() = connectionObserved.await(5, TimeUnit.SECONDS)
        fun awaitPayload() = payloadObserved.await(3, TimeUnit.SECONDS)
        override fun close() { runCatching { server.close() }; worker?.join(500) }
    }

    companion object { private const val MARKER_PREFIX = "pvnetwork-xray-real-path-ok" }
}
