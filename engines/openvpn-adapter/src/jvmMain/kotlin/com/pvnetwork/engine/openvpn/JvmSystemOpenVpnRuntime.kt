package com.pvnetwork.engine.openvpn

import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.connection.ConnectionStateMachine
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.security.SecretStore
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.util.Comparator
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Linux desktop runtime using an OpenVPN executable supplied by the host OS.
 *
 * This module does not distribute OpenVPN. The complete protected source profile
 * is materialized only inside a mode-0700 temporary directory, with the config
 * itself mode 0600, and deleted on stop/failure/process exit.
 */
class JvmSystemOpenVpnRuntimeFactory(
    executable: Path? = null,
) : OpenVpnRuntimeFactory {
    private val selectedExecutable: Path? = executable?.toAbsolutePath()?.normalize() ?: discoverExecutable()
    private val probe = selectedExecutable?.let(::probeExecutable)

    override val runtimeDescriptor: OpenVpnRuntimeDescriptor = OpenVpnRuntimeDescriptor(
        implementationId = IMPLEMENTATION_ID,
        upstreamVersion = probe?.versionLine,
        available = probe?.usable == true,
    )

    override fun prepare(profile: PVProfile, secretStore: SecretStore): PreparedConnection {
        val executablePath = selectedExecutable
        check(runtimeDescriptor.available && executablePath != null) {
            "system OpenVPN runtime is unavailable"
        }
        val sourceRef = profile.secretRefs["openvpn.original-profile"]
            ?: error("OpenVPN protected source reference is missing")
        return JvmSystemOpenVpnPreparedConnection(executablePath, sourceRef, secretStore)
    }

    private data class Probe(val usable: Boolean, val versionLine: String?)

    private fun probeExecutable(path: Path): Probe {
        if (!isSupportedHost() || !supportsPosixPermissions()) return Probe(false, null)
        if (!Files.isRegularFile(path) || !Files.isExecutable(path)) return Probe(false, null)
        return runCatching {
            val process = ProcessBuilder(path.toString(), "--version")
                .redirectErrorStream(true)
                .start()
            val firstLine = process.inputStream.bufferedReader(StandardCharsets.UTF_8).use { it.readLine() }
            val exited = process.waitFor(PROBE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            if (!exited) process.destroyForcibly()
            Probe(
                usable = exited && process.exitValue() == 0 && !firstLine.isNullOrBlank(),
                versionLine = firstLine?.trim()?.take(MAX_VERSION_LINE_LENGTH),
            )
        }.getOrElse { Probe(false, null) }
    }

    private fun discoverExecutable(): Path? {
        if (!isSupportedHost()) return null
        val candidates = buildList {
            System.getenv("PATH")
                ?.split(File.pathSeparatorChar)
                ?.filter(String::isNotBlank)
                ?.forEach { add(Path.of(it).resolve("openvpn")) }
            add(Path.of("/usr/sbin/openvpn"))
            add(Path.of("/usr/bin/openvpn"))
        }
        return candidates.firstOrNull { Files.isRegularFile(it) && Files.isExecutable(it) }
    }

    private fun isSupportedHost(): Boolean =
        System.getProperty("os.name").orEmpty().lowercase().contains("linux")

    private fun supportsPosixPermissions(): Boolean =
        FileSystemsCompat.supportsPosixPermissions()

    companion object {
        const val IMPLEMENTATION_ID = "system-openvpn-process-linux"
        private const val PROBE_TIMEOUT_SECONDS = 3L
        private const val MAX_VERSION_LINE_LENGTH = 160
    }
}

private class JvmSystemOpenVpnPreparedConnection(
    private val executable: Path,
    private val sourceRef: com.pvnetwork.core.profile.SecretRef,
    private val secretStore: SecretStore,
) : PreparedConnection {
    private val lock = Any()
    private var machine = ConnectionStateMachine()
    private var process: Process? = null
    private var runtimeDirectory: Path? = null
    private var started = false

    override fun start(onState: (ConnectionSnapshot) -> Unit) {
        synchronized(lock) {
            check(!started) { "OpenVPN prepared connection is single-use" }
            started = true
            emit(machine.transition(ConnectionState.PREPARING), onState)
        }

        val config = try {
            createProtectedRuntimeConfig()
        } catch (_: MissingProtectedSource) {
            fail("OPENVPN_SOURCE_SECRET_UNAVAILABLE", onState)
            return
        } catch (_: Throwable) {
            fail("OPENVPN_RUNTIME_MATERIALIZATION_FAILED", onState)
            return
        }

        val launched = try {
            ProcessBuilder(
                executable.toString(),
                "--config", config.toString(),
                "--auth-nocache",
                "--verb", "3",
            )
                .directory(config.parent.toFile())
                .redirectErrorStream(true)
                .start()
        } catch (_: Throwable) {
            cleanupRuntimeDirectory()
            fail("OPENVPN_PROCESS_START_FAILED", onState)
            return
        }

        synchronized(lock) {
            process = launched
            emit(machine.transition(ConnectionState.CONNECTING), onState)
        }
        monitor(launched, onState)
    }

    override fun stop(onState: (ConnectionSnapshot) -> Unit) {
        val currentProcess: Process?
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
        }

        currentProcess?.let {
            it.destroy()
            if (!it.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                it.destroyForcibly()
                it.waitFor(STOP_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            }
        }
        cleanupRuntimeDirectory()
        synchronized(lock) {
            process = null
            if (machine.state != ConnectionState.DISCONNECTED) {
                emit(machine.transition(ConnectionState.DISCONNECTED), onState)
            }
        }
    }

    override fun snapshot(): ConnectionSnapshot = synchronized(lock) {
        ConnectionSnapshot(machine.state, OpenVpnAdapter.ADAPTER_ID)
    }

    private fun createProtectedRuntimeConfig(): Path {
        val directory = Files.createTempDirectory("pvnetwork-openvpn-")
        FileSystemsCompat.setPermissions(directory, DIRECTORY_PERMISSIONS)
        runtimeDirectory = directory
        val config = directory.resolve("profile.ovpn")
        Files.createFile(config)
        FileSystemsCompat.setPermissions(config, FILE_PERMISSIONS)
        val found = secretStore.withSecret(sourceRef) { chars ->
            Files.newBufferedWriter(config, StandardCharsets.UTF_8).use { writer ->
                writer.write(chars)
            }
        }
        if (found == null) {
            cleanupRuntimeDirectory()
            throw MissingProtectedSource()
        }
        return config
    }

    private fun monitor(launched: Process, onState: (ConnectionSnapshot) -> Unit) {
        thread(name = "pvnetwork-openvpn-monitor", isDaemon = true) {
            var connectedObserved = false
            runCatching {
                launched.inputStream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
                    lines.forEach { line ->
                        if (!connectedObserved && line.contains("Initialization Sequence Completed")) {
                            connectedObserved = true
                            synchronized(lock) {
                                if (machine.state == ConnectionState.CONNECTING) {
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
                    emit(machine.transition(ConnectionState.ERROR), onState, "OPENVPN_PROCESS_EXITED")
                }
            }
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
        onState(ConnectionSnapshot(state, OpenVpnAdapter.ADAPTER_ID, reasonCode))
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

    private class MissingProtectedSource : IllegalStateException()

    companion object {
        private const val STOP_TIMEOUT_SECONDS = 3L
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

/** Kept tiny so security assumptions are explicit and testable. */
private object FileSystemsCompat {
    fun supportsPosixPermissions(): Boolean =
        java.nio.file.FileSystems.getDefault().supportedFileAttributeViews().contains("posix")

    fun setPermissions(path: Path, permissions: Set<PosixFilePermission>) {
        check(supportsPosixPermissions()) { "POSIX permissions are required for the system OpenVPN runtime" }
        Files.setPosixFilePermissions(path, permissions)
    }
}
