# Hysteria v1 vs Hysteria2 generation, architecture and configuration evidence

Official upstream: `apernet/hysteria`.

Research pins:

- legacy Hysteria v1: tag `v1.3.5`, commit `57c5164854d6cfe00bead730cce731da2babe406`;
- current Hysteria2 architecture pin already recorded by PVNetwork: `14e9fff1d972ab0187ac7fcf75b9514dc8664065`.

This note exists to prevent PVNetwork entries **042 Hysteria** and **043 Hysteria2** from being collapsed into one protocol merely because they share repository history.

## 1. Entry 042 is a legacy generation with its own source/config contract

The official repository still exposes legacy tags through `v1.3.5`. At that tag the source tree has a materially different application/config layout from current Hysteria2.

Legacy `app/cmd/config.go` defines JSON/JSON5-style client configuration with fields such as:

- `server`, `protocol`;
- required upload/download rate (`up`/`down`, or Mbps forms);
- retry/handshake/idle/hop timing;
- SOCKS5 and HTTP local proxy blocks;
- TUN, TCP/UDP relay, TProxy and redirect blocks;
- ACL/MMDB;
- a single `obfs` string;
- `auth` / `auth_str`;
- `alpn`, `server_name`, `insecure`, custom CA;
- receive windows, MTU-discovery toggle, Fast Open, lazy start and resolver controls.

The v1 validation path requires at least one local operating mode and validates configured bandwidth; its defaults include ALPN `hysteria`, receive-window values and a default client hop interval.

Official source:
https://github.com/apernet/hysteria/blob/57c5164854d6cfe00bead730cce731da2babe406/app/cmd/config.go

### PVNetwork conclusion for entry 042

**Legacy-compatibility target, not an alias of Hysteria2.**

If PVNetwork supports entry 042, its canonical model must retain an explicit protocol generation/version and translate to the legacy v1 schema/runtime. Do not feed a legacy profile to the v2 parser and do not silently migrate semantics such as bandwidth, authentication or obfuscation fields without a version-aware migration step.

## 2. Entry 043 uses the Hysteria2/v2 module family and a different wire specification

At the current architecture pin:

- app module is `github.com/apernet/hysteria/app/v2`;
- app depends on local `core/v2` and `extras/v2` modules;
- official `PROTOCOL.md` explicitly states that it describes the protocol starting with version `2.0.0`, internally sometimes called the `v4` protocol.

The Hysteria2 wire protocol requires:

- standard QUIC RFC 9000;
- QUIC unreliable DATAGRAM RFC 9221;
- HTTP/3 masquerading/authentication;
- client auth request via HTTP/3 POST `/auth` and success status 233;
- TCP proxy requests on QUIC bidirectional streams;
- UDP messages over QUIC DATAGRAM with packet-scoped destination and session/fragment metadata;
- congestion-control rate negotiation;
- optional packet obfuscation such as Salamander.

Official spec:
https://github.com/apernet/hysteria/blob/14e9fff1d972ab0187ac7fcf75b9514dc8664065/PROTOCOL.md

### PVNetwork conclusion for entry 043

**Current primary upstream reuse candidate.** Hysteria2 should be modeled independently from legacy Hysteria v1 and can use the official core/app boundary as the primary reference.

## 3. Current Hysteria2 client configuration surface

Current `app/cmd/client.go` defines a broad typed configuration with:

- server and authentication;
- Realm/STUN/NAT-punch settings;
- transport configuration and UDP port hopping;
- obfuscation (`salamander` and current `gecko` support at the reviewed pin);
- TLS SNI, CA, pin SHA-256, client certificate/key and ECH;
- QUIC receive windows, idle/keepalive, PMTU-discovery controls and platform socket options;
- congestion and bandwidth controls;
- SOCKS5, HTTP, TCP/UDP forwarding, TProxy, redirect and TUN modes;
- TUN IPv4/IPv6 route include/exclude controls.

The implementation validates platform-specific socket options and can return an explicit unsupported-on-platform configuration error. This matters for PVNetwork UI capability gating: a field appearing in the shared schema does not imply every platform can apply it.

Official source:
https://github.com/apernet/hysteria/blob/14e9fff1d972ab0187ac7fcf75b9514dc8664065/app/cmd/client.go

## 4. Hysteria2 dependency and transport ownership

The current `app/go.mod` requires Go 1.25 and directly references local `core/v2` and `extras/v2`. The reviewed dependency surface includes:

- `github.com/apernet/quic-go` fork;
- `apernet/sing-tun`;
- certmagic and multiple libdns providers;
- uTLS;
- DTLS/STUN/NAT dependencies;
- Cobra/Viper configuration/CLI;
- crypto/net/system packages.

Therefore the exact QUIC fork and app/core/extras revisions belong in the product SBOM. “Hysteria2 version” alone is not a sufficient reproducibility identifier.

Official source:
https://github.com/apernet/hysteria/blob/14e9fff1d972ab0187ac7fcf75b9514dc8664065/app/go.mod

## 5. Adapter boundary

Recommended product boundary:

`PVNetwork versioned canonical profile -> Hysteria generation adapter -> official executable/core -> platform TUN/proxy lifecycle`

Rules:

1. `protocol_generation = v1 | v2` is explicit and immutable without migration.
2. Entry 042 and entry 043 have different validators and generated config schemas.
3. Hysteria2-only options such as current TLS/ECH/gecko/socket-option fields must never be written into a legacy v1 profile.
4. Legacy fields must not be discarded on import just because the current Hysteria2 client has a newer shape.
5. Shared product UI may present both entries, but capability labels must identify the generation.

## 6. Current research classifications

| Entry | Classification | Reuse direction |
|---|---|---|
| 042 Hysteria | `LEGACY-GENERATION / COMPATIBILITY TARGET` | version-pinned legacy executable/core adapter only if product compatibility requires it |
| 043 Hysteria2 | `CURRENT PRIMARY HYSTERIA TARGET` | strong official-core/app adapter candidate, subject to security/SBOM/platform gates |

These are research decisions, not implementation claims.

## 7. Remaining original-v1 gaps

- current release/security review and safe-version rule;
- legacy v1 security/maintenance status and whether it should be enabled by default or only imported for compatibility;
- exact build/asset/platform matrix and package digests;
- client-reference/GUI import behavior;
- issue/regression sampling;
- final entry synchronization and template audit.
