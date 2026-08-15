# 087 — HTTP/3 — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / RFC 9114 HTTP OVER QUIC / ENGINE-SELECTED QUIC STACK / NOT A VPN`**

## Authority and pins
- RFC 9110 — HTTP Semantics.
- RFC 9114 — HTTP/3.
- QUIC authority: RFC 9000/9001/9002/9369 via completed Entry 083.
- APernet quic-go fork commit `6c6cc9bcb716256af2977c4b3b8a2924269e9718`, tree `01ed6f54049664ae875a466f819fb750b6ddca06`, MIT.
- Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem|PASS|HTTP/3 authority is RFC9114; selected Xray path uses the pinned APernet QUIC/HTTP3 implementation. Parent HTTP servers/proxies own deployment.|
|2|Installers/deployment projects|PASS / N-A|No standalone HTTP/3 VPN installer. Deployment follows the parent app/server/library and UDP reachability.|
|3|Server install matrix|PASS|Go/parent engine is cross-platform at source level; exact UDP/QUIC/server packaging is parent-deployment capability.|
|4|Server UI/menu map|PASS / N-A|No generic HTTP/3 panel. Parent server may expose QUIC versions, ALPN, TLS, stream/QPACK/resource limits.|
|5|Client install matrix|PASS|Capability follows parent engine/library and UDP-capable platform.|
|6|Client UI/menu map|PASS / N-A|Parent profile may expose HTTP3/QUIC capability where supported; no standalone VPN card.|
|7|Cryptography|PASS|Security comes from QUIC/TLS integration; HTTP/3 itself is HTTP mapping and QPACK, not an independent crypto layer.|
|8|Data path/wire flow|PASS|HTTP semantics -> HTTP/3 control/request streams + QPACK -> QUIC streams/packet protection/loss/congestion -> UDP/IP.|
|9|Ports/transports/handshake|PASS|Runs over QUIC/UDP. Parent app chooses port; QUIC performs TLS-integrated handshake and HTTP/3 uses appropriate ALPN/application negotiation.|
|10|Deployment topologies|PASS|Origin, reverse proxy/CDN, gateway and parent application layouts are deployment patterns.|
|11|Source/license/activity pins|PASS|Exact APernet/Xray pins and MIT/MPL boundaries plus RFCs.|
|12|Supply-chain/security risks|PASS|Keep selected fork pinned; review QPACK/resource/0-RTT limits and dependency upgrades; do not float QUIC implementation.|
|13|Upgrade/uninstall/rollback|PASS|Follows parent app/QUIC dependency; rollback restores previous engine/dependency/config.|
|14|Differences/uncertainties|PASS|HTTP/3 versus h1/h2, QUIC v1/v2, 0-RTT, QPACK limits, UDP reachability, proxy/CDN behavior and migration are explicit.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md`.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 088 gRPC.|

## Final decision
All 16 V2 gates are evidence-backed or correctly HTTP/QUIC-layer N/A bounded. Entry 087 qualifies for **`COMPLETE-REFERENCE-v2`** without implementation/device/Store certification claims.
