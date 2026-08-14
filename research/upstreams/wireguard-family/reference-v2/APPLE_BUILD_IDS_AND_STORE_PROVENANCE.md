# WireGuard / AmneziaWG Apple — Build IDs and Store Provenance

Review date: 2026-08-14

Scope: source-level Xcode target identity and public App Store metadata for entries 002/003. This file deliberately separates **source configuration evidence** from **shipped binary provenance**.

## 1. WireGuard Apple source pin

Pinned source: `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1` (GitHub mirror; canonical project is the zx2c4 repository).

### Developer configuration model

`Sources/WireGuardApp/Config/Developer.xcconfig.template` does not hard-code the production application identifier. It requires the builder to supply:

- `DEVELOPMENT_TEAM`
- `APP_ID_IOS`
- `APP_ID_MACOS`

The template explicitly says the app ID should be created with Network Extensions capability.

### Xcode target identifier derivation

Pinned `WireGuard.xcodeproj/project.pbxproj` shows:

- iOS containing app: `PRODUCT_BUNDLE_IDENTIFIER = $(APP_ID_IOS)`
- iOS packet-tunnel extension: `PRODUCT_BUNDLE_IDENTIFIER = $(APP_ID_IOS).network-extension`
- macOS containing app: `PRODUCT_BUNDLE_IDENTIFIER = $(APP_ID_MACOS)`
- macOS packet-tunnel extension: `PRODUCT_BUNDLE_IDENTIFIER = $(APP_ID_MACOS).network-extension`
- macOS login helper: `PRODUCT_BUNDLE_IDENTIFIER = $(APP_ID_MACOS).login-item-helper`

Together with the previously reviewed entitlements, this establishes the source-level containing-app / packet-tunnel extension / shared-App-Group architecture. It does **not** reveal the production App Store signing values because the source intentionally leaves those developer values external.

### Public Store metadata

The Apple App Store listing reviewed on 2026-08-14 identifies:

- app: **WireGuard**
- App Store item ID: `1441195209`
- developer: **WireGuard Development Team**
- iPhone/iPad availability and a separate Mac listing under the same developer account
- advertised tunnel import paths include archives/files and QR code, plus creation from scratch.

This is useful distribution/UI evidence, but the public listing does not provide a source commit, build manifest, embedded entitlement dump, or reproducible-build attestation.

**Conclusion:** Store presence is authoritative distribution evidence, but current research cannot prove that App Store item `1441195209` was built from the pinned commit. Do not infer source correspondence from matching product name alone.

## 2. AmneziaWG Apple source pin

Pinned source: `amnezia-vpn/amneziawg-apple@e5410a539f28b8ce5dd1d060c45e4fa555e9a210`.

### Concrete source build identity

Unlike upstream WireGuard's template-only builder identity, this pinned fork contains `Sources/WireGuardApp/Config/Developer.xcconfig` with:

- `DEVELOPMENT_TEAM = X7UJ388FXK`
- `APP_ID_IOS = org.amnezia.awg`
- `APP_ID_MACOS = org.amnezia.awg`

Pinned `Version.xcconfig` contains:

- `VERSION_NAME = 3.0.1`
- `VERSION_ID_IOS = 0`
- `VERSION_ID_MACOS = 14`
- `MARKETING_VERSION = $(VERSION_NAME)`

The pinned Xcode project resolves the containing app to `org.amnezia.awg` and the packet-tunnel extension to `$(APP_ID_IOS).network-extension` / `$(APP_ID_MACOS).network-extension`.

### Public Store metadata and mismatch

The Apple App Store listing reviewed on 2026-08-14 identifies:

- app: **AmneziaWG**
- App Store item ID: `6478942365`
- developer shown by Apple: **Privacy Technologies**
- current listed version: **2.0.2**
- version history includes `2.0.0` with AWG2 support and earlier 1.x releases.

The pinned source says marketing version `3.0.1`, while the public Store listing currently exposes `2.0.2`. Therefore this pinned source **cannot be claimed as the source provenance of the current Store binary** without additional release/build evidence.

The bundle identifier `org.amnezia.awg` is strong source-level identity evidence but still does not prove that Apple's current binary corresponds byte-for-byte or commit-for-commit to this pin.

## 3. PVNetwork release-gate implications

For Apple client provenance, source review alone is insufficient. A future product/release audit should preserve at least:

1. exact source commit/tag used for the build;
2. generated Xcode build settings (`PRODUCT_BUNDLE_IDENTIFIER`, team, version/build number);
3. archive/export receipt;
4. signed app and extension entitlement dump;
5. App Store/TestFlight build number correspondence;
6. dependency/SBOM snapshot for the archived build;
7. notarization/App Store processing identifiers where applicable.

Do not mark Store provenance complete from a public listing plus a similar repository.

## 4. Gate result

- WireGuard Apple target/bundle derivation: **SOURCE-COMPLETE**.
- AmneziaWG Apple concrete team/app IDs and pinned marketing version: **SOURCE-COMPLETE**.
- Public App Store product identity: **EVIDENCED**.
- Pinned-source -> shipped Store binary correspondence: **BLOCKED / NOT PROVEN**.
- Signed-entitlement / archive / TestFlight execution receipts: **BLOCKED_EXTERNAL** in the current environment.
