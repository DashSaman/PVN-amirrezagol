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

A family may be marked `V1-HANDOFF-READY` when its original research is broad enough to move on with residual evidence gaps explicitly preserved. This is not implementation/support certification and does not make its numbered entries production-ready.

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

Use meaningful commit messages such as `docs(research): ...`, `docs/protocols): ...`, `docs(agents): ...`. Do not use meaningless commit messages.

## 18. Latest handoff pointer — MUST READ

Newest handoff:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

This checkpoint records the reasonable original-research family closure for OpenConnect/Enterprise, including D-Bus/secret ownership, GUI screen/storage map, security/advisory review, packaging, asset references, performance evidence, support/reuse decisions and all remaining explicit gaps.

Earlier OpenConnect closure handoff remains available at:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT_V1_CLOSURE.md`

Scope-expansion handoff remains available at:

`AGENTS_HANDOFF_2026-08-14_REFERENCE_EXPANSION.md`

Previous OpenConnect handoff remains available at:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT.md`

## 19. Exact current next action

OpenConnect/Enterprise shared-family original research is now `V1-HANDOFF-READY / NOT IMPLEMENTED`. Move to the next highest-value unfinished **original `COMPLETE-RESEARCH-v1` family** from actual repository state.

Recommended next family: **Xray / modern proxy ecosystem**, because shared/client research exists but source/core/client/storage/issues/platform/reuse closure is incomplete and it covers many numbered entries.

Keep remaining WireGuard/AmneziaWG v1 gaps queued and return before claiming overall original-campaign completion.

Do **not** begin mass `COMPLETE-REFERENCE-v2` work until the original campaign reaches its intended gates. After every meaningful work unit, create/update a newer AGENTS handoff and point this file to it.

## 20. Mandatory continuous-execution bootstrap

The owner has already authorized execution of the entire repository-documented research/reference backlog. Before continuing meaningful work, also read:

1. `AGENT_EXECUTION_CONTRACT.md`
2. `docs/AGENT_RUN_STATE.json`
3. `docs/AGENT_CHECKPOINT_LOG.md`
4. `research/REFERENCE_V2_COMPLETENESS.md`

When a checkout/Python runtime is available, run:

```bash
python scripts/agent_state.py verify
python scripts/agent_state.py build
python scripts/agent_state.py next
```

Do not ask the owner to say “continue” between work units, protocols, families, or campaigns. The normal loop is:

**work -> persist evidence -> checkpoint -> verify -> select next executable task -> continue**

If the platform forcibly interrupts execution, that is an interruption, not completion. The next invocation must resume from repository state immediately instead of restarting from chat memory.

## 21. Full backlog means all 93 entries and both research layers

The original scope is all 93 numbered entries in `docs/PROTOCOL_MATRIX.md`, with the full gate set in `research/PROTOCOL_RESEARCH_TEMPLATE.md`.

Completing one protocol or family is only a checkpoint. After the original campaign, continue the mandatory second layer tracked in `research/REFERENCE_V2_COMPLETENESS.md` and defined by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.

The generated backlog in `docs/AGENT_BACKLOG.generated.json` is derived by `scripts/agent_state.py`; it exists to prevent forgotten sub-tasks. Repository contracts and evidence remain authoritative.

## 22. Strict no-fake-completion gate

No overall `DONE`, `COMPLETE`, or equivalent claim is allowed unless this passes:

```bash
python scripts/agent_state.py verify --require-complete
```

A strict pass requires, at minimum, all 93 v1 tracker entries to be `COMPLETE-RESEARCH-v1`, all 93 v2 tracker entries to be `COMPLETE-REFERENCE-v2`, the run state to be complete, no nonterminal active work unit, and genuine evidence for the detailed gates.

`V1-HANDOFF-READY`, `IN-RESEARCH`, `EVIDENCE-GAPS`, `PENDING`, `SKELETON`, `RESERVED`, `BLOCKED_EXTERNAL`, or a completed family does not mean the whole campaign is complete.

## 23. Stop and blocker rules

Voluntary stopping is allowed only when:

1. the entire owner-approved scope passes strict completion validation; or
2. every remaining required item is genuinely externally blocked, all blockers are documented, and no independent executable work remains; or
3. a platform/safety/capability restriction forcibly prevents continuation.

Cases 2 and 3 are not completion. Before any interruption that still allows repository writes, update `docs/AGENT_RUN_STATE.json` and `docs/AGENT_CHECKPOINT_LOG.md` with the exact resume point.

If one task is blocked, continue other independent required tasks. If the same approach fails twice, record it and change strategy instead of looping.
