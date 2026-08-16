# M0 Build System

Status: CI execution pending for this commit.

## Pins

- Kotlin Gradle Plugin / Kotlin Multiplatform: `2.4.10`
- Gradle: `9.5.0`
- JDK in CI: Temurin 21
- Foundation JVM bytecode target: 17

Official Kotlin compatibility evidence checked 2026-08-16 records Kotlin Gradle Plugin `2.4.0–2.4.10` as fully supported with Gradle `7.6.3–9.5.0`. Gradle 9.5.0 is therefore intentionally inside the documented fully supported range rather than a best-effort newer version.

## Supply-chain pins in CI

GitHub Actions are pinned to immutable commit SHAs rather than floating major tags:

- `actions/checkout` v7.0.1 -> `3d3c42e5aac5ba805825da76410c181273ba90b1`
- `actions/setup-java` v5.7.0 -> `b6effb05e454b25005698d916606bdc6ffcbf961`
- `gradle/actions` v6.3.0 -> `9c971963bec38e04b3d30dcc455b5382be2fdbfb`

The Gradle Actions v6.3.0 release tag was verified upstream and resolves to the pinned commit above.

## Current target scope

M0 configures the shared foundation as a Kotlin Multiplatform module with a JVM test target. It intentionally does not add Android, Apple, Windows or Linux application packaging yet. Those platform targets require their own SDK/toolchain/signing/networking work and must not be inferred from a shared-module test.

## Test command

CI executes:

```bash
gradle --no-daemon :core:foundation:jvmTest --stacktrace
```

The legacy `scripts/test-foundation.sh` smoke remains useful for environments that already have `kotlinc`, but the Gradle/KMP test suite is the reproducible M0 build gate going forward.

## Status boundary

Until a GitHub Actions run for this configuration completes successfully, do not mark the KMP/Gradle build gate PASS. A committed workflow is configuration evidence, not a test result.
