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

data class DesktopShellState(
    val profiles: List<PVProfile> = emptyList(),
    val connection: ConnectionSnapshot = ConnectionSnapshot(ConnectionState.DISCONNECTED),
    val diagnostics: List<DiagnosticEvent> = emptyList(),
) {
    fun sanitizedDiagnostics(): List<DiagnosticEvent> =
        diagnostics.map(DiagnosticSanitizer::sanitize)
}
