# TLS — COMPLETE-REFERENCE-v2 index

Research date: 2026-08-15

Classification: **security protocol layer**, not a standalone VPN protocol.

## Standards and source pins

- Current standards baseline: RFC 9846, TLS 1.3 (July 2026), which obsoletes RFC 8446 while retaining TLS version number 1.3.
- Identity verification guidance: RFC 9525.
- Primary Go/Xray-family implementation: Go `crypto/tls`, reviewed at Go 1.26.5 source commit `c19862e5f8415b4f24b189d065ed739517c548ba`, BSD-3-Clause.
- Xray integration reference: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0.

## Existing evidence reused

- `research/protocols/077-entry/V1_GATE_RECONCILIATION.md`
- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- `research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`

## Primary references

- `https://www.rfc-editor.org/info/rfc9846/`
- `https://www.rfc-editor.org/info/rfc9525/`
- `https://github.com/golang/go/tree/c19862e5f8415b4f24b189d065ed739517c548ba/src/crypto/tls`
- `https://github.com/XTLS/Xray-core/commit/7d214f8b094f75322fa3990f8aadad1c912f24f5`

Boundary: TLS security is a separate axis from application protocol, transport, REALITY and uTLS/fingerprinting. PVNetwork should consume maintained engine/native TLS and must not reimplement TLS cryptography.
