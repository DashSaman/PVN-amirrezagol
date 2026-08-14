# Entry 010 — L2TPv3/IPsec — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/classic-tunnels-family/l2tpv3-ipsec-reference-v2/ENTRY_010_V2_GATE_RECONCILIATION.md` plus the indexed L2TPv3 and completed IPsec-family evidence.

| # | Official V2 gate | Result |
|---:|---|---|
| 1 | Server/peer ecosystem | PASS |
| 2 | Installer/deployment projects | PASS |
| 3 | OS/container/orchestration matrix | PASS |
| 4 | Server/control UI | PASS |
| 5 | Client/peer install matrix | PASS / N/A-CONSUMER |
| 6 | Client/peer UI map | PASS / N/A-CONSUMER |
| 7 | Cryptographic/security boundary | PASS |
| 8 | Data path/wire flow | PASS |
| 9 | Ports/transports/handshake | PASS |
| 10 | Deployment topologies | PASS |
| 11 | Source/license/activity pins | PASS — reuses pinned L2TPv3 kernel/iproute2/go-l2tp and strongSwan/Libreswan evidence only where applicable |
| 12 | Security/supply-chain | PASS |
| 13 | Upgrade/uninstall/rollback | PASS at reference layer; execution receipts unclaimed |
| 14 | Differences/uncertainties | PASS — plain vs protected pseudowire, protocol115 vs UDP, selective vs protected-underlay, L2TP cookie vs IKE credentials explicit |
| 15 | Reference index | PASS |
| 16 | Handoff | PASS — same checkpoint exact continuation |

Detailed evidence for every row is in the dedicated V2 directory and its full 16-gate reconciliation. The old promotion blocker was live XFRM/vendor/interoperability/fail-safe proof; that is certification evidence, not a hidden current V2 gate.

Reuse decision: advanced protected infrastructure composition; no-clear-fallback and exact selector/route ownership are mandatory architecture constraints if implemented.

**Entry 010 — L2TPv3/IPsec: `COMPLETE-REFERENCE-v2`.**
