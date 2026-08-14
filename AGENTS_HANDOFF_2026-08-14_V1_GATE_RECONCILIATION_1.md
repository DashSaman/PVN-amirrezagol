# PVNetwork Agent Handoff — V1 Gate Reconciliation 1

Date: 2026-08-14

## Mandatory phase truth

Active campaign: **`COMPLETE-RESEARCH-v1`**.

Strict V1 tracker: **2 / 93 COMPLETE-RESEARCH-v1**.

V2 phase lock: **ENABLED**. Do not make `COMPLETE-REFERENCE-v2` active until V1 reaches 93/93.

This handoff supersedes old V2-active handoffs for execution priority. Existing V2 evidence remains useful and must not be deleted, but it is incidental until the V1 tracker is complete.

## Completed in the latest fresh run

### 013 — SoftEther VPN Protocol

Promoted to `COMPLETE-RESEARCH-v1` only after a formal 20-gate audit.

Primary files:

- `research/protocols/013-softether/V1_GATE_RECONCILIATION.md`
- `research/protocols/013-softether/V1_RESEARCH.md`
- `research/protocols/013-softether/README.md`

Important commits:

- `564576c42820f9507c40d9d694b050bebab62e10`
- `1a9911fc4f20b61c03a25c395c49f24cb5ab2364`
- `c1c86e6fdab7cc2c0805de3aa0eb7c95b95c8ce6`
- tracker promotion `ba02454d1a6d980bddec72cc97d4b150e2b0d5f7`

No production-safe SoftEther release was claimed. Current high-severity advisory/release selection, exact build SBOM, mobile runtime, Store/device/performance proof remain separate implementation/release evidence.

### 014 — EtherIP

Promoted to `COMPLETE-RESEARCH-v1` after a formal infrastructure-aware 20-gate audit.

Primary files:

- `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md`
- `research/protocols/014-etherip/V1_RESEARCH.md`
- `research/protocols/014-etherip/README.md`

Canonical/reference set:

- RFC 3378;
- SoftEther `Proto_EtherIP.c` at `b1f7ef00040786d00bfa06c27fa463d106851e0c`;
- OpenBSD `etherip(4)`;
- FreeBSD `gif(4)`/bridge EtherIP.

Important commits:

- `a953311f29331305973d6218afe37c7bae727187`
- `009fff17e9f753e88684657500af5bedb00e7ea9`
- `62aec24a519b0744c3b1c81e9758a3364cef2942`
- tracker promotion `286ab31e46bd8f02af636c256d2f892a37333637`

Raw EtherIP remains explicitly **not encrypted by itself**. Consumer mobile/app UI gates were handled as evidence-backed `N/A-CONSUMER / PEER-MAPPED`; no fake app/support claim was created.

## Current active work — 015 EtherIP/IPsec

Entry 015 is `V1-HANDOFF-READY / NOT IMPLEMENTED` and is the next closure candidate because it shares mature source evidence with entries 014 and the existing IPsec family.

Existing numbered file:

- `research/protocols/015-etherip-ipsec/V1_RESEARCH.md`

High-value shared evidence:

- `research/upstreams/softether-family/SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md`
- pinned `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c/src/Cedar/Proto_EtherIP.c`
- pinned `.../src/Cedar/Proto_IPsec.c`
- pinned `.../src/Cedar/Proto_IKE.c`
- existing `research/upstreams/strongswan-family/` and numbered IKE/IPsec entries where semantics match
- completed entry 014 EtherIP research

Pinned `Proto_IPsec.c` proves that SoftEther service enablement treats `EtherIP_IPsec` and `L2TP_IPsec` as IPsec-using services, owns IKE/ESP packet dispatch, handles UDP-encapsulated/raw ESP paths, and has OS-service/kernel-ESP ownership logic. Preserve this as implementation-specific evidence; do not generalize one SoftEther profile to all IPsec implementations.

## Exact next sequence

1. Read entry 015 plus relevant SoftEther and IPsec-family research.
2. Reconcile all 20 V1 gates explicitly.
3. Model entry 015 as **EtherIP encapsulation + IKE/IPsec protection**, not as a new monolithic crypto protocol.
4. Keep raw EtherIP properties inherited from entry 014 separate from IPsec cryptographic/authentication properties.
5. Use evidence-backed infrastructure/N-A treatment for consumer-app UI/Store fields where appropriate.
6. Do not require live router/packet/interoperability receipts as hidden research gates.
7. If all research gates pass, update entry docs and `research/RESEARCH_COMPLETENESS.md` in the same work unit.
8. If a true research evidence gap remains, record it precisely and move to the next mature V1 entry rather than looping.
9. Keep `docs/AGENT_RUN_STATE.json.active_phase = COMPLETE-RESEARCH-v1` until tracker is 93/93.

## Logging discipline

The current run has a valid `RUN_START`. Do not write `RUN_END` until actually ending the run. Never continue research after `RUN_END` without a new `RUN_START`.

## Completion warning

2/93 V1 entries are complete. Overall V1, V2, implementation, certification and product completion are all **not complete**.
