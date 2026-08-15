# PVNetwork handoff — Hysteria2 V2 complete — 2026-08-15

After promotion: V1 **93/93**, V2 **43/93**, first PENDING **044 — TUIC**. Re-fetch live main before writes.

Hysteria2 current upstream is `apernet/hysteria@14e9fff...`, MIT; current stable release is `app/v2.12.1` (2026-08-09) with hashes and release asset digests. Protocol uses RFC9000 QUIC + RFC9221 DATAGRAM + HTTP/3 authentication/masquerade, TCP bidirectional streams, UDP session/fragment datagrams and optional Salamander obfuscation. Reviewed historical High advisories are patched from 2.9.2; current release still needs full SBOM/advisory certification before shipping.

Exact next action: entry 044 TUIC. Identify canonical TUIC specification and maintained v5 implementation(s), pin releases/source/licenses, independently map QUIC/TLS/auth/token/UUID/congestion/UDP relay/heartbeat/wire behavior, deployment/client/admin matrices and version differences. Then continue to 045 AnyTLS.
