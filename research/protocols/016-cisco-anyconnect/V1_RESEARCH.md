# 016 — Cisco AnyConnect-compatible VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/016-cisco-anyconnect/V1_GATE_RECONCILIATION.md`

Current proprietary reference:

- `research/protocols/016-cisco-anyconnect/PROPRIETARY_REFERENCE_CURRENT.md`
- `research/protocols/016-cisco-anyconnect/CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`

Primary reusable upstream candidate: **OpenConnect v9.21** through the public `libopenconnect` API.

Shared evidence: `research/upstreams/openconnect-family/`.

Research decision:

**`HIGH-VALUE ENTERPRISE COMPATIBILITY TARGET / OPENCONNECT PUBLIC-API CANDIDATE`**

PVNetwork should use a typed Enterprise/OpenConnect Adapter around a pinned OpenConnect library build rather than reproduce AnyConnect-compatible protocol behavior from scratch.

Cisco Secure Client is the authoritative proprietary behavioral reference; its current reviewed desktop release is 5.1.18.314. Cisco private source/build/test internals are not claimed or inferred.

Keep authentication/browser SSO/MFA/posture/provisioning separate from tunnel/data-transport state. Generic OpenConnect/AnyConnect compatibility must later be certified against exact server/software versions and authentication methods.

OpenConnect licensing, dependencies and platform packaging require exact build review before product integration.

No Cisco branding/assets/proprietary client code should be copied into PVNetwork.

`COMPLETE-RESEARCH-v1` means research closure only. Runtime interoperability, implementation, device tests, Store review and production certification remain separate evidence states. Later mandatory v2 adds exhaustive server/client versions, installers/deployments, full menus, cryptography, TLS/DTLS/data path/handshake and topology evidence.