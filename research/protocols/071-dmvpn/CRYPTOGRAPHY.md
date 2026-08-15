# DMVPN — Cryptography

Reviewed: 2026-08-15

DMVPN's GRE/mGRE and NHRP layers do not supply confidentiality. Cryptographic protection normally comes from IKE/IPsec/ESP, whose algorithms, authentication, key exchange, replay/rekey and trust boundaries are documented in completed entries 004–007.

NHRP authentication/metadata and GRE keys are not substitutes for modern IKE/IPsec peer authentication. Cisco V1 evidence explicitly warns against wildcard preshared-key designs because compromise of one spoke can expose the VPN; certificate/strong IKE authentication is preferred where supported.

Do not implement cryptography inside an NHRP/DMVPN orchestration layer.