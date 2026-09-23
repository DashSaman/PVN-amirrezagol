package com.pvnetwork.desktop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberDialogState
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.diagnostics.DiagnosticSanitizer
import com.pvnetwork.core.i18n.TextDirection
import com.pvnetwork.core.profile.PVProfile

private const val UI_SMOKE_ENV = "PVNETWORK_UI_SMOKE"
private const val UI_SMOKE_PASS = "PVNetwork desktop launch smoke: PASS"

private val GoldPrimary = Color(0xFFD4AF37)
private val GoldDim = Color(0xFF8C7326)
private val StatusGreen = Color(0xFF2E9E44)
private val StatusRed = Color(0xFFCC3B3B)
private val StatusAmber = Color(0xFFC98A1B)

fun main() = application {
    val smokeMode = System.getenv(UI_SMOKE_ENV) == "1"
    val exit = ::exitApplication

    Window(
        onCloseRequest = exit,
        title = "PVNetwork",
        icon = painterResource("pvnetwork_logo.png"),
    ) {
        if (smokeMode) {
            LaunchedEffect(Unit) {
                println(UI_SMOKE_PASS)
                exit()
            }
        }
        PVNetworkDesktopApp()
    }
}

@Composable
fun PVNetworkDesktopApp() {
    var language by remember { mutableStateOf(AppLanguage.PERSIAN) }
    var themePreference by remember { mutableStateOf(ThemePreference.SYSTEM) }
    val systemDark = isSystemInDarkTheme()
    val darkTheme = themePreference.resolve(systemDark)
    val copy = shellCopy(language)
    val layoutDirection = if (language.locale.direction == TextDirection.RTL) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
    val controller = remember {
        val dataDir = DesktopVpnController.defaultDataDirectory()
        try {
            DesktopVpnController(
                secrets = DesktopSecretStore(dataDir.resolve("secrets")),
                repository = ProfileRepository(dataDir.resolve("profiles.txt")),
                proxy = SystemProxyController(),
            )
        } catch (_: Throwable) {
            null
        }
    }

    val colors = if (darkTheme) {
        darkColors(primary = GoldPrimary, secondary = GoldDim, surface = Color(0xFF17191F), background = Color(0xFF121317))
    } else {
        lightColors(primary = GoldDim, secondary = GoldPrimary)
    }

    var showSubscriptions by remember { mutableStateOf(false) }
    var showAdd by remember { mutableStateOf(false) }
    var showDiagnostics by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }

    MaterialTheme(colors = colors) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Surface(modifier = Modifier.fillMaxSize()) {
                if (controller == null) {
                    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                        Text(copy.storageUnavailable, color = MaterialTheme.colors.error)
                    }
                    return@Surface
                }
                Column(modifier = Modifier.fillMaxSize()) {
                    Header(
                        controller = controller,
                        copy = copy,
                        language = language,
                        themePreference = themePreference,
                        onLanguageToggle = { language = language.toggle() },
                        onThemeNext = { themePreference = themePreference.next() },
                        onConnect = { controller.connect() },
                        onDisconnect = { controller.disconnect() },
                    )
                    Divider()
                    Toolbar(
                        controller = controller,
                        copy = copy,
                        onAddProfiles = { showAdd = true },
                        onSubscriptions = { showSubscriptions = true },
                        onDiagnostics = { showDiagnostics = true },
                        onTestAll = { controller.testAllLatency() },
                        onStatus = { statusMessage = it },
                    )
                    Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                        ServerTable(controller, copy)
                    }
                    StatusBar(controller, copy)
                }

                statusMessage?.let { message ->
                    LaunchedEffect(message) {
                        kotlinx.coroutines.delay(4000)
                        statusMessage = null
                    }
                    Box(Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.TopCenter) {
                        Card(elevation = 6.dp) { Text(message, modifier = Modifier.padding(10.dp), fontSize = 13.sp) }
                    }
                }

                if (showAdd) {
                    AddProfilesDialog(
                        copy = copy,
                        onDismiss = { showAdd = false },
                        onImport = { text ->
                            when (val outcome = controller.importShareLinks(text)) {
                                is ImportOutcome.Success -> {
                                    statusMessage = copy.importedCount
                                        .replace("{n}", outcome.profiles.size.toString())
                                        .replace("{f}", outcome.failedLines.toString())
                                    showAdd = false
                                }
                                is ImportOutcome.Failure -> statusMessage = "${copy.importFailed}: ${outcome.reason}"
                            }
                        },
                    )
                }
                if (showSubscriptions) {
                    SubscriptionsDialog(
                        controller = controller,
                        copy = copy,
                        onDismiss = { showSubscriptions = false },
                        onStatus = { statusMessage = it },
                    )
                }
                if (showDiagnostics) {
                    DiagnosticsDialog(
                        events = controller.diagnostics.map(DiagnosticSanitizer::sanitize),
                        copy = copy,
                        onDismiss = { showDiagnostics = false },
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    controller: DesktopVpnController,
    copy: ShellCopy,
    language: AppLanguage,
    themePreference: ThemePreference,
    onLanguageToggle: () -> Unit,
    onThemeNext: () -> Unit,
    onConnect: () -> Unit,
    onDisconnect: () -> Unit,
) {
    val state = controller.connectionState.state
    val busy = state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Image(painterResource("pvnetwork_logo.png"), contentDescription = null, modifier = Modifier.size(42.dp))
        Column {
            Text("PVNetwork", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                if (controller.coreStatus.available) {
                    copy.coreReady.replace("{version}", controller.coreStatus.version ?: "?")
                } else {
                    copy.coreMissingShort
                },
                fontSize = 11.sp,
                color = if (controller.coreStatus.available) {
                    MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                } else {
                    MaterialTheme.colors.error
                },
            )
        }
        StatusChip(state, copy)
        Spacer(Modifier.weight(1f))
        if (busy) {
            OutlinedButton(onClick = onDisconnect) { Text(copy.disconnect) }
            CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
        } else {
            Button(
                onClick = onConnect,
                enabled = controller.selectedProfileId != null && controller.coreStatus.available,
            ) {
                Text(copy.connect, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
        OutlinedButton(onClick = onLanguageToggle) { Text(language.locale.languageTag.uppercase()) }
        OutlinedButton(onClick = onThemeNext) { Text(copy.themeIcon) }
    }
}

@Composable
private fun StatusChip(state: ConnectionState, copy: ShellCopy) {
    val (label, color) = when (state) {
        ConnectionState.CONNECTED -> copy.stateConnected to StatusGreen
        ConnectionState.ERROR -> copy.stateError to StatusRed
        ConnectionState.DISCONNECTED -> copy.stateDisconnected to MaterialTheme.colors.onSurface
        else -> localizedState(state, copy) to StatusAmber
    }
    Row(
        modifier = Modifier.background(color.copy(alpha = 0.15f), RoundedCornerShape(50)).padding(horizontal = 12.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(Modifier.size(8.dp).background(color, CircleShape))
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = color)
    }
}

@Composable
private fun Toolbar(
    controller: DesktopVpnController,
    copy: ShellCopy,
    onAddProfiles: () -> Unit,
    onSubscriptions: () -> Unit,
    onDiagnostics: () -> Unit,
    onTestAll: () -> Unit,
    onStatus: (String) -> Unit,
) {
    val busy = controller.connectionState.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(onClick = onAddProfiles, enabled = !busy) { Text(copy.addProfiles) }
        OutlinedButton(onClick = onSubscriptions, enabled = !busy) { Text(copy.subscriptions) }
        OutlinedButton(onClick = onTestAll, enabled = !busy) { Text(copy.testAll) }
        OutlinedButton(onClick = onDiagnostics) { Text(copy.diagnostics) }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun ServerTable(controller: DesktopVpnController, copy: ShellCopy) {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.primary.copy(alpha = 0.10f)).padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(Modifier.width(34.dp))
            Text(copy.colName, modifier = Modifier.weight(2.2f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(copy.colProtocol, modifier = Modifier.weight(1f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(copy.colEndpoint, modifier = Modifier.weight(2.2f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(copy.colSource, modifier = Modifier.weight(1.3f), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(copy.colLatency, modifier = Modifier.width(86.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(copy.colActions, modifier = Modifier.width(46.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        if (controller.profilesState.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(copy.noProfiles, color = MaterialTheme.colors.onSurface.copy(alpha = 0.55f))
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(controller.profilesState, key = { it.id.value }) { profile ->
                    ServerRow(
                        profile = profile,
                        controller = controller,
                        copy = copy,
                    )
                    Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f))
                }
            }
        }
    }
}

@Composable
private fun ServerRow(
    profile: PVProfile,
    controller: DesktopVpnController,
    copy: ShellCopy,
) {
    val busy = controller.connectionState.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    val selected = controller.selectedProfileId == profile.id
    val latency = controller.latencies[profile.id.value]
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(
                if (selected) MaterialTheme.colors.primary.copy(alpha = 0.14f) else Color.Transparent,
            )
            .clickable(enabled = !busy) { controller.select(profile.id) }
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = selected,
            onCheckedChange = { if (!busy) controller.select(profile.id) },
            modifier = Modifier.size(34.dp),
            enabled = !busy,
        )
        Text(
            profile.displayName,
            modifier = Modifier.weight(2.2f),
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
        )
        LtrText(profile.protocolId, Modifier.weight(1f), fontSize = 12.sp)
        LtrText("${profile.endpoint.host}:${profile.endpoint.port}", Modifier.weight(2.2f), fontSize = 12.sp)
        Text(
            controller.subscriptionNameOf(profile) ?: copy.manual,
            modifier = Modifier.weight(1.3f),
            fontSize = 12.sp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
            maxLines = 1,
        )
        Box(Modifier.width(86.dp)) {
            when {
                latency == null -> Text("—", fontSize = 12.sp, color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f))
                latency.testing -> CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp)
                latency.timeout -> Text(copy.latencyTimeout, fontSize = 12.sp, color = StatusRed)
                else -> {
                    val color = when {
                        latency.millis!! < 300 -> StatusGreen
                        latency.millis < 1000 -> StatusAmber
                        else -> StatusRed
                    }
                    LtrTextColored("${latency.millis} ms", color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Box(Modifier.width(46.dp)) {
            OutlinedButton(
                enabled = !busy,
                onClick = { controller.deleteProfile(profile.id) },
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier.height(26.dp),
            ) { Text(copy.delete, fontSize = 11.sp) }
        }
    }
}

@Composable
private fun StatusBar(controller: DesktopVpnController, copy: ShellCopy) {
    Row(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.surface.copy(alpha = 0.6f)).padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LtrText("SOCKS 127.0.0.1:10808 · HTTP 127.0.0.1:10809", fontSize = 11.sp, alpha = 0.7f)
        Spacer(Modifier.weight(1f))
        Text(
            copy.profilesCount.replace("{n}", controller.profilesState.size.toString())
                .replace("{s}", controller.subscriptionsState.size.toString()),
            fontSize = 11.sp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
        )
    }
}

@Composable
private fun AddProfilesDialog(copy: ShellCopy, onDismiss: () -> Unit, onImport: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Dialog(onCloseRequest = onDismiss, title = copy.addProfiles, state = rememberDialogState(width = 560.dp, height = 340.dp)) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(copy.addProfilesHint, fontSize = 12.sp, color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f))
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f).fillMaxWidth(),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(enabled = text.isNotBlank(), onClick = { onImport(text) }) { Text(copy.addProfile) }
                OutlinedButton(onClick = onDismiss) { Text(copy.close) }
            }
        }
    }
}

@Composable
private fun SubscriptionsDialog(
    controller: DesktopVpnController,
    copy: ShellCopy,
    onDismiss: () -> Unit,
    onStatus: (String) -> Unit,
) {
    var url by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    Dialog(onCloseRequest = onDismiss, title = copy.subscriptions, state = rememberDialogState(width = 640.dp, height = 420.dp)) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text(copy.subName) }, modifier = Modifier.width(140.dp), singleLine = true)
                OutlinedTextField(value = url, onValueChange = { url = it }, label = { Text(copy.subUrl) }, modifier = Modifier.weight(1f), singleLine = true)
                Button(
                    enabled = url.isNotBlank(),
                    onClick = {
                        when (val outcome = controller.addSubscription(url, name)) {
                            is SubscriptionOutcome.Success -> {
                                onStatus(copy.subAdded.replace("{n}", outcome.added.toString()))
                                url = ""
                                name = ""
                            }
                            is SubscriptionOutcome.Failure -> onStatus("${copy.importFailed}: ${outcome.reason}")
                        }
                    },
                ) { Text(copy.subAdd) }
            }
            if (controller.subscriptionsState.isEmpty()) {
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(copy.subsEmpty, color = MaterialTheme.colors.onSurface.copy(alpha = 0.55f))
                }
            } else {
                LazyColumn(Modifier.weight(1f).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(controller.subscriptionsState, key = { it.id }) { sub ->
                        Card(elevation = 2.dp) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                Column(Modifier.weight(1f)) {
                                    Text(sub.name, fontWeight = FontWeight.SemiBold)
                                    LtrText(sub.url, fontSize = 11.sp, alpha = 0.6f)
                                    Text(
                                        sub.lastStatus ?: copy.subNeverUpdated,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                                    )
                                }
                                OutlinedButton(onClick = {
                                    when (val outcome = controller.updateSubscription(sub.id)) {
                                        is SubscriptionOutcome.Success -> onStatus(copy.subUpdated.replace("{n}", outcome.added.toString()))
                                        is SubscriptionOutcome.Failure -> onStatus("${copy.importFailed}: ${outcome.reason}")
                                    }
                                }) { Text(copy.subUpdate) }
                                OutlinedButton(onClick = {
                                    controller.removeSubscription(sub.id)
                                    onStatus(copy.subRemoved)
                                }) { Text(copy.delete) }
                            }
                        }
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(enabled = controller.subscriptionsState.isNotEmpty(), onClick = {
                    val ok = controller.updateAllSubscriptions()
                    onStatus(copy.subsUpdatedAll.replace("{n}", ok.toString()).replace("{t}", controller.subscriptionsState.size.toString()))
                }) { Text(copy.subUpdateAll) }
                OutlinedButton(onClick = onDismiss) { Text(copy.close) }
            }
        }
    }
}

@Composable
private fun DiagnosticsDialog(
    events: List<com.pvnetwork.core.diagnostics.DiagnosticEvent>,
    copy: ShellCopy,
    onDismiss: () -> Unit,
) {
    Dialog(onCloseRequest = onDismiss, title = copy.diagnostics, state = rememberDialogState(width = 620.dp, height = 400.dp)) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            if (events.isEmpty()) {
                Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text(copy.noDiagnostics)
                }
            } else {
                LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(events.asReversed()) { event ->
                        Column {
                            LtrText("${event.severity.name} · ${event.subsystem} · ${event.code}", fontSize = 11.sp, alpha = 0.8f)
                            event.metadata.forEach { (k, v) -> LtrText("$k=$v", fontSize = 11.sp, alpha = 0.6f) }
                            Divider()
                        }
                    }
                }
            }
            OutlinedButton(onClick = onDismiss) { Text(copy.close) }
        }
    }
}

private fun localizedState(state: ConnectionState, copy: ShellCopy): String = when (state) {
    ConnectionState.DISCONNECTED -> copy.stateDisconnected
    ConnectionState.PREPARING -> copy.statePreparing
    ConnectionState.REQUESTING_PERMISSION -> copy.statePreparing
    ConnectionState.CONNECTING -> copy.stateConnecting
    ConnectionState.AUTHENTICATING -> copy.stateConnecting
    ConnectionState.ESTABLISHING_TUNNEL -> copy.stateConnecting
    ConnectionState.CONNECTED -> copy.stateConnected
    ConnectionState.RECONNECTING -> copy.stateConnecting
    ConnectionState.DISCONNECTING -> copy.stateDisconnecting
    ConnectionState.ERROR -> copy.stateError
}

@Composable
private fun LtrText(text: String, modifier: Modifier = Modifier, fontSize: TextUnit = 12.sp, alpha: Float = 0.75f) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Text(text, modifier = modifier, fontSize = fontSize, color = MaterialTheme.colors.onSurface.copy(alpha = alpha))
    }
}

@Composable
private fun LtrTextColored(text: String, color: Color, fontSize: TextUnit = 12.sp, fontWeight: FontWeight = FontWeight.Normal) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Text(text, fontSize = fontSize, color = color, fontWeight = fontWeight)
    }
}
