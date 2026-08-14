# v2rayNG — Current Android Client UI / Menu Map (v1 Research)

Research date: 2026-08-14

State: `IN-RESEARCH / REFERENCE-ONLY` because application code is GPLv3.

This file is a source-backed menu/screen map for the pinned current client. The later `COMPLETE-REFERENCE-v2` campaign must expand it screen-by-screen, field-by-field, screenshot-by-screenshot and version by version.

Pinned source:

`2dust/v2rayNG@e8a82d9810ca1cf97a3cc8a9b9525a9f21955807`

## Primary product shell

`MainActivity` renders a Compose `MainScreen` and handles the main product actions.

Primary connection actions include:

- start/stop service;
- test current server;
- restart service;
- locate selected server;
- select a server/profile;
- edit a profile;
- share a profile/share-link;
- share full generated content/configuration.

## Main drawer

Pinned `MainDrawer.kt` defines these destinations in current order:

### Primary group

1. **Subscriptions**
2. **Per-App Proxy**
3. **Routing**
4. **User Assets**
5. **Settings**

### Secondary group

6. **Promotion**
7. **Logcat**
8. **Check Update**
9. **Backup / Restore**
10. **About**

PVNetwork lesson:

- v2rayNG has broad expert-oriented navigation;
- PVNetwork should keep comparable advanced capabilities but can simplify normal-user navigation with Simple/Advanced mode;
- Promotion/third-party marketing should not be copied as a structural requirement.

## Add / import actions

Current `MainActivity` has distinct import paths:

### Import QR code

Opens QR scanner and passes scanned content to batch import.

### Import clipboard

Reads current clipboard text and passes it to batch import.

### Import local configuration/content

Uses a file chooser/content URI and passes file text to batch import.

### Manual creation

Routes to dedicated editors by selected configuration type.

Current dedicated/manual editor destinations include:

- Policy Group
- Proxy Chain
- VMess
- VLESS
- Shadowsocks
- SOCKS
- HTTP
- Trojan
- WireGuard
- Hysteria2
- fallback/custom HTTP-created path for other selected config types

### Edit existing profile

Current profile type determines the editor:

- custom config;
- policy group;
- proxy chain;
- VMess;
- VLESS;
- Shadowsocks;
- SOCKS;
- HTTP;
- Trojan;
- WireGuard;
- Hysteria2.

## Export / sharing actions

Current source distinguishes:

- **Share to Clipboard** — profile/share-link representation;
- **Share Full Content** — generated full content/configuration.

PVNetwork should retain this semantic distinction and use explicit labels.

## Main destination activities from manifest/source

Current Android manifest/source declares screens including:

- `MainActivity`
- `ServerVmessActivity`
- `ServerVlessActivity`
- `ServerShadowsocksActivity`
- `ServerSocksActivity`
- `ServerHttpActivity`
- `ServerTrojanActivity`
- `ServerWireguardActivity`
- `ServerHysteria2Activity`
- `ServerCustomConfigActivity`
- `ServerGroupActivity`
- `ServerProxyChainActivity`
- `SettingsActivity`
- `PerAppProxyActivity`
- `AppPickerActivity`
- `ScannerActivity`
- `LogcatActivity`
- `RoutingSettingActivity`
- `RoutingEditActivity`
- `SubSettingActivity`
- `SubEditActivity`
- `UserAssetActivity`
- `UserAssetUrlActivity`
- shortcut scanner/switch/start/stop activities
- URL-scheme/import activity
- update screen
- backup screen
- about screen
- Tasker integration activity.

This is evidence of the broad functional surface; later v2 work must map every interactive field and submenu individually.

## Settings — current top-level groups

Pinned `SettingsActivity` currently defines collapsible groups for:

1. **UI Settings**
2. **VPN Settings**
3. **Core Settings**
4. **Mux Settings**
5. **Fragment Settings**
6. **Observatory Settings**
7. **Advanced Settings**
8. **Mode Settings**

### UI Settings observed

Current state/options include concepts such as:

- speed display;
- confirmation before remove;
- double-column display;
- show-all groups;
- application language;
- light/dark/system-style theme mode.

### VPN Settings observed

Current state/options include concepts such as:

- IPv6 enable;
- prefer IPv6;
- local DNS;
- fake DNS;
- VPN DNS;
- append local HTTP proxy;
- bypass-LAN behavior;
- VPN interface-address selection;
- MTU;
- hev/tun2socks mode and related logging/timeout settings.

### Core Settings observed

Current settings state includes concepts such as:

- sniffing;
- route-only behavior;
- remote DNS;
- domestic DNS;
- DNS hosts;
- core log level;
- outbound domain-resolution method;
- local proxy enablement;
- local SOCKS port;
- dynamic SOCKS port;
- SOCKS username/password;
- SOCKS UDP;
- local-network proxy sharing.

### Mux settings observed

Current source holds controls for:

- mux enablement;
- mux concurrency;
- XUDP concurrency;
- XUDP/QUIC policy.

### Fragment settings observed

Current source contains controls for:

- fragmentation enablement;
- packet category;
- fragment length;
- interval;
- maximum split behavior.

These settings are technically advanced. PVNetwork should not expose all of them in Simple Mode.

### Observatory settings observed

Current source contains least-ping/least-load style intervals/method/sampling/timeout settings.

PVNetwork should model server selection/testing as product concepts rather than exposing raw core terminology unless in Advanced mode.

### Advanced settings observed

Current state variables include:

- boot/start behavior;
- delay-test URL;
- real-ping concurrency;
- remote IP-information URL;
- and additional source-defined expert settings.

### Mode settings observed

Current source distinguishes modes including VPN and alternative run modes, with root-mode/LAN-sharing related controls.

PVNetwork must carefully separate normal consumer VPN mode from privileged/root/debug modes and Store-safe builds.

## Per-app proxy UI

Dedicated destination:

- `PerAppProxyActivity`
- supporting `AppPickerActivity`.

Pinned VPN service supports both allow-list and bypass/disallow behavior.

PVNetwork v2 menu research must record:

- enable/disable toggle;
- include vs bypass mode;
- app selection/search;
- system/non-launchable app visibility;
- package visibility requirements;
- own-app handling;
- Android version limitations.

## Routing UI

Dedicated screens:

- `RoutingSettingActivity`
- `RoutingEditActivity`

Assets/source also contain predefined routing profiles.

PVNetwork should keep routing as a separate product module instead of embedding route rules inside each endpoint editor.

## Subscription UI

Dedicated screens:

- `SubSettingActivity`
- `SubEditActivity`

Background update logic exists separately through `SubscriptionUpdater`.

PVNetwork v2 research must capture exact current fields, including URL, remarks, update behavior, user-agent/header options if present, refresh intervals and grouping behavior.

## User Assets

Dedicated screens:

- `UserAssetActivity`
- `UserAssetUrlActivity`

These are evidence that external GeoData/asset management is surfaced to advanced users.

PVNetwork should make asset provenance/update/security visible, but avoid making normal users manage low-level asset files unless necessary.

## Logs / diagnostics

Dedicated `LogcatActivity` exists. Core/service source also produces extensive logs.

PVNetwork improvement:

- split user-friendly diagnostics from developer logs;
- redact secrets/generated configs;
- provide exportable sanitized support bundle;
- do not make raw logcat the primary troubleshooting experience.

## Backup / restore

Dedicated backup activity exists, with WebDAV-related implementation elsewhere in the codebase.

PVNetwork improvement:

- separate settings/profile backup from credential/private-key export;
- encrypt sensitive backups;
- make remote-sync privacy explicit.

## Update / About

Dedicated check-update and About screens exist.

PVNetwork Store builds must not implement an updater that conflicts with Store rules; direct-distribution desktop builds can use a separate signed updater architecture where permitted.

## Shortcut / integration surfaces

Pinned manifest includes:

- Android launcher shortcuts;
- Quick Settings tile;
- app widget;
- Tasker integration;
- boot receiver;
- share/view URL-scheme import path;
- start/stop/switch shortcut activities.

These are valuable power-user features but significantly expand lifecycle/exported-component testing.

PVNetwork should only expose such integrations deliberately and audit every exported component/permission.

## Android TV indicators

Current manifest includes Leanback launcher and TV banner support and does not require touchscreen. Main activity handles controller Button-B as Back/background behavior.

This is not a full TV menu audit. Later v2 research must test actual D-pad focus and layout for every screen.

## UI architecture lessons for PVNetwork

### Keep

- fast connect/disconnect path;
- profile list/grouping;
- QR/clipboard/file/manual import;
- separate subscriptions/routing/per-app/logs/settings;
- dedicated protocol editors when advanced options matter;
- separate full-config vs share-link export;
- quick settings/TV concepts as optional platform integrations.

### Improve

- Simple Mode should hide mux/fragment/core-specific details;
- route/DNS/core state should be normalized across engines;
- secure credentials should not be ordinary profile fields;
- user-facing errors should be higher-level than raw engine messages;
- Store-sensitive/root features should live in separate capability/build channels;
- Persian/RTL should be first-class and tested, not only translated strings.

## v2 exhaustive menu backlog

`COMPLETE-REFERENCE-v2` must later create or extend a full field-level map for:

- main server card/actions;
- all add/import dialogs;
- every protocol editor field;
- every Settings group/control and dependency/enablement rule;
- subscriptions;
- routing/rule editor;
- per-app picker;
- user assets;
- logs;
- backup/restore;
- update/about;
- QR scanner;
- shortcuts/Tasker/widget/tile;
- Android TV focus/remote behavior;
- every visible error/empty/loading state;
- Persian/English screenshots/reference URLs and source component mapping.

Current file is a substantial v1 map but is not yet the v2 exhaustive menu gate.
