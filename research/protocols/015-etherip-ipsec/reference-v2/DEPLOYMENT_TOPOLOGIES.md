# EtherIP/IPsec — Deployment Topologies

Review date: 2026-08-14 UTC

## Covered patterns

1. **SoftEther site-to-site / protected L2 extension** — SoftEther EtherIP mapping + Virtual Hub/bridge + built-in IPsec/IKE service.
2. **OpenBSD protected EtherIP gateway pair** — native `etherip(4)` with IPsec flows selecting EtherIP/protocol 97.
3. **Typed composition with separate IPsec backend** — architecture is valid only after exact selector/routing/backend ownership and peer interoperability are proven.

## Required separation

- EtherIP endpoint/mapping state;
- IKE control plane;
- ESP data plane;
- outer route/NAT/firewall;
- bridge/L2 forwarding.

## Safety / non-goals

- no raw-clear fallback when the deployment policy requires protection;
- no assumption that any two EtherIP and IPsec implementations interoperate as a composition without exact configuration evidence;
- no consumer mobile profile claim;
- MTU, rekey, NAT and failover remain backend/topology-specific certification items.
