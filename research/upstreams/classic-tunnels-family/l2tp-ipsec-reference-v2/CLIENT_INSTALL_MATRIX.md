# L2TP/IPsec — Client Installation Matrix

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

This is a legacy compatibility matrix. Native platform availability changes by OS version/OEM and must be verified before any support claim.

## 1. Windows 10 / Windows 11 native client

State: `NATIVE CLIENT / LEGACY COMPATIBILITY / NEEDS-LAB`.

Current Microsoft `Add-VpnConnection` documentation still supports:

- `TunnelType L2tp`;
- `L2tpPsk` for pre-shared-key machine authentication;
- certificate-based L2TP/IPsec when no L2TP PSK is specified;
- PPP/user authentication selections including EAP/MSCHAPv2 paths;
- split-tunneling and credential persistence options.

### Provisioning paths

- Windows Settings VPN UI;
- PowerShell VpnClient cmdlets;
- managed/enterprise profile tooling where applicable.

### PVNetwork direction

Use the native Windows VPN stack through a typed adapter; do not bundle a custom L2TP implementation merely to duplicate built-in behavior.

### Required lab

- Windows 10 selected final supported build;
- Windows 11 selected current builds;
- PSK and certificate machine-auth profiles;
- username/password/EAP path required by server;
- behind-NAT behavior;
- source port/NAT server interoperability;
- split/full tunnel;
- update/profile migration;
- uninstall/profile+credential cleanup.

## 2. iOS / iPadOS / macOS native client

State: `NATIVE CLIENT / LEGACY COMPATIBILITY / NEEDS-LAB`.

Current Apple deployment documentation still lists **L2TP over IPsec** as a built-in VPN protocol for managed Apple devices and exposes an L2TP management payload with machine shared secret and user-auth choices.

Current Apple Platform Deployment materials list L2TP over IPsec alongside IKEv2/SSL VPN families for supported Apple operating systems.

### Provisioning paths

- system/managed VPN configuration;
- MDM/device-management configuration profile;
- current Apple Business configuration tooling.

### Product boundary

PVNetwork must use supported platform configuration APIs/managed profiles where available and keep system-owned connection state separate from app state.

### Required lab

- current iPhone/iPad OS versions;
- current macOS versions;
- PSK profile and certificate variation where current platform supports the target form;
- PPP user auth methods exposed by the platform;
- NAT/network handover/sleep;
- managed vs user-created profile behavior;
- update/profile removal/credential cleanup.

Do not infer continued GUI availability solely from management-schema support; verify the real device/system UI for the selected OS.

## 3. Android native legacy client

State: `PLATFORM LEGACY CLIENT / OEM+VERSION DEPENDENT / NEEDS-LAB`.

The current Android VPN developer guide describes a built-in PPTP/L2TP-IPsec client as **legacy VPN**. This establishes platform-level legacy support history/current documentation, but it does not prove identical Settings UI availability across all Android/OEM releases.

### PVNetwork direction

Do not make Android native L2TP/IPsec a first-choice architecture. If required for compatibility:

- detect actual platform capability/UI/API on the target device;
- document OEM/version constraints;
- avoid hidden dependence on removed/private APIs;
- prefer a modern protocol for new deployments.

### Required matrix

- Pixel/AOSP selected Android versions;
- Samsung/Xiaomi/other major OEMs only if product scope requires them;
- PSK/identifier/profile fields actually available;
- behind-NAT behavior;
- profile persistence across OS updates;
- uninstall/cleanup.

A third-party server guide showing Android settings is not enough to certify current Android support.

## 4. Linux desktop — NetworkManager-l2tp

State: `OPEN-SOURCE CLIENT/PLUGIN / REFERENCE-PASS / NEEDS-LAB`.

Pinned source:

- `nm-l2tp/NetworkManager-l2tp@ef970e2f3bf3e219d99c949b7a91a6bb55ab6ef7`
- source version 1.52.4
- GPLv2.

The current README documents dependencies including:

- NetworkManager >= 1.20;
- ppp/pppd;
- `kl2tpd` or `xl2tpd`;
- Libreswan or strongSwan;
- GTK/libnma/libsecret for the editor/credential/UI stack depending build.

It also documents build examples for Fedora, Debian/Ubuntu, openSUSE and Arch-family environments.

### Architecture

`NetworkManager UI/profile`

`-> NetworkManager-l2tp plugin/service`

`-> strongSwan or Libreswan IPsec`

`-> kl2tpd or xl2tpd L2TP`

`-> pppd`

### Installation evidence required per distro

- distro/release;
- plugin package/version;
- NetworkManager version;
- IPsec backend/version;
- L2TP backend/version;
- pppd version;
- secret service/keyring;
- desktop editor integration;
- systemd services/temp file cleanup;
- update/uninstall.

## 5. Linux CLI — xl2tpd/kl2tpd + IPsec + pppd

State: `ADVANCED / NEEDS-LAB`.

For operator/server-like Linux workstations, a manual composed client is possible using:

- strongSwan/Libreswan;
- xl2tpd or kl2tpd;
- pppd;
- routes/DNS scripts.

This is not a desirable consumer UI path for PVNetwork. It is useful for interoperability labs and headless environments.

## 6. Katalix kl2tpd

Pinned source:

- `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- MIT license.

`kl2tpd` is a minimal L2TPv2 client daemon. It is used as an option by NetworkManager-l2tp and should be treated as an L2TP component, not a complete IPsec/PPP VPN client.

## 7. ChromeOS / Android-derived environments

State: `UNVERIFIED`.

Do not infer ChromeOS L2TP behavior from Android or old ChromeOS documentation. If required, search current Google enterprise/device documentation and execute on selected ChromeOS versions.

## 8. tvOS / visionOS

Apple deployment documentation exposes L2TP management options across several Apple platforms, but PVNetwork product scope and practical UI/use case differ. Do not add these as certified client targets without exact API/profile and device testing.

## 9. Linux mobile / other Unix desktops

State: `ADVANCED / UNVERIFIED`.

A NetworkManager-l2tp-capable environment may be possible, but exact desktop/mobile integration must be tested. Daemon package availability is not equivalent to a usable client UI.

## 10. Credential storage by platform

### Windows

Use native credential/certificate stores and avoid sending `L2tpPsk` over unsafe IPC/CLI logging. Microsoft explicitly documents that `-Force` acknowledges supplying a PSK over an insecure channel in the PowerShell cmdlet context.

### Apple

Use native VPN/profile/Keychain/managed-secret mechanisms. Do not persist the shared secret redundantly in PVNetwork plaintext storage.

### Android

Use platform credential storage or product secure storage for any profile metadata under PVNetwork control; legacy system profile storage is OS-owned.

### Linux NetworkManager

Use NetworkManager secret flags/desktop secret service where possible; audit generated IPsec/PPP temp files and cleanup because the plugin creates configuration fragments for child daemons.

## 11. Native vs product-owned lifecycle

When using a native OS client, PVNetwork should model:

- `ProfileNotProvisioned`
- `NeedsPermissionOrAdmin`
- `Provisioned`
- `ConnectingIPsec`
- `ConnectingL2TP`
- `AuthenticatingPPP`
- `Connected`
- `DisconnectedExternally`
- `ProfileRemovedExternally`

Do not assume PVNetwork is the sole owner of a native profile.

## 12. Strict execution table

| Client | Backend | Clean install | Profile provision | Connect | Behind NAT | Update | Uninstall/profile cleanup |
|---|---|---:|---:|---:|---:|---:|---:|
| Windows 11 | native L2TP/IPsec | TODO | TODO | TODO | TODO | TODO | TODO |
| Windows 10 | native L2TP/IPsec | TODO | TODO | TODO | TODO | TODO | TODO |
| iOS/iPadOS | native L2TP/IPsec | TODO | TODO | TODO | TODO | TODO | TODO |
| macOS | native L2TP/IPsec | TODO | TODO | TODO | TODO | TODO | TODO |
| Android selected OEM/version | native legacy client | TODO | TODO | TODO | TODO | TODO | TODO |
| Ubuntu/Fedora desktop | NetworkManager-l2tp | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux CLI lab | IPsec + xl2tpd/kl2tpd + pppd | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO entries are external execution gates.
