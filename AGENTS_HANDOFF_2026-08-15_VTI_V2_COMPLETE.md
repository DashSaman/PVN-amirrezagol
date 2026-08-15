# PVNetwork Handoff — VTI/IPsec V2 Complete

Date: 2026-08-15

## Progress

- COMPLETE-RESEARCH-v1 remains **93/93**.
- Entry **067 — VTI/IPsec** has a complete evidence-backed 16-gate V2 dossier under `research/protocols/067-vti-ipsec/` and is approved for tracker promotion.
- This handoff supplies the exact continuation state required by gate 16.

## Important boundary

VTI is a Linux route-based local interface abstraction around existing IPsec policies/SAs. It does not introduce a new cryptographic protocol or GRE-like wire header. Marks bind routed traffic to policy/SAs and are not cryptographic keys. Current strongSwan documentation identifies newer XFRM interfaces as generally preferable because they remove several VTI limitations; this is why entry 068 remains a separate required research item.

## Exact continuation

Next required V2 entry: **068 — XFRM/IPsec**.

For 068 evaluate all exact 16 gates independently. Reuse the strongSwan-family IKE/IPsec evidence, but add XFRM-interface-specific evidence: Linux/iproute2 source and version boundary, interface IDs linking policy/SAs, absence of configured tunnel endpoint addresses, IPv4/IPv6 and IPsec-mode flexibility, route installation and no-policy/no-SA behavior, network namespaces/VRFs/offload boundaries, lifecycle and explicit differences from VTI.

After 068, continue **069 VXLAN**, **070 VXLAN over IPsec**, **071 DMVPN** and onward.

Research completion remains separate from implementation/device testing/Store/production certification.
