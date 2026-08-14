# 065 — IP-in-IP / IPIP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **065 — IP-in-IP / IPIP**

Decision: **`COMPLETE-RESEARCH-v1 / INFRASTRUCTURE IP TUNNEL / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`

## Identity / source

- RFC 2003 defines IPv4-in-IPv4 encapsulation.
- Linux implementation pin: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `net/ipv4/ipip.c`.
- iproute2 pin: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78` plus generic tunnel/link/route tooling.
- Kernel/iproute2 GPL source is behavior/API evidence; PVNetwork should use native networking APIs rather than copy/link GPL tunnel/control code into the product without separate legal review.

IPIP adds an outer IP header around an inner IP packet. It provides **no encryption, integrity or cryptographic authentication**.

## Product model

Typed state: local/remote underlay endpoints, inner/outer family/capability, link/device/namespace/VRF binding, TTL/TOS/DF/PMTU behavior where supported, MTU/routes and implementation/version metadata. Firewall/NAT and persistence are separate host-network policy.

Consumer GUI is N/A; if exposed, place under Advanced / Infrastructure / Site-to-Site. There is no canonical IPIP consumer subscription URI/QR format.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel/iproute2 are the selected current implementation/control references; network-OS implementations are separately versioned interop targets when selected.|
|2|Sources pinned|PASS|RFC2003 plus exact Linux `ad8d485e...` and iproute2 `da2ccdf...` source-analysis pins; stable/pre-release boundaries are documented.|
|3|Licenses|PASS|Linux `GPL-2.0 WITH Linux-syscall-note`, iproute2 GPLv2, file-level SPDX, commercial/distribution/modification/source/notice/network-use/linking/API/trademark/Store boundaries and reference-only source reuse are explicitly audited.|
|4|Source tree|PASS|Pinned complete recursive Linux/iproute2 tree references plus `net/ipv4/ipip.c`, common tunnel/control paths, build/tests/packaging/docs inventories are traceable in the source-tree audit.|
|5|Languages/build|PASS|Relevant kernel/iproute2 code is C; Kbuild/Kconfig, root/iproute2 Makefiles, tests and packaging boundaries are mapped.|
|6|Architecture|PASS|Inner IP -> IPIP encapsulation -> outer IPv4 underlay -> peer decapsulation/routing; data plane and userspace control plane remain distinct.|
|7|Engine integration|PASS|Native kernel/netlink/network-OS adapter; no custom cryptography or forked IPIP core is required.|
|8|UI/menu|PASS/N-A|No canonical consumer UI; peer/admin configuration/status role is explicitly bounded to infrastructure UI.|
|9|Config/import|PASS|Endpoints/device/route/MTU/outer-header fields are mapped; no standard URI/QR exists; runtime kernel command output is not treated as a portable profile.|
|10|Persistence/secrets|PASS|No IPIP cryptographic secret exists; persistent desired config vs runtime link/route state is separated. Endpoint/topology data is still handled as potentially sensitive.|
|11|Platforms|PASS for research|Linux primary; vendor/downstream OS support is implementation-specific and must be pinned independently; mobile consumer role N/A absent a concrete native use case.|
|12|Logs/diagnostics|PASS|Underlay, encapsulation, route, PMTU/fragmentation, firewall/NAT, namespace/device and cleanup failures are separated.|
|13|Assets|PASS/N-A|No canonical consumer application/store asset surface applies.|
|14|Alternatives|PASS|No unsupported protocol-specific fork is promoted. Distribution/vendor kernels are downstream targets; GRE, VXLAN and IPIP-over-IPsec are distinct technologies/compositions.|
|15|Issues/releases|PASS|Current IPIP path history was reviewed. Pinned source includes `8211a263...` cross-netns CAP_NET_ADMIN fix and common tunnel race fix `80a7e350...`; release/tag and iproute2 maintenance boundaries are recorded in the shared maintenance audit.|
|16|Docs/forums|PASS|RFC2003, pinned kernel source/Documentation, iproute2 source/man/development/security docs and accepted maintenance discussion links are primary.|
|17|Tests/CI|PASS|Kernel networking selftests/review and iproute2 `testsuite/` are mapped; PVNetwork packet/device/interop tests remain later acceptance evidence, not a hidden V1 gate.|
|18|Store/privacy/security|PASS|Raw IPIP is cleartext; endpoint/topology metadata may be sensitive; consumer Store role N/A; IPIP must not be marketed as encrypted VPN on an untrusted underlay.|
|19|Reuse decision|PASS|Use native OS networking adapter only for a real infrastructure use case; kernel/iproute2 source remains reference/API evidence and no source fork is required.|
|20|Uncertainties|PASS|IPv6 variants/vendor/downstream interop, PMTU/NAT/firewall matrix, exact deployment OS versions and V2 installer/UI/wire evidence remain later.|

## Final V1 decision

All 20 gates are evidence-backed or correctly infrastructure-N/A bounded through the numbered and hardened shared audits. Entry 065 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
