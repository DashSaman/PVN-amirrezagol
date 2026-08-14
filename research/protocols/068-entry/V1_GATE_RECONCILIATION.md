# 068 — XFRM/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **068 — XFRM/IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / LINUX IPSEC POLICY+STATE ARCHITECTURE / NOT A DISTINCT WIRE PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`
- completed IKE/IPsec/ESP/AH entries 004–007

## Identity

Linux XFRM is the kernel framework implementing transform state/policy processing used by IPsec and related networking features. `ip xfrm` is the iproute2 userspace control interface. It is not a separate cryptographic protocol and must not be marketed as “another VPN protocol”.

Current reviewed source baseline:

- `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `net/xfrm/` and related IPsec hooks;
- `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `ip xfrm` tooling;
- cryptographic/key-management semantics are inherited from selected completed IKE/IPsec engines and kernel transforms.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel XFRM + iproute2 control + the already completed maintained IKE/IPsec implementation evidence are the selected primary architecture.|
|2|Sources pinned|PASS|Exact Linux `ad8d485e...`, iproute2 `da2ccdf...` and completed IPsec source/standard pins are traceable; stable versus prerelease Linux tags are separated.|
|3|Licenses|PASS|Linux root `GPL-2.0 WITH Linux-syscall-note`, iproute2 GPLv2, file-level SPDX and commercial/distribution/modification/source/notice/network-use/linking/API/trademark/Store boundaries are explicitly audited; selected IKE component licenses remain separate.|
|4|Source tree|PASS|Pinned complete recursive Linux/iproute2 tree manifests cover `net/xfrm/`, headers/UAPI/hooks and `ip xfrm` control, plus build/tests/package/docs boundaries; completed IPsec dossiers cover IKE/ESP/AH maps.|
|5|Languages/build|PASS|Relevant kernel/iproute2 XFRM code is C; Kbuild/Kconfig/iproute2 build and test/package boundaries plus the selected IKE stack are mapped.|
|6|Architecture|PASS|IKE/control plane installs XFRM state/policy; kernel transforms matching packets; routing/interface, policy/state and credential ownership remain distinct.|
|7|Engine integration|PASS|Use native netlink/XFRM and approved IKE/IPsec adapter; no custom cryptography. Capability/version checks and least-privilege helper boundaries are required.|
|8|UI/menu|PASS/N-A|No canonical consumer GUI; advanced admin/debug UI may expose policy/state/SA health only without revealing keying material.|
|9|Config/import|PASS|Policy/state/selectors/marks/if_id/lifetimes and references to credential/profiles are typed. No canonical XFRM consumer URI/QR exists; runtime dumps are not portable profiles.|
|10|Persistence/secrets|PASS|XFRM cryptographic keying/SAs are sensitive runtime state; credentials live in IPsec secure storage; persistent desired policy is separate and exports exclude keys by default.|
|11|Platforms|PASS for research|XFRM is Linux-specific; other OS IPsec stacks are distinct implementations and must not be called XFRM or assumed compatible without independent evidence.|
|12|Logs/diagnostics|PASS|Policy lookup, SA state, replay/lifetime, route/mark/interface, IKE negotiation and transform failures are separated; support bundles redact keying state.|
|13|Assets|PASS/N-A|No canonical consumer application/store asset surface applies.|
|14|Alternatives|PASS|VTI, XFRM interfaces, policy routing and vendor route-based IPsec are related but separate abstractions; downstream kernels require separate version pins rather than being treated as canonical forks.|
|15|Issues/releases|PASS|Canonical `net/xfrm/` maintenance history was reviewed. Pinned source includes 2026 fixes `f38f8cce...` (policy hash rebuild failure/panic path), `430ea57d...` (shared-fragment propagation preventing unsafe ESP memory corruption/panic behavior) and `2538bd3c...` (stale mode callback after failed setup). Release/tag and iproute2 maintenance boundaries are recorded in shared evidence.|
|16|Docs/forums|PASS|Pinned kernel/iproute2 source/docs, completed IPsec standards/dossiers and accepted upstream maintenance discussions are authoritative; community tutorials are secondary only.|
|17|Tests/CI|PASS|Kernel XFRM/network selftests/review and selected IKE tests are mapped. Product policy/packet/device/interop testing remains later acceptance evidence, not a hidden V1 completion gate.|
|18|Store/privacy/security|PASS|Keying/state dumps are highly sensitive; least-privilege networking access is required; consumer Store role N/A; XFRM itself is not a separate security protocol claim.|
|19|Reuse decision|PASS|Use native Linux XFRM APIs only as a platform adapter with approved IKE/IPsec engines; never fork/market XFRM as another VPN engine.|
|20|Uncertainties|PASS|Exact xfrm-interface/mark/policy architecture, deployment kernel/downstream matrix, offload/vendor behavior and V2 installer/UI/topology evidence remain explicitly later.|

## Final V1 decision

All 20 gates are evidence-backed or correctly infrastructure-N/A bounded. Entry 068 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
