# WireGuard / AmneziaWG — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Scope: shared evidence for entries **002 WireGuard** and **003 AmneziaWG**. The entries remain distinct: WireGuard is the baseline protocol/family; AmneziaWG is documented as a versioned derivative with additional packet-shaping/obfuscation behavior. This folder is research/reference only and does not claim PVNetwork implementation or certification.

## Current state

- Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`
- State: `IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED`
- Source provenance and exact component pins remain in `../SOURCE_REVISIONS.md`.
- V1 architecture/client/dependency findings remain in the parent `wireguard-family/` dossier and are reused rather than duplicated.
- Every mandatory v2 filename now exists, but **file presence is not completion**. Residual evidence and entry-specific reconciliation remain before tracker promotion.

## Mandatory v2 files

| Required file | State | Notes |
|---|---|---|
| `SERVER_IMPLEMENTATIONS.md` | evidence started | peer/server implementations, kernel/userspace distinction, AWG variants |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | evidence started | deployment/control projects separated from engines; supply-chain review remains |
| `SERVER_INSTALL_MATRIX.md` | evidence started | OS/package/container/backend distinctions; tested receipts remain |
| `SERVER_UI_AND_MENUS.md` | evidence started | no canonical WireGuard server UI; management-plane boundary and pinned wg-easy source anchors |
| `CLIENT_INSTALL_MATRIX.md` | evidence started | official/current client/package matrix; exact platform receipts remain |
| `CLIENT_UI_AND_MENUS.md` | evidence started | pinned Android/Windows/Amnezia source-level UI anchors; Apple/import/export detail remains |
| `CRYPTOGRAPHY.md` | evidence started | authoritative WireGuard primitives plus AWG non-equivalence rule |
| `DATA_PATH_AND_WIRE_FLOW.md` | evidence started | peer routing, kernel/userspace path, roaming, keepalive |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | evidence started | UDP, Noise_IK, AllowedIPs/endpoint behavior, AWG packet deltas |
| `DEPLOYMENT_TOPOLOGIES.md` | evidence started | remote access, site-to-site, roaming, mesh, kernel/userspace, AWG and provisioning layers |
| `REFERENCE_INDEX.md` | synchronized | this file; completion remains forbidden until contract gates pass |

## Primary evidence pins

### WireGuard baseline

Use parent dossier pins:

- `wireguard-go`: `ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- WireGuard Windows: `4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- WireGuard Android: `e7b3a3c118836e112620b1302a8ba1873ad4daac`
- WireGuard Apple: `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`

Canonical protocol evidence:

- https://www.wireguard.com/protocol/
- canonical source family linked by WireGuard mirrors: https://git.zx2c4.com/

### AmneziaWG

Use parent dossier pins:

- `amneziawg-go`: `1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1` (AWG3.1-era snapshot)
- `amneziawg-android`: `d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`
- `amneziawg-apple`: `e5410a539f28b8ce5dd1d060c45e4fa555e9a210`
- `amneziawg-windows-client`: `c8fa887db05ade03b9281b0e9de60579f744f995`
- `amneziawg-windows`: `1326e9bbdc71be88ddcc20925e092c6f5b9513da`

Authoritative/current project evidence:

- https://github.com/amnezia-vpn/amneziawg-go
- https://github.com/amnezia-vpn/amneziawg-linux-kernel-module
- cross-platform Amnezia product reference: https://github.com/amnezia-vpn/amnezia-client

## Separation rules

1. A WireGuard peer can serve traffic for other peers, but the protocol does not define a special client/server handshake role. Documents may use “server” operationally while preserving this protocol fact.
2. Tailscale, NetBird and similar control-plane products are not interchangeable with the WireGuard protocol implementation; they belong in topology/control-plane comparisons, not as canonical WireGuard engines.
3. AmneziaWG changes must be described from pinned generation/source. Do not generalize one AWG generation to another.
4. Do not claim AWG replaces WireGuard cryptography unless source/spec evidence explicitly demonstrates a primitive change. Current reviewed project documentation exposes header protection, content padding, timing ranges, junk/signature packets, header/type changes and transport-message shaping on top of WireGuard-derived code.
5. License conclusions stay component-specific. The parent dossier records MIT/Apache-2.0/GPL-2.0 distinctions across userspace clients and the Linux AWG kernel module.
6. Third-party server panels are management planes, not WireGuard protocol UI. Their auth, persistence, privileges and supply chain require independent review.

## Known current regression/evidence items to preserve

- AWG3.1 `RandomTrailers` / `HandshakeCookie` behavior required a current userspace fix in the pinned research snapshot.
- Current open AWG Go issue evidence includes S4/streaming-obfuscation interoperability failures and a package-global protocol-state race report. These are research/test inputs, not proof that every platform is affected.
- Current AWG Linux issue history includes AWG2/AWG3 compatibility/source and S3/S4 interoperability questions; platform/generation compatibility must therefore be a test dimension.
- A 2026 Amnezia issue records a standalone iOS `.conf` document-import path without a registered custom URL scheme at the time of the report; this is a platform import/deep-link test input, not a permanent limitation claim.

## Residual gates before any `COMPLETE-REFERENCE-v2` promotion

- exact versioned install/update/uninstall/rollback receipts on representative server and client targets;
- pinned management-plane license/dependency/container/auth/default-exposure review;
- Apple client UI/import/export lifecycle mapping at pinned source level;
- exact QR/file/deep-link behavior by major client/platform;
- generation-specific AWG interoperability receipts;
- issue/advisory/test reconciliation against selected pins;
- entry-specific 002/003 contract checklist showing every required category has traceable evidence.

## Exact next action

Deepen server installer/panel supply-chain and authentication evidence, then Apple/import-export client evidence and AWG generation interoperability. Reconcile entries 002/003 against `FULL_PROTOCOL_REFERENCE_CONTRACT.md`; keep both tracker rows `PENDING` until every applicable gate is evidenced. Then checkpoint and continue the next required v2 family without owner prompting.