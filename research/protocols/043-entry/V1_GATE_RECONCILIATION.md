# 043 — Hysteria2 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **043 — Hysteria2**

Decision: **`COMPLETE-RESEARCH-v1 / HIGH-PRIORITY MODERN TARGET / NOT IMPLEMENTED / NOT CERTIFIED`**

This entry covers the current Hysteria2 generation. It is separate from entry 042 legacy Hysteria v1.

## Exact upstream/release baseline

Official repository: `apernet/hysteria`

Research architecture pin:

- commit: `14e9fff1d972ab0187ac7fcf75b9514dc8664065`
- root license: MIT
- current module family: `github.com/apernet/hysteria/app/v2`, with local `core/v2` and `extras/v2`

Newest non-prerelease release observed in the repository review:

- tag: `app/v2.12.1`
- published: 2026-08-09
- release includes platform/architecture assets plus `hashes.txt` and GitHub asset digests.

Production selection must pin exact tag + asset filename + SHA-256 + source/dependency provenance + rollback version; never download an unqualified `latest` artifact at runtime.

## Protocol architecture

Official `PROTOCOL.md` at the research pin describes the protocol starting with version 2.0.0 (internally sometimes called v4). Core behavior includes:

- standard QUIC RFC 9000;
- QUIC DATAGRAM RFC 9221;
- HTTP/3 masquerading/authentication;
- client auth via HTTP/3 POST `/auth`, with protocol-specific success status;
- TCP proxy requests over QUIC bidirectional streams;
- UDP messages over QUIC DATAGRAM with session/fragment metadata;
- congestion-control/rate negotiation;
- optional packet obfuscation such as Salamander.

Current client configuration additionally exposes server/auth, transport/UDP port hopping, obfuscation, TLS SNI/CA/pin/client cert/ECH, QUIC receive/idle/keepalive/PMTU/socket options, congestion/bandwidth settings, SOCKS5/HTTP/TCP/UDP/TProxy/redirect/TUN modes and TUN route include/exclude controls.

Platform-specific socket options can return an explicit unsupported-platform error, so schema presence is not platform-certification evidence.

## Security floor and advisories

Two official Hysteria security advisories establish a known minimum patch floor:

### `GHSA-vgrc-hq28-p3xp`

- High severity;
- affected `github.com/apernet/hysteria/core/v2` versions `>=2.0.0, <=2.9.1`;
- patched in `2.9.2`;
- issue: UDP ACL/session authorization could be reused to reach blocked/internal destinations.

### `GHSA-qh5x-rfwf-rvfv`

- High severity;
- affected `<2.9.2`;
- patched in `2.9.2`;
- issue: QUIC DATAGRAM fragmentation path could crash the server with very small peer datagram limits.

Therefore **2.9.2 is the minimum floor established by these reviewed advisories**, not a permanent blanket approval. The selected release still needs a complete current advisory/dependency/SBOM review.

## Dependency boundary

At the architecture pin, important dependencies include:

- Hysteria's `apernet/quic-go` fork;
- `apernet/sing-tun`;
- certmagic and DNS-provider modules;
- uTLS;
- DTLS/STUN/NAT libraries;
- Viper/Cobra and logging/system/network modules.

A Hysteria2 product version is not reproducible merely from the app tag. Exact app/core/extras/QUIC/dependency revisions belong in the SBOM.

## Product classification / reuse decision

**`HIGH-PRIORITY MODERN QUIC PROXY TARGET / OFFICIAL UPSTREAM STRONG REUSE CANDIDATE / EXACT RELEASE+SBOM+PLATFORM CERTIFICATION REQUIRED`**

Prefer the official Hysteria2 engine/core behind a product-owned adapter unless another already-approved engine demonstrates equal protocol parity, patch latency, performance, platform support and lower overall integration/license burden.

Do not copy third-party GUI applications merely for their UX.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / Hysteria2 decision |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | Official upstream client/server is primary engine authority. Hiddify, Karing, NekoBox and other modern multi-protocol clients are useful UX/import/platform references but have independent cores/licenses. |
| 2 | Canonical sources pinned | PASS | Exact upstream architecture commit recorded; current release tag `app/v2.12.1` recorded separately for release evidence. |
| 3 | Licenses reviewed | PASS | Official root license MIT; third-party client/core licenses remain independent; shipped dependency licenses require exact SBOM review. |
| 4 | Complete source-tree reference/manifest captured | PASS | Shared family dossier records exact recursive source tree for `14e9fff...`; app/core/extras and protocol/config paths are mapped. |
| 5 | Languages/build systems mapped | PASS | Go project; current app module requires Go 1.25/toolchain 1.25.1 and local core/extras modules; platform/release assets documented. |
| 6 | Architecture mapped | PASS | QUIC/TLS/auth/HTTP3/TCP-stream/UDP-DATAGRAM/obfs/congestion plus client/server/config/runtime boundaries documented. |
| 7 | Core/engine integration mapped | PASS | Recommended boundary is `PVNetwork canonical profile -> Hysteria generation adapter -> official executable/core -> platform TUN/proxy lifecycle`; direct-upstream vs approved multi-core engine must be benchmarked. |
| 8 | UI/menu map completed | PASS for V1 | Family decision defines simple vs advanced UI and existing multi-client references cover import/subscription/profile/routing/log lifecycle. Product UI must capability-gate version/platform-specific QUIC/TLS/TUN options. Exhaustive fields/screenshots remain V2. |
| 9 | Config/import/export mapped | PASS | Typed current config surface is documented; original imported source stays separate from canonical profile and generated engine config; generation/version is explicit. |
| 10 | Persistence/secrets mapped | PASS | Auth secrets, client key/cert material and sensitive subscription/config values require secure storage/redaction; non-secret transport/routing state remains separate. |
| 11 | Platform integrations mapped | PASS for research | Current source/release shows broad platform assets and TUN/socket-specific behavior; actual Android/Apple/desktop lifecycle remains later exact-version/device certification. |
| 12 | Logs/diagnostics mapped | PASS | Family dossier defines process/connection/TUN/routing health separation, auth/config redaction, and server/client fault ownership. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Official and multi-client resources are reference-only; no third-party branding/assets are copied. Exhaustive screenshots remain V2. |
| 14 | Meaningful forks/alternatives reviewed | PASS | Official engine is compared conceptually with sing-box/Mihomo/multi-client implementations; duplication rule requires parity/license/performance/patch-latency comparison before adding a second engine. |
| 15 | Issues/releases/advisories reviewed | PASS | Current release, two High advisories, version floor, dependency risk and release/hash policy are documented. Current issue/regression refresh remains source-freeze/certification work. |
| 16 | Relevant forums/docs reviewed | PASS | Official protocol/config/source/release/security documentation and modern client ecosystem references are incorporated. |
| 17 | Tests/CI reviewed | PASS | Family test dossier maps upstream engine testing and independent PVNetwork tests for imports, TUN, DNS/routing, handover, lifecycle, error mapping and performance. |
| 18 | Store/privacy/security implications reviewed | PASS | TLS/cert validation, auth secure storage, unsafe-insecure override policy, QUIC dependency/SBOM, platform permissions/lifecycle, artifact hashes and advisory floor are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Strong official-core candidate behind product-owned adapter; compare with any existing approved full-parity engine before shipping duplicate core. |
| 20 | Uncertainties explicitly listed | PASS | Exact production release/artifact/SBOM, current full advisory set, wrapper/API-vs-process boundary, mobile embedding, exact client interoperability, performance and V2 server/wire/install evidence remain explicit later gates. |

## Canonical profile rules

1. `protocol_generation = v2` is explicit and never inferred from entry 042.
2. Keep endpoint, auth, TLS/trust, obfuscation, QUIC/session, congestion/bandwidth, UDP/TCP behavior, routing/TUN and engine version as typed fields.
3. Preserve original imported source separately.
4. Do not expose unsupported platform socket/TUN options merely because they exist in shared config schema.
5. Do not default to `insecure` certificate verification.
6. Do not treat a local SOCKS/HTTP connection as proof of full-device TUN/DNS/routing support.
7. Exact app/core/extras/QUIC dependency revisions are part of release identity.
8. No automatic v1->v2 migration without explicit version-aware conversion and interop test.

## Mandatory later acceptance tests derived from reviewed advisories

- UDP ACL must be evaluated for packet-scoped destinations; an allowed UDP session must not pivot to blocked localhost/RFC1918 targets.
- Tiny/malformed QUIC DATAGRAM limits must fail safely without process crash.
- A protocol-session/process fault must not leave product UI reporting a healthy connection/server.
- Builds below the reviewed 2.9.2 security floor must be rejected by default.

## Future acceptance work — not V1 blockers

Before support claim: freeze exact current release and asset hashes; produce resolved dependency/SBOM/license/advisory report; certify app/core/extras/QUIC revisions; test auth/TLS/cert/pinning/ECH negatives, obfs and port hopping, TCP/UDP/QUIC behavior, loss/latency/PMTU/network migration, TUN/DNS/routing/IPv4/IPv6/leaks, platform lifecycle, import/export, crash/reconnect/rollback and performance; then complete V2 server/install/UI/crypto/wire/ports/topology evidence.

## Final V1 decision

All 20 original research gates are evidence-backed with current release/security/dependency and generation boundaries explicit. Entry 043 is therefore **`COMPLETE-RESEARCH-v1`**, while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
