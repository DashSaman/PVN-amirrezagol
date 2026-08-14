# 041 — Shadowsocks 2022 — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: modern Shadowsocks protocol/cipher-generation compatibility target, distinct from generic legacy Shadowsocks entry 040.

Primary open-source implementation/reference: `shadowsocks/shadowsocks-rust` plus current implementations in selected multi-protocol cores.

Research decision:

**`MODERN SHADOWSOCKS TARGET / SHADOWSOCKS-RUST OR EXISTING APPROVED CORE`**

Root license of `shadowsocks-rust` reviewed: MIT.

PVNetwork should compare direct shadowsocks-rust integration against already-approved cores for exact Shadowsocks 2022 method/protocol parity, release/security latency, platform support, binary size and maintenance burden.

Canonical model must preserve exact Shadowsocks generation/method/auth/key semantics and must never silently downgrade a 2022 profile to an older Shadowsocks method.

Reusable secrets belong in platform secure storage rather than ordinary profile JSON or logs.

Shared related evidence:

- `research/upstreams/xray-family/`
- `research/upstreams/modern-proxy-family/`

Residual gaps:

- exact current shadowsocks-rust release/commit pin and SS2022 feature matrix;
- exact approved-core parity comparison;
- current issues/advisories/performance/platform evidence;
- full client menus/import behavior;
- server installers, cryptography/wire flow and install matrices deferred to mandatory v2.
