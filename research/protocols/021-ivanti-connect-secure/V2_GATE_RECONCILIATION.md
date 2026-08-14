# Entry 021 — Ivanti Connect Secure — COMPLETE-REFERENCE-v2 Gate Reconciliation

Reviewed: 2026-08-14 UTC

Scope: research/reference completion only. No appliance, Store, device, Host Checker, SAML, interoperability or production-certification receipt is fabricated.

| # | Official V2 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | current proprietary ICS headend + separately identified OpenConnect compatible client in `reference-v2/SERVER_IMPLEMENTATIONS.md` |
| 2 | Official/major installer/deployment projects reviewed | PASS | authorized appliance/upgrade/migration model and no-fake-community-server boundary in `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS | current release/migration/platform vs generic package/container/K8s N/A in `SERVER_INSTALL_MATRIX.md` |
| 4 | Server panel/UI/menu maps | PASS | current user roles, VPN tunneling, resource policy, auth/Host Checker/logging controls in `SERVER_UI_AND_MENUS.md` |
| 5 | Client install matrix | PASS | ISAC 22.8R7 desktop + reviewed 22.8.7 mobile + OpenConnect alternative in `CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI/menu maps | PASS | proprietary ISAC profile/auth/state/posture/UI lifecycle + separate OpenConnect frontend boundary in `CLIENT_UI_AND_MENUS.md` |
| 7 | Cryptographic design | PASS | IF-T/TLS, EAP/EAP-TTLS, ESP, server identity/auth/posture separation and separate ICS IKEv2 boundary in `CRYPTOGRAPHY.md` |
| 8 | Data path/wire flow | PASS | auth → role/resource policy → IF-T/TLS/ESP tunnel → enterprise forwarding in `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | HTTPS/TLS normal TCP 443 exposure, IF-T/TLS + ESP, no fabricated UDP port, Pulse/NC/IKEv2 separation in `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Deployment topologies | PASS | split/full, IPv4/IPv6, auth/posture, desktop/mobile, OpenConnect subset and separate IKEv2 in `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/release/license/activity pins | PASS | ICS 25.1.2.1 build 15773 proprietary, ISAC 22.8R7 build 48847 proprietary, mobile 22.8.7, OpenConnect v9.21 exact commit/LGPL in `REFERENCE_INDEX.md` |
| 12 | Security/supply-chain risks | PASS | vendor-authorized image/update provenance, current security-enhancement release, exact migration paths, separate OpenConnect source/package chain |
| 13 | Upgrade/uninstall/rollback researched | PASS | current 25.1.2.1 tested upgrade/migration paths and platform-specific client lifecycle researched; live receipt unclaimed |
| 14 | Protocol/server/client differences and uncertainties | PASS | Pulse lineage vs current ICS, Pulse vs NC vs IKEv2, proprietary Host Checker/auth vs OpenConnect gaps, 25.1.2.1×22.8R7 qualification gap explicit |
| 15 | `REFERENCE_INDEX.md` links complete dossier | PASS | `reference-v2/REFERENCE_INDEX.md` |
| 16 | Latest AGENTS handoff contains exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_IVANTI_CONNECT_SECURE_V2_COMPLETE.md` in this checkpoint names entry 022 |

All 16 applicable V2 gates are evidence-backed.

**Entry 021 — Ivanti Connect Secure: `COMPLETE-REFERENCE-v2`.**
