# PVNetwork Continuous Agent Execution Contract

This file is a **mandatory execution contract** for every AI/coding/research agent working on `DashSaman/PVN-amirrezagol`.

Its purpose is to prevent voluntary early stopping, lost context, fake completion, and repeated work during long research/engineering campaigns.

## 1. Core rule: continue until the gates pass

When the owner has already supplied the task/scope, the agent MUST continue executing the documented backlog without waiting for the owner to say “continue”.

The agent MUST NOT voluntarily stop merely because:

- a work unit was completed;
- the response/context is getting long;
- one source/tool/path failed;
- one protocol/family was completed;
- a partial result looks useful;
- the next action is already known;
- the owner is temporarily absent.

Within an active agent run, immediately take the next executable required task from `docs/AGENT_RUN_STATE.json` and continue.

A platform-enforced interruption, context limit, revoked tool access, safety restriction, or genuinely external dependency can interrupt execution. Those are **interruptions/blockers, not completion**. Persist the exact recovery state whenever the environment still allows a write.

## 2. No repeated confirmation for known scope

Do not ask the owner to repeat or reconfirm requirements already recorded in repository evidence.

If a non-critical detail is ambiguous, choose the safest evidence-preserving interpretation, record the assumption, and continue. Ask the owner only when a missing decision is genuinely impossible to infer and blocks all useful independent work.

## 3. Mandatory startup / resume algorithm

At the beginning of every run:

1. Read `AI_START_HERE.md`.
2. Read `AGENTS.md`.
3. Read this file.
4. Read `docs/AGENT_RUN_STATE.json`.
5. Read the handoff pointed to by `AGENTS.md`.
6. Read the project/research state required by those files.
7. Inspect recent Git history and the actual repository tree.
8. Run `python scripts/verify_agent_state.py` when a Python runtime is available.
9. Resume `active_task_id`; if it is already PASS, choose the first required non-PASS task in dependency order.
10. Start real work immediately. Do not return only a plan.

Repository evidence wins over chat memory.

## 4. Persistent task ledger is mandatory

`docs/AGENT_RUN_STATE.json` is the machine-readable execution ledger.

For every required task it must preserve:

- stable task ID;
- title/scope;
- dependency information where needed;
- status;
- acceptance criteria;
- evidence paths/commits when completed;
- blocker details when blocked;
- exact next action.

Allowed task states:

- `PENDING`
- `IN_PROGRESS`
- `PASS`
- `FAILED_RETRYABLE`
- `BLOCKED_EXTERNAL`

`PASS` means the task's acceptance criteria were actually satisfied with repository evidence. It does not mean “researched a little” or “attempted”.

## 5. Checkpoint after every meaningful work unit

After every meaningful work unit, and before every voluntary handoff/termination:

1. commit/update the technical evidence produced;
2. update `docs/AGENT_RUN_STATE.json`;
3. append a concise entry to `docs/AGENT_CHECKPOINT_LOG.md`;
4. update the relevant project/research tracker or dated status snapshot;
5. update/create the newest `AGENTS_HANDOFF_*.md` when the recovery point materially changed;
6. update the Latest handoff pointer in `AGENTS.md` when a new handoff is created;
7. continue to the next executable task.

Do not log every HTTP/API/tool call. Log every **meaningful unit of work, decision, failure class, accepted evidence set, and state transition**.

## 6. Exact checkpoint content

A checkpoint entry must be sufficient for a fresh agent with no chat history to continue. Record:

- timestamp/date;
- task ID;
- what changed;
- files/commits created or updated;
- tests/checks run and PASS/FAIL result;
- failed approaches that must not be repeated;
- blockers and why they are external;
- active task after the checkpoint;
- exact next command/research action when known.

## 7. Anti-loop and failure handling

If an approach fails twice, do not repeat it unchanged a third time.

Instead:

1. record both failures;
2. change source/path/tool/granularity/strategy materially;
3. continue other independent tasks when possible;
4. mark `BLOCKED_EXTERNAL` only when no repository-controlled workaround remains.

A blocker on one task never justifies idling while other required tasks are executable.

## 8. No fake completion

The agent MUST NOT set top-level `run_status` to `COMPLETE` unless all required tasks in the active owner-approved scope are `PASS`.

`BLOCKED_EXTERNAL`, `FAILED_RETRYABLE`, `PENDING`, and `IN_PROGRESS` are all non-complete states.

Before declaring completion, run:

```bash
python scripts/verify_agent_state.py --require-complete
```

The command must exit successfully. Then independently verify that the relevant research/test/build gates required by `AGENTS.md`, project contracts, and task acceptance criteria are PASS.

If the validator or any required gate fails, completion is forbidden. Fix the gap or keep the run non-complete.

## 9. Scope progression rule

Current repository priority remains the documented project priority, not whatever is easiest.

For the current PVNetwork campaign:

1. finish the active `COMPLETE-RESEARCH-v1` work and its remaining OpenConnect/Enterprise gaps;
2. continue the next highest-value unfinished original R1–R4 family/entry until the original research campaign gates are met;
3. then execute the mandatory `COMPLETE-REFERENCE-v2` expansion contract across the documented 93-entry scope;
4. do not silently promote the project into implementation/production states without explicit repository evidence and owner-approved phase progression.

Completing one protocol/family is a checkpoint, not permission to stop the overall campaign.

## 10. Long-run behavior

During large jobs, prefer small auditable commits and resumable work units. Do not accumulate a huge uncommitted state.

At the end of each work unit:

- checkpoint;
- verify state consistency;
- select next executable task;
- continue.

The default behavior is **work -> evidence -> checkpoint -> verify -> next task**, not **work -> wait for owner**.

## 11. What “stop” means

A voluntary stop is allowed only when one of these is true:

1. all required tasks are `PASS` and completion validation succeeds;
2. every remaining required task is `BLOCKED_EXTERNAL`, the blockers are documented with evidence, and there is no independent executable work left;
3. the platform forcibly ends the run or removes required capabilities;
4. a safety/policy restriction prevents continuation.

Cases 2–4 are not completion. Leave `run_status` non-complete and write the exact resume point whenever possible.

## 12. Owner-visible completion standard

A final “done” claim must cite repository evidence, including:

- final state ledger;
- completion validator result;
- relevant PASS gates/tests;
- final handoff/status file;
- commits containing the finished work.

Anything less is a progress report, not project completion.
