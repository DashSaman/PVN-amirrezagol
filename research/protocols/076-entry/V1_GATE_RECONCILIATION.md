# 076 — XTLS Vision — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **076 — XTLS Vision**

Decision: **`COMPLETE-RESEARCH-v1 / CURRENT VLESS FLOW-MODE CAPABILITY / NOT A STANDALONE VPN PROTOCOL / NOT A SECURITY LAYER / NOT IMPLEMENTED / NOT CERTIFIED`**

This reconciliation reuses the completed Xray-family research and closes only Vision-specific current-source/configuration gaps.

## Existing evidence reused

- `research/protocols/076-xtls-vision/README.md`
- `research/upstreams/xray-family/INDEX.md`
- `research/upstreams/xray-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/xray-family/CONFIG_CAPABILITY_MODEL.md`
- `research/upstreams/xray-family/DEPENDENCIES_TESTS_RELEASES.md`
- `research/upstreams/xray-family/SECURITY_AND_DEPENDENCY_ADVISORIES.md`
- `research/upstreams/xray-family/ISSUE_RELEASE_LESSONS.md`
- `research/upstreams/xray-family/SUPPORT_REUSE_DECISIONS.md`
- `research/upstreams/xray-family/CLIENT_ECOSYSTEM.md`
- completed entry 037 VLESS
- completed entry 074 REALITY
- completed entry 075 legacy XTLS terminology

## Exact current-source evidence

Primary implementation/reference:

- repository: `XTLS/Xray-core`
- research commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- recursive source manifest:
  `https://api.github.com/repos/XTLS/Xray-core/git/trees/46ee908a9a67513d3c85bbf998be5d553a078109?recursive=1`
- root license: **MPL-2.0**
- language/build: Go module, `go 1.26` at the research pin.

Current source evidence at that exact pin:

- `proxy/vless/vless.go` defines `XRV = "xtls-rprx-vision"`.
- `proxy/vless/account.go` stores `Flow` on the VLESS account and explicitly comments that it may be `xtls-rprx-vision`.
- `infra/conf/vless_test.go` contains current parsing tests for `xtls-rprx-vision` and the `xtls-rprx-vision-udp443` variant.
- `proxy/vless/outbound/outbound.go` handles the Vision flow and its UDP/443 variant, distinguishes Vision from default/no-flow behavior, and states that XTLS/Vision handling **only supports TLS and REALITY directly for now**.
- The same current outbound path checks that outer TLS is **TLS 1.3** before continuing Vision processing when TLS/uTLS is used.
- Current `infra/conf/transport_internet.go` rejects legacy generic `security: "xtls"` and points to `xtls-rprx-vision with TLS or REALITY`, proving that Vision is a flow used with a current security layer rather than the removed legacy security setting itself.

## Product classification

PVNetwork models Vision as one axis in a version-aware capability tuple:

`application protocol + flow + security + transport + client core version + server core version + platform capability`.

For the current Xray pin, the flow is tied to VLESS source/config semantics. It must not be promoted as a standalone VPN protocol, outer transport, or generic security mode.

## 20-gate reconciliation

| # | v1 completion gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations identified | PASS | Xray-core is the primary current implementation. Shared client evidence maps v2rayNG, v2rayN and other Xray-family clients as UI/import/reference implementations while preserving their distinct application licenses. No separate Vision protocol engine is invented. |
| 2 | Canonical sources pinned | PASS | Exact Xray-core commit/tree/recursive manifest are pinned. Current VLESS source defines `xtls-rprx-vision`; parser tests and inbound/outbound implementation paths are traceable at the same exact pin. |
| 3 | Licenses reviewed | PASS | Xray-core is MPL-2.0; wrapper/client licenses and reuse boundaries are mapped separately. MPL covered-file/source/notice obligations, dependency/SBOM review and final legal review remain explicit for shipping. |
| 4 | Complete source-tree reference / manifest captured | PASS | Full recursive Xray tree is pinned. Vision-relevant VLESS account/constants/config tests and inbound/outbound implementation sit inside that complete source map; no fabricated independent Vision repository exists. |
| 5 | Languages / build systems mapped | PASS | Vision is implemented inside the Go Xray-core module; build/dependency/test/release workflows are mapped by shared Xray evidence. Client/wrapper platform technologies remain separate. |
| 6 | Internal architecture / data flow mapped | PASS | Canonical VLESS profile -> VLESS account `Flow` -> version-aware Xray adapter -> selected TLS/REALITY security + selected transport -> VLESS outbound/inbound Vision processing. Vision-specific traffic handling is part of VLESS runtime flow logic, not an independent tunnel stack. |
| 7 | Core / engine integration mapped | PASS | Use the existing Xray VLESS flow field and exact-version capability validation; do not reimplement Vision separately. Current source directly accepts TLS/REALITY connections for Vision and rejects incompatible direct security connection types. |
| 8 | UI / menu map completed | PASS for v1 | Shared client UI evidence covers VLESS editors/import/share/settings. PVNetwork should expose a Vision flow selector only when the selected VLESS/core/security/transport combination reports it as valid. Simple mode may hide it; no separate “XTLS Vision VPN” product card is justified. Exhaustive field screenshots are v2 work. |
| 9 | Config / import / export / URI / QR mapped | PASS | Vision is represented by the VLESS `flow` field, including the current `xtls-rprx-vision` value and observed `-udp443` variant. Import/export must preserve the flow separately from `security`. No standalone Vision URI/QR standard is invented; it travels inside the applicable VLESS/full-config representation. |
| 10 | Persistence / secrets mapped | PASS | Vision itself introduces no independent long-term credential type. VLESS identity and TLS/REALITY credentials retain their own secure-storage ownership. Flow is non-secret configuration metadata; generated runtime config remains transient/redacted with the rest of the profile. |
| 11 | Platform-specific implementation mapped | PASS for research | Xray/libXray/client platform boundaries are already mapped. Vision availability depends on exact core/client/server/platform integration; source cross-compilation does not itself certify every OS/device combination. |
| 12 | Logs / diagnostics / failure mapping | PASS | Product errors must distinguish unsupported flow, VLESS identity/config, incompatible TLS/REALITY connection, outer TLS version mismatch, UDP/443 Vision policy, transport and general connectivity. Shared Xray logging/redaction rules apply. |
| 13 | Assets / screenshots / localization mapped | PASS for v1 | Vision has no independent canonical consumer application/store asset set. Shared client GUI/localization evidence is reference-only according to client licenses; exhaustive menu/screenshot evidence is deferred to v2. |
| 14 | Meaningful forks / alternatives / variants reviewed | PASS | No-flow VLESS, `xtls-rprx-vision`, observed `xtls-rprx-vision-udp443`, TLS security and REALITY security are distinct configuration dimensions/variants. Legacy generic XTLS security is separately completed as entry 075 and is removed in current source. |
| 15 | Important issues / PRs / releases / advisories reviewed | PASS | Shared Xray maintenance evidence records current-main vs stable drift, exact-combination regressions and GHSA-5wf9-h793-w73c. Current pinned Vision code contains explicit compatibility checks rather than a universal-support assumption. Therefore deployment must pin exact client/server versions; later runtime certification is not converted into a hidden V1 gate. |
| 16 | Official docs / forums reviewed | PASS | Pinned Xray source/tests/config behavior are the primary authority. Upstream issues/discussions may support maintenance context but public performance/detectability/security claims are not promoted to verified facts without source/accepted evidence. |
| 17 | Tests / CI / quality evidence reviewed | PASS | Xray CI and `go test ./...` matrix are mapped. Current `infra/conf/vless_test.go` specifically exercises Vision flow parsing. Scenario/client-server combination tests remain implementation/certification evidence and are not required merely to complete V1 research. |
| 18 | Store / privacy / security implications reviewed | PASS | Vision must not be presented as a separate security layer; security comes from the actual TLS/REALITY configuration and VLESS/core behavior. Unsupported combinations should fail visibly rather than silently downgrading. Store feasibility/privacy handling follow the selected platform/client build. |
| 19 | PVNetwork reuse / rewrite / hybrid decision documented | PASS | **CURRENT FLOW/MODE CAPABILITY / XRAY PRIMARY REFERENCE**. Use Xray's current VLESS flow implementation behind the adapter, version/capability-gate it, and do not fork/rewrite Vision or inflate protocol count. |
| 20 | Open uncertainties / blockers listed | PASS | Exact handshake/crypto relationship, detailed Vision data-path/wire analysis, historical/current version matrix, server/client deployment guides, exhaustive client UI screenshots, performance and real-device interoperability remain legitimate V2/certification work. They do not block the current V1 classification and integration decision. |

## Final V1 decision

Every applicable gate is evidence-backed or correctly bounded. Current source proves that Vision is an active VLESS flow/mode with concrete TLS/REALITY and TLS-version constraints, while legacy generic XTLS security is a different, removed concept.

Entry 076 therefore qualifies for **`COMPLETE-RESEARCH-v1`** while remaining **not implemented / not combination-certified / not runtime-certified**.
