# OpenVPN 3 Core — Developer Research

Pinned source: `OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`.

## Software role
OpenVPN 3 is a C++ **client class library**, not a complete end-user GUI and not a server. Upstream identifies `client/ovpncli.hpp` as its client API, includes a minimal wrapper under `test/ovpncli/`, and states that the library is used as the core of OpenVPN Connect products on iOS, Android, Linux, Windows and macOS.

PVNetwork classification: **engine/core candidate**, not a UI donor.

## Complete pinned source reference
Recursive tree:
`https://api.github.com/repos/OpenVPN/openvpn3/git/trees/1fd271caefc9a71406afdc2ff2460999dcfdb234?recursive=1`

Important source areas observed:
- `client/` — client-facing API.
- `openvpn/` — core implementation modules.
- platform-specific subtrees including Apple/Windows areas.
- `test/` and `test/unittests/` — test infrastructure.
- build/CI files including CMake configuration and `.github/` workflows.
- license files under `LICENSE.md` / `LICENSES/`.

The recursive URL is the full pinned file-list reference. Do not copy the whole tree into PVNetwork unless a deliberate vendoring decision is made.

## Languages and build system
GitHub language statistics at review time are dominated by C++, with supporting C, CMake and smaller supporting languages/tooling. The project uses CMake and documents builds/tests for multiple desktop platforms.

## API/integration lesson for PVNetwork
Keep OpenVPN-specific types behind a PVNetwork adapter boundary. UI and profile-management code should depend on PVNetwork’s normalized interfaces rather than directly on OpenVPN 3 classes. The adapter should be responsible for configuration translation, lifecycle/event translation, version reporting and diagnostics exposure at an application-architecture level.

## Platform lesson
A multi-platform core does not remove the need for platform-specific application integration. PVNetwork must still isolate OS-specific networking/service/extension behavior from the shared application layer.

## Tests
Upstream README describes protocol stress/performance testing and points to `test/unittests/test_proto.cpp`, while the source tree contains broader unit/platform tests. PVNetwork should treat upstream tests as evidence of core maturity but still create its own parser, adapter, lifecycle, error, compatibility and platform regression tests.

## License
Pinned `LICENSE.md` states OpenVPN 3 is distributed under **AGPL-3.0-only OR MPL-2.0**, with the upstream-documented OpenSSL permission for the AGPL path. Before integration, PVNetwork must choose/document the applicable path and audit dependencies/path-specific notices.

Research reuse classification: **REUSE-CANDIDATE — legal/dependency audit still required**.

## Not provided by this core
OpenVPN 3 does not replace PVNetwork’s own branded UI, localization/RTL shell, universal profile/subscription storage, multi-engine orchestration, Store-compliance layer or product update/account systems.

## Remaining evidence gaps
- map the pinned client API surface relevant to the adapter;
- map profile validation and unsupported-option reporting;
- complete dependency/license inventory;
- review current issues/releases/security notices affecting integration;
- document platform integration boundaries from public sources;
- define PVNetwork-specific acceptance/regression tests.

Status: `IN-RESEARCH`.