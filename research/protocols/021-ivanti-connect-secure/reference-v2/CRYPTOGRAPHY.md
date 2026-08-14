# Ivanti Connect Secure — Cryptography / Security Boundary

Reviewed: 2026-08-14 UTC

For the Pulse-compatible VPN-tunneling path, authoritative public compatibility analysis from OpenConnect documents:

- IF-T/TLS as the TCP tunnel/control transport;
- EAP/EAP-TTLS participation in authentication;
- ESP as the accelerated data transport where negotiated/supported;
- TLS server identity validation, user authentication and endpoint posture as separate controls.

OpenConnect protocol reference: https://www.infradead.org/openconnect/pulse.html

ICS also has a separate IKEv2 access capability. Its IKE/ESP negotiation must not be used to describe the Pulse IF-T/TLS path.

Ivanti's proprietary cryptographic implementation/source is vendor-owned. Current 25.1.2.1 is explicitly a security-enhancement release, so exact supported release/security guidance must supersede stale Pulse-era cipher assumptions.

PVNetwork reuse should delegate TLS/crypto to maintained upstream/client libraries and never invent replacement cryptography. Private keys, passwords, cookies, SAML tokens and endpoint credentials remain secret-store state and must be redacted from logs/diagnostics.
