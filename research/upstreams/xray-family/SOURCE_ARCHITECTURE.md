# Xray-core — Source / Architecture Map

Research date: 2026-08-14

State: `IN-RESEARCH`; source/architecture evidence only. No PVNetwork implementation/support claim.

## Pinned source

Repository: `XTLS/Xray-core`

Pinned `main` commit:

`7d214f8b094f75322fa3990f8aadad1c912f24f5`

Commit date: 2026-08-12.

Pinned tree:

`46ee908a9a67513d3c85bbf998be5d553a078109`

Recursive source manifest:

`https://api.github.com/repos/XTLS/Xray-core/git/trees/46ee908a9a67513d3c85bbf998be5d553a078109?recursive=1`

Current latest stable GitHub release observed during this review:

`v26.3.27` (2026-03-27).

The main branch is newer than the latest stable release, so PVNetwork must separately pin a release candidate and not equate `main` with a production-approved component.

## License

Repository root `LICENSE` at the pinned commit is **Mozilla Public License 2.0 (MPL-2.0)**.

MPL is file-level copyleft and allows Larger Works under other terms subject to MPL obligations for covered source. Final commercial reuse still requires:

- exact file/dependency review;
- notices/source availability for covered files/modifications;
- trademark/branding separation;
- per-platform bundled component license review;
- final legal sign-off.

Do not infer client-GUI license from Xray-core's license.

## Language / build shape

Xray-core is a Go module:

`github.com/xtls/xray-core`

Pinned `go.mod` declares Go 1.26 and a substantial dependency graph including networking, TLS/fingerprint, DNS, QUIC, protobuf/gRPC, WireGuard/Wintun and platform/network packages.

PVNetwork consequence: SBOM/license/security review must be generated from the exact pinned build, not only the root MPL license.

## Major source domains

### `core/`

Core instance/lifecycle/component assembly. `core/xray.go` is a central source boundary for the runtime instance rather than product UI logic.

### `app/`

Higher-level runtime services/modules. Pinned source contains areas such as:

- `app/dispatcher/`
- `app/router/`
- `app/dns/`
- `app/proxyman/`
- `app/policy/`
- `app/stats/`
- `app/log/`
- `app/commander/`
- `app/observatory/`
- `app/reverse/`
- `app/geodata/`
- metrics/API-oriented areas.

This makes Xray more than one outbound protocol implementation: it is a configurable networking runtime with routing, DNS, inbound/outbound management and observability components.

### `proxy/`

Protocol/proxy implementations live under protocol-specific modules. Pinned source includes dedicated VLESS inbound/outbound areas, VMess, Trojan, Shadowsocks and other proxy/network implementations.

Examples:

- `proxy/vless/inbound/`
- `proxy/vless/outbound/`
- `proxy/vmess/`
- `proxy/trojan/`
- `proxy/shadowsocks/`
- `proxy/wireguard/`
- `proxy/socks/`
- `proxy/http/`
- `proxy/freedom/`
- `proxy/blackhole/`

PVNetwork should not collapse all of these into one user-facing protocol schema without capability-specific validation.

### `transport/`

Transport/network implementation is separate from protocol modules. The tree contains `transport/internet/` and transport-specific implementations/configuration.

This confirms a key PVNetwork modeling rule:

**application protocol, security layer and outer transport are different axes.**

A VLESS profile, for example, must not be represented as a single opaque string if the product needs validation/migration across transports/security settings.

### `infra/conf/`

Human/config-file parsing and conversion lives separately from runtime protobuf/config structures.

Pinned source contains protocol-specific configuration builders such as:

- `infra/conf/vless.go`
- `infra/conf/vmess.go`
- `infra/conf/trojan.go`
- other inbound/outbound/transport/app configuration builders;
- `infra/conf/serial/` builder/serialization logic.

This is strong evidence that PVNetwork should keep its own canonical profile model separate from raw Xray JSON/TOML/YAML-facing representation and generate a runtime config through a dedicated adapter.

### `common/`

Shared networking/session/buffer/log/mux/serialization/platform utilities.

Examples found in the pinned tree include:

- `common/session/`
- `common/mux/`
- `common/log/`
- shared protocol/network primitives.

PVNetwork must not bind product models directly to common/private internals merely because they are convenient.

### `testing/` and `*_test.go`

The source has unit and scenario/integration-style tests. Examples include config tests under `infra/conf/` and scenario tests such as VLESS under `testing/scenarios/`.

A separate test/CI dossier must map this in detail.

## Runtime architecture implication

At a product level, the evidence supports this abstraction:

`PVNetwork UI / Profile / Subscription`

`-> canonical PVProfile + routing/DNS models`

`-> Xray Adapter`

`-> generated Xray runtime configuration`

`-> Xray core instance/process/library wrapper`

`-> platform network integration`

The product should treat Xray as one engine behind a stable adapter, not as the application database or UI architecture.

## Process vs embedded-library decision

Xray-core is commonly consumed as a standalone binary, while wrapper projects also expose it to mobile/application environments.

PVNetwork must separately evaluate per platform:

- subprocess/managed-process integration;
- wrapper/library/FFI integration where available and legally/Store feasible;
- lifecycle/crash isolation;
- configuration handoff;
- logging/statistics/control API;
- binary-update/SBOM obligations.

Do not choose one integration strategy globally before platform analysis.

## Configuration identity rule

Keep at least these layers distinct:

1. original imported link/file/subscription payload;
2. normalized PVNetwork canonical profile;
3. user overrides and routing/DNS policy;
4. generated engine-specific Xray runtime config;
5. transient engine/session state;
6. sanitized logs/statistics.

This prevents engine config format changes from corrupting user-owned canonical data.

## Current source/release activity lesson

The pinned `main` head from 2026-08-12 fixes a WireGuard outbound `sendThrough` behavior issue. This demonstrates that even non-VLESS subsystems inside Xray can change close to release cycles and can affect product routing behavior.

PVNetwork must run engine-upgrade regression tests across the capabilities it actually uses, not only the headline Xray protocol.

## Remaining source-architecture gaps

- exact release-tag source tree for the production candidate rather than current `main` only;
- command/CLI/config-loader lifecycle map;
- runtime API/commander/stats ownership map;
- exact per-protocol module inventory and capability matrix;
- transport/security module inventory;
- platform-specific binary/wrapper integration;
- dependency/path-level license/SBOM review;
- current security/advisory review;
- release-to-main differences relevant to PVNetwork.
