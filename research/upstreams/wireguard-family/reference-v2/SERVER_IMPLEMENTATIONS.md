# WireGuard / AmneziaWG — Server / Peer Implementations

Review date: 2026-08-14

Status: evidence-backed reference inventory. “Server” is used operationally; WireGuard itself is peer-to-peer and does not define separate client/server handshake roles.

## WireGuard implementation families

### Linux kernel WireGuard

Canonical family: WireGuard in the Linux kernel. For Linux hosts this is the reference high-performance kernel data path on supported kernels. Administration is normally performed through `wg`/`wg-quick`, distribution networking systems, or higher-level management software; the protocol does not ship a web server-control plane.

PVNetwork reference role: interoperability baseline and deployment target, not application code to vendor into mobile/desktop clients.

### `wireguard-go`

Canonical upstream family: `git.zx2c4.com/wireguard-go`; research mirror and pinned revision are recorded in `../SOURCE_REVISIONS.md`.

Role: userspace WireGuard implementation used where an in-kernel path is absent or inappropriate. Parent research pin: `ecfc5a8d54462e18e13c72173e2623d16d8e25a0`, MIT.

PVNetwork reference role: strong reusable/core architecture candidate subject to target-platform integration, performance and Store review.

### Official platform implementations/clients

The parent dossier pins official Windows, Android and Apple source families separately. These include platform-specific service/tunnel integrations and should not be reduced to one generic “WireGuard server binary.” A desktop/mobile peer can be configured to route traffic, but product UI/service architecture differs by platform.

## Operational server patterns

A typical operational WireGuard “server” is a peer that:

- has a stable reachable endpoint/listen port;
- has one or more peer public keys configured;
- routes/forwards traffic for those peers where desired;
- applies host firewall/NAT/routing policy outside the WireGuard cryptographic protocol.

Therefore server research must distinguish:

1. protocol implementation (`kernel`, `wireguard-go`, platform driver/backend);
2. host networking configuration;
3. key/config provisioning;
4. optional management/control plane.

## Management/control-plane products

Tailscale, NetBird and similar systems can use WireGuard data-plane technology while adding identity, coordination, ACL, discovery, relay/NAT-traversal, management APIs and UI. They are useful references but are not canonical WireGuard protocol implementations and must not be listed as interchangeable with `wireguard-go`/kernel WireGuard.

## AmneziaWG userspace

Repository: `amnezia-vpn/amneziawg-go`

Parent research pin: `1b86b2ae0e493e7ea93f8c1a0f0cb6735b1551f1`, MIT, current AWG3.1-era snapshot.

The project derives from WireGuard Go and exposes generation-specific packet/header/padding/timing behavior. Current project documentation states Windows users should consume the engine through the AmneziaWG Windows integration rather than treating the standalone Go code as the complete Windows client.

PVNetwork reference role: AWG engine candidate behind a generation-aware adapter; never silently substitute for baseline WireGuard.

## AmneziaWG Linux kernel module

Repository: `amnezia-vpn/amneziawg-linux-kernel-module`

Relationship: fork/evolution of WireGuard Linux compatibility code.

License: GPL-2.0 according to the project repository.

Project installation evidence includes distribution package paths (for example Ubuntu/Debian-family and Fedora/RHEL-family instructions) plus manual/DKMS build paths. The project explicitly requires kernel-source/header/build integration and provides dynamic-debug guidance.

PVNetwork reference role: Linux peer/server deployment reference and interoperability target. GPL/kernel-module and packaging obligations are materially different from MIT userspace components and must remain separated in reuse decisions.

Primary source:

- https://github.com/amnezia-vpn/amneziawg-linux-kernel-module

## AmneziaWG platform implementations

The parent dossier separately pins:

- Android client/fork;
- Apple client/fork;
- Windows full client;
- Windows tunnel/library;
- Go userspace implementation;
- Linux kernel module.

Do not infer feature parity across these repositories. AWG generation fields and release dates can differ, and platform wrappers may lag or lead core behavior.

## Current risk evidence

Recent project issue history makes the following server/peer test dimensions mandatory:

- AWG generation compatibility across kernel/userspace/client variants;
- Linux kernel build compatibility across distro/kernel updates;
- S3/S4 and newer obfuscation field interoperability;
- multi-device userspace concurrency/race behavior;
- MTU-sensitive padding/junk behavior;
- rollback/config migration between AWG generations.

## Server/peer selection guidance for later architecture

- Baseline WireGuard Linux gateway: prefer the operating system's supported kernel implementation where appropriate.
- Cross-platform userspace/reference path: `wireguard-go` is the canonical userspace baseline candidate.
- AWG Linux gateway: evaluate the official AWG kernel module versus AWG Go by kernel/package availability and generation compatibility; do not promise automatic fallback until tested.
- Windows/Android/Apple: use platform-specific official/maintained adapters and wrappers rather than assuming a headless Linux-style server model.

## Remaining v2 gaps

This file does not complete the v2 server gate by itself. Still required:

- installer/deployment-project inventory with source/license/supply-chain review;
- explicit install matrix by OS/package/container/orchestration target;
- server/admin UI/control-panel map for major management projects;
- exact versioned deployment recipes and rollback/uninstall evidence;
- test receipts proving interop across selected implementation combinations.
