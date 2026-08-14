# 091 — XHTTP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **091 — XHTTP**

Decision: **`COMPLETE-RESEARCH-v1 / XRAY-SPECIFIC HTTP TRANSPORT FAMILY / SPLITHTTP SOURCE PATH / NOT AN IETF HTTP VERSION / NOT A VPN / TLS SECURITY SEPARATE / NOT IMPLEMENTED / NOT CERTIFIED`**

## Exact source authority

Primary source is the pinned Xray core:

- repository: `XTLS/Xray-core`
- commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- license: MPL-2.0
- language/build: Go module, Go 1.26
- implementation path: `transport/internet/splithttp/`

Current Xray source uses the SplitHTTP/XHTTP implementation area rather than an independent `xhttp` repository. Source/config paths expose XHTTP/SplitHTTP behavior, including current mode concepts such as `packet-up`, `stream-up` and `stream-one`, plus HTTP request/response, upload/download, path/host/header and mux-oriented configuration.

XHTTP is therefore an **Xray-specific transport family built on HTTP semantics/transports**, not a new IETF HTTP version. Entries 085/086/087 remain the independent standards-backed HTTP/1.1, HTTP/2 and HTTP/3 research authorities.

## Architecture boundary

Parent protocol bytes are carried through Xray's XHTTP/SplitHTTP transport using one or more HTTP request/response streams according to the selected mode. The concrete lower transport can involve HTTP/1.1, HTTP/2 or HTTP/3-capable engine paths depending on configuration and core/server capabilities. TLS/REALITY, authentication and parent protocol security remain separate axes.

No statement is made that every XHTTP mode works identically over every HTTP version, CDN, reverse proxy, browser path or platform; those are later exact-version interoperability questions.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations | PASS | Pinned Xray-core is the primary/canonical implementation authority for this matrix entry. Xray-family GUI clients are config/UI references only and do not redefine XHTTP wire semantics. |
| 2 | Canonical sources pinned | PASS | Exact Xray commit/tree and `transport/internet/splithttp/` implementation path are pinned. No nonexistent RFC, standalone repository or release is fabricated. |
| 3 | Licensing / legal reuse | PASS | XHTTP source is part of Xray's MPL-2.0 boundary. PVNetwork may consume it through the Xray engine subject to MPL file-level/source/notice and dependency/SBOM obligations; no permissive standalone XHTTP license is invented. |
| 4 | Complete source-tree review | PASS | Full recursive Xray tree is already pinned. SplitHTTP/XHTTP config, dialer/client/server/mux, protobuf, tests/build/release and shared HTTP/TLS paths are traceable in that tree. |
| 5 | Languages / build / dependencies | PASS | Go/Xray module; XHTTP has no separate package manager or build product outside the engine. HTTP/2/3/TLS/QUIC dependencies remain owned by the parent Xray stack already researched. |
| 6 | Internal architecture / data flow | PASS | Parent protocol -> XHTTP mode/session logic -> HTTP request/upload/download stream(s) -> selected HTTP/TLS/QUIC lower layer -> peer XHTTP server -> parent protocol. Mode, HTTP version, TLS/REALITY and application protocol are independent configuration dimensions. |
| 7 | Core / engine integration | PASS | Use Xray's native XHTTP/SplitHTTP transport through the version-aware adapter. Do not reimplement it as a generic HTTP wrapper or assume compatibility with unrelated SplitHTTP projects. |
| 8 | UI / menus | PASS for v1 | Advanced transport option only. UI may expose source-backed mode/path/host/header/upload/download/mux fields when compatible with the selected core/server. Do not create a standalone “XHTTP VPN protocol” product card. |
| 9 | Config / import / export / URI / QR | PASS | XHTTP settings are nested in Xray-compatible links/full configs. Import/export must preserve mode, path/host/headers and version-sensitive settings. No standalone `xhttp://` standard is invented. |
| 10 | Persistence / secrets | PASS | Most XHTTP transport fields are non-secret topology/behavior metadata; authorization/custom headers can be sensitive and must be redacted/securely stored. TLS/REALITY and parent credentials remain separately owned. |
| 11 | Platform-specific implementation | PASS for research | Capability follows Xray core/platform support. HTTP proxy/CDN/browser/network-stack/mobile/background behavior is platform/deployment specific and remains later certification evidence. |
| 12 | Logs / diagnostics / failure mapping | PASS | Distinguish XHTTP mode/config, HTTP status/path/host/header, upload/download/mux, lower HTTP version, TLS/QUIC, proxy/CDN and parent-protocol failures. Secret headers/config values are redacted. |
| 13 | Assets / screenshots / localization | PASS for v1 | No independent canonical XHTTP application/store asset set. Parent Xray client UI/localization is separately licensed; exhaustive screenshots are V2 evidence. |
| 14 | Forks / alternatives / variants | PASS | `packet-up`, `stream-up`, `stream-one` and related current source modes are XHTTP variants. HTTP/1.1/2/3, WebSocket and gRPC are distinct transports/standards, not XHTTP aliases. |
| 15 | Issues / releases / advisories | PASS | XHTTP behavior is coupled to active Xray source/release evolution; exact Xray pin is therefore mandatory. No claim is made that current modes/defaults are stable forever or universally CDN-compatible. Future Xray upgrades require configuration and interoperability regression review. |
| 16 | Official docs / discussions | PASS | Pinned Xray source/config/tests and official Xray-maintained documentation are primary. Community “best CDN/XHTTP mode” recipes are not promoted into universal research facts. |
| 17 | Tests / CI / quality evidence | PASS | Xray shared Go CI/tests plus source-resident SplitHTTP/XHTTP test paths provide upstream quality evidence. Real reverse-proxy/CDN/server/device combinations remain implementation/certification work, not hidden V1 gates. |
| 18 | Store / privacy / security implications | PASS | XHTTP itself is not encryption/authentication. TLS/REALITY must provide transport security where required; HTTP headers/path/host can expose profile metadata and require minimization. Unsafe certificate bypass is not a default. |
| 19 | PVNetwork reuse decision | PASS | **XRAY-NATIVE TRANSPORT / NO STANDALONE XHTTP ENGINE.** Reuse the exact engine implementation and expose only validated source-backed options. |
| 20 | Open uncertainties / blockers | PASS | Detailed wire flows per mode, exact HTTP/1.1/2/3 compatibility, CDN/reverse-proxy behavior, browser-dialer paths, upload/download tuning, mux interactions, performance and device/server interoperability remain V2/deployment/certification work. None blocks the V1 architecture/reuse decision. |

## Final V1 decision

All 20 V1 gates are evidence-backed or correctly bounded. Entry 091 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
