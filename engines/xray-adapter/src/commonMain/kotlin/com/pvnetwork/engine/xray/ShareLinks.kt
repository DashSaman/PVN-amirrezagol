package com.pvnetwork.engine.xray

import com.pvnetwork.core.importing.ImportWarning
import com.pvnetwork.core.importing.ImportWarningKind
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.ProfileOrigin
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Additional share-link importers (VMess / Trojan / Shadowsocks) feeding the
 * same canonical PVProfile model the VLESS importer produces. They preserve
 * the complete original link behind SecretStore and never import or execute
 * Xray-core themselves.
 */
class ShareLinkException(message: String) : IllegalArgumentException(message)

data class ShareLinkParseResult(
    val profile: PVProfile,
    val warnings: List<ImportWarning> = emptyList(),
)

object ShareLinkParser {

    fun parse(link: String, profileId: ProfileId, secretStore: SecretStore): ShareLinkParseResult? {
        val trimmed = link.trim()
        return when {
            trimmed.startsWith("vless://", ignoreCase = true) -> {
                val imported = VlessShareLinkImporter(secretStore).import(trimmed, profileId)
                ShareLinkParseResult(imported.canonicalProfile, imported.warnings)
            }
            trimmed.startsWith("vmess://", ignoreCase = true) ->
                VmessShareLinkImporter(secretStore).import(trimmed, profileId)
            trimmed.startsWith("trojan://", ignoreCase = true) ->
                TrojanShareLinkImporter(secretStore).import(trimmed, profileId)
            trimmed.startsWith("ss://", ignoreCase = true) ->
                ShadowsocksShareLinkImporter(secretStore).import(trimmed, profileId)
            else -> null
        }
    }

    fun storeOriginal(link: String, secretStore: SecretStore): SecretRef {
        val chars = link.trim().toCharArray()
        return try {
            secretStore.put(SecretPurpose.OTHER, chars)
        } finally {
            chars.clearSecret()
        }
    }
}

@OptIn(ExperimentalEncodingApi::class)
internal fun decodeBase64Lenient(raw: String): String? {
    val cleaned = raw.filterNot { it.isWhitespace() }
        .replace('-', '+')
        .replace('_', '/')
    val padded = when (cleaned.length % 4) {
        2 -> "$cleaned=="
        3 -> "$cleaned="
        else -> cleaned
    }
    return try {
        Base64.Default.decode(padded).decodeToString()
    } catch (_: Throwable) {
        null
    }
}

/** Extracts a flat string/int field from the simple JSON VMess links use. */
internal fun flatJsonField(json: String, field: String): String? {
    val stringMatch = Regex("\"$field\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").find(json)
        ?: return Regex("\"$field\"\\s*:\\s*(-?\\d+(?:\\.\\d+)?)").find(json)?.groupValues?.get(1)
    return stringMatch.groupValues[1]
        .replace("\\\"", "\"")
        .replace("\\\\", "\\")
        .replace("\\/", "/")
}

class VmessShareLinkImporter(private val secretStore: SecretStore) {

    fun import(link: String, profileId: ProfileId): ShareLinkParseResult {
        val body = link.trim().removePrefix("vmess://").removePrefix("VMESS://")
        val json = decodeBase64Lenient(body)
            ?: throw ShareLinkException("vmess link body is not valid base64")
        val host = flatJsonField(json, "add")?.takeIf(String::isNotBlank)
            ?: throw ShareLinkException("vmess link is missing the server address")
        val port = flatJsonField(json, "port")?.toIntOrNull()
            ?: throw ShareLinkException("vmess link is missing the server port")
        val identity = flatJsonField(json, "id")?.takeIf(String::isNotBlank)
            ?: throw ShareLinkException("vmess link is missing the user id")
        val displayName = flatJsonField(json, "ps")?.takeIf { it.isNotBlank() } ?: "VMess"

        val warnings = mutableListOf<ImportWarning>()
        val net = flatJsonField(json, "net")?.lowercase() ?: "tcp"
        val transport = when (net) {
            "tcp", "raw" -> "raw"
            "ws", "websocket" -> "websocket"
            "grpc" -> "grpc"
            "xhttp", "splithttp" -> "xhttp"
            "kcp", "mkcp" -> "mkcp"
            else -> {
                warnings += warn("network=$net")
                "raw"
            }
        }
        var security = "none"
        flatJsonField(json, "tls")?.lowercase()?.let {
            if (it == "tls" || it == "reality") security = it else if (it != "none") warnings += warn("tls=$it")
        }
        if (security == "reality") {
            warnings += warn("security=reality")
            security = "tls"
        }
        flatJsonField(json, "type")?.let { header ->
            if (header.isNotBlank() && header != "none" && transport == "raw") warnings += warn("headerType=$header")
        }

        val identityRef = store(identity, SecretPurpose.TOKEN)
        val originalRef = ShareLinkParser.storeOriginal(link, secretStore)
        val extensions = linkedMapOf(
            "xray.application-protocol" to "vmess",
            "xray.security" to security,
            "xray.transport" to transport,
        )
        flatJsonField(json, "scy")?.takeIf { it.isNotBlank() }?.let { extensions["xray.vmess-security"] = it }
        flatJsonField(json, "sni")?.takeIf { it.isNotBlank() }?.let { extensions["xray.server-name"] = it }
        flatJsonField(json, "fp")?.takeIf { it.isNotBlank() }?.let { extensions["xray.fingerprint"] = it }
        flatJsonField(json, "host")?.takeIf { it.isNotBlank() }?.let { extensions["xray.host-header"] = it }
        flatJsonField(json, "path")?.takeIf { it.isNotBlank() }?.let { extensions["xray.path"] = it }
        flatJsonField(json, "serviceName")?.takeIf { it.isNotBlank() }?.let { extensions["xray.service-name"] = it }

        return ShareLinkParseResult(
            PVProfile(
                id = profileId,
                displayName = displayName,
                protocolId = XrayAdapter.VMESS_CAPABILITY,
                endpoint = Endpoint(host, port),
                secretRefs = mapOf("xray.vmess.identity" to identityRef, "xray.original-share-link" to originalRef),
                extensions = extensions,
                origin = ProfileOrigin.IMPORT,
            ),
            warnings,
        )
    }

    private fun store(value: String, purpose: SecretPurpose): SecretRef {
        val chars = value.toCharArray()
        return try {
            secretStore.put(purpose, chars)
        } finally {
            chars.clearSecret()
        }
    }

    private fun warn(detail: String) = ImportWarning(
        ImportWarningKind.UNSUPPORTED_FIELD,
        detail.substringBefore('='),
        "$detail is preserved but not certified by this importer",
    )
}

class TrojanShareLinkImporter(private val secretStore: SecretStore) {

    fun import(link: String, profileId: ProfileId): ShareLinkParseResult {
        val trimmed = link.trim()
        val afterScheme = trimmed.substringAfter("://")
        val fragmentIndex = afterScheme.indexOf('#')
        val beforeFragment = if (fragmentIndex >= 0) afterScheme.substring(0, fragmentIndex) else afterScheme
        val displayName = if (fragmentIndex >= 0) percentDecode(afterScheme.substring(fragmentIndex + 1)) else null
        val queryIndex = beforeFragment.indexOf('?')
        val authority = if (queryIndex >= 0) beforeFragment.substring(0, queryIndex) else beforeFragment
        val query = parseQuery(if (queryIndex >= 0) beforeFragment.substring(queryIndex + 1) else "")

        val at = authority.lastIndexOf('@')
        if (at <= 0 || at == authority.lastIndex) throw ShareLinkException("trojan link requires password@host:port")
        val password = percentDecode(authority.substring(0, at))
        if (password.isBlank()) throw ShareLinkException("trojan password must not be blank")
        val endpoint = parseEndpoint(authority.substring(at + 1))

        val warnings = mutableListOf<ImportWarning>()
        val transport = transportFromQuery(query, warnings)
        val security = if (query["security"]?.lowercase() == "reality") "reality" else "tls"

        val passwordRef = store(password, SecretPurpose.PASSWORD)
        val originalRef = ShareLinkParser.storeOriginal(link, secretStore)
        val extensions = linkedMapOf(
            "xray.application-protocol" to "trojan",
            "xray.security" to security,
            "xray.transport" to transport,
        )
        query["sni"]?.takeIf { it.isNotBlank() }?.let { extensions["xray.server-name"] = it }
            ?: run { extensions["xray.server-name"] = endpoint.host }
        query["fp"]?.takeIf { it.isNotBlank() }?.let { extensions["xray.fingerprint"] = it }
        query["pbk"]?.takeIf { it.isNotBlank() }?.let { extensions["xray.reality-public-key"] = it }
        query["sid"]?.takeIf { it.isNotBlank() }?.let { extensions["xray.reality-short-id"] = it }
        query["path"]?.takeIf { it.isNotBlank() }?.let { extensions["xray.path"] = it }
        query["host"]?.takeIf { it.isNotBlank() }?.let { extensions["xray.host-header"] = it }
        query["serviceName"]?.takeIf { it.isNotBlank() }?.let { extensions["xray.service-name"] = it }

        return ShareLinkParseResult(
            PVProfile(
                id = profileId,
                displayName = displayName?.takeIf { it.isNotBlank() } ?: "Trojan",
                protocolId = XrayAdapter.TROJAN_CAPABILITY,
                endpoint = endpoint,
                secretRefs = mapOf("xray.trojan.password" to passwordRef, "xray.original-share-link" to originalRef),
                extensions = extensions,
                origin = ProfileOrigin.IMPORT,
            ),
            warnings,
        )
    }

    private fun store(value: String, purpose: SecretPurpose): SecretRef {
        val chars = value.toCharArray()
        return try {
            secretStore.put(purpose, chars)
        } finally {
            chars.clearSecret()
        }
    }
}

class ShadowsocksShareLinkImporter(private val secretStore: SecretStore) {

    fun import(link: String, profileId: ProfileId): ShareLinkParseResult {
        val trimmed = link.trim()
        val afterScheme = trimmed.substringAfter("://")
        val fragmentIndex = afterScheme.indexOf('#')
        val beforeFragment = if (fragmentIndex >= 0) afterScheme.substring(0, fragmentIndex) else afterScheme
        val displayName = if (fragmentIndex >= 0) percentDecode(afterScheme.substring(fragmentIndex + 1)) else null

        // SIP002: ss://base64(method:password)@host:port  (or legacy fully-encoded body)
        val queryIndex = beforeFragment.indexOf('?')
        var main = if (queryIndex >= 0) beforeFragment.substring(0, queryIndex) else beforeFragment
        main = main.removeSuffix("/")
        val at = main.lastIndexOf('@')
        val methodAndPassword: String
        val endpointText: String
        if (at > 0) {
            methodAndPassword = decodeBase64Lenient(main.substring(0, at))
                ?: percentDecode(main.substring(0, at))
            endpointText = main.substring(at + 1)
        } else {
            val decoded = decodeBase64Lenient(main)
                ?: throw ShareLinkException("shadowsocks link body is not valid base64")
            val splitAt = decoded.lastIndexOf('@')
            if (splitAt <= 0) throw ShareLinkException("shadowsocks link is missing userinfo or endpoint")
            methodAndPassword = decoded.substring(0, splitAt)
            endpointText = decoded.substring(splitAt + 1)
        }
        val colon = methodAndPassword.indexOf(':')
        if (colon <= 0) throw ShareLinkException("shadowsocks userinfo must be method:password")
        val method = methodAndPassword.substring(0, colon)
        val password = methodAndPassword.substring(colon + 1)
        if (method.isBlank() || password.isBlank()) throw ShareLinkException("shadowsocks method/password must not be blank")
        val endpoint = parseEndpoint(endpointText)

        val passwordRef = store(password, SecretPurpose.PASSWORD)
        val originalRef = ShareLinkParser.storeOriginal(link, secretStore)
        return ShareLinkParseResult(
            PVProfile(
                id = profileId,
                displayName = displayName?.takeIf { it.isNotBlank() } ?: "Shadowsocks",
                protocolId = XrayAdapter.SHADOWSOCKS_CAPABILITY,
                endpoint = endpoint,
                secretRefs = mapOf("xray.shadowsocks.password" to passwordRef, "xray.original-share-link" to originalRef),
                extensions = linkedMapOf(
                    "xray.application-protocol" to "shadowsocks",
                    "xray.security" to "none",
                    "xray.transport" to "raw",
                    "xray.shadowsocks-method" to method,
                ),
                origin = ProfileOrigin.IMPORT,
            ),
        )
    }

    private fun store(value: String, purpose: SecretPurpose): SecretRef {
        val chars = value.toCharArray()
        return try {
            secretStore.put(purpose, chars)
        } finally {
            chars.clearSecret()
        }
    }
}

internal fun transportFromQuery(query: Map<String, String>, warnings: MutableList<ImportWarning>): String {
    val type = query["type"]?.lowercase() ?: "tcp"
    return when (type) {
        "tcp", "raw" -> "raw"
        "ws", "websocket" -> "websocket"
        "grpc" -> "grpc"
        "xhttp", "splithttp" -> "xhttp"
        "kcp", "mkcp" -> "mkcp"
        else -> {
            warnings += ImportWarning(
                ImportWarningKind.UNSUPPORTED_FIELD,
                "type",
                "type=$type is preserved but not certified by this importer",
            )
            "raw"
        }
    }
}

internal fun parseEndpoint(value: String): Endpoint {
    if (value.startsWith('[')) {
        val close = value.indexOf(']')
        if (close <= 1 || close + 2 >= value.length || value[close + 1] != ':') {
            throw ShareLinkException("invalid bracketed endpoint")
        }
        val port = value.substring(close + 2).toIntOrNull() ?: throw ShareLinkException("invalid port")
        return Endpoint(value.substring(1, close), port)
    }
    val colon = value.lastIndexOf(':')
    if (colon <= 0 || colon == value.lastIndex) throw ShareLinkException("endpoint requires explicit host:port")
    val port = value.substring(colon + 1).toIntOrNull() ?: throw ShareLinkException("invalid port")
    return Endpoint(value.substring(0, colon), port)
}

internal fun parseQuery(raw: String): Map<String, String> {
    if (raw.isBlank()) return emptyMap()
    val result = linkedMapOf<String, String>()
    raw.split('&').filter(String::isNotBlank).forEach { item ->
        val eq = item.indexOf('=')
        val key = percentDecode(if (eq >= 0) item.substring(0, eq) else item)
        val value = percentDecode(if (eq >= 0) item.substring(eq + 1) else "")
        if (key.isNotBlank()) result[key] = value
    }
    return result
}

internal fun percentDecode(value: String): String {
    val out = StringBuilder()
    var i = 0
    while (i < value.length) {
        if (value[i] != '%' || i + 2 >= value.length) {
            out.append(value[i])
            i++
            continue
        }
        val hi = hex(value[i + 1])
        val lo = hex(value[i + 2])
        if (hi < 0 || lo < 0) {
            out.append(value[i])
            i++
            continue
        }
        out.append(((hi shl 4) or lo).toChar())
        i += 3
    }
    return out.toString()
}

private fun hex(c: Char): Int = when (c) {
    in '0'..'9' -> c - '0'
    in 'a'..'f' -> c - 'a' + 10
    in 'A'..'F' -> c - 'A' + 10
    else -> -1
}
