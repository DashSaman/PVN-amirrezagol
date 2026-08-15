# 043 Hysteria2 — server implementations / ecosystem

Reviewed: 2026-08-15

Canonical current implementation: `apernet/hysteria`.

- current architecture/master pin: `14e9fff1d972ab0187ac7fcf75b9514dc8664065`
- tree: `39ad00e06933ebcc3077e825cc0ac969875a03cd`
- license: MIT
- current stable release: `app/v2.12.1`, published 2026-08-09
- app module family: `github.com/apernet/hysteria/app/v2`, local `core/v2` and `extras/v2`

The upstream protocol authority is `PROTOCOL.md` at the same pin. It defines Hysteria2 (from 2.0.0, internally sometimes v4) independently of Hysteria v1.

PVNetwork decision: high-priority modern target; official upstream is the primary engine/reuse candidate behind a product-owned adapter. Multi-protocol implementations/clients are interoperability/UX candidates only unless separately pinned for parity/license/security.
