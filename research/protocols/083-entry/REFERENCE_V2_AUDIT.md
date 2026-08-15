# 083 — QUIC — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / SECURE UDP-BASED TRANSPORT / ENGINE-SELECTED APERNET QUIC-GO FORK / NOT A STANDALONE VPN`**

## Authority and exact implementation
- RFC 9000 — QUIC v1 transport.
- RFC 9001 — TLS integration.
- RFC 9002 — loss detection/congestion control.
- RFC 9369 — QUIC v2.
- Pinned Xray dependency: `github.com/apernet/quic-go v0.59.1-0.20260425001925-6c6cc9bcb716`.
- Exact fork commit `6c6cc9bcb716256af2977c4b3b8a2924269e9718`, tree `01ed6f54049664ae875a466f819fb750b6ddca06`, MIT.
- Parent Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0.

The selected APernet repository is a modified fork of quic-go used by APernet/Hysteria. Its pinned commit contains a deliberate DATAGRAM transport-parameter compatibility change; it must not be silently replaced with upstream latest.

## Exact 16-gate reconciliation
|#|Gate|Result|Evidence / boundary|
|---:|---|---|---|
|1|Server ecosystem mapped|PASS|Meaningful server/client implementation for the selected Xray path is the pinned APernet quic-go fork used by the parent engine. Upstream quic-go is parent/reference, not a transparent substitute.|
|2|Installer/deployment projects|PASS / N-A|QUIC is a library/transport embedded by applications. No standalone QUIC VPN installer is applicable; deployment follows the parent server/client application.|
|3|Server install matrix|PASS|Go library/parent engine is cross-platform at source level; exact UDP, PMTU, kernel and service packaging follows the embedding app/OS. No separate QUIC server package matrix is invented.|
|4|Server UI/menu map|PASS / N-A|No canonical generic QUIC server panel. Parent server apps may expose QUIC/ALPN/datagram/congestion/idle controls.|
|5|Client install matrix|PASS|Client capability follows the selected parent engine/library and UDP-capable OS. Mobile/background/network-change behavior is platform implementation evidence.|
|6|Client UI/menu map|PASS / N-A|Expose QUIC only as a parent transport/capability where supported; no standalone QUIC VPN card.|
|7|Cryptography|PASS|QUIC uses TLS 1.3-derived handshake/keying and QUIC packet protection per RFC 9001; it is not TLS records over UDP. 0-RTT replay considerations and certificate identity remain explicit.|
|8|Data path/wire flow|PASS|Application streams/datagrams -> QUIC streams/DATAGRAM -> TLS-derived packet protection + QUIC reliability/loss/congestion/path logic -> UDP/IP -> peer.|
|9|Ports/transports/handshake|PASS|Runs over UDP; parent application chooses ports/ALPN. QUIC version negotiation, TLS-integrated handshake, connection IDs, migration and retransmission are protocol-level behavior.|
|10|Deployment topologies|PASS|Applicable to parent client/server, proxy, HTTP/3, Hysteria/TUIC or other QUIC-based designs; QUIC itself does not define VPN topology.|
|11|Source/license/activity pins|PASS|Exact APernet pseudo-version/commit/tree and MIT license plus exact Xray pin. Fork release drift beyond the selected pin is explicit.|
|12|Supply-chain/security risks|PASS|Keep the exact engine-selected fork pinned; do not float to upstream latest or another fork without compatibility/security regression review. No blind installer scripts.|
|13|Upgrade/uninstall/rollback|PASS|Lifecycle follows parent engine/dependency. Upgrade requires fork changelog/advisory/regression review; rollback restores previous parent-engine/dependency set.|
|14|Differences/uncertainties|PASS|QUIC v1/v2, streams vs DATAGRAM, 0-RTT, congestion, migration/PMTU and APernet fork-specific DATAGRAM compatibility are explicit and capability-gated.|
|15|Reference index|PASS|Companion `REFERENCE_INDEX.md` records the selected fork and exact pins.|
|16|Handoff continuation|PASS|Batch handoff advances to Entry 084 WebSocket after promotion.|

## Final decision
All exact 16 V2 gates are evidence-backed or correctly transport-layer N/A bounded. Entry 083 qualifies for **`COMPLETE-REFERENCE-v2`**. This does not certify real-network/device/Store interoperability.
