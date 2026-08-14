# 089 — mKCP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **089 — mKCP**

Decision: **`COMPLETE-RESEARCH-v1 / XRAY-SPECIFIC MODIFIED KCP RELIABLE-OVER-UDP TRANSPORT / NOT CANONICAL KCP / NOT ENCRYPTED / NOT A VPN / NOT IMPLEMENTED / NOT CERTIFIED`**

## Exact source authority

Primary implementation/reference is the already pinned Xray core:

- repository: `XTLS/Xray-core`
- exact source pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- license: MPL-2.0
- language/build: Go module, Go 1.26 at the pin
- implementation path: `transport/internet/kcp/`

The selected Xray `go.mod` does not identify canonical `skywind3000/kcp` as the mKCP engine dependency. The mKCP implementation is source-resident in Xray and must therefore be researched as an Xray transport, not as a transparent alias for canonical KCP entry 090.

Source/config evidence under `transport/internet/kcp/` includes the transport implementation, configuration/protobuf model, segment/session/connection handling, cryptographic obfuscation/header helpers and tests. Current config exposes transport-oriented parameters such as MTU, TTI, uplink/downlink capacity, congestion-control toggle, read/write buffer sizing and header/seed-related settings.

## Product/security boundary

mKCP provides Xray-specific reliable/ordered behavior and traffic-shaping/obfuscation choices over UDP. It is **not** a cryptographic security layer: parent protocol/TLS/REALITY or other security semantics remain independent. Seed/header camouflage settings must not be marketed as equivalent to authenticated encryption.

## 20-gate reconciliation

|#|Gate|Result|Evidence / decision|
|---:|---|---|---|
|1|Top implementations|PASS|Pinned Xray-core is the canonical implementation for this matrix entry's mKCP semantics. Canonical KCP is deliberately separated as entry 090.|
|2|Sources pinned|PASS|Exact Xray commit/tree/path are pinned; no nonexistent standalone mKCP RFC/release/library is fabricated.|
|3|Licenses|PASS|Implementation is covered by Xray MPL-2.0/file-level source obligations. PVNetwork should consume it through the Xray engine unless a separately reviewed extraction is intentionally undertaken.|
|4|Source tree|PASS|Complete recursive Xray tree is pinned; `transport/internet/kcp/`, protobuf/config, tests, build/dependency and release surfaces are traceable.|
|5|Languages/build|PASS|Go/Xray module; no separate mKCP package manager or external engine is required for this path.|
|6|Architecture|PASS|Parent protocol bytes -> Xray mKCP session/segmentation/retransmission/congestion/obfuscation -> UDP -> peer mKCP -> parent protocol. Security and application protocol remain separate layers.|
|7|Engine integration|PASS|Use Xray's native mKCP transport through its version-aware adapter. Do not substitute canonical KCP implementation and assume wire/config compatibility.|
|8|UI/menu|PASS for v1|Advanced transport option only. Source-backed controls may expose MTU/TTI/capacity/congestion/buffers/header/seed with validated ranges; no standalone VPN card.|
|9|Config/import/export/URI/QR|PASS|mKCP settings are nested in Xray-compatible parent configs/links. No canonical standalone `mkcp://` subscription URI/QR is identified or invented.|
|10|Persistence/secrets|PASS|Most tuning/header fields are non-secret; seed may be security/privacy-sensitive configuration but is not promoted to a cryptographic credential. Parent protocol keys remain separately owned.|
|11|Platforms|PASS for research|Capability follows Xray engine platform support; UDP socket, MTU, mobile background and VPN wrapper behavior remain platform-specific implementation evidence.|
|12|Logs/diagnostics|PASS|Differentiate UDP reachability, MTU, session/segment/retransmit, congestion/window, buffer, header/seed mismatch and parent-protocol/security failures.|
|13|Assets/localization|PASS/N-A|No independent canonical mKCP application/store asset set. Parent client UI assets remain separately licensed.|
|14|Forks/alternatives|PASS|Canonical KCP entry 090 is an upstream algorithm/reference, not assumed wire-equivalent. UDP/QUIC/Hysteria/TUIC are separate transports/protocols; mKCP header variants are modes, not separate VPN protocols.|
|15|Issues/releases/advisories|PASS|mKCP lifecycle is coupled to the exact Xray release/source history, which is actively changing in 2026. No independent mKCP release feed is invented; exact Xray version pin and regression testing are the maintenance control.|
|16|Official docs/discussions|PASS|Pinned Xray source/config/tests are primary authority. Community “best mKCP values” and anti-censorship tuning recipes are not promoted to protocol facts.|
|17|Tests/CI|PASS|Xray source contains kcp transport tests within the repository and shared Go CI/test workflows. Real network/device/server tuning/performance tests remain later certification evidence.|
|18|Store/privacy/security|PASS|mKCP is not authenticated encryption. UDP exposure, traffic fingerprints, seed/header metadata and high resource use require safe defaults; Store/privacy follow the parent app.|
|19|Reuse decision|PASS|**XRAY-NATIVE TRANSPORT / NO SEPARATE mKCP ENGINE.** Reuse current Xray implementation and keep canonical KCP as independent research/reference.|
|20|Open uncertainties|PASS|Exact wire delta from canonical KCP, tuning ranges, header variants, MTU/NAT/firewall behavior, performance/resource tradeoffs, cross-version compatibility and device/server interoperability remain V2/deployment/certification work.|

## Final V1 decision

All 20 gates are evidence-backed or correctly bounded. Entry 089 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
