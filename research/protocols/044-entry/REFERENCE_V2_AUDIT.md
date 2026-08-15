# 044 — TUIC v5 — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only; no runtime/device/Store/interoperability certification.

## Exact 16 gates
1. **Server ecosystem — PASS.** Canonical spec has no official implementation; current ClashRS/shoes/Itsusinn implementations are separately pinned/licensed.
2. **Installer/deployment projects — PASS.** Cargo/workspace/container/config deployment paths mapped by implementation with supply-chain boundaries.
3. **Server install matrix — PASS.** Linux/Windows/macOS/FreeBSD/OCI and evidence-backed Kubernetes/mobile N/A/bounds recorded.
4. **Server UI/menu maps — PASS.** Config/CLI server controls and independent dashboard/product layers mapped; no canonical web panel invented.
5. **Client install matrix — PASS.** ClashRS, shoes, Itsusinn and canonical-list closed/mobile clients independently bounded.
6. **Major client UI/menu maps — PASS.** ClashRS converter provides exact practical TUIC fields; product routing/TUN/subscription kept outside wire protocol.
7. **Cryptographic design — PASS.** TLS/QUIC security and TLS-exporter UUID/raw-password token derivation are canonical-spec backed; 0-RTT replay boundary explicit.
8. **Data path/wire flow — PASS.** v0x05 Authenticate/Connect/Packet/Dissociate/Heartbeat, TCP stream and UDP association/fragment modes mapped from spec.
9. **Ports/transports/handshake — PASS.** No fixed port; multiplexed TLS stream/QUIC principal path, DATAGRAM vs uni-stream UDP and implementation-defined errors explicit.
10. **Deployment topologies — PASS.** Direct/TCP/Full-Cone UDP/two UDP modes/resumption/multi-bind/product-routing topologies bounded.
11. **Source/license/activity pins — PASS.** Spec + three serious current implementations have immutable pins and separate GPL/protocol-concept/Apache/MIT/copyleft boundaries.
12. **Security/supply-chain risks — PASS.** raw password/TLS keys, verify bypass, 0-RTT replay, dependency/SBOM, container/release pinning and management secrets explicit.
13. **Upgrade/uninstall/rollback — PASS.** engine binary/config/TLS credentials/routing/TUN/dashboard data are separate lifecycle objects; protocol-version migration is explicit.
14. **Differences/uncertainties — PASS.** exact selected engine/API, dependency advisories, alternative implementations, HTTP/3 integration, mobile/Store/live interop/performance remain bounded.
15. **REFERENCE_INDEX — PASS.** Complete dossier and reuse decision linked.
16. **Continuation — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **045 — AnyTLS**.

Decision: **COMPLETE-REFERENCE-v2**.
