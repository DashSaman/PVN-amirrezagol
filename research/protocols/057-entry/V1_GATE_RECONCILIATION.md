# 057 — ZeroTier — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **057 — ZeroTier**

Decision: **`COMPLETE-RESEARCH-v1 / DISTINCT ENCRYPTED OVERLAY ECOSYSTEM / OPTIONAL ENGINE INTEGRATION / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared mesh evidence:

- `research/upstreams/mesh-overlay-family/README.md`
- `research/upstreams/mesh-overlay-family/SUPPORT_REUSE_DECISIONS.md`

ZeroTier is its own cryptographically addressed overlay/network virtualization architecture. It is not WireGuard and must not be translated into a normal server/port VPN profile.

## 1. Current source / release baseline

Primary repository:

- `zerotier/ZeroTierOne`
- default branch: `dev`
- reviewed commit: `899352e38405968516bb12a770f0ac02f6058fa8`
- reviewed tree: `1abcbd6b6d5a0608105972fd4bde1ef470c70236`
- reviewed head date: 2026-07-22
- latest reviewed release: **ZeroTier One 1.16.2**, published 2026-05-28.

Current README describes ZeroTier as combining:

- `VL1`: cryptographically addressed secure peer-to-peer network;
- `VL2`: Ethernet virtualization/SDN layer;
- end-to-end encrypted peer traffic;
- mostly peer-to-peer traffic with relay fallback where direct connectivity is unavailable.

Current 1.16.2 release notes include Windows network-join/leave fixes, compiler/toolchain cleanup and internal central-controller schema/build updates, showing active 2026 maintenance.

## 2. Critical path-level license model

The reviewed root `LICENSE.txt` explicitly states:

- `node/`, `osdep/`, `service/` and most code outside `ext/` and `nonfree/` -> see `LICENSE-MPL.txt` (MPL-2.0);
- `nonfree/` -> separate source-available/non-free license;
- `ext/` -> each dependency retains its original license.

Current `build.md` adds an important production distinction:

- default official-style daemon builds include bundled FileDB network-controller code from `nonfree/` (`ZT_NONFREE=ON`);
- a purely free daemon can be built with `ZT_NONFREE=OFF` / free presets;
- source portability and the root repository's visibility do **not** mean every bundled feature is open-source under one permissive license.

PVNetwork rule:

**Never label ZeroTierOne simply “MPL-2.0” without identifying the exact selected paths/build flags.**

Reuse classification:

- free node/osdep/service client/data-plane paths: **`REUSE-CANDIDATE / MPL-COMPLIANCE REQUIRED`**;
- `nonfree/` controller portions: **`NEEDS-SEPARATE COMMERCIAL/LEGAL REVIEW`**;
- `ext/`: dependency/path-level audit mandatory.

## 3. Current architecture / service boundary

Current repository/build docs separate:

- `node/` — core overlay/data-plane logic;
- `osdep/` — platform network/device integration;
- `service/` — long-running service and local management API;
- controller/network-authorization components;
- `nonfree/` controller/server pieces;
- `ext/` dependencies;
- Windows-specific build/service files;
- Rust `rustybits/` support/components in current tree.

Current service model:

`platform service/GUI/CLI`

`-> local zerotier-one service`

`-> local JSON management API`

`-> node identity + joined network state`

`-> controller-delivered network membership/config/rules`

`-> virtual Ethernet interface + ZeroTier peer/relay data plane`

Important: a network controller authorizes/configures members. Network membership, identity, roots/moons/controller state and virtual interface configuration are not equivalent to a raw tunnel endpoint profile.

## 4. Local API / persistence / credentials

Current `build.md` documents:

- local JSON API normally at `127.0.0.1:9993`;
- broader bind is possible only with deliberate `allowManagementFrom` configuration;
- `zerotier-cli` calls the service API;
- `authtoken.secret` in the home directory is the local management API secret;
- service home directories are platform-specific:
  - Linux `/var/lib/zerotier-one`;
  - BSD `/var/db/zerotier-one`;
  - macOS `/Library/Application Support/ZeroTier/One`;
  - Windows `ProgramData\ZeroTier\One`.

PVNetwork requirements:

- local management token is a privileged reusable secret and must never appear in ordinary profile JSON/log/support bundles;
- node identity/private key material is backend-owned protected state;
- joined network IDs, assigned addresses, routes/rules and member status are structured network state;
- leaving/deleting a network is lifecycle/state mutation, not merely disconnect;
- management API must remain loopback/local by default unless a separately secured/admin use case exists.

## 5. Platforms / installers / GUI evidence

Current build docs provide first-class source/build/install behavior for:

- macOS;
- Linux;
- Windows (Visual Studio 2022 on Windows 10+);
- FreeBSD/OpenBSD/NetBSD variants;
- source builds, CMake presets, free vs default/nonfree builds and services/boot startup.

Current README says Android and iOS applications are available through their app stores. The reviewed main repo is the core/service source baseline; proprietary/store UI details are not inferred as open source unless their exact source repository is separately verified.

V1 UI map:

- canonical service/CLI: join network, leave network, node/network status, peers, addresses/routes and service/API state;
- official mobile/desktop GUI: behavioral reference for network list, join/auth, connectivity and settings;
- web/central controller interfaces: network/member/rule/admin behavior, not part of the open client core by implication.

PVNetwork should model ZeroTier as a **Mesh/Ecosystem Adapter** with account/controller/network/device state, not as one server connection card.

## 6. Tests / build / security evidence

Current `build.md` documents `make selftest` for internal tests/build environment checks.

Current repository activity includes 2026 dependency-alert maintenance; the reviewed head merged Dependabot-related cleanup/updates in Rust dependencies. Release 1.16.2 includes Windows lifecycle fixes and compiler/toolchain updates.

Production review still requires:

- exact selected release/source archive;
- exact free/nonfree build mode;
- dependency/SBOM/license/advisory scan;
- node/controller/mobile version compatibility;
- real overlay/relay/NAT tests.

## 7. PVNetwork reuse decision

Classification:

**`OPTIONAL DISTINCT OVERLAY ENGINE / DO NOT INCLUDE MERELY FOR PROTOCOL COUNT`**

If users require joining ZeroTier networks:

1. integrate official free client/service components or controlled subprocess/API boundary rather than reimplementing the overlay cryptography;
2. keep node identity, controller/network membership and local API state in a dedicated mesh adapter;
3. choose free/nonfree build path deliberately and document MPL/source-disclosure obligations;
4. do not bundle `nonfree/` controller simply because it is present in the repository;
5. do not claim all traffic is always direct peer-to-peer; relay/fallback topology exists.

## 8. 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / ZeroTier conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | Official ZeroTier One service/client is primary; official mobile/desktop apps and controller are role-separated behavioral references. |
| 2 | Canonical sources pinned | PASS | Exact current dev commit/tree plus 1.16.2 release are pinned. |
| 3 | Licenses reviewed | PASS | MPL free paths, `nonfree/` source-available paths and `ext/` original licenses explicitly separated. |
| 4 | Complete source-tree reference | PASS | Exact current tree plus documented node/osdep/service/controller/ext/nonfree/windows/rustybits structure is captured. |
| 5 | Languages/build systems | PASS | Primarily C/C++ core/service with Make/Visual Studio/CMake and current Rust component/dependency paths; platform presets documented. |
| 6 | Architecture | PASS | Service/API -> identity/network/controller config -> virtual Ethernet -> encrypted overlay/direct-or-relay path is mapped. |
| 7 | Core/engine integration | PASS | Official service/library/client code or subprocess/local API boundary is preferred; controller and nonfree server code are separate. |
| 8 | UI/menu map | PASS for V1 | Service/CLI/admin behavior and mobile/desktop behavioral references map network join/leave/status/settings; no proprietary GUI source is fabricated. Exhaustive menus remain V2. |
| 9 | Config/import/export | PASS | Network IDs/membership, local config, controller/member state and service API are mapped; this is not a simple URI/share-link protocol. |
| 10 | Persistence/secrets | PASS | Node identity, `authtoken.secret`, controller credentials and home/state directories are separately owned/protected; network metadata is structured state. |
| 11 | Platform integrations | PASS for research | macOS/Linux/Windows/BSD build/service paths and Android/iOS app availability are documented; exact Store/mobile source/lifecycle remains later. |
| 12 | Logs/diagnostics | PASS | Local service/API/selftest/status/peer/network diagnostics are mapped; identity/token/network metadata needs redaction. |
| 13 | Assets/screenshots | PASS for V1 | ZeroTier GUI/web assets are branding/copyright-controlled references; core source license does not grant automatic product-brand reuse. |
| 14 | Meaningful alternatives/forks | PASS | Other mesh systems and free-vs-nonfree controller options are explicitly distinguished; no attempt to translate to WireGuard. |
| 15 | Issues/PRs/releases/advisories | PASS | Current 2026 dev activity, dependency-alert cleanup and release 1.16.2 lifecycle/compiler fixes are pinned. |
| 16 | Relevant forums/docs | PASS | Current README, build/service API docs and ZeroTier docs are primary; controller docs are treated by license boundary. |
| 17 | Tests/CI | PASS | `make selftest`, current dependency/build pipelines and release maintenance provide upstream quality evidence; product interop tests remain later. |
| 18 | Store/privacy/security | PASS | End-to-end encrypted data plane, relay metadata/topology, node identity, controller/member metadata, local API token exposure and nonfree/license paths are explicit. |
| 19 | PVNetwork reuse decision | PASS | Optional dedicated ZeroTier mesh adapter using official client/service code/API; no forced translation into ordinary VPN profiles. |
| 20 | Uncertainties | PASS | Exact selected free/nonfree release build, controller compatibility, mobile GUI/source/Store behavior, dependency/SBOM, relay/NAT performance and full V2 deployment/UI/crypto/wire evidence remain later. |

## 9. Later acceptance work — not V1 blockers

- exact release/source/SBOM and free/nonfree build choice;
- node identity/key rotation and secure local API token lifecycle;
- controller/self-hosted/hosted membership authorization;
- direct vs relay path behavior under NAT/firewall/network changes;
- managed routes/rules/DNS and virtual-interface cleanup;
- desktop/mobile install/update/uninstall and Store lifecycle;
- IPv4/IPv6, MTU and performance;
- V2 server/controller installers, exhaustive admin/client menus, cryptography/data path and deployment topologies.

## Final V1 decision

All 20 original V1 research gates have traceable evidence or explicit proprietary/path-license boundaries. Entry 057 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
