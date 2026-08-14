# 002 — WireGuard Research Dossier

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`** at the research level.

This does not mean PVNetwork currently supports WireGuard in production.

## Primary shared evidence

Use `research/upstreams/wireguard-family/` as the current evidence base:

- `SOURCE_REVISIONS.md`
- `CORE_ARCHITECTURE.md`
- `ANDROID_CLIENT.md`
- `APPLE_CLIENT.md`
- `WINDOWS_CLIENT.md`
- `DEPENDENCIES_SBOM.md`
- `LESSONS_AND_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`
- related `AMNEZIAWG_*` files for entry 003 only.

## Current reviewed upstream set

- `WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- `WireGuard/wireguard-windows@4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- `WireGuard/wireguard-android@e7b3a3c118836e112620b1302a8ba1873ad4daac`
- `WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`

GitHub WireGuard repositories are research mirrors; canonical upstream provenance is recorded in `SOURCE_REVISIONS.md`.

## Current research decision

**`HIGH-PRIORITY CORE VPN TARGET / OFFICIAL-STACK-FIRST`**

PVNetwork should use the best maintained official/native WireGuard implementation per platform behind one product-owned WireGuard Adapter rather than forcing one userspace engine everywhere.

Provisional direction:

- Windows: official Windows service/tunnel/driver or embeddable component boundary after exact package/license review;
- Android: official tunnel/backend reference/reuse with PVNetwork-owned VpnService/UI/storage;
- Apple: WireGuardKit/NetworkExtension-compatible architecture with PVNetwork-owned product lifecycle/storage;
- Linux: native kernel implementation where appropriate, userspace fallback only where justified.

## Important Windows storage decision

Official Windows source proves a useful separation:

- `.conf` = standard import/export representation;
- app-managed persisted tunnels = DPAPI-protected `.conf.dpapi`.

PVNetwork rule:

**import format is not internal secret-storage format.**

Private keys/PSKs must use platform secure storage/protected vault semantics.

## Important platform architecture decisions

- Windows: privileged manager/service separated from user UI through local IPC;
- Android: VPN permission/TUN/per-app/network lifecycle remains product/platform responsibility;
- Apple: NetworkExtension and Keychain/application lifecycle remain product/platform responsibility;
- UI/tray/tiles/services must share one authoritative connection state.

## Dependency/SBOM position

Reviewed component manifests are recorded in `DEPENDENCIES_SBOM.md`.

Root project licenses are not enough. Final release review must include exact drivers/native libraries/module dependencies/toolchain/artifact hashes and advisories per platform.

## Regression classes already preserved

- Android Always-On/VPN ownership conflict;
- reboot restore/state synchronization;
- Quick Tile/UI/service state drift;
- startup before DNS/network readiness;
- roaming/network-family changes;
- sleep/resume;
- Apple route/NetworkExtension release risk;
- Windows service/session lifecycle;
- secure storage migration/import/export;
- route/DNS cleanup and repeated start/stop.

## Current client/UI reference evidence

Windows source-level menus are now documented in `WINDOWS_CLIENT.md`, including:

- Tunnels/Log/Update pages;
- import/add/edit/remove/export actions;
- tray status/tunnel toggle/manage/import/about/exit/update actions;
- close-to-tray behavior;
- DPAPI storage and privileged service ownership.

Android/Apple source architecture is documented separately.

## Residual gaps — explicit, not hidden

- exact final production component pins;
- final per-platform dependency/advisory scan;
- full current UI/assets/accessibility screenshot catalog;
- real-device/performance/power/interoperability tests;
- Store/package validation;
- server-side implementations/installers/menus and cryptography/wire-flow belong to mandatory later `COMPLETE-REFERENCE-v2`.

These residual gaps do not require keeping original v1 research permanently active. Entry 002 is research-handoff-ready but unimplemented.
