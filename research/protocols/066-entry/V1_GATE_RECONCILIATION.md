# 066 — IPIP over IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **066 — IPIP over IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / SITE-TO-SITE COMPOSITION / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`
- completed entry 065 for IPIP
- completed entries 004–007 for IKE/IPsec/ESP/AH

## Composition boundary

IPIP supplies IP-in-IP encapsulation (RFC2003). IPsec/IKE/ESP supplies cryptographic protection. Product state independently represents IPIP endpoints/route/MTU behavior and IPsec identities/credentials/selectors/SAs protecting that tunnel traffic.

An IPIP link plus an unrelated IPsec SA is **not** evidence of protected IPIP. Later certification must prove selector/policy match and absence of unintended cleartext; that runtime receipt is not a hidden V1 research gate.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux IPIP/iproute2 plus the completed maintained IPsec/IKE implementation evidence form the selected composition; vendor peers are independently pinned interop targets when chosen.|
|2|Sources pinned|PASS|RFC2003 + exact Linux/iproute2 IPIP source pins from entry 065/shared audits + exact completed IPsec source/standards pins from entries 004–007.|
|3|Licenses|PASS|Linux/iproute2 GPL/syscall/source-copy boundaries are fully recorded in the shared legal audit; IPsec component licenses remain separately authoritative in completed entries 004–007.|
|4|Source tree|PASS|Complete pinned Linux/iproute2 recursive source-tree manifests cover IPIP/control; completed IPsec dossiers cover IKE/XFRM/ESP. The composition creates no additional source tree.|
|5|Languages/build|PASS|Linux/iproute2 C build/test/package boundaries and selected IPsec toolchain are already mapped; no new build system is invented.|
|6|Architecture|PASS|Inner IP -> IPIP -> XFRM/IPsec policy/ESP -> underlay; route/interface, IKE/SA, policy and firewall planes remain separate.|
|7|Engine integration|PASS|Native IPIP/netlink + approved IPsec adapter; no new cryptographic implementation. Link health and protection health remain independent states.|
|8|UI/menu|PASS/N-A|Infrastructure peer/admin UI; consumer GUI N/A. UI must show IPIP state separately from IPsec protection.|
|9|Config/import|PASS|IPIP typed fields + IPsec credential/policy references + selectors/routes/MTU are mapped. No canonical consumer URI/QR exists; runtime command/XFRM output is not a portable profile.|
|10|Persistence/secrets|PASS|IPIP has no cryptographic secret; IPsec PSKs/private keys/cert credentials inherit completed secure-storage rules; runtime SA/key state is excluded from ordinary exports.|
|11|Platforms|PASS for research|Linux/network-OS infrastructure role mapped; mobile consumer role N/A absent a use case. Downstream/vendor implementations require independent version pins.|
|12|Logs/diagnostics|PASS|Underlay/IPIP/route failures are separated from IKE/SA/XFRM/ESP/firewall/MTU failures.|
|13|Assets|PASS/N-A|No canonical consumer/store asset surface applies.|
|14|Alternatives|PASS|GRE-over-IPsec, VTI and XFRM route-based architectures are distinct. Distribution/vendor kernels are downstream targets rather than fabricated IPIP forks.|
|15|Issues/releases|PASS|Current IPIP path history review includes 2026 cross-netns authorization and tunnel race fixes contained in the pinned source; shared release/iproute2 maintenance evidence and completed IPsec maintenance evidence are traceable.|
|16|Docs/forums|PASS|RFC2003, completed IPsec standards/dossiers, pinned kernel/iproute2 source/docs and accepted maintenance discussions are primary.|
|17|Tests/CI|PASS|Kernel/iproute2 and selected IPsec upstream test/review ecosystems are mapped; later packet capture must prove protection but remains acceptance/certification evidence.|
|18|Store/privacy/security|PASS|Security depends on the IPsec selector/SA, not IPIP. Cleartext fallback must not be hidden; consumer Store role N/A; topology/credential data remains protected by layer.|
|19|Reuse decision|PASS|Compose native IPIP and approved IPsec engines behind an infrastructure adapter; do not fork Linux/iproute2 or implement new crypto.|
|20|Uncertainties|PASS|Exact selectors/NAT/PMTU/vendor/downstream interoperability, failover and V2 deployment/UI/wire evidence remain explicitly later.|

## Final V1 decision

All 20 gates are independently reconciled and backed by completed IPIP/IPsec evidence plus the hardened shared Linux tunnel audits. Entry 066 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
