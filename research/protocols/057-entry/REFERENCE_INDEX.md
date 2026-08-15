# 057 — ZeroTier — Reference Index

Current research state: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2**  
Implementation/certification state: **NOT IMPLEMENTED / NOT CERTIFIED**

## Core dossier

- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 closure.
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 reference closure.

## Shared family evidence

- `research/upstreams/mesh-overlay-family/README.md`
- `research/upstreams/mesh-overlay-family/SUPPORT_REUSE_DECISIONS.md`

## Canonical source/release pins

- Repository: https://github.com/zerotier/ZeroTierOne
- Default branch: `dev`
- Reviewed V1 commit: `899352e38405968516bb12a770f0ac02f6058fa8`
- Reviewed tree: `1abcbd6b6d5a0608105972fd4bde1ef470c70236`
- Stable release re-verified 2026-08-15: `1.16.2`

License boundary:

- `node/`, `osdep/`, `service/` and most non-`ext/`/non-`nonfree/` paths: MPL-2.0.
- `nonfree/`: separately licensed source-available/non-free code.
- `ext/`: dependency-specific licenses.

## Current first-party docs

- Compatibility: https://docs.zerotier.com/compatibility/
- Platform index: https://docs.zerotier.com/platforms/
- Quickstart: https://docs.zerotier.com/quickstart/
- Service API reference: https://docs.zerotier.com/api/client/
- Service API overview: https://docs.zerotier.com/api-service/
- Service/API tokens: https://docs.zerotier.com/tokens/
- Standalone controller: https://docs.zerotier.com/controller/
- Hosted Central API: https://docs.zerotier.com/api-central/

## Key engineering boundaries

- ZeroTier is a distinct encrypted overlay, not WireGuard and not a single server/port VPN profile.
- ZeroTierOne local node/service, local controller and hosted ZeroTier Central are separate roles.
- `authtoken.secret` authenticates the local localhost management API and is not a portable network credential.
- Free/MPL paths must not be conflated with `nonfree/` controller code or `ext/` dependencies.
- Direct peer traffic is preferred, but relay/fallback behavior exists and must remain explicit.
- Hosted Central's implementation/license is not inferred from the client/service repository.

## Reuse decision

**OPTIONAL DISTINCT MESH/OVERLAY ADAPTER.** Prefer official free ZeroTierOne client/service paths only after exact path-level license review. Keep hosted/control-plane integration separate from the node data plane.

## Exact continuation

Advance V2 to **058 — NetBird**. Apply all exact 16 gates with separate management/control/signal/relay/client/server boundaries, current canonical source/release/license pins, self-hosted and hosted deployment paths, web admin and client UI maps, WireGuard-based data-plane semantics, NAT traversal/relay behavior, installers/container/Kubernetes evidence, upgrade/uninstall/security and supply-chain review.
