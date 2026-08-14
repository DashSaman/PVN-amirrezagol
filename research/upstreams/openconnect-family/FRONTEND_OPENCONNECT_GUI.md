# OpenConnect GUI — Front-End / UX Research Dossier

Research date: 2026-08-14

Canonical project: `https://gitlab.com/openconnect/openconnect-gui`

Research state: `IN-RESEARCH`. This is a UI/architecture reference; it is not PVNetwork implementation evidence.

## Project status and source provenance

The old GitHub repository is archived and explicitly points to the GitLab project. Current research therefore uses the canonical GitLab project.

Reviewed stable GUI release: **v1.6.2** (`c89e5dc0`, released 2024-06-06). The canonical `main` branch remains active after that release and has more recent commits, so stable-release behavior and current-development behavior must be distinguished.

## License / reuse classification

Canonical `LICENSE.txt` states **GPL-2.0-or-later** for the GUI project.

Current PVNetwork classification:

- UI/architecture/bug reference: `REFERENCE-ONLY` by default for a closed/commercial PVNetwork application unless the chosen distribution architecture is explicitly GPL-compatible.
- direct wholesale GUI copy/fork into PVNetwork: **not the default path**.
- reusable OpenConnect library/core remains a separate LGPL-2.1 decision and must not inherit the GUI application's GPL classification by association.

A MinGW packaging spec in the repository has historically exposed different package-license metadata, which is another reason to verify the canonical license file and path-level dependencies rather than trusting one package field.

## Technology / build layout

The current tree is a C++/Qt application with CMake/build/packaging infrastructure. Top-level areas include:

- `src/` — application/UI/library-integration source;
- `CMake/` and `CMakeLists.txt` — build system;
- `external/` and `contrib/` — bundled/external integration areas requiring separate license review;
- `bundle/` — packaging/bundle support;
- `nsis/` — Windows installer support;
- `wintun/` — Windows networking component area;
- `.gitlab/` and `.gitlab-ci.yml` — CI/release infrastructure;
- `docs/` — development/release documentation;
- `appveyor.yml` — Windows CI/build history; current source records a Qt6 port;
- translation/resource data under the application source tree.

The complete tree still needs a pinned recursive manifest before this dossier can be complete.

## Product / UI goals from upstream README

The project explicitly targets a simple/minimal interface for non-technical users. Its primary user tasks are intentionally close at hand:

- connect to a new server;
- connect to an existing server/profile;
- disconnect;
- view logs.

This is a strong UX lesson for PVNetwork: keep ordinary connection tasks shallow, and place enterprise/vendor-specific details behind progressive disclosure rather than exposing the raw OpenConnect API as a settings form.

## Platform scope

Current README states Windows 10+ and macOS 10.12+ as supported GUI platforms. This is upstream GUI scope only; it is not the PVNetwork platform/minimum-OS decision.

The GitLab project metadata/repository history also reflects Windows/macOS/Linux-oriented development/build work, but PVNetwork must independently choose its frontend technology per target platform.

## UI behavior / release lessons

Historical/current releases and issues expose valuable product lessons:

### Profile flows

Release history includes both quick-connect flows and fuller profile creation. PVNetwork should preserve the same conceptual split:

- fast “connect to server/account” path;
- advanced/editable profile path;
- no requirement for a user to understand every engine option before first connection.

### Tray / close behavior / logs

Earlier release notes document notification-area connection actions, log access, minimize-to-tray behavior and remembering window geometry. These should be treated as UX references, not copied UI.

PVNetwork must keep window lifecycle separate from connection lifecycle and use the same authoritative session state for tray/menu and main UI.

### HiDPI / path presentation / signing

Release notes include HiDPI improvements, OS-native path display, IPv6 improvements and Windows executable signing. These are product-quality requirements PVNetwork should treat as baseline platform polish rather than afterthoughts.

## Authentication / SSO gap — important

A 2024 GUI issue shows that OpenConnect library SSO capability does not automatically mean the GUI implements a browser handler. The maintainer explicitly pointed to the library's webview callback as the mechanism a GUI could integrate.

PVNetwork architecture rule:

- browser/SSO handling belongs to the product/platform authentication service;
- the Enterprise Adapter requests a browser/webview/external-browser handoff;
- product UI owns the browser lifecycle and secure result transfer;
- the core library never directly dictates the visual/browser architecture;
- lack of a frontend callback must be detected as a capability gap, not reported as generic “authentication failed”.

## Credential-storage UX lesson

A current/closed GUI issue shows that a user-facing option named “Batch Mode” was used to control remembered password behavior and was confusing enough that maintainers discussed renaming/splitting it.

PVNetwork requirements:

- do not expose implementation terminology such as “batch mode” for credential policy;
- separate “remember username”, “remember password/secret”, “remember group/realm”, and “suppress/remember non-secret prompts” where behavior differs;
- secrets must use platform-protected credential storage;
- non-secret profile metadata can use the canonical profile store;
- UI must explain what is stored and how to clear it.

## Profile storage / portability lesson

Historical user questions around locating GUI profile data show that the distinction between GUI profile storage and OpenConnect CLI config was not obvious.

PVNetwork should explicitly separate:

- product profile/account storage;
- portable import/export format;
- secure credential storage;
- engine/runtime configuration;
- support diagnostic export.

A user should not need to inspect hidden application folders to understand backup/export behavior.

## Windows platform regression lesson

A current GUI issue reports repeated Windows network-profile artifacts being created across connections. Whether or not upstream later fixes the exact issue, the failure class is important for PVNetwork:

- platform networking artifacts must have stable ownership/lifecycle;
- connect/disconnect cycles must not leak OS network objects/profiles/routes;
- cleanup must be tested after failure and application crash as well as normal disconnect.

## Release / bug lessons worth converting to PVNetwork tests

OpenConnect GUI release history includes fixes around:

- password/profile storage behavior;
- disconnect-before-quit semantics;
- respecting per-profile proxy settings;
- route cleanup;
- MTU behavior;
- non-English interface names;
- HiDPI/display quality;
- IPv6;
- executable signing.

PVNetwork should turn these into cross-platform acceptance categories rather than copying upstream implementation details.

## Store/security UX principle

The current GUI README explicitly states that security decisions should not be delegated to non-technical users unless necessary and describes a trust-validation approach that attempts normal PKI validation first.

PVNetwork should preserve the principle—secure defaults and minimal unnecessary trust prompts—while implementing platform-appropriate certificate/trust UI and keeping exact policy in the Enterprise Adapter/security design rather than cloning the GUI's behavior blindly.

## Current reuse decision

- Study UI/navigation: **YES**.
- Study issue/release lessons: **YES**.
- Directly copy GUI code into a closed PVNetwork app: **NO default; GPL review required**.
- Use OpenConnect GUI as proof that libopenconnect frontend integration is feasible: **YES, reference evidence**.
- Use it as the only frontend reference: **NO**; compare NetworkManager and platform-native patterns too.

## Remaining gaps

- pin exact current `main` commit and stable v1.6.2 recursive source trees;
- file-to-screen map under `src/`;
- exact profile/preferences/credential storage implementation paths;
- translation/localization and RTL inventory;
- full CI/build/package dependency audit;
- current open issue triage by UI/auth/storage/platform category;
- screenshot/asset catalog and reuse-rights audit;
- Windows signing/update path review;
- macOS packaging/notarization review;
- compare against NetworkManager-openconnect / GNOME secret-agent integration.