# IPIP — Deployment Topologies

Reviewed: 2026-08-15

## Evidence-backed models

- **Point-to-point routed tunnel:** two configured IPv4 tunnel endpoints encapsulate inner IPv4 packets using protocol 4 and decapsulate at the remote endpoint.
- **Site-to-site routed overlay:** routes for selected inner prefixes point at the IPIP interface while outer endpoint reachability remains in the underlay.
- **Nested/tunnel compositions:** IPIP may be protected by another security mechanism, but IPIP-over-IPsec is entry 066 and must stay separate from bare IPIP.

## Plane separation

Bare IPIP has no management/control protocol. Endpoint addresses, interface configuration and routing are external configuration/control; encapsulation/decapsulation is the data plane.

## Split/full route behavior

Routing policy determines which prefixes enter the IPIP interface. Split/full tunnel are not protocol negotiation modes.

## HA/mesh

RFC 2003 does not define a controller, mesh membership, clustering or HA protocol. Redundancy must come from routing/platform configuration.

Evidence: RFC 2003; Linux/iproute2 implementation references in this dossier.
