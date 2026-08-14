# 067 — VTI/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **067 — VTI/IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / LINUX ROUTE-BASED IPSEC INTERFACE ARCHITECTURE / NOT A NEW WIRE PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence: `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`; reuse completed entries 004–007 for IKE/IPsec/ESP/AH.

## Identity / source

- Current Linux VTI implementation: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `net/ipv4/ip_vti.c` (GPL-2.0-or-later).
- Current iproute2 VTI control: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `ip/link_vti.c`.
- Reviewed Linux VTI source directly calls XFRM policy/state/input mechanisms. VTI is therefore an interface/routing abstraction over IPsec/XFRM, **not a standalone on-wire VPN protocol**.

Current iproute2 VTI options include local/remote endpoints, key/ikey/okey, physical device and fwmark. These keys/marks are selectors/metadata, not cryptographic secrets.

## Product model

Typed state: VTI interface identity, local/remote underlay addresses, mark/key selector metadata, table/routes/VRF/namespace binding, MTU, referenced IPsec/IKE profile/SAs/policies and lifecycle state. Cryptographic credentials remain owned by the IPsec layer.

Consumer GUI is N/A; expose only under Advanced/Site-to-Site if needed.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel/iproute2 VTI plus completed strongSwan/native XFRM evidence are primary; vendor route-based IPsec interfaces are later interop references.|
|2|Sources pinned|PASS|Exact current Linux/iproute2 pins and completed IPsec source pins/dossiers.|
|3|Licenses|PASS|GPL kernel/iproute2 and separate IPsec component licenses documented.|
|4|Source tree|PASS|`ip_vti.c`, `link_vti.c` and XFRM/IKE/ESP maps are traceable.|
|5|Languages/build|PASS|Linux/iproute2 C plus selected IPsec stack.|
|6|Architecture|PASS|Route/interface -> VTI metadata -> XFRM policy/state -> ESP/AH underlay; VTI and crypto layers distinct.|
|7|Engine integration|PASS|Native netlink/VTI + approved IPsec engine; no new protocol/crypto core.|
|8|UI/menu|PASS/N-A|Infrastructure route-based VPN admin UI; consumer client N/A.|
|9|Config/import|PASS|Interface/endpoints/marks/routes/table/MTU plus referenced IPsec profile mapped as typed composition.|
|10|Persistence/secrets|PASS|VTI marks/keys are metadata; IPsec PSKs/private keys are protected separately; runtime XFRM state not portable profile data.|
|11|Platforms|PASS for research|Linux-specific primary architecture; vendor analogues vary; mobile consumer role N/A.|
|12|Logs/diagnostics|PASS|Interface/route/mark vs IKE/SA/XFRM policy/ESP/MTU/firewall failures separated.|
|13|Assets|PASS/N-A|No canonical consumer assets.|
|14|Alternatives|PASS|XFRM interfaces/policy routing and vendor route-based IPsec are distinct architectures; GRE/IPIP compositions separate.|
|15|Issues/releases|PASS|Current Linux/iproute2 maintenance and completed IPsec release evidence are pinned.|
|16|Docs/forums|PASS|Current kernel/iproute2 source plus completed IPsec standards/dossiers are primary.|
|17|Tests/CI|PASS|Upstream kernel/IPsec test ecosystems; PVNetwork route/policy packet tests later.|
|18|Store/privacy/security|PASS|Security is IPsec-owned; VTI metadata is not crypto; consumer Store role N/A.|
|19|Reuse decision|PASS|Use native Linux route-based IPsec adapter only for infrastructure needs; do not present VTI as another VPN protocol engine.|
|20|Uncertainties|PASS|Exact marks/selectors, VTI vs XFRM-interface choice, vendor interop, IPv6 and V2 installer/UI/wire topology remain later.|

## Final V1 decision

Entry 067 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
