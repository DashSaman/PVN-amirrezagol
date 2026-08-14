# PVNetwork Agent Handoff — V1 Gate Reconciliation 2

Date: 2026-08-14

## Authoritative execution state

- Active campaign: **`COMPLETE-RESEARCH-v1`**
- Strict V1 completion: **4 / 93**
- V2 phase lock: **ENABLED until V1 is 93/93**
- Overall project: **NOT COMPLETE**

This handoff supersedes older V2-active handoffs for execution priority. Preserve their research evidence, but do not make V2 the active campaign.

## Newly complete original-v1 entries

### 011 SSTP / MS-SSTP

Files:

- `research/protocols/011-sstp/V1_GATE_RECONCILIATION.md`
- `research/protocols/011-sstp/V1_RESEARCH.md`
- `research/upstreams/classic-tunnels-family/SSTP_CLIENT_SOURCE_PIN_2026-08-14.md`

Linux source identity was corrected to canonical GitLab `sstp-project/sstp-client`, research tag `1.0.20`. Windows remains native-first; Linux remains an explicit GPL client/backend candidate; unsupported mobile/macOS paths are not invented.

### 013 SoftEther VPN Protocol

Files:

- `research/protocols/013-softether/V1_GATE_RECONCILIATION.md`
- `research/protocols/013-softether/V1_RESEARCH.md`

The native source/client/service/config/secret/CI/security/reuse evidence now satisfies the original 20-gate research template. Implementation/release/device evidence remains separate.

### 014 EtherIP

Files:

- `research/protocols/014-etherip/V1_GATE_RECONCILIATION.md`
- `research/protocols/014-etherip/V1_RESEARCH.md`

Canonical RFC 3378 plus SoftEther/OpenBSD/FreeBSD infrastructure references were reconciled. Consumer app/Store/UI categories use evidence-backed `N/A-CONSUMER / PEER-MAPPED`. Raw EtherIP remains explicitly unencrypted by itself.

### 015 EtherIP/IPsec

Files:

- `research/protocols/015-etherip-ipsec/V1_GATE_RECONCILIATION.md`
- `research/protocols/015-etherip-ipsec/V1_RESEARCH.md`

The entry is modeled as EtherIP encapsulation composed with a typed IPsec/IKE security layer. Pinned SoftEther `Proto_EtherIP.c`, `Proto_IPsec.c` and `Proto_IKE.c` establish one concrete composed implementation; RFC/IPsec-family evidence supplies the protocol security boundaries. No generic “secure EtherIP” flag is allowed.

## Tracker truth

`research/RESEARCH_COMPLETENESS.md` now has exactly four `COMPLETE-RESEARCH-v1` entries: **011, 013, 014, 015**.

Do not infer V2 progress from the existence of earlier v2 dossiers. `research/REFERENCE_V2_COMPLETENESS.md` remains the strict V2 tracker and V2 is locked.

## Exact next action

Work on **012 PPTP** next because its detailed later-layer research already exists and can be reconciled efficiently without new V2 expansion.

Required sequence:

1. read `research/protocols/012-pptp/V1_RESEARCH.md` plus the existing PPTP reference-v2 dossier;
2. reconcile the exact 20 original-v1 gates;
3. keep protocol age/security limitations explicit and do not market it as a modern secure default;
4. separate Windows/native/vendor compatibility, open-source implementation evidence and platform support;
5. use evidence-backed N/A for unsupported consumer/platform roles rather than inventing support;
6. missing runtime/device/interoperability proof is not a hidden research gate;
7. if all research gates pass, promote 012 and update the tracker in the same work unit;
8. otherwise persist the precise research gap and continue another mature V1 entry;
9. never switch active phase to V2 before 93/93 V1.

## Logging rule

A valid `RUN_START` exists for the current run. Append `RUN_END` only when actually ending. The next invocation must create its own RUN_START before continuing.
