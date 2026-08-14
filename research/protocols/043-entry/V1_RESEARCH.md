# 043 — Hysteria2 — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: current QUIC-based proxy/tunnel protocol generation.

Primary upstream: `apernet/hysteria`.

Root license reviewed: MIT.

Research decision:

**`HIGH-PRIORITY MODERN QUIC PROXY TARGET / UPSTREAM ENGINE CANDIDATE`**

PVNetwork should evaluate the official upstream client/core behind a product-owned Hysteria Adapter while retaining product ownership of:

- canonical profile;
- protected authentication secrets;
- TLS/trust policy;
- QUIC/session/bandwidth options;
- TUN/routing/DNS;
- platform service/extension lifecycle;
- UI/localization;
- diagnostics and release packaging.

Do not treat a working local Hysteria2 proxy as proof of full-device VPN/TUN behavior.

Shared evidence:

- `research/upstreams/hysteria-family/SOURCE_ARCHITECTURE.md`
- `DEPENDENCIES_SECURITY_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`

Residual gaps:

- exact selected current release/commit and SBOM;
- exact library/API vs subprocess boundary;
- current issue/release regression matrix;
- mobile/client GUI details;
- device/performance/Store evidence;
- server installers/menus/cryptography/wire-flow deferred to mandatory v2.
