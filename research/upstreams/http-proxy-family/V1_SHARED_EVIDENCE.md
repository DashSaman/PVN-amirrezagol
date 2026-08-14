# HTTP Proxy / HTTPS Proxy / CONNECT — Shared V1 Evidence

Review date: 2026-08-14

Scope:

- 052 HTTP Proxy
- 053 HTTPS Proxy / HTTP CONNECT

The matrix groups HTTPS proxy transport and HTTP CONNECT in entry 053 for scope convenience, but the dossier must keep them distinct:

1. **HTTP forward proxy** — client speaks HTTP to a proxy, commonly using absolute-form request targets for normal proxied HTTP requests.
2. **HTTPS proxy** — the client-to-proxy hop itself is protected by TLS, then HTTP proxy semantics operate inside that secure connection.
3. **HTTP CONNECT** — a client asks an HTTP proxy to establish a tunnel to an authority; after a successful 2xx response, bytes are tunneled rather than forwarded as ordinary HTTP messages.

These are not three synonyms.

## Standards authority

Primary current standards:

- RFC 9110 — HTTP Semantics: `https://www.rfc-editor.org/rfc/rfc9110.html`
- RFC 9112 / STD 99 — HTTP/1.1: `https://www.rfc-editor.org/rfc/rfc9112.html`
- current RFC Editor errata must be consulted for implementation/testing.

Important current semantics:

- ordinary requests sent to an HTTP proxy use **absolute-form** request targets rather than origin-form, except CONNECT/server-wide OPTIONS cases;
- CONNECT uses authority-form and switches to tunnel mode after a successful response;
- proxy authentication uses `Proxy-Authenticate` / `Proxy-Authorization` and 407 responses;
- CONNECT to arbitrary destinations carries security/abuse risk and proxies commonly restrict allowed targets/ports;
- HTTP proxy authentication credentials inherit the confidentiality properties of the client-to-proxy connection/auth scheme; an unencrypted HTTP proxy hop must not be marketed as protecting credentials or traffic.

## Current reusable client implementation — curl/libcurl

Repository:

`curl/curl`

Reviewed commit:

`d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`

Reviewed tree:

`39bb285e8839dc38e3406812ecabe29723fe5063`

Reviewed head date:

2026-08-14

License:

permissive curl license (`COPYING`).

Current proxy API evidence:

- `CURLOPT_PROXY` supports `http://` and `https://` proxies plus explicit credentials/options;
- current libcurl can use multiple TLS backends for HTTPS proxies;
- proxy connection may use HTTP/1 by default, HTTP/2 when selected/supported, and experimental HTTP/3 proxy support in capable builds;
- `CURLOPT_HTTPPROXYTUNNEL` explicitly distinguishes normal HTTP proxying from CONNECT tunneling;
- normal HTTP proxying can translate non-HTTP URL operations into HTTP proxy requests, while CONNECT creates a byte tunnel;
- environment variables and `NO_PROXY`/`CURLOPT_NOPROXY` are separate policy inputs that a unified app must not silently conflate with an explicitly imported profile;
- proxy usernames/passwords can be supplied separately from the proxy URL and therefore should be stored as secure credential references in PVNetwork.

Reuse classification:

**`REUSE-CANDIDATE`** as a mature permissive client library when its API/build size/platform role fits. Exact selected release/TLS backends/SBOM/advisories still require production freeze.

## Current server/reference implementation — Squid

Repository:

`squid-cache/squid`

Reviewed master:

`5751f41f48e0de70f701c0c1f6073fefcf973337`

Reviewed tree:

`1b7209570fb42c9b20142fb9b2ef00bfbe8f9ff8`

Reviewed head date:

2026-08-11

Latest reviewed release:

`v7.6 / SQUID_7_6`, published 2026-06-08 with signed source archives/hashes in the GitHub release.

License:

GPLv2 (`COPYING`).

Source/build/quality evidence includes:

- C++ proxy/cache codebase;
- configuration and access-control framework;
- TLS/security, auth helpers, forwarding/cache/logging/ICAP/adaptation source;
- GitHub Actions quick/slow/coverity workflows;
- extensive tests and docs;
- current security reporting policy;
- release source archives and signatures.

Role:

**server/interoperability/security/admin reference**. Directly embedding Squid into a closed consumer application is not the default reuse path due GPLv2 and architectural mismatch.

## Additional server/admin reference — 3proxy

Shared pin from SOCKS-family research:

`3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`

Current README documents:

- HTTP/1.1 forward proxy;
- HTTPS CONNECT proxy;
- TLS server/client proxy modes;
- auth, ACL/chaining;
- Windows service, Linux/BSD/macOS builds, Docker;
- logging and web administration/statistics.

Use as a lightweight current server/admin/reference implementation under its separately reviewed license terms.

## UI / configuration / persistence lessons

HTTP proxy protocols do not define a canonical GUI.

Useful product/reference surfaces:

- curl/libcurl: API/CLI, correctly N/A for consumer UI;
- Squid: server configuration, ACL/auth/cache/log/admin/security operational model;
- 3proxy: config/service/web-admin model;
- multi-protocol desktop/mobile clients already audited by PVNetwork: profile endpoint, credential, bypass/no-proxy, CONNECT/tunnel, logging and routing UX references.

PVNetwork canonical profile should separate:

- proxy transport: plaintext HTTP vs TLS-to-proxy HTTPS;
- proxy endpoint/port;
- proxy authentication scheme/capability;
- credential reference;
- normal forward-proxy vs CONNECT tunnel policy/capability;
- TLS trust/SNI/certificate policy for HTTPS proxy as a separate secure-transport object;
- bypass/no-proxy/routing policy outside core protocol credentials;
- selected HTTP version/capability only where the chosen client library exposes/tested it.

## Security/privacy rules

1. Plain HTTP proxying does not encrypt client-to-proxy traffic.
2. HTTPS proxy means TLS **to the proxy**, not automatically end-to-end encryption to every destination.
3. CONNECT is a tunnel establishment method; whether tunneled payload is encrypted depends on the inner protocol (for example TLS to an origin).
4. Proxy credentials are reusable secrets and must be secure-store owned/redacted.
5. TLS verification for an HTTPS proxy is ON by default; proxy certificate bypass must be dangerous/explicit.
6. CONNECT target/port ACLs are a server-side security boundary; arbitrary tunneling should not be assumed or enabled by default.
7. `NO_PROXY`/bypass rules are routing/privacy state and need deterministic matching/tests.
8. Proxy logs can reveal full URLs/hostnames and credentials if poorly configured; support bundles must redact.
9. TLS interception/MITM proxying is a different high-risk feature and is **not** implied by ordinary HTTPS proxy/CONNECT support.

## Shared later work — not V1 blockers

- freeze exact curl/library release/TLS backend/dependencies/SBOM;
- certify HTTP/1.1 vs HTTP/2/experimental HTTP/3 proxy modes actually advertised;
- test proxy-auth schemes and credential lifecycle;
- test CONNECT allow/deny/error cases and tunneled TLS behavior;
- test HTTP absolute-form behavior, redirects, large headers/body/streaming;
- test HTTPS-proxy certificate trust, SNI, rotation, invalid certs;
- test no-proxy/bypass/DNS/routing/per-app and leak behavior;
- test proxy chains/pre-proxy only if product scope includes them;
- complete V2 server installers/admin menus, exhaustive client UI, data path/wire flow and deployment topologies.
