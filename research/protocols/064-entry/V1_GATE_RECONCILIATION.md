# 064 — GRE over IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **064 — GRE over IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / SITE-TO-SITE COMPOSITION / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence: `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`; reuse completed IPsec V1 entries 004–007 without merging their semantics.

## Composition boundary

GRE (RFC2784/2890) supplies inner tunnel/encapsulation. IPsec/IKE/ESP supplies cryptographic protection. A valid product profile must keep:

- GRE local/remote/key/flags/interface/route state;
- IPsec identity/auth/IKE/ESP policy state;
- selector/policy that actually protects the intended GRE traffic;
- route/MTU/firewall/NAT state;
- underlay vs inner tunnel addresses.

A configured GRE interface does not prove IPsec is protecting it. A live IPsec SA also does not prove the GRE payload uses that SA. Later certification needs packet/policy evidence showing no unintended cleartext GRE path.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux GRE/iproute2 + completed strongSwan/native IPsec family; vendor routers are interop references.|
|2|Sources pinned|PASS|Current Linux/iproute2 GRE pins plus completed current IPsec pins/dossiers and GRE/IPsec standards.|
|3|Licenses|PASS|GPL Linux/iproute2 source boundaries and selected IPsec component licenses remain separately audited.|
|4|Source tree|PASS|GRE kernel/control paths plus completed XFRM/IKE/ESP source maps.|
|5|Languages/build|PASS|Linux/iproute2 C plus selected IPsec implementation toolchain already mapped.|
|6|Architecture|PASS|inner traffic -> GRE -> IPsec policy/ESP -> underlay; control/route/policy planes kept distinct.|
|7|Engine integration|PASS|Native netlink/GRE plus selected IPsec adapter; no new crypto core.|
|8|UI/menu|PASS/N-A|Infrastructure peer/admin UI; consumer GUI N/A. UI must show both GRE and protection state independently.|
|9|Config/import|PASS|GRE fields + IPsec credential/policy references + selectors/routes/MTU mapped as composition, not one opaque blob.|
|10|Persistence/secrets|PASS|GRE key is metadata; IPsec private keys/PSKs/certs follow completed secure-storage ownership.|
|11|Platforms|PASS for research|Linux and network-OS site-to-site role mapped; mobile consumer role normally N/A.|
|12|Logs/diagnostics|PASS|Underlay/GRE/interface/route vs IKE/SA/XFRM selector/ESP/firewall/MTU failures separated.|
|13|Assets|PASS/N-A|No canonical consumer assets.|
|14|Alternatives|PASS|Route-based VTI/XFRM and vendor tunnel interfaces are separate entries/architectures.|
|15|Issues/releases|PASS|Current Linux/iproute2 + current completed IPsec maintenance evidence; live interop later.|
|16|Docs/forums|PASS|GRE RFCs, IPsec standards/dossiers and current Linux source are primary.|
|17|Tests/CI|PASS|Upstream source/test ecosystems exist; later packet capture must prove protected GRE.|
|18|Store/privacy/security|PASS|Security depends on IPsec selector/SA; no cleartext fallback may be hidden; Store consumer role N/A.|
|19|Reuse decision|PASS|Compose native GRE and approved IPsec engines behind infrastructure adapter; never implement new cryptography.|
|20|Uncertainties|PASS|Exact selectors/vendor interop/NAT/MTU/failover and V2 installer/UI/wire topology evidence remain later.|

## Final V1 decision

All 20 V1 gates are evidence-backed or infrastructure-N/A bounded. Entry 064 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
