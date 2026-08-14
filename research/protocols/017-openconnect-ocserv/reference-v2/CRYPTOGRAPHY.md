# OpenConnect / ocserv — Cryptography / Security Boundary

Review date: 2026-08-14 UTC

OpenConnect/ocserv AnyConnect-compatible mode uses TLS for authentication/control/CSTP and DTLS for the optional UDP data path. Server certificate/private key and client trust policy are distinct from user authentication.

OpenConnect current documentation disables obsolete 3DES/RC4 compatibility ciphers by default. ocserv delegates TLS/DTLS cryptography to maintained crypto dependencies (not PVNetwork-written cryptography) and supports certificate plus pluggable user-auth methods.

ocserv 1.5.0 is security-significant: release notes fix an unauthenticated heap-buffer overflow in worker cookie parsing and a DTLS MTU integer-underflow/buffer-overflow condition for authenticated clients. The exact 1.5.0 or later reviewed security baseline must therefore be explicit.

Secrets/private keys/tokens remain in platform/server secret storage; never log them. Authentication method availability and TLS/DTLS policy are exact-version/configuration concerns.
