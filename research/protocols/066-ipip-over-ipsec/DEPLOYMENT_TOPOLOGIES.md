# IPIP over IPsec — Deployment Topologies

Reviewed: 2026-08-15

## Supported reference model

- **Site-to-site point-to-point:** two gateways maintain an IPIP routed tunnel and an IPsec/IKE security association whose policy covers the IPIP traffic.
- **Route-based infrastructure composition:** routing selects the IPIP interface; XFRM/IPsec protects the resulting protocol-4 traffic between peers.
- **Multiple protected tunnels:** possible through multiple independently configured IPIP/IPsec peers and routing policy; there is no IPIP-defined mesh/controller protocol.

## Plane separation

- management/control: endpoint addresses/routes plus IKE identity/authentication/policy and SA lifecycle;
- data: inner IPv4 -> IPIP -> ESP/IPsec -> outer network.

## Policy variants

Which prefixes use the tunnel is a routing decision. IPsec transport/tunnel mode and NAT traversal are separate security-encapsulation decisions and alter overhead/outer headers.

## Boundaries

This entry is not VTI/IPsec (067), XFRM/IPsec interface architecture (068), GRE-over-IPsec (064) or DMVPN (071). Those entries have different route/interface/framing semantics.

Evidence: RFC 2003; entries 004–007; strongSwan-family V2 topology/data-path evidence.
