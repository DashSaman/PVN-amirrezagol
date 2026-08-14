# AGENTS Handoff — 2026-08-14 — L2TPv3 v2 slice 1

Work unit: `L2TPV3-COMPLETE-REFERENCE-V2`

Entry: 009 L2TPv3

## State transition

Entry 009 source/reference state is now:

`REFERENCE-V2-SOURCE-COMPLETE / ADVANCED-PSEUDOWIRE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Strict `COMPLETE-REFERENCE-v2` tracker promotion remains PENDING because live Linux/Cisco/interoperability/packet-capture evidence is unavailable in the current environment.

## Completed source/reference dossier

Folder:

`research/upstreams/classic-tunnels-family/l2tpv3-reference-v2/`

All 11 mandatory files:

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_INDEX.md`

Formal 16-gate reconciliation:

`ENTRY_009_V2_GATE_RECONCILIATION.md`

## Key source/implementation pins

### Linux kernel L2TP

- `torvalds/linux@2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- current source includes L2TPv3 session handling and Ethernet pseudowire netdevice code.

### iproute2

- `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- root GPLv2; `ip/ipl2tp.c` GPL-2.0-or-later.
- static unmanaged L2TPv3 tunnel/session programming through generic netlink.

### go-l2tp / ql2tpd

- `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- MIT.
- Linux kernel-backed static L2TPv3 orchestration plus optional minimal ql2tpd HELLO like-peer liveness.

### Cisco IOS XE

Current official IOS XE 17.x L2TPv3 pseudowire/xconnect/static/signaled documentation is used as the principal proprietary router interoperability reference. No Cisco source reuse is claimed.

## Key protocol/security rules

1. L2TPv3 is a Layer-2 pseudowire technology, not a consumer remote-access login VPN.
2. RFC3931 supports direct IP protocol 115 and UDP encapsulation.
3. Dynamic UDP control begins at destination UDP 1701; selected source/response ports may differ.
4. Static Linux `ip l2tp` has no RFC3931 control connection.
5. Cookie is session anti-misdirection/blind-injection hardening, not encryption.
6. Plain L2TPv3 has no payload confidentiality on an untrusted underlay.
7. Dynamic control authentication/integrity protects control traffic, not the pseudowire payload as cryptographic VPN encryption.
8. Ethernet pseudowires extend broadcast/VLAN/STP/MAC risks across the underlay.
9. Current RFC9601 ECN propagation update must be considered in implementation tests.
10. Entry 010 owns explicit L2TPv3/IPsec protection and remains separate.
11. Consumer Windows/Apple/Android L2TPv2/IPsec support is not L2TPv3 support.

## Evidence commits

- initial index: `fdb1e5e3c0d7b639edd071c7f5f2bcb13a5a1458`
- implementations: `61c8916c93597fc28eb54dad80eab3036bd4c6b1`
- security/crypto: `705e06a81ed2c7f27a6cdefa763a6ed4712fa158`
- ports/control/session: `7748a952d06ff32f18c7afe55f294d0e6bcddf9c`
- data path: `67b3a17f69548d8152fca13f9c9ab932b417761d`
- installers/projects: `a55c05c78bf9fd120b5144dd6ce3190a338e1c8a`
- install matrix: `ad05683ad41e760ae008f9da3decd9bfaa838eee`
- server/operator UI: `801095edcfa7719fe118c89fa67e371031e03c5b`
- peer install matrix: `e8f5b6fa3987c122f950b0a9866dc8b378f11f27`
- peer UI: `1d6fe1ac4889f3b5a4b715b1c06e8016fedae633`
- topologies: `fe2409df00ea6499048aa9ffb0b57d25e0d735df`
- gate reconciliation: `68518535cb09a8566fc196d1ef5a9a9a5b545285`
- final index sync: `f3682a0bc22d6f325c03a3bf83b4b410a5fe7356`

## Strict external blockers

1. selected Linux distro kernel/module + iproute2 lifecycle;
2. direct-IP protocol-115 Linux-Linux PW;
3. UDP Linux-Linux PW;
4. Ethernet/VLAN/broadcast/multicast/STP behavior;
5. cookie/sequence/reorder negative tests;
6. MTU/PMTU/RFC9601 ECN behavior;
7. ql2tpd restart/HELLO like-peer runtime;
8. Linux-to-Cisco exact-release static interop;
9. dynamic/signaled full RFC3931 peer interop;
10. Cisco selected-platform feature/upgrade/rollback/status evidence;
11. OCI/Kubernetes kernel/netns/capability proof if retained;
12. packet captures proving entry 009 plain data has no confidentiality;
13. entry 010 protected path separately.

## Do not repeat

- do not confuse consumer L2TPv2 with L2TPv3;
- do not treat Cookie as encryption;
- do not claim static iproute2 has dynamic signaling;
- do not expose direct-IP protocol 115 as a TCP/UDP port;
- do not bridge production LANs without loop/VLAN/MTU design;
- do not claim all pseudowire payload types from one Ethernet success;
- do not promote source/reference closure to runtime certification.

## Next work unit

Activate:

`L2TPV3-IPSEC-COMPLETE-REFERENCE-V2`

Entry:

- 010 L2TPv3/IPsec

Exact next action:

1. read entry 010 v1 evidence;
2. reuse entry 009 pseudowire dossier and entries 004–007 IPsec dossier;
3. define exact composition for direct-IP protocol 115 and UDP L2TPv3 security selectors;
4. document IKE/auth/ESP policy without copying old cipher defaults;
5. map Linux strongSwan/Libreswan + kernel L2TP and Cisco/IPsec capable peer architectures;
6. create all 11 mandatory v2 files for entry 010;
7. map install/UI/security/credential ownership separately from plain L2TPv3;
8. document data path/order of encapsulation, NAT/firewall/MTU/topologies;
9. reconcile all 16 gates;
10. preserve external protected-pseudowire packet/interoperability blockers and continue without owner prompting.
