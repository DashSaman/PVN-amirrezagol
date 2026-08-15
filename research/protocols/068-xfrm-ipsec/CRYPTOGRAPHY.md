# XFRM/IPsec — Cryptography

Reviewed: 2026-08-15

XFRM interfaces add no cryptographic primitive. They are local Linux interfaces that associate routing with existing XFRM policies/SAs using interface IDs.

All confidentiality, integrity, authentication, key exchange, replay protection, rekey and trust semantics belong to IKE/IPsec/ESP and are already evidence-backed in entries 004–007 and `research/upstreams/strongswan-family/reference-v2/CRYPTOGRAPHY.md`.

An XFRM interface ID is a policy selector, not a secret or key. Do not expose it as a credential. Hardware IPsec offload may be available through Linux XFRM device infrastructure, but offload changes execution location rather than the negotiated cryptographic protocol.

Evidence: Linux `net/xfrm/xfrm_interface_core.c`; Linux kernel XFRM-device documentation; strongSwan route-based VPN documentation; RFC 7296/4303.
