# OpenVPN GUI for Windows — Source / UI / Storage Research

Pinned source: `OpenVPN/openvpn-gui@7295bdc8739a007d099aa590be678c756d02def4`.

Research role: **Windows behavior/integration/UI reference**. This is distinct from OpenVPN 3 core research.

## Complete source reference
Recursive tree:
`https://api.github.com/repos/OpenVPN/openvpn-gui/git/trees/7295bdc8739a007d099aa590be678c756d02def4?recursive=1`

Root contents:
`https://api.github.com/repos/OpenVPN/openvpn-gui/contents?ref=7295bdc8739a007d099aa590be678c756d02def4`

## Languages/build
GitHub language statistics at review time show the project is overwhelmingly **C**, with small C++, Makefile, CMake, M4 and Shell components. Root evidence includes `CMakeLists.txt`, `CMakePresets.json`, `BUILD.rst`, `CHANGES.rst`, `COPYING`, resources and source files.

## Architectural shape
This is a traditional native Windows GUI around OpenVPN functionality. Important source responsibilities observed:
- `tray.c` — notification-area icon, popup menu creation, connection/group menus and tray UI state.
- `registry.c` — application/install preferences and Windows Registry integration.
- `options.*` — application option/state structures.
- `openvpn.*` and `openvpn_config.*` — engine/config integration boundary.
- `localization.*`, `openvpn-gui-res.h`, `res/*.rc` — localized resource strings/icons/dialog resources.
- build/change documentation — packaging/build/release evidence.

## Tray/menu architecture
`tray.c` declares a root menu, per-connection submenus and an import menu. It creates popup menus dynamically for discovered connections and grouping structures. It also changes icon/menu state for connection status. This is useful as a behavioral reference for PVNetwork desktop tray/menu-bar support, but PVNetwork should create its own cross-platform navigation model rather than duplicate this Win32 menu design.

## Configuration discovery and persistence
`registry.c` documents important current defaults and preference storage. Examples at the pinned revision include:
- per-user config directory default under `%USERPROFILE%\OpenVPN\config`;
- `.ovpn` config extension;
- per-user log directory default under `%USERPROFILE%\OpenVPN\log`;
- global install/config/log locations derived from the OpenVPN installation Registry key;
- preferences for log append, balloon/messages, script timeouts, menu view, persistent connections, auto restart, password behavior and selected OpenVPN engine;
- management-port offset and other GUI behavior settings.

This demonstrates a **registry + filesystem profile/log model**, not a centralized database abstraction. PVNetwork should not copy that persistence design directly because it must normalize many protocol families and platforms; instead, use it as evidence for Windows migration/import compatibility and behavior expectations.

## Engine selection observation
The pinned `registry.c` includes an `ovpn_engine` preference and conditional code for an OpenVPN 3 integration path in addition to the OpenVPN 2-style executable path. This makes OpenVPN GUI useful for studying how one desktop frontend can select/control different OpenVPN engine generations.

## Localization
The repository contains per-language Win32 resource files. Search evidence at the pinned revision includes English, Spanish, Danish, Finnish, Norwegian, Korean, Polish, Italian, Portuguese, Turkish, Dutch, German, Czech, French, Japanese, Simplified/Traditional Chinese, **Persian (`res/openvpn-gui-res-fa.rc`)**, Greek, Ukrainian, Russian and others.

PVNetwork lesson: existing Persian strings are useful terminology references, but do not assume Win32 resource localization proves correct modern RTL layout. PVNetwork requires its own RTL layout testing, mixed LTR/RTL IP/URL tests and responsive component design.

## Logs/diagnostics
Registry defaults expose separate log directories and append behavior; source also includes event/debug logging paths. A later audit must map log viewers, status windows, event logs and redaction behavior file by file.

## Credentials/security
The source references password-save policy controls and `save_pass` related code. Do not assume credentials are stored safely without auditing the exact implementation. PVNetwork must use a stronger cross-platform secure-storage abstraction and separately classify which profile secrets may be persisted.

## License
Source headers such as `tray.c` and `registry.c` state GPL v2 or later. Treat direct code reuse in a proprietary/closed PVNetwork product as **NEEDS-LEGAL-REVIEW / generally REFERENCE-ONLY unless licensing architecture is compatible**. Preserve upstream notices for any approved reuse.

## Developer lessons for PVNetwork
- Desktop tray UX can be dynamically generated from normalized connection/profile objects.
- Windows-specific persistence should remain behind a platform adapter rather than leak into shared model code.
- Global/admin-managed and per-user profile locations are separate concepts worth supporting in migration/import tooling.
- Engine selection/version should be explicit and observable.
- Localization resources should be separated from source logic.
- Large profile counts need menu/list scalability rather than one flat list.

## Evidence gaps remaining
- complete menu command map from `tray.c` + resource IDs;
- settings/dialog map from all Win32 dialog resources;
- exact import/export/profile edit flows;
- credential-storage implementation audit;
- service/helper/privilege model;
- current issues and regressions involving Windows updates, DCO, DNS/routes, sleep/resume and pre-login;
- release/package signing/update model;
- screenshot/icon inventory and asset licensing.

Research reuse classification: **REFERENCE-ONLY for GUI code by default; behavioral and integration lessons reusable.**

Status: `IN-RESEARCH`.