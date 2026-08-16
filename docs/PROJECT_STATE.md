# PVNetwork Project State

Last synchronized: 2026-08-16

## Repository truth

- Repository: `DashSaman/PVN-amirrezagol`
- Branch: `main`
- Product: **PVNetwork**
- Research V1: **93/93 COMPLETE-RESEARCH-v1**
- Research V2: **93/93 COMPLETE-REFERENCE-v2**
- Strict research validator: **PASS** on current main via scheduled `Agent State Validation`
- Research backlog remaining: **0 numbered entries**
- Research completion remains separate from implementation/build/test/device/Store/production status.

## Phase progression

R1–R4.5 research/reference work is complete. The implementation roadmap now governs execution.

- **R5 — Architecture decision phase: PASS**
- **R6 — Minimum viable engine set approval: IN PROGRESS**
- M0 — Application foundation: PENDING after R6 engine-set approval; engine-independent foundation contracts may be prepared without claiming engine implementation.

Machine-readable implementation state: `docs/IMPLEMENTATION_PHASE_STATE.json`.
Architecture decisions: `docs/ARCHITECTURE_DECISIONS_R5.md`.

## R5 decisions now fixed

- Kotlin Multiplatform for product-owned shared domain/application code.
- Compose Multiplatform for supported presentation targets beginning in M1, with native/OS-specific networking kept behind platform adapters.
- Product-owned versioned `PVProfile` canonical model.
- Stable core-adapter API independent of UI and individual engines.
- Secrets represented by opaque references and resolved through a platform `SecretStore` boundary; no approved production plaintext fallback.
- Routing, DNS, import/subscription, localization/RTL and diagnostics are shared product subsystems with platform execution boundaries.
- Third-party engine integrations must follow each completed dossier's source/release/commit pin, reuse decision and license strategy.

## Current active work unit

`R6-MINIMUM-VIABLE-ENGINE-SET / IN_PROGRESS`

Exact next action:

1. Read completed reuse/support decisions and pins for the candidate baseline.
2. Approve the smallest engine set that provides the initial high-value coverage with a compatible license/integration strategy.
3. Record library/subprocess/platform-native boundaries explicitly; proprietary/reference-only implementations remain reference-only.
4. Start M0 engine-independent source foundation, build it, and add real contract tests before any protocol is marked implemented.

## Current product evidence state

- RESEARCHED: 93/93 V1 and 93/93 V2.
- IMPLEMENTED: no protocol engine is yet allowed to be claimed implemented.
- BUILT: no production application build yet.
- TESTED: no product integration/E2E claim yet.
- INTEROPERABILITY VERIFIED: none claimed.
- DEVICE VERIFIED: none claimed.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not reopen completed research without a real contradiction.
- Do not infer implementation from research.
- Do not copy GPL/AGPL/reference-only code into a closed product without an explicit compatible strategy.
- Do not implement cryptography from scratch.
- Do not log or persist reusable secrets in plaintext.
- Re-check live Store/platform policy before release-affecting claims.
