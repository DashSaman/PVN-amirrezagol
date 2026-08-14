# 039 Trojan — ports, transports and handshake

Reviewed: 2026-08-15

Trojan has no mandatory universal port in its framing. Port 443 is conventional for HTTPS-like TLS deployment but remains operator configuration.

Canonical handshake: `TCP connection -> genuine TLS handshake -> 56-byte SHA224 password token + CRLF -> Trojan command/address/port + CRLF -> TCP stream or framed UDP-associate data`.

Original Trojan security relies on genuine TLS. Xray separates protocol from `streamSettings`, so transport/security combinations are version-specific Xray capabilities and must not be projected back into the historical Trojan specification. Exact TLS versions/ciphers/ALPN/SNI/certificate policy belong to the TLS/selected stream-security layer.

Xray fallbacks can route invalid/unmatched traffic based on configured name/ALPN/path/destination and PROXY protocol version 0/1/2; fallback behavior is implementation/config specific, not a mandatory Trojan wire feature.
