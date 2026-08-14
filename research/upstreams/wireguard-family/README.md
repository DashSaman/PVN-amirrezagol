# WireGuard / AmneziaWG Family — Shared Research Dossier

Related matrix entries: **002 WireGuard**, **003 AmneziaWG**, plus later mesh products that use WireGuard as a data-plane component.

Research state: `IN-RESEARCH`.

## Core and client references selected
- `WireGuard/wireguard-go` — official userspace Go implementation/reference; MIT at pinned revision.
- WireGuard Windows — official GitHub mirror; canonical upstream is `git.zx2c4.com/wireguard-windows`.
- WireGuard Android — official GitHub mirror; canonical upstream is `git.zx2c4.com/wireguard-android`.
- WireGuard Apple — official GitHub mirror; canonical upstream is `git.zx2c4.com/wireguard-apple`.
- `amnezia-vpn/amneziawg-go` — AmneziaWG userspace core candidate, fork-derived from WireGuard Go; MIT at pinned revision.
- Amnezia client repositories — to be audited separately as multi-protocol product references.
- Tailscale / NetBird — not WireGuard protocol implementations alone; analyze later as higher-level mesh/control-plane products.

## Research principle
Do not merge WireGuard protocol/core research with mesh product architecture. PVNetwork needs to know which component is the tunnel/data-plane engine, which is platform UI/integration, and which belongs to a separate coordination/control plane.

## Current direction
Prefer official platform implementations/native support where practical, with `wireguard-go` as an important portable reference. AmneziaWG must remain a separate compatibility capability with its own pinned implementation/version.

## Required deeper files
- `SOURCE_REVISIONS.md`
- core architecture/license notes
- Windows UI/service/config/storage notes
- Android UI/config/storage notes
- Apple NetworkExtension/UI notes
- AmneziaWG delta/config notes
- issues/releases/forks/tests/asset references

Nothing in this dossier means PVNetwork support is implemented.