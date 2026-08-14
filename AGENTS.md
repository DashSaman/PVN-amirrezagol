# AGENTS.md — PVNetwork AI / Developer Operating Rules

This file defines mandatory operating rules for all AI agents and human developers working on `DashSaman/PVN-amirrezagol`.

## 1. Mandatory startup order

Read before meaningful work:

1. `AI_START_HERE.md`
2. this `AGENTS.md`
3. `AGENT_EXECUTION_CONTRACT.md`
4. `docs/AGENT_RUN_STATE.json`
5. the **latest `AGENTS_HANDOFF_*.md` named below**
6. `docs/PROJECT_STATE.md`
7. `docs/AGENT_CHECKPOINT_LOG.md`
8. `docs/RESEARCH_LOG.md`
9. newest `docs/RESEARCH_CAMPAIGN_STATUS_*.md`
10. `docs/ROADMAP.md`
11. `docs/PROTOCOL_MATRIX.md`
12. `research/RESEARCH_COMPLETENESS.md`
13. `research/REFERENCE_V2_COMPLETENESS.md`
14. relevant numbered/shared research dossiers
15. recent Git history and actual repository tree

Repository evidence wins over chat memory. Trackers may lag newer commits when connector writes are rejected.

## 2. Current phase

The repository is in **research / requirements / architecture**.

Do not claim implementation, protocol support, successful builds, tests, Store readiness, or production readiness without repository evidence.

## 3. Evidence states stay separate

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

## 4. Persistent memory

Important discoveries must be committed. Use:

- `docs/RESEARCH_LOG.md` for chronological findings;
- `docs/PROJECT_STATE.md` for compact current state;
- dated `docs/RESEARCH_CAMPAIGN_STATUS_*.md` snapshots;
- `docs/AGENT_RUN_STATE.json` for machine-readable active task/state;
- `docs/AGENT_CHECKPOINT_LOG.md` for deterministic interruption recovery;
- `AGENTS_HANDOFF_*.md` for exact resumable technical handoff;
- `research/protocols/` and `research/upstreams/` for durable technical evidence.

Do not leave important decisions only in chat.

## 5. Continuous handoff — mandatory

After every meaningful work unit:

1. update relevant technical dossier(s);
2. update Run State;
3. append/update checkpoint evidence;
4. update Project State/Research Log or a dated status snapshot;
5. create/update newest `AGENTS_HANDOFF_*.md` with work, commits, failures, blockers and exact next action;
6. update this file's Latest handoff pointer;
7. immediately continue to the next executable required task.

Do not log every low-level API call. Log meaningful work units and decisions.

## 6. Anti-loop rule

If the same approach fails twice, do not repeat it unchanged a third time. Record the failure, change source/path/tool/granularity/strategy, and continue other independent work.

## 7. Research source rules

For current/changing facts prefer primary evidence:

- official repositories;
- official protocol/spec documentation;
- official platform/Store documentation;
- release notes;
- source code;
- issue/MR/PR history;
- authoritative standards/RFCs.

Pin revisions/releases when possible. Do not treat a mirror as canonical when upstream says otherwise.

## 8. License and reuse

Open source does not automatically mean commercially reusable without obligations.

For every candidate record source/license paths, component-level differences, dependencies, redistribution/linking obligations, branding/trademark constraints and Store implications.

Client application and core/engine licenses must be reviewed independently. Engineering license research is not final legal advice.

## 9. Architecture rules

- Prefer unified product/application layer plus stable Core Adapter abstractions.
- Do not couple UI directly to one engine.
- Do not implement cryptography from scratch.
- Keep import/export, canonical profile, protected persistence and runtime engine config separate.
- Keep reusable credentials, non-secret remembered choices and transient session material separate.
- Platform-specific implementations are expected behind shared product contracts.
- Support/certification is version/capability/evidence based, not a Boolean marketing flag.

## 10. Localization / branding

Product: **PVNetwork**.

Persian and English are mandatory first-class languages. Persian requires correct RTL while IPs, ports, URLs, protocol IDs, hashes, paths and logs remain readable technical LTR tokens.

Use the exact owner-supplied PVNetwork logo when present. Do not invent replacement branding without approval.

## 11. Platform scope

Long-term targets:

- Android phones/tablets/foldables
- Android TV / Google TV
- Windows
- macOS
- iPhone/iPad
- Linux

Do not assume one network implementation fits every OS.

## 12. Store rule

Store policies change. Re-check current official requirements before implementation/release decisions for Google Play, Android TV/Google TV, Apple App Store, Mac distribution, Microsoft Store and Linux package channels.

## 13. Competitor/upstream learning

Study mature clients, cores, servers, installers and panels for architecture, menus, storage, bugs, regressions, routing/DNS, reconnect, permissions, crash recovery, performance/battery, packaging, updates, security and Store issues.

Do not copy branding or incompatible-license code/assets.

## 14. Original research campaign remains first priority

The existing `COMPLETE-RESEARCH-v1` campaign across all 93 entries remains first priority.

A shared family may become `V1-HANDOFF-READY` when its original research is broad enough to move on and residual gaps are explicitly preserved. This is not implementation/support certification and does not make numbered entries production-ready.

## 15. Mandatory second layer — COMPLETE-REFERENCE-v2

After original v1 research gates reach their intended state, execute:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

for every applicable entry. This layer requires evidence-backed separate files for:

- server implementations/forks/projects;
- official/community server installers/deployment projects;
- server install matrix across OS/container/orchestration targets;
- server UI/control-panel menus in detail;
- client install matrix across relevant OS/architectures/packages;
- every major client's UI/menu map in detail;
- cryptography;
- data path/wire flow;
- ports/transports/handshake;
- deployment topologies;
- source/license/activity/supply-chain review;
- exact reference index and next action.

The state is `COMPLETE-REFERENCE-v2`; it still does not mean product implementation/certification.

## 16. Server installer research rule

Research major community installers/panels because operators use them, but popularity is not safety proof. Record source pins, privileges/root requirements, installed packages/services, firewall/routing/DNS changes, exposed admin interfaces, credential/secret defaults, update/uninstall/rollback, container privileges/host networking and supply-chain risks.

Do not recommend blind remote-script execution without source review.

## 17. Git discipline

Use meaningful commit messages such as `docs(research): ...`, `docs(protocols): ...`, `docs(agents): ...`.

## 18. Latest handoff pointer — MUST READ

Newest handoff:

`AGENTS_HANDOFF_2026-08-14_XRAY_V1_2.md`

This checkpoint closes Xray/modern-proxy shared-family original research at `V1-HANDOFF-READY / NOT IMPLEMENTED`, preserves all residual gaps, and activates WireGuard/AmneziaWG residual v1 closure as the next work unit.

Previous Xray handoff:

`AGENTS_HANDOFF_2026-08-14_XRAY_V1_1.md`

Previous family handoff:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

Scope-expansion handoff:

`AGENTS_HANDOFF_2026-08-14_REFERENCE_EXPANSION.md`

## 19. Exact current next action

Active work unit: `WIREGUARD-AMNEZIAWG-V1-CLOSURE`.

1. read current `research/upstreams/wireguard-family/` tree and previous WireGuard evidence;
2. close Windows source/storage/service evidence;
3. close dependency/SBOM/license distinctions;
4. close AmneziaWG platform/source/version gaps;
5. finalize client/reference and issue/regression decisions;
6. synchronize entries 002/003 and shared family index/state;
7. if the family becomes `V1-HANDOFF-READY`, checkpoint and immediately select the next unfinished original-v1 family;
8. keep residual implementation/device/server evidence explicit;
9. do **not** begin mass `COMPLETE-REFERENCE-v2` work yet.

## 20. Mandatory continuous-execution bootstrap

The owner has authorized the entire repository-documented research/reference backlog. Also read:

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

If this execution path is unavailable, record it and continue through available repository tools rather than looping.

Do not ask the owner to say “continue” between work units. Normal loop:

**work -> persist evidence -> checkpoint -> verify -> select next executable task -> continue**

A platform interruption is not completion; next invocation resumes from repository state.

## 21. Full backlog means all 93 entries and both layers

The original scope is all 93 entries in `docs/PROTOCOL_MATRIX.md`, with gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`.

After original v1, continue the mandatory v2 layer tracked in `research/REFERENCE_V2_COMPLETENESS.md` and defined by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.

`docs/AGENT_BACKLOG.generated.json` is derived by agent-state tooling and helps prevent forgotten subtasks. Contracts/evidence remain authoritative.

## 22. Strict no-fake-completion gate

No overall `DONE`, `COMPLETE`, or equivalent claim unless:

```bash
python scripts/agent_state.py verify --require-complete
```

passes and repository evidence confirms all required v1/v2 gates.

`V1-HANDOFF-READY`, `IN-RESEARCH`, `EVIDENCE-GAPS`, `PENDING`, `SKELETON`, `RESERVED`, `BLOCKED_EXTERNAL`, or one completed family is not overall completion.

## 23. Stop and blocker rules

Voluntary stop is allowed only when:

1. entire approved scope passes strict completion validation; or
2. every remaining required item is genuinely externally blocked, documented, and no independent executable work remains; or
3. a platform/safety/capability restriction forcibly prevents continuation.

Cases 2/3 are interruptions/blockers, not completion. Before interruption, persist exact resume state whenever repository writes remain available.

If one task is blocked, continue independent required work.