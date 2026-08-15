# Cisco FlexVPN — Deployment Topologies

Reviewed: 2026-08-15

Cisco documents FlexVPN as one IKEv2/IPsec framework spanning site-to-site, remote access, hub-and-spoke and partial-mesh/spoke-to-spoke designs using tunnel/virtual-template interfaces and authorization/routing attributes.

AAA/RADIUS/PKI may participate in identity/authorization. Exact IOS XE version/topology/authentication restrictions remain vendor-specific and require later certification.

This is distinct from DMVPN: DMVPN's dynamic architecture uses mGRE/NHRP, while FlexVPN is centered on IKEv2/IPsec framework/tunnel-interface semantics. Do not merge the two because both can form hub/spoke networks.