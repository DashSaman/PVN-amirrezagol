# Karing Platform Implementation Analysis

Research snapshot: **2026-08-29**  
Karing source snapshot inspected: `KaringX/karing` `main` at `fbaf3600e9239db14882d594907879230004921f`

Status: **source-level architecture/reference research only.** This document does not claim that PVNetwork has imported or implemented Karing code.

## 1. Scope

This report answers a narrower question than `KARING_DEEP_SOURCE_ANALYSIS.md`: **how Karing is implemented on each operating system** and which platform patterns are worth carrying into a future PVNetwork client.

Official targets verified from Karing's current README:

- Windows 10+ (64-bit)
- Android 8+
- Linux 64-bit
- iOS 15+
- macOS 12+ (Intel and Apple Silicon)
- tvOS 17+

Android also contains explicit Android TV / Leanback integration, so Android TV / Google TV is treated as a separate UX/platform concern below even though it is delivered from the Android project.

Primary upstream:

- https://github.com/KaringX/karing
- https://github.com/KaringX/sing-box
- https://github.com/KaringX/karing-ruleset

---

# 2. The most important architectural finding

Karing is **not** one monolithic Flutter application that directly implements the tunnel on every OS.

The visible source shows this repeated architecture:

```text
Shared product/UI/config layer
        |
        +-- Flutter on Android/iOS/macOS/Windows/Linux
        |
        +-- native SwiftUI shell on tvOS
        |
Platform bridge / OS lifecycle
        |
VPN service / packet-tunnel / system-extension / helper boundary
        |
Karing-modified sing-box / Libbox / native networking runtime
```

The operating-system projects mainly provide UI hosting, permissions, lifecycle, packaging and access to the OS VPN facility. The actual VPN service/core is deliberately separated.

This is visible in several independent places:

- Flutter `pubspec.yaml` declares `vpn_service` from a sibling path: `../vpn-service/`.
- Android code calls `io.nebula.vpn_service.VpnServiceImpl` rather than implementing the full service in `android/app`.
- iOS/macOS/tvOS `PacketTunnelProvider.swift` is only a tiny subclass of `LibVpnCore.ExtensionProvider`.
- Windows CMake expects `bind/windows/core/` in release builds.
- Linux CMake expects `bind/linux/core/karingService`.

At the inspected public revision, those complete runtime/service sources and generated core artifacts are **not all present in the public `KaringX/karing` tree**. Therefore the public repo is extremely useful for studying architecture and OS integration, but cloning it alone is not evidence of a fully reproducible release build.

## Consequence for PVNetwork

This separation is a good idea even though Karing's exact private/generated boundary should not be copied blindly:

```text
PVNetwork UI
  -> PVNetwork-owned canonical config + routing + DNS model
  -> stable PlatformVpnService contract
  -> stable EngineAdapter contract
  -> audited protocol engine(s)
```

OS-specific code should remain thin and replaceable.

---

# 3. Platform matrix

| Platform | Primary UI shell | OS VPN boundary visible in source | Core/service visibility in main repo | Distinctive integrations |
|---|---|---|---|---|
| Android | Flutter + Kotlin bridge | external `VpnServiceImpl` | incomplete/external `vpn_service` | foreground service, Quick Settings tile, automation broadcasts, package visibility, TV launcher |
| Android TV / Google TV | same Flutter product with TV mode + Leanback resources | same Android VPN service boundary | incomplete/external | Leanback launcher, TV banner, no-touch hardware assumptions, runtime TV detection |
| iOS / iPadOS | Flutter + Swift host | Network Extension packet tunnel | `PacketTunnelProvider` public; implementation delegated to `LibVpnCore` | App Group, Wi-Fi info entitlement, iCloud sync advertised at product level |
| tvOS | **native SwiftUI** | packet tunnel via `VpnServiceHandler` / `LibVpnCore` | core implementation not present | QR/LAN pairing, local HTTP sync server, profile URL, Always-On flow |
| macOS | Flutter + Cocoa host | packet-tunnel **system extension** | extension shell public; `LibVpnCore` implementation not present | System Extension install entitlement, App Sandbox, App Group, iCloud, persistent desktop lifecycle |
| Windows | Flutter + Win32/C++ runner | release bundle expects separate core directory | `bind/windows/core/` absent at inspected revision | COM, Win32 message loop, `WM_COPYDATA`, keep-running-on-close, Inno Setup |
| Linux | Flutter + GTK/C++ runner | release bundle expects `karingService` helper | `bind/linux/core/karingService` absent at inspected revision | GTK3, AppIndicator/keybinder/libsecret dependencies, DEB/RPM packaging |

---

# 4. Android implementation

## 4.1 Project shape

High-value files:

- `android/app/src/main/AndroidManifest.xml`
- `android/app/src/main/kotlin/com/nebula/karing/MainActivity.kt`
- `android/app/src/main/kotlin/com/nebula/karing/MainChannelManager.kt`
- `android/app/src/main/kotlin/com/nebula/karing/TileService.kt`
- `android/app/src/main/kotlin/com/nebula/karing/AutomationCommandReceiver.kt`
- `android/app/build.gradle.kts`

The Kotlin layer is intentionally small. Flutter owns the product UI and most configuration UX; Kotlin supplies Android-specific entry points and lifecycle integration.

## 4.2 Flutter ↔ Android bridge

`MainChannelManager.kt` creates a Flutter `MethodChannel` named:

- `channel_main_method`

It keeps a callback map and allows native Android events/commands to enter the Dart layer without putting product logic into the Activity.

`MainActivity.kt` registers that bridge and exposes a `getCommand` callback. Commands can be obtained from:

- an Intent `command` extra;
- a deep-link host.

Supported normalized commands are:

- connect
- disconnect
- reconnect

### PVNetwork lesson

Keep Android Activity code almost stateless. Make external/deep-link commands enter a narrow command interface rather than directly mutating tunnel state from UI code.

## 4.3 VPN lifecycle boundary

`TileService.kt` and `AutomationCommandReceiver.kt` both refer to:

- `io.nebula.vpn_service.VpnServiceImpl`
- `io.nebula.vpn_service.VpnState`

The visible Android app tree does not define that class. The root `pubspec.yaml` points at a sibling `../vpn-service/` dependency.

This shows a clean architectural boundary:

```text
Flutter/Kotlin product shell
  -> vpn_service package
  -> Android VpnService + core runtime
```

but it also means the inspected public application tree is not self-contained.

## 4.4 Foreground/background survival

Manifest permissions include:

- `FOREGROUND_SERVICE`
- `FOREGROUND_SERVICE_SPECIAL_USE`
- `FOREGROUND_SERVICE_SYSTEM_EXEMPTED`
- `POST_NOTIFICATIONS`
- `RECEIVE_BOOT_COMPLETED`
- `WAKE_LOCK`
- `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`

The Quick Settings tile starts the VPN with `startForegroundService()` on Android O+.

This is the right class of platform concerns for a production VPN client: the tunnel cannot be modeled as merely a UI task.

## 4.5 Quick Settings tile

`TileService.kt`:

- listens to VPN service state/result broadcasts;
- maps connected/disconnected/transitional states to Android tile states;
- starts/stops the actual VPN service directly;
- does not require Flutter UI to be visible.

This is a strong reusable *pattern* for PVNetwork Android: connection control must continue to work when the main UI process/window is not the active interaction surface.

## 4.6 External automation

`AutomationCommandReceiver.kt` exposes broadcast actions for:

- CONNECT
- DISCONNECT
- RECONNECT

On newer Android versions it can inspect the sender package. It reads an `allowed_sender_packages` array from the service configuration and rejects non-allowed senders when an allowlist exists.

### PVNetwork security recommendation

If PVNetwork exposes Tasker/automation/intents, do **not** make an unrestricted exported receiver the final trust boundary. Use one or more of:

- signature permission;
- explicit allowlist;
- authenticated app-link/nonce mechanism;
- disabled-by-default external automation setting.

Karing's allowlist concept is worth keeping, but PVNetwork should make the trust decision explicit in its own threat model.

## 4.7 Android permissions / capabilities

The manifest requests or declares support for networking, notifications, battery/lifecycle and device integration including:

- Internet/network state/Wi-Fi state
- location permissions (including background location)
- package visibility (`QUERY_ALL_PACKAGES`)
- camera (QR import)
- file/storage compatibility permissions
- package installation request
- boot/user-present events

Some of these are capability-sensitive and can affect Play Store review. PVNetwork should not copy the permission set wholesale. Each permission needs a product feature and store-policy justification.

## 4.8 Build configuration

Current Android build configuration shows:

- compile SDK: 35
- target SDK: 35
- min SDK: 26
- Java/Kotlin target: 17
- NDK: 28.2.13676358
- debug/profile ABIs: armv7, arm64, x86, x86_64
- release ABIs: armv7 + arm64
- ABI splits enabled plus a universal APK

Sentry mapping/native-symbol/source uploads are gated by a publish-time define rather than always occurring.

### PVNetwork lesson

Release diagnostics should be a deliberate release-pipeline feature. Never make production telemetry/source upload an accidental side effect of a debug-oriented build configuration.

---

# 5. Android TV / Google TV

Android TV support is not merely a README claim. The Android manifest and assets contain concrete TV accommodations.

## 5.1 Manifest / launcher

Verified elements include:

- `android.software.leanback` declared optional;
- `LEANBACK_LAUNCHER` category;
- TV banner asset;
- touchscreen, telephony, camera, NFC, GPS, microphone and sensors marked non-required where appropriate.

This lets the same Android package run on devices that do not behave like phones.

## 5.2 Runtime TV detection

`SettingConfigItemUI.maybeTv()` checks Android system features for:

- `android.hardware.type.television`
- `android.software.leanback`

There is also a persisted `tvMode` UI setting.

## 5.3 PVNetwork lesson

Do not treat Android TV as a resized phone screen. The network/core layer can be shared, but interaction should account for:

- D-pad/focus navigation;
- no touchscreen assumption;
- simple connect/select-server flow;
- QR-assisted configuration from a phone;
- minimal typing;
- large focus targets;
- background tunnel control independent of the screen.

Karing's separate native tvOS design reinforces the same conclusion from another platform.

---

# 6. iOS / iPadOS implementation

## 6.1 UI shell

The iOS app is primarily a Flutter host. `Runner/AppDelegate.swift` is thin and registers generated Flutter plugins.

Minimum platform configured in Podfile:

- iOS 15.0

## 6.2 Packet tunnel

The public `ios/karingService/PacketTunnelProvider.swift` is intentionally tiny:

```text
PacketTunnelProvider -> LibVpnCore.ExtensionProvider
```

The important point is architectural, not the four-line source file: Apple `NEPacketTunnelProvider` behavior is abstracted into `LibVpnCore` instead of being reimplemented in the Flutter UI target.

## 6.3 Entitlements

The extension declares:

- `packet-tunnel-provider`
- Wi-Fi information access
- App Group `group.com.nebula.karing`
- network client/server entitlements

The App Group is the natural boundary for sharing configuration/state between the host app and Network Extension.

## 6.4 Source-completeness caveat

`LibVpnCore` is referenced by the Xcode project and Swift source, but no authoritative public `KaringX/LibVpnCore` repository was found in this snapshot. The main Karing repo therefore exposes the extension shell and project wiring, not the complete core implementation.

## 6.5 PVNetwork lesson

For Apple mobile targets, use:

```text
Swift host / UI bridge
  -> App Group shared config
  -> NEPacketTunnelProvider extension
  -> PVNetwork core adapter / audited engine
```

Do not make Flutter/Dart responsible for the packet-tunnel lifecycle itself.

---

# 7. tvOS implementation — a separate product shell

This is one of the most valuable findings in the source review.

Karing does **not** simply reuse its phone Flutter UI on Apple TV. The tvOS target contains a dedicated SwiftUI interface in `tvos/Runner`, with its own `DashboardView.swift`.

## 7.1 TV-specific UI

The native screen includes:

- agreement state;
- large connect/disconnect control;
- QR display;
- settings sheet;
- profile URL input/download;
- Always-On toggle;
- connection state display/behavior.

This is much more appropriate for a remote-control TV interface than a dense phone/desktop settings screen.

## 7.2 Shared directory model

The tvOS app stores shared files under App Group:

- `group.com.nebula.karing`

Files include:

- `agree.json`
- `service.json`
- `settings.json`
- `service_core.json`
- `service_core.log`
- `service_error.log`
- cache directory

This is a clean separation between UI preferences, service configuration, core configuration and logs.

## 7.3 LAN + QR provisioning

`DashboardView` starts a small local HTTP server using Swifter on port 4040 while active.

Exposed flows include:

- sync upload;
- deleting core configuration;
- fetching selected file contents.

The QR code contains a `karing://tvos?...` URI with LAN addresses plus pairing/session information such as:

- local IP(s)
- HTTP port
- generated UUID
- core control port
- control secret
- app version
- core version

That allows a phone to become the convenient configuration keyboard for the TV.

### PVNetwork lesson

This is a very good interaction pattern for PVNetwork TV:

```text
TV shows one-time/ephemeral pairing QR
        <-> phone transfers subscription/profile over LAN
TV stores normalized config
        -> VPN extension/core
```

For a new implementation, the pairing endpoint should be hardened further with short-lived tokens, strict request sizes, origin/device confirmation and a small attack surface.

## 7.4 VPN install/start/stop

The tvOS UI uses `VpnServiceHandler` to:

1. check whether the VPN component is installed;
2. install when needed;
3. start with a timeout;
4. apply Always-On after successful start;
5. query current state;
6. disable Always-On before explicit stop.

This is an excellent example of treating extension installation and connection lifecycle as a state machine rather than a single connect button callback.

## 7.5 Identity / persistence

A device identifier is persisted through Keychain. Shared runtime files live in the App Group container.

These are the right platform primitives; PVNetwork should use its own identity/pairing semantics and never copy secrets between targets without an explicit threat model.

---

# 8. macOS implementation

## 8.1 UI host and desktop lifecycle

macOS is Flutter-based with a native Cocoa host.

`Runner/AppDelegate.swift` deliberately returns `false` from `applicationShouldTerminateAfterLastWindowClosed` so the app can remain alive when the main window closes.

The Dock reopen handler:

- finds an existing app window;
- de-minimizes it if necessary;
- restores visibility;
- brings it to front;
- activates the app.

This is correct VPN-desktop behavior: closing a management window should not be synonymous with tearing down the networking process.

## 8.2 Network System Extension

The macOS app entitlement is materially different from iOS:

- `packet-tunnel-provider-systemextension`
- `com.apple.developer.system-extension.install`

The app therefore models the VPN tunnel as a System Extension on macOS.

## 8.3 Additional entitlements

Runner entitlements include:

- App Sandbox
- App Group
- iCloud container / CloudDocuments
- Wi-Fi info
- network client/server
- user-selected file read/write
- keychain access group

Karing's README also advertises iCloud synchronization for iOS/macOS.

## 8.4 Service target

`macos/karingService` contains:

- the same very small `PacketTunnelProvider.swift` using `LibVpnCore`;
- extension entitlements;
- C bridging files for Libbox.

The Podfile explicitly isolates system-extension-related targets from inappropriate pod framework injection and marks extension APIs appropriately.

### PVNetwork lesson

A macOS client should keep:

- UI process lifecycle;
- system extension lifecycle;
- configuration persistence;
- core process/tunnel state

as separate concerns. The user closing a window must not accidentally destroy network state.

---

# 9. Windows implementation

## 9.1 Win32 host

Windows uses a C++ Win32 Flutter runner rather than a pure Dart executable.

`main.cpp`:

- initializes COM in apartment-threaded mode;
- forwards command-line arguments to Dart;
- creates a native Flutter window;
- enables `WM_COPYDATA` through the Windows message filter;
- runs a standard Win32 message loop;
- sets `SetQuitOnClose(false)`;
- installs defensive unhandled-exception behavior during teardown.

`flutter_window.cpp` creates the `FlutterViewController`, registers generated plugins and forwards native window messages to Flutter/plugin handlers.

## 9.2 Meaning of keep-running-on-close

As with macOS, `SetQuitOnClose(false)` reflects an important VPN desktop principle:

- management window state != tunnel/service lifetime.

PVNetwork should make this explicit through tray/service UX rather than letting it be an incidental window setting.

## 9.3 Core packaging boundary

Windows CMake installs normal Flutter runtime/native assets and then expects:

- debug VC runtime files;
- MSVC/UCRT libraries;
- ICU library;
- `../bind/windows/core/` for Profile/Release.

At the inspected public revision, `bind/windows/core/` does not resolve as a public path even though CMake expects it.

Therefore the shipping networking core/helper is injected or produced outside the public source tree visible here.

## 9.4 Installer

Karing uses Inno Setup.

The installer:

- installs in 64-bit mode;
- copies the release bundle recursively;
- creates Start Menu and optional desktop shortcuts;
- offers to preserve user data during uninstall;
- removes runtime data when requested;
- keeps installed files and user-generated data conceptually separate.

### PVNetwork lesson

On Windows, explicitly define three lifetimes:

1. installed application binaries;
2. privileged/core service/driver/helper artifacts;
3. per-user profiles/settings/logs.

Upgrade/uninstall must handle each independently and should restore system proxy/DNS/routes even if the UI process crashed previously.

---

# 10. Linux implementation

## 10.1 GTK Flutter host

Linux uses a native GTK application host around Flutter.

`my_application.cc`:

- chooses GTK header-bar behavior based on desktop environment;
- creates a 400x740 default window;
- creates a Flutter Linux view;
- passes command-line arguments into Dart;
- uses `G_APPLICATION_NON_UNIQUE` so an `xdg-open` launch can start a process, while the Dart-side single-instance mechanism is expected to forward focus/arguments and exit the duplicate process.

This is a practical workaround for URI/protocol-handler behavior on Linux desktops.

## 10.2 Packaging / desktop dependencies

DEB metadata lists runtime dependencies including:

- GTK3
- Ayatana AppIndicator
- keybinder
- libsecret
- libstdc++

These dependencies reveal intended desktop integrations:

- tray/AppIndicator
- global key binding / shortcut support
- desktop secret storage
- GTK UI hosting

RPM packaging is also present.

## 10.3 Core/helper boundary

Linux CMake installs Flutter/plugin assets and explicitly tries to install:

- `../bind/linux/core/karingService`

into the bundle.

That path is not present in the inspected public `bind/` tree. As on Windows, the release networking helper is a separate generated/injected artifact.

### PVNetwork lesson

Linux should not depend on UI privileges. A better production architecture is:

```text
unprivileged desktop UI
  -> narrow authenticated IPC
  -> privileged helper only where required
  -> TUN/routes/DNS/core
```

Privilege escalation, helper installation, stale route cleanup and distro differences must be explicit test cases.

---

# 11. Apple core/service provenance

A repository search for `LibVpnCore` at this snapshot finds references in Karing's Apple projects, but no separate public KaringX repository named `LibVpnCore` was verified.

The same tiny `PacketTunnelProvider.swift` implementation is used by iOS/tvOS and appears in macOS service wiring:

```text
import LibVpnCore
PacketTunnelProvider -> ExtensionProvider
```

That is enough to prove the boundary but **not** enough to reconstruct the implementation behind the boundary.

Do not describe the public Karing repo as a complete reproducible source distribution of its Apple VPN runtime without additional build artifacts/source provenance.

---

# 12. Common Flutter/product-layer clues

Although multiple utility files imported by Karing are missing from the inspected public tree, visible shared settings show explicit platform-aware product behavior.

Examples from `SettingConfigItemUI`:

- macOS `hideDockIcon`
- Android `excludeFromRecent`
- Android `wakeLock`
- iOS-oriented `hideVpn`
- `hideAfterLaunch`
- Android `tvMode`
- runtime Android TV detection
- platform-specific minimum desktop window dimensions

This confirms Karing follows a useful rule:

> share product concepts where possible, but retain OS-specific policy fields when the operating system genuinely differs.

PVNetwork's canonical model should follow the same principle without leaking low-level OS APIs into every UI screen.

---

# 13. What is truly reusable vs what is only a reference

## Strong clean-room architecture references

- Thin OS runner around shared UI/domain layer
- Separate tunnel/service process or extension
- App Group/shared-config model on Apple
- Android Quick Settings tile independent of UI visibility
- explicit external automation entry point
- Android TV capability detection
- native TV-first UI and QR/LAN provisioning
- desktop keep-running-on-window-close behavior
- clear install-data vs user-data separation
- release-specific native bundle injection

## Do not directly copy without license strategy

Karing app/core code is GPL-family source with additional association/naming restrictions observed in the repositories. Direct source copying is not the default recommendation for a separately licensed PVNetwork product.

Use behavior, architecture and public interfaces as references and implement PVNetwork-owned equivalents unless a GPL distribution strategy is intentionally chosen and legally reviewed.

## Do not treat as available source

At this snapshot, do not claim direct source reuse for:

- Karing `vpn_service` implementation;
- `LibVpnCore` implementation;
- Windows `bind/windows/core/` contents;
- Linux `bind/linux/core/karingService`.

Their references are visible; their complete source/runtime content is not present in the inspected public application tree.

---

# 14. Platform-specific PVNetwork recommendations

## Android / Android TV

Build:

- a dedicated Android VPN service module;
- foreground-service-safe lifecycle;
- state flow independent of Activity lifecycle;
- Quick Settings tile;
- optional automation with a strong trust boundary;
- separate TV navigation shell or TV-adaptive Compose layout;
- QR/subscription handoff to avoid typing on TV.

Avoid:

- UI-owned tunnel state;
- broad permissions without feature justification;
- unprotected exported automation receivers;
- assuming phone navigation works on D-pad devices.

## iOS / iPadOS

Build:

- Network Extension `NEPacketTunnelProvider` target;
- App Group configuration contract;
- host-to-extension state protocol;
- crash/restart-safe tunnel lifecycle;
- keychain-backed secrets;
- explicit On-Demand/Always-On capability decision based on distribution/entitlements.

## tvOS

Prefer a purpose-built TV shell. Karing's native SwiftUI decision is strong evidence that this can be cleaner than forcing the full phone UI onto TV.

Priority flow:

1. pair by QR;
2. import/update subscription;
3. choose server/profile;
4. connect/disconnect;
5. show minimal diagnostics.

## macOS

Use a System Extension/Network Extension boundary and preserve tunnel state when management windows close. Treat extension installation/update as a first-class migration flow.

## Windows

Keep the UI non-privileged where possible. Put privileged networking/service actions behind a narrow service/IPC contract. Package driver/helper/core versions explicitly and make upgrade rollback possible.

## Linux

Use a narrow privileged helper where TUN/routes/DNS demand it. Support distro/network-manager differences through platform adapters and integration tests rather than shell-command assumptions scattered through UI code.

---

# 15. Cross-platform state model recommended for PVNetwork

Karing's platform implementations reinforce the need for a shared state machine such as:

```text
UNINSTALLED
  -> INSTALLING_PLATFORM_COMPONENT
  -> DISCONNECTED
  -> PREPARING_CONFIG
  -> CONNECTING
  -> CONNECTED
  -> RECONNECTING
  -> DISCONNECTING
  -> DISCONNECTED

Any active state
  -> DEGRADED
  -> RECOVERING
  -> CONNECTED / FAILED
```

The UI should observe this state. It should not infer connection from whether a button was pressed.

State should cover at least:

- platform component installed/version;
- engine version;
- selected profile/node;
- tunnel state;
- current network/interface;
- route/DNS apply state;
- bytes/traffic counters;
- last error category;
- recoverability/retry state.

---

# 16. Security review notes raised by this source survey

These are review points, not vulnerability claims.

1. **Android exported automation receiver** — use a stronger explicit trust policy in PVNetwork.
2. **Android broad permission footprint** — minimize per Store policy and actual feature usage.
3. **tvOS local HTTP provisioning** — preserve the convenience, but use short-lived authenticated pairing and strict endpoint validation.
4. **Desktop helper/core injection** — signed/versioned artifacts and hash verification should be mandatory.
5. **App Group/shared files** — secrets should not be stored as ordinary plaintext configuration when Keychain/secure storage is appropriate.
6. **System proxy/routes/DNS restoration** — implement crash-safe transaction/rollback behavior.
7. **Telemetry** — make opt-in/consent and redaction policy explicit; separate diagnostics from tunnel correctness.

---

# 17. Exact upstream files worth studying

## Shared/product

- `pubspec.yaml`
- `lib/main.dart`
- `lib/app/modules/setting_manager.dart`
- `lib/screens/diversion_group_custom_edit_screen.dart`
- `assets/datas/preset/ir.json`

## Android

- `android/app/src/main/AndroidManifest.xml`
- `android/app/src/main/kotlin/com/nebula/karing/MainActivity.kt`
- `android/app/src/main/kotlin/com/nebula/karing/MainChannelManager.kt`
- `android/app/src/main/kotlin/com/nebula/karing/TileService.kt`
- `android/app/src/main/kotlin/com/nebula/karing/AutomationCommandReceiver.kt`
- `android/app/build.gradle.kts`

## iOS

- `ios/Runner/AppDelegate.swift`
- `ios/karingService/PacketTunnelProvider.swift`
- `ios/karingService/karingService.entitlements`
- `ios/Podfile`

## tvOS

- `tvos/Runner/DashboardView.swift`
- `tvos/karingService/PacketTunnelProvider.swift`
- `tvos/karingService/karingService.entitlements`
- `tvos/Podfile`

## macOS

- `macos/Runner/AppDelegate.swift`
- `macos/Runner/Runner.entitlements`
- `macos/karingService/PacketTunnelProvider.swift`
- `macos/karingService/karingService.entitlements`
- `macos/Podfile`

## Windows

- `windows/runner/main.cpp`
- `windows/runner/flutter_window.cpp`
- `windows/CMakeLists.txt`
- `windows/packaging/exe/inno_setup.iss`

## Linux

- `linux/my_application.cc`
- `linux/CMakeLists.txt`
- `linux/packaging/deb/make_config.yaml`
- `linux/packaging/rpm/make_config.yaml`

---

# 18. Final architecture conclusion

The deepest lesson from Karing's per-platform source is **not Flutter**. It is the boundary discipline:

```text
product UI / subscriptions / routing UX
          !=
OS VPN lifecycle
          !=
protocol engine
```

Karing shares a large amount of product UX across platforms, but it still uses native Android services, Apple packet/system extensions, desktop OS hosts and separately packaged core helpers.

PVNetwork should follow the same *separation of concerns* while keeping its own canonical model, adapters, security policy, release pipeline and licensing boundary.

For TV platforms specifically, Karing provides strong evidence for a **TV-first shell with QR-assisted provisioning**, not a generic resized desktop/mobile UI.

For desktop platforms, Karing provides strong evidence that **window lifecycle must be separate from network-service lifecycle**.

For every platform, the future PVNetwork client should make the platform tunnel service independently testable from the UI and should be able to replace protocol engines without redesigning the product shell.

---

# 19. Evidence / source snapshot notes

The analysis above was made from Karing public source as visible on 2026-08-29. Important source paths were inspected directly rather than inferred from screenshots or marketing pages.

Because Karing's build references external/generated components not all present in the public tree, claims in this report deliberately distinguish:

- **verified public shell/wiring**;
- **verified external/core reference**;
- **unverified or absent implementation content**.

If those external repositories/artifacts become public later, this document should be amended rather than assuming their internals from current call sites.