# 066 — IPIP over IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **066 — IPIP over IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / SITE-TO-SITE COMPOSITION / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence: `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`; reuse completed IPsec entries 004–007 and IPIP entry 065.

## Composition boundary

IPIP supplies IP-in-IP encapsulation (RFC2003). IPsec/IKE/ESP supplies cryptographic protection. Product state must independently represent IPIP outer/inner endpoints, route/MTU behavior and the IPsec identities/credentials/selectors/SAs protecting that tunnel traffic.

An IPIP link plus an unrelated IPsec SA is **not** evidence of protected IPIP. Later certification must prove selector/policy match and no unintended cleartext path.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux IPIP/iproute2 plus completed strongSwan/native XFRM/IPsec evidence; vendor peers later.|
|2|Sources pinned|PASS|Current Linux/iproute2 IPIP source + completed current IPsec pins/dossiers + RFC2003/IPsec standards.|
|3|Licenses|PASS|GPL Linux/iproute2 and selected IPsec component licenses remain separately audited.|
|4|Source tree|PASS|IPIP kernel/control and completed XFRM/IKE/ESP maps are traceable.|
|5|Languages/build|PASS|Linux/iproute2 C plus selected IPsec toolchain already documented.|
|6|Architecture|PASS|Inner IP -> IPIP -> XFRM/ESP -> underlay; route/policy/SA planes separate.|
|7|Engine integration|PASS|Native IPIP/netlink + approved IPsec adapter; no new crypto.|
|8|UI/menu|PASS/N-A|Infrastructure peer/admin UI; consumer GUI N/A. Protection status shown separately from IPIP link state.|
|9|Config/import|PASS|IPIP fields + IPsec credential/policy references + selectors/routes/MTU mapped.|
|10|Persistence/secrets|PASS|IPIP has no secret; IPsec secrets follow completed secure-storage rules.|
|11|Platforms|PASS for research|Linux/network-OS infrastructure role; mobile consumer N/A absent use case.|
|12|Logs/diagnostics|PASS|Underlay/IPIP/route vs IKE/SA/XFRM/ESP/firewall/MTU failures separated.|
|13|Assets|PASS/N-A|No canonical consumer assets.|
|14|Alternatives|PASS|GRE-over-IPsec/VTI/XFRM are separate architectures.|
|15|Issues/releases|PASS|Current Linux/iproute2 and IPsec maintenance evidence; interop later.|
|16|Docs/forums|PASS|RFC2003, IPsec standards/dossiers and current Linux source.|
|17|Tests/CI|PASS|Upstream test ecosystems; later packet capture must prove encryption/protection.|
|18|Store/privacy/security|PASS|Security depends on IPsec; no hidden cleartext fallback; consumer Store N/A.|
|19|Reuse decision|PASS|Compose native IPIP and approved IPsec engine behind infrastructure adapter.|
|20|Uncertainties|PASS|Exact selectors/NAT/PMTU/vendor interoperability and V2 deployment/UI/wire evidence remain later.|

## Final V1 decision

Entry 066 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
