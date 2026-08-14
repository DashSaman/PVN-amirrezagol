# AGENTS HANDOFF — V1 Gate Reconciliation 4

Date: 2026-08-14

## State to resume

- Campaign: `COMPLETE-RESEARCH-v1`
- Complete: **15 / 93**
- V2 lock: enabled until 93/93
- Current entry: **016 — Cisco AnyConnect**
- Work unit: `V1-GATE-RECONCILIATION`

## What was completed

This slice promoted entries 001-007 after exact original-v1 gate reconciliation. Combined with entries 008-015 from prior slices, entries **001 through 015 are now COMPLETE-RESEARCH-v1**.

Do not reopen these entries merely because implementation/device/Store/interoperability receipts are absent. Those are separate evidence states unless a future research-source change exposes a real original-v1 gap.

## Key fresh pins

- OpenVPN3 current release reference: `release/3.11.7` -> `18edfae7e7fd8051c93bd4746ec69be91eb02dbb`
- strongSwan current reviewed baseline: `6.0.7` -> `5973ff8e41deef4e015e1138a2de688acedf6f75`

## Exact continuation

1. Read `research/upstreams/openconnect-family/` and entry 016 dossier.
2. Audit entry 016 against every original-v1 completion item in `research/PROTOCOL_RESEARCH_TEMPLATE.md`.
3. Keep Cisco proprietary client/gateway behavior separate from public OpenConnect/ocserv source; never invent proprietary source visibility.
4. Promote only when all applicable original research gates have traceable evidence or evidence-backed N/A treatment.
5. Continue entries 017-025 in matrix order when the shared evidence supports them.
6. Runtime/device/Store/interoperability evidence remains implementation/certification unless the written research gate explicitly requires it.
7. Update tracker and machine state after each meaningful closure.
8. Keep `COMPLETE-REFERENCE-v2` inactive until the V1 tracker reaches 93/93.

## Primary checkpoint

`docs/AGENT_CHECKPOINT_2026-08-14_V1_GATE_RECONCILIATION_4.md`
