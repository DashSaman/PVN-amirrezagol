# GRE over IPsec — Deployment Topologies

Reviewed: 2026-08-15

## Common evidence-backed models

- **Site-to-site point-to-point:** GRE interface between two gateways, protected by an IPsec SA/IKE peer relationship. Supports routing over the GRE interface while IPsec protects the outer GRE traffic.
- **Route-based Linux:** GRE device supplies the routing interface; IPsec host-to-host policy/SA protects GRE protocol traffic. strongSwan documents this directly.
- **Cisco tunnel protection:** GRE tunnel interface carries traffic and is bound to an IPsec profile with `tunnel protection ipsec profile`.
- **VRF-aware router deployments:** Cisco current documentation describes front-door/inside VRF separation on supported platforms.
- **Hub-and-spoke/full mesh:** possible with multiple protected GRE tunnels, but dynamic multipoint/NHRP behavior belongs to DMVPN (071), not bare point-to-point GRE-over-IPsec.

## Plane separation

- Management/control: endpoint routing, IKE identity/authentication/policy, SA negotiation.
- Data: inner traffic -> GRE -> ESP/IPsec -> outer network.

## Policy variants

Split/full routing is a route-policy choice. IPsec transport mode can protect only GRE between endpoints; IPsec tunnel mode may add another IP encapsulation layer. Exact design must remain explicit in profiles because overhead and traffic selectors differ.

Evidence: strongSwan route-based VPN guide; Cisco GRE-over-IPsec guide; entry 063 GRE dossier; strongSwan-family V2 topology dossier.
