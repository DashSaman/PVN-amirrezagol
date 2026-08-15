# XFRM/IPsec — Deployment Topologies

Reviewed: 2026-08-15

## Evidence-backed models

- **Route-based site-to-site:** routes select an XFRM interface whose ID links traffic to matching IPsec policies/SAs.
- **Shared interface across multiple SAs:** strongSwan documents sharing an XFRM interface ID when policies do not conflict, including road-warrior virtual-address ranges.
- **Separate inbound/outbound interfaces:** independent interface IDs may be configured per direction.
- **Network namespaces:** the interface may be moved into a namespace while SAs/keys remain in another, enabling separation of data-plane access from key management.
- **VRF/multi-tenancy:** XFRM interfaces may attach to an L3 VRF master with documented kernel-version caveats.
- **Transport or tunnel mode:** unlike VTI, XFRM interfaces are not restricted to IPsec tunnel mode.

The abstraction is local: the peer does not need an XFRM interface. It adds no extra encapsulation header.

This entry remains distinct from VTI/IPsec (067) and from hardware XFRM offload, which is an execution optimization rather than a different VPN topology.
