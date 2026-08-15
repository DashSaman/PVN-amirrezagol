# 047 — NaiveProxy — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

1. Server ecosystem — PASS: stable Naive/Chromium and current Caddy forwardproxy fork pinned.
2. Installer/deployment projects — PASS: release assets/digests and xcaddy server build path mapped with moving-dependency risk.
3. Server install matrix — PASS: desktop/server/plugin/container/OpenWrt/Kubernetes bounds explicit.
4. Server UI/menu maps — PASS: Caddy/forwardproxy config/admin surface mapped; no Naive web panel invented.
5. Client install matrix — PASS: official multi-platform release/APKs plus GUI hosts bounded.
6. Client UI/menu maps — PASS: USAGE CLI/config and v2rayN GUI/import/runtime paths mapped.
7. Cryptographic design — PASS: Chromium TLS/QUIC stack security and dangerous diagnostic/PQ controls explicit.
8. Data path/wire flow — PASS: H2/H3 CONNECT, auth, padding/first-eight-read-write/fast-open and forwarding mapped.
9. Ports/transports/handshake — PASS: https/quic upstream schemes, no fixed port, padding negotiation and layer boundaries explicit.
10. Deployment topologies — PASS: direct/fronted/chained/Android/OpenWrt/ordinary-proxy interoperability mapped.
11. Source/license/activity pins — PASS: exact stable client/Chromium/server commits, root licenses and third-party-notice boundary recorded.
12. Security/supply-chain risks — PASS: rebased master warning, Caddy moving build dependencies, artifact digests/SBOM, credentials, NetLog/TLS keylog and concurrency detectability explicit.
13. Upgrade/uninstall/rollback — PASS: Naive+Chromium binary, Caddy module, config/credentials/certs and diagnostics treated separately; updates follow stable tags.
14. Differences/uncertainties — PASS: complete shipping notices/SBOM/advisories, H3 server matrix, Android hosts, Store/device/live interop/performance remain bounded.
15. REFERENCE_INDEX — PASS.
16. Continuation — PASS with promotion batch to **048 — Snell**.

Decision: **COMPLETE-REFERENCE-v2**.
