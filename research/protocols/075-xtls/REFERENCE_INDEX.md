# XTLS — COMPLETE-REFERENCE-v2 index

Research date: 2026-08-15

Classification: **legacy XTLS security-mode terminology/configuration family**.

Current canonical reference: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0.

Current-source evidence:

- `infra/conf/transport_internet.go` explicitly rejects `security: "xtls"` as a removed feature and directs users to `xtls-rprx-vision` with TLS or REALITY.
- `proxy/vless/vless.go` defines the current Vision flow constant `xtls-rprx-vision`; this is tracked separately as Entry 076.
- shared architecture/migration evidence: `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`, `SOURCE_ARCHITECTURE.md`, `SUPPORT_REUSE_DECISIONS.md`, `CLIENT_ECOSYSTEM.md`.

Primary URLs:

- `https://github.com/XTLS/Xray-core/commit/7d214f8b094f75322fa3990f8aadad1c912f24f5`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/infra/conf/transport_internet.go`
- `https://github.com/XTLS/Xray-core/blob/7d214f8b094f75322fa3990f8aadad1c912f24f5/proxy/vless/vless.go`

Boundary: historical XTLS metadata may need import/migration preservation, but PVNetwork must not expose the removed generic XTLS security mode as a current standalone capability. Current Vision semantics are Entry 076; TLS and REALITY remain separate security entries.
