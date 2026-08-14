# Entry 013 — SoftEther VPN Protocol — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/classic-tunnels-family/softether-protocol-reference-v2/ENTRY_013_V2_GATE_RECONCILIATION.md` and its complete dedicated V2 dossier.

| # | Official V2 gate | Result |
|---:|---|---|
| 1 | Server ecosystem | PASS — canonical `SoftEtherVPN/SoftEtherVPN` implementation mapped |
| 2 | Installer/deployment projects | PASS |
| 3 | Server matrix | PASS |
| 4 | Server UI/control map | PASS — listeners, hubs, users/auth, bridge/SecureNAT/cascade and management separated |
| 5 | Client install matrix | PASS |
| 6 | Client UI map | PASS |
| 7 | Cryptographic design | PASS |
| 8 | Wire flow | PASS |
| 9 | Ports/transports/handshake | PASS — configurable TCP/TLS listeners; product defaults not misrepresented as protocol invariants |
| 10 | Topologies | PASS |
| 11 | Source/license/activity pins | PASS — reviewed canonical source `49eb2f08641709d1af57a0d04971973ff94461db`, Apache-2.0; release refresh remains normal implementation freeze work |
| 12 | Security/supply-chain | PASS |
| 13 | Lifecycle | PASS at reference layer; runtime receipts unclaimed |
| 14 | Differences/uncertainties | PASS — native protocol separated from SSTP/L2TP/OpenVPN/EtherIP compatibility modes |
| 15 | Reference index | PASS |
| 16 | Handoff | PASS — same checkpoint exact continuation to entry 014 |

The older gate file already concluded all 16 categories had traceable evidence and withheld promotion only for exact-release runtime/client/server certification. That is not a hidden V2 gate.

Reuse decision: canonical SoftEther source is an architecture/interoperability candidate; native protocol support must remain distinct from compatibility listeners and multiprotocol attack surface should be minimized.

**Entry 013 — SoftEther VPN Protocol: `COMPLETE-REFERENCE-v2`.**
