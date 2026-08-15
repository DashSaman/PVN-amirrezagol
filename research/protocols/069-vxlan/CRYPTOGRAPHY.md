# VXLAN — Cryptography

Reviewed: 2026-08-15

RFC 7348 VXLAN provides encapsulation and segmentation, **not intrinsic confidentiality, peer authentication, key exchange, integrity authentication, replay protection or forward secrecy**. The 24-bit VNI separates overlay segments but is not a secret or security credential.

RFC 7348 Security Considerations expects appropriate security controls in the environment; management mapping should use secure methods. If cryptographic protection of VXLAN transport is required, use a separate security layer such as IPsec; that composition is entry 070.

Do not describe UDP checksum, Ethernet FCS or VNI as cryptographic integrity/authentication.