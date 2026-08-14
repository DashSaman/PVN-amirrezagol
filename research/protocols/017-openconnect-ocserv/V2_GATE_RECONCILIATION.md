# Entry 017 — OpenConnect / ocserv-compatible — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

| # | Official V2 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Server ecosystem | PASS | pinned ocserv + proprietary Cisco interoperability reference in `reference-v2/SERVER_IMPLEMENTATIONS.md` |
| 2 | Installer/deployment projects | PASS | canonical ocserv build/config/deploy and no-blind-image rule in `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS | `SERVER_INSTALL_MATRIX.md` |
| 4 | Server control/UI map | PASS | config/service/`occtl` map; canonical web UI is evidence-backed N/A in `SERVER_UI_AND_MENUS.md` |
| 5 | Client install matrix | PASS | OpenConnect v9.21/frontends/platform paths in `CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI map | PASS | CLI/OpenConnect-GUI/NetworkManager mapped separately in `CLIENT_UI_AND_MENUS.md` |
| 7 | Cryptographic design | PASS | `CRYPTOGRAPHY.md`; TLS/DTLS and 1.5.0 security boundary explicit |
| 8 | Data path | PASS | `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Topologies | PASS | `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/release/license/activity | PASS | OpenConnect v9.21 exact commit/LGPL and ocserv 1.5.0 exact commit/GPLv2+ in `REFERENCE_INDEX.md` |
| 12 | Supply-chain/security | PASS | signed release evidence, dependency/license dossier, ocserv 1.5.0 security fixes, no arbitrary image selection |
| 13 | Upgrade/uninstall/rollback | PASS | server/client packaging/lifecycle researched; exact runtime receipts unclaimed |
| 14 | Differences/uncertainties | PASS | libopenconnect vs frontends vs ocserv vs Cisco proprietary references and platform/runtime boundaries explicit |
| 15 | Reference index | PASS | `reference-v2/REFERENCE_INDEX.md` |
| 16 | Latest handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_ANYCONNECT_OCSERV_V2_COMPLETE.md` in same checkpoint |

All 16 applicable gates are evidence-backed without equating ocserv with Cisco proprietary implementation.

**Entry 017 — OpenConnect / ocserv-compatible: `COMPLETE-REFERENCE-v2`.**
