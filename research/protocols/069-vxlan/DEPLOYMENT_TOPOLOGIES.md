# VXLAN — Deployment Topologies

Reviewed: 2026-08-15

Evidence-backed models include data-center L2 overlays across an L3 underlay, VTEP-to-VTEP unicast, multicast-assisted broadcast/unknown/multicast replication, static unicast FDB entries, Linux bridge integration and virtual-switch deployments.

VXLAN is 1-to-N rather than only point-to-point. The VNI identifies the overlay segment. Mapping/learning may be data-plane based or supplied by a separate control plane; RFC 7348 focuses on data-plane learning and does not make a specific control-plane product part of VXLAN.

HA/EVPN/controller behavior is external to bare VXLAN. VXLAN over IPsec is entry 070 and adds a separate security layer.