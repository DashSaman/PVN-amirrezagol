# 084 — WebSocket — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / RFC 6455 MESSAGE FRAMING / XRAY + GORILLA WEBSOCKET / NOT A VPN / TLS SEPARATE`**

## Authority and pins
- RFC 6455 — WebSocket.
- RFC 8441 — WebSockets over HTTP/2 extended CONNECT, when explicitly implemented.
- Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.
- Xray-selected `github.com/gorilla/websocket v1.5.3`, commit `ce903f6d1d961af3a8602f2842c8b1c3fca58c4d`, tree `0cef094486eaeb81bbc1614c26bbbbd0cb3eb391`, BSD-2-Clause.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem|PASS|RFC6455 server/client ecosystem is broad; for the selected Xray path, Gorilla v1.5.3 plus Xray transport code is authoritative. Reverse proxies/CDNs are deployment peers, not protocol implementations.|
|2|Installers/deployment projects|PASS / N-A|No standalone WebSocket VPN installer. Deployment follows parent app/server/proxy/CDN.|
|3|Server install matrix|PASS|Capability follows parent server runtime. Xray/Go path is cross-platform; proxy/CDN support is deployment-specific.|
|4|Server UI/menu map|PASS / N-A|No generic WebSocket server panel. Parent server UI may expose path, Host, headers, TLS and upgrade settings.|
|5|Client install matrix|PASS|Capability follows Xray/client packaging; no separate WebSocket client package required.|
|6|Client UI/menu map|PASS|Parent profile transport UI may expose path/Host/headers/early-data/TLS when source-backed; no standalone VPN card.|
|7|Cryptography|PASS / N-A|WebSocket has none. `wss` security comes from TLS; plain `ws` is plaintext.|
|8|Data path/wire flow|PASS|Parent bytes -> HTTP Upgrade/WebSocket handshake -> WebSocket frames -> TCP; optional TLS wraps the connection for `wss`.|
|9|Ports/transports/handshake|PASS|Uses parent HTTP/TCP/TLS port selection. Classic path uses HTTP/1.1 Upgrade; RFC8441 is a distinct extended-CONNECT deployment path and not assumed universally.|
|10|Deployment topologies|PASS|Direct server, reverse proxy/CDN, gateway and parent proxy/tunnel topologies are supported only as parent deployment models.|
|11|Source/license/activity pins|PASS|Exact Xray/Gorilla pins and BSD-2-Clause/MPL-2.0 boundaries recorded.|
|12|Supply-chain/security risks|PASS|No dedicated installer. Pin parent engine/library; redact secret headers/cookies; do not trust arbitrary CDN recipes.|
|13|Upgrade/uninstall/rollback|PASS|Follows parent Xray/client/server lifecycle; rollback restores prior core/config.|
|14|Differences/uncertainties|PASS|`ws`, `wss`, RFC8441, proxy/CDN behavior, compression and early-data are distinct capabilities/deployment choices.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md`.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 085 HTTP/1.1.|

## Final decision
All 16 V2 gates are evidence-backed or correctly transport-layer N/A bounded. Entry 084 qualifies for **`COMPLETE-REFERENCE-v2`** without implementation/device/Store certification claims.
