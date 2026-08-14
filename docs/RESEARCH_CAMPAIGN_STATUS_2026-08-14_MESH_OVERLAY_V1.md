# PVNetwork Research Campaign Status — 2026-08-14 — Mesh / Overlay v1 Closure

Repository phase: research / requirements / architecture.

Entries 056–062: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Shared evidence

Under `research/upstreams/mesh-overlay-family/`:

- existing `README.md`
- `SUPPORT_REUSE_DECISIONS.md`

## Numbered evidence

Separate `V1_RESEARCH.md` files now exist for:

- 056 Tailscale
- 057 ZeroTier
- 058 NetBird
- 059 Netmaker
- 060 Nebula
- 061 Tinc
- 062 innernet

## Main architecture rule

Mesh products may combine data plane, coordination, identity, policy, relays, DNS/naming, accounts and device management. PVNetwork must not model an entire mesh ecosystem as a normal single-server VPN profile.

## Decisions

- Tailscale — optional Tailscale ecosystem integration; basic WireGuard does not require it.
- ZeroTier — distinct overlay engine/ecosystem; no WireGuard translation.
- NetBird — optional WireGuard-based ecosystem integration with path-level license review.
- Netmaker — control-plane/orchestration reference; not a required WireGuard engine.
- Nebula — distinct optional mesh engine with its own identity/certificate model.
- Tinc — low-priority mature/legacy mesh compatibility.
- innernet — WireGuard orchestration reference; not a distinct required data-plane engine.

## Engine minimization rule

Do not ship Tailscale/NetBird/Netmaker/innernet merely to duplicate WireGuard. ZeroTier/Nebula/Tinc add truly distinct engines and should only be included when real demand justifies maintenance, attack surface and platform cost.

## Residual gaps

- exact current source/license/SBOM pins for several ecosystems;
- complete Tailscale/NetBird path-level component license map;
- current client UI/menu/platform/Store evidence;
- issues/advisories/performance;
- control-plane/self-hosted API details.

Server/control-plane installers, full menus, cryptography/wire flow and topology details remain mandatory later v2.

## Next exact action

Continue original v1 immediately with **063–073 Router / Site-to-Site group** from the actual protocol matrix: GRE, GRE over IPsec, IPIP, IPIP over IPsec, VTI/IPsec, XFRM/IPsec, VXLAN, VXLAN over IPsec, DMVPN, Cisco FlexVPN and GETVPN. Keep raw encapsulation, IPsec protection, routing/control-plane and vendor-proprietary solutions separate. Reuse the already documented IPsec family where appropriate; do not duplicate cryptographic models. Do not begin mass v2 yet.
