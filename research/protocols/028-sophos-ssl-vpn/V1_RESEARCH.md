# 028 — Sophos SSL VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/028-sophos-ssl-vpn/V1_GATE_RECONCILIATION.md`

Current audit:

`research/protocols/028-sophos-ssl-vpn/SOPHOS_SSL_VPN_CURRENT_AUDIT.md`

Decision: **`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST WHEN PROFILE+AUTH CAPABILITIES MATCH / SOPHOS PROVISIONING+SSO SEPARATE`**.

Current Sophos Firewall documentation explicitly provides `.ovpn` SSL VPN configuration usable by Sophos Connect and OpenVPN-compatible clients. Current Sophos Connect also exposes an OpenVPN service/logging domain for SSL VPN, separate from its strongSwan/IPsec domain.

Therefore PVNetwork should reuse its existing OpenVPN Adapter/core architecture rather than add a dedicated Sophos SSL tunnel engine.

OpenVPN compatibility does not imply blanket Sophos compatibility: generated profile directives, authentication/MFA/Entra SSO, `.pro` provisioning, policy update, routes/DNS and exact firewall versions remain capability/certification concerns.

Current reviewed first-party releases are Sophos Connect 2.5 MR1 for Windows (2026-06-18) and 2.0 MR1 for macOS (2026-05-21). Sophos Connect source/application UI remains proprietary/reference-only; the public reusable core evidence remains in `research/upstreams/openvpn-family/`.

`COMPLETE-RESEARCH-v1` means research closure only. Real Sophos Firewall interoperability, exact `.ovpn` profile matrix, `.pro` semantics, SSO/MFA combinations, packet captures, installer signatures and production certification remain later evidence states.