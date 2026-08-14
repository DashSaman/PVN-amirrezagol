# AGENTS Handoff — 2026-08-14 — Original v1 Coverage -> OpenVPN v2

Mandatory continuation checkpoint.

## Original campaign state

All 93 numbered entries now have original-v1 research decisions or explicit shared-family evidence and the campaign is reconciled as:

**`V1-COVERAGE-HANDOFF-READY / NOT IMPLEMENTED`**

Authoritative dated reconciliation:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_V1_COVERAGE_RECONCILIATION.md`

This does not mean implementation/certification.

## Mandatory phase transition

Activate:

**`COMPLETE-REFERENCE-v2`**

Contract:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

## Active work unit

`OPENVPN-COMPLETE-REFERENCE-V2`

## OpenVPN v2 required files

Create under the OpenVPN research area a dedicated v2 folder containing at least:

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_INDEX.md`

Add more source/project subfiles when useful.

## Research rules for v2

- use current primary sources and pin revisions/releases where possible;
- distinguish OpenVPN Community Server, OpenVPN3 client core, OpenVPN Connect, Access Server and third-party installers/panels;
- inspect installer source before recording/recommending it;
- record root/admin requirements, packages/services, firewall/routing/DNS changes, exposed admin interfaces, credentials, update/uninstall/rollback and supply chain;
- do not copy third-party source/assets unless license review permits;
- exhaustive UI/menu inventories must be version/source backed;
- cryptography/wire-flow must distinguish control channel, data channel, TLS/auth, key negotiation/rekey, DCO vs userspace data path and platform differences;
- current Store/platform install rules must be rechecked from official sources when implementation/release decisions are made;
- preserve unsupported/legacy modes and security warnings explicitly.

## Exact next action

1. pin current OpenVPN Community server/core release/source and relevant OpenVPN3/OpenVPN Connect references;
2. inventory major open-source server installers/projects/panels and their current license/activity;
3. build server/client install matrices;
4. map OpenVPN server and major-client UI/menu/config surfaces;
5. build crypto/data-path/ports/handshake/topology references;
6. create `REFERENCE_INDEX.md` with exact references and residual gaps;
7. checkpoint OpenVPN v2 and immediately continue WireGuard/AWG v2 without owner prompting.
