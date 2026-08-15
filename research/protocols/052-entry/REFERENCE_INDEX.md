# 052 — HTTP Proxy — Reference Index

Current state: **COMPLETE-REFERENCE-v2** (research/reference only; not implementation or certification)

## Core dossier

- `README.md` — entry label
- `V1_RESEARCH.md` — compact original research notes
- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 closure
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 reference audit
- shared family evidence: `research/upstreams/http-proxy-family/V1_SHARED_EVIDENCE.md`

## Primary specifications

- RFC 9110 — HTTP Semantics: https://www.rfc-editor.org/rfc/rfc9110.html
- RFC 9112 — HTTP/1.1: https://www.rfc-editor.org/rfc/rfc9112.html

## Pinned implementations

- client/reference: `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`, tree `39bb285e8839dc38e3406812ecabe29723fe5063`, curl permissive license
- server/reference: `squid-cache/squid@5751f41f48e0de70f701c0c1f6073fefcf973337`, tree `1b7209570fb42c9b20142fb9b2ef00bfbe8f9ff8`, reviewed release `v7.6`, GPLv2
- lightweight server/admin reference: `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`

## Boundary

This entry is ordinary HTTP forward-proxy behavior. It does **not** cover TLS-to-proxy HTTPS proxy transport, HTTP CONNECT tunneling, transparent TLS interception/MITM, or generic HTTP transport entries later in the matrix.

## Security summary

Plain HTTP client-to-proxy transport is not confidential. Proxy credentials and `Proxy-Authorization` are reusable secrets; URLs/headers/logs require redaction. Bind/ACL mistakes can create an open proxy. Third-party installer/container projects are not trusted merely because they are public.

## Continuation

Next numbered V2 entry: **053 — HTTPS / HTTP CONNECT**. Keep two sub-modes separate inside that entry:

1. TLS-protected connection from client to proxy (HTTPS proxy), and
2. HTTP CONNECT tunnel establishment.
