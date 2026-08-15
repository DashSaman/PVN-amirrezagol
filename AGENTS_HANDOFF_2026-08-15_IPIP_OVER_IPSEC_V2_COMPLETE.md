# PVNetwork Handoff — IPIP over IPsec V2 Complete

Date: 2026-08-15

## Progress

- COMPLETE-RESEARCH-v1 remains **93/93**.
- Entry 066 **IPIP over IPsec** has a complete 16-gate V2 dossier under `research/protocols/066-ipip-over-ipsec/` and is approved for tracker promotion.
- This handoff supplies the exact continuation record required by gate 16.

## Evidence composition

Entry 066 reuses only traceable evidence from:

- entry 065 IPIP for the bare tunnel/interface/data-path boundary; and
- completed entries 004–007 plus `research/upstreams/strongswan-family/reference-v2/` for IKE/IPsec/ESP server/client/install/UI/security/source/license/lifecycle evidence.

The dossier keeps the non-cryptographic IPIP layer distinct from the security layer and does not infer generic consumer-platform support from standalone IPsec availability.

## Exact continuation

Next required V2 entry: **067 — VTI/IPsec**.

For entry 067, independently evaluate all exact 16 V2 gates. Focus on Linux VTI route-based interface semantics, marks/policy interaction, IPv4/IPv6 variants, lifecycle and the distinction between VTI devices and newer XFRM interfaces. Reuse the existing IPsec/IKE evidence where applicable, but do not collapse VTI/IPsec into entry 068 XFRM/IPsec.

After 067, continue 068 XFRM/IPsec, 069 VXLAN and 070 VXLAN over IPsec.

Research completion is not implementation, device testing, Store verification or production certification.
