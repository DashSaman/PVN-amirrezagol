# Entry 019 — Fortinet FortiGate SSL VPN — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Scope: research/reference completion only. No runtime, appliance, Store, interoperability, MFA, posture, DTLS or production-certification receipt is fabricated.

| # | Official V2 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | proprietary FortiGate/FortiOS tunnel headend + separately identified OpenConnect client ecosystem in `reference-v2/SERVER_IMPLEMENTATIONS.md` |
| 2 | Official/major installer/deployment projects reviewed | PASS | FortiOS vendor deployment and migration; no fake open server/container in `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS | version/model retirement matrix in `SERVER_INSTALL_MATRIX.md` |
| 4 | Server panel/UI/menu maps | PASS | FortiOS 7.4.12 SSL-VPN settings/portals/auth/`ssl.root`/monitor/log map in `SERVER_UI_AND_MENUS.md` |
| 5 | Client install matrix | PASS | FortiClient 7.4.7 platform/version matrix + separate OpenConnect path in `CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI/menu maps | PASS | FortiClient Remote Access profile/connect/status + OpenConnect separation in `CLIENT_UI_AND_MENUS.md` |
| 7 | Cryptographic design | PASS | TLS/optional DTLS/auth/cert boundary and current PSIRT rule in `CRYPTOGRAPHY.md` |
| 8 | Data path/wire flow | PASS | FortiClient/FortiGate proprietary reference path + OpenConnect PPP subset in `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | configurable TLS TCP listener (443 default, 10443 official example), optional DTLS, OpenConnect PPP-over-DTLS/TLS limitations in `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Deployment topologies | PASS | full/split/cert/MFA/EMS/prelogon/TLS/DTLS/OpenConnect/migration topologies in `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/release/license/activity pins | PASS | FortiOS 7.4.12 selected proprietary tunnel baseline, FortiOS 7.6.3 retirement boundary, FortiClient 7.4.7 current reference, OpenConnect v9.21 exact commit/LGPL in `REFERENCE_INDEX.md` |
| 12 | Security/supply-chain risks | PASS | proprietary authorized image boundary, FortiGuard PSIRT/current upgrade requirement, no arbitrary server image, OpenConnect dependency/license evidence |
| 13 | Upgrade/uninstall/rollback | PASS | FortiOS tunnel-mode migration/retirement and FortiClient package lifecycle researched; 7.6.3 non-upgrade of tunnel config explicit; live receipt unclaimed |
| 14 | Protocol/server/client differences and uncertainties | PASS | legacy SSL tunnel vs Agentless vs IPsec migration, FortiClient vs OpenConnect, PPP v1 vs unsupported newer non-PPP wire mode, model/version/auth/reconnect boundaries explicit |
| 15 | `REFERENCE_INDEX.md` links complete dossier | PASS | `reference-v2/REFERENCE_INDEX.md` |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_FORTIGATE_SSLVPN_V2_COMPLETE.md` in same checkpoint names entry 020 |

All 16 applicable V2 gates are evidence-backed.

**Entry 019 — Fortinet FortiGate SSL VPN: `COMPLETE-REFERENCE-v2`.**
