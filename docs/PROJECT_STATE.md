# PVNetwork Project State

Last synchronized: 2026-08-16

## Repository truth

- Repository: `DashSaman/PVN-amirrezagol`
- Branch: `main`
- Product: **PVNetwork**
- Research V1: **93/93 COMPLETE-RESEARCH-v1**
- Research V2: **93/93 COMPLETE-REFERENCE-v2**
- Strict research validator: **PASS**
- Research backlog remaining: **0 numbered entries**
- Research completion remains separate from implementation/build/test/device/Store/production status.

## Phase progression

- R1–R4.5 research/reference work: COMPLETE.
- R5 architecture decision: **PASS**.
- R6 minimum viable engine set: **PASS**.
- M0 application foundation: **PASS**.
- M1 first working platform/client shell: **IN PROGRESS**.

## M0 close evidence

M0 now meets every roadmap item: repository/build/CI structure, shared models, secure-storage and adapter interfaces, localization foundation, and PVNetwork branding foundation. The final M0 localization/branding commit passed pinned Kotlin 2.4.10 / Gradle 9.5.0 CI in GitHub Actions run `31938458227`.

M0 PASS does not claim a production application or any protocol implementation.

## Current M1 work

A Compose Multiplatform `1.11.1` desktop shell is being added under `apps/desktop` with English/Persian, RTL, system/light/dark themes, a profile-list surface, canonical connection-state surface, and sanitized diagnostics surface.

The shell starts with no invented profiles, `DISCONNECTED`, and no invented diagnostic events. Compile/test/package results remain pending until the M1 workflow executes successfully.

## Product evidence state

- RESEARCHED: V1 93/93 and V2 93/93.
- IMPLEMENTED: M0 product-owned foundation; M1 desktop shell source is present in the active work unit.
- BUILT: M0 shared foundation JVM/KMP gate PASS; M1 desktop app build not yet claimed.
- TESTED: M0 foundation tests PASS; M1 tests not yet claimed.
- INTEROPERABILITY VERIFIED: none.
- DEVICE VERIFIED: none.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not reopen completed research without a real contradiction.
- Do not infer protocol implementation from research/R6/M0/M1 UI work.
- Dependency imports remain governed by `docs/ENGINE_SET_R6.md`.
- Do not copy GPL/AGPL/reference-only code into a closed product without an explicit compatible strategy.
- Do not implement cryptography from scratch.
- Do not log or persist reusable secrets in plaintext.
- Re-check live Store/platform policy before release-affecting claims.
