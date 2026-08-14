# 055 — Tor SOCKS — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **055 — Tor SOCKS**

Decision: **`COMPLETE-RESEARCH-v1 / TOR-CLIENT APPLICATION INTERFACE / NOT ORDINARY SOCKS5 / NOT IMPLEMENTED / NOT CERTIFIED`**

Tor SOCKS is the application-facing SOCKS interface to a Tor client and includes Tor-specific semantics for remote name resolution, stream/circuit isolation, onion-service errors and Tor network routing. It must not be flattened into ordinary SOCKS5 entry 051.

## 1. Current Tor protocol authority

Primary current specification:

- Tor Specifications — `Tor's extensions to the SOCKS protocol`: `https://spec.torproject.org/socks-extensions.html`
- reviewed 2026-08-14 from current Tor specification site.

Current specification states that Tor supports SOCKS4, SOCKS4A and SOCKS5 with important restrictions/extensions:

- SOCKS4/4A BIND is not supported;
- SOCKS5 UDP ASSOCIATE is not supported;
- SOCKS5 BIND is not supported;
- SOCKS5 GSSAPI auth/subnegotiation is not supported;
- Tor adds remote `RESOLVE` and SOCKS5 `RESOLVE_PTR` commands;
- Tor adds extended onion-service error responses;
- SOCKS5 username/password fields can carry Tor stream-isolation/RPC extension metadata rather than merely conventional proxy login credentials;
- Tor emphasizes hostname-through-SOCKS behavior because client-side DNS can disclose destinations.

Proposal 351 (implemented in Arti 1.2.8 and C Tor 0.4.9.1-alpha) formalizes extensible SOCKS5 authentication-field use for stream isolation/RPC metadata. New product code must not assume every Tor SOCKS username/password field means ordinary RFC1929 authentication.

## 2. Current preferred embeddable client candidate — Arti

Canonical source repository:

`https://gitlab.torproject.org/tpo/core/arti.git`

Current reviewed release:

**Arti 2.5.0**, released 2026-06-30 by the Tor Project.

Current published Rust library package reviewed:

- `arti-client 0.44.0` source/API documentation;
- license: **MIT OR Apache-2.0**;
- highest-level client-only Rust API intended for applications embedding Tor;
- persistent Tor client state/cache paths are controlled through `TorClientConfig`;
- stream isolation is exposed through isolated clients / isolation tokens;
- bridge and pluggable-transport features are feature-gated;
- current docs warn that network protocols and APIs evolve, so applications must be prepared to upgrade.

Current `arti` CLI crate documentation reports version **2.5.0** and a default SOCKS proxy listener on localhost port 9150, configurable by Arti configuration.

Current Tor Project support-policy evidence:

- Linux is target/tested;
- Android, macOS, Windows and iOS are target platforms;
- current automated tests are primarily Linux, with automated builds on macOS/Windows;
- exact current support tiers are policy, not PVNetwork certification;
- production dependencies are represented by Cargo.lock and Tor Project recommends dependency-security monitoring;
- APIs below/high around `arti-client` can remain unstable.

Current integration docs include an iOS custom-wrapper/FFI example and warn about Rust panic/FFI/logging integration behavior. This is valuable mobile architecture evidence, not a Store-readiness claim.

PVNetwork reuse classification:

**`REUSE-CANDIDATE / PREFERRED MODERN EMBEDDING PATH`** for client-side Tor where required features, current API maturity, dependency/SBOM and platform support are acceptable.

## 3. Current C Tor reference

Canonical source:

`https://gitlab.torproject.org/tpo/core/tor`

Current release-line evidence at review time:

- Tor 0.4.9.x is the current stable line;
- security release 0.4.9.8 was published 2026-05-07;
- Tor Project announced 0.4.8 retirement/sunset in June 2026 and instructed users to move to 0.4.9.

License/reuse:

- C Tor is distributed under a 3-clause BSD-style license;
- optional features/dependencies can introduce additional obligations: Tor Project documentation specifically notes onion-service PoW libraries with LGPLv3 implications and the need for GPL-compatible C Tor builds when that feature is enabled.

Role:

**mature behavior/interoperability/control reference and possible subprocess/client backend**, but production bundling requires exact C Tor release/features/dependency/license review rather than relying on the core license alone.

## 4. Arti runtime / UI / configuration evidence

Current Arti docs expose:

- `arti proxy` SOCKS proxy mode;
- default localhost SOCKS port 9150;
- TOML config (`arti.toml` / example config);
- CLI config file selection and key/value overrides;
- configurable SOCKS and DNS listeners;
- log levels;
- Tor network bootstrapping and persistent cache/state;
- bridges/pluggable transports when selected features/external PTs are configured;
- direct Rust embedding through `arti-client` rather than requiring a local SOCKS boundary.

Arti itself is CLI/library-oriented, so consumer GUI menus are correctly `N/A` for the canonical upstream. Tor Browser is the principal behavioral UX/privacy reference for safe web use, but its browser/UI source and bundled components have separate licenses and are not copied simply because Tor is redistributable.

Current Tor Project guidance warns that routing a normal browser through a Tor SOCKS port is not equivalent to Tor Browser's anti-fingerprinting/privacy hardening.

## 5. Tor-specific DNS / isolation boundary

PVNetwork must distinguish:

- ordinary local DNS before a SOCKS request — privacy leak risk;
- SOCKS4A/SOCKS5 hostname requests routed through Tor;
- Tor `RESOLVE` / `RESOLVE_PTR` extensions;
- Arti optional DNS listener resolving through Tor;
- `.onion` names, which are Tor destinations and must not be leaked to normal DNS;
- stream isolation encoded through Tor SOCKS auth-extension fields / native Arti isolation APIs.

For a PVNetwork Tor profile, remote resolution must be the safe default. A generic `socks5://127.0.0.1:9150` profile cannot by itself express every Tor isolation and DNS guarantee.

## 6. Canonical PVNetwork model

Separate typed concerns:

- `network = Tor`;
- backend: Arti embedded / Arti subprocess / C Tor subprocess / approved alternative;
- SOCKS interface endpoint only when using a proxy boundary;
- remote DNS / Tor-resolve policy;
- stream-isolation policy/token ownership;
- onion-service client capability;
- bridge/pluggable-transport capability/config references;
- bootstrap/network-state policy;
- persistent Tor state/cache directories owned by backend;
- control/RPC capabilities separately from SOCKS;
- routing/TUN/per-app integration outside Tor SOCKS request semantics.

Do not use SOCKS username/password isolation metadata as a reusable login credential in the canonical model.

## 7. Persistence / privacy / logs

Tor-related state can be exceptionally sensitive.

PVNetwork requirements:

- bridge addresses/tokens, onion-service credentials, proxy credentials and private keys -> protected storage;
- persistent Tor directory/cache/guard state -> backend-owned application-private storage with safe permissions;
- Tor destinations, circuit paths, guards, bridges, user IP/system identity and activity timing -> sensitive logs by default;
- support bundles must preserve Arti/Tor safe-logging principles and not expose destinations/circuit/bridge details casually;
- stream-isolation identifiers -> transient or deliberately scoped state, not user-facing passwords;
- SOCKS listener defaults to loopback unless an explicit safe product use case requires otherwise.

Arti's own issue/design work explicitly treats destination addresses, user system identity, guards, circuits and bridge information as sensitive logging material.

## 8. 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / Tor SOCKS conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | Modern Arti client/library, mature C Tor, Tor Browser behavioral integration and ordinary SOCKS-capable apps are role-separated. |
| 2 | Canonical sources pinned | PASS | Tor specification is current; Arti release 2.5.0 and arti-client 0.44.0 are version-pinned; current C Tor stable release line 0.4.9.x/security release 0.4.9.8 is recorded. |
| 3 | Licenses reviewed | PASS | arti-client MIT OR Apache-2.0; C Tor core 3-clause BSD-style; feature/dependency license interactions retained; Tor Browser component licenses are separate. |
| 4 | Complete source-tree reference/manifest captured | PASS for V1 | Canonical Arti and C Tor GitLab repositories plus versioned Arti crate source/documentation and Tor specs are recorded. Exact production source tarball/SBOM tree freeze remains later release work. |
| 5 | Languages/build systems | PASS | Arti Rust/Cargo workspace/crates; C Tor C/build/package ecosystem; Tor Browser separate Firefox-derived application stack. |
| 6 | Architecture | PASS | App -> SOCKS/Tor API -> Tor client bootstrap/circuits/streams -> Tor network is separated from SOCKS framing, DNS/isolation/control and browser hardening. |
| 7 | Core/engine integration | PASS | Preferred Arti `arti-client` library or controlled Arti/C Tor subprocess paths are mapped; proxy-boundary vs direct library embedding is explicit. |
| 8 | UI/menu map | PASS for V1 | Canonical Tor clients are CLI/library or browser product; evidence-backed N/A for a canonical standalone SOCKS GUI. PVNetwork fields/states and Tor Browser privacy UX reference are mapped. Exhaustive menus remain V2. |
| 9 | Config/import/export | PASS | Arti TOML/CLI settings, SOCKS listener, DNS listener, bridges/PT feature config and Tor SOCKS extension semantics are mapped. |
| 10 | Persistence/secrets | PASS | Tor persistent state/cache, bridges/PT secrets, credentials, isolation state and logs have distinct ownership and sensitivity policy. |
| 11 | Platform integrations | PASS for research | Arti support policy targets Android/Linux/macOS/Windows/iOS with Linux tested; iOS FFI guidance exists; exact platform/Store lifecycle remains certification. |
| 12 | Logs/diagnostics | PASS | Arti logging/safelog guidance and Tor SOCKS extended errors/bootstrap failure domains are mapped; sensitive destinations/circuits/bridges are redacted by policy. |
| 13 | Assets/screenshots | PASS for V1 | Tor Browser/Tor Project visual assets are trademark/copyright-controlled behavioral references; no asset reuse is inferred from protocol/source licenses. |
| 14 | Meaningful alternatives/forks | PASS | Arti vs C Tor provide independent modern Rust-embedding vs mature C-daemon approaches; ordinary SOCKS clients remain application-side interfaces, not Tor engines. |
| 15 | Issues/PRs/releases/advisories | PASS | Arti 2.5.0 June 2026 release, current support/security policy and C Tor 0.4.9.x 2026 security release/sunset evidence are recorded. |
| 16 | Relevant forums/docs | PASS | Current Tor SOCKS extension spec, Arti guides/FAQ/support policy/integration docs and Tor Project release/security guidance are primary evidence. |
| 17 | Tests/CI | PASS | Arti support policy documents Linux automated tests and macOS/Windows builds; contributor docs use full-feature cargo tests; mature C Tor has its separate long-running test/release ecosystem. PVNetwork tests remain independent. |
| 18 | Store/privacy/security implications | PASS | DNS leaks, browser fingerprinting risk, sensitive logs/state, local listener exposure, bridges/PT dependencies, component licenses, current network-protocol upgrades and mobile background constraints are explicit. |
| 19 | PVNetwork reuse decision | PASS | Evaluate Arti/arti-client first for embeddable client use; compare C Tor subprocess for missing capability/maturity. Preserve Tor-specific DNS/isolation semantics instead of generic SOCKS wrapping. |
| 20 | Uncertainties explicitly listed | PASS | Exact production Arti/C Tor version/source tree/SBOM, feature parity, control/RPC needs, bridges/PT matrix, Store lifecycle, performance and V2 installers/UI/wire/topology remain later work. |

## 9. Required regression/security tests derived from research

1. No local DNS lookup for Tor-routed hostnames/onion names.
2. SOCKS4A/SOCKS5 hostname behavior and Tor `RESOLVE` semantics.
3. SOCKS5 BIND/UDP ASSOCIATE/GSSAPI must be reported unsupported rather than silently emulated.
4. Stream-isolation credentials/tokens must not be treated as ordinary login authentication.
5. Different isolation contexts must not accidentally share circuits when policy forbids it.
6. SOCKS listener exposure remains loopback-only by default.
7. Bootstrap/offline/stale-directory/network-change/reconnect states must be distinct.
8. Bridge/PT secrets and circuit/destination data must be redacted from support bundles.
9. Tor Browser-style privacy is not claimed for arbitrary third-party browsers merely because traffic uses Tor.
10. Backend-version obsolescence/network-protocol upgrade errors must be surfaced clearly.

## 10. Later acceptance work — not V1 blockers

- freeze exact production Arti/C Tor backend, source/tag/tarball, Cargo.lock/dependencies/SBOM/licenses/advisories;
- test exact SOCKS/Tor extension matrix and negative commands;
- test onion service client access, DNS/RESOLVE and isolation behavior;
- test bridges/pluggable transports if advertised;
- test Android/iOS/Windows/macOS/Linux lifecycle/background/TUN/routing integration;
- test bootstrap/reconnect/network switch/sleep/crash/cleanup;
- test privacy-safe logs and support bundles;
- measure resource/performance effects;
- complete V2 server/client install projects, UI/menu maps, cryptography/wire flow, ports/transports and deployment topologies.

## Final V1 decision

All 20 original V1 research gates have evidence or justified version/source boundaries. Entry 055 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
