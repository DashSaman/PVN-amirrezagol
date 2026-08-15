# 056 — Tailscale — Reference Index

Current research state: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2**  
Implementation/certification state: **NOT IMPLEMENTED / NOT CERTIFIED**

## Core dossier

- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 closure.
- `CURRENT_RELEASE_POLICY_SECURITY_AUDIT_2026-08-14.md` — current release/policy/security evidence.
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 reference closure.

## Shared family evidence

- `research/upstreams/mesh-overlay-family/README.md`
- `research/upstreams/mesh-overlay-family/SUPPORT_REUSE_DECISIONS.md`
- Entry 002 WireGuard research for the underlying WireGuard cryptographic/data-plane reference.

## Canonical source pins

### Main client / service / data plane

- Repository: https://github.com/tailscale/tailscale
- Reviewed V1 commit: `0953fd9a97e9697fb496c0e1d3a0e2a45bc264ea`
- Reviewed tree: `a1703106fb225b27026d8c45964b4c8ac0a260b8`
- Stable release re-verified 2026-08-15: `v1.102.2`
- License: BSD-3-Clause for the reviewed open repository; hosted control service, closed wrappers, third-party code and branding remain separate boundaries.

### Android / Android TV

- Repository: https://github.com/tailscale/tailscale-android
- Reviewed commit: `0867f01687a3955f7c0b5c6c62b236b997d68601`
- Reviewed tree: `8eaa1daf0f632e71d058dcb09300efb3b1ccb079`
- License: BSD-3-Clause-style root license.

## Current first-party reference docs

- Install/update/uninstall: https://tailscale.com/docs/install
- Custom control server: https://tailscale.com/docs/how-to/set-up-custom-control-server
- Kubernetes: https://tailscale.com/docs/kubernetes
- Kubernetes Connector: https://tailscale.com/docs/kubernetes-operator/connector/deploy-subnet-router
- Subnet routers: https://tailscale.com/docs/features/subnet-routers
- Exit nodes: https://tailscale.com/kb/1103/exit-nodes/
- Local web UI: https://tailscale.com/kb/1325/device-web-interface
- Updates: https://tailscale.com/kb/1067/update
- Daemon configuration: https://tailscale.com/kb/1654/tailscaled-config-file
- Server provisioning: https://tailscale.com/kb/1245/set-up-servers

## Key engineering boundaries

- Tailscale is not raw WireGuard; account/device/control/network-map semantics require a dedicated mesh/provider adapter.
- Hosted coordination/control is not proven open source by the BSD client repository.
- DERP is relay fallback, not a conventional decrypting VPN server.
- Exit nodes and subnet routers are roles, not new protocols.
- Custom-control-server support does not make Headscale identical to the hosted Tailscale service.
- Closed platform GUI wrappers remain behavior references only unless their exact source is separately available.

## Reuse decision

**OPTIONAL DEDICATED MESH-PROVIDER ADAPTER.** Reuse official open client/backend components where the selected architecture and license review permit. Do not bundle merely to duplicate raw WireGuard support.

## Exact continuation

Advance V2 to **057 — ZeroTier**. Apply all exact 16 gates, preserving the free/MPL client-service paths versus `nonfree/` controller boundary, local management API/token security, joined-network/controller identity semantics, direct-vs-relay path behavior, installer/build/platform matrices, desktop/mobile/admin UI boundaries and source/license pins.
