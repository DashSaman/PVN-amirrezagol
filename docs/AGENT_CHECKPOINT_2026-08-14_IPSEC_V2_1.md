# Agent Checkpoint — 2026-08-14 — IKE / IPsec v2 slice 1

Work unit: `IKE-IPSEC-COMPLETE-REFERENCE-V2`

State transition: source/reference work for entries 004–007 is complete; strict v2 promotion remains `BLOCKED_EXTERNAL`.

## Completed

- all 11 mandatory v2 dossier files under `research/upstreams/strongswan-family/reference-v2/`;
- strongSwan existing 6.0.7 evidence reused without duplication;
- Libreswan v5.4 added as a current serious implementation/reference with exact tag/commit/license and package/build/service evidence;
- OPNsense and pfSense IPsec control-plane/menu/source references mapped;
- Android native/strongSwan, Apple native, Windows native and Linux client installation/UI paths mapped;
- authoritative IKEv2/IKEv1/ESP/AH crypto, ports, NAT-T, data path and topologies documented;
- all 16 `FULL_PROTOCOL_REFERENCE_CONTRACT.md` research/reference categories reconciled;
- `REFERENCE_INDEX.md` synchronized to the final source/reference state;
- created `AGENTS_HANDOFF_2026-08-14_IPSEC_V2_1.md`.

## Evidence commits

- `b09974b2453d03077b1a1924f62857387b870207` — initial IKE/IPsec v2 index
- `c81f4028bf0ac057adb73426929c90f1d698d719` — server implementations
- `f6ac01939e9b2fa4a08fe3987d47ad75d9822c74` — cryptography
- `f0d7918d6bedeadc7549e621c6320cb827de84a8` — ports/transports/handshake
- `ba1a7f7234e32a9a8756a597447c6c28082c8562` — server UI/control plane
- `413de3d17fc554f657c462ddcf2ecf3525d69658` — installers/projects
- `b700d3e6638ab22903e87f9e875fcf9cd3cfacaa` — server install matrix
- `1aaa8cee7d7eceab2d60930ae29bc2dcac7b0d8e` — client install matrix
- `57a279c6460cf1e04b9615353220a66605d6f80b` — client UI/menu map
- `024e6740ba7dc1497d4990a822f33a536ac01831` — data path/wire flow
- `dd478b41cb54aa76e02bd77a3277cb752e3c935c` — deployment topologies
- `30c71e37589726d7740ae40dee0b5d79e0376e4c` — formal gate reconciliation
- `8e7b6c804241a78b6a177753f90f8bbf6f6051cb` — final reference index sync
- `6571f21fc0bcb37a9813cff2f03ee3ed8313bcfa` — handoff

## Checks

- 11 mandatory v2 file categories present: PASS.
- 16 research/reference contract categories have evidence: PASS at source/reference layer.
- strict `COMPLETE-REFERENCE-v2` for 004–007: NOT PASS because execution evidence is intentionally missing.
- implementation/production support claim: NONE.

## External blockers

- representative server install/start/upgrade/rollback/uninstall receipts;
- OPNsense/pfSense exact-release runtime UI/config lifecycle;
- OCI/Kubernetes XFRM/capability execution;
- Android/Apple/Windows/Linux real-device/runtime client matrices;
- cross-backend/server interoperability;
- IKEv1 legacy lab;
- native ESP/NAT-T packet/rekey/MTU lab;
- AH non-NAT lab.

## Do not repeat

- no generic flat `ipsec=true` model;
- no silent IKEv2->IKEv1 downgrade;
- no generic AH assumption;
- no privileged container recommendation without source/image/kernel review;
- no source/reference-to-production promotion.

## Active task after checkpoint

`L2TP-IPSEC-COMPLETE-REFERENCE-V2`

Entry 008.

Exact resume action: read entry 008 `V1_RESEARCH.md`, `classic-tunnels-family` evidence and the completed IPsec v2 reference. Then build L2TP/IPsec as a layered composition (IPsec + L2TP + PPP where applicable) with its own server/client implementations, installers, OS matrices, UIs, cryptography/wire/data flow, ports and topologies.
