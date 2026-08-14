# 068 — XFRM/IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: Linux kernel IPsec policy/SA transformation/interface integration, not a new VPN protocol.

Decision: **`LINUX IPSEC BACKEND CAPABILITY / REUSE STRONGSWAN+KERNEL MODEL`**.

PVNetwork should treat XFRM as a Linux backend/integration option behind the shared IPsec Adapter, with privileged route/policy handling isolated from the GUI.

Later v2 adds XFRM interface/policy/state flow, package/kernel matrices, admin menus and topologies.
