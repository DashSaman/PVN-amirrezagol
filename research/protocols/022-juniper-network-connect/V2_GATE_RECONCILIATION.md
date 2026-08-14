# Entry 022 — Juniper Network Connect — COMPLETE-REFERENCE-v2 Gate Reconciliation

Reviewed: 2026-08-14 UTC

Scope: reference completion for a retired/legacy family. No current ICS NC-wire support, appliance interoperability, TNCC, Store/device or runtime result is invented.

| # | Official V2 gate | Result | Evidence |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem | PASS | historical proprietary Juniper/Pulse gateway lineage + current-support uncertainty + OpenConnect client ecosystem in `reference-v2/SERVER_IMPLEMENTATIONS.md` |
| 2 | Installer/deployment projects | PASS / RETIRED-N/A | no current standalone NC server project; proprietary appliance lineage and migration decision in `SERVER_INSTALLERS_AND_PROJECTS.md` |
| 3 | Server OS/container/orchestration matrix | PASS / RETIRED-N/A | historical appliances, current ICS unverified NC wire, generic server/container N/A in `SERVER_INSTALL_MATRIX.md` |
| 4 | Server panel/UI/menu maps | PASS / LEGACY | legacy policy/control concepts mapped; no current standalone NC panel invented in `SERVER_UI_AND_MENUS.md` |
| 5 | Client install matrix | PASS | official Windows/macOS retirement + current OpenConnect alternative in `CLIENT_INSTALL_MATRIX.md` |
| 6 | Client UI/menu maps | PASS | OpenConnect/browser/TNCC/current frontend map and retired proprietary UI boundary in `CLIENT_UI_AND_MENUS.md` |
| 7 | Cryptographic design | PASS | TLS/ESP/certificate/auth/TNCC boundary and legacy-cipher warning in `CRYPTOGRAPHY.md` |
| 8 | Data path/wire flow | PASS | HTTPS auth-cookie/oNCP/ESP and IPv4-only limit in `DATA_PATH_AND_WIRE_FLOW.md` |
| 9 | Ports/transports/handshake | PASS | normal HTTPS TCP 443 + cookie/oNCP + negotiated ESP, no invented UDP port in `PORTS_TRANSPORTS_AND_HANDSHAKE.md` |
| 10 | Deployment topologies | PASS | historical, OpenConnect, TNCC/browser, ESP/TLS fallback and migration cases in `DEPLOYMENT_TOPOLOGIES.md` |
| 11 | Source/release/license/activity pins | PASS | vendor source N/A-proprietary/retired; OpenConnect v9.21 exact commit/LGPL/current changelog in `REFERENCE_INDEX.md` |
| 12 | Security/supply-chain risks | PASS | retired proprietary client/source opacity, TNCC/helper/browser risk, obsolete cipher exception boundary and maintained OpenConnect source chain |
| 13 | Upgrade/uninstall/rollback | PASS | vendor client EOL + migration to maintained current client/mode; OpenConnect package lifecycle under shared family evidence; runtime receipts unclaimed |
| 14 | Differences/uncertainties | PASS | NC vs Pulse vs IKEv2, current ICS NC-wire uncertainty, no IPv6, auth/TNCC/browser limits explicit |
| 15 | Reference index | PASS | `reference-v2/REFERENCE_INDEX.md` |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-14_JUNIPER_NC_V2_COMPLETE.md` in this checkpoint names entry 023 |

All 16 applicable V2 responsibilities are evidence-backed, including retired/N/A categories.

**Entry 022 — Juniper Network Connect: `COMPLETE-REFERENCE-v2`.**
