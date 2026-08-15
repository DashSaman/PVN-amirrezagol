# 045 — AnyTLS — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only; no runtime/device/Store/interoperability certification.

## Exact 16 gates
1. **Server ecosystem — PASS.** Canonical/reference anytls-go plus sing-anytls/sing-box/independent alternatives mapped with explicit license gaps.
2. **Installer/deployment projects — PASS.** v0.0.13 cross-platform release/digests and serious implementation deployment paths recorded.
3. **Server install matrix — PASS.** Windows/Linux/macOS official assets plus bounded OCI/Kubernetes/mobile treatment.
4. **Server UI/menu maps — PASS.** canonical CLI/config surface plus exact product controls; web panel N/A.
5. **Client install matrix — PASS.** official desktop binaries, sing-box, Throne and bounded closed/mobile clients mapped.
6. **Major client UI/menu maps — PASS.** Throne dedicated AnyTLS editor/persistence evidence plus canonical URI/profile controls mapped.
7. **Cryptographic design — PASS.** TLS security boundary and exact SHA-256 password-auth request/padding behavior source-backed; hash is not password storage.
8. **Data path/wire flow — PASS.** v2 settings/SYN/SYNACK/PSH/FIN/heartbeat/server-settings/padding/pooling/TCP/UDP-over-TCP mapped.
9. **Ports/transports/handshake — PASS.** configurable port/default URI 443, TCP/TLS/auth order, v1/v2 fallback and UDP-over-stream boundary explicit.
10. **Deployment topologies — PASS.** direct/multiplex/pool/UDP-over-TCP/fallback/TUN/TLS-layer/mixed-version topologies bounded.
11. **Source/license/activity pins — PASS.** anytls-go current commit/tree/release/digests and unresolved license plus GPL references explicitly pinned.
12. **Security/supply-chain risks — PASS.** unlicensed source, asset digest, password URI, TLS insecure override, metadata privacy, padding validation, SBOM/dependency risk explicit.
13. **Upgrade/uninstall/rollback — PASS.** binary/session/config/TLS cert/password/padding/client-metadata state separated; protocol v1/v2 negotiation prevents silent incompatibility assumptions.
14. **Differences/uncertainties — PASS.** license clarification, chosen engine, third-party metadata defaults, platform/Store/live interop/performance and dependency advisories bounded.
15. **REFERENCE_INDEX — PASS.** complete granular dossier linked.
16. **Continuation — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **046 — ShadowTLS**.

Decision: **COMPLETE-REFERENCE-v2 / REFERENCE-CODE-LICENSE-UNCLEAR**.
