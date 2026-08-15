# GRE over IPsec — Server Installers and Deployment Projects

Reviewed: 2026-08-15

This composition does not have a canonical standalone installer. Deployment is assembled from the GRE endpoint and IPsec/IKE implementation.

## Linux path

- GRE: kernel + iproute2, as documented in entry 063.
- IPsec/IKE: reuse the already-reviewed strongSwan/native IPsec deployment evidence under `research/upstreams/strongswan-family/reference-v2/`.
- strongSwan's current route-based VPN documentation explicitly documents creating a GRE tunnel with `ip tunnel`, bringing it up, adding routes, and protecting GRE using an IPsec host-to-host connection whose traffic selectors may be limited to GRE (`dynamic[gre]`).
- Privileged networking is required. Firewall/routing/XFRM policies are security-sensitive. Do not introduce arbitrary one-click scripts when native package/configuration paths exist.

## Cisco path

Cisco IOS XE supplies GRE and IPsec natively. The official GRE-over-IPsec workflow configures IKEv2/keyring/profile, IPsec transform/profile, GRE tunnel source/destination and `tunnel protection ipsec profile`.

## Lifecycle / supply chain

Upgrade/uninstall/rollback belongs to the chosen OS/kernel/iproute2/IKE stack or network OS. There is no separate GRE-over-IPsec package lifecycle. Reuse the strongSwan family's installer/supply-chain audit rather than duplicating or weakening it.

Evidence:
- https://docs.strongswan.org/docs/latest/features/routeBasedVpn.html
- https://www.cisco.com/c/en/us/td/docs/switches/lan/c9000/lyr3-fwd/gre/gre-configuration-guide/m-gre-over-ipsec.html
