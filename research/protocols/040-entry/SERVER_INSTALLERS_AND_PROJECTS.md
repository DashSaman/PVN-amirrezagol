# 040 Shadowsocks — server installers/deployment projects

Reviewed: 2026-08-15

`shadowsocks-rust@9214fdaf...` documents:

- crates.io `cargo install shadowsocks-rust`;
- Homebrew (macOS/Linux), Snap and distro-package ecosystems;
- downloadable static releases for Linux/macOS/Windows targets;
- official GHCR Docker images for `sslocal-rust` and `ssserver-rust` on multiple Linux architectures;
- Kubernetes YAML and Helm chart under `k8s/`;
- source build/Cargo, systemd/Snap service operation;
- OpenWrt/community integrations separately referenced upstream.

Major admin/panel reference: 3X-UI can manage Shadowsocks inbounds/users as part of Xray, but it is a GPL-3.0 multi-protocol panel and not the dedicated protocol implementation.

Supply-chain: never leave `latest` container tags or remote manifests unpinned in production; freeze image digest/release asset/checksum and plugin binaries. Plugins are independent executable/license/update/security boundaries.
