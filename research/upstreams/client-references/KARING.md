# Karing — Developer Research Dossier

Pinned repository: `KaringX/karing@9a0e3b7736f6c8f407985cb697f669c93087b909`.

Research role: cross-platform Flutter client reference for profile import, large settings surface, synchronization/backup, localization, desktop/mobile integration and storage choices.

## License / reuse
The reviewed `LICENSE.md` states GPL version 3 or later and includes an additional condition preventing derivative works from using the Karing name or implying association without prior written consent.

PVNetwork classification: **REFERENCE-ONLY by default for a closed commercial product; direct code reuse requires a GPL-compatible distribution strategy and respect for the additional naming/association condition.**

## Complete source reference
Recursive tree:
`https://api.github.com/repos/KaringX/karing/git/trees/9a0e3b7736f6c8f407985cb697f669c93087b909?recursive=1`

Important Flutter source roots at the pinned revision:
- `lib/app/`
- `lib/builders/`
- `lib/i18n/`
- `lib/screens/`
- `lib/main.dart`

The repository also contains platform projects, plugins/services, assets, packaging/build files and localized README files including Persian documentation.

## Language/framework
Main application language is Dart/Flutter. `pubspec.yaml` at the pinned revision requires Dart >=3.12.2 and Flutter >=3.35.0 and declares extensive platform/network/UI/storage dependencies.

## Screen/UI map — evidence from `lib/screens/`
The source contains dedicated screen files for, among others:
- About
- Accessibility
- Add profile by file import
- Add profile by link/content
- Add profile by QR scan
- backup/automatic backup
- iCloud synchronization
- LAN synchronization
- WebDAV synchronization
- additional account/settings/network/profile screens in the same directory

This file-per-screen style provides a direct map for later exhaustive menu/navigation documentation. The complete recursive tree should be used to enumerate all screens rather than relying only on a README feature list.

## Import UX lessons
Separate source files exist for:
- file import
- link/content input
- QR scanning

PVNetwork lesson: universal import should be one normalized subsystem with multiple acquisition surfaces rather than several unrelated parsers owned by individual screens.

## Storage/security dependencies
The pinned `pubspec.yaml` includes:
- `sqlite_async` and `sqlite3` — structured local database capability
- `path_provider` — application filesystem locations
- `flutter_secure_storage` — platform-backed secure-storage abstraction
- crypto/cryptography/encrypt packages
- archive/share/open-file packages for data movement/export flows

Presence of a dependency does not prove every sensitive field uses it correctly. A later source audit must identify exact tables/files/keys and what is or is not stored in secure storage.

## Backup/synchronization surface
Pinned dependencies and screen files show explicit support paths for:
- iCloud storage
- WebDAV
- LAN synchronization
- automatic backup

PVNetwork lesson: backup/sync should be an optional service above the normalized profile database and must have explicit secret/privacy rules. Do not make cloud backup a prerequisite for local VPN operation.

## Desktop/platform integrations visible in dependencies
The application declares dependencies for:
- tray management
- hotkeys
- window management
- launch-at-startup
- single-instance behavior
- Windows registry access
- notifications
- quick actions
- device/network information

This is useful for mapping the difference between a shared Flutter UI and platform-specific desktop/mobile behavior.

## Localization
The source has `lib/i18n/`, Flutter localization dependencies and many localized project READMEs including `README_fa.md`. This makes Karing a useful translation/terminology reference.

PVNetwork rule: existence of Persian documentation does **not** prove correct RTL UI. Future research must inspect generated locale resources, text-direction behavior, mixed IP/URL strings and screenshots on a Persian locale.

## Privacy/observability flag
`pubspec.yaml` includes `sentry_flutter` and Sentry build tooling/configuration. This does not by itself prove what is transmitted in production, but it creates a mandatory privacy audit item: configuration, opt-in/default behavior, captured fields, redaction, DSN/runtime setup and Store privacy declarations must be inspected before drawing conclusions.

## Asset/package evidence
The Flutter asset list includes groups such as:
- dashboard/web assets
- text/data assets
- fonts
- images
- GeoSite/GeoIP/ACL/preset datasets

App-icon configuration references project logo assets. These are third-party brand/assets references only; do not copy them into PVNetwork without rights review.

## PVNetwork lessons
- use multiple import surfaces over one normalized importer;
- separate structured local storage from secure secret storage;
- backup/sync must have explicit privacy boundaries;
- build cross-platform desktop affordances as modular integrations;
- accessibility deserves a first-class settings/screen path;
- localization resources and UI directionality need independent testing;
- third-party crash reporting must be audited rather than added casually.

## Remaining research required
- enumerate every file in `lib/screens/` into a full navigation/menu map;
- inspect routing/navigation construction in `main.dart` and app modules;
- identify exact SQLite schemas and filesystem paths;
- trace every use of secure storage and credential persistence;
- map profile/subscription parsing and internal normalized models;
- map underlying engine/core integration and version management;
- review issues/PRs/releases for crashes, import failures, sync corruption, performance, platform and RTL/localization problems;
- audit tests/CI;
- inspect Sentry runtime configuration/privacy behavior;
- inventory screenshots/assets as links plus license status;
- audit Store/package configuration per platform.

Status: `IN-RESEARCH`. No PVNetwork implementation/support is implied.