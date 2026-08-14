# Agent Checkpoint — 2026-08-14 — WireGuard / AmneziaWG v2 slice 9

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`

State transition: `IN_PROGRESS -> BLOCKED_EXTERNAL` for strict v2 promotion; source/reference work is complete for the current dossier.

## Completed

- Apple Xcode/bundle/build identity and public Store provenance separation.
- wg-easy v15.3.0 Nitro/h3 dependency/request boundary closure.
- Formal 16-category v2 gate reconciliation for entries 002/003.
- Synchronized WireGuard/AWG v2 reference index.
- Created `AGENTS_HANDOFF_2026-08-14_WIREGUARD_AWG_V2_9.md`.
- Advanced machine run state to `IKE-IPSEC-COMPLETE-REFERENCE-V2` entries 004-007.

## Evidence commits

- Apple build/Store provenance file: prior commit from this work unit.
- `WGEASY_V15_3_NITRO_DEPENDENCY_BOUNDARY.md`: `048fdecebb08f1a3c25d424d644da8998455b049`.
- `ENTRY_002_003_V2_GATE_RECONCILIATION.md`: `be60e18c2d4e125a244cd2775db026631ebc2264`.
- synchronized `REFERENCE_INDEX.md`: `746276940cdbd7f7d899f6f1101c46585e1c2df4`.
- handoff v2 slice 9: `0be766288c30d548f4df31a3d1fa29e4a6ed031e`.
- run-state advance: `9a73b280ba92eb805237a91ef31a725c903c80b6`.

## Checks

- All 16 research/reference categories from `FULL_PROTOCOL_REFERENCE_CONTRACT.md` have traceable evidence: PASS at source/reference level.
- Strict entries 002/003 `COMPLETE-REFERENCE-v2`: NO, because the current work unit preserves execution-only receipts as required evidence.
- No production implementation/device certification is claimed.

## Blockers

External-only:

- representative server/container install/start/upgrade/rollback/uninstall receipts;
- representative Windows/Android/Apple install/update/uninstall receipts;
- Apple signing/TestFlight/App Store build-to-source receipts and real-device extension execution;
- exercised reverse-proxy wg-easy built-image behavior;
- AWG multi-generation cross-implementation interoperability matrix.

## Do not repeat

- do not infer Store binary provenance from similar source/product identity;
- do not substitute a same-named current Nitro Git tag for the exact package locked by wg-easy;
- do not rerun source-only WireGuard/AWG research unless upstream evidence changes materially.

## Exact resume action

Continue `IKE-IPSEC-COMPLETE-REFERENCE-V2` for entries 004-007. Keep IKEv2/IKEv1 negotiation/authentication separate from ESP/AH packet protection. Start from existing strongSwan/native evidence and build the v2 server/client/install/UI/crypto/wire-flow/handshake/topology reference set.

Note: the monolithic `docs/AGENT_CHECKPOINT_LOG.md` is already very large and connector reads truncate it. This dated checkpoint is the durable append-style recovery artifact for this work unit; do not discard it merely because the legacy log was not rewritten wholesale.
