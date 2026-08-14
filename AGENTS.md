# AGENTS.md — PVNetwork AI / Developer Operating Rules

This file defines mandatory operating rules for all AI agents and human developers working on `DashSaman/PVN-amirrezagol`.

## 1. Mandatory startup order

Read these before doing meaningful work:

1. `AI_START_HERE.md`
2. this `AGENTS.md`
3. the **latest `AGENTS_HANDOFF_*.md` file named below**
4. `docs/PROJECT_STATE.md`
5. `docs/RESEARCH_LOG.md`
6. the newest `docs/RESEARCH_CAMPAIGN_STATUS_*.md` snapshot
7. `docs/ROADMAP.md`
8. `docs/PROTOCOL_MATRIX.md`
9. `research/RESEARCH_COMPLETENESS.md`
10. the relevant numbered/shared research dossiers
11. recent Git history and actual repository tree

Repository evidence wins over chat memory. Some trackers can lag newer commits because connector writes may be rejected.

## 2. Current phase

The repository is in the **research / requirements / architecture phase**.

Do not claim implementation, protocol support, successful builds, tests, Store readiness, or production readiness without repository evidence.

## 3. Evidence states must stay separate

1. Researched
2. Candidate identified
3. License reviewed
4. Architecture approved
5. Implemented
6. Builds
7. Unit tested
8. Integration tested
9. E2E tested
10. Real-device tested
11. Store verified
12. Production verified

Never promote a feature without evidence.

## 4. Persistent-memory rule

Important discoveries must be committed. Use:

- `docs/RESEARCH_LOG.md` for chronological findings;
- `docs/PROJECT_STATE.md` for compact current state;
- dated `docs/RESEARCH_CAMPAIGN_STATUS_*.md` when a larger state/log update is safer;
- `AGENTS_HANDOFF_*.md` for exact resumable work-unit handoff;
- relevant files under `research/protocols/` and `research/upstreams/` for durable technical evidence.

Do not leave important decisions only in chat.

## 5. Continuous handoff — mandatory

After every meaningful research/work unit:

1. update the relevant technical dossier(s);
2. update Research Log/Project State or a dated status snapshot where appropriate;
3. update or create the newest `AGENTS_HANDOFF_*.md` with what was done, commits, blockers and exact next action;
4. update this file's **Latest handoff pointer**;
5. record connector-write failures and do not blindly repeat the same rejected write.

Do not log every low-level API call. Log meaningful work units and decisions.

## 6. Anti-loop rule

If the same approach fails twice, do not repeat it unchanged a third time. Preserve evidence through a materially different/smaller file or a dated handoff/status snapshot.

## 7. Research-source rules

For changing/current facts prefer primary evidence:

- official repositories;
- official protocol/spec documentation;
- official platform/Store documentation;
- release notes;
- source code;
- issue/MR/PR history;
- authoritative standards/RFCs.

Pin revisions/releases when possible. Do not treat a GitHub mirror as canonical when the repository says otherwise.

## 8. License and reuse rules

Do not assume open-source means commercially reusable without obligations.

For every candidate record exact source/license paths, component-level differences, dependencies, redistribution/linking obligations, branding/trademark constraints and Store implications.

Engineering license research is not final legal advice/sign-off.

## 9. Architecture rules

- Prefer a unified application/product layer and stable Core Adapter abstractions.
- Do not couple UI directly to a specific engine.
- Do not implement cryptography from scratch.
- Keep import/export format, canonical profile model, protected persistence and engine-runtime representation separate.
- Platform-specific implementations are allowed and often required behind one product-facing contract.

## 10. Localization / branding

Product name: **PVNetwork**.

Persian and English are mandatory first-class languages. Persian must have correct RTL behavior while IP addresses, ports, URLs, protocol IDs, hashes, paths and logs remain readable technical LTR tokens.

Use the exact official PVNetwork logo when provided in the repository. Do not invent replacement branding without owner approval.

## 11. Platform scope

Long-term targets:

- Android phones/tablets/foldables
- Android TV / Google TV
- Windows
- macOS
- iPhone/iPad
- Linux

Do not assume one networking implementation fits every OS.

## 12. Store rule

Store policies change. Re-check current official requirements before implementation/release decisions for Google Play, Android TV/Google TV, Apple App Store, Mac distribution, Microsoft Store and Linux package channels.

## 13. Competitor/upstream learning

Study mature clients/cores/server projects for architecture, menus, bugs, regressions, routing/DNS behavior, reconnect, permissions, crash recovery, battery/performance, packaging, updates, security and Store issues.

Do not copy branding or incompatible-license code.

## 14. Original research campaign remains first priority

The existing `COMPLETE-RESEARCH-v1` campaign and previously active backlog remain first priority. Do not abandon unfinished prior research merely because the full-reference scope below was added.

## 15. Full protocol reference expansion — mandatory second layer

After the prior research gates for an entry/family are satisfied, execute:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

The owner requires the repository to become a complete client+server engineering reference for every protocol/technology.

The second layer requires separate evidence-backed files for:

- server implementations/forks/projects;
- official and community server installers/deployment projects;
- server install matrix across relevant OS/container/orchestration targets;
- server UI/control-panel menus in detail;
- client install matrix across all relevant operating systems/architectures/packages;
- every major client's UI/menu map in detail;
- cryptography;
- data path/wire flow;
- ports/transports/handshake;
- deployment topologies;
- source/license/activity/supply-chain review of server and client projects;
- exact reference index and next action.

The second-layer state is `COMPLETE-REFERENCE-v2`. It comes after `COMPLETE-RESEARCH-v1` and still does not mean implementation or production certification.

## 16. Server installer research rule

Research major community installers/panels because operators use them, but do not equate popularity with safety. Record source pins, root/privilege requirements, installed services/packages, firewall/routing/DNS changes, exposed admin interfaces, credential/secret defaults, update/uninstall/rollback behavior, container privileges/host networking and supply-chain risks.

Do not recommend blind remote-script execution without source review.

## 17. Git discipline

Use meaningful commit messages such as `docs(research): ...`, `docs(protocols): ...`, `docs(agents): ...`. Do not use meaningless commit messages.

## 18. Latest handoff pointer — MUST READ

Newest handoff:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT_V1_CLOSURE.md`

This checkpoint contains the latest OpenConnect dependency/LGPL, issue/MR/fix, test/CI, configuration/storage, API lifetime/callback findings, the v9.21 source-materialization blocker, and exact remaining original-research gaps.

Earlier scope-expansion handoff remains available at:

`AGENTS_HANDOFF_2026-08-14_REFERENCE_EXPANSION.md`

Previous OpenConnect handoff remains available at:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT.md`

## 19. Exact current next action

Continue the previous-priority OpenConnect/Enterprise `COMPLETE-RESEARCH-v1` closure from the newest handoff: map NetworkManager D-Bus/service/secret ownership and OpenConnect GUI screen/profile-storage internals, then security/advisory and packaging gaps. Only after the original research campaign reaches its intended gates should the 93-entry `COMPLETE-REFERENCE-v2` expansion become the main campaign.