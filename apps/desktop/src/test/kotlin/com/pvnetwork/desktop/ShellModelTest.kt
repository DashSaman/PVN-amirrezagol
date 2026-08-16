package com.pvnetwork.desktop

import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.diagnostics.DiagnosticEvent
import com.pvnetwork.core.diagnostics.DiagnosticSeverity
import com.pvnetwork.core.diagnostics.DiagnosticSanitizer
import com.pvnetwork.core.i18n.TextDirection
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ShellModelTest {
    @Test
    fun languageToggleKeepsEnglishAndPersianFirstClass() {
        assertEquals(AppLanguage.PERSIAN, AppLanguage.ENGLISH.toggle())
        assertEquals(AppLanguage.ENGLISH, AppLanguage.PERSIAN.toggle())
        assertEquals(TextDirection.RTL, AppLanguage.PERSIAN.locale.direction)
        assertTrue(shellCopy(AppLanguage.ENGLISH).profiles.isNotBlank())
        assertTrue(shellCopy(AppLanguage.PERSIAN).profiles.isNotBlank())
    }

    @Test
    fun themePreferenceCyclesAndSystemDelegatesToOsSignal() {
        assertEquals(ThemePreference.LIGHT, ThemePreference.SYSTEM.next())
        assertEquals(ThemePreference.DARK, ThemePreference.LIGHT.next())
        assertEquals(ThemePreference.SYSTEM, ThemePreference.DARK.next())
        assertEquals(true, ThemePreference.SYSTEM.resolve(systemDark = true))
        assertEquals(false, ThemePreference.SYSTEM.resolve(systemDark = false))
        assertEquals(false, ThemePreference.LIGHT.resolve(systemDark = true))
        assertEquals(true, ThemePreference.DARK.resolve(systemDark = false))
    }

    @Test
    fun defaultShellDoesNotInventProfilesConnectionsOrDiagnostics() {
        val state = DesktopShellState()
        assertTrue(state.profiles.isEmpty())
        assertEquals(ConnectionState.DISCONNECTED, state.connection.state)
        assertTrue(state.diagnostics.isEmpty())
    }

    @Test
    fun diagnosticsAreSanitizedBeforeShellPresentation() {
        val state = DesktopShellState(
            diagnostics = listOf(
                DiagnosticEvent(
                    timestampEpochMillis = 1,
                    severity = DiagnosticSeverity.ERROR,
                    subsystem = "adapter",
                    code = "AUTH_FAILURE",
                    metadata = mapOf("token" to "must-not-leak", "endpoint" to "example.invalid"),
                )
            )
        )
        val event = state.sanitizedDiagnostics().single()
        assertEquals(DiagnosticSanitizer.REDACTED, event.metadata["token"])
        assertEquals("example.invalid", event.metadata["endpoint"])
    }
}
