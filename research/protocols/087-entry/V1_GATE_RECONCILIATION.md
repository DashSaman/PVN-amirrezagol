# 087 — HTTP/3 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **087 — HTTP/3**

Decision: **`COMPLETE-RESEARCH-v1 / HTTP MAPPING OVER QUIC / NOT A VPN / QUIC+TLS SECURITY SEPARATE / NOT IMPLEMENTED / NOT CERTIFIED`**

## Authority / implementation

- RFC 9110 — HTTP Semantics: `https://www.rfc-editor.org/info/rfc9110/`
- RFC 9114 — HTTP/3: `https://www.rfc-editor.org/info/rfc9114/`
- completed entry 083 — QUIC, including RFC9000/9001/9002/9369.
- exact Xray-selected QUIC/HTTP3 implementation: `apernet/quic-go@6c6cc9bcb716256af2977c4b3b8a2924269e9718`, tree `01ed6f54049664ae875a466f819fb750b6ddca06`, MIT, selected by pinned Xray pseudo-version.
- Xray core `7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0.

HTTP/3 maps common HTTP semantics onto QUIC streams and QPACK. QUIC/TLS supplies transport security; HTTP/3 is not a separate VPN/security product.

## 20-gate reconciliation

|#|Gate|Result|Evidence / decision|
|---:|---|---|---|
|1|Top implementations|PASS|RFC9114 is authority; exact APernet quic-go fork selected by Xray supplies QUIC/HTTP3 implementation; Xray is integration reference.|
|2|Sources pinned|PASS|RFC9110/9114 + completed QUIC standards + exact APernet/Xray commit/tree pins.|
|3|Licenses|PASS|APernet quic-go MIT; Xray MPL-2.0; no custom QPACK/QUIC parser implementation required.|
|4|Source tree|PASS|Complete APernet/Xray trees pinned; HTTP3/QPACK/QUIC tests and integration paths are source-visible.|
|5|Languages/build|PASS|Go modules; selected fork version is exact parent-engine dependency.|
|6|Architecture|PASS|HTTP semantics -> HTTP/3 control/request streams + QPACK -> QUIC streams/packet protection/loss/congestion -> UDP. TLS handshake is QUIC-integrated, not HTTPS-over-TCP.|
|7|Engine integration|PASS|Use exact engine-selected QUIC/HTTP3 stack; do not swap to upstream latest or custom implementation without regression review.|
|8|UI/menu|PASS/N-A|HTTP/3 is a transport/application capability under parent profiles; no standalone VPN card. ALPN/version/datagram or transport knobs only when source-backed.|
|9|Config/import/export|PASS|Parent configs carry HTTP authority/path/headers plus QUIC/TLS settings. `https://` does not by itself encode whether h1/h2/h3 will be negotiated; no standalone HTTP/3 VPN URI/QR.|
|10|Persistence/secrets|PASS|Authorization/cookie and TLS/QUIC credentials owned by their layers; QPACK/stream state runtime-only.|
|11|Platforms|PASS for research|Go stack cross-platform; UDP reachability, migration, MTU/mobile/network-change behavior vary by OS/network.|
|12|Logs/diagnostics|PASS|Separate UDP/QUIC handshake/version/TLS/ALPN, HTTP3 settings/control/QPACK/stream/status and parent errors; redact secret headers/keys.|
|13|Assets/localization|PASS/N-A|No independent protocol assets.|
|14|Forks/alternatives|PASS|HTTP/1.1, HTTP/2 and HTTP/3 separate; QUIC v1/v2 and fork-specific DATAGRAM behavior are transport details, not separate HTTP semantics.|
|15|Issues/releases|PASS|Exact APernet pseudo-version remains pinned while fork tag feed advanced to v0.61.0; HTTP3 behavior must be regression-tested with any dependency upgrade. Resource/stream/QPACK limits remain security-sensitive.|
|16|Official docs|PASS|RFC9110/9114 plus QUIC RFCs and canonical selected source are primary.|
|17|Tests/CI|PASS|Selected quic-go tree includes HTTP3/QPACK/interop test infrastructure; Xray shared tests mapped. Real proxy/CDN/network tests later.|
|18|Store/privacy/security|PASS|HTTP3 relies on QUIC/TLS identity/security; headers/content metadata remain application-sensitive. 0-RTT/replay and connection migration policies belong to QUIC/application design.|
|19|Reuse decision|PASS|**ENGINE-SELECTED HTTP/3/QUIC STACK / NO CUSTOM H3.** Keep version coupled to parent engine.|
|20|Open uncertainties|PASS|QPACK limits, 0-RTT, QUIC v2, proxy/CDN support, fallback policy, middleboxes, performance and device/server interoperability remain V2/deployment/certification work.|

## Final V1 decision

All 20 gates are evidence-backed or correctly HTTP/QUIC-layer N/A bounded. Entry 087 qualifies for **`COMPLETE-RESEARCH-v1`**.
