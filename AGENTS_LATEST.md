# AGENTS Latest Resume Pointer

This file is an additional deterministic recovery pointer for long-running
PVNetwork work.

Always also read `AGENTS.md`, `AGENT_EXECUTION_CONTRACT.md`,
`docs/AGENT_RUN_STATE.json`, recent Git history and the actual repository
tree. Repository evidence wins over this pointer if newer commits exist.

## Current campaign (since 2026-09-23)

The research campaign (V1 93/93 + V2 93/93) is COMPLETE and CLOSED.

The active work is **public-app implementation** per
`docs/superpowers/plans/2026-08-29-public-mobile-client.md`:

- Task 1 (launch decisions): **DONE 2026-09-23** → `app/PUBLIC_APP_DECISIONS.md`.
- Task 2 (mobile-capable shared foundation): **DONE 2026-09-23** → Android
  (AGP 9.3.3 KMP library plugin) + `iosArm64`/`iosSimulatorArm64` targets on
  `core/foundation`, evidence in
  `docs/M-P1_FOUNDATION_MOBILE_TARGETS_VALIDATION.md`.
- Task 3 (canonical account/entitlement models): **DONE 2026-09-23** →
  `com.pvnetwork.core.account` / `com.pvnetwork.core.entitlement` with
  contract tests green on JVM + Android host target.
- State reconciliation snapshot: `app/CURRENT_STATE_2026-09-23.md`.
- Pre-existing red CI items discovered meanwhile are registered in
  `docs/BROKEN_CI_REGISTER.md` (Xray REALITY interop, Mihomo TUIC v5
  interop — both open, both pre-dating 2026-09-23 work).

## Exact next action

**Task 4** of the public-app plan: formalize `PlatformVpnService` and the
connection coordinator in `core/foundation` (files and TDD steps specified
in the plan). Verify with the same local multi-target command set recorded
in `docs/M-P1_FOUNDATION_MOBILE_TARGETS_VALIDATION.md`.

Read `app/PUBLIC_APP_AGENT_HANDOFF.md` first; it remains the authoritative
public-app handoff.

## Historical (superseded)

The original pointer below predates research completion and is kept only as
history. Do not resume from it.

- Original-v1 IKE/IPsec closure and family lists were fully completed and
  validated during the research campaign (V1/V2 93/93, strict validator
  PASS, GitHub Actions run 31873037675).
