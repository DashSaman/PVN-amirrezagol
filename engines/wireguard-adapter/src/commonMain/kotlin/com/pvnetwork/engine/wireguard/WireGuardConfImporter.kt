package com.pvnetwork.engine.wireguard

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

class WireGuardConfigException(message: String) : IllegalArgumentException(message)

data class WireGuardImportResult(
    val canonicalProfile: PVProfile,
    val config: WireGuardProfileConfig,
    val warnings: List<ImportWarning>,
)

/**
 * Imports the common wg-quick style configuration surface without importing any
 * WireGuard engine code. PrivateKey/PresharedKey values are moved immediately
 * into [SecretStore]; canonical/profile configuration retains only SecretRef.
 *
 * The input String itself is transient caller-owned material and must not be
 * persisted or logged by callers. Engine/runtime validation remains downstream.
 */
class WireGuardConfImporter(
    private val secretStore: SecretStore,
) {
    fun import(
        text: String,
        profileId: ProfileId,
        displayName: String,
    ): WireGuardImportResult {
        val interfaceValues = linkedMapOf<String, MutableList<String>>()
        val peerValues = mutableListOf<LinkedHashMap<String, MutableList<String>>>()
        val warnings = mutableListOf<ImportWarning>()
        var currentSection: String? = null

        text.lineSequence().forEachIndexed { index, rawLine ->
            val lineNumber = index + 1
            val line = rawLine.substringBefore('#').trim()
            if (line.isEmpty()) return@forEachIndexed

            if (line.startsWith('[') && line.endsWith(']')) {
                val section = line.substring(1, line.length - 1).trim().lowercase()
                when (section) {
                    "interface" -> {
                        if (interfaceValues.isNotEmpty() || currentSection == "interface") {
                            throw WireGuardConfigException("line $lineNumber: duplicate [Interface] section")
                        }
                        currentSection = "interface"
                    }
                    "peer" -> {
                        peerValues += linkedMapOf()
                        currentSection = "peer"
                    }
                    else -> throw WireGuardConfigException("line $lineNumber: unsupported section [$section]")
                }
                return@forEachIndexed
            }

            val separator = line.indexOf('=')
            if (separator <= 0) {
                throw WireGuardConfigException("line $lineNumber: expected key = value")
            }
            val key = line.substring(0, separator).trim()
            val value = line.substring(separator + 1).trim()
            if (value.isEmpty()) {
                throw WireGuardConfigException("line $lineNumber: $key must not be empty")
            }

            val target = when (currentSection) {
                "interface" -> interfaceValues
                "peer" -> peerValues.lastOrNull()
                    ?: throw WireGuardConfigException("line $lineNumber: peer value without [Peer]")
                else -> throw WireGuardConfigException("line $lineNumber: value outside a section")
            }
            target.getOrPut(key) { mutableListOf() } += value
        }

        if (interfaceValues.isEmpty()) {
            throw WireGuardConfigException("missing [Interface] section")
        }
        if (peerValues.isEmpty()) {
            throw WireGuardConfigException("missing [Peer] section")
        }

        warnings += unknownWarnings(
            interfaceValues.keys,
            setOf("PrivateKey", "Address", "DNS", "MTU"),
            "Interface",
        )
        peerValues.forEachIndexed { index, values ->
            warnings += unknownWarnings(
                values.keys,
                setOf("PublicKey", "PresharedKey", "Endpoint", "AllowedIPs", "PersistentKeepalive"),
                "Peer[$index]",
            )
        }

        val privateKey = singleton(interfaceValues, "PrivateKey", required = true)!!
        val privateKeyRef = storeSecret(privateKey, SecretPurpose.PRIVATE_KEY)
        val addresses = listValues(interfaceValues, "Address")
        if (addresses.isEmpty()) throw WireGuardConfigException("[Interface] requires Address")
        addresses.forEach(::validateCidr)
        val dnsServers = listValues(interfaceValues, "DNS")
        val mtu = singleton(interfaceValues, "MTU")?.toIntOrNull()?.also {
            if (it !in 1..65535) throw WireGuardConfigException("MTU must be between 1 and 65535")
        } ?: singleton(interfaceValues, "MTU")?.let { throw WireGuardConfigException("MTU must be an integer") }

        val secretRefs = linkedMapOf("wireguard.private-key" to privateKeyRef)
        val peers = peerValues.mapIndexed { index, values ->
            val publicKey = singleton(values, "PublicKey", required = true)!!
            val allowedIps = listValues(values, "AllowedIPs")
            if (allowedIps.isEmpty()) throw WireGuardConfigException("Peer[$index] requires AllowedIPs")
            allowedIps.forEach(::validateCidr)
            val endpoint = singleton(values, "Endpoint")?.let(::parseEndpoint)
            val keepaliveRaw = singleton(values, "PersistentKeepalive")
            val keepalive = keepaliveRaw?.toIntOrNull()?.also {
                if (it !in 0..65535) throw WireGuardConfigException("Peer[$index] PersistentKeepalive must be 0..65535")
            } ?: keepaliveRaw?.let { throw WireGuardConfigException("Peer[$index] PersistentKeepalive must be an integer") }
            val preSharedKeyRef = singleton(values, "PresharedKey")?.let {
                val ref = storeSecret(it, SecretPurpose.PRE_SHARED_KEY)
                secretRefs["wireguard.peer.$index.preshared-key"] = ref
                ref
            }
            WireGuardPeerConfig(
                publicKey = publicKey,
                preSharedKeyRef = preSharedKeyRef,
                endpoint = endpoint,
                allowedIps = allowedIps,
                persistentKeepaliveSeconds = keepalive,
            )
        }

        val primaryEndpoint = peers.firstNotNullOfOrNull { it.endpoint }
            ?: throw WireGuardConfigException("PVNetwork client profile requires at least one peer Endpoint")

        val config = WireGuardProfileConfig(
            addresses = addresses,
            dnsServers = dnsServers,
            mtu = mtu,
            privateKeyRef = privateKeyRef,
            peers = peers,
        )

        val extensions = linkedMapOf<String, String>()
        extensions["wireguard.interface.addresses"] = addresses.joinToString(",")
        if (dnsServers.isNotEmpty()) extensions["wireguard.interface.dns"] = dnsServers.joinToString(",")
        mtu?.let { extensions["wireguard.interface.mtu"] = it.toString() }
        extensions["wireguard.peer.count"] = peers.size.toString()
        peers.forEachIndexed { index, peer ->
            extensions["wireguard.peer.$index.public-key"] = peer.publicKey
            extensions["wireguard.peer.$index.allowed-ips"] = peer.allowedIps.joinToString(",")
            peer.endpoint?.let { extensions["wireguard.peer.$index.endpoint"] = renderEndpoint(it) }
            peer.persistentKeepaliveSeconds?.let {
                extensions["wireguard.peer.$index.keepalive"] = it.toString()
            }
        }

        return WireGuardImportResult(
            canonicalProfile = PVProfile(
                id = profileId,
                displayName = displayName,
                protocolId = WireGuardAdapter.PROTOCOL_ID,
                endpoint = primaryEndpoint,
                secretRefs = secretRefs,
                extensions = extensions,
                origin = ProfileOrigin.IMPORT,
            ),
            config = config,
            warnings = warnings,
        )
    }

    private fun storeSecret(value: String, purpose: SecretPurpose): SecretRef {
        val chars = value.toCharArray()
        return try {
            secretStore.put(purpose, chars)
        } finally {
            chars.clearSecret()
        }
    }

    private fun singleton(
        values: Map<String, List<String>>,
        key: String,
        required: Boolean = false,
    ): String? {
        val matches = values[key].orEmpty()
        if (matches.size > 1) throw WireGuardConfigException("$key must appear at most once")
        if (required && matches.isEmpty()) throw WireGuardConfigException("missing required $key")
        return matches.singleOrNull()
    }

    private fun listValues(values: Map<String, List<String>>, key: String): List<String> =
        values[key].orEmpty().flatMap { raw -> raw.split(',').map(String::trim).filter(String::isNotEmpty) }

    private fun unknownWarnings(
        actual: Set<String>,
        supported: Set<String>,
        section: String,
    ): List<ImportWarning> = actual.filter { it !in supported }.map { key ->
        ImportWarning(
            kind = ImportWarningKind.UNSUPPORTED_FIELD,
            field = "$section.$key",
            message = "$section field $key is not consumed by the first WireGuard adapter slice",
        )
    }

    private fun parseEndpoint(value: String): Endpoint {
        if (value.startsWith('[')) {
            val close = value.indexOf(']')
            if (close <= 1 || close + 2 >= value.length || value[close + 1] != ':') {
                throw WireGuardConfigException("invalid bracketed Endpoint: $value")
            }
            val host = value.substring(1, close)
            val port = value.substring(close + 2).toIntOrNull()
                ?: throw WireGuardConfigException("invalid Endpoint port: $value")
            return Endpoint(host, port)
        }

        val separator = value.lastIndexOf(':')
        if (separator <= 0 || separator == value.lastIndex) {
            throw WireGuardConfigException("Endpoint must be host:port or [ipv6]:port")
        }
        val host = value.substring(0, separator)
        val port = value.substring(separator + 1).toIntOrNull()
            ?: throw WireGuardConfigException("invalid Endpoint port: $value")
        return Endpoint(host, port)
    }

    private fun renderEndpoint(endpoint: Endpoint): String =
        if (':' in endpoint.host) "[${endpoint.host}]:${endpoint.port}" else "${endpoint.host}:${endpoint.port}"

    private fun validateCidr(value: String) {
        val slash = value.lastIndexOf('/')
        if (slash <= 0 || slash == value.lastIndex) {
            throw WireGuardConfigException("expected CIDR address, got $value")
        }
        val address = value.substring(0, slash)
        val prefix = value.substring(slash + 1).toIntOrNull()
            ?: throw WireGuardConfigException("invalid CIDR prefix in $value")
        if (':' in address) {
            if (prefix !in 0..128 || address.isBlank()) throw WireGuardConfigException("invalid IPv6 CIDR: $value")
        } else {
            if (prefix !in 0..32 || !isIpv4(address)) throw WireGuardConfigException("invalid IPv4 CIDR: $value")
        }
    }

    private fun isIpv4(value: String): Boolean {
        val parts = value.split('.')
        return parts.size == 4 && parts.all { part ->
            part.isNotEmpty() && part.all(Char::isDigit) && part.toIntOrNull() in 0..255
        }
    }
}
