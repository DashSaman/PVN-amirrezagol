# Shadowsocks Family — Current Source / Client Audit

Review date: 2026-08-14

Scope: shared V1 evidence for entries **040 Shadowsocks** and **041 Shadowsocks 2022**. These two entries remain semantically distinct even when one engine supports both.

## 1. Primary engine candidate — shadowsocks-rust

Canonical repository:

`shadowsocks/shadowsocks-rust`

Reviewed current master:

`9214fdaf1f8938a20f6c295b1260c69a625d1f4f`

Reviewed recursive tree:

`858c964ef63544d8b1c8b7c4d328c487bcd92e10`

Reviewed current release reference:

`v1.24.0` — published 2025-12-10

License:

**MIT** (`LICENSE` at reviewed pin)

Activity evidence:

The reviewed master commit is dated 2026-08-13 and updates a Rust dependency. This is an actively maintained upstream at review time; production still requires an exact release/build/SBOM freeze rather than following moving `master`.

### Architecture

The README explicitly separates three Rust crates:

- `shadowsocks` — core protocol library;
- `shadowsocks-service` — client/server/service implementations;
- `shadowsocks-rust` — binaries for common services.

Top-level/source/build evidence includes:

- Rust `Cargo.toml` / `Cargo.lock`;
- `bin/sslocal.rs`, `ssserver.rs`, `ssmanager.rs`, `ssurl.rs`, Windows service support;
- library/service source trees;
- Dockerfile;
- Kubernetes/Helm material;
- build scripts/Cross configuration;
- ACL examples/tooling;
- multiple GitHub Actions workflows for build/test, MSRV, release/nightly, clippy and deny checks;
- CircleCI/AppVeyor legacy/secondary CI material.

### Platform/package surface

The reviewed release/README documents/builds artifacts or installation paths across Linux, macOS, Windows and Android targets, plus crates.io, Homebrew, Snap, static release artifacts, containers and other packaging ecosystems.

Source portability/build artifacts are not PVNetwork platform certification.

### Optional service features

The current README exposes modular features for local HTTP/HTTPS, tunnel, SOCKS4/4a, transparent proxy, DNS/FakeDNS, TUN, SIP008 online configuration and other service functions. These are application/service layers around the core protocol and must not be represented as alternative Shadowsocks cipher/protocol versions.

### Classic AEAD vs 2022

The current engine explicitly separates:

- regular AEAD cipher support; and
- `aead-cipher-2022` for AEAD-2022 / SIP022 methods.

It also labels legacy stream ciphers as deprecated/unsafe when the corresponding optional feature is enabled.

PVNetwork rule:

**Entry 040 classic Shadowsocks and Entry 041 Shadowsocks 2022 remain separate typed protocol/cipher generations even if implemented by the same engine.**

Do not silently replace one with the other and do not describe parser support as interoperability certification.

## 2. Official Android reference client

Repository:

`shadowsocks/shadowsocks-android`

Reviewed master:

`ae28fd91931fe4d2d5aab044de9ceaf9ed07ad56`

Reviewed head/version change:

5.3.5-nightly source state, commit dated 2026-02-10.

License:

**GPLv3-or-later**.

Reuse classification:

**REFERENCE-ONLY by default for a closed commercial PVNetwork GUI.**

Important architecture evidence:

- Android app/Core split;
- embedded Rust `shadowsocks-rust` source/core integration;
- Android build supports multiple ABIs;
- 2026 workflow includes emulator E2E testing against a locally built `ssserver`;
- profile state uses Room entity `Profile`;
- preferences use Room-backed `DataStore` abstractions;
- VPN/proxy/routing/app-selection fields are stored separately from protocol endpoint fields;
- profile parser supports `ss://` URI and JSON-style profile import paths.

### Persistence lesson

The reviewed `Profile` entity includes fields such as:

- host;
- port;
- password;
- method;
- routing/DNS/app selection/plugin/fallback state.

Because the password is part of the persistent profile model in this reference implementation, PVNetwork must **not copy that persistence decision blindly**. PVNetwork should store reusable Shadowsocks secrets through platform secure credential storage and keep only a reference in the canonical non-secret profile.

`DataStore.kt` shows separate public/private Room preference stores and direct-boot/service-mode preferences. This is useful architecture evidence, but not proof that secret-at-rest requirements for PVNetwork are satisfied.

## 3. Official Windows reference client

Repository:

`shadowsocks/shadowsocks-windows`

Reviewed branch/head:

`v4@891d971682eefcaa2e640258d3b352a3ad3b2233`

Commit history visible at review time includes a 2025 committer date for the reviewed v4 head.

License:

**GPLv3** (`LICENSE.txt`).

Implementation role:

C#/.NET Windows GUI/tray reference with server-selection/menu/config/system-proxy behavior. It is valuable for Windows UX/state/history but is **REFERENCE-ONLY by default** for a closed commercial PVNetwork product.

## 4. Other client/core evidence

The existing PVNetwork Xray/client dossiers remain useful for:

- v2rayN / v2rayNG multi-protocol GUI behavior;
- subscription/import handling;
- routing/DNS/TUN ownership;
- lifecycle/logging/state lessons;
- cross-protocol canonical model design.

Those GUI implementations do not replace the canonical Shadowsocks protocol/engine evidence above and their licenses remain separate.

## 5. Configuration / import / subscription model

Important formats/behaviors for V1 research:

- `ss://` URI import/export and QR representation;
- JSON configuration used by engine/service deployments;
- SIP008 online configuration/subscription support where enabled;
- plugin/plugin-options as an extension layer distinct from core Shadowsocks cipher/protocol semantics;
- endpoint, password and method/cipher as typed protocol fields;
- routing, DNS, app filtering, TUN and proxy-mode fields as product/platform fields rather than Shadowsocks protocol identity.

PVNetwork must preserve original imported payload metadata where safe and report unsupported/lossy fields instead of silently dropping them.

## 6. Build/test/release evidence

Current `shadowsocks-rust` tree includes:

- GitHub Actions build/test;
- MSRV checks;
- release/nightly build workflows;
- clippy checks;
- cargo-deny checks;
- platform release builds;
- Docker and package material.

The Android client additionally contains a 2026 E2E emulator workflow that builds the Android app and a local `ssserver`, starts an Android emulator and runs end-to-end tests.

This is strong upstream quality evidence, but PVNetwork still needs its own regression/interoperability tests for the exact selected engine/version/configuration.

## 7. Security / supply-chain boundary

- Do not enable deprecated stream ciphers as a compatibility default.
- Keep classic AEAD and Shadowsocks 2022 explicitly typed and validated.
- Treat password/PSK material as secrets and keep it out of ordinary profile storage/logs/telemetry/support bundles.
- Plugins are separate executable/dependency surfaces and require their own license/update/security audit.
- A production engine build requires exact release/commit, Cargo lock/SBOM/license/vulnerability review, reproducible/build-origin evidence where feasible, and regression tests.
- GUI GPL obligations do not automatically apply to the MIT Rust core, and the MIT core does not make GPL GUIs permissively reusable. Audit components separately.

## 8. PVNetwork shared decision

Primary reusable candidate:

**`shadowsocks-rust` core/service crates — REUSE-CANDIDATE, subject to dependency/SBOM/legal/security review.**

Reference clients:

- `shadowsocks-android` — architecture/UI/lifecycle/E2E reference; GPLv3+;
- `shadowsocks-windows` — Windows GUI/tray/config reference; GPLv3;
- Xray-capable multi-protocol clients — import/routing/common-UI reference under their separate licenses.

Canonical product model must keep:

`protocol-generation + cipher/method + endpoint + secret-reference + plugin/extension + product routing/DNS/TUN metadata`

as separate typed concerns.

## 9. Remaining later evidence

Not V1 blockers when the 20 research gates are otherwise satisfied:

- exact production binary/release/SBOM choice;
- real-device client lifecycle;
- server/client version interoperability;
- Store review;
- performance/throughput/battery benchmarking;
- exhaustive menus/screenshots;
- server installer/panel survey;
- full wire/cryptography/deployment documentation required by V2.
