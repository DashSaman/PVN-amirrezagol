# Entry 018 — Palo Alto GlobalProtect — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Scope: research/reference completion only; no runtime, device, Store, HIP/posture, SAML, interoperability or vendor-certification result is fabricated.

| # | Official V2 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | proprietary PAN-OS portal/gateway + Prisma Access roles, separate OpenConnect compatible-client ecosystem in `reference-v2/SERVER_IMPLEMENTATIONS.md` |
| 2 | Official/major installer/deployment projects reviewed | PASS | vendor platform deployment and no-fake-server rule in `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS | `SERVER_INSTALL_MATRIX.md`; generic package/container/K8s are evidence-backed N/A for proprietary headend |
| 4 | Server panel/UI/menu maps | PASS | current PAN-OS 12.1 Portal/Gateway menu/control map in `SERVER_UI_AND_MENUS.md` |
| 5 | Client install matrix | PASS | official 6.3 OS/platform matrix + separate OpenConnect path in `CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI/menu maps | PASS | `CLIENT_UI_AND_MENUS.md`; platform differences and proprietary UI/trade-dress boundary explicit |
| 7 | Cryptographic design | PASS | `CRYPTOGRAPHY.md`; SSL/TLS control, SSL tunnel and IPsec/ESP data mode separated; not mislabeled as IKEv2 |
| 8 | Data path/wire flow | PASS | Portal → Gateway → SSL/IPsec tunnel lifecycle in `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | HTTPS/TLS common TCP 443, SSL tunnel and ESP IP protocol 50 with no fabricated IKE ports in `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Deployment topologies | PASS | PAN-OS, multi-gateway, internal/external, split/full, IPsec/fallback/IPsec-only, Prisma and OpenConnect compatibility boundaries in `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/release/license/activity pins | PASS | PAN-OS 12.1 docs + GP App 6.3 selected preferred/current-activity evidence as proprietary; OpenConnect v9.21 exact commit/LGPL in `REFERENCE_INDEX.md` |
| 12 | Security/supply-chain risks | PASS | authorized vendor package/update boundary, proprietary source limitation, OSS disclosure distinction, current security-release activity and OpenConnect dependency/security evidence |
| 13 | Upgrade/uninstall/rollback | PASS | vendor app/headend lifecycle and official endpoint install/uninstall/update paths researched; exact live receipt remains unclaimed |
| 14 | Protocol/server/client differences and uncertainties | PASS | Portal vs Gateway, SSL vs IPsec, proprietary app vs OpenConnect, posture/SAML/license/platform/version gaps explicitly bounded |
| 15 | `REFERENCE_INDEX.md` links complete dossier | PASS | `reference-v2/REFERENCE_INDEX.md` |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_GLOBALPROTECT_V2_COMPLETE.md` in same checkpoint names entry 019 |

All 16 applicable V2 gates are evidence-backed. Later runtime/vendor/interoperability certification remains separate.

**Entry 018 — Palo Alto GlobalProtect: `COMPLETE-REFERENCE-v2`.**
