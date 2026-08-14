# 086 — HTTP/2 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **086 — HTTP/2**

Decision: **`COMPLETE-RESEARCH-v1 / MULTIPLEXED HTTP MAPPING / NOT A VPN / TLS SECURITY SEPARATE / NOT IMPLEMENTED / NOT CERTIFIED`**

## Authority / implementation

- RFC 9110 — HTTP Semantics: `https://www.rfc-editor.org/info/rfc9110/`
- RFC 9113 — HTTP/2, June 2022, obsoleting RFC 7540/8740: `https://www.rfc-editor.org/info/rfc9113/`
- Go `net/http` implementation pin: `golang/go@c19862e5f8415b4f24b189d065ed739517c548ba` (go1.26.5), tree `0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74`, BSD-3-Clause.
- Xray integration pin: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.

HTTP/2 is a binary, multiplexed mapping of common HTTP semantics over a connection, with HPACK header compression and streams. It does not itself supply a VPN or independent identity/encryption guarantee. Production `h2` commonly uses TLS/ALPN; cleartext upgrade/prior-knowledge forms are separate deployment choices.

## 20-gate reconciliation

|#|Gate|Result|Evidence / decision|
|---:|---|---|---|
|1|Top implementations|PASS|RFC9113 authority; Go `net/http`/HTTP2 is the primary Go runtime implementation; Xray is integration reference.|
|2|Sources pinned|PASS|RFC9110/9113 + exact Go/Xray source/tree pins.|
|3|Licenses|PASS|Go BSD-3-Clause and Xray MPL-2.0; no parser/framer fork needed.|
|4|Source tree|PASS|Complete Go/Xray manifests cover HTTP2 framing, transport/server, HPACK integration, tests/build paths.|
|5|Languages/build|PASS|Go stdlib/runtime and Go engine; platform TLS/socket layers stay separate.|
|6|Architecture|PASS|HTTP semantics -> HTTP/2 frames/streams/HPACK -> reliable connection; optional TLS/ALPN protects connection. Stream state, flow control and HTTP semantics are separate.|
|7|Engine integration|PASS|Use runtime/engine HTTP/2; no custom framer/compression implementation. Capability-gate h2/h2c/ALPN.|
|8|UI/menu|PASS/N-A|Transport/application capability under parent profiles; no standalone VPN card. Expose h2-specific knobs only when required/source-backed.|
|9|Config/import/export|PASS|Parent config carries HTTP authority/path/headers/TLS/ALPN and transport selection. No standalone HTTP/2 VPN URI/QR.|
|10|Persistence/secrets|PASS|Authorization/cookie headers and TLS credentials are secrets by their parent layers; stream/window state is runtime-only.|
|11|Platforms|PASS for research|Go engine cross-platform; native proxy/TLS/background behavior differs by OS.|
|12|Logs/diagnostics|PASS|Separate TLS/ALPN, protocol preface/settings, stream reset, flow-control, HPACK, GOAWAY, status/header and parent errors. Redact secret headers.|
|13|Assets/localization|PASS/N-A|No independent protocol assets.|
|14|Forks/alternatives|PASS|HTTP/1.1 and HTTP/3 are separate entries; h2c is a deployment form; gRPC is a higher-layer use.|
|15|Issues/releases|PASS|Exact Go1.26.5 is pinned; Go HTTP code remains under active 2026 maintenance. HTTP/2 historically has resource-amplification/rapid-reset classes, so stream/concurrency limits and patched runtime selection are deployment requirements rather than assumptions of safety from the RFC alone.|
|16|Official docs|PASS|RFC9110/9113 and official Go/Xray source/docs primary.|
|17|Tests/CI|PASS|Go HTTP/2 client/server/framing tests and CI are source-visible; Xray shared tests mapped. Interop/load/DoS regression later.|
|18|Store/privacy/security|PASS|HTTP/2 framing is not encryption; TLS identity verification remains separate. Header compression/state and request metadata require privacy/resource controls.|
|19|Reuse decision|PASS|**MAINTAINED HTTP/2 RUNTIME / NO CUSTOM STACK.** Use engine/runtime implementation.|
|20|Open uncertainties|PASS|Exact h2c support, priority extensions, stream limits, proxy/CDN behavior, HPACK/resource policies, native platform differences and device/server interoperability remain V2/deployment/certification work.|

## Final V1 decision

All 20 gates are evidence-backed or correctly HTTP-layer N/A bounded. Entry 086 qualifies for **`COMPLETE-RESEARCH-v1`**.
