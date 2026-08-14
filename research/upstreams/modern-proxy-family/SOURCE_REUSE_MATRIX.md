# Modern Proxy / Tunnel Family — Source / Reuse Matrix

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Entries covered for research efficiency:

- 044 TUIC
- 045 AnyTLS
- 046 ShadowTLS
- 047 NaiveProxy
- 048 Snell
- 049 SOCKS4
- 050 SOCKS4a
- 051 SOCKS5
- 052 HTTP Proxy
- 053 HTTPS / HTTP CONNECT
- 054 SSH Tunnel
- 055 Tor SOCKS

These remain separate protocol/capability entries. This grouping is not a runtime-engine claim.

## 044 — TUIC

Primary open-source upstream/reference: `EAimTY/tuic`.

Current codebase is a QUIC-based proxy implementation and a major protocol reference for TUIC behavior.

Research classification:

`MODERN QUIC PROXY TARGET / MULTI-CORE VS DIRECT-UPSTREAM COMPARISON REQUIRED`

PVNetwork should compare direct TUIC upstream integration against existing approved engines that already implement TUIC (for example modern multi-protocol cores) before adding a dedicated engine.

Decision criteria:

- exact protocol/version compatibility;
- release/security patch lag;
- QUIC behavior/performance;
- platform support;
- dependency/license burden;
- binary size;
- adapter complexity;
- client/server interoperability.

Do not add a duplicate TUIC engine without measurable value.

## 045 — AnyTLS

Primary implementation/reference: `anytls/anytls-go` plus implementations in selected multi-protocol cores.

Research classification:

`MODERN TLS-BASED PROXY TARGET / PREFER EXISTING APPROVED CORE IF PARITY EXISTS`

PVNetwork should treat AnyTLS as an application proxy protocol/capability, not as generic “TLS support”.

The canonical model must keep:

- AnyTLS endpoint/authentication;
- TLS server name/trust/fingerprint settings;
- protocol-specific padding/session options;
- original import source;
- selected engine/version.

Do not conflate entry 045 AnyTLS with entry 077 generic TLS security technology.

## 046 — ShadowTLS

Primary implementation/reference: `ihciah/shadow-tls` plus modern core implementations.

Research classification:

`SECURITY/PROXY WRAPPER TARGET / MULTI-CORE COMPARISON REQUIRED`

ShadowTLS is not a general-purpose VPN protocol in the same sense as WireGuard/OpenVPN. Model it as a protocol/wrapper capability that can be combined with an underlying proxy according to the implementation/version.

PVNetwork must keep the outer ShadowTLS parameters separate from the inner proxy profile rather than flattening both into one opaque URL.

## 047 — NaiveProxy

Primary upstream: `klzgrad/naiveproxy`.

NaiveProxy uses Chromium's networking stack and HTTPS/HTTP2/HTTP3-style web transport behavior depending on build/version.

Research classification:

`HIGH-COMPATIBILITY WEB-PROXY TARGET / LARGE DEPENDENCY+BUILD FOOTPRINT REVIEW REQUIRED`

PVNetwork should evaluate whether direct NaiveProxy integration is justified versus an approved core that can invoke/implement the required behavior.

Key cost: Chromium/network-stack build and update surface can be significantly larger than compact proxy engines.

## 048 — Snell

Research classification:

`PROPRIETARY/EXTERNALLY-SPECIFIED COMPATIBILITY TARGET / NO ASSUMED OPEN SERVER CORE`

Snell is associated with the Surge ecosystem. Public multi-protocol clients/cores may implement compatibility, but PVNetwork must not assume the original server/client source is open or redistributable.

Before support:

- identify authoritative protocol/version documentation;
- identify a legally compatible maintained implementation;
- record server availability/licensing;
- test exact protocol version interoperability;
- avoid copying proprietary binaries/assets.

This entry may remain reference/compatibility-only if no suitable reusable implementation is available.

## 049 — SOCKS4

Classification: standard generic proxy protocol.

Research decision direction:

`FOUNDATIONAL PROXY CAPABILITY / NO DEDICATED ENGINE NEEDED`

Use an already-approved networking core/library or platform proxy component. Do not add a separate SOCKS4 daemon solely for this entry unless required by architecture.

## 050 — SOCKS4a

Classification: SOCKS4 extension supporting hostname-oriented behavior.

Research direction:

`FOUNDATIONAL PROXY CAPABILITY / NORMALIZE AS SOCKS FAMILY WITH VERSION=4A`

Keep version semantics explicit for import/export and DNS-resolution behavior.

## 051 — SOCKS5

Classification: standard generic proxy protocol.

Research direction:

`CORE PRODUCT PROXY CAPABILITY / USE EXISTING APPROVED CORE OR LIBRARY`

Canonical model should support, where implemented:

- endpoint;
- optional username/password secure reference;
- remote/local DNS behavior;
- TCP;
- UDP ASSOCIATE capability when supported;
- engine/version.

Do not infer UDP support from basic TCP SOCKS5 connectivity.

## 052 — HTTP Proxy

Classification: standard HTTP proxy capability.

Research direction:

`FOUNDATIONAL PROXY CAPABILITY / PLATFORM-OR-CORE IMPLEMENTATION`

Keep authentication/proxy headers and secret references protected. Do not leak proxy credentials into logs or subscription URLs.

## 053 — HTTPS / HTTP CONNECT

Classification: HTTP CONNECT/tunneled web-proxy capability, potentially protected by TLS depending on scheme/implementation.

Research direction:

`FOUNDATIONAL WEB-PROXY CAPABILITY / USE MATURE HTTP/TLS STACK`

Do not implement HTTP/TLS parsing/cryptography from scratch.

Keep:

- proxy TLS validation;
- proxy authentication;
- CONNECT target policy;
- system/browser vs full-TUN integration

as distinct product concerns.

## 054 — SSH Tunnel

Primary reusable upstream/reference: `openssh/openssh-portable` plus native OS OpenSSH clients.

Research classification:

`MATURE STANDARD TUNNEL TARGET / NATIVE-OPENSSH-FIRST WHERE PRACTICAL`

PVNetwork should not implement SSH cryptography/protocol stack from scratch.

Use mature SSH implementation/library/process with a narrowly controlled adapter.

Potential connection modes to model separately:

- dynamic forwarding (SOCKS);
- local forwarding;
- remote forwarding (primarily advanced/admin use);
- SSH-based proxy transport where a selected engine supports it.

Private keys/passwords/known-host state require platform-secure storage and explicit host-key verification policy.

## 055 — Tor SOCKS

Primary modern embedding/reference direction: Tor Project's **Arti** (Rust implementation) and the established Tor daemon ecosystem.

Research classification:

`PRIVACY-OVERLAY OPTIONAL MODULE / ARTI VS TOR-DAEMON COMPARISON REQUIRED`

PVNetwork should not treat Tor as just another fast VPN server. It has distinct privacy, routing, bootstrap, bridge/pluggable-transport and performance semantics.

For a simple client use case, Tor exposes a local SOCKS endpoint; a product adapter can route selected traffic to it without conflating Tor circuits with a normal single-hop VPN tunnel.

## Engine minimization conclusion

Entries 049–053 should usually **not** create five separate engines. They are protocol capabilities provided by mature networking libraries/cores.

Entries 044–047 may be implemented through one or more already-approved modern multi-protocol cores if feature parity/security/release lag is acceptable.

Entry 054 should prefer mature OpenSSH/native implementation.

Entry 055 should use an actual Tor/Arti implementation if included, not reimplement onion routing.

## Canonical model rule

Do not store every proxy as a raw share URL.

Use typed profiles:

- protocol/version;
- endpoint;
- auth secret reference;
- TLS/security properties;
- transport/session options;
- DNS semantics;
- UDP capability;
- inner/outer relationship for wrapper protocols;
- source/import metadata;
- selected engine/version.

Preserve original source and unknown fields when possible.

## Residual v1 gaps

- exact immutable pins/licenses for each selected upstream;
- TUIC/AnyTLS/ShadowTLS/Naive config/API maps;
- Snell authoritative version/license/implementation landscape;
- exact SOCKS/HTTP standards and engine behavior matrices;
- OpenSSH/Arti exact source/license/release pins;
- current issues/advisories/performance/platform evidence;
- full client GUI/menu mapping.

These gaps can be closed in per-entry dossiers or carried explicitly at family handoff. Server installers/cryptography/wire flow/full install/menu evidence remains mandatory later v2.
