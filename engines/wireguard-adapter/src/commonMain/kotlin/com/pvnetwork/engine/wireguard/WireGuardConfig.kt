package com.pvnetwork.engine.wireguard

import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.SecretRef

data class WireGuardPeerConfig(
    val publicKey: String,
    val preSharedKeyRef: SecretRef? = null,
    val endpoint: Endpoint? = null,
    val allowedIps: List<String>,
    val persistentKeepaliveSeconds: Int? = null,
) {
    init {
        require(publicKey.isNotBlank()) { "WireGuard peer public key must not be blank" }
        require(allowedIps.isNotEmpty()) { "WireGuard peer requires at least one AllowedIPs entry" }
        require(allowedIps.none(String::isBlank)) { "WireGuard AllowedIPs entries must not be blank" }
        persistentKeepaliveSeconds?.let {
            require(it in 0..65535) { "WireGuard PersistentKeepalive must be between 0 and 65535 seconds" }
        }
    }
}

data class WireGuardProfileConfig(
    val addresses: List<String>,
    val dnsServers: List<String> = emptyList(),
    val mtu: Int? = null,
    val privateKeyRef: SecretRef,
    val peers: List<WireGuardPeerConfig>,
) {
    init {
        require(addresses.isNotEmpty()) { "WireGuard client profile requires at least one Address" }
        require(addresses.none(String::isBlank)) { "WireGuard Address entries must not be blank" }
        require(dnsServers.none(String::isBlank)) { "WireGuard DNS entries must not be blank" }
        mtu?.let { require(it in 1..65535) { "WireGuard MTU must be between 1 and 65535" } }
        require(peers.isNotEmpty()) { "WireGuard profile requires at least one peer" }
    }
}
