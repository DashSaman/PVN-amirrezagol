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
9. the newest `docs/RESEARCH_CAMPAIGN_STATUS_*.md` snapshot
10. `research/RESEARCH_COMPLETENESS.md`
11. relevant numbered/shared dossiers
12. recent Git history and actual repository tree

Repository evidence wins over chat memory. Some trackers can lag newer commits because connector writes may be rejected.

## Priority order

### Priority 1 — original campaign

Continue source/client/core/license/architecture/issues/platform research toward the original `COMPLETE-RESEARCH-v1` gates across all 93 entries.

### Priority 2 — exhaustive full reference

After original v1 gates reach their intended state, execute:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

for the second `COMPLETE-REFERENCE-v2` layer: server implementations/installers, server/client OS install matrices, complete server/client UI/menu maps, cryptography, data path/wire flow, ports/transports/handshake and deployment topologies.

Do not let the second layer erase unfinished original research.

## Current active work unit — Xray / modern proxy

Machine-readable state:

`docs/AGENT_RUN_STATE.json`

Newest Xray status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_XRAY_V1_1.md`

Newest Xray handoff:

`AGENTS_HANDOFF_2026-08-14_XRAY_V1_1.md`

Active state:

`XRAY-MODERN-PROXY-V1-CLOSURE / IN_PROGRESS`

### Xray evidence now committed

Under `research/upstreams/xray-family/`:

- `SOURCE_ARCHITECTURE.md`
- `DEPENDENCIES_TESTS_RELEASES.md`
- `CONFIG_CAPABILITY_MODEL.md`
- `CLIENT_ECOSYSTEM.md`
- `ISSUE_RELEASE_LESSONS.md`
- `LIBXRAY_WRAPPER.md`
- existing `INDEX.md`

### Xray source baseline

- Xray-core current-main research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- pinned tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- root license: MPL-2.0
- latest stable release observed during research: `v26.3.27`
- current main is newer than stable and includes fixes not represented by the stable tag.

### libXray wrapper baseline

- pin: `d0ab60ae4dd91cf119c878152d12103e6f84b78a`
- wrapper root license: MIT
- wraps/depends on Xray-core; wrapper license does not change Xray-core MPL or dependency obligations.
- provisional classification: `STRONG-WRAPPER-CANDIDATE / LEGAL+PLATFORM+LIFECYCLE REVIEW REQUIRED`.

### Xray architecture conclusions so far

- Xray-core is a modular networking runtime, not one protocol.
- Protocol, transport, security/flow and routing/DNS must remain separate product capabilities.
- PVNetwork canonical profile storage must remain independent from raw/generated Xray runtime configuration.
- Core-version-aware validation/migration is mandatory because current source contains active/deprecated/removed configuration semantics.
- Exact per-build/per-platform SBOM is mandatory; root MPL is not the whole dependency/license answer.
- Cross-platform wrapper availability does not remove Android VpnService, Apple NetworkExtension, Windows service/TUN or Linux route/DNS lifecycle responsibilities.

### Current Xray numbered relationship

At minimum active related entries include:

- 037 VLESS
- 038 VMess
- 039 Trojan
- 040 Shadowsocks
- 074 REALITY
- 075 XTLS
- 076 XTLS Vision
- 084 WebSocket
- 086 HTTP/2-related transport classification
- 088 gRPC
- 089 mKCP
- 091 XHTTP
- 092 RAW

These are a mix of protocol, security/flow and transport concepts. Do not advertise all as standalone VPN protocols.

## OpenConnect / Enterprise

Shared family state:

`V1-HANDOFF-READY / NOT IMPLEMENTED`

Latest detailed status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

Latest family handoff:

`AGENTS_HANDOFF_2026-08-14_OPENCONNECT_V1_CLOSURE_2.md`

Residual gaps remain explicit but no longer justify keeping OpenConnect as the only active family.

## WireGuard / AmneziaWG

Materially researched but not `COMPLETE-RESEARCH-v1`. Deep evidence exists for source pins, core architecture, Android/Apple, AmneziaWG compatibility/versioning and issue-derived regressions. Residual v1 gaps must be revisited before overall original-campaign completion.

## Current product architecture direction

- product-owned stable Core Adapter contracts;
- UI/business state independent from engine internals;
- no custom cryptographic primitive implementation when mature engines exist;
- separate import/export, canonical profile, protected persistence and runtime config;
- separate reusable secrets, non-secret remembered choices and transient session values;
- platform-native network lifecycle behind shared product contracts;
- evidence/version/capability-based support status instead of Boolean marketing flags.

## No false completion

There is still no production PVNetwork app, build/package, product test suite, E2E proof, real-device certification or Store approval.

No family research milestone means production protocol support.

## Tooling-path note

A local checkout/Python execution attempt for `scripts/agent_state.py verify/build/next` failed because the current container could not resolve `github.com`. GitHub connector research/write access is working.

Do not retry the same clone path unchanged in this runtime. This is not a project blocker.

## Exact next action

Continue Xray original-v1 closure:

1. create per-entry capability/support/reuse decisions;
2. map Xray commander/API/stats/control ownership;
3. deepen libXray lifecycle/API/dependency/issue evidence;
4. expand v2rayNG Android source/storage/VpnService/import/menu evidence in split files;
5. add Xray security/dependency-advisory review;
6. synchronize Xray `INDEX.md` and affected numbered entries;
7. checkpoint and continue next unfinished original-v1 family without owner prompting;
8. keep WireGuard/AmneziaWG residual v1 closure queued before any overall v1 completion claim.
