# VXLAN — Ports, Transports and Handshake

Reviewed: 2026-08-15

RFC 7348 assigns VXLAN UDP destination port **4789** and uses UDP over IPv4 or IPv6 underlay. The VNI is 24 bits in the VXLAN header.

Linux source notes the IANA port is 4789 but the kernel module default historically uses 8472 for compatibility with early adopters; deployments must therefore record actual configured port rather than assume all Linux environments use one value.

VXLAN has no connection-oriented authentication handshake, TLS negotiation or key exchange. Learning/FDB/control-plane behavior is separate from a security handshake. NAT traversal/proxy/fallback are not protocol-defined VXLAN mechanisms.