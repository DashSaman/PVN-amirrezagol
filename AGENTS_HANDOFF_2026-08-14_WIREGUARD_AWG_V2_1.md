# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 1

Mandatory continuation checkpoint.

## Work unit

`WIREGUARD-AWG-COMPLETE-REFERENCE-V2`

Entries:

- 002 WireGuard
- 003 AmneziaWG

State:

**`IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED`**

## Repository-state reconciliation

`docs/AGENT_RUN_STATE.json` and the older pointer in `AGENTS.md` lagged newer repository history. Recent commits and `AGENTS_HANDOFF_2026-08-14_OPENVPN_V2_TO_WIREGUARD_AWG_V2.md` show that the campaign has already entered the mandatory v2 reference layer and activated WireGuard/AWG. This handoff follows recent repository evidence rather than reverting to the stale IPsec-v1 state.

## New evidence committed in this slice

Shared folder created:

`research/upstreams/wireguard-family/reference-v2/`

New files:

1. `REFERENCE_INDEX.md`
   - mandatory 11-file v2 checklist;
   - pinned WireGuard/AWG provenance carried from parent source-revision dossier;
   - WireGuard peer-vs-server separation;
   - AWG generation/version separation;
   - current AWG3.1 regression evidence preserved.

2. `CRYPTOGRAPHY.md`
   - authoritative WireGuard primitive set from official protocol documentation;
   - `Noise_IKpsk2_25519_ChaChaPoly_BLAKE2s` construction boundary;
   - secret-storage separation for private key, optional PSK and AWG header-protection key;
   - explicit rule not to describe AWG obfuscation/layout features as replacement cryptographic primitives without pinned proof.

3. `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
   - WireGuard UDP-only protocol transport;
   - Noise_IK-derived handshake and periodic rekey model;
   - endpoint roaming, configured-vs-runtime endpoint distinction;
   - AllowedIPs / cryptokey-routing role;
   - persistent keepalive semantics;
   - AWG junk/padding/header/signature/timing delta and regression-test matrix.

4. `SERVER_IMPLEMENTATIONS.md`
   - Linux kernel WireGuard vs `wireguard-go` distinction;
   - platform implementation separation;
   - operational “server” as a routing peer rather than a separate protocol handshake role;
   - control-plane products separated from canonical engines;
   - AWG Go and Linux kernel-module roles/license distinction;
   - server/peer selection guidance and residual install/control-plane gaps.

## Commits

- `7144685077de37f30cedef0edcfa660f87b813f0` — create shared v2 reference index.
- `5e85bfecc91b53300fa9b6aa588fbf34a5423d97` — cryptography and AWG boundary.
- `1586294aa78556f734d782473e8ccb00dd212b7c` — UDP/handshake/roaming/AWG packet deltas.
- `c81433152d6d51bdab6aa5f17b8e8ce9632bf74b` — server/peer implementation inventory.
- `e6f19e4aba5392b9e290eea63ac24bc8dfc39b65` — synchronize machine-readable run state with actual v2 campaign position.

## Primary evidence used

- WireGuard official protocol/cryptography documentation: `https://www.wireguard.com/protocol/`.
- canonical/research source pins already recorded in `research/upstreams/wireguard-family/SOURCE_REVISIONS.md`.
- official AmneziaWG Go repository/documentation: `https://github.com/amnezia-vpn/amneziawg-go`.
- official AmneziaWG Linux kernel module repository: `https://github.com/amnezia-vpn/amneziawg-linux-kernel-module`.
- current official AWG issue history used only as regression/test evidence, not universal-failure claims.

## Checks

- v2 mandatory category list is explicitly represented in `REFERENCE_INDEX.md`: PASS for structure/indexing.
- official WireGuard cryptographic/transport claims grounded in official WireGuard protocol documentation: PASS for documented baseline.
- AWG claims kept generation-specific and linked to maintained official project repositories: PASS for research wording.
- `COMPLETE-REFERENCE-v2` tracker promotion: **NOT ALLOWED YET**; several mandatory files/evidence categories remain incomplete.
- implementation/device/store certification: NOT DONE and not claimed.

## Current residual gaps

Required next files/evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `DEPLOYMENT_TOPOLOGIES.md`

Also required before strict completion:

- exact versioned install/uninstall/rollback receipts;
- installer/panel source/license/supply-chain review;
- exact UI/menu evidence by major current client;
- generation-specific AWG interop evidence and test receipts;
- entry-specific 002/003 reconciliation against the full v2 contract.

## Exact next action

1. create `SERVER_INSTALLERS_AND_PROJECTS.md` using official/current sources and separate management/control projects from engines;
2. create `SERVER_INSTALL_MATRIX.md` for Linux distributions/package paths, Windows and other meaningful peer/server targets, including kernel/userspace/AWG distinctions;
3. create `CLIENT_INSTALL_MATRIX.md` and `CLIENT_UI_AND_MENUS.md` from the pinned official platform clients;
4. close `DATA_PATH_AND_WIRE_FLOW.md` and `DEPLOYMENT_TOPOLOGIES.md`;
5. reconcile entries 002/003 and only then assess whether either row satisfies `COMPLETE-REFERENCE-v2`;
6. checkpoint and continue IKE/IPsec v2 without waiting for the owner.
