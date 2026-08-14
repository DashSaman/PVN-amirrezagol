# 031 — WatchGuard SSL VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/031-watchguard-ssl-vpn/V1_GATE_RECONCILIATION.md`

Current audit:

`research/protocols/031-watchguard-ssl-vpn/WATCHGUARD_SSL_CURRENT_AUDIT.md`

Decision: **`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST FOR STANDARD PROFILE+WIRE SEMANTICS / WATCHGUARD FIRST-PARTY SAML+VENDOR FEATURES SEPARATE`**.

Current WatchGuard Firebox documentation explicitly provides `client.ovpn` for Mobile VPN with SSL and supports OpenVPN-compatible clients, including documented OpenVPN Connect paths on Android and iOS.

PVNetwork should therefore reuse the existing OpenVPN Adapter/OpenVPN3 architecture for standard profile/tunnel behavior rather than build a WatchGuard-specific cryptographic engine.

This does not imply blanket first-party feature parity: WatchGuard's current SAML integration is documented for its first-party SSL client on supported Windows/macOS versions and explicitly does not support third-party OpenVPN clients for that SAML flow. AuthPoint/RADIUS, `.wgssl` configuration, routed/bridged policy and exact Fireware profile behavior remain separate capabilities.

WatchGuard client/Fireware code and branding are proprietary/reference-only.

`COMPLETE-RESEARCH-v1` means research closure only. Exact client/Fireware matrix, generated OVPN directives, SAML/MFA combinations, installer signatures, packet interoperability and production certification remain later evidence states.