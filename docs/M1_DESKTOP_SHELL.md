# M1 Desktop Client Shell

Status: **IN PROGRESS — CI failures are being resolved; no successful desktop build claim yet**

## Scope implemented in this slice

A real Compose Multiplatform desktop client shell exists under `apps/desktop` using:

- Kotlin `2.4.10`;
- Compose Multiplatform `1.11.1`;
- product-owned `core:foundation` contracts;
- no protocol engine dependency.

The shell includes the M1 roadmap surfaces:

- PVNetwork-branded desktop window;
- English/Persian toggle;
- LTR/RTL layout selection using the shared locale contract;
- technical tokens rendered LTR inside RTL layouts;
- system/light/dark theme preference;
- profile list shell backed by `PVProfile`;
- canonical connection-state panel;
- diagnostics shell with sanitizer applied before presentation.

The initial shell state is intentionally honest: empty profile list, `DISCONNECTED`, empty diagnostics. No mock profile and no fake `CONNECTED` state are used to make the UI look complete.

## Build gate

`.github/workflows/m1-desktop-shell-ci.yml` executes:

```bash
gradle --no-daemon :apps:desktop:test :apps:desktop:createDistributable --stacktrace
```

### Failure evidence retained

- Run `31938655622`: failed before a valid build result; source review also identified a Compose scope/import issue which was corrected in commit `5de7f36480e9a985d2b05496d10f245dc7737eb0`.
- Run `31938706717`: failed dependency resolution. Its decoded job log showed Compose 1.11.1 requiring AndroidX artifacts such as `androidx.collection:collection:1.5.0` and `androidx.annotation:annotation:1.9.1`, while repository policy contained only Maven Central. Google Maven has now been added to dependency resolution.

These failures are not erased or re-labelled as successful tests.

## Still required before M1 close

- successful compile/unit-test/distributable evidence after the repository fix;
- a real desktop launch smoke so "first working client shell" is supported by runtime evidence rather than packaging alone;
- fix any further CI/runtime failure found by those gates.

No protocol adapter, connection, interoperability, device verification, Store verification or production readiness is claimed by M1 shell work.
