# Modern Proxy / Tunnel Family — PVNetwork Support / Reuse Decisions

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork implementation/certification exists.

## 044 — TUIC

Decision:

**`MODERN QUIC PROXY TARGET / DIRECT-UPSTREAM VS EXISTING-CORE BENCHMARK REQUIRED`**

Primary reference: `EAimTY/tuic`.

Do not add a standalone TUIC engine if an already-approved core provides current protocol parity, fast security/release updates and acceptable performance. If direct upstream is materially better, isolate it behind a TUIC adapter.

## 045 — AnyTLS

Decision:

**`MODERN TLS-BASED PROXY TARGET / PREFER EXISTING APPROVED CORE WHEN PARITY EXISTS`**

Primary reference: `anytls/anytls-go` plus implementations in modern multi-protocol cores.

Keep AnyTLS protocol/auth/session data distinct from generic TLS security settings.

## 046 — ShadowTLS

Decision:

**`PROXY/SECURITY-WRAPPER TARGET / COMPOSITION MODEL REQUIRED`**

Primary reference: `ihciah/shadow-tls` plus core implementations.

Model outer ShadowTLS behavior separately from the inner proxy. Do not flatten the chain into an opaque URL in canonical storage.

## 047 — NaiveProxy

Decision:

**`WEB-PROXY COMPATIBILITY TARGET / CHROMIUM-STACK COST REVIEW REQUIRED`**

Primary reference: `klzgrad/naiveproxy`.

The Chromium networking stack can provide excellent web-protocol compatibility but creates a large build/update/SBOM footprint. Add direct NaiveProxy only if compatibility value justifies that cost or use an approved equivalent engine where semantics match.

## 048 — Snell

Decision:

**`PROPRIETARY/EXTERNALLY-SPECIFIED COMPATIBILITY TARGET / REUSE ONLY THROUGH LEGALLY COMPATIBLE IMPLEMENTATION`**

Do not assume original Snell server/client source is open or redistributable. Pin protocol version and a legitimate implementation before support.

## 049 — SOCKS4

Decision:

**`FOUNDATIONAL PROXY CAPABILITY / NO DEDICATED ENGINE`**

Use an approved networking core/library.

## 050 — SOCKS4a

Decision:

**`FOUNDATIONAL SOCKS VERSION / NO DEDICATED ENGINE`**

Preserve hostname-resolution/version semantics distinctly from SOCKS4.

## 051 — SOCKS5

Decision:

**`CORE PRODUCT PROXY CAPABILITY / USE EXISTING APPROVED CORE OR LIBRARY`**

Treat TCP, UDP ASSOCIATE, auth and DNS semantics as capability dimensions. Do not infer UDP from TCP success.

## 052 — HTTP Proxy

Decision:

**`FOUNDATIONAL PROXY CAPABILITY / MATURE HTTP STACK`**

No dedicated VPN engine required.

## 053 — HTTPS / HTTP CONNECT

Decision:

**`FOUNDATIONAL WEB-PROXY CAPABILITY / MATURE HTTP+TLS STACK`**

Keep TLS validation, proxy auth and CONNECT target behavior explicit.

## 054 — SSH Tunnel

Decision:

**`MATURE STANDARD TUNNEL TARGET / OPENSSH-NATIVE FIRST`**

Primary source/reference: `openssh/openssh-portable` and platform-native OpenSSH packages.

Do not implement SSH cryptography/protocol from scratch. Use a mature SSH implementation behind a typed adapter and secure host-key/key/password storage.

## 055 — Tor SOCKS

Decision:

**`OPTIONAL PRIVACY-OVERLAY MODULE / ARTI OR TOR-DAEMON IMPLEMENTATION`**

Primary modern reference: Tor Project Arti, with established Tor daemon ecosystem as another implementation path.

Do not implement onion routing from scratch and do not market Tor as equivalent to a normal fast single-hop VPN.

## Engine-count rule

A major product goal is **maximum useful coverage with minimum reliable engines**.

Therefore:

- 049–053 should normally be capabilities of existing core/library stacks;
- 044–047 should be compared against approved multi-protocol engines before direct extra-core integration;
- 054 should use OpenSSH/native OS implementation;
- 055 should use Tor/Arti.

## Security / storage rule

All reusable credentials must be protected:

- proxy usernames/passwords;
- AnyTLS/TUIC/ShadowTLS secrets;
- SSH private keys/passwords;
- Tor bridge/auth material where applicable.

Canonical profiles contain secure references, not raw reusable secrets where practical.

## Chained/wrapper profiles

Some capabilities may be used in chains, for example outer transport/security + inner proxy.

PVNetwork canonical model should represent a typed connection graph/chain rather than concatenate arbitrary engine command strings.

Validate cycles, unsupported combinations and credential boundaries.

## Product UI rule

Simple Mode should show a human connection/profile and hide low-level inner/outer proxy composition unless needed.

Advanced Mode can expose:

- protocol version;
- DNS behavior;
- UDP capability;
- TLS/host verification;
- chain order;
- engine/version;
- expert session/transport options.

## Residual gaps

- exact current upstream pins and license tables;
- exact config/API maps for 044–047;
- authoritative Snell source/protocol/version landscape;
- exact OpenSSH/Arti integration model/license/package state;
- current issue/advisory/performance evidence;
- full per-client UI/menu/source comparison.

These can remain explicit at v1 handoff while implementation and mandatory v2 server/crypto/wire/install/menu work remain later.
