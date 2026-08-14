# 075 — XTLS — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **075 — XTLS**

Decision: **`COMPLETE-RESEARCH-v1 / LEGACY XRAY SECURITY-LAYER TERMINOLOGY / NOT A CURRENT STANDALONE VPN PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

This reconciliation reuses the completed Xray-family research and closes the entry-specific 20-gate decision without inventing a current XTLS engine or silently equating legacy XTLS with Vision.

## Existing evidence reused

- `research/protocols/075-xtls/README.md`
- `research/upstreams/xray-family/INDEX.md`
- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/DEPENDENCIES_TESTS_RELEASES.md`
- `research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `research/upstreams/xray-family/ISSUE_RELEASE_LESSONS.md`
- `research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- completed entry 074 REALITY

## Exact current-source evidence

Primary current implementation/reference:

- repository: `XTLS/Xray-core`
- research commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- pinned tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- recursive source manifest:
  `https://api.github.com/repos/XTLS/Xray-core/git/trees/46ee908a9a67513d3c85bbf998be5d553a078109?recursive=1`
- root license: **MPL-2.0**
- language/build: Go module, `go 1.26` at the research pin.

At that exact pin, `infra/conf/transport_internet.go` parses the stream security axis as:

- empty/`none`;
- `tls`;
- `reality`;
- `xtls` -> **removed-feature error** directing users to `xtls-rprx-vision with TLS or REALITY`.

Therefore the current source itself proves that legacy generic `security: "xtls"` is **not** an active current security-layer option. The source separately permits current TLS and REALITY security and treats Vision as a flow/mode in the applicable protocol path.

Current path history for `infra/conf/transport_internet.go` was also reviewed; recent 2026 changes continue to modify current transport/config semantics, reinforcing that migration and capability validation must be tied to an exact core version rather than historical terminology.

## Product classification

PVNetwork must model three separate concepts:

1. **legacy XTLS metadata/configuration** — retained for import provenance, diagnosis and migration;
2. **current security layer** — for example TLS or REALITY when supported by the selected core/version;
3. **current flow/mode** — for example `xtls-rprx-vision` where supported, covered independently by entry 076.

A historical profile containing `security=xtls` must not be silently normalized into `security=tls`, `security=reality`, or `flow=xtls-rprx-vision`. Any migration requires explicit, version-aware semantic validation.

## 20-gate reconciliation

| # | v1 completion gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations identified | PASS | Xray-core is the primary current/historical implementation authority for this XTLS-family terminology. Shared client evidence maps v2rayNG, v2rayN and other Xray-family clients as migration/import/UX references while preserving their separate licenses. No separate current standalone XTLS engine is invented. |
| 2 | Canonical sources pinned | PASS | Exact Xray-core commit/tree/recursive manifest are pinned. Current source at that pin explicitly rejects legacy `security: xtls`. Historical semantics are preserved as legacy provenance; a historical release archaeology campaign is not required to decide current V1 support classification and remains available for v2 reference expansion. |
| 3 | Licenses reviewed | PASS | Xray-core is MPL-2.0; libXray and GUI/client licenses are independently mapped in shared evidence. PVNetwork may evaluate Xray as a component subject to MPL file-level obligations, exact dependency/SBOM review and legal sign-off; GPL/custom GUI code remains reference-only by default for a closed product. |
| 4 | Complete source-tree reference / manifest captured | PASS | The full recursive Xray source tree is pinned by tree SHA/API manifest; current config/parser, proxy, transport, tests, build and release areas are covered by shared source architecture. No fictitious separate XTLS repository/tree is claimed. |
| 5 | Languages / build systems mapped | PASS | Xray-core is Go/Go modules at the pin; build, dependency, test and release workflow surfaces are mapped in the shared Xray dossiers. Client/wrapper technology stacks are separately recorded where relevant. |
| 6 | Internal architecture / data flow mapped | PASS | Canonical profile -> version-aware Xray adapter -> stream security/flow/transport config -> Xray runtime. Legacy XTLS security metadata is distinguished from TLS/REALITY security and Vision flow; no current standalone XTLS data plane is modeled. |
| 7 | Core / engine integration mapped | PASS | PVNetwork integrates through the approved Xray process/wrapper boundary and must reject or explicitly migrate unsupported legacy XTLS config. It must not reimplement an obsolete security mode merely to preserve a protocol-count label. |
| 8 | UI / menu map completed | PASS for v1 | Shared v2rayNG/client UI evidence covers profile editors/import/settings/logs. PVNetwork decision: do **not** expose a current generic “XTLS” security toggle; legacy imported XTLS state may appear as legacy/unsupported/migration metadata, while current TLS/REALITY and Vision controls are shown only when valid. Exhaustive historical screenshots are v2 work. |
| 9 | Config / import / export / URI / QR mapped | PASS | Shared config evidence separates original imported source, canonical profile and generated Xray config. Legacy `security=xtls` must be preserved losslessly enough for provenance and explicit migration. No standalone `xtls://` standard is invented; QR is only an encoding of an underlying profile/link format. |
| 10 | Persistence / secrets mapped | PASS | XTLS terminology introduces no independent credential class. Existing protocol/TLS/REALITY credentials retain their own secure-storage ownership. Original legacy source and migration metadata are stored separately from reusable secrets and transient generated runtime config. |
| 11 | Platform-specific implementation mapped | PASS for research | Xray/libXray/client platform boundaries are already mapped. Whether a historical XTLS profile can run is determined by the selected client/server core versions and platform integration; current cross-platform source availability is not evidence that the removed legacy mode remains supported. |
| 12 | Logs / diagnostics / failure mapping | PASS | Product diagnostics must distinguish “legacy XTLS security removed/unsupported”, migration validation, current TLS/REALITY failure, Vision-flow compatibility, transport failure and general connectivity. Generated configs and credentials remain redacted under shared logging policy. |
| 13 | Assets / screenshots / localization mapped | PASS for v1 | XTLS has no independent canonical consumer app/store asset set. Shared client UI/localization assets are reference-only according to their licenses. Historical/current field-level screenshots belong to the later 16-gate reference phase. |
| 14 | Meaningful forks / alternatives / variants reviewed | PASS | Current alternatives are TLS and REALITY on the security axis and Vision on the flow axis. REALITY is independently completed as entry 074; Vision remains separate entry 076. GUI clients/forks do not redefine core XTLS semantics. |
| 15 | Important issues / PRs / releases / advisories reviewed | PASS | Current exact source provides the strongest compatibility result: legacy `xtls` returns a removed-feature error and directs users to Vision with TLS/REALITY. Shared Xray evidence records main-vs-stable drift, configuration/default regressions and GHSA-5wf9-h793-w73c, proving exact-version migration/release review is necessary. Recent 2026 config-path changes further show that source semantics are actively evolving. |
| 16 | Official docs / forums reviewed | PASS | Pinned Xray source/config guidance and upstream-maintained repository documentation are primary. Public issue/forum claims are treated as secondary and are not converted into protocol/security facts without source or accepted upstream evidence. |
| 17 | Tests / CI / quality evidence reviewed | PASS | Xray upstream CI/test surface is mapped (`go test ./...` across Windows/Ubuntu/macOS plus config/scenario tests). PVNetwork must add legacy-import/migration regression tests when implementing, but runtime/device interoperability is not a hidden V1 research gate. |
| 18 | Store / privacy / security implications reviewed | PASS | Product must not advertise removed legacy XTLS as a current security feature or silently weaken/alter a legacy imported profile. Credential/log/privacy rules are inherited from the actual active protocol/security layers. Store feasibility is platform/build-specific and later. |
| 19 | PVNetwork reuse / rewrite / hybrid decision documented | PASS | **LEGACY TERMINOLOGY / MIGRATION-REFERENCE ONLY**. Do not build or market a standalone current XTLS engine. Preserve provenance, validate against exact core version, and expose only currently supported TLS/REALITY + flow combinations through the Xray adapter. |
| 20 | Open uncertainties / blockers listed | PASS | Exact historical version chronology, historical handshakes/crypto behavior, legacy client/server migration matrices, screenshots, wire references and field-by-field version changes remain legitimate `COMPLETE-REFERENCE-v2` work. They do not block the V1 conclusion that current pinned Xray removes generic XTLS security. |

## Final V1 decision

Every applicable V1 gate is evidence-backed or correctly bounded. The current canonical source explicitly rejects legacy generic XTLS security, so maintaining entry 075 as `EVIDENCE-GAPS` would incorrectly make historical archaeology or runtime testing a hidden V1 prerequisite.

Entry 075 therefore qualifies for **`COMPLETE-RESEARCH-v1`** while remaining **not implemented / not migration-certified / not runtime-certified**.
