# 025 — Check Point VPN — snx-rs Current Source Audit

Review date: 2026-08-14

Purpose: original `COMPLETE-RESEARCH-v1` evidence for the strongest public Check Point interoperability implementation. This is not an implementation approval and not a claim of official Check Point compatibility certification.

## Exact current pin

Canonical repository:

`ancwrd1/snx-rs`

Current reviewed release:

- release: `v6.2.4`
- published: 2026-08-12
- annotated tag object: `875a2b1237784b8add62ea808e2e057ec06afde4`
- exact commit: `a263c47cecdbbc019bc77c482bb77525a02e20a1`
- tag signature: GitHub reports the annotated PGP signature as verified
- repository default branch: `main`
- license: `AGPL-3.0`

The complete recursive Git tree for the exact commit was captured through the GitHub tree API and returned `truncated=false`.

## Source/build architecture

The repository is a Rust Cargo workspace. At the reviewed release it includes:

- `crates/snxcore` — protocol/session/platform/network core;
- `crates/i18n` — localization assets and lookup;
- `apps/snx-rs` — CLI/service application;
- `apps/snxctl` — control CLI;
- `apps/snx-rs-gui` — Slint GUI frontend;
- platform-specific Windows/macOS/Linux networking, resolver, routing, stats and keychain modules;
- packaging for Debian/RPM, Windows WiX/MSI and macOS;
- GitHub Actions CI/release workflows;
- deterministic source-level test fixtures under `crates/snxcore/tests`.

Minimum Rust version documented for source builds is 1.88. Normal dependencies include OpenSSL, SQLite/fontconfig and optional GTK4/WebKit6 for the mobile-access embedded-browser feature. The project also documents vendored OpenSSL/static Linux build modes.

## Core protocol/data-path evidence

The source tree separates:

- `tunnel/ipsec` — Check Point-related IPsec path;
- Linux XFRM native IPsec path;
- userspace TUN/ESP path used where kernel XFRM is unavailable/unsuitable;
- NAT-T probing and UDP transport;
- proprietary Check Point TCPT-over-TCP transport;
- `tunnel/ssl` legacy SSL tunnel path;
- gateway discovery/session/controller/authentication logic;
- platform route/DNS/network lifecycle.

Current project documentation states:

- IPsec is the default tunnel mode;
- IPsec ESP commonly uses UDP 4500/NAT-T;
- the implementation can fall back to Check Point TCPT over TCP 443;
- legacy SSL tunnel is available for older/problematic gateways;
- macOS/Windows use userspace TUN/ESP rather than Linux kernel XFRM.

This establishes a valuable behavioral reference but does not establish that every current Check Point Remote Access gateway/version/policy is supported.

## Authentication / SSO / certificate surface

The current implementation documents and/or contains paths for:

- username/password;
- browser-based SSO;
- MFA/OTP challenge handling;
- PKCS#8/PKCS#11/PKCS#12 certificate authentication;
- Windows system certificate store;
- HSM/token use;
- machine-certificate-related behavior;
- embedded WebKit portal/mobile-access mode where built with the relevant feature.

Authentication state is separate from tunnel establishment. PVNetwork must preserve that separation in any future adapter.

## UI / menu / state map

The current Slint GUI/i18n source exposes a concrete screen/menu model:

### Status / primary connection surface

- connection profile;
- server/user/login type;
- tunnel type and IPsec transport type;
- assigned IP/interface;
- DNS/search domains;
- DNS/routing/default-route status;
- RTT, bytes, packet and transfer-rate statistics;
- Connect / Disconnect;
- Settings;
- copy connection information.

### Settings

Tabs:

- General;
- Advanced.

Advanced expanders/groups include:

- DNS;
- Routing;
- Certificates;
- Misc Settings;
- UI Settings.

Configurable concepts include server, auth method, IPsec/SSL selection, certificate source, username, password/keychain, DNS/search domains, routing, custom CA, certificate validation, IKE lifetime/persistence, keepalive, NAT-T port knock, default route, static/ignored routes, auto-connect, IPv6 protection, MTU, forwarding, TLS maximum version, language and themes.

### Tray menu

- Connect;
- Disconnect;
- Connection status;
- Settings;
- About;
- Exit.

PVNetwork may learn information architecture/state/error lessons, but must not copy project identity/assets blindly.

## Configuration / persistence / secrets

The implementation supports multiple named profiles stored through `ConnectionProfilesStore` / `TunnelParams`, including add/update/remove/reorder behavior and profile files.

Important secret/security distinctions from current source/docs:

- password can be stored in OS keychain when explicitly enabled;
- Linux/macOS/Windows each have platform keychain modules;
- certificate private material may be referenced by path/store/HSM rather than copied into generic profile metadata;
- `mfa-code` is documented as transient and not written back on save;
- persisted IKE session state is a separate reconnect feature (`ike-persist`);
- trace logging can contain sensitive HTTP/request/response data and must never be the normal PVNetwork logging default;
- `ignore-server-cert` disables certificate checks and is explicitly insecure; PVNetwork must not expose insecure trust bypass as a silent fallback.

## Platform integration

### Linux

Source includes routing, DNS resolver handling, XFRM, keychain, stats and device management. The project publishes DEB/RPM/.run/tar packages and signed APT/DNF repositories, plus NixOS/AUR-oriented documentation.

### Windows

Source includes platform networking, firewall, routing, DNS/NRPT, system certificate/keychain integration, stats and WiX/MSI packaging.

### macOS

Source includes platform routing/resolver/network/keychain/stats, LaunchDaemon packaging and GUI/app packaging. Current project installation documentation explicitly says the project package is ad-hoc signed and not Apple Developer ID signed/notarized. Therefore source support/build availability is **not** App Store/notarization readiness.

### Mobile

No native Android/iOS app is established by the current snx-rs release. Do not infer mobile support from desktop source portability or the term `mobile-access`, which refers to a Check Point portal/auth feature in this project.

## CI / tests

Current `.github/workflows/ci.yml` runs on Linux, Windows and macOS and executes:

- `cargo fmt --check`;
- Clippy with warnings denied;
- workspace tests;
- mobile-access feature coverage;
- platform-specific target builds/tests.

The source tree contains `crates/snxcore/tests/test_server.rs` plus protocol handshake fixture files and additional module unit tests (for example profile-store tests).

This is meaningful source-quality evidence; it does not replace exact Check Point gateway interoperability labs.

## Packaging / release lifecycle

Release v6.2.4 publishes platform artifacts including Windows MSI, macOS packages/images and Linux DEB/RPM/run/tar variants. Release workflows and package scripts are present in the exact source tree.

The v6.2.4 changelog includes macOS installer-upgrade and GUI-exit fixes, demonstrating active packaging/lifecycle maintenance immediately before this review.

For PVNetwork, direct AGPL integration and redistribution would have material licensing obligations. Packaging maturity does not make direct embedding acceptable for a closed commercial product.

## Issues / regressions converted into PVNetwork tests

Current/high-impact upstream examples reviewed:

1. **Office Mode route gap (#217)** — tunnel can report success while the route set differs from the official Windows client and internal networks remain unreachable. Future certification must compare effective routes/policy, not only connection state.
2. **Persisted IKE/XFRM reconnect gap (#221)** — reconnect can appear connected while traffic is dead. Future state must require data-path/keepalive health, and failed persisted-session restore must fall back or surface a typed failure.
3. **Machine certificate + multi-factor behavior (#186, closed)** — enterprise machine/user certificate and additional authentication semantics can be policy-specific; must remain a distinct capability gate.
4. **NetworkManager integration request (#58)** — confirms Linux integration/DNS ownership is an architectural concern, not merely a cosmetic frontend choice.

The current project also has ongoing issues; release upgrades require issue/changelog/CI regression review.

## Ecosystem / forks

A repository search finds the canonical `ancwrd1/snx-rs`, ordinary forks and older/helper projects such as `qsnx`.

`qsnx` is a Qt GUI around the **proprietary official SNX executable**, not an independent protocol engine, so it is not selected as a reusable core. Current snx-rs is the strongest public source-level interoperability reference found in this audit.

Forks of snx-rs are not treated as meaningful alternatives unless they show maintained divergent features/fixes that are absent upstream. No such fork was selected during this v1 review.

## Official Check Point references

Official Check Point remains the authority for supported clients/gateways and enterprise policy behavior.

Current evidence sampled:

- E89.x Windows Remote Access VPN client release notes; E89.11/E89.05 standalone updates and E89.00 IKEv2/stronger algorithm support with R82 prerequisites;
- E89.x macOS release notes; current E89.23 maintenance line;
- SSL Network Extender Administration Guide;
- SNX Linux/macOS CLI parameters, including server/user/certificate/CA/debug and default HTTPS port TCP 443;
- SNX gateway configuration through Mobile Access / SSL Clients;
- official distinction between Mobile Access SNX and Remote Access VPN blade usage.

Official Check Point client/appliance code is proprietary/reference-only. No source reuse right is inferred.

## Security / privacy consequences

- AGPL-3.0 is a strong copyleft/network-copyleft boundary; direct embedding into a closed PVNetwork client is not approved by this research.
- TLS certificate validation must remain enabled by default.
- trace-level protocol dumps may contain secrets and must be protected/redacted.
- SSO cookies, passwords, MFA codes, certificate private keys/PINs and persisted IKE session state are separate secret classes.
- route/DNS/IPv6 cleanup and reconnect correctness are security properties, not just UX.
- client-identification/impersonation fields exposed by snx-rs are interoperability research data; PVNetwork must not misrepresent vendor identity or rely on spoofed vendor metadata as a default product design.
- exact Rust dependency/SBOM/security advisory review is required at any future source freeze.

## Reuse decision

`VALUABLE OPEN-SOURCE INTEROPERABILITY REFERENCE / DO-NOT-DIRECTLY-EMBED-IN-CLOSED-PRODUCT-WITHOUT-AGPL-COMPATIBLE-LEGAL-MODEL`

Preferred future choices, in order:

1. certify standards/native IPsec behavior where an exact Check Point gateway policy supports it;
2. design a legally reviewed isolation/service architecture only if snx-rs reuse remains necessary and licensing obligations are intentionally accepted;
3. otherwise use snx-rs strictly as a behavioral/source/test reference and implement only independently supportable standards/protocol components with clean ownership;
4. retain official-client-only state for unsupported proprietary/posture/provisioning combinations.

No home-grown cryptography and no unsupported claim of blanket Check Point compatibility.

## Residual after v1

Original research categories are evidence-backed. Remaining work is later implementation/reference-v2/certification:

- exhaustive gateway/server/version matrix;
- exact crypto/wire-flow and gateway deployment dossier;
- official-client full UI/update lifecycle across every platform;
- exact SSO/MFA/posture/SCV combinations;
- real gateway interoperability and packet captures;
- exact production dependency/SBOM/license model;
- PVNetwork mobile/store/notarization architecture;
- real route/DNS/reconnect/failover/security testing.
