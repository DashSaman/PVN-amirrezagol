# AmneziaWG — Current Platform Repository / Version / License Map

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

## Why platform repos are separate evidence

AmneziaWG is not one repository or one binary. Current public source is split across the portable Go engine, platform clients/wrappers and Windows-specific tunnel/client repositories. PVNetwork must pin each component independently.

## Portable Go engine

Repository: `amnezia-vpn/amneziawg-go`

Current master pin during this review:

`1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`

Current head date: 2026-08-13.

Root license previously reviewed: MIT.

### Important AWG3.1 regression/fix evidence

The current head fixes a runtime panic introduced by AWG3.1-related `RandomTrailers` behavior for `HandshakeCookie` messages. The fix corrects buffer length allocation when a random trailer is appended.

PVNetwork regression requirements:

- exercise every message class affected by padding/trailer behavior, not only normal data packets;
- test `RandomTrailers` enabled and disabled;
- malformed/extreme parameter boundaries must not panic the engine;
- exact AWG version must be recorded with config and runtime core version;
- never assume a newly introduced obfuscation field is stable merely because parsing succeeds.

## Android

Repository: `amnezia-vpn/amneziawg-android`

Current master pin:

`d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`

Current head date: 2026-08-12.

Repository relationship: fork of official `WireGuard/wireguard-android`.

Root `COPYING`: Apache License 2.0.

Current tree includes:

- Gradle/Kotlin build;
- `tunnel/` module;
- Git submodules;
- GitHub build workflow;
- WireGuard-derived Android client/library architecture.

### PVNetwork lesson

Treat AmneziaWG Android as a distinct platform implementation/reference above the portable core. Android still requires product-owned `VpnService`, TUN, notification/background, per-app and network-change lifecycle. A Go engine alone does not provide a Play-ready app.

### Current gaps

- exact `tunnel/` source-to-core version pin;
- current build workflow/artifact provenance;
- source-level menu/storage differences from official WireGuard Android;
- current Android API/minimum SDK and Store behavior;
- current issue/regression review.

## Apple

Repository: `amnezia-vpn/amneziawg-apple`

Current master pin:

`e5410a539f28b8ce5dd1d060c45e4fa555e9a210`

Current head date: 2026-08-11.

Repository relationship: fork of official WireGuard Apple client/library.

Root `COPYING`: MIT license text.

Current tree includes:

- Swift Package manifest;
- `Sources/`;
- `Tests/`;
- Xcode project;
- `MOBILECONFIG.md`;
- translation synchronization script.

### Current release/bug lesson

The current head fixes a route-handling problem where adding an excluded route for the tunnel network could cause a Linux server peer to reset connections.

PVNetwork regression category:

- Apple route include/exclude behavior must be tested against real peers;
- route optimization must not break peer reachability;
- NetworkExtension route changes require server-side interoperability tests;
- reconnect and underlying-network changes need exact-route verification.

### PVNetwork lesson

Apple client/library source is a valuable MIT reference, but App Store feasibility still depends on PVNetwork's NetworkExtension, entitlement, signing, privacy and distribution architecture.

## Windows full client

Repository: `amnezia-vpn/amneziawg-windows-client`

Current master pin:

`c8fa887db05ade03b9281b0e9de60579f744f995`

Current head date: 2026-08-13.

Root `COPYING`: MIT license text.

Repository relationship: fork/evolution of WireGuard Windows client architecture.

### AWG3.1 dependency pin

Current head commit `feat: add awg3.1 support (#122)` updates:

- `github.com/amnezia-vpn/amneziawg-go/v3` -> `v3.1.20260813`
- `github.com/amnezia-vpn/amneziawg-windows/v3` -> `v3.1.20260813`

Current UI/config source also adds AWG3.1 fields:

- `RandomTrailers`
- `DisableCookies`

This means the Windows product has a concrete three-layer version relationship:

`amneziawg-windows-client`

`-> amneziawg-windows/v3`

`-> amneziawg-go/v3`

PVNetwork must record all three, not one generic “AWG version”.

## Windows tunnel/library layer

Repository: `amnezia-vpn/amneziawg-windows`

Current master pin:

`1326e9bbdc71be88ddcc20925e092c6f5b9513da`

Current head date: 2026-08-13.

The current head adds AWG3.1 support to Windows configuration/UAPI handling. Source changes add:

- `RandomTrailers`
- `DisableCookies`
- wg-quick/UAPI normalization for the new Boolean-style values.

Earlier/current README evidence describes this repository as an embeddable WireGuard-style tunnel library derived from the upstream Windows embeddable DLL/tunnel pattern.

### License caution

A root `COPYING` file was not found at the exact path attempted during this work unit. Prior directory/README evidence described the embeddable contents as MIT-licensed, but PVNetwork must preserve this as **component/path-level evidence requiring exact file confirmation** before reuse.

Do not infer the library's exact license solely from the separate Windows client repository.

## AWG versioning conclusion

The family must no longer stop at “AWG 2.0”. Current source in August 2026 contains **AWG3.1** features and same-day fixes.

At minimum PVNetwork's future AWG extension model needs:

- AWG generation/version metadata;
- old AWG1/AWG2 compatibility fields already documented in `AMNEZIAWG_DELTA.md`;
- AWG3.1 fields including `RandomTrailers` and `DisableCookies`;
- unknown future-field preservation;
- adapter/core-version validation;
- migration and explicit unsupported-state behavior.

Do not silently remove unknown AWG fields when a profile is edited on a client with an older core.

## Core/client license separation

Current reviewed component licenses include:

- `amneziawg-go` — MIT;
- `amneziawg-android` — Apache-2.0;
- `amneziawg-apple` — MIT;
- `amneziawg-windows-client` — MIT;
- `amneziawg-windows` — exact reusable path/license needs final file-level confirmation despite prior MIT README evidence.

This is a strong example of why PVNetwork needs a component/SBOM license table rather than one “AmneziaWG license” label.

## Reuse direction

### Strong candidates

- portable `amneziawg-go` for platforms where its integration model is appropriate;
- official AmneziaWG platform forks as source/architecture references and possible reusable components when exact licenses/dependencies permit;
- Windows client/tunnel architecture for AWG-specific config/version behavior.

### Do not assume

- same feature fields exist on all platform repos at the same release date;
- Android/Apple/Windows share one core build/version;
- app source license equals embedded engine license;
- AWG3.1 config generated by one platform is automatically accepted by every other pinned platform build.

## Future cross-platform compatibility tests

For each AWG profile fixture record:

- AWG generation/version;
- config field set;
- engine/platform version;
- server version;
- successful parse/generate round trip;
- successful connect/data exchange;
- reconnect/network-change behavior;
- unknown-field preservation;
- downgrade behavior.

Include negative tests where AWG3.1 fields are opened by AWG2-era clients/cores.

## Remaining v1 gaps

- exact current Android core/submodule version map;
- exact current Apple Go/core dependency/version map;
- exact `amneziawg-windows` license file/path review;
- current platform issue/release matrices;
- per-platform dependency/SBOM table;
- complete support/reuse decision and numbered entry synchronization.

Server packages/installers and cryptographic/wire-flow detail belong to the mandatory later `COMPLETE-REFERENCE-v2` campaign after original v1 closure.
