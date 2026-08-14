# 065 — IP-in-IP / IPIP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **065 — IP-in-IP / IPIP**

Decision: **`COMPLETE-RESEARCH-v1 / INFRASTRUCTURE IP TUNNEL / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence: `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`.

## Identity / source

- RFC 2003 defines IPv4-in-IPv4 encapsulation.
- Current Linux implementation pin: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `net/ipv4/ipip.c`.
- Current iproute2 pin: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78` plus generic tunnel/link/route tooling.
- Kernel/iproute2 GPL source is behavior/API evidence; PVNetwork should use native networking APIs rather than copy GPL tunnel code.

IPIP adds an outer IP header around an inner IP packet. It provides **no encryption, integrity or cryptographic authentication**.

## Product model

Typed state: local/remote underlay endpoints, inner/outer family/capability, link/device/namespace/VRF binding, TTL/TOS/DF/PMTU behavior where supported, MTU/routes and implementation/version metadata. Firewall/NAT and persistence are separate host-network policy.

Consumer GUI is N/A; if exposed, place under Advanced / Infrastructure / Site-to-Site.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel/iproute2 are current primary references; network OS implementations are later interop targets.|
|2|Sources pinned|PASS|RFC2003 plus exact current Linux/iproute2 pins.|
|3|Licenses|PASS|Linux/iproute2 GPL boundaries documented; no source-copy assumption.|
|4|Source tree|PASS|`net/ipv4/ipip.c` and shared iproute2/kernel tunnel paths pinned.|
|5|Languages/build|PASS|Kernel C; iproute2 C/system packages.|
|6|Architecture|PASS|Inner IP -> IPIP encapsulation -> outer IPv4 underlay -> peer decapsulation/routing.|
|7|Engine integration|PASS|Native kernel/netlink/network-OS adapter; no custom crypto/core.|
|8|UI/menu|PASS/N-A|No canonical consumer UI; peer/admin configuration/status role mapped.|
|9|Config/import|PASS|Endpoints/device/route/MTU/outer-header options mapped; runtime kernel state not treated as portable profile.|
|10|Persistence/secrets|PASS|No IPIP cryptographic secret; persistent desired config vs runtime link/route state separated.|
|11|Platforms|PASS for research|Linux primary; vendor OS support implementation-specific; mobile consumer role N/A absent concrete native use case.|
|12|Logs/diagnostics|PASS|Underlay, encapsulation, route, PMTU/fragmentation, firewall/NAT and cleanup failures separated.|
|13|Assets|PASS/N-A|No canonical app assets.|
|14|Alternatives|PASS|GRE, VXLAN and IPIP-over-IPsec are separate entries/compositions.|
|15|Issues/releases|PASS|Current Linux/iproute2 maintenance pinned; exact deployment regressions remain later.|
|16|Docs/forums|PASS|RFC2003 + current kernel/iproute2 source/docs are primary.|
|17|Tests/CI|PASS|Kernel/iproute2 upstream quality evidence; PVNetwork packet/interop tests later.|
|18|Store/privacy/security|PASS|Raw IPIP is cleartext; endpoint/topology metadata sensitive; consumer Store role N/A.|
|19|Reuse decision|PASS|Use native OS networking adapter only when infrastructure use case exists; do not market as secure VPN.|
|20|Uncertainties|PASS|IPv6 variants/vendor interop, PMTU/NAT/firewall matrix, exact OS versions and V2 installer/UI/wire evidence remain later.|

## Final V1 decision

Entry 065 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
