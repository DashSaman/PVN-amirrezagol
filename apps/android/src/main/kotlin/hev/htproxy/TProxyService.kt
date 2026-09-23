package hev.htproxy

/**
 * JNI bindings for hev-socks5-tunnel (MIT, https://github.com/heiher/hev-socks5-tunnel
 * pinned tag 2.17.1). The library registers these natives on load; the
 * service keeps the TUN fd open for the lifetime of the tunnel.
 */
object TProxyService {
    init {
        System.loadLibrary("hev-socks5-tunnel")
    }

    /** Starts the tunnel on a background thread inside the native library. */
    external fun TProxyStartService(configPath: String, tunFd: Int): Boolean

    external fun TProxyStopService(): Boolean

    external fun TProxyIsRunning(): Boolean

    /** [txPackets, txBytes, rxPackets, rxBytes] */
    external fun TProxyGetStats(): LongArray
}
