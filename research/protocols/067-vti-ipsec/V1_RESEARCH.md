# 067 — VTI/IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: route-able virtual-interface integration for IPsec, not a separate cryptographic protocol.

Decision: **`ADVANCED PLATFORM BACKEND MODE / REUSE IPSEC ADAPTER`**.

Prefer OS/kernel VTI plus approved IKE/IPsec backend. Do not duplicate crypto/profile fields as a separate VTI protocol model.

Later v2 adds Linux/router implementations, install matrices, route/policy flow, menus and topologies.
