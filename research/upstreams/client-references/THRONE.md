# Throne — Developer Research Dossier

Pinned repository: `throneproj/Throne@195f9918a982f317f4e1001b07cbc4bc67a1daca` from the current `dev` branch.

Research role: active cross-platform C++ desktop GUI reference for a modern multi-protocol client ecosystem. Repository license is GPL-3.0, so direct code reuse in a closed commercial PVNetwork build requires a GPL-compatible distribution strategy.

## Complete source reference
Recursive tree:
`https://api.github.com/repos/throneproj/Throne/git/trees/195f9918a982f317f4e1001b07cbc4bc67a1daca?recursive=1`

## Repository shape
Pinned root includes:
- `3rdparty/`
- `core/`
- `include/`
- `src/`
- `res/`
- `script/`
- `cmake/`
- `CMakeLists.txt`
- GitHub automation and project metadata.

`src/` is already separated into major responsibilities:
- `api/`
- `configs/`
- `database/`
- `global/`
- `stats/`
- `sys/`
- `ui/`
- `main.cpp`

This is useful evidence of an application architecture that separates configuration, persistence, system integration, statistics and UI instead of putting all logic into the main window.

## Language/build
GitHub reports C++ as the main language. The root CMake infrastructure and dedicated `cmake/` directory indicate a native CMake-based desktop build.

## PVNetwork lessons
- keep config, database, system/platform services, statistics and UI as separate modules;
- treat `3rdparty/` as a separate license/supply-chain audit surface;
- keep the client app’s GPL license separate from the license of its underlying networking engine(s);
- use a pinned source tree for architecture/bug research rather than copying the whole GUI into PVNetwork.

## Remaining research
- identify GUI framework and full screen/menu hierarchy;
- map database schema and configuration serialization;
- map core integration and version management;
- inspect system tray, startup and desktop integrations;
- audit `3rdparty/` licenses;
- review issues/PRs/releases and migration history;
- inventory translations/assets/screenshots;
- inspect tests/CI and Store/package behavior.

Status: `IN-RESEARCH`.