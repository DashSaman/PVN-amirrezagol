# 046 — ShadowTLS — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **046 — ShadowTLS**

Decision: **`COMPLETE-RESEARCH-v1 / V3-PREFERRED CAMOUFLAGE TRANSPORT / NOT IMPLEMENTED / NOT CERTIFIED`**

ShadowTLS is a TLS-handshake camouflage / TCP-flow proxy layer. It is **not** generic TLS, AnyTLS, Trojan, or an end-user encrypted proxy by itself. The official project explicitly positions it to expose a real TLS handshake to observers and normally pair with another proxy/encryption layer. PVNetwork must therefore model composition rather than claim that ShadowTLS alone supplies payload confidentiality.

## Canonical source and exact pins

Official repository: `ihciah/shadow-tls`

Current reviewed source:

- branch: `master`
- commit: `02dd0bc7bae8a2011729f95021690e694fd8e43e`
- tree: `3a98cb8012ffddb32809fd2cddeb2c208c68c646`
- latest reviewed tagged release: `v0.2.25`
- release commit: `dee3a5a819d6f56cfdd56c44a0d42be186c44238`
- release published: 2023-12-13
- language/build: Rust / Cargo
- license: **MIT** at the exact reviewed HEAD

The current HEAD is newer than the latest tagged release and includes 2025 maintenance changes, including a TLS1.2 test-host fix and WildcardSNI SIP003 argument handling. PVNetwork must therefore keep `release` and `reviewed source HEAD` as separate pins and not pretend the 2025 fixes are included in `v0.2.25` unless verified by a later release.

The exact source tree contains Cargo lock/build files, Docker and compose files, release/CI workflows, v2/v3 protocol documents, client/server examples, SIP003 support, client/server implementation code, and dedicated SNI/TLS1.2/TLS1.3 tests.

## Generation boundary: v1 / v2 / v3

Canonical v3 document: `docs/protocol-v3-en.md` at blob `1766a21576ddc81958276fb1f2f82bbc0b9665c2`.

Upstream evolution is explicit:

- **v1**: forwards a real TLS handshake and relied on weak assumptions about passive observation;
- **v2**: adds client authentication/challenge-response and TLS ApplicationData encapsulation to improve active-probe resistance;
- **v3**: redesigns handshake/data verification to address traffic hijacking/tampering/replay/order/cut-splice classes while keeping the implementation weakly coupled to TLS internals.

PVNetwork policy:

- model `version` explicitly;
- prefer v3 for newly created profiles when the selected engine/server combination supports it;
- retain v1/v2 only as explicit compatibility modes;
- never silently reinterpret v1/v2 as v3.

### TLS version / strict-mode boundary

For v3, the canonical design says strict mode requires a handshake server supporting TLS 1.3. Non-strict mode can allow TLS 1.2 for compatibility / weaker anti-hijack scenarios. This is a security-relevant product setting, not a cosmetic advanced toggle.

## Architecture and cryptographic role

V3 uses a real handshake server plus a separate data path. The client constructs/authenticates a TLS ClientHello/SessionID using the shared password/key material; the server forwards the handshake toward a real TLS endpoint, observes ServerRandom, authenticates/switches the flow, and wraps the later data stream in TLS ApplicationData-shaped records with HMAC-based integrity/state checks.

Important scope boundary:

- ShadowTLS is an obfuscation/camouflage transport around a TCP flow;
- it does **not** replace the inner proxy's confidentiality/authentication requirements;
- a common composition is an encrypted proxy such as Shadowsocks through ShadowTLS;
- PVNetwork must not label a bare ShadowTLS hop as an encrypted VPN tunnel.

The upstream security text is treated as a protocol-design claim, not third-party certification. PVNetwork still needs independent interoperability/negative/security regression tests before support claims.

## Canonical config evidence

Current official example client config includes:

- `v3`
- `strict`
- local client `listen`
- ShadowTLS `server_addr`
- one or more `tls_names`
- `password`
- transport-level options such as Fast Open / Nagle behavior.

The official source also contains SIP003 integration. PVNetwork canonical profile therefore keeps separately typed:

- `protocol = shadowtls`;
- explicit protocol `version`;
- endpoint/port;
- password as secure-store reference where required by generation;
- strict-mode policy;
- handshake SNI/name set;
- handshake-server mapping / wildcard-SNI capability where supported;
- selected engine/version;
- inner encrypted proxy / detour as a separate typed layer;
- product routing/DNS/TUN outside ShadowTLS identity;
- original imported configuration/source separately from normalized fields.

## Serious current host/client evidence

### sing-box

Reviewed stable host source: `SagerNet/sing-box@v1.13.18`, commit `45ca32dcb966f07f97fc888fe8586e359dbe8405`.

License: GPL-3.0-or-later with additional naming/association language already recorded in PVNetwork research.

Exact ShadowTLS option source exposes inbound `Version`, `Password`, users, handshake target(s), strict mode and wildcard-SNI modes; outbound exposes endpoint, `Version`, `Password`, and a distinct TLS options container.

Exact stable `test/shadowtls_test.go` covers:

- v1, v2, v3;
- v2/v3 with uTLS;
- v3 wildcard-SNI modes;
- fallback behavior and failure cases;
- integration where ShadowTLS is composed with Shadowsocks/SS2022 rather than treated as the payload-encryption protocol itself.

This is strong independent implementation and test evidence. It is not permissive code-reuse permission for a closed GUI.

### Throne GUI reference

Existing PVNetwork Throne dossier records GPL-3.0 architecture/reference status. Current source resolves dedicated ShadowTLS UI/config paths including:

- `include/configs/outbounds/shadowtls.h`
- `src/configs/outbounds/shadowtls.cpp`
- `src/ui/profile/edit_shadowtls.cpp`
- `include/ui/profile/edit_shadowtls.h`
- `include/ui/profile/edit_shadowtls.ui`
- profile database/factory/subscription integration paths.

This is direct V1 evidence of a typed desktop profile editor. The GUI/source/assets remain reference-only unless GPL-compatible distribution is deliberately chosen.

## Platform boundary

The official Rust implementation/release is not a universal-platform proof. Its historical/runtime stack and published binaries are strongest on Linux/macOS-class targets; lack of an official native Windows binary must not be hidden. A multi-protocol host such as sing-box may offer broader product integration, but exact desktop/mobile/TUN/Store support remains certification work.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / ShadowTLS conclusion |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | Official `ihciah/shadow-tls` is protocol/runtime authority; sing-box is a current serious multi-protocol implementation; Throne provides real GUI/profile evidence. |
| 2 | Canonical sources pinned | PASS | Official HEAD commit/tree plus latest release commit/date pinned; sing-box stable pin and Throne source paths recorded. |
| 3 | Licenses reviewed | PASS | Official ShadowTLS exact source is MIT; sing-box GPLv3+ with additional naming boundary; Throne GPLv3. MIT notice obligations and copyleft component boundaries remain explicit. |
| 4 | Complete source-tree reference/manifest captured | PASS | Recursive official tree `3a98cb...` is pinned and contains code, docs, examples, CI/release, Docker, Cargo lock and test files. |
| 5 | Languages/build systems mapped | PASS | Official Rust/Cargo plus Docker/GitHub Actions; sing-box Go; Throne C++/CMake GUI. |
| 6 | Architecture mapped | PASS | v1/v2/v3 evolution, real handshake server, client/server wrappers, HMAC/state switching, ApplicationData camouflage, fallback and separate inner data server/proxy are mapped. |
| 7 | Core/engine integration mapped | PASS | PVNetwork model is inner proxy -> ShadowTLS adapter/engine -> TCP/real-TLS-handshake camouflage. Official MIT runtime and maintained host-core options are separate candidates. |
| 8 | UI/menu map completed | PASS for V1 | Throne has dedicated ShadowTLS editor/config/UI files; sing-box exposes typed version/password/TLS/handshake/wildcard settings. Exhaustive screenshots remain V2. |
| 9 | Config/import/export mapped | PASS | Official JSON/example/SIP003 and host-core options map version, strict, password, SNI/handshake target and endpoint; original source is preserved separately from generated config. |
| 10 | Persistence/secrets mapped | PASS | Password/shared secret requires secure storage/redaction. SNI/handshake names are not passwords but are privacy/routing metadata. Runtime ServerRandom/HMAC/session state is transient, not persisted profile truth. |
| 11 | Platform integrations mapped | PASS for research | Official release/runtime scope and missing universal native-platform coverage are explicit; sing-box/GUI hosts provide broader integration references. Real device/TUN/Store lifecycle remains later certification. |
| 12 | Logs/diagnostics mapped | PASS | Diagnostics must distinguish handshake-server TLS failure, version/strict incompatibility, auth/HMAC failure, fallback, inner-proxy failure and product TUN/routing/DNS; password/raw configs are redacted. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Official repository contains project resource material and Throne contains actual UI resources. Third-party branding/screens are reference-only; no trademark/brand rights are inferred from source license. |
| 14 | Meaningful forks/alternatives reviewed | PASS | sing-box is a maintained independent implementation; v1/v2/v3 are explicit protocol-generation alternatives. SIP003 and GUI host paths are also mapped without claiming equivalence. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | Latest release v0.2.25, newer 2025 source fixes, WildcardSNI change and protocol-v3 hijacking-motivation lineage are recorded. Design claims are not mislabeled external security certification. |
| 16 | Relevant forums/docs reviewed | PASS | Official README, v2/v3 protocol docs, quick-start/how-to-run material, source examples and issue-linked protocol evolution are primary evidence. |
| 17 | Tests/CI reviewed | PASS | Official HEAD includes CI/release workflows and SNI/TLS1.2/TLS1.3 tests; current sing-box adds v1/v2/v3, uTLS, wildcard-SNI, fallback and composition tests. |
| 18 | Store/privacy/security implications reviewed | PASS | Password storage, SNI privacy, strict TLS1.3 policy, non-strict TLS1.2 tradeoff, lack of standalone payload encryption, platform gaps, MIT/GPL boundaries and fallback behavior are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Official MIT code is legally much simpler to reuse with required notices, but engine choice must still compare maintenance/platform/performance. Prefer an already-approved host engine when it avoids duplicate lifecycle cost and license architecture is acceptable. Do not copy GPL GUI code into a closed app by default. |
| 20 | Uncertainties explicitly listed | PASS | Exact shipping engine/release, dependency SBOM/advisories, v1/v2 compatibility demand, handshake-server/SNI matrix, strict/non-strict policy, inner-proxy combinations, mobile lifecycle/performance and V2 wire/server/deployment evidence remain later work. |

## Security/product rules that survive handoff

1. Do not describe ShadowTLS alone as a payload-encrypting VPN/proxy.
2. Prefer v3 for new profiles when supported; keep v1/v2 explicit compatibility modes.
3. Prefer strict v3/TLS1.3 where the handshake-server matrix supports it; non-strict/TLS1.2 is an explicit security tradeoff.
4. Store passwords in platform secure storage and redact generated configs/logs/support bundles.
5. Treat handshake SNI/name selection as typed behavior and privacy-sensitive metadata.
6. Preserve real fallback behavior for unauthenticated/probe traffic where the selected implementation supports it.
7. Keep the inner encrypted proxy/detour independently typed and certified.
8. Reuse maintained TLS/crypto libraries and protocol engines; do not invent custom cryptography in the GUI.

## Later acceptance work — not V1 blockers

Before a support claim: freeze exact engine/release/source/artifact hashes/dependency SBOM/license notices; test v1/v2/v3 interop where advertised; test strict/non-strict TLS1.3/TLS1.2 handshake servers, password negatives, SNI/wildcard/fallback, uTLS where exposed, real inner-proxy combinations, IPv4/IPv6, DNS/routing/TUN, reconnect/network-change/suspend/crash cleanup; benchmark target networks/platforms; then complete V2 server/installers/panels, exhaustive UI, cryptography/wire/handshake/ports/topology evidence.

## Final V1 decision

All 20 original V1 research gates are evidence-backed with version, encryption-role, platform, license and security boundaries explicit. Entry 046 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
