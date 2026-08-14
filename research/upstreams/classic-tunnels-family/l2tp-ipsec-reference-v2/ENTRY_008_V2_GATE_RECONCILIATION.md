# Entry 008 L2TP/IPsec — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Decision: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`.

This reconciliation follows `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. It does not equate source/reference coverage with implementation or runtime certification.

| # | Contract gate | Evidence | State |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | `SERVER_IMPLEMENTATIONS.md` | PASS-REFERENCE |
| 2 | Official/major installer/deployment projects reviewed | `SERVER_INSTALLERS_AND_PROJECTS.md` | PASS-REFERENCE; execution pending |
| 3 | Server OS/container/orchestration install matrix | `SERVER_INSTALL_MATRIX.md` | PASS-REFERENCE; runtime rows TODO |
| 4 | Server panel/UI/menu maps | `SERVER_UI_AND_MENUS.md` | PASS-REFERENCE; exact runtime screenshots/source audits remain for selected products |
| 5 | Client install matrix across relevant OS | `CLIENT_INSTALL_MATRIX.md` | PASS-REFERENCE; Android exact-version and all device receipts pending |
| 6 | Major client UI/menu maps | `CLIENT_UI_AND_MENUS.md` | PASS-REFERENCE; runtime UI receipts pending |
| 7 | Cryptographic design | `CRYPTOGRAPHY.md` + entries 004-007 IPsec reference | PASS-REFERENCE |
| 8 | Data path/wire flow | `DATA_PATH_AND_WIRE_FLOW.md` | PASS-REFERENCE; synchronized packet/runtime traces pending |
| 9 | Ports/transports/handshake | `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | PASS-REFERENCE |
| 10 | Deployment topologies | `DEPLOYMENT_TOPOLOGIES.md` | PASS-REFERENCE; HA/migration runtime if product-scoped pending |
| 11 | Source/license/activity pins | `REFERENCE_INDEX.md`, server/client matrices | PASS-REFERENCE |
| 12 | Security/supply-chain risks | installer/server/client files + index | PASS-REFERENCE; selected artifact/digest/runtime verification remains |
| 13 | Upgrade/uninstall/rollback behavior researched | server/client install matrices | PASS-REFERENCE; actual lifecycle receipts pending |
| 14 | Protocol/server/client differences and uncertainties | all dossier files, especially index/data path/client matrices | PASS |
| 15 | `REFERENCE_INDEX.md` links/summarizes complete dossier | `REFERENCE_INDEX.md` | PASS |
| 16 | Latest AGENTS handoff exact continuation state | must be checkpointed after this reconciliation | PENDING-CHECKPOINT |

## Why strict tracker promotion remains blocked

The v2 contract is a research/reference contract, but this repository's current campaign deliberately preserves stronger execution blockers inherited from the project evidence policy. The following cannot be manufactured in this environment:

- representative server install/start/update/rollback/uninstall receipts;
- native Windows/Apple and selected Android/Linux device receipts;
- synchronized IPsec/L2TP/PPP packet and log traces;
- NAT/multi-client/rekey/network-change/MTU interoperability;
- selected appliance backup/restore and upgrade evidence;
- migration/HA receipts where included in product scope.

Therefore entry 008 must not be promoted to strict `COMPLETE-REFERENCE-v2` merely because all mandatory files exist.

## Semantic closure

The reference now consistently preserves:

- IPsec protection vs L2TP tunnel/session vs PPP/AAA/addressing;
- machine authentication vs user authentication;
- IPsec NAT-T UDP encapsulation vs inner L2TP transport;
- native OS profile ownership vs PVNetwork-owned UI/state;
- legacy compatibility vs preferred modern deployment;
- source/reference evidence vs runtime certification.

## Exact continuation

1. checkpoint entry 008 as source/reference complete but execution blocked;
2. keep strict tracker state non-complete;
3. select the next independent v2 entry/family according to the matrix and repository checkpoint policy;
4. return to entry 008 only when new execution infrastructure/evidence can close the recorded runtime gates.
