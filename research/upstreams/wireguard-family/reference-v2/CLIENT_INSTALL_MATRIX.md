# WireGuard / AmneziaWG v2 — Client Install Matrix

Status: source-backed client availability/reference matrix; **not device certification**.

## Official WireGuard clients

| Client target | Official distribution path | Implementation/reference | Current evidence captured |
|---|---|---|---|
| Windows | WireGuard official installer / MSI | `wireguard-windows`, WireGuardNT | official install page lists Windows 10/11 and Server 2016/2019/2022/2025, v1.1 surface |
| Android | Play Store or official direct APK | `wireguard-android`; embeddable `com.wireguard.android:tunnel` | official install page lists Android v1.0.20260315 at research date |
| iOS | App Store | `wireguard-apple` / WireGuardKit | official install page lists v1.0.16 surface |
| macOS | App Store | `wireguard-apple` / WireGuardKit | official install page lists v1.0.16 surface |
| Linux desktop | distro `wireguard-tools`, plus NetworkManager/systemd integrations where applicable | kernel + tools / desktop network manager | official install and embedding pages |
| macOS CLI | Homebrew/MacPorts `wireguard-tools` | userspace/tools | official install page |

Sources: <https://www.wireguard.com/install/>, <https://www.wireguard.com/repositories/>, <https://www.wireguard.com/embedding/>.

### Embedding boundary

The official embedding guidance identifies platform-native integration surfaces rather than requiring PVNetwork to fork full GUI clients:

- Windows: prefer the embeddable DLL service over directly driving WireGuardNT;
- iOS/macOS: WireGuardKit;
- Android: `com.wireguard.android:tunnel`;
- Linux: embeddable C library in `wireguard-tools`, or platform control APIs such as NetworkManager/systemd/connman;
- Go control applications: `wgctrl-go`.

This is strong evidence for a reuse-first architecture, but license/API/version review is still required before implementation.

## AmneziaWG client surfaces

### Amnezia multi-protocol client

The official `amnezia-vpn/amnezia-client` release stream provides platform assets and changelogs. Availability is release-specific: the observed current release page explicitly notes temporary unavailability for some older macOS and Debian/Ubuntu combinations. Therefore the correct representation is “platform assets vary by release,” not a timeless all-platform claim.

Source: <https://github.com/amnezia-vpn/amnezia-client/releases/>.

### AWG engine/tool distinction

Client applications may embed/use AWG implementations, but `amneziawg-linux-kernel-module`, `amneziawg-go`, and `amneziawg-tools` remain separate implementation/tooling projects. A GUI release is not evidence that its embedded AWG generation matches every server generation.

## Required device certification matrix

Before marking an entry COMPLETE-REFERENCE-v2 or later implementation-ready, collect for each major client:

| Field | Required evidence |
|---|---|
| OS / version | exact tested version |
| app version | immutable release/build identifier |
| distribution channel | store, official APK/MSI/pkg/release asset |
| publisher/signing | observed publisher/signature/hash where available |
| import methods | file, QR, URI/deep link, manual fields as applicable |
| tunnel lifecycle | create/import, enable, disable, delete |
| routing controls | full tunnel, split tunnel / AllowedIPs behavior |
| DNS controls | exact UI/config semantics |
| kill switch / always-on | exact platform semantics, if exposed |
| logs/diagnostics | location and export path |
| update/rollback | supported path and limitations |
| AWG generation | exact AWG protocol generation/parameters when applicable |

## Current conclusion

WireGuard has first-party clients and embedding surfaces for the major desktop/mobile platforms, making upstream reuse preferable to recreating the tunnel engine. AWG client availability must be tracked against Amnezia release artifacts and exact generation compatibility. This matrix closes the high-level install/distribution inventory only; UI/menu and hands-on receipts remain separate gates.
