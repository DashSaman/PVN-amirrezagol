# Fortinet FortiGate SSL VPN — Ports / Transports / Handshake

Review date: 2026-08-14 UTC

## Proprietary tunnel-mode reference

- SSL VPN TLS listener: **TCP 443 by default** in current FortiClient documentation, but configurable on FortiGate/FortiClient.
- Fortinet's official FortiOS 7.4.12 example uses custom TCP **10443**, proving 443 is not an immutable protocol constant.
- Optional DTLS tunnel/data transport uses UDP when enabled/supported; exact listener/negotiation behavior is release/configuration specific and is not generalized into a fixed undocumented port.
- Authentication rides the HTTPS/TLS SSL-VPN service and can include password, client certificate and additional challenge/SSO mechanisms according to gateway policy.

References:
- https://docs.fortinet.com/document/forticlient/7.4.7/administration-guide/539869/required-services-and-ports
- https://docs.fortinet.com/document/fortigate/7.4.12/administration-guide/559546/ssl-vpn-full-tunnel-for-remote-user

## OpenConnect Fortinet mode

Official OpenConnect documentation defines experimental PPP-based Fortinet support: PPP-over-DTLS is preferred, with PPP-over-TLS fallback. The newer non-PPP Fortinet tunnel protocol is unsupported by OpenConnect.

Reference: https://www.infradead.org/openconnect/fortinet.html

FortiOS 7.6.3+ IPsec migration transports are entry 029/other IPsec capability territory, not the legacy SSL tunnel wire protocol.
