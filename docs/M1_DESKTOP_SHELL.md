# M1 Desktop Client Shell

Status: **IN PROGRESS — source committed; build/test/distributable CI pending for this commit**

## Scope implemented in this slice

A real Compose Multiplatform desktop client shell now exists under `apps/desktop` using:

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

`.github/workflows/m1-desktop-shell-ci.yml` runs the pinned build environment and executes:

```bash
gradle --no-daemon :apps:desktop:test :apps:desktop:createDistributable --stacktrace
```

This must succeed before this slice can be marked BUILT/TESTED. A source commit is not a build result.

## Still required before M1 close

- real successful compile/unit-test/distributable evidence for this commit;
- a real desktop launch smoke so "first working client shell" is supported by runtime evidence rather than packaging alone;
- fix any CI/runtime failure found by those gates.

No protocol adapter, connection, interoperability, device verification, Store verification or production readiness is claimed by M1 shell work.
