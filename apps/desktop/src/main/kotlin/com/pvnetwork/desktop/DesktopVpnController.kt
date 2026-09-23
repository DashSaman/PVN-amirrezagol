package com.pvnetwork.desktop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.diagnostics.DiagnosticEvent
import com.pvnetwork.core.diagnostics.DiagnosticSeverity
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.engine.xray.JvmHostXrayRuntimeFactory
import com.pvnetwork.engine.xray.VlessShareLinkException
import com.pvnetwork.engine.xray.VlessShareLinkImporter
import com.pvnetwork.engine.xray.XrayAdapter
import java.nio.file.Path

data class CoreStatus(
    val available: Boolean,
    val version: String?,
    val detail: String,
)

sealed interface ImportOutcome {
    data class Success(val profile: PVProfile, val warnings: Int) : ImportOutcome
    data class Failure(val reason: String) : ImportOutcome
}

/**
 * Desktop orchestration: canonical import -> adapter validation -> real
 * host-supplied engine runtime -> optional system-proxy wiring. Connection
 * truth comes exclusively from the runtime state machine callbacks.
 */
class DesktopVpnController(
    private val secrets: DesktopSecretStore,
    private val repository: ProfileRepository,
    private val proxy: SystemProxyController,
    private val useSystemProxy: Boolean = true,
    private val socksPort: Int = JvmHostXrayRuntimeFactory.DEFAULT_SOCKS_PORT,
) {
    private var connection: PreparedConnection? = null

    var profilesState by mutableStateOf(repository.profiles())
        private set
    var selectedProfileId by mutableStateOf<ProfileId?>(null)
    var connectionState by mutableStateOf(ConnectionSnapshot(ConnectionState.DISCONNECTED))
        private set
    var coreStatus by mutableStateOf<CoreStatus>(CoreStatus(false, null, ""))
        private set
    var diagnostics by mutableStateOf<List<DiagnosticEvent>>(emptyList())
        private set

    private val adapter: XrayAdapter
    private val factory: JvmHostXrayRuntimeFactory

    init {
        factory = JvmHostXrayRuntimeFactory(socksListenPort = socksPort)
        adapter = XrayAdapter(factory)
        coreStatus = CoreStatus(
            available = factory.runtimeDescriptor.availableCapabilities.isNotEmpty(),
            version = factory.runtimeDescriptor.upstreamVersion,
            detail = if (factory.runtimeDescriptor.availableCapabilities.isEmpty()) {
                "xray executable not found (set PVNETWORK_XRAY_EXECUTABLE or place xray.exe in %LOCALAPPDATA%\\pvnetwork\\core)"
            } else {
                "host runtime ready"
            },
        )
        if (proxy.supported && useSystemProxy) proxy.installShutdownRestore()
    }

    fun importShareLink(link: String): ImportOutcome {
        val trimmed = link.trim()
        if (trimmed.isEmpty()) return ImportOutcome.Failure("empty input")
        return try {
            val id = repository.nextId()
            val imported = VlessShareLinkImporter(secrets).import(trimmed, id)
            repository.add(imported.canonicalProfile)
            profilesState = repository.profiles()
            if (selectedProfileId == null) selectedProfileId = id
            ImportOutcome.Success(imported.canonicalProfile, imported.warnings.size)
        } catch (failure: VlessShareLinkException) {
            ImportOutcome.Failure(failure.message ?: "invalid VLESS share link")
        } catch (failure: Throwable) {
            ImportOutcome.Failure(failure.message ?: "import failed")
        }
    }

    fun deleteProfile(id: ProfileId) {
        if (connectionState.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)) return
        if (selectedProfileId == id) selectedProfileId = null
        repository.remove(id)
        profilesState = repository.profiles()
    }

    fun select(id: ProfileId) {
        if (connectionState.state in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)) {
            selectedProfileId = id
        }
    }

    val httpPort: Int get() = if (socksPort == 65535) 65534 else socksPort + 1

    fun connect() {
        if (connectionState.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)) return
        val id = selectedProfileId ?: return record("DESKTOP_NO_PROFILE_SELECTED", "no profile selected")
        val profile = repository.find(id) ?: return record("DESKTOP_PROFILE_MISSING", "profile vanished")
        if (!coreStatus.available) return record("DESKTOP_CORE_UNAVAILABLE", coreStatus.detail)

        val validation = adapter.validate(profile)
        if (!validation.isValid) {
            record("DESKTOP_PROFILE_INVALID", validation.issues.joinToString { it.code })
            connectionState = ConnectionSnapshot(ConnectionState.ERROR, XrayAdapter.ADAPTER_ID, "DESKTOP_PROFILE_INVALID")
            return
        }

        val prepared = try {
            adapter.prepare(profile, secrets)
        } catch (failure: Throwable) {
            record("DESKTOP_PREPARE_FAILED", failure.message ?: "prepare failed")
            connectionState = ConnectionSnapshot(ConnectionState.ERROR, XrayAdapter.ADAPTER_ID, "DESKTOP_PREPARE_FAILED")
            return
        }
        connection = prepared
        prepared.start { snapshot -> onEngineState(snapshot) }
    }

    fun disconnect() {
        val current = connection ?: return
        current.stop { snapshot -> connectionState = snapshot }
        connection = null
        restoreProxyIfActive()
    }

    private fun onEngineState(snapshot: ConnectionSnapshot) {
        connectionState = snapshot
        when (snapshot.state) {
            ConnectionState.CONNECTED -> {
                record("DESKTOP_ENGINE_CONNECTED", "socks=127.0.0.1:$socksPort http=127.0.0.1:$httpPort")
                if (proxy.supported && useSystemProxy) {
                    val ok = proxy.enable("127.0.0.1", httpPort)
                    record(
                        if (ok) "DESKTOP_PROXY_ENABLED" else "DESKTOP_PROXY_ENABLE_FAILED",
                        "WinINET proxy -> 127.0.0.1:$httpPort",
                    )
                }
            }
            ConnectionState.ERROR, ConnectionState.DISCONNECTED -> {
                val reason = snapshot.reasonCode
                if (reason != null) record(reason, "engine reported ${snapshot.state}")
                restoreProxyIfActive()
                connection = null
            }
            else -> Unit
        }
    }

    private fun restoreProxyIfActive() {
        if (proxy.isActive) {
            proxy.restore()
            record("DESKTOP_PROXY_RESTORED", "system proxy returned to previous values")
        }
    }

    private fun record(code: String, detail: String) {
        diagnostics = diagnostics + DiagnosticEvent(
            timestampEpochMillis = System.currentTimeMillis(),
            severity = DiagnosticSeverity.INFO,
            subsystem = "desktop",
            code = code,
            metadata = mapOf("detail" to detail),
        )
    }

    companion object {
        fun defaultDataDirectory(): Path = Path.of(
            System.getProperty("user.home"),
            ".pvnetwork",
        )
    }
}
