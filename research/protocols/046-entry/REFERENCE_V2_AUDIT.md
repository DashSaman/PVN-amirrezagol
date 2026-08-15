# 046 — ShadowTLS — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

1. Server ecosystem — PASS: official MIT implementation plus sing-box alternative pinned.
2. Installer/deployment projects — PASS: Cargo, release, Docker/Compose, SIP003 and host-runtime paths mapped.
3. Server install matrix — PASS: Linux/macOS/containers plus Windows/Kubernetes/mobile bounds explicit.
4. Server UI/menu maps — PASS: CLI/config + typed sing-box controls; no web panel invented.
5. Client install matrix — PASS: official, sing-box, Throne and bounded other clients mapped.
6. Client UI/menu maps — PASS: version/strict/password/SNI/inner-proxy/security diagnostics mapped.
7. Cryptographic design — PASS: v3 SessionID HMAC, ServerRandom transforms, stateful directional HMAC and TLS1.3 strict boundary source-backed.
8. Data path/wire flow — PASS: real-handshake forwarding, validation/switch, data-server path and AppData/HMAC framing mapped.
9. Ports/transports/handshake — PASS: TCP/configured port, TLS handshake server, strict/non-strict and separate inner protocol explicit.
10. Deployment topologies — PASS: inner proxy, fallback/probe, TLS1.3/1.2, SNI/SIP003 compositions bounded.
11. Source/license/activity pins — PASS: current HEAD/tree/MIT, older tagged release and independent GPL references pinned separately.
12. Security/supply-chain risks — PASS: password, handshake metadata, release-vs-HEAD gap, dependency/SBOM, fallback, strict mode and inner-encryption boundary explicit.
13. Upgrade/uninstall/rollback — PASS: binary/config/password/handshake targets/inner proxy/container state separated; generation migration explicit.
14. Differences/uncertainties — PASS: v1/v2 demand, chosen shipping engine, current dependency advisories, Windows/mobile/live interop/performance remain bounded.
15. REFERENCE_INDEX — PASS.
16. Continuation — PASS with promotion batch to **047 — NaiveProxy**.

Decision: **COMPLETE-REFERENCE-v2**.
