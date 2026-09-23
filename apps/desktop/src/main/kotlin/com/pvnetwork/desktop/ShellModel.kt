package com.pvnetwork.desktop

import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.diagnostics.DiagnosticEvent
import com.pvnetwork.core.diagnostics.DiagnosticSanitizer
import com.pvnetwork.core.i18n.PVLocales
import com.pvnetwork.core.i18n.SupportedLocale
import com.pvnetwork.core.profile.PVProfile

enum class AppLanguage(val locale: SupportedLocale) {
    ENGLISH(PVLocales.ENGLISH),
    PERSIAN(PVLocales.PERSIAN),
    ;

    fun toggle(): AppLanguage = if (this == ENGLISH) PERSIAN else ENGLISH
}

enum class ThemePreference {
    SYSTEM,
    LIGHT,
    DARK,
    ;

    fun next(): ThemePreference = when (this) {
        SYSTEM -> LIGHT
        LIGHT -> DARK
        DARK -> SYSTEM
    }

    fun resolve(systemDark: Boolean): Boolean = when (this) {
        SYSTEM -> systemDark
        LIGHT -> false
        DARK -> true
    }
}

data class ShellCopy(
    val profiles: String,
    val noProfiles: String,
    val connection: String,
    val diagnostics: String,
    val noDiagnostics: String,
    val language: String,
    val theme: String,
    val protocol: String,
    val endpoint: String,
    val pasteLink: String,
    val addProfile: String,
    val delete: String,
    val imported: String,
    val importFailed: String,
    val connect: String,
    val disconnect: String,
    val stateDisconnected: String,
    val statePreparing: String,
    val stateConnecting: String,
    val stateConnected: String,
    val stateDisconnecting: String,
    val stateError: String,
    val coreReady: String,
    val coreMissing: String,
    val storageUnavailable: String,
)

fun shellCopy(language: AppLanguage): ShellCopy = when (language) {
    AppLanguage.ENGLISH -> ShellCopy(
        profiles = "Profiles",
        noProfiles = "No profiles yet — paste a VLESS share link above",
        connection = "Connection",
        diagnostics = "Diagnostics",
        noDiagnostics = "No diagnostic events",
        language = "Language",
        theme = "Theme",
        protocol = "Protocol",
        endpoint = "Endpoint",
        pasteLink = "vless://…",
        addProfile = "Add",
        delete = "Delete",
        imported = "Profile \"{name}\" imported",
        importFailed = "Import failed",
        connect = "Connect",
        disconnect = "Disconnect",
        stateDisconnected = "Disconnected",
        statePreparing = "Preparing…",
        stateConnecting = "Connecting…",
        stateConnected = "Connected",
        stateDisconnecting = "Disconnecting…",
        stateError = "Error",
        coreReady = "Engine core ready ({version})",
        coreMissing = "Xray core not found — set PVNETWORK_XRAY_EXECUTABLE or copy xray.exe to %LOCALAPPDATA%\\pvnetwork\\core",
        storageUnavailable = "Local storage could not be initialized",
    )

    AppLanguage.PERSIAN -> ShellCopy(
        profiles = "پروفایل‌ها",
        noProfiles = "هنوز پروفایلی نیست — لینک اشتراک VLESS را بالا بچسبانید",
        connection = "وضعیت اتصال",
        diagnostics = "گزارش فنی",
        noDiagnostics = "رویداد تشخیصی وجود ندارد",
        language = "زبان",
        theme = "پوسته",
        protocol = "پروتکل",
        endpoint = "مقصد",
        pasteLink = "vless://…",
        addProfile = "افزودن",
        delete = "حذف",
        imported = "پروفایل «{name}» اضافه شد",
        importFailed = "افزودن ناموفق بود",
        connect = "اتصال",
        disconnect = "قطع اتصال",
        stateDisconnected = "قطع است",
        statePreparing = "در حال آماده‌سازی…",
        stateConnecting = "در حال برقراری…",
        stateConnected = "متصل شد",
        stateDisconnecting = "در حال قطع…",
        stateError = "خطا",
        coreReady = "هسته موتور آماده است ({version})",
        coreMissing = "هسته Xray پیدا نشد — متغیر PVNETWORK_XRAY_EXECUTABLE را تنظیم یا xray.exe را در %LOCALAPPDATA%\\pvnetwork\\core قرار دهید",
        storageUnavailable = "ذخیره‌سازی محلی مقداردهی نشد",
    )
}

data class DesktopShellState(
    val profiles: List<PVProfile> = emptyList(),
    val connection: ConnectionSnapshot = ConnectionSnapshot(ConnectionState.DISCONNECTED),
    val diagnostics: List<DiagnosticEvent> = emptyList(),
) {
    fun sanitizedDiagnostics(): List<DiagnosticEvent> =
        diagnostics.map(DiagnosticSanitizer::sanitize)
}
