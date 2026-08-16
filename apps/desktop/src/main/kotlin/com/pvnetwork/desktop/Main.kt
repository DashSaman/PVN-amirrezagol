package com.pvnetwork.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.pvnetwork.core.branding.PVNetworkBrand
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.diagnostics.DiagnosticEvent
import com.pvnetwork.core.i18n.TextDirection
import com.pvnetwork.core.profile.PVProfile

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = PVNetworkBrand.identity.productName,
    ) {
        PVNetworkDesktopApp()
    }
}

@Composable
fun PVNetworkDesktopApp(
    initialState: DesktopShellState = DesktopShellState(),
) {
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

    MaterialTheme(colors = if (darkTheme) darkColors() else lightColors()) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Surface(modifier = Modifier.fillMaxSize()) {
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
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        ProfilesPanel(
                            modifier = Modifier.weight(1.25f).fillMaxHeight(),
                            profiles = initialState.profiles,
                            copy = copy,
                        )
                        Column(
                            modifier = Modifier.weight(0.75f).fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            ConnectionPanel(
                                modifier = Modifier.fillMaxWidth(),
                                state = initialState.connection.state,
                                copy = copy,
                            )
                            DiagnosticsPanel(
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                events = initialState.sanitizedDiagnostics(),
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
            Text(PVNetworkBrand.identity.productName, fontSize = 26.sp)
            TechnicalText("M1 desktop shell")
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
    profiles: List<PVProfile>,
    copy: ShellCopy,
) {
    ShellCard(modifier) {
        Text(copy.profiles, fontSize = 20.sp)
        Spacer(Modifier.height(12.dp))
        if (profiles.isEmpty()) {
            EmptyState(copy.noProfiles)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(profiles, key = { it.id.value }) { profile ->
                    ProfileRow(profile, copy)
                }
            }
        }
    }
}

@Composable
private fun ProfileRow(profile: PVProfile, copy: ShellCopy) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(profile.displayName)
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
    state: ConnectionState,
    copy: ShellCopy,
) {
    ShellCard(modifier) {
        Text(copy.connection, fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        TechnicalText(state.name)
    }
}

@Composable
private fun DiagnosticsPanel(
    modifier: Modifier,
    events: List<DiagnosticEvent>,
    copy: ShellCopy,
) {
    ShellCard(modifier) {
        Text(copy.diagnostics, fontSize = 20.sp)
        Spacer(Modifier.height(10.dp))
        if (events.isEmpty()) {
            EmptyState(copy.noDiagnostics)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(events, key = { "${it.timestampEpochMillis}:${it.subsystem}:${it.code}" }) { event ->
                    DiagnosticRow(event)
                }
            }
        }
    }
}

@Composable
private fun DiagnosticRow(event: DiagnosticEvent) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        TechnicalText("${event.severity.name} · ${event.subsystem} · ${event.code}")
        event.metadata.forEach { (key, value) ->
            TechnicalText("$key=$value")
        }
        Spacer(Modifier.height(6.dp))
        Divider()
    }
}

@Composable
private fun ShellCard(
    modifier: Modifier,
    content: @Composable Column.() -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        elevation = 4.dp,
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(18.dp), content = content)
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
