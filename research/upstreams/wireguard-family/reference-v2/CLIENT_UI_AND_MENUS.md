# WireGuard / AmneziaWG — Client UI and Menu Reference

Status: **EVIDENCE-BACKED REFERENCE / NOT IMPLEMENTED / NOT COMPLETE-REFERENCE-v2**

Scope: entries 002 WireGuard and 003 AmneziaWG. This file records current source-level client UI surfaces that are useful as product/reference evidence. It does not claim PVNetwork implements any of them.

## Evidence pins

### Official WireGuard Android

Repository: `WireGuard/wireguard-android`
Observed source revision from GitHub code search: `e7b3a3c118836e112620b1302a8ba1873ad4daac`.

Source-level UI anchors:

- `ui/src/main/java/com/wireguard/android/activity/MainActivity.kt` — primary application activity/navigation owner.
- `ui/src/main/java/com/wireguard/android/fragment/TunnelEditorFragment.kt` — tunnel create/edit flow.
- `ui/src/main/res/layout/tunnel_editor_fragment.xml` — interface/editor layout.
- `ui/src/main/res/layout/tunnel_editor_peer.xml` — peer editor layout.
- `ui/src/main/java/com/wireguard/android/activity/SettingsActivity.kt` — settings surface.
- `ui/src/main/java/com/wireguard/android/preference/KernelModuleEnablerPreference.kt` — explicit kernel-module preference surface when applicable.
- `ui/src/main/AndroidManifest.xml` — activity/intent/application integration evidence.

The official README describes this project as the Android GUI for WireGuard and states that it opportunistically uses the kernel implementation and falls back to the non-root userspace implementation. Therefore UI state must not be conflated with one fixed backend implementation.

### Official WireGuard Windows

Repository: `WireGuard/wireguard-windows`
Observed source revision from GitHub code search: `4e6726c23ae9c5cb58e0c9910f3b7515621d133d`.

Source-level UI anchors:

- `ui/managewindow.go` — tunnel-management window.
- `ui/tray.go` — tray interaction surface.
- `ui/ui.go` — UI bootstrap/ownership.

Windows is therefore a desktop tunnel-manager/tray model, not a copy of the Android navigation model. PVNetwork should preserve platform-native interaction while keeping shared profile semantics behind adapters.

### Amnezia cross-platform client

Repository: `amnezia-vpn/amnezia-client`
Observed source revision from GitHub code search: `e643fa008cdeab1045b4b37652d07dd57924ccfc`.

Current source-level QML anchors include:

- `client/ui/qml/Pages2/PageStart.qml` — start/home surface.
- `client/ui/qml/Pages2/PageSettings.qml` — settings root.
- `client/ui/qml/Pages2/PageSettingsConnection.qml` — connection settings.
- `client/ui/qml/Pages2/PageSettingsServersList.qml` — server-list settings.
- `client/ui/qml/Pages2/PageSettingsServerInfo.qml` — server information.
- `client/ui/qml/Pages2/PageSettingsKillSwitch.qml` — kill-switch settings.
- `client/ui/qml/Pages2/PageSettingsApplication.qml` — application settings.
- `client/ui/qml/Components/ServersListView.qml` — reusable server-list view.
- `client/ui/qml/qml.qrc` — QML resource inventory.

The same pinned tree contains `client/translations/amneziavpn_fa_IR.ts`, which is direct evidence that Persian localization exists upstream. This is reference evidence only; PVNetwork still requires its own first-class Persian RTL QA and must not copy upstream branding/assets blindly.

## Functional UI map

| Surface | WireGuard Android | WireGuard Windows | Amnezia client | PVNetwork lesson |
|---|---|---|---|---|
| tunnel/profile list | primary app flow | manage window | server/profile-oriented flow | expose canonical profiles independent of engine |
| create/edit profile | tunnel editor + peer editor | tunnel management/edit path | protocol/server configuration pages | keep protocol-specific advanced fields behind capability-aware editor sections |
| connect/disconnect | tunnel state in main UI | manager/tray | start/home connection flow | one shared product action, platform-specific runtime adapter |
| settings | `SettingsActivity` | desktop manager/tray preferences | `PageSettings*` family | settings taxonomy should be product-owned, not copied from one engine |
| server/peer detail | peer editor | tunnel detail | server info/list pages | distinguish WireGuard peer semantics from commercial “VPN server” language |
| kill switch | platform/backend-dependent behavior | service/routing integration | explicit kill-switch page exists | expose only after platform-specific semantics and tests are documented |
| backend choice | kernel/userspace distinction exists | Windows service/tunnel architecture | protocol engine abstraction | backend selection is an implementation capability, not a marketing toggle |

## WireGuard profile editor fields that must remain semantically separate

A PVNetwork WireGuard editor should eventually map, with validation and secure handling, at least:

- interface private key (secret);
- interface addresses;
- DNS configuration where supported by product/platform policy;
- peer public key;
- optional preshared key (secret);
- peer endpoint host/IP and UDP port;
- AllowedIPs;
- persistent keepalive where required.

These are protocol/profile semantics. UI labels, grouping and platform behavior can differ without changing the canonical profile model.

## AmneziaWG-specific UI rule

AmneziaWG must not be presented as “WireGuard plus one obfuscation checkbox.” The product model must preserve generation/version-specific AWG parameters and only show fields supported by the selected AWG generation/backend. Existing repository research already records that AWG2/AWG3-era packet-shaping/header/signature behavior must remain version-specific.

The Amnezia client is valuable evidence for how a multi-protocol product separates start/home, server selection, connection settings, application settings and kill-switch configuration. It is not a license to copy its UI/branding.

## Import/export and deep-link caution

Configuration-file import is a distinct surface from profile editing. Platform document handlers, share sheets, QR import and custom URL/deep-link schemes must be tracked separately because support differs by client and OS. A 2026 upstream Amnezia issue documents that the standalone AmneziaWG iOS app handles `.conf` document URLs but lacked a registered custom URL scheme at the time of that report. Treat this as issue evidence for test design, not as a permanent product limitation.

## Accessibility/localization requirements for PVNetwork

- Persian and English must be first-class product languages.
- RTL layout must not reverse technical tokens such as IPs, CIDRs, ports, URLs, hashes, public keys or config paths.
- Secret fields need explicit reveal/copy controls and clipboard-risk policy.
- destructive actions (delete profile, replace imported config, reset keys) need confirmation and recovery semantics.
- connection state, backend state and configuration validity must be visually distinct.

## Reuse decision

- **WireGuard Android UI source:** architecture/reference value high; do not couple PVNetwork UI directly to it.
- **WireGuard Windows UI source:** architecture/reference value high for native desktop/service/tray ownership.
- **Amnezia client QML:** strong cross-platform product/reference evidence; UI/branding should be independently designed and license obligations reviewed before any code reuse.
- **Protocol configuration semantics:** normalize into PVNetwork canonical profile contracts, then render through platform-specific UI.

## Residual evidence before strict v2 completion

1. pin and inspect the Apple WireGuard/AmneziaWG client UI/menu trees in equivalent detail;
2. add exact import/export/QR/deep-link behavior per current major client and platform;
3. add screenshots/assets only as references where redistribution rights are clear;
4. reconcile every UI claim with the final client install matrix and source/license review;
5. add test receipts for RTL, accessibility, secret handling and platform-specific tunnel lifecycle during implementation phase.

Until those gaps and the other v2 contract categories pass, entries 002/003 remain `PENDING` in `REFERENCE_V2_COMPLETENESS.md`.