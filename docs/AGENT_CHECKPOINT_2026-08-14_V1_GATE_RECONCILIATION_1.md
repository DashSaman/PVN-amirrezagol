# PVNetwork Agent Checkpoint — V1 Gate Reconciliation 1

Date: 2026-08-14

Active campaign: `COMPLETE-RESEARCH-v1`

Hard phase lock: **ENABLED** until all 93 numbered entries are `COMPLETE-RESEARCH-v1`.

## What changed

This run is the first fresh scheduled run operating under the corrected V1-before-V2 rule. It did not continue the old V2-only progression.

Two mature original-v1 entries were formally reconciled against all 20 checks in `research/PROTOCOL_RESEARCH_TEMPLATE.md` and promoted only after traceable evidence or evidence-backed N/A/uncertainty was recorded:

1. **013 SoftEther VPN Protocol** → `COMPLETE-RESEARCH-v1`
2. **014 EtherIP** → `COMPLETE-RESEARCH-v1`

Owner-visible strict V1 completion therefore moved from **0/93 to 2/93**.

## Entry 013 — SoftEther

Primary new evidence:

- `research/protocols/013-softether/V1_GATE_RECONCILIATION.md`
- synchronized `V1_RESEARCH.md` and entry README
- tracker promotion in `research/RESEARCH_COMPLETENESS.md`

Important closure findings:

- canonical source-analysis pin remains `SoftEtherVPN/SoftEtherVPN@b1f7ef00040786d00bfa06c27fa463d106851e0c`;
- current upstream head was refreshed to `28564dd1886c5c5b6264ba07557498783311b3ca`;
- current Developer release remains `5.2.5188`, while Windows assets identify build `5.02.5187`; tag/artifact identity is kept separate;
- official Stable sibling latest observed commit is `ed17437af9719ac66acab30faa29e375d613c35f` (`v4.44-9807-rtm`);
- pinned `Client.c` source closed the native credential-persistence research question: hashed-password, encrypted plain-password, certificate/private-key, and secure-device modes are represented separately;
- unresolved production-safe release/advisory selection remains an implementation/release blocker, not a hidden research gate.

Research commits:

- `564576c42820f9507c40d9d694b050bebab62e10` — 20-gate reconciliation
- `1a9911fc4f20b61c03a25c395c49f24cb5ab2364` — V1 decision promotion
- `c1c86e6fdab7cc2c0805de3aa0eb7c95b95c8ce6` — entry index sync
- `ba02454d1a6d980bddec72cc97d4b150e2b0d5f7` — master tracker promotion

## Entry 014 — EtherIP

Primary new evidence:

- `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md`
- synchronized `V1_RESEARCH.md` and entry README
- tracker promotion in `research/RESEARCH_COMPLETENESS.md`

Canonical/reference set:

- RFC 3378 (`rfc-editor.org`) as the protocol authority;
- SoftEther `Proto_EtherIP.c` at the pinned family source revision;
- OpenBSD `etherip(4)` as a current native infrastructure implementation reference;
- FreeBSD `gif(4)` + bridge EtherIP support as another OS-native reference.

The v1 reconciliation explicitly uses `N/A-CONSUMER / PEER-MAPPED` for app/Store/menu categories that do not apply to a Layer-2 infrastructure protocol. It does not invent a mobile consumer client. Raw EtherIP remains classified as not encrypted by itself; IPsec protection belongs to entry 015/common IPsec evidence.

Research commits:

- `a953311f29331305973d6218afe37c7bae727187` — 20-gate reconciliation
- `009fff17e9f753e88684657500af5bedb00e7ea9` — V1 decision promotion
- `62aec24a519b0744c3b1c81e9758a3364cef2942` — entry index sync
- `286ab31e46bd8f02af636c256d2f892a37333637` — master tracker promotion

## Validation / no-fake-completion

- No runtime, device, packet-capture, Store, interoperability or production receipt was fabricated.
- V2 tracker was not promoted and V2 is not the active campaign.
- `COMPLETE-RESEARCH-v1` is used only for the original research contract.
- Overall project completion is **not** claimed.

## Next active work

Entry **015 — EtherIP/IPsec** is next because it shares the now-reconciled SoftEther/EtherIP evidence and has a mature `V1-HANDOFF-READY` dossier.

Exact next action:

1. reconcile entry 015 against all 20 original-v1 gates;
2. reuse entry 014 EtherIP evidence without conflating raw EtherIP with IPsec protection;
3. reuse the pinned SoftEther `Proto_IPsec.c` / `Proto_IKE.c` evidence and the existing strongSwan/IPsec family research where semantics match;
4. document the exact security/control-plane ownership as a composition rather than a generic “encrypted EtherIP” label;
5. promote entry 015 only if every applicable research gate passes; otherwise persist the precise research gap and continue the next mature V1 entry.
