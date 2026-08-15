# VTI/IPsec — Ports, Transports and Handshake

Reviewed: 2026-08-15

VTI has no independent on-wire transport, port or handshake. It is a local Linux interface abstraction around IPsec policies/SAs.

- IKE/IPsec negotiation, peer authentication, SA establishment/rekey/liveness and ESP transport identities are inherited from the selected IPsec stack and documented in the strongSwan-family V2 ports/handshake dossier.
- VTI marks select/match local XFRM policies; they are not wire authentication fields.
- No extra GRE/IPIP header is added merely because VTI is used. strongSwan explicitly describes VTI use as a local decision the peer need not share.
- No VTI-defined proxy/fallback/NAT traversal exists; those behaviors belong to IPsec/IKE.

This separation is essential: VTI is not a protocol port and must not be assigned a fictional TCP/UDP service number.
