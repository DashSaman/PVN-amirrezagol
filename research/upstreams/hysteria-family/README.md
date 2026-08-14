# Hysteria Family — Shared Core Research

Related matrix entries: 042 Hysteria and 043 Hysteria2, plus clients that expose the protocol through broader multi-protocol engines.

Research state: `IN-RESEARCH`.

## Pinned upstream
- Repository: `apernet/hysteria`
- Pinned SHA: `14e9fff1d972ab0187ac7fcf75b9514dc8664065`
- Complete recursive tree reference: `https://api.github.com/repos/apernet/hysteria/git/trees/14e9fff1d972ab0187ac7fcf75b9514dc8664065?recursive=1`
- Main language: Go, with smaller Python/Shell support.
- Pinned `LICENSE.md` contains MIT license text.

## Research role
The official Hysteria project is the primary protocol/core reference. Multi-protocol GUI clients such as Hiddify or other compatible front-ends must be audited separately for UX, configuration conversion and their own licenses.

## PVNetwork direction
Classify the official core as `REUSE-CANDIDATE`, subject to dependency review, API/process integration analysis, Store implications and version-compatibility testing. Do not use GUI-client license status as a substitute for the core’s own license review.

## Required deeper research
- source/module and public API/CLI boundary map;
- configuration schema/version differences between Hysteria generations;
- client-side storage/UI behavior in top front-ends;
- platform build/package support;
- test/CI and performance evidence;
- issues/releases/security advisories;
- compatibility/fork ecosystem;
- license/dependency inventory;
- explicit PVNetwork adapter and acceptance-test design.

Nothing here means PVNetwork support is implemented.