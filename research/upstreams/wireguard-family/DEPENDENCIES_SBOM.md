# WireGuard / AmneziaWG — Dependency / SBOM / License Matrix

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

This dossier records **component boundaries and exact reviewed manifests**, not a final legal/security sign-off.

## Why one “WireGuard license” label is insufficient

PVNetwork may consume:

- portable Go engine;
- Windows client/service/driver integration;
- Android application/library code;
- Apple Swift/Go library code;
- AmneziaWG portable Go engine;
- AmneziaWG platform forks/libraries.

Each has a different dependency graph, toolchain and license boundary.

Final distribution needs an SBOM for the **exact binaries actually shipped per platform**.

---

# A. Official WireGuard

## A1. wireguard-go

Pin:

`WireGuard/wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`

Module:

`golang.zx2c4.com/wireguard`

Go version in reviewed `go.mod`:

`1.23.1`

Direct dependencies reviewed:

- `golang.org/x/crypto v0.37.0`
- `golang.org/x/net v0.39.0`
- `golang.org/x/sys v0.32.0`
- `golang.zx2c4.com/wintun` pinned commit `0fa3db229ce2`
- `gvisor.dev/gvisor` pinned 2025 commit `39ed1f5ac29c`

Indirect reviewed:

- `github.com/google/btree v1.1.2`
- `golang.org/x/time v0.7.0`

### PVNetwork implication

Portable engine is relatively narrow, but Wintun/gVisor and Go module security/license state still require exact-build review.

Do not bundle Wintun/native components without their own file/version/license/provenance evidence.

## A2. WireGuard Windows

Pin:

`WireGuard/wireguard-windows@4e6726c23ae9c5cb58e0c9910f3b7515621d133d`

Go version:

`1.25.0`

Reviewed direct dependencies:

- `github.com/lxn/walk` — replaced with WireGuard-hosted Windows module revision;
- `github.com/lxn/win` — replaced with WireGuard-hosted Windows module revision;
- `golang.org/x/crypto v0.50.0`
- `golang.org/x/net v0.53.0`
- `golang.org/x/sys v0.43.0`
- `golang.org/x/text v0.36.0`

Indirect:

- `golang.org/x/mod v0.34.0`
- `golang.org/x/sync v0.20.0`
- `golang.org/x/tools v0.43.0`

### PVNetwork implication

Windows UI/service code and actual tunnel/driver components must not be treated as one dependency line. The Windows packaging dossier later needs:

- driver/Wintun version and license;
- service binary;
- UI binary;
- update helper;
- embeddable tunnel library if used;
- compiler/toolchain/signing evidence.

## A3. WireGuard Android

Pin:

`WireGuard/wireguard-android@e7b3a3c118836e112620b1302a8ba1873ad4daac`

Reviewed version catalog:

- Android Gradle Plugin `9.1.0`
- AndroidX Activity KTX `1.13.0`
- Annotation `1.9.1`
- AppCompat `1.7.1`
- Biometric `1.1.0`
- Collection `1.6.0`
- ConstraintLayout `2.2.1`
- CoordinatorLayout `1.3.0`
- Core KTX `1.18.0`
- DataStore Preferences `1.2.1`
- Fragment KTX `1.8.9`
- Lifecycle Runtime KTX `2.10.0`
- Preference KTX `1.2.1`
- Material `1.13.0`
- desugar JDK libs `2.1.5`
- Kotlin coroutines Android `1.10.2`
- ZXing Android Embedded `4.3.0`
- JSR305 / JUnit test dependencies.

### PVNetwork implication

Android app dependency/licensing/privacy review is separate from the WireGuard tunnel engine. QR scanner, preferences/biometric/UI dependencies can change Store/privacy/size surface even when the core protocol stays unchanged.

## A4. WireGuard Apple / WireGuardKit

Pin:

`WireGuard/wireguard-apple@2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`

Reviewed `Package.swift`:

- package `WireGuardKit`
- platform declarations: macOS 12, iOS 15
- no external Swift Package dependencies declared in this manifest
- targets:
  - `WireGuardKit`
  - `WireGuardKitC`
  - `WireGuardKitGo`
- `WireGuardKitGo` links `wg-go`.

### PVNetwork implication

“No Swift Package dependencies” does not mean dependency-free. The actual Go/native build and Apple NetworkExtension/framework chain must be included in the release SBOM.

The platform declarations are upstream package choices, not automatically PVNetwork minimum supported OS versions.

---

# B. AmneziaWG

## B1. amneziawg-go v3

Pin:

`amnezia-vpn/amneziawg-go@1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`

Module:

`github.com/amnezia-vpn/amneziawg-go/v3`

Go version:

`1.25.0`

Reviewed direct dependencies:

- `github.com/goccy/go-yaml v1.17.1`
- `go.uber.org/atomic v1.11.0`
- `golang.getoutline.org/sdk v0.0.23`
- `golang.getoutline.org/sdk/x v0.2.0`
- `golang.org/x/crypto v0.42.0`
- `golang.org/x/net v0.44.0`
- `golang.org/x/sys v0.36.0`
- `golang.zx2c4.com/wintun` pinned commit `0fa3db229ce2`
- `gvisor.dev/gvisor` pinned 2023 commit `1f7806d17489`

Reviewed indirect dependencies include:

- Google btree/pprof;
- Gorilla WebSocket;
- Ginkgo/Testify/GoMock test ecosystem;
- `quic-go/qpack` and `quic-go`;
- `shadowsocks/go-shadowsocks2`;
- `golang.org/x/exp`, `x/mobile`, `x/mod`, `x/sync`, `x/text`, `x/time`, `x/tools`.

### Important comparison

The reviewed AmneziaWG Go module has a materially broader dependency surface than the reviewed WireGuard-Go module.

This is not automatically bad, but it increases:

- SBOM size;
- license-review scope;
- vulnerability monitoring scope;
- binary-size/toolchain surface;
- potential transitive-update risk.

PVNetwork should justify AWG inclusion based on real compatibility/product value and keep it a separate adapter/component rather than assuming it is “free” once WireGuard exists.

## B2. AmneziaWG Windows client

Pin:

`amnezia-vpn/amneziawg-windows-client@c8fa887db05ade03b9281b0e9de60579f744f995`

Root `COPYING`: MIT.

Go version:

`1.25.0`

Reviewed dependencies include:

- Walk/Win UI packages, replaced with WireGuard-hosted Windows revisions;
- `golang.org/x/crypto v0.42.0`
- `golang.org/x/sys v0.36.0`
- `golang.org/x/text v0.29.0`
- Wintun pinned `0fa3db229ce2`
- `amneziawg-go/v3 v3.1.20260813`
- `amneziawg-windows/v3 v3.1.20260813`
- x/mod/net/sync/tools support dependencies.

### Version relationship

Current Windows client is explicitly an **AWG3.1** build chain:

`windows-client -> amneziawg-windows/v3 v3.1.20260813 -> amneziawg-go/v3 v3.1.20260813`

PVNetwork diagnostics must report component versions separately.

## B3. AmneziaWG Android

Pin:

`amnezia-vpn/amneziawg-android@d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`

Root `COPYING`: Apache-2.0.

Current tree contains Gradle/Kotlin Android code, a tunnel module and submodule/build integration.

Exact current tunnel/core dependency graph is still a v1 evidence gap and must be resolved before product reuse.

## B4. AmneziaWG Apple

Pin:

`amnezia-vpn/amneziawg-apple@e5410a539f28b8ce5dd1d060c45e4fa555e9a210`

Root `COPYING`: MIT.

Current tree contains Swift package/Xcode project/Sources/Tests and Go/native integration inherited/evolved from WireGuard Apple architecture.

Exact embedded Go/core version and full dependency graph remain an explicit gap.

## B5. AmneziaWG Windows tunnel/library

Pin:

`amnezia-vpn/amneziawg-windows@1326e9bbdc71be88ddcc20925e092c6f5b9513da`

Current source adds AWG3.1 configuration/UAPI support such as `RandomTrailers` and `DisableCookies`.

A root `COPYING` file was not found at the attempted path during this work unit. Previous README/directory evidence described embeddable contents as MIT. Keep this as a **license/path confirmation gap** rather than copying the Windows-client MIT conclusion onto this separate component.

---

# C. Mandatory SBOM record for PVNetwork

For every shipped platform/component record:

- project/repository;
- canonical vs mirror provenance;
- exact commit/tag/module version;
- exact binary/library artifact hash;
- programming language/toolchain version;
- architecture/ABI;
- direct/transitive dependencies;
- native bundled libraries/drivers;
- data assets;
- license and notice obligations;
- vulnerability/advisory state;
- whether modified by PVNetwork;
- source/relinking obligations if applicable;
- Store/package-specific constraints.

## Supply-chain policy

Prefer controlled/reproducible builds of critical networking components. If prebuilt upstream artifacts are consumed:

- pin immutable version;
- verify digest/signature/provenance;
- archive source revision relationship;
- scan exact binary/dependency set;
- do not download a moving “latest” component at runtime for production networking code.

## Upgrade gate

Before upgrading WireGuard/AWG components:

1. diff config/API changes;
2. diff dependency/SBOM;
3. review advisories/issues/releases;
4. run config import/export tests;
5. run start/stop/reconnect/network-handover tests;
6. test route/DNS/per-app behavior;
7. test secure storage migration;
8. test cross-version server/client compatibility;
9. verify Store packaging/signing;
10. retain rollback evidence.

## Remaining gaps

- exact license/advisory table for every transitive dependency;
- exact Android/Apple AmneziaWG core version graph;
- exact `amneziawg-windows` file-level license confirmation;
- Wintun/driver path-level SBOM/signing evidence;
- selected production WireGuard/AWG component pins;
- final per-platform binary size/performance/security scan.

These gaps can remain explicit at v1 handoff; final exact-build SBOM is necessarily repeated at implementation/release time.
