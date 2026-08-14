# SoftEther VPN Protocol — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **013 — SoftEther VPN Protocol**

State: `IN-RESEARCH / NATIVE-SOFTETHER-PROTOCOL / NOT IMPLEMENTED`

Original evidence:

`research/protocols/013-softether-vpn-protocol/V1_RESEARCH.md`

## Scope boundary

This dossier covers the **native SoftEther VPN Protocol** used between SoftEther-native client/bridge/server components.

It does **not** treat these compatibility features as the native protocol:

- SSTP;
- L2TP/IPsec;
- OpenVPN-compatible mode;
- EtherIP;
- other compatibility listeners/protocols exposed by SoftEther VPN Server.

Those belong to their own matrix entries/dossiers.

## Exact source baseline

Reviewed source:

- repository: `SoftEtherVPN/SoftEtherVPN`
- commit: `49eb2f08641709d1af57a0d04971973ff94461db`
- relevant source areas reviewed/available at this pin include `src/Cedar/Protocol.c`, `Client.c`, `Server.c` and the surrounding Cedar connection/session/hub implementation.
- root license at the reviewed repository is Apache License 2.0; component/third-party license inventory must still be preserved for redistribution/build decisions.

## Native architecture

Conceptual native path:

`SoftEther VPN Client / Bridge`

`-> configurable TCP listener on SoftEther VPN Server`

`-> TLS-protected SoftEther-native control/session protocol`

`-> user/device authentication`

`-> Virtual Hub session`

`-> virtual Ethernet packet transfer`

`-> local bridge / SecureNAT / cascade / server-side forwarding according to configuration`

The server product can host multiple protocol families simultaneously, but entry 013 tracks only the native SoftEther path.

## Listener/port boundary

SoftEther VPN Server supports configurable TCP listeners; common product defaults historically include TCP 443, 992 and 5555, but these are **server listener defaults/configuration**, not three mandatory wire-protocol ports. Exact selected release/server configuration must be recorded.

Management channels such as Server Manager/vpncmd administration are also separate from the end-user native VPN session and must not be conflated with client data traffic.

## Security boundary

- TLS protects native client/server transport;
- client/server certificate trust and server identity policy remain distinct from user authentication;
- SoftEther supports multiple user authentication backends/methods through the Virtual Hub/user model;
- the selected release's TLS/provider/cipher/certificate behavior must be audited at runtime/source-freeze;
- do not weaken certificate validation or enable obsolete crypto globally for compatibility;
- do not implement replacement cryptography outside the maintained/native backend.

## Mandatory v2 files

| File | State |
|---|---|
| `SERVER_IMPLEMENTATIONS.md` | started |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | pending |
| `SERVER_INSTALL_MATRIX.md` | pending |
| `SERVER_UI_AND_MENUS.md` | pending |
| `CLIENT_INSTALL_MATRIX.md` | pending |
| `CLIENT_UI_AND_MENUS.md` | pending |
| `CRYPTOGRAPHY.md` | started |
| `DATA_PATH_AND_WIRE_FLOW.md` | started |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | started |
| `DEPLOYMENT_TOPOLOGIES.md` | pending |
| `REFERENCE_INDEX.md` | active |

## Non-negotiable rules

1. Native SoftEther VPN Protocol is separate from SSTP/L2TP/OpenVPN/EtherIP compatibility modes.
2. Product listener ports are configurable and are not equivalent to a fixed protocol port requirement.
3. Management/API/vpncmd traffic is separate from client VPN data/session traffic.
4. TLS server trust, SoftEther session authentication and Virtual Hub authorization are separate layers.
5. Virtual Hub, local bridge, SecureNAT and cascade features are server networking/session topology features, not alternative names for the native wire protocol.
6. Multiple simultaneous TCP connections/connection modes, where supported by the selected client/server profile, are runtime transport capabilities and require exact-version evidence.
7. SoftEther Server's broad multiprotocol surface must be minimized to required listeners/features.
8. Secrets/private keys/user credentials remain in product/platform secure ownership and are redacted from logs/exports.
9. Exact client/server/platform support is release-specific; source portability is not certification.
10. No production support claim until real native-client/server packet/session/lifecycle tests pass.

## Exact next action

Complete the remaining 10 mandatory files, reconcile all 16 v2 gates, preserve exact runtime/platform/client/server blockers, checkpoint entry 013 and immediately continue entry 014 EtherIP.
