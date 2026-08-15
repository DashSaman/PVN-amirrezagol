# Cisco GETVPN — Cryptography

Reviewed: 2026-08-15

GETVPN uses group key management to distribute policy/keying material for IPsec group security associations. The key server/GCKS authorizes group members and distributes group keys; group members then protect native group traffic with IPsec rather than forming point-to-point tunnels with every peer.

Standards boundary:
- RFC 9838 (November 2025) defines G-IKEv2, obsoletes RFC 6407 GDOI, and extends IKEv2 for group registration/rekeying. It defines GCKS/GM roles, GSA_AUTH/GSA_REGISTRATION/GSA_REKEY exchanges, key wrapping and IPsec Group Security Associations.
- RFC 6407 is legacy GDOI/IKEv1 group key management and remains operationally relevant because current Cisco IOS XE documentation still supports GDOI and migration/coexistence.
- Cisco current G-IKEv2 docs describe policy/key download, TEK/KEK/group rekey and IKEv2-based authentication, but cite an IETF standards draft; exact RFC 9838 wire conformance is therefore NOT claimed without vendor confirmation.

Cisco implementation-specific algorithm/profile support is release-dependent. Security keys/credentials must be protected as secrets. Do not implement group crypto from scratch.