# IPIP — Cryptography

Reviewed: 2026-08-15

IP-in-IP per RFC 2003 provides **no intrinsic confidentiality, integrity authentication, peer authentication, key exchange, certificates, PSK, AEAD, replay protection or forward secrecy**. It inserts an outer IPv4 header around the original IP datagram.

Accordingly:

- outer protocol value 4 identifies IPv4-in-IPv4 encapsulation; it is not a security mechanism;
- tunnel endpoint addresses are routing/configuration identifiers, not authenticated identities;
- IP header checksum behavior is error detection, not cryptographic integrity;
- bare IPIP exposes the encapsulated packet to an on-path observer unless another layer protects it.

When cryptographic protection is required, compose IPIP with a security layer such as IPsec; that composition is entry 066 and its crypto must be attributed to IKE/IPsec/ESP rather than IPIP.

Evidence:
- RFC 2003, especially header construction and Security Considerations: https://www.rfc-editor.org/rfc/rfc2003.html
- Linux implementation pin: `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/ipv4/ipip.c`.
