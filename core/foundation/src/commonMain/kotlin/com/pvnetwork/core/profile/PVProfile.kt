package com.pvnetwork.core.profile

data class ProfileId(val value: String) {
    init {
        require(value.isNotBlank()) { "profile id must not be blank" }
    }
}

data class SecretRef(val value: String) {
    init {
        require(value.isNotBlank()) { "secret reference must not be blank" }
    }
}

data class Endpoint(
    val host: String,
    val port: Int,
) {
    init {
        require(host.isNotBlank()) { "endpoint host must not be blank" }
        require(port in 1..65535) { "endpoint port must be between 1 and 65535" }
    }
}

enum class ProfileOrigin {
    MANUAL,
    IMPORT,
    SUBSCRIPTION,
    MIGRATION,
}

/**
 * Product-owned canonical profile metadata.
 *
 * Reusable secret material is intentionally excluded. Callers store only opaque
 * [SecretRef] values and resolve them at the platform security boundary.
 * A protocol id identifies requested semantics; it is not proof that an adapter
 * implements the protocol.
 */
data class PVProfile(
    val schemaVersion: Int = CURRENT_SCHEMA_VERSION,
    val id: ProfileId,
    val displayName: String,
    val protocolId: String,
    val endpoint: Endpoint,
    val secretRefs: Map<String, SecretRef> = emptyMap(),
    val extensions: Map<String, String> = emptyMap(),
    val origin: ProfileOrigin = ProfileOrigin.MANUAL,
) {
    init {
        require(schemaVersion > 0) { "schema version must be positive" }
        require(displayName.isNotBlank()) { "profile display name must not be blank" }
        require(protocolId.isNotBlank()) { "protocol id must not be blank" }
        require(secretRefs.keys.all { it.isNotBlank() }) { "secret role must not be blank" }
        require(extensions.keys.all(::isNamespacedExtensionKey)) {
            "extension keys must be namespaced, for example vendor.option"
        }
    }

    companion object {
        const val CURRENT_SCHEMA_VERSION: Int = 1

        private fun isNamespacedExtensionKey(key: String): Boolean {
            val separator = key.indexOf('.')
            return separator > 0 && separator < key.lastIndex && !key.any(Char::isWhitespace)
        }
    }
}
