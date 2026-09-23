# CURRENT STATE — 2026-09-23

Produced by the continuation agent as **Phase A / STEP 4** of the master
continuation prompt, from direct repository + GitHub API inspection
(default branch `main`, last push `2026-08-29T16:22:28Z`, HEAD commit
`484b6a8` at the time of inspection).

This document reconciles the repository's own state files with the actual
tree and CI evidence. Repository evidence wins over chat memory.

## 1. Research status — COMPLETE

- `COMPLETE-RESEARCH-v1`: **93/93** entries (`research/RESEARCH_COMPLETENESS.md`).
- `COMPLETE-REFERENCE-v2`: **93/93** entries (`research/REFERENCE_V2_COMPLETENESS.md`).
- Strict completion validator: **PASS** (GitHub Actions run `31873037675`,
  head SHA `265f0ed4`).
- `docs/AGENT_RUN_STATE.json` records the research campaign as COMPLETE with
  the phase lock satisfied.
- The scheduled `Agent State Validation` workflow still runs regularly and is
  green as of 2026-09-23.

Research must NOT be restarted. Reference-repository re-verification is only
required per-item when an implementation decision actually consumes a
specific upstream (pin-at-consumption-time), not as a blanket re-research
campaign.

## 2. Existing implementation — real but JVM-scoped

Gradle multi-module project (`settings.gradle.kts`):

| Module | Content | Evidence |
|---|---|---|
| `core/foundation` | KMP common contracts: `CoreAdapter`, `AdapterDescriptor`/`CapabilityRegistry`, `ConnectionState` + strict state machine, `PVProfile`, `SecretStore`, `ImportContract`, `NetworkPolicy`, `Diagnostics`, `Localization`, `Branding` | `m0-foundation-ci.yml` green |
| `apps/desktop` | Compose Desktop shell (`Main.kt`, `ShellModel.kt`) + unit test | `m1-desktop-shell-ci.yml` green |
| `engines/wireguard-adapter` | conf importer, model, adapter, secret import transaction, `JvmSystemOpenVpnRuntime`-style system runtime | real Linux kernel namespace handshake + 3 tunneled pings, Actions run `31939414530` |
| `engines/openvpn-adapter` | importer/model/adapter/secret transaction + `JvmSystemOpenVpnRuntime` | real Ubuntu OpenVPN 2.6.19 + TLS peer, run `31942028587`, CONNECTED→TUN→clean stop verified |
| `engines/xray-adapter` | VLESS share-link importer, model, adapter, secret transaction, `JvmHostXrayRuntime` (external binary, config `run -test`, no shell) | real VLESS/RAW/no-TLS data path vs pinned `Xray-core v26.7.28`, run `32072138649`; REALITY variant test file exists |
| `engines/mihomo-adapter` | adapter + `JvmHostMihomoRuntime` + real-binary interop tests | `m3-mihomo-adapter-ci.yml` green |
| `engines/openconnect-adapter` | fail-closed adapter + host-supplied JVM runtime | committed 2026-08-26, gate added to the m3 workflow |
| `tools/foundation-smoke` | smoke tool | M0 evidence |

Toolchain: Kotlin 2.4.10 / Gradle 9.5.0 / JDK 21 (Temurin) / ubuntu-24.04 runners.

Important boundary (from `docs/PROJECT_STATE.md`):
- **DEVICE VERIFIED: none.**
- **PRODUCTION READY: no.**
- All runtime receipts are Linux/JVM/CI-scoped interoperability evidence.

## 3. Public consumer app — SPEC ONLY, implementation not started

The 2026-08-29 documentation wave (commits `8075744..484b6a8`) produced:

- `app/CLIENT_SOURCE_REUSE_MATRIX.md` — reuse classification
  (Karing/Hiddify/v2rayNG/v2rayN/Amnezia = GPL or restricted → REFERENCE-ONLY;
  Xray-core MPL-2.0 = strongest direct upstream candidate).
- `app/KARING_DEEP_SOURCE_ANALYSIS.md`, `app/KARING_PLATFORM_IMPLEMENTATION_ANALYSIS.md`.
- `app/CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md` — **decision: keep and
  extend the existing PVNetwork-owned KMP foundation; do not fork any client.**
- `app/PUBLIC_APP_MASTER_REQUIREMENTS.md` — authoritative consumer-app spec,
  milestones M-P0…M-P8.
- `app/PRODUCTION_READINESS_GAP_ANALYSIS.md` — Store/publisher blockers.
- `docs/superpowers/plans/2026-08-29-public-mobile-client.md` — 20-task
  executable plan (Tasks 1–5 decisions/shared contracts; 6–9 Android vertical
  slice; 10–11 iOS; 12–18 product hardening; 19–20 release pipeline/RC).
- `app/PUBLIC_APP_AGENT_HANDOFF.md` — names the exact next action:
  **Task 1 — create `app/PUBLIC_APP_DECISIONS.md`.**

Verification against the tree: `app/` contains documents only;
`apps/android/` and `apps/ios/` **do not exist**; no `PUBLIC_APP_DECISIONS.md`
exists; `core/foundation` has **no Android/iOS target configured** (JVM target
only). The plan's checkboxes are all unchecked. Conclusion: **Tasks 1–20 of
the public-app plan are all open; Task 1 is the entry point.**

## 4. Architecture decision — confirmed, continue KMP

The previously selected architecture (Kotlin Multiplatform / Compose
foundation with `EngineAdapter`-style contracts — named `CoreAdapter` in the
committed code) exists in the repository with green CI and must be continued.
No evidence supports restarting with a different stack. Key committed rules:

- UI never touches engines directly; everything goes through product-owned
  contracts (`CoreAdapter`, `PreparedConnection`, `ConnectionStateMachine`).
- Canonical `PVProfile` + `SecretStore` reference-based secret handling.
- Host-supplied external-core runtime boundary proven for Xray/Mihomo
  (JVM), with bundled-core adoption still gated per upstream advisories.

## 5. Stale / inconsistent items found

1. `AGENTS_LATEST.md` is stale — it points at the long-finished IKE/IPsec
   V1 closure. The real latest handoff is `app/PUBLIC_APP_AGENT_HANDOFF.md`.
2. `docs/PROJECT_STATE.md` last synchronized 2026-08-18 — it does not record
   the OpenConnect adapter (2026-08-26) nor the 2026-08-29 public-app spec
   wave. Its M3 "in progress" statement predates the mihomo/OpenConnect CI
   gates. Its evidence-state section (DEVICE VERIFIED: none) remains true.
3. `AI_START_HERE.md` mandatory-reading list still describes the research
   phase as current; acceptable since it defers to `AGENTS.md` + run state,
   but a future edit should mark research closed and point implementation
   agents at `app/PUBLIC_APP_AGENT_HANDOFF.md` first.
4. The OpenConnect "M4 gate" lives inside `m3-mihomo-adapter-ci.yml` (no
   separately named workflow) — naming is cosmetic, evidence exists.

## 6. Build / test status

- All module CI workflows green on their last triggering pushes (code paths
  last touched 2026-08-26; docs-only commits since).
- Scheduled agent-state validation green through 2026-09-23.
- No local toolchain existed on the current Windows workstation at session
  start (git/JDK installed during this session; Gradle distribution still to
  be provisioned locally). Local reproducibility of `:core:foundation:jvmTest`
  is planned before touching build files.

## 7. Platform status

| Platform | Status |
|---|---|
| JVM/Desktop (Compose) | builds + tested in CI |
| Android | **not started** (no module) |
| Android TV | not started (post-launch per plan) |
| iOS/iPadOS | **not started** (no module) |
| macOS/Linux desktop | desktop shell only; no packaging/service work |

## 8. Engine status (implementation, not research)

| Engine | Adapter code | Real data-path evidence | Scope limits |
|---|---|---|---|
| WireGuard | yes | Linux kernel namespace CI | no device evidence |
| OpenVPN | yes | Ubuntu system OpenVPN 2.6.19 CI | no OpenVPN3 embed |
| Xray (VLESS) | yes | real VLESS RAW data path CI | no TLS/REALITY/Vision/WS/gRPC/XHTTP receipt yet; bundled-core BLOCKED by advisory range |
| Mihomo | yes | adapter + real-binary interop CI | per `m3-mihomo-adapter-ci.yml` scope |
| OpenConnect | yes (fail-closed) | runtime contract tests | see workflow scope |
| NaiveProxy | **no adapter code** | none | research-complete only; first-class requirement of the master prompt, must be scheduled into the engine plan |
| sing-box | **no adapter code** | none | research-complete only |

## 9. Major risks

1. **Store/publisher organization gates** (Apple org enrollment, Google Play
   org account, D-U-N-S) are owner-side blockers that gate public release but
   not vertical-slice engineering.
2. **KMP target expansion** (JVM → Android/iOS) can silently break common
   code if platform types leak into `commonMain`; Task 2 exists for this.
3. **Bundling native cores** on Android (16 KB page size, ABI matrix) is a
   heavy engineering step; the proven host-supplied-runtime pattern does not
   transfer to consumer Android, where cores must be packaged/approved.
4. Xray bundled-core advisory-range blocker (documented in
   `docs/M2_XRAY_STABLE_RELEASE_GATE.md`).
5. Documentation drift risk shown by §5; state files must be updated with
   each implementation slice.

## 10. Decisions for this session (evidence-backed)

- Continue the committed KMP architecture — no restart.
- Enter the public-app execution plan; execute Task 1 now
  (`app/PUBLIC_APP_DECISIONS.md`), marking owner-side unknowns
  `BLOCKED_EXTERNAL` instead of fabricating values.
- Then proceed to Task 2 (mobile-capable foundation) in dependency order.
- NaiveProxy adapter work must be inserted into the engine backlog
  (master-prompt first-class requirement) once the mobile foundation tasks
  unblock engine selection for the first vertical slice.

## 11. Exact next action

Execute **Task 1** of `docs/superpowers/plans/2026-08-29-public-mobile-client.md`:
create `app/PUBLIC_APP_DECISIONS.md`, then Task 2.
