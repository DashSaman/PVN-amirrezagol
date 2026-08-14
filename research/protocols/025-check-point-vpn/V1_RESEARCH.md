# 025 — Check Point VPN — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Formal 20-gate reconciliation:

`research/protocols/025-check-point-vpn/V1_GATE_RECONCILIATION.md`

Current public-source audit:

`research/protocols/025-check-point-vpn/SNX_RS_SOURCE_AUDIT.md`

Decision:

**`VALUABLE OPEN-SOURCE INTEROPERABILITY REFERENCE / AGPL DIRECT-EMBED CAUTION / OFFICIAL-VENDOR CERTIFICATION REQUIRED`**

Primary open-source reference: `ancwrd1/snx-rs` **v6.2.4**, exact reviewed commit `a263c47cecdbbc019bc77c482bb77525a02e20a1`, Rust/Cargo, AGPL-3.0.

Use it for source-level interoperability, IPsec/SSL, SSO/MFA/certificate, routing/DNS, platform, UI, packaging, CI and regression lessons. Do not directly embed AGPL code into a closed PVNetwork product without an intentional compatible legal/architecture model.

Official Check Point clients/appliances/documentation remain the authoritative proprietary interoperability target. Official code, branding and assets are reference-only and are not copied.

The v1 audit explicitly preserves critical future certification cases such as effective Office Mode route equivalence and reconnect/data-path health rather than treating a reported `Connected` state as sufficient.

Shared family evidence remains under:

`research/upstreams/vendor-enterprise-family/`

`COMPLETE-RESEARCH-v1` means research closure only. Runtime gateway interoperability, implementation, packet captures, Store/notarization work, exact production SBOM/legal model and production support remain later evidence states. Mandatory v2 later adds exhaustive gateway/server versions, full admin/client menus, cryptography/wire flow, deployment/install references and topology evidence.