# L2TPv3 — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **009 — L2TPv3**

## Current state

`REFERENCE-V2-SOURCE-COMPLETE / ADVANCED-PSEUDOWIRE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Original v1 state: `V1-HANDOFF-READY / NOT IMPLEMENTED`.

Strict `COMPLETE-REFERENCE-v2` tracker promotion remains PENDING because live Linux/Cisco/interoperability/packet-capture evidence is not available in this agent environment.

Formal gate reconciliation:

`ENTRY_009_V2_GATE_RECONCILIATION.md`

## Protocol identity

L2TPv3 is primarily a Layer-2 pseudowire/control/data protocol, not the legacy consumer remote-access L2TP/IPsec stack from entry 008.

Authoritative standards:

- RFC 3931 — L2TPv3;
- RFC 4719 — Ethernet/Ethernet-VLAN pseudowires over L2TPv3;
- RFC 5641 — Ethernet pseudowire update;
- RFC 9601 — current ECN propagation update to RFC 3931.

Key boundaries:

- direct L2TPv3 over IP uses IP protocol **115**;
- UDP mode uses the L2TP UDP control-port model beginning at destination port **1701** with negotiated/source ports potentially differing;
- L2TPv3 carries pseudowire payloads such as Ethernet; it is not a user-login VPN;
- the native data channel has no cryptographic confidentiality;
- Cookie is anti-misdirection/blind-injection hardening, not encryption;
- entry 010 L2TPv3/IPsec is a separate protected composition.

## Current implementation/source pins

### Linux kernel L2TP subsystem

- repository: `torvalds/linux`
- reviewed commit: `2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- relevant source: `net/l2tp/l2tp_core.c`, `l2tp_eth.c` and associated netlink/IP modules;
- current source includes version-3 session handling and Ethernet pseudowire netdevice support.

### iproute2 `ip l2tp`

- repository: `iproute2/iproute2`
- reviewed commit: `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- root COPYING: GPLv2; `ip/ipl2tp.c`: GPL-2.0-or-later.

Static `ip l2tp` sessions are **unmanaged/static** L2TPv3 objects; no RFC3931 control protocol runs to negotiate/repair values.

### Katalix go-l2tp / ql2tpd

- repository: `katalix/go-l2tp`
- reviewed commit: `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- MIT license.

Provides Linux-kernel L2TPv3 orchestration/static ql2tpd sessions and optional minimal HELLO behavior for compatible like-peers.

### Cisco IOS XE

Current official IOS XE documentation continues to expose L2TPv3 pseudowire classes, signaling vs `protocol none`, `xconnect`, manual session IDs/cookies and selected Ethernet/other attachment circuits.

Cisco is a proprietary network-OS interoperability target, not a source-reuse candidate.

## Mandatory v2 file set

| Required file | Current state | Purpose |
|---|---|---|
| `SERVER_IMPLEMENTATIONS.md` | REFERENCE-PASS | Linux kernel/iproute2/go-l2tp/Cisco peer implementations |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | REFERENCE-PASS | distro/kernel ownership, ql2tpd packaging, automation, Cisco, OCI/K8s |
| `SERVER_INSTALL_MATRIX.md` | REFERENCE-PASS | Linux/Cisco/container peer installation/feature matrix |
| `SERVER_UI_AND_MENUS.md` | REFERENCE-PASS | iproute2/bridge/ql2tpd/Cisco control-plane and admin UI map |
| `CLIENT_INSTALL_MATRIX.md` | PEER-MAPPED REFERENCE-PASS | relevant pseudowire peer installation; no fake consumer clients |
| `CLIENT_UI_AND_MENUS.md` | PEER-MAPPED REFERENCE-PASS | infrastructure peer/operator UI and safety UX |
| `CRYPTOGRAPHY.md` | REFERENCE-PASS | no-confidentiality boundary, Cookie/control auth, IPsec dependency |
| `DATA_PATH_AND_WIRE_FLOW.md` | REFERENCE-PASS | kernel/netlink/bridge/encapsulation/return path |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | REFERENCE-PASS | protocol 115, UDP/1701, static vs dynamic control/session flow |
| `DEPLOYMENT_TOPOLOGIES.md` | REFERENCE-PASS | Linux/Cisco/VLAN/underlay/HA/security-boundary topologies |
| `REFERENCE_INDEX.md` | synchronized | this recovery/reference index |

## Non-negotiable rules

1. L2TPv3 is a pseudowire technology, not a consumer VPN login protocol.
2. Static `ip l2tp` sessions do not run the RFC3931 control plane.
3. Cookie validation is not encryption and does not replace IPsec on an untrusted underlay.
4. UDP and direct-IP protocol 115 are distinct transport modes.
5. Ethernet pseudowire extends Layer-2 risks: broadcasts, loops, STP, VLAN and MTU must be engineered.
6. Entry 010 owns L2TPv3/IPsec protection and must remain separate.
7. L2TPv2 fallback over UDP must never be invisible product behavior.
8. Linux and Cisco control models are distinct and support is exact-version/platform based.
9. Current RFC9601 ECN behavior belongs in implementation testing.
10. Consumer Windows/Apple/Android L2TPv2 VPN support is not evidence of L2TPv3 support.

## Strict external blockers

Before strict tracker promotion:

- Linux distro kernel/modules + iproute2 lifecycle receipts;
- direct-IP protocol-115 Linux-Linux pseudowire;
- UDP Linux-Linux pseudowire;
- Ethernet/VLAN/broadcast/multicast/STP behavior;
- cookie mismatch and sequence/reorder negative tests;
- MTU/PMTU/ECN behavior;
- ql2tpd restart/HELLO like-peer behavior;
- Linux-to-Cisco exact-version static interoperability;
- dynamic/signaled RFC3931 interoperability against a selected full-control peer;
- selected Cisco platform feature/upgrade/rollback/show/debug receipts;
- OCI/Kubernetes host-kernel/netns/capability proof if retained;
- packet capture proving plain entry-009 has no confidentiality;
- entry-010 protected-path proof separately.

## Exact next action

Checkpoint entry 009 as source/reference complete but execution-blocked, then immediately execute **entry 010 L2TPv3/IPsec COMPLETE-REFERENCE-v2**. Reuse this pseudowire reference plus the completed entries 004–007 IPsec dossier, but add exact protection composition/selectors/security policy/install/UI/topology/interoperability evidence. Do not promote entry 009 in the strict tracker until external receipts exist.
