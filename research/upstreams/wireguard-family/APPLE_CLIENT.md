# WireGuard Apple Platforms — Developer Dossier

Pinned official source: `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`.
Pinned standalone AWG source: `amnezia-vpn/amneziawg-apple@fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d` (observed 2026-08-14; signed GitHub commit updating awg-go version).

Research state: `IN-RESEARCH`.

## Source architecture

Both reviewed trees retain the Apple app + NetworkExtension + adapter/shared-model architecture inherited from WireGuard Apple. The product boundary remains `App UI/state -> Apple tunnel manager -> NetworkExtension -> protocol adapter/core`; UI code must not directly own packet-tunnel internals.

## Protected profile/secret storage

The WireGuard-family Apple source uses Apple Security/Keychain APIs and persistent references. Imported text is therefore an interchange format, not a suitable long-term persistence model. Deletion/migration must clean stale references and diagnostics must not expose protected material.

## Official WireGuard Apple — exact pinned import source

At pinned mirror commit `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`:

- `TunnelsListTableViewController.addButtonTapped` exposes both file import and QR scan.
- `presentViewControllerForFileImport()` accepts `com.wireguard.config.quick`, text and ZIP archive document types; the picker callback routes URLs to `TunnelImporter.importFromFile(...)`.
- `TunnelImporter.importFromFile(...)` expands ZIPs through `ZipImporter`, otherwise reads text and parses `TunnelConfiguration(fromWgQuickConfig:called:)`, then calls `tunnelsManager.addMultiple(...)`.
- `presentViewControllerForScanningQRCode()` presents `QRScanViewController`; its delegate receives a parsed `TunnelConfiguration` and adds it through `tunnelsManager.add(...)`.
- `MainViewController.importFromDisposableFile(url:)` sends externally opened disposable files through the same importer and deletes the disposable copy afterward.

Pinned anchors:
- <https://github.com/WireGuard/wireguard-apple/blob/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1/Sources/WireGuardApp/UI/iOS/ViewController/TunnelsListTableViewController.swift>
- <https://github.com/WireGuard/wireguard-apple/blob/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1/Sources/WireGuardApp/UI/TunnelImporter.swift>
- <https://github.com/WireGuard/wireguard-apple/blob/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1/Sources/WireGuardApp/UI/iOS/ViewController/MainViewController.swift>

This closes the exact file/archive/QR **import source-path** gap for the pinned official mirror. It does not prove current App Store binary equivalence and does not establish QR export.

## Exact iOS ZIP export/share path — official WireGuard

At the same immutable WireGuard Apple pin, `Sources/WireGuardApp/UI/iOS/ViewController/SettingsTableViewController.swift` exposes a dedicated `exportZipArchive` settings row. Selecting it calls `exportConfigurationsAsZipFile(sourceView:)`, which first invokes `PrivateDataConfirmation.confirmAccess(...)`, gathers every tunnel configuration from `TunnelsManager`, writes them through `ZipExporter.exportConfigFiles(...)` to `wireguard-export.zip` in the app document directory, and then presents `UIDocumentPickerViewController(..., in: .exportToService)` for the OS export/share destination.

This establishes a precise **all-tunnels ZIP export** path and a privacy-confirmation boundary for the pinned iOS source. It does **not** establish QR export, per-tunnel share, custom URL export, or current App Store binary equivalence.

Pinned anchor:
- <https://github.com/WireGuard/wireguard-apple/blob/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1/Sources/WireGuardApp/UI/iOS/ViewController/SettingsTableViewController.swift>

## Standalone AmneziaWG Apple — current immutable source pin

The standalone fork was re-audited at current commit `fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d`, whose upstream commit timestamp is 2026-08-14 and whose GitHub verification is valid. This removes the previous moving-branch ambiguity for the source observations below.

At that pin, `Sources/WireGuardApp/UI/iOS/ViewController/TunnelsListTableViewController.swift` still exposes the same explicit **Import file** and **Scan QR code** actions. Its file picker accepts WireGuard quick-config, text and ZIP archive types and routes selected URLs to `TunnelImporter.importFromFile(...)`; its QR delegate receives a parsed `TunnelConfiguration` and persists it via `tunnelsManager.add(...)`.

At the same pin, `Sources/WireGuardApp/UI/TunnelImporter.swift` retains the ZIP-or-text import pipeline and parses non-ZIP content through `TunnelConfiguration(fromWgQuickConfig:called:)`. Because this is the AWG fork, the configuration model/core carries AWG-specific extensions elsewhere, while the UI import orchestration remains recognizably WireGuard-derived.

Pinned anchors:
- <https://github.com/amnezia-vpn/amneziawg-apple/commit/fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d>
- <https://github.com/amnezia-vpn/amneziawg-apple/blob/fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d/Sources/WireGuardApp/UI/iOS/ViewController/TunnelsListTableViewController.swift>
- <https://github.com/amnezia-vpn/amneziawg-apple/blob/fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d/Sources/WireGuardApp/UI/TunnelImporter.swift>

A 2026 feature request still documents absence of a registered standalone custom URL scheme and proposes `amneziawg://`; therefore a one-tap custom-scheme import is **not claimed**. This is compatible with the source-pinned file and QR paths above: file/QR import can exist without a custom URL scheme.

A separate 2026 issue reports `.conf` import succeeding while a hand-generated QR payload can fail with error 900. Treat that as payload-format/interoperability evidence, not as proof that QR scanning is absent. Real-device generated-QR receipts remain required before PVNetwork publishes a standalone AWG QR-generation contract.

## Exact iOS ZIP export/share path — standalone AmneziaWG

At immutable AWG Apple commit `fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d`, `SettingsTableViewController.swift` retains the same export architecture as the WireGuard-derived app: an `exportZipArchive` settings row invokes `PrivateDataConfirmation.confirmAccess(...)`, collects all tunnel configurations, calls `ZipExporter.exportConfigFiles(...)`, writes `amneziawg-export.zip`, and presents an `UIDocumentPickerViewController` in `.exportToService` mode.

This is source-level evidence that the standalone AWG iOS app supports **all-tunnels ZIP export to an OS-selected service** at the pinned revision. It is not evidence of QR export or a registered `amneziawg://` scheme.

Pinned anchor:
- <https://github.com/amnezia-vpn/amneziawg-apple/blob/fe2e2d7ebb835a0a2ad1d8de75094a6de134da4d/Sources/WireGuardApp/UI/iOS/ViewController/SettingsTableViewController.swift>

## Capability decision

| Apple surface | File/archive import | QR import | Custom URL/deep-link import | Export |
|---|---|---|---|---|
| official WireGuard Apple | **source-pinned** | **source-pinned UI path** | not claimed | **all-tunnels ZIP export source-pinned**; QR/per-tunnel export not claimed |
| standalone AmneziaWG Apple | **source-pinned at 2026-08-14 commit** | **source-pinned UI path**; generated payload contract still needs receipt | **NOT CLAIMED; feature requested** | **all-tunnels ZIP export source-pinned**; QR/per-tunnel export not claimed |
| main Amnezia client | separate codebase | separate audit | separate `vpn://` code path; do not inherit into standalone AWG | separate audit |

## Evidence boundaries

QR provisioning and QR export are different capabilities. An app accepting QR input does not imply it can export QR. Likewise, a main-Amnezia `vpn://` URI must not be advertised as a standalone AmneziaWG URL scheme. ZIP export source evidence also does not prove that every external share target or malformed/private-data case behaves correctly on a real device.

## Current-source freshness warning

The official WireGuard GitHub mirror is architecture evidence but visibly older than the standalone AWG fork. Current release certification must check shipped Store/build revisions and unresolved patches, not infer binary freshness from either source tree alone.

## License/reuse

Reviewed WireGuard Apple source is MIT; the standalone AWG fork derives from it. Reuse still requires dependency, entitlement, distribution, attribution/trademark and current-device testing. Classification remains `REUSE-CANDIDATE / ARCHITECTURE-REFERENCE`, not implemented support.

## PVNetwork regression requirements

Future Apple tests must cover app/extension state synchronization, protected configuration create/update/delete, app-driven and OS-driven extension launch, corrupted/missing saved configuration, adapter error mapping, extension stop/restart, on-demand changes, log redaction, upgrade/migration with saved tunnels, Persian RTL with technical LTR tokens, `.conf` open/share import, ZIP archive import, QR import, duplicate-name behavior, malformed QR/file rejection, AWG-specific field round-trip, ZIP export after privacy confirmation, export cancellation/failure, imported-export round-trip, and explicit negative tests proving unsupported deep-link schemes do not get advertised as supported.

## Remaining gaps

- QR export/per-tunnel share behavior is still not established for either reviewed Apple app;
- full entitlement/app-group inventory and current shipped Store revision mapping;
- complete test target/CI/dependency/SBOM audit;
- real-device import/export and malformed-input receipts, including generated AWG QR interoperability.
