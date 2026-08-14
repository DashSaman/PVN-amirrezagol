# 084 — WebSocket — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **084 — WebSocket**

Decision: **`COMPLETE-RESEARCH-v1 / MESSAGE-FRAMING TRANSPORT OVER HTTP UPGRADE/TLS / XRAY+GORILLA IMPLEMENTATION / NOT A VPN / NOT SECURITY BY ITSELF / NOT IMPLEMENTED / NOT CERTIFIED`**

## Standards baseline

- RFC 6455 — *The WebSocket Protocol*: `https://www.rfc-editor.org/info/rfc6455/`
- RFC 8441 — *Bootstrapping WebSockets with HTTP/2* (extended CONNECT), when an implementation explicitly supports that path.

Classic WebSocket uses an HTTP/1.1 Upgrade handshake and then WebSocket framing over TCP. `wss` adds TLS; WebSocket itself does not provide independent encryption/authentication.

## Exact Xray-selected implementation

Pinned Xray-core:

- `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree `46ee908a9a67513d3c85bbf998be5d553a078109`
- MPL-2.0, Go 1.26
- source `transport/internet/websocket/`

Pinned Xray `go.mod` selects:

- `github.com/gorilla/websocket v1.5.3`
- tag commit `ce903f6d1d961af3a8602f2842c8b1c3fca58c4d`
- tree `0cef094486eaeb81bbc1614c26bbbbd0cb3eb391`
- recursive manifest `https://api.github.com/repos/gorilla/websocket/git/trees/0cef094486eaeb81bbc1614c26bbbbd0cb3eb391?recursive=1`
- license BSD-2-Clause
- v1.5.3 remains the latest reviewed Gorilla WebSocket tag.

Current Xray `dialer.go` proves:

- plain `ws` and TLS-protected `wss` are distinct;
- TLS path sets HTTP/1.1 ALPN for the Gorilla dialer;
- TLS fingerprint/uTLS may be used without disabling normal hostname verification unless `InsecureSkipVerify` is explicitly configured;
- Host/path/request headers, early-data (`ed`) and browser-dialer paths are implementation configuration, not new WebSocket standards.

## 20-gate reconciliation

|#|Gate|Result|Evidence / decision|
|---:|---|---|---|
|1|Top implementations|PASS|RFC6455 is protocol authority; Gorilla WebSocket v1.5.3 is the exact library selected by pinned Xray; Xray is the product transport implementation/reference.|
|2|Sources pinned|PASS|RFC6455/RFC8441 + exact Xray and Gorilla tag/commit/tree pins.|
|3|Licenses|PASS|Gorilla WebSocket is BSD-2-Clause with source/binary notice/disclaimer obligations; Xray is MPL-2.0. Browser/app assets are not licensed by these code licenses.|
|4|Source tree|PASS|Complete Gorilla and Xray recursive trees are pinned; dialer/hub/connection/config/tests/build paths are traceable.|
|5|Languages/build|PASS|Go library + Go Xray module. Gorilla v1.5.3 source includes Go tests/CI history; Xray build/tests are separately mapped.|
|6|Architecture|PASS|Parent protocol bytes -> HTTP Upgrade/WS handshake -> WebSocket frames -> TCP; optional TLS wraps the connection for `wss`. Host/path/proxy/CDN behavior is separate from parent protocol and TLS trust.|
|7|Engine integration|PASS|Use Xray's selected Gorilla-backed transport. Preserve exact engine version and do not implement a second WebSocket stack. TLS/fingerprint remains the shared TLS adapter.|
|8|UI/menu|PASS for v1|Expose WebSocket only as a parent transport with path/Host/headers/early-data/TLS settings when supported. Do not market `ws` as encrypted; `wss` security belongs to TLS.|
|9|Config/import/export/URI/QR|PASS|Parent links/full configs carry transport=`ws`, path/Host/headers and security fields. `ws://`/`wss://` are generic WebSocket URIs, not standalone PVNetwork VPN subscription formats; parent-protocol import must preserve all transport metadata.|
|10|Persistence/secrets|PASS|Path/Host/headers are generally non-secret configuration; authorization/cookie/custom headers can be secret and must be redacted/securely stored when used. TLS/private credentials remain TLS-owned.|
|11|Platforms|PASS for research|Go/Xray transport is cross-platform at engine level; proxy/CDN/browser/network-stack and mobile background/VPN wrapper behavior varies by platform.|
|12|Logs/diagnostics|PASS|Separate TCP connect, HTTP upgrade/status, Host/path/header, TLS/hostname, proxy/CDN, ping/pong/close, frame/read/write and parent-protocol errors. Secret headers are redacted.|
|13|Assets/localization|PASS/N-A|No independent canonical consumer WebSocket VPN assets. Parent client UI/localization remains separately licensed.|
|14|Forks/alternatives|PASS|Classic WS, wss and RFC8441 extended-CONNECT WebSocket are deployment forms; XHTTP/gRPC/HTTP transports are separate entries. No assumption that Xray classic Gorilla path automatically implements every RFC8441 mode.|
|15|Issues/releases/advisories|PASS|Exact Gorilla v1.5.3 is pinned and remains latest reviewed tag; its release commit intentionally reverted problematic post-v1.5.0 changes after issue review. Exact library/version is therefore retained rather than floating. Xray current transport changes remain version-sensitive.|
|16|Official docs|PASS|RFC6455/RFC8441, Gorilla source/docs and pinned Xray source are primary. CDN/proxy recipes are deployment examples, not protocol standards.|
|17|Tests/CI|PASS|Gorilla source/release history includes unit/race-test CI; Xray has transport/shared tests. CDN/proxy/device interoperability remains later certification evidence.|
|18|Store/privacy/security|PASS|`ws` is plaintext; `wss` requires correct TLS identity verification. Host/path/header metadata can expose profile/topology; unsafe cert bypass is not a default.|
|19|Reuse decision|PASS|**XRAY TRANSPORT / GORILLA INDIRECT REUSE.** Use the engine-selected library; no standalone WebSocket VPN implementation.|
|20|Open uncertainties|PASS|Extended CONNECT support, proxy/CDN quirks, compression policy, early-data behavior, browser-dialer differences, performance and exact device/server interoperability remain V2/deployment/certification work.|

## Final V1 decision

All 20 gates are evidence-backed or correctly transport-layer N/A bounded. Entry 084 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
