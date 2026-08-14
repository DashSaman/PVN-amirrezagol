# OpenConnect — Source Pin

Research date: 2026-08-14

- Canonical upstream: `https://gitlab.com/openconnect/openconnect`
- Stable reviewed release: **v9.21**
- Exact release commit: **`8b702bf2dbaf11302ed98629214b1df5d50a12aa`**
- Stable source tree: `https://gitlab.com/openconnect/openconnect/-/tree/8b702bf2dbaf11302ed98629214b1df5d50a12aa`
- Canonical tag API evidence: `https://gitlab.com/api/v4/projects/openconnect%2Fopenconnect/repository/tags/v9.21`
- Release evidence: `https://gitlab.com/openconnect/openconnect/-/releases/v9.21`
- Signed release tarball distribution: `https://www.infradead.org/openconnect/download/openconnect-9.21.tar.gz` plus `.asc`; upstream download documentation states release tarballs since 3.13 are PGP-signed.
- Current development tree is separate from the release pin: `https://gitlab.com/openconnect/openconnect/-/tree/master`
- Public API: `https://gitlab.com/openconnect/openconnect/-/blob/master/openconnect.h`
- Reviewed public API version on current master: **5.10**
- License identified by canonical source/header: **LGPL-2.1**

The GitLab v9.21 tag API resolves the release to `8b702bf2dbaf11302ed98629214b1df5d50a12aa` (2026-06-16). The archived GitHub repository is a historical mirror and must not be treated as current release authority.

## Source-tree inventory boundary

The immutable v9.21 tree URL above is the source-tree reference used by this dossier. The family dossier inventories the important source/build/test/platform/front-end boundaries in dedicated evidence files. A locally materialized recursive archive manifest was not available through the current connector and is retained as an explicit tooling limitation; this does **not** change the immutable source identity. Any later vendoring/build must independently verify the downloaded tarball signature and generate an SBOM/file manifest from the exact artifact actually shipped.

## Important source areas

Canonical source contains shared library/API files, protocol-specific C modules, TLS backends, tunnel/platform code, Android/Java wrappers, translations, docs and tests. Current tree evidence includes protocol modules for Cisco/AnyConnect, GlobalProtect, Juniper, Pulse, F5, Fortinet and Array families plus PPP/ESP/DTLS-related support.

## PVNetwork direction

Evaluate OpenConnect through its **public library API**, not private structures. Browser/SSO/auth UI, protected credential storage and PVNetwork session state must remain above the library boundary.

Every library upgrade must be treated as a compatibility change and rerun per-vendor regression tests. v9.21 itself was released to fix a CPU/infinite-loop regression exposed more readily after v9.20 changes.

Status: `V1-SOURCE-PINNED`; no implementation claim.