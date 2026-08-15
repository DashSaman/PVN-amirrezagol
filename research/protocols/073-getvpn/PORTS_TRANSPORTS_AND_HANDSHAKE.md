# Cisco GETVPN — Ports, Transports and Handshake

Reviewed: 2026-08-15

GETVPN has two relevant key-management generations/modes in current Cisco documentation:

- Legacy GDOI: Cisco GETVPN documentation identifies GDOI as key management and documents UDP **848** in GDOI-bypass behavior. RFC 6407 is now obsoleted by RFC 9838 but remains deployed/vendor-supported legacy behavior.
- G-IKEv2/GKM: RFC 9838 G-IKEv2 SHOULD use IKEv2 ports **UDP 500/4500** and may use TCP for unicast registration IKE SA per RFC 9329. Current Cisco GETVPN G-IKEv2 documentation implements an IKEv2-based GKM model, but exact RFC 9838 conformance is not claimed because Cisco's 2026 page says exchanges conform to an IETF standards draft.

The group data plane uses IPsec group SAs; there is no pairwise GETVPN overlay-tunnel handshake among every GM. Rekey is a separate group maintenance operation.

Do not collapse GDOI UDP 848 and G-IKEv2 IKE ports into one fictional GETVPN port.