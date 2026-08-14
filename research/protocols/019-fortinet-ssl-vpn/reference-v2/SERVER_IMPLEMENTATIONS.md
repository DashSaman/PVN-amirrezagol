# Fortinet FortiGate SSL VPN — Server / Headend Ecosystem

Review date: 2026-08-14 UTC

## Authoritative proprietary headend

**FortiGate / FortiOS SSL VPN tunnel mode** is the canonical server/headend for entry 019. FortiOS/FortiGate source code is proprietary and unavailable for public source pinning; `N/A-PUBLIC-SOURCE / PROPRIETARY` is the correct result.

Selected maintained legacy tunnel-mode server reference: **FortiOS 7.4.12**. Fortinet's 7.4.12 administration guide still documents SSL VPN tunnel mode with FortiClient, SSL-VPN portals/settings, `ssl.root`, firewall policies and monitoring.

Lifecycle boundary: beginning with **FortiOS 7.6.3**, Fortinet removes SSL VPN tunnel mode from GUI and CLI and replaces it with IPsec VPN; legacy SSL tunnel settings are not upgraded automatically. FortiOS 7.6.3+ therefore is not a valid server baseline for this tunnel-mode entry. SSL VPN web mode is renamed **Agentless VPN** and remains a distinct browser-based feature.

Official references:
- https://docs.fortinet.com/document/fortigate/7.4.12/administration-guide/559546/ssl-vpn-full-tunnel-for-remote-user
- https://docs.fortinet.com/document/fortigate/7.6.3/fortios-release-notes/173430/ssl-vpn-tunnel-mode-replaced-with-ipsec-vpn
- https://docs.fortinet.com/document/fortigate/7.6.0/new-features/645457/agentless-vpn-7-6-3

## Public compatible client ecosystem

OpenConnect v9.21 implements experimental Fortinet mode (`--protocol=fortinet`), PPP-based and separate from FortiClient. Exact repository pin: `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.

OpenConnect is **not** a FortiGate server and its support does not imply full Fortinet feature/version parity.
