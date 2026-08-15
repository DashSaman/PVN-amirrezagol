# VXLAN over IPsec — Deployment Topologies

Reviewed: 2026-08-15

Evidence-backed architecture is a VXLAN L2 overlay whose VTEP-to-VTEP underlay traffic is protected by IPsec. VXLAN can remain one-to-many with static/dynamic FDB behavior while IPsec policy/SAs protect the relevant VTEP traffic.

Control-plane/EVPN/controller behavior remains external to bare VXLAN; IPsec does not create VXLAN membership. HA/multipath decisions belong to the overlay/underlay/security architecture rather than a new combined protocol.

This entry is distinct from bare VXLAN 069 and from XFRM/VTI route-based L3 IPsec abstractions 067–068.