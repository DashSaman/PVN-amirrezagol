# Fortinet FortiGate SSL VPN — Data Path / Wire Flow

Review date: 2026-08-14 UTC

## Proprietary FortiClient/FortiGate reference flow

1. FortiClient resolves/reaches the configured FortiGate SSL-VPN listener.
2. TLS session and server identity validation are established.
3. User/client authentication occurs according to FortiGate rule/portal policy (password, certificate, MFA/SAML or other exact supported mechanism).
4. FortiGate maps the authenticated user/group to an SSL-VPN portal and allocates tunnel/network policy.
5. FortiClient establishes the SSL VPN virtual tunnel; FortiGate represents tunnel traffic through `ssl.root` and applies firewall/routing/DNS/split/full-tunnel policy.
6. When configured/supported, DTLS can carry tunnel data over UDP; TLS/TCP remains the compatibility/control path.
7. Logs/monitoring expose active SSL VPN sessions and forwarded traffic.
8. Disconnect/timeout/re-auth/network interruption cleans up or re-establishes state according to exact FortiOS/FortiClient version and policy.

## OpenConnect compatible path

OpenConnect's official Fortinet implementation is PPP-based. It prefers PPP-over-DTLS and falls back to PPP-over-TLS when DTLS fails/is disabled. OpenConnect explicitly does **not** support Fortinet's newer non-PPP `v2` wire protocol and documents version-dependent reconnect limitations.

OpenConnect reference: https://www.infradead.org/openconnect/fortinet.html

Therefore a successful OpenConnect connection is only evidence for the supported PPP-compatible subset, not full FortiClient/FortiGate protocol parity.
