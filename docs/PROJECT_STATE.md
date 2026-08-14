# PVNetwork Project State

Last synchronized: 2026-08-14

## Repository

- Repository: `DashSaman/PVN-amirrezagol`
- Branch: `main`
- Product: **PVNetwork**
- Phase: **research / requirements / architecture**
- Production implementation: **not started**
- Research scope: **93 numbered entries** in `docs/PROTOCOL_MATRIX.md`
- Execution mode: **continuous until repository-defined gates pass or work is externally blocked**

## Recovery order

A new AI must read:

1. `AI_START_HERE.md`
2. `AGENTS.md`
3. `AGENT_EXECUTION_CONTRACT.md`
4. `docs/AGENT_RUN_STATE.json`
5. the newest `AGENTS_HANDOFF_*.md` pointed to by `AGENTS.md`
6. this file
7. `docs/AGENT_CHECKPOINT_LOG.md`
8. `docs/RESEARCH_LOG.md`
9. newest dated research-status snapshot
10. `research/RESEARCH_COMPLETENESS.md`
11. relevant numbered/shared dossiers
12. recent Git history and actual repository tree

Repository evidence wins over chat memory. Trackers may lag newer commits when connector writes are rejected.

## Priority order

### Priority 1 — original campaign

Continue the source/client/core/license/architecture/issues/platform campaign toward `COMPLETE-RESEARCH-v1` across all 93 entries.

### Priority 2 — exhaustive full reference

After original v1 gates reach their intended state, execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` for the mandatory `COMPLETE-REFERENCE-v2` layer: server implementations/installers, server/client install matrices, full UI/menu maps, cryptography, data path/wire flow, ports/transports/handshake and deployment topologies.

## Current active work unit — WireGuard / AmneziaWG

Machine state: `docs/AGENT_RUN_STATE.json`

Active ID:

`WIREGUARD-AMNEZIAWG-V1-CLOSURE / IN_PROGRESS`

Resume source:

`AGENTS_HANDOFF_2026-08-14_XRAY_V1_2.md`

### Current goal

Close the original-v1 gaps already identified for entries 002 WireGuard and 003 AmneziaWG:

- Windows architecture/storage/service evidence;
- dependency/SBOM/license distinctions;
- AmneziaWG platform/source/version evidence;
- major client/reference roles;
- issue/regression/release evidence;
- support/reuse decisions;
- numbered entry synchronization;
- shared family index/status/handoff.

## Xray / modern proxy

Shared family state:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

Latest closure status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_XRAY_V1_2.md`

Latest closure handoff:

`AGENTS_HANDOFF_2026-08-14_XRAY_V1_2.md`

Important preserved security finding: Xray advisory `GHSA-5wf9-h793-w73c` marks versions `>= v26.1.13` vulnerable and `>= v26.7.11` patched. The non-prerelease `releases/latest` value observed during research (`v26.3.27`) must not be selected merely because it is labeled latest.

## OpenConnect / Enterprise

Shared family state:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

Latest detailed status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

Latest family handoff:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

## WireGuard / AmneziaWG prior evidence

Material research already exists for:

- WireGuard Go core source/pin;
- Windows/Android/Apple official source pins;
- Android/Apple architecture;
- AmneziaWG Go source/versioning;
- issue-derived regression lessons;
- official client/platform references.

The family is not yet v1 handoff-ready and must be completed rather than restarted.

## Current product architecture direction

- product-owned stable Core Adapter contracts;
- UI/business state independent from engine internals;
- no custom cryptographic primitive implementation when mature engines exist;
- separate import/export, canonical profile, protected persistence and runtime config;
- separate reusable secrets, non-secret remembered choices and transient session values;
- platform-native network lifecycle behind shared product contracts;
- evidence/version/capability-based support state rather than Boolean marketing flags.

## No false completion

There is still no production PVNetwork app, build/package, product test suite, E2E proof, real-device certification or Store approval.

Family research handoff states do not equal product protocol support.

## Tooling-path note

A local checkout/Python execution attempt for `scripts/agent_state.py verify/build/next` failed because the current container could not resolve `github.com`. GitHub connector access is working. Do not retry the same clone path unchanged in this runtime.

## Exact next action

1. read actual current `research/upstreams/wireguard-family/` tree and prior WireGuard evidence;
2. close Windows/storage/service and dependency/SBOM gaps;
3. close AmneziaWG platform/version/source gaps;
4. create/refresh support/reuse decisions for 002/003;
5. synchronize family index and numbered dossiers;
6. checkpoint and, if v1 handoff-ready, immediately select the next unfinished original-v1 family without waiting for the owner;
7. do not begin mass `COMPLETE-REFERENCE-v2` yet.
