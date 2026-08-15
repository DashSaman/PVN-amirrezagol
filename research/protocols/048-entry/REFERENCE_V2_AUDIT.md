# 048 — Snell — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. This does not claim permission to implement/reverse engineer Snell, redistribute official binaries, or certify live Surge interoperability.

## Exact 16 gates
1. **Server ecosystem — PASS.** Official proprietary binary-only v5.0.1 server and community interoperability implementations are separately identified; no fake official source is created.
2. **Installer/deployment projects — PASS.** Official Linux packages/systemd capabilities plus evidence-backed lack of canonical container/orchestration project and third-party GPL deployment are mapped.
3. **Server install matrix — PASS.** Exact official Linux architectures plus bounded Mac embedded V1 and N/A Windows/container/Kubernetes/mobile server targets.
4. **Server UI/menu maps — PASS.** Official CLI/config/wizard and v5/v6 server controls mapped; no web panel invented.
5. **Client install matrix — PASS.** Surge official client generation/platform evidence plus third-party reference bounded by rights concerns.
6. **Major client UI/menu maps — PASS.** Surge proxy syntax and v4/v5/v6 controls, PSK, UDP, obfs, ShadowTLS, reuse and beta policy mapped.
7. **Cryptographic design — PASS via proprietary evidence boundary.** Public security semantics are captured; exact cipher/key/record internals are officially unpublished and explicitly N/A rather than fabricated.
8. **Data path/wire flow — PASS via proprietary evidence boundary.** Vendor-published functional TCP/UDP/QUIC/version behavior is mapped; byte-level proprietary framing is explicitly unavailable.
9. **Ports/transports/handshake — PASS.** Configurable port, UDP relay, v5 QUIC UDP-over-UDP vs ordinary UDP-over-TCP, v6 removal and separate ShadowTLS boundary explicit.
10. **Deployment topologies — PASS.** Stable, backward-compatible, QUIC, egress/systemd, beta-v6, ShadowTLS and embedded-V1 topologies bounded.
11. **Source/license/activity pins — PASS.** Official v5.0.1 package line is proprietary/no-source/no-open-license; opensnell GPL pin/release independently recorded; v6 beta state explicit.
12. **Security/supply-chain risks — PASS.** PSK, binary rights, package/version verification, historical armv7l publishing issue, community reverse-engineering/legal boundary and beta drift explicit.
13. **Upgrade/uninstall/rollback — PASS.** v4-client/v5-server compatibility, explicit v6 beta pair pinning and generation-preserving rollback required; binary/config/service/PSK state separate.
14. **Differences/uncertainties — PASS.** Official proprietary internals, v6 beta changes, exact vendor licensing/redistribution rights, third-party parity, device/Store/live interop remain explicit unknowns—not guessed.
15. **REFERENCE_INDEX — PASS.** Full dossier and reuse/rights decision linked.
16. **Continuation — PASS with promotion batch.** Tracker/state/handoff/foreground advance to **049 — SOCKS4**.

Decision: **COMPLETE-REFERENCE-v2 / PROPRIETARY-PERMISSION-BOUND**.
