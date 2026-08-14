# 063 — GRE — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **063 — GRE**

Decision: **`COMPLETE-RESEARCH-v1 / INFRASTRUCTURE TUNNEL / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`

## Identity / authority

- RFC 2784 defines GRE; RFC 2890 defines key and sequence-number extensions.
- Linux kernel reviewed source: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, notably `net/ipv4/ip_gre.c`; pinned recursive tree SHA `cdfb6ad04701df82290575494f40fbb00efe0512` is recorded in the shared tree audit.
- iproute2 reviewed control source: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, notably `ip/link_gre.c`; pinned recursive tree SHA `c822a724f3a2d2cb8ca93b2329358ee90d02c2e2` is recorded in the shared tree audit.
- Linux/iproute2 are GPL source/reference components. PVNetwork should use structured OS/netlink facilities rather than copy/link these implementations into a closed application without separate legal review.

GRE encapsulates another protocol; it does **not** provide confidentiality or cryptographic peer authentication. GRE keys, sequence numbers and checksums are not encryption keys.

## Product model

Typed state includes local/remote underlay endpoints, GRE/GRETAP role, key/checksum/sequence flags, TTL/TOS/DF/encap options where supported, physical-device/VRF/namespace binding, MTU/route state and implementation capability metadata. Persistent desired config is distinct from live kernel state.

Consumer GUI is `N/A-CONSUMER`; if exposed by PVNetwork it belongs under advanced infrastructure/site-to-site UI with peer/tunnel status and diagnostics. There is no canonical GRE subscription URI or QR interchange format; PVNetwork must not invent one and call it standard.

## 20-gate reconciliation

| # | Gate | Result | Evidence/conclusion |
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel is the primary current GRE implementation reference and iproute2 is its userspace control-plane reference; vendor network OSes are separately versioned interoperability targets when selected.|
|2|Sources pinned|PASS|RFC2784/2890 plus exact Linux `ad8d485e...` and iproute2 `da2ccdf...` source-analysis commits; stable/pre-release tag status is separated in the source-tree audit.|
|3|Licenses|PASS|Linux root `GPL-2.0 WITH Linux-syscall-note`, iproute2 GPLv2, file-level SPDX, distribution/modification/source/notice/network-use/linking/API/trademark/Store boundaries and `REFERENCE-ONLY` source-copy decision are explicitly recorded in the legal audit.|
|4|Source tree|PASS|Complete recursive tree API references are pinned to Linux tree `cdfb6ad...` and iproute2 tree `c822a724...`; top-level purposes, GRE paths, build scripts, tests, packaging, docs/platform boundaries are inventoried.|
|5|Languages/build|PASS|Relevant kernel/iproute2 implementation is C; Kbuild/Kconfig/root Makefile, iproute2 Makefile/development guidance, tests and packaging boundaries are mapped in the source-tree audit.|
|6|Architecture|PASS|Underlay IP -> GRE encapsulation -> inner protocol/interface -> routing, with kernel data plane and userspace/netlink control plane kept separate.|
|7|Engine integration|PASS|Use native Linux/netlink/network-OS APIs; no custom GRE cryptography/core is required. Capability/version checks must be explicit.|
|8|UI/menu|PASS/N-A|No canonical consumer GUI exists for the selected implementation. Admin CLI/network UI role and PVNetwork Advanced/Infrastructure placement are explicitly bounded.|
|9|Config/import|PASS|Endpoint/key/flags/device/route/MTU fields are mapped. No standard GRE URI/QR exists; raw `ip ... show` output is not treated as a portable profile; imports/exports require typed product or selected system schema.|
|10|Persistence/secrets|PASS|GRE has no cryptographic secret; GRE key is metadata. Persistent host desired config and runtime kernel state are separated; topology metadata still receives privacy-conscious diagnostics handling.|
|11|Platforms|PASS for research|Linux is the selected primary architecture; vendor/other OS implementations require their own future pin. Mobile consumer role is N/A absent a concrete native infrastructure use case.|
|12|Logs/diagnostics|PASS|Underlay, interface creation, route/table, GRE decapsulation, MTU/PMTU, firewall/NAT, namespace/device and peer failure domains are mapped separately.|
|13|Assets|PASS/N-A|No canonical consumer asset/icon/store-artwork surface applies to Linux GRE administration; documentation/admin UX is not misrepresented as consumer assets.|
|14|Alternatives|PASS|No protocol-specific maintained fork is promoted without evidence. Distribution/vendor kernels are downstream targets needing separate version pins; GRETAP and GRE-over-IPsec are distinct modes/compositions, with entry 064 covering the protected composition.|
|15|Issues/releases|PASS|Canonical GRE path history was reviewed. Source pin includes 2026 fixes `675ed582...` (LLTX SEQ/CSUM regression), `8165f7ff...` (cross-netns CAP_NET_ADMIN changelink authorization) and common tunnel race fix `80a7e350...`; iproute2 GRE path maintenance and release tags were also reviewed. Runtime regression certification remains later, not a hidden V1 gate.|
|16|Docs/forums|PASS|RFC2784/2890, pinned kernel source/Documentation, pinned iproute2 source/man/development/security docs and accepted kernel mailing-list fix links are primary; community tutorials are not elevated over them.|
|17|Tests/CI|PASS|Kernel in-tree selftests/review ecosystem and iproute2 `testsuite/` are inventoried. No fabricated GitHub Actions result is claimed; PVNetwork packet/device/interop tests remain later acceptance evidence.|
|18|Store/privacy/security|PASS|GRE provides no encryption/authentication; topology/endpoints are potentially sensitive; consumer Store role is N/A; raw GRE on an untrusted underlay must not be marketed as secure VPN.|
|19|Reuse decision|PASS|Native OS/network infrastructure adapter; Linux/iproute2 source is reference/API evidence rather than copied implementation; no protocol-count-driven consumer exposure.|
|20|Uncertainties|PASS|Vendor/downstream kernel interoperability, exact deployment kernel/iproute2 versions, IPv6/GRETAP options, MTU/NAT/firewall behavior and V2 installer/UI/wire/topology evidence remain explicitly later.|

## Final V1 decision

All 20 V1 research gates are now evidence-backed or correctly infrastructure-N/A bounded. The previously weak source-tree, legal/config and maintenance evidence has been closed by the two shared supplemental audits. Entry 063 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining **not implemented/certified**.
