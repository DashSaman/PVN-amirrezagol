# 068 — XFRM/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **068 — XFRM/IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / LINUX IPSEC POLICY+STATE ARCHITECTURE / NOT A DISTINCT WIRE PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence: `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`; reuse completed IKE/IPsec/ESP/AH entries 004–007.

## Identity

Linux XFRM is the kernel framework implementing transform state/policy processing used by IPsec and related networking features. `ip xfrm` is the iproute2 user-space control interface. It is not a separate cryptographic protocol and should never be marketed as “another VPN protocol”.

Current source baseline:

- `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `net/xfrm/` and related IPsec hooks;
- `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `ip xfrm` tooling;
- cryptographic/key-management semantics are inherited from selected IKE/IPsec engine and kernel transforms.

## Product model

PVNetwork infrastructure model should keep:

- XFRM state (SA identity, SPI/protocol/mode/endpoints/algorithm references/lifetime);
- XFRM policy/selectors/direction/priority/mark/if_id;
- routing table/interface/namespace/VRF integration;
- IKE daemon/profile/credential ownership;
- runtime state vs persistent desired configuration;
- sensitive keying material excluded from ordinary diagnostics/exports.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel XFRM + iproute2 control + selected strongSwan/native IKE implementation.|
|2|Sources pinned|PASS|Exact current Linux/iproute2 and completed current IPsec pins.|
|3|Licenses|PASS|Kernel/iproute2 GPL source and separate IKE component licenses documented.|
|4|Source tree|PASS|Current `net/xfrm/`, IPsec hooks and iproute2 XFRM tooling plus completed IPsec maps.|
|5|Languages/build|PASS|Kernel/iproute2 C; selected IKE stack already mapped.|
|6|Architecture|PASS|IKE/control plane installs XFRM state/policy; kernel transforms matching packets; routing/interface layers remain separate.|
|7|Engine integration|PASS|Use native netlink/XFRM and approved IKE/IPsec adapter; no custom cryptography.|
|8|UI/menu|PASS/N-A|No consumer GUI; admin/debug UI should expose policy/state/SA health only in advanced infrastructure mode.|
|9|Config/import|PASS|Policy/state/selectors/marks/if_id/lifetimes and references to IPsec credentials/profiles are typed; runtime dumps are not portable profiles.|
|10|Persistence/secrets|PASS|XFRM cryptographic keying/SAs are sensitive runtime state; credentials live in IPsec secure storage; persistent policy is separate.|
|11|Platforms|PASS for research|Linux-specific architecture; other OS IPsec stacks are different and not called XFRM.|
|12|Logs/diagnostics|PASS|Policy lookup, SA state, replay/lifetime, route/mark/interface, IKE negotiation and transform failures separated.|
|13|Assets|PASS/N-A|No canonical consumer assets.|
|14|Alternatives|PASS|VTI/XFRM interfaces/native vendor route-based IPsec are related but separate abstractions.|
|15|Issues/releases|PASS|Current Linux/iproute2 and selected IPsec maintenance evidence pinned.|
|16|Docs/forums|PASS|Current kernel/iproute2 source plus IPsec standards/dossiers are authoritative.|
|17|Tests/CI|PASS|Kernel XFRM/IPsec selftest/review ecosystem and selected IKE tests exist; product policy/packet tests later.|
|18|Store/privacy/security|PASS|Keying/state dumps are highly sensitive; consumer Store role N/A; least-privilege networking helper required.|
|19|Reuse decision|PASS|Use Linux native XFRM APIs only as platform adapter; never fork/market XFRM as a separate VPN engine.|
|20|Uncertainties|PASS|Exact xfrm-interface/mark/policy architecture, kernel/version matrix, offload/vendor behavior and V2 installer/UI/topology evidence remain later.|

## Final V1 decision

Entry 068 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
