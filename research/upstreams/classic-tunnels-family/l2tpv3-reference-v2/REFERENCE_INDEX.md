# L2TPv3 — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **009 — L2TPv3**

State: `IN-RESEARCH / ADVANCED PSEUDOWIRE TARGET / NOT IMPLEMENTED`

Original v1 state: `V1-HANDOFF-READY / NOT IMPLEMENTED`.

## Protocol identity

L2TPv3 is primarily a Layer-2 pseudowire/control/data protocol, not the legacy consumer remote-access L2TP/IPsec stack from entry 008.

Authoritative base:

- RFC 3931 — Layer Two Tunneling Protocol Version 3;
- RFC 4719 — Ethernet/Ethernet-VLAN pseudowires over L2TPv3;
- RFC 5641 — updates Ethernet pseudowire behavior;
- RFC 9601 — 2024 ECN propagation update that explicitly updates RFC 3931.

Important distinction:

- L2TPv3 can run over UDP or directly over IP protocol 115;
- a pseudowire may carry Ethernet/PPP/other supported Layer-2 service payloads;
- L2TPv3 itself does not provide cryptographic confidentiality for its data channel;
- entry 010 L2TPv3/IPsec is a separate protected composition and is **not** completed by this dossier.

## Current implementation/source pins

### Linux kernel L2TP subsystem

Reviewed source:

- repository: `torvalds/linux`
- commit: `2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- relevant source includes `net/l2tp/l2tp_core.c`, `l2tp_eth.c` and related L2TP modules;
- source files use GPL-2.0-only / GPL-2.0-or-later SPDX identifiers as applicable.

Current source explicitly contains L2TPv3 session tables/header handling and an Ethernet pseudowire netdevice implementation.

### iproute2 `ip l2tp`

Reviewed source:

- repository: `iproute2/iproute2`
- commit: `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- root COPYING: GPLv2;
- `ip/ipl2tp.c`: GPL-2.0-or-later.

`ip l2tp` programs the Linux generic-netlink L2TP API with version 3, IP or UDP encapsulation, local/peer tunnel/session IDs, cookies, sequencing/reorder settings and pseudowire type/interface name.

The Linux manual explicitly documents that `ip l2tp` creates **static unmanaged L2TPv3 Ethernet pseudowires**: there is no L2TP control protocol running for those static sessions.

### Katalix go-l2tp / ql2tpd

Reviewed source:

- repository: `katalix/go-l2tp`
- commit: `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- license: MIT.

Current README documents:

- L2TPv3 data plane via Linux L2TP subsystem;
- IPv4/IPv6 endpoints;
- UDP and L2TP-over-IP encapsulation;
- `ql2tpd` for static L2TPv3 sessions;
- optional minimal HELLO-based control keepalive between ql2tpd peers;
- root-required kernel integration tests.

### Cisco IOS XE

Current official Cisco IOS XE 17.x documentation continues to document L2TPv3 pseudowire classes, `encapsulation l2tpv3`, signaling vs `protocol none`, `xconnect`, manual session IDs/cookies and multiple attachment-circuit types.

Role: major vendor interoperability/production-router reference. Cisco IOS XE is a managed proprietary network OS, not a source-reuse candidate.

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

1. L2TPv3 is a pseudowire technology, not a consumer VPN login protocol.
2. Static `ip l2tp` sessions do not magically run the RFC 3931 control plane.
3. Cookie validation is anti-misdirection/spoof-hardening, **not encryption** and not a substitute for IPsec on an untrusted network.
4. UDP 1701 and direct IP protocol 115 are different outer transport modes.
5. Ethernet pseudowire creates Layer-2 extension risks: broadcast domains, loops, STP/VLAN leakage and MTU must be engineered explicitly.
6. Entry 010 owns the explicit L2TPv3-over-IPsec composition; do not mark it complete from entry 009 evidence.
7. L2TPv2 automatic fallback is a separate compatibility behavior and must never happen invisibly in a product policy.
8. Linux kernel/iproute2/go-l2tp and Cisco IOS XE have different control-plane/configuration models; support is exact-platform/version based.
9. Current RFC 9601 ECN behavior must be considered; do not freeze pre-2024 tunnel ECN assumptions.

## Exact next action

Complete all mandatory v2 files, then reconcile the 16 reference gates. Keep peer/router deployment terminology rather than inventing consumer “server/client” semantics. Preserve external Linux/Cisco/interoperability/packet-capture blockers instead of fabricating execution receipts.
