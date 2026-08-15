# 053 — HTTPS Proxy / HTTP CONNECT — Reference Index

Current state: **COMPLETE-REFERENCE-v2** (research/reference only)

## Dossier

- `V1_GATE_RECONCILIATION.md` — exact 20-gate V1 closure
- `REFERENCE_V2_AUDIT.md` — exact 16-gate V2 audit
- shared evidence: `research/upstreams/http-proxy-family/V1_SHARED_EVIDENCE.md`

## Standards

- RFC 9110 — HTTP Semantics: https://www.rfc-editor.org/rfc/rfc9110.html
- RFC 9112 — HTTP/1.1: https://www.rfc-editor.org/rfc/rfc9112.html

## Pinned implementations

- `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`, tree `39bb285e8839dc38e3406812ecabe29723fe5063`, permissive curl license
- `squid-cache/squid@5751f41f48e0de70f701c0c1f6073fefcf973337`, tree `1b7209570fb42c9b20142fb9b2ef00bfbe8f9ff8`, reviewed release `v7.6`, GPLv2
- `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`

## Critical boundary

Two capabilities must remain separate:

1. HTTPS proxy = TLS on the client-to-proxy hop.
2. HTTP CONNECT = tunnel establishment; no intrinsic encryption.

TLS interception/MITM is outside this entry. Inner tunneled TLS/SSH/etc. is independently owned by the inner protocol.

## Continuation

Next V2 entry: **054 — SSH Tunnel**.
