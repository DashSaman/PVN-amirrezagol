# 083 — QUIC — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **083 — QUIC**

Decision: **`COMPLETE-RESEARCH-v1 / SECURE UDP-BASED TRANSPORT / XRAY-SELECTED APERNET QUIC-GO FORK / NOT A STANDALONE VPN / NOT IMPLEMENTED / NOT CERTIFIED`**

## Current standards baseline

- RFC 9000 — QUIC v1 core transport: `https://www.rfc-editor.org/info/rfc9000/`
- RFC 9001 — TLS integration for QUIC: `https://www.rfc-editor.org/info/rfc9001/`
- RFC 9002 — QUIC loss detection and congestion control: `https://www.rfc-editor.org/info/rfc9002/`
- RFC 9369 — QUIC Version 2: `https://www.rfc-editor.org/info/rfc9369/`

QUIC is a secure, multiplexed transport over UDP. TLS provides handshake/keying while QUIC owns packet protection, streams, reliability, loss recovery, congestion control and migration semantics. QUIC is not itself a VPN/application proxy protocol.

## Exact Xray-selected implementation

Pinned Xray-core `go.mod` selects:

`github.com/apernet/quic-go v0.59.1-0.20260425001925-6c6cc9bcb716`

Selected source:

- repository `apernet/quic-go`
- repository metadata explicitly says it is a **modified fork of `quic-go/quic-go` for APernet/Hysteria**
- exact commit `6c6cc9bcb716256af2977c4b3b8a2924269e9718`
- commit date 2026-04-25
- commit purpose: support omitted DATAGRAM transport parameter with an assumed peer size
- tree `01ed6f54049664ae875a466f819fb750b6ddca06`
- recursive manifest: `https://api.github.com/repos/apernet/quic-go/git/trees/01ed6f54049664ae875a466f819fb750b6ddca06?recursive=1`
- language Go
- license MIT
- default branch `master`

Release drift is explicit:

- base tag `v0.59.1` is older than the selected pseudo-version;
- current reviewed fork tag feed on 2026-08-14 reaches `v0.61.0` at `579ee19d5b54c4f9320ffca668113c3513a138e5`;
- the selected Xray dependency therefore must not be described as “latest quic-go”.

The selected commit itself adds non-standard/compatibility behavior around omitted QUIC DATAGRAM transport parameters. It includes integration tests covering assumed support versus standards-compliant rejection. This fork-specific behavior must be kept separate from base RFC semantics and capability-gated by the parent engine.

## 20-gate reconciliation

|#|Gate|Result|Evidence / decision|
|---:|---|---|---|
|1|Top implementations|PASS|IETF QUIC standards are the protocol authority. For the pinned Xray engine, the exact selected implementation is APernet's MIT `quic-go` fork; upstream `quic-go/quic-go` is its parent/reference, not silently substituted.|
|2|Sources pinned|PASS|RFC9000/9001/9002/9369 + exact APernet pseudo-version/commit/tree + exact Xray pin. Stable/fork/latest-version distinctions are explicit.|
|3|Licenses|PASS|APernet quic-go is MIT; Xray is MPL-2.0; RFC rights are standards-text/code-component rights rather than implementation-source licensing. Exact dependency notices/SBOM remain shipping requirements.|
|4|Source tree|PASS|Complete selected fork tree and complete Xray tree are pinned; transport, TLS, HTTP/3, datagram, integration tests, build/workflow/dependency areas are source-visible.|
|5|Languages/build|PASS|Selected fork and Xray are Go modules; exact dependency version is in Xray go.mod. Build/test/package behavior follows the selected engine/fork rather than an abstract QUIC library.|
|6|Architecture|PASS|Application streams/datagrams -> QUIC streams/DATAGRAM frames -> TLS-derived packet protection -> QUIC loss/congestion/path logic -> UDP -> IP. QUIC TLS is not TLS records over UDP; RFC9001 integration is distinct from TLS-over-TCP.|
|7|Engine integration|PASS|Use the QUIC implementation selected/tested by the parent engine. Do not replace the APernet fork with upstream latest without regression/capability review, and do not implement custom QUIC cryptography.|
|8|UI/menu|PASS/N-A|QUIC is a parent transport/capability, exposed only where an engine/profile supports it. Advanced options may include datagrams, congestion/path/idle/MTU controls only when source-backed; no standalone VPN card.|
|9|Config/import/export/URI/QR|PASS|QUIC parameters live inside the parent protocol/transport configuration. No standalone QUIC VPN subscription URI/QR is invented. ALPN, TLS identity, datagram support and transport parameters remain distinct typed state.|
|10|Persistence/secrets|PASS|TLS/QUIC credentials, tickets/tokens and session state are security-sensitive; server name/ALPN/transport limits are policy metadata. Resumption/0-RTT state is runtime-sensitive and not ordinary portable profile data.|
|11|Platforms|PASS for research|Go engine is cross-platform, but UDP sockets, PMTU, migration, mobile background/network-change behavior and native VPN wrappers vary. Exact platform certification remains later.|
|12|Logs/diagnostics|PASS|Separate UDP reachability, version negotiation, TLS/certificate/ALPN, transport-parameter, stream/datagram, idle/handshake timeout, path migration/MTU, loss/congestion and parent-application failures. Do not expose TLS/QUIC secrets.|
|13|Assets/localization|PASS/N-A|No canonical standalone consumer QUIC app asset set; parent client assets remain separately licensed.|
|14|Forks/alternatives|PASS|APernet fork versus upstream quic-go is explicitly identified. QUIC v1/v2, streams versus DATAGRAM, HTTP/3 and Hysteria/TUIC are distinct version/capability/application layers, not aliases.|
|15|Issues/releases/advisories|PASS|Selected fork commit is a deliberate DATAGRAM compatibility patch; current fork tag feed has advanced to v0.61.0 after the Xray pin. Exact parent-engine dependency must remain pinned and upgrades must pass regression tests rather than floating automatically.|
|16|Official docs|PASS|RFC9000/9001/9002/9369 and canonical source are primary. Parent fork/source behavior overrides generic tutorials for exact implementation facts.|
|17|Tests/CI|PASS|Selected commit includes integration tests for its DATAGRAM transport-parameter behavior; quic-go tree includes extensive unit/integration/interoperability test infrastructure; Xray shared tests remain separately mapped. Product network/device/server testing is later certification evidence.|
|18|Store/privacy/security|PASS|QUIC integrates TLS security and encrypts most transport metadata, but 0-RTT has replay considerations and connection IDs/path migration expose operational metadata. Certificate verification/ALPN must fail safely; Store/privacy follows parent app/network behavior.|
|19|Reuse decision|PASS|**ENGINE-SELECTED QUIC LIBRARY / NO CUSTOM QUIC.** For Xray paths retain APernet's exact tested fork until an intentional upgrade; other engines may use their own maintained QUIC stack.|
|20|Open uncertainties|PASS|Exact v1/v2 support matrix, 0-RTT policy, DATAGRAM compatibility extension behavior, congestion algorithms, migration/multipath evolution, PMTU, HTTP/3 interactions, latest fork security drift and device/network interoperability remain V2/deployment/certification work.|

## Final V1 decision

All 20 gates are evidence-backed or correctly transport-layer N/A bounded. Entry 083 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
