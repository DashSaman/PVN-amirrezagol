# 026 — SonicWall NetExtender / SSL VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/026-sonicwall-netextender/V1_GATE_RECONCILIATION.md`

Current official/product evidence:

`research/protocols/026-sonicwall-netextender/OFFICIAL_NETEXTENDER_CURRENT.md`

Decision:

**`VENDOR-SPECIFIC PRODUCT COMPATIBILITY TARGET / OFFICIAL CLIENT PRIMARY / NO MATURE PUBLIC DROP-IN SELECTED`**

Current official research baseline is SonicWall NetExtender 10.3, with Windows 10.3.5 reviewed as the current Windows release in May 2026 documentation. Gateway/client compatibility remains exact-version specific.

Modern NetExtender connection profiles can expose multiple transport capabilities such as Auto, TLS/TCP, DTLS/UDP and WireGuard. These are capability/version/gateway choices, not evidence that one generic tunnel implementation equals NetExtender compatibility.

OpenConnect's SonicWall NetExtender support remains an open development effort rather than a current merged/released protocol; PVNetwork must not market or architect around it as a mature drop-in without a future fresh audit.

Official SonicWall code, UI, branding and internal source/build/test systems are proprietary/reference-only. Do not copy or fabricate internals.

Where an exact SonicWall gateway exposes an independently supportable standards protocol, use the corresponding product-owned PVNetwork standards adapter and exact capability evidence. Keep entry 027 SonicWall Global VPN/IPsec separate.

`COMPLETE-RESEARCH-v1` means research closure only. Real SonicWall gateway interoperability, exact transport cryptography/wire flow, installers/signatures, PreLogon/Always-On lifecycle, SAML/MFA/certificate permutations, Store/platform work and production support remain later evidence states.