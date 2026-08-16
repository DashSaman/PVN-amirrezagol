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
- R5 — Architecture decision phase: **PASS** (`docs/ARCHITECTURE_DECISIONS_R5.md`).
- R6 — Minimum viable engine set approval: **PASS** (`docs/ENGINE_SET_R6.md`).
- M0 — Application foundation: **IN PROGRESS**.

Machine-readable implementation state: `docs/IMPLEMENTATION_PHASE_STATE.json`.

## Current M0 evidence

The first engine-independent foundation slice is now source-backed and locally executable:

- versioned canonical `PVProfile` metadata with opaque `SecretRef` values;
- `SecretStore` platform boundary with no production plaintext fallback implementation;
- product-owned `CoreAdapter`/capability contracts;
- canonical connection state machine;
- structured diagnostic event/redaction contract;
- import warning/lossiness contracts;
- routing and DNS policy contracts;
- executable foundation smoke harness.

Validation evidence: `docs/M0_FOUNDATION_VALIDATION_2026-08-16.md`.

This slice was compiled with `kotlinc-jvm 1.9.0` and executed successfully in the run that created it. That is a real JVM smoke build/test, not a claim that the Kotlin 2.4.10 KMP application or target apps have been built.

## R6 approved initial networking families

The smallest approved initial set is WireGuard, OpenVPN and Xray-core. Dependency imports remain gated by exact per-platform source/release/license decisions in `docs/ENGINE_SET_R6.md`.

## Current active work unit

`M0-KMP-BUILD-CI / IN_PROGRESS`

Exact next action: add the reproducible Kotlin Multiplatform build configuration and CI validation for the foundation module, then localization and branding foundation without importing protocol engines.

## Current product evidence state

- RESEARCHED: 93/93 V1 and 93/93 V2.
- IMPLEMENTED: M0 product-owned foundation contracts slice; **no protocol engine claimed implemented**.
- BUILT: M0 foundation JVM smoke artifact only; **no production application build yet**.
- TESTED: M0 foundation smoke assertions only; **no protocol or product E2E claim**.
- INTEROPERABILITY VERIFIED: none claimed.
- DEVICE VERIFIED: none claimed.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not reopen completed research without a real contradiction.
- Do not infer protocol implementation from research or R6 approval.
- Do not copy GPL/AGPL/reference-only code into a closed product without an explicit compatible strategy.
- Do not implement cryptography from scratch.
- Do not log or persist reusable secrets in plaintext.
- Re-check live Store/platform policy before release-affecting claims.
