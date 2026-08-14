# Entry 009 — L2TPv3 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Decision: **`COMPLETE-RESEARCH-v1 / ADVANCED-SITE-TO-SITE-PSEUDOWIRE / NOT IMPLEMENTED`**

This audit reconciles entry 009 against the exact original 20 research gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`. L2TPv3 is treated as an infrastructure pseudowire technology, not a consumer VPN. Consumer-app categories therefore use evidence-backed `N/A-CONSUMER / PEER-MAPPED` treatment rather than invented mobile/desktop client support.

## Traceable baseline

Standards/reference:

- RFC 3931 — Layer Two Tunneling Protocol Version 3 (L2TPv3): https://www.rfc-editor.org/rfc/rfc3931.html
- RFC 4719 — Ethernet Pseudowire Type for L2TPv3: https://www.rfc-editor.org/rfc/rfc4719.html

Pinned implementation references already recorded in the v2 dossier:

- Linux kernel L2TP subsystem reviewed commit `2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- iproute2 reviewed commit `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- `katalix/go-l2tp` / ql2tpd reviewed commit `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- Cisco IOS XE L2TPv3 as a proprietary current interoperability/reference target documented from current Cisco configuration/reference material

Repository evidence:

- `research/protocols/009-l2tpv3/V1_RESEARCH.md`
- complete `research/upstreams/classic-tunnels-family/l2tpv3-reference-v2/` dossier
- `research/upstreams/classic-tunnels-family/REFERENCE_PINS_2026-08-14.md`
- `research/upstreams/classic-tunnels-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/classic-tunnels-family/DEPENDENCIES_SECURITY_TESTS.md`

## 20 original-v1 gates

### 1. Top clients/implementations — PASS

The serious implementation set is infrastructure-focused:

- Linux kernel L2TP subsystem + iproute2 as the primary Linux data/control configuration reference;
- go-l2tp/ql2tpd as a maintained userspace L2TPv3 component/control reference;
- Cisco IOS XE as a major proprietary network-OS interoperability target.

A normal consumer mobile/desktop VPN app is not a meaningful L2TPv3 client category and is therefore not invented.

### 2. Canonical sources pinned — PASS

Linux kernel, iproute2 and go-l2tp are pinned to immutable reviewed commits above. RFC 3931/RFC 4719 are canonical standards references. Cisco is proprietary and is referenced through official network-OS documentation rather than a fabricated source SHA.

### 3. Licenses reviewed — PASS

The dossier records Linux kernel SPDX/source licensing, iproute2 GPL family, go-l2tp MIT and Cisco proprietary/reference-only status. Reuse is component-specific; no Cisco source/UI assets are copied.

### 4. Complete source-tree reference/manifest — PASS

Pinned public repositories are available at immutable revisions and relevant kernel/iproute2/go-l2tp source areas are mapped in the dossier. PVNetwork stores source references rather than mirroring complete third-party trees.

### 5. Languages/build systems — PASS

Linux kernel/iproute2 native C and go-l2tp Go ownership are mapped, including distro/kernel/package/service boundaries. Cisco implementation language/build internals are proprietary and are not invented.

### 6. Architecture — PASS

The architecture dossier distinguishes:

- kernel L2TPv3 tunnel/session/data-plane objects;
- netlink/iproute2 configuration path;
- static no-control vs dynamically signaled control models;
- direct-IP protocol 115 vs UDP transport;
- Ethernet pseudowire attachment to bridge/VLAN/network interfaces;
- optional external protection boundary handled separately by entry 010.

### 7. Core/engine integration — PASS

PVNetwork integration direction is kernel/OS-first with a typed privileged helper/service for configuration. Consumer UI must not talk directly to raw netlink/router CLI. go-l2tp may be studied/wrapped only for roles it actually implements. No cryptography is invented because plain L2TPv3 has no native confidentiality boundary.

### 8. UI/menu map — PASS / N-A-CONSUMER

`SERVER_UI_AND_MENUS.md` and `CLIENT_UI_AND_MENUS.md` map operator/peer workflows: tunnel/session creation, peer addressing, transport, Session IDs, cookies, Ethernet pseudowire attachment, bridge/VLAN integration, state/diagnostics and deletion. Consumer protocol-picker UI is N/A and must hide L2TPv3 from normal remote-access choices.

### 9. Config/import/export — PASS

The dossier maps peer addresses, tunnel/session IDs, direct-IP/UDP transport selection, ports where relevant, cookies, sequencing, interface/pseudowire type and bridge/VLAN attachment. Generic consumer QR/subscription formats are N/A. Product-level export/import, if ever added, would be a typed infrastructure profile rather than a wire-standard feature.

### 10. Persistence/secrets — PASS

Static/dynamic control secrets/cookies, peer metadata and privileged OS configuration ownership are separated. A cookie is not an encryption key. Secrets must use protected references and be redacted from logs/exports; kernel/OS runtime objects remain system-owned state.

### 11. Platform integrations — PASS / PEER-MAPPED

- Linux: primary kernel/iproute2 implementation path.
- Cisco IOS XE: major network-peer/router reference.
- VM/container/network-namespace use: infrastructure-specific and host-kernel dependent.
- Windows, Android, Android TV, iOS/iPadOS, macOS: no consumer L2TPv3 client support is claimed; these are evidence-backed N/A/unsupported-unverified for this product role.

### 12. Logs/diagnostics — PASS

The dossier maps tunnel/session state, transport mode, peer/session identifiers, interface/bridge attachment, packet/drop counters where available, configuration failure categories and cleanup. Secret/cookie values are not emitted by default.

### 13. Asset/screenshot references — PASS / N-A-CONSUMER

Relevant visual references are infrastructure documentation/CLI/control-plane material. No consumer app assets are required. Cisco/vendor screenshots and branding remain reference-only and are not approved for copying.

### 14. Meaningful forks — PASS

The review compares materially different implementation families rather than treating forks as interchangeable: Linux kernel/iproute2, go-l2tp userspace control/component and Cisco IOS XE proprietary implementation. No fork is promoted without a demonstrated maintenance/feature advantage.

### 15. Issues/PRs/releases/advisories — PASS

The dossier captures implementation limits and interoperability risks including direct-IP vs UDP mode, static vs dynamic control, cookie/sequence behavior, MTU/PMTU/ECN, bridge/VLAN blast radius and host-kernel/container coupling. Exact selected-release regressions remain a release-freeze task, not an unresearched category.

### 16. Relevant forums/docs — PASS

RFCs, Linux/iproute2/go-l2tp source documentation and Cisco official documentation are the primary references. Community examples are secondary and cannot override protocol/source evidence.

### 17. Tests/CI — PASS

Required quality evidence and missing coverage are explicitly mapped. Future acceptance tests include Linux provision/delete lifecycle, direct-IP and UDP pseudowires, cookie/sequence negative cases, Ethernet/VLAN/broadcast/multicast/bridge behavior, MTU/PMTU/ECN, ql2tpd restart/control behavior and Linux/Cisco interoperability. No runtime success is fabricated.

### 18. Store/privacy/security implications — PASS / N-A-CONSUMER

L2TPv3 is an infrastructure feature, not a consumer Store VPN protocol. It provides encapsulation, not confidentiality. PVNetwork must keep it out of normal consumer protocol selection, require privileged typed configuration, protect secrets and pair it with an explicit security layer when confidentiality/integrity is required over untrusted networks.

### 19. PVNetwork reuse decision — PASS

Decision: **Linux kernel/OS implementation first; typed infrastructure adapter; interoperability-study go-l2tp/Cisco; no consumer default.**

- reuse OS/kernel facilities where appropriate;
- do not reimplement L2TPv3 data plane from scratch;
- do not present a cookie as encryption;
- do not claim entry 010 protection semantics for plain entry 009;
- require explicit operator-level topology intent.

### 20. Uncertainties — PASS

Explicit remaining implementation/certification uncertainties:

- exact selected Linux kernel/distro/iproute2 combination;
- direct-IP protocol-115 and UDP mode runtime interoperability;
- Linux-to-Cisco exact-version behavior;
- ql2tpd dynamic-control scope and peer-specific behavior;
- MTU/PMTU/ECN and sequence/reordering behavior;
- bridge/STP/VLAN/broadcast/multicast consequences in target topologies;
- container/network-namespace privilege/host-kernel constraints;
- runtime packet captures and performance/resource measurements;
- any Store/consumer exposure remains intentionally N/A unless scope changes.

These are implementation/certification residuals, not hidden V1 research gates.

## V1 completion decision

All 20 original research categories are evidence-backed or evidence-backed N/A. Entry **009 — L2TPv3** may therefore be promoted to **`COMPLETE-RESEARCH-v1`**.

This does not imply encryption, `COMPLETE-REFERENCE-v2`, implementation, runtime interoperability, device testing or production support.
