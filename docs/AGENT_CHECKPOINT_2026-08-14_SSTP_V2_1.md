# Agent Checkpoint — 2026-08-14 — SSTP/MS-SSTP v2 slice 1

Work unit: `SSTP-MS-SSTP-COMPLETE-REFERENCE-V2`

Entry: 011 SSTP / MS-SSTP

State: `REFERENCE-V2-EVIDENCE-COMPLETE / EXACT-LINUX-CLIENT-PIN-RESIDUAL / WINDOWS+INTEROP-EXECUTION-BLOCKED / NOT IMPLEMENTED`.

## Completed

- all 11 mandatory v2 files under `research/upstreams/classic-tunnels-family/sstp-reference-v2/`;
- Microsoft MS-SSTP/Windows native/RRAS authority separated from open-source implementation evidence;
- SoftEther server interoperability/reference path documented using existing exact source pin;
- canonical Linux `sstp-client` project identified without inventing an immutable SHA;
- TLS, SSTP crypto/channel binding, PPP/EAP and proxy credentials separated;
- TCP443/TLS/SSTP/PPP connection sequence and data path documented;
- server/client install/UI matrices, deployment topologies, supply-chain, lifecycle and migration guidance documented;
- all 16 v2 reference categories reconciled;
- final `REFERENCE_INDEX.md` synchronized;
- `AGENTS_HANDOFF_2026-08-14_SSTP_V2_1.md` created.

## Checks

- mandatory file categories: PASS at reference layer;
- 16 v2 categories: PASS, with one explicit immutable Linux-client source-freeze residual;
- strict `COMPLETE-REFERENCE-v2`: NOT PASS;
- implementation/support: NOT CLAIMED.

## Residual/source blocker

Exact maintained `sstp-client` release/commit SHA + root/component license still needs to be materialized before implementation/source freeze. Do not fabricate it and do not redo the rest of the SSTP dossier when it is found.

## External blockers

Windows RRAS/native client, SoftEther/Linux interop, certificate/crypto-binding/auth/proxy/load-balancer/IPv6/performance/MTU/routes-DNS/lifecycle and mobile/mac engine evidence remain runtime gates.

## Active task after checkpoint

`PPTP-COMPLETE-REFERENCE-V2`

Entry 012.

Resume from `research/protocols/012-pptp/V1_RESEARCH.md` plus existing classic-tunnels evidence. Preserve PPTP's obsolete security status and separate TCP1723 control, GRE protocol47 data path, PPP auth and MPPE/security.
