# AGENTS.md — PVNetwork AI / Developer Operating Rules

This file defines mandatory operating rules for all AI agents and human developers working on `DashSaman/PVN-amirrezagol`.

## 1. Mandatory startup order

Before meaningful repository work, read:

1. `AI_START_HERE.md`
2. this `AGENTS.md`
3. `AGENT_EXECUTION_CONTRACT.md`
4. `docs/AGENT_RUN_STATE.json`
5. `research/RESEARCH_COMPLETENESS.md`
6. `research/REFERENCE_V2_COMPLETENESS.md`
7. the current handoff named by `docs/AGENT_RUN_STATE.json`
8. `docs/PROJECT_STATE.md`
9. `docs/AGENT_CHECKPOINT_LOG.md` and newer dated checkpoints
10. relevant numbered/shared research dossiers
11. recent Git history and the actual repository tree

Repository evidence wins over chat memory. If state, handoff, tracker, or old documentation disagree, the authoritative tracker plus newest committed evidence wins; reconcile stale pointers instead of following them blindly.

## 2. Current phase and hard phase lock

The repository is in **research / requirements / architecture**.

The active campaign is **COMPLETE-RESEARCH-v1** until all 93 numbered entries are `COMPLETE-RESEARCH-v1` in `research/RESEARCH_COMPLETENESS.md`.

While V1 is below 93/93:

- do **not** make `COMPLETE-REFERENCE-v2` the active campaign;
- do **not** choose V2-only work instead of unfinished V1 work;
- V2 evidence may be preserved incidentally, but it must not displace V1 completion;
- missing runtime/device/Store/container/interoperability/certification receipts are not hidden V1 research gates unless the written V1 research contract explicitly requires that exact evidence.

Only after V1 reaches 93/93 may the active campaign switch to `COMPLETE-REFERENCE-v2`.

## 3. Research completion is not implementation certification

Keep these evidence states separate:

1. researched;
2. candidate identified;
3. license reviewed;
4. architecture approved;
5. implemented;
6. builds;
7. unit tested;
8. integration tested;
9. E2E tested;
10. real-device tested;
11. Store verified;
12. production verified.

`COMPLETE-RESEARCH-v1` and `COMPLETE-REFERENCE-v2` are research/reference completion states. They do not by themselves mean implemented, certified, Store-ready, or production-ready.

## 4. Exact V1 gate rule

For every numbered V1 entry, evaluate the exact 20 checklist items in `research/PROTOCOL_RESEARCH_TEMPLATE.md`.

Every PASS must map to:

- canonical documentation/specification;
- canonical upstream source/repository;
- exact release/tag/commit pin where applicable;
- authoritative vendor/platform documentation where source is proprietary;
- or an evidence-backed `N/A` treatment.

Generic prose is not evidence. Never invent citations, source pins, release facts, runtime receipts, licenses, capabilities, or completion.

If all applicable research gates are evidence-backed and uncertainties are explicitly preserved, update the tracker to `COMPLETE-RESEARCH-v1` immediately.

If a real research gate is missing, work that exact gap. If one entry is blocked, record the blocker and continue another independent unfinished V1 entry.

## 5. Speed + accuracy rule

The owner wants maximum useful throughput without lowering evidence quality.

Inside a run:

- continue entry after entry while capacity remains;
- batch mature entries when shared family evidence legitimately applies;
- keep entry-specific conclusions and limitations explicit;
- reuse already-pinned evidence instead of re-researching identical facts;
- avoid one-commit-per-sentence churn;
- use meaningful batched research commits;
- update the authoritative tracker as soon as one or more entries genuinely complete;
- do not voluntarily stop after one successful entry or one family.

## 6. Machine state and dashboard clarity

`docs/AGENT_RUN_STATE.json` must stay synchronized with the authoritative V1 tracker.

Before ending a work slice:

- `v1_complete_count` must equal the actual count in `research/RESEARCH_COMPLETENESS.md`;
- `active_phase` must remain `COMPLETE-RESEARCH-v1` until 93/93;
- `current_entry` must identify the first unfinished numbered V1 entry;
- `exact_next_action` must name that real entry;
- the handoff pointer must point to the newest relevant handoff.

The dashboard independently derives the first unfinished entry from the tracker so stale state must never be used to fake progress.

## 7. Scheduled-run log contract

Scheduled ChatGPT continuation runs use `docs/AUTOMATION_RUN_LOG.md`.

- write one `RUN_START` before long scheduled research;
- do not write `RUN_END` until the scheduled slice is actually about to stop;
- never continue scheduled research after `RUN_END` without a new `RUN_START`;
- if a previous `RUN_START` is unmatched, record an inferred interruption and resume from repository state.

GitHub Actions watchdog runs are not ChatGPT scheduled runs and must not be counted as such.

## 8. Foreground / manual chat activity contract

Interactive ChatGPT work is separate from the scheduled automation.

When an interactive/manual chat begins making real repository changes, update `docs/FOREGROUND_ACTIVITY.json` with:

- `status: ACTIVE`;
- actor/task description;
- `started_at_utc`;
- current `heartbeat_at_utc`.

Refresh the heartbeat after meaningful work units if the interactive task lasts long enough that the previous heartbeat could become stale.

Before the interactive/manual repository task ends normally, set:

- `status: IDLE`;
- final `heartbeat_at_utc`;
- a short final task/result note.

This file exists only so the live dashboard can distinguish:

- scheduled automation running/waiting; and
- foreground/manual ChatGPT repository work running/idle.

Do not use foreground activity to fake research progress. Only tracker/evidence changes count as research completion.

## 9. Anti-loop rule

If the same approach fails twice, record the failure and materially change source/path/tool/granularity. Continue independent work instead of looping.

Do not keep an entry permanently PENDING because of a requirement that belongs to later implementation/certification rather than the written research contract.

## 10. Research source rules

For current/changing facts prefer official repositories, protocol/spec documentation, official vendor/platform/Store docs, release notes, source code, issue/PR history, RFCs and authoritative standards sources.

Pin revisions/releases when possible. Do not treat a mirror as canonical when upstream exists.

## 11. License and reuse

Open source is not automatically commercially reusable without obligations. Record component-specific licenses, dependencies, redistribution/linking obligations, trademark/branding constraints and Store implications.

Client, core, wrapper and server licenses are separate. Engineering review is not final legal sign-off.

## 12. Architecture rules

- Prefer a unified product layer plus stable Core/Platform Adapters.
- Do not couple UI directly to one engine.
- Do not implement cryptography from scratch.
- Keep imported payload, canonical profile, protected persistence, generated runtime config and transient state separate.
- Keep reusable credentials, remembered non-secret choices and transient session state separate.
- Platform-specific implementations belong behind shared product contracts.
- Support/certification is exact capability/version/evidence based, not a Boolean marketing flag.

## 13. Platform scope

Long-term targets include Android phones/tablets/foldables, Android TV/Google TV, Windows, macOS, iPhone/iPad and Linux.

Infrastructure-only technologies must not be forced into consumer-client UX when the role is not applicable. Use evidence-backed infrastructure/peer treatment instead.

## 14. Store rule

Store policies change. Re-check current official Google Play/TV, Apple App Store/Mac, Microsoft Store and Linux package-channel requirements before implementation/release decisions.

## 15. Competitor/upstream learning

Study mature clients, cores, servers, installers and panels for architecture, UI/menu/state, storage, routing/DNS, reconnect, permissions, crash recovery, packaging, updates, security and regressions.

Do not copy incompatible code, assets, branding or license-restricted material.

## 16. V2 layer after V1 unlocks

After V1 reaches 93/93, execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` for applicable entries and evaluate the exact 16 V2 gates.

V2 still represents reference/research completion, not implementation/certification. Runtime/device/Store evidence must not become an undocumented hidden V2 completion gate.

## 17. Persistent memory and handoff

Important discoveries must be committed. Use:

- `research/protocols/` and `research/upstreams/` for durable evidence;
- `research/RESEARCH_COMPLETENESS.md` as V1 completion source of truth;
- `research/REFERENCE_V2_COMPLETENESS.md` as V2 completion source of truth;
- `docs/AGENT_RUN_STATE.json` for machine-readable current task;
- checkpoint files/log for interruption recovery;
- `AGENTS_HANDOFF_*.md` for exact technical resume context;
- `docs/FOREGROUND_ACTIVITY.json` for interactive dashboard activity only.

Do not leave important technical decisions only in chat.

## 18. Current authoritative next action

Do **not** trust a hard-coded entry number in this file if newer tracker/state commits exist.

At startup derive the next entry as:

> the first numbered row in `research/RESEARCH_COMPLETENESS.md` whose base state is not `COMPLETE-RESEARCH-v1`.

Then confirm against `docs/AGENT_RUN_STATE.json` and newest handoff. If they disagree, reconcile stale state from the tracker before continuing.

At the time this rule was refreshed, V1 had progressed through entry 026 and the machine state had advanced to entry 027 SonicWall Global VPN / IPsec. Always re-read the tracker before acting.

## 19. Continuous execution

Normal loop:

**read authoritative state -> research exact gap -> persist evidence -> promote tracker if justified -> sync state -> checkpoint/handoff -> next entry -> continue**

Do not ask the owner to say “continue” between known work units. A platform interruption is not completion.

## 20. Strict no-fake-completion gate

No overall `DONE`/`COMPLETE` claim unless the repository completion validator passes and all required tracker/evidence states agree.

`V1-HANDOFF-READY`, `REFERENCE-V2-SOURCE-COMPLETE`, `PENDING`, `IN-RESEARCH`, `EVIDENCE-GAPS`, `BLOCKED_EXTERNAL`, or one completed family is not overall completion.

## 21. Stop/blocker rules

Voluntary stop only when:

- the approved scope genuinely passes strict completion; or
- every remaining item is externally blocked and no independent executable work remains; or
- platform/safety/capability limits forcibly prevent continuation.

Blockers are not completion. Persist exact resume state whenever writes remain possible.
