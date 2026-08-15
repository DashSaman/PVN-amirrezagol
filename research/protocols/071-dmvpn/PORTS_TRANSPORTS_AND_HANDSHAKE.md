# DMVPN — Ports, Transports and Handshake

Reviewed: 2026-08-15

DMVPN is layered rather than one transport handshake. GRE/mGRE provides tunnel encapsulation, NHRP supplies registration/resolution control, routing supplies prefix reachability, and IKE/IPsec supplies security negotiation/data protection.

GRE and NHRP identities/traffic must not be mislabeled as a single DMVPN TCP/UDP port. IKE/IPsec transport details are inherited from the completed IPsec reference layer.

Cisco-specific keepalive and phase/feature behavior is implementation-specific. The V1 evidence notes GRE keepalives are not supported in DMVPN in the cited Cisco guidance; operational health should use appropriate NHRP/routing/IPsec observability.