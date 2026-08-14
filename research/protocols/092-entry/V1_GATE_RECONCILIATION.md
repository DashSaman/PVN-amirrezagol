# 092 — RAW — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **092 — RAW**

Decision: **`COMPLETE-RESEARCH-v1 / XRAY RAW-TCP TRANSPORT NAMING+FRAMING CAPABILITY / NOT RAW-IP SOCKETS / NOT ENCRYPTED / NOT A VPN / NOT IMPLEMENTED / NOT CERTIFIED`**

## Exact source authority

Primary source is the pinned Xray core:

- repository: `XTLS/Xray-core`
- commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- license: MPL-2.0
- language/build: Go module, Go 1.26
- current implementation area: `transport/internet/tcp/`

Current Xray source/configuration uses **RAW** as the modern transport naming around the TCP transport implementation while retaining legacy TCP-oriented source paths/compatibility. This matrix entry therefore refers to Xray's raw TCP byte-stream/framing transport capability, **not** operating-system raw IP sockets, IP protocol-number injection, packet crafting or a new cryptographic protocol.

The lower transport remains TCP (completed entry 081). Optional TCP/HTTP-style header camouflage/framing in Xray is configuration around that transport and must not be confused with standardized HTTP/1.1/2/3 entries or TLS security.

## Product classification

PVNetwork models RAW as a version-aware Xray transport option attached to a parent protocol. The canonical profile must preserve:

- modern `raw` versus legacy `tcp` source/provenance naming;
- optional header/framing mode and request/response host/path/header fields where the exact core supports them;
- TCP socket/transport settings separately from parent protocol/security;
- TLS/REALITY security as an independent axis.

A profile imported with legacy `tcp` transport naming must not be silently interpreted as OS raw-socket access.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations | PASS | Pinned Xray-core is the implementation authority for this RAW matrix entry. Xray-family GUI clients are import/UI references only; OS raw sockets are explicitly out of scope. |
| 2 | Canonical sources pinned | PASS | Exact Xray commit/tree plus current `transport/internet/tcp/` source/config paths are pinned. No separate `raw` RFC/repository/engine is fabricated. |
| 3 | Licensing / legal reuse | PASS | RAW implementation is inside Xray's MPL-2.0 boundary. PVNetwork consumes it through the Xray engine subject to MPL/source/notice/dependency obligations; no special raw-socket license is relevant because raw IP sockets are not the feature. |
| 4 | Complete source-tree review | PASS | Full recursive Xray tree is already pinned; TCP/RAW config, dialer/listener/framing, protobuf, tests, build and release paths are traceable. |
| 5 | Languages / build / dependencies | PASS | Go/Xray module; host TCP stack is the lower OS transport. There is no separate RAW package/daemon/build system. |
| 6 | Internal architecture / data flow | PASS | Parent protocol bytes -> Xray RAW/TCP transport/framing -> OS TCP socket -> peer Xray transport -> parent protocol. Optional header camouflage is framing/config, while TLS/REALITY remains a separate security layer. |
| 7 | Core / engine integration | PASS | Use Xray's native RAW/TCP transport through the exact-version adapter. Do not request raw-socket privileges or implement packet injection for this matrix entry. |
| 8 | UI / menus | PASS for v1 | Expose as a parent transport, with legacy TCP naming handled as compatibility/migration metadata. Advanced header/framing options are shown only when source-backed. No standalone RAW VPN card and no “raw packet” wording. |
| 9 | Config / import / export / URI / QR | PASS | RAW/TCP transport fields remain nested in parent Xray links/full configs. Import/export preserves modern/legacy naming and header settings. No standalone `raw://` VPN URI/QR is invented. |
| 10 | Persistence / secrets | PASS | Transport/header fields are generally non-secret; authorization/custom header values can be sensitive. TLS/REALITY/parent credentials retain separate secure-storage ownership. |
| 11 | Platform-specific implementation | PASS for research | Capability follows Xray plus normal TCP socket support; it does not require privileged raw sockets. Mobile/background/VPN-wrapper/socket behavior remains later platform evidence. |
| 12 | Logs / diagnostics / failure mapping | PASS | Separate legacy-name/config errors, TCP connect/reset/timeout, header/framing mismatch, TLS/REALITY and parent-protocol failures. Sensitive headers/credentials are redacted. |
| 13 | Assets / screenshots / localization | PASS/N-A | No independent canonical RAW application/store asset set. Parent Xray-client assets remain separately licensed; detailed screenshots are V2. |
| 14 | Forks / alternatives / variants | PASS | Legacy `tcp` naming and modern `raw` naming belong to the same Xray transport family for this entry. WebSocket/gRPC/XHTTP are separate transports; OS raw IP sockets are explicitly not an alternative alias. |
| 15 | Issues / releases / advisories | PASS | Naming/config behavior is tied to actively evolving Xray releases; exact core pin is therefore mandatory. Product migration must not infer support from stale `tcp` terminology alone. Runtime regressions remain exact-version acceptance evidence. |
| 16 | Official docs / discussions | PASS | Pinned Xray source/config/tests and official Xray-maintained docs are primary. Community references that call this “raw socket” are not accepted without source evidence. |
| 17 | Tests / CI / quality evidence | PASS | Xray shared Go CI/tests and source-resident TCP transport tests are mapped. Real client/server/header/security combination testing remains later certification evidence. |
| 18 | Store / privacy / security implications | PASS | RAW/TCP is not encryption/authentication and needs TLS/REALITY/parent security where required. No privileged raw-socket permission should be requested based on this entry. Header metadata may reveal topology and must be minimized/redacted. |
| 19 | PVNetwork reuse decision | PASS | **XRAY-NATIVE RAW/TCP TRANSPORT / NO RAW-SOCKET ENGINE.** Reuse exact Xray semantics, preserve legacy import provenance and avoid protocol-count inflation. |
| 20 | Open uncertainties / blockers | PASS | Exact legacy-version naming chronology, header/framing wire examples, compatibility with each parent protocol/security/transport option, performance and platform/server interoperability remain V2/deployment/certification work. None blocks V1 classification/reuse. |

## Final V1 decision

All 20 V1 gates are evidence-backed or correctly bounded. Entry 092 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
