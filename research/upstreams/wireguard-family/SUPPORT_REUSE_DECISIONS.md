# WireGuard / AmneziaWG — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

State: **research architecture decision only**. Nothing here means PVNetwork currently implements or certifies entries 002 or 003.

## 002 — WireGuard

Research classification:

**`HIGH-PRIORITY CORE VPN TARGET / OFFICIAL-STACK-FIRST`**

### Why

- mature, compact protocol/core design;
- official source exists for portable Go, Windows, Android and Apple;
- strong platform-native reference implementations;
- standard `.conf` ecosystem;
- broad user/server deployment base;
- attractive license position on reviewed official components, subject to exact path/dependency review.

### Preferred PVNetwork integration direction

Use the most appropriate **official/native WireGuard implementation per platform**, hidden behind one product-owned WireGuard Adapter.

Do not force one userspace Go implementation onto every operating system if an official platform stack offers better lifecycle/driver/Store integration.

Provisional direction:

- Windows: official Windows service/tunnel/driver components or embeddable boundary after package/license review;
- Android: official tunnel/backend architecture or a narrowly reusable backend, with PVNetwork-owned `VpnService`/UI/storage;
- Apple: WireGuardKit/NetworkExtension-compatible architecture, with PVNetwork-owned app/UI/profile/secure storage;
- Linux: native kernel WireGuard where available, with userspace fallback only where justified;
- other targets: select official/maintained userspace implementation based on platform evidence.

### Product-owned responsibilities

Even when an official WireGuard engine is reused, PVNetwork owns:

- canonical profile model;
- import/export and QR parsing;
- protected key/PSK persistence;
- connection/session state;
- DNS/routing/full-tunnel/split-tunnel UX;
- kill switch and leak testing;
- per-app routing where supported;
- platform service/extension lifecycle;
- diagnostics/redaction;
- updates and Store packaging.

### Storage decision

Standard `.conf` is an import/export representation, **not the required internal persistence format**.

Windows official source provides a strong pattern: persisted tunnel configs use DPAPI-protected `.conf.dpapi` rather than leaving app-managed private keys in plaintext `.conf` files.

PVNetwork must use platform secure storage or an explicitly protected vault for private keys/PSKs.

### Reuse classification by reviewed component

- `wireguard-go` — `REUSE-CANDIDATE`
- official Windows source/components — `STRONG REUSE/ARCHITECTURE CANDIDATE`
- official Android tunnel/backend — `REUSE-CANDIDATE/REFERENCE`
- official Apple WireGuardKit/NetworkExtension source — `REUSE-CANDIDATE/REFERENCE`

All remain subject to exact dependency/platform/release/Store review.

### Support claim today

**None.** Research does not equal implementation.

---

## 003 — AmneziaWG

Research classification:

**`HIGH-VALUE WIREGUARD-DERIVATIVE COMPATIBILITY TARGET / VERSIONED EXTENSION REQUIRED`**

### Why

- real active cross-platform open-source implementation family;
- current AWG3.1-era development in 2026;
- separate Android, Apple and Windows client/library source;
- useful compatibility/obfuscation variant for operators already deploying AWG;
- reviewed portable Go core license is MIT.

### Critical modeling rule

AmneziaWG is **not a new cryptographic primitive suite to be reimplemented by PVNetwork**.

Treat it as a WireGuard-derived engine/configuration family with additional packet-layout/timing/obfuscation parameters and versioned behavior.

PVNetwork should reuse maintained AWG implementations rather than reproducing packet/handshake behavior from scratch.

### Versioned extension requirement

Canonical product model needs a dedicated AWG extension with:

- AWG generation/version metadata;
- AWG1/AWG2 fields already documented in `AMNEZIAWG_DELTA.md`;
- AWG3.1 additions including `RandomTrailers` and `DisableCookies`;
- unknown future field preservation;
- adapter/core capability validation;
- explicit downgrade/unsupported behavior.

Never silently strip newer AWG fields when opened by an older client/core.

### Current component relationships

Reviewed current source includes:

- `amneziawg-go` current master pin `1b86b2...`;
- Android pin `d6cd664...`;
- Apple pin `e5410a5...`;
- Windows full-client pin `c8fa887...`;
- Windows tunnel/library pin `1326e9b...`;
- Windows client dependencies `amneziawg-go/v3 v3.1.20260813` + `amneziawg-windows/v3 v3.1.20260813`.

This proves AWG product/core/platform versions must be tracked separately.

### Current regression lesson

The current portable Go head fixes an AWG3.1 `RandomTrailers` buffer-allocation bug that could panic on `HandshakeCookie` messages.

Therefore a brand-new AWG generation is not approved merely because the UI exposes its new fields. Cross-platform regression tests must cover uncommon handshake/cookie/rekey paths.

### Reuse classification

- `amneziawg-go` — `REUSE-CANDIDATE`
- AmneziaWG Android — `REUSE-CANDIDATE/REFERENCE`, Apache-2.0 root COPYING reviewed
- AmneziaWG Apple — `REUSE-CANDIDATE/REFERENCE`, MIT root COPYING reviewed
- AmneziaWG Windows client — `REUSE-CANDIDATE/REFERENCE`, MIT root COPYING reviewed
- AmneziaWG Windows tunnel/library — `REUSE-CANDIDATE-PENDING-FILE-LEVEL-LICENSE-CONFIRMATION`; do not infer exact license from separate client repository.

### Dependency caution

Reviewed `amneziawg-go` has a materially larger dependency surface than reviewed `wireguard-go`, including QUIC, Outline SDK, gVisor and Shadowsocks-related dependencies.

This increases SBOM/license/security scope and is documented in `DEPENDENCIES_SBOM.md`.

### Support claim today

**None.** AWG support must later be implemented and tested by exact generation/platform/server version.

---

# Shared product architecture recommendation

PVNetwork should expose a common high-level product family while preserving engine distinction:

`WireGuard-family profile`

- base WireGuard fields;
- secure key references;
- peer/AllowedIPs/DNS/keepalive;
- optional versioned AmneziaWG extension;
- platform/product routing policy outside the engine config where possible.

Do not flatten AWG-only options into generic WireGuard fields.

## Adapter direction

One product-facing family may use two capabilities/engines internally:

- standard WireGuard capability;
- AmneziaWG capability/version.

The UI can remain simple for normal users while Advanced mode displays engine family/version and AWG-specific fields when relevant.

## Engine selection policy

Do not auto-run an AWG profile using standard WireGuard or vice versa.

Selection must be deterministic from canonical profile capability metadata.

## Import policy

When importing:

- detect standard WireGuard vs AWG-specific fields;
- preserve unknown fields;
- identify unsupported/newer AWG generation;
- never downgrade silently;
- distinguish standard `.conf` compatibility from AWG-specific configuration syntax;
- store secrets securely after import.

## Minimum future certification matrix

For each platform record:

- product build;
- platform OS/version/architecture;
- engine/client/wrapper version;
- WireGuard vs AWG generation;
- server/peer implementation/version;
- import/create/connect/disconnect;
- IPv4/IPv6;
- DNS/routing;
- per-app/split tunnel where applicable;
- network handover;
- sleep/resume;
- process/service/extension restart;
- secure storage;
- upgrade/downgrade;
- Store/package path.

## Family v1 closure position

With current source pins, Windows source/storage/service evidence, Android/Apple evidence, AmneziaWG platform/AWG3.1 evidence, dependency/SBOM mapping and issue-derived tests, the family is close to a reasonable `V1-HANDOFF-READY` state.

Remaining closure actions:

1. synchronize entries 002 and 003;
2. update shared family README/index;
3. preserve remaining exact-build/Android/Apple/license gaps;
4. create dated status + AGENTS handoff;
5. move to the next unfinished original-v1 family.

Later server installers, exhaustive menus, cryptography, wire flow and deployment topologies belong to mandatory `COMPLETE-REFERENCE-v2`.
