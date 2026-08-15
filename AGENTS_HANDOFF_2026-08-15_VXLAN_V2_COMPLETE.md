# PVNetwork Handoff — VXLAN V2 Complete

Date: 2026-08-15

- V1 remains **93/93**.
- Entry **069 — VXLAN** has a complete evidence-backed 16-gate V2 dossier under `research/protocols/069-vxlan/` and is approved for promotion.
- Canonical evidence: RFC 7348; current Linux VXLAN docs; Linux kernel `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `drivers/net/vxlan/vxlan_core.c`; pinned iproute2.
- Important boundary: bare VXLAN is a UDP-based L2 overlay with VNI segmentation, not an encrypted/authenticated VPN. VNI is not a secret. RFC/IANA uses UDP 4789; Linux source retains historical default 8472 unless configured otherwise.

## Exact continuation

Next required V2 entry: **070 — VXLAN over IPsec**. Reuse entry 069 only for VXLAN framing/VTEP/FDB/topology evidence and the completed strongSwan/IKE/IPsec layer for security. Document the composition independently, cumulative overhead/MTU, UDP VXLAN inside protected IPsec traffic, SA/interface state separation and consumer-platform N/A boundaries. Attribute all confidentiality/authentication to IPsec, not VXLAN.

After 070 continue **071 DMVPN**.
