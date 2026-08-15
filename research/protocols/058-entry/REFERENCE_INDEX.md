# 058 — NetBird — Reference Index

Current research state: **COMPLETE-RESEARCH-v1 + COMPLETE-REFERENCE-v2**  
Implementation/certification state: **NOT IMPLEMENTED / NOT CERTIFIED**

## Core dossier

- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 closure.
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 reference closure.

## Canonical source/release pins

- Repository: https://github.com/netbirdio/netbird
- Stable release re-verified 2026-08-15: `v0.77.0`
- V1 pinned annotated tag object: `a30bb8d40108f4ff96e5731f8508d4fc28e6d1ca`
- Tag target commit: `c5503fdc7f93ae6844a39caecf2970b43618c9b2`
- Release tree: `9da63d4d8e9d22918c56cc6112f97e3a3cd496a2`

License boundary:

- general/client repository paths: BSD-3-Clause;
- `management/`, `signal/`, `relay/`, `combined/`: AGPL-3.0;
- Enterprise self-host: separate commercial license option;
- NetBird Cloud: separate service/commercial terms.

## Current first-party docs

- Install: https://docs.netbird.io/get-started/install
- Architecture: https://docs.netbird.io/about-netbird/how-netbird-works
- Self-host advanced guide: https://docs.netbird.io/selfhosted/selfhosted-guide
- Scaling/split deployment: https://docs.netbird.io/selfhosted/maintenance/scaling/scaling-your-self-hosted-deployment
- Environment variables: https://docs.netbird.io/selfhosted/environment-variables
- Access control: https://docs.netbird.io/manage/access-control
- Routes: https://docs.netbird.io/manage/network-routes
- Networks/routing peers: https://docs.netbird.io/manage/networks/how-routing-peers-work
- Exit nodes: https://docs.netbird.io/use-cases/remote-access/exit-nodes
- DNS settings: https://docs.netbird.io/manage/dns/dns-settings
- Control Center: https://docs.netbird.io/manage/control-center

## Key engineering boundaries

- NetBird is not raw WireGuard; Management, Signal, Relay, identity, policy, DNS and routing are first-class provider state.
- Signal brokers negotiation; ordinary payload does not traverse Signal after path establishment.
- Relay forwards already WireGuard-encrypted traffic when direct P2P fails.
- Cloud, OSS self-host and Enterprise self-host are distinct service/license modes.
- Current `Networks` is preferred for new routed resources; legacy `Routes` is retained mainly for compatibility and exit-node use cases.
- Do not label the whole repository BSD because server directories are AGPL-3.0.

## Reuse decision

**OPTIONAL DEDICATED NETBIRD PROVIDER ADAPTER.** BSD client paths can be evaluated for reuse; AGPL self-host server paths require explicit compliance architecture or a commercial alternative.

## Exact continuation

Advance V2 to **059 — Netmaker**. Apply all exact 16 gates with current source/release/license pins, management/server/client/remote-access boundaries, installer and container/Kubernetes deployment review, WireGuard data-path semantics, admin/client UI maps, routing/DNS/egress/remote-access roles, upgrade/uninstall/security and supply-chain analysis.
