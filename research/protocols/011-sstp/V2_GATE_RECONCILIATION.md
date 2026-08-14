# Entry 011 — SSTP / MS-SSTP — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/classic-tunnels-family/sstp-reference-v2/ENTRY_011_V2_GATE_RECONCILIATION.md`, the dedicated V2 files, and the later correction `research/upstreams/classic-tunnels-family/SSTP_CLIENT_SOURCE_PIN_2026-08-14.md`.

The later correction is decisive for the only genuine source-freeze residual recorded by the older gate file: canonical Linux client is `sstp-project/sstp-client`, research freeze tag `1.0.20`, canonical short commit identifier `dd243124`, GPLv2+/GPLv2-family. A full object SHA/archive digest is correctly deferred to implementation/build SBOM freeze rather than fabricated.

| # | Official V2 gate | Result |
|---:|---|---|
| 1 | Server ecosystem | PASS — RRAS proprietary/native plus reviewed open-source interop/server references |
| 2 | Installer/deployment projects | PASS |
| 3 | Server matrix | PASS |
| 4 | Server UI/control map | PASS |
| 5 | Client install matrix | PASS — Windows native, canonical Linux SSTP path and explicitly non-native targets separated |
| 6 | Client UI map | PASS |
| 7 | Cryptographic design | PASS — TLS, SSTP binding, PPP/EAP and credential boundaries separated |
| 8 | Wire flow | PASS |
| 9 | Ports/transports/handshake | PASS — TCP443/TLS/SSTP/PPP boundaries mapped |
| 10 | Topologies | PASS |
| 11 | Source/license/activity pins | PASS — SoftEther pin retained where used; canonical `sstp-client` tag/license residual closed by correction note; proprietary Microsoft evidence treated as vendor reference |
| 12 | Security/supply-chain | PASS |
| 13 | Lifecycle | PASS at reference layer; runtime receipts unclaimed |
| 14 | Differences/uncertainties | PASS |
| 15 | Reference index | PASS with correction note treated as later authoritative evidence |
| 16 | Handoff | PASS — same checkpoint exact continuation |

Reuse decision: Windows native first on Windows; Linux/Unix backend remains GPL-sensitive and separately packaged/reviewed; protocol reachability over 443 must never be confused with generic HTTPS compatibility.

**Entry 011 — SSTP / MS-SSTP: `COMPLETE-REFERENCE-v2`.**
