# ics-openvpn / OpenVPN for Android — Source / Storage / UI Reference

Research date: 2026-08-14

State: `IN-RESEARCH / REFERENCE-ONLY` for a closed PVNetwork product because application source is GPLv2 with additional terms.

## Source pin

Repository: `schwabe/ics-openvpn`

Pinned commit:

`ede0aa0b334b47941407599fef3d76da8b933edf`

Root/source licensing is GPLv2 with additional terms referenced by the project source headers/documentation. Treat code as reference-only for a closed PVNetwork application unless the chosen distribution model and legal review permit reuse.

## Why this project matters

This is one of the strongest public Android references for OpenVPN product architecture because it includes:

- Android app/service lifecycle;
- profile model and persistence;
- profile editor/import UI;
- OpenVPN engine integration;
- Always-On/Android TV/product behavior;
- certificate/credential handling;
- logs/diagnostics;
- external app/AIDL integration.

It should be studied separately from OpenVPN 3 Core and OpenVPN Connect product UX.

## Source-layer separation

Current pinned tree/source exposes distinct areas for:

- application/UI activities/fragments;
- `VpnProfile` domain/config model;
- `ProfileManager` persistence/indexing;
- `OpenVPNService` runtime/service lifecycle;
- parser/config converter/import;
- native/OpenVPN core components;
- settings/preferences;
- logging/status;
- external application/AIDL API;
- managed/minimal UI variants.

### PVNetwork lesson

Do not bind Android UI directly to an OpenVPN process/CLI. Use:

`PVNetwork UI/Profile`

`-> canonical PVProfile`

`-> OpenVPN Adapter`

`-> Android VPN/service layer`

`-> selected OpenVPN core/runtime`

with product-owned secure storage and state.

## Profile persistence

Pinned `ProfileManager.java` keeps profiles as UUID-addressed objects, maintains an in-memory map and stores a list of profile UUIDs in SharedPreferences.

Profile objects themselves are serialized to files in internal application storage.

Important behavior includes:

- explicit profile versioning/migration concepts;
- add/save/remove lifecycle;
- always-on profile selection metadata;
- management of user-edited profiles independently from currently active session state.

### PVNetwork lesson

Stable profile identity should be independent of display name/file name and should survive subscription/import/order changes.

## Profile encryption

Pinned UI implementation `ProfileEncryption.kt` uses AndroidX Security:

- `MasterKey` with `AES256_GCM`;
- `EncryptedFile` with `AES256_GCM_HKDF_4KB`.

The encrypted profile-file path is therefore a valuable secure-storage reference.

### PVNetwork rule

Do not store OpenVPN passwords/private-key material as ordinary plaintext JSON/config files.

Use:

- canonical profile metadata in product storage;
- Android Keystore-backed protected secret/file storage;
- explicit export/backup policy for credentials/certificates/private keys.

## Profile list / main OpenVPN UI

Pinned `VPNProfileList.kt` provides source-backed current menu/action evidence.

### Primary profile-list actions

- Add profile
- Import profile/configuration
- Change sorting
- Import Access Server/remote-style profile flow

### Profile interactions

The list supports:

- start selected VPN;
- disconnect current active profile;
- edit profile;
- duplicate/add profile;
- dynamic Android shortcuts for recent profiles;
- dynamic Disconnect shortcut;
- sort by name or LRU/recent usage.

### Import paths

Source distinguishes:

- Android/system file picker for `.ovpn`-style configuration;
- fallback/internal file chooser;
- remote/Access Server import flow.

PVNetwork should similarly keep file import, URL/subscription/account import and manual profile creation as explicit source types.

## Android shortcuts / external controls

Current source generates dynamic launcher shortcuts for:

- recent VPN profiles;
- disconnect action.

The wider project also has external/AIDL control surfaces and quick/system integration.

PVNetwork requirement:

Every exported/control surface must be included in permission, authorization and state-synchronization tests. External commands must not bypass product security policy or create UI/service state drift.

## Android TV evidence

Pinned profile-list source contains TV-specific behavior comments/branches: TV builds use a minimal UI path and avoid some normal-phone notification-permission prompting.

This is useful reference evidence but does not prove exhaustive TV quality.

PVNetwork later needs real Android TV/Google TV tests for:

- D-pad-only navigation;
- focus traversal;
- profile import/pairing without touch keyboard dependence;
- connect/disconnect status readability;
- Persian RTL at 10-foot distance;
- background/Always-On behavior.

## User-input / challenge flow

Pinned profile-list source observes connection-state events and can show a password/challenge dialog when the service enters a waiting-for-user-input state.

PVNetwork lesson:

Core/service state machine needs an explicit **UserActionRequired/AuthChallenge** state rather than treating authentication prompts as generic connection errors.

## Service/UI state ownership

The profile list registers as a VPN state listener and derives the current active profile/status from the shared service/status layer.

PVNetwork should preserve the principle:

- one authoritative connection/session state;
- UI subscribes to it;
- shortcuts/tiles/services update through the same state model;
- no page independently guesses connection status from process existence.

## OpenVPN profile format vs product storage

`.ovpn` is an important interoperable input/export format. It is not necessarily the best internal product storage format.

PVNetwork should preserve:

1. original imported `.ovpn` source where safe/useful;
2. normalized canonical product model;
3. protected reusable credentials/keys/certificates;
4. generated runtime OpenVPN configuration;
5. transient session/auth state.

Do not make user-owned product data depend solely on one engine's raw config text.

## Logs / diagnostics

The source has dedicated VPN status/logging infrastructure and UI. It is useful as a reference for a user-visible troubleshooting surface.

PVNetwork must improve/support:

- secret redaction;
- sanitized support bundles;
- diagnostic IDs/category mapping;
- distinction between platform permission, profile parse, authentication, TLS, route/DNS and server failures.

## GPL/reuse decision

The application code should be treated as **REFERENCE-ONLY by default** for a closed commercial PVNetwork application.

Architecture and behavior worth reimplementing independently include:

- UUID profile identity;
- secure encrypted profile files;
- Android VPN/service/UI state separation;
- user-action-required connection state;
- file/remote/manual import distinction;
- TV/shortcut integration concepts;
- logs/status surface.

For actual OpenVPN connectivity, evaluate OpenVPN 3 Core / compatible reusable libraries separately.

## Remaining v1 gaps

- exact current OpenVPN core/native version embedded in this pin;
- full Android settings/profile-editor menu field map;
- current manifest/permissions/API-level/store audit;
- current CI/test/build/package map;
- current issues by Android version/Always-On/background/network change;
- complete screenshot/assets/accessibility audit;
- exact backup/export behavior and all secret classes.

These can remain explicit at family v1 handoff; final Android implementation/device certification is future work.
