# 074 — REALITY — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **074 — REALITY**

Decision: **`COMPLETE-RESEARCH-v1 / SECURITY-LAYER CAPABILITY / XRAY PRIMARY REFERENCE / NOT A STANDALONE VPN PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

This reconciliation uses the substantial existing Xray-family research and closes only the remaining REALITY-specific source/dependency and 20-gate evidence gaps.

## Existing repository evidence reused

- `research/protocols/074-reality/README.md`
- `research/upstreams/xray-family/INDEX.md`
- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/DEPENDENCIES_TESTS_RELEASES.md`
- `research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `research/upstreams/xray-family/ISSUE_RELEASE_LESSONS.md`
- `research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- `research/upstreams/client-references/V2RAYNG_CLIENT_UI_AND_MENUS_V1.md`

## Exact source/dependency pins

### Xray-core

- canonical repository: `XTLS/Xray-core`
- research commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- pinned tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- recursive manifest:
  `https://api.github.com/repos/XTLS/Xray-core/git/trees/46ee908a9a67513d3c85bbf998be5d553a078109?recursive=1`
- root license: **MPL-2.0**
- language/build: Go module, `go 1.26`
- REALITY integration path at this pin:
  `transport/internet/reality/` containing `config.go`, `config.proto`, generated protobuf config and `reality.go`.

### Direct REALITY dependency selected by that Xray pin

Pinned `Xray-core/go.mod` requires:

`github.com/xtls/reality v0.0.0-20260322125925-9234c772ba8f`

Canonical repository/pin:

- repository: `XTLS/REALITY`
- exact commit: `9234c772ba8f181f31c3e81dc2b4177322e5a9a9`
- tree: `c95ac5e1b520e4aa937060a92c533de683cc32f0`
- recursive manifest:
  `https://api.github.com/repos/XTLS/REALITY/git/trees/c95ac5e1b520e4aa937060a92c533de683cc32f0?recursive=1`
- default branch: `main`
- repository language: Go
- root license: **MPL-2.0**
- dependency `go.mod`: Go 1.24.0 and pinned CIRCL/uTLS/x-crypto/support dependencies.
- tag review: the repository returned no tags during this review; therefore the exact pseudo-version/commit above is the correct source pin rather than an invented release tag.
- the selected dependency pin is also the current first commit returned for `main` during this review.

The standalone dependency README describes itself as the server-side REALITY implementation derived from Go's TLS package and points to Xray-core's `transport/internet/reality/reality.go` for client-side integration. Its broad anti-detection/security statements are **upstream design claims**, not independent PVNetwork security certification.

## Product / config classification

REALITY is modeled as the **security axis** of a connection, separate from:

- application protocol such as VLESS;
- `flow` such as Vision;
- outer transport such as RAW/XHTTP/gRPC/etc.;
- routing/DNS/policy;
- core and server versions.

The pinned Xray configuration model recognizes `none`, `tls` and `reality` as security choices and treats legacy generic `xtls` security mode as removed. PVNetwork therefore must not market REALITY, XTLS and Vision as three equivalent standalone VPN protocols.

Representative source-backed REALITY fields include server-side target/server-name set/private key/client-version/time/short-ID limits and optional post-quantum signing seed, and client-side fingerprint/serverName/server public-key value/shortId/optional post-quantum verification material. Exact allowed combinations remain core-version-aware.

## 20-gate reconciliation

| # | v1 completion gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations identified | PASS | Xray-core is the primary current engine/reference. `CLIENT_ECOSYSTEM.md` maps v2rayNG, v2rayN and other Xray-family clients by platform/role and keeps their application licenses separate. `XTLS/REALITY` is the direct source dependency, not a GUI/client replacement. |
| 2 | Canonical sources pinned | PASS | Xray-core exact commit/tree and direct `XTLS/REALITY@9234c772...` pseudo-version/commit/tree are pinned. REALITY repository has no tag in the reviewed tag feed, so no release tag is fabricated. |
| 3 | Licenses reviewed | PASS | Xray-core and `XTLS/REALITY` are MPL-2.0; libXray wrapper candidate is MIT; major GUI references are GPL-family/custom and reference-only by default for a closed product. MPL file-level covered-source/notice obligations, dependency/SBOM and final legal review remain explicit. |
| 4 | Complete source-tree reference captured | PASS | Full recursive manifest URLs are recorded for both the pinned Xray-core tree and the exact direct REALITY dependency tree. Relevant Xray integration path and complete small dependency tree boundaries are traceable. |
| 5 | Languages / build systems mapped | PASS | Xray-core is Go (`go 1.26` at pin); direct REALITY dependency is Go (`go 1.24.0` at its pin) with exact module dependencies. Xray build/test/release workflows and wrapper/client technology boundaries are already mapped in shared evidence. |
| 6 | Architecture mapped | PASS | Product -> canonical profile -> version-aware Xray adapter -> generated runtime config -> Xray transport/security layer. REALITY's client integration is in Xray `transport/internet/reality/`; server/security implementation is supplied by the pinned `XTLS/REALITY` dependency. Protocol, security, flow and transport are distinct axes. |
| 7 | Core/engine integration mapped | PASS | Xray process or approved narrow wrapper remains the engine boundary. PVNetwork does not reimplement REALITY cryptography merely to expose the feature; it generates validated Xray config through its adapter and pins exact client/server core versions for later certification. |
| 8 | UI / menu map completed | PASS for v1 | Xray client ecosystem and v2rayNG v1 menus cover protocol editors, import/share, subscriptions, routing, settings/logs and core-oriented UI. PVNetwork decision is to expose REALITY as an advanced **Security** choice only when the selected protocol/transport/flow/core combination supports it; exhaustive screen/field screenshots are correctly v2 work. |
| 9 | Config / import / export / URI / QR mapped | PASS | `CONFIG_CAPABILITY_MODEL.md` separates protocol/security/flow/transport and original import from canonical profile/runtime config. REALITY fields are carried inside supported protocol links/full configs; no standalone `reality://` standard is invented. QR is an encoding of an underlying supported profile/link, not a separate REALITY protocol format. Lossy import must be surfaced. |
| 10 | Persistence / secrets mapped | PASS | Server REALITY `privateKey` and optional private signing seed are secrets; they never belong in ordinary client export/logs. Upstream's client `password` field is documented as the server public key, while `shortId` is a client discriminator and must not be mislabeled as an equivalent private key. PVNetwork stores reusable secrets through platform secure-storage references and keeps generated runtime config transient. |
| 11 | Platform integrations mapped | PASS for research | Xray cross-platform artifacts, libXray Android/Apple/Linux/Windows integration and v2rayNG Android VpnService/core lifecycle are mapped. REALITY support still requires exact core/platform combination certification later; cross-compile availability is not Store certification. |
| 12 | Logs / diagnostics mapped | PASS | Shared Xray logs/API/control boundaries plus REALITY debug/config sensitivity are mapped. Errors must distinguish config validation, key/serverName/shortId/version/time/handshake/target/transport/core failures; generated configs/private keys and management state are redacted. |
| 13 | Assets / screenshot references mapped | PASS for v1 | Shared client dossiers identify UI/resources/localization and explicitly keep third-party GUI assets reference-only. REALITY has no independent canonical application/store asset set. Exhaustive screenshots are reserved for the 16-gate v2 reference phase. |
| 14 | Meaningful forks / alternatives reviewed | PASS | Xray-core + direct `XTLS/REALITY` are the selected primary implementation path. TLS is a distinct security alternative; legacy XTLS security terminology and Vision flow are separately classified in entries 075/076. No unrelated client fork is promoted as a separate REALITY core without evidence. |
| 15 | Issues / releases / advisories reviewed | PASS | Exact direct dependency history includes `ad4fbafc...` (2026-03-21) fixing record-detection use of configured network type rather than hardcoded TCP, and selected pin `9234c772...` (2026-03-22) adding bounded detection for repeated non-advancing/ChangeCipherSpec-style records. Shared Xray evidence also records main-vs-stable drift, combination regressions and GHSA-5wf9-h793-w73c release-selection implications. |
| 16 | Relevant official docs / discussions reviewed | PASS | Pinned Xray source/config guidance, direct REALITY README/source and upstream issue/fix references are used. The repository explicitly preserves the rule that public detectability/security claims are not promoted to verified security facts without stronger evidence. |
| 17 | Tests / CI reviewed | PASS | Xray-core CI runs formatting/generated-data checks and `go test ./...` across Windows/Ubuntu/macOS and contains transport/config/scenario tests. The small direct REALITY tree does not expose a separate tagged release/test matrix in the pinned manifest; no fake CI receipt is created. Product combination/device/server tests remain later certification evidence. |
| 18 | Store / privacy / security implications reviewed | PASS | Server private keys/signing seed, imported profiles, generated config, logs and management API are security/privacy-sensitive. Upstream anti-detection/security claims remain claims rather than PVNetwork certification. Unsafe TLS/certificate overrides and management listeners are already covered by shared security policy; Store feasibility is platform/build-specific and later. |
| 19 | PVNetwork reuse decision documented | PASS | `SECURITY-LAYER CAPABILITY / XRAY PRIMARY REFERENCE`. Xray-core and direct REALITY dependency are open-source MPL candidates subject to exact MPL/dependency/SBOM/legal obligations; narrow wrappers may be evaluated per platform; GPL/custom full GUI clients remain reference-only by default. |
| 20 | Uncertainties explicitly listed | PASS | Exact patched production Xray pin, full resolved SBOM/license/vulnerability scan, server/client version matrix, cryptographic/security design verification, wire/handshake reference, target selection, real-device/Store behavior, performance and exact protocol+flow+security+transport interoperability are preserved as downstream/V2/certification work rather than hidden V1 blockers. |

## Security-claim discipline

The upstream REALITY README makes strong statements about detectability, forward secrecy and security relative to conventional TLS. For this V1 completion they are retained only as **upstream design claims**. PVNetwork must not convert them into verified marketing/security claims without the later cryptographic design, wire-flow, independent evidence and exact-version certification required by the reference/certification phases.

## Final V1 decision

Every applicable gate in `research/PROTOCOL_RESEARCH_TEMPLATE.md` now has traceable evidence or a correctly bounded v1/deferred result. Runtime/device/server/Store/interoperability work is not treated as a hidden research requirement.

Entry 074 therefore qualifies for **`COMPLETE-RESEARCH-v1`** while remaining **not implemented / not runtime-certified / not security-certified**.
