# WireGuard Apple Platforms — Developer Dossier

Pinned source: `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`.

Research state: `IN-RESEARCH`.

## Source architecture

The pinned tree separates Apple integration into `Sources/WireGuardApp/` (UI/product), `Sources/WireGuardNetworkExtension/` (packet-tunnel provider), `Sources/WireGuardKit/` (adapter/library) and `Sources/Shared/` (models, keychain, logging/utilities). This supports an app + NetworkExtension + adapter/shared-model architecture rather than a generic subprocess wrapper.

## NetworkExtension boundary

`PacketTunnelProvider.swift` subclasses `NEPacketTunnelProvider` and owns a `WireGuardAdapter`. The product boundary is:

`App UI/state -> Apple tunnel manager -> NetworkExtension -> protocol adapter/core`

The UI must not directly own packet-tunnel internals.

## Protected profile/secret storage

`Sources/Shared/Keychain.swift` uses Apple Security/Keychain APIs and persistent references. Imported text is therefore an interchange format, not a suitable long-term persistence model. Deletion/migration must clean stale references and diagnostics must not expose protected material.

## Configuration/model evidence

The shared tree includes `TunnelConfiguration+WgQuickConfig.swift`, `NETunnelProviderProtocol+Extension.swift`, and tunnel-management models including `TunnelsManager.swift`. Canonical parsing should remain separate from Apple persistence and extension representation.

## Import/export and launch-path evidence

### Official WireGuard Apple — exact pinned import source

At pinned mirror commit `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`, the iOS import paths are now source-pinned rather than inferred only from user documentation:

1. `Sources/WireGuardApp/UI/iOS/ViewController/TunnelsListTableViewController.swift`
   - `addButtonTapped` exposes both **Import file** and **Scan QR code** actions.
   - `presentViewControllerForFileImport()` creates `UIDocumentPickerViewController` accepting `com.wireguard.config.quick`, text and ZIP archive document types.
   - `documentPicker(...didPickDocumentsAt:)` passes selected URLs to `TunnelImporter.importFromFile(...)`.
   - `presentViewControllerForScanningQRCode()` presents `QRScanViewController`; the `QRScanViewControllerDelegate` callback receives a parsed `TunnelConfiguration` and adds it through `tunnelsManager.add(...)`.
2. `Sources/WireGuardApp/UI/TunnelImporter.swift`
   - `.zip` inputs are expanded through `ZipImporter.importConfigFiles`.
   - non-ZIP inputs are treated as config text, read from the URL, and parsed through `TunnelConfiguration(fromWgQuickConfig:called:)` before `tunnelsManager.addMultiple(...)`.
3. `Sources/WireGuardApp/UI/iOS/ViewController/MainViewController.swift`
   - `importFromDisposableFile(url:)` routes externally opened disposable files into the same `TunnelImporter.importFromFile(...)` path and deletes the disposable copy afterward.

Pinned anchors:
- <https://github.com/WireGuard/wireguard-apple/blob/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1/Sources/WireGuardApp/UI/iOS/ViewController/TunnelsListTableViewController.swift>
- <https://github.com/WireGuard/wireguard-apple/blob/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1/Sources/WireGuardApp/UI/TunnelImporter.swift>
- <https://github.com/WireGuard/wireguard-apple/blob/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1/Sources/WireGuardApp/UI/iOS/ViewController/MainViewController.swift>

This closes the earlier **exact file/archive/QR import source-path gap** for the pinned official WireGuard Apple mirror. It does **not** prove current App Store binary equivalence, and it does not establish QR export.

Important evidence boundary: QR generation is generally a provisioning-side concern; seeing a QR provisioning workflow does not imply the Apple app exports QR codes. PVNetwork must separately certify import and export directions.

### Standalone AmneziaWG Apple fork

`amnezia-vpn/amneziawg-apple` is a fork/reference for AWG on Apple platforms. A current 2026 feature request against the Amnezia project documents the standalone iOS AWG app's existing import paths as:

- receive/open `.conf`, then use the share sheet into AmneziaWG;
- scan a QR code from inside AmneziaWG.

The same request proposes a new `amneziawg://` URL scheme precisely because the standalone app does **not** currently provide the requested one-tap scheme import. Therefore PVNetwork must not claim deep-link import support for standalone AmneziaWG Apple today. The proposal references the existing file-import route `mainVC.importFromDisposableFile(url:)`, which is useful source-level evidence for disposable-file ingestion but not evidence of a registered custom scheme.

Evidence anchor: <https://github.com/amnezia-vpn/amnezia-client/issues/2498> (opened 2026; feature request, not implemented capability).

A separate 2026 Amnezia issue reports that standalone `.conf` import succeeds while a hand-generated QR payload can fail with error 900. That issue is unresolved evidence about payload-format ambiguity, not proof that QR import itself is absent. PVNetwork must use source-pinned parser evidence and real-device receipts before specifying a generated standalone AWG QR format.

### Deep-link decision

| Apple surface | File import | QR import | Custom URL/deep-link import | Export |
|---|---|---|---|---|
| official WireGuard Apple | **source-pinned** (`UIDocumentPicker` -> `TunnelImporter`) | **source-pinned UI path** (`QRScanViewController` -> parsed `TunnelConfiguration`) | **not claimed** | exact file/share export path still to pin |
| standalone AmneziaWG Apple | `.conf` via open/share path documented | documented existing path; exact parser/payload source still to pin | **NOT CURRENTLY CLAIMED; feature requested** | exact export path still to pin |
| main Amnezia client | separate codebase; do not inherit standalone behavior | separate audit | `vpn://` behavior is separate code and requires its own source audit | separate audit |

This distinction prevents a common research error: capability in the main Amnezia client must not be silently attributed to the standalone AmneziaWG Apple fork.

## Current-source freshness warning

The GitHub WireGuard Apple mirror's visible commit history tops out at the pinned app source revision, while open pull requests in 2026 include fixes such as transient keychain tunnel-loss behavior and IPv6 endpoint preference. This means the pinned source is valuable architecture evidence but is **not proof that every open 2026 regression is resolved in a shipped build**. PVNetwork release certification must check the actual App Store/build revision and unresolved upstream patches at release time.

Source anchors:
- <https://github.com/WireGuard/wireguard-apple>
- <https://github.com/WireGuard/wireguard-apple/pulls>

## Logging/error model

The source has shared logging plus NetworkExtension-specific error notification. PVNetwork should keep a stable product error taxonomy while retaining detailed internal context and secret-redacted support export.

## Platform-specific lesson

The reviewed code contains macOS-specific lifecycle handling. Shared UI/core abstractions must still allow platform-version-gated workarounds with regression tests.

## License/reuse

Reviewed WireGuard Apple source is MIT. Reuse still requires dependency, entitlement, distribution, attribution/trademark and current-device testing. Current classification: `REUSE-CANDIDATE / ARCHITECTURE-REFERENCE`.

## PVNetwork regression requirements derived from this source

Future Apple tests must cover app/extension state synchronization, protected configuration create/update/delete, app-driven and OS-driven extension launch, corrupted/missing saved configuration, adapter error mapping, extension stop/restart, on-demand changes, log redaction, upgrade/migration with saved tunnels, Persian RTL with technical LTR tokens, `.conf` open/share import, ZIP archive import, QR import, duplicate-name behavior, malformed QR/file rejection, and explicit negative tests proving unsupported deep-link schemes do not get advertised as supported.

## Remaining gaps

- exact official WireGuard export/share behavior and archive semantics;
- full entitlement/app-group inventory;
- current shipped Store revision versus mirror revision;
- complete test target/CI inventory and dependency/SBOM audit;
- AmneziaWG Apple immutable revision and exact QR/file/export source-path pin;
- real-device import/export receipts and malformed-input tests.
