# 091 — XHTTP — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / XRAY-SPECIFIC HTTP TRANSPORT FAMILY / SPLITHTTP SOURCE PATH / NOT AN IETF HTTP VERSION / TLS SECURITY SEPARATE`**

## Authority and pins
- Xray `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0.
- Implementation path: `transport/internet/splithttp/`.
- Standards layers remain Entries 085 HTTP/1.1, 086 HTTP/2 and 087 HTTP/3; TLS/REALITY remain separate security layers.

Current XHTTP/SplitHTTP modes and source behavior are Xray-specific. No standalone RFC/repository/server product is invented.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem|PASS|Pinned Xray is the canonical implementation authority for this matrix entry on both peer sides. Reverse proxies/CDNs are deployment intermediaries, not XHTTP standards authorities.|
|2|Installers/deployment projects|PASS / N-A|No standalone XHTTP installer. Deployment follows the Xray server/client package and any separately reviewed proxy/CDN.|
|3|Server install matrix|PASS|Capability follows Xray/Go and supported lower HTTP/TLS/QUIC stack. Exact container/service deployment is parent-engine owned.|
|4|Server UI/menu map|PASS|Parent Xray panel may expose mode/path/host/headers/upload/download/mux and lower HTTP/security controls; no independent canonical XHTTP panel exists.|
|5|Client install matrix|PASS|Capability follows Xray-family client packaging; no separate XHTTP app/package.|
|6|Client UI/menu map|PASS|Expose only source-backed versioned XHTTP mode/path/host/header/upload/download/mux fields under a parent profile. No standalone VPN card.|
|7|Cryptography|PASS / N-A|XHTTP is not encryption/authentication. TLS/REALITY security remains separate.|
|8|Data path/wire flow|PASS|Parent protocol -> XHTTP mode/session logic -> one or more HTTP request/upload/download streams -> selected HTTP/TLS/QUIC lower layer -> peer XHTTP -> parent protocol.|
|9|Ports/transports/handshake|PASS|Uses parent-selected HTTP/TLS/QUIC endpoints; no XHTTP-specific fixed port or independent cryptographic handshake. Mode and lower HTTP version are separate axes.|
|10|Deployment topologies|PASS|Direct, reverse-proxy/CDN/gateway and split upload/download layouts are parent deployment patterns; support is exact-mode/server/proxy dependent.|
|11|Source/license/activity pins|PASS|Exact Xray commit/tree/path and MPL-2.0 boundary.|
|12|Supply-chain/security risks|PASS|Pin Xray core; treat proxy/CDN projects independently; redact authorization/custom headers and avoid arbitrary community tuning recipes.|
|13|Upgrade/uninstall/rollback|PASS|Lifecycle follows Xray core/config. Upgrade requires schema/mode/interoperability regression review; rollback restores prior core/config.|
|14|Differences/uncertainties|PASS|`packet-up`, `stream-up`, `stream-one`, lower HTTP version, mux, browser/proxy/CDN behavior and tuning are explicit version-sensitive capabilities.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md`.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 092 RAW.|

## Final decision
All exact 16 V2 gates are evidence-backed or correctly Xray-transport N/A bounded. Entry 091 qualifies for **`COMPLETE-REFERENCE-v2`** without implementation/device/Store certification claims.
