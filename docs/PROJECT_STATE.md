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

## R6 approved initial networking families

The smallest approved initial set is WireGuard, OpenVPN and Xray-core, matching the roadmap's first networking wave while deferring redundant cores. Approval means integration planning may proceed; it does not mean implemented.

- WireGuard: official/mature implementation reuse, no cryptography rewrite; per-platform artifact pin/license gate before import.
- OpenVPN: platform-specific engine strategy. OpenVPN 2 desktop subprocess is preferred where obligations are satisfied; AGPL OpenVPN3 is not silently embedded in a closed product; Apple/Android candidates retain their own license/import gates.
- Xray-core: MPL-2.0 research baseline with libXray MIT wrapper reference; exact stable production release/SBOM must be locked before dependency import.

## Current active work unit

`M0-FOUNDATION-CONTRACTS / IN_PROGRESS`

Exact next action: implement and execute engine-independent profile/security/adapter/connection/diagnostics/import/routing/DNS contracts, then establish the reproducible KMP build and CI layer without importing protocol engines.

## Current product evidence state

- RESEARCHED: 93/93 V1 and 93/93 V2.
- IMPLEMENTED: no protocol engine claimed implemented. Product-owned M0 foundation work is starting independently.
- BUILT: no production application build yet.
- TESTED: no protocol/product E2E claim yet.
- INTEROPERABILITY VERIFIED: none claimed.
- DEVICE VERIFIED: none claimed.
- PRODUCTION READY: no.

## Non-negotiable boundaries

- Do not reopen completed research without a real contradiction.
- Do not infer implementation from research or R6 approval.
- Do not copy GPL/AGPL/reference-only code into a closed product without an explicit compatible strategy.
- Do not implement cryptography from scratch.
- Do not log or persist reusable secrets in plaintext.
- Re-check live Store/platform policy before release-affecting claims.
