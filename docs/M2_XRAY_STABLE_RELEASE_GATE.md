# M2 Xray Stable Release / Import Gate

Status: **BLOCKED for bundled/imported production Xray runtime; host-supplied managed-subprocess work may continue**

Date: 2026-08-16  
CI fixture digest reconciled: 2026-08-18

## Decision

PVNetwork will not approve the current non-prerelease Xray-core release as a bundled/imported production dependency. The release-selection gate is blocked until an acceptable non-prerelease release exists outside the currently identified vulnerable range and its source, assets, license, dependency/security evidence, update policy and rollback metadata pass review.

This blocker does **not** prohibit product-owned runtime-boundary work against an externally supplied Xray executable. It also does not authorize libXray, source embedding or product distribution of any Xray binary.

## Current non-prerelease stable candidate — rejected

GitHub `releases/latest` currently resolves to:

- upstream: `XTLS/Xray-core`
- tag: `v26.3.27`
- prerelease: `false`
- commit: `d2758a023cd7f4174a5a5fa4ff66e487d4342ba0`
- tree: `dbcb1d36a7ce09215956aa450001ee3a3f422608`
- Linux x86_64 asset: `Xray-linux-64.zip`
- asset SHA-256: `23cd9af937744d97776ee35ecad4972cf4b2109d1e0fe6be9930467608f7c8ae`
- snapshot license: Mozilla Public License 2.0

The source/tag/asset metadata is sufficiently pinned for review, but this candidate is **rejected** because the upstream repository security-advisory review identified `GHSA-r3q3-8h45-5fmm`, whose affected range includes `>= v26.1.13, < v26.7.11` and whose patched range begins at `v26.7.11`. Therefore `v26.3.27` is not an acceptable PVNetwork production pin.

## Patched release line — not production-approved

The upstream release line at and after the advisory fix includes `v26.7.11`. The inspected `v26.7.11` and `v26.7.28` GitHub releases are marked prerelease, so they do not satisfy PVNetwork's present stable-production release gate.

The newest inspected patched-line candidate is recorded for **CI-only fixture use**, not product distribution:

- tag: `v26.7.28`
- prerelease: `true`
- commit: `5ca6f4b7d4dc20a881d4330e498892697627ec0c`
- tree: `a6ef5707377e4305bd991427eca71fc6eadeff61`
- Linux x86_64 asset: `Xray-linux-64.zip`
- asset SHA-256: `8195d909f1109b8f3d99eefe401a3c451d7bf4af71f24d3815420f77e5dd2a40`
- snapshot license: Mozilla Public License 2.0
- snapshot module: `github.com/xtls/xray-core`, Go 1.26 with a non-trivial direct and transitive dependency surface recorded by upstream `go.mod`.

The CI-fixture checksum above was reconciled on 2026-08-18 against the official GitHub release asset metadata for `Xray-linux-64.zip`; an earlier transcription in this document was incorrect and must not be reused.

A `go.mod` snapshot is dependency inventory evidence, **not** a vulnerability-clearance claim. Before any future bundled production approval, PVNetwork must run and retain an explicit SBOM/dependency and vulnerability review for the selected release.

## Desktop runtime strategy while the production gate is blocked

PVNetwork may continue a product-owned JVM/desktop runtime boundary that:

- accepts/discovers only a host-supplied Xray executable;
- never downloads an unpinned `latest` binary at runtime;
- never invokes a shell to start Xray;
- creates transient private configuration material only after resolving secrets through `SecretStore`;
- uses private runtime-directory/file permissions where the OS supports them;
- validates generated configuration fail-closed before starting the long-lived process;
- maps process readiness/exit/stop into canonical PVNetwork connection states;
- drains child output without retaining raw secrets or unbounded diagnostics;
- removes transient configuration on all stop/failure paths.

The inspected upstream CLI at `v26.7.28` documents `xray run -c config.json` and `-test` for configuration validation without launching the server. Runtime implementation must still verify the exact executable/version it is handed before relying on those semantics.

## CI-only fixture exception

A patched prerelease such as the exact `v26.7.28` artifact above may be used **ephemerally in GitHub Actions only** if all of the following are true:

1. the workflow names the exact tag and asset;
2. SHA-256 is verified before extraction/execution;
3. the artifact is not committed, cached as a product dependency, packaged or published by PVNetwork;
4. the evidence is labeled CI-fixture interoperability only;
5. passing the fixture never promotes the prerelease to a production dependency.

## Update and rollback policy for a future production pin

A future product-managed Xray artifact must be an explicit allowlisted version. Promotion requires review of tag/commit/tree, official release assets/checksums, license/notice obligations, dependency/SBOM/vulnerability evidence and platform lifecycle behavior. A checksum mismatch must fail closed. Upgrade metadata must retain the previously verified version so a rollback can restore the last approved artifact rather than resolving `latest` dynamically.

## Unblock condition

Re-run this gate when upstream publishes a suitable **non-prerelease** Xray-core release at or beyond the patched line. Approval requires all of:

- exact tag/commit/tree lock;
- exact official per-platform asset digests;
- MPL-2.0 distribution/notice/source-availability obligations reviewed for the intended distribution model;
- dependency inventory/SBOM and vulnerability review for the selected snapshot;
- managed-subprocess lifecycle/update/rollback strategy validated;
- no unresolved security advisory that places the selected release in an affected range.

Until then, the bundled/imported production Xray dependency gate remains **BLOCKED** while host-supplied runtime engineering may proceed independently.
