package com.pvnetwork.engine.xray

import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
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

/**
 * CI-only interoperability evidence against an exact-checksum external Xray binary.
 *
 * The workflow provides PVNETWORK_XRAY_TEST_EXECUTABLE. This test never downloads,
 * packages, publishes, or promotes that executable. It proves the product-owned
 * host-runtime path by carrying an HTTP response through:
 *
 *   test client -> PVNetwork SOCKS inbound -> VLESS outbound -> real Xray VLESS
 *   server -> freedom outbound -> local HTTP origin.
 */
class JvmHostXrayRealBinaryInteropTest {
    @Test
    fun realXrayCarriesHttpPayloadThroughProductVlessRuntime() {
        val executableValue = System.getenv("PVNETWORK_XRAY_TEST_EXECUTABLE")?.takeIf(String::isNotBlank)
            ?: return
        val executable = Path.of(executableValue).toAbsolutePath().normalize()
        assertTrue(Files.isRegularFile(executable) && Files.isExecutable(executable))

        val identity = "a1b2c3d4-1111-4111-8111-1234567890ab"
        val origin = LocalHttpOrigin()
        val serverPort = reserveLoopbackPort()
        val socksPort = reserveLoopbackPort()
        val serverDirectory = Files.createTempDirectory("pvnetwork-xray-real-server-")
        val serverConfig = serverDirectory.resolve("server.json")
        var serverProcess: Process? = null
        var prepared: com.pvnetwork.core.adapter.PreparedConnection? = null

        try {
            origin.start()
            Files.writeString(serverConfig, serverConfigJson(identity, serverPort), StandardCharsets.UTF_8)

            val validation = ProcessBuilder(
                executable.toString(), "run", "-test", "-c", serverConfig.toString(),
            ).redirectErrorStream(true).start()
            drain(validation, "pvnetwork-xray-real-server-config-test")
            assertTrue(validation.waitFor(10, TimeUnit.SECONDS), "real Xray server config validation timed out")
            assertEquals(0, validation.exitValue(), "real Xray rejected the CI VLESS server config")

            serverProcess = ProcessBuilder(
                executable.toString(), "run", "-c", serverConfig.toString(),
            ).redirectErrorStream(true).start()
            drain(serverProcess, "pvnetwork-xray-real-server-output")
            waitForTcpListener(serverPort)

            val secretStore = MemorySecretStore()
            val imported = VlessShareLinkImporter(secretStore).import(
                "vless://$identity@127.0.0.1:$serverPort?security=none&type=raw#RealInterop",
                ProfileId("xray-real-ci"),
            )
            val adapter = XrayAdapter(JvmHostXrayRuntimeFactory(executable, socksPort))
            assertTrue(adapter.validate(imported.canonicalProfile).isValid)

            prepared = adapter.prepare(imported.canonicalProfile, secretStore)
            val connected = CountDownLatch(1)
            prepared.start { if (it.state == ConnectionState.CONNECTED) connected.countDown() }
            assertTrue(connected.await(10, TimeUnit.SECONDS), "PVNetwork Xray runtime did not report engine readiness")
            waitForTcpListener(socksPort)

            val response = fetchThroughSocks5(socksPort, origin.port)
            assertTrue(response.startsWith("HTTP/1.1 200"), "HTTP origin response did not return through VLESS")
            assertTrue(response.contains(LocalHttpOrigin.MARKER), "known payload marker did not traverse the real VLESS path")
            assertTrue(origin.awaitRequest(), "local HTTP origin did not receive the proxied request")
            assertEquals(ConnectionState.CONNECTED, prepared.snapshot().state)
        } finally {
            runCatching { prepared?.stop { } }
            terminate(serverProcess)
            origin.close()
            deleteTree(serverDirectory)
        }
    }

    private fun fetchThroughSocks5(socksPort: Int, originPort: Int): String {
        Socket().use { socket ->
            socket.soTimeout = 10_000
            socket.connect(InetSocketAddress(InetAddress.getLoopbackAddress(), socksPort), 5_000)
            val input = BufferedInputStream(socket.getInputStream())
            val output = socket.getOutputStream()

            output.write(byteArrayOf(0x05, 0x01, 0x00))
            output.flush()
            assertEquals(0x05, input.read(), "unexpected SOCKS version")
            assertEquals(0x00, input.read(), "SOCKS server did not accept no-auth method")

            val portHigh = (originPort ushr 8) and 0xff
            val portLow = originPort and 0xff
            output.write(byteArrayOf(0x05, 0x01, 0x00, 0x01, 127, 0, 0, 1, portHigh.toByte(), portLow.toByte()))
            output.flush()

            assertEquals(0x05, input.read(), "unexpected SOCKS connect reply version")
            assertEquals(0x00, input.read(), "SOCKS/VLESS connect request failed")
            input.read() // RSV
            when (val atyp = input.read()) {
                0x01 -> readExactly(input, 4)
                0x03 -> readExactly(input, input.read())
                0x04 -> readExactly(input, 16)
                else -> error("unexpected SOCKS connect reply address type: $atyp")
            }
            readExactly(input, 2)

            output.write(
                "GET /pvnetwork-xray-real HTTP/1.1\r\nHost: 127.0.0.1:$originPort\r\nConnection: close\r\n\r\n"
                    .toByteArray(StandardCharsets.US_ASCII),
            )
            output.flush()
            return readUntilMarker(input, LocalHttpOrigin.MARKER)
        }
    }

    private fun readUntilMarker(input: BufferedInputStream, marker: String): String {
        val buffer = ByteArray(1024)
        val collected = ByteArrayOutputStream()
        repeat(64) {
            val read = input.read(buffer)
            check(read > 0) { "unexpected EOF before HTTP payload marker" }
            collected.write(buffer, 0, read)
            val value = collected.toString(StandardCharsets.UTF_8)
            if (value.contains(marker)) return value
            check(collected.size() <= 64 * 1024) { "HTTP response exceeded bounded interoperability buffer" }
        }
        error("HTTP payload marker was not observed through the real VLESS data path")
    }

    private fun readExactly(input: BufferedInputStream, count: Int) {
        require(count >= 0)
        var remaining = count
        val buffer = ByteArray(32)
        while (remaining > 0) {
            val read = input.read(buffer, 0, minOf(buffer.size, remaining))
            check(read > 0) { "unexpected EOF from SOCKS server" }
            remaining -= read
        }
    }

    private fun waitForTcpListener(port: Int) {
        repeat(100) {
            try {
                Socket().use { it.connect(InetSocketAddress(InetAddress.getLoopbackAddress(), port), 100) }
                return
            } catch (_: Exception) {
                Thread.sleep(50)
            }
        }
        error("TCP listener 127.0.0.1:$port did not become ready")
    }

    private fun reserveLoopbackPort(): Int = ServerSocket(0, 50, InetAddress.getLoopbackAddress()).use { it.localPort }

    private fun drain(process: Process, threadName: String) {
        thread(name = threadName, isDaemon = true) {
            runCatching {
                process.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                    lines.forEach { _ -> /* drain without storing potentially sensitive diagnostics */ }
                }
            }
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

    private fun serverConfigJson(identity: String, port: Int): String =
        """{"log":{"loglevel":"info"},"inbounds":[{"listen":"127.0.0.1","port":$port,"protocol":"vless","settings":{"clients":[{"id":"$identity"}],"decryption":"none"},"streamSettings":{"network":"raw","security":"none"}}],"outbounds":[{"protocol":"freedom","tag":"direct"}]}"""

    private fun deleteTree(path: Path) {
        if (!Files.exists(path)) return
        Files.walk(path).use { stream ->
            stream.sorted(Comparator.reverseOrder()).forEach { Files.deleteIfExists(it) }
        }
    }

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1

        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef =
            SecretRef("secret://xray-real-ci/${nextId++}").also { values[it.value] = secret.copyOf() }

        override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
            val stored = values[ref.value] ?: return null
            val copy = stored.copyOf()
            return try { block(copy) } finally { copy.clearSecret() }
        }

        override fun delete(ref: SecretRef): Boolean =
            values.remove(ref.value)?.let { it.clearSecret(); true } ?: false
    }

    private class LocalHttpOrigin : AutoCloseable {
        private val server = ServerSocket(0, 50, InetAddress.getLoopbackAddress())
        private val requestObserved = CountDownLatch(1)
        private var worker: Thread? = null
        val port: Int get() = server.localPort

        fun start() {
            worker = thread(name = "pvnetwork-xray-real-http-origin", isDaemon = true) {
                runCatching {
                    server.accept().use { socket ->
                        socket.soTimeout = 10_000
                        val reader = socket.getInputStream().bufferedReader(StandardCharsets.US_ASCII)
                        while (true) {
                            val line = reader.readLine() ?: break
                            if (line.isEmpty()) break
                        }
                        requestObserved.countDown()
                        val body = MARKER
                        val response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: ${body.toByteArray().size}\r\nConnection: close\r\n\r\n$body"
                        socket.getOutputStream().write(response.toByteArray(StandardCharsets.US_ASCII))
                        socket.getOutputStream().flush()
                    }
                }
            }
        }

        fun awaitRequest(): Boolean = requestObserved.await(3, TimeUnit.SECONDS)

        override fun close() {
            runCatching { server.close() }
            worker?.join(500)
        }

        companion object {
            const val MARKER = "pvnetwork-xray-real-path-ok"
        }
    }
}
