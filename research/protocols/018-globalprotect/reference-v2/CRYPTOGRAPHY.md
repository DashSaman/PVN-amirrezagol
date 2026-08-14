# Palo Alto GlobalProtect — Cryptography / Security Boundary

Review date: 2026-08-14 UTC

GlobalProtect supports two tunnel data modes that must remain distinct:

1. **SSL VPN tunnel** — TLS protects the tunnel/control path.
2. **IPsec tunnel** — ESP protects data after GlobalProtect control/authentication establishes the necessary tunnel parameters. Palo Alto's current documentation explicitly states that SSL/TLS is used for key/algorithm exchange even when IPsec is the data tunnel; this must not be mislabeled as a generic IKEv2 exchange.

Current PAN-OS configuration separates:

- SSL/TLS Service Profile — server identity and TLS versions/ciphers;
- IPsec Crypto Profile — ESP encryption/authentication algorithms;
- certificates / client certificate profiles;
- user authentication / MFA/SAML policy;
- optional HIP/posture policy.

Current vendor guidance supports modern AES/AES-GCM choices subject to platform/release policy. Legacy compatibility settings must not silently become PVNetwork defaults.

The proprietary GlobalProtect application/headend cryptographic implementation is vendor-owned. OpenConnect GP mode delegates TLS/crypto to its maintained public dependencies and remains a separate compatible implementation.

Never disable server identity verification to make a mismatched portal/gateway work without an explicit security exception.
