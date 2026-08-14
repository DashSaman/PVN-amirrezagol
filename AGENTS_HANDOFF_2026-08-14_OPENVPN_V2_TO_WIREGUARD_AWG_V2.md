# AGENTS Handoff — 2026-08-14 — OpenVPN v2 -> WireGuard / AmneziaWG v2

Mandatory continuation checkpoint.

## OpenVPN v2 state

Entry 001 now has all 11 mandatory `COMPLETE-REFERENCE-v2` file categories under:

`research/protocols/001-openvpn/reference-v2/`

State:

**`V2-STRUCTURE-HANDOFF-READY / DEEP REFERENCE IN-RESEARCH / NOT IMPLEMENTED`**

Authoritative status:

`docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_OPENVPN_V2_STRUCTURE.md`

Exact version/menu/lab/install/packet receipts remain explicit residual gaps and must not be forgotten.

## Active work unit

**`WIREGUARD-AWG-COMPLETE-REFERENCE-V2`**

Entries:

- 002 WireGuard
- 003 AmneziaWG

## Required v2 structure

Create under a shared or numbered v2 folder, while keeping 002 and 003 distinct:

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

## WireGuard v2 research rules

- distinguish protocol/data-plane implementation from control-plane products such as Tailscale/NetBird;
- include official kernel implementations, wireguard-go, official Windows/Android/Apple clients and Linux tooling;
- server is normally a peer, not a special protocol role;
- document key generation/storage, Curve25519/X25519, ChaCha20-Poly1305, BLAKE2s, HKDF and NoiseIK-derived handshake only from authoritative WireGuard references;
- document UDP-only transport and configurable listen port;
- document cryptokey routing, AllowedIPs, endpoint roaming and persistent keepalive;
- distinguish kernel vs userspace paths;
- document NAT/firewall/routing/DNS as system configuration outside the protocol itself;
- include installer/control projects only after source/license/supply-chain review.

## AmneziaWG v2 rules

- use WireGuard as the base and document only versioned AWG changes/deltas explicitly;
- pin AWG generation/version;
- document AWG1/AWG2/AWG3.1 fields and current source behavior only from versioned sources;
- do not claim AWG changes cryptographic primitives unless source/spec proves it; distinguish packet obfuscation/layout/timing behavior from WireGuard cryptography;
- include official AWG Go/Windows/Android/Apple/client/server deployment projects;
- record broader dependency/SBOM footprint and exact compatibility requirements;
- preserve current AWG3.1 regression evidence around RandomTrailers/HandshakeCookie.

## Exact next action

1. create WireGuard/AWG v2 folder/reference index;
2. inventory server/peer implementations and installer projects;
3. build OS/client/server install matrices;
4. map client UI and management surfaces;
5. document cryptography and WireGuard vs AWG deltas;
6. document packet/data path, UDP handshake/roaming/AllowedIPs and topologies;
7. checkpoint entries 002–003;
8. continue IKE/IPsec v2 without owner prompting.