# L2TP/IPsec — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **008 — L2TP/IPsec**

State: `IN-RESEARCH / LEGACY COMPOSED COMPATIBILITY TARGET / NOT IMPLEMENTED`

Original v1 state: `V1-HANDOFF-READY / NOT IMPLEMENTED`.

## Composition boundary

L2TP/IPsec is not one monolithic protocol engine. The reference must keep at least these layers separate:

1. **IPsec/IKE protection layer** — Security Associations, authentication/key management, ESP protection and NAT traversal;
2. **L2TPv2 tunnel/session layer** — RFC 2661 control/data messages over UDP;
3. **PPP layer** — link establishment, optional peer/user authentication and network-layer configuration;
4. **OS/product networking** — address assignment, DNS, routes, firewall/NAT and client lifecycle.

The completed 004–007 IKE/IPsec v2 dossier is reused as a dependency. It does **not** complete L2TP or PPP research automatically.

## Standards baseline

Primary protocol references:

- RFC 2661 — L2TPv2;
- RFC 3193 — Securing L2TP using IPsec;
- RFC 1661 / STD 51 — PPP;
- RFC 2759 — MS-CHAPv2 definition where that PPP authentication method is used;
- completed IPsec standards set from `strongswan-family/reference-v2/`.

RFC 3193 is a 2001 composition built around the then-current IPsec/IKEv1 architecture. Modern product policy must not copy its old mandatory cipher-suite assumptions; current IPsec algorithm/security guidance and selected platform/backend policy govern the protection layer.

## Current serious source/project pins

### xl2tpd

- repository: `xelerance/xl2tpd`
- reviewed release: `v1.3.20`
- release commit: `07b3063e2b6870fad16366bc8d7c52a6f2a4292f`
- annotated tag object: `40ebc64102237422aca80ce522d80355a21a5afc`
- root license: GPL version 2 or later
- role: userspace L2TPv2 implementation, PPP via `pppd`, optional Linux kernel L2TP support; IPsec is external/composed.

### Accel-PPP NG

- repository: `accel-ppp/accel-ppp-ng`
- reviewed current commit: `9654bb66fa129fc3c20b24612ea91fb43dd14f38`
- root `COPYING`: GPLv2
- role: high-performance Linux access server with an explicit L2TP control module plus integrated PPP/auth/pools/RADIUS features; IPsec protection remains a separate composition decision.

### pppd / ppp-project

- repository: `ppp-project/ppp`
- reviewed current commit: `86c240ea75d48205310a4d0761784cb11f0b086e`
- root COPYING: code is redistributable with per-file notices; pppd/pppstats/pppdump are under BSD-style notices while some plugins are GPL.
- role: major PPP implementation used in classic Linux L2TP stacks.

### NetworkManager-l2tp

- repository: `nm-l2tp/NetworkManager-l2tp`
- reviewed current release commit: `ef970e2f3bf3e219d99c949b7a91a6bb55ab6ef7`
- version in source: `1.52.4`
- root COPYING: GPLv2
- role: Linux NetworkManager client/frontend/plugin composing `kl2tpd` or `xl2tpd` for L2TP, strongSwan or Libreswan for IPsec and PPP user authentication.

### Katalix go-l2tp / kl2tpd

- repository: `katalix/go-l2tp`
- reviewed release/current commit: `0f3bb650da44ce8565d1ff0e62d5cef000d36c65` (`v0.1.8` documentation bump)
- license: MIT
- role: L2TP implementation; `kl2tpd` is a minimal L2TPv2 client daemon used as an option by NetworkManager-l2tp.

## Current platform evidence

### Apple

Current Apple deployment documentation still lists **L2TP over IPsec** as a native supported VPN protocol on iOS/iPadOS/macOS/tvOS/visionOS, with shared-secret machine authentication and user authentication options. Current Apple Business configuration documentation (2026) also exposes L2TP over IPsec as a managed-device configuration choice.

### Windows

Current Microsoft documentation still supports L2TP client profiles and RRAS server functionality, but Windows Server 2025 **does not accept L2TP/PPTP by default for new RRAS setups**; administrators can explicitly enable them. This is strong operational evidence to classify L2TP/IPsec as legacy compatibility rather than a new-deployment default.

### Android

The current Android VPN developer guide describes the built-in PPTP/L2TP-IPsec client as a **legacy VPN** client. Exact user-facing availability/OEM/version behavior still requires device/runtime certification.

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
| `DATA_PATH_AND_WIRE_FLOW.md` | pending |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | started |
| `DEPLOYMENT_TOPOLOGIES.md` | pending |
| `REFERENCE_INDEX.md` | active |

## Non-negotiable rules

1. L2TP provides tunneling/session transport, not adequate VPN confidentiality by itself.
2. PPP authentication is a separate layer from IPsec machine/peer authentication.
3. The IPsec SA must exist and protect L2TP control/data according to the L2TP/IPsec security profile; a raw UDP/1701 success is not L2TP/IPsec certification.
4. Do not expose old IKEv1/3DES/SHA1-era compatibility as a new-deployment default.
5. Do not silently downgrade from IKEv2/modern VPN choices to L2TP/IPsec.
6. Source L2TP support does not prove native-platform UI availability.
7. Multiple L2TP clients behind one NAT and source-port behavior are interoperability dimensions, not minor implementation details.
8. Product secrets must be separated: IPsec machine PSK/certificate, PPP user credentials and transient session keys are different credential classes.

## Exact next action

Finish the remaining mandatory v2 files, then reconcile all 16 reference gates. Preserve external real-device/server/interoperability blockers instead of fabricating success. Entry 008 must remain separate from plain L2TP/L2TPv3 entries and from the already completed IPsec reference family.
