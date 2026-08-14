# 032 — WatchGuard L2TP VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/032-watchguard-l2tp/V1_GATE_RECONCILIATION.md`

Current audit:

`research/protocols/032-watchguard-l2tp/WATCHGUARD_L2TP_CURRENT_AUDIT.md`

Decision: **`STANDARD L2TP/IPSEC INTEROPERABILITY TARGET / NATIVE-OS-FIRST WHERE CURRENT SUPPORT EXISTS / REUSE ENTRY-008 LAYERED STACK / WATCHGUARD AUTH+POLICY CERTIFICATION REQUIRED / MODERN-ANDROID-NATIVE-UNAVAILABLE`**.

Current WatchGuard Mobile VPN with L2TP uses IPsec by default and separates tunnel authentication (PSK or certificate) from PPP/MS-CHAPv2 user authentication, Firebox-DB/RADIUS/AuthPoint policy, virtual IP assignment and Firebox access policy.

PVNetwork should reuse the layered L2TP/IPsec model from entry 008 and native OS clients where current platform support exists. Do not build a WatchGuard-specific L2TP/IPsec cryptographic stack.

WatchGuard technically permits L2TP without IPsec, but explicitly describes it as less secure and not recommended; PVNetwork must not use unprotected L2TP as a silent fallback.

Current WatchGuard documentation also records that native Android L2TP support is unavailable on Android 12+, so modern Android is an explicit negative capability unless a separately audited third-party client is selected.

WatchGuard Fireware source/UI/assets remain proprietary/reference-only.

`COMPLETE-RESEARCH-v1` means research closure only. Exact Fireware/OS/transform/MFA/profile matrix, real interoperability, packet traces and production certification remain later evidence states.