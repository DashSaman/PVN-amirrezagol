# GRE — Ports, Transports and Handshake

Reviewed: 2026-08-15

- **Outer transport:** GRE over IPv4 uses IP protocol number **47**, not TCP or UDP; there is no GRE TCP/UDP port.
- **Handshake:** bare GRE has no protocol-defined connection handshake, TLS negotiation, authentication exchange or session-resumption handshake. Endpoints are configured out of band and encapsulate traffic when routing selects the tunnel.
- **Header:** RFC 2784 GRE version is 0 and includes Protocol Type plus optional checksum. RFC 2890 adds optional Key and Sequence Number fields.
- **Keepalive:** not part of the RFC 2784 base handshake because there is no base handshake. Some implementations add operational keepalive behavior; Cisco IOS XE documents GRE tunnel keepalive as a vendor implementation feature.
- **NAT traversal:** bare GRE does not define UDP encapsulation/NAT-T. Middleboxes/firewalls must understand/allow IP protocol 47; NAT behavior is implementation/network dependent.
- **Retry/fallback:** no protocol-defined TCP/UDP fallback exists.
- **Proxy support:** NOT-APPLICABLE to GRE itself.

Security note: accepting IP protocol 47 is not equivalent to authenticating a peer. A GRE key is a flow/tunnel identifier, not a cryptographic authentication secret.

Evidence:
- RFC 2784: https://www.rfc-editor.org/rfc/rfc2784.html
- RFC 2890: https://www.rfc-editor.org/rfc/rfc2890.html
- Cisco IOS XE GRE tunnel guide: https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/ip-routing/b-ip-routing/m_ir-impl-tun-xe.html
- Linux/iproute2 pins: `torvalds/linux@15ef2f78...`, `iproute2/iproute2@da2ccdf...`.
