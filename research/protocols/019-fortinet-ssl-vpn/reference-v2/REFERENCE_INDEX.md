# Fortinet FortiGate SSL VPN — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14 UTC

Entry: **019 — Fortinet FortiGate SSL VPN**.

Reuse decision: **LEGACY / VERSION-BOUNDED ENTERPRISE COMPATIBILITY TARGET**. Proprietary FortiGate/FortiClient are vendor reference/certification targets. OpenConnect Fortinet mode is a separately licensed, partial compatible client-engine candidate only for explicitly supported capabilities/versions.

## Proprietary vendor evidence / selected baselines

Server tunnel-mode baseline:
- FortiOS **7.4.12** administration guide — current maintained 7.4 tunnel-mode reference at review time.
- Full tunnel configuration and menu/data-path example: https://docs.fortinet.com/document/fortigate/7.4.12/administration-guide/559546/ssl-vpn-full-tunnel-for-remote-user

Retirement/activity boundary:
- FortiOS **7.6.3** special notice removes SSL VPN tunnel mode from GUI/CLI and requires manual IPsec migration: https://docs.fortinet.com/document/fortigate/7.6.3/fortios-release-notes/173430/ssl-vpn-tunnel-mode-replaced-with-ipsec-vpn
- Agentless VPN naming/boundary: https://docs.fortinet.com/document/fortigate/7.6.0/new-features/645457/agentless-vpn-7-6-3
- migration guidance: https://docs.fortinet.com/document/fortigate/7.6.6/administration-guide/155142/ssl-vpn-tunnel-mode-to-ipsec-vpn-migration

Client baseline:
- FortiClient **7.4.7** administration/release documentation.
- SSL VPN profile: https://docs.fortinet.com/document/forticlient/7.4.7/administration-guide/205286/configuring-an-ssl-vpn-connection
- Linux support/version boundary: https://docs.fortinet.com/document/forticlient/7.4.7/linux-release-notes/136392/product-integration-and-support
- special notices/free VPN-only agent/coexistence: https://docs.fortinet.com/document/forticlient/7.4.7/linux-release-notes/745986

Security:
- FortiGuard PSIRT index: https://www.fortiguard.com/psirt
- Exact branch/mode PSIRT applicability must be checked before deployment/upgrade; current activity includes 2026 SSL-VPN/Agentless advisories and older SSL-VPN fixes.

FortiOS/FortiClient source code: `N/A-PUBLIC-SOURCE / PROPRIETARY`. No source hash/open-source license is fabricated.

## Public compatible source

- OpenConnect **v9.21** — canonical tag commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1, existing repository freeze in `research/upstreams/openconnect-family/SOURCE_PIN.md`.
- official Fortinet mode documentation: https://www.infradead.org/openconnect/fortinet.html
- shared evidence: `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`, `DEPENDENCIES_AND_LGPL.md`, `API_LIFETIME_AND_CALLBACKS.md`, `CONFIG_STORAGE_AND_PLATFORM.md`, `SECURITY_AND_ADVISORIES.md`, `ISSUE_MR_FIX_MATRIX.md`, `SUPPORT_REUSE_DECISIONS.md`.

OpenConnect Fortinet support remains experimental/partial, PPP-based; newer non-PPP wire protocol is not supported and reconnect/auth capability varies by FortiGate generation.

## Mandatory V2 files

`SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_INDEX.md`.
