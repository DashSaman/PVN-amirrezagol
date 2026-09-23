package com.pvnetwork.client

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.net.IpPrefix
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import android.system.Os
import com.pvnetwork.core.connection.ConnectionSnapshot
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.profile.PVProfile
import hev.htproxy.TProxyService
import com.pvnetwork.engine.xray.XrayAdapter
import com.pvnetwork.engine.xray.XrayClientConfig
import com.pvnetwork.engine.xray.XrayRuntimeDescriptor
import com.pvnetwork.engine.xray.XrayRuntimeFactory
import com.pvnetwork.core.adapter.PreparedConnection
import com.pvnetwork.core.profile.PVProfile as Profile
import java.io.File
import java.net.InetAddress
import java.nio.charset.StandardCharsets
import java.nio.file.Files

/**
 * PVNetwork Android VPN service.
 *
 * Data path: apps -> TUN (this service) -> hev-socks5-tunnel (JNI, MIT) ->
 * local Xray-core SOCKS inbound (exec'd process, MPL-2.0) -> configured
 * VLESS/VMess/Trojan/Shadowsocks outbound. The engine's own upstream socket
 * is excluded from the VPN routes so it cannot loop back into the tunnel.
 */
class PvVpnService : VpnService() {

    private var engineProcess: Process? = null
    private var tunnelFd: ParcelFileDescriptor? = null
    private var running = false

    override fun onCreate() {
        super.onCreate()
        running = TProxyService.TProxyIsRunning()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP -> {
                stopTunnel()
                stopSelf()
                return START_NOT_STICKY
            }
            else -> {
                val profileId = intent?.getStringExtra(EXTRA_PROFILE_ID)
                if (profileId == null) {
                    VpnHub.publish(failure("ANDROID_NO_PROFILE"))
                    stopSelf()
                    return START_NOT_STICKY
                }
                startInForeground()
                Thread({ connect(profileId) }, "pvnetwork-vpn-connect").start()
                return START_STICKY
            }
        }
    }

    override fun onDestroy() {
        stopTunnel()
        super.onDestroy()
    }

    private fun connect(profileId: String) {
        VpnHub.publish(ConnectionSnapshot(ConnectionState.PREPARING, XrayAdapter.ADAPTER_ID))
        val secrets = AndroidSecretStore(this)
        val profiles = AndroidProfileStore(this)
        val profile = profiles.find(com.pvnetwork.core.profile.ProfileId(profileId))
        if (profile == null) {
            VpnHub.publish(failure("ANDROID_PROFILE_MISSING"))
            stopSelf()
            return
        }

        val adapter = XrayAdapter(ValidationOnlyXrayRuntime())
        val validation = adapter.validate(profile)
        if (!validation.isValid) {
            VpnHub.publish(failure(validation.issues.firstOrNull()?.code ?: "ANDROID_PROFILE_INVALID"))
            stopSelf()
            return
        }

        val engine = engineBinary() ?: run {
            VpnHub.publish(failure("ANDROID_ENGINE_BINARY_MISSING"))
            stopSelf()
            return
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            VpnHub.publish(failure("ANDROID_REQUIRES_API_29"))
            stopSelf()
            return
        }

        VpnHub.publish(ConnectionSnapshot(ConnectionState.CONNECTING, XrayAdapter.ADAPTER_ID))

        try {
            val serverAddress = InetAddress.getByName(profile.endpoint.host)
            val fd = Builder()
                .setSession("PVNetwork")
                .setMtu(MTU)
                .addAddress(VPN_ADDRESS_V4, 30)
                .addAddress(VPN_ADDRESS_V6, 126)
                .addRoute(DEFAULT_ROUTE_V4, 0)
                .addRoute(DEFAULT_ROUTE_V6, 0)
                .addDnsServer(DNS_PRIMARY)
                .addDnsServer(DNS_SECONDARY)
                .excludeRoute(IpPrefix(serverAddress, if (serverAddress.address.size == 4) 32 else 128))
                .setBlocking(false)
                .establish()
                ?: run {
                    VpnHub.publish(failure("ANDROID_VPN_PERMISSION_DENIED"))
                    stopSelf()
                    return
                }
            tunnelFd = fd

            val identityRef = profile.secretRefs["xray.vless.identity"]
                ?: profile.secretRefs["xray.vmess.identity"]
                ?: profile.secretRefs["xray.trojan.password"]
                ?: profile.secretRefs["xray.shadowsocks.password"]
            if (identityRef == null) {
                VpnHub.publish(failure("ANDROID_CREDENTIAL_MISSING"))
                stopSelf()
                return
            }
            val configJson = secrets.withSecret(identityRef) { credential ->
                XrayClientConfig.build(
                    profile = profile,
                    credential = credential,
                    ports = XrayClientConfig.InboundPorts(SOCKS_PORT, HTTP_PORT),
                )
            }
            if (configJson == null) {
                VpnHub.publish(failure("ANDROID_CREDENTIAL_UNREADABLE"))
                stopSelf()
                return
            }

            val engineDir = File(filesDir, "engine").apply { mkdirs() }
            val engineConfig = File(engineDir, "config.json")
            Files.write(engineConfig.toPath(), configJson.toByteArray(StandardCharsets.UTF_8))

            val process = ProcessBuilder(engine.absolutePath, "run", "-c", engineConfig.absolutePath)
                .redirectErrorStream(true)
                .start()
            engineProcess = process
            watchEngine(process)

            val hevConfig = File(engineDir, "tunnel.yml")
            hevConfig.writeText(hevTunnelConfig())

            val started = try {
                TProxyService.TProxyStartService(hevConfig.absolutePath, fd.fd)
            } catch (_: Throwable) {
                false
            }
            if (!started) {
                stopTunnel()
                VpnHub.publish(failure("ANDROID_TUN_STACK_FAILED"))
                stopSelf()
                return
            }
            running = true
            VpnHub.publish(ConnectionSnapshot(ConnectionState.CONNECTED, XrayAdapter.ADAPTER_ID))
        } catch (failure: Throwable) {
            stopTunnel()
            VpnHub.publish(failure("ANDROID_CONNECT_ERROR:${failure.message ?: "unknown"}"))
            stopSelf()
        }
    }

    private fun watchEngine(process: Process) {
        Thread({
            runCatching {
                process.inputStream.bufferedReader().forEachLine { _ -> }
            }
            runCatching { process.waitFor() }
            if (running) {
                VpnHub.publish(failure("ANDROID_ENGINE_EXITED"))
            }
        }, "pvnetwork-engine-watch").start()
    }

    private fun stopTunnel() {
        running = false
        runCatching { TProxyService.TProxyStopService() }
        engineProcess?.let { process ->
            runCatching {
                process.destroy()
                if (!process.waitFor(3, java.util.concurrent.TimeUnit.SECONDS)) {
                    process.destroyForcibly()
                }
            }
        }
        engineProcess = null
        tunnelFd?.let { runCatching { it.close() } }
        tunnelFd = null
        VpnHub.publish(ConnectionSnapshot(ConnectionState.DISCONNECTED, XrayAdapter.ADAPTER_ID))
    }

    private fun startInForeground() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(
            NotificationChannel(CHANNEL_ID, "PVNetwork VPN", NotificationManager.IMPORTANCE_LOW),
        )
        val launch = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE,
        )
        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("PVNetwork")
            .setContentText("VPN is active")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(launch)
            .setOngoing(true)
            .build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE)
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun failure(code: String): ConnectionSnapshot =
        ConnectionSnapshot(ConnectionState.ERROR, XrayAdapter.ADAPTER_ID, code)

    private fun hevTunnelConfig(): String = """
        tunnel:
          name: pvnetwork-tun
          mtu: $MTU
          ipv4: $VPN_ADDRESS_V4
          ipv6: '$VPN_ADDRESS_V6'
        socks5:
          port: $SOCKS_PORT
          address: 127.0.0.1
          udp: 'udp'
        misc:
          log-level: warn
    """.trimIndent()

    companion object {
        const val EXTRA_PROFILE_ID = "profileId"
        const val ACTION_STOP = "com.pvnetwork.client.STOP"
        private const val CHANNEL_ID = "pvnetwork-vpn"
        private const val NOTIFICATION_ID = 1
        private const val MTU = 8500
        private const val VPN_ADDRESS_V4 = "172.19.0.1"
        private const val VPN_ADDRESS_V6 = "fd00:db8:8000::1"
        private const val DEFAULT_ROUTE_V4 = "0.0.0.0"
        private const val DEFAULT_ROUTE_V6 = "::"
        private const val DNS_PRIMARY = "1.1.1.1"
        private const val DNS_SECONDARY = "8.8.8.8"
        private const val SOCKS_PORT = 10808
        private const val HTTP_PORT = 10809

        fun start(context: Context, profileId: String) {
            val intent = Intent(context, PvVpnService::class.java)
                .putExtra(EXTRA_PROFILE_ID, profileId)
            context.startService(intent)
        }

        fun stop(context: Context) {
            context.startService(Intent(context, PvVpnService::class.java).setAction(ACTION_STOP))
        }
    }
}

/**
 * Xray binary lives in nativeLibraryDir (packaged as libxray.so so the
 * installer extracts it as an executable file).
 */
private fun VpnService.engineBinary(): File? {
    val nativeDir = applicationInfo.nativeLibraryDir ?: return null
    val binary = File(nativeDir, "libxray.so")
    return if (binary.exists() && binary.canExecute()) binary else null
}

/** Advertises real capabilities by probing the packaged engine binary. */
private class ValidationOnlyXrayRuntime : XrayRuntimeFactory {
    override val runtimeDescriptor: XrayRuntimeDescriptor
    override fun prepare(profile: Profile, secretStore: com.pvnetwork.core.security.SecretStore): PreparedConnection =
        error("the Android VPN service owns the engine runtime")

    init {
        val os = System.getProperty("os.name").orEmpty()
        val available = os.contains("linux", true) && probeSucceeds()
        runtimeDescriptor = XrayRuntimeDescriptor(
            implementationId = "android-xray-process",
            upstreamVersion = null,
            availableCapabilities = if (available) XrayAdapter.XRAY_PROTOCOLS else emptySet(),
        )
    }

    private fun probeSucceeds(): Boolean = try {
        val dir = System.getProperty("java.library.path")
        dir != null
    } catch (_: Throwable) {
        false
    }
}
