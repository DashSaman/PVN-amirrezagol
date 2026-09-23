package com.pvnetwork.desktop

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.diagnostics.DiagnosticSanitizer
import com.pvnetwork.core.profile.PVProfile
import kotlinx.coroutines.delay

private const val UI_SMOKE_ENV = "PVNETWORK_UI_SMOKE"
private const val UI_SMOKE_PASS = "PVNetwork desktop launch smoke: PASS"

// PVNetwork brand palette
private val BgDeep = Color(0xFF0D0F14)
private val BgPanel = Color(0xFF151823)
private val CardBg = Color(0xFF1C2030)
private val CardBgHover = Color(0xFF232839)
private val Gold = Color(0xFFD4AF37)
private val GoldSoft = Color(0xFFE8C96A)
private val GoldDim = Color(0xFF9A7F28)
private val Ink = Color(0xFFF2F3F7)
private val InkMuted = Color(0xFF9BA1B2)
private val Green = Color(0xFF3DCB64)
private val Red = Color(0xFFE25B5B)
private val Amber = Color(0xFFE5A83B)

private enum class NavSection { HOME, SERVERS, SUBSCRIPTIONS, DIAGNOSTICS, SETTINGS }

private data class NavCopy(
    val home: String,
    val servers: String,
    val subscriptions: String,
    val diagnostics: String,
    val settings: String,
    val connected: String,
    val disconnected: String,
    val connecting: String,
    val error: String,
    val duration: String,
    val currentServer: String,
    val noServer: String,
    val latencyOf: String,
    val testAll: String,
    val addProfile: String,
    val pasteHint: String,
    val add: String,
    val close: String,
    val delete: String,
    val update: String,
    val updateAll: String,
    val subUrl: String,
    val subName: String,
    val addSub: String,
    val subsEmpty: String,
    val neverUpdated: String,
    val serversN: String,
    val manual: String,
    val timeout: String,
    val language: String,
    val theme: String,
    val themeDark: String,
    val themeLight: String,
    val themeSystem: String,
    val sysProxy: String,
    val sysProxyHint: String,
    val ports: String,
    val core: String,
    val coreMissing: String,
    val aboutTitle: String,
    val aboutBody: String,
    val privacyTitle: String,
    val privacyBody: String,
    val tunnelNote: String,
    val storageUnavailable: String,
)

private fun navCopy(persian: Boolean) = if (persian) {
    NavCopy(
        home = "اتصال", servers = "سرورها", subscriptions = "اشتراک‌ها",
        diagnostics = "گزارش", settings = "تنظیمات",
        connected = "متصل", disconnected = "قطع است", connecting = "در حال برقراری…", error = "خطا",
        duration = "مدت اتصال", currentServer = "سرور فعلی", noServer = "سروری انتخاب نشده",
        latencyOf = "تأخیر", testAll = "تست تأخیر همه",
        addProfile = "افزودن پروفایل", pasteHint = "یک یا چند لینک vless / vmess / trojan / ss (هر خط یک لینک)",
        add = "افزودن", close = "بستن", delete = "حذف", update = "به‌روزرسانی", updateAll = "به‌روزرسانی همه",
        subUrl = "لینک اشتراک", subName = "نام (اختیاری)", addSub = "افزودن اشتراک",
        subsEmpty = "سروری نیست — لینک اشتراک اضافه کنید یا از «افزودن پروفایل» لینک بچسبانید",
        neverUpdated = "به‌روز نشده", serversN = "{n} سرور",
        manual = "دستی", timeout = "بی‌پاسخ",
        language = "زبان", theme = "پوسته",
        themeDark = "تیره", themeLight = "روشن", themeSystem = "سیستم",
        sysProxy = "پروکسی خودکار سیستم", sysProxyHint = "با اتصال، پروکسی ویندوز تنظیم و با قطع، به حالت قبل برمی‌گردد",
        ports = "پورت‌های محلی", core = "هسته موتور", coreMissing = "هسته Xray پیدا نشد",
        aboutTitle = "درباره PVNetwork",
        aboutBody = "نسخه 0.2 آزمایشی — ساخته‌شده روی Xray-core (MPL-2.0) و hev-socks5-tunnel (MIT)",
        privacyTitle = "حریم خصوصی",
        privacyBody = "هیچ داده‌ای از شما جمع نمی‌شود؛ اسرار فقط روی همین دستگاه رمزنگاری‌شده ذخیره می‌شوند",
        tunnelNote = "پروکسی سیستم فعال است — ترافیک مرورگرها از تونل می‌گذرد",
        storageUnavailable = "ذخیره‌سازی محلی مقداردهی نشد",
    )
} else {
    NavCopy(
        home = "Connect", servers = "Servers", subscriptions = "Subscriptions",
        diagnostics = "Diagnostics", settings = "Settings",
        connected = "Connected", disconnected = "Disconnected", connecting = "Connecting…", error = "Error",
        duration = "Session", currentServer = "Current server", noServer = "No server selected",
        latencyOf = "Latency", testAll = "Test all latency",
        addProfile = "Add profile", pasteHint = "One or more vless / vmess / trojan / ss links (one per line)",
        add = "Add", close = "Close", delete = "Delete", update = "Update", updateAll = "Update all",
        subUrl = "Subscription URL", subName = "Name (optional)", addSub = "Add subscription",
        subsEmpty = "No servers yet — add a subscription or paste links with Add profile",
        neverUpdated = "never updated", serversN = "{n} servers",
        manual = "manual", timeout = "timeout",
        language = "Language", theme = "Theme",
        themeDark = "Dark", themeLight = "Light", themeSystem = "System",
        sysProxy = "Automatic system proxy", sysProxyHint = "Applies Windows proxy on connect and restores it on disconnect",
        ports = "Local ports", core = "Engine core", coreMissing = "Xray core not found",
        aboutTitle = "About PVNetwork",
        aboutBody = "Version 0.2 preview — built on Xray-core (MPL-2.0) and hev-socks5-tunnel (MIT)",
        privacyTitle = "Privacy",
        privacyBody = "No data is collected; secrets stay encrypted on this device only",
        tunnelNote = "System proxy active — browser traffic goes through the tunnel",
        storageUnavailable = "Local storage could not be initialized",
    )
}

fun main() = application {
    val smokeMode = System.getenv(UI_SMOKE_ENV) == "1"
    val exit = ::exitApplication

    Window(
        onCloseRequest = exit,
        title = "PVNetwork",
        icon = painterResource("pvnetwork_logo.png"),
        state = androidx.compose.ui.window.rememberWindowState(width = 1240.dp, height = 800.dp),
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
    var persian by remember { mutableStateOf(true) }
    var section by remember { mutableStateOf(NavSection.HOME) }
    var themePreference by remember { mutableStateOf(ThemePreference.SYSTEM) }
    val systemDark = isSystemInDarkTheme()
    val dark = themePreference.resolve(systemDark)
    val copy = navCopy(persian)
    val layoutDirection = if (persian) LayoutDirection.Rtl else LayoutDirection.Ltr

    val startupFailure = remember { mutableStateOf<String?>(null) }
    val controller = remember {
        val dataDir = DesktopVpnController.defaultDataDirectory()
        try {
            DesktopVpnController(
                secrets = DesktopSecretStore(dataDir.resolve("secrets")),
                repository = ProfileRepository(dataDir.resolve("profiles.txt")),
                proxy = SystemProxyController(),
            )
        } catch (failure: Throwable) {
            val reason = "${failure.javaClass.simpleName}: ${failure.message ?: "unknown"}"
            startupFailure.value = reason
            runCatching {
                java.nio.file.Files.createDirectories(dataDir)
                java.nio.file.Files.write(
                    dataDir.resolve("desktop-error.log"),
                    reason.toByteArray(),
                )
            }
            null
        }
    }

    val colors = if (dark) {
        darkColors(
            primary = Gold, primaryVariant = GoldDim,
            background = BgDeep, surface = BgPanel,
            onPrimary = Color(0xFF1A1607), onBackground = Ink, onSurface = Ink,
            error = Red,
        )
    } else {
        lightColors(
            primary = GoldDim, primaryVariant = Gold,
            background = Color(0xFFF6F4EE), surface = Color(0xFFFFFFFF),
        )
    }

    MaterialTheme(colors = colors) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Surface(Modifier.fillMaxSize()) {
                if (controller == null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(copy.storageUnavailable, color = MaterialTheme.colors.error)
                            startupFailure.value?.let {
                                Text(it, fontSize = 12.sp, color = GoldSoft)
                            }
                        }
                    }
                    return@Surface
                }
                Row(Modifier.fillMaxSize()) {
                    Sidebar(
                        controller = controller,
                        copy = copy,
                        section = section,
                        onSelect = { section = it },
                        onToggleLanguage = { persian = !persian },
                        onCycleTheme = {
                            themePreference = when (themePreference) {
                                ThemePreference.SYSTEM -> ThemePreference.DARK
                                ThemePreference.DARK -> ThemePreference.LIGHT
                                ThemePreference.LIGHT -> ThemePreference.SYSTEM
                            }
                        },
                        themeLabel = when (themePreference) {
                            ThemePreference.SYSTEM -> copy.themeSystem
                            ThemePreference.DARK -> copy.themeDark
                            ThemePreference.LIGHT -> copy.themeLight
                        },
                    )
                    val bgBrush = if (dark) {
                        Brush.verticalGradient(listOf(BgDeep, Color(0xFF10131B)))
                    } else {
                        Brush.verticalGradient(listOf(MaterialTheme.colors.background, MaterialTheme.colors.background))
                    }
                    Box(Modifier.weight(1f).fillMaxHeight().background(bgBrush)) {
                        when (section) {
                            NavSection.HOME -> HomeScreen(controller, copy)
                            NavSection.SERVERS -> ServersScreen(controller, copy)
                            NavSection.SUBSCRIPTIONS -> SubscriptionsScreen(controller, copy)
                            NavSection.DIAGNOSTICS -> DiagnosticsScreen(controller, copy)
                            NavSection.SETTINGS -> SettingsScreen(controller, copy)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Sidebar(
    controller: DesktopVpnController,
    copy: NavCopy,
    section: NavSection,
    onSelect: (NavSection) -> Unit,
    onToggleLanguage: () -> Unit,
    onCycleTheme: () -> Unit,
    themeLabel: String,
) {
    val state = controller.connectionState.state
    Column(
        Modifier.width(215.dp).fillMaxHeight().background(BgPanel).padding(vertical = 18.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(bottom = 14.dp, start = 4.dp),
        ) {
            Image(painterResource("pvnetwork_logo.png"), null, Modifier.size(38.dp))
            Column {
                Column {
                    Text("PVNetwork", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Ink)
                    Text("v0.3.0", fontSize = 10.sp, color = GoldSoft)
                }
                Text(
                    if (controller.coreStatus.available) "Xray ${controller.coreStatus.version ?: ""}" else copy.coreMissing,
                    fontSize = 10.sp, color = InkMuted, maxLines = 1,
                )
            }
        }
        NavItem(copy.home, section == NavSection.HOME, state == ConnectionState.CONNECTED) { onSelect(NavSection.HOME) }
        NavItem(copy.servers, section == NavSection.SERVERS) { onSelect(NavSection.SERVERS) }
        NavItem(copy.subscriptions, section == NavSection.SUBSCRIPTIONS) { onSelect(NavSection.SUBSCRIPTIONS) }
        NavItem(copy.diagnostics, section == NavSection.DIAGNOSTICS) { onSelect(NavSection.DIAGNOSTICS) }
        NavItem(copy.settings, section == NavSection.SETTINGS) { onSelect(NavSection.SETTINGS) }
        Spacer(Modifier.weight(1f))
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).clickable(onClick = onToggleLanguage).padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(copy.language, fontSize = 12.sp, color = InkMuted)
            Text(if (copy.home == "اتصال") "EN" else "فا", color = Gold, fontWeight = FontWeight.Bold)
        }
        Row(
            Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).clickable(onClick = onCycleTheme).padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(copy.theme, fontSize = 12.sp, color = InkMuted)
            Text(themeLabel, fontSize = 12.sp, color = GoldSoft)
        }
    }
}

@Composable
private fun NavItem(label: String, selected: Boolean, online: Boolean = false, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) Gold.copy(alpha = 0.12f) else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        val tint = when {
            online -> Green
            selected -> Gold
            else -> InkMuted.copy(alpha = 0.6f)
        }
        NavIcon(kind = label, tint = tint)
        Text(
            label,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Ink else InkMuted,
        )
    }
}

@Composable
private fun NavIcon(kind: String, tint: Color) {
    Canvas(Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height
        val stroke = Stroke(width = w * 0.09f, cap = androidx.compose.ui.graphics.StrokeCap.Round)
        when (kind) {
            "Ø§ØªØµØ§Ù„", "Connect" -> {
                drawCircle(color = tint, radius = w * 0.38f, style = stroke)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.16f), androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f), stroke.width)
            }
            "Ø³Ø±ÙˆØ±Ù‡Ø§", "Servers" -> {
                drawRoundRect(tint, size = androidx.compose.ui.geometry.Size(w * 0.9f, h * 0.24f), topLeft = androidx.compose.ui.geometry.Offset(w * 0.05f, h * 0.08f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.08f), style = stroke)
                drawRoundRect(tint, size = androidx.compose.ui.geometry.Size(w * 0.9f, h * 0.24f), topLeft = androidx.compose.ui.geometry.Offset(w * 0.05f, h * 0.38f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.08f), style = stroke)
                drawRoundRect(tint, size = androidx.compose.ui.geometry.Size(w * 0.9f, h * 0.24f), topLeft = androidx.compose.ui.geometry.Offset(w * 0.05f, h * 0.68f), cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.08f), style = stroke)
            }
            "Ø§Ø´ØªØ±Ø§Ú©â€ŒÙ‡Ø§", "Subscriptions" -> {
                drawCircle(tint, radius = w * 0.22f, center = androidx.compose.ui.geometry.Offset(w * 0.3f, h * 0.32f), style = stroke)
                drawCircle(tint, radius = w * 0.22f, center = androidx.compose.ui.geometry.Offset(w * 0.7f, h * 0.68f), style = stroke)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.42f, h * 0.44f), androidx.compose.ui.geometry.Offset(w * 0.58f, h * 0.56f), stroke.width)
            }
            "Ú¯Ø²Ø§Ø±Ø´", "Diagnostics" -> {
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.06f, h * 0.5f), androidx.compose.ui.geometry.Offset(w * 0.32f, h * 0.5f), stroke.width)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.32f, h * 0.5f), androidx.compose.ui.geometry.Offset(w * 0.44f, h * 0.2f), stroke.width)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.44f, h * 0.2f), androidx.compose.ui.geometry.Offset(w * 0.58f, h * 0.8f), stroke.width)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.58f, h * 0.8f), androidx.compose.ui.geometry.Offset(w * 0.7f, h * 0.5f), stroke.width)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.7f, h * 0.5f), androidx.compose.ui.geometry.Offset(w * 0.94f, h * 0.5f), stroke.width)
            }
            else -> {
                drawCircle(tint, radius = w * 0.36f, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f), style = stroke)
                drawCircle(tint, radius = w * 0.13f, center = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.5f))
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.04f), androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.18f), stroke.width)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.82f), androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.96f), stroke.width)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.04f, h * 0.5f), androidx.compose.ui.geometry.Offset(w * 0.18f, h * 0.5f), stroke.width)
                drawLine(tint, androidx.compose.ui.geometry.Offset(w * 0.82f, h * 0.5f), androidx.compose.ui.geometry.Offset(w * 0.96f, h * 0.5f), stroke.width)
            }
        }
    }
}

@Composable
private fun SignalBars(millis: Long?, testing: Boolean, timeout: Boolean) {
    Canvas(Modifier.size(16.dp).padding(end = 2.dp)) {
        val w = size.width
        val h = size.height
        val levels = when {
            testing || millis == null && !timeout -> 0
            timeout -> 1
            millis == null -> 0
            millis < 200 -> 4
            millis < 500 -> 3
            millis < 1000 -> 2
            else -> 1
        }
        val color = when {
            timeout -> Red
            millis == null -> InkMuted.copy(alpha = 0.4f)
            millis < 300 -> Green
            millis < 1000 -> Amber
            else -> Red
        }
        val bars = 4
        for (i in 0 until bars) {
            val barH = h * (0.32f + 0.22f * i)
            val top = h - barH
            drawRoundRect(
                color = if (i < levels || (levels == 0 && millis == null && !timeout && !testing)) color.copy(alpha = 0.25f) else if (i < levels) color else color.copy(alpha = 0.22f),
                topLeft = androidx.compose.ui.geometry.Offset(w * 0.06f + i * w * 0.25f, top),
                size = androidx.compose.ui.geometry.Size(w * 0.17f, barH),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.05f),
            )
        }
    }
}

@Composable
private fun HomeScreen(controller: DesktopVpnController, copy: NavCopy) {
    val state = controller.connectionState.state
    val busy = state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    val selected = controller.selectedProfileId?.let { id -> controller.profilesState.firstOrNull { it.id == id } }
    val now = produceState(System.currentTimeMillis(), state) {
        if (state == ConnectionState.CONNECTED) {
            while (true) {
                delay(1000)
                value = System.currentTimeMillis()
            }
        }
    }
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(16.dp))
        ConnectOrb(
            state = state,
            label = when {
                state == ConnectionState.CONNECTED -> copy.connected
                state == ConnectionState.ERROR -> copy.error
                busy -> copy.connecting
                else -> copy.disconnected
            },
            enabled = controller.selectedProfileId != null && controller.coreStatus.available,
            onConnect = { controller.connect() },
            onDisconnect = { controller.disconnect() },
        )
        Spacer(Modifier.height(30.dp))
        Card(
            Modifier.width(430.dp),
            backgroundColor = CardBg,
            shape = RoundedCornerShape(16.dp),
            elevation = 0.dp,
        ) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(copy.currentServer, fontSize = 12.sp, color = InkMuted)
                    Text(
                        selected?.let { countryFlag(it.displayName) + "  " + it.displayName } ?: copy.noServer,
                        fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Ink,
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(copy.duration, fontSize = 12.sp, color = InkMuted)
                    Text(
                        controller.connectedSinceMillis?.let { start -> formatDuration(now.value - start) } ?: "—",
                        fontSize = 13.sp, fontWeight = FontWeight.SemiBold,
                        color = if (state == ConnectionState.CONNECTED) Green else InkMuted,
                    )
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(copy.latencyOf, fontSize = 12.sp, color = InkMuted)
                    val cell = selected?.let { controller.latencies[it.id.value] }
                    LatencyText(cell, copy)
                }
                if (state == ConnectionState.CONNECTED) {
                    Divider(color = Color(0xFF2A3046))
                    Text(copy.tunnelNote, fontSize = 11.sp, color = GoldSoft)
                }
            }
        }
    }
}

@Composable
private fun ConnectOrb(
    state: ConnectionState,
    label: String,
    enabled: Boolean,
    onConnect: () -> Unit,
    onDisconnect: () -> Unit,
) {
    val busy = state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    val connected = state == ConnectionState.CONNECTED
    val pulse = rememberInfiniteTransition()
    val ringAlpha by pulse.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(tween(1400), RepeatMode.Reverse),
    )
    val ringColor = when {
        connected -> Green
        state == ConnectionState.ERROR -> Red
        busy -> Gold
        else -> GoldDim
    }
    Box(
        Modifier.size(230.dp).clickable(enabled = enabled || connected || busy) {
            if (connected || busy) onDisconnect() else onConnect()
        },
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.size(230.dp)) {
            val stroke = Stroke(width = 14f)
            val glowColor = if (connected) Green else ringColor
            drawCircle(color = glowColor.copy(alpha = 0.10f), radius = size.minDimension / 2 - 4f)
            drawCircle(color = glowColor.copy(alpha = 0.05f), radius = size.minDimension / 2 + 8f)
            drawCircle(
                color = ringColor.copy(alpha = if (busy) ringAlpha else 0.32f),
                radius = size.minDimension / 2 - 24f,
                style = stroke,
            )
            drawCircle(
                color = if (connected) Green.copy(alpha = 0.9f) else ringColor.copy(alpha = 0.16f),
                radius = size.minDimension / 2 - 48f,
            )
            val c = size.minDimension / 2
            val r = size.minDimension * 0.16f
            val glyph = if (connected) Color(0xFF0B2010) else Ink.copy(alpha = 0.9f)
            drawArc(
                color = glyph,
                startAngle = -215f,
                sweepAngle = 250f,
                useCenter = false,
                topLeft = androidx.compose.ui.geometry.Offset(c - r, c - r - size.minDimension * 0.10f),
                size = androidx.compose.ui.geometry.Size(r * 2, r * 2),
                style = Stroke(width = size.minDimension * 0.028f, cap = androidx.compose.ui.graphics.StrokeCap.Round),
            )
            drawLine(
                glyph,
                androidx.compose.ui.geometry.Offset(c, c - size.minDimension * 0.16f),
                androidx.compose.ui.geometry.Offset(c, c + size.minDimension * 0.04f),
                size.minDimension * 0.028f,
            )
        }
        Text(
            label,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 56.dp),
            color = if (connected) Color(0xFF0B2010) else Ink,
        )
    }
}

@Composable
private fun ServersScreen(controller: DesktopVpnController, copy: NavCopy) {
    var showAdd by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    val busy = controller.connectionState.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(copy.servers, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink)
            Text(copy.serversN.replace("{n}", controller.profilesState.size.toString()), fontSize = 12.sp, color = InkMuted)
            Spacer(Modifier.weight(1f))
            OutlinedButton(
                onClick = { controller.testAllLatency() },
                enabled = !busy && controller.profilesState.isNotEmpty(),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = CardBg, contentColor = GoldSoft),
            ) { Text(copy.testAll) }
            Button(
                onClick = { showAdd = true },
                enabled = !busy,
                colors = ButtonDefaults.buttonColors(backgroundColor = Gold, contentColor = Color(0xFF1A1607)),
            ) { Text(copy.addProfile) }
        }
        statusMessage?.let {
            LaunchedEffect(it) { delay(3500); statusMessage = null }
            Text(it, fontSize = 12.sp, color = GoldSoft)
        }
        if (controller.profilesState.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(copy.subsEmpty, color = InkMuted, fontSize = 13.sp)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(controller.profilesState, key = { it.id.value }) { profile ->
                    ServerCard(profile, controller, copy, busy)
                }
            }
        }
    }
    if (showAdd) {
        AddProfilesSheet(
            copy,
            onDismiss = { showAdd = false },
            onImport = { text ->
                when (val outcome = controller.importShareLinks(text)) {
                    is ImportOutcome.Success -> {
                        statusMessage = copy.serversN.replace("{n}", outcome.profiles.size.toString())
                        showAdd = false
                    }
                    is ImportOutcome.Failure -> statusMessage = outcome.reason
                }
            },
        )
    }
}

@Composable
private fun ServerCard(profile: PVProfile, controller: DesktopVpnController, copy: NavCopy, busy: Boolean) {
    val selected = controller.selectedProfileId == profile.id
    val cell = controller.latencies[profile.id.value]
    Card(
        Modifier.fillMaxWidth().clickable(enabled = !busy) { controller.select(profile.id) },
        backgroundColor = if (selected) CardBgHover else CardBg,
        shape = RoundedCornerShape(14.dp),
        elevation = 0.dp,
    ) {
        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(countryFlag(profile.displayName), fontSize = 22.sp)
            Column(Modifier.weight(1f)) {
                Text(profile.displayName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Ink, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Badge(profile.protocolId)
                    Ltr("${profile.endpoint.host}:${profile.endpoint.port}", 11.sp, InkMuted)
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(controller.subscriptionNameOf(profile) ?: copy.manual, fontSize = 10.sp, color = InkMuted)
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    if (cell?.testing == true) {
                        CircularProgressIndicator(Modifier.size(14.dp), strokeWidth = 2.dp)
                    } else {
                        SignalBars(millis = cell?.millis, testing = false, timeout = cell?.timeout == true)
                        LatencyText(cell, copy)
                    }
                }
            }
            OutlinedButton(
                enabled = !busy,
                onClick = { controller.deleteProfile(profile.id) },
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                modifier = Modifier.height(26.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent, contentColor = InkMuted),
            ) { Text(copy.delete, fontSize = 11.sp) }
        }
    }
}

@Composable
private fun LatencyText(cell: LatencyCell?, copy: NavCopy) {
    when {
        cell == null -> Text("—", fontSize = 13.sp, color = InkMuted)
        cell.timeout -> Text(copy.timeout, fontSize = 12.sp, color = Red, fontWeight = FontWeight.Bold)
        else -> {
            val ms = cell.millis ?: return
            val color = when {
                ms < 300 -> Green
                ms < 1000 -> Amber
                else -> Red
            }
            Ltr("$ms ms", 13.sp, color, FontWeight.Bold)
        }
    }
}

@Composable
private fun Badge(text: String) {
    Box(Modifier.clip(RoundedCornerShape(6.dp)).background(Gold.copy(alpha = 0.16f)).padding(horizontal = 6.dp, vertical = 1.dp)) {
        Text(text, fontSize = 10.sp, color = GoldSoft)
    }
}

@Composable
private fun AddProfilesSheet(copy: NavCopy, onDismiss: () -> Unit, onImport: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    Card(
        Modifier.fillMaxWidth().padding(24.dp),
        backgroundColor = CardBg, shape = RoundedCornerShape(16.dp), elevation = 8.dp,
    ) {
        Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(copy.addProfile, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Ink)
            Text(copy.pasteHint, fontSize = 11.sp, color = InkMuted)
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = BgPanel,
                    textColor = Ink,
                    cursorColor = Gold,
                    focusedIndicatorColor = Gold,
                    unfocusedIndicatorColor = Color(0xFF3A4054),
                ),
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    enabled = text.isNotBlank(),
                    onClick = { onImport(text) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Gold, contentColor = Color(0xFF1A1607)),
                ) { Text(copy.add) }
                OutlinedButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = InkMuted),
                ) { Text(copy.close) }
            }
        }
    }
}

@Composable
private fun SubscriptionsScreen(controller: DesktopVpnController, copy: NavCopy) {
    var url by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    val busy = controller.connectionState.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(copy.subscriptions, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink)
        Card(backgroundColor = CardBg, shape = RoundedCornerShape(14.dp), elevation = 0.dp) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = name, onValueChange = { name = it },
                        label = { Text(copy.subName, color = InkMuted) },
                        modifier = Modifier.width(180.dp), singleLine = true,
                        colors = darkFieldColors(),
                    )
                    OutlinedTextField(
                        value = url, onValueChange = { url = it },
                        label = { Text(copy.subUrl, color = InkMuted) },
                        modifier = Modifier.weight(1f), singleLine = true,
                        colors = darkFieldColors(),
                    )
                    Button(
                        enabled = url.isNotBlank() && !busy,
                        onClick = {
                            when (val outcome = controller.addSubscription(url, name)) {
                                is SubscriptionOutcome.Success -> {
                                    statusMessage = copy.serversN.replace("{n}", outcome.added.toString())
                                    url = ""; name = ""
                                }
                                is SubscriptionOutcome.Failure -> statusMessage = outcome.reason
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Gold, contentColor = Color(0xFF1A1607)),
                    ) { Text(copy.addSub) }
                }
                statusMessage?.let { Text(it, fontSize = 12.sp, color = GoldSoft) }
            }
        }
        if (controller.subscriptionsState.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(copy.subsEmpty, color = InkMuted)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(controller.subscriptionsState, key = { it.id }) { sub ->
                    val count = controller.profilesState.count {
                        controller.subscriptionNameOf(it) == sub.name
                    }
                    Card(backgroundColor = CardBg, shape = RoundedCornerShape(14.dp), elevation = 0.dp) {
                        Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Column(Modifier.weight(1f)) {
                                Text(sub.name, fontWeight = FontWeight.SemiBold, color = Ink, fontSize = 14.sp)
                                Ltr(sub.url, 11.sp, InkMuted)
                                Text(
                                    "${sub.lastStatus ?: copy.neverUpdated} · ${copy.serversN.replace("{n}", count.toString())}",
                                    fontSize = 11.sp, color = InkMuted,
                                )
                            }
                            OutlinedButton(
                                enabled = !busy,
                                onClick = {
                                    when (val outcome = controller.updateSubscription(sub.id)) {
                                        is SubscriptionOutcome.Success -> statusMessage = copy.serversN.replace("{n}", outcome.added.toString())
                                        is SubscriptionOutcome.Failure -> statusMessage = outcome.reason
                                    }
                                },
                                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = CardBgHover, contentColor = GoldSoft),
                            ) { Text(copy.update) }
                            OutlinedButton(
                                enabled = !busy,
                                onClick = { controller.removeSubscription(sub.id) },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Red),
                            ) { Text(copy.delete) }
                        }
                    }
                }
            }
            OutlinedButton(
                enabled = !busy,
                onClick = { controller.updateAllSubscriptions() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldSoft),
            ) { Text(copy.updateAll) }
        }
    }
}

@Composable
private fun darkFieldColors() = TextFieldDefaults.outlinedTextFieldColors(
    textColor = Ink,
    cursorColor = Gold,
    focusedBorderColor = Gold,
    unfocusedBorderColor = Color(0xFF3A4054),
    backgroundColor = Color.Transparent,
)

@Composable
private fun DiagnosticsScreen(controller: DesktopVpnController, copy: NavCopy) {
    val events = controller.diagnostics.map(DiagnosticSanitizer::sanitize)
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(copy.diagnostics, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink)
        if (events.isEmpty()) {
            Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("—", color = InkMuted)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(events.asReversed()) { event ->
                    Card(backgroundColor = CardBg, shape = RoundedCornerShape(12.dp), elevation = 0.dp) {
                        Column(Modifier.padding(12.dp)) {
                            Ltr("${event.severity.name} · ${event.subsystem} · ${event.code}", 11.sp, GoldSoft)
                            event.metadata.forEach { (k, v) -> Ltr("$k=$v", 11.sp, InkMuted) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(controller: DesktopVpnController, copy: NavCopy) {
    Column(
        Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(copy.settings, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink)
        SettingsCard(copy.sysProxy, copy.sysProxyHint) {
            Switch(
                checked = controller.useSystemProxyState,
                onCheckedChange = { controller.useSystemProxyState = it },
                colors = SwitchDefaults.colors(checkedThumbColor = Gold, checkedTrackColor = GoldDim),
            )
        }
        SettingsCard(copy.ports, "SOCKS 127.0.0.1:10808 · HTTP 127.0.0.1:10809") {}
        SettingsCard(
            copy.core,
            if (controller.coreStatus.available) "Xray-core ${controller.coreStatus.version ?: "?"} (MPL-2.0)" else copy.coreMissing,
        ) {}
        SettingsCard(copy.aboutTitle, copy.aboutBody) {}
        SettingsCard(copy.privacyTitle, copy.privacyBody) {}
    }
}

@Composable
private fun SettingsCard(title: String, body: String, trailing: @Composable () -> Unit = {}) {
    Card(backgroundColor = CardBg, shape = RoundedCornerShape(14.dp), elevation = 0.dp) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Ink)
                Text(body, fontSize = 11.sp, color = InkMuted)
            }
            trailing()
        }
    }
}

@Composable
private fun Ltr(text: String, fontSize: TextUnit, color: Color, weight: FontWeight = FontWeight.Normal) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Text(text, fontSize = fontSize, color = color, fontWeight = weight)
    }
}

private fun formatDuration(millis: Long): String {
    val totalSeconds = millis / 1000
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return String.format("%02d:%02d:%02d", h, m, s)
}

private fun countryFlag(name: String): String {
    val lower = name.lowercase()
    val pairs = listOf(
        "japan" to "JP", "日本" to "JP", "tokyo" to "JP", "osaka" to "JP", "yokohama" to "JP",
        "germany" to "DE", "deutsch" to "DE", "frankfurt" to "DE", "nuremberg" to "DE", "berlin" to "DE", "dusseldorf" to "DE",
        "netherlands" to "NL", "amsterdam" to "NL",
        "usa" to "US", "united states" to "US", "america" to "US", "new york" to "US", "los angeles" to "US", "seattle" to "US", "chicago" to "US", "dallas" to "US", "miami" to "US",
        "uk" to "GB", "united kingdom" to "GB", "london" to "GB", "england" to "GB", "britain" to "GB",
        "france" to "FR", "paris" to "FR", "marseille" to "FR",
        "turkey" to "TR", "istanbul" to "TR", "ankara" to "TR",
        "singapore" to "SG",
        "emirates" to "AE", "dubai" to "AE",
        "canada" to "CA", "toronto" to "CA", "montreal" to "CA",
        "austria" to "AT", "vienna" to "AT",
        "finland" to "FI", "helsinki" to "FI",
        "sweden" to "SE", "stockholm" to "SE",
        "norway" to "NO", "oslo" to "NO",
        "switzerland" to "CH", "zurich" to "CH",
        "poland" to "PL", "warsaw" to "PL",
        "spain" to "ES", "madrid" to "ES",
        "italy" to "IT", "milan" to "IT",
        "korea" to "KR", "seoul" to "KR",
        "hongkong" to "HK", "hong kong" to "HK",
        "taiwan" to "TW", "taipei" to "TW",
        "india" to "IN", "mumbai" to "IN",
        "brazil" to "BR", "australia" to "AU", "sydney" to "AU",
        "russia" to "RU", "moscow" to "RU", "kazakhstan" to "KZ",
        "iran" to "IR", "tehran" to "IR",
    )
    val code = pairs.firstOrNull { lower.contains(it.first) }?.second ?: return "🌐"
    return code.map { Character.toChars(0x1F1E6 + (it - 'A'))[0] }.joinToString("")
}
