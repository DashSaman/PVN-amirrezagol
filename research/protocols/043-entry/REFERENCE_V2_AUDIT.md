# 043 — Hysteria2 — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion; no runtime/device/Store/interoperability certification.

## Exact 16 gates
1. **Server ecosystem — PASS.** Current upstream source, tree, MIT license and stable v2.12.1 release pinned independently of v1.
2. **Installer/deployment projects — PASS.** Official release/hash/service/container/source lifecycle and supply-chain pin policy mapped.
3. **Server install matrix — PASS.** Official major platform assets plus bounded OCI/Kubernetes/mobile treatment documented.
4. **Server UI/menu maps — PASS.** Canonical CLI/config surface mapped; first-party web panel N/A.
5. **Client install matrix — PASS.** Official broad platform binary/core matrix and platform-specific local modes mapped.
6. **Client UI/menu maps — PASS.** Endpoint/auth/port-hop/obfs/TLS-QUIC/rate/TUN/routing controls mapped; GUI wrapper claims bounded.
7. **Cryptographic design — PASS.** Standard QUIC/TLS, HTTP/3 auth, cert trust and Salamander's non-security obfs role precisely separated.
8. **Data path/wire flow — PASS.** HTTP/3 auth 233, TCP stream request and UDP DATAGRAM/session/fragment framing source/spec backed.
9. **Ports/transports/handshake — PASS.** QUIC/UDP, configurable/hopping ports, TLS->HTTP3 auth ordering and TCP/UDP split explicit.
10. **Deployment topologies — PASS.** Proxy/TUN/relay/ACME/masquerade/obfs/port-hop/ACL topologies mapped.
11. **Source/license/activity pins — PASS.** Current master=architecture pin, stable release/hash asset, MIT and app/core/extras dependency identity explicit.
12. **Security/supply-chain risks — PASS.** Advisories, 2.9.2 floor, current SBOM requirement, TLS bypass, auth/ACME/client-key/obfs secrets and artifact hashes explicit.
13. **Upgrade/uninstall/rollback — PASS.** Binary/service/config/certs/auth/masquerade/routing state separated; exact rollback release required.
14. **Differences/uncertainties — PASS.** Wrapper/embedding, exact current dependency advisories, platform socket/TUN behavior, Store/device/live interop/performance remain bounded.
15. **REFERENCE_INDEX — PASS.** Complete granular dossier linked.
16. **Continuation — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **044 — TUIC**.

Decision: **COMPLETE-REFERENCE-v2**.
