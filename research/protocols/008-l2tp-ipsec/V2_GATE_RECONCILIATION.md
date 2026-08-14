# Entry 008 — L2TP/IPsec — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Primary evidence: `research/upstreams/classic-tunnels-family/l2tp-ipsec-reference-v2/`, especially `ENTRY_008_V2_GATE_RECONCILIATION.md` and `REFERENCE_INDEX.md`. IPsec-specific cryptography/data-plane evidence is reused from the completed strongSwan family only where traceable.

Scope is research/reference completion, not runtime/device/Store/interoperability certification.

| # | Official V2 gate | Result | Evidence boundary |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | `SERVER_IMPLEMENTATIONS.md` |
| 2 | Installer/deployment projects reviewed | PASS | `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS | `SERVER_INSTALL_MATRIX.md` |
| 4 | Server panel/UI/menu maps | PASS | `SERVER_UI_AND_MENUS.md`; L2TP, IPsec, PPP/AAA ownership kept separate |
| 5 | Client install matrix | PASS | `CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI/menu maps | PASS | `CLIENT_UI_AND_MENUS.md` |
| 7 | Cryptographic design | PASS | `CRYPTOGRAPHY.md` + completed entries 004–007 IPsec evidence |
| 8 | Data path/wire flow | PASS | `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Deployment topologies | PASS | `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/license/activity pins | PASS | `REFERENCE_INDEX.md`, implementation/install matrices and reused pinned IPsec references |
| 12 | Security/supply-chain risks | PASS | installer, crypto, UI and index evidence |
| 13 | Upgrade/uninstall/rollback researched | PASS | server/client lifecycle matrices; execution receipts intentionally unclaimed |
| 14 | Differences/uncertainties explicit | PASS | IPsec protection != L2TP session != PPP/AAA; NAT-T and native profile ownership explicit |
| 15 | Reference index complete | PASS | `REFERENCE_INDEX.md` |
| 16 | Latest handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_CLASSIC_TUNNELS_V2_COMPLETE.md` in same checkpoint |

The older family reconciliation withheld tracker promotion only for live install/device/packet/interoperability receipts. The current `FULL_PROTOCOL_REFERENCE_CONTRACT.md` and `AGENTS.md` §16 do not make those hidden V2 gates. No receipt is fabricated.

Reuse decision: legacy compatibility composition; keep IPsec, L2TP and PPP/AAA state and credentials separate, and do not present it as the preferred modern default.

**Entry 008 — L2TP/IPsec: `COMPLETE-REFERENCE-v2`.**
