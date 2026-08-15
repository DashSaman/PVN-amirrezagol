# IPIP — Ports, Transports and Handshake

Reviewed: 2026-08-15

- **Outer protocol:** IPv4-in-IPv4 uses IP Protocol **4** per RFC 2003. It has no TCP or UDP port.
- **Handshake:** bare IPIP has no protocol-defined connection setup, peer-authentication exchange, key agreement or session-resumption handshake. Endpoints are configured out of band.
- **Authentication:** none intrinsic.
- **Keepalive/DPD:** none defined by RFC 2003; any liveness mechanism belongs to an implementation or surrounding routing/control system.
- **NAT traversal:** no IPIP-defined UDP encapsulation/NAT-T mechanism. NAT/firewall behavior is network/implementation dependent and middleboxes must pass protocol 4 as needed.
- **Proxy/fallback:** none defined by IPIP.
- **Version negotiation:** none; the inner/outer IPv4 semantics are defined by the IP headers and RFC 2003.

Do not describe protocol 4 as “port 4”. Do not infer an authenticated session from successful decapsulation.

Evidence: RFC 2003; pinned Linux `ipip.c`; pinned iproute2 `ip-tunnel.8`.
