package com.pvnetwork.desktop

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.pvnetwork.core.branding.PVNetworkBrand
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.diagnostics.DiagnosticSanitizer
import com.pvnetwork.core.i18n.TextDirection
import com.pvnetwork.core.profile.PVProfile

private const val UI_SMOKE_ENV = "PVNETWORK_UI_SMOKE"
private const val UI_SMOKE_PASS = "PVNetwork desktop launch smoke: PASS"

fun main() = application {
    val smokeMode = System.getenv(UI_SMOKE_ENV) == "1"
    val exit = ::exitApplication

    Window(
        onCloseRequest = exit,
        title = PVNetworkBrand.identity.productName,
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
    var language by remember { mutableStateOf(AppLanguage.ENGLISH) }
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
        } catch (failure: Throwable) {
            null
        }
    }

    MaterialTheme(colors = if (darkTheme) darkColors() else lightColors()) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Surface(modifier = Modifier.fillMaxSize()) {
                if (controller == null) {
                    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
                        Text(copy.storageUnavailable, color = MaterialTheme.colors.error)
                    }
                    return@Surface
                }
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Header(
                        language = language,
                        themePreference = themePreference,
                        copy = copy,
                        onLanguageToggle = { language = language.toggle() },
                        onThemeNext = { themePreference = themePreference.next() },
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        ProfilesPanel(
                            modifier = Modifier.weight(1.15f).fillMaxHeight(),
                            controller = controller,
                            copy = copy,
                        )
                        Column(
                            modifier = Modifier.weight(0.85f).fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            ConnectionPanel(
                                modifier = Modifier.fillMaxWidth(),
                                controller = controller,
                                copy = copy,
                            )
                            DiagnosticsPanel(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                events = controller.diagnostics.map(DiagnosticSanitizer::sanitize),
                                copy = copy,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(
    language: AppLanguage,
    themePreference: ThemePreference,
    copy: ShellCopy,
    onLanguageToggle: () -> Unit,
    onThemeNext: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(PVNetworkBrand.identity.productName, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            TechnicalText("PVNetwork Desktop · Xray VLESS")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onLanguageToggle) {
                Text("${copy.language}: ${language.locale.languageTag.uppercase()}")
            }
            Button(onClick = onThemeNext) {
                Text("${copy.theme}: ${themePreference.name}")
            }
        }
    }
}

@Composable
private fun ProfilesPanel(
    modifier: Modifier,
    controller: DesktopVpnController,
    copy: ShellCopy,
) {
    var linkInput by remember { mutableStateOf("") }
    var importMessage by remember { mutableStateOf<String?>(null) }

    ShellCard(modifier) {
        Text(copy.profiles, fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = linkInput,
            onValueChange = { linkInput = it },
            label = { Text(copy.pasteLink) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    when (val outcome = controller.importShareLink(linkInput)) {
                        is ImportOutcome.Success -> {
                            linkInput = ""
                            importMessage = copy.imported.replace("{name}", outcome.profile.displayName)
                        }
                        is ImportOutcome.Failure -> importMessage = "${copy.importFailed}: ${outcome.reason}"
                    }
                },
                enabled = linkInput.isNotBlank(),
            ) {
                Text(copy.addProfile)
            }
        }
        importMessage?.let {
            Spacer(Modifier.height(6.dp))
            Text(it, fontSize = 12.sp, color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f))
        }
        Spacer(Modifier.height(12.dp))
        val profiles = controller.profilesState
        if (profiles.isEmpty()) {
            EmptyState(copy.noProfiles)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(profiles, key = { it.id.value }) { profile ->
                    ProfileRow(
                        profile = profile,
                        copy = copy,
                        selected = controller.selectedProfileId == profile.id,
                        busy = controller.connectionState.state !in setOf(
                            ConnectionState.DISCONNECTED,
                            ConnectionState.ERROR,
                        ),
                        onSelect = { controller.select(profile.id) },
                        onDelete = { controller.deleteProfile(profile.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileRow(
    profile: PVProfile,
    copy: ShellCopy,
    selected: Boolean,
    busy: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(
                if (selected) MaterialTheme.colors.primary.copy(alpha = 0.12f) else Color.Transparent,
                RoundedCornerShape(12.dp),
            )
            .padding(8.dp)
            .clickable(enabled = !busy, onClick = onSelect),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(profile.displayName, fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal)
            OutlinedButton(enabled = !busy, onClick = onDelete) { Text(copy.delete) }
        }
        Spacer(Modifier.height(4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("${copy.protocol}:")
            TechnicalText(profile.protocolId)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("${copy.endpoint}:")
            TechnicalText("${profile.endpoint.host}:${profile.endpoint.port}")
        }
        Spacer(Modifier.height(8.dp))
        Divider()
    }
}

@Composable
private fun ConnectionPanel(
    modifier: Modifier,
    controller: DesktopVpnController,
    copy: ShellCopy,
) {
    val state = controller.connectionState.state
    val busy = state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    ShellCard(modifier) {
        Text(copy.connection, fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        Text(
            localizedState(state, copy),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = when (state) {
                ConnectionState.CONNECTED -> Color(0xFF2E7D32)
                ConnectionState.ERROR -> MaterialTheme.colors.error
                else -> MaterialTheme.colors.onSurface
            },
        )
        controller.connectionState.reasonCode?.let {
            Spacer(Modifier.height(4.dp))
            TechnicalText(it)
        }
        Spacer(Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (busy) {
                OutlinedButton(onClick = { controller.disconnect() }) {
                    Text(copy.disconnect)
                }
                CircularProgressIndicator(modifier = Modifier.height(20.dp).padding(2.dp))
            } else {
                Button(
                    onClick = { controller.connect() },
                    enabled = controller.selectedProfileId != null && controller.coreStatus.available,
                ) {
                    Text(copy.connect, fontSize = 16.sp)
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        Text(
            if (controller.coreStatus.available) {
                copy.coreReady.replace("{version}", controller.coreStatus.version ?: "?")
            } else {
                copy.coreMissing
            },
            fontSize = 12.sp,
            color = if (controller.coreStatus.available) {
                MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            } else {
                MaterialTheme.colors.error
            },
        )
        if (state == ConnectionState.CONNECTED) {
            Spacer(Modifier.height(6.dp))
            TechnicalText("SOCKS 127.0.0.1:10808 · HTTP 127.0.0.1:10809 · system proxy on")
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
private fun DiagnosticsPanel(
    modifier: Modifier,
    events: List<com.pvnetwork.core.diagnostics.DiagnosticEvent>,
    copy: ShellCopy,
) {
    ShellCard(modifier) {
        Text(copy.diagnostics, fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        if (events.isEmpty()) {
            EmptyState(copy.noDiagnostics)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(events.asReversed()) { event ->
                    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        TechnicalText("${event.severity.name} · ${event.subsystem} · ${event.code}")
                        event.metadata.forEach { (key, value) -> TechnicalText("$key=$value") }
                        Spacer(Modifier.height(6.dp))
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun ShellCard(
    modifier: Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        elevation = 4.dp,
    ) {
        Column(modifier = Modifier.padding(18.dp), content = content)
    }
}

@Composable
private fun EmptyState(message: String) {
    Box(
        modifier = Modifier.fillMaxWidth().background(
            MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
            RoundedCornerShape(12.dp),
        ).padding(16.dp),
    ) {
        Text(message, color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f))
    }
}

@Composable
private fun TechnicalText(text: String) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Text(text)
    }
}
