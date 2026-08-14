# WireGuard / AmneziaWG — Source Revisions and Provenance

Review date: 2026-08-14

Status: evidence-backed research snapshot. These revisions are research pins, not a claim that PVNetwork has implemented or shipped any component.

## Canonical-source rule

The repositories under the GitHub `WireGuard` organization explicitly identify themselves as mirrors. For provenance, treat the `git.zx2c4.com` projects named in each repository description as canonical and the GitHub repositories below as convenient immutable research mirrors.

## Pinned sources

### WireGuard userspace Go implementation
- Research mirror: `WireGuard/wireguard-go`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-go`
- Reviewed branch: `master`
- Pinned commit: `ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- Repository metadata language: Go
- Repository metadata license: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-go/git/trees/ecfc5a8d54462e18e13c72173e2623d16d8e25a0?recursive=1`
- Important top-level/source areas visible in the pinned tree include `conn/`, `device/`, `ipc/`, `ratelimiter/`, `rwcancel/`, `tun/`, platform-specific files, tests, `Makefile`, `go.mod`, and `LICENSE`.

### WireGuard for Windows
- Research mirror: `WireGuard/wireguard-windows`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-windows`
- Reviewed branch: `master`
- Pinned commit: `4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- Repository metadata primary language: Go
- Repository metadata license: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-windows/git/trees/4e6726c23ae9c5cb58e0c9910f3b7515621d133d?recursive=1`
- Important source areas include `conf/`, `manager/`, `ui/`, `driver/`, `services/`, `ringlogger/`, `updater/`, `elevate/`, `l18n/`, `docs/`, installer/build files, and an `embeddable-dll-service/` area.

### WireGuard for Android
- Research mirror: `WireGuard/wireguard-android`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-android`
- Reviewed branch: `master`
- Pinned commit: `e7b3a3c118836e112620b1302a8ba1873ad4daac`
- Repository metadata primary language: Kotlin
- Repository metadata license: Apache-2.0
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-android/git/trees/e7b3a3c118836e112620b1302a8ba1873ad4daac?recursive=1`
- The pinned tree separates reusable tunnel/backend code under `tunnel/` from application/UI code under `ui/` and includes Gradle/version-catalog configuration plus localization/resources.

### WireGuard for Apple platforms
- Research mirror: `WireGuard/wireguard-apple`
- Canonical upstream stated by mirror: `git.zx2c4.com/wireguard-apple`
- Reviewed branch: `master`
- Pinned commit: `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`
- Repository metadata primary language: Swift
- Repository metadata license: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/WireGuard/wireguard-apple/git/trees/2fec12a6e1f6e3460b6ee483aa00ad29cddadab1?recursive=1`
- Important areas include `Sources/Shared/`, `Sources/WireGuardApp/`, `Sources/WireGuardKit/`, `Sources/WireGuardNetworkExtension/`, localized resources, Xcode project/build configuration, `Package.swift`, `MOBILECONFIG.md`, and tests/helpers visible in the full tree.

### AmneziaWG userspace Go implementation
- Repository: `amnezia-vpn/amneziawg-go`
- Relationship: GitHub identifies it as a fork derived from `WireGuard/wireguard-go`.
- Reviewed branch: `master`
- Pinned commit: `1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`
- Repository metadata primary language: Go
- Repository metadata license: MIT
- Complete recursive-tree reference: `https://api.github.com/repos/amnezia-vpn/amneziawg-go/git/trees/1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1?recursive=1`
- Amnezia Client is a separate application/codebase and must not be assigned this core's license by association.

## Reuse classification at this stage

This is only a first-pass source/license classification:
- `wireguard-go`: `REUSE-CANDIDATE`, subject to dependency/platform/store review.
- WireGuard Windows source: `REUSE-CANDIDATE` for appropriately separated components and a strong Windows architecture reference; path/dependency review still required.
- WireGuard Android: `REUSE-CANDIDATE`/reference depending on module and desired integration; application and tunnel modules must be evaluated separately.
- WireGuard Apple: `REUSE-CANDIDATE`/reference depending on NetworkExtension/App Store architecture and entitlement review.
- `amneziawg-go`: `REUSE-CANDIDATE`, subject to exact compatibility/version/dependency testing.

No third-party source or visual assets are mirrored into PVNetwork by this file. Follow `research/SOURCE_MIRROR_POLICY.md` before vendoring.