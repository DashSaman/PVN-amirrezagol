# PVNetwork Agent Gate Audit — 2026-08-14

## Why this audit exists

Owner-visible dashboard counters stayed unchanged while many research commits were being produced. A live repository audit found that real work was being persisted, but campaign progression logic had drifted away from the repository contracts.

## Finding 1 — real work is happening

The agent is not merely cycling on the same file. Recent research moved across distinct entries/families, including L2TP/IPsec, L2TPv3, L2TPv3/IPsec, SSTP/MS-SSTP, PPTP and SoftEther, with new reference indexes, implementation maps, cryptography/authentication notes, install/UI/data-path/topology files and gate reconciliations.

Therefore the problem is not absence of work; it is gate accounting and phase progression.

## Finding 2 — V2 became active before V1 completion

At audit time:

- `research/RESEARCH_COMPLETENESS.md` contained **0 / 93** entries marked `COMPLETE-RESEARCH-v1`.
- `docs/AGENT_RUN_STATE.json` nevertheless declared `active_phase = COMPLETE-REFERENCE-v2`.
- `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` explicitly says the existing/original research campaign remains first priority and V2 expansion follows prior research gates.
- `research/REFERENCE_V2_COMPLETENESS.md` explicitly says `COMPLETE-REFERENCE-v2` is allowed only after the original `COMPLETE-RESEARCH-v1` gate has passed.

This is a campaign-order violation. Until all required V1 gates are reconciled, V2 evidence may be captured opportunistically but must not replace the V1 completion campaign.

## Finding 3 — hidden runtime/certification gates were incorrectly preventing research completion

`research/PROTOCOL_RESEARCH_TEMPLATE.md` states that `COMPLETE-RESEARCH-v1` is **research completion, not implementation completion**.

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` states that `COMPLETE-REFERENCE-v2` is a **research/reference completion state** and still does not mean PVNetwork implementation or production certification.

However recent gate-reconciliation documents sometimes state that all 16 V2 research/reference categories are `REFERENCE-PASS`, then keep the tracker `PENDING` solely because live Linux/Cisco/device/container/interoperability/packet-capture/runtime receipts are unavailable.

That is stricter than the written V2 contract and creates a permanent-zero loop in environments that cannot execute those external runtime tests.

### Correct rule

- Never fabricate runtime evidence.
- Runtime/device/interoperability/production tests remain valuable implementation/certification evidence and must be recorded as future blockers/uncertainties where unavailable.
- They must **not** silently become extra completion gates for V1 or V2 unless the applicable research contract explicitly requires that exact execution proof.
- If every applicable checklist item in the written research contract has traceable evidence and uncertainties are explicit, the research tracker must be promoted accordingly.

## Finding 4 — tracker updates must be part of every gate reconciliation

Creating files does not move owner-visible counters. For every completed gate reconciliation, the agent must in the same work unit:

1. evaluate the exact contract checklist;
2. record PASS / N-A / unresolved evidence gap per gate;
3. update `research/RESEARCH_COMPLETENESS.md` or `research/REFERENCE_V2_COMPLETENESS.md` when the written gate is actually satisfied;
4. update `docs/AGENT_RUN_STATE.json` so `active_phase` matches tracker truth;
5. checkpoint and continue.

A dossier may remain non-complete when a **research-contract** requirement truly lacks evidence. It must not remain non-complete merely because implementation certification has not been performed.

## Finding 5 — evidence quality still matters

No counter may be increased from generic prose alone. A non-trivial factual claim used to satisfy a gate must have one of:

- canonical source URL/documentation;
- exact source/release/tag and preferably immutable commit pin where applicable;
- repository/source-tree evidence;
- explicit `UNVERIFIED` / `UNKNOWN` / uncertainty marker when evidence is not yet available.

Do not turn this correction into permissive or fake completion.

## Mandatory execution correction

Until V1 tracker truth reaches 93/93 `COMPLETE-RESEARCH-v1`:

- active campaign = `COMPLETE-RESEARCH-v1`;
- first task of each run = reconcile existing mature dossiers against the exact 20 V1 gates before expanding unrelated V2 work;
- prioritize converting already-researched entries from `IN-RESEARCH`, `EVIDENCE-GAPS`, or `SKELETON` to true `COMPLETE-RESEARCH-v1` where the written research gate is genuinely satisfied;
- if a V1 gate is not satisfied, identify the precise missing research evidence and work that gap;
- do not require hardware/device/runtime certification unless it is explicitly a V1 research-gate requirement.

Only after V1 reaches 93/93 may `COMPLETE-REFERENCE-v2` become the active campaign. Then apply the same exact-contract reconciliation to the 16 V2 gates.

## Audit example proving the issue

Entry 010 L2TPv3/IPsec gate reconciliation explicitly recorded all 16 research/reference categories as having traceable evidence and stated `REFERENCE-V2-SOURCE-COMPLETE`, but then kept strict V2 `PENDING` because of live runtime/interoperability/packet-capture requirements. Those execution requirements belong in certification/implementation residuals unless the written V2 checklist itself requires them.

## Automation correction

The scheduled `PVNetwork Overnight Run` prompt was updated on 2026-08-14 to enforce:

- V1-before-V2 campaign ordering;
- exact 20-gate V1 and 16-gate V2 reconciliation;
- separation of research completion from runtime/production certification;
- tracker update after meaningful gate completion;
- canonical/pinned evidence or explicit uncertainty;
- no fabricated completion;
- correct `RUN_START` / `RUN_END` ordering.

## Follow-up audit — first correction was not yet controlling the already-running slice

A second live audit found that the run already in progress when the automation prompt was changed continued using the old V2 state. After the first correction commit, it still created new V2 dossiers for SSTP, PPTP and SoftEther instead of returning to V1 reconciliation.

The durable repository state was also still stale:

- `docs/AGENT_RUN_STATE.json` still said `active_phase = COMPLETE-REFERENCE-v2` and pointed to L2TPv3/IPsec V2;
- `research/RESEARCH_COMPLETENESS.md` still had **0 / 93** `COMPLETE-RESEARCH-v1` entries;
- `research/REFERENCE_V2_COMPLETENESS.md` still had **0 / 93** `COMPLETE-REFERENCE-v2` entries;
- the automation ledger recorded `RUN_END` at `2026-08-14T12:12:30Z`, yet research commits continued after that time without a new `RUN_START`.

This proves the current/old run did not yet obey the corrected sequencing or logging contract.

### Hard corrective action

`docs/AGENT_RUN_STATE.json` was forcefully reset to:

- `active_phase = COMPLETE-RESEARCH-v1`;
- `active_work_unit = V1-GATE-RECONCILIATION`;
- a hard phase lock forbidding V2 from becoming active before V1 reaches 93/93;
- deterministic gate-by-gate reconciliation of existing mature V1 dossiers;
- no hidden runtime/certification gate.

Already-created V2 material is preserved as useful evidence and must not be deleted, but it is now secondary/incidental until V1 completes.

## Expected owner-visible effect

The dashboard percentages may remain unchanged briefly while existing dossiers are audited against the exact gates. After that reconciliation, counters should move only when a real research completion gate is satisfied. If the next fresh scheduled run still creates unrelated V2-only work while V1 remains below 93/93, treat that as a hard execution failure rather than legitimate progress.
