# M-P2 Public Apps (Windows + Android) — Validation Record

Date: 2026-09-23 (second execution slice)
Scope: first genuinely usable PVNetwork applications for Windows and
Android, per the owner's direct request, ahead of the plan's mobile-UI
tasks (plan Tasks 4–5 remain open; these apps consume the existing
committed contracts instead of duplicating them).

## Delivered

### 1. Windows desktop client (`apps/desktop`, extended)

- **Import**: real `VlessShareLinkImporter` (canonical profile + SecretStore
  transaction); profiles persist in `~/.pvnetwork/profiles.txt`
  (atomic write, corruption-bounded lines).
- **Secrets**: `DesktopSecretStore` — Windows DPAPI (`CryptProtectData`,
  per-user) encrypted files; POSIX fallback uses owner-only 0600 files
  (documented limitation).
- **Engine**: existing audited `JvmHostXrayRuntime` extended with Windows
  host support (env `PVNETWORK_XRAY_EXECUTABLE`, PATH, `%LOCALAPPDATA%\pvnetwork\core\xray.exe`,
  `<app-dir>\core\xray.exe` discovery; non-POSIX temp-dir config handling;
  POSIX path byte-identical to before). Config generation extracted to
  shared commonMain `XrayClientConfig` (also used by Android) with an HTTP
  inbound added next to SOCKS for WinINET.
- **System proxy**: `SystemProxyController` sets/restores HKCU WinINET
  ProxyEnable/ProxyServer (+AutoConfigURL save) and notifies via
  `InternetSetOption` (JNA); snapshot-restore on disconnect/error and JVM
  shutdown hook.
- **UI**: Compose — import box, profile list with select/delete, truthful
  state machine display with reason codes, core status (version), fa/en
  with real RTL, light/dark/system theme, sanitized diagnostics panel.

**Evidence (local Windows 11, JDK 21 / Gradle 9.5.0)**:
- `:apps:desktop:test` PASS (existing ShellModelTest suite).
- `:engines:xray-adapter:jvmTest --tests '*JvmHostXrayRealBinaryInteropTest'`
  with real `Xray-core v26.7.28` windows binary: **6/6 PASS** (VLESS RAW,
  VLESS Vision+TLS, VMess RAW, Trojan TLS, Shadowsocks RAW, VLESS XHTTP).
  This is the exact runtime + config generator the desktop app uses.
- Packaged distributable runs (`PVNETWORK_UI_SMOKE=1 → PASS`).
- Distribution: `PVNetwork-Windows-x64.zip` (app + pinned core + fa/en
  README + install/start scripts).

### 2. Android client (`apps/android`, new)

- Real Android application: `com.pvnetwork.client`, minSdk 26 (routing
  exclusion needs API 29+; below Q the app fails honestly with
  `ANDROID_REQUIRES_API_29`), targetSdk 36, compileSdk 37, arm64-v8a.
- **Import**: same canonical importer; `AndroidSecretStore` =
  AES-256-GCM under a non-exportable Android Keystore key;
  `AndroidProfileStore` = app-private JSON.
- **Data path**: `VpnService` (foreground specialUse) → TUN →
  `hev-socks5-tunnel` 2.17.1 JNI (MIT, built from pinned tag with NDK
  r27c, committed as `libhev-socks5-tunnel.so`, sha256
  `0c26c47620d7a3c881650e2a8d0f35cf164dd680b3aa0db35e37d1e3b88707e0`)
  → local Xray-core `v26.7.28` (MPL-2.0, official release asset fetched at
  build time behind a pinned sha256 gate, packaged as `libxray.so`,
  exec'd from `nativeLibraryDir`) → configured outbound. Server endpoint
  excluded from VPN routes (`excludeRoute`) so the engine socket cannot
  loop; dual-stack routes; DNS 1.1.1.1/8.8.8.8 through the tunnel.
- **UI**: Compose Material3 — import, profile list, truthful states with
  reason codes, fa (RTL) / en.
- **Build**: `:apps:android:assembleDebug` PASS locally →
  `android-debug.apk` (26.4 MB) containing both native components.

**Evidence limits (explicit)**: no physical-device execution yet — Android
status is **built + code-reviewed only**, not device-verified. The owner
installs the APK and reports; failures surface as reason codes.

## Shared engine change

`XrayClientConfig` (commonMain) now owns client-config JSON for all
platforms (unit-tested incl. JSON escaping); `JvmHostXrayRuntime` delegates
to it. POSIX CI tests unchanged in scope (config substring assertions
still hold; SOCKS inbound port assertions preserved).

## Not claimed

- No iOS app yet; no Android TV; no subscription URLs (VLESS links only);
  no NaiveProxy adapter yet (queued); no Store readiness; no signing
  (debug APK); Windows DPAPI scope = current user (no machine-key option).
