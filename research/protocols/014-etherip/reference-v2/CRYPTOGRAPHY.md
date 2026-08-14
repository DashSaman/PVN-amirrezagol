# EtherIP — Cryptography / Security Boundary

Review date: 2026-08-14 UTC

Authoritative baseline: RFC 3378 security considerations plus canonical OS documentation.

Raw EtherIP has **no integrated confidentiality, peer authentication or cryptographic integrity**. The IPv4 header/checksum is not a substitute for payload integrity. Bridging remote Ethernet segments can widen a local trust boundary and can carry protocols that assumed LAN-local scope.

Therefore:

- never display raw EtherIP as an encrypted/secure VPN;
- firewall acceptance of IP protocol 97 requires deliberate policy;
- bridge-loop, broadcast/multicast and LAN trust implications are part of the security boundary;
- if confidentiality/authentication is required, use a separately modeled protection layer such as IPsec (entry 015), without changing the identity of raw EtherIP;
- do not invent PVNetwork cryptography around the EtherIP header.
