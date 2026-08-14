# Entry 012 — PPTP — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/classic-tunnels-family/pptp-reference-v2/ENTRY_012_V2_GATE_RECONCILIATION.md`, its dedicated V2 dossier, and `research/upstreams/classic-tunnels-family/REFERENCE_PINS_2026-08-14.md`.

PPTP remains **LEGACY / INSECURE / COMPATIBILITY-ONLY**. Completion here is reference coverage, never a recommendation to deploy it.

| # | Official V2 gate | Result |
|---:|---|---|
| 1 | Server ecosystem | PASS — RRAS, RouterOS and Linux reference categories mapped; active accel-ppp server/interoperability reference pinned |
| 2 | Installer/deployment projects | PASS |
| 3 | Server matrix | PASS |
| 4 | Server UI/control map | PASS |
| 5 | Client install matrix | PASS — native/proprietary and historical OS paths classified without claiming modern cross-platform support |
| 6 | Client UI map | PASS |
| 7 | Cryptographic/security design | PASS — obsolete MS-CHAP/MPPE/RC4-era security boundary explicit |
| 8 | Wire flow | PASS |
| 9 | Ports/transports/handshake | PASS — TCP1723 control and GRE IP protocol 47 data explicitly separated |
| 10 | Topologies | PASS |
| 11 | Source/license/activity pins | PASS for applicable selected/current references — accel-ppp `4f562467dbdf819395e138617c2a057e02595b9e`, GPL-2.0 and active as of review; RRAS/RouterOS are proprietary vendor references. Historical pptpd/pptp-client are explicitly non-selected legacy categories, so an implementation source freeze for them is not applicable unless a future lab deliberately selects one. |
| 12 | Security/supply-chain | PASS |
| 13 | Lifecycle | PASS — retirement/migration and removal preferred; live receipts unclaimed |
| 14 | Differences/uncertainties | PASS |
| 15 | Reference index | PASS |
| 16 | Handoff | PASS — same-checkpoint exact continuation |

The older gate file's “historical pin residual” is a conditional future-lab requirement, not a current reusable-project gap: the repository explicitly declines to select those obsolete Linux projects. Evidence-backed non-selection is preferable to inventing or elevating an abandoned source just to create a pin.

Reuse decision: do not implement by default; retain only as explicit migration/legacy-interop knowledge with warning UX and exact-demand gating.

**Entry 012 — PPTP: `COMPLETE-REFERENCE-v2`.**
