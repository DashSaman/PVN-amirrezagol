# 044 TUIC v5 — installers and deployment projects

Reviewed: 2026-08-15

The spec repository has no implementation or installer. Deployment evidence therefore comes from implementation projects.

- ClashRS: Rust/Cargo workspace with library, executable/FFI and web dashboard. Deployment/runtime packaging is project-specific; dependency/NOTICE/SBOM/API stability must be frozen before reuse.
- shoes: Rust application with YAML configuration, server/client/TUN roles, multi-address/port-range server binding, QUIC TLS settings and active tests. It is a strong MIT server/interoperability reference.
- Itsusinn/tuic: Rust/Cargo client/server workspace with TOML/YAML/JSON/JSON5 configuration, containers, ACME/TLS, routing/masquerade/observability and real 0-RTT-resumption tests. Copyleft/component licenses make it reference-only by default.

Container images, release binaries and remote install helpers must be digest/version pinned at implementation freeze. No moving `latest` or unreviewed installer script is an acceptable production pin.
