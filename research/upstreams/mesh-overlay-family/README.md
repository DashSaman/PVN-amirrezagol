# Mesh / Overlay Client Family — Shared Research Dossier

Related matrix entries include 056 Tailscale, 057 ZeroTier, 058 NetBird, 059 Netmaker, 060 Nebula, 061 Tinc and 062 innernet.

Research state: `IN-RESEARCH`.

These projects are not interchangeable with a simple VPN protocol core. Many include identity, peer discovery, coordination/control-plane and policy components in addition to their data plane. PVNetwork must decide whether it wants compatibility/integration, embedded client components, or only architectural lessons.

## Pinned projects reviewed so far
### Tailscale
- `tailscale/tailscale@ab0489912f699aba2a88a27878f6c9df09c55e11`
- Root license: BSD 3-Clause.
- Treat client/data-plane code separately from external service/control-plane assumptions.

### NetBird
- `netbirdio/netbird@5544761b4780626b092af715cc7572baf30e8f9c`
- Root license says BSD-3-Clause for most of the repository, but explicitly excludes `management/`, `signal/`, `relay/` and `combined/`, which use AGPLv3 license files.
- This requires path-level reuse classification.

### ZeroTier One
- `zerotier/ZeroTierOne@899352e38405968516bb12a770f0ac02f6058fa8`
- Root `LICENSE.txt` explicitly points different paths to different license regimes: MPL-covered code, external code under original licenses, and `nonfree/` source-available portions.
- Never classify the entire repository with one simple license label.

### Nebula
- `slackhq/nebula@6d124d04414e08ba47cdea3eab2e1e6fb9823a31`
- Root license: MIT.

## Required developer-level research
For each project:
- complete source-tree manifest;
- client vs coordination/server/control-plane boundary;
- GUI/menu/account/device/network UX;
- local configuration and identity/key storage;
- platform services and installers;
- APIs and IPC;
- update model;
- offline/direct-connect behavior versus dependency on coordination services;
- issue/release/security history;
- forks;
- tests/CI;
- privacy/telemetry/account requirements;
- commercial/Store implications.

## PVNetwork lesson
Do not add a mesh project just because its data plane is familiar. A mesh client can carry product/account/control-plane assumptions that do not fit a generic universal VPN client. Evaluate each integration as a product feature, not merely a protocol checkbox.

No PVNetwork mesh support is implemented.