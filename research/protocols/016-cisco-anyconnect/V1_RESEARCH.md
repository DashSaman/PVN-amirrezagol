# 016 — Cisco AnyConnect-compatible VPN — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Primary reusable upstream candidate: **OpenConnect**.

Shared evidence: `research/upstreams/openconnect-family/`.

Research decision:

**`HIGH-VALUE ENTERPRISE COMPATIBILITY TARGET / OPENCONNECT PUBLIC-API CANDIDATE`**

PVNetwork should use a typed Enterprise/OpenConnect Adapter around a pinned OpenConnect library build rather than reproduce AnyConnect-compatible protocol behavior from scratch.

Keep authentication/browser SSO/MFA/posture/provisioning separate from tunnel/data-transport state. Generic OpenConnect/AnyConnect compatibility must be certified against exact server/software versions and authentication methods.

OpenConnect licensing, dependencies and platform packaging require exact build review before product integration.

No Cisco branding/assets/proprietary client code should be copied into PVNetwork.

Later mandatory v2 adds exact server/client versions, full client/UI menus, installer/deployment references, TLS/DTLS/handshake/data path and authentication flows.
