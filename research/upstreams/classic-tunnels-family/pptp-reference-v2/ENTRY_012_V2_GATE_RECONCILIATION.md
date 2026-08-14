# Entry 012 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Entry: **012 — PPTP**

Purpose: reconcile the PPTP dossier against every category in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`, while preserving PPTP's obsolete-security status and refusing to invent current-maintenance/source-freeze evidence for historical Linux implementations.

## Status vocabulary

- `REFERENCE-PASS`: category has traceable reference evidence.
- `REFERENCE-PASS-WITH-HISTORICAL-PIN-RESIDUAL`: historical open-source implementation category is documented but exact canonical immutable source/license/maintenance pin must be materialized before any lab/source freeze.
- `BLOCKED_EXTERNAL`: strict proof requires live exact-version runtime/interoperability evidence.

## 1. Server implementation ecosystem mapped

`REFERENCE-PASS-WITH-HISTORICAL-PIN-RESIDUAL`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- Windows RRAS mapped as primary proprietary historical server;
- MikroTik RouterOS mapped as current proprietary legacy interop target with vendor security warning;
- Poptop/pptpd + pppd mapped as historical Linux server architecture, not promoted as maintained production dependency.

Residual: immutable canonical pptpd/Poptop source/release/license/activity must be materialized only if a legacy lab actually requires it.

## 2. Installer/deployment projects reviewed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`

Covers RRAS explicit legacy enablement, RouterOS built-in ownership, historical Linux pptpd stack, community scripts/images, cloud GRE/NAT limitations, containers/Kubernetes, upgrade/rollback and retirement/uninstall.

## 3. Server OS/container/orchestration matrix completed

`REFERENCE-PASS`

Evidence:

- `SERVER_INSTALL_MATRIX.md`

Covers Windows Server 2025, older selected Windows releases, RouterOS, historical Linux, cloud, containers and explicit no-new-Kubernetes path.

## 4. Server UI/control-plane menus completed

`REFERENCE-PASS`

Evidence:

- `SERVER_UI_AND_MENUS.md`

Maps RRAS, RouterOS and historical Linux control domains plus a normalized legacy/migration-focused PVNetwork admin UI and security controls.

## 5. Client install matrix completed

`REFERENCE-PASS-WITH-HISTORICAL-PIN-RESIDUAL`

Evidence:

- `CLIENT_INSTALL_MATRIX.md`

Covers Windows native legacy PPTP, Android legacy/device-dependent behavior, Apple native removal, Linux historical pptp-client/pppd, NetworkManager component boundary and RouterOS client.

Residual: exact immutable historical Linux pptp-client/source/license/maintenance state must be materialized before any lab/source freeze.

## 6. Client UI/menu maps completed

`REFERENCE-PASS`

Evidence:

- `CLIENT_UI_AND_MENUS.md`

Maps Windows native/system-owned state, Android legacy policy, Apple no-native path, Linux/RouterOS legacy UI concepts, layered errors and mandatory migration UX.

## 7. Cryptographic/security design documented

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`

Documents PPP authentication, MPPE/RC4 legacy encryption, MS-CHAP-related key derivation, no custom crypto upgrade, secret classes, NAT-helper non-security and explicit obsolete classification.

## 8. Data path/wire flow documented

`REFERENCE-PASS`

Evidence:

- `DATA_PATH_AND_WIRE_FLOW.md`

Covers separate TCP1723 control and GRE protocol47 data, PPP/MPPE path, Windows/Linux/RouterOS ownership, NAT helper behavior, reconnect, routing/DNS, observability, cleanup and migration telemetry.

## 9. Ports/transports/handshake documented

`REFERENCE-PASS`

Evidence:

- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`

Documents TCP1723 control, GRE IP protocol47 data, Call IDs, PPP LCP/auth/MPPE/NCP, NAT/PPTP ALG, multi-client NAT, firewall, MTU and failure categories.

## 10. Deployment topologies documented

`REFERENCE-PASS`

Evidence:

- `DEPLOYMENT_TOPOLOGIES.md`

Covers Windows/RRAS, Windows/RouterOS, RouterOS client, historical Linux lab, NAT/CGNAT, cloud/GRE, load-balancer/HA limitations, segmentation and parallel migration.

## 11. Source/license/activity pins recorded

`REFERENCE-PASS-WITH-HISTORICAL-PIN-RESIDUAL`

Evidence:

- Windows and RouterOS are proprietary/native/vendor reference targets;
- PPP standards and maintained pppd family are covered in shared classic-tunnels evidence;
- Poptop/pptpd and Linux pptp-client are explicitly historical source categories.

Residual:

- exact immutable canonical pptpd/Poptop and pptp-client release/commit/license/activity pins are not fabricated. They become mandatory only if a future implementation/lab selects those projects.

## 12. Security/supply-chain risks recorded

`REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- install/UI matrices.

Includes obsolete protocol security, weak-auth/MPPE fallback prohibition, root/helper/firewall changes, opaque installer rejection, GRE/NAT complexity and migration requirement.

## 13. Upgrade/uninstall/rollback researched

`REFERENCE-PASS at reference layer / BLOCKED_EXTERNAL for receipts`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `CLIENT_INSTALL_MATRIX.md`

Retirement is the preferred lifecycle. Rules cover parallel replacement, disabling listener, removing TCP1723/GRE/helper rules, credentials/routes and legacy packages/config.

## 14. Differences/uncertainties explicit

`REFERENCE-PASS`

Dossier explicitly separates:

- TCP1723 control vs GRE47 data;
- PPTP transport vs PPP auth vs MPPE;
- IP protocol 47 vs imaginary port47;
- NAT helper interop vs security;
- Windows/RouterOS current legacy support vs Apple removal/Android legacy variability;
- historical Linux source vs current maintained support;
- connectivity proof vs acceptable security.

## 15. REFERENCE_INDEX links complete dossier

`SOURCE-PASS after synchronization`

Update index after this file with final state/residuals/blockers.

## 16. Latest AGENTS handoff contains exact continuation state

`SOURCE-PASS after checkpoint`

Create PPTP handoff/checkpoint, advance Run State and AGENTS pointer to the next actual v2 entry.

---

# Formal reference result

All 16 reference categories are covered, with historical Linux implementation pins intentionally left as explicit selection-time residuals.

Recommended state:

`REFERENCE-V2-EVIDENCE-COMPLETE / HISTORICAL-LINUX-SOURCE-PIN-RESIDUAL / OBSOLETE-RUNTIME-EXECUTION-BLOCKED / NOT IMPLEMENTED`

# Strict external/source blockers

Before any strict `COMPLETE-REFERENCE-v2` claim for a retained legacy combination:

1. exact Windows Server build/PPTP enablement/runtime;
2. Windows client exact build -> RRAS;
3. selected RouterOS exact release server/client interop;
4. exact immutable historical Linux client/server source pins only if selected;
5. TCP1723 + GRE47 captures;
6. Call-ID mapping;
7. PPP auth/MPPE negotiation and negative cases;
8. GRE blocked while control succeeds;
9. NAT/PPTP ALG single and multiple-client behavior;
10. CGNAT/cloud/provider GRE behavior if business-critical;
11. route/DNS/MTU cleanup;
12. OS/router update/rollback;
13. migration cutover and final listener/firewall/helper removal.

These tests do not make PPTP a recommended secure protocol; they only certify one legacy compatibility path.

# Promotion decision

Keep entry 012 `PENDING` in strict v2 tracker.

Checkpoint the evidence-complete state and continue entry 013 from actual repository state. Do not install or recommend a new PPTP server merely to satisfy research completeness.
