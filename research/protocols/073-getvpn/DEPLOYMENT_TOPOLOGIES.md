# Cisco GETVPN — Deployment Topologies

Reviewed: 2026-08-15

Canonical topology: one or more key servers/control-plane roles centrally define policy/keying material for many group members on an enterprise private WAN. GMs protect native routed unicast/multicast traffic without building a full mesh of point-to-point overlay tunnels.

Cisco supports resiliency/cooperative key-server features and group-member lifecycle features subject to release/mode restrictions. Current G-IKEv2 documentation specifically notes IKEv2 for COOP is not supported and IKEv1 is used for COOP between key servers in that setup, demonstrating why topology/control-plane version capability must be explicit.

GETVPN differs from DMVPN/FlexVPN: it preserves native routing and distributes group IPsec policy/keys rather than using mGRE/NHRP or pairwise route-based IKEv2 tunnels.