# 040 — Shadowsocks

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: proxy protocol with multiple mature engine implementations.

Xray evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (MPL-2.0).

Research decision:

`MULTI-CORE / DEDICATED-IMPLEMENTATION COMPARISON REQUIRED`.

Xray supports Shadowsocks, but PVNetwork must compare dedicated and other approved cores for current method/cipher coverage, UDP behavior, modern variants, performance, platform packaging, maintenance/security and license/dependency cost. Do not choose Xray solely because it is already integrated for VLESS.

Canonical Shadowsocks data should stay core-neutral where semantics permit.

Later `COMPLETE-REFERENCE-v2` must add server implementations/installers/panels, server/client install matrices, exhaustive menus, cryptography, data/wire flow, ports/handshake and deployment topology evidence required by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.
