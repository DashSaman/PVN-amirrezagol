# 043 Hysteria2 — cryptographic/security design

Canonical `PROTOCOL.md` requires standard QUIC RFC 9000; QUIC uses TLS 1.3 security. Hysteria2 additionally requires HTTP/3 RFC 9114 behavior for masquerading/authentication.

Client authentication is an HTTP/3 `POST /auth` over the already protected QUIC connection with `Hysteria-Auth` and rate/padding headers. Successful Hysteria auth is status 233; failed auth must behave like ordinary HTTP/3 content/reverse-proxy behavior rather than exposing a fixed probe signature.

TLS server identity/CA/pinning/client-certificate/ECH settings are distinct trust controls. `insecure` is not a safe default.

Salamander is explicitly **obfuscation**, not replacement transport encryption. Per QUIC packet it prepends an 8-byte random salt, computes BLAKE2b-256 over PSK+salt and XORs payload with the repeated hash. QUIC/TLS remains the confidentiality/integrity layer.

Reviewed High advisories set a minimum historical security floor of 2.9.2 for two core issues; current 2.12.1 still requires current dependency/SBOM/advisory review before production certification.
