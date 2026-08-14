# 038 — VMess — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **038 — VMess**

Decision: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT CERTIFIED`**

This file reconciles VMess against all 20 original gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`. Shared Xray-family evidence is reused only where it genuinely applies; the protocol-specific support/migration decision remains independent from VLESS.

## Primary evidence

- Xray core: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- pinned Xray tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- root license: MPL-2.0
- shared core evidence: `research/upstreams/xray-family/`
- wrapper candidate: `XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a` (MIT wrapper; Xray/MPL/dependency obligations remain separate)
- primary application references: v2rayN, v2rayNG and other current Xray-capable clients documented by `CLIENT_ECOSYSTEM.md` and `research/upstreams/client-references/`

Pinned Xray source contains dedicated `proxy/vmess/` implementation areas and protocol-specific configuration/test coverage. Transport, security, routing/DNS and runtime services are separate source/config dimensions and must remain separate in PVNetwork's canonical model.

## Product classification and reuse decision

VMess is an **application proxy protocol** in the V2Ray/Xray ecosystem.

PVNetwork classification:

**`COMPATIBILITY TARGET / MATURE ECOSYSTEM / LOWER STRATEGIC PRIORITY THAN VLESS`**

VMess remains important for installed-base/import compatibility. It must not dominate the new architecture and must never be silently rewritten to VLESS merely because VLESS is strategically preferred.

Required semantics to preserve include the imported VMess identity/auth/security/profile fields, effective transport/security settings, version/default behavior, and exact server/core compatibility assumptions.

## 20-gate reconciliation

| # | v1 completion gate | Result | Evidence / protocol-specific conclusion |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | `CLIENT_ECOSYSTEM.md` provides tiered v2rayN/v2rayNG/Hiddify/Karing/NekoBox/Throne/Happ references and explains each role. v2rayN/v2rayNG are especially relevant to VMess installed-base/import behavior. |
| 2 | Canonical sources pinned | PASS | Xray-core and libXray exact pins/tree are recorded; major client pins are recorded in shared/client dossiers. |
| 3 | Licenses reviewed | PASS | Xray-core MPL-2.0; libXray MIT; GUI applications mostly GPL-family/custom and reference-only by default for a closed commercial product. |
| 4 | Complete source-tree reference/manifest captured | PASS | Xray pinned recursive tree is recorded in `SOURCE_ARCHITECTURE.md`; client tree manifests exist for primary detailed references such as v2rayNG. |
| 5 | Languages/build systems mapped | PASS | Xray Go/Go modules plus wrapper/client build/language boundaries are documented. |
| 6 | Architecture mapped | PASS | Core/app/proxy/transport/infra-conf/runtime boundaries and client/service/core separation are documented. |
| 7 | Core/engine integration mapped | PASS | Managed-process vs libXray wrapper options, config handoff, lifecycle and platform networking ownership are mapped. |
| 8 | UI/menu map completed | PASS for v1 | v2rayNG v1 menu map includes dedicated VMess editor plus import/subscription/routing/settings/log/backup/update surfaces; other client references provide comparative UX architecture. Exhaustive field/screenshot coverage remains v2. |
| 9 | Config/import/export mapped | PASS | Shared capability model plus v2rayNG storage/import evidence distinguish protocol, transport, security, endpoint link vs full config, QR/clipboard/file/manual import and separate share/full-config export. VMess migration must preserve source metadata and surface lossy conversion. |
| 10 | Persistence/secrets mapped | PASS | v2rayNG MMKV logical stores/profile schema/raw source/subscription storage and sensitive-field concerns are documented. PVNetwork requires secure-store references for reusable secrets. |
| 11 | Platform integrations mapped | PASS for research | Xray cross-platform core artifacts, wrapper paths and detailed Android VpnService/daemon/TV evidence are mapped; portability is not certification. |
| 12 | Logs/diagnostics mapped | PASS | Xray control/stats/log surfaces and client log/config-secret risks are documented with redaction/control isolation requirements. |
| 13 | Asset/screenshot references mapped | PASS for v1 | Upstream/client asset/localization/resource locations and copyright/reference-only policy are recorded; exhaustive screenshots are deferred to mandatory v2. |
| 14 | Meaningful forks reviewed | PASS | Xray-native wrapper and major multi-core/GUI alternatives are compared by architecture, maintenance and license role rather than treated as interchangeable source. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | Main-vs-stable drift, routing/DNS regressions, transport/default drift, advisory `GHSA-5wf9-h793-w73c`, and client/server version-combination lessons are documented. |
| 16 | Relevant forums/docs reviewed | PASS | Upstream docs/README/config guidance and issue discussions are incorporated with explicit evidence-quality rules. |
| 17 | Tests/CI reviewed | PASS | Xray cross-platform `go test ./...`, config/scenario test structure, CI/release surface and v2rayNG build/CI evidence are mapped. |
| 18 | Store/privacy/security implications reviewed | PASS | Platform lifecycle/permissions, Apple/Android Store caution, dependency/SBOM/advisory state, secret handling and GUI-license boundaries are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | VMess is a compatibility target; Xray is a strong engine candidate; libXray is a wrapper candidate; GPL/custom GUIs are reference-only by default. |
| 20 | Uncertainties explicitly listed | PASS | Exact production pin/SBOM, VMess server/core-version matrix, effective legacy/default semantics, real-device lifecycle, performance, interoperability and v2 server/wire/crypto/topology work remain explicit later gates. |

## VMess-specific canonical-profile rules

1. Keep VMess as its own typed application protocol.
2. Keep transport and security as independent typed axes rather than embedding them in an opaque URI.
3. Preserve the original imported share link/full config when safe so legacy/unknown fields can be diagnosed or re-exported.
4. Distinguish explicit values from core defaults/unspecified values; core-version drift can change effective behavior.
5. Mark lossy conversions explicitly.
6. Never auto-migrate VMess to VLESS. Migration must be an explicit user/operator action with a known target configuration and compatibility test.
7. Server/client core-version compatibility is certification evidence, not inferred from parser acceptance.

## Security/release requirement that survives handoff

The Xray family advisory audit records `GHSA-5wf9-h793-w73c` and shows that the observed non-prerelease `v26.3.27` is not a safe automatic production choice. Research on current `main` is useful but does not approve `main` for production either. A production VMess engine must use an exact advisory-aware release/commit with dependency/SBOM/license/regression review.

## Future acceptance/certification work — not v1 blockers

Before any support claim:

- select an exact patched Xray release/build;
- generate exact per-platform SBOM/license/vulnerability evidence;
- certify imported/current VMess field semantics and defaults;
- test share-link/full-config round trips and unknown/legacy fields;
- test exact transport/security combinations actually advertised;
- build a client/server core-version interoperability matrix;
- test routing/DNS/TUN/IPv4/IPv6/UDP behavior where applicable;
- test network changes, reconnect, crash and cleanup;
- test platform/Store lifecycle;
- benchmark actual supported combinations;
- complete the later COMPLETE-REFERENCE-v2 server/install/crypto/data-flow/ports/topology work.

## Final v1 decision

All 20 original research gates are evidence-backed or explicitly and correctly deferred to later implementation/reference-v2/certification phases. Entry 038 can be promoted to:

**`COMPLETE-RESEARCH-v1`**

while remaining:

**`NOT IMPLEMENTED / NOT CERTIFIED`**.
