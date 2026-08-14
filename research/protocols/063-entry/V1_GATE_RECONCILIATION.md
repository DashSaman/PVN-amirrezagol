# 063 — GRE — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **063 — GRE**

Decision: **`COMPLETE-RESEARCH-v1 / INFRASTRUCTURE TUNNEL / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence: `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`

## Identity / authority

- RFC 2784 defines GRE; RFC 2890 defines key and sequence-number extensions.
- Linux kernel current reviewed source: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, notably `net/ipv4/ip_gre.c`.
- Current iproute2 control source: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, notably `ip/link_gre.c`.
- Linux/iproute2 are GPL source/reference components; PVNetwork should use structured OS/netlink facilities rather than copy them into a closed app.

GRE encapsulates another protocol; it does **not** provide confidentiality or cryptographic peer authentication. GRE keys, sequence numbers and checksums are not encryption keys.

## Product model

Typed state includes local/remote underlay endpoints, GRE/GRETAP role, key/checksum/sequence flags, TTL/TOS/DF/encap options where supported, physical-device/VRF/namespace binding, MTU/route state and implementation capability metadata. Persistent desired config is distinct from live kernel state.

Consumer GUI is `N/A-CONSUMER`; if exposed by PVNetwork it belongs under advanced infrastructure/site-to-site UI with peer/tunnel status and diagnostics.

## 20-gate reconciliation

| # | Gate | Result | Evidence/conclusion |
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel/iproute2 are current primary implementation/control references; vendor network OSes are interop references when selected.|
|2|Sources pinned|PASS|Exact 2026 Linux/iproute2 commits plus RFC2784/2890.|
|3|Licenses|PASS|Kernel/iproute2 GPL path boundaries documented; no source-copy assumption.|
|4|Source tree|PASS|Relevant GRE kernel/control paths pinned in shared evidence.|
|5|Languages/build|PASS|Kernel C; iproute2 C/build/package ecosystem.|
|6|Architecture|PASS|Underlay IP -> GRE encapsulation -> inner protocol/interface -> routing, with OS control plane separate.|
|7|Engine integration|PASS|Use native Linux/netlink/network-OS APIs; no custom GRE crypto/core needed.|
|8|UI/menu|PASS/N-A|No canonical consumer GUI; admin CLI/network UI role explicitly mapped.|
|9|Config/import|PASS|Endpoint/key/flags/device/route/MTU fields mapped; live `ip` output not treated as portable profile.|
|10|Persistence/secrets|PASS|GRE has no cryptographic secret; key field is metadata. Persistent host config vs runtime kernel state separated.|
|11|Platforms|PASS for research|Linux primary; vendor/other OS support is implementation-specific; mobile consumer role N/A unless a concrete native path is later selected.|
|12|Logs/diagnostics|PASS|Underlay, interface, route, decapsulation, MTU/PMTU, firewall/NAT and peer failure domains mapped.|
|13|Assets|PASS/N-A|No canonical consumer assets.|
|14|Alternatives|PASS|GRETAP/vendor implementations and protected GRE-over-IPsec are distinct modes/entry 064.|
|15|Issues/releases|PASS|Current Linux/iproute2 maintenance is pinned; exact deployment kernel/version regression review is later.|
|16|Docs/forums|PASS|RFCs + current kernel/iproute2 source/manual behavior are primary.|
|17|Tests/CI|PASS|Kernel/iproute2 upstream test/review ecosystem exists; PVNetwork packet/interop tests remain later.|
|18|Store/privacy/security|PASS|No encryption; topology/endpoints may be sensitive; consumer Store role normally N/A; raw GRE on untrusted underlay not marketed secure.|
|19|Reuse decision|PASS|Native OS/network infrastructure adapter; no GPL code copy and no protocol-count-driven consumer exposure.|
|20|Uncertainties|PASS|Vendor interop, exact kernel/iproute2 matrix, IPv6/GRETAP options, MTU/NAT and V2 installer/UI/wire/topology evidence remain later.|

## Final V1 decision

All 20 V1 research gates are evidence-backed or correctly infrastructure-N/A bounded. Entry 063 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **not implemented/certified**.
