# PUBLIC APP DECISIONS — Launch Decisions and Identifiers

Status: **Task 1 of `docs/superpowers/plans/2026-08-29-public-mobile-client.md` — executed 2026-09-23.**

Decision vocabulary:

- **DECIDED** — locked from committed repository evidence / existing engineering
  constraints; changing it later has bounded cost.
- **PROPOSED** — engineering default recorded so implementation can proceed;
  requires owner confirmation before any Store-visible or hard-to-reverse use.
- **BLOCKED_EXTERNAL** — cannot be resolved by repository evidence; the exact
  owner/legal action is recorded. No value is fabricated.

Per the plan: no unresolved product decision may be silently replaced by an
engineer assumption; external blockers stay explicit.

---

## 1. Launch platform list vs post-launch

**DECIDED**

- Launch (public V1): Android phone/tablet first, then iPhone/iPad.
  Rationale: the master continuation prompt fixes "Android real vertical
  slice first"; the public-app plan (Tasks 6–9 before 10–11) encodes the
  same order.
- Post-launch: Android TV/Google TV, tvOS where feasible, desktop packaging
  (Windows/macOS/Linux) — desktop shell already exists in-repo for
  engineering validation only.

## 2. Android minimum SDK

**DECIDED (engineering): `minSdk = 26` (Android 8.0)**

Rationale:

- VPN-critical APIs used by the committed architecture
  (`VpnService`, foreground-service with notification channel, per-app
  tunneling via `addAllowedApplication`) are fully available from API 26+
  (notification channels exist from API 26; per-app split tunneling works
  below 26 but battery/background restrictions differ).
- All studied reference clients target this floor or lower; API 26 covers
  the overwhelming majority of active devices in 2026.
- Raise-only rule: raising minSdk later is cheap; lowering is not. If device
  QA on the owner's actual customer base demands lower, revisit before
  first upload.

## 3. Android target SDK strategy

**DECIDED (constraint, not preference): target latest release (36+ as of the
2026-08-31 Play requirement snapshot), compile against current SDK.**

Rationale: Play phone/tablet submissions must meet the API-level requirement
effective 2026-08-31 (per `app/PRODUCTION_READINESS_GAP_ANALYSIS.md`).
Re-verify the exact current requirement immediately before Store submission
(Store rules change).

Also locked: 64-bit only native cores (`arm64-v8a` first; `x86_64` for
emulator/QA), 16 KB page-size compatibility for every bundled native
artifact.

## 4. iOS minimum version

**PROPOSED: iOS 16 floor for host + Packet Tunnel extension.**

Rationale: Network Extension APIs needed are stable well below 16, but iOS 16
maximizes Xcode/current-SDK compatibility headroom per the gap analysis.
Requires owner/Xcode-toolchain confirmation when `apps/ios` work starts
(Task 10). Not blocking Android tasks.

## 5. Android application ID

**DECIDED (engineering namespace): `com.pvnetwork.client`**

Rationale: existing committed code already uses the `com.pvnetwork.*`
namespace (`com.pvnetwork.desktop`, `com.pvnetwork.core.*`). `client` avoids
collision with the desktop shell and stays engine-agnostic.

Constraint recorded: the application ID becomes immutable once published;
owner must ratify before first Play upload (see §10 owner actions).

## 6. Apple bundle ID root

**PROPOSED: `com.pvnetwork.` root — host `com.pvnetwork.client` (mirror of
Android), tunnel extension `com.pvnetwork.client.tunnel`.**

Requires Apple developer account/team identity (§10) before creation.
Derivation follows the plan's Task 10 Step 1.

## 7. URL / deep-link scheme

**DECIDED (engineering): custom scheme `pvnetwork://` reserved for
import/share deep links; HTTPS App Links / Universal Links deferred until
backend domains are fixed (BLOCKED_EXTERNAL on domain ownership).**

Import pipeline must treat every deep-link payload as untrusted input
(master prompt §26/§51).

## 8. First launch engine / protocol / transport / security set

**DECIDED (scope, evidence-anchored):**

1. **Xray-core (VLESS + REALITY)** — primary modern path for PVNetwork
   customers. Adapter/importer/model code exists and a real JVM VLESS/RAW
   data path is CI-proven (`docs/M2_XRAY_HOST_RUNTIME_VALIDATION.md`).
   Mobile bundling still requires: exact pinned release passing the
   advisory-range gate (`docs/M2_XRAY_STABLE_RELEASE_GATE.md`), Android
   ABI/16 KB-page audit, and a real-device receipt (Task 8). TLS/REALITY
   transport receipts must be produced before claiming them.
2. **WireGuard** — secondary path; importer/adapter/model code exists and the
   Linux kernel data path is CI-proven. Mobile embedding strategy (official
   `wireguard-android` library vs own tunnel) is a Task 8 engineering
   decision with its own provenance audit.
3. **NaiveProxy — first-class P1 requirement, scheduled into the engine
   backlog now** (master prompt §25/§60): no adapter code exists yet. It is
   the PVNetwork backend's (PVNaive) primary customer path and therefore
   cannot stay absent from the mobile plan. A `naive-adapter` module
   (import + config generation + runtime boundary) is queued after the
   shared-foundation tasks; its own robust multi-format importer
   (`naive+https://`, `sn://naive?...`, native subscription) is required
   per the committed QR/Naive compatibility lesson.

Not in first-launch scope: VMess/Trojan/Shadowsocks/Hysteria2/TUIC/etc.
(research-complete, no adapter code) — added only through the §12 engine
release gate with real-device evidence.

## 9. Account / login / monetization / telemetry / backend

- **Account/login model: PROPOSED — PVNetwork account (PVNaive backend) +
  subscription import as the universal fallback.** Client stays decoupled:
  non-PVNetwork profiles remain importable (master prompt §39). Exact auth
  flow/API contract is Task 15; client code must not hard-code internal
  server addresses.
- **Billing/purchase model: BLOCKED_EXTERNAL** — owner must choose between
  Store-native IAP vs existing-customer/companion model. Per plan Task 16:
  until decided, billing UI must not be built on assumptions; tunnel
  vertical slices proceed regardless.
- **Telemetry/privacy: DECIDED (engineering default) — no third-party
  analytics SDKs in V1; crash reporting, if any, must be
  privacy-review-approved before inclusion.** Privacy-policy URL:
  **BLOCKED_EXTERNAL** (owner must publish policy at an owned domain).
- **Backend/API ownership: BLOCKED_EXTERNAL** — PVNaive backend is
  owner-owned (`DashSaman/PV-NativePanel`); versioned API contract to be
  defined in Task 15 under `docs/api/`.

## 10. Legal publisher organization path

**BLOCKED_EXTERNAL** — required owner actions (from
`app/PRODUCTION_READINESS_GAP_ANALYSIS.md`, snapshot 2026-08-29):

1. Apple Developer Program enrollment as an **organization** (D-U-N-S
   verified) — required for a VPN app with Network Extension.
2. Google Play **organization** account + `VpnService` declaration flow.
3. Legal entity/branding ratification for `PVNetwork` app identity
   (application ID §5, bundle root §6) before first Store upload.
4. Publish privacy policy at an owned HTTPS URL.

None of these block Tasks 2–11 engineering; all block Store submission.

## 11. Localization / brand constants

**DECIDED:** Persian (real RTL) + English at launch; localization
architecture must permit more languages (existing
`core/foundation/.../i18n/Localization.kt`). PVNetwork logo: owner-supplied
original asset only — **BLOCKED_EXTERNAL** on receiving the final asset file
into the repository; no substitute logo may be invented (master prompt §40).

## 12. Open items register

| # | Item | Status | Unblocks |
|---|---|---|---|
| O-1 | Apple org enrollment + D-U-N-S | BLOCKED_EXTERNAL | iOS Store submission |
| O-2 | Play org account + VpnService declaration | BLOCKED_EXTERNAL | Play submission |
| O-3 | Billing model choice | BLOCKED_EXTERNAL | Task 16 |
| O-4 | Privacy policy URL | BLOCKED_EXTERNAL | Store listing |
| O-5 | PVNetwork logo asset file | BLOCKED_EXTERNAL | App icon/branding |
| O-6 | Ratify `com.pvnetwork.client` IDs | PROPOSED→owner | first upload |
| O-7 | iOS 16 floor confirmation | PROPOSED→owner | Task 10 |
| O-8 | PVNaive API contract sessions | BLOCKED_EXTERNAL | Task 15 |

## 13. Immediate consequence for execution

Task 1 acceptance is met: no product decision is silently assumed; all
external blockers are explicit. Execution proceeds to **Task 2** (make the
shared foundation genuinely mobile-capable) without waiting on §10 blockers,
exactly as the plan's execution order requires.
