# SoftEther VPN — Dependency / Build / Test / Security Surface

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Reviewed source: `SoftEtherVPN/SoftEtherVPN` on research revision `b1f7ef0...` previously pinned in this repository.

Root license reviewed: **Apache-2.0**.

## Build/source surface

Current repository contains:

- top-level CMake build system;
- shared `src/Cedar/` networking/protocol code;
- shared `src/Mayaqua/` runtime/platform code;
- product executables for server/client/bridge/management;
- platform-specific build/package resources;
- test/build automation under repository CI/workflow paths.

This is a large native C/C++-style systems codebase, not a small single-protocol library.

## Dependency-review rule

Apache-2.0 at repository root is favorable for evaluation, but final product distribution still needs an exact dependency/SBOM for the **selected build configuration**.

SoftEther features touch categories such as:

- TLS/cryptographic libraries;
- compression/encoding/runtime support;
- OS socket/network APIs;
- virtual network/TAP/TUN/adapter drivers;
- certificate/key parsing;
- platform UI/system libraries;
- optional protocol compatibility modules;
- installer/service helpers.

Do not infer identical dependency sets for Windows, Linux, macOS or any future mobile port.

## Feature-to-dependency minimization

PVNetwork should not compile/ship every SoftEther server feature merely because the source tree contains it.

If only native SoftEther client connectivity is needed, evaluate a minimal client-focused build/component boundary and avoid unnecessary server/admin/protocol modules where technically feasible.

Benefits:

- smaller attack surface;
- simpler SBOM;
- smaller binaries;
- fewer licenses/notices;
- less platform privilege;
- easier Store review.

Do not remove modules until dependencies/build boundaries are source-verified.

## Cryptographic/security ownership

SoftEther native/client/server source relies on mature cryptographic/TLS primitives; PVNetwork must not reimplement them.

Before selecting a core build record:

- exact TLS library/backend/version;
- enabled protocol/cipher policy;
- certificate-validation behavior;
- legacy compatibility modes;
- private-key handling;
- random-number source;
- dependency advisories;
- compiler hardening/options.

Entry 013 native protocol, entry 014 raw EtherIP and entry 015 EtherIP/IPsec have different security properties. Never present them under one generic “encrypted SoftEther” badge.

## EtherIP security rule

Raw EtherIP provides Layer-2 encapsulation and **must not be marketed as confidential/encrypted by itself**.

Where protected with IPsec, the IPsec layer provides the cryptographic/authentication protection and needs its own exact IKE/IPsec security review.

## Server compatibility attack surface

A SoftEther server may expose multiple protocol listeners/compatibility services. Every enabled listener increases attack/configuration surface.

PVNetwork later server-management research should require:

- explicit enable/disable state per protocol;
- bound address/port inventory;
- certificate/auth policy;
- firewall exposure;
- log/audit behavior;
- rate/connection limits where supported;
- patch/update status.

Do not recommend enabling every compatibility protocol by default.

## Configuration / secret handling

This v1 pass has not yet certified SoftEther's native configuration files as secure client/server secret storage.

Final review must identify how the exact pinned build stores:

- administrator password material;
- client credentials;
- user hashes/passwords;
- PSKs;
- certificates/private keys;
- exported server/client configuration.

PVNetwork client credentials should still use OS secure storage/keychain/vault semantics independent from server file-format choices.

## Tests / CI distinction

The source tree/build automation provides upstream tests/build checks useful for core/project validation, but PVNetwork must create its own integration tests for the exact modules it uses.

### PVNetwork native SoftEther client tests

- profile import/create/edit round trip;
- authentication success/failure/challenge;
- certificate validation;
- adapter/service start/stop/restart;
- repeated reconnect;
- network change/sleep/resume;
- IPv4/IPv6 where supported;
- DNS/routing/leak behavior;
- error/log redaction;
- process/service crash recovery;
- upgrade with stored profiles.

### EtherIP / EtherIP-IPsec tests

- exact protocol classification;
- raw EtherIP clearly rejected/flagged when encryption is required;
- site-to-site/bridge interoperability;
- IPsec protection enabled/validated for 015;
- route/bridge cleanup after failure;
- malformed/unsupported configuration handling.

## Security advisory/release gate

A repository issue/release list is not enough to certify an old native networking codebase.

Before implementation/release:

1. select exact SoftEther release/tag/commit;
2. review official release notes/security notices;
3. scan exact dependencies;
4. compare current release vs source head;
5. run sanitizers/static analysis/fuzzing where practical on parser/network boundaries;
6. test protocol modules actually enabled by PVNetwork;
7. review certificate/authentication defaults;
8. review privileged service/admin-manager surfaces;
9. verify package signing/update/rollback;
10. archive exact SBOM and hashes.

## Parser/input-risk classes

SoftEther processes network protocol input, management input and configuration files. Product integration must consider:

- malformed protocol frames;
- malformed management requests;
- configuration parser boundaries;
- certificate/key files;
- imported connection settings;
- externally supplied host/hub/user names;
- log output containing secrets or remote-controlled text.

Normalize/redact before displaying untrusted strings in PVNetwork logs/UI.

## Privilege boundary

Server/bridge/client virtual-adapter operations may require elevated privileges depending on OS.

PVNetwork should not run the entire GUI as root/admin. Use:

- narrow privileged service/helper;
- authenticated local IPC;
- least privilege;
- explicit install/uninstall lifecycle;
- service binary signing;
- permission tests.

## Platform/Store caution

Desktop source portability does not prove Android/iOS/App Store feasibility for the native SoftEther protocol.

Before mobile implementation, determine whether:

- a reusable library can operate inside Android VpnService/iOS NetworkExtension constraints;
- required TAP/Layer-2 semantics are supported/approximable;
- background/session behavior is Store-compliant;
- native code/toolchain is acceptable;
- platform-specific functionality differs from desktop.

Do not promise mobile native SoftEther support until proven.

## Current v1 conclusion

SoftEther source is a technically significant and license-attractive reference/reuse candidate, but the exact minimal client library boundary and per-platform dependency/security package are not yet selected.

This does not block original-v1 handoff if the remaining uncertainty is preserved; final exact-build analysis necessarily repeats during implementation.

## Residual gaps

- exact full current source/tag pin;
- exact dependency/license/advisory manifest for selected build;
- full CI/test coverage table;
- current security/release issue matrix;
- exact client/server secret storage format;
- exact native mobile feasibility;
- performance/resource evidence;
- server installer/panel security belongs to mandatory v2.
