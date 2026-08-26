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

/**
 * Real VLESS + Vision + REALITY interoperability evidence using the exact
 * external Xray CI fixture. REALITY key material is generated ephemerally by
 * that same executable and is never persisted outside the test runtime.
 */
class RealityJvmHostXrayRealBinaryInteropTest {
    private val loopback = InetAddress.getByName("127.0.0.1")

    @Test
    fun realVlessVisionRealityDataPath() {
        val executableValue = System.getenv("PVNETWORK_XRAY_TEST_EXECUTABLE")?.takeIf(String::isNotBlank) ?: return
        val cert = System.getenv("PVNETWORK_XRAY_TEST_TLS_CERT")?.takeIf(String::isNotBlank) ?: return
        val key = System.getenv("PVNETWORK_XRAY_TEST_TLS_KEY")?.takeIf(String::isNotBlank) ?: return
        val executable = Path.of(executableValue).toAbsolutePath().normalize()
        assertTrue(Files.isRegularFile(executable) && Files.isExecutable(executable), "missing exact Xray CI fixture")
        assertTrue(Files.isRegularFile(Path.of(cert)) && Files.isRegularFile(Path.of(key)), "missing CI TLS decoy material")

        val realityKeys = generateRealityKeys(executable)
        val origin = LocalEchoOrigin(loopback, MARKER)
        val decoyPort = reservePort()
        val serverPort = reservePort()
        val socksPort = reservePort()
        val serverDirectory = Files.createTempDirectory("pvnetwork-xray-reality-server-")
        val serverConfig = serverDirectory.resolve("server.json")
        var decoyProcess: Process? = null
        var serverProcess: Process? = null
        var prepared: PreparedConnection? = null

        try {
            origin.start()
            decoyProcess = ProcessBuilder(
                "openssl", "s_server",
                "-accept", "127.0.0.1:$decoyPort",
                "-cert", cert,
                "-key", key,
                "-www",
                "-quiet",
            ).redirectErrorStream(true).start()
            drain(decoyProcess, "pvnetwork-reality-decoy-output")
            waitForListener(decoyPort)

            Files.writeString(
                serverConfig,
                serverConfigJson(serverPort, decoyPort, realityKeys.privateKey),
                StandardCharsets.UTF_8,
            )
            val serverValidation = ProcessBuilder(executable.toString(), "run", "-test", "-c", serverConfig.toString())
                .redirectErrorStream(true).start()
            drain(serverValidation, "pvnetwork-reality-server-config-test")
            assertTrue(serverValidation.waitFor(10, TimeUnit.SECONDS), "REALITY server config validation timed out")
            assertEquals(0, serverValidation.exitValue(), "exact Xray rejected the REALITY server config")

            serverProcess = ProcessBuilder(executable.toString(), "run", "-c", serverConfig.toString())
                .redirectErrorStream(true).start()
            drain(serverProcess, "pvnetwork-reality-server-output")
            waitForListener(serverPort)

            val secretStore = MemorySecretStore()
            val identityRef = secretStore.putText(VLESS_IDENTITY, SecretPurpose.TOKEN)
            val profile = PVProfile(
                id = ProfileId("xray-real-vless-vision-reality"),
                displayName = "Real VLESS Vision REALITY CI",
                protocolId = XrayAdapter.VLESS_CAPABILITY,
                endpoint = Endpoint("127.0.0.1", serverPort),
                secretRefs = mapOf(XrayAdapter.VLESS_IDENTITY_SECRET_ROLE to identityRef),
                extensions = mapOf(
                    "xray.application-protocol" to XrayAdapter.VLESS_CAPABILITY,
                    "xray.security" to "reality",
                    "xray.transport" to "raw",
                    "xray.flow" to "xtls-rprx-vision",
                    "xray.server-name" to "localhost",
                    "xray.fingerprint" to "chrome",
                    "xray.reality-public-key" to realityKeys.publicKey,
                    "xray.reality-short-id" to SHORT_ID,
                ),
                origin = ProfileOrigin.MANUAL,
            )
            val adapter = XrayAdapter(JvmHostXrayRuntimeFactory(executable, socksPort))
            val validation = adapter.validate(profile)
            assertTrue(validation.isValid, validation.issues.joinToString { it.code })

            prepared = adapter.prepare(profile, secretStore)
            val connected = CountDownLatch(1)
            prepared.start { if (it.state == ConnectionState.CONNECTED) connected.countDown() }
            assertTrue(connected.await(10, TimeUnit.SECONDS), "PVNetwork REALITY runtime did not report readiness")
            waitForListener(socksPort)

            val response = roundTripThroughSocks5(socksPort, origin.port, origin)
            assertEquals("echo:$MARKER", response)
            assertTrue(origin.awaitPayload(), "origin did not receive the REALITY proxied payload")
            assertEquals(ConnectionState.CONNECTED, prepared.snapshot().state)
        } finally {
            runCatching { prepared?.stop { } }
            terminate(serverProcess)
            terminate(decoyProcess)
            origin.close()
            deleteTree(serverDirectory)
        }
    }

    private fun generateRealityKeys(executable: Path): RealityKeys {
        val process = ProcessBuilder(executable.toString(), "x25519").redirectErrorStream(true).start()
        val lines = process.inputStream.bufferedReader(StandardCharsets.UTF_8).readLines()
        assertTrue(process.waitFor(5, TimeUnit.SECONDS), "Xray x25519 key generation timed out")
        assertEquals(0, process.exitValue(), "Xray x25519 key generation failed")
        val privateKey = lines.firstOrNull { it.startsWith("PrivateKey:") }
            ?.substringAfter(':')?.trim()?.takeIf(String::isNotBlank)
            ?: error("Xray x25519 output omitted PrivateKey")
        val publicKey = lines.firstOrNull { it.startsWith("Password (PublicKey):") }
            ?.substringAfter(':')?.trim()?.takeIf(String::isNotBlank)
            ?: error("Xray x25519 output omitted Password (PublicKey)")
        return RealityKeys(privateKey, publicKey)
    }

    private fun serverConfigJson(port: Int, decoyPort: Int, privateKey: String): String =
        "{\"log\":{\"loglevel\":\"info\"}," +
            "\"inbounds\":[{\"listen\":\"127.0.0.1\",\"port\":$port,\"protocol\":\"vless\"," +
            "\"settings\":{\"clients\":[{\"id\":${json(VLESS_IDENTITY)},\"flow\":\"xtls-rprx-vision\"}],\"decryption\":\"none\"}," +
            "\"streamSettings\":{\"network\":\"raw\",\"security\":\"reality\"," +
            "\"realitySettings\":{\"show\":false,\"target\":${json("127.0.0.1:$decoyPort")}," +
            "\"serverNames\":[\"localhost\"],\"privateKey\":${json(privateKey)},\"shortIds\":[${json(SHORT_ID)}]}}}]," +
            "\"outbounds\":[{\"protocol\":\"freedom\",\"tag\":\"direct\",\"settings\":{\"finalRules\":[{\"action\":\"allow\"}]}}]}"

    private fun roundTripThroughSocks5(socksPort: Int, originPort: Int, origin: LocalEchoOrigin): String {
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
            assertEquals(0x05, input.read()); assertEquals(0x00, input.read(), "SOCKS/REALITY connect failed")
            input.read()
            when (val atyp = input.read()) {
                0x01 -> readExactly(input, 4)
                0x03 -> readExactly(input, input.read())
                0x04 -> readExactly(input, 16)
                else -> error("unexpected SOCKS address type: $atyp")
            }
            readExactly(input, 2)
            output.write(MARKER.toByteArray(StandardCharsets.US_ASCII)); output.flush()
            assertTrue(origin.awaitConnection(), "REALITY target connection was not established")
            assertTrue(origin.awaitPayload(), "REALITY target did not receive marker")
            val expected = "echo:$MARKER".toByteArray(StandardCharsets.US_ASCII)
            val response = ByteArray(expected.size)
            readExactly(input, response)
            return response.toString(StandardCharsets.US_ASCII)
        }
    }

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
            check(read > 0) { "unexpected EOF before REALITY proxied response" }
            offset += read
        }
    }

    private fun waitForListener(port: Int) {
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

    private fun reservePort(): Int = ServerSocket(0, 50, loopback).use { it.localPort }

    private fun drain(process: Process, name: String) {
        thread(name = name, isDaemon = true) {
            runCatching { process.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines -> lines.forEach { _ -> } } }
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
        Files.walk(path).use { paths -> paths.sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists) }
    }

    private data class RealityKeys(val privateKey: String, val publicKey: String)

    private class MemorySecretStore : SecretStore {
        private val values = linkedMapOf<String, CharArray>()
        private var nextId = 1

        fun putText(value: String, purpose: SecretPurpose): SecretRef {
            val chars = value.toCharArray()
            return try { put(purpose, chars) } finally { chars.clearSecret() }
        }

        override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef =
            SecretRef("secret://xray-reality-ci/${nextId++}").also { values[it.value] = secret.copyOf() }

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
            worker = thread(name = "pvnetwork-xray-reality-echo", isDaemon = true) {
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
                            check(count > 0) { "unexpected EOF at REALITY echo origin" }
                            offset += count
                        }
                        check(received.contentEquals(expected)) { "unexpected REALITY echo payload" }
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
        private const val VLESS_IDENTITY = "e1b2c3d4-5555-4555-8555-1234567890ab"
        private const val SHORT_ID = "0123456789abcdef"
        private const val MARKER = "pvnetwork-m3-vless-vision-reality"
    }
}
