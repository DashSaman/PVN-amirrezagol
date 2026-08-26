package com.pvnetwork.engine.mihomo

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
import java.net.DatagramSocket
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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Exact-checksum cross-core M3 interoperability evidence.
 *
 * Client: PVNetwork -> host-supplied Mihomo.
 * Server: independently pinned sing-box CI fixture.
 * Target: local TCP echo origin.
 *
 * The external binaries are supplied only by CI and are never bundled or
 * downloaded by the product. TLS certificate verification remains enabled.
 */
class JvmHostMihomoRealBinaryInteropTest {
    private val loopback = InetAddress.getByName("127.0.0.1")

    @Test
    fun realHysteria2TlsDataPath() = runProtocolCase(
        caseName = "hysteria2-tls",
        protocol = MihomoAdapter.HYSTERIA2_CAPABILITY,
        credential = "pvnetwork-m3-hysteria2-password",
        secretRole = MihomoAdapter.HYSTERIA2_PASSWORD_SECRET_ROLE,
        datagramServer = true,
        extraExtensions = mapOf("mihomo.sni" to "localhost"),
        serverInbound = { port, cert, key, credential ->
            "{\"type\":\"hysteria2\",\"tag\":\"server\",\"listen\":\"127.0.0.1\",\"listen_port\":$port," +
                "\"users\":[{\"name\":\"pvnetwork-ci\",\"password\":${json(credential)}}]," +
                "\"ignore_client_bandwidth\":true,\"tls\":${tls(cert, key)}}"
        },
    )

    @Test
    fun realTuicV5TlsDataPath() {
        val uuid = "550e8400-e29b-41d4-a716-446655440000"
        runProtocolCase(
            caseName = "tuic-v5-tls",
            protocol = MihomoAdapter.TUIC_CAPABILITY,
            credential = "pvnetwork-m3-tuic-password",
            secretRole = MihomoAdapter.TUIC_PASSWORD_SECRET_ROLE,
            datagramServer = true,
            extraExtensions = mapOf(
                "mihomo.sni" to "localhost",
                "mihomo.tuic.uuid" to uuid,
            ),
            serverInbound = { port, cert, key, credential ->
                "{\"type\":\"tuic\",\"tag\":\"server\",\"listen\":\"127.0.0.1\",\"listen_port\":$port," +
                    "\"users\":[{\"name\":\"pvnetwork-ci\",\"uuid\":${json(uuid)},\"password\":${json(credential)}}]," +
                    "\"tls\":${tls(cert, key)}}"
            },
        )
    }

    @Test
    fun realAnyTlsDataPath() = runProtocolCase(
        caseName = "anytls",
        protocol = MihomoAdapter.ANYTLS_CAPABILITY,
        credential = "pvnetwork-m3-anytls-password",
        secretRole = MihomoAdapter.ANYTLS_PASSWORD_SECRET_ROLE,
        datagramServer = false,
        extraExtensions = mapOf("mihomo.sni" to "localhost"),
        serverInbound = { port, cert, key, credential ->
            "{\"type\":\"anytls\",\"tag\":\"server\",\"listen\":\"127.0.0.1\",\"listen_port\":$port," +
                "\"users\":[{\"name\":\"pvnetwork-ci\",\"password\":${json(credential)}}]," +
                "\"tls\":${tls(cert, key)}}"
        },
    )

    private fun runProtocolCase(
        caseName: String,
        protocol: String,
        credential: String,
        secretRole: String,
        datagramServer: Boolean,
        extraExtensions: Map<String, String>,
        serverInbound: (Int, String, String, String) -> String,
    ) {
        val mihomoValue = System.getenv("PVNETWORK_MIHOMO_TEST_EXECUTABLE")?.takeIf(String::isNotBlank) ?: return
        val singBoxValue = System.getenv("PVNETWORK_SING_BOX_TEST_EXECUTABLE")?.takeIf(String::isNotBlank) ?: return
        val cert = System.getenv("PVNETWORK_MIHOMO_TEST_TLS_CERT")?.takeIf(String::isNotBlank) ?: return
        val key = System.getenv("PVNETWORK_MIHOMO_TEST_TLS_KEY")?.takeIf(String::isNotBlank) ?: return
        val mihomo = Path.of(mihomoValue).toAbsolutePath().normalize()
        val singBox = Path.of(singBoxValue).toAbsolutePath().normalize()
        assertTrue(Files.isRegularFile(mihomo) && Files.isExecutable(mihomo), "missing exact Mihomo CI fixture")
        assertTrue(Files.isRegularFile(singBox) && Files.isExecutable(singBox), "missing exact sing-box CI fixture")
        assertTrue(Files.isRegularFile(Path.of(cert)) && Files.isRegularFile(Path.of(key)), "missing CI TLS material")

        val origin = LocalEchoOrigin(loopback, "$MARKER_PREFIX-$caseName")
        val serverPort = if (datagramServer) reserveUdpPort() else reserveTcpPort()
        val socksPort = reserveTcpPort()
        val serverDirectory = Files.createTempDirectory("pvnetwork-mihomo-$caseName-server-")
        val serverConfig = serverDirectory.resolve("server.json")
        var serverProcess: Process? = null
        var prepared: PreparedConnection? = null

        try {
            origin.start()
            val inbound = serverInbound(serverPort, cert, key, credential)
            Files.writeString(
                serverConfig,
                "{\"log\":{\"level\":\"info\"},\"inbounds\":[$inbound],\"outbounds\":[{\"type\":\"direct\",\"tag\":\"direct\"}]}",
                StandardCharsets.UTF_8,
            )

            val configTest = ProcessBuilder(singBox.toString(), "check", "-c", serverConfig.toString())
                .redirectErrorStream(true).start()
            drain(configTest, "pvnetwork-sing-box-$caseName-config-test")
            assertTrue(configTest.waitFor(10, TimeUnit.SECONDS), "sing-box $caseName config validation timed out")
            assertEquals(0, configTest.exitValue(), "sing-box rejected the $caseName server config")

            serverProcess = ProcessBuilder(singBox.toString(), "run", "-c", serverConfig.toString())
                .redirectErrorStream(true).start()
            drain(serverProcess, "pvnetwork-sing-box-$caseName-server-output")
            assertFalse(serverProcess.waitFor(1_500, TimeUnit.MILLISECONDS), "sing-box $caseName server exited during startup")

            val secretStore = MemorySecretStore()
            val credentialRef = secretStore.putText(credential, SecretPurpose.TOKEN)
            val profile = PVProfile(
                id = ProfileId("mihomo-real-$caseName"),
                displayName = "Real $caseName CI",
                protocolId = protocol,
                endpoint = Endpoint("127.0.0.1", serverPort),
                secretRefs = mapOf(secretRole to credentialRef),
                extensions = mapOf("mihomo.application-protocol" to protocol) + extraExtensions,
                origin = ProfileOrigin.MANUAL,
            )
            val adapter = MihomoAdapter(JvmHostMihomoRuntimeFactory(mihomo, socksPort))
            val validation = adapter.validate(profile)
            assertTrue(validation.isValid, validation.issues.joinToString { it.code })

            prepared = adapter.prepare(profile, secretStore)
            val connected = CountDownLatch(1)
            prepared.start { if (it.state == ConnectionState.CONNECTED) connected.countDown() }
            assertTrue(connected.await(10, TimeUnit.SECONDS), "PVNetwork $caseName Mihomo runtime did not report local readiness")
            waitForTcpListener(socksPort)

            val response = roundTripThroughSocks5(socksPort, origin.port, origin.marker, origin)
            assertEquals("echo:${origin.marker}", response)
            assertTrue(origin.awaitPayload(), "origin did not receive the proxied $caseName payload")
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
            socket.soTimeout = 15_000
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

    private fun tls(cert: String, key: String): String =
        "{\"enabled\":true,\"certificate_path\":${json(cert)},\"key_path\":${json(key)}}"

    private fun json(value: String): String = buildString {
        append('"')
        value.forEach { c ->
            when (c) {
                '\\' -> append("\\\\")
                '"' -> append("\\\"")
                '\n' -> append("\\n")
                '\r' -> append("\\r")
                '\t' -> append("\\t")
                else -> append(c)
            }
        }
        append('"')
    }

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

    private fun waitForTcpListener(port: Int) {
        repeat(100) {
            try {
                Socket().use { it.connect(InetSocketAddress(loopback, port), 100) }
                return
            } catch (_: Exception) {
                Thread.sleep(50)
            }
        }
        error("127.0.0.1:$port did not become ready")
    }

    private fun reserveTcpPort(): Int = ServerSocket(0, 50, loopback).use { it.localPort }

    private fun reserveUdpPort(): Int = DatagramSocket(0, loopback).use { it.localPort }

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
            process.destroyForcibly()
            process.waitFor(3, TimeUnit.SECONDS)
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
            SecretRef("secret://mihomo-real-ci/${nextId++}").also { values[it.value] = secret.copyOf() }

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
            worker = thread(name = "pvnetwork-mihomo-real-echo", isDaemon = true) {
                runCatching {
                    server.accept().use { socket ->
                        socket.soTimeout = 15_000
                        connectionObserved.countDown()
                        val input = BufferedInputStream(socket.getInputStream())
                        val expected = marker.toByteArray(StandardCharsets.US_ASCII)
                        val received = ByteArray(expected.size)
                        var offset = 0
                        while (offset < received.size) {
                            val count = input.read(received, offset, received.size - offset)
                            check(count > 0) { "unexpected EOF at echo origin" }
                            offset += count
                        }
                        check(received.contentEquals(expected)) { "unexpected echo-origin payload" }
                        payloadObserved.countDown()
                        socket.getOutputStream().apply {
                            write("echo:$marker".toByteArray(StandardCharsets.US_ASCII))
                            flush()
                        }
                    }
                }
            }
        }

        fun awaitConnection(): Boolean = connectionObserved.await(10, TimeUnit.SECONDS)
        fun awaitPayload(): Boolean = payloadObserved.await(10, TimeUnit.SECONDS)

        override fun close() {
            runCatching { server.close() }
            worker?.join(1_000)
        }
    }

    companion object {
        private const val MARKER_PREFIX = "pvnetwork-m3"
    }
}
