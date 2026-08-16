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

class VlessShareLinkException(message: String) : IllegalArgumentException(message)

data class VlessImportResult(
    val canonicalProfile: PVProfile,
    val config: XrayVlessConfig,
    val warnings: List<ImportWarning>,
)

/**
 * Parses a conservative VLESS share-link surface while preserving the complete
 * original link behind SecretStore. It does not import or execute Xray-core.
 */
class VlessShareLinkImporter private constructor(
    private val secretStore: SecretStore,
    private val transactional: Boolean,
) {
    constructor(secretStore: SecretStore) : this(secretStore, true)

    fun import(link: String, profileId: ProfileId): VlessImportResult {
        if (transactional) {
            val tx = XraySecretTransaction(secretStore)
            return try {
                VlessShareLinkImporter(tx, false).import(link, profileId).also { tx.commit() }
            } catch (failure: Throwable) {
                tx.rollback(); throw failure
            }
        }
        if (!link.startsWith("vless://", ignoreCase = true)) throw VlessShareLinkException("expected vless:// share link")
        val originalRef = store(link, SecretPurpose.OTHER)
        val afterScheme = link.substring(8)
        val fragmentIndex = afterScheme.indexOf('#')
        val beforeFragment = if (fragmentIndex >= 0) afterScheme.substring(0, fragmentIndex) else afterScheme
        val displayName = if (fragmentIndex >= 0) percentDecode(afterScheme.substring(fragmentIndex + 1)) else null
        val queryIndex = beforeFragment.indexOf('?')
        val authority = if (queryIndex >= 0) beforeFragment.substring(0, queryIndex) else beforeFragment
        val queryRaw = if (queryIndex >= 0) beforeFragment.substring(queryIndex + 1) else ""
        val at = authority.lastIndexOf('@')
        if (at <= 0 || at == authority.lastIndex) throw VlessShareLinkException("VLESS link requires identity@host:port")
        val identity = percentDecode(authority.substring(0, at))
        if (identity.isBlank()) throw VlessShareLinkException("VLESS identity must not be blank")
        val endpoint = parseEndpoint(authority.substring(at + 1))
        val identityRef = store(identity, SecretPurpose.TOKEN)
        val query = parseQuery(queryRaw)
        val warnings = mutableListOf<ImportWarning>()

        val securityRaw = query["security"]?.lowercase() ?: "none"
        val security = when (securityRaw) {
            "none" -> XraySecurity.NONE
            "tls" -> XraySecurity.TLS
            "reality" -> XraySecurity.REALITY
            else -> XraySecurity.UNKNOWN.also { warnings += unsupported("security=$securityRaw") }
        }
        val transportRaw = (query["type"] ?: "tcp").lowercase()
        val transport = when (transportRaw) {
            "tcp", "raw" -> XrayTransport.RAW
            "ws", "websocket" -> XrayTransport.WEBSOCKET
            "grpc" -> XrayTransport.GRPC
            "xhttp", "splithttp" -> XrayTransport.XHTTP
            "kcp", "mkcp" -> XrayTransport.MKCP
            else -> XrayTransport.UNKNOWN.also { warnings += unsupported("type=$transportRaw") }
        }
        val flow = query["flow"]?.takeIf(String::isNotBlank)
        if (flow != null && flow != "xtls-rprx-vision") warnings += unsupported("flow=$flow")
        if (security == XraySecurity.REALITY && query["pbk"].isNullOrBlank()) {
            warnings += ImportWarning(ImportWarningKind.AMBIGUOUS_SEMANTICS, "pbk", "REALITY link has no explicit public key; runtime certification must reject or resolve this before connection")
        }

        val known = setOf("security", "type", "flow", "sni", "fp", "pbk", "sid", "path", "host", "serviceName", "encryption")
        query.keys.filter { it !in known }.sorted().forEach { warnings += unsupported("query:$it") }
        query["encryption"]?.let { if (it != "none") warnings += unsupported("encryption=$it") }

        val config = XrayVlessConfig(
            endpoint = endpoint,
            identityRef = identityRef,
            protectedOriginalRef = originalRef,
            security = security,
            transport = transport,
            flow = flow,
            serverName = query["sni"],
            fingerprint = query["fp"],
            realityPublicKey = query["pbk"],
            realityShortId = query["sid"],
            path = query["path"],
            hostHeader = query["host"],
            serviceName = query["serviceName"],
            displayName = displayName,
        )
        val extensions = linkedMapOf<String, String>(
            "xray.application-protocol" to "vless",
            "xray.security" to security.name.lowercase(),
            "xray.transport" to transport.name.lowercase(),
        )
        flow?.let { extensions["xray.flow"] = it }
        config.serverName?.let { extensions["xray.server-name"] = it }
        config.fingerprint?.let { extensions["xray.fingerprint"] = it }
        config.realityPublicKey?.let { extensions["xray.reality-public-key"] = it }
        config.realityShortId?.let { extensions["xray.reality-short-id"] = it }
        config.path?.let { extensions["xray.path"] = it }
        config.hostHeader?.let { extensions["xray.host-header"] = it }
        config.serviceName?.let { extensions["xray.service-name"] = it }

        return VlessImportResult(
            canonicalProfile = PVProfile(
                id = profileId,
                displayName = displayName?.takeIf(String::isNotBlank) ?: "VLESS",
                protocolId = XrayAdapter.VLESS_CAPABILITY,
                endpoint = endpoint,
                secretRefs = mapOf("xray.vless.identity" to identityRef, "xray.original-share-link" to originalRef),
                extensions = extensions,
                origin = ProfileOrigin.IMPORT,
            ),
            config = config,
            warnings = warnings,
        )
    }

    private fun parseEndpoint(value: String): Endpoint {
        if (value.startsWith('[')) {
            val close = value.indexOf(']')
            if (close <= 1 || close + 2 >= value.length || value[close + 1] != ':') throw VlessShareLinkException("invalid bracketed VLESS endpoint")
            val port = value.substring(close + 2).toIntOrNull() ?: throw VlessShareLinkException("invalid VLESS port")
            return Endpoint(value.substring(1, close), port)
        }
        val colon = value.lastIndexOf(':')
        if (colon <= 0 || colon == value.lastIndex) throw VlessShareLinkException("VLESS endpoint requires explicit host:port")
        val port = value.substring(colon + 1).toIntOrNull() ?: throw VlessShareLinkException("invalid VLESS port")
        return Endpoint(value.substring(0, colon), port)
    }

    private fun parseQuery(raw: String): Map<String, String> {
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

    private fun percentDecode(value: String): String {
        val out = StringBuilder()
        var i = 0
        while (i < value.length) {
            val c = value[i]
            if (c == '%' && i + 2 < value.length) {
                val hi = hex(value[i + 1]); val lo = hex(value[i + 2])
                if (hi >= 0 && lo >= 0) { out.append(((hi shl 4) or lo).toChar()); i += 3; continue }
            }
            out.append(if (c == '+') ' ' else c); i++
        }
        return out.toString()
    }

    private fun hex(c: Char): Int = when (c) {
        in '0'..'9' -> c - '0'
        in 'a'..'f' -> c - 'a' + 10
        in 'A'..'F' -> c - 'A' + 10
        else -> -1
    }

    private fun store(value: String, purpose: SecretPurpose): SecretRef {
        val chars = value.toCharArray()
        return try { secretStore.put(purpose, chars) } finally { chars.clearSecret() }
    }

    private fun unsupported(value: String) = ImportWarning(
        ImportWarningKind.UNSUPPORTED_FIELD,
        value.substringBefore('='),
        "$value is preserved in the protected source but is not certified by this first Xray adapter slice",
    )
}
