# EtherIP — Installers and Deployment Projects

Review date: 2026-08-14 UTC

## SoftEther-backed deployment

Reuse the canonical SoftEther server/bridge deployment research in `research/upstreams/classic-tunnels-family/softether-protocol-reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md` only for product-level installation/lifecycle facts. EtherIP remains a distinct compatibility capability.

Relevant verified packaging/source facts from the SoftEther family:

- separate `vpnserver`, `vpnbridge` and `vpncmd` roles;
- DEB/RPM packaging paths in the pinned CMake source;
- source builds require tracked submodules;
- container deployments may require NET_ADMIN/bridge/host-network privileges depending topology;
- generic Kubernetes/stateless claims are unsafe for L2 bridge/tunnel state;
- unused multiprotocol listeners should be disabled.

## Native BSD deployment

- OpenBSD: `etherip(4)` is an OS-native pseudo-interface configured with native network/bridge tools and policy controls; no third-party “EtherIP installer” is required.
- FreeBSD: `gif(4)` + bridge is OS-native functionality; no separate consumer installer exists.

## Supply-chain boundary

No community one-line EtherIP installer or container image is selected. Any future image/script must be pinned to source/image digest, base image, package versions, privileges and license inventory before reuse.
