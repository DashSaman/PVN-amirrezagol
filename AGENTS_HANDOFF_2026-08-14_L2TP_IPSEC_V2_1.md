# AGENTS Handoff — 2026-08-14 — L2TP/IPsec v2 slice 1

Work unit: `L2TP-IPSEC-COMPLETE-REFERENCE-V2`

Entry: 008 L2TP/IPsec.

## State

`REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / LEGACY COMPOSED COMPATIBILITY TARGET / NOT IMPLEMENTED`

Strict `COMPLETE-REFERENCE-v2` remains forbidden because representative runtime/device/interoperability receipts are external blockers.

## Dossier

`research/upstreams/classic-tunnels-family/l2tp-ipsec-reference-v2/`

All 11 mandatory v2 files now exist and are source/reference reconciled:

- SERVER_IMPLEMENTATIONS.md
- SERVER_INSTALLERS_AND_PROJECTS.md
- SERVER_INSTALL_MATRIX.md
- SERVER_UI_AND_MENUS.md
- CLIENT_INSTALL_MATRIX.md
- CLIENT_UI_AND_MENUS.md
- CRYPTOGRAPHY.md
- DATA_PATH_AND_WIRE_FLOW.md
- PORTS_TRANSPORTS_AND_HANDSHAKE.md
- DEPLOYMENT_TOPOLOGIES.md
- REFERENCE_INDEX.md

Formal reconciliation: `ENTRY_008_V2_GATE_RECONCILIATION.md`.

## Key semantics

- L2TP/IPsec is a composition of IPsec protection, L2TP tunnel/session and PPP/auth/addressing layers.
- IPsec entries 004-007 are reused but do not replace L2TP/PPP research.
- L2TP is not the confidentiality boundary.
- PPP user authentication is separate from IPsec machine authentication.
- NAT-T UDP/4500 is IPsec encapsulation; it is not a fake L2TP transport label.
- Native client profiles remain OS-owned state.
- No silent downgrade from modern protocols to L2TP/IPsec.
- Entry 008 is legacy compatibility, not a preferred new-deployment default.

## Current platform facts

- Microsoft: new Windows Server 2025 RRAS setups do not accept L2TP/PPTP by default, but administrators can explicitly enable them; upgraded existing configurations retain behavior.
- Apple: current Platform Deployment docs still list L2TP over IPsec on current Apple platforms and document MS-CHAPv2 user password + shared-secret machine authentication for this model; current macOS UI still documents L2TP over IPSec setup/options.
- Android: exact release/OEM capability remains a runtime/source matrix gate; do not claim universal current native support.
- Linux: NetworkManager-l2tp is a composition over an IPsec backend, L2TP daemon and pppd, so exact distro/backend versions matter.

## Evidence commits in this slice

- client install matrix: `360866e1cfe11738b188a63d5061b3934ff5a9a5`
- client UI/lifecycle: `d7ebbb9a503a0a9d11dbd9c2397cc973d3c72ee2`
- data path/failure boundaries: `cb54a7eb2c3144ee2e09ccf1e28dfc77ee233920`
- deployment/migration topologies: `c6c7d7832cc86f57b177bd93a532b976405f68ef`
- synchronized reference index: `9c468303065ade342f2fb9f6e4b656d267b00163`
- 16-gate reconciliation: `1b089d6b06ceeb113bb5da745f9015badfd751f8`

Earlier entry-008 evidence immediately before this run includes server implementations, cryptography, ports/handshake, installers, server install matrix and server UI/control-plane mapping.

## External blockers

- Windows native device/profile lifecycle receipts;
- Apple real-device/managed-profile receipts;
- Android exact-version/OEM capability and runtime if in scope;
- Linux exact distro NetworkManager-l2tp install/UI/runtime;
- representative Linux/RRAS/appliance server lifecycle;
- synchronized IPsec/L2TP/PPP traces;
- NAT/multi-client/rekey/network-change/MTU tests;
- migration and HA receipts if product-scoped.

## Tracker rule

Keep entry 008 `PENDING` in `research/REFERENCE_V2_COMPLETENESS.md` until the repository's strict evidence policy permits promotion. Source/reference closure alone is not tracker completion.

## Exact next action

Advance to entry 009 `L2TPv3` as the next independent classic-tunnel v2 work unit. Keep it distinct from L2TPv2/PPP remote-access semantics: L2TPv3 carries pseudowires/session payloads and has different deployment/security composition. Inventory authoritative standards, Linux/kernel/iproute2 and serious implementations, then build the 11-file v2 dossier without inheriting entry-008 client/UI/auth assumptions. If entry 009 is blocked, continue entry 010 L2TPv3/IPsec using the same separation principle.
