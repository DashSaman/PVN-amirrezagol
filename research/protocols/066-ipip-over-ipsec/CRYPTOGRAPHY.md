# IPIP over IPsec — Cryptography

Reviewed: 2026-08-15

IPIP contributes no cryptography. Confidentiality, integrity, peer authentication, key agreement, replay protection, rekeying and trust all belong to the selected IKE/IPsec/ESP layer.

The authoritative security evidence is already maintained in entries 004–007 and `research/upstreams/strongswan-family/reference-v2/CRYPTOGRAPHY.md`, grounded in IKEv2 RFC 7296 and ESP RFC 4303. Bare IPIP remains RFC 2003 protocol-4 encapsulation.

Engineering boundary:

- do not call IPIP endpoint addresses authenticated identities;
- do not call IP header checksums cryptographic integrity;
- do not implement IPsec cryptography inside an IPIP adapter;
- expose negotiated IKE/ESP security state separately from IPIP interface state.

Evidence:
- https://www.rfc-editor.org/rfc/rfc2003.html
- https://www.rfc-editor.org/rfc/rfc7296.html
- https://www.rfc-editor.org/rfc/rfc4303.html
- existing strongSwan-family V2 cryptography dossier.
