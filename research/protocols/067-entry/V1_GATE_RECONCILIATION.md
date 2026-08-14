# 067 — VTI/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **067 — VTI/IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / LINUX ROUTE-BASED IPSEC INTERFACE ARCHITECTURE / NOT A NEW WIRE PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`
- completed entries 004–007 for IKE/IPsec/ESP/AH

## Identity / source

- Linux VTI implementation pin: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `net/ipv4/ip_vti.c`.
- iproute2 VTI control pin: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `ip/link_vti.c`.
- VTI calls into XFRM policy/state processing and is therefore an interface/routing abstraction over IPsec/XFRM, **not a standalone on-wire VPN protocol**.
- VTI keys/marks are selector metadata, not cryptographic secrets.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel/iproute2 VTI plus the completed maintained IPsec/IKE implementation evidence are the selected primary architecture; vendor route-based IPsec interfaces are separately pinned interop targets.|
|2|Sources pinned|PASS|Exact Linux `ad8d485e...`, iproute2 `da2ccdf...` and completed IPsec source/standards pins are traceable.|
|3|Licenses|PASS|Linux/iproute2 GPL/syscall/source-copy/commercial/distribution/modification/notice/network-use/trademark/Store boundaries are explicitly audited; IPsec component licenses remain separately authoritative.|
|4|Source tree|PASS|Complete pinned Linux/iproute2 recursive trees cover `ip_vti.c`, `link_vti.c`, XFRM/network support, build/tests/packaging/docs; completed IPsec dossiers cover the IKE/ESP side.|
|5|Languages/build|PASS|Relevant VTI/kernel/iproute2 code is C; Kbuild/Kconfig/iproute2 build, tests and package boundaries plus the selected IPsec toolchain are mapped.|
|6|Architecture|PASS|Route/interface -> VTI selector metadata -> XFRM policy/state -> ESP/AH underlay; VTI, routing, IKE/SA and crypto layers are distinct.|
|7|Engine integration|PASS|Use native netlink/VTI/XFRM plus approved IKE/IPsec adapter; no new protocol or cryptographic core is required.|
|8|UI/menu|PASS/N-A|Infrastructure route-based VPN admin UI only; consumer client UI N/A. Status must distinguish interface/routing health from IKE/SA/protection health.|
|9|Config/import|PASS|Interface/endpoints/marks/routes/table/MTU plus referenced IPsec profile are typed. No canonical VTI consumer URI/QR exists; raw runtime XFRM/VTI dumps are not portable profiles.|
|10|Persistence/secrets|PASS|VTI marks/keys are metadata; IPsec PSKs/private keys/cert credentials are protected separately; runtime XFRM state is not ordinary portable profile data.|
|11|Platforms|PASS for research|Linux-specific primary architecture; vendor analogues vary and require independent pinning; mobile consumer role N/A.|
|12|Logs/diagnostics|PASS|Interface/route/mark failures are separated from IKE/SA/XFRM policy/ESP/MTU/firewall failures.|
|13|Assets|PASS/N-A|No canonical consumer/store asset surface applies.|
|14|Alternatives|PASS|XFRM interfaces/policy-routing and vendor route-based IPsec are related but distinct abstractions; GRE/IPIP compositions remain separate entries. No unsupported VTI-specific fork is promoted.|
|15|Issues/releases|PASS|Canonical VTI path history was reviewed. Pinned source contains `95cceadb...`, the 2026 cross-netns `CAP_NET_ADMIN` changelink fix marked for stable; shared release/path-maintenance review and completed IPsec maintenance evidence are traceable.|
|16|Docs/forums|PASS|Pinned kernel/iproute2 source/docs plus completed IPsec standards/dossiers and accepted upstream maintenance discussions are primary.|
|17|Tests/CI|PASS|Kernel networking/XFRM selftests/review and selected IPsec tests are mapped; PVNetwork route/policy packet/device/interop tests remain later acceptance evidence.|
|18|Store/privacy/security|PASS|Security is IPsec-owned; VTI metadata is not cryptographic protection. XFRM/credential dumps are sensitive; consumer Store role N/A.|
|19|Reuse decision|PASS|Use native Linux route-based IPsec adapter only for infrastructure needs; do not present VTI as a new VPN wire protocol and do not fork kernel/iproute2 code merely for integration.|
|20|Uncertainties|PASS|Exact marks/selectors, VTI versus XFRM-interface selection, downstream/vendor interop, IPv6 and V2 installer/UI/wire topology remain explicitly later.|

## Final V1 decision

All 20 gates are evidence-backed or correctly infrastructure-N/A bounded. Entry 067 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
