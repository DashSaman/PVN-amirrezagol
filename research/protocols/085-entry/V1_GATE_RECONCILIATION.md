# 085 — HTTP/1.1 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **085 — HTTP/1.1**

Decision: **`COMPLETE-RESEARCH-v1 / HTTP MESSAGE SYNTAX OVER RELIABLE TRANSPORT / NOT ENCRYPTED BY ITSELF / NOT A VPN / NOT IMPLEMENTED / NOT CERTIFIED`**

## Standards/source baseline

- RFC 9110 — HTTP Semantics, STD 97: `https://www.rfc-editor.org/info/rfc9110/`
- RFC 9112 — HTTP/1.1, STD 99: `https://www.rfc-editor.org/info/rfc9112/`
- Go implementation: `golang/go@c19862e5f8415b4f24b189d065ed739517c548ba` (go1.26.5), tree `0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74`, BSD-3-Clause, `src/net/http/`.
- Xray integration: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.

HTTP/1.1 carries HTTP semantics using textual start lines/fields and message framing on a reliable connection. HTTPS is HTTP protected by TLS; HTTP/1.1 alone does not authenticate/encrypt.

## 20-gate reconciliation

|#|Gate|Result|Evidence / decision|
|---:|---|---|---|
|1|Top implementations|PASS|RFC9110/9112 are authority; Go `net/http` is the primary reusable implementation for Go engines; Xray is product integration reference.|
|2|Sources pinned|PASS|Current HTTP semantics/message RFCs plus exact Go/Xray source/tree pins.|
|3|Licenses|PASS|Go BSD-3-Clause; Xray MPL-2.0; RFC text is standards evidence. Use maintained libraries rather than copying parser code.|
|4|Source tree|PASS|Complete Go/Xray recursive trees pinned; net/http parser/client/server/proxy/tests and engine paths traceable.|
|5|Languages/build|PASS|Go stdlib + Go Xray module; platform TLS/native networking separate.|
|6|Architecture|PASS|HTTP semantics -> HTTP/1.1 message framing -> TCP; optional TLS yields HTTPS. Proxy CONNECT/Upgrade/WebSocket are distinct semantics/extensions.|
|7|Engine integration|PASS|Use Go/engine HTTP stack; no custom parser unless a separately reviewed backend requires it. Preserve strict message-framing validation.|
|8|UI/menu|PASS/N-A|Foundational/app transport; parent profiles may expose HTTP host/path/header/proxy fields. No standalone VPN card.|
|9|Config/import/export|PASS|Parent configs carry method/Host/path/headers/proxy/TLS fields. Generic `http://`/`https://` URIs identify HTTP resources, not PVNetwork VPN subscriptions.|
|10|Persistence/secrets|PASS|Authorization/cookie/proxy-auth headers can be secrets; Host/path/ordinary headers are metadata. Secret headers are secure/redacted.|
|11|Platforms|PASS for research|Go implementation is cross-platform; system proxy, certificate store and mobile/background behavior are platform-specific.|
|12|Logs/diagnostics|PASS|Separate TCP/TLS, parse/framing, status, proxy, header/Host, timeout/body and parent-protocol failures; redact auth/cookies.|
|13|Assets/localization|PASS/N-A|No independent protocol assets.|
|14|Alternatives|PASS|HTTP/2 and HTTP/3 are separate entries sharing RFC9110 semantics; WebSocket/CONNECT are distinct uses.|
|15|Issues/releases|PASS|Go1.26.5 exact source is pinned. Current Go master in August 2026 contains ongoing Host/:authority normalization changes, showing HTTP parser/interop behavior remains version-sensitive; product must not float runtime versions silently.|
|16|Official docs|PASS|RFC9110/9112 and official Go/Xray source/docs primary.|
|17|Tests/CI|PASS|Go net/http has extensive client/server/parser tests and Go CI; Xray shared tests mapped. Proxy/server/device tests later.|
|18|Store/privacy/security|PASS|Plain HTTP exposes requests/metadata/content; HTTPS requires completed TLS security. Parser ambiguity/request-smuggling boundaries demand standards-compliant implementations.|
|19|Reuse decision|PASS|**MAINTAINED HTTP STACK / NO CUSTOM HTTP/1.1 PARSER.** Use engine/runtime implementation.|
|20|Open uncertainties|PASS|Proxy chains, header normalization, CONNECT/Upgrade, compression, auth, exact parser-hardening changes, performance and server/device interoperability remain V2/deployment/certification work.|

## Final V1 decision

All 20 gates are evidence-backed or correctly foundational HTTP N/A bounded. Entry 085 qualifies for **`COMPLETE-RESEARCH-v1`**.
