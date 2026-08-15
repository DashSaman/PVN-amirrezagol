# 055 — Tor SOCKS — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / TOR CLIENT SOCKS INTERFACE / TOR-SPECIFIC DNS+ISOLATION / NOT ORDINARY SOCKS5 / NOT IMPLEMENTED / NOT CERTIFIED`**

## Scope boundary

Entry 055 is the application-facing SOCKS interface to a Tor client. It is not ordinary SOCKS5 entry 051 and it is not the full Tor relay protocol. The product model must keep four layers distinct:

1. application SOCKS request;
2. Tor-specific SOCKS extensions / remote DNS / stream isolation;
3. Tor client state, circuits and streams;
4. Tor network/onion-routing internals.

Tor Browser privacy hardening is a separate application/browser concern; routing an ordinary browser through a Tor SOCKS listener does not automatically reproduce Tor Browser's anti-fingerprinting/privacy properties.

## Canonical specifications and current release evidence

Primary specification:

- Tor Specifications — `Tor's extensions to the SOCKS protocol`: https://spec.torproject.org/socks-extensions.html

Current documented semantics include:

- SOCKS4/4A BIND unsupported;
- SOCKS5 UDP ASSOCIATE unsupported;
- SOCKS5 BIND unsupported;
- SOCKS5 GSSAPI auth/subnegotiation unsupported;
- Tor `RESOLVE` and SOCKS5 `RESOLVE_PTR` extensions for remote name resolution;
- extended onion-service error codes;
- SOCKS5 username/password extension fields can encode stream-isolation/RPC metadata and must not automatically be treated as normal reusable login credentials.

Proposal 351 formalizes extensible SOCKS5 auth-field metadata and is recorded as implemented in Arti 1.2.8 and C Tor 0.4.9.1-alpha.

## Preferred modern client / local SOCKS server — Arti

Canonical source: `https://gitlab.torproject.org/tpo/core/arti.git`

Reviewed current release line:

- **Arti 2.5.0**, released 2026-06-30;
- `arti-client 0.44.0` library evidence from the V1 dossier;
- `arti-client` license: **MIT OR Apache-2.0**;
- Rust/Cargo workspace and direct library embedding path;
- CLI `arti proxy` local SOCKS service;
- default localhost SOCKS listener 9150, configurable through Arti config;
- canonical docs state there are currently no official Arti binaries in the referenced compile guide, so source/Cargo build is the upstream deployment path rather than an invented binary installer.

Arti configuration surfaces include `arti.toml`, config-file selection, key/value overrides, proxy SOCKS listener, optional DNS listener, log level and feature-gated bridge/pluggable-transport capabilities.

## Mature C Tor reference

Canonical source: `https://gitlab.torproject.org/tpo/core/tor`

V1 recorded the 0.4.9 stable line. Fresh Tor Project release evidence now shows the line advanced after that audit: the Tor Project release-announcement index lists security release **0.4.9.11** on 2026-06-30, and the official package archive contains `tor-0.4.9.11.tar.gz`. Therefore this V2 audit supersedes the older 0.4.9.8 V1 point-in-time release note and records **0.4.9.11 as the current reviewed C Tor release pin**.

Core C Tor licensing remains a 3-clause BSD-style model per the existing dossier, with feature/dependency obligations retained separately. Optional libraries/features must be included in the production license/SBOM freeze rather than inferred from the core license.

## Server ecosystem / installer / install matrix

For this entry, "server" means the local SOCKS service presented by a Tor client to applications, not a public VPN concentrator.

### Arti

- source/Cargo build path is canonical in current upstream docs;
- `arti proxy` exposes a loopback SOCKS listener, normally 9150;
- Linux is the strongest tested target in the existing support evidence; Android/macOS/Windows/iOS are target platforms with varying integration maturity;
- no official binary installer is invented where upstream currently documents source build;
- container/Kubernetes deployment is not a Tor SOCKS protocol requirement and no unpinned community image is promoted to canonical status.

### C Tor

- official Tor Project source/repository and signed/package archive are canonical;
- Tor Project Debian package repository is a first-party distribution path for supported C Tor releases;
- OS/distro packages exist across broader ecosystems, but exact package versions remain distribution facts and must be frozen when selected;
- Tor Browser/Tails bundles are application distributions, not generic server installers for PVNetwork reuse.

## UI / management surfaces

### Canonical Tor/Arti service UI

No canonical standalone consumer SOCKS GUI exists. Evidence-backed N/A applies at protocol level.

Arti's canonical management surfaces are CLI/config oriented:

- `arti proxy` subcommand;
- `--config` / TOML configuration;
- `--log-level`;
- key/value overrides;
- SOCKS listener and optional DNS listener;
- backend bootstrap/network state.

C Tor uses `torrc` plus the separately authenticated Tor Control Protocol. Control/RPC surfaces must not be conflated with SOCKS authentication.

Tor Browser is a privacy/UX behavioral reference, but its browser menus/assets/component licenses are separate and not copied into this dossier as a generic Tor SOCKS UI.

## Client install / product UI model

Application clients connect to the local Tor SOCKS listener or embed Arti through `arti-client`.

PVNetwork's typed profile should expose only meaningful state:

- backend: Arti embedded / Arti subprocess / C Tor subprocess / separately approved alternative;
- local SOCKS endpoint only when a proxy boundary is used;
- remote DNS / Tor resolution policy;
- stream isolation policy/token ownership;
- onion-service client capability;
- bridge/pluggable-transport config references where enabled;
- bootstrap status and backend state paths;
- control/RPC capability separate from SOCKS;
- routing/TUN/per-app integration outside SOCKS request semantics.

No fake canonical iOS/Android/TV Tor SOCKS app menu is asserted. Mobile Store/background/TUN lifecycle remains implementation/certification work, not a hidden V2 research gate.

## Cryptography and trust boundary

SOCKS framing itself does not provide Tor's anonymity or end-to-end application encryption. After the SOCKS request is accepted, the Tor client maps a stream to Tor circuits and the Tor network cryptographic layers. Exact Tor relay/circuit cryptographic algorithms belong to the current Tor protocol specifications/backend release, not to generic SOCKS5.

For this entry the critical security boundaries are:

- local application-to-Tor SOCKS listener should be loopback/private by default;
- hostname requests should be sent through Tor to avoid local DNS disclosure;
- `.onion` names must never be leaked to ordinary DNS;
- isolation metadata is privacy-routing state, not a user password;
- Tor client state/guards/bridges/circuit/destination logs are sensitive;
- Tor Browser-level anonymity must not be claimed for arbitrary applications merely because traffic traverses Tor.

## Data path / wire flow

```text
Application
  -> local SOCKS4A/SOCKS5 request
      -> optional Tor isolation metadata / remote RESOLVE semantics
  -> Arti or C Tor client
      -> bootstrap / directory state
      -> select/build Tor circuit
      -> attach application stream to circuit
      -> Tor network relays
      -> exit or onion-service path
  <- SOCKS result / Tor-specific error
  <- application TCP stream data over Tor
```

A Tor SOCKS request is primarily a TCP stream interface. Unsupported SOCKS5 BIND/UDP ASSOCIATE/GSSAPI must be surfaced as unsupported rather than silently emulated.

## Ports / transports / handshake

- Arti's documented default local SOCKS listener is **localhost:9150**, configurable;
- this is an implementation default, not a Tor network protocol port requirement;
- application handshake follows SOCKS4A/SOCKS5 plus Tor extensions;
- remote DNS uses hostname requests / `RESOLVE` extensions rather than local pre-resolution;
- after SOCKS success, the application sees a TCP stream while Tor independently builds/uses circuits underneath;
- Tor network relay transports and bridges/PTs are separate lower-layer/backend concerns.

## Deployment topologies

- application -> local Arti SOCKS -> Tor network;
- application -> local C Tor SOCKS -> Tor network;
- application -> embedded `arti-client` without local SOCKS boundary;
- Tor Browser using an external Arti SOCKS provider where supported;
- bridge/pluggable-transport assisted client path when explicitly configured;
- isolated application/profile contexts mapped to distinct stream isolation tokens/circuit-sharing policies.

This entry does not make a Tor exit/relay a PVNetwork "server" to be embedded with the client.

## Supply chain / upgrade / rollback

- prefer canonical Tor Project source/releases and first-party documentation;
- Arti source/Cargo dependency graph and lockfile require production SBOM/license/advisory freeze;
- C Tor exact release/dependencies/features require the same freeze;
- third-party binaries, containers and one-click installers are not trusted without separate source pin/review;
- backend upgrades must preserve or deliberately migrate state/cache/guard configuration and protected bridge/PT credentials;
- downgrade/rollback across Tor network protocol changes can be unsafe or unsupported and must not be assumed;
- current support policy/network release line must be checked at production freeze because Tor software evolves in response to protocol and security changes.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | Arti and C Tor local SOCKS service roles mapped; Tor Browser/Tails/ordinary apps kept as separate client/application distributions. |
| 2 | Official/community installer/deployment projects reviewed | PASS | Canonical Arti source/Cargo path, Tor Project C Tor source/archive/Debian distribution mapped; unpinned community binaries/images not trusted. |
| 3 | Server OS/container/orchestration install matrix | PASS | Arti target/support evidence and C Tor first-party/source/package paths mapped; unsupported container/Kubernetes cells are not invented as protocol requirements. |
| 4 | Server panel/UI/menu maps | PASS / N/A | No canonical SOCKS GUI; Arti CLI/TOML/listener/log surfaces and C Tor torrc/control boundary mapped. |
| 5 | Client install matrix | PASS | Local SOCKS-consumer model, Arti source/embed path and major target-platform boundary mapped; Store packaging remains implementation-specific. |
| 6 | Major client UI/menu maps | PASS / N/A | No canonical standalone Tor SOCKS client GUI; PVNetwork typed fields and Tor Browser behavioral-reference boundary documented. |
| 7 | Cryptographic design | PASS | SOCKS vs Tor circuit/onion crypto boundary explicit; backend/current Tor specs own network crypto rather than generic SOCKS. |
| 8 | Data path/wire flow | PASS | App -> SOCKS/extensions -> Tor client -> circuit/stream -> network -> target path documented. |
| 9 | Ports/transports/handshake | PASS | Arti localhost 9150 default, SOCKS4A/5 + Tor extensions and unsupported BIND/UDP/GSSAPI boundaries recorded. |
| 10 | Deployment topologies | PASS | Local Arti/C Tor proxy, embedded Arti, external Arti for Tor Browser and bridge/PT-assisted paths mapped. |
| 11 | Source/license/activity pins | PASS | Arti 2.5.0 / arti-client 0.44.0 MIT-or-Apache-2.0; C Tor current reviewed release pin refreshed to 0.4.9.11 with BSD-style core-license boundary. |
| 12 | Installer security/supply-chain risks | PASS | First-party source/release preference, Cargo/dependency/SBOM review, third-party binary/image distrust, state/log/bridge secret risk explicit. |
| 13 | Upgrade/uninstall/rollback | PASS | Backend-owned state/config/guards/bridges and release/network-protocol compatibility implications mapped; rollback not assumed safe. |
| 14 | Differences/uncertainties explicit | PASS | Tor SOCKS vs ordinary SOCKS5, DNS/isolation/auth metadata, browser privacy, Arti vs C Tor and bridges/PT/control boundaries explicit. |
| 15 | `REFERENCE_INDEX.md` complete | PASS | Added beside this audit. |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-15_TOR_SOCKS_V2_COMPLETE.md` advances to entry 056 Tailscale. |

## Final decision

Every applicable second-layer research/reference gate is evidence-backed. Entry 055 may be promoted to **`COMPLETE-REFERENCE-v2`**. This does not assert anonymity guarantees for arbitrary applications, live Tor-network certification, Store approval or PVNetwork implementation.
