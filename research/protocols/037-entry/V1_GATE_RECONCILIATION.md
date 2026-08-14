# 037 — VLESS — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **037 — VLESS**

Decision: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT CERTIFIED`**

This file reconciles entry 037 against the 20 completion gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`. It is a research-completion statement only. It does not claim that PVNetwork implements VLESS or that any VLESS + flow + security + transport combination has passed runtime, real-device, interoperability, Store, or production certification.

## Primary evidence baseline

### Xray core

- canonical repository: `XTLS/Xray-core`
- research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- pinned tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- root license: MPL-2.0
- shared dossier: `research/upstreams/xray-family/`

The pinned source has dedicated VLESS modules under `proxy/vless/inbound/` and `proxy/vless/outbound/`, VLESS configuration construction under `infra/conf/vless.go`, VLESS configuration tests, and scenario-style VLESS tests. The source architecture also separates application protocol, transport, security layer, routing/DNS/policy, and runtime control.

### Narrow wrapper candidate

- repository: `XTLS/libXray`
- research pin: `d0ab60ae4dd91cf119c878152d12103e6f84b78a`
- wrapper root license: MIT
- classification: `STRONG-WRAPPER-CANDIDATE / Xray-MPL+DEPENDENCY+LIFECYCLE REVIEW REQUIRED`

The wrapper license does not replace or relax Xray-core MPL/dependency obligations.

### Primary client/UX references

Shared client evidence covers v2rayN, v2rayNG, Hiddify, Karing, NekoBox, Throne, Happ and selected adjacent multi-core clients. Major GUI applications are reference-only by default for a closed commercial PVNetwork product because their application licenses/terms are separate from Xray-core and are commonly GPL-family or custom-restrictive.

Important source-backed Android reference files include:

- `research/upstreams/client-references/V2RAYNG_ANDROID_ARCHITECTURE.md`
- `research/upstreams/client-references/V2RAYNG_STORAGE_IMPORT.md`
- `research/upstreams/client-references/V2RAYNG_CLIENT_UI_AND_MENUS_V1.md`
- `research/upstreams/client-references/V2RAYNG_BUILD_CI.md`

## Protocol/product classification

VLESS is an **application proxy protocol in the Xray/V2Ray ecosystem**. It is not itself a transport or a security layer.

PVNetwork must treat a usable/certifiable VLESS configuration as a typed combination of at least:

- VLESS identity/authentication fields;
- `flow` where supported;
- security layer such as TLS/REALITY/none according to the selected core and combination;
- outer transport such as RAW/XHTTP/WebSocket/gRPC/mKCP where the selected version supports it;
- routing/DNS policy;
- IPv4/IPv6 behavior;
- UDP behavior where applicable;
- exact client/server core versions.

Do not expose an unrestricted Cartesian product of protocol/security/flow/transport options. Combination validation must be core-version-aware.

## 20-gate reconciliation

| # | v1 completion gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | `xray-family/CLIENT_ECOSYSTEM.md` provides tiered v2rayN/v2rayNG/Hiddify/Karing/NekoBox/Throne/Happ and adjacent references with distinct architecture/UX roles. |
| 2 | Canonical sources pinned | PASS | Xray-core pin/tree and libXray pin are recorded; primary client pins are recorded in shared/client dossiers. |
| 3 | Licenses reviewed | PASS | Xray-core MPL-2.0; libXray MIT; v2rayN/v2rayNG GPLv3; Hiddify extended GPL terms; Karing/NekoBox/Throne GPL-family; Happ source/license uncertainty retained explicitly. |
| 4 | Complete source-tree reference/manifest captured | PASS | `SOURCE_ARCHITECTURE.md` records pinned Xray tree and recursive GitHub tree API; v2rayNG architecture dossier records its pinned tree; client dossiers reference their source boundaries. |
| 5 | Languages/build systems mapped | PASS | Xray-core is Go/Go modules; wrapper/native boundary and client-specific Kotlin/Compose, Avalonia/.NET, Flutter/Dart, C++ etc. are mapped in shared/client evidence. |
| 6 | Architecture mapped | PASS | `SOURCE_ARCHITECTURE.md`, `LIBXRAY_WRAPPER.md`, `LIBXRAY_API_LIFECYCLE.md`, and v2rayNG architecture map core/app/proxy/transport/config/runtime/client/service boundaries. |
| 7 | Core/engine integration mapped | PASS | Xray process-vs-wrapper options, libXray lifecycle, Android native/core daemon model, config handoff and product-owned adapter boundary are documented. |
| 8 | UI/menu map completed | PASS for v1 | v2rayNG v1 menu dossier maps main shell, imports, VLESS editor, subscriptions, routing, per-app, settings, logs, assets, backup, update/about, shortcuts and TV indicators. Other clients remain reference comparisons; exhaustive multi-client field/screenshot work is correctly deferred to v2. |
| 9 | Config/import/export mapped | PASS | `CONFIG_CAPABILITY_MODEL.md` separates protocol/transport/security/flow and raw/runtime config; v2rayNG storage/import dossier maps QR/clipboard/file/manual import, VLESS editor, raw-source preservation and share-link vs full-config export. |
| 10 | Persistence/secrets mapped | PASS | v2rayNG MMKV stores, profile/subscription/raw stores, multi-process concerns and lack of explicit MMKV cryptKey in reviewed initialization are documented; PVNetwork requires secure-store references for reusable secrets/keys. |
| 11 | Platform integrations mapped | PASS for research | Xray cross-platform build surface, libXray Android/Apple/Linux/Windows paths, Android VpnService/daemon lifecycle and TV indicators are documented. Source portability is explicitly not Store/runtime certification. |
| 12 | Logs/diagnostics mapped | PASS | Xray API/control/stats/log boundaries and v2rayNG Logcat/generated-config sensitivity are documented; PVNetwork requires redaction and privileged control isolation. |
| 13 | Asset/screenshot references mapped | PASS for v1 | Shared/client dossiers identify upstream resources/localization/UI assets and preserve the rule to reference rather than copy third-party assets; exhaustive screenshot-by-screen catalog is explicitly a v2 task. |
| 14 | Meaningful forks reviewed | PASS | Client ecosystem deliberately distinguishes native Xray core/wrapper from major GUI/multi-core implementations/forks and adjacent clients, recording why each is architecture/UX/reference-only or candidate. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | `ISSUE_RELEASE_LESSONS.md` records current main-vs-stable drift, VLESS/flow/transport combination lessons, XHTTP default-drift class, routing/DNS regressions; `SECURITY_AND_DEPENDENCY_ADVISORIES.md` records GHSA-5wf9-h793-w73c and patched-version implications. |
| 16 | Relevant forums/docs reviewed | PASS | Upstream README/docs/config guidance plus issue discussions are incorporated with a rule not to turn unverified public claims into security facts. |
| 17 | Tests/CI reviewed | PASS | `DEPENDENCIES_TESTS_RELEASES.md` maps upstream cross-platform GitHub Actions, `go test ./...`, VLESS config/scenario tests and distinguishes upstream tests from PVNetwork product integration tests; v2rayNG CI evidence is separately recorded. |
| 18 | Store/privacy/security implications reviewed | PASS | Android permission/VpnService/background/TV implications, Apple wrapper-vs-Store caution, GPL/custom GUI reuse boundaries, secret-storage/redaction requirements, dependency SBOM/advisory requirements and management API exposure are documented. Current Store rules must be rechecked before release. |
| 19 | PVNetwork reuse decision documented | PASS | `SUPPORT_REUSE_DECISIONS.md` classifies VLESS as `HIGH-PRIORITY XRAY-NATIVE TARGET / EXACT COMBINATION CERTIFICATION REQUIRED`; Xray-core is a strong core candidate, libXray a strong wrapper candidate, and GPL GUI apps reference-only by default. |
| 20 | Uncertainties explicitly listed | PASS | Exact patched production core pin, resolved SBOM/license/vulnerability scan, release-vs-main regression, wrapper/core mapping, real-device/TV/platform lifecycle, performance and exact combination interoperability remain explicit downstream gates. |

## Source/release security finding that must survive handoff

The Xray family index records advisory `GHSA-5wf9-h793-w73c`, published 2026-07-10. The observed GitHub non-prerelease `v26.3.27` lies inside the recorded vulnerable range, while the advisory records patched versions at/after `v26.7.11`. Therefore **do not select v26.3.27 merely because an API labels it latest/stable**. Production candidate selection must be advisory-aware and accompanied by exact SBOM/dependency/license/regression evidence.

The current research pin on `main` is newer than that stable release and is useful for source research, but **main is not automatically a production-approved release**.

## Canonical PVNetwork architecture decision

Use this boundary:

`PVNetwork UI / profile / subscription`

`-> canonical typed PVProfile + routing/DNS policy`

`-> version-aware Xray Core Adapter`

`-> generated transient Xray runtime config`

`-> Xray process or approved wrapper`

`-> platform network integration`

Keep these artifacts distinct:

1. original imported source;
2. normalized canonical profile;
3. secure credential references;
4. user routing/DNS policy;
5. generated engine config;
6. transient session state;
7. sanitized logs/statistics.

Raw Xray JSON is not the authoritative PVNetwork user database.

## Reuse / legal decision

- **Xray-core:** strong reusable-core candidate subject to MPL file-level obligations, exact dependency/license/SBOM review and final legal sign-off.
- **libXray:** strong wrapper candidate subject to wrapper/core dependency and lifecycle review.
- **v2rayN/v2rayNG/Hiddify/Karing/NekoBox/Throne and similar GUIs:** architecture/UX/bug reference only by default for a closed commercial PVNetwork product unless a deliberately compatible distribution/license model or separate rights are chosen.
- **Happ:** product/UX reference only until canonical complete source/license provenance is established.

Do not copy third-party branding/assets/UI merely because source is visible.

## Required future acceptance/certification work — not v1 blockers

Before any VLESS support claim:

- select an exact patched Xray release/core pin;
- generate per-platform SBOM/license/vulnerability evidence;
- certify each advertised VLESS + flow + security + transport combination;
- test client/server core-version mismatch and upgrade/rollback;
- test routing/DNS/TUN/IPv4/IPv6/UDP behavior as applicable;
- test network handover/reconnect/crash/cleanup;
- test Android Always-On/boot/update/background and Android TV behavior if supported;
- test Apple/desktop lifecycle and Store/distribution model as applicable;
- test import/export round trips and lossy/full-config cases;
- test secret redaction and support bundle behavior;
- performance/resource benchmark actual selected combinations;
- complete later `COMPLETE-REFERENCE-v2` server/install/crypto/wire-flow/ports/topology layer.

These are implementation/reference-v2/certification gates, not hidden omissions in the original research campaign.

## Final v1 decision

All 20 original research gates are now evidence-backed or explicitly scoped according to the v1 contract. Entry 037 can therefore be promoted from `EVIDENCE-GAPS` to:

**`COMPLETE-RESEARCH-v1`**

with implementation/support status remaining:

**`NOT IMPLEMENTED / NOT CERTIFIED`**.
