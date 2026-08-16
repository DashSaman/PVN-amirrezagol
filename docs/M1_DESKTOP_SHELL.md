# M1 Desktop Client Shell

Status: **IN PROGRESS — CI failures are being resolved; no successful desktop build claim yet**

## Scope implemented in this slice

A real Compose Multiplatform desktop client shell exists under `apps/desktop` using Kotlin `2.4.10`, Compose Multiplatform `1.11.1`, product-owned `core:foundation` contracts, and no protocol engine dependency.

The shell includes PVNetwork branding, English/Persian toggle, LTR/RTL layouts, technical-token LTR isolation, system/light/dark theme preference, profile list, canonical connection state, and sanitized diagnostics. The initial state is intentionally empty/`DISCONNECTED`; no mock profile or fake connected state is used.

## Build gate

`.github/workflows/m1-desktop-shell-ci.yml` executes:

```bash
gradle --no-daemon :apps:desktop:test :apps:desktop:createDistributable --stacktrace
```

### Failure evidence retained

- Run `31938655622`: failed before a valid build result; source review identified a Compose scope/import issue.
- Run `31938706717`: failed dependency resolution because AndroidX transitive artifacts required Google Maven; `google()` was added.
- Run `31938789626`: dependency resolution passed, then Java 21/Kotlin 17 JVM target validation failed; Java source/target was aligned to 17.
- Run `31938875712`: JVM target validation passed and compilation advanced further. Kotlin compilation then rejected a direct `androidx.compose.foundation.layout.weight` import because the referenced symbol is internal in this Compose version. The unnecessary direct import is removed; `weight()` remains used only in its valid `RowScope`/`ColumnScope` receivers.

Failures remain recorded as failures; none is re-labelled as successful evidence.

## Still required before M1 close

- successful compile/unit-test/distributable evidence;
- real desktop launch smoke;
- fix any further gate failure.

No protocol adapter, connection, interoperability, device verification, Store verification or production readiness is claimed by M1 shell work.
