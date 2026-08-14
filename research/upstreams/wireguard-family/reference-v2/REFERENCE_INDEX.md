# WireGuard / AmneziaWG — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Scope: shared evidence for entries **002 WireGuard** and **003 AmneziaWG**. The entries remain distinct: WireGuard is the baseline protocol/family; AmneziaWG is a versioned derivative with additional packet-format/obfuscation behavior. This folder is research/reference only and does not claim PVNetwork implementation or production certification.

## Current state

- Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`
- Research/reference category coverage: **SOURCE/REFERENCE COMPLETE**
- Strict tracker promotion: **NOT COMPLETE-REFERENCE-v2 — EXECUTION-BLOCKED**
- Implementation: **NOT IMPLEMENTED**
- Source provenance and exact component pins: `../SOURCE_REVISIONS.md`
- Formal gate reconciliation: `ENTRY_002_003_V2_GATE_RECONCILIATION.md`

Every mandatory v2 category now has traceable evidence. Remaining gaps require external runtime, signing, Store or multi-peer interoperability environments and must not be fabricated.

## Mandatory v2 files

| Required file | Current evidence state | Purpose |
|---|---|---|
| `SERVER_IMPLEMENTATIONS.md` | REFERENCE-PASS | kernel/userspace implementations, operational server/peer distinction, AWG variants |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | REFERENCE-PASS | official/community deployment projects, panels and supply-chain review |
| `SERVER_INSTALL_MATRIX.md` | REFERENCE-PASS | OS/package/container/backend matrix; runtime receipts remain external |
| `SERVER_UI_AND_MENUS.md` | REFERENCE-PASS | management-plane/UI map; no fictitious canonical WireGuard admin UI |
| `CLIENT_INSTALL_MATRIX.md` | REFERENCE-PASS | platform/package/install matrix; device receipts remain external |
| `CLIENT_UI_AND_MENUS.md` | REFERENCE-PASS | source-backed client UI/menu inventory across selected clients |
| `CRYPTOGRAPHY.md` | REFERENCE-PASS | authoritative WireGuard primitives and AWG non-equivalence rules |
| `DATA_PATH_AND_WIRE_FLOW.md` | REFERENCE-PASS | kernel/userspace path, routing, roaming, return flow |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | REFERENCE-PASS | UDP/handshake/AllowedIPs/endpoint/AWG packet distinctions |
| `DEPLOYMENT_TOPOLOGIES.md` | REFERENCE-PASS | remote access, routing peer, site-to-site, split/full tunnel, control-plane boundaries |
| `REFERENCE_INDEX.md` | synchronized | this compact recovery index |

## Additional deep evidence

### Apple platform boundary and Store provenance

- `APPLE_ENTITLEMENTS_AND_EXTENSION_BOUNDARY.md`
- `APPLE_BUILD_IDS_AND_STORE_PROVENANCE.md`

Resolved source-level facts include containing-app / packet-tunnel-extension bundle derivation, App Group/signing boundary, WireGuard developer-supplied app IDs and the pinned AmneziaWG Apple identifiers/version metadata.

Public Store product identity is kept separate from source-to-binary provenance. The pinned AmneziaWG Apple source advertises marketing version `3.0.1`, while the Store listing reviewed during this campaign showed `2.0.2`; therefore current Store binary provenance is explicitly **not inferred**.

### wg-easy v15.3.0 management/deployment boundary

- `WGEASY_V15_3_SECURITY_AUDIT.md`
- `WGEASY_V15_3_OCI_PIN.md`
- `WGEASY_V15_3_STATE_CHANGING_API_GUARD_MATRIX.md`
- `WGEASY_V15_3_REQUEST_BOUNDARY_AND_PROXY.md`
- `WGEASY_V15_3_FRAMEWORK_REQUEST_SEMANTICS.md`
- `WGEASY_V15_3_NITRO_DEPENDENCY_BOUNDARY.md`

The committed package/lock pair pins Nuxt `3.21.5`, `nitropack` `2.13.4` and h3 `1.15.11` on the relevant Nitro-2 path. Exact h3 request helper semantics were reviewed from canonical h3 v1.15.11 source. The dossier records that forwarded host and forwarded client IP are opt-in in those helpers while `x-forwarded-proto: https` can affect default protocol derivation.

A same-looking current Nitro Git tag was **not** treated as the exact package provenance after its checked tree identified itself as Nitro 3 beta-era source. wg-easy's committed lockfile remains authoritative for installed package versions.

## Primary evidence pins

### WireGuard baseline

Parent dossier pins:

- `wireguard-go`: `ecfc5a8d54462e18e13c72173e2623d16d8e25a0`
- WireGuard Windows: `4e6726c23ae9c5cb58e0c9910f3b7515621d133d`
- WireGuard Android: `e7b3a3c118836e112620b1302a8ba1873ad4daac`
- WireGuard Apple: `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`

Canonical protocol/source family:

- `https://www.wireguard.com/protocol/`
- `https://git.zx2c4.com/`

### AmneziaWG

Parent dossier pins:

- `amneziawg-go`: `1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`
- `amneziawg-android`: `d6cd6647465a9a593aa9ccadbbd20c44bf600d5b`
- `amneziawg-apple`: `e5410a539f28b8ce5dd1d060c45e4fa555e9a210`
- `amneziawg-windows-client`: `c8fa887db05ade03b9281b0e9de60579f744f995`
- `amneziawg-windows`: `1326e9bbdc71be88ddcc20925e092c6f5b9513da`

Authoritative project families:

- `https://github.com/amnezia-vpn/amneziawg-go`
- `https://github.com/amnezia-vpn/amneziawg-linux-kernel-module`
- cross-platform product reference: `https://github.com/amnezia-vpn/amnezia-client`

## Separation rules

1. WireGuard has symmetric protocol peers; “server” is an operational routing role, not a distinct handshake role.
2. Tailscale, NetBird and similar products add coordination/control planes and are not interchangeable with a canonical WireGuard engine.
3. AmneziaWG behavior must be attributed to a pinned generation/source; do not generalize one generation to another.
4. Do not claim AWG changes WireGuard cryptographic primitives unless authoritative source/spec evidence demonstrates that change.
5. Licenses remain component-specific across userspace, platform clients and kernel modules.
6. Third-party panels are independent management planes whose auth, persistence, container privileges and supply chain require their own review.

## Strict external blockers

All remaining gaps require environments not available to this agent run:

- live install/start/upgrade/rollback/uninstall receipts on representative server/container hosts;
- clean install/update/uninstall receipts on representative Windows/Android/Apple clients;
- Apple archive/signing/TestFlight/App Store build-to-source correspondence and real-device Network Extension execution;
- built wg-easy/Nitro container behavior behind an exercised reverse proxy;
- executed AWG generation × kernel/userspace × platform interoperability matrix.

These blockers are not missing source research and do not justify idling the overall campaign.

## Promotion decision

Entries 002/003 remain **PENDING** in the strict `COMPLETE-REFERENCE-v2` tracker because the repository work unit requires the execution receipts above before promotion.

Recommended internal state:

- 002 WireGuard: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 003 AmneziaWG: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`

## Exact next action

Checkpoint this work unit as externally blocked, preserve the exact missing execution environments, then immediately select and execute the next independent `COMPLETE-REFERENCE-v2` family from the machine-readable backlog. Do not repeat source research already reconciled here unless upstream evidence materially changes.
