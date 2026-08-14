# libXray — Cross-Platform Wrapper Candidate

Research date: 2026-08-14

State: `IN-RESEARCH / STRONG-WRAPPER-CANDIDATE`; no PVNetwork integration claim.

## Source pin

Repository: `XTLS/libXray`

Pinned main commit:

`d0ab60ae4dd91cf119c878152d12103e6f84b78a`

Commit date: 2026-08-06.

Pinned tree:

`66eaa38193982ea69835f3b37df83edea49a756a`

## License

Root `LICENSE` at the pinned revision is **MIT**.

Important: libXray wraps/depends on Xray-core, whose reviewed root license is MPL-2.0, plus its own dependency graph. The wrapper's MIT license does **not** convert underlying Xray-core or other dependencies into MIT.

PVNetwork must retain component-level notices/source obligations and generate a complete SBOM for the exact wrapper build.

## Upstream-defined purpose

The current repository's own engineering documentation describes libXray as a Go wrapper around Xray-core for mobile and desktop applications.

This makes it more directly relevant to PVNetwork than copying a complete GPL GUI client.

## Source layout from current upstream documentation

Current upstream documents boundaries including:

- `invoke.go` — structured request dispatch/response encoding;
- `invoke_model.go` — public method and typed model definitions;
- `xray/` — Xray instance lifecycle, config validation and latency/test behavior;
- `share/` — share-link parsing/validation/generation;
- `geo/` — GeoData helpers;
- `controller/` — Android socket/process integration;
- `dns/` — Android VPN-aware resolver behavior;
- `memory/` — platform memory-pressure handling;
- `nodep/` — utilities not depending on managed Xray instance;
- `cgo_bridge/` — C ABI exports for Apple/Linux/Windows/Dart FFI;
- `android_wrapper.go` — Android/gomobile interfaces;
- `build/` and GitHub workflows — cross-platform artifact construction.

This is a strong example of a narrow engine integration boundary rather than a product UI architecture.

## Public invocation model

Current upstream documents a versioned structured JSON request/response entry point rather than exposing arbitrary private Go internals directly to every platform.

The current API includes operations for categories such as:

- running/stopping/querying Xray state/version;
- validating/testing generated Xray configuration;
- batch latency/testing helpers;
- share-link conversion;
- GeoData helpers;
- key/subscription utility operations.

PVNetwork should not automatically adopt the exact API shape, but the architectural principle is valuable:

**small versioned native boundary + product-owned typed models above it.**

## Platform artifacts documented upstream

### Android

Current upstream builds an Android AAR/source artifact through gomobile and exposes Android-specific integration for socket protection/process lookup/DNS handling.

### Apple

Current upstream builds `LibXray.xcframework` for iOS, iOS Simulator, macOS, tvOS and tvOS Simulator through a C ABI boundary.

### Linux

Current upstream builds a shared-object artifact.

### Windows

Current upstream builds a DLL exposing the C ABI.

This cross-platform surface is highly relevant to PVNetwork because it can reduce custom wrapper duplication, but **platform VPN lifecycle is still product-owned**. A wrapper library does not replace Android VpnService, Apple NetworkExtension, Windows service/TUN lifecycle or Linux route/DNS integration.

## Lifetime / concurrency warning from upstream

Current upstream documentation explicitly notes process-wide Xray state and warns that temporary/test instances can interact with a running managed instance. It does not claim independent concurrent instance isolation.

PVNetwork consequence:

- define one engine-instance owner per process unless proven otherwise;
- serialize lifecycle operations or isolate independent engines in separate processes;
- never let UI test/ping helpers mutate or replace state underneath an active session;
- add concurrency/state ownership regression tests.

## Memory ownership

The C ABI documentation uses explicit allocation/free ownership for returned strings.

PVNetwork FFI layer must specify:

- allocator owner;
- exactly-once free rule;
- thread/lifetime rules;
- maximum request/response sizes;
- cancellation behavior;
- secret redaction before logging native envelopes.

## Android DNS/socket lesson

Upstream exposes Android-specific resolver/socket protection integration and notes process-wide DNS behavior.

PVNetwork requirement:

- socket protection and resolver state belong to the active VPN session owner;
- teardown must restore/reset product-owned process state;
- ping/test operations cannot be allowed to corrupt the connected-session resolver state.

## Share-link/import role

The wrapper contains share-link conversion utilities. This is useful but should not become PVNetwork's canonical profile storage.

PVNetwork should treat wrapper parsers as one adapter input source:

`external link -> parser result -> canonical PVProfile -> validation -> generated Xray runtime config`

Preserve unsupported/unknown input so editing does not silently destroy future fields.

## Why this candidate is attractive

Compared with a full client GUI fork, libXray offers:

- narrow integration scope;
- active source in 2026;
- MIT wrapper license;
- explicit Android/Apple/Linux/Windows build outputs;
- versioned API boundary;
- Xray-specific lifecycle/share/config helpers without forcing a product UI.

## Why it is not automatically approved

Before selecting it, PVNetwork still needs:

- full dependency/SBOM/license audit including Xray-core MPL obligations;
- API stability/versioning history review;
- current issues/releases/security review;
- Android/Apple Store architecture validation;
- concurrency/lifecycle stress tests;
- wrapper-vs-direct-process performance and crash-isolation comparison;
- exact product secret/config ownership model;
- confirmation that required Xray features are exposed without binding to unstable private internals.

## Provisional PVNetwork decision

Classification:

**`STRONG-WRAPPER-CANDIDATE / LEGAL+PLATFORM+LIFECYCLE REVIEW REQUIRED`**

Recommended comparison set:

1. libXray embedded/wrapper model;
2. managed Xray subprocess model on desktop/server-like platforms;
3. other maintained narrow wrappers only if they offer materially better lifecycle/API/store properties.

Do not choose a full GPL client application merely to obtain Xray integration when a narrow wrapper/core boundary can satisfy the engineering requirement.
