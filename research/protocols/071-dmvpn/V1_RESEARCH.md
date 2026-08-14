# 071 — DMVPN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: multi-component dynamic VPN architecture, typically combining mGRE, NHRP, IPsec and routing.

Decision: **`ADVANCED ROUTER/SITE-TO-SITE FRAMEWORK / NO CONSUMER-FIRST ENGINE`**.

Open-source reference directions include Linux mGRE/kernel networking, strongSwan/IPsec and FRRouting NHRP/routing components; Cisco implementations remain key interoperability references.

PVNetwork should model the components explicitly rather than create an opaque “DMVPN protocol” blob.

Later v2 adds NHRP/control-plane flow, Cisco/open-source implementations, installs, full admin menus, crypto/wire flow and topologies.
