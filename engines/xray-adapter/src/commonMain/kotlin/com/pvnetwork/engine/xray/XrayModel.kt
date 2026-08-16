package com.pvnetwork.engine.xray

import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.SecretRef

enum class XrayApplicationProtocol { VLESS }
enum class XraySecurity { NONE, TLS, REALITY, UNKNOWN }
enum class XrayTransport { RAW, WEBSOCKET, GRPC, XHTTP, MKCP, UNKNOWN }

data class XrayVlessConfig(
    val endpoint: Endpoint,
    val identityRef: SecretRef,
    val protectedOriginalRef: SecretRef,
    val security: XraySecurity,
    val transport: XrayTransport,
    val flow: String? = null,
    val serverName: String? = null,
    val fingerprint: String? = null,
    val realityPublicKey: String? = null,
    val realityShortId: String? = null,
    val path: String? = null,
    val hostHeader: String? = null,
    val serviceName: String? = null,
    val displayName: String? = null,
) {
    val applicationProtocol: XrayApplicationProtocol = XrayApplicationProtocol.VLESS
}
