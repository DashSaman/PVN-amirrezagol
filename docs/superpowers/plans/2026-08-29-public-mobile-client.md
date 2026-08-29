# PVNetwork Public Mobile Client Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a professional public PVNetwork Android and iOS VPN client that is truthful, reliable, privacy-safe, Store-compliant, real-device verified and maintainable.

**Architecture:** Preserve the existing PVNetwork-owned Kotlin Multiplatform/domain and engine-adapter boundaries. Extend the shared foundation to mobile, add narrow platform VPN contracts, implement Android `VpnService` and Apple Packet Tunnel Network Extension separately, then prove one complete real data-path vertical slice per platform before expanding consumer UI and protocol breadth.

**Tech Stack:** Kotlin Multiplatform, Kotlin/JVM, Android SDK/API 36+, Android `VpnService`, Compose/selected KMP presentation direction, Apple Network Extension / `NETunnelProviderManager`, Swift/SwiftUI where native host/extension code is required, existing PVNetwork EngineAdapter modules, secure OS storage, GitHub Actions, Google Play/TestFlight release tracks.

**Spec:** `app/PUBLIC_APP_MASTER_REQUIREMENTS.md`

## Global Constraints

- Public launch priority: Android phone/tablet + iPhone/iPad.
- Reliability outranks protocol count.
- UI never calls third-party cores directly.
- No cryptography reimplementation.
- No secret logging.
- No `CONNECTED` state based solely on process/service start.
- Parser/adapter/runtime/device/Store/production evidence states remain separate.
- Android phone/tablet release planning targets API 36+ under the 2026-08-31 Play requirement snapshot.
- Apple VPN publishing uses an organization publisher and valid Network Extension capability.
- iOS VPN support requires real-device evidence; simulator-only evidence is insufficient.
- Every embedded native core requires exact version/hash, license/provenance, SBOM/security and Store-binary compatibility review.
- No Blocker/Critical known issue is allowed at release candidate.
- Re-check current Google/Apple policies before release-affecting implementation.

---

# File structure locked by this plan

Existing shared code remains under:

```text
core/foundation/src/commonMain/kotlin/pvnetwork/core/
```

New shared product domains should be added under focused packages:

```text
core/foundation/src/commonMain/kotlin/pvnetwork/core/account/
core/foundation/src/commonMain/kotlin/pvnetwork/core/entitlement/
core/foundation/src/commonMain/kotlin/pvnetwork/core/subscription/
core/foundation/src/commonMain/kotlin/pvnetwork/core/platform/
core/foundation/src/commonMain/kotlin/pvnetwork/core/routing/
core/foundation/src/commonMain/kotlin/pvnetwork/core/dns/
core/foundation/src/commonMain/kotlin/pvnetwork/core/support/
```

Android application:

```text
apps/android/
```

Apple host/extension:

```text
apps/ios/
```

Do not move existing desktop or engine modules merely to make the new tree look symmetrical.

---

### Task 1: Lock launch decisions and identifiers

**Files:**
- Create: `app/PUBLIC_APP_DECISIONS.md`
- Modify: `app/PUBLIC_APP_MASTER_REQUIREMENTS.md` only if an approved decision changes the spec
- Test/Review: document review against Task 1 acceptance list

**Interfaces:**
- Consumes: Store/release gap analysis and master requirements.
- Produces: immutable initial values/decisions used by Android/iOS build configuration and backend contracts.

- [ ] **Step 1: Record launch platform and minimum-version decisions**

The document must explicitly record Android minimum SDK, Android target SDK strategy, iOS minimum version, launch countries/storefront constraints if already known, and whether Android TV/tvOS are launch or post-launch.

- [ ] **Step 2: Record product identity decisions**

Record the approved application name, Android `applicationId`, Apple bundle ID root, URL/deep-link scheme, and organization/team ownership. Do not invent legal entity information if not yet supplied; mark externally blocked decisions as `BLOCKED_EXTERNAL` with the exact owner action required rather than fabricating a value.

- [ ] **Step 3: Record monetization/account decision**

Choose and document one initial model: Store-native purchase + PVNetwork account, or companion/existing-customer use. If monetization is undecided, implementation may continue through tunnel vertical slices but billing UI must not be built on assumptions.

- [ ] **Step 4: Record first release engine scope**

List exact candidate engine/protocol/transport/security paths to be proven on Android and iOS. Each path must refer to existing research/adapter evidence. Do not select a capability solely because Karing supports it.

- [ ] **Step 5: Commit**

```bash
git add app/PUBLIC_APP_DECISIONS.md app/PUBLIC_APP_MASTER_REQUIREMENTS.md
git commit -m "docs: lock public mobile launch decisions"
```

**Acceptance:** No unresolved product decision may be silently replaced by an engineer assumption. External blockers are explicit.

---

### Task 2: Make the shared foundation genuinely mobile-capable

**Files:**
- Modify: `core/foundation/build.gradle.kts`
- Modify: root Gradle/plugin configuration only as required for Android/KMP targets
- Test: `core/foundation/src/commonTest/...`

**Interfaces:**
- Consumes: existing `pvnetwork.core.*` common domains.
- Produces: shared code compilable for JVM and selected mobile targets without importing Android/iOS UI APIs into common code.

- [ ] **Step 1: Write a common compilation smoke test**

Add a common test that constructs existing profile/connection/security abstractions without JVM-only classes.

```kotlin
@Test
fun commonFoundationCreatesCoreModelsWithoutPlatformTypes() {
    val state: ConnectionState = ConnectionState.Disconnected
    assertTrue(state is ConnectionState.Disconnected)
}
```

Adapt the exact constructor/type spelling to the already committed `ConnectionState.kt`; do not create a duplicate state model to make this test compile.

- [ ] **Step 2: Run current tests before changing targets**

```bash
./gradlew :core:foundation:allTests
```

Expected: existing JVM/common tests pass before target expansion.

- [ ] **Step 3: Extend KMP configuration**

Add Android and Apple targets required by the chosen architecture while keeping common code platform-neutral. Any dependency unavailable on a target must be moved behind an interface rather than replaced with platform `if` statements throughout common code.

- [ ] **Step 4: Compile all configured targets**

Use Gradle task discovery if exact target task names differ after target creation:

```bash
./gradlew :core:foundation:tasks --all
./gradlew :core:foundation:allTests
```

Expected: common/JVM tests remain green and all configured shared source sets compile.

- [ ] **Step 5: Commit**

```bash
git add core/foundation build.gradle.kts settings.gradle.kts
git commit -m "build: extend shared foundation to mobile targets"
```

**Acceptance:** No Android/iOS framework class leaks into `commonMain`.

---

### Task 3: Add canonical account and entitlement models

**Files:**
- Create: `core/foundation/src/commonMain/kotlin/pvnetwork/core/account/Account.kt`
- Create: `core/foundation/src/commonMain/kotlin/pvnetwork/core/entitlement/Entitlement.kt`
- Create: `core/foundation/src/commonMain/kotlin/pvnetwork/core/entitlement/DeviceRegistration.kt`
- Test: matching files under `core/foundation/src/commonTest/kotlin/pvnetwork/core/...`

**Interfaces:**
- Produces conceptual API:

```kotlin
data class AccountSession(
    val accountId: String,
    val accessTokenRef: SecretRef,
    val expiresAtEpochSeconds: Long,
)

data class Entitlement(
    val planId: String,
    val status: EntitlementStatus,
    val expiresAtEpochSeconds: Long?,
    val trafficQuotaBytes: Long?,
    val trafficUsedBytes: Long?,
    val deviceLimit: Int?,
    val features: Set<String>,
)
```

Use the existing `SecretStore.kt`/secret-reference vocabulary rather than persisting a raw bearer token in the model.

- [ ] **Step 1: Write tests for entitlement invariants**

Include cases for unlimited quota, finite quota, expired time and invalid negative quota/device-limit inputs if constructors validate them.

- [ ] **Step 2: Run tests and verify they fail because types do not exist**

```bash
./gradlew :core:foundation:allTests
```

- [ ] **Step 3: Implement the minimal immutable models**

Do not add billing-provider-specific receipt fields to the canonical entitlement model. Store/provider transaction details belong behind backend/billing boundaries.

- [ ] **Step 4: Run tests**

```bash
./gradlew :core:foundation:allTests
```

Expected: PASS.

- [ ] **Step 5: Commit**

```bash
git add core/foundation/src/commonMain core/foundation/src/commonTest
git commit -m "feat: add canonical account entitlement models"
```

---

### Task 4: Formalize `PlatformVpnService` and connection coordination

**Files:**
- Create: `core/foundation/src/commonMain/kotlin/pvnetwork/core/platform/PlatformVpnService.kt`
- Modify: `core/foundation/src/commonMain/kotlin/pvnetwork/core/connection/ConnectionState.kt`
- Create: `core/foundation/src/commonMain/kotlin/pvnetwork/core/connection/ConnectionCoordinator.kt`
- Test: `core/foundation/src/commonTest/kotlin/pvnetwork/core/connection/ConnectionCoordinatorTest.kt`

**Interfaces:**

```kotlin
interface PlatformVpnService {
    suspend fun prepare(): PlatformVpnPreparation
    suspend fun start(request: PlatformVpnStartRequest): PlatformVpnStartResult
    suspend fun stop(reason: DisconnectReason): PlatformVpnStopResult
    fun currentState(): PlatformVpnState
}
```

The exact async/state mechanism may use the project's selected coroutine/flow design, but there must be one narrow contract.

- [ ] **Step 1: Write tests proving `CONNECTED` cannot occur from engine-start alone**

Use fakes for platform and engine. Test that platform-prepared + engine-running but tunnel-not-established remains a transitional state.

- [ ] **Step 2: Write idempotency tests**

Repeated `disconnect` from disconnected must be safe. Repeated `connect` while connecting must not spawn duplicate services/cores.

- [ ] **Step 3: Run tests; expect FAIL**

```bash
./gradlew :core:foundation:allTests
```

- [ ] **Step 4: Implement the contract/coordinator**

Map platform/core failures into structured `ConnectionFailure` categories rather than only free-text exceptions.

- [ ] **Step 5: Run tests; expect PASS**

- [ ] **Step 6: Commit**

```bash
git add core/foundation/src/commonMain core/foundation/src/commonTest
git commit -m "feat: formalize platform vpn lifecycle contract"
```

---

### Task 5: Formalize secure storage and diagnostic redaction

**Files:**
- Modify or extend: `core/foundation/src/commonMain/kotlin/pvnetwork/core/security/SecretStore.kt`
- Modify: `core/foundation/src/commonMain/kotlin/pvnetwork/core/diagnostics/Diagnostics.kt`
- Create: `core/foundation/src/commonMain/kotlin/pvnetwork/core/support/DiagnosticRedactor.kt`
- Test: redaction/secret-store contract tests under `commonTest`

**Interfaces:**
- Secret values are referenced by opaque IDs, never embedded in normal diagnostics.
- `DiagnosticRedactor` must remove or replace URL credentials, bearer tokens, passwords, UUID/private-key-like configured secret fields and Store receipt/token fields.

- [ ] **Step 1: Add failing redaction tests using synthetic secrets**

```kotlin
@Test
fun exportNeverContainsKnownSecret() {
    val secret = "synthetic-secret-never-log"
    val output = redactor.redact("token=$secret")
    assertFalse(output.contains(secret))
}
```

Include subscription URL credential cases.

- [ ] **Step 2: Run tests and confirm FAIL**
- [ ] **Step 3: Implement source-level redaction**
- [ ] **Step 4: Run tests and confirm PASS**
- [ ] **Step 5: Commit**

**Acceptance:** Diagnostic export cannot reproduce fixture secrets byte-for-byte.

---

### Task 6: Create the Android application shell

**Files:**
- Create: `apps/android/build.gradle.kts`
- Create: `apps/android/src/main/AndroidManifest.xml`
- Create: Android app/activity/application source under `apps/android/src/main/kotlin/...`
- Modify: `settings.gradle.kts`
- Test: Android unit/instrumentation smoke tests under `apps/android/src/test` and `src/androidTest`

**Interfaces:**
- Consumes shared product models and `PlatformVpnService` contract.
- Does not call engine binaries directly from UI Activity/Composable.

- [ ] **Step 1: Add module and a build smoke test**

```bash
./gradlew :apps:android:assembleDebug
```

Initial expected result: module/task unavailable until scaffolding is added.

- [ ] **Step 2: Create Android module targeting current public-release requirements**

Use the locked package ID from `PUBLIC_APP_DECISIONS.md`; do not copy Karing's package IDs/branding.

- [ ] **Step 3: Add minimal launch screen that renders shared connection state**

No tunnel implementation yet. The UI must use a fake/platform abstraction in tests.

- [ ] **Step 4: Add instrumentation startup test**

Verify Activity launches and renders disconnected state without requesting VPN permission immediately.

- [ ] **Step 5: Build/test**

```bash
./gradlew :apps:android:assembleDebug :apps:android:testDebugUnitTest
```

Run instrumentation on emulator/device when CI/dev environment is available.

- [ ] **Step 6: Commit**

---

### Task 7: Implement Android `VpnService` lifecycle before engine integration

**Files:**
- Create focused service/adapter files under `apps/android/src/main/kotlin/.../vpn/`
- Modify: Android manifest for service declaration/permissions
- Test: unit + instrumentation tests for permission/service lifecycle

**Interfaces:**
- Produces Android implementation of `PlatformVpnService`.
- Engine runtime is injected; service layer must be testable with a fake runtime.

- [ ] **Step 1: Write permission-denied and permission-granted tests**
- [ ] **Step 2: Write duplicate-start/duplicate-stop tests**
- [ ] **Step 3: Write process/service state restoration tests where test framework permits**
- [ ] **Step 4: Implement foreground service and notification channel**
- [ ] **Step 5: Implement Android network-change observer**
- [ ] **Step 6: Run unit/instrumentation tests**
- [ ] **Step 7: Commit**

**Acceptance:** With a fake engine, Android proves correct VPN permission/service state transitions without leaking engine-specific logic into UI.

---

### Task 8: Prove the first Android real-engine vertical slice

**Files:**
- Modify the selected existing `engines/*-adapter` only as required by a documented mobile runtime boundary
- Add Android runtime bridge in a focused module/package rather than embedding core logic in Activity
- Test: real Android device/instrumentation or controlled device harness
- Document: `docs/MOBILE_ANDROID_<ENGINE>_VALIDATION.md`

**Interfaces:**
- Exact protocol/transport/security scope comes from `PUBLIC_APP_DECISIONS.md`.

- [ ] **Step 1: Pin exact core source/release/hash/license**
- [ ] **Step 2: Verify Android ABI and 16 KB page-size compatibility for bundled native artifacts**
- [ ] **Step 3: Add a failing real-data-path harness**
- [ ] **Step 4: Integrate core behind existing EngineAdapter contract**
- [ ] **Step 5: Prove on real Android device:** connect -> tunneled marker -> background -> network transition -> disconnect -> cleanup
- [ ] **Step 6: Capture exact device/OS/core/protocol evidence**
- [ ] **Step 7: Re-run secret/log scan**
- [ ] **Step 8: Commit**

**Acceptance:** This task may claim only the exact validated Android capability combination, not the entire upstream engine feature set.

---

### Task 9: Build Android lifecycle stress acceptance

**Files:**
- Create: `docs/MOBILE_ANDROID_ACCEPTANCE_MATRIX.md`
- Add automated/device tests where practical

- [ ] **Step 1: Define exact physical device matrix including Pixel-class, Samsung and Xiaomi/HyperOS/MIUI-class behavior**
- [ ] **Step 2: Execute repeated connect/disconnect stress loop**
- [ ] **Step 3: Execute Wi-Fi/cellular, airplane, screen-off, process-death, service-restart and reboot cases from the master spec**
- [ ] **Step 4: Record pass/fail per exact build/device/OS**
- [ ] **Step 5: Fix Blocker/Critical/Major lifecycle issues before promoting milestone**
- [ ] **Step 6: Commit evidence**

---

### Task 10: Create iOS host + Packet Tunnel shell

**Files:**
- Create: `apps/ios/` Xcode project/workspace structure
- Create host app source
- Create Packet Tunnel extension source
- Create entitlements/App Group configuration files
- Add shared KMP framework integration according to selected KMP architecture
- Test: host logic/unit tests and real-device smoke run

**Interfaces:**
- Consumes shared models and `PlatformVpnService` conceptual contract.
- Apple-specific adapter exposes equivalent behavior; Swift/ObjC boundary must not leak into shared product models.

- [ ] **Step 1: Create host app and extension targets with separate bundle IDs derived from the approved root**
- [ ] **Step 2: Add Network Extension capability and App Group**
- [ ] **Step 3: Implement install/permission/start/stop state shell without real engine**
- [ ] **Step 4: Store synthetic secret using Keychain and verify it is not in shared plaintext config**
- [ ] **Step 5: Run `xcodebuild` for simulator host compile and real-device provisioning/build**

Example discovery/build sequence; use committed scheme names once created:

```bash
xcodebuild -list -project apps/ios/PVNetwork.xcodeproj
xcodebuild -project apps/ios/PVNetwork.xcodeproj -scheme PVNetwork -configuration Debug build
```

- [ ] **Step 6: Commit**

**Acceptance:** Host + extension install/start/stop shell works on a real Apple device; no engine support claim yet.

---

### Task 11: Prove first iOS real-engine vertical slice

**Files:**
- Add selected Apple-compatible engine runtime/bridge behind extension boundary
- Document: `docs/MOBILE_IOS_<ENGINE>_VALIDATION.md`
- Add extension lifecycle tests/harnesses where practical

- [ ] **Step 1: Pin and audit exact Apple core build/version/license**
- [ ] **Step 2: Verify extension-safe dependencies and architecture support**
- [ ] **Step 3: Implement core translation/runtime behind existing EngineAdapter/product contracts**
- [ ] **Step 4: Prove real iPhone/iPad data path**
- [ ] **Step 5: Test lock/unlock, background, host-process kill, Wi-Fi/cellular transition, extension restart, IPv6/NAT64 as available**
- [ ] **Step 6: Capture memory/CPU behavior sufficient to identify extension-limit failures**
- [ ] **Step 7: Commit exact-scope validation evidence**

**Acceptance:** Real-device Packet Tunnel data path and lifecycle pass for the exact selected protocol combination.

---

### Task 12: Implement versioned subscription/config update with last-known-good rollback

**Files:**
- Create: `core/foundation/src/commonMain/kotlin/pvnetwork/core/subscription/SubscriptionRepository.kt`
- Create: `.../SubscriptionUpdate.kt`
- Tests under `commonTest`
- Add platform/backend client integration in separate packages/modules

**Interfaces:**

```kotlin
sealed interface SubscriptionUpdateResult {
    data class Applied(val version: String) : SubscriptionUpdateResult
    data class Rejected(val reason: String) : SubscriptionUpdateResult
    data object NotModified : SubscriptionUpdateResult
}
```

- [ ] **Step 1: Test invalid new config preserves prior valid config**
- [ ] **Step 2: Test interrupted download cannot replace active config**
- [ ] **Step 3: Test version/ETag not-modified behavior**
- [ ] **Step 4: Implement atomic validation/apply/rollback**
- [ ] **Step 5: Run shared tests**
- [ ] **Step 6: Commit**

---

### Task 13: Implement consumer Home/server/account experience

**Files:**
- Add mobile presentation files following chosen Compose/Swift boundary
- Reuse shared use cases/state; platform UI adapters may differ
- Add UI tests/snapshots where useful

**Interfaces:**
- Home consumes canonical `ConnectionState`, `Entitlement`, selected `ServerDescriptor` and `TrafficStats`.

- [ ] **Step 1: Build onboarding with privacy disclosure and deferred VPN permission**
- [ ] **Step 2: Build Home with one truthful connect/disconnect control**
- [ ] **Step 3: Display quota/expiry from entitlement, never local invented values**
- [ ] **Step 4: Build Auto/manual server selection, favorites/recent and unavailable state**
- [ ] **Step 5: Add subscription refresh status and recovery actions**
- [ ] **Step 6: Verify Persian/English RTL/LTR and text scaling**
- [ ] **Step 7: UI test critical state transitions**
- [ ] **Step 8: Commit**

---

### Task 14: Implement product-level routing and DNS contracts

**Files:**
- Create/extend: `core/foundation/src/commonMain/kotlin/pvnetwork/core/routing/*`
- Create/extend: `core/foundation/src/commonMain/kotlin/pvnetwork/core/dns/*`
- Add engine translation tests in each selected adapter

- [ ] **Step 1: Add canonical rule/action tests**
- [ ] **Step 2: Add canonical DNS policy tests**
- [ ] **Step 3: Implement translation result that distinguishes exact/unsupported/lossy**
- [ ] **Step 4: Prove unsupported rule is never silently discarded**
- [ ] **Step 5: Add consumer presets Global / Smart / Direct-equivalent**
- [ ] **Step 6: Commit**

---

### Task 15: Implement backend identity/device/entitlement contract

**Files:**
- Create API contract documentation under `docs/api/`
- Add shared client/repository interfaces in `core/foundation`
- Backend implementation belongs in the appropriate server repository/module if/when established; do not bury server code in the mobile UI module.

- [ ] **Step 1: Define versioned endpoints/schemas for session, entitlement, devices, config and server catalog**
- [ ] **Step 2: Add contract tests with deterministic fixtures**
- [ ] **Step 3: Ensure client cannot mutate authoritative quota/expiry**
- [ ] **Step 4: Implement revoke/logout/device-limit behavior**
- [ ] **Step 5: Test clock-skew/offline/error behavior**
- [ ] **Step 6: Commit contract and client integration**

---

### Task 16: Implement selected Store billing path

**Files:**
- Create billing abstraction and provider-specific adapters outside canonical entitlement model
- Add backend transaction verification/event handling
- Document sandbox evidence

- [ ] **Step 1: Write idempotency tests for duplicate Store events**
- [ ] **Step 2: Write out-of-order renewal/cancel/refund tests**
- [ ] **Step 3: Implement server-side verification**
- [ ] **Step 4: Implement restore purchases**
- [ ] **Step 5: Test sandbox purchase, renewal/cancel/refund/grace scenarios selected by the product**
- [ ] **Step 6: Verify receipts/tokens absent from logs**
- [ ] **Step 7: Commit evidence**

If Task 1 selects a no-in-app-purchase companion model, replace this task with a Store-policy review of the exact companion flow; do not implement hidden external-purchase steering.

---

### Task 17: Privacy, account deletion and Store disclosure source of truth

**Files:**
- Create: `app/DATA_INVENTORY.md`
- Create: `app/STORE_DISCLOSURE_CHECKLIST.md`
- Add Apple `PrivacyInfo.xcprivacy` in the actual Apple target when applicable
- Add deletion API/UI tests

- [ ] **Step 1: Inventory every app/backend/SDK data field**
- [ ] **Step 2: Map inventory to Google Data Safety and Apple App Privacy answers**
- [ ] **Step 3: Implement in-app deletion initiation and required external web deletion path when accounts exist**
- [ ] **Step 4: Test session/device revocation on deletion**
- [ ] **Step 5: Verify privacy policy/disclosure matches real telemetry and SDK behavior**
- [ ] **Step 6: Commit**

**Acceptance:** Any declaration/behavior mismatch is a release blocker.

---

### Task 18: Support and sanitized diagnostics

**Files:**
- Add diagnostic timeline/support UI
- Add export/upload implementation
- Create: `docs/SUPPORT_RUNBOOK.md`

- [ ] **Step 1: Build synthetic diagnostic export containing known fixture secrets**
- [ ] **Step 2: Assert exported bundle contains none of them**
- [ ] **Step 3: Include app/OS/core/state/network/config-refresh summaries**
- [ ] **Step 4: Add user-controlled export/upload action**
- [ ] **Step 5: Document support triage without requesting raw credentials**
- [ ] **Step 6: Commit**

---

### Task 19: Mobile CI, supply-chain and signed RC pipeline

**Files:**
- Modify/create workflows under `.github/workflows/`
- Create: `docs/RELEASE_PROVENANCE.md`
- Create/update SBOM/license tooling/config

- [ ] **Step 1: Add shared + Android build/test workflow**
- [ ] **Step 2: Add Apple build/test workflow on macOS runner**
- [ ] **Step 3: Add dependency/license/SBOM/security gates**
- [ ] **Step 4: Add secret scanning**
- [ ] **Step 5: Add protected signed release artifact workflow using repository/Store secret facilities, never committed keys**
- [ ] **Step 6: Retain symbols/build metadata**
- [ ] **Step 7: Commit**

---

### Task 20: Release-candidate acceptance and Store dry run

**Files:**
- Create: `app/RC_ACCEPTANCE_CHECKLIST.md`
- Create: `docs/MOBILE_DEVICE_ACCEPTANCE_MATRIX.md`
- Create: `docs/STORE_REVIEW_RUNBOOK.md`
- Update: `docs/PROJECT_STATE.md` only with evidence-backed states

- [ ] **Step 1: Execute exact Android/iOS device/network matrix**
- [ ] **Step 2: Record crash/ANR/stability/performance baselines**
- [ ] **Step 3: Confirm known Blocker = 0 and Critical = 0**
- [ ] **Step 4: Review all Major issues and record explicit release decisions**
- [ ] **Step 5: Verify privacy/data/billing/account-deletion declarations**
- [ ] **Step 6: Produce signed Android/iOS RC artifacts**
- [ ] **Step 7: Run Play internal/closed test + TestFlight acceptance**
- [ ] **Step 8: Prepare screenshots, listing, support/privacy URLs, reviewer credentials/instructions and VPN declaration artifacts**
- [ ] **Step 9: Re-check current official Store policies immediately before submission**
- [ ] **Step 10: Commit final RC evidence**

**Acceptance:** RC is not Store-verified until actual Store review approves the exact submitted build.

---

# Self-review checklist for agents executing this plan

Before claiming a task complete, confirm:

- spec requirement mapped to a task;
- no placeholder/TBD hidden in implemented behavior;
- exact types/contracts are not duplicated under different names;
- tests failed before implementation where TDD applies;
- tests now pass;
- real-device evidence exists where the task requires it;
- exact capability scope is stated;
- secrets are redacted;
- third-party version/license/provenance recorded;
- checkpoint/handoff/state updated;
- no claim exceeds evidence.

# Execution order

Tasks 1–5 establish decisions/shared contracts. Then execute Android 6–9 to achieve the first real mobile vertical slice. Execute iOS 10–11 next. Tasks 12–18 turn the network client into a consumer product/service. Tasks 19–20 build the release system and RC gate.

Do not parallelize tasks that modify the same canonical contracts before those contracts are stable. Independent backend documentation, Store organization administration, device-lab preparation and design asset work can proceed in parallel.
