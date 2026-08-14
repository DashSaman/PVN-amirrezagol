# 060 — Nebula — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **060 — Nebula**

Decision: **`COMPLETE-RESEARCH-v1 / DISTINCT NOISE-BASED OVERLAY / MIT CORE / MOBILE SOURCE LICENSE GAP / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared mesh evidence:

- `research/upstreams/mesh-overlay-family/README.md`
- `research/upstreams/mesh-overlay-family/SUPPORT_REUSE_DECISIONS.md`

Nebula is a mutually authenticated software-defined overlay network based on the Noise Protocol Framework. It is not WireGuard/Tailscale/ZeroTier and has its own CA, host certificates, lighthouse discovery, firewall/security-group model and encrypted peer data plane.

## 1. Current primary source / release baseline

Repository:

- `slackhq/nebula`
- reviewed master commit: `6d124d04414e08ba47cdea3eab2e1e6fb9823a31`
- reviewed tree: `6e269ea065564c66be459f6a824d0d2a35ade586`
- reviewed head date: 2026-07-31
- latest reviewed release: **v1.11.0**, published 2026-07-23
- license: **MIT**
- implementation: Go

Current release assets include macOS, Linux, Windows, FreeBSD and multiple CPU architectures with release SHA256 digests.

The reviewed latest master fix is directly useful lifecycle evidence: Linux TUN address enumeration can race with concurrent IPv6 address-state changes; the fix tolerates `ErrDumpInterrupted` instead of failing startup. PVNetwork regression tests for any Nebula integration should include interface/address update races rather than treating startup as a single atomic operation.

## 2. Current architecture / crypto boundary

Current README defines Nebula as:

- mutually authenticated peer-to-peer overlay;
- Noise Protocol Framework-based;
- Nebula CA certificates assert overlay IP, node name and group membership;
- groups drive provider-independent firewall/security policy;
- lighthouses provide peer discovery;
- peers use UDP hole punching behind NAT/firewalls where possible;
- encrypted peer communication can span clouds, datacenters and endpoints.

Current default cryptographic description:

- Curve25519/default curve for handshakes/signatures;
- ECDH key exchange;
- AES-256-GCM in default configuration;
- optional P256 CA/handshake path for certain compliance use cases;
- optional BoringCrypto build mode exists but is not upstream's recommended default.

PVNetwork must use upstream Nebula crypto and certificate machinery, not reimplement Noise/ECDH/AEAD.

## 3. Identity / CA / persistence boundary

A Nebula network has:

- CA certificate + **CA private key**;
- per-host private key;
- per-host Nebula certificate containing overlay identity/IP/groups/lifetime;
- host configuration including lighthouses/firewall/routes/listeners;
- overlay interface/runtime peer state.

Upstream README explicitly warns that `ca.key` is the most sensitive file and must **not** be copied to individual nodes. PVNetwork rules:

- CA private key is server/admin provisioning material and should normally never enter an end-user client;
- host private key is protected device identity material;
- host certificate/CA certificate are non-secret but security-sensitive identity/trust state;
- certificate expiry/CA rotation is first-class lifecycle state;
- group/firewall policy is structured security policy, not an opaque config blob;
- support bundles redact private keys and sensitive topology/peer addresses.

## 4. Platform / mobile source evidence

Core README lists:

- Linux;
- Windows;
- macOS;
- FreeBSD;
- mobile iOS and Android through `DefinedNet/mobile_nebula`.

Current mobile source repository:

- `DefinedNet/mobile_nebula`
- reviewed main commit: `c9bef19e519a35d35d37f5d4cef867fdebb7e2e9`
- reviewed tree: `d1fad4ebfb7047be52220e1521745e6c20dcadbe`
- reviewed head date: 2026-08-03
- Flutter/Dart UI plus Android, iOS and Go/gomobile integration;
- current README pins Flutter 3.44.6 and Android NDK 28.2.13676358 and documents iOS XCFramework/gomobile build flow;
- current workflows include gofmt, Go tests, mobile smoke/build and release work.

### Critical mobile-license gap

At the reviewed mobile root:

- no `LICENSE` file is present in the root listing;
- root README does not provide an explicit source-code reuse license in the reviewed material.

Therefore:

**mobile source is `REFERENCE-ONLY / DO-NOT-COPY UNTIL LICENSE IS VERIFIED`**, even though the main Nebula core is MIT.

The core's MIT license does not automatically license separately published Flutter/Swift/Kotlin mobile wrapper source.

## 5. Server/discovery / deployment role

Nebula uses lighthouses as peer discovery nodes. Current README states:

- at least one routable lighthouse is strongly recommended;
- the lighthouse overlay/public mapping should be stable;
- current default UDP port referenced for Nebula traffic is 4242;
- lighthouse resources can be small;
- lighthouses are discovery infrastructure, not centralized payload decryption proxies.

Managed Nebula from Defined Networking is a separate commercial/provider option that manages PKI/lighthouses; hosted product terms/control plane are separate from the MIT core.

PVNetwork should not describe a lighthouse as a VPN server in the conventional client/server sense.

## 6. UI / config / installation evidence

Core Nebula is primarily CLI/config-file oriented:

- `nebula` runtime;
- `nebula-cert` CA/host certificate tooling;
- YAML configuration (`examples/config.yml`);
- lighthouse role/config;
- static host map;
- firewall rules/groups;
- listen/TUN/route settings;
- logging/metrics/SSH-like service options in current docs/config surface.

Current distribution/package evidence includes:

- GitHub release archives;
- Arch;
- Fedora;
- Debian;
- Alpine;
- Homebrew;
- Docker.

Current mobile app provides the consumer Android/iOS GUI reference. Exact screen/menu research is V2; V1 correctly records the mobile source/build architecture and its unresolved license boundary.

## 7. Tests / CI / current maintenance

Current core repository contains extensive Go tests and CI/release workflows. Current release 1.11.0 was generated in July 2026, and master continues receiving platform/network fixes.

Current mobile repo's August 2026 head is itself a CI-cache correctness change. Its workflows explicitly run formatting, Go tests, smoke tests and release builds. This is useful quality evidence but not PVNetwork certification.

## 8. PVNetwork reuse decision

Classification:

**`OPTIONAL DISTINCT OVERLAY ENGINE / MIT CORE REUSE-CANDIDATE / MOBILE WRAPPER REFERENCE-ONLY PENDING LICENSE`**

Recommended approach if Nebula support is selected:

1. use official MIT Nebula core/library/process and certificate formats rather than recreating Noise/crypto/network discovery;
2. implement a dedicated overlay adapter that understands CA/host certificates, groups/firewall, lighthouses and certificate rotation;
3. do not import the CA private key into normal client profiles;
4. do not copy `mobile_nebula` UI/source until an explicit compatible source license is found;
5. treat Managed Nebula as an optional external provider/control service with independent API/terms review;
6. reuse mobile repo only as platform/lifecycle/UI behavior reference until legal clarity exists.

## 9. 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / Nebula conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | Official MIT Nebula core plus official linked mobile app/source and managed-provider option are role-separated. |
| 2 | Canonical sources pinned | PASS | Exact core commit/tree/release and exact mobile commit/tree are pinned. |
| 3 | Licenses reviewed | PASS | Core MIT; mobile wrapper has no explicit root license in reviewed source and is DO-NOT-COPY pending clarification; managed product separate. |
| 4 | Complete source-tree reference | PASS | Exact core and mobile trees identified; core config/cert/network/platform/test and mobile Flutter/iOS/Android/gomobile paths are mapped. |
| 5 | Languages/build systems | PASS | Core Go/Make; mobile Flutter/Dart + Swift/Kotlin/Go/gomobile/Xcode/Gradle; release/package ecosystems mapped. |
| 6 | Architecture | PASS | CA/cert identity + lighthouse discovery + Noise-authenticated encrypted peer overlay + firewall/groups + TUN is explicitly separated. |
| 7 | Core/engine integration | PASS | Official MIT core is preferred; mobile wrapper is not a licensed reuse candidate yet; managed provider is separate. |
| 8 | UI/menu map | PASS for V1 | Core CLI/config and current mobile app/source provide behavioral UI reference; exhaustive screens remain V2 and mobile source rights remain unresolved. |
| 9 | Config/import/export | PASS | YAML config, CA/host certificates/keys, lighthouse/static host map, group/firewall/network fields and provisioning lifecycle are mapped. |
| 10 | Persistence/secrets | PASS | CA key, host key, host certificate, CA cert, group/firewall/config and runtime peer state have distinct ownership; CA-key client prohibition explicit. |
| 11 | Platform integrations | PASS for research | Core desktop/server packages and official Android/iOS source/build paths are current; Store lifecycle remains later. |
| 12 | Logs/diagnostics | PASS | Core logging/metrics/TUN/peer/lighthouse/cert errors and mobile smoke/test surfaces are mapped; keys/topology/cert identity require redaction. |
| 13 | Assets/screenshots | PASS for V1 | Core/mobile official assets may be referenced; mobile/source brand rights are not inferred from core MIT license. |
| 14 | Meaningful alternatives/forks | PASS | Managed Nebula, mobile wrapper and independent mesh platforms are distinguished; lighthouses are discovery not generic VPN servers. |
| 15 | Issues/PRs/releases/advisories | PASS | v1.11.0 July 2026 release, current master TUN/IPv6 race fix and active mobile CI maintenance are source-backed. |
| 16 | Relevant forums/docs | PASS | Current README, official Nebula docs/guides and NebulaOSS community/Managed Nebula references are identified. |
| 17 | Tests/CI | PASS | Core Go tests/release CI plus mobile gotest/smoke/release workflows are current. |
| 18 | Store/privacy/security | PASS | Noise/AEAD, CA/host key ownership, lighthouse metadata, firewall/groups, mobile source-license gap, managed-provider boundaries and Store lifecycle are explicit. |
| 19 | PVNetwork reuse decision | PASS | Optional dedicated Nebula adapter using MIT core; mobile wrapper reference-only until licensed; no custom crypto. |
| 20 | Uncertainties | PASS | Exact production core release/SBOM, mobile wrapper licensing, Managed Nebula API/terms, cert rotation UX, Store lifecycle/performance and V2 deployment/UI/wire evidence remain later. |

## 10. Later acceptance work — not V1 blockers

- exact selected release/source/SBOM/advisories and algorithm build mode;
- certificate issuance/expiry/rotation/revocation and safe provisioning;
- multiple lighthouse/discovery/NAT behavior and failover;
- firewall/group rule semantics and route/DNS/TUN cleanup;
- Android/iOS wrapper license resolution before any code reuse;
- mobile/background/Store lifecycle;
- Linux/macOS/Windows package/update/uninstall;
- performance/MTU/IPv4/IPv6/network-change tests;
- V2 lighthouses/server deployment, client UI menus, crypto/wire flow and topologies.

## Final V1 decision

All 20 V1 research gates have traceable evidence or explicit mobile-license/provider boundaries. Entry 060 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
