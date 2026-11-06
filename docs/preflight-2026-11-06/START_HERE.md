# PVNetwork — Parallel ChatGPT Pro Preflight for the 6-Day Implementation Sprint
Date: 2026-11-06
Status: planning/assignment only — NOT an implementation or device validation claim.
Repository: DashSaman/PVN-amirrezagol (public at inspection time)
Baseline main SHA: 78dcd4511dbb830a999cc8c1796f2a38f88587e8

## Goal
Run six independent, bounded ChatGPT Pro research/preflight sessions, then one reconciliation session, before spending Codex and Z.ai/GLM coding tokens. Their job is to produce precise evidence-backed implementation contracts, test cases, file-level task maps, and license/architecture decisions for a universal consumer VPN app. No duplicate 93-protocol research, no fake readiness.

## Already established — DO NOT rediscover wholesale
- Research V1/V2 for 93 entries is documented complete. See `research/RESEARCH_COMPLETENESS.md`, `research/REFERENCE_V2_COMPLETENESS.md`.
- Existing `core/foundation`, `apps/android`, `apps/desktop`, and `engines/{xray,wireguard,openvpn,mihomo,openconnect}-adapter`.
- `docs/M-P2_PUBLIC_APPS_VALIDATION.md` reports a locally built Android debug APK and Windows distributable, but explicitly NO physical Android-device validation, NO Store approval, and limited protocol/runtime coverage. Claims are *historical document reports*, not new independent test receipts.
- `app/PUBLIC_APP_MASTER_REQUIREMENTS.md`, `app/CLIENT_SOURCE_REUSE_MATRIX.md`, `app/PUBLIC_APP_DECISIONS.md`, and `docs/superpowers/plans/2026-08-29-public-mobile-client.md` are the starting contracts.
- Previous `docs/PROJECT_STATE.md` and `AGENTS_LATEST.md` may have stale task pointers. Always prefer concrete tree and newest evidence.

## User-approved product scope
- Commercial PVNetwork-branded application whose own UI/business code is intended to remain proprietary.
- Customers can buy/renew PVNetwork services, log in optionally, view account/quota/expiry; external subscriptions/configs continue to work without a PVNetwork account.
- Broad import and protocol goals: Xray family (VLESS/VMess/Trojan/REALITY etc.), Hysteria2, WireGuard, OpenVPN, SSH, SSTP, IKEv2/IPsec, DNS tunnels, NaiveProxy, and more. A feature is not supported until exact real-world device/data-path receipts exist.
- Android first; Windows, macOS, iOS in the future with platform-native VPN requirements.
- Strict six-day implementation *target* AFTER preflight; never promise all features/platforms or Store approvals in six days.

## Collaboration rules (mandatory for all six sessions)
1. Read `AI_START_HERE.md`, `AGENTS.md`, `AGENTS_LATEST.md`, relevant public-app specs, actual branch/CI status, and task-specific prior documentation. Do not copy existing dossiers into new reports.
2. **Branch isolation:** each worker creates its own branch `prep/2026-11-06/0N-<slug>` from latest main and creates ONE primary report at the exact exclusive path below. Open PR to main; DO NOT merge; do not edit main directly.
3. **Exclusive ownership:** do not modify any other worker's report; do not modify shared runtime code, `AGENTS.md`, existing state JSON, `docs/PROJECT_STATE.md` or existing plan files. Propose changes in your own report with exact target files for Codex/Z.ai.
4. **Evidence vocabulary:** OBSERVED in source / VERIFIED by reproducible test / DOCUMENTED historically / PROPOSED / UNKNOWN / BLOCKED. Cite GitHub file paths + line/code locations, official upstream URLs and pinned versions where applicable. Do not invent tool or device execution.
5. **Token economy:** reuse existing 93-protocol research; inspect only needed latest docs/source; set hard bounds on external searches; avoid dumping entire GitHub trees; summarize diffs/gaps only; no repetitive status commits.
6. **Security & public repo:** never commit keys, passwords, private links, subscription tokens, customer data, credentials, real payment secrets, proprietary unreleased commercial code, user-identifying logs, or production configs. This repo was PUBLIC when inspected. Recommend a separate private product repo for proprietary deliverables; public OSS license notices/required corresponding source remain a separate obligation.
7. **Legal:** no pasting Hiddify, Karing, v2rayNG, Happ, NPV or other third-party code/assets unless license, ownership and distribution obligations are specifically approved. The presence of a public repository does not equal permissive licensing. Distinguish MPL file-level duties, GPL source obligations, and proprietary no-source restrictions.
8. **Scope:** research/preflight, specifications, mocks as text, test fixtures/plans, and low-risk proof-of-concept instructions only. Leave substantial development/build integration to Codex/Z.ai after synthesis.
9. End with: DONE / OPEN / BLOCKED, evidence links, decision-needed items, five highest leverage implementation tasks, and PR URL.

## Ownership matrix

| Chat | Workstream | Exclusive report path | Required output |
|---|---|---|---|
| 01 | Existing repo reality / delta audit | `docs/preflight-2026-11-06/01-repo-truth.md` | module-by-module verified inventory, build/device truth, missing API/services, 6-day critical path, stale doc pointers |
| 02 | Source reuse / licenses / white-label accelerator | `docs/preflight-2026-11-06/02-reuse-licenses.md` | legal source matrix incl. all transitive dependencies, Flutter/KMP decision accelerator experiment plan, clear GO/NO-GO and attribution/source obligations |
| 03 | Engines / protocols / subscription imports | `docs/preflight-2026-11-06/03-engines-imports.md` | exact platform/core/protocol matrix; support states; minimal adapter contracts; import-format compatibility table; prioritized tests |
| 04 | Product / UI / accessibility / RTL | `docs/preflight-2026-11-06/04-product-ux.md` | consumer workflows, simple+advanced modes, page map, copy/components, error/empty flows, testable UI acceptance, implementation tickets |
| 05 | Store / accounts / subscriptions / backend APIs | `docs/preflight-2026-11-06/05-backend-commerce.md` | PVNetwork vs external provider separation, API contract proposals, data model, checkout constraints, payment verification, expiry/quota sync, privacy |
| 06 | Android+iOS+desktop release QA/security | `docs/preflight-2026-11-06/06-platform-qa-release.md` | Android 1st critical QA, leak/reconnect/device matrix, release signing/CI, platform future work, security/SBOM and Store blockers |

## Chat 07 — consolidation (only AFTER six PRs exist)
- Read all six reports / PRs, review conflicts, de-duplicate tasks and verify consistency with latest main.
- Deliver a SINGLE integration recommendation at `docs/preflight-2026-11-06/00-integration-handoff.md` on its own branch/PR.
- Include: implementation decision record, week-long ordered DAG, explicit module ownership for Codex vs Z.ai, blocker owner list, protocol release gates, test commands, CI artifact locations, and definition of success/failure.
- Do not auto-merge six PRs if they conflict or have unverified claims.
- Generate separate ready-to-run Codex and Z.ai implementer prompts that only mention approved/pinned files and concrete acceptance tests.

## Explicit release gates
- Prove real tunnel traffic on a physical Android device, not only emulator or parser.
- Verify correct route & DNS behavior, reconnect after Wi-Fi/cellular transitions, lifecycle kill/restart, import of external-provider profiles, secure storage and safe logging.
- Consumer billing cannot pretend to succeed: backend purchase verification, entitlement reconciliation, explicit error/rollback.
- Android and Windows packaging recorded in repo are not proof of production/Store readiness.
- Each protocol/transport combo is allowed in public support claims only when evidenced on its target OS.
- For iOS/macOS, Network Extension/signing/entitlement and Apple account availability are external dependencies, not background assumptions.

## Final principle
Fastest safe route is **existing PVNetwork code first**, augmented only by license-compatible modules if preflight proves a concrete speedup. Research/preflight must reduce Codex and Z.ai token burn rather than become a second infinite research project.
