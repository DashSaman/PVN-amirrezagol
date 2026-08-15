# 049 SOCKS4 — Cryptography

Review: 2026-08-15

**SOCKS4 defines no cryptographic confidentiality or integrity layer.** It must not be marketed as an encrypted VPN/proxy.

The protocol carries a USERID field, but this is not a modern password-authentication or key-exchange framework. There is no protocol-defined AEAD, cipher-suite negotiation, KDF, certificate/PSK handshake, forward secrecy, replay protection or rekey mechanism.

If SOCKS4 is carried inside SSH/TLS/VPN, the cryptographic properties belong to that outer composition and must be modeled/certified separately. 3proxy's current support for TLS-secured parent variants is implementation composition, not a property of SOCKS4 itself.

Security consequence: destination/application confidentiality depends on the application protocol or an explicit outer secure transport. Logs/profile exports must still redact any sensitive endpoint/user metadata.