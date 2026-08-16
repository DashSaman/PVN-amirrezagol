# M1 Desktop Client Shell

Status: **IN PROGRESS — build/test/distributable PASS; runtime launch validation pending**

## Implemented source scope

A real Compose Multiplatform desktop client shell exists under `apps/desktop` using Kotlin `2.4.10`, Compose Multiplatform `1.11.1`, product-owned `core:foundation` contracts, and no protocol engine dependency.

The shell includes PVNetwork branding, English/Persian toggle, LTR/RTL layouts, technical-token LTR isolation, system/light/dark theme preference, profile list, canonical connection state, and sanitized diagnostics. Initial state is intentionally empty and `DISCONNECTED`; no mock profile or fake connection state is used.

## Real build/test evidence

GitHub Actions run `31938962751` on commit `00e8a6cc829084dbaf0f535a4b53ace885e342c8` completed **SUCCESS**. It executed:

```bash
gradle --no-daemon :apps:desktop:test :apps:desktop:createDistributable --stacktrace
```

Therefore this M1 desktop source slice is now legitimately **BUILT** and **TESTED** for unit/build/package scope on Ubuntu CI. This does not imply a real network connection or target-device verification.

## Runtime launch gate

The application now has a CI-only launch-smoke mode selected by `PVNETWORK_UI_SMOKE=1`. It enters the actual Compose window composition, prints exactly:

```text
PVNetwork desktop launch smoke: PASS
```

and exits. The workflow launches the real desktop Gradle run task under Xvfb, requires a zero exit code, and greps that marker. Merely compiling or creating a distributable does not satisfy this gate.

The launch-smoke workflow has not yet passed at the time of this source commit, so M1 remains IN_PROGRESS.

## Retained failure evidence

- `31938655622`: early source/build failure.
- `31938706717`: missing Google Maven for AndroidX transitive artifacts.
- `31938789626`: Java/Kotlin JVM target mismatch.
- `31938875712`: invalid direct Compose `weight` import.
- `31938962751`: first successful test + distributable run.

No protocol adapter, connection, interoperability, device verification, Store verification or production readiness is claimed by M1 shell work.
