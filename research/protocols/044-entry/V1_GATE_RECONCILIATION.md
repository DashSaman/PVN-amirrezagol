# 044 — TUIC — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **044 — TUIC**

Decision: **`COMPLETE-RESEARCH-v1 / MODERN QUIC PROXY TARGET / NOT IMPLEMENTED / NOT CERTIFIED`**

This entry covers TUIC protocol version `0x05` as currently standardized by the TUIC protocol project. TUIC is distinct from Hysteria2 and from generic QUIC: QUIC/TLS supplies the secure multiplexed transport, while TUIC defines authentication, TCP relay and UDP relay commands above that transport.

## 1. Protocol authority

Canonical specification repository:

- repository: `tuic-protocol/tuic` (historical owner URL `EAimTY/tuic` resolves to this organization)
- reviewed commit: `8e118f242f24a17a9f487dc344cc50d7e63e557e`
- reviewed tree: `3dab59619e77fe44d4f97b534e7b8ea9a0e96475`
- tree contains protocol/spec governance only: `SPEC.md`, README, LICENSE, contribution/code-of-conduct and issue/PR templates
- repository license: GPLv3
- README explicitly states that **the TUIC protocol concept itself is license-free, untrademarked and unpatented**, and independent implementations may implement/modify/distribute the protocol without restriction.
- current reviewed protocol version: `0x05`
- reviewed spec blob: `SPEC.md@fe246d88e57e306e767265230fa178640950060a`

The spec repository deliberately contains **no official implementation**. Implementations are third-party and must be audited separately.

## 2. Wire/security boundary from the current specification

The current spec defines a multiplexable **TLS-encrypted stream**, mainly intended for QUIC. Command types include:

- Authenticate;
- Connect (TCP relay);
- Packet (UDP relay/fragment);
- Dissociate;
- Heartbeat.

Authentication uses a 16-byte UUID plus a 32-byte token. The raw password is incorporated via the TLS Keying Material Exporter for the current TLS session; PVNetwork must therefore keep UUID and raw reusable password as separate profile/secure-secret fields rather than persisting an opaque generated token as the user's canonical credential.

TCP relay uses bidirectional streams. UDP relay uses an associate-ID session model, packet IDs/fragment metadata and can use QUIC unidirectional streams or QUIC DATAGRAM mode. The specification also documents 0-RTT-oriented behavior and connection migration motivation.

Important failure-model limitation: the protocol defines no universal error response for commands; implementation behavior can be connection close, stream close or ignore. PVNetwork therefore needs an implementation-specific error adapter instead of assuming a universal TUIC error code.

## 3. Current serious implementations

### A. ClashRS — strong permissive client/core candidate

- repository: `Watfaq/clash-rs`
- reviewed commit: `b0538e86aedcbe7f000bb9f00889175ffb85176c`
- current reviewed head date: 2026-08-05
- license: Apache-2.0
- language/build: Rust/Cargo with separate `clash-lib`, binary, FFI and web-dashboard subprojects
- TUIC-specific current source includes `clash-lib/src/proxy/tuic/` (`mod.rs`, `types.rs`, stream/task handling, compatibility and test utilities) and `clash-lib/src/proxy/converters/tuic.rs`.
- current converter maps typed TUIC fields including server/port, UUID/password, UDP relay mode, SNI/ALPN/cert-verification, heartbeat/timeouts, congestion control, UDP packet size, stream/window settings and TLS material into a TUIC handler.
- dashboard/UI and broader routing/DNS/provider/log behavior live in separate product layers.

Reuse classification: **`REUSE-CANDIDATE / NEEDS DEPENDENCY+SBOM+API REVIEW`**. Its Apache-2.0 root license is materially easier for a closed commercial product than the GPL implementations below, but exact dependencies/NOTICE/FFI/API stability still require production review.

### B. `cfal/shoes` — active permissive server/reference implementation

- repository: `cfal/shoes`
- reviewed commit: `7a5a8ee3bd1c52bc15ec57e074e95e374d41f275`
- reviewed head date: 2026-08-09
- license: MIT
- role in canonical TUIC README: open-source server-side implementation
- current source is actively changing server/config behavior and contains tests/config documentation.

Reuse classification: **server/interoperability/reference candidate**, not automatically a consumer-client engine.

### C. `Itsusinn/tuic` — very active full client/server implementation, copyleft boundary

- repository: `Itsusinn/tuic`
- reviewed commit: `0eef0b1d62758bb63f954a81f7ac74b94ed9da29`
- reviewed tree: `cfd1d3bf38c5eeb3ba72de5f65fb737e5ef7c8a7`
- reviewed head date: 2026-08-10
- current project state identifies itself as `2.0.0-dev1`
- implementation: Rust/Cargo workspace, client/server/tests plus networking framework submodule
- root license: GPLv3-or-later according to current README/LICENSE; the reviewed tree also contains component-specific copyleft license files (for example a client AGPLv3 file), so path/component-level legal review is mandatory.
- config supports TOML/YAML/JSON/JSON5, client/server roles, SOCKS5/forwarding, congestion control, ACME/TLS, routing, HTTP/3 masquerade, container deployment and management/observability features.
- current 2026 tests explicitly distinguish a “0-RTT enabled config path” from a real resumed 0-RTT handshake and add actual resumption acceptance tests for multiple QUIC backends.

Reuse classification: **`REFERENCE-ONLY / NEEDS-LEGAL-REVIEW` by default for a closed PVNetwork application**, but excellent interoperability/test/source behavior reference.

### D. Other implementations/clients listed by protocol authority

Current canonical TUIC README lists server implementations including mihomo, shoes, sing-box and Itsusinn/tuic; client implementations include ClashRS, dae, Egern, mihomo, sing-box, Shadowrocket, Stash, Surge and Itsusinn/tuic. Closed-source products are behavioral/interop/UI references only. Mihomo/sing-box and GUI licenses remain independently audited; their presence does not grant a permissive reuse path.

## 4. Canonical PVNetwork model

Keep separate typed concerns:

- `protocol = TUIC`;
- `tuic_version = 0x05` or exact supported protocol-version identifier;
- endpoint/port;
- UUID;
- password as secure-store reference;
- TLS/server-name/certificate policy;
- ALPN where the selected engine exposes it;
- UDP relay mode (`native`/DATAGRAM vs stream-style behavior as supported);
- congestion/heartbeat/window/timeout/0-RTT capability fields only when the selected engine/version supports them;
- routing/DNS/TUN/per-app outside the TUIC protocol object;
- original imported source and engine/version metadata.

Do not flatten TUIC into “QUIC proxy”, do not equate Hysteria2 with TUIC, and do not silently enable certificate verification bypass or 0-RTT.

## 5. 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / TUIC-specific conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations identified and justified | PASS | Canonical TUIC README provides maintained client/server implementation list; ClashRS, shoes and Itsusinn/tuic are source-audited by role, with closed clients retained only as behavioral references. |
| 2 | Canonical sources pinned | PASS | Protocol repo `8e118f...`; ClashRS `b0538e...`; shoes `7a5a8e...`; Itsusinn `0eef0b...` and exact trees/paths recorded. |
| 3 | Licenses reviewed | PASS | Spec repo GPLv3 but protocol concept explicitly license-free; ClashRS Apache-2.0; shoes MIT; Itsusinn root GPLv3+ with component-level copyleft complexity; closed clients reference-only. |
| 4 | Complete source-tree reference/manifest captured | PASS | Entire canonical spec tree and Itsusinn recursive tree pinned; ClashRS TUIC implementation/config paths and shoes current server role/pin recorded. |
| 5 | Languages/build systems mapped | PASS | Spec docs only; ClashRS Rust/Cargo + lib/bin/FFI/dashboard; Itsusinn Rust/Cargo workspace/submodule; shoes Rust-based server project; closed clients are N/A for source build. |
| 6 | Architecture mapped | PASS | Protocol commands/auth/TCP/UDP/session behavior separated from QUIC/TLS; client/core/server/product routing/UI boundaries mapped across current implementations. |
| 7 | Core/engine integration mapped | PASS | ClashRS library/FFI/executable path is a permissive candidate; Itsusinn full client/server is a copyleft reference; server-only shoes is interoperability/reference. Product adapter remains engine-neutral. |
| 8 | UI/menu map completed | PASS for V1 | Canonical implementation list identifies current GUI ecosystems; ClashRS includes web dashboard/proxy-list/product UI, while dedicated implementations are primarily config/CLI. PVNetwork owns final GUI. Exhaustive per-client menus/screenshots remain V2. |
| 9 | Config/import/export mapped | PASS | Current TUIC spec provides protocol fields; ClashRS converter maps practical TUIC options; Itsusinn current implementation provides TOML/YAML/JSON/JSON5 config and backward-compat test fixtures. Unknown/version-specific fields must be preserved/reported. |
| 10 | Persistence/secrets mapped | PASS | UUID is identity; raw password is reusable secret and must live in protected platform storage. TLS keys/certs and generated configs are sensitive. Product profile stores references/non-secret parameters separately. |
| 11 | Platform integrations mapped | PASS for research | Canonical README lists cross-platform and Apple/Linux clients; ClashRS is cross-platform; Itsusinn documents Linux/Windows/macOS/FreeBSD builds/containers. Exact mobile/Store TUN lifecycle remains later certification. |
| 12 | Logs/diagnostics mapped | PASS | Current implementations expose logs/metrics/API/runtime diagnostics at implementation/product layers; PVNetwork must map protocol vs QUIC/TLS/TUN failures and redact password/TLS/API secrets. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Canonical repo has documentation-only assets; ClashRS dashboard and third-party clients provide UI references. Third-party branding/screenshots remain reference-only unless licensing permits reuse. |
| 14 | Meaningful forks/alternatives reviewed | PASS | Canonical repo intentionally has no implementation; current serious alternatives across ClashRS/shoes/Itsusinn plus mihomo/sing-box ecosystem are explicitly separated by role/license. |
| 15 | Issues/PRs/releases/advisories reviewed | PASS | Canonical head/current implementation list reviewed; canonical GitHub security-advisory endpoint currently returns no published entries (not a security certification); current Itsusinn 2026 commit/test evolution and active ClashRS/shoes maintenance are recorded. Exact selected dependency advisories remain source-freeze work. |
| 16 | Relevant forums/docs reviewed | PASS | Canonical README/SPEC and current implementation docs/configs are authoritative; current protocol reboot/governance note and implementation listings establish version/governance context. |
| 17 | Tests/CI reviewed | PASS | Itsusinn current tree contains CI and extensive config/integration tests including real 0-RTT resumption; ClashRS TUIC test utilities/source exist; final PVNetwork interoperability tests remain independent. |
| 18 | Store/privacy/security implications reviewed | PASS | TLS verification, password storage, 0-RTT replay risk, QUIC dependency/SBOM, copyleft implementation boundaries, management API secrets and platform TUN/Store lifecycle are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Prefer evaluating Apache-2.0 ClashRS TUIC library/FFI path first; compare with any approved existing engine. Use shoes for server/interoperability evidence; treat Itsusinn/mihomo/sing-box/closed clients according to their separate licenses. |
| 20 | Uncertainties explicitly listed | PASS | Exact production engine/API/FFI stability, current dependency SBOM/advisories, protocol-version matrix, server interoperability, client import URI/schema, mobile lifecycle, performance and V2 server/UI/wire/install evidence remain later gates. |

## 6. Security/compatibility requirements derived from research

1. Preserve and validate TUIC protocol version; different versions are not guaranteed compatible.
2. Store raw password in secure credential storage; derive/session-use tokens through the selected maintained implementation.
3. Default TLS verification ON; certificate bypass is dangerous/explicit/temporary.
4. Treat 0-RTT as a capability with replay semantics; do not perform non-idempotent product/admin actions using early data.
5. TCP and UDP relay capabilities are independently testable; UDP native/DATAGRAM and stream modes must not be conflated.
6. Product error taxonomy must account for implementation-defined TUIC command failures plus QUIC/TLS failures.
7. Pin exact QUIC/TLS dependencies and selected implementation before shipping.
8. No home-grown TLS/QUIC cryptography.

## 7. Later acceptance work — not V1 blockers

Before advertising TUIC support:

- select exact engine/release/commit and produce SBOM/license/vulnerability evidence;
- certify protocol version 0x05 and reject/handle unsupported versions explicitly;
- test UUID/password auth including negatives and secure-store lifecycle;
- test TLS certificate/SNI/ALPN/rotation/invalid certs;
- test genuine 0-RTT resumption and replay-sensitive behavior;
- test TCP and UDP native/stream relay modes, fragmentation and Full Cone behavior;
- test QUIC migration/loss/latency/PMTU/IPv4/IPv6/network handover;
- test routing/DNS/TUN/per-app and leak behavior per platform;
- test crash/reconnect/background/Store lifecycle;
- benchmark selected engine(s);
- complete V2 server implementations/installers/UI, client install/menu map, cryptography, data path, ports/handshake and deployment topologies.

## Final V1 decision

All 20 original V1 research gates are evidence-backed with current protocol governance, source/license alternatives and QUIC/TLS/TUIC boundaries explicit.

Entry 044 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
