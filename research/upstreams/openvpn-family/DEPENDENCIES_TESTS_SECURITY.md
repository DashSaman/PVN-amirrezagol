# OpenVPN Family — Dependency / Tests / Security Surface

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Primary core pin:

`OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`

Core license at pin: `AGPL-3.0-only OR MPL-2.0` per upstream `LICENSE.md`.

## Source/build dependency architecture

Pinned OpenVPN3 root contains:

- `CMakeLists.txt`
- `cmake/`
- `deps/`
- `vcpkg.json`
- `conan/`
- `openvpn/`
- `client/`
- `test/`
- `doc/`
- platform build files such as `build-ios-macos.sh`, `msvc/` and Windows/Apple-related helpers.

This is a library/core source tree, not a finished PVNetwork application.

## Dependency manifests

The pinned `deps/` tree includes support for dependency acquisition/packaging through mechanisms such as:

- vcpkg manifests;
- CMake dependency scripts;
- JSON/CMake helper material;
- LZ4 patch/helper material;
- platform-specific manifest structure.

Reviewed Windows vcpkg manifest currently declares dependencies including:

- `asio`
- `lz4`
- `mbedtls`
- `openssl`
- `tap-windows6`
- `xxhash`
- `jsoncpp`

and a `pkcs11-helper` feature.

### PVNetwork consequence

OpenVPN3's dual-license line is not the complete commercial distribution answer. Final per-platform SBOM must include:

- TLS/crypto backend actually compiled/used;
- compression libraries;
- TAP/TUN/driver components;
- PKCS#11/token support when enabled;
- JSON/helper dependencies;
- platform network-extension/service libraries;
- build-time/runtime native libraries;
- bundled certificates/data/assets if any.

Do not infer Windows dependency set applies unchanged to Android/Apple/Linux.

## TLS/crypto backend selection

The source/build system can support multiple TLS/crypto paths such as OpenSSL and mbedTLS depending on build configuration/platform.

PVNetwork must record the exact backend in diagnostics/SBOM/security evidence because:

- certificate behavior can differ;
- available ciphers/features can differ;
- vulnerability/advisory state can differ;
- binary size/performance can differ.

Do not advertise generic “OpenVPN3” security without naming the actual shipped backend/build.

## Driver/TUN boundary

The reviewed Windows manifest references `tap-windows6`, while modern OpenVPN deployments can also involve DCO or other platform-specific data-channel integration depending on product/version.

PVNetwork must separate:

- OpenVPN protocol/core;
- virtual adapter/driver;
- DCO/kernel acceleration where available;
- platform route/DNS lifecycle.

A core build passing tests does not certify a particular driver path.

## Test tree

Pinned source has a substantial `test/` directory and dedicated client/library test infrastructure.

Current root also contains test-support configuration and CI/build workflow material.

PVNetwork must distinguish:

### Upstream tests

Useful for validating OpenVPN3 internals, parsers, utilities and supported core behavior.

### PVNetwork tests

Still required for:

- `.ovpn` import/round trip;
- canonical profile normalization;
- unsupported-directive handling;
- secure credential/certificate/key references;
- adapter API/lifetime;
- Android VpnService;
- Apple NetworkExtension;
- Windows/Linux service/TUN/route/DNS;
- network handover/reconnect;
- kill switch/leak behavior;
- Store/package lifecycle;
- Persian RTL/UI/error mapping.

Upstream tests cannot prove PVNetwork product integration.

## CI/build evidence

Pinned repository contains multiple GitHub Actions workflows for build/test/release-related tasks and platform configurations.

PVNetwork should not copy upstream CI blindly; instead use it to identify supported build paths/toolchains and reproduce critical core builds under PVNetwork-controlled CI.

Release evidence should record:

- core commit/tag;
- compiler/toolchain;
- dependency lock/manifests;
- TLS backend;
- target OS/architecture;
- binary/library hash;
- test results;
- license/notice bundle;
- signing/notarization where applicable.

## GitHub security-advisory endpoint

At research time, the repository's GitHub Security Advisories API returned an empty list.

This must **not** be interpreted as “OpenVPN3 has no security history or dependencies have no vulnerabilities.”

PVNetwork security review must also include:

- official OpenVPN security advisories/release notes;
- dependency advisories/CVEs;
- OpenSSL/mbedTLS/backend advisories;
- TAP/DCO/platform driver advisories;
- commits/issues fixing security-sensitive behavior;
- exact selected build/version.

## Profile/parser security

OpenVPN profiles can include many directives and embedded certificate/key material. Importing arbitrary `.ovpn` content is therefore security-sensitive input handling.

PVNetwork requirements:

- validate directive support before use;
- preserve/report unknown directives rather than silently dropping them;
- do not execute arbitrary external script/plugin directives in consumer Store builds without explicit policy;
- prevent path traversal/file-reference abuse in imports;
- copy/secure imported credential/key material deliberately;
- redact inline private keys/passwords from logs/support bundles;
- distinguish profile parsing success from policy/Store safety.

## Plugin/script capability caution

OpenVPN ecosystem configurations can contain options that assume external scripts/plugins or privileged platform operations.

PVNetwork must maintain a **safe capability policy** per platform/build. A syntactically valid `.ovpn` file may be unsupported or unsafe in a sandboxed/mobile/Store context.

Do not treat arbitrary OpenVPN directive compatibility as a requirement to execute arbitrary local code.

## DCO / accelerated data path

Official OpenVPN Connect/product documentation exposes DCO-related behavior on supported platforms. PVNetwork should treat DCO as a separate capability dimension with fallback behavior rather than one global OpenVPN feature flag.

Tests must compare:

- DCO enabled/available;
- DCO unavailable/fallback;
- routing/DNS/MTU differences;
- reconnect;
- unsupported profile directives preventing DCO use;
- error mapping.

## Upgrade gate

Before changing OpenVPN3 in PVNetwork:

1. pin candidate source/tag;
2. review OpenVPN security/release notes;
3. resolve exact dependency graph and vulnerability state;
4. compare TLS/crypto backend;
5. compare profile/config/directive behavior;
6. run parser/import/canonical round-trip tests;
7. run certificate/auth/TLS tests;
8. run platform TUN/DNS/route/reconnect/leak tests;
9. run DCO/non-DCO paths where supported;
10. verify Store/package/signing/update behavior;
11. retain rollback compatibility with existing canonical profiles.

## Reuse decision

OpenVPN3 remains the preferred current reusable OpenVPN core candidate for PVNetwork research.

Reference GUI/app projects remain separate license/architecture sources:

- OpenVPN Connect — official UX behavior reference;
- OpenVPN GUI — Windows source reference;
- ics-openvpn — Android source/security/storage reference;
- Tunnelblick — macOS source/lifecycle reference;
- Pritunl — reference-only due current commercial-use restrictions.

## Remaining v1 gaps

- exact current OpenVPN3 stable release candidate and main/tag comparison;
- complete authoritative dependency-license/advisory table for each target platform;
- exact OpenVPN official security-release matrix;
- exact CI workflow/test coverage table;
- exact DCO implementation/package matrix;
- final Store/platform build strategy.

These are explicit residual gaps; final exact-build security/SBOM review repeats at implementation/release time.
