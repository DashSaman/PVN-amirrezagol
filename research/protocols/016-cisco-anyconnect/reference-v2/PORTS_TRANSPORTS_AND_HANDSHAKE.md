# Cisco AnyConnect — Ports / Transports / Handshake

Review date: 2026-08-14 UTC

Core Cisco Secure Client / AnyConnect-compatible SSL VPN reference:

- TLS/CSTP: TCP **443** in the common/default headend configuration;
- DTLS: UDP **443** in the common/default headend configuration, optional/negotiated;
- optional HTTP redirection: TCP **80** where configured;
- Cisco IKEv2/IPsec mode: UDP **500/4500**, a separate transport family from CSTP/DTLS.

Cisco ASA 9.24 documents DTLS as a simultaneous companion tunnel to SSL and TLS fallback behavior via DPD.

OpenConnect flow is two-stage: HTTPS authentication obtains session/cookie state, then the tunnel is established via HTTPS/CSTP; DTLS is negotiated when possible.

Ports are configuration/headend facts, not proof every deployment exposes all transports. Proxy/NAT/firewall behavior can force TLS-only operation.
