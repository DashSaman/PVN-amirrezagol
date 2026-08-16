# M1 Desktop Client Shell

Status: **PASS — first desktop client shell is implemented, built, tested and launch-smoke validated**

## Implemented source scope

A real Compose Multiplatform desktop client shell exists under `apps/desktop` using Kotlin `2.4.10`, Compose Multiplatform `1.11.1`, product-owned `core:foundation` contracts, and no protocol engine dependency.

The shell includes PVNetwork branding, English/Persian toggle, LTR/RTL layouts, technical-token LTR isolation, system/light/dark theme preference, profile list, canonical connection state, and sanitized diagnostics. Initial state is intentionally empty and `DISCONNECTED`; no mock profile or fake connection state is used.

## Real build/test evidence

GitHub Actions run `31938962751` on commit `00e8a6cc829084dbaf0f535a4b53ace885e342c8` completed **SUCCESS** for unit tests and `createDistributable`.

GitHub Actions run `31939070255` on commit `f1441c7b18c299ab83cdcfd4eee0062c815ff92b` completed **SUCCESS** and executed both:

```bash
gradle --no-daemon :apps:desktop:test :apps:desktop:createDistributable --stacktrace
PVNETWORK_UI_SMOKE=1 xvfb-run -a gradle --no-daemon :apps:desktop:run --stacktrace
```

The runtime log contains the required marker:

```text
PVNetwork desktop launch smoke: PASS
```

The run also logged a Skiko warning/fallback because the hosted Xvfb environment could not create a Linux GL context. Compose fell back, the application entered composition, emitted the marker, exited normally, and the Gradle run completed successfully. This is CI virtual-display runtime evidence, **not** real-device/device-GPU verification.

## Retained failure evidence

The development failures remain useful evidence rather than being erased: `31938655622`, `31938706717`, `31938789626`, and `31938875712` each exposed and led to a concrete build fix before the two successful runs above.

## Status boundary

M1 evidence supports IMPLEMENTED / BUILT / TESTED / CI launch-smoke for the desktop shell. It does not support protocol implementation, interoperability, device verification, Store verification or production readiness.
