package com.pvnetwork.engine.openconnect

import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.connection.ConnectionStateMachine
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretStore
import java.io.File
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

/**
 * POSIX/JVM runtime for a host-supplied OpenConnect executable.
 *
 * The product never downloads or bundles OpenConnect. Runtime identity and
 * advertised protocols are probed from `openconnect --version`. Passwords are
 * written as characters to stdin under `--passwd-on-stdin`; they never appear
 * in argv, environment variables, files, or retained logs.
 */
class JvmHostOpenConnectRuntimeFactory(
    executable: Path? = null,
) : OpenConnectRuntimeFactory {
    private val selectedExecutable = executable?.toAbsolutePath()?.normalize() ?: discoverExecutable()
    private val probe = selectedExecutable?.let(::probeExecutable) ?: Probe(false, null, emptySet())

    override val runtimeDescriptor = OpenConnectRuntimeDescriptor(
        implementationId = IMPLEMENTATION_ID,
        upstreamVersion = probe.versionLine,
        available = probe.usable && OpenConnectAdapter.ANYCONNECT_PROTOCOL in probe.supportedProtocols,
        supportedProtocols = probe.supportedProtocols.intersect(OpenConnectAdapter.IMPLEMENTED_PROTOCOLS),
    )

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val executablePath = selectedExecutable
        check(runtimeDescriptor.available && executablePath != null) { "host-supplied OpenConnect runtime is unavailable" }
        return JvmHostOpenConnectPreparedConnection(executablePath, profile, secretStore)
    }

    private data class Probe(
        val usable: Boolean,
        val versionLine: String?,
        val supportedProtocols: Set<String>,
    )

    private fun probeExecutable(path: Path): Probe {
        if (!isSupportedHost() || !Files.isRegularFile(path) || !Files.isExecutable(path)) {
            return Probe(false, null, emptySet())
        }
        return runCatching {
            val process = ProcessBuilder(path.toString(), "--version").redirectErrorStream(true).start()
            val lines = mutableListOf<String>()
            val drained = CountDownLatch(1)
            thread(name = "pvnetwork-openconnect-version-probe", isDaemon = true) {
                try {
                    process.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { sequence ->
                        sequence.take(32).forEach { line -> synchronized(lines) { lines += line.take(512) } }
                    }
                } finally {
                    drained.countDown()
                }
            }
            val exited = process.waitFor(PROBE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            if (!exited) terminate(process)
            drained.await(1, TimeUnit.SECONDS)
            val snapshot = synchronized(lines) { lines.toList() }
            val versionLine = snapshot.firstOrNull { it.trim().startsWith("OpenConnect version v") }?.trim()
            val protocolLine = snapshot.firstOrNull { it.trim().startsWith("Supported protocols:") }
            val protocols = protocolLine
                ?.substringAfter(':')
                ?.split(',')
                ?.map { it.trim().substringBefore(' ').lowercase() }
                ?.filter { it in OpenConnectAdapter.UPSTREAM_PROTOCOL_NAMES }
                ?.toSet()
                .orEmpty()
            Probe(exited && process.exitValue() == 0 && isVersionLine(versionLine) && protocols.isNotEmpty(), versionLine, protocols)
        }.getOrElse { Probe(false, null, emptySet()) }
    }

    private fun isVersionLine(line: String?): Boolean {
        if (line == null || !line.startsWith("OpenConnect version v")) return false
        return line.removePrefix("OpenConnect version v").firstOrNull()?.isDigit() == true
    }

    private fun discoverExecutable(): Path? {
        if (!isSupportedHost()) return null
        val candidates = buildList {
            System.getenv("PATH")?.split(File.pathSeparatorChar)?.filter(String::isNotBlank)?.forEach {
                add(Path.of(it).resolve("openconnect"))
            }
            add(Path.of("/usr/local/bin/openconnect"))
            add(Path.of("/usr/bin/openconnect"))
        }
        return candidates.firstOrNull { Files.isRegularFile(it) && Files.isExecutable(it) }
    }

    private fun isSupportedHost(): Boolean {
        val os = System.getProperty("os.name").orEmpty().lowercase()
        return os.contains("linux") || os.contains("mac") || os.contains("darwin")
    }

    private fun terminate(process: Process) {
        if (!process.isAlive) return
        process.destroy()
        if (!process.waitFor(1, TimeUnit.SECONDS)) {
            process.destroyForcibly()
            process.waitFor(1, TimeUnit.SECONDS)
        }
    }

    companion object {
        const val IMPLEMENTATION_ID = "host-openconnect-process-posix"
        private const val PROBE_TIMEOUT_SECONDS = 3L
    }
}

private class JvmHostOpenConnectPreparedConnection(
    private val executable: Path,
    private val profile: PVProfile,
    private val secretStore: SecretStore,
) : PreparedConnection {
    private val lock = Any()
    private var machine = ConnectionStateMachine()
    private var process: Process? = null
    private var started = false
    private val readinessCandidate = CountDownLatch(1)
    private val terminalFailure = AtomicReference<String?>(null)
    private val stopping = AtomicBoolean(false)

    override fun start(onState: (ConnectionSnapshot) -> Unit) {
        synchronized(lock) {
            check(!started) { "OpenConnect prepared connection is single-use" }
            started = true
            emit(machine.transition(ConnectionState.PREPARING), onState)
        }

        val passwordRef = profile.secretRefs[OpenConnectAdapter.PASSWORD_SECRET_ROLE]
        if (passwordRef == null) {
            fail("OPENCONNECT_PASSWORD_SECRET_UNAVAILABLE", onState)
            return
        }

        val command = try {
            buildCommand()
        } catch (_: Throwable) {
            fail("OPENCONNECT_COMMAND_BUILD_FAILED", onState)
            return
        }

        val launched = try {
            ProcessBuilder(command).redirectErrorStream(true).start()
        } catch (_: Throwable) {
            fail("OPENCONNECT_PROCESS_START_FAILED", onState)
            return
        }
        synchronized(lock) {
            process = launched
            emit(machine.transition(ConnectionState.CONNECTING), onState)
            emit(machine.transition(ConnectionState.AUTHENTICATING), onState)
        }
        drainAndMonitor(launched, onState)

        if (!writePassword(launched, passwordRef)) {
            terminalFailure.compareAndSet(null, "OPENCONNECT_PASSWORD_SECRET_UNAVAILABLE")
            terminate(launched)
            failIfNeeded(onState)
            return
        }

        if (!readinessCandidate.await(READINESS_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            val reason = terminalFailure.get() ?: if (launched.isAlive) "OPENCONNECT_READINESS_TIMEOUT" else "OPENCONNECT_PROCESS_EXITED"
            terminate(launched)
            fail(reason, onState)
            return
        }

        synchronized(lock) {
            if (machine.state == ConnectionState.AUTHENTICATING) {
                emit(machine.transition(ConnectionState.ESTABLISHING_TUNNEL), onState)
            }
        }

        Thread.sleep(TUN_SETUP_GRACE_MILLIS)
        val failure = terminalFailure.get()
        if (failure != null || !launched.isAlive) {
            terminate(launched)
            fail(failure ?: "OPENCONNECT_PROCESS_EXITED", onState)
            return
        }

        synchronized(lock) {
            if (machine.state == ConnectionState.ESTABLISHING_TUNNEL) {
                emit(machine.transition(ConnectionState.CONNECTED), onState)
            }
        }
    }

    override fun stop(onState: (ConnectionSnapshot) -> Unit) {
        stopping.set(true)
        val launched: Process?
        synchronized(lock) {
            if (machine.state == ConnectionState.DISCONNECTED) {
                emit(ConnectionState.DISCONNECTED, onState)
                return
            }
            if (machine.state != ConnectionState.DISCONNECTING) {
                val next = if (machine.state == ConnectionState.ERROR) ConnectionState.DISCONNECTING else ConnectionState.DISCONNECTING
                emit(machine.transition(next), onState)
            }
            launched = process
        }
        terminate(launched)
        synchronized(lock) {
            process = null
            if (machine.state != ConnectionState.DISCONNECTED) {
                emit(machine.transition(ConnectionState.DISCONNECTED), onState)
            }
        }
    }

    override fun snapshot(): ConnectionSnapshot = synchronized(lock) {
        ConnectionSnapshot(machine.state, OpenConnectAdapter.ADAPTER_ID, terminalFailure.get())
    }

    private fun buildCommand(): List<String> {
        val selectedProtocol = profile.extensions[OpenConnectAdapter.PROTOCOL_EXTENSION]
            ?.trim()?.lowercase().orEmpty().ifBlank { OpenConnectAdapter.ANYCONNECT_PROTOCOL }
        check(selectedProtocol == OpenConnectAdapter.ANYCONNECT_PROTOCOL)
        val username = requireNotNull(profile.extensions[OpenConnectAdapter.USERNAME_EXTENSION]).trim()
        val path = profile.extensions[OpenConnectAdapter.SERVER_PATH_EXTENSION].orEmpty()
        val userGroup = profile.extensions[OpenConnectAdapter.USERGROUP_EXTENSION]?.trim()
        val host = if (':' in profile.endpoint.host && !profile.endpoint.host.startsWith('[')) {
            "[${profile.endpoint.host}]"
        } else {
            profile.endpoint.host
        }
        val server = "https://$host:${profile.endpoint.port}$path"
        return buildList {
            add(executable.toString())
            add("--non-inter")
            add("--passwd-on-stdin")
            add("--protocol=$selectedProtocol")
            add("--user=$username")
            if (!userGroup.isNullOrBlank()) add("--usergroup=$userGroup")
            add(server)
        }
    }

    private fun writePassword(launched: Process, ref: SecretRef): Boolean {
        return secretStore.withSecret(ref) { password ->
            runCatching {
                OutputStreamWriter(launched.outputStream, StandardCharsets.UTF_8).use { writer ->
                    writer.write(password)
                    writer.write('\n'.code)
                    writer.flush()
                }
                true
            }.getOrDefault(false)
        } ?: false
    }

    private fun drainAndMonitor(launched: Process, onState: (ConnectionSnapshot) -> Unit) {
        thread(name = "pvnetwork-openconnect-output", isDaemon = true) {
            runCatching {
                launched.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                    lines.forEach { raw ->
                        val line = raw.take(2048)
                        when {
                            line.contains("Set up tun device failed", ignoreCase = true) -> {
                                terminalFailure.compareAndSet(null, "OPENCONNECT_TUN_SETUP_FAILED")
                            }
                            line.contains("Cookie was rejected", ignoreCase = true) ||
                                line.contains("Failed to obtain WebVPN cookie", ignoreCase = true) -> {
                                terminalFailure.compareAndSet(null, "OPENCONNECT_AUTHENTICATION_FAILED")
                            }
                            line.contains("SSL connection failure", ignoreCase = true) ||
                                line.contains("Server certificate verify failed", ignoreCase = true) -> {
                                terminalFailure.compareAndSet(null, "OPENCONNECT_TLS_FAILED")
                            }
                            line.contains("CSTP connected.", ignoreCase = true) -> readinessCandidate.countDown()
                        }
                    }
                }
            }
            val exited = runCatching { launched.waitFor(); true }.getOrDefault(false)
            if (exited && !stopping.get()) {
                terminalFailure.compareAndSet(null, "OPENCONNECT_PROCESS_EXITED")
                synchronized(lock) {
                    if (machine.state == ConnectionState.CONNECTED ||
                        machine.state == ConnectionState.ESTABLISHING_TUNNEL ||
                        machine.state == ConnectionState.AUTHENTICATING ||
                        machine.state == ConnectionState.CONNECTING
                    ) {
                        runCatching { emit(machine.transition(ConnectionState.ERROR), onState) }
                    }
                }
                readinessCandidate.countDown()
            }
        }
    }

    private fun failIfNeeded(onState: (ConnectionSnapshot) -> Unit) {
        val reason = terminalFailure.get() ?: "OPENCONNECT_RUNTIME_FAILED"
        fail(reason, onState)
    }

    private fun fail(reason: String, onState: (ConnectionSnapshot) -> Unit) {
        terminalFailure.compareAndSet(null, reason)
        synchronized(lock) {
            if (machine.state == ConnectionState.ERROR) {
                emit(ConnectionState.ERROR, onState, terminalFailure.get())
                return
            }
            if (machine.state == ConnectionState.DISCONNECTED) {
                emit(machine.transition(ConnectionState.PREPARING), onState)
            }
            if (machine.state != ConnectionState.ERROR) {
                runCatching { machine.transition(ConnectionState.ERROR) }
            }
            emit(ConnectionState.ERROR, onState, terminalFailure.get())
        }
    }

    private fun emit(state: ConnectionState, onState: (ConnectionSnapshot) -> Unit, reason: String? = null) {
        onState(ConnectionSnapshot(state, OpenConnectAdapter.ADAPTER_ID, reason))
    }

    private fun terminate(launched: Process?) {
        launched ?: return
        if (!launched.isAlive) return
        launched.destroy()
        if (!launched.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            launched.destroyForcibly()
            launched.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
    }

    companion object {
        private const val READINESS_TIMEOUT_SECONDS = 30L
        private const val TUN_SETUP_GRACE_MILLIS = 1_000L
        private const val STOP_TIMEOUT_SECONDS = 3L
    }
}
