# IPIP over IPsec — Server Installers and Deployment Projects

Reviewed: 2026-08-15

There is no canonical standalone IPIP-over-IPsec installer. Deployment composes:

- native Linux IPIP (`ip tunnel ... mode ipip`) from entry 065;
- Linux XFRM/IPsec plus an IKE daemon such as strongSwan, whose official/community install paths, privileges, configuration ownership, supply-chain and lifecycle evidence are already reviewed in `research/upstreams/strongswan-family/reference-v2/`.

The composition requires privileged network administration and security-sensitive routing/firewall/XFRM changes. Prefer distribution packages and pinned/native components; do not introduce an unreviewed one-click shell script merely to combine IPIP and IPsec.

Upgrade/uninstall/rollback are the lifecycle of kernel/iproute2/IKE/IPsec components plus removal/reversion of the tunnel and IPsec policy/SAs; there is no separate composition package.
