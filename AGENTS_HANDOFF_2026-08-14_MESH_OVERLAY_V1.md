# AGENTS Handoff — 2026-08-14 — Mesh / Overlay v1 Closure

Mandatory continuation checkpoint.

## State transition

Entries 056–062 are now:

**`V1-HANDOFF-READY / NOT IMPLEMENTED`**

## Evidence

- `research/upstreams/mesh-overlay-family/SUPPORT_REUSE_DECISIONS.md`
- separate `V1_RESEARCH.md` files for 056 Tailscale, 057 ZeroTier, 058 NetBird, 059 Netmaker, 060 Nebula, 061 Tinc and 062 innernet
- dated status `docs/RESEARCH_CAMPAIGN_STATUS_2026-08-14_MESH_OVERLAY_V1.md`

## Core product rule

A mesh ecosystem can contain data plane + coordination + identity + policy + relay + DNS + account/device management. Do not flatten it into a single ordinary VPN profile.

## Engine minimization

- do not add Tailscale/NetBird/Netmaker/innernet merely to duplicate WireGuard;
- ZeroTier/Nebula/Tinc are distinct engines and require real user demand before inclusion;
- ecosystem integrations should use MeshProvider/Network/Identity objects rather than normal server:port profiles.

## Residual gaps

Exact current pins/SBOM/license paths, client menus, control-plane APIs, issues/security/performance and full server deployment remain explicit. Mandatory v2 later covers complete control-plane/server installs, crypto/wire flow and topologies.

## Exact next action

1. Activate original-v1 Router/Site-to-Site group 063–073.
2. Research GRE, GRE over IPsec, IPIP, IPIP over IPsec, VTI/IPsec, XFRM/IPsec, VXLAN, VXLAN/IPsec, DMVPN, FlexVPN and GETVPN.
3. Prefer OS/kernel/router implementations for raw encapsulation rather than custom packet engines.
4. Reuse the existing IPsec security model for protected compositions.
5. Treat DMVPN/FlexVPN/GETVPN vendor/control-plane semantics separately from raw GRE/IPsec building blocks.
6. Create per-entry research decisions and checkpoint.
7. Continue the next unfinished original-v1 group without waiting for owner.
8. Do not begin mass COMPLETE-REFERENCE-v2 until original v1 gates reach intended state.
