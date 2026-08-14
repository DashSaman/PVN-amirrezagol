# 030 — WatchGuard IKEv2 VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/030-watchguard-ikev2/V1_GATE_RECONCILIATION.md`

Current audit:

`research/protocols/030-watchguard-ikev2/WATCHGUARD_IKEV2_CURRENT_AUDIT.md`

Decision: **`STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST / STRONGSWAN FOR ANDROID+ADVANCED PORTABILITY / WATCHGUARD PROFILE+AUTH CERTIFICATION REQUIRED`**.

Current WatchGuard guidance explicitly supports native IKEv2 on Windows, macOS and iOS, strongSwan on Android, and an optional WatchGuard IPSec Mobile VPN Client Windows profile-import path on Fireware 12.11.1+.

WatchGuard Mobile VPN with IKEv2 uses certificate-based tunnel/server identity plus EAP/MS-CHAPv2 user authentication, with Firebox-DB/RADIUS/AuthPoint capabilities and version/platform-specific certificate/proposal support.

PVNetwork should normalize WatchGuard-generated profile material into the shared canonical IKEv2 model and prefer native OS backends; pinned strongSwan remains the public engine candidate where a separate engine is justified.

WatchGuard Fireware/client code and branding remain proprietary/reference-only.

`COMPLETE-RESEARCH-v1` means research closure only. Exact Fireware/OS/proposal/auth/profile matrix, real packet interoperability, profile/certificate lifecycle, MFA behavior and production certification remain later evidence states.