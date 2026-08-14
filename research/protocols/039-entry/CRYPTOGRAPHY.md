# 039 Trojan — cryptographic/security design

Reviewed: 2026-08-15

## Canonical protocol boundary

`trojan-gfw/trojan` protocol documentation requires a **real TLS handshake first**. Subsequent Trojan request/payload bytes are protected by that TLS channel. TLS certificate/server-identity validation is therefore a primary security boundary and is independently versioned/configured from Trojan authentication.

## Trojan password token

Original spec and current Xray agree on a 56-byte lowercase hex token equal to `hex(SHA224(password))` followed by CRLF. Xray `proxy/trojan/config.go` derives this exact token using SHA-224 and hex encoding.

This SHA-224 token is protocol authentication material, not a password-storage KDF. Reusable plaintext passwords still require secure local storage and must be redacted from logs/share links/config exports.

## Payload security

Trojan protocol framing itself does not add an independent payload cipher after the TLS boundary. Confidentiality/integrity comes from the configured outer TLS/security layer. Current Xray can compose Trojan with its stream-security machinery, but PVNetwork must distinguish canonical Trojan-over-real-TLS compatibility from Xray-specific alternate compositions.

## Current Xray lifecycle warning

At `v26.7.28`, Xray warns that Trojan lacks newer Flow capabilities and recommends VLESS with Flow & Seed. That is a strategic/deprecation signal, not permission to silently migrate existing Trojan profiles.
