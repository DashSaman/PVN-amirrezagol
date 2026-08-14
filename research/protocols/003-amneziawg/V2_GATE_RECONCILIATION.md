# Entry 003 — AmneziaWG — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Scope: second-layer research/reference completion only. This is not an implementation, runtime, Store, device, interoperability, performance, or production-certification claim.

Primary evidence base: `research/upstreams/wireguard-family/` and `research/upstreams/wireguard-family/reference-v2/`. AmneziaWG is evaluated independently from baseline WireGuard even where family evidence is reused.

Governing rule: the exact completion gate is `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. Earlier family notes withheld promotion for external execution/signing/device/interoperability receipts even after finding all 16 source/reference categories evidence-backed. Those receipts remain valuable later certification evidence, but they are not hidden V2 gates under the current contract and `AGENTS.md` §16.

## Exact 16-gate result

| # | Official V2 gate | Result | Evidence / boundary |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | `reference-v2/SERVER_IMPLEMENTATIONS.md`; `amneziawg-go`, official Linux kernel module, tools and platform implementations are separated and generation-aware. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | `reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md`; official Amnezia package/source paths, control-plane references and third-party installer risks are mapped without treating them as protocol guarantees. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | `reference-v2/SERVER_INSTALL_MATRIX.md`; official narrower AWG platform paths are recorded and unsupported/unevidenced paths are not invented. |
| 4 | Server panel/UI/menu maps completed | PASS | `reference-v2/SERVER_UI_AND_MENUS.md` plus pinned wg-easy/AWG management evidence. AWG does not define a canonical web admin panel; management products are separate control planes. |
| 5 | Client install matrix completed across relevant OS targets | PASS | `reference-v2/CLIENT_INSTALL_MATRIX.md`, Apple build/entitlement evidence and parent AmneziaWG platform dossiers. |
| 6 | Major client UI/menu maps completed separately | PASS | `reference-v2/CLIENT_UI_AND_MENUS.md` and parent AWG Android/Apple/Windows research preserve platform/generation differences instead of flattening them. |
| 7 | Cryptographic design documented from authoritative specifications/source | PASS | `reference-v2/CRYPTOGRAPHY.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, pinned AWG source and canonical WireGuard baseline. The dossier explicitly does not claim a cryptographic-primitive change merely from AWG packet-format/obfuscation behavior. |
| 8 | Data path/wire flow documented | PASS | `reference-v2/DATA_PATH_AND_WIRE_FLOW.md`; generation-specific packet/header behavior is kept distinct from baseline WireGuard data-path semantics. |
| 9 | Ports/transports/handshake documented | PASS | `reference-v2/PORTS_TRANSPORTS_AND_HANDSHAKE.md`; AWG generation/config deltas are attributed to pinned source rather than generalized as ordinary WireGuard settings. |
| 10 | Deployment topologies documented | PASS | `reference-v2/DEPLOYMENT_TOPOLOGIES.md`; routing-peer/site-to-site/split/full-tunnel patterns and management/control/data-plane roles are separated. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | `../SOURCE_REVISIONS.md` pins `amneziawg-go@1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`, Android `d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`, Apple `e5410a539f28b8ce5dd1d060c45e4fa555e9a210`, Windows client `c8fa887db05ade03b9281b0e9de60579f744f995`, Windows tunnel `1326e9bbdc71be88ddcc20925e092c6f5b9513da`, and separates MIT/Apache/GPL/reuse uncertainties rather than inventing a family-wide license. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | `SERVER_INSTALLERS_AND_PROJECTS.md`, wg-easy v15.3 OCI/security/API/request-boundary audits and third-party installer cautions cover privileged deployment and transitive supply-chain risks. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | `SERVER_INSTALL_MATRIX.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, and `CLIENT_INSTALL_MATRIX.md`; generation migration/rollback uncertainty is preserved and execution receipts are not fabricated. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | `REFERENCE_INDEX.md`, `SERVER_IMPLEMENTATIONS.md`, client matrices, Apple provenance file and AWG issue/history evidence preserve generation compatibility, Store provenance and platform divergence uncertainties. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `reference-v2/REFERENCE_INDEX.md` indexes all mandatory files, AWG pins, deep management/security evidence and shared reconciliation. |
| 16 | Latest AGENTS handoff contains the exact continuation state | PASS | `AGENTS_HANDOFF_2026-08-14_WIREGUARD_AWG_V2_COMPLETE.md` is committed in the same checkpoint and names entry 004 IKEv2/IPsec as the next unfinished V2 entry. |

## Reuse decision

AmneziaWG is a **generation-aware derivative / reusable-engine and platform-reference candidate**, not a drop-in synonym for WireGuard. Exact AWG generation/configuration must remain visible in capability and diagnostics state. Reuse is component-specific and subject to the recorded MIT/Apache/GPL boundaries and any unresolved path-specific licensing confirmation; cross-generation compatibility must never be assumed.

## Preserved non-gating certification work

Future certification should execute generation × kernel/userspace × platform interoperability, live install/upgrade/rollback/uninstall, real-device/Store provenance and panel/reverse-proxy behavior against selected release pins. Those receipts remain explicitly unclaimed here.

# Formal result

All 16 applicable requirements in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` have traceable repository evidence.

**Entry 003 — AmneziaWG: `COMPLETE-REFERENCE-v2`.**
