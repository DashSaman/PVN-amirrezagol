# 045 AnyTLS — cryptographic/security design

Canonical source: `anytls-go docs/protocol.md@fd6167acd6d73b9fa3e607659951847fbc9e6c50`.

AnyTLS runs **after a TLS handshake**. TLS supplies transport confidentiality/integrity; TLS configuration is explicitly excluded from AnyTLS protocol parameters and belongs to a separate config section.

Immediately after TLS establishment the client sends:
- `sha256(password)` — 32 bytes;
- big-endian uint16 padding0 length;
- variable padding0.

This SHA-256 value is protocol authentication material, not a password-storage KDF. The reusable plaintext password remains a secret and must be stored securely/redacted from URI/log/clipboard/support export.

Padding modifies TLS plaintext write sizes/segmentation for traffic-shape behavior; it is not encryption. Server-supplied padding schemes must be syntax/size bounded and scoped to the server/client profile rather than executed as trusted code.

TLS verification remains ON by default. `insecure` is explicit high-risk override; no custom TLS cryptography is permitted.
