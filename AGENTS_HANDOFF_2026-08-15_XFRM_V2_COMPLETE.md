# PVNetwork Handoff — XFRM/IPsec V2 Complete

Date: 2026-08-15

## Progress

- COMPLETE-RESEARCH-v1 remains **93/93**.
- Entry **068 — XFRM/IPsec** has a complete evidence-backed 16-gate V2 dossier under `research/protocols/068-xfrm-ipsec/` and is approved for tracker promotion.
- This handoff supplies gate 16's exact continuation state.

## Key evidence/boundary

Current Linux XFRM interface source is pinned at `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/xfrm/xfrm_interface_core.c` (GPL-2.0). Current strongSwan route-based VPN documentation establishes Linux kernel 4.19+ / iproute2 5.1.0+ support and the interface-ID model. XFRM interfaces require no tunnel endpoint addresses, support IPv4/IPv6 and multiple IPsec modes, and are a local interface decision that adds no extra on-wire header. They remain distinct from VTI/IPsec entry 067.

## Exact continuation

Next required V2 entry: **069 — VXLAN**.

Evaluate all exact 16 gates independently using RFC 7348 and current Linux/iproute2 VXLAN implementation evidence. Map VNI, UDP encapsulation, FDB/learning, multicast/unicast endpoint models, MTU, server/peer and UI N/A boundaries, source/license/activity, deployment lifecycle and bare VXLAN's lack of intrinsic cryptographic confidentiality/authentication. Keep **070 VXLAN over IPsec** separate and attribute its security only to IPsec.

Research completion is not implementation/device testing/Store/production certification.
