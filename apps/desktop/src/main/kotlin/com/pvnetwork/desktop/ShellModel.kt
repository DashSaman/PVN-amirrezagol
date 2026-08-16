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
)

fun shellCopy(language: AppLanguage): ShellCopy = when (language) {
    AppLanguage.ENGLISH -> ShellCopy(
        profiles = "Profiles",
        noProfiles = "No profiles yet",
        connection = "Connection",
        diagnostics = "Diagnostics",
        noDiagnostics = "No diagnostic events",
        language = "Language",
        theme = "Theme",
        protocol = "Protocol",
        endpoint = "Endpoint",
    )

    AppLanguage.PERSIAN -> ShellCopy(
        profiles = "پروفایل‌ها",
        noProfiles = "هنوز پروفایلی وجود ندارد",
        connection = "وضعیت اتصال",
        diagnostics = "گزارش فنی",
        noDiagnostics = "رویداد تشخیصی وجود ندارد",
        language = "زبان",
        theme = "پوسته",
        protocol = "پروتکل",
        endpoint = "مقصد",
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
