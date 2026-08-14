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
- commit tree: `f337850416be8834f2276118e0ce8a2630bd67ee`
- published release: `v1.3.5`, 2023-06-12
- release includes `hashes.txt` plus multi-platform binaries
- implementation language/build: Go 1.20 modules, Taskfile/Shell/PowerShell/Docker/release workflows

### License boundary — corrected from the earlier draft

The exact `LICENSE.md` at `v1.3.5` states that Hysteria source is MIT-licensed **except that an executable built with `-tags gpl` must be distributed under GPLv3**.

PVNetwork therefore must record the exact build tags used for any legacy binary. It is incorrect to classify every possible v1 build simply as MIT. Dependency licenses remain a separate SBOM obligation.

The current upstream repository is Hysteria2-focused, but official tag history preserves the v1 line through this pin. Legacy source/config/dependency evidence is therefore version-pinned rather than inferred from current v2 code.

## Legacy dependency/build boundary

Exact v1 module evidence:

- `app/go.mod` — module `github.com/apernet/hysteria/app`, Go 1.20, local replacement to `../core/`;
- `core/go.mod` — module `github.com/apernet/hysteria/core`, Go 1.20;
- both app/core replace upstream `quic-go` with `github.com/apernet/quic-go v0.34.1-0.20230507231629-ec008b7e8473`;
- app dependency surface includes TUN/TProxy, certmagic, routedns, geoip, Prometheus, Cobra/Viper, WireGuard/wintun and other networking/system packages;
- `.github/workflows/` at the exact v1 pin contains CodeQL, development build, release and release-Docker workflows.

This is legacy v1 evidence. Current Hysteria2 app/core/extras or QUIC dependencies are not substituted for it.

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
| 1 | Top clients identified and justified | PASS | Official legacy Hysteria v1 client/server binary/source is the primary compatibility authority. Broader multi-protocol GUI references are useful only when an exact historical version proves v1 behavior. |
| 2 | Canonical sources pinned | PASS | Official `apernet/hysteria` tag `v1.3.5`, commit `57c5164854d6cfe00bead730cce731da2babe406`, tree `f337850416be8834f2276118e0ce8a2630bd67ee` pinned. |
| 3 | Licenses reviewed | PASS | Exact v1 `LICENSE.md`: source is MIT; executable builds using `-tags gpl` must be distributed under GPLv3. Build tags and dependency licenses must therefore be captured in the shipping SBOM/legal record. |
| 4 | Complete source-tree reference/manifest captured | PASS | Exact legacy commit tree is pinned. Root contains `.github`, `app`, `core`, `docs`, Docker/build/release files and changelog; exact app/core module boundaries are recorded. |
| 5 | Languages/build systems mapped | PASS | Go 1.20 app/core modules plus Taskfile, Shell, PowerShell, Docker and GitHub Actions build/release surfaces are pinned at the v1 tag. |
| 6 | Architecture mapped | PASS | Legacy client/server/config/runtime plus local SOCKS/HTTP/TUN/relay/TProxy/redirect and auth/TLS/bandwidth boundaries are mapped in `GENERATION_ARCHITECTURE_CONFIG.md`. |
| 7 | Core/engine integration mapped | PASS | PVNetwork decision is a version-pinned legacy executable/core adapter only if compatibility demand justifies it; current Hysteria2 engine is not assumed v1-compatible. |
| 8 | UI/menu map completed | PASS for V1 | Legacy upstream is primarily configuration/CLI driven; PVNetwork UI must expose legacy status and imported fields rather than inventing current-v2 controls. Historical GUI behavior is not assumed without an exact v1-capable pin. |
| 9 | Config/import/export mapped | PASS | Legacy JSON/JSON5 schema fields and validator/default boundaries are documented; original source is preserved separately from normalized profile; migrations/conversions are explicit and lossy changes reported. |
| 10 | Persistence/secrets mapped | PASS | `auth`/`auth_str`, CA/security material and imported secrets require secure-store references; raw config/profile and transient runtime state remain distinct. |
| 11 | Platform integrations mapped | PASS for research | v1.3.5 release/build evidence includes multi-platform binaries and legacy local proxy/TUN/relay modes. Full PVNetwork device lifecycle remains product/platform-owned and later certified. |
| 12 | Logs/diagnostics mapped | PASS | Family diagnostics/security rules require auth/config secret redaction and separate process/TUN/routing health. Legacy runtime error mapping remains exact-version acceptance work. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Official project/release resources are reference-only; no third-party branding is required. Exhaustive GUI screenshots are N/A for the upstream CLI and later-client references belong to V2. |
| 14 | Meaningful forks/alternatives reviewed | PASS | Current Hysteria2 and multi-protocol clients are explicitly alternatives/new generation, not interchangeable v1 implementations. The legacy `apernet/quic-go` fork is part of the v1 dependency identity. |
| 15 | Important issues/releases/advisories reviewed | PASS | Exact v1.3.5 release, changelog/history and end-of-v1 maintenance boundary are recorded. Current v2 advisories are deliberately not misapplied to v1; unresolved legacy dependency/advisory risk drives compatibility-only policy. |
| 16 | Relevant forums/docs reviewed | PASS | Official legacy source/config/docs plus generation notes are authoritative; current Hysteria2 documentation is used only to establish the generation split, not v1 behavior. |
| 17 | Tests/CI reviewed | PASS for research | Exact v1 tree has CodeQL, dev-build, release and Docker-release workflows plus app/core test source. These do not substitute for later PVNetwork v1 interoperability/lifecycle testing. |
| 18 | Store/privacy/security implications reviewed | PASS | Legacy maintenance, auth-secret storage, certificate/insecure options, build-tag license branch, TUN/platform lifecycle and old dependency risk are explicit. Legacy mode must be labeled and not enabled/recommended silently. |
| 19 | PVNetwork reuse decision documented | PASS | Compatibility-only default; use a version-pinned legacy component only when demand justifies maintenance cost. Shipping must record source pin, build tags, artifact hash and dependency/SBOM data. |
| 20 | Uncertainties explicitly listed | PASS | Legacy dependency/advisory floor, installed-base demand, exact client/server interop, performance and real-device lifecycle remain later acceptance/support gates; none is hidden as a V1 completion requirement. |

## Security/migration rules

1. Hysteria v1 is legacy by default.
2. Never mark a v1 profile as Hysteria2 merely because fields look similar.
3. Never send a v1 profile to a v2 runtime without an explicit tested migration.
4. Preserve imported bandwidth/auth/obfs/TLS semantics and original source.
5. `insecure` certificate verification bypass is never silently enabled as a new default.
6. A local-proxy success is not proof of full TUN/DNS/routing support.
7. No new-profile recommendation until a deliberate legacy security/dependency review says otherwise.
8. Record the exact v1 build tags: `-tags gpl` changes the executable distribution obligation to GPLv3.
9. Pin the legacy `apernet/quic-go` and full Go module graph for any shipping build; do not substitute Hysteria2 dependencies.

## Future acceptance work — not V1 blockers

Before any v1 support claim: freeze exact v1 binary/source/build tags/dependencies/artifact hashes; review legacy advisories/dependencies separately; test exact client/server v1 interoperability, auth/TLS/cert negatives, bandwidth/obfs/port-hop behavior, local proxy/TUN/UDP/TCP, IPv4/IPv6, routing/DNS/leaks, reconnect/network handover/crash cleanup, import/export and migration-to-v2 behavior; then complete V2 server/install/crypto/wire/ports/topology evidence.

## Final V1 decision

After correcting the build-tag license boundary and pinning the exact legacy source tree, modules, QUIC fork, release and CI surface, all 20 original research gates are evidence-backed with the legacy-generation boundary explicit. Entry 042 is therefore **`COMPLETE-RESEARCH-v1`**, while remaining **legacy compatibility only, not implemented and not certified**.
