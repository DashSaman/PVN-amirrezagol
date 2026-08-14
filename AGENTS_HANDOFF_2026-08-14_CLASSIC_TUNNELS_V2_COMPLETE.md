# PVNetwork Agent Handoff — Classic Tunnels V2 Complete (008–013)

Date: 2026-08-14 UTC

Authoritative phase: `COMPLETE-REFERENCE-v2`; V1 remains 93/93.

## Completed in this checkpoint

- 008 — L2TP/IPsec
- 009 — L2TPv3
- 010 — L2TPv3/IPsec
- 011 — SSTP / MS-SSTP
- 012 — PPTP
- 013 — SoftEther VPN Protocol

Each numbered dossier now contains an entry-specific V2 reconciliation. Detailed evidence is reused from the dedicated classic-tunnels `*-reference-v2` directories; all exact 16 gates are independently reconciled.

Governance reconciliation: the older family gate files frequently withheld tracker promotion for live install/device/interoperability/packet/runtime receipts. Under the current `FULL_PROTOCOL_REFERENCE_CONTRACT.md` and `AGENTS.md` §16 those are later certification evidence, not hidden V2 research gates. No runtime receipt has been invented.

Specific non-runtime gaps were handled rather than ignored:

- SSTP: the old Linux-client source-pin residual is closed at research source/tag level by `SSTP_CLIENT_SOURCE_PIN_2026-08-14.md`: canonical `sstp-project/sstp-client`, tag `1.0.20`, short identifier `dd243124`, GPLv2+; full object/archive digest remains implementation/SBOM freeze work.
- PPTP: active current server/interoperability reference `accel-ppp` is pinned at `4f562467dbdf819395e138617c2a057e02595b9e`, GPL-2.0. Historical pptpd/pptp-client code is explicitly non-selected because PPTP is obsolete/insecure; an exact source freeze becomes applicable only if a future isolated legacy lab deliberately selects such a project.

## Exact continuation state

V2: **13/93**.

Next unfinished V2 entry: **014 — EtherIP**.

Exact next action: reconcile EtherIP against all exact 16 V2 gates, reusing SoftEther/native platform evidence only where directly applicable, preserve infrastructure/peer semantics and evidence-backed N/A rather than inventing consumer UI, then continue 015 EtherIP/IPsec.
