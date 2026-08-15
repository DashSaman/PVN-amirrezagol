# Cisco FlexVPN — Cryptography

Reviewed: 2026-08-15

FlexVPN does not define a separate cryptographic system. Cryptographic semantics come from IKEv2/IPsec/ESP (RFC 7296, RFC 4301/4303) and Cisco-supported algorithm/authentication profiles.

The complete generic crypto/key-exchange/replay/rekey/trust evidence is reused from entry 004 and the strongSwan-family V2 dossier. Cisco-specific authorization/configuration attributes do not become cryptographic primitives.

PVNetwork must use audited/native IKEv2/IPsec engines and represent Cisco policy/extensions as configuration/capability metadata. Cisco proprietary code is reference-only.