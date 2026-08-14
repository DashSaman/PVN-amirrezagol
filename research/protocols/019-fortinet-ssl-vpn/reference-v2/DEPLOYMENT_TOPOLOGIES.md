# Fortinet FortiGate SSL VPN — Deployment Topologies

Review date: 2026-08-14 UTC

Covered reference patterns for pre-7.6.3 / maintained legacy tunnel-mode FortiOS:

1. **Full tunnel** — FortiClient sends remote-user traffic through FortiGate `ssl.root`; firewall policies allow internal and/or Internet egress.
2. **Split tunnel** — portal/routing policy sends selected destinations through SSL VPN while other traffic remains local.
3. **Certificate/MFA remote access** — user authentication can be combined with client/machine certificates and MFA according to FortiGate policy.
4. **EMS-managed endpoint** — FortiClient profile is provisioned centrally; endpoint connects using managed profile and policy.
5. **Prelogon/machine-certificate tunnel** — exact FortiClient/FortiGate support can establish a machine tunnel before user logon in documented Windows designs.
6. **TLS-only constrained network** — SSL-VPN data remains on TLS/TCP where DTLS is unavailable/disabled.
7. **TLS + DTLS path** — UDP/DTLS is used for data where configured to reduce TCP-over-TCP limitations.
8. **OpenConnect compatibility** — OpenConnect v9.21 can target the PPP-compatible Fortinet subset; server-version/auth/reconnect capability must remain granular.
9. **Migration topology** — before FortiOS 7.6.3+, migrate tunnel mode to standards-based IPsec; do not represent Agentless VPN as the same tunnel.

Deployment claims remain model/version/client/auth specific. New FortiOS 7.6.3+ installations should not be designed around entry-019 tunnel mode.
