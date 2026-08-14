# 042 — Hysteria (legacy v1) — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **042 — Hysteria**

Decision: **`COMPLETE-RESEARCH-v1 / LEGACY-COMPATIBILITY-TARGET / NOT IMPLEMENTED / NOT CERTIFIED`**

This entry covers the legacy Hysteria v1 generation. It is **not** an alias for entry 043 Hysteria2 and must not inherit Hysteria2 wire/config/security claims.

## Exact legacy source baseline

Official repository: `apernet/hysteria`

Legacy generation pin:

- tag: `v1.3.5`
- commit: `57c5164854d6cfe00bead730cce731da2babe406`
- root project license: MIT

The current upstream repository is Hysteria2-focused, but official tag history preserves the v1 line through this pin. Legacy source/config evidence is therefore version-pinned rather than inferred from current v2 code.

## Legacy configuration/architecture boundary

At v1.3.5, legacy `app/cmd/config.go` exposes a JSON/JSON5-style client model including server/protocol, upload/download bandwidth, retry/handshake/idle/hop timing, SOCKS5/HTTP proxy, TUN, TCP/UDP relay, TProxy/redirect, ACL/MMDB, one `obfs` string, `auth`/`auth_str`, ALPN/server-name/insecure/custom CA, receive windows, MTU discovery, Fast Open, lazy start and resolver controls.

The v1 validator requires a valid local operating mode and validates configured bandwidth. Defaults include ALPN `hysteria` and generation-specific receive-window/hop behavior.

PVNetwork consequence: a legacy profile requires `protocol_generation = v1` and a v1-specific adapter/schema. Do not feed it into the Hysteria2 parser and do not silently translate bandwidth/auth/obfs semantics.

## Product classification

**`LEGACY GENERATION / IMPORT+INTEROP COMPATIBILITY ONLY BY DEFAULT`**

- keep entry 042 because installed profiles/servers may exist;
- do not recommend Hysteria v1 for newly created profiles by default;
- do not use Hysteria2 availability to claim v1 support;
- a separate legacy runtime/version may be required;
- migration to Hysteria2 is explicit and version-aware, never silent.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / legacy-v1 decision |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | Official legacy Hysteria v1 client/server binary/source is the primary compatibility authority. Broader multi-protocol GUI references are useful for import/UX comparison but are not assumed to preserve v1 semantics unless exact versions prove it. |
| 2 | Canonical sources pinned | PASS | Official `apernet/hysteria` tag `v1.3.5` / commit `57c5164854d6cfe00bead730cce731da2babe406` pinned. |
| 3 | Licenses reviewed | PASS | Root upstream license is MIT; exact legacy dependency licenses remain a release/build SBOM gate, not hidden V1 research. |
| 4 | Complete source-tree reference/manifest captured | PASS | Family research records the exact legacy tag/commit and current upstream source-tree methodology; legacy config/source paths are identified separately from v2. |
| 5 | Languages/build systems mapped | PASS | Official implementation is Go with supporting shell/Python/build assets; generation-specific source/build is separated from current v2 modules. |
| 6 | Architecture mapped | PASS | Legacy client/server/config/runtime plus local SOCKS/HTTP/TUN/relay/TProxy/redirect and auth/TLS/bandwidth boundaries are mapped in `GENERATION_ARCHITECTURE_CONFIG.md`. |
| 7 | Core/engine integration mapped | PASS | PVNetwork decision is a version-pinned legacy executable/core adapter only if compatibility demand justifies it; current Hysteria2 engine is not assumed v1-compatible. |
| 8 | UI/menu map completed | PASS for V1 | Legacy upstream is primarily configuration/CLI driven; PVNetwork UI must expose legacy status, imported fields and simple/advanced separation rather than cloning CLI. Multi-client GUI evidence remains reference-only; exhaustive screenshots/fields are V2. |
| 9 | Config/import/export mapped | PASS | Legacy JSON/JSON5 schema fields and validator/default boundaries are documented; original source is preserved separately from normalized profile; migrations/conversions are explicit and lossy changes reported. |
| 10 | Persistence/secrets mapped | PASS | `auth`/`auth_str`, CA/security material and imported secrets require secure-store references; raw config/profile and transient runtime state remain distinct. |
| 11 | Platform integrations mapped | PASS for research | Legacy source supports local proxy/TUN/relay operating modes; full PVNetwork device lifecycle remains product/platform-owned and later certified. Current v2 platform evidence is not reused as v1 certification. |
| 12 | Logs/diagnostics mapped | PASS | Family diagnostics/security rules require auth/config/subscription secret redaction and separate process/TUN/routing health. Legacy runtime error mapping remains exact-version acceptance work. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Official project/client resources are reference-only; no need to copy third-party branding. Exhaustive screen/assets catalog is deferred to V2. |
| 14 | Meaningful forks/alternatives reviewed | PASS | Current Hysteria2 and multi-protocol clients are explicitly treated as alternatives/new generation, not interchangeable v1 implementations. |
| 15 | Important issues/releases/advisories reviewed | PASS | Release history establishes v1 line ending at `v1.3.5` while active releases are v2. Legacy maintenance status is an explicit security risk; current v2 advisories are not falsely applied to v1. |
| 16 | Relevant forums/docs reviewed | PASS | Official legacy source/config plus current project generation notes and shared client ecosystem evidence are incorporated with generation boundaries. |
| 17 | Tests/CI reviewed | PASS for research | Family test strategy and official upstream testing/build surface are documented; exact v1 release tests/CI do not substitute for later PVNetwork interoperability/lifecycle tests. |
| 18 | Store/privacy/security implications reviewed | PASS | Legacy maintenance, auth-secret storage, certificate/insecure options, TUN/platform lifecycle and dependency SBOM risks are explicit. Legacy mode must be labeled and not enabled/recommended silently. |
| 19 | PVNetwork reuse decision documented | PASS | Compatibility-only default; use a version-pinned legacy component only when demand justifies maintenance cost. Do not fork current v2 or assume v2 parser/wire compatibility. |
| 20 | Uncertainties explicitly listed | PASS | Legacy dependency/advisory floor, exact artifact/platform matrix, current installed-base demand, client/server interop, performance and real-device lifecycle remain later acceptance/support gates. |

## Security/migration rules

1. Hysteria v1 is legacy by default.
2. Never mark a v1 profile as Hysteria2 merely because fields look similar.
3. Never send a v1 profile to a v2 runtime without an explicit tested migration.
4. Preserve imported bandwidth/auth/obfs/TLS semantics and original source.
5. `insecure` certificate verification bypass is never silently enabled as a new default.
6. A local-proxy success is not proof of full TUN/DNS/routing support.
7. No new-profile recommendation until a deliberate legacy security/dependency review says otherwise.

## Future acceptance work — not V1 blockers

Before any v1 support claim: freeze exact v1 binary/source/dependencies/artifact hashes; review legacy advisories/dependencies separately; test exact client/server v1 interoperability, auth/TLS/cert negatives, bandwidth/obfs/port-hop behavior, local proxy/TUN/UDP/TCP, IPv4/IPv6, routing/DNS/leaks, reconnect/network handover/crash cleanup, import/export and migration-to-v2 behavior; then complete V2 server/install/crypto/wire/ports/topology evidence.

## Final V1 decision

All 20 original research gates are now evidence-backed with the legacy-generation boundary explicit. Entry 042 is therefore **`COMPLETE-RESEARCH-v1`**, while remaining **legacy compatibility only, not implemented and not certified**.
