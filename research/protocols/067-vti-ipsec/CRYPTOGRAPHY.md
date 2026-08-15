# VTI/IPsec — Cryptography

Reviewed: 2026-08-15

VTI adds no cryptographic primitive. It is a Linux route-based interface/policy-selection abstraction around ordinary IPsec.

All confidentiality, integrity, peer authentication, key exchange, replay protection, rekey and trust properties belong to IKE/IPsec/ESP. The authoritative crypto evidence remains entries 004–007 and `research/upstreams/strongswan-family/reference-v2/CRYPTOGRAPHY.md`, grounded in RFC 7296 and RFC 4303.

VTI marks/keys are policy selectors and must never be presented as cryptographic keys or credentials. The Linux source path `net/ipv4/ip_vti.c` integrates with XFRM policy/input processing; it is not an alternative crypto engine.

Engineering decision: use reviewed OS/IPsec cryptography and keep VTI interface state separate from negotiated security state.
