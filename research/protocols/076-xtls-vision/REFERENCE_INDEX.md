# XTLS Vision — COMPLETE-REFERENCE-v2 index

Research date: 2026-08-15

Classification: **current Xray VLESS flow/mode capability**, not a standalone VPN protocol, not an outer transport and not the removed generic `security: "xtls"` mode.

Canonical engine/source:

- `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- license: MPL-2.0
- latest observed release: `v26.3.27` (published 2026-03-27)

Current-source anchors:

- `proxy/vless/vless.go` defines `XRV = "xtls-rprx-vision"`.
- `proxy/vless/encoding/addons.go` serializes the Vision flow in VLESS addons and selects `proxy.NewVisionWriter` for TCP body handling.
- `infra/conf/transport_internet.go` rejects legacy `security: "xtls"` and points toward Vision with TLS or REALITY.
- `proxy/proxy.go` contains the current Vision writer/data-path implementation.

Shared evidence:

- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- `research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`

Primary URLs:

- `https://github.com/XTLS/Xray-core/commit/7d214f8b094f75322fa3990f8aadad1c912f24f5`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/proxy/vless/vless.go`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/proxy/vless/encoding/addons.go`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/proxy/proxy.go`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/infra/conf/transport_internet.go`

Boundary: Vision must only be exposed where the selected core/version/application protocol/security/transport combination supports it. TLS/REALITY supply the security layer; Vision changes flow/data-path handling and must not be marketed as an independent VPN protocol.
