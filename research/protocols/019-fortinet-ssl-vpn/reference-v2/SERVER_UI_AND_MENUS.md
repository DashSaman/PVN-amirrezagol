# Fortinet FortiGate SSL VPN — Server / Control UI Map

Review date: 2026-08-14 UTC

Selected tunnel-mode reference: FortiOS 7.4.12.

## FortiGate administration concepts

- `System > Feature Visibility > SSL-VPN` — expose the feature where applicable;
- `VPN > SSL-VPN Portals` — full/split portal configuration and endpoint-facing portal behavior;
- `VPN > SSL-VPN Settings` — listener/interface/port, certificate, address range/network settings and authentication/portal mapping;
- Users/Groups and authentication rules — password/certificate/remote-auth/MFA policy as configured;
- firewall policies from/to `ssl.root` — tunnel authorization and egress/internal reachability;
- routing/DNS/split-tunnel policy;
- `VPN > Monitor > SSL-VPN Monitor` — connected tunnel users/sessions;
- `Log & Report > Forward Traffic` and event/security logs — traffic/session diagnostics;
- optional SSL-VPN Clients visibility/distribution functions where supported by exact release/licensing.

Official full-tunnel walkthrough: https://docs.fortinet.com/document/fortigate/7.4.12/administration-guide/559546/ssl-vpn-full-tunnel-for-remote-user

## Version boundary

FortiOS 7.6.3+ removes tunnel-mode GUI and CLI. Web mode becomes Agentless VPN. A UI designed for entry 019 must therefore show server-version capability/retirement state rather than presenting legacy SSL tunnel settings against a 7.6.3+ headend.
