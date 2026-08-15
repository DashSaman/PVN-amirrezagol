# REALITY — COMPLETE-REFERENCE-v2 index

Research date: 2026-08-15

Classification: **Xray security-layer capability**, not an independent VPN protocol.

## Canonical engine/source pins

- Xray-core repository: `XTLS/Xray-core`
- Current reviewed `main`: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (2026-08-12)
- License: MPL-2.0
- Latest GitHub release observed during this review: `v26.3.27` (published 2026-03-27)
- REALITY implementation directory at the pin: `transport/internet/reality/`
- Human/config boundary at the pin: `infra/conf/transport_internet.go`

Canonical source paths used directly:

- `transport/internet/reality/config.go`
- `transport/internet/reality/config.proto`
- `transport/internet/reality/reality.go`
- `infra/conf/transport_internet.go`

## Repository evidence reused

- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- `research/upstreams/xray-family/DEPENDENCIES_TESTS_RELEASES.md`
- `research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/xray-family/INDEX.md`
- `research/protocols/074-reality/README.md`

## Primary evidence URLs

- `https://github.com/XTLS/Xray-core`
- `https://github.com/XTLS/Xray-core/commit/7d214f8b094f75322fa3990f8aadad1c912f24f5`
- `https://github.com/XTLS/Xray-core/tree/7d214f8b094f75322fa3990f8aadad1c912f24f5/transport/internet/reality`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/transport/internet/reality/config.proto`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/transport/internet/reality/reality.go`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/infra/conf/transport_internet.go`
- `https://github.com/XTLS/Xray-core/releases/tag/v26.3.27`

## Scope boundary

REALITY is represented independently from application protocol, transport and flow. The reviewed Xray config builder exposes `security: "reality"` as a security axis and currently constrains it to RAW/TCP, XHTTP and gRPC transports. Legacy `security: "xtls"` is explicitly removed in current source. VLESS, XTLS Vision, TLS and the selected transport remain separate numbered/capability concerns.

The current source also contains optional ML-DSA-65 material in the REALITY config and client verification path. This is documented as current implementation evidence, not generalized into a claim that every historical REALITY deployment uses post-quantum authentication.

No claim of production support, censorship resistance, Store compatibility, device certification, interoperability certification or measured performance is implied by this research index.
