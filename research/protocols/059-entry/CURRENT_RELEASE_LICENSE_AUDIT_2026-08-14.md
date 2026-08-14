# 059 Netmaker — Current Release / License Audit — 2026-08-14

This addendum strengthens the existing 20-gate reconciliation with exact stable production-line evidence. Where more specific, it wins over moving `develop` references.

## Stable server pin
- `gravitl/netmaker` release `v1.6.0`, published 2026-06-12.
- tag resolves to commit `5f20416e13c307696605551459b116428b4053d3`.
- release tree `13e70d47ebbc9fa32de217dfe04274356c9ed1b5`.
- release commit has valid GitHub signature verification.
- current `SECURITY.md` says security work is supported on latest version(s), so production must stay on a current release rather than a moving historical line.

## Stable Netclient pin
- canonical repo: `gravitl/netclient`.
- stable release: `v1.6.0`, published 2026-06-12; release assets refreshed 2026-07-14.
- release publishes Darwin/Linux/Windows and multiple CPU architecture binaries with SHA-256 digests.
- exact root `LICENSE.txt` at `v1.6.0`: Apache-2.0.

This confirms that Netclient remains a separate first-party source/release artifact and must be versioned independently from the Netmaker server even when release numbers match.

## Exact server path-level license boundary
Exact `gravitl/netmaker@v1.6.0` `LICENSE.md` states:
- content under `pro/`, if present, uses `pro/PRO_LICENSE`;
- incorporated third-party components keep their original licenses;
- other Netmaker content is Apache-2.0.

Exact `pro/PRO_LICENSE` is not an open-source production grant. It permits production use only with a valid Netmaker Enterprise license/subscription and restricts copying/distribution/sublicensing/sale beyond granted terms. Development/testing permission must not be converted into commercial production permission.

PVNetwork consequence:
- Community paths: Apache-2.0 reuse candidate with NOTICE/dependency review.
- Netclient: Apache-2.0 reuse candidate with NOTICE/dependency review.
- `pro/`: **DO NOT SHIP/EMBED/USE IN COMMERCIAL PRODUCTION WITHOUT VALID ENTERPRISE RIGHTS**.
- Hosted/Enterprise product terms remain separate from source-code licenses.

## Current architecture/release implications
Current first-party release material confirms active concepts including enrollment keys/device approval, users/groups/ACLs, DNS, gateway-based full/split/site routing, egress/domain routing, monitoring and OAuth/basic-auth registration. Current release history also removes older per-node failover APIs in favor of gateway patterns.

Do not make obsolete failover APIs part of PVNetwork's canonical adapter merely because older Netmaker docs/examples exist.

## Gate effect
This addendum hardens gates 2, 3, 4, 5, 7, 11, 15, 18, 19 and 20. It does not add a runtime/device/certification condition to V1.

Entry 059 remains eligible for `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT CERTIFIED`.
