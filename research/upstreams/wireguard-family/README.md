# WireGuard / AmneziaWG Family — Shared Research Dossier

Related matrix entries: **002 WireGuard**, **003 AmneziaWG**. Mesh products that happen to use WireGuard remain separate higher-level control-plane/overlay research.

Research state: **`V1-HANDOFF-READY / NOT IMPLEMENTED`** at the shared-family original-research level.

This state is a research milestone only. It does not mean PVNetwork currently implements or production-certifies WireGuard or AmneziaWG.

## Current shared evidence

### Source / architecture

- `SOURCE_REVISIONS.md` — official/canonical provenance and current WireGuard/AWG platform pins through AWG3.1.
- `CORE_ARCHITECTURE.md` — WireGuard-Go/core boundaries.
- `WINDOWS_CLIENT.md` — Windows manager/service/UI/IPC/DPAPI storage/menu map.
- `ANDROID_CLIENT.md` — official Android backend/UI/profile/settings architecture.
- `APPLE_CLIENT.md` — official Apple WireGuardKit/NetworkExtension/Keychain architecture.
- `AMNEZIAWG_DELTA.md` — AWG extension/config differences from WireGuard.
- `AMNEZIAWG_PLATFORMS.md` — current Android/Apple/Windows/Go pins, AWG3.1 version relationships and platform-license distinctions.

### Dependency / quality / decision

- `DEPENDENCIES_SBOM.md` — per-component dependency/SBOM/license boundaries.
- `LESSONS_AND_TESTS.md` — recurring Android/Windows/Apple/WireGuard/AWG failure classes and PVNetwork regression requirements.
- `SUPPORT_REUSE_DECISIONS.md` — research-stage support/reuse direction for entries 002/003.

## WireGuard current research decision

**`HIGH-PRIORITY CORE VPN TARGET / OFFICIAL-STACK-FIRST`**

PVNetwork should use the most appropriate maintained official/native WireGuard implementation per platform behind a product-owned WireGuard Adapter rather than forcing one userspace Go engine everywhere.

Reviewed current source pins include:

- `wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- Windows mirror `4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- Android mirror `e7b3a3c118836e112620b1302a8ba1873ad4daac`
- Apple mirror `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`

GitHub WireGuard repositories are research mirrors; canonical `git.zx2c4.com` provenance remains recorded in `SOURCE_REVISIONS.md`.

## Windows architecture/storage conclusion

Official Windows source demonstrates a strong product pattern:

- privileged manager/service separate from user UI;
- local IPC between service/UI;
- tunnel/service/session lifecycle managed outside the window;
- `.conf` accepted as standard import/export;
- persisted app-managed tunnel configuration encrypted with Windows DPAPI as `.conf.dpapi`;
- main Tunnels/Log/Update pages and shallow tray tunnel controls.

PVNetwork rule:

**import format != protected internal persistence format.**

Private keys/PSKs/credentials must use platform secure storage or an explicitly protected product vault.

## AmneziaWG current research decision

**`HIGH-VALUE WIREGUARD-DERIVATIVE COMPATIBILITY TARGET / VERSIONED EXTENSION REQUIRED`**

PVNetwork must not implement AWG packet/handshake behavior from scratch. Reuse maintained implementations and model AWG as a versioned WireGuard-derived capability.

Current reviewed AWG pins:

- Go core `1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`
- Android `d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`
- Apple `e5410a539f28b8ce5dd1d060c45e4fa555e9a210`
- Windows client `c8fa887db05ade03b9281b0e9de60579f744f995`
- Windows tunnel/library `1326e9bbdc71be88ddcc20925e092c6f5b9513da`

Current Windows client explicitly pins:

- `amneziawg-go/v3 v3.1.20260813`
- `amneziawg-windows/v3 v3.1.20260813`

and adds AWG3.1 fields including `RandomTrailers` and `DisableCookies`.

The current Go-core head fixes an AWG3.1 random-trailer buffer-allocation panic on a HandshakeCookie path. This is preserved as a release/regression gate.

## AWG canonical model rule

Use:

`WireGuard base profile`

`+ explicit AWG generation/version`

`+ versioned AWG extension fields`

`+ unknown-future-field preservation`

Do not flatten AWG-only values into generic WireGuard fields and never silently drop newer-generation fields.

## Component-license separation

Reviewed current root licenses differ by component:

- `wireguard-go` — MIT
- official Windows — MIT
- official Android — Apache-2.0
- official Apple — MIT
- `amneziawg-go` — MIT
- AWG Android — Apache-2.0
- AWG Apple — MIT
- AWG Windows client — MIT
- AWG Windows tunnel/library — exact reusable path/file license confirmation remains an explicit gap despite earlier MIT README evidence.

Final exact-build SBOM/license review remains mandatory.

## Dependency conclusion

Reviewed `amneziawg-go` has a materially broader dependency surface than reviewed `wireguard-go`, including QUIC/Outline/gVisor/Shadowsocks-related packages.

AWG inclusion therefore has a real maintenance/SBOM/security cost and should remain a separate component/capability.

## Platform responsibility rule

Core reuse does not replace product/platform lifecycle:

- Android: VpnService/TUN/per-app/background/Always-On/network change;
- Apple: NetworkExtension/routes/Keychain/signing/Store;
- Windows: service/driver/IPC/DPAPI/update/package;
- Linux: kernel/userspace selection, route/DNS/service/package.

PVNetwork owns canonical profile, secure persistence, session state, DNS/routing UX, diagnostics/redaction and Store/package behavior.

## Numbered entry state

- `research/protocols/002-wireguard/README.md` — `V1-HANDOFF-READY / NOT IMPLEMENTED`
- `research/protocols/003-amneziawg/README.md` — `V1-HANDOFF-READY / NOT IMPLEMENTED`

## Why v1 can move on

The family now has broad original-research evidence for:

- source/provenance and platform pins;
- core architecture;
- Windows/Android/Apple client/platform architecture;
- protected storage/service/IPC lessons;
- AWG delta/version/platform mapping through AWG3.1;
- dependencies/SBOM boundaries;
- issue/regression classes;
- support/reuse decisions;
- numbered entry synchronization.

Keeping the whole 93-entry campaign blocked on implementation/device/server evidence would be incorrect.

## Residual gaps — preserve explicitly

1. final selected production pins and full advisory scans;
2. exact AmneziaWG Android/Apple embedded-core dependency/version graph;
3. exact file-level license confirmation for the AWG Windows tunnel/library component;
4. full current screenshot/assets/accessibility/menu catalogs across every platform;
5. real-device/server interoperability, long-duration performance/power and Store tests;
6. exact driver/native-artifact signing/provenance;
7. server implementations/installers/menus, cryptography and wire/data-flow belong to mandatory later `COMPLETE-REFERENCE-v2`.

These are residual research/implementation gaps, not hidden completion.

## Next original-v1 action

Checkpoint this family and immediately select the next unfinished original `COMPLETE-RESEARCH-v1` family from actual repository state. Do not begin mass `COMPLETE-REFERENCE-v2` until original v1 gates across the campaign reach their intended state.
