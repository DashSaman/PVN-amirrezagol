# L2TP/IPsec — Server Implementations and Composition

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

The key architectural rule is composition. A server needs a compatible IPsec/IKE protection layer, an L2TPv2 control/data/session layer, a PPP/user/session layer, and routing/addressing/firewall integration. One daemon may own several layers, but the reference must still distinguish them.

## 1. Linux classic stack — strongSwan/Libreswan + xl2tpd + pppd

### IPsec layer

Reuse the completed IKE/IPsec v2 reference under:

`research/upstreams/strongswan-family/reference-v2/`

Primary Linux IPsec engines in the current dossier:

- strongSwan 6.0.7, commit `5973ff8e41deef4e015e1138a2de688acedf6f75`;
- Libreswan v5.4, commit `5eb03b7772b312e705feab9ad5868678a3c007e6`.

For classic RFC 3193 interoperability, the exact IKE version and transform policy must match the client. Historically this composition is IKEv1-driven; do not assume an IKEv2-capable server automatically provides standards-compatible L2TP/IPsec to legacy clients.

### L2TP layer — xl2tpd

Pinned release:

- `xelerance/xl2tpd@07b3063e2b6870fad16366bc8d7c52a6f2a4292f`
- release `v1.3.20`
- GPL-2.0-or-later root license evidence.

Upstream README explicitly states:

- implements RFC 2661 L2TP;
- tunnels PPP over UDP;
- is intended for VPN use where IPsec secures L2TP per RFC 3193;
- communicates with `pppd` using a pseudo-terminal;
- supports userspace and Linux kernel-mode L2TP;
- includes IPsec SA-reference tracking intended to help overlapping/multiple NATed-client cases.

Therefore xl2tpd is a **L2TP/PPP bridge component**, not the cryptographic VPN layer.

### PPP layer — pppd

Pinned current source:

- `ppp-project/ppp@86c240ea75d48205310a4d0761784cb11f0b086e`.

Root COPYING says pppd/pppstats/pppdump are under BSD-style notices while some plugins are GPL. Component-level license review is required before bundling plugins.

PPP owns link negotiation, selected authentication protocol and network-layer configuration after the L2TP session is established.

### PVNetwork role

This classic stack is valuable for Linux server interoperability and lab/reference deployment. It is not a reason to merge three daemon/config domains into one opaque “L2TP server” setting.

## 2. Accel-PPP NG — integrated L2TP + PPP access server

Pinned source:

- `accel-ppp/accel-ppp-ng@9654bb66fa129fc3c20b24612ea91fb43dd14f38`
- GPLv2 root COPYING.

The source tree contains a dedicated `accel-pppd/ctrl/l2tp/` module including L2TP protocol, AVP/dictionary, kernel/netlink and test code.

The reviewed sample configuration explicitly enables:

- `l2tp` control module;
- PPP core;
- PAP, CHAP-MD5, MS-CHAPv1 and MS-CHAPv2 authentication modules;
- address pools;
- RADIUS option;
- L2TP-specific controls such as secret, data sequencing, address pools and interface naming.

### Important boundary

Accel-PPP can replace the separate xl2tpd+pppd part of a Linux stack, but **it does not make IPsec disappear**. For entry 008, a compatible strongSwan/Libreswan/native IPsec layer is still required if traffic is to satisfy L2TP/IPsec rather than plain L2TP.

### Role

High-scale Linux L2TP/PPP server reference and candidate for operator deployments that require many PPP sessions/RADIUS/pools. It requires a separate license/supply-chain/security review from strongSwan/Libreswan.

## 3. SoftEther VPN Server

Existing classic-tunnels source evidence pins SoftEther VPN at:

`49eb2f08641709d1af57a0d04971973ff94461db`

and records L2TP/IPsec-related source families such as `Proto_L2TP.*` and `IPsec_*`/classic tunneling code.

SoftEther is architecturally different from the classic three-process Linux composition: a larger server product owns multiple VPN protocols, users, hubs and management surfaces.

### Role

Serious compatibility/server implementation and useful UI/management reference. Its multiprotocol nature must not be treated as proof that every L2TP/IPsec detail is identical to xl2tpd/Windows/Apple interoperability.

## 4. Windows Server RRAS

Current Microsoft documentation states that Routing and Remote Access Services (RRAS) supports L2TP alongside IKEv2/SSTP/PPTP.

Important current policy signal:

- beginning with **Windows Server 2025**, new RRAS setups do not accept L2TP and PPTP by default;
- administrators may explicitly enable them when necessary;
- existing upgraded configurations retain previous behavior.

### Role

Major native L2TP/IPsec server/interoperability target, especially for Windows-native clients and legacy enterprise deployments.

### Product interpretation

This is a managed native OS stack. PVNetwork should configure/test it through supported Windows management mechanisms rather than attempt to embed or replace RRAS internals.

## 5. Apple native client stack — not a server implementation

Current Apple platform deployment documentation still lists L2TP over IPsec as a built-in client capability. Apple devices in this project are client targets; do not classify their native stack as a general server implementation.

## 6. Android native legacy client — not a server implementation

Current Android developer documentation calls the built-in PPTP/L2TP-IPsec client the **legacy VPN** client. This is client/platform evidence, not a server engine.

## 7. NetworkManager-l2tp — client orchestration, not server

Pinned source:

- `nm-l2tp/NetworkManager-l2tp@ef970e2f3bf3e219d99c949b7a91a6bb55ab6ef7`
- source version 1.52.4
- GPLv2.

Its README explicitly composes:

- `kl2tpd` or `xl2tpd` for L2TP;
- Libreswan or strongSwan for IPsec;
- PPP user authentication.

It is a high-value proof of the layered architecture, but it is a Linux NetworkManager client/frontend rather than a server.

## 8. Katalix go-l2tp / kl2tpd

Pinned source:

- `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- MIT license.

The reviewed release material identifies `kl2tpd` as a minimal L2TPv2 client daemon and includes L2TPv2 control-message compatibility fixes. NetworkManager-l2tp can use it instead of xl2tpd.

Role: client-side L2TP component/reference, not the complete L2TP/IPsec stack.

## 9. Server/authentication backends

The PPP layer may integrate with:

- local PAP/CHAP/MS-CHAP credentials;
- RADIUS/AAA;
- directory-backed systems through the chosen PPP/server integration;
- certificate/machine authentication at the IPsec layer separately.

Do not conflate:

- IPsec machine/peer authentication;
- L2TP tunnel secret/AVP authentication where used;
- PPP user authentication.

They are different trust boundaries and secret classes.

## 10. Selection direction

### New deployments

`NOT PREFERRED`.

Prefer IKEv2 or other modern approved protocols where requirements allow. Windows Server 2025 default behavior and Android's “legacy VPN” terminology reinforce this direction.

### Compatibility deployments

Candidate stacks:

- Windows-native client <-> Windows RRAS or compatible appliance;
- Apple native L2TP/IPsec <-> compatible gateway;
- Linux NetworkManager-l2tp <-> compatible gateway;
- strongSwan/Libreswan + xl2tpd + pppd;
- strongSwan/Libreswan + Accel-PPP for higher-scale Linux L2TP/PPP;
- SoftEther compatibility server where separately approved.

Every combination needs exact-version interoperability testing.

## 11. Remaining implementation inventory

Still to deepen:

- current SoftEther release/source pin for the L2TP/IPsec-specific v2 slice;
- exact OPNsense/pfSense L2TP server implementation/current availability;
- selected router/vendor stacks;
- RADIUS/AAA integration source and storage behavior for selected Linux server;
- current Windows RRAS management/config/security implementation details;
- current Apple/Android native runtime matrices.
