# Cisco FlexVPN — Ports, Transports and Handshake

Reviewed: 2026-08-15

FlexVPN inherits IKEv2/IPsec negotiation and data-plane transport from the generic IPsec layer. It has no separate FlexVPN service port or alternate cryptographic handshake.

Peer authentication, IKE_SA/CHILD_SA establishment, NAT traversal, rekey/liveness and ESP transport are those of IKEv2/IPsec. Cisco authorization/configuration exchanges and framework features occur within/alongside the IKEv2 lifecycle and must not be described as a new transport protocol.

No universal `flexvpn://` URI or consumer import handshake exists.