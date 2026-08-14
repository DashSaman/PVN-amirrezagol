# WireGuard / AmneziaWG — Source Revisions and Provenance

Review date: 2026-08-14

Status: evidence-backed research snapshot. These revisions are research pins, not a claim that PVNetwork has implemented or shipped any component.

## Canonical-source rule

The repositories under the GitHub `WireGuard` organization explicitly identify themselves as mirrors. For provenance, treat the `git.zx2c4.com` projects named in each repository description as canonical and the GitHub repositories below as convenient immutable research mirrors.

AmneziaWG repositories are separately maintained forks/evolutions and must be pinned independently from WireGuard upstream.

## Pinned sources

### WireGuard userspace Go implementation
- Research mirror: `WireGuard/wireguard-go`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-go`
- Reviewed branch: `master`
- Pinned commit: `ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- Repository metadata language: Go
- Repository metadata license: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-go/git/trees/ecfc5a8d54462e18e13c72173e2623d16d8e25a0?recursive=1`
- Important source areas: `conn/`, `device/`, `ipc/`, `ratelimiter/`, `rwcancel/`, `tun/`, platform-specific files, tests, `Makefile`, `go.mod`, and `LICENSE`.

### WireGuard for Windows
- Research mirror: `WireGuard/wireguard-windows`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-windows`
- Reviewed branch: `master`
- Pinned commit: `4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- Repository metadata primary language: Go
- Repository metadata license: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-windows/git/trees/4e6726c23ae9c5cb58e0c9910f3b7515621d133d?recursive=1`
- Important source areas: `conf/`, `manager/`, `ui/`, `driver/`, `services/`, `ringlogger/`, `updater/`, `elevate/`, `l18n/`, `docs/`, installer/build files, and `embeddable-dll-service/`.
- Deep current dossier: `WINDOWS_CLIENT.md`.

### WireGuard for Android
- Research mirror: `WireGuard/wireguard-android`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-android`
- Reviewed branch: `master`
- Pinned commit: `e7b3a3c118836e112620b1302a8ba1873ad4daac`
- Repository metadata primary language: Kotlin
- Repository metadata license: Apache-2.0
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-android/git/trees/e7b3a3c118836e112620b1302a8ba1873ad4daac?recursive=1`
- The pinned tree separates reusable tunnel/backend code under `tunnel/` from application/UI code under `ui/` and includes Gradle/version-catalog configuration plus localization/resources.
- Detailed dossier: `ANDROID_CLIENT.md`.

### WireGuard for Apple platforms
- Research mirror: `WireGuard/wireguard-apple`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-apple`
- Reviewed branch: `master`
- Pinned commit: `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`
- Repository metadata primary language: Swift
- Repository metadata license: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-apple/git/trees/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1?recursive=1`
- Important areas: `Sources/Shared/`, `Sources/WireGuardApp/`, `Sources/WireGuardKit/`, `Sources/WireGuardNetworkExtension/`, localized resources, Xcode project/build configuration, `Package.swift`, `MOBILECONFIG.md`, and tests/helpers.
- Detailed dossier: `APPLE_CLIENT.md`.

### AmneziaWG userspace Go implementation
- Repository: `amnezia-vpn/amneziawg-go`
- Relationship: fork/evolution derived from WireGuard Go.
- Reviewed branch: `master`
- Pinned commit: `1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`
- Current head date at review: 2026-08-13
- Repository metadata primary language: Go
- Root license reviewed: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/amnezia-vpn/amneziawg-go/git/trees/520207e4a2c07074a306865f1189594ec154a1fc?recursive=1`
- Current module path: `github.com/amnezia-vpn/amneziawg-go/v3`
- Current source is AWG3.1-era. The pinned head fixes a runtime panic in `HandshakeCookie` trailer-buffer handling introduced by AWG3.1 `RandomTrailers` behavior.
- Amnezia Client is a separate application/codebase and must not be assigned this core's license by association.

### AmneziaWG Android
- Repository: `amnezia-vpn/amneziawg-android`
- Relationship: fork/evolution of official WireGuard Android
- Reviewed branch: `master`
- Pinned commit: `d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`
- Current head date at review: 2026-08-12
- Primary language: Kotlin
- Root `COPYING`: Apache-2.0
- Important areas include Gradle/application/tunnel modules and platform build workflow.
- Exact current tunnel/core submodule relationship remains a tracked gap.

### AmneziaWG Apple
- Repository: `amnezia-vpn/amneziawg-apple`
- Relationship: fork/evolution of WireGuard Apple
- Reviewed branch: `master`
- Pinned commit: `e5410a539f28b8ce5dd1d060c45e4fa555e9a210`
- Current head date at review: 2026-08-11
- Primary language: Swift
- Root `COPYING`: MIT
- Important areas include `Sources/`, `Tests/`, `Package.swift`, Xcode project and `MOBILECONFIG.md`.
- Current head fixes an excluded-route behavior problem capable of disturbing a Linux peer connection.

### AmneziaWG Windows client
- Repository: `amnezia-vpn/amneziawg-windows-client`
- Relationship: WireGuard-Windows-derived full client
- Reviewed branch: `master`
- Pinned commit: `c8fa887db05ade03b9281b0e9de60579f744f995`
- Current head date at review: 2026-08-13
- Primary language: Go
- Root `COPYING`: MIT
- Current client dependency pins include:
  - `amneziawg-go/v3 v3.1.20260813`
  - `amneziawg-windows/v3 v3.1.20260813`
- Current AWG3.1 config/UI additions include `RandomTrailers` and `DisableCookies`.

### AmneziaWG Windows tunnel/library
- Repository: `amnezia-vpn/amneziawg-windows`
- Reviewed branch: `master`
- Pinned commit: `1326e9bbdc71be88ddcc20925e092c6f5b9513da`
- Current head date at review: 2026-08-13
- Current source adds AWG3.1 config/UAPI support for `RandomTrailers` and `DisableCookies`.
- Earlier/current README evidence describes the embeddable tunnel-library area as MIT-licensed; a root `COPYING` file was not found at the attempted path, so exact reusable path/file licensing remains a confirmation gap rather than being inferred from the separate Windows client.

## Versioning rule

Do not store one generic `WireGuardVersion` or `AmneziaWGVersion` field for diagnostics/release evidence.

Record separately where applicable:

- product/client revision;
- platform tunnel/wrapper revision;
- portable Go engine revision;
- driver/native component revision;
- configuration/AWG generation;
- server/peer implementation/version.

This is especially important for AWG3.1, where the Windows client and Windows tunnel layer are independently versioned modules above the portable Go engine.

## Reuse classification at this stage

- `wireguard-go`: `REUSE-CANDIDATE`, subject to dependency/platform/Store review.
- WireGuard Windows source: `STRONG REUSE/ARCHITECTURE CANDIDATE` for separated components; path/dependency/package review required.
- WireGuard Android: `REUSE-CANDIDATE/REFERENCE` depending on tunnel vs application module and target architecture.
- WireGuard Apple: `REUSE-CANDIDATE/REFERENCE` depending on NetworkExtension/App Store architecture and entitlement review.
- `amneziawg-go`: `REUSE-CANDIDATE`, subject to exact AWG generation/platform/dependency testing.
- AmneziaWG Android/Apple/Windows projects: `REUSE-CANDIDATE/REFERENCE` per component; do not infer cross-platform feature parity or one license.

See `DEPENDENCIES_SBOM.md`, `AMNEZIAWG_PLATFORMS.md`, and later `SUPPORT_REUSE_DECISIONS.md` before making an implementation decision.

No third-party source or visual assets are mirrored into PVNetwork by this file. Follow `research/SOURCE_MIRROR_POLICY.md` before vendoring.
