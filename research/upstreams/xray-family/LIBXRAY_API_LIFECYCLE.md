# libXray — API / Lifecycle / Platform Ownership Deep Audit

Research date: 2026-08-14

State: `IN-RESEARCH`; wrapper architecture evidence only.

Pinned wrapper:

`XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`

Wrapper root license: MIT. Underlying Xray-core and dependencies retain their own licenses/obligations.

## Versioned invocation contract

Pinned `invoke_model.go` defines:

`LibXrayAPIVersion = 2`

The current public invocation model uses a JSON envelope containing:

- `apiVersion`;
- `method`;
- typed JSON `payload`.

The dispatcher rejects mismatched API versions and unknown methods.

### Current method categories

Pinned API exposes operations in these product-relevant groups:

#### Utility / setup

- obtain free ports;
- GeoData counting/helper operation;
- Xray version/state query.

#### Import/export

- share links -> Xray JSON;
- Xray JSON -> share links;
- age key generation for the wrapper's encrypted subscription/share feature.

#### Validation/measurement

- test Xray configuration;
- batch latency/ping helper.

#### Lifecycle

- run Xray;
- stop Xray;
- get running state.

PVNetwork should not expose the wrapper method names directly as UI/domain APIs. Create typed product services and adapt them to the wrapper version.

## Request/response size boundary

Pinned `invoke.go` limits both complete invocation request and response envelopes to **16 MiB**.

PVNetwork consequences:

- validate product payload sizes before crossing native boundary;
- do not send giant support logs/config databases through this API;
- define explicit handling for subscription/profile sets exceeding wrapper limits;
- never log the full invocation envelope when it can contain secrets or decrypted subscription content.

## Single managed Xray instance

Pinned `xray/xray.go` keeps one package-level `*core.Instance` protected by a mutex.

`RunXray`:

- refuses a second managed instance while one is present;
- loads JSON through Xray core config loading;
- creates/starts the instance;
- closes it when startup fails;
- stores it as the package-level active instance.

`StopXray` closes the active instance and clears the package-level pointer.

### PVNetwork rule

One libXray process should have one explicit product session owner.

Do not allow:

- two UI flows racing to start independent sessions;
- background measurement helpers mutating an active session without serialization/isolation;
- service restart spawning a second unmanaged Xray instance;
- stale product state after wrapper `RunXray` returns `already running`.

## Temporary test/ping instances and process-wide state

Current upstream engineering documentation warns that Xray-core contains process-wide state. Temporary `testXray` / `pingBatch` operations may create temporary Xray instances and can affect process-wide state used by an active managed instance. Closing the temporary instance does not necessarily restore all prior state.

### PVNetwork decision direction

For independent testing while connected, prefer one of:

1. serialize test/measurement operations around the active process state if upstream guarantees safe semantics;
2. run measurements in a separate process;
3. use a non-Xray product-level probe that does not create competing core instances.

Do not run arbitrary temporary in-process Xray instances concurrently with a production session until proven safe by tests.

## C ABI ownership

Pinned `cgo_bridge/main.go` exposes two C functions:

- one call that accepts a C string request and returns a newly allocated C string response;
- one explicit free function.

The returned memory is allocated through the C allocator and must be freed through the wrapper's documented free boundary exactly once.

PVNetwork native binding rules:

- wrap response ownership in an RAII/scope-safe abstraction where the platform language supports it;
- never use a different allocator to free wrapper memory;
- define null/error handling;
- ensure exceptions/cancellation do not skip release;
- test repeated high-volume calls for leaks.

## Android-specific platform ownership

Pinned `android_wrapper.go` exposes Android-only concepts:

- a dialer/listener socket-protection callback;
- process lookup integration for per-app routing;
- VPN-aware process DNS resolver setup/reset.

The file documents that process lookup behavior differs by Android API level, with an application callback used on newer Android releases and legacy behavior on older versions.

### PVNetwork consequence

libXray does **not** replace Android `VpnService`.

PVNetwork Android must own:

- VPN permission and `VpnService` lifecycle;
- TUN descriptor creation/teardown;
- socket protection callbacks;
- process/app identity integration where allowed;
- foreground/background execution policy;
- resolver setup/reset;
- crash/process-death cleanup;
- OS/API-version compatibility.

Any global process DNS modification must be reverted as part of session teardown.

## Build/artifact matrix from current CI

Pinned `.github/workflows/build.yml` currently builds:

### Linux

- x64 shared library/header;
- arm64 shared library/header.

### Android

- Android AAR;
- sources JAR;
- Android NDK r29/toolchain path in current CI.

### Windows

- x64 DLL/header;
- arm64 DLL/header;
- workflow includes architecture verification for generated PE binaries.

### Apple

- XCFramework built on current macOS runner;
- upstream documentation identifies iOS, iOS Simulator, macOS, tvOS and tvOS Simulator slices.

### PVNetwork conclusion

This is strong cross-platform wrapper-build evidence, but it is **not** app-store/product lifecycle evidence. Each PVNetwork platform still needs its own signing, sandbox/entitlement, network extension/service and packaging review.

## Release pipeline

Pinned build workflow runs on tags/manual dispatch, builds a matrix of artifacts, packages per platform and creates a prerelease GitHub release for tag-triggered builds.

PVNetwork supply-chain requirements if using libXray:

- pin exact tag/commit, not “latest”;
- reproduce or independently build artifacts rather than blindly downloading moving binaries;
- record toolchain versions and hashes;
- generate SBOM/license notices;
- sign/notarize inside PVNetwork's own release chain where required;
- monitor wrapper and underlying Xray-core version separately.

## Share-link / subscription boundary

Current wrapper includes share-link conversion and age-encrypted subscription support.

Product ownership must remain explicit:

- libXray may parse/decrypt/convert within its API;
- PVNetwork owns network fetching, HTTP headers, account/subscription state, protected persistence of generated keys, refresh scheduling and user-visible error handling;
- never log secret keys or decrypted subscription payloads;
- conversion result becomes importer input, not authoritative product storage.

## Error contract

Pinned invocation code returns a common structured response envelope containing success/data/error.

PVNetwork should map wrapper errors into product categories such as:

- invalid canonical/generated config;
- unsupported wrapper API version;
- lifecycle conflict/already-running;
- import/decryption failure;
- measurement failure;
- native bridge/serialization failure.

Do not show raw internal errors as the only user message, but retain sanitized technical detail for diagnostics.

## Upgrade compatibility

Because the wrapper API is explicitly versioned and underlying Xray-core changes separately, PVNetwork must track both:

- libXray API/version/commit;
- embedded Xray-core version/commit.

Upgrade tests must cover:

- request/response compatibility;
- lifecycle ownership;
- share-link conversion semantics;
- generated config acceptance;
- Android socket/DNS callbacks;
- Apple C ABI memory/lifecycle;
- Windows/Linux DLL/SO loading;
- concurrent test/ping behavior;
- canonical-profile round trip.

## Provisional integration decision

libXray remains a strong candidate for platforms where an embedded/native library boundary is preferable.

However, PVNetwork should still compare:

- crash isolation vs subprocess model;
- update/rollback/replacement requirements;
- Store policies;
- process-wide state/concurrency limitations;
- memory footprint;
- dependency/SBOM complexity;
- ability to run diagnostics without disturbing active session.

No one integration architecture is approved for all platforms yet.

## Remaining gaps

- exact `go.mod` dependency/license table for libXray plus embedded Xray-core version pin;
- current issue/release regression review for libXray;
- exact Apple slice/deployment-target metadata from built artifacts/source scripts;
- Android minSdk/ABI behavior and app integration examples;
- thread-safety guarantees for `Invoke` beyond the explicitly mutex-protected managed instance;
- performance/leak testing for C ABI and gomobile paths;
- wrapper security/advisory process;
- product-level cancellation/timeout semantics for long-running calls.
