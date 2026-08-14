# Entry 016 — Cisco AnyConnect — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

| # | Official V2 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Server ecosystem | PASS | Cisco ASA/FTD proprietary headends + separate ocserv compatible ecosystem mapped in `reference-v2/SERVER_IMPLEMENTATIONS.md` |
| 2 | Installer/deployment projects | PASS | Cisco webdeploy/predeploy + pinned ocserv in `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS | `SERVER_INSTALL_MATRIX.md`; Cisco container/source-build correctly N/A-PROPRIETARY |
| 4 | Server UI/control map | PASS | ASA headend control concepts + FTD management boundary + ocserv CLI/file map in `SERVER_UI_AND_MENUS.md` |
| 5 | Client install matrix | PASS | Cisco 5.1.18.314 desktop/mobile variants + OpenConnect alternative in `CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI map | PASS | `CLIENT_UI_AND_MENUS.md` + existing Cisco UI/storage/diagnostics evidence |
| 7 | Cryptographic design | PASS | `CRYPTOGRAPHY.md`; TLS/DTLS separated from optional IKEv2/IPsec |
| 8 | Data path | PASS | `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Topologies | PASS | `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/release/license/activity | PASS | Cisco proprietary current release/docs; OpenConnect exact v9.21 commit/LGPL; ocserv exact 1.5.0 commit/GPLv2+ in `REFERENCE_INDEX.md` |
| 12 | Supply-chain/security | PASS | signed/current public releases, Cisco proprietary package boundary, ocserv 1.5.0 security release, shared dependency/security evidence |
| 13 | Upgrade/uninstall/rollback | PASS | Cisco predeploy/webdeploy/update paths and package changes + open package lifecycle researched; runtime receipt unclaimed |
| 14 | Differences/uncertainties | PASS | Cisco vs OpenConnect/ocserv, SSL/DTLS vs IKEv2, core VPN vs posture/modules, exact headend/version boundaries explicit |
| 15 | Reference index | PASS | `reference-v2/REFERENCE_INDEX.md` |
| 16 | Latest handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_ANYCONNECT_OCSERV_V2_COMPLETE.md` in same checkpoint |

No Cisco source visibility, vendor certification or runtime result is fabricated.

**Entry 016 — Cisco AnyConnect: `COMPLETE-REFERENCE-v2`.**
