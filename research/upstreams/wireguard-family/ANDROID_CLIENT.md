# WireGuard Android — Developer Dossier

Pinned source: `WireGuard/wireguard-android@e7b3a3c118836e112620b1302a8ba1873ad4daac`.

Research state: `IN-RESEARCH`.

## Module structure

The reviewed source separates two major concerns:

- `tunnel/` — backend and reusable tunnel/configuration layer.
- `ui/` — Android application, activities/fragments, preferences, resources, import/export and product state.

This is directly relevant to PVNetwork: protocol/platform integration should not be fused into screens and navigation code.

## Backend abstraction

The tunnel module defines a `Backend` abstraction and includes implementations/references such as `GoBackend` and `WgQuickBackend`, plus shared tunnel/statistics models. The application selects a backend based on platform capability and settings rather than making the UI depend on one engine implementation.

PVNetwork should preserve the same principle through its own Core Adapter interface.

## Application composition

`ui/.../Application.kt` initializes:

- a backend selected at runtime;
- a `TunnelManager`;
- a file-based configuration store;
- Android Preferences DataStore for application settings;
- update monitoring and product-level helpers.

The source also distinguishes settings/state persistence from tunnel configuration persistence.

## Profile persistence

`FileConfigStore.kt` stores one `wg-quick`-style `.conf` file per configured tunnel inside the application's private files directory. It provides create, load, save, rename, enumerate and delete operations through a `ConfigStore` abstraction.

For PVNetwork this is an architecture reference, not a final storage design. PVNetwork must make an explicit decision about encryption/protected secret storage and backup/export semantics rather than assuming a text profile file is sufficient for all products and platforms.

## UI map verified from source

Important source/resources include:

- `TunnelListFragment.kt` — primary tunnel/profile list flow.
- `SettingsActivity.kt` — settings host.
- `main_activity.xml` and tablet-width variant — adaptive layout evidence.
- `tunnel_list_fragment.xml` / `tunnel_list_item.xml` — list UI.
- `preferences.xml` — application settings definitions.
- import/export and preference helper classes under the UI module.

The reviewed settings resource includes product/version information, restore-on-boot, export, quick tile, log viewer, theme, multiple-tunnel behavior, tools/kernel-related options, remote-control intent permission and donation entry. PVNetwork should not copy this menu verbatim; it is evidence of how a mature client separates ordinary settings from advanced/system-specific capabilities.

## Settings storage

The application creates an Android Preferences DataStore named `settings`. This is distinct from tunnel configuration files.

PVNetwork should keep at least these data classes separate:

- ordinary UI/application preferences;
- canonical profile data;
- credentials/private secrets;
- cache/temporary import material;
- diagnostic logs.

## Platform behavior lessons

The official Android client supports different backend paths and contains code for Android service/platform integration. Future PVNetwork Android design should therefore treat backend selection, permission acquisition, background lifecycle and UI state as separate layers.

Android TV/Google TV is not proven by this Android phone/tablet codebase. PVNetwork must independently validate D-pad navigation, TV manifest/store requirements, focus states and lean-back UI behavior.

## License/reuse

Repository metadata reports Apache-2.0 for the reviewed application repository. The final reuse decision still requires dependency-level review and module-specific attribution/NOTICE analysis.

Current classification: `REUSE-CANDIDATE / ARCHITECTURE-REFERENCE`, not approved for wholesale copying.

## PVNetwork regression requirements derived from this source

- backend can change/fallback without UI-specific engine assumptions;
- profile store and preference store migrate independently;
- import/export round trips preserve supported fields;
- invalid configuration cannot corrupt the profile list;
- connection/service state survives expected activity recreation;
- settings changes are reflected through one state source;
- phone/tablet layouts remain functionally equivalent;
- Persian RTL and technical LTR tokens remain readable;
- Android TV behavior is tested separately rather than inferred from mobile support.

## Remaining gaps

- complete issue/release/community regression review;
- exact Android service/background behavior map;
- secure-secret/backup behavior audit;
- complete localization and accessibility inventory;
- dependency/SBOM review;
- Google Play and Android TV policy review at implementation/release time;
- real-device battery, sleep, network-switch and process-death testing.