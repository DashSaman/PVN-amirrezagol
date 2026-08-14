# Amnezia Client — Multi-Platform / Multi-Engine Reference

Pinned repository: `amnezia-vpn/amnezia-client@e643fa008cdeab1045b4b37652d07dd57924ccfc` from the current default `dev` branch.

## Why it matters to PVNetwork
Amnezia is one of the most relevant product references for PVNetwork because it is a real cross-platform client that combines multiple networking technologies under one user-facing application. It should be studied for engine orchestration, profile UX, platform abstraction, installer/Store work and coexistence of native/mobile/desktop code.

## Source/language profile
GitHub reports a large mixed codebase dominated by:
- C++
- QML
- Kotlin / Java
- CMake
- Swift / Objective-C++ / Objective-C
- Python / Shell
- smaller C, JavaScript and Go components

This strongly suggests a shared C++/Qt/QML product core with platform-native mobile/Apple/Android integration rather than one language across every target.

## License
GitHub metadata for the current repository reports GPL-3.0. Therefore the client application source is a **REFERENCE-ONLY / GPL-ARCHITECTURE-REVIEW** input for a closed commercial PVNetwork product. This does not change the separate license of `amneziawg-go`, which was independently reviewed as MIT.

## Source provenance
Complete pinned tree reference:
`https://api.github.com/repos/amnezia-vpn/amnezia-client/git/trees/e643fa008cdeab1045b4b37652d07dd57924ccfc?recursive=1`

Do not mirror the entire client repository into PVNetwork. Use the pinned tree to map modules, then vendor only separately approved components.

## Developer research priorities
The next pass must map:
- QML screen/menu/navigation structure;
- C++ application/service/controller layers;
- engine adapters and per-protocol process/library boundaries;
- Android Kotlin/Java integration;
- Apple Swift/Objective-C++ integration;
- configuration serialization/import/export;
- local settings/profile persistence and secret storage;
- logs/diagnostics;
- update/install packaging by platform;
- issue tracker themes and platform regressions;
- CI/tests;
- bundled third-party dependencies and licenses.

## PVNetwork lesson
Amnezia should be treated as proof that a unified multi-engine product architecture is practical, not as a source tree to clone. Reuse the architectural lessons while keeping PVNetwork branding, UI, normalized profile model and license strategy independent.

Status: `IN-RESEARCH`.