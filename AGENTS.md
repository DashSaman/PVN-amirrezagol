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
7. `docs/AGENT_CHECKPOINT_LOG.md` plus newer dated `docs/AGENT_CHECKPOINT_*.md`
8. `docs/RESEARCH_LOG.md`
9. newest `docs/RESEARCH_CAMPAIGN_STATUS_*.md`
10. `docs/ROADMAP.md`
11. `docs/PROTOCOL_MATRIX.md`
12. `research/RESEARCH_COMPLETENESS.md`
13. `research/REFERENCE_V2_COMPLETENESS.md`
14. relevant numbered/shared research dossiers
15. recent Git history and actual repository tree

Repository evidence wins over chat memory. Trackers/pointers can lag newer commits; when they disagree, inspect the latest machine Run State, handoff, checkpoint and real tree before changing work.

## 2. Current phase

The repository is in **research / requirements / architecture**. Do not claim implementation, protocol support, successful builds/tests, Store readiness or production readiness without repository evidence.

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

- `docs/RESEARCH_LOG.md` and dated campaign snapshots for research chronology;
- `docs/PROJECT_STATE.md` for compact state;
- `docs/AGENT_RUN_STATE.json` for machine-readable active task;
- checkpoint log/dates for deterministic interruption recovery;
- `AGENTS_HANDOFF_*.md` for exact resumable technical handoff;
- `research/protocols/` and `research/upstreams/` for durable evidence.

Do not leave important decisions only in chat.

## 5. Continuous handoff — mandatory

After every meaningful work unit:

1. update technical dossier(s);
2. update Run State;
3. add checkpoint evidence;
4. update Project State/Research Log or dated status where useful;
5. create/update newest handoff with work, commits, failures, blockers and exact next action;
6. update this file's Latest handoff/current-action pointer;
7. immediately continue to the next executable task.

If a monolithic log becomes connector-truncated, create a dated checkpoint instead of a destructive blind rewrite.

## 6. Anti-loop rule

If the same approach fails twice, record it and materially change source/path/tool/granularity. Continue independent work instead of looping.

## 7. Research source rules

For current/changing facts prefer official repositories, protocol/spec documentation, official platform/Store docs, release notes, source code, issue/MR/PR history and authoritative RFC/standards sources. Pin revisions/releases when possible. Do not treat a mirror as canonical when upstream says otherwise.

## 8. License and reuse

Open source is not automatically commercially reusable without obligations. Record component-specific source/license paths, dependencies, redistribution/linking obligations, branding/trademark constraints and Store implications. Client and engine licenses are separate. Engineering review is not final legal sign-off.

## 9. Architecture rules

- Prefer unified product layer plus stable Core/Platform Adapters.
- Do not couple UI directly to one engine.
- Do not implement cryptography from scratch.
- Keep import/export, canonical profile, protected persistence and runtime backend config separate.
- Keep reusable credentials, remembered non-secret choices and transient session state separate.
- Platform-specific implementations are expected behind shared product contracts.
- Support/certification is exact capability/version/evidence based, not a Boolean marketing flag.

## 10. Localization / branding

Product: **PVNetwork**. Persian and English are first-class. Persian is RTL, while IPs, ports, URLs, protocol IDs, hashes, paths, IDs and logs remain technical LTR spans. Use owner-supplied branding only.

## 11. Platform scope

Long-term targets include Android phones/tablets/foldables, Android TV/Google TV, Windows, macOS, iPhone/iPad and Linux. Infrastructure-only technologies must not be forced into consumer client UX when the role is not applicable.

## 12. Store rule

Store policies change. Re-check current official requirements before implementation/release decisions for Google Play/TV, Apple App Store/Mac, Microsoft Store and Linux package channels.

## 13. Competitor/upstream learning

Study mature clients, cores, servers, installers and panels for architecture, UI/menu/state, storage, regressions, routing/DNS, reconnect, permissions, crash recovery, performance, packaging, updates, security and Store issues. Do not copy incompatible code/assets/branding.

## 14. Original v1 remains authoritative

The original 93-entry `COMPLETE-RESEARCH-v1` scope remains authoritative. Family-level `V1-HANDOFF-READY` checkpoints may allow v2 work while residual v1 evidence gaps stay explicit; they are not implementation/support certification. If a v2 assumption exposes a real missing v1 prerequisite, repair and checkpoint it rather than hiding it.

## 15. Mandatory second layer — COMPLETE-REFERENCE-v2

For each applicable entry, execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` and create evidence for:

- server/peer implementations;
- installers/deployment projects;
- server OS/container/orchestration matrix;
- server/control UI menus;
- client/peer install matrix;
- client/peer UI map;
- cryptography/security boundary;
- data path/wire flow;
- ports/transports/handshake;
- deployment topologies;
- source/license/activity/supply-chain review;
- exact reference index and next action.

For infrastructure protocols where “consumer client” is not meaningful, use evidence-backed `N/A-CONSUMER / PEER-MAPPED` treatment rather than inventing a fake app.

`COMPLETE-REFERENCE-v2` still does not mean product implementation/certification.

## 16. Installer/deployment rule

Popularity is not safety proof. Record source pins, root/capabilities, installed packages/services, firewall/routing/DNS/network-namespace changes, exposed admin interfaces, secret defaults, update/uninstall/rollback, container privileges/host networking and supply-chain risks. Do not recommend blind remote scripts without source review.

## 17. Git discipline

Use meaningful commits such as `docs(research): ...`, `docs(protocols): ...`, `docs(agents): ...`.

## 18. Latest handoff pointer — MUST READ

Newest handoff:

`AGENTS_HANDOFF_2026-08-14_L2TPV3_V2_1.md`

This checkpoint records entry 009 L2TPv3 as `REFERENCE-V2-SOURCE-COMPLETE / ADVANCED-PSEUDOWIRE-EXECUTION-BLOCKED / NOT IMPLEMENTED` and advances active work to entry 010 L2TPv3/IPsec.

Previous relevant handoffs:

- `AGENTS_HANDOFF_2026-08-14_L2TP_IPSEC_V2_1.md`
- `AGENTS_HANDOFF_2026-08-14_IPSEC_V2_1.md`
- `AGENTS_HANDOFF_2026-08-14_WIREGUARD_AWG_V2_9.md`
- `AGENTS_HANDOFF_2026-08-14_OPENVPN_V2_TO_WIREGUARD_AWG_V2.md`
- `AGENTS_HANDOFF_2026-08-14_XRAY_V1_2.md`
- `AGENTS_HANDOFF_2026-08-14_REFERENCE_EXPANSION.md`

## 19. Exact current next action

Active work unit:

`L2TPV3-IPSEC-COMPLETE-REFERENCE-V2`

Entry:

- **010 L2TPv3/IPsec**

Required sequence:

1. read entry 010 v1 evidence;
2. reuse entry 009 L2TPv3 pseudowire dossier and entries 004–007 IKE/IPsec dossier without merging their semantics;
3. define exact IPsec selector/protection composition for direct-IP protocol 115 and UDP L2TPv3;
4. document current IKE/auth/ESP security policy boundaries and credential ownership;
5. map serious Linux and vendor protected-pseudowire implementations/deployments;
6. build all 11 mandatory v2 files including server/peer install/UI matrices;
7. document protected encapsulation/data-path order, NAT/firewall/MTU/ECN/topologies;
8. record source/license/activity/supply-chain/upgrade/uninstall/rollback evidence;
9. reconcile all 16 v2 gates;
10. preserve external Linux/Cisco/IPsec packet/interoperability blockers instead of fabricating receipts;
11. checkpoint and immediately continue the next independent entry/family.

Entries 002–009 remain strict-tracker `PENDING` where external execution evidence is still missing. Do not redo their completed source/reference work unless upstream evidence materially changes.

## 20. Continuous-execution bootstrap

The owner has authorized the full documented backlog. Also read `AGENT_EXECUTION_CONTRACT.md`, Run State, checkpoint(s), and `research/REFERENCE_V2_COMPLETENESS.md`.

When checkout/Python runtime is available:

```bash
python scripts/agent_state.py verify
python scripts/agent_state.py build
python scripts/agent_state.py next
```

If unavailable, record it and continue through available repo tools rather than looping.

Normal loop:

**work -> persist evidence -> checkpoint -> verify -> next task -> continue**

Do not ask the owner to say “continue” between known work units. A platform interruption is not completion.

## 21. Full backlog means all 93 entries and both layers

All 93 entries remain in scope with v1 gates plus the v2 expansion. Generated backlog state helps prevent missed subtasks but repository contracts/trackers/handoffs/evidence are authoritative.

## 22. Strict no-fake-completion gate

No overall `DONE`/`COMPLETE` claim unless the repository completion validator passes and all required evidence agrees. `V1-HANDOFF-READY`, `REFERENCE-V2-SOURCE-COMPLETE`, `PENDING`, `IN-RESEARCH`, `BLOCKED_EXTERNAL` or one finished family is not overall completion.

## 23. Stop and blocker rules

Voluntary stop only when the whole approved scope passes strict completion; or every remaining item is genuinely externally blocked and no independent executable work remains; or the platform/safety/capability environment forcibly prevents continuation. Blockers are not completion. Persist the exact resume state whenever writes are still available.