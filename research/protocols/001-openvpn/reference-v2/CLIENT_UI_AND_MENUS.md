# OpenVPN — Client UI / Menus / Controls Reference

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH` — source-backed/product-doc menu domains are recorded; exact screenshots/control labels require version-pinned snapshots.

## Goal

Capture every major client UX so a PVNetwork developer understands:

- which functions users expect;
- which controls belong in Simple vs Advanced mode;
- which state/error flows recur across products;
- which options are engine/platform-specific;
- which source files implement them when public source exists.

Do not copy visual branding/assets.

---

# 1. OpenVPN Connect — official product behavior reference

Official documentation: current `openvpn.net/connect-docs/`.

## Core navigation/product areas

### Profiles / My Profiles

Functions documented by the product family include:

- list imported profiles/connections;
- connect/disconnect a profile;
- import/add profile;
- profile details/edit where supported;
- remove profile;
- authentication prompt;
- certificate/key selection depending on profile;
- server/profile identity display.

### Import / Add profile

Reference flows can include:

- import from file;
- import/provision from URL/server/account flow where supported;
- Access Server profile retrieval/provisioning;
- certificate import;
- handling profile errors/unsupported options.

PVNetwork should keep source type visible in metadata but unify final onboarding.

### Settings

Version/platform-dependent settings documented across current Connect products include categories such as:

- VPN protocol preference / connection transport behavior;
- connection timeout/retry;
- IPv6-related behavior;
- DNS fallback/security behavior;
- DCO-related control/compatibility where exposed;
- certificate/security preferences;
- proxy behavior;
- logging/troubleshooting;
- launch/startup/background behavior depending on OS.

Exact names/defaults/order must be captured from the selected Connect version before claiming exhaustive parity.

### Statistics / connection details

Expected connection information includes:

- connection state;
- session duration;
- bytes sent/received;
- server/profile identity;
- protocol/transport details;
- logs/diagnostics where exposed.

### Logs / troubleshooting

- connection log;
- error messages;
- troubleshooting/export/support behavior depending on platform.

### Authentication states

UI must accommodate:

- username/password;
- certificate/key;
- saved vs prompted credentials;
- token/OTP/MFA challenge;
- SSO/browser flow where provisioning/server requires it;
- expired/revoked/invalid certificate;
- user action required while connection engine is paused.

PVNetwork state machine should have `UserActionRequired/AuthChallenge`, not generic Error.

---

# 2. OpenVPN GUI for Windows — source-backed tray/reference

Pinned source:

`OpenVPN/openvpn-gui@7295bdc155e0d8d66dd53ab9bc4eb462e77bfa7f`

Existing v1 dossier:

`research/upstreams/openvpn-family/OPENVPN_GUI_WINDOWS.md`

## Tray/profile actions

Source-level menu concepts include:

- profile/connection selection;
- Connect;
- Disconnect;
- Reconnect where applicable;
- status/log/view connection information;
- edit/open configuration location;
- proxy/settings/options depending on version;
- About;
- Exit.

The exact menu is dynamically built from available profiles/groups and runtime state.

## Windows-specific settings

Pinned source contains registry/preferences/config-location behavior and Windows localization resources including a Persian resource file.

PVNetwork lessons:

- tray actions must reflect authoritative session state;
- profiles can be grouped without exposing file-system complexity;
- Windows technical tokens remain LTR inside Persian RTL UI;
- profile files and saved credentials require stronger product storage than legacy config-directory patterns.

---

# 3. ics-openvpn / OpenVPN for Android — source-backed UI

Pinned source:

`schwabe/ics-openvpn@ede0aa0b334b47941407599fef3d76da8b933edf`

Existing v1 dossier:

`research/upstreams/openvpn-family/ICS_OPENVPN_ANDROID.md`

## Profile list actions

Source-backed actions include:

- Add profile;
- Import profile/configuration;
- remote/Access Server-style import flow;
- sort/change sorting;
- select/start VPN;
- disconnect current VPN;
- edit profile;
- duplicate/add profile;
- dynamic shortcuts for recent profiles;
- dynamic Disconnect shortcut.

## Import flow

- Android/system document picker;
- fallback/internal file picker in some paths;
- remote server/profile import;
- parser/validation feedback.

## Profile editor/settings domains

The application source contains multiple profile-edit/settings fragments/screens for categories such as:

- basic endpoint/profile identity;
- authentication;
- routing;
- allowed apps/per-app behavior;
- DNS;
- advanced OpenVPN directives/options;
- certificate/key material;
- proxy-related behavior;
- generated config/viewing/logging.

Exact field-by-field inventory must be generated from the selected source tree/resources in the next refinement pass.

## Runtime UI

- connection status;
- logs/status;
- user-input/password challenge dialog;
- service notifications;
- Android shortcuts/system integration;
- TV/minimal-UI branches.

## Secure profile storage

Source uses AndroidX Security `MasterKey` + `EncryptedFile` for encrypted profile files in the reviewed UI path.

PVNetwork should preserve this product-level lesson independent of engine choice.

---

# 4. Tunnelblick — macOS reference

Pinned source:

`Tunnelblick/Tunnelblick@cc3cefa77912fc103831ef8517962be438a983d2`

Existing dossier:

`research/upstreams/openvpn-family/TUNNELBLICK_MACOS.md`

## Major UX concepts

- menu-bar connection list;
- connect/disconnect;
- configuration/profile management;
- settings/preferences;
- credentials/keychain interactions;
- connection log/details;
- configuration update/import;
- helper/elevated network operations;
- application/update/about/quit.

## PVNetwork macOS lesson

Normal user connectivity belongs in a compact menu-bar/main-window workflow; privileged network/helper logic must stay outside the ordinary UI process and secrets belong in Keychain/product secure storage.

Exact Tunnelblick menus/control labels require a pinned-version source/resource snapshot later.

---

# 5. Linux NetworkManager OpenVPN UI

Common desktop path:

`NetworkManager`

`+ NetworkManager OpenVPN plugin`

`+ desktop settings/editor`

## Typical connection editor domains

- Gateway/server;
- authentication type;
- user name/password;
- CA/cert/private key;
- advanced security/cipher/TLS controls;
- proxy/transport depending on plugin version;
- IPv4 routing;
- IPv6 routing;
- DNS;
- routes;
- connection auto-connect/permissions.

Exact UI differs between GNOME/KDE/NetworkManager versions. PVNetwork should learn the semantic grouping, not copy desktop-environment visuals.

## Import behavior

NetworkManager can import many `.ovpn` profiles but may not preserve every directive in an editable first-class field.

PVNetwork requirement:

- retain original source;
- record normalization loss;
- expose raw/advanced preserved directives when necessary;
- never silently drop security/routing semantics.

---

# 6. Pritunl Client — UX reference only

Pinned source:

`pritunl/pritunl-client-electron@69508329df8a55070d9a1758765064516bb42a3a`

Current public license restrictions make it reference-only unless separate rights are obtained.

Useful UX concepts:

- profiles/servers;
- import profile;
- connect/disconnect;
- SSO/OTP/auth prompt;
- server/profile status;
- logs;
- settings/tray behavior;
- automatic profile/server handling.

Do not copy code or visual identity.

---

# 7. PVNetwork OpenVPN Simple Mode — derived minimum

Based on all reference clients, normal user UI should contain only:

## Home / profile card

- profile/server name;
- location/label if known;
- protocol: OpenVPN;
- transport indicator if useful;
- Connect / Disconnect;
- status;
- duration;
- upload/download;
- latency/health where measured.

## Profile actions

- import/add;
- edit basic name/server/auth source;
- duplicate;
- export where safe;
- delete;
- view source/diagnostics only under Advanced.

## Authentication

- username;
- password;
- certificate/key identity;
- MFA/OTP/SSO challenge;
- remember/save choice governed by secure-storage policy.

## Error UI

Human-readable categories:

- profile unsupported;
- DNS/server unreachable;
- TLS certificate failure;
- authentication failed;
- certificate/key invalid;
- data cipher mismatch;
- route/DNS install failure;
- platform permission/driver failure;
- server timeout;
- user action required.

---

# 8. PVNetwork OpenVPN Advanced Mode

Only expose controls supported by the selected core/platform and validated against version metadata.

Possible groups:

### Connection

- UDP/TCP preference;
- remote endpoints/order;
- retry/timeout;
- proxy.

### TLS / Certificates

- CA/server-name policy;
- client identity;
- tls-auth/tls-crypt-related imported state;
- TLS policy when safe/necessary.

### Data Channel

- data cipher policy/read-only negotiated cipher;
- DCO state/fallback;
- MTU/MSS advanced compatibility;
- compression legacy warning.

### Routing

- full/split tunnel;
- accepted/pushed routes;
- local LAN exclusion policy;
- IPv6.

### DNS

- server-pushed DNS;
- override/custom DNS;
- leak-protection policy;
- split DNS where platform supports it.

### Apps / Platform

- per-app include/exclude where OS supports it;
- Always-On/kill-switch integration;
- background/reconnect.

### Diagnostics

- effective core version;
- server version if known;
- transport;
- TLS backend;
- DCO state;
- negotiated data cipher;
- assigned address;
- routes/DNS;
- sanitized log.

---

# 9. Exhaustive field-catalog schema

For each reference client/version, final v2 refinement should append a machine-readable-like table:

```text
Client:
Version:
Platform:
Screen/Menu:
Subsection:
Control:
Type:
Default:
Visible condition:
Core/platform mapping:
Secret?:
Reconnect required?:
Source/resource path or official-doc reference:
PVNetwork decision: KEEP / SIMPLE / ADVANCED / DIAGNOSTIC-ONLY / OMIT
```

This prevents copying obsolete menus across versions and makes UI design traceable.

## Remaining gaps

- exact current OpenVPN Connect version-specific screenshots/control labels on every platform;
- exhaustive ics-openvpn profile-editor field extraction;
- exhaustive OpenVPN GUI current tray/context menu labels;
- Tunnelblick exact current menu/preferences inventory;
- NetworkManager/GNOME/KDE field differences;
- accessibility/keyboard/TV/persian-RTL screenshots.
