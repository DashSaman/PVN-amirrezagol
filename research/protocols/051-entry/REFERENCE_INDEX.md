# 051 SOCKS5 — Reference index

State: **COMPLETE-REFERENCE-v2** (research/reference only; not implementation certification).

## Local dossier
- `README.md`
- `V1_RESEARCH.md`
- `V1_GATE_RECONCILIATION.md`
- `REFERENCE_V2_AUDIT.md`

## Authoritative protocol references
- RFC 1928 — SOCKS Protocol Version 5: https://www.rfc-editor.org/rfc/rfc1928.html
- RFC 1929 — Username/Password Authentication for SOCKS V5: https://www.rfc-editor.org/rfc/rfc1929.html
- RFC 1961 — GSS-API Authentication Method for SOCKS Version 5: https://www.rfc-editor.org/rfc/rfc1961.html
- RFC 1928 errata: https://www.rfc-editor.org/errata/rfc1928

## Shared implementation evidence
- `research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`

## Boundary summary
SOCKS5 is an application proxy protocol, not a VPN. It adds method negotiation, IPv4/domain/IPv6 addressing, CONNECT/BIND/UDP ASSOCIATE and extensible authentication compared with SOCKS4/4a. It does not itself guarantee transport encryption. RFC 1929 username/password credentials are cleartext unless protected by a separate security layer.

Next entry after promotion: **052 — HTTP Proxy**.