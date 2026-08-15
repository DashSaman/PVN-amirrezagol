# XFRM/IPsec — Ports, Transports and Handshake

Reviewed: 2026-08-15

XFRM interfaces have no independent on-wire port, framing or handshake. They are a local Linux routing/policy abstraction.

IKE/IPsec supplies peer authentication, key establishment, SA creation/rekey/liveness and ESP data-plane transport, as documented in the completed strongSwan-family V2 ports/handshake evidence. XFRM interface IDs never appear as a substitute for IKE authentication or a cryptographic key.

The selected IPsec mode/NAT traversal determines actual outer transport. XFRM-interface use adds no GRE/IPIP header and requires no awareness by the remote peer.

No XFRM-interface-defined proxy, fallback transport or session-resumption protocol exists.
