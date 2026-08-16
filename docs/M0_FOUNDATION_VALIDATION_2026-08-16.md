# M0 Foundation Validation — 2026-08-16

Scope: engine-independent PVNetwork application foundation contracts only.

## Real execution

The source in `core/foundation/src/commonMain/kotlin` plus `tools/foundation-smoke/Main.kt` was compiled and executed in this run before commit.

Command represented by the committed script:

```bash
./scripts/test-foundation.sh
```

Observed result:

```text
PVNetwork foundation smoke: PASS
```

Execution environment observed in this run:

- `kotlinc-jvm 1.9.0`
- `openjdk 21.0.11`

## What the smoke validates

- canonical `PVProfile` accepts secret references rather than reusable plaintext secret fields;
- profile extension keys require a namespace;
- canonical connection-state machine accepts a valid lifecycle and rejects an invalid direct `DISCONNECTED -> CONNECTED` transition;
- diagnostic metadata redacts authorization/private-key/token-style fields;
- capability registry reports only capabilities supplied by concrete adapter descriptors, not research status;
- basic routing and DNS policy contracts enforce their initial invariants.

## Evidence status

For this foundation slice only:

- IMPLEMENTED: **yes** — product-owned contracts are present in source.
- BUILT: **yes, JVM smoke artifact only** — compiled by the local `kotlinc-jvm` command above.
- TESTED: **yes, smoke scope only** — executable assertions passed.

Not claimed:

- Kotlin 2.4.10/Gradle KMP production build;
- Android/iOS/macOS/Windows/Linux application build;
- any protocol adapter or protocol runtime;
- interoperability;
- real-device behavior;
- Store compliance;
- production readiness.

The next M0 work item is the reproducible Kotlin Multiplatform build/CI layer, followed by localization and branding foundation.
