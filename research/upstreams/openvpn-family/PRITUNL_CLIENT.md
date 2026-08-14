# Pritunl Client — Architecture/UX Reference With Commercial License Restriction

Pinned canonical repository: `pritunl/pritunl-client@9c6a0823abb4edcc1ba913a9fdac0d8323b6cc30`.

A previous repository name/URL (`pritunl-client-electron`) redirects to the current repository identity; preserve the pinned canonical repository in future research.

## Why it is included
Pritunl Client is useful as a multi-platform desktop client reference for profile management and desktop product architecture. It is **not** currently a safe code donor for a commercial PVNetwork release under the pinned public license.

## Complete source reference
Recursive tree:
`https://api.github.com/repos/pritunl/pritunl-client/git/trees/9c6a0823abb4edcc1ba913a9fdac0d8323b6cc30?recursive=1`

## License finding — critical
Pinned `LICENSE` states, in its summary and grant terms:
- non-commercial use only;
- source-code or binary products cannot be resold or distributed;
- modified source cannot be distributed under the public terms;
- commercial use requires a different arrangement/license.

Therefore:

**PVNetwork reuse classification: `REFERENCE-ONLY / DO-NOT-COPY` unless Pritunl grants a separate commercial license covering the intended use.**

This applies even though the source is publicly viewable on GitHub. Public source availability is not the same as an open-source license suitable for commercial redistribution.

## Research use allowed for this project
Use the repository to understand:
- desktop client architecture patterns;
- UI/menu/profile management concepts;
- packaging/update patterns;
- bug/issue lessons;
- separation of application UI and network components;
- platform-specific design trade-offs.

Do not mirror source files, icons, screenshots or modified forks into PVNetwork under the assumption that GitHub visibility grants redistribution rights.

## Developer lesson
Every PVNetwork upstream dossier must classify **license before code reuse**. A technically excellent client can still be reference-only. This is exactly why PVNetwork keeps behavior/UX research separate from the reusable-core decision.

## Remaining research gaps
- source-tree/module architecture map;
- language/framework/build/dependency inventory;
- UI/menu and profile workflow map;
- persistence/credential-storage analysis;
- platform packaging/update architecture;
- issue/release history;
- screenshot/assets reference list;
- whether a commercially licensable SDK/component is offered and under what terms.

Status: `IN-RESEARCH`, with reuse decision already constrained by the pinned public license.