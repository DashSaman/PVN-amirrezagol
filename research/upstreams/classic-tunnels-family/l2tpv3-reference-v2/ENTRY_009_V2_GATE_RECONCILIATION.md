# Entry 009 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Entry: **009 — L2TPv3**

Purpose: reconcile the L2TPv3 dossier against all completion categories in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` while correctly treating the technology as infrastructure pseudowire rather than a consumer VPN client/server.

## Status vocabulary

- `REFERENCE-PASS`: research/reference category has traceable evidence.
- `N/A-CONSUMER / PEER-MAPPED`: a consumer-client interpretation is not applicable; serious pseudowire peer/operator installation/UI evidence is provided instead.
- `BLOCKED_EXTERNAL`: remaining proof requires live kernel/router/interoperability/packet-capture environments.

## 1. Server implementation/project ecosystem mapped

`REFERENCE-PASS`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- Linux kernel L2TP subsystem pinned at `2f1baf1fc8929e6c48370be543ad028ac7ad4131`;
- iproute2 pinned at `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`;
- go-l2tp/ql2tpd pinned at `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`;
- current Cisco IOS XE L2TPv3 pseudowire implementation/documentation mapped.

## 2. Official and major installer/deployment projects reviewed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`

Covers distro kernel/iproute2 ownership, pinned go-l2tp build/service considerations, network automation, Cisco built-in feature ownership, OCI/Kubernetes constraints and anti-blind-installer rules.

## 3. Server OS/container/orchestration install matrix completed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALL_MATRIX.md`

Covers major Linux families, Linux router distributions as product-specific, Cisco IOS XE, OCI/Kubernetes and explicit non-claims for consumer OS/BSD until evidenced.

## 4. Server panel/UI/control-plane menus completed

`REFERENCE-PASS`

Evidence:

- `SERVER_UI_AND_MENUS.md`

Maps `ip l2tp`, Linux bridge/VLAN integration, ql2tpd config, Cisco IOS XE L2TP class/pseudowire/xconnect hierarchy and recommended PVNetwork admin UI/RBAC.

## 5. Client install matrix completed across relevant targets

`N/A-CONSUMER / PEER-MAPPED / REFERENCE-PASS`

Evidence:

- `CLIENT_INSTALL_MATRIX.md`

L2TPv3 is not a normal consumer VPN client. The matrix instead maps the relevant Linux/Cisco/VM/container/network-peer targets and explicitly prevents confusion with native L2TPv2/IPsec on Windows/Apple/Android.

## 6. Major client UI/menu maps completed separately

`N/A-CONSUMER / PEER-MAPPED / REFERENCE-PASS`

Evidence:

- `CLIENT_UI_AND_MENUS.md`

Maps infrastructure peer/operator UI, static pair generation, dynamic-control state, layer-2 safety and explicit hiding from consumer protocol pickers.

## 7. Cryptographic/security design documented

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`

Documents no native payload confidentiality, Session ID/cookie scope, control authentication/integrity, static vs dynamic secret classes, IPsec dependency for untrusted underlays and boundary to entry 010.

## 8. Data path/wire flow documented

`REFERENCE-PASS`

Evidence:

- `DATA_PATH_AND_WIRE_FLOW.md`

Covers static Linux kernel/netlink flow, outbound/inbound Ethernet pseudowire path, dynamic control-plane distinction, ql2tpd, Cisco, bridge/VLAN implications, direct-IP/UDP modes, MTU/ECN and safe telemetry.

## 9. Ports/transports/handshake documented

`REFERENCE-PASS`

Evidence:

- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`

Covers direct IP protocol 115, UDP/1701 control-port selection, static `ip l2tp` no-control mode, dynamic SCC*/session signaling, ql2tpd HELLO limitation, cookies, Ethernet PW, sequencing, fallback and Cisco static/signaled modes.

## 10. Deployment topologies documented

`REFERENCE-PASS`

Evidence:

- `DEPLOYMENT_TOPOLOGIES.md`

Covers Linux-Linux, Linux-Cisco, Cisco-Cisco, ql2tpd peers, VLAN/whole-LAN risks, carrier attachment circuits, direct-IP/UDP, IPsec boundary, namespaces, containers, HA limitations and migration alternatives.

## 11. Source/license/activity pins recorded

`REFERENCE-PASS`

Evidence:

- `REFERENCE_INDEX.md`
- `SERVER_IMPLEMENTATIONS.md`

Pins:

- Linux kernel current reviewed commit with SPDX source evidence;
- iproute2 current reviewed commit, GPLv2/GPL-2.0-or-later;
- go-l2tp current reviewed release/commit, MIT;
- Cisco as current proprietary network-OS interoperability reference with official current docs rather than source reuse.

## 12. Security/supply-chain risks recorded

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_UI_AND_MENUS.md`

Includes root/CAP_NET_ADMIN, kernel module provenance, opaque installer rejection, static cookie/control-secret handling, bridge/VLAN attack surface, plain-data confidentiality limitation and container host-kernel coupling.

## 13. Upgrade/uninstall/rollback behavior researched

`REFERENCE-PASS at reference layer / BLOCKED_EXTERNAL for receipts`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `CLIENT_INSTALL_MATRIX.md`

Defines safe deletion order, module ownership, kernel/iproute2 pairing, Cisco image/config rollback and restart cleanup requirements.

## 14. Protocol/server/client differences and uncertainties explicitly listed

`REFERENCE-PASS`

Evidence throughout dossier:

- L2TPv3 != L2TPv2/IPsec;
- static != dynamic control;
- direct IP != UDP;
- cookie != encryption;
- Ethernet PW != all pseudowire types;
- consumer client semantics are not applicable;
- Linux kernel/iproute2/go-l2tp != Cisco IOS XE feature model;
- entry 010 is separate protection composition.

## 15. REFERENCE_INDEX links the complete dossier

`SOURCE-PASS after synchronization`

Update `REFERENCE_INDEX.md` after this file to list all mandatory files, this reconciliation and final blockers.

## 16. Latest AGENTS handoff contains exact continuation state

`SOURCE-PASS after checkpoint`

Create a new L2TPv3 v2 handoff/checkpoint, update Run State and point `AGENTS.md` to the next active work unit.

---

# Formal source/reference result

All 16 research/reference categories have traceable evidence for entry 009, with the client categories correctly converted into **peer/operator** matrices rather than fake consumer app claims.

Recommended internal state:

`REFERENCE-V2-SOURCE-COMPLETE / ADVANCED-PSEUDOWIRE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

# Strict external blockers

Do not mark strict `COMPLETE-REFERENCE-v2` until representative evidence exists for:

1. selected Linux distro kernel/module + iproute2 install/provision/delete lifecycle;
2. static direct-IP protocol-115 Linux-to-Linux pseudowire;
3. static UDP L2TPv3 Linux-to-Linux pseudowire;
4. Ethernet/VLAN/broadcast/multicast/bridge/STP behavior;
5. cookie mismatch and sequence/reorder negative tests;
6. MTU/PMTU/ECN behavior including RFC9601-relevant implementation results;
7. ql2tpd service/restart/HELLO like-peer behavior;
8. Linux-to-Cisco IOS XE exact-version static interoperability;
9. dynamic/signaled RFC3931 interoperability against a selected full-control peer;
10. Cisco selected-platform install/feature/upgrade/rollback/show/debug receipts;
11. OCI/Kubernetes host-kernel/namespace/capability execution if retained in scope;
12. synchronized packet captures proving lack of confidentiality for entry 009;
13. entry-010 IPsec-protected data path separately.

# Promotion decision

Keep entry 009 `PENDING` in the strict v2 tracker under the repository's execution-evidence standard.

Checkpoint source/reference closure and immediately continue entry **010 L2TPv3/IPsec**, reusing entry 009 pseudowire evidence plus entries 004–007 IPsec evidence but adding the exact protection composition, selectors/encapsulation/security policy/install/UI/topology and interoperability evidence required for the combined technology.
