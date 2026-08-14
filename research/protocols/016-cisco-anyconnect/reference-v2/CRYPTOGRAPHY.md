# Cisco AnyConnect — Cryptography / Security Boundary

Review date: 2026-08-14 UTC

Cisco's current Secure Client 5.x feature/license/OS documentation separately enumerates VPN TLS/DTLS and IKEv2/IPsec cryptographic capabilities. Exact algorithm availability is release/headend/policy dependent.

For the AnyConnect-compatible SSL VPN path tracked here:

- HTTPS/TLS is used for initial authentication/control and the CSTP tunnel;
- DTLS is an optional UDP data tunnel for lower latency where negotiated/allowed;
- certificate/server identity validation and user authentication are distinct;
- posture/SSO/MFA are higher-layer policy/auth flows, not replacement transport cryptography;
- Cisco's optional IKEv2/IPsec capability is a separate transport mode and must not be conflated with TLS/DTLS.

OpenConnect v9.21 uses maintained TLS backends and explicitly disables obsolete 3DES/RC4 compatibility ciphers by default in current documentation. PVNetwork should use the maintained public API/backend rather than implement private Cisco cryptography.

Never weaken verification merely to match a legacy headend without an explicit compatibility/security policy.
