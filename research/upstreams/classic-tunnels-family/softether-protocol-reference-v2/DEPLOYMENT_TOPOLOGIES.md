# SoftEther VPN Protocol — Deployment Topologies

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## 1. Native remote-access client to SoftEther VPN Server

`SoftEther native client`

`-> TLS/TCP listener`

`-> native SoftEther session`

`-> Virtual Hub`

`-> SecureNAT or local bridge/routed enterprise access`

Baseline native-protocol topology. Certify exact client/server release, certificate/auth, Virtual Hub and OS network behavior.

## 2. Native client to SecureNAT-backed hub

`client -> native session -> Virtual Hub -> SecureNAT -> target network/Internet`

Useful where server-side virtual NAT/DHCP is desired without direct physical bridging. Measure throughput/resource behavior separately from local bridge.

## 3. Native client to local-bridge hub

`client -> native session -> Virtual Hub -> local bridge -> physical/VLAN network`

Higher Layer-2 exposure. Validate loops/STP, VLAN, DHCP/ARP/ND trust, MAC scale, MTU and interface privilege.

## 4. Site-to-site / VPN Bridge

`LAN A -> SoftEther VPN Bridge/native connection -> SoftEther Server/Hub -> LAN B`

Treat as infrastructure L2 extension, not consumer remote access. Explicitly design loop/VLAN/broadcast/security policy.

## 5. Cascade connection

`SoftEther Server/Hub A -> cascade/native connection -> Server/Hub B`

Useful for hub-to-hub/service federation. Record cascade credential, direction, reconnect, routing/Layer-2 loop risk and which server owns policy.

## 6. Multi-listener server

One server can expose multiple native TCP listeners for reachability. Native clients may use selected listener ports according to profile/server config.

Do not expose extra listeners without need; firewall and monitor each separately.

## 7. Multiprotocol gateway

`native SoftEther clients + SSTP/L2TP/OpenVPN-compatible clients -> one SoftEther Server`

Operationally possible, but each protocol remains a separate matrix capability/security model. Minimize enabled compatibility listeners and keep per-protocol telemetry/certification.

## 8. Cloud VM

`Internet clients -> cloud firewall/TCP listener -> SoftEther Server VM -> VPC/VNet/private network`

Validate public DNS/certificate, security groups, forwarding/NAT, persistent server config and image/update lifecycle.

## 9. L4 TCP load balancer / HA

Potential:

`native clients -> TCP load balancer -> SoftEther servers`

Long-lived TLS/native sessions cannot be assumed to migrate seamlessly between backends. Validate stickiness, idle timeout, certificate identity, hub/user configuration consistency and reconnect behavior.

Do not claim cluster HA from a generic TCP balancer alone.

## 10. SoftEther clustering/cascade features

If native SoftEther clustering/server-farm features are selected, treat them as product-specific stateful infrastructure. Map exact selected release management, user/session distribution, Virtual Hub ownership and failure semantics before support claims.

## 11. Containerized server

`client -> TCP service/NAT -> pinned SoftEther container -> persistent config/hub/network integration`

Advanced only. Validate NET_ADMIN/TUN/bridge privileges, TLS key mounts, management exposure, restart and config persistence.

## 12. IPv4/IPv6

Test endpoint transport and virtual network behavior independently:

- server listener reachability over IPv4/IPv6;
- native TLS/session;
- Virtual Hub/adapter IP behavior;
- DHCP/IPv6 configuration;
- DNS/routes;
- MTU/firewall.

No dual-stack claim from IPv4 success alone.

## 13. Migration/interop topology

A SoftEther Server can help migrate clients between protocols because compatibility modes can coexist, but migration must be explicit:

- identify current wire protocol;
- provision/test target protocol/profile;
- preserve access/routes/auth semantics;
- switch users;
- disable old listener only after evidence.

Do not call this native-protocol fallback.

## 14. Required topology labs

- native Windows client -> native SoftEther server;
- SecureNAT and local bridge separately;
- client parallel connection behavior;
- VPN Bridge/site-to-site;
- cascade;
- disabled compatibility-listener proof;
- cloud/NAT/firewall;
- L4 load-balancer/reconnect if retained;
- IPv6;
- server restart/upgrade/certificate rotation;
- Layer-2 VLAN/STP/MTU/security behavior.
