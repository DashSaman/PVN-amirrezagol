# Checkpoint — Public App Master Specification

Date: **2026-08-29**
Repository: `DashSaman/PVN-amirrezagol`

## Owner direction captured

The owner intends to ship a **high-quality public PVNetwork application for ordinary users** through Google Play and Apple App Store. The target is a professional production product, not a protocol demo or internal client.

Future agents must optimize for correctness, lifecycle stability, privacy, account/entitlement integrity, Store compliance, real-device evidence and maintainability before protocol-count marketing.

## New authoritative files

1. `app/PUBLIC_APP_MASTER_REQUIREMENTS.md`
   - full product/engineering/Store/quality specification;
   - Android and Apple platform requirements;
   - connection truth state model;
   - account/entitlement/device/billing/backend requirements;
   - routing/DNS/product UX;
   - security/privacy/data deletion;
   - diagnostics/support;
   - device/network QA;
   - performance/stability;
   - CI/CD/release engineering;
   - Store submission;
   - M-P0 through M-P8 Definition of Done;
   - bug severity/release blocker policy;
   - incident response and no-fake-completion rules.

2. `docs/superpowers/plans/2026-08-29-public-mobile-client.md`
   - 20-task implementation plan;
   - exact file/module boundaries;
   - TDD/test expectations;
   - Android `VpnService` vertical slice before broad UI;
   - iOS Packet Tunnel real-device vertical slice next;
   - subscription rollback, consumer UX, routing/DNS, backend, billing, privacy, support, CI and RC tasks.

3. `app/PUBLIC_APP_AGENT_HANDOFF.md`
   - mandatory read order;
   - current repository truth;
   - owner quality intent;
   - exact next action;
   - forbidden shortcuts;
   - Store/publisher blockers;
   - handoff discipline.

4. `app/README.md`
   - updated to index the new authoritative public-app set.

## Exact next action for a future implementation agent

Unless later commits supersede this checkpoint, start with:

**Task 1 — Lock launch decisions and identifiers**

Create:

`app/PUBLIC_APP_DECISIONS.md`

Record or explicitly mark `BLOCKED_EXTERNAL` for legal publisher path, package/bundle IDs, minimum OS versions, initial engine/protocol scope, account model, billing model, privacy/telemetry policy and backend ownership.

Do not fabricate missing owner/legal information.

Then execute Tasks 2–5 shared contracts, Tasks 6–9 Android real vertical slice, and Tasks 10–11 iOS real Packet Tunnel vertical slice.

## Important repository truth

- No new Android/iOS implementation is claimed by this checkpoint.
- No mobile device verification is claimed.
- No Store approval is claimed.
- No production readiness is claimed.
- Existing protocol research/runtime evidence remains useful but must not be misrepresented as mobile certification.

## Quality gate

A public release candidate cannot pass with known Blocker/Critical issues. Compilation, parser success, process start, emulator success or one CI environment is not enough to claim production mobile support.
