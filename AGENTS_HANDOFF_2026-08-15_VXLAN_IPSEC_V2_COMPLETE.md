# PVNetwork Handoff — VXLAN over IPsec V2 Complete

Date: 2026-08-15

- V1 remains **93/93**.
- Entry **070 — VXLAN over IPsec** has a complete evidence-backed 16-gate V2 dossier under `research/protocols/070-vxlan-over-ipsec/` and is approved for tracker promotion.
- VXLAN overlay/framing evidence is reused from 069; security-layer evidence is reused from completed IKE/IPsec/ESP references. VXLAN supplies overlay segmentation/encapsulation; IPsec supplies all cryptographic protection.

## Exact continuation

Next required V2 entry: **071 — DMVPN**. Evaluate all 16 gates independently. Reuse GRE/IPsec evidence only for those layers, then add DMVPN-specific mGRE/NHRP/routing/hub-spoke/phase behavior, Cisco proprietary implementation boundaries, current IOS XE documentation, Linux/FRR/strongSwan feasibility only where traceable, lifecycle/UI and deployment topologies. Do not equate generic GRE-over-IPsec with DMVPN.

After 071 continue 072 Cisco FlexVPN and 073 GETVPN.
