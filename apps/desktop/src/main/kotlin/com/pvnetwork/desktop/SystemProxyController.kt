package com.pvnetwork.desktop

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.Advapi32Util
import com.sun.jna.platform.win32.WinReg
import com.sun.jna.win32.StdCallLibrary

/**
 * Windows per-user system proxy (WinINET). Sets ProxyEnable/ProxyServer and
 * notifies listeners via InternetSetOption so browsers pick the change up
 * without a reboot. Previous values are snapshotted and restored on
 * disconnect or JVM shutdown.
 */
class SystemProxyController {

    private val isWindows = System.getProperty("os.name").orEmpty()
        .lowercase().contains("windows")

    val supported: Boolean = isWindows

    private val snapshot = java.util.concurrent.atomic.AtomicReference<ProxySnapshot?>(null)

    val isActive: Boolean get() = snapshot.get() != null

    fun enable(proxyHost: String, proxyPort: Int): Boolean {
        if (!isWindows) return false
        val saved = snapshot.get() ?: readSnapshot()
        if (saved == null) return false
        if (!snapshot.compareAndSet(null, saved)) return false
        try {
            Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, KEY, "ProxyEnable", 1)
            Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, KEY, "ProxyServer", "$proxyHost:$proxyPort")
            saved.autoConfigUrl?.let {
                try {
                    Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER, KEY, "AutoConfigURL")
                } catch (_: Throwable) {
                    // best effort; a stale PAC only reduces effect, it never corrupts state
                }
            }
            notifySettingsChanged()
            return true
        } catch (_: Throwable) {
            snapshot.set(null)
            return false
        }
    }

    fun restore() {
        val saved = snapshot.getAndSet(null) ?: return
        if (!isWindows) return
        try {
            Advapi32Util.registrySetIntValue(WinReg.HKEY_CURRENT_USER, KEY, "ProxyEnable", saved.enable)
            if (saved.server != null) {
                Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, KEY, "ProxyServer", saved.server)
            }
            if (saved.autoConfigUrl != null) {
                Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, KEY, "AutoConfigURL", saved.autoConfigUrl)
            }
            notifySettingsChanged()
        } catch (_: Throwable) {
            // never crash the disconnect path; the snapshot stays restorable manually
        }
    }

    fun installShutdownRestore() {
        Runtime.getRuntime().addShutdownHook(Thread { restore() })
    }

    private fun readSnapshot(): ProxySnapshot? = try {
        ProxySnapshot(
            enable = Advapi32Util.registryGetIntValue(WinReg.HKEY_CURRENT_USER, KEY, "ProxyEnable"),
            server = readOptionalString("ProxyServer"),
            autoConfigUrl = readOptionalString("AutoConfigURL"),
        )
    } catch (_: Throwable) {
        ProxySnapshot(enable = 0, server = null, autoConfigUrl = null)
    }

    private fun readOptionalString(value: String): String? = try {
        Advapi32Util.registryGetStringValue(WinReg.HKEY_CURRENT_USER, KEY, value)
    } catch (_: Throwable) {
        null
    }

    private fun notifySettingsChanged() {
        try {
            val wininet = Native.load("wininet", WininetLibrary::class.java)
            wininet.InternetSetOption(null, INTERNET_OPTION_SETTINGS_CHANGED, null, 0)
            wininet.InternetSetOption(null, INTERNET_OPTION_REFRESH, null, 0)
        } catch (_: Throwable) {
            // registry values remain authoritative; applications pick them up on restart
        }
    }

    private data class ProxySnapshot(val enable: Int, val server: String?, val autoConfigUrl: String?)

    private interface WininetLibrary : StdCallLibrary {
        fun InternetSetOption(
            hInternet: Pointer?,
            dwOption: Int,
            lpBuffer: Pointer?,
            dwBufferLength: Int,
        ): Boolean
    }

    companion object {
        private const val KEY = "Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings"
        private const val INTERNET_OPTION_SETTINGS_CHANGED = 39
        private const val INTERNET_OPTION_REFRESH = 95
    }
}
