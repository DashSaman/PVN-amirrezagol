# M-P1 Foundation Mobile Target Expansion — Validation Record

Date: 2026-09-23
Task: **Task 2** of `docs/superpowers/plans/2026-08-29-public-mobile-client.md`
(make the shared foundation genuinely mobile-capable).

## What changed

1. `core/foundation/src/commonTest/kotlin/com/pvnetwork/core/CommonFoundationSmokeTest.kt`
   (new) — common-code smoke tests constructing `ConnectionState`,
   `ConnectionSnapshot`, the connection state machine lifecycle and a
   canonical `PVProfile` **without any JVM-only types**, proving commonMain
   stays platform-neutral.
2. `build.gradle.kts` (root) — registers
   `com.android.kotlin.multiplatform.library` version **9.3.3**.
   AGP 9.3 is the release line whose required Gradle version is exactly
   **9.5.0**, matching the repository's pinned Gradle. Since AGP 9.0 the old
   `com.android.library` plugin is rejected together with the KMP plugin;
   the KMP-native library plugin is the supported path.
3. `core/foundation/build.gradle.kts` — adds targets:
   - `android` via `kotlin { android { namespace = "com.pvnetwork.core.foundation";
     compileSdk = 36; minSdk = 26; withHostTest {} } }`
     (values from `app/PUBLIC_APP_DECISIONS.md`); `withHostTest` also runs the
     common test suite against the Android target (`testAndroidHostTest`);
   - `iosArm64`, `iosSimulatorArm64`.
4. `.github/workflows/m0-foundation-ci.yml` — new
   `foundation-mobile-targets` job: metadata + Android compile + AAR +
   `compileKotlinIosArm64` / `compileKotlinIosSimulatorArm64` on
   ubuntu-24.04 (cross-compilation), plus a platform-36 install guard.

## Local verification (Windows 11 workstation, JDK 21.0.12 / Gradle 9.5.0)

Network constraint recorded: `dl.google.com` is unreachable from this
workstation (geo-blocked; Google returns 404 for SDK/maven paths). Local
verification used a user-local Gradle init script adding the Aliyun google
mirror (`~/.gradle/init.d/local-mirrors.settings.gradle.kts`, NOT part of
the repository) and a manually assembled local Android SDK (platform-36
r02 + build-tools 36.0.0 + license hashes from the Tencent SDK mirror).
CI runners resolve `google()` directly and are unaffected.

Commands and results:

| Command | Result |
|---|---|
| `gradle :core:foundation:jvmTest` (baseline, before target changes, with new smoke test) | BUILD SUCCESSFUL |
| `gradle :core:foundation:compileCommonMainKotlinMetadata :core:foundation:jvmTest :core:foundation:compileAndroidMain :core:foundation:bundleAndroidMainAar :core:foundation:testAndroidHostTest :core:foundation:compileKotlinIosArm64 :core:foundation:compileKotlinIosSimulatorArm64` (final full run) | BUILD SUCCESSFUL (31 tasks) |
| `gradle :core:foundation:compileKotlinIosArm64 :core:foundation:compileKotlinIosSimulatorArm64` (first K/N run, toolchain download) | BUILD SUCCESSFUL |

Test-result XML evidence: JVM suites `CommonFoundationSmokeTest` (3),
`FoundationContractTest` (5), `LocalizationBrandingTest` (3) — 11 tests,
0 failures, 0 errors; identical 11-test suite passed under
`testAndroidHostTest` against the Android target.

## Scope and limits (no overclaiming)

- This proves **commonMain/commonTest compile for JVM, Android and
  Kotlin/Native(iOS)** and the JVM suite (including the new smoke tests) is
  green. It does **not** claim: an Android application, a VpnService, an
  iOS Packet Tunnel, device execution, simulator test runs, or any engine
  behavior on mobile.
- Android host-test compilation (`withHostTest`) compiles common tests
  against the Android target; running them on JVM-host semantics is a CI
  follow-up, not yet a device receipt.
- Evidence ladder position: **builds / unit-tested (JVM)** for the shared
  foundation; mobile runtime steps remain unclaimed.

## Follow-ups queued

- Verify `foundation-mobile-targets` CI job green on GitHub.
- Task 3 (canonical account/entitlement models) next per plan order.
- NaiveProxy adapter backlog entry (first-class requirement) after shared
  product domains.
