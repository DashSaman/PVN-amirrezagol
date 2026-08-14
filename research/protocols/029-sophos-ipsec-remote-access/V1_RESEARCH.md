# 029 — Sophos IPsec Remote Access — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/029-sophos-ipsec-remote-access/V1_GATE_RECONCILIATION.md`

Current audit:

`research/protocols/029-sophos-ipsec-remote-access/SOPHOS_IPSEC_CURRENT_AUDIT.md`

Decision: **`CURRENT SOPHOS IPSEC COMPATIBILITY TARGET / STRONGSWAN-FIRST FOR STANDARD IKEV1-IPSEC SEMANTICS / SCX-PRO-POLICY-SSO SEPARATE / RETIRED LEGACY MODE MIGRATION-ONLY`**.

Current SFOS 22.0 remote-access IPsec documentation still requires IKEv1 profiles for the modern Sophos Connect remote-access mode. This must not be confused with the separate `IPsec (legacy)` feature, which Sophos retired in SFOS 22.0 MR1 and later.

Use the existing strongSwan/IKEv1/IPsec research architecture for standard cryptographic/tunnel semantics. Current PVNetwork strongSwan baseline remains release 6.0.7 at exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75`.

Sophos `.scx`, `.tgb`, and `.pro` inputs, Entra/MFA/group policy, Security Heartbeat, client IP/DNS/routes, provisioning/update, and exact firewall authorization remain vendor capability layers around the standards engine.

Current Sophos Connect diagnostics explicitly identify `charon.log` as strongSwan/IKE/ESP/IPsec activity, while Sophos Connect/SFOS application code and UI remain proprietary/reference-only.

`COMPLETE-RESEARCH-v1` means research closure only. Exact Sophos gateway/profile/proposal matrix, profile schema/protection, real interoperability, packet captures, installers/signatures, third-party Linux/mobile support, and production certification remain later evidence states.