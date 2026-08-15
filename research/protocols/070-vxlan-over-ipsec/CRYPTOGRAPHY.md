# VXLAN over IPsec — Cryptography

Reviewed: 2026-08-15

VXLAN contributes no cryptography. All confidentiality, integrity authentication, peer authentication, key exchange, replay protection, rekey and trust properties come from IKE/IPsec/ESP.

Authoritative security evidence is reused from entries 004–007 and `research/upstreams/strongswan-family/reference-v2/CRYPTOGRAPHY.md` (RFC 7296 / RFC 4303). Entry 069 documents bare VXLAN's lack of security.

Do not call VNI, UDP checksum or Ethernet FCS cryptographic security. Do not implement crypto in the VXLAN adapter.