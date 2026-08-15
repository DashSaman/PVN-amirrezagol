# 045 AnyTLS — server implementations / ecosystem

Reviewed: 2026-08-15

Canonical/reference protocol source: `anytls/anytls-go@fd6167acd6d73b9fa3e607659951847fbc9e6c50`, tree `59c373e406e4781ec4ae06d893c873dc29325ef8`, latest release `v0.0.13` (2026-06-27). The repository contains reference client/server programs plus protocol/session/padding code and canonical docs.

Critical source-license boundary: GitHub metadata still reports `license: null` and no explicit compatible LICENSE is present in the reviewed tree. Public source is therefore **reference-only / do-not-copy** until an explicit grant is verified.

Other serious implementations:
- `anytls/sing-anytls`: same-organization compatible implementation, GPL-3.0-or-later.
- `SagerNet/sing-box`: current full inbound/outbound implementation, GPLv3-or-later plus additional naming/association condition; interoperability/reference unless packaging/legal strategy permits.
- `ssrlive/anytls-rs`: independent Rust interoperability/test reference; README-MIT claim is not used as legal permission without exact license artifact.
- mihomo and closed clients listed by upstream: compatibility references only.

PVNetwork must not equate AnyTLS with generic TLS or ShadowTLS.
