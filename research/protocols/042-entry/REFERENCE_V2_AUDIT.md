# 042 — Hysteria v1 — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only; runtime/device/interoperability certification is not claimed.

## Exact 16 gates
1. **Server ecosystem — PASS.** Exact v1.3.5 source/release/tree pinned; Hysteria2 explicitly excluded.
2. **Installer/deployment projects — PASS.** Frozen official installer/Docker/Compose/build/release paths and hash asset mapped.
3. **Server install matrix — PASS.** Legacy desktop/server releases plus Docker and evidence-backed Kubernetes/mobile N/A recorded.
4. **Server UI/menu maps — PASS.** CLI/config server surface mapped; first-party web panel N/A.
5. **Client install matrix — PASS.** Official multi-platform legacy runtime and local modes mapped; no later-mobile inference.
6. **Client UI/menu maps — PASS.** CLI/JSON5 client controls mapped; GUI N/A without exact v1-capable pin.
7. **Cryptographic design — PASS.** QUIC/TLS security boundary, certificate policy, post-QUIC auth and obfs distinction documented.
8. **Data path/wire flow — PASS.** protocol v3 control hello/rate/auth, TCP stream and UDP session/message framing source-backed.
9. **Ports/transports/handshake — PASS.** Configured port, QUIC/UDP, v1 packet adapters, ALPN and handshake ordering mapped.
10. **Deployment topologies — PASS.** Proxy/TUN/relay/TProxy/cert/container/port-hop legacy topologies bounded.
11. **Source/license/activity pins — PASS.** v1 release/commit/tree/license/build-tag and QUIC-fork pin explicit; current upstream legacy label recorded.
12. **Security/supply-chain risks — PASS.** Legacy dependency age, remote installer, hash verification, cert bypass, auth secrets and build-tag licensing explicit.
13. **Upgrade/uninstall/rollback — PASS.** Legacy binary/config/cert/service/container lifecycle separate; migration to v2 is explicit and never silent.
14. **Differences/uncertainties — PASS.** v1-v2 split, exact old dependency/advisory state, mobile GUI, runtime interop/performance remain bounded.
15. **REFERENCE_INDEX — PASS.** Complete granular dossier linked.
16. **Continuation — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **043 — Hysteria2**.

Decision: **COMPLETE-REFERENCE-v2 / LEGACY COMPATIBILITY TARGET**.
