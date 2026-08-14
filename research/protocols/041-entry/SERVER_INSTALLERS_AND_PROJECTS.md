# 041 Shadowsocks 2022 — installers/deployment projects

Reviewed: 2026-08-15

Deployment machinery is shared with the same `shadowsocks-rust` runtime reviewed for entry 040, while protocol semantics are not.

Evidence-backed paths at `shadowsocks-rust@9214fdaf...` include Cargo/crates.io install, Homebrew, Snap, static release assets, source builds, GHCR Docker images, Kubernetes YAML and Helm chart. SS2022 support requires a build/package with `aead-cipher-2022` enabled.

No community web panel is promoted as a canonical SS2022 administration surface without exact method/EIH evidence. Generic Shadowsocks panels that only accept classic password methods are not sufficient.

Supply-chain rules: freeze exact package/image digest at implementation time; do not deploy moving `latest`; treat plugins, chart/manifests, config delivery, PSKs/iPSKs/uPSKs and control-plane credentials as separate trust boundaries.
