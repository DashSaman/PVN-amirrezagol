# 042 Hysteria v1 — cryptographic/security design

Hysteria v1 is a customized QUIC-based protocol. Current frozen source creates a `tls.Config` and uses QUIC (`apernet/quic-go` fork) for the transport. Server configuration requires ACME material or certificate/key; client exposes server name, custom CA and `insecure` certificate-verification bypass.

The protocol-specific `auth` bytes are sent on a control QUIC stream **after** the QUIC/TLS connection is established. They are application authentication, not the transport encryption primitive.

The `obfs` packet-connection layer is an obfuscation capability and must not be described as a replacement for TLS/QUIC cryptography.

Security rules: certificate validation is default/trust boundary; `insecure` must never be silently enabled; auth material is secret; legacy QUIC fork/dependencies require separate vulnerability/SBOM review before shipping. Exact TLS cipher/version behavior belongs to the pinned Go/quic-go implementation and is not invented here.
