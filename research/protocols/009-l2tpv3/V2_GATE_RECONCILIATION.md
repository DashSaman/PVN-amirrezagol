# Entry 009 — L2TPv3 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/classic-tunnels-family/l2tpv3-reference-v2/`, especially `ENTRY_009_V2_GATE_RECONCILIATION.md` and `REFERENCE_INDEX.md`.

| # | Official V2 gate | Result | Evidence boundary |
|---:|---|---|---|
| 1 | Server/peer ecosystem | PASS | Linux kernel, iproute2, go-l2tp/ql2tpd and Cisco references mapped |
| 2 | Installer/deployment projects | PASS | `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | OS/container/orchestration matrix | PASS | `SERVER_INSTALL_MATRIX.md` |
| 4 | Server/control UI | PASS | `SERVER_UI_AND_MENUS.md` |
| 5 | Client install matrix | PASS / N/A-CONSUMER | `CLIENT_INSTALL_MATRIX.md`; infrastructure peers mapped instead of fake consumer clients |
| 6 | Client UI map | PASS / N/A-CONSUMER | `CLIENT_UI_AND_MENUS.md` |
| 7 | Cryptographic/security design | PASS | `CRYPTOGRAPHY.md`; no native confidentiality explicitly preserved |
| 8 | Data path/wire flow | PASS | `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | direct IP protocol 115, UDP/control/static-vs-dynamic distinctions in `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Topologies | PASS | `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/license/activity pins | PASS | Linux kernel `2f1baf1fc8929e6c48370be543ad028ac7ad4131`; iproute2 `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`; go-l2tp `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`; Cisco proprietary interop reference |
| 12 | Supply-chain/security | PASS | privilege/kernel/module/bridge/cookie/control-secret risks documented |
| 13 | Lifecycle | PASS | delete/rollback/module/package/network cleanup researched; runtime receipts unclaimed |
| 14 | Differences/uncertainties | PASS | L2TPv3 != L2TPv2/IPsec; static != dynamic; cookie != encryption; entry 010 protection separate |
| 15 | Reference index | PASS | `REFERENCE_INDEX.md` |
| 16 | Handoff | PASS | same-checkpoint classic-tunnels handoff names next unfinished entry |

The prior PENDING decision relied only on runtime pseudowire/interoperability/packet-capture evidence outside the written V2 gate. Those remain later certification tasks.

Reuse decision: infrastructure pseudowire capability only; hide from consumer protocol pickers unless an operator/site-to-site product explicitly needs it.

**Entry 009 — L2TPv3: `COMPLETE-REFERENCE-v2`.**
