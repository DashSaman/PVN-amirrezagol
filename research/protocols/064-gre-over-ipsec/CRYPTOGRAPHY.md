# GRE over IPsec — Cryptography

Reviewed: 2026-08-15

GRE contributes no cryptography. All confidentiality, integrity, peer authentication, key agreement, replay protection, rekey and trust behavior in this composition comes from the selected IPsec/IKE layer.

## Authoritative boundary

- RFC 2784/2890: GRE encapsulation, optional checksum/key/sequence; none are cryptographic security primitives.
- RFC 7296 / IKEv2 and RFC 4303 / ESP govern the security layer. The repository's existing entries 004 (IKEv2/IPsec), 006 (ESP) and `research/upstreams/strongswan-family/reference-v2/CRYPTOGRAPHY.md` already contain the evidence-backed algorithm/proposal/KEX/authentication/rekey/replay boundaries.
- strongSwan documentation explicitly notes that IPsec transport mode is often used to protect insecure tunneling protocols such as GRE.
- Cisco's current GRE-over-IPsec guide explicitly states GRE does not provide confidentiality/authentication and IPsec supplies confidentiality, integrity and authentication.

## Engineering decision

Do not implement cryptography from scratch and do not duplicate crypto policy in a GRE core. The GRE adapter should hand traffic to the selected IPsec/XFRM implementation and expose negotiated-security state separately from GRE tunnel state.

Evidence:
- https://docs.strongswan.org/docs/latest/howtos/ipsecProtocol.html
- https://www.rfc-editor.org/rfc/rfc7296.html
- https://www.rfc-editor.org/rfc/rfc4303.html
- https://www.cisco.com/c/en/us/td/docs/switches/lan/c9000/lyr3-fwd/gre/gre-configuration-guide/m-gre-over-ipsec.html
