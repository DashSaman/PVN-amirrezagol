# OpenConnect GUI — Screen, Profile and Storage Map

Research date: 2026-08-14

State: `IN-RESEARCH`; UI/architecture reference only.

## Current upstream baseline

Canonical project: `https://gitlab.com/openconnect/openconnect-gui`

Current main branch remains active in 2026. Latest stable release reviewed: **v1.6.2**, tag/commit `c89e5dc0`, released 2024-06-06.

The old GitHub repository is archived. Its pinned historical tree is still useful for file-to-feature archaeology but must not be mistaken for the current canonical source:

`openconnect/openconnect-gui@1b9bc0ae61496a871dbee955ec2443e46d411ed4`

Historical recursive tree: `aa8bbc95f31bb84c034093e6d1f01c14805fb482`.

## Product UI shape from current upstream

Current upstream states that the primary tasks should be one click away:

- connect to a new server;
- connect to an existing server/profile;
- disconnect;
- view logs.

Current release history adds evidence for:

- quick-connect flow without forcing full profile creation;
- advanced profile setup/editing;
- dynamic protocol selection including GlobalProtect;
- per-profile interface name;
- notification/tray connection actions;
- profile proxy behavior;
- update notification;
- log viewing;
- certificate/key selection and certificate pinning behavior.

## Historical file-to-screen map

The archived source tree provides a concrete mapping of the original Qt application structure. Current main should be checked before any code-level reuse decision, but these boundaries are useful architectural evidence.

### Main window

Historical files:

- `src/dialog/mainwindow.cpp`
- `src/dialog/mainwindow.h`
- `src/dialog/mainwindow.ui`

Responsibilities observed from project/release history and tree structure:

- current/selected profile presentation;
- connect/disconnect state;
- profile list/action entry points;
- tray/notification behavior;
- access to logs and profile management;
- application/window lifecycle and connection-state presentation.

### New / quick profile

Historical files:

- `src/dialog/NewProfileDialog.cpp`
- `src/dialog/NewProfileDialog.h`
- `src/dialog/NewProfileDialog.ui`
- `src/dialog/VpnProtocolModel.cpp`
- `src/dialog/VpnProtocolModel.h`

Release evidence confirms that the modern upstream keeps a quick profile/setup path and protocol selection as explicit product concepts.

PVNetwork lesson: preserve a fast first-connection path while keeping protocol-specific details available through an advanced editor.

### Advanced profile editor

Historical files:

- `src/dialog/editdialog.cpp`
- `src/dialog/editdialog.h`
- `src/dialog/editdialog.ui`

The editor is the natural boundary for endpoint/protocol/certificate/key/proxy/transport and other profile-specific options. Release history shows that newer upstream versions added options such as protocol selection, interface naming and alternate connection-script behavior.

PVNetwork lesson: never expose a raw engine option dump. Group fields by user intent and validate unsupported combinations through the adapter capability model.

### Log window

Historical files:

- `src/dialog/logdialog.cpp`
- `src/dialog/logdialog.h`
- `src/dialog/logdialog.ui`
- `src/FileLogger.cpp`
- `src/logger.cpp`

OpenConnect GUI treats logs as a first-class user task. PVNetwork should do the same, while adding stronger secret-redaction and support-bundle controls.

### Authentication / prompt dialogs

Historical files:

- `src/dialog/MyInputDialog.cpp`
- `src/dialog/MyMsgBox.cpp`
- `src/dialog/MyCertMsgBox.cpp`

These demonstrate the old frontend pattern of specialized dialogs for prompts/trust decisions. Current SSO issue history proves this is insufficient for every modern enterprise flow.

PVNetwork should use a generic Auth Challenge Model plus a platform Browser/SSO service rather than an expanding collection of protocol-specific modal dialogs.

## Profile and configuration storage

Historical source identifies:

- `src/server_storage.cpp`
- `src/server_storage.h`
- `src/cryptdata.cpp`
- `src/cryptdata.h`
- certificate/key helper classes.

A maintainer explicitly clarified in upstream issue discussion that OpenConnect GUI profiles are stored in the GUI's own format and are **not** interchangeable with OpenConnect CLI `--config` files.

On macOS, upstream issue discussion identifies the Qt application preference domain around `io.github.openconnect.OpenConnect-GUI`, with platform caching meaning a plist file is not always a reliable direct source of truth.

### PVNetwork storage rule

Keep these separate:

1. PVNetwork canonical profile schema;
2. portable import/export representation;
3. protected secret store;
4. runtime OpenConnect adapter configuration;
5. transient SSO/session values;
6. logs/support exports.

Do not make the engine's CLI config format the product database format.

## Password / remembered-auth UX

A current upstream issue documents that the user-visible option named **Batch Mode** controls password remembering and has been confusing enough that maintainers discussed splitting it into clearer options.

PVNetwork should expose explicit policies instead:

- remember username/account;
- remember password/secret;
- remember group/realm;
- remember non-secret form choices;
- suppress/remember informational banners only where appropriate;
- clear saved credentials.

The UI should state which values are protected secrets and which are ordinary profile metadata.

## SSO gap

A current GUI issue shows a real failure mode where libopenconnect can request external browser authentication but the GUI has no SSO handler.

PVNetwork requirement:

- Enterprise Adapter advertises capabilities;
- platform Browser/SSO service owns browser lifecycle;
- session-specific result is returned through a typed callback;
- lack of browser capability is surfaced as a specific capability error, not generic authentication failure.

## Assets / resources

Historical source tree contains application icons, connection-state imagery, profile/edit/new/delete icons, macOS-specific status assets, installer images and Qt resource manifests.

These are **reference assets only** unless exact reuse rights and branding intent are approved. PVNetwork must use its own official supplied branding.

## Packaging evidence

Current upstream has:

- Windows installer assets in releases;
- NSIS packaging area;
- Windows signing work reflected in v1.6.2;
- Qt/CMake build system;
- macOS bundle support;
- MinGW/RPM packaging material;
- CI/release automation.

Current upstream states Windows 10+ and macOS 10.12+ for the GUI. These are upstream compatibility statements, not PVNetwork minimum-OS decisions.

## Current-main vs historical-source caution

The archived GitHub file map is from an older source snapshot. Current canonical GitLab main is active and has changed since then. Therefore:

- use the archived tree to understand long-lived component boundaries;
- use current GitLab main/release/issues for feature/current-behavior claims;
- do not copy old source under the assumption it matches v1.6.2/current main;
- re-pin exact current source files before any implementation/reuse decision.

## PVNetwork menu/screen lessons

The reference suggests this minimum enterprise-client navigation model:

- Home / connection status;
- Profiles;
- Quick connect;
- Add/edit profile;
- Authentication challenge/browser flow;
- Logs/diagnostics;
- Settings/updates;
- tray/menu-bar quick actions.

This is not the final PVNetwork menu design; it is an evidence-backed reference baseline.

## Regression requirements

- quick-connect and advanced-profile paths produce semantically equivalent valid canonical profiles;
- tray/menu state cannot disagree with main-window state;
- close/minimize behavior cannot terminate an active tunnel accidentally;
- stored profile data remains independent from runtime engine config;
- remembered-secret controls have explicit separate meanings;
- app crash does not leave stale routes/network artifacts;
- SSO/browser-required flows are capability-checked before connection attempt;
- profile import/export never silently loses protocol-specific fields;
- logs and UI errors redact credentials/session tokens.

## Remaining gaps

- pin a complete recursive tree for current GitLab main and v1.6.2 source archive when tooling permits;
- verify current source paths for all historical components;
- screen-by-screen current Qt widget/action inventory;
- exact Windows protected-storage behavior in current main;
- exact macOS credential/protected-storage behavior in current main;
- current issue triage by UI/profile/storage/platform category;
- screenshot catalog and asset reuse-rights classification;
- current Windows signing/update and macOS notarization details.
