# 052 — HTTP Proxy — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **052 — HTTP Proxy**

Decision: **`COMPLETE-RESEARCH-v1 / HTTP FORWARD PROXY / PLAINTEXT CLIENT-HOP POSSIBLE / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

`research/upstreams/http-proxy-family/V1_SHARED_EVIDENCE.md`

## Scope boundary

Entry 052 is ordinary HTTP forward-proxy behavior. For HTTP/1.1, requests to a proxy normally use the absolute-form request target. This entry is distinct from:

- TLS-protected HTTPS proxy transport;
- CONNECT tunneling;
- transparent interception/MITM;
- generic HTTP protocol entries later in the matrix.

A plaintext `http://` proxy connection does not encrypt the client-to-proxy hop.

## Current implementations

Client/library candidate:

- `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`
- tree `39bb285e8839dc38e3406812ecabe29723fe5063`
- permissive curl license
- current `CURLOPT_PROXY` documentation supports HTTP proxy, explicit credentials, environment/bypass policy and conversion/tunneling distinctions.

Server/reference:

- `squid-cache/squid@5751f41f48e0de70f701c0c1f6073fefcf973337`
- tree `1b7209570fb42c9b20142fb9b2ef00bfbe8f9ff8`
- latest reviewed release `v7.6`, 2026-06-08
- GPLv2
- current proxy/cache/ACL/auth/log/security/tests/CI source provides mature server reference.

Additional lightweight server/admin reference:

- `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`
- current HTTP/1.1 proxy, auth/log/web-admin/service/container evidence.

## Canonical PVNetwork model

- `protocol = HTTP_PROXY`;
- client-to-proxy transport = plaintext HTTP for this entry;
- endpoint/port;
- proxy auth scheme/capability;
- credential secure-store reference;
- bypass/no-proxy policy outside credential object;
- selected HTTP version/capability only if actually supported/tested by selected engine;
- routing/DNS/TUN/per-app outside the HTTP proxy protocol object.

Normal HTTP forward proxying is message forwarding, not CONNECT byte tunneling.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / HTTP Proxy conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | curl/libcurl client, Squid mature server, 3proxy lightweight server/admin and existing multi-protocol GUI references are role-separated. |
| 2 | Canonical sources pinned | PASS | RFC9110/9112 plus exact current curl/Squid/3proxy source pins/trees are recorded. |
| 3 | Licenses reviewed | PASS | curl permissive; Squid GPLv2 reference-only/default server role; 3proxy separately reviewed redistribution terms. |
| 4 | Complete source-tree reference | PASS | Exact curl/Squid/3proxy tree IDs and relevant proxy/config/docs/test areas are pinned in shared evidence. |
| 5 | Languages/build systems | PASS | curl C/CMake/autotools; Squid C++/autotools-style build; 3proxy C/CMake/Make/containers/services. |
| 6 | Architecture | PASS | Client -> HTTP forward proxy -> origin flow, proxy auth and product bypass/routing boundaries are separated from HTTPS-proxy TLS and CONNECT. |
| 7 | Core/engine integration | PASS | libcurl/approved existing HTTP client engine is preferred; Squid/3proxy are server/reference components rather than required client embeds. |
| 8 | UI/menu map | PASS for V1 | No canonical GUI; product profile needs endpoint/auth/bypass/log state. Server admin/config references exist; exhaustive menus remain V2. |
| 9 | Config/import/export | PASS | `http://` proxy endpoint/credentials, explicit API options and environment/no-proxy policy are mapped; imported credential-bearing URLs require secure migration. |
| 10 | Persistence/secrets | PASS | Proxy credentials are OS secure-store owned; endpoint/bypass/options are non-secret profile fields; logs/cache/runtime are separately owned. |
| 11 | Platform integrations | PASS for research | curl and client ecosystems are broadly cross-platform; server deployment evidence is current; exact mobile/Store/TUN lifecycle remains later. |
| 12 | Logs/diagnostics | PASS | Error taxonomy covers proxy DNS/connect, 407/auth, HTTP parsing/forwarding, origin/connectivity and bypass decisions; URLs/headers/credentials require redaction. |
| 13 | Assets/screenshots | PASS / N/A | HTTP proxy has no canonical brand/UI; server dashboards and client screenshots are reference-only under their licenses. |
| 14 | Meaningful alternatives/forks | PASS | Independent curl/Squid/3proxy implementations plus multi-protocol clients provide role-diverse evidence. |
| 15 | Issues/PRs/releases/advisories | PASS | curl/Squid/3proxy are active in 2026; Squid 7.6 and current security/CI evidence are pinned; exact production CVE review remains release-freeze work. |
| 16 | Relevant forums/docs | PASS | RFC9110/9112 and current curl/Squid/3proxy docs/source are primary evidence. |
| 17 | Tests/CI | PASS | curl extensive CI; Squid quick/slow/coverity and tests; 3proxy active build/container ecosystem. PVNetwork-specific proxy tests remain later. |
| 18 | Store/privacy/security | PASS | Plaintext client-proxy hop, proxy auth confidentiality, URL/header logging, bypass/DNS policy and dependency/Store constraints are explicit. |
| 19 | PVNetwork reuse decision | PASS | Prefer permissive mature HTTP client library/approved engine. Treat Squid as server/interoperability reference, not a closed-app embedded default. |
| 20 | Uncertainties | PASS | Exact auth schemes, HTTP version subset, chosen engine/release/SBOM, proxy server matrix, platform lifecycle/performance and V2 evidence remain later. |

## Final V1 decision

All 20 V1 research gates are evidence-backed. Entry 052 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **not implemented/certified**.
