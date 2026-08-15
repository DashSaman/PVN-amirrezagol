# VTI/IPsec — Deployment Topologies

Reviewed: 2026-08-15

## Evidence-backed models

- **Route-based site-to-site IPsec:** routes point to a VTI; the VTI mark binds packets to matching IPsec policies/SAs.
- **Broad selectors plus routing control:** strongSwan documents negotiating broad traffic selectors and using VTI marks/routing to select actual tunneled traffic.
- **Dynamic/static routing over VTI:** because VTI is an L3 route-based interface, ordinary routing can select the protected path, subject to matching IPsec policy.
- **Multiple VTIs:** separate endpoint/mark combinations can map traffic to different SAs/policies; wildcard/endpoints and 1:1 limitations distinguish VTI from XFRM interfaces.

## Constraints / adjacent technologies

VTI devices require endpoint addresses, support a single address family per device and depend on IPsec tunnel mode. Current strongSwan documentation recommends XFRM interfaces on newer Linux because they remove several VTI limitations; XFRM/IPsec is entry 068.

VTI is a local implementation choice and adds no GRE-like extra encapsulation. It is distinct from GRE-over-IPsec (064) and IPIP-over-IPsec (066).
