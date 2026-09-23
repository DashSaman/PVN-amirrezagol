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
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.engine.xray.JvmHostXrayRuntimeFactory
import com.pvnetwork.engine.xray.ShareLinkParser
import com.pvnetwork.engine.xray.XrayAdapter
import java.nio.file.Path
import java.util.UUID
import java.util.concurrent.Executors

data class CoreStatus(
    val available: Boolean,
    val version: String?,
    val detail: String,
)

sealed interface ImportOutcome {
    data class Success(val profiles: List<PVProfile>, val warnings: Int, val failedLines: Int) : ImportOutcome
    data class Failure(val reason: String) : ImportOutcome
}

sealed interface SubscriptionOutcome {
    data class Success(val added: Int, val failedLines: Int) : SubscriptionOutcome
    data class Failure(val reason: String) : SubscriptionOutcome
}

/** latency display state per profile id: null = untested, "…" = testing, "ms" value or "timeout". */
data class LatencyCell(val testing: Boolean = false, val millis: Long? = null, val timeout: Boolean = false)

/**
 * Desktop orchestration: canonical import -> adapter validation -> real
 * host-supplied engine runtime -> system-proxy wiring. Connection truth
 * comes exclusively from the runtime state machine callbacks.
 */
class DesktopVpnController(
    private val secrets: DesktopSecretStore,
    private val repository: ProfileRepository,
    private val proxy: SystemProxyController,
    private val subscriptions: SubscriptionRepository = SubscriptionRepository(Path.of(System.getProperty("user.home"), ".pvnetwork", "subscriptions.txt")),
    private val useSystemProxy: Boolean = true,
    private val socksPort: Int = JvmHostXrayRuntimeFactory.DEFAULT_SOCKS_PORT,
) {
    private var connection: PreparedConnection? = null
    private val backgroundExecutor = Executors.newCachedThreadPool { runnable ->
        Thread(runnable, "pvnetwork-background").apply { isDaemon = true }
    }

    private val fetcher = SubscriptionFetcher { link, id ->
        ShareLinkParser.parse(link, id, secrets)?.profile
    }

    var profilesState by mutableStateOf(repository.profiles())
        private set
    var subscriptionsState by mutableStateOf(subscriptions.all())
        private set
    var selectedProfileId by mutableStateOf<ProfileId?>(null)
    var connectionState by mutableStateOf(ConnectionSnapshot(ConnectionState.DISCONNECTED))
        private set
    var coreStatus by mutableStateOf(CoreStatus(false, null, ""))
        private set
    var diagnostics by mutableStateOf<List<DiagnosticEvent>>(emptyList())
        private set
    var latencies by mutableStateOf<Map<String, LatencyCell>>(emptyMap())
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

    fun importShareLinks(text: String): ImportOutcome {
        val lines = text.lines().map(String::trim).filter(String::isNotBlank)
        if (lines.isEmpty()) return ImportOutcome.Failure("empty input")
        val imported = mutableListOf<PVProfile>()
        var warnings = 0
        var failed = 0
        lines.forEach { line ->
            val id = repository.nextId()
            val result = try {
                ShareLinkParser.parse(line, id, secrets)
            } catch (_: Throwable) {
                null
            }
            if (result == null) {
                failed++
            } else {
                repository.add(result.profile)
                imported += result.profile
                warnings += result.warnings.size
            }
        }
        profilesState = repository.profiles()
        if (imported.isEmpty()) return ImportOutcome.Failure("no parsable vless/vmess/trojan/ss link found")
        if (selectedProfileId == null) selectedProfileId = imported.first().id
        return ImportOutcome.Success(imported, warnings, failed)
    }

    fun addSubscription(rawUrl: String, name: String?): SubscriptionOutcome {
        val url = rawUrl.trim()
        val subscription = Subscription(
            id = UUID.randomUUID().toString(),
            name = name?.trim()?.takeIf(String::isNotBlank) ?: "اشتراک ${subscriptions.all().size + 1}",
            url = url,
        )
        return run {
            val outcome = refreshInto(subscription)
            if (outcome is SubscriptionOutcome.Success) {
                subscriptions.upsert(subscription.copy(lastUpdateEpochMillis = System.currentTimeMillis(), lastStatus = "OK (${outcome.added})"))
                subscriptionsState = subscriptions.all()
                outcome
            } else {
                val reason = (outcome as SubscriptionOutcome.Failure).reason
                subscriptions.upsert(subscription.copy(lastStatus = "خطا: $reason"))
                subscriptionsState = subscriptions.all()
                outcome
            }
        }
    }

    fun updateSubscription(id: String): SubscriptionOutcome {
        val existing = subscriptions.find(id) ?: return SubscriptionOutcome.Failure("subscription not found")
        return when (val outcome = refreshInto(existing)) {
            is SubscriptionOutcome.Success -> {
                subscriptions.upsert(
                    existing.copy(lastUpdateEpochMillis = System.currentTimeMillis(), lastStatus = "OK (${outcome.added})"),
                )
                subscriptionsState = subscriptions.all()
                outcome
            }
            is SubscriptionOutcome.Failure -> {
                subscriptions.upsert(existing.copy(lastStatus = "خطا: ${outcome.reason}"))
                subscriptionsState = subscriptions.all()
                outcome
            }
        }
    }

    fun updateAllSubscriptions(): Int {
        var ok = 0
        subscriptions.all().forEach { sub ->
            if (updateSubscription(sub.id) is SubscriptionOutcome.Success) ok++
        }
        return ok
    }

    fun removeSubscription(id: String) {
        val removedProfiles = repository.removeWhere { repository.subscriptionIdOf(it) == id }
        removedProfiles.forEach { profile -> profile.secretRefs.values.forEach(secrets::delete) }
        profilesState = repository.profiles()
        subscriptions.remove(id)
        subscriptionsState = subscriptions.all()
        if (selectedProfileId?.let { repository.find(it) } == null) selectedProfileId = profilesState.firstOrNull()?.id
    }

    private fun refreshInto(subscription: Subscription): SubscriptionOutcome = try {
        val (profiles, failed) = fetcher.fetch(subscription.url)
        val old = repository.removeWhere { repository.subscriptionIdOf(it) == subscription.id }
        old.forEach { profile -> profile.secretRefs.values.forEach(secrets::delete) }
        repository.addAll(profiles, subscription.id)
        profilesState = repository.profiles()
        if (selectedProfileId?.let { repository.find(it) } == null) {
            selectedProfileId = profilesState.firstOrNull()?.id
        }
        SubscriptionOutcome.Success(profiles.size, failed)
    } catch (failure: Throwable) {
        SubscriptionOutcome.Failure(failure.message ?: "subscription fetch failed")
    }

    fun deleteProfile(id: ProfileId) {
        if (connectionState.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)) return
        repository.find(id)?.secretRefs?.values?.forEach(secrets::delete)
        repository.remove(id)
        profilesState = repository.profiles()
        if (selectedProfileId == id) selectedProfileId = profilesState.firstOrNull()?.id
    }

    fun select(id: ProfileId) {
        if (connectionState.state in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)) {
            selectedProfileId = id
        }
    }

    fun subscriptionNameOf(profile: PVProfile): String? =
        repository.subscriptionIdOf(profile)?.let { subscriptions.find(it)?.name }

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

    fun testLatency(profile: PVProfile) {
        latencies = latencies + (profile.id.value to LatencyCell(testing = true))
        backgroundExecutor.execute {
            val millis = LatencyTester.tcpPingMillis(profile)
            latencies = latencies + (
                profile.id.value to LatencyCell(
                    testing = false,
                    millis = millis,
                    timeout = millis == null,
                )
                )
        }
    }

    fun testAllLatency() {
        profilesState.forEach(::testLatency)
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
