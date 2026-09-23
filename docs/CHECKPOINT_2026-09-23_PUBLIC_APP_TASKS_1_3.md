# Checkpoint — 2026-09-23 — Public-app Tasks 1–3 + state recovery

## Completed

- Phase A / STEP 4 state reconciliation: `app/CURRENT_STATE_2026-09-23.md`.
- Task 1: `app/PUBLIC_APP_DECISIONS.md` (engineering decisions locked;
  owner-side items explicitly `BLOCKED_EXTERNAL`, incl. publisher orgs,
  billing model, privacy-policy URL, logo asset, PVNaive API sessions).
- Task 2: `core/foundation` extended to Android + iOS targets
  (`com.android.kotlin.multiplatform.library` 9.3.3; `iosArm64`,
  `iosSimulatorArm64`; `withHostTest`), common smoke tests added,
  `m0-foundation-ci.yml` gained `foundation-mobile-targets` job.
- Task 3: canonical `Account`, `AccountSession`, `Entitlement`,
  `EntitlementStatus`, `DeviceRegistration` models + contract tests.
- CI hygiene: fixed pre-existing `setup-java` SHA typo in
  `m0-foundation-ci.yml` (commit `e74a87f`); added
  `kotlin.native.ignoreDisabledTargets=true`.
- Broken-work register created: `docs/BROKEN_CI_REGISTER.md`.

## Verified (local, Windows 11, JDK 21.0.12 / Gradle 9.5.0)

- `:core:foundation:jvmTest` — 26 tests, 0 failures (suites:
  CommonFoundationSmokeTest 3, FoundationContractTest 5,
  LocalizationBrandingTest 3, AccountContractTest 4,
  EntitlementContractTest 8, DeviceRegistrationContractTest 3).
- `:core:foundation:testAndroidHostTest` — same suites green against the
  Android target.
- `:core:foundation:compileCommonMainKotlinMetadata`,
  `:core:foundation:compileAndroidMain`, `:core:foundation:bundleAndroidMainAar`,
  `:core:foundation:compileKotlinIosArm64`,
  `:core:foundation:compileKotlinIosSimulatorArm64` — BUILD SUCCESSFUL.
- Environment note: `dl.google.com` unreachable from this workstation
  (geo-block); local builds used the Aliyun google mirror via a
  user-local Gradle init script and a manually assembled Android SDK from
  the Tencent mirror. Neither workaround is committed to the repository.

## Verified (CI, GitHub Actions)

- M1 desktop, M2 OpenVPN, M2 WireGuard adapter workflows: SUCCESS on
  2026-09-23 commits (foundation target changes did not break consumers).
- M0 foundation workflow: green expected after SHA-typo fix (commit
  `e74a87f`); confirm on run for that commit.

## Failed / open (pre-existing, not caused by 2026-09-23 commits)

- Xray REALITY Vision interop test red since 2026-08-26
  (`RealityJvmHostXrayRealBinaryInteropTest.realVlessVisionRealityDataPath`).
- Mihomo TUIC v5 interop test red since 2026-08-26
  (`JvmHostMihomoRealBinaryInteropTest.realTuicV5TlsDataPath`).
- Details + evidence in `docs/BROKEN_CI_REGISTER.md`.

## Changed

- `app/PUBLIC_APP_DECISIONS.md` (new), `app/CURRENT_STATE_2026-09-23.md`
  (new), `docs/M-P1_FOUNDATION_MOBILE_TARGETS_VALIDATION.md` (new),
  `docs/BROKEN_CI_REGISTER.md` (new), this checkpoint (new).
- `build.gradle.kts`, `core/foundation/build.gradle.kts`,
  `core/foundation/src/commonTest/.../CommonFoundationSmokeTest.kt` (new),
  account/entitlement commonMain models + tests (new),
  `.github/workflows/m0-foundation-ci.yml`, `gradle.properties`,
  `AGENTS_LATEST.md`, `docs/PROJECT_STATE.md`, `docs/FOREGROUND_ACTIVITY.json`.

## Decisions

- Continue committed KMP architecture; AGP 9.3.3 chosen because its
  required Gradle (9.5.0) matches the repo pin, and the KMP-native library
  plugin is mandatory since AGP 9.0.
- NaiveProxy adapter scheduled into the engine backlog as first-class P1
  work (recorded in PUBLIC_APP_DECISIONS §8).

## Risks

- iOS targets verified by cross-compilation only (no macOS host yet).
- Red REALITY/TUIC interop tests limit near-term protocol claims.
- Owner-side blockers (publisher orgs, billing, privacy URL, logo) gate
  Store submission but not Tasks 4–11.

## Remaining / Next

- **Task 4**: `PlatformVpnService` + `ConnectionCoordinator` in
  `core/foundation` (TDD steps in the plan).
- Then Task 5 (redaction/secret-store contract), Task 6 (Android shell).
- Separate track when scheduled: fix Xray REALITY + Mihomo TUIC interop
  tests per `docs/BROKEN_CI_REGISTER.md`.

## Current build

`core/foundation` builds for JVM, Android (AAR) and iOS (klib) locally and
in CI; all other existing modules unchanged and green in their gates.

## Current connectivity

Real data-path receipts unchanged from `docs/PROJECT_STATE.md` (WireGuard
kernel CI, OpenVPN system runtime CI, Xray VLESS RAW CI). No mobile/device
connectivity yet.
