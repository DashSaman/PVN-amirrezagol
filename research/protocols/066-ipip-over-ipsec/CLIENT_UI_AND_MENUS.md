# IPIP over IPsec — Client UI and Menus

Reviewed: 2026-08-15

No canonical consumer UI is defined for IPIP-over-IPsec. A future infrastructure editor would combine two independently evidenced sections:

1. IPIP: local/remote outer endpoint, inner interface addressing/routes, TTL/TOS and PMTU behavior.
2. IPsec/IKE: identity/authentication, credentials/certificates or PSK, IKE/ESP proposals, selectors/SAs, status/logging and lifecycle.

Do not present IPIP endpoint fields as security properties, and do not imply that a successful IPIP interface proves an established IPsec SA. Consumer subscription/QR/catalog/Store flows remain NOT-APPLICABLE unless a selected implementation later supplies them.
