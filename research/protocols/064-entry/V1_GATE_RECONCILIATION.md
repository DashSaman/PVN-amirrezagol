# 064 — GRE over IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **064 — GRE over IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / SITE-TO-SITE COMPOSITION / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`
- completed V1 entries 004–007 for IKEv2/IKEv1/ESP/AH
- completed V1 entry 063 for GRE

## Composition boundary

GRE (RFC2784/2890) supplies inner tunnel/encapsulation. IPsec/IKE/ESP supplies cryptographic protection. A valid product profile must keep:

- GRE local/remote/key/flags/interface/route state;
- IPsec identity/auth/IKE/ESP policy state;
- selector/policy that actually protects the intended GRE traffic;
- route/MTU/firewall/NAT state;
- underlay vs inner tunnel addresses.

A configured GRE interface does not prove IPsec is protecting it. A live IPsec SA also does not prove the GRE payload uses that SA. Later certification needs packet/policy evidence showing no unintended cleartext GRE path; that runtime receipt is not a hidden V1 research gate.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux GRE/iproute2 plus the already completed maintained IPsec/IKE implementation evidence are the primary composition candidates; vendor routers remain separately pinned interop targets when selected.|
|2|Sources pinned|PASS|RFC2784/2890 + exact Linux/iproute2 pins from entry 063/shared audits + exact already completed IPsec source/standards pins from entries 004–007.|
|3|Licenses|PASS|Linux/iproute2 GPL/syscall/source-copy boundaries are explicitly audited in the shared legal audit; selected IPsec component licenses remain independently authoritative in completed entries 004–007. No license is silently inherited across layers.|
|4|Source tree|PASS|Pinned complete Linux/iproute2 recursive tree references now cover GRE/control paths; completed IPsec dossiers cover IKE/XFRM/ESP source maps. Composition does not create a new source tree.|
|5|Languages/build|PASS|Linux/iproute2 C/Kbuild/userspace build boundaries plus the selected IPsec implementation toolchain are already mapped; no new build system is invented for the composition.|
|6|Architecture|PASS|inner traffic -> GRE -> XFRM/IPsec policy/ESP -> underlay; GRE, IKE/SA, XFRM policy, routing and firewall planes are modeled separately.|
|7|Engine integration|PASS|Native netlink/GRE plus approved IPsec adapter; no new cryptographic implementation. The adapter must expose independent GRE-link and IPsec-protection health.|
|8|UI/menu|PASS/N-A|Infrastructure peer/admin UI; consumer GUI N/A. UI must show both GRE state and IPsec protection state independently and must not imply encryption merely because GRE is up.|
|9|Config/import|PASS|GRE typed fields + references to IPsec credential/policy objects + selectors/routes/MTU are mapped. No canonical GRE-over-IPsec consumer URI/QR exists; raw command output is not a portable profile.|
|10|Persistence/secrets|PASS|GRE key is metadata, not a cryptographic secret. IPsec PSKs/private keys/cert credentials inherit completed secure-storage ownership; runtime SA/XFRM key state is excluded from ordinary export/support bundles.|
|11|Platforms|PASS for research|Linux/network-OS site-to-site role is mapped; mobile consumer role is normally N/A. Any vendor/downstream kernel implementation requires an exact separate version pin before support.|
|12|Logs/diagnostics|PASS|Underlay/GRE/interface/route failures are separated from IKE/SA/XFRM selector/ESP/firewall/MTU failures; no generic “VPN failed” bucket is used.|
|13|Assets|PASS/N-A|No canonical consumer assets/store artwork apply to this infrastructure composition.|
|14|Alternatives|PASS|VTI/XFRM route-based IPsec and vendor tunnel-interface architectures are distinct alternatives; downstream/vendor kernels are not mislabeled as canonical GRE forks.|
|15|Issues/releases|PASS|GRE current maintenance review includes 2026 LLTX and cross-netns authorization fixes at the pinned Linux baseline; iproute2 path/release review is recorded in the shared maintenance audit; IPsec issue/release evidence is inherited only from completed entries 004–007.|
|16|Docs/forums|PASS|GRE RFCs, completed IPsec standards/dossiers, pinned Linux/iproute2 docs/source and accepted kernel maintenance discussions are primary; unverified tutorials are not evidence authority.|
|17|Tests/CI|PASS|Kernel/iproute2 and selected IPsec upstream test/review ecosystems are mapped. Later packet capture/policy testing must prove protected GRE but is implementation/certification evidence, not hidden V1 research.|
|18|Store/privacy/security|PASS|Security comes from the selected IPsec policy/SA, not GRE. Cleartext fallback must not be hidden; endpoint/topology metadata is sensitive; consumer Store role N/A.|
|19|Reuse decision|PASS|Compose native GRE networking and approved IPsec engines behind an infrastructure adapter; do not fork kernel/iproute2 and never implement new crypto merely for this entry.|
|20|Uncertainties|PASS|Exact selectors/vendor interop/NAT/MTU/failover/downstream-version behavior and V2 installer/UI/wire/topology evidence are explicitly later.|

## Final V1 decision

All 20 V1 gates are evidence-backed or infrastructure-N/A bounded through the completed GRE/IPsec dossiers and the hardened shared Linux tunnel audits. Entry 064 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
