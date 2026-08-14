# 074 — REALITY

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **security-layer capability**, not a standalone VPN protocol.

Primary current engine/reference: Xray-core.

Shared evidence: `research/upstreams/xray-family/`.

Current Xray research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (MPL-2.0).

Research decision:

`SECURITY-LAYER CAPABILITY / XRAY PRIMARY REFERENCE`.

PVNetwork must model REALITY separately from application protocol, flow and transport. Support must be certified as exact combinations with selected protocol/core/server versions. Do not count REALITY as an independent VPN protocol in marketing.

Relevant shared files:

- `CONFIG_CAPABILITY_MODEL.md`
- `SOURCE_ARCHITECTURE.md`
- `SUPPORT_REUSE_DECISIONS.md`
- `SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `LIBXRAY_WRAPPER.md`

Later `COMPLETE-REFERENCE-v2` must add cryptographic/security design references, handshake/data-flow relationship, server implementation/deployment sources, install matrices and client/server UI/menu evidence required by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.
