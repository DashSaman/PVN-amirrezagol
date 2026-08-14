# Entry 015 — EtherIP/IPsec — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Scope: written source/reference completion only; no live interoperability, device, Store, packet-capture or production claim.

| # | Official V2 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | `reference-v2/SERVER_IMPLEMENTATIONS.md` |
| 2 | Official/major installer/deployment projects reviewed | PASS | `reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS | `reference-v2/SERVER_INSTALL_MATRIX.md`; unsupported/unselected compositions explicit |
| 4 | Server/control UI/menu maps | PASS | `reference-v2/SERVER_UI_AND_MENUS.md`; no universal protocol panel claimed |
| 5 | Client install matrix | PASS / PEER-MAPPED | `reference-v2/CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI/menu map | PASS / PEER-MAPPED | `reference-v2/CLIENT_UI_AND_MENUS.md` |
| 7 | Cryptographic design | PASS | `reference-v2/CRYPTOGRAPHY.md` + completed IPsec-family evidence; SoftEther path explicitly IKEv1-style rather than generalized |
| 8 | Data path/wire flow | PASS | `reference-v2/DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | `reference-v2/PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Deployment topologies | PASS | `reference-v2/DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/release/license/activity pins | PASS | `reference-v2/REFERENCE_INDEX.md`; SoftEther pin/license/activity + separately completed IPsec backend pins |
| 12 | Security/supply-chain risks | PASS | installer, crypto, source-dependency and completed IPsec-family security evidence |
| 13 | Upgrade/uninstall/rollback researched | PASS | deployment/lifecycle file; SoftEther OS-service ownership and native backend cleanup requirements explicit; receipts unclaimed |
| 14 | Differences/uncertainties explicit | PASS | EtherIP vs IKE vs ESP, SoftEther IKEv1-style vs other backends, selected vs unselected platform compositions explicit |
| 15 | `REFERENCE_INDEX.md` complete | PASS | `reference-v2/REFERENCE_INDEX.md` |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_ETHERIP_V2_COMPLETE.md` in same checkpoint |

All 16 applicable gates are evidence-backed. Unknown runtime/interoperability/backend-specific behavior remains bounded rather than fabricated.

**Entry 015 — EtherIP/IPsec: `COMPLETE-REFERENCE-v2`.**
