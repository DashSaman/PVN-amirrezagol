# 053 — HTTPS Proxy / HTTP CONNECT — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **053 — HTTPS Proxy / HTTP CONNECT**

Decision: **`COMPLETE-RESEARCH-v1 / TLS-TO-PROXY + HTTP TUNNEL CAPABILITIES / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

`research/upstreams/http-proxy-family/V1_SHARED_EVIDENCE.md`

## Scope boundary

The matrix label contains two related but distinct capabilities:

1. **HTTPS proxy**: the client establishes TLS to an HTTP proxy and then uses HTTP proxy semantics inside that secure client-proxy connection.
2. **HTTP CONNECT**: an HTTP proxy method that asks the proxy to establish a tunnel to an authority; after a successful 2xx response, the connection switches to byte-tunnel mode.

CONNECT does not itself encrypt tunneled bytes. HTTPS-to-proxy protects the client-to-proxy hop but does not imply end-to-end origin encryption. TLS interception/MITM is a separate high-risk feature and is outside this entry unless explicitly added later.

## Current standards / implementations

Standards:

- RFC 9110 — CONNECT semantics, proxy authentication, protection-space/security rules;
- RFC 9112 — HTTP/1.1 request target forms, including authority-form for CONNECT and absolute-form for ordinary proxy requests.

Client/library:

- `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`
- current `CURLOPT_PROXY` explicitly supports `https://` proxies using multiple TLS backends and optional HTTP/2 / experimental HTTP/3 proxy modes;
- current `CURLOPT_HTTPPROXYTUNNEL` explicitly sends CONNECT and distinguishes tunneling from normal proxy forwarding;
- proxy credentials have dedicated API fields and must not be persisted in ordinary URLs/config logs.

Server/reference:

- `squid-cache/squid@5751f41f48e0de70f701c0c1f6073fefcf973337`, tree `1b7209570fb42c9b20142fb9b2ef00bfbe8f9ff8`, current v7.6 release, GPLv2;
- `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`, current HTTPS CONNECT / TLS-server-client / ACL/auth/web-admin evidence.

## Canonical PVNetwork model

Separate typed fields:

- proxy type: HTTPS proxy vs HTTP proxy + CONNECT tunnel;
- proxy endpoint/port;
- TLS-to-proxy object: SNI, trust roots, certificate policy, TLS backend/capability metadata;
- proxy auth scheme and secure credential reference;
- CONNECT/tunnel capability/policy;
- target authority/port policy only as transient connection state;
- bypass/no-proxy/routing/DNS/TUN outside credential/TLS objects;
- inner tunneled protocol remains independent (for example HTTPS-to-origin, SSH, another proxy protocol).

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | curl/libcurl client, Squid/3proxy server references and current multi-protocol clients are identified by role. |
| 2 | Canonical sources pinned | PASS | RFC9110/9112 plus exact current curl/Squid/3proxy pins/trees. |
| 3 | Licenses reviewed | PASS | curl permissive; Squid GPLv2 server/reference; 3proxy current redistribution terms separately reviewed. |
| 4 | Complete source-tree reference | PASS | Exact current source trees and proxy/tunnel docs/API/server/test areas are pinned in shared evidence. |
| 5 | Languages/build systems | PASS | curl C; Squid C++; 3proxy C; current build/TLS/package/CI ecosystems documented. |
| 6 | Architecture | PASS | TLS-to-proxy handshake, HTTP proxy auth, CONNECT request/2xx switch, tunneled inner connection and product routing layers are separated. |
| 7 | Core/engine integration | PASS | libcurl/approved engine is reusable client candidate; server projects are interop/admin references. |
| 8 | UI/menu map | PASS for V1 | Final UI must distinguish proxy TLS verification from CONNECT and inner TLS; canonical protocols do not define a GUI. Existing clients/admin UIs are references. |
| 9 | Config/import/export | PASS | `https://` proxy URI/API settings, CONNECT tunnel option, proxy credentials and TLS options are mapped; secret-bearing proxy URLs require secure migration/redaction. |
| 10 | Persistence/secrets | PASS | Proxy passwords/client cert keys are protected-store material; TLS trust/options and endpoint are non-secret profile data; tunnel authority is transient. |
| 11 | Platform integrations | PASS for research | curl TLS backends and current client ecosystems cover major platforms; exact Store/mobile lifecycle remains later certification. |
| 12 | Logs/diagnostics | PASS | Taxonomy separates proxy TLS failure, 407/auth, CONNECT rejection/ACL, tunnel establishment, inner-protocol failure and bypass. Sensitive headers/URLs/certs are redacted. |
| 13 | Assets/screenshots | PASS / N/A | No canonical UI/brand; server dashboards/client screenshots remain reference-only. |
| 14 | Meaningful alternatives/forks | PASS | Independent curl/Squid/3proxy implementations and multi-protocol client references provide diverse evidence. |
| 15 | Issues/PRs/releases/advisories | PASS | Current 2026 activity and Squid 7.6/current curl/3proxy security processes are pinned; exact production advisories remain release-freeze work. |
| 16 | Relevant forums/docs | PASS | RFC9110/9112 plus current curl docs/source and server docs are primary evidence. |
| 17 | Tests/CI | PASS | curl extensive CI, Squid quick/slow/coverity, 3proxy build/container ecosystem; product CONNECT/TLS/auth negative tests remain later. |
| 18 | Store/privacy/security | PASS | Proxy certificate verification, credential confidentiality, CONNECT abuse/port ACL, URL/log privacy, inner-vs-outer TLS and platform restrictions are explicit. |
| 19 | PVNetwork reuse decision | PASS | Use mature permissive HTTP/TLS client library/approved engine; keep server GPL components reference-side unless product licensing architecture changes. |
| 20 | Uncertainties | PASS | Exact TLS backend, HTTP/2/3 proxy support, auth schemes, CONNECT server matrix, cert lifecycle, performance and full V2 deployment/UI/wire evidence remain later. |

## Final V1 decision

All 20 V1 research gates are evidence-backed. Entry 053 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **not implemented/certified**.
