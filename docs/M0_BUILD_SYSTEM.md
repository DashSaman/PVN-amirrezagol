# M0 Build System

Status: **PASS for current engine-independent foundation JVM/KMP gate**

## Pins

- Kotlin Gradle Plugin / Kotlin Multiplatform: `2.4.10`
- Gradle: `9.5.0`
- JDK in CI: Temurin 21
- Foundation JVM bytecode target: 17

Official Kotlin compatibility evidence checked 2026-08-16 records Kotlin Gradle Plugin `2.4.0–2.4.10` as fully supported with Gradle `7.6.3–9.5.0`. Gradle 9.5.0 is intentionally inside the documented fully supported range.

## Supply-chain pins in CI

- `actions/checkout` v7.0.1 -> `3d3c42e5aac5ba805825da76410c181273ba90b1`
- `actions/setup-java` v5.7.0 -> `b6effb05e454b25005698d916606bdc6ffcbf961`
- `gradle/actions` v6.3.0 -> `9c971963bec38e04b3d30dcc455b5382be2fdbfb`

## Real CI evidence

GitHub Actions run `31938297195`, run number 1, executed against commit `5cdfa60a547a4d08bc00d4c85a86cd0669fd11d8` and completed **SUCCESS** on 2026-08-16. The job successfully set up JDK 21 and Gradle 9.5.0 and ran `gradle --no-daemon :core:foundation:jvmTest --stacktrace`.

This is real shared-foundation build/test evidence. It is not a target application, protocol, interoperability, device, Store or production result.

## Current target scope

M0 configures the shared foundation as a Kotlin Multiplatform module with a JVM test target. Android, Apple, Windows and Linux application packaging remain downstream platform work.
