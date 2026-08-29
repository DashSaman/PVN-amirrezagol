# Agent Handoff — PVNetwork Public Consumer App

Date: **2026-08-29**

Repository: `DashSaman/PVN-amirrezagol`

Status: **PUBLIC-APP REQUIREMENTS + EXECUTION PLAN DEFINED; MOBILE IMPLEMENTATION HAS NOT YET BEEN CLAIMED.**

This is the primary handoff for any agent asked to continue building the public PVNetwork application.

## Mandatory read order for public-app work

1. `AGENTS.md`
2. `docs/PROJECT_STATE.md`
3. `app/PUBLIC_APP_MASTER_REQUIREMENTS.md`
4. `docs/superpowers/plans/2026-08-29-public-mobile-client.md`
5. `app/PUBLIC_APP_DECISIONS.md` **if it exists when execution starts**
6. `app/PRODUCTION_READINESS_GAP_ANALYSIS.md`
7. `app/CROSS_PLATFORM_ARCHITECTURE_RECOMMENDATION.md`
8. `app/KARING_DEEP_SOURCE_ANALYSIS.md`
9. `app/KARING_PLATFORM_IMPLEMENTATION_ANALYSIS.md`
10. `app/CLIENT_SOURCE_REUSE_MATRIX.md`
11. existing `core/foundation`, `engines/*-adapter`, `apps/desktop` code/tests
12. newest mobile/public-app checkpoint and recent Git history

Repository evidence wins over this handoff if later commits supersede it.

---

# Owner intent

The owner wants a **high-quality public application for ordinary users**, intended for Google Play and Apple App Store, not an internal proof-of-concept.

The quality bar is:

- simple for non-technical users;
- professional UI/UX;
- stable connection lifecycle;
- correct subscription/quota/expiry handling;
- strong privacy and secret handling;
- Store-compliant;
- real-device validated;
- maintainable;
- no fake completion claims;
- no Blocker/Critical known defects at release candidate.

Do not optimize primarily for the number of protocol names displayed in the UI.

---

# Current repository truth

At this handoff:

- protocol/reference research is extensive and should not be restarted wholesale;
- `apps:desktop` exists;
- `core:foundation` is Kotlin Multiplatform in structure but currently has JVM-only target configuration in the inspected state;
- engine adapters already exist for WireGuard, OpenVPN, Xray, Mihomo and OpenConnect;
- current real runtime receipts are scoped primarily to Linux/JVM/CI paths;
- no Android public app module has been claimed complete;
- no iOS host + Packet Tunnel implementation has been claimed complete;
- no mobile device certification exists;
- no Store approval exists;
- `docs/PROJECT_STATE.md` correctly states `DEVICE VERIFIED: none` and `PRODUCTION READY: no` in the inspected state.

The main distance-to-market is now **mobile product/platform/backend/privacy/QA/release engineering**, not broad protocol research.

---

# Authoritative public-app specification

`app/PUBLIC_APP_MASTER_REQUIREMENTS.md` defines:

- product mission and quality bar;
- supported platform priority;
- architecture boundaries;
- canonical connection truth model;
- Android production/lifecycle requirements;
- Apple Packet Tunnel requirements;
- consumer UX;
- account/entitlement/device model;
- billing integrity;
- backend/control-plane requirements;
- routing/DNS requirements;
- protocol/engine release gate;
- security and privacy;
- account deletion;
- diagnostics/support;
- device/network QA matrices;
- performance/stability expectations;
- CI/CD;
- Store submission package;
- publisher organization requirements;
- milestones M-P0 through M-P8;
- bug severities and release blockers;
- migration/data integrity;
- privacy-safe observability;
- incident response;
- future-agent rules;
- exact release-declaration template.

Agents must not replace this with a shorter ad-hoc checklist.

---

# Execution plan

`docs/superpowers/plans/2026-08-29-public-mobile-client.md` defines 20 implementation tasks.

High-level sequence:

```text
Task 1     launch decisions
Tasks 2-5 shared mobile/product/platform contracts
Tasks 6-9 Android shell -> VpnService -> real engine -> lifecycle stress
Tasks 10-11 iOS host/Packet Tunnel -> real engine
Task 12    safe subscription update/rollback
Task 13    consumer UX
Task 14    routing/DNS product contracts
Task 15    backend account/device/entitlement
Task 16    selected Store billing model
Task 17    data inventory/privacy/deletion
Task 18    support/sanitized diagnostics
Task 19    CI/supply-chain/signed RC pipeline
Task 20    RC/device/Store dry-run acceptance
```

Do not skip directly to Task 13 UI polish before the connection/lifecycle contracts are real.

---

# Exact next action

Unless later repository state shows these items completed, the next execution task is **Task 1: Lock launch decisions and identifiers**.

Create:

`app/PUBLIC_APP_DECISIONS.md`

It must record or explicitly mark `BLOCKED_EXTERNAL` for:

- legal publisher organization path;
- Android application ID;
- Apple bundle ID root;
- minimum Android version;
- Android target SDK strategy;
- minimum iOS version;
- first launch engine/protocol/transport/security set;
- account/login model;
- billing/purchase model;
- telemetry/privacy policy;
- backend/API ownership;
- launch platform list vs post-launch platform list.

Do not fabricate missing owner/legal values.

After Task 1, proceed to Task 2 and Task 3 rather than reopening competitor research.

---

# Architecture that must be preserved

```text
PVNetwork UI
 -> PVNetwork use cases/application layer
 -> PVNetwork canonical models
 -> PlatformVpnService / platform adapters
 -> EngineAdapter contracts
 -> approved engine / OS VPN APIs
```

Forbidden shortcuts:

- UI -> Xray/sing-box/Mihomo directly;
- Android Activity owning engine lifecycle;
- iOS host app pretending to be the Packet Tunnel runtime;
- core JSON as product database;
- raw credentials in model/logs;
- duplicated Android and iOS account/entitlement models;
- reporting `CONNECTED` because process/service started;
- silently dropping unsupported routing/DNS/security options.

---

# First release feature philosophy

A professional first release should include a **small number of fully proven paths** rather than every researched protocol.

The public V1 should prioritize:

- login/import;
- account/entitlement/quota/expiry;
- simple Connect/Disconnect;
- Auto/Smart + manual server selection;
- safe subscription refresh;
- real state/error handling;
- reconnect/network transitions;
- simple routing/DNS presets;
- Persian + English + RTL;
- diagnostics/support;
- privacy/account deletion;
- stable Store release pipeline.

Advanced raw core settings can remain expert/developer features until the base product is stable.

---

# Store/publisher blockers agents must remember

Under the policy snapshot researched on 2026-08-29:

- Apple VPN apps require organization publisher enrollment and valid Network Extension use;
- Google Play `VpnService` public distribution uses organization-account requirements under current/upcoming policy flow;
- organization/D-U-N-S verification is an owner/admin prerequisite;
- Android phone/tablet Play planning must account for API 36+ requirements effective 2026-08-31;
- native Android cores need current 64-bit/16 KB page-size compatibility;
- Apple submissions use current Xcode/iOS SDK requirements;
- Google `VpnService` declaration/disclosure/review evidence is required;
- Apple VPN data-use/privacy restrictions are stricter than an ordinary utility app;
- Store billing rules apply if digital VPN access is purchased/unlocked in-app;
- account deletion requirements apply if account creation exists.

Store rules change. Re-check official sources before implementation/release decisions that depend on them.

---

# Karing research role

Karing remains the strongest capability/UX/platform reference studied, but do not blindly fork it.

Important findings already committed:

- Flutter/product layer + Karing sing-box fork + rulesets;
- broad routing/DNS/TLS/protocol support;
- Android thin native bridge with external `vpn_service`;
- Apple thin Packet Tunnel/System Extension shell delegating to `LibVpnCore`;
- Windows/Linux separate packaged service/core artifacts;
- tvOS native SwiftUI + QR/LAN provisioning;
- parts of Karing's final service/build chain are not fully present in the public app repository;
- Karing app/core licensing requires deliberate GPL-compatible legal strategy for direct copying.

Use these lessons for architecture and behavior, not uncontrolled source copying.

---

# Definition of “done”

An agent must scope its claim.

Examples:

- `Android module compiles` is not `Android supported`.
- parser test is not runtime support.
- runtime start is not data-path verification.
- emulator success is not mobile device verification.
- one phone is not production matrix verification.
- internal TestFlight/Play test is not Store approval.
- Store approval is not production verification.

Use the evidence-state ladder in `AGENTS.md` and milestone DoD in the master spec.

---

# Release blockers

Public RC must stop for:

- known Blocker;
- known Critical security/privacy/secret leak;
- false Connected state;
- broad reproducible crash/ANR;
- broken entitlement/billing integrity;
- tunnel traffic behavior contradicting UI/security claim;
- Store data disclosures inconsistent with actual behavior;
- unsigned/untraceable release artifact;
- no real-device evidence for claimed mobile path.

Major defects normally block RC unless explicitly risk-accepted and documented.

---

# Required handoff discipline for future work

At the end of each meaningful implementation slice:

1. update the relevant implementation/validation doc;
2. update `docs/PROJECT_STATE.md` only with evidence-backed status;
3. add a dated checkpoint when useful;
4. update this handoff or create a newer public-app handoff if the exact next task changes materially;
5. keep `docs/FOREGROUND_ACTIVITY.json` synchronized for interactive work;
6. record exact commits, test commands, device models/OS, core versions/hashes and limitations;
7. leave an exact next action.

No important implementation truth should remain only in chat.
