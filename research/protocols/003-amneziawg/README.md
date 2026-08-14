# 003 — AmneziaWG Research Dossier

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`** at the research level.

This does not mean PVNetwork currently supports or certifies AmneziaWG.

## Primary shared evidence

Use `research/upstreams/wireguard-family/`:

- `AMNEZIAWG_DELTA.md`
- `AMNEZIAWG_PLATFORMS.md`
- `SOURCE_REVISIONS.md`
- `DEPENDENCIES_SBOM.md`
- `LESSONS_AND_TESTS.md`
- `SUPPORT_REUSE_DECISIONS.md`
- WireGuard base architecture/client dossiers for inherited platform concepts.

## Current reviewed sources

### Portable Go engine

`amnezia-vpn/amneziawg-go@1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`

Root license: MIT.

### Android

`amnezia-vpn/amneziawg-android@d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`

Root `COPYING`: Apache-2.0.

### Apple

`amnezia-vpn/amneziawg-apple@e5410a539f28b8ce5dd1d060c45e4fa555e9a210`

Root `COPYING`: MIT.

### Windows client

`amnezia-vpn/amneziawg-windows-client@c8fa887db05ade03b9281b0e9de60579f744f995`

Root `COPYING`: MIT.

Current dependency pins include:

- `amneziawg-go/v3 v3.1.20260813`
- `amneziawg-windows/v3 v3.1.20260813`

### Windows tunnel/library

`amnezia-vpn/amneziawg-windows@1326e9bbdc71be88ddcc20925e092c6f5b9513da`

Current source contains AWG3.1 config/UAPI behavior. Exact file-level license confirmation remains an explicit gap; do not infer it solely from the separate Windows client.

## Research decision

**`HIGH-VALUE WIREGUARD-DERIVATIVE COMPATIBILITY TARGET / VERSIONED EXTENSION REQUIRED`**

AmneziaWG must be modeled as a WireGuard-derived engine/configuration family with additional packet-layout/timing/obfuscation parameters. PVNetwork must not reimplement its protocol/packet behavior from scratch.

## Versioned configuration model

PVNetwork canonical profile requires:

- standard WireGuard base fields;
- secure key references;
- explicit AWG generation/version;
- AWG1/AWG2 extension fields documented in `AMNEZIAWG_DELTA.md`;
- AWG3.1 additions such as `RandomTrailers` and `DisableCookies`;
- unknown future field preservation;
- core/version-aware validation;
- explicit downgrade/unsupported behavior.

Never silently remove AWG3.1 fields when opened by an older engine/client.

## Important current regression evidence

The current `amneziawg-go` head fixes a runtime panic in AWG3.1 random-trailer handling on `HandshakeCookie` messages.

This proves that parser/UI support for a new AWG generation is not enough for release approval. Tests must cover less-common handshake/cookie/rekey paths and exact client/server/core versions.

The current Apple head also fixes excluded-route behavior that could disturb a Linux peer connection, reinforcing the need for real peer route tests.

## Dependency/SBOM position

Reviewed AmneziaWG Go dependency surface is materially broader than reviewed WireGuard-Go and includes QUIC, Outline SDK, gVisor and Shadowsocks-related dependencies in addition to Go networking/system packages.

This increases SBOM/license/security review scope and is documented in `DEPENDENCIES_SBOM.md`.

## Core/client license separation

Do not use one “AmneziaWG license” label. Reviewed components have different root licenses and independent dependency graphs.

The separate Amnezia VPN application is also a different codebase/license from the AWG core/platform components.

## Current architecture direction

PVNetwork may expose WireGuard-family profiles at a high level, but must deterministically select standard WireGuard vs AmneziaWG based on canonical capability/version metadata.

Do not run AWG profiles through standard WireGuard or standard WireGuard profiles through AWG-specific behavior without explicit compatible semantics.

## Residual gaps — explicit

- exact current Android embedded-core/submodule version graph;
- exact current Apple Go/core dependency graph;
- exact reusable path/license confirmation for `amneziawg-windows`;
- broader current AWG platform issue/release matrix;
- final production pins/SBOM/security scan;
- real-device/server cross-version interoperability/performance tests;
- server implementation/installers/menus plus cryptography/wire/data-flow belong to mandatory later `COMPLETE-REFERENCE-v2`.

Entry 003 is now research-handoff-ready but unimplemented.
