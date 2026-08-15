# 046 ShadowTLS — server implementations / ecosystem

Reviewed: 2026-08-15

Canonical implementation/spec authority: `ihciah/shadow-tls`.
- reviewed HEAD: `02dd0bc7bae8a2011729f95021690e694fd8e43e`, tree `3a98cb8012ffddb32809fd2cddeb2c208c68c646`, MIT;
- latest release: `v0.2.25` -> `dee3a5a819d6f56cfdd56c44a0d42be186c44238`, 2023-12-13;
- canonical v3 doc blob: `1766a21576ddc81958276fb1f2f82bbc0b9665c2`.

HEAD is newer than release and contains 2025 TLS1.2 test and WildcardSNI/SIP003 maintenance; release and source pin must remain separate.

Independent serious implementation: sing-box stable V1 pin `v1.13.18` / `45ca32dcb966f07f97fc888fe8586e359dbe8405`, GPL-3.0-or-later plus additional condition, with v1/v2/v3, uTLS, wildcard-SNI, fallback and Shadowsocks-composition tests. Throne GPL-3.0 supplies typed desktop UI reference.

Preferred generation for new profiles: v3; v1/v2 compatibility only and never silently upgraded.
