package com.pvnetwork.engine.mihomo

import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.connection.ConnectionStateMachine
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretStore
import java.io.File
import java.io.Writer
import java.net.InetSocketAddress
import java.net.Socket
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
 * POSIX/JVM runtime for a host-supplied Mihomo executable.
 *
 * No Mihomo binary is downloaded or bundled by this module. The executable is
 * identity-probed with `-v`, generated configuration is held in a private temp
 * directory, validated by Mihomo itself with `-t -f`, and the long-lived
 * process is considered ready only after its local SOCKS listener accepts a
 * connection. Reusable credentials are read only through SecretStore.
 */
class JvmHostMihomoRuntimeFactory(
    executable: Path? = null,
    private val socksListenPort: Int = DEFAULT_SOCKS_PORT,
) : MihomoRuntimeFactory {
    init { require(socksListenPort in 1..65535) }

    private val selectedExecutable = executable?.toAbsolutePath()?.normalize() ?: discoverExecutable()
    private val probe = selectedExecutable?.let(::probeExecutable) ?: Probe(false, null)

    override val runtimeDescriptor = MihomoRuntimeDescriptor(
        implementationId = IMPLEMENTATION_ID,
        upstreamVersion = probe.versionLine,
        availableCapabilities = if (probe.usable) MihomoAdapter.MIHOMO_PROTOCOLS else emptySet(),
    )

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val executablePath = selectedExecutable
        check(probe.usable && executablePath != null) { "host-supplied Mihomo runtime is unavailable" }
        return JvmHostMihomoPreparedConnection(executablePath, profile, secretStore, socksListenPort)
    }

    private data class Probe(val usable: Boolean, val versionLine: String?)

    private fun probeExecutable(path: Path): Probe {
        if (!isSupportedHost() || !supportsPosixPermissions()) return Probe(false, null)
        if (!Files.isRegularFile(path) || !Files.isExecutable(path)) return Probe(false, null)
        return runCatching {
            val process = ProcessBuilder(path.toString(), "-v").redirectErrorStream(true).start()
            val firstLine = AtomicReference<String?>(null)
            val drained = CountDownLatch(1)
            thread(name = "pvnetwork-mihomo-version-probe", isDaemon = true) {
                try {
                    process.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                        lines.forEach { line ->
                            if (firstLine.get() == null && line.isNotBlank()) firstLine.compareAndSet(null, line.trim().take(192))
                        }
                    }
                } finally { drained.countDown() }
            }
            val exited = process.waitFor(PROBE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            if (!exited) terminate(process)
            drained.await(1, TimeUnit.SECONDS)
            val line = firstLine.get()
            Probe(exited && process.exitValue() == 0 && isMihomoVersionLine(line), line)
        }.getOrElse { Probe(false, null) }
    }

    private fun isMihomoVersionLine(line: String?): Boolean {
        if (line == null || !line.startsWith("Mihomo Meta ")) return false
        val version = line.removePrefix("Mihomo Meta ").substringBefore(' ').trim()
        return version.startsWith("v") && version.drop(1).firstOrNull()?.isDigit() == true
    }

    private fun discoverExecutable(): Path? {
        if (!isSupportedHost()) return null
        val candidates = buildList {
            System.getenv("PATH")?.split(File.pathSeparatorChar)?.filter(String::isNotBlank)?.forEach { add(Path.of(it).resolve("mihomo")) }
            add(Path.of("/usr/local/bin/mihomo")); add(Path.of("/usr/bin/mihomo"))
        }
        return candidates.firstOrNull { Files.isRegularFile(it) && Files.isExecutable(it) }
    }

    private fun isSupportedHost(): Boolean {
        val os = System.getProperty("os.name").orEmpty().lowercase()
        return os.contains("linux") || os.contains("mac") || os.contains("darwin")
    }

    private fun supportsPosixPermissions() = java.nio.file.FileSystems.getDefault().supportedFileAttributeViews().contains("posix")

    private fun terminate(process: Process) {
        if (!process.isAlive) return
        process.destroy()
        if (!process.waitFor(1, TimeUnit.SECONDS)) process.destroyForcibly()
    }

    companion object {
        const val IMPLEMENTATION_ID = "host-mihomo-process-posix"
        const val DEFAULT_SOCKS_PORT = 10809
        private const val PROBE_TIMEOUT_SECONDS = 3L
    }
}

private class JvmHostMihomoPreparedConnection(
    private val executable: Path,
    private val profile: PVProfile,
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
            check(!started) { "Mihomo prepared connection is single-use" }
            started = true
            emit(machine.transition(ConnectionState.PREPARING), onState)
        }
        val config = try { createProtectedRuntimeConfig() } catch (_: MissingSecret) {
            cleanup(); fail("MIHOMO_CREDENTIAL_SECRET_UNAVAILABLE", onState); return
        } catch (_: Throwable) {
            cleanup(); fail("MIHOMO_RUNTIME_MATERIALIZATION_FAILED", onState); return
        }
        if (!validateConfig(config)) {
            cleanup(); fail("MIHOMO_CONFIG_VALIDATION_FAILED", onState); return
        }
        val launched = try {
            ProcessBuilder(executable.toString(), "-d", config.parent.toString(), "-f", config.toString())
                .directory(config.parent.toFile()).redirectErrorStream(true).start()
        } catch (_: Throwable) {
            cleanup(); fail("MIHOMO_PROCESS_START_FAILED", onState); return
        }
        synchronized(lock) { process = launched; emit(machine.transition(ConnectionState.CONNECTING), onState) }
        drainAndMonitor(launched, onState)
        awaitReadiness(launched, onState)
    }

    override fun stop(onState: (ConnectionSnapshot) -> Unit) {
        val launched: Process?
        val validating: Process?
        synchronized(lock) {
            if (machine.state == ConnectionState.DISCONNECTED) { cleanup(); emit(ConnectionState.DISCONNECTED, onState); return }
            if (machine.state != ConnectionState.DISCONNECTING) emit(machine.transition(ConnectionState.DISCONNECTING), onState)
            launched = process; validating = validationProcess
        }
        terminate(validating); terminate(launched); cleanup()
        synchronized(lock) {
            process = null; validationProcess = null
            if (machine.state != ConnectionState.DISCONNECTED) emit(machine.transition(ConnectionState.DISCONNECTED), onState)
        }
    }

    override fun snapshot(): ConnectionSnapshot = synchronized(lock) { ConnectionSnapshot(machine.state, MihomoAdapter.ADAPTER_ID) }

    private fun createProtectedRuntimeConfig(): Path {
        check(java.nio.file.FileSystems.getDefault().supportedFileAttributeViews().contains("posix"))
        val directory = Files.createTempDirectory("pvnetwork-mihomo-", PosixFilePermissions.asFileAttribute(DIRECTORY_PERMISSIONS))
        runtimeDirectory = directory
        val config = directory.resolve("config.yaml")
        Files.createFile(config, PosixFilePermissions.asFileAttribute(FILE_PERMISSIONS))
        val primaryRef = primaryCredentialRef()
        val written = secretStore.withSecret(primaryRef) { primary ->
            val obfsRef = profile.secretRefs[MihomoAdapter.HYSTERIA2_OBFS_PASSWORD_SECRET_ROLE]
            if (obfsRef == null) {
                Files.newBufferedWriter(config, StandardCharsets.UTF_8).use { writeConfig(it, primary, null) }
                true
            } else {
                secretStore.withSecret(obfsRef) { obfs ->
                    Files.newBufferedWriter(config, StandardCharsets.UTF_8).use { writeConfig(it, primary, obfs) }
                    true
                } ?: false
            }
        }
        if (written != true) throw MissingSecret()
        return config
    }

    private fun primaryCredentialRef(): SecretRef = when (profile.protocolId) {
        MihomoAdapter.HYSTERIA2_CAPABILITY -> profile.secretRefs[MihomoAdapter.HYSTERIA2_PASSWORD_SECRET_ROLE]
        MihomoAdapter.TUIC_CAPABILITY -> profile.secretRefs[MihomoAdapter.TUIC_PASSWORD_SECRET_ROLE]
        MihomoAdapter.ANYTLS_CAPABILITY -> profile.secretRefs[MihomoAdapter.ANYTLS_PASSWORD_SECRET_ROLE]
        else -> null
    } ?: throw MissingSecret()

    private fun validateConfig(config: Path): Boolean = runCatching {
        val candidate = ProcessBuilder(executable.toString(), "-d", config.parent.toString(), "-t", "-f", config.toString())
            .directory(config.parent.toFile()).redirectErrorStream(true).start()
        synchronized(lock) { validationProcess = candidate }
        val drained = CountDownLatch(1)
        thread(name = "pvnetwork-mihomo-config-test-output", isDaemon = true) {
            try { candidate.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines -> lines.forEach { _ -> } } }
            finally { drained.countDown() }
        }
        val exited = candidate.waitFor(CONFIG_TEST_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        if (!exited) terminate(candidate)
        drained.await(1, TimeUnit.SECONDS)
        synchronized(lock) { if (validationProcess === candidate) validationProcess = null }
        exited && candidate.exitValue() == 0
    }.getOrElse { synchronized(lock) { validationProcess = null }; false }

    private fun drainAndMonitor(launched: Process, onState: (ConnectionSnapshot) -> Unit) {
        thread(name = "pvnetwork-mihomo-monitor", isDaemon = true) {
            runCatching { launched.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines -> lines.forEach { _ -> } } }
            runCatching { launched.waitFor() }
            cleanup()
            synchronized(lock) {
                if (process === launched) process = null
                if (machine.state !in setOf(ConnectionState.DISCONNECTING, ConnectionState.DISCONNECTED, ConnectionState.ERROR)) {
                    emit(machine.transition(ConnectionState.ERROR), onState, "MIHOMO_PROCESS_EXITED")
                }
            }
        }
    }

    private fun awaitReadiness(launched: Process, onState: (ConnectionSnapshot) -> Unit) {
        thread(name = "pvnetwork-mihomo-readiness", isDaemon = true) {
            val deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(READINESS_TIMEOUT_MILLIS)
            while (System.nanoTime() < deadline && launched.isAlive) {
                if (listenerReady()) {
                    synchronized(lock) {
                        if (process === launched && machine.state == ConnectionState.CONNECTING) {
                            emit(machine.transition(ConnectionState.ESTABLISHING_TUNNEL), onState)
                            emit(machine.transition(ConnectionState.CONNECTED), onState)
                        }
                    }
                    return@thread
                }
                Thread.sleep(50)
            }
            val timedOut = synchronized(lock) { process === launched && machine.state == ConnectionState.CONNECTING }
            if (timedOut) {
                terminate(launched); cleanup()
                synchronized(lock) {
                    if (process === launched) process = null
                    if (machine.state == ConnectionState.CONNECTING) emit(machine.transition(ConnectionState.ERROR), onState, "MIHOMO_READINESS_TIMEOUT")
                }
            }
        }
    }

    private fun listenerReady(): Boolean = runCatching {
        Socket().use { socket -> socket.connect(InetSocketAddress("127.0.0.1", socksListenPort), 100) }
        true
    }.getOrDefault(false)

    private fun writeConfig(writer: Writer, primary: CharArray, obfsPassword: CharArray?) {
        writer.write("{\"socks-port\":$socksListenPort,\"allow-lan\":false,\"mode\":\"rule\",\"log-level\":\"info\",\"ipv6\":false,\"proxies\":[{")
        field(writer, "name", "pvnetwork-proxy", false)
        when (profile.protocolId) {
            MihomoAdapter.HYSTERIA2_CAPABILITY -> writeHysteria2(writer, primary, obfsPassword)
            MihomoAdapter.TUIC_CAPABILITY -> writeTuic(writer, primary)
            MihomoAdapter.ANYTLS_CAPABILITY -> writeAnyTls(writer, primary)
            else -> error("unsupported Mihomo protocol")
        }
        writer.write("}],\"rules\":[\"MATCH,pvnetwork-proxy\"]}")
    }

    private fun writeHysteria2(writer: Writer, password: CharArray, obfsPassword: CharArray?) {
        field(writer, "type", "hysteria2")
        endpoint(writer)
        field(writer, "password", password)
        optional(writer, "sni", "mihomo.sni")
        optional(writer, "up", "mihomo.hysteria2.up")
        optional(writer, "down", "mihomo.hysteria2.down")
        profile.extensions["mihomo.hysteria2.obfs"]?.takeIf(String::isNotBlank)?.let { field(writer, "obfs", it) }
        if (obfsPassword != null) field(writer, "obfs-password", obfsPassword)
        writer.write(",\"skip-cert-verify\":false")
    }

    private fun writeTuic(writer: Writer, password: CharArray) {
        field(writer, "type", "tuic")
        endpoint(writer)
        field(writer, "uuid", profile.extensions["mihomo.tuic.uuid"] ?: error("missing TUIC UUID"))
        field(writer, "password", password)
        optional(writer, "sni", "mihomo.sni")
        optional(writer, "congestion-controller", "mihomo.tuic.congestion-controller")
        optional(writer, "udp-relay-mode", "mihomo.tuic.udp-relay-mode")
        writer.write(",\"skip-cert-verify\":false")
    }

    private fun writeAnyTls(writer: Writer, password: CharArray) {
        field(writer, "type", "anytls")
        endpoint(writer)
        field(writer, "password", password)
        optional(writer, "sni", "mihomo.sni")
        optional(writer, "client-fingerprint", "mihomo.anytls.client-fingerprint")
        writer.write(",\"skip-cert-verify\":false")
    }

    private fun endpoint(writer: Writer) {
        field(writer, "server", profile.endpoint.host)
        writer.write(",\"port\":${profile.endpoint.port}")
    }

    private fun optional(writer: Writer, outputName: String, extension: String) {
        profile.extensions[extension]?.takeIf(String::isNotBlank)?.let { field(writer, outputName, it) }
    }

    private fun field(writer: Writer, name: String, value: String, prefixComma: Boolean = true) {
        if (prefixComma) writer.write(",")
        json(writer, name); writer.write(":"); json(writer, value)
    }

    private fun field(writer: Writer, name: String, value: CharArray) {
        writer.write(","); json(writer, name); writer.write(":"); json(writer, value)
    }

    private fun json(writer: Writer, value: String) { writer.write("\""); value.forEach { jsonChar(writer, it) }; writer.write("\"") }
    private fun json(writer: Writer, value: CharArray) { writer.write("\""); value.forEach { jsonChar(writer, it) }; writer.write("\"") }
    private fun jsonChar(writer: Writer, c: Char) {
        when (c) {
            '\\' -> writer.write("\\\\"); '"' -> writer.write("\\\""); '\n' -> writer.write("\\n"); '\r' -> writer.write("\\r"); '\t' -> writer.write("\\t")
            else -> if (c.code < 0x20) writer.write("\\u%04x".format(c.code)) else writer.write(c.code)
        }
    }

    private fun terminate(candidate: Process?) {
        candidate ?: return
        if (!candidate.isAlive) return
        candidate.destroy()
        if (!candidate.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS)) { candidate.destroyForcibly(); candidate.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS) }
    }

    private fun cleanup() {
        val directory = synchronized(lock) { runtimeDirectory.also { runtimeDirectory = null } } ?: return
        runCatching { Files.walk(directory).use { paths -> paths.sorted(Comparator.reverseOrder()).forEach(Files::deleteIfExists) } }
    }

    private fun fail(reason: String, onState: (ConnectionSnapshot) -> Unit) {
        synchronized(lock) { if (machine.state != ConnectionState.ERROR) emit(machine.transition(ConnectionState.ERROR), onState, reason) }
    }

    private fun emit(state: ConnectionState, onState: (ConnectionSnapshot) -> Unit, reason: String? = null) {
        onState(ConnectionSnapshot(state, MihomoAdapter.ADAPTER_ID, reason))
    }

    private class MissingSecret : IllegalStateException()

    companion object {
        private const val CONFIG_TEST_TIMEOUT_SECONDS = 8L
        private const val STOP_TIMEOUT_SECONDS = 3L
        private const val READINESS_TIMEOUT_MILLIS = 8_000L
        private val DIRECTORY_PERMISSIONS = setOf(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE)
        private val FILE_PERMISSIONS = setOf(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE)
    }
}
