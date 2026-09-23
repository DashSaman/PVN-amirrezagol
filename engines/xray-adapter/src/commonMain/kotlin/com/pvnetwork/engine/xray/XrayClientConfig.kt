package com.pvnetwork.engine.xray

import com.pvnetwork.core.profile.PVProfile

/**
 * Builds the runtime client JSON consumed by an Xray executable. Shared by
 * every platform runtime so desktop and mobile generate byte-identical
 * engine configuration from the same canonical profile.
 */
object XrayClientConfig {

    data class InboundPorts(
        val socksPort: Int,
        val httpPort: Int,
    ) {
        init {
            require(socksPort in 1..65535) { "socks port must be between 1 and 65535" }
            require(httpPort in 1..65535) { "http port must be between 1 and 65535" }
            require(socksPort != httpPort) { "socks and http ports must differ" }
        }
    }

    fun build(
        profile: PVProfile,
        credential: CharArray,
        ports: InboundPorts,
    ): String {
        val security = profile.extensions["xray.security"] ?: error("missing xray.security")
        val transport = profile.extensions["xray.transport"] ?: error("missing xray.transport")
        val network = when (transport) {
            "raw" -> "raw"
            "websocket" -> "ws"
            "grpc" -> "grpc"
            "xhttp" -> "xhttp"
            "mkcp" -> "kcp"
            else -> error("unsupported Xray transport: $transport")
        }

        val out = StringBuilder()
        out.append("{\"log\":{\"loglevel\":\"info\"},")
        out.append("\"inbounds\":[")
        out.append("{\"tag\":\"pvnetwork-socks\",\"listen\":\"127.0.0.1\",\"port\":${ports.socksPort},")
        out.append("\"protocol\":\"socks\",\"settings\":{\"udp\":true}},")
        out.append("{\"tag\":\"pvnetwork-http\",\"listen\":\"127.0.0.1\",\"port\":${ports.httpPort},")
        out.append("\"protocol\":\"http\",\"settings\":{}}")
        out.append("],\"outbounds\":[{\"tag\":\"pvnetwork-proxy\",\"protocol\":")
        appendJsonString(out, profile.protocolId)
        out.append(",\"settings\":")
        appendProtocolSettings(out, profile, credential)
        out.append(",\"streamSettings\":{\"network\":")
        appendJsonString(out, network)
        out.append(",\"security\":")
        appendJsonString(out, security)
        when (security) {
            "tls" -> appendTlsSettings(out, profile)
            "reality" -> appendRealitySettings(out, profile)
            "none" -> Unit
            else -> error("unsupported Xray security: $security")
        }
        appendTransportSettings(out, profile, transport)
        out.append("}},{\"tag\":\"direct\",\"protocol\":\"freedom\"}]}")
        return out.toString()
    }

    private fun appendProtocolSettings(out: StringBuilder, profile: PVProfile, credential: CharArray) {
        when (profile.protocolId) {
            XrayAdapter.VLESS_CAPABILITY -> appendVlessSettings(out, profile, credential)
            XrayAdapter.VMESS_CAPABILITY -> appendVmessSettings(out, profile, credential)
            XrayAdapter.TROJAN_CAPABILITY -> appendTrojanSettings(out, profile, credential)
            XrayAdapter.SHADOWSOCKS_CAPABILITY -> appendShadowsocksSettings(out, profile, credential)
            else -> error("unsupported Xray protocol: ${profile.protocolId}")
        }
    }

    private fun appendVlessSettings(out: StringBuilder, profile: PVProfile, identity: CharArray) {
        out.append("{\"vnext\":[{\"address\":")
        appendJsonString(out, profile.endpoint.host)
        out.append(",\"port\":${profile.endpoint.port},\"users\":[{\"id\":")
        appendJsonString(out, identity)
        out.append(",\"encryption\":\"none\"")
        profile.extensions["xray.flow"]?.takeIf(String::isNotBlank)?.let {
            out.append(",\"flow\":")
            appendJsonString(out, it)
        }
        out.append("}]}]}")
    }

    private fun appendVmessSettings(out: StringBuilder, profile: PVProfile, identity: CharArray) {
        val accountSecurity = profile.extensions["xray.vmess-security"] ?: "auto"
        out.append("{\"vnext\":[{\"address\":")
        appendJsonString(out, profile.endpoint.host)
        out.append(",\"port\":${profile.endpoint.port},\"users\":[{\"id\":")
        appendJsonString(out, identity)
        out.append(",\"security\":")
        appendJsonString(out, accountSecurity)
        out.append("}]}]}")
    }

    private fun appendTrojanSettings(out: StringBuilder, profile: PVProfile, password: CharArray) {
        out.append("{\"servers\":[{\"address\":")
        appendJsonString(out, profile.endpoint.host)
        out.append(",\"port\":${profile.endpoint.port},\"password\":")
        appendJsonString(out, password)
        out.append("}]}")
    }

    private fun appendShadowsocksSettings(out: StringBuilder, profile: PVProfile, password: CharArray) {
        val method = profile.extensions["xray.shadowsocks-method"] ?: error("missing xray.shadowsocks-method")
        out.append("{\"servers\":[{\"address\":")
        appendJsonString(out, profile.endpoint.host)
        out.append(",\"port\":${profile.endpoint.port},\"method\":")
        appendJsonString(out, method)
        out.append(",\"password\":")
        appendJsonString(out, password)
        out.append("}]}")
    }

    private fun appendTlsSettings(out: StringBuilder, profile: PVProfile) {
        val fields = mutableListOf<Pair<String, String>>()
        profile.extensions["xray.server-name"]?.takeIf(String::isNotBlank)?.let { fields += "serverName" to it }
        profile.extensions["xray.fingerprint"]?.takeIf(String::isNotBlank)?.let { fields += "fingerprint" to it }
        out.append(",\"tlsSettings\":{")
        appendFields(out, fields)
        out.append("}")
    }

    private fun appendRealitySettings(out: StringBuilder, profile: PVProfile) {
        val publicKey = profile.extensions["xray.reality-public-key"]?.takeIf(String::isNotBlank)
            ?: error("REALITY public key is required")
        val fields = mutableListOf("publicKey" to publicKey)
        profile.extensions["xray.server-name"]?.takeIf(String::isNotBlank)?.let { fields += "serverName" to it }
        profile.extensions["xray.fingerprint"]?.takeIf(String::isNotBlank)?.let { fields += "fingerprint" to it }
        profile.extensions["xray.reality-short-id"]?.let { fields += "shortId" to it }
        out.append(",\"realitySettings\":{")
        appendFields(out, fields)
        out.append("}")
    }

    private fun appendTransportSettings(out: StringBuilder, profile: PVProfile, transport: String) {
        when (transport) {
            "raw" -> Unit
            "websocket" -> {
                out.append(",\"wsSettings\":{")
                var wrote = false
                profile.extensions["xray.path"]?.takeIf(String::isNotBlank)?.let {
                    out.append("\"path\":")
                    appendJsonString(out, it)
                    wrote = true
                }
                profile.extensions["xray.host-header"]?.takeIf(String::isNotBlank)?.let {
                    if (wrote) out.append(",")
                    out.append("\"headers\":{\"Host\":")
                    appendJsonString(out, it)
                    out.append("}")
                }
                out.append("}")
            }
            "grpc" -> {
                out.append(",\"grpcSettings\":{")
                profile.extensions["xray.service-name"]?.takeIf(String::isNotBlank)?.let {
                    out.append("\"serviceName\":")
                    appendJsonString(out, it)
                }
                out.append("}")
            }
            "xhttp" -> {
                val fields = mutableListOf<Pair<String, String>>()
                profile.extensions["xray.host-header"]?.takeIf(String::isNotBlank)?.let { fields += "host" to it }
                profile.extensions["xray.path"]?.takeIf(String::isNotBlank)?.let { fields += "path" to it }
                fields += "mode" to "auto"
                out.append(",\"xhttpSettings\":{")
                appendFields(out, fields)
                out.append("}")
            }
            "mkcp" -> out.append(",\"kcpSettings\":{}")
        }
    }

    private fun appendFields(out: StringBuilder, fields: List<Pair<String, String>>) {
        fields.forEachIndexed { index, (name, value) ->
            if (index > 0) out.append(",")
            appendJsonString(out, name)
            out.append(":")
            appendJsonString(out, value)
        }
    }

    private fun appendJsonString(out: StringBuilder, value: String) {
        out.append('"')
        value.forEach { appendJsonChar(out, it) }
        out.append('"')
    }

    private fun appendJsonString(out: StringBuilder, value: CharArray) {
        out.append('"')
        value.forEach { appendJsonChar(out, it) }
        out.append('"')
    }

    private fun appendJsonChar(out: StringBuilder, c: Char) {
        when (c) {
            '\\' -> out.append("\\\\")
            '"' -> out.append("\\\"")
            '\b' -> out.append("\\b")
            '\u000C' -> out.append("\\f")
            '\n' -> out.append("\\n")
            '\r' -> out.append("\\r")
            '\t' -> out.append("\\t")
            else -> if (c.code < 0x20) out.append("\\u%04x".format(c.code)) else out.append(c)
        }
    }
}
