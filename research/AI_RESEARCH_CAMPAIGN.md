# PVNetwork — Mandatory 93-Entry AI Research Campaign

This document is part of the permanent AI handoff for PVNetwork.

## Mission
Research every one of the 93 numbered entries in `docs/PROTOCOL_MATRIX.md` deeply enough that a senior developer can understand the best existing clients, their codebases and architecture, their strengths/failures, and the legally/technically appropriate way to reuse lessons or components in PVNetwork.

This is **not** a task to create 93 superficial summaries.

## Mandatory workflow for a new AI
Before doing any work, read:
1. `AI_START_HERE.md`
2. `PVNETWORK_MASTER_CONTEXT.md`
3. `AGENTS.md`
4. `docs/PROJECT_STATE.md`
5. `docs/PROTOCOL_MATRIX.md`
6. `research/RESEARCH_COMPLETENESS.md`
7. `research/PROTOCOL_RESEARCH_TEMPLATE.md`
8. `research/SOURCE_MIRROR_POLICY.md`
9. The relevant numbered protocol dossier
10. Relevant shared dossier(s) under `research/upstreams/`

Then inspect Git history and continue from the **lowest-value-gap/highest-priority incomplete research item**, not from chat memory.

## Required depth for every numbered entry
For each protocol/technology/transport/security entry:

1. Identify the strongest real-world clients/implementations.
2. Explain why each was selected; do not rank only by GitHub stars.
3. Locate canonical repositories and official documentation.
4. Pin the exact source revision/tag used for analysis.
5. Audit license and commercial/redistribution obligations.
6. Capture a complete source-tree manifest or immutable recursive-tree reference.
7. Map programming languages, frameworks, build systems and dependencies.
8. Map the software architecture and engine/core relationship.
9. Map every meaningful UI screen/menu/navigation path visible from source or official evidence.
10. Map configuration/import/export formats and parsers.
11. Map profile/preferences/database/cache/log storage.
12. Map secure credential/key/certificate storage.
13. Describe the connection lifecycle at an architectural level.
14. Map platform-specific integration for Windows, Android, Android TV, iOS/iPadOS, macOS and Linux where applicable.
15. Map logs, diagnostics, status/statistics and crash handling.
16. Record official screenshots/assets and repository asset directories as references; audit reuse rights before copying.
17. Find meaningful forks and explain their deltas.
18. Review important open/closed issues, PRs, release notes and security advisories.
19. Review official/community forums, wikis and maintainer discussions where they add evidence.
20. Extract bugs/failures/lessons that PVNetwork can prevent.
21. Convert relevant lessons into proposed PVNetwork acceptance/regression tests.
22. Record upstream test/CI coverage and gaps.
23. Record Store/privacy/security implications.
24. Make an explicit PVNetwork reuse decision.
25. Record unresolved unknowns rather than guessing.

The detailed completion contract is `research/PROTOCOL_RESEARCH_TEMPLATE.md`.

## Shared upstream rule
Many of the 93 entries share the same client/core. Never duplicate an identical 20,000-file tree description into multiple folders.

Instead:
- create one exhaustive upstream dossier in `research/upstreams/<project>/`;
- store its pinned revisions, tree manifests, architecture, UI, storage, issues and license research there;
- link every relevant numbered protocol dossier to it;
- keep protocol-specific conclusions in each numbered dossier.

Examples:
- VLESS, VMess, REALITY, XTLS, Vision, XHTTP and mKCP may share Xray-family evidence;
- Cisco AnyConnect, GlobalProtect, Pulse/Juniper, F5 and other compatible families may share OpenConnect evidence;
- several IKE/IPsec entries may share strongSwan/native-platform evidence.

## Source and asset copying rule
Do not equate “public on GitHub” with “safe to copy into PVNetwork.”

Follow `research/SOURCE_MIRROR_POLICY.md`.

Default behavior:
- store canonical URL;
- store pinned SHA/tag;
- store recursive source-tree/API reference or generated manifest;
- summarize modules and important files;
- store issue/PR/forum URLs and findings;
- store image/screenshot links and licensing notes;
- do not mirror whole upstream repositories or asset sets unless license review explicitly permits it and there is a real engineering reason.

If vendoring is approved later, preserve upstream license/copyright/NOTICE requirements and document modifications.

## Evidence rule
Every important claim must be traceable to one or more of:
- source file/path at pinned revision;
- official documentation;
- official release notes;
- issue/PR;
- security advisory;
- official/community forum post with clear provenance;
- reproducible test evidence.

Do not use vague AI memory as evidence.

## Completion states
Use only these states:
- `PENDING`
- `RESERVED`
- `SKELETON`
- `IN-RESEARCH`
- `EVIDENCE-GAPS`
- `COMPLETE-RESEARCH-v1`
- later revisions such as `COMPLETE-RESEARCH-v2`

Research completion never implies implemented/tested product support.

## No-loop / recovery behavior
If research hits a dead link, archived repo, moved project, ambiguous fork or repeated tool failure:
- document the failed path;
- do not repeat it unchanged more than twice;
- find canonical upstream or archive evidence;
- keep partial verified findings;
- continue with independent fields;
- update `docs/PROJECT_STATE.md` with the exact blocker.

If context is lost, resume from GitHub documentation. Do not ask the owner to repeat the whole project.

## End-of-work-unit requirement
After each meaningful research unit:
- update the numbered dossier;
- update shared upstream dossier(s);
- update `research/RESEARCH_COMPLETENESS.md`;
- update `docs/RESEARCH_LOG.md`;
- update `docs/PROJECT_STATE.md`;
- commit with a meaningful message.

## Final campaign goal
The campaign is complete only when all 93 entries have evidence-backed dossiers, shared upstream research is complete enough for engineering decisions, licenses are categorized, and each entry has an explicit PVNetwork reuse/support decision with uncertainties and required future tests documented.