package com.pvnetwork.engine.openvpn

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

class OpenVpnConfigException(message: String) : IllegalArgumentException(message)

data class OpenVpnImportResult(
    val canonicalProfile: PVProfile,
    val config: OpenVpnProfileConfig,
    val warnings: List<ImportWarning>,
)

/**
 * First PVNetwork-owned .ovpn import slice.
 *
 * The complete original profile is stored only through SecretStore so unknown
 * or not-yet-supported directives can be preserved without copying raw secret
 * material into canonical metadata. Inline private/tls/certificate material is
 * also represented by opaque refs. This parser never reads external file paths.
 */
class OpenVpnImporter private constructor(
    private val secretStore: SecretStore,
    private val transactional: Boolean,
) {
    constructor(secretStore: SecretStore) : this(secretStore, true)

    fun import(text: String, profileId: ProfileId, displayName: String): OpenVpnImportResult {
        if (transactional) {
            val transaction = OpenVpnSecretTransaction(secretStore)
            return try {
                OpenVpnImporter(transaction, false).import(text, profileId, displayName)
                    .also { transaction.commit() }
            } catch (failure: Throwable) {
                transaction.rollback()
                throw failure
            }
        }

        if (text.isBlank()) throw OpenVpnConfigException("OpenVPN profile must not be blank")

        val originalProfileRef = store(text, SecretPurpose.OTHER)
        val remotes = mutableListOf<OpenVpnRemote>()
        val warnings = mutableListOf<ImportWarning>()
        val unknownNames = linkedSetOf<String>()
        val caRefs = mutableListOf<SecretRef>()
        val certificateRefs = mutableListOf<SecretRef>()
        val privateKeyRefs = mutableListOf<SecretRef>()
        val tlsAuthRefs = mutableListOf<SecretRef>()
        val tlsCryptRefs = mutableListOf<SecretRef>()
        var protocol: String? = null
        var device: String? = null
        var authUserPassRequired = false

        val lines = text.lines()
        var index = 0
        while (index < lines.size) {
            val lineNumber = index + 1
            val trimmed = lines[index].trim()
            index++
            if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith(";")) continue

            if (trimmed.startsWith("<") && trimmed.endsWith(">") && !trimmed.startsWith("</")) {
                val tag = trimmed.substring(1, trimmed.length - 1).trim().lowercase()
                val closeTag = "</$tag>"
                val content = StringBuilder()
                var foundClose = false
                while (index < lines.size) {
                    val blockLine = lines[index]
                    index++
                    if (blockLine.trim().equals(closeTag, ignoreCase = true)) {
                        foundClose = true
                        break
                    }
                    if (content.isNotEmpty()) content.append('\n')
                    content.append(blockLine)
                }
                if (!foundClose) throw OpenVpnConfigException("line $lineNumber: unclosed <$tag> block")
                val value = content.toString()
                when (tag) {
                    "ca" -> caRefs += store(value, SecretPurpose.CERTIFICATE_CREDENTIAL)
                    "cert" -> certificateRefs += store(value, SecretPurpose.CERTIFICATE_CREDENTIAL)
                    "key" -> privateKeyRefs += store(value, SecretPurpose.PRIVATE_KEY)
                    "tls-auth" -> tlsAuthRefs += store(value, SecretPurpose.PRE_SHARED_KEY)
                    "tls-crypt" -> tlsCryptRefs += store(value, SecretPurpose.PRE_SHARED_KEY)
                    else -> {
                        unknownNames += "<$tag>"
                        warnings += unsupported("<$tag>", lineNumber)
                    }
                }
                continue
            }

            val tokens = tokenize(trimmed, lineNumber)
            if (tokens.isEmpty()) continue
            val directive = tokens.first().lowercase()
            val args = tokens.drop(1)
            when (directive) {
                "remote" -> remotes += parseRemote(args, lineNumber)
                "proto" -> protocol = singletonArg(directive, args, lineNumber)
                "dev" -> device = singletonArg(directive, args, lineNumber)
                "auth-user-pass" -> {
                    authUserPassRequired = true
                    if (args.isNotEmpty()) {
                        warnings += ImportWarning(
                            ImportWarningKind.UNSUPPORTED_FIELD,
                            "auth-user-pass",
                            "line $lineNumber: external auth-user-pass file references are preserved only in the protected original profile and are not read implicitly",
                        )
                    }
                }
                "ca", "cert", "key", "tls-auth", "tls-crypt" -> {
                    warnings += ImportWarning(
                        ImportWarningKind.UNSUPPORTED_FIELD,
                        directive,
                        "line $lineNumber: external file reference for $directive is not read implicitly; use a later explicit file-resolution flow",
                    )
                }
                in SAFE_FLAG_DIRECTIVES -> {
                    if (args.isNotEmpty()) {
                        unknownNames += directive
                        warnings += unsupported(directive, lineNumber)
                    }
                }
                else -> {
                    unknownNames += directive
                    warnings += unsupported(directive, lineNumber)
                }
            }
        }

        if (remotes.isEmpty()) throw OpenVpnConfigException("OpenVPN profile requires at least one remote with an explicit port")

        val materials = OpenVpnMaterialRefs(
            originalProfileRef = originalProfileRef,
            caRefs = caRefs,
            certificateRefs = certificateRefs,
            privateKeyRefs = privateKeyRefs,
            tlsAuthRefs = tlsAuthRefs,
            tlsCryptRefs = tlsCryptRefs,
        )
        val secretRefs = linkedMapOf("openvpn.original-profile" to originalProfileRef)
        caRefs.forEachIndexed { i, ref -> secretRefs["openvpn.ca.$i"] = ref }
        certificateRefs.forEachIndexed { i, ref -> secretRefs["openvpn.cert.$i"] = ref }
        privateKeyRefs.forEachIndexed { i, ref -> secretRefs["openvpn.private-key.$i"] = ref }
        tlsAuthRefs.forEachIndexed { i, ref -> secretRefs["openvpn.tls-auth.$i"] = ref }
        tlsCryptRefs.forEachIndexed { i, ref -> secretRefs["openvpn.tls-crypt.$i"] = ref }

        val extensions = linkedMapOf<String, String>()
        protocol?.let { extensions["openvpn.protocol"] = it }
        device?.let { extensions["openvpn.device"] = it }
        extensions["openvpn.remote.count"] = remotes.size.toString()
        extensions["openvpn.auth-user-pass-required"] = authUserPassRequired.toString()
        if (unknownNames.isNotEmpty()) {
            extensions["openvpn.unsupported-directive-names"] = unknownNames.sorted().joinToString(",")
        }

        return OpenVpnImportResult(
            canonicalProfile = PVProfile(
                id = profileId,
                displayName = displayName,
                protocolId = OpenVpnAdapter.PROTOCOL_ID,
                endpoint = remotes.first().endpoint,
                secretRefs = secretRefs,
                extensions = extensions,
                origin = ProfileOrigin.IMPORT,
            ),
            config = OpenVpnProfileConfig(
                remotes = remotes,
                protocol = protocol,
                device = device,
                authUserPassRequired = authUserPassRequired,
                materials = materials,
                unsupportedDirectiveNames = unknownNames,
            ),
            warnings = warnings,
        )
    }

    private fun parseRemote(args: List<String>, lineNumber: Int): OpenVpnRemote {
        if (args.size < 2) {
            throw OpenVpnConfigException("line $lineNumber: first implementation slice requires remote host and explicit port")
        }
        val host = args[0]
        val port = args[1].toIntOrNull()
            ?: throw OpenVpnConfigException("line $lineNumber: remote port must be an integer")
        val endpoint = Endpoint(host, port)
        val remoteProto = args.getOrNull(2)
        if (args.size > 3) {
            throw OpenVpnConfigException("line $lineNumber: remote has unsupported extra arguments")
        }
        return OpenVpnRemote(endpoint, remoteProto)
    }

    private fun singletonArg(name: String, args: List<String>, lineNumber: Int): String {
        if (args.size != 1) throw OpenVpnConfigException("line $lineNumber: $name requires exactly one argument")
        return args.single()
    }

    private fun unsupported(name: String, lineNumber: Int) = ImportWarning(
        ImportWarningKind.UNSUPPORTED_FIELD,
        name,
        "line $lineNumber: directive $name is preserved in the protected original profile but not interpreted by this adapter slice",
    )

    private fun store(value: String, purpose: SecretPurpose): SecretRef {
        val chars = value.toCharArray()
        return try {
            secretStore.put(purpose, chars)
        } finally {
            chars.clearSecret()
        }
    }

    private fun tokenize(line: String, lineNumber: Int): List<String> {
        val out = mutableListOf<String>()
        val current = StringBuilder()
        var quote: Char? = null
        var escape = false
        fun flush() {
            if (current.isNotEmpty()) {
                out += current.toString()
                current.clear()
            }
        }
        for (char in line) {
            if (escape) {
                current.append(char)
                escape = false
                continue
            }
            if (char == '\\') {
                escape = true
                continue
            }
            if (quote != null) {
                if (char == quote) quote = null else current.append(char)
                continue
            }
            if (char == '\'' || char == '"') {
                quote = char
            } else if (char.isWhitespace()) {
                flush()
            } else {
                current.append(char)
            }
        }
        if (escape || quote != null) throw OpenVpnConfigException("line $lineNumber: unterminated quote or escape")
        flush()
        return out
    }

    companion object {
        private val SAFE_FLAG_DIRECTIVES = setOf("client", "nobind", "persist-key", "persist-tun")
    }
}
