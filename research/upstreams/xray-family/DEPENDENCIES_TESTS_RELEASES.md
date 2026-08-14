# Xray-core — Dependencies / Tests / Release Surface

Research date: 2026-08-14

State: `IN-RESEARCH`; evidence only.

Pinned source:

`XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`

Latest stable release observed during review:

`v26.3.27` (2026-03-27).

The pinned `main` commit is newer than the stable release and is therefore not automatically the PVNetwork release candidate.

## Go/dependency surface

Pinned `go.mod` declares Go 1.26.

Direct dependencies include major categories such as:

- QUIC implementation (`apernet/quic-go`);
- cryptographic/network primitives (`cloudflare/circl`, `golang.org/x/crypto`);
- TLS fingerprinting (`refraction-networking/utls`);
- REALITY (`xtls/reality`);
- WebSocket (`gorilla/websocket`);
- DNS (`miekg/dns`);
- STUN (`pion/stun`);
- gRPC/protobuf;
- WireGuard/Wintun;
- netlink/platform networking;
- Shadowsocks-related packages;
- YAML/TOML serialization;
- gVisor networking pieces;
- BLAKE3 and other supporting libraries.

There are additional indirect dependencies including compression, DTLS, qpack, networking namespace and tooling modules.

### PVNetwork consequence

The root MPL-2.0 license is not a full distribution-license answer. Before shipping any Xray build, generate a per-platform SBOM containing:

- exact module version/commit;
- license;
- linked/bundled/dynamically loaded classification;
- security advisory state;
- which enabled Xray feature pulls it into the build/runtime package;
- attribution/source obligations.

## Bundled external components

The upstream README explicitly notes that certain optional third-party components can be bundled in ZIP distributions and remain separate works under their own licenses. It specifically documents official prebuilt Wintun distribution in Windows packages.

PVNetwork must therefore distinguish:

1. Go module dependencies compiled into the Xray binary;
2. separately bundled native DLL/assets;
3. GeoIP/GeoSite or other downloaded data assets;
4. product-owned PVNetwork binaries/config/assets.

## Test workflow

Pinned `.github/workflows/test.yml` currently performs:

- asset existence checks for GeoIP/GeoSite data used by tests;
- generated protobuf header consistency checks;
- formatting checks;
- `go test -timeout 1h -v ./...` across:
  - Windows latest;
  - Ubuntu latest;
  - macOS latest.

This is meaningful cross-platform core evidence, but it is not Android/iOS/TV/product integration evidence.

## Test source shape

The source tree contains both local unit tests and scenario-style tests.

Examples found in the pinned tree/search:

- `infra/conf/vless_test.go` — configuration conversion/validation;
- `testing/scenarios/vless_test.go` — end-to-end/scenario-style protocol behavior;
- application/router/dispatcher/stats tests;
- transport-specific tests;
- serialization/reflection tests.

PVNetwork should preserve the distinction:

- upstream internal core tests prove upstream behavior;
- PVNetwork adapter/config/import/routing/platform tests prove product integration.

## Release workflow / platform surface

Pinned tree contains release workflows plus Windows helper assets and Docker build definitions. Current stable release publishes many platform/architecture archives, including Android ABI artifacts and desktop/server-style builds for multiple operating systems.

The upstream README also documents official/container/install ecosystem references.

This establishes broad core portability, but not one universal application architecture.

### PVNetwork platform rule

For each target platform separately decide:

- subprocess vs wrapper/FFI integration;
- executable/library packaging;
- TUN/VpnService/NetworkExtension/desktop service ownership;
- background lifecycle;
- binary update strategy;
- Store feasibility;
- crash isolation;
- logs/control API path.

Do not infer iOS App Store support from a Go cross-compiled core artifact.

## Main-vs-release drift

The pinned main head is from 2026-08-12 and contains a WireGuard outbound fix after stable v26.3.27. This is direct evidence that main contains behavior not represented by the latest tagged stable release.

Before selecting a release candidate, compare:

- latest stable tag;
- current main;
- security/fix commits after stable;
- compatibility changes affecting profiles supported by PVNetwork.

## Upgrade gate for PVNetwork

A future Xray-core upgrade should require:

1. exact tag/commit comparison;
2. dependency/SBOM diff;
3. protocol/config schema diff;
4. import/export regression tests;
5. routing/DNS/TUN integration tests;
6. protocol/transport combination tests actually advertised by PVNetwork;
7. process/FFI lifecycle tests for every target platform;
8. sanitized logging/support-bundle tests;
9. rollback compatibility with stored canonical PVNetwork profiles.

## Security reporting

Pinned `SECURITY.md` instructs reporters to use GitHub private security-advisory reporting for vulnerabilities. This is useful process evidence, but it does not replace a historical advisory/CVE/dependency audit for the exact release candidate.

## Remaining gaps

- complete dependency-license table;
- current vulnerability/advisory inventory for Xray plus exact dependency graph;
- release-workflow artifact matrix by OS/architecture;
- exact GeoIP/GeoSite/data-asset ownership/update behavior;
- coverage/fuzzing/static-analysis inventory;
- exact scenario-test map by protocol/transport/security layer;
- performance/resource evidence;
- final product integration model per platform.
