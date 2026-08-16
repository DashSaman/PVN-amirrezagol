package com.pvnetwork.engine.openvpn

import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.SecretRef

data class OpenVpnRemote(
    val endpoint: Endpoint,
    val protocol: String? = null,
) {
    init {
        protocol?.let { require(it.isNotBlank()) { "OpenVPN remote protocol must not be blank" } }
    }
}

data class OpenVpnMaterialRefs(
    val originalProfileRef: SecretRef,
    val caRefs: List<SecretRef> = emptyList(),
    val certificateRefs: List<SecretRef> = emptyList(),
    val privateKeyRefs: List<SecretRef> = emptyList(),
    val tlsAuthRefs: List<SecretRef> = emptyList(),
    val tlsCryptRefs: List<SecretRef> = emptyList(),
)

data class OpenVpnProfileConfig(
    val remotes: List<OpenVpnRemote>,
    val protocol: String? = null,
    val device: String? = null,
    val authUserPassRequired: Boolean = false,
    val materials: OpenVpnMaterialRefs,
    val unsupportedDirectiveNames: Set<String> = emptySet(),
) {
    init {
        require(remotes.isNotEmpty()) { "OpenVPN profile requires at least one remote" }
        protocol?.let { require(it.isNotBlank()) { "OpenVPN protocol must not be blank" } }
        device?.let { require(it.isNotBlank()) { "OpenVPN device must not be blank" } }
    }
}
