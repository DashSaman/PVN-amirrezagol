# M1 Desktop Client Shell

Status: **IN PROGRESS — CI failures are being resolved; no successful desktop build claim yet**

## Scope implemented in this slice

A real Compose Multiplatform desktop client shell exists under `apps/desktop` using:

- Kotlin `2.4.10`;
- Compose Multiplatform `1.11.1`;
- product-owned `core:foundation` contracts;
- no protocol engine dependency.

The shell includes the M1 roadmap surfaces: PVNetwork branding, English/Persian toggle, LTR/RTL layouts, technical-token LTR isolation, system/light/dark theme preference, profile list, canonical connection state, and sanitized diagnostics.

The initial shell state is intentionally honest: empty profile list, `DISCONNECTED`, empty diagnostics. No mock profile and no fake `CONNECTED` state are used to make the UI look complete.

## Build gate

`.github/workflows/m1-desktop-shell-ci.yml` executes:

```bash
gradle --no-daemon :apps:desktop:test :apps:desktop:createDistributable --stacktrace
```

### Failure evidence retained

- Run `31938655622`: failed before a valid build result; source review also identified a Compose scope/import issue which was corrected in commit `5de7f36480e9a985d2b05496d10f245dc7737eb0`.
- Run `31938706717`: failed dependency resolution because AndroidX transitive artifacts were not available from Maven Central alone. Google Maven was added in commit `c84a4ef7dfa7166002e16057131dd2d9b56e4095`.
- Run `31938789626`: dependency resolution succeeded and the build advanced through runtime-image creation and foundation compilation. It then failed at `:apps:desktop:compileKotlin` because Java targeted JVM 21 while Kotlin targeted JVM 17. The desktop Java source/target compatibility is now explicitly pinned to 17 while CI continues to run on the JDK 21 toolchain.

These failures are evidence and are not erased or re-labelled as successful tests.

## Still required before M1 close

- successful compile/unit-test/distributable evidence after the JVM target alignment;
- a real desktop launch smoke so "first working client shell" is supported by runtime evidence rather than packaging alone;
- fix any further CI/runtime failure found by those gates.

No protocol adapter, connection, interoperability, device verification, Store verification or production readiness is claimed by M1 shell work.
