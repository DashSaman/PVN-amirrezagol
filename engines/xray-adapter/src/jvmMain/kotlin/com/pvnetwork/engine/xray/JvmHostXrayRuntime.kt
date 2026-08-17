package com.pvnetwork.engine.xray

import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.connection.ConnectionStateMachine
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretStore
import java.io.File
import java.io.Writer
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions
import java.util.Comparator
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

/**
 * POSIX desktop runtime for an Xray executable supplied by the host.
 *
 * The product does not download or bundle Xray here. The exact executable is
 * probed directly, transient configuration is mode 0600 in a mode 0700 directory,
 * secrets are resolved only through SecretStore, Xray validates its own config,
 * and only then is the long-lived process started without a shell.
 */
class JvmHostXrayRuntimeFactory(
    executable: Path? = null,
    private val socksListenPort: Int = DEFAULT_SOCKS_PORT,
) : XrayRuntimeFactory {
    init {
        require(socksListenPort in 1..65535) { "Xray SOCKS listen port must be between 1 and 65535" }
    }

    private val selectedExecutable = executable?.toAbsolutePath()?.normalize() ?: discoverExecutable()
    private val probe = selectedExecutable?.let(::probeExecutable) ?: Probe(false, null)

    override val runtimeDescriptor = XrayRuntimeDescriptor(
        implementationId = IMPLEMENTATION_ID,
        upstreamVersion = probe.versionLine,
        availableCapabilities = if (probe.usable) setOf(XrayAdapter.VLESS_CAPABILITY) else emptySet(),
    )

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val executablePath = selectedExecutable
        check(probe.usable && executablePath != null) { "host-supplied Xray runtime is unavailable" }
        val identityRef = profile.secretRefs[IDENTITY_SECRET_ROLE]
            ?: error("VLESS protected identity reference is missing")
        return JvmHostXrayPreparedConnection(
            executable = executablePath,
            profile = profile,
            identityRef = identityRef,
            secretStore = secretStore,
            socksListenPort = socksListenPort,
        )
    }

    private data class Probe(val usable: Boolean, val versionLine: String?)

    private fun probeExecutable(path: Path): Probe {
        if (!isSupportedHost() || !supportsPosixPermissions()) return Probe(false, null)
        if (!Files.isRegularFile(path) || !Files.isExecutable(path)) return Probe(false, null)
        return runCatching {
            val process = ProcessBuilder(path.toString(), "version")
                .redirectErrorStream(true)
                .start()
            val firstLine = AtomicReference<String?>(null)
            val outputDone = CountDownLatch(1)
            thread(name = "pvnetwork-xray-version-probe", isDaemon = true) {
                try {
                    process.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                        lines.forEach { line ->
                            if (firstLine.get() == null && line.isNotBlank()) {
                                firstLine.compareAndSet(null, line.trim().take(MAX_VERSION_LINE_LENGTH))
                            }
                        }
                    }
                } finally {
                    outputDone.countDown()
                }
            }
            val exited = process.waitFor(PROBE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            if (!exited) {
                process.destroyForcibly()
                process.waitFor(PROBE_KILL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            }
            outputDone.await(PROBE_KILL_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            val line = firstLine.get()
            Probe(
                usable = exited && process.exitValue() == 0 && isXrayVersionLine(line),
                versionLine = line,
            )
        }.getOrElse { Probe(false, null) }
    }

    private fun isXrayVersionLine(line: String?): Boolean {
        if (line == null || !line.startsWith("Xray ")) return false
        val token = line.removePrefix("Xray ").substringBefore(' ').trim()
        return token.isNotBlank() && token.firstOrNull()?.isDigit() == true
    }

    private fun discoverExecutable(): Path? {
        if (!isSupportedHost()) return null
        val candidates = buildList {
            System.getenv("PATH")
                ?.split(File.pathSeparatorChar)
                ?.filter(String::isNotBlank)
                ?.forEach { add(Path.of(it).resolve("xray")) }
            add(Path.of("/usr/local/bin/xray"))
            add(Path.of("/usr/bin/xray"))
        }
        return candidates.firstOrNull { Files.isRegularFile(it) && Files.isExecutable(it) }
    }

    private fun isSupportedHost(): Boolean {
        val os = System.getProperty("os.name").orEmpty().lowercase()
        return os.contains("linux") || os.contains("mac") || os.contains("darwin")
    }

    private fun supportsPosixPermissions(): Boolean =
        java.nio.file.FileSystems.getDefault().supportedFileAttributeViews().contains("posix")

    companion object {
        const val IMPLEMENTATION_ID = "host-xray-process-posix"
        const val DEFAULT_SOCKS_PORT = 10808
        internal const val IDENTITY_SECRET_ROLE = "xray.vless.identity"
        private const val PROBE_TIMEOUT_SECONDS = 3L
        private const val PROBE_KILL_TIMEOUT_SECONDS = 1L
        private const val MAX_VERSION_LINE_LENGTH = 192
    }
}

private class JvmHostXrayPreparedConnection(
    private val executable: Path,
    private val profile: PVProfile,
    private val identityRef: SecretRef,
    private val secretStore: SecretStore,
    private val socksListenPort: Int,
) : PreparedConnection {
    private val lock = Any()
    private var machine = ConnectionStateMachine()
    private var process: Process? = null
    private var validationProcess: Process? = null
    private var runtimeDirectory: Path? = null
    private var started = false

    override fun start(onState: (ConnectionSnapshot) -> Unit) {
        synchronized(lock) {
            check(!started) { "Xray prepared connection is single-use" }
            started = true
            emit(machine.transition(ConnectionState.PREPARING), onState)
        }

        val config = try {
            createProtectedRuntimeConfig()
        } catch (_: MissingIdentitySecret) {
            fail("XRAY_IDENTITY_SECRET_UNAVAILABLE", onState)
            return
        } catch (_: Throwable) {
            cleanupRuntimeDirectory()
            fail("XRAY_RUNTIME_MATERIALIZATION_FAILED", onState)
            return
        }

        if (!validateConfig(config)) {
            cleanupRuntimeDirectory()
            fail("XRAY_CONFIG_VALIDATION_FAILED", onState)
            return
        }

        val launched = try {
            ProcessBuilder(executable.toString(), "run", "-c", config.toString())
                .directory(config.parent.toFile())
                .redirectErrorStream(true)
                .start()
        } catch (_: Throwable) {
            cleanupRuntimeDirectory()
            fail("XRAY_PROCESS_START_FAILED", onState)
            return
        }

        synchronized(lock) {
            process = launched
            emit(machine.transition(ConnectionState.CONNECTING), onState)
        }
        monitor(launched, onState)
        startReadinessDeadline(launched, onState)
    }

    override fun stop(onState: (ConnectionSnapshot) -> Unit) {
        val currentProcess: Process?
        val currentValidation: Process?
        synchronized(lock) {
            if (machine.state == ConnectionState.DISCONNECTED) {
                cleanupRuntimeDirectory()
                emit(ConnectionState.DISCONNECTED, onState)
                return
            }
            if (machine.state != ConnectionState.DISCONNECTING) {
                emit(machine.transition(ConnectionState.DISCONNECTING), onState)
            }
            currentProcess = process
            currentValidation = validationProcess
        }

        terminate(currentValidation)
        terminate(currentProcess)
        cleanupRuntimeDirectory()
        synchronized(lock) {
            process = null
            validationProcess = null
            if (machine.state != ConnectionState.DISCONNECTED) {
                emit(machine.transition(ConnectionState.DISCONNECTED), onState)
            }
        }
    }

    override fun snapshot(): ConnectionSnapshot = synchronized(lock) {
        ConnectionSnapshot(machine.state, XrayAdapter.ADAPTER_ID)
    }

    private fun createProtectedRuntimeConfig(): Path {
        check(java.nio.file.FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
            "POSIX permissions are required for the host Xray runtime"
        }
        val directory = Files.createTempDirectory(
            "pvnetwork-xray-",
            PosixFilePermissions.asFileAttribute(DIRECTORY_PERMISSIONS),
        )
        runtimeDirectory = directory
        val config = directory.resolve("config.json")
        Files.createFile(config, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS))
        val found = secretStore.withSecret(identityRef) { identity ->
            Files.newBufferedWriter(config, StandardCharsets.UTF_8).use { writer ->
                writeConfig(writer, identity)
            }
        }
        if (found == null) {
            cleanupRuntimeDirectory()
            throw MissingIdentitySecret()
        }
        return config
    }

    private fun validateConfig(config: Path): Boolean = runCatching {
        val candidate = ProcessBuilder(
            executable.toString(),
            "run",
            "-test",
            "-c", config.toString(),
        )
            .directory(config.parent.toFile())
            .redirectErrorStream(true)
            .start()
        synchronized(lock) { validationProcess = candidate }
        val drained = CountDownLatch(1)
        thread(name = "pvnetwork-xray-config-test-output", isDaemon = true) {
            try {
                candidate.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                    lines.forEach { _ -> /* drain without retaining potentially sensitive diagnostics */ }
                }
            } finally {
                drained.countDown()
            }
        }
        val exited = candidate.waitFor(CONFIG_TEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        if (!exited) terminate(candidate)
        drained.await(CONFIG_TEST_DRAIN_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        synchronized(lock) {
            if (validationProcess === candidate) validationProcess = null
        }
        exited && candidate.exitValue() == 0
    }.getOrElse {
        synchronized(lock) { validationProcess = null }
        false
    }

    private fun monitor(launched: Process, onState: (ConnectionSnapshot) -> Unit) {
        thread(name = "pvnetwork-xray-monitor", isDaemon = true) {
            var readyObserved = false
            runCatching {
                launched.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                    lines.forEach { line ->
                        if (!readyObserved && isReadyLine(line)) {
                            readyObserved = true
                            synchronized(lock) {
                                if (process === launched && machine.state == ConnectionState.CONNECTING) {
                                    emit(machine.transition(ConnectionState.ESTABLISHING_TUNNEL), onState)
                                    emit(machine.transition(ConnectionState.CONNECTED), onState)
                                }
                            }
                        }
                    }
                }
            }
            runCatching { launched.waitFor() }
            cleanupRuntimeDirectory()
            synchronized(lock) {
                if (process === launched) process = null
                if (machine.state !in setOf(ConnectionState.DISCONNECTING, ConnectionState.DISCONNECTED, ConnectionState.ERROR)) {
                    emit(machine.transition(ConnectionState.ERROR), onState, "XRAY_PROCESS_EXITED")
                }
            }
        }
    }

    private fun startReadinessDeadline(launched: Process, onState: (ConnectionSnapshot) -> Unit) {
        thread(name = "pvnetwork-xray-readiness-deadline", isDaemon = true) {
            Thread.sleep(READINESS_TIMEOUT_MILLIS)
            val timedOut = synchronized(lock) {
                process === launched && machine.state == ConnectionState.CONNECTING
            }
            if (timedOut) {
                terminate(launched)
                cleanupRuntimeDirectory()
                synchronized(lock) {
                    if (process === launched) process = null
                    if (machine.state == ConnectionState.CONNECTING) {
                        emit(machine.transition(ConnectionState.ERROR), onState, "XRAY_READINESS_TIMEOUT")
                    }
                }
            }
        }
    }

    private fun isReadyLine(line: String): Boolean {
        val normalized = line.lowercase()
        return "started" in normalized && "xray" in normalized
    }

    private fun terminate(candidate: Process?) {
        candidate ?: return
        if (!candidate.isAlive) return
        candidate.destroy()
        if (!candidate.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            candidate.destroyForcibly()
            candidate.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
    }

    private fun fail(reasonCode: String, onState: (ConnectionSnapshot) -> Unit) {
        synchronized(lock) {
            if (machine.state != ConnectionState.ERROR) {
                emit(machine.transition(ConnectionState.ERROR), onState, reasonCode)
            }
        }
    }

    private fun emit(
        state: ConnectionState,
        onState: (ConnectionSnapshot) -> Unit,
        reasonCode: String? = null,
    ) {
        onState(ConnectionSnapshot(state, XrayAdapter.ADAPTER_ID, reasonCode))
    }

    private fun cleanupRuntimeDirectory() {
        val directory = synchronized(lock) {
            runtimeDirectory.also { runtimeDirectory = null }
        } ?: return
        runCatching {
            Files.walk(directory).use { paths ->
                paths.sorted(Comparator.reverseOrder()).forEach { Files.deleteIfExists(it) }
            }
        }
    }

    private fun writeConfig(writer: Writer, identity: CharArray) {
        val security = profile.extensions["xray.security"] ?: error("missing xray.security")
        val transport = profile.extensions["xray.transport"] ?: error("missing xray.transport")
        val network = when (transport) {
            "raw" -> "raw"
            "websocket" -> "ws"
            "grpc" -> "grpc"
            "xhttp" -> "xhttp"
            "mkcp" -> "kcp"
            else -> error("unsupported Xray transport: $transport")
        }
        require(security in setOf("none", "tls", "reality")) { "unsupported Xray security: $security" }

        writer.write("{\"log\":{\"loglevel\":\"info\"},")
        writer.write("\"inbounds\":[{\"tag\":\"pvnetwork-socks\",\"listen\":\"127.0.0.1\",\"port\":$socksListenPort,\"protocol\":\"socks\",\"settings\":{\"udp\":true}}],")
        writer.write("\"outbounds\":[{\"tag\":\"pvnetwork-proxy\",\"protocol\":\"vless\",\"settings\":{\"vnext\":[{\"address\":")
        writeJsonString(writer, profile.endpoint.host)
        writer.write(",\"port\":${profile.endpoint.port},\"users\":[{\"id\":")
        writeJsonString(writer, identity)
        writer.write(",\"encryption\":\"none\"")
        profile.extensions["xray.flow"]?.takeIf(String::isNotBlank)?.let {
            writer.write(",\"flow\":")
            writeJsonString(writer, it)
        }
        writer.write("}]}]},\"streamSettings\":{\"network\":")
        writeJsonString(writer, network)
        writer.write(",\"security\":")
        writeJsonString(writer, security)
        when (security) {
            "tls" -> writeTlsSettings(writer)
            "reality" -> writeRealitySettings(writer)
        }
        writeTransportSettings(writer, transport)
        writer.write("}},{\"tag\":\"direct\",\"protocol\":\"freedom\"}]}")
    }

    private fun writeTlsSettings(writer: Writer) {
        val fields = mutableListOf<Pair<String, String>>()
        profile.extensions["xray.server-name"]?.takeIf(String::isNotBlank)?.let { fields += "serverName" to it }
        profile.extensions["xray.fingerprint"]?.takeIf(String::isNotBlank)?.let { fields += "fingerprint" to it }
        writer.write(",\"tlsSettings\":{")
        writeFields(writer, fields)
        writer.write("}")
    }

    private fun writeRealitySettings(writer: Writer) {
        val publicKey = profile.extensions["xray.reality-public-key"]?.takeIf(String::isNotBlank)
            ?: error("REALITY public key is required")
        val fields = mutableListOf("publicKey" to publicKey)
        profile.extensions["xray.server-name"]?.takeIf(String::isNotBlank)?.let { fields += "serverName" to it }
        profile.extensions["xray.fingerprint"]?.takeIf(String::isNotBlank)?.let { fields += "fingerprint" to it }
        profile.extensions["xray.reality-short-id"]?.let { fields += "shortId" to it }
        writer.write(",\"realitySettings\":{")
        writeFields(writer, fields)
        writer.write("}")
    }

    private fun writeTransportSettings(writer: Writer, transport: String) {
        when (transport) {
            "raw" -> Unit
            "websocket" -> {
                writer.write(",\"wsSettings\":{")
                var wrote = false
                profile.extensions["xray.path"]?.takeIf(String::isNotBlank)?.let {
                    writer.write("\"path\":")
                    writeJsonString(writer, it)
                    wrote = true
                }
                profile.extensions["xray.host-header"]?.takeIf(String::isNotBlank)?.let {
                    if (wrote) writer.write(",")
                    writer.write("\"headers\":{\"Host\":")
                    writeJsonString(writer, it)
                    writer.write("}")
                }
                writer.write("}")
            }
            "grpc" -> {
                writer.write(",\"grpcSettings\":{")
                profile.extensions["xray.service-name"]?.takeIf(String::isNotBlank)?.let {
                    writer.write("\"serviceName\":")
                    writeJsonString(writer, it)
                }
                writer.write("}")
            }
            "xhttp" -> {
                writer.write(",\"xhttpSettings\":{")
                val fields = mutableListOf<Pair<String, String>>()
                profile.extensions["xray.host-header"]?.takeIf(String::isNotBlank)?.let { fields += "host" to it }
                profile.extensions["xray.path"]?.takeIf(String::isNotBlank)?.let { fields += "path" to it }
                fields += "mode" to "auto"
                writeFields(writer, fields)
                writer.write("}")
            }
            "mkcp" -> writer.write(",\"kcpSettings\":{}")
        }
    }

    private fun writeFields(writer: Writer, fields: List<Pair<String, String>>) {
        fields.forEachIndexed { index, (name, value) ->
            if (index > 0) writer.write(",")
            writeJsonString(writer, name)
            writer.write(":")
            writeJsonString(writer, value)
        }
    }

    private fun writeJsonString(writer: Writer, value: String) {
        writer.write("\"")
        value.forEach { writeJsonChar(writer, it) }
        writer.write("\"")
    }

    private fun writeJsonString(writer: Writer, value: CharArray) {
        writer.write("\"")
        value.forEach { writeJsonChar(writer, it) }
        writer.write("\"")
    }

    private fun writeJsonChar(writer: Writer, c: Char) {
        when (c) {
            '\\' -> writer.write("\\\\")
            '"' -> writer.write("\\\"")
            '\b' -> writer.write("\\b")
            '\u000C' -> writer.write("\\f")
            '\n' -> writer.write("\\n")
            '\r' -> writer.write("\\r")
            '\t' -> writer.write("\\t")
            else -> if (c.code < 0x20) writer.write("\\u%04x".format(c.code)) else writer.write(c.code)
        }
    }

    private class MissingIdentitySecret : IllegalStateException()

    companion object {
        private const val CONFIG_TEST_TIMEOUT_SECONDS = 5L
        private const val CONFIG_TEST_DRAIN_TIMEOUT_SECONDS = 1L
        private const val STOP_TIMEOUT_SECONDS = 3L
        private const val READINESS_TIMEOUT_MILLIS = 8_000L
        private val DIRECTORY_PERMISSIONS = setOf(
            PosixFilePermission.OWNER_READ,
            PosixFilePermission.OWNER_WRITE,
            PosixFilePermission.OWNER_EXECUTE,
        )
        private val FILE_PERMISSIONS = setOf(
            PosixFilePermission.OWNER_READ,
            PosixFilePermission.OWNER_WRITE,
        )
    }
}
