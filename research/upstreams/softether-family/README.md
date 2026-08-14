# SoftEther Family — Shared Source / Client Research

Related matrix entries: 013 SoftEther VPN Protocol, 009/010 L2TPv3-related compatibility research, 014/015 EtherIP-related compatibility research, plus vendor/legacy interoperability where applicable.

Research state: `IN-RESEARCH`.

## Pinned upstream
- Repository: `SoftEtherVPN/SoftEtherVPN`
- Pinned SHA: `b1f7ef00040786d00bfa06c27fa463d106851e0c`
- Recursive tree reference: `https://api.github.com/repos/SoftEtherVPN/SoftEtherVPN/git/trees/b1f7ef00040786d00bfa06c27fa463d106851e0c?recursive=1`
- Root LICENSE at the pinned revision is Apache License 2.0.

## Languages and repository shape
GitHub language statistics show a large mixed codebase dominated by C, with substantial C#, TypeScript and HTML plus smaller C++, CMake, Swift, shell and other support code. The root contains CI files, CMake build files, `3rdparty/`, security/antivirus documentation, submodule metadata and multiple application/server/client areas.

This is not a single small client library. The research must separate:
- client-side components;
- server/admin components;
- platform GUI/management tools;
- protocol/core libraries;
- third-party dependencies;
- installer/build tooling.

## PVNetwork reuse direction
Apache-2.0 makes SoftEther materially more promising for commercial reuse than several GPL/custom-license GUI projects, but no direct import should happen until:
- client-only modules are isolated;
- all `3rdparty`/submodule licenses are inventoried;
- platform support and Store constraints are mapped;
- source architecture and configuration persistence are documented;
- tests/issues/releases are reviewed.

## Developer research still required
- complete module/source-tree map with client/server separation;
- native client UI/menu map;
- virtual adapter/platform integration map;
- configuration and credential-storage model;
- protocol compatibility boundaries;
- management/RPC interfaces;
- installer/update behavior;
- screenshots/assets inventory and rights;
- meaningful forks;
- issues, release notes and security advisories;
- tests/CI and coverage gaps.

Current classification: `REUSE-CANDIDATE`, pending dependency/path-level audit. No PVNetwork support is implemented.