# PVNetwork Agent Checkpoint Log

This is the append-style recovery log for long-running AI/research work.

Do not use this file as a substitute for technical dossiers. Its job is to make interruption recovery deterministic.

## Required entry format

Each meaningful work unit records:

- Date/time if available
- Work-unit/task ID
- State transition (`PENDING`/`IN_PROGRESS`/`PASS`/`FAILED_RETRYABLE`/`BLOCKED_EXTERNAL`)
- What was actually completed
- Evidence files and commit SHA(s)
- Checks/tests and explicit PASS/FAIL
- Failed approaches that must not be repeated unchanged
- Blockers
- Exact next action

After recording a checkpoint, the active agent must continue to the next executable required task rather than waiting for the owner to say “continue”.

---

## 2026-08-14 — Continuous execution infrastructure

- Work unit: `AGENT-CONTINUITY-INFRA`
- State: `IN_PROGRESS`
- Completed so far:
  - added `AGENT_EXECUTION_CONTRACT.md`;
  - added `docs/AGENT_RUN_STATE.json`;
  - bound the execution model to the full 93-entry research campaign plus the mandatory v2 reference expansion;
  - preserved the current technical resume point at `AGENTS_HANDOFF_2026-08-14_OPENCONNECT_V1_CLOSURE.md`.
- Existing current technical task: OpenConnect/Enterprise `COMPLETE-RESEARCH-v1` closure.
- Important constraint: repository instructions/checkpoints can guarantee deterministic resume on the next agent invocation, but the repository itself cannot relaunch a stopped ChatGPT process without an external agent runner/scheduler.
- Exact next infrastructure action: add machine-readable backlog/verification tooling and wire `AGENTS.md`/`AI_START_HERE.md` to the continuous-execution contract.
- Exact next technical action after infrastructure: resume the OpenConnect v1 closure from the newest handoff, then continue the remaining original R1-R4 campaign, then the v2 reference expansion without owner prompting.

---

## 2026-08-14 — Xray / modern-proxy v1 work unit 1

- Work unit: `XRAY-MODERN-PROXY-V1-CLOSURE`
- State: `IN_PROGRESS`
- Previous dependency checkpoint: OpenConnect/Enterprise shared family is `V1-HANDOFF-READY / NOT IMPLEMENTED`; residual gaps remain explicit.
- Completed in this unit:
  - created `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md` — commit `e200ad6c01c190ff267ea7ef3182fecda60e11bc`;
  - created `research/upstreams/xray-family/DEPENDENCIES_TESTS_RELEASES.md` — commit `1083c4e9ebec66b8b2734da2ba7af00bd041f3b2`;
  - created `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md` — commit `ad5135feed8c9cf5aeab3eb2e24ee84451850443`;
  - created `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md` — commit `4c08b98287a95b3e8a42b7c9138a7575129564b1`;
  - created `research/upstreams/xray-family/ISSUE_RELEASE_LESSONS.md` — commit `23e8f43c3a9b16900c83d7eaea33c6e04a6bb5da`;
  - created `research/upstreams/xray-family/LIBXRAY_WRAPPER.md` — commit `9cd861ce44e659a912f1a40d8a9b737046fb927e`;
  - created dated status `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_XRAY_V1_1.md` — commit `e411cf66d9a083d104a0f264064cfb7db69a0135`;
  - created handoff `AGENTS_HANDOFF_2026-08-14_XRAY_V1_1.md` — commit `d5f0c9c2e5ea71ff00d301878970b02b45ae04f2`;
  - synchronized `docs/AGENT_RUN_STATE.json` — commit `f2357e6cd94f6358e9c032c7cf57558a62d7c5c2`.
- Source pins/evidence:
  - Xray-core current-main research pin `7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, root MPL-2.0;
  - latest stable release observed: `v26.3.27`, older than current main;
  - libXray pin `d0ab60ae4dd91cf119c878152d12103e6f84b78a`, root MIT, wrapper around MPL-licensed Xray-core plus dependencies.
- Key findings:
  - protocol, transport, security/flow and routing/DNS are independent capability axes;
  - canonical PVNetwork profile must not equal generated Xray runtime configuration;
  - Xray main contains active/deprecated/removed configuration semantics, requiring core-version-aware validation/migration;
  - exact build SBOM is required because dependency surface includes QUIC, uTLS, REALITY, DNS, gRPC/protobuf, WireGuard/Wintun, Shadowsocks and platform networking;
  - libXray is a strong narrow wrapper candidate but has process-wide lifecycle/concurrency caveats and does not eliminate platform VPN lifecycle work;
  - v2rayN current regression evidence proves core connectivity can be healthy while system DNS/TUN behavior is broken;
  - major GUI client licenses remain separate from core licenses and are mostly reference-only for a closed commercial PVNetwork build.
- Checks/tests:
  - primary-source repo/tree/license/CI/source evidence was fetched and cross-checked through the GitHub connector: PASS for the documented research claims;
  - local checkout/Python `agent_state.py verify/build/next` path was attempted but the current local runtime could not resolve `github.com`: FAIL due environment DNS, not repository/project state.
- Failed approach not to repeat unchanged:
  - do not retry public GitHub clone from the same local container until DNS/network capability changes; use the working GitHub connector for repository research/checkpoint work.
- Blockers:
  - no project-wide blocker; local clone/validator execution path is temporarily unavailable in this runtime;
  - Xray family remains `IN-RESEARCH`, not v1 complete.
- Exact next action:
  1. create Xray per-entry support/reuse decision matrix;
  2. map Xray commander/API/stats runtime-control ownership;
  3. deepen libXray API/lifecycle/build/dependency issue evidence;
  4. create split v2rayNG Android source/storage/VpnService/import/menu dossiers;
  5. add Xray security/dependency-advisory evidence;
  6. synchronize Xray `INDEX.md`, affected numbered entries, project state and newest handoff;
  7. continue the next unfinished original-v1 family without waiting for owner.

---

## 2026-08-14 — Xray / modern-proxy v1 closure

- Work unit: `XRAY-MODERN-PROXY-V1-CLOSURE`
- State transition: `IN_PROGRESS -> PASS` at the **shared-family original-research handoff** level only.
- New state: `V1-HANDOFF-READY / NOT IMPLEMENTED`.
- Important: PASS here means the family has a reasonable v1 research handoff with residual gaps preserved. It does not mean protocol implementation/certification.
- New technical evidence includes:
  - per-entry support/reuse decisions for 037/038/039/040/074/075/076/084/086/088/089/091/092 — `research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`;
  - Xray runtime Commander/proxyman/router/stats control map — `XRAY_API_CONTROL.md`;
  - libXray API/lifecycle/platform deep audit — `LIBXRAY_API_LIFECYCLE.md`;
  - libXray issue/lifecycle lessons — `LIBXRAY_ISSUE_LESSONS.md`;
  - repository advisory/security/SBOM review — `SECURITY_AND_DEPENDENCY_ADVISORIES.md`;
  - v2rayNG Android architecture/storage/menu/build-CI dossiers;
  - synchronized numbered entry files for Xray protocols/security/flow/transports;
  - shared Xray `INDEX.md` now `V1-HANDOFF-READY / NOT IMPLEMENTED`.
- Critical security finding:
  - Xray advisory `GHSA-5wf9-h793-w73c` records vulnerable `>= v26.1.13`, patched `>= v26.7.11`;
  - GitHub non-prerelease `releases/latest` returned `v26.3.27`, inside the vulnerable range;
  - therefore “latest non-prerelease” is not an acceptable product-selection rule.
- v2rayNG native supply-chain pin:
  - app pin `e8a82d...`;
  - AndroidLibXrayLite submodule `b213898...` maps to exact wrapper tag `v26.7.31`;
  - wrapper root license LGPL-3.0;
  - wrapper embeds a 2026-07-28 Xray pseudo-version later than the known advisory patch threshold, but exact final SBOM/security review remains required.
- Checks:
  - primary-source repository/license/tree/workflow/advisory evidence through GitHub connector: PASS for documented research claims;
  - no PVNetwork executable/device tests exist yet: NOT APPLICABLE TO RESEARCH HANDOFF, still required later.
- Residual gaps preserved:
  - exact production Xray pin/SBOM not selected;
  - stable/prerelease/main comparison can be deepened;
  - long-tail clients/current Android issue sampling/real-device soak/performance remain;
  - server/crypto/wire-flow belongs to mandatory v2 phase.
- Closure handoff:
  - `AGENTS_HANDOFF_2026-08-14_XRAY_V1_2.md`.
- Dated closure status:
  - `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_XRAY_V1_2.md`.
- Exact next action:
  1. activate `WIREGUARD-AMNEZIAWG-V1-CLOSURE`;
  2. re-read the current `research/upstreams/wireguard-family/` tree and prior evidence;
  3. close Windows/storage/dependency/Amnezia platform/reuse/issue gaps;
  4. synchronize entries 002/003 and family state;
  5. if handoff-ready, immediately select next unfinished original-v1 family without waiting for owner.
