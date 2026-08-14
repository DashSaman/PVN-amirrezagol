# PVNetwork — AI START HERE

> **MANDATORY:** Every AI assistant, coding agent, developer, or new chat working on this repository MUST read the repository research documentation before doing any work.

Repository: `DashSaman/PVN-amirrezagol`  
Product: **PVNetwork Universal VPN / Proxy Super Client**

## Current phase
This repository is currently **research / requirements / architecture first**. It is not yet a production application. Never invent implementation, testing, platform-support, or Store-readiness claims.

## Mandatory reading order
Read these files before continuing:

1. `AI_START_HERE.md`
2. `PVNETWORK_MASTER_CONTEXT.md`
3. `AGENTS.md`
4. `AGENT_EXECUTION_CONTRACT.md`
5. `docs/AGENT_RUN_STATE.json`
6. the newest handoff named by `AGENTS.md`
7. `docs/PROJECT_STATE.md`
8. `docs/ROADMAP.md`
9. `docs/ARCHITECTURE.md`
10. `docs/PROTOCOL_MATRIX.md`
11. `docs/RESEARCH_LOG.md`
12. `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14.md` and any newer dated research-status file
13. **`research/AI_RESEARCH_CAMPAIGN.md`**
14. **`research/RESEARCH_COMPLETENESS.md`**
15. **`research/PROTOCOL_RESEARCH_TEMPLATE.md`**
16. **`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`**
17. **`research/REFERENCE_V2_COMPLETENESS.md`**
18. **`research/SOURCE_MIRROR_POLICY.md`**
19. `docs/AGENT_CHECKPOINT_LOG.md`
20. the relevant numbered folder under `research/protocols/`
21. relevant shared dossiers under `research/upstreams/`
22. recent Git history and the actual repository tree, because connector filtering may occasionally make a tracker row lag behind a newly created dossier

When a checkout/Python runtime is available, run:

```bash
python scripts/agent_state.py verify
python scripts/agent_state.py build
python scripts/agent_state.py next
```

Then continue from the documented incomplete work. Do not restart the research from zero because chat context was lost.

## Permanent research requirement
The scope contains **93 numbered entries** in `docs/PROTOCOL_MATRIX.md`. Every entry must eventually receive an evidence-backed dossier covering its strongest clients/implementations, canonical source and pinned revision, license/reuse status, complete source-tree reference, languages/build system, architecture and engine/core, UI/menu map, configuration/import/export, persistence and secure storage, platform integration, diagnostics, official image/asset references, meaningful forks, important issues/PRs/releases/advisories/forums, tests/CI, lessons for PVNetwork, Store/privacy/security implications, and an explicit reuse/support decision.

A folder or short README is only a research **SKELETON**. It must never be marked complete until all completion gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md` are satisfied with traceable evidence.

Many entries share the same core/client. Put exhaustive shared source analysis under `research/upstreams/` and link protocol-specific dossiers to it instead of duplicating identical source-tree research.

After the original research layer reaches its intended gates, every applicable entry must continue through the second exhaustive reference layer in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`, tracked by `research/REFERENCE_V2_COMPLETENESS.md`. Finishing one protocol/family never means the full owner-approved campaign is complete.

## Third-party source/assets
Follow `research/SOURCE_MIRROR_POLICY.md`. Public GitHub source does not automatically mean it can be copied, redistributed, or commercially shipped. Prefer pinned links, tree manifests, architecture analysis, issue/forum evidence, and asset references. Mirror/vendor third-party code or images only after license and attribution obligations have been reviewed and documented.

## GitHub is project memory
Anything required for another AI/developer to continue must be written into this repository. After meaningful research, update the relevant dossier, `research/RESEARCH_COMPLETENESS.md`, `docs/RESEARCH_LOG.md`, `docs/PROJECT_STATE.md`, `docs/AGENT_RUN_STATE.json`, and `docs/AGENT_CHECKPOINT_LOG.md` when the connector permits. If a large tracker/state write is rejected, preserve the verified evidence in smaller dated status/index files and record the write blocker instead of fabricating completion.

## Continuous execution / resume rule
The owner has already authorized execution of the repository-documented full backlog. Do not stop after a work unit and wait for “continue”. After every meaningful unit:

1. persist evidence;
2. checkpoint exact state and next action;
3. verify state consistency;
4. select the next executable required work item;
5. continue.

If execution is forcibly interrupted by the platform, the next invocation must resume immediately from repository state. A platform interruption is not completion.

If one task is blocked, record the blocker and continue independent required work. If the same approach fails twice, change strategy rather than looping.

## No-loop / no-fake-completion rules
If an approach fails twice, do not repeat it unchanged. Record the blocker and change strategy. Keep research, implementation, build, integration test, real-device test, Store verification, and production verification as separate states.

Overall completion may not be claimed unless:

```bash
python scripts/agent_state.py verify --require-complete
```

passes and the detailed repository evidence genuinely satisfies the required gates.

## Branding/platform requirements
The final application must use the owner's exact supplied PVNetwork logo, support Persian correctly as a first-class RTL language plus English and an extensible localization system, and target Android/Android TV, Windows, macOS, iPhone/iPad, and Linux using platform-appropriate architecture. Store requirements are live constraints and must be rechecked from current official sources before release.

## Required behavior for any new AI
First report that the mandatory files were read, state the current research status from repository evidence, then continue actual work from the next evidence gap. Do not merely propose a new plan and stop. Do not ask the owner to repeat requirements already preserved in repository state.

`AI_START_HERE.md` is the permanent handoff entry point. Do not delete or bypass it.
