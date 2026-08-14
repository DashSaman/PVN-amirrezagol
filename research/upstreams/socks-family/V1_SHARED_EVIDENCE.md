# SOCKS Family — Shared V1 Evidence for Entries 049–051

Review date: 2026-08-14

Scope:

- 049 SOCKS4
- 050 SOCKS4a
- 051 SOCKS5

This shared file avoids repeating the same source/client/server evidence. Each numbered entry still requires its own independent 20-gate conclusion.

## Protocol authority and version boundaries

### SOCKS5

Primary standards:

- RFC 1928 — SOCKS Protocol Version 5: `https://www.rfc-editor.org/rfc/rfc1928.html`
- RFC 1929 — Username/Password Authentication for SOCKS V5: `https://www.rfc-editor.org/rfc/rfc1929.html`
- RFC 1961 — GSS-API Authentication Method for SOCKS Version 5, where relevant to an implementation.

Key research semantics:

- SOCKS5 begins with authentication-method negotiation;
- the base protocol defines IPv4, domain-name and IPv6 destination address types;
- request commands include CONNECT, BIND and UDP ASSOCIATE;
- DNS ownership is implementation/client-mode dependent when a hostname is available;
- RFC1929 username/password subnegotiation carries the password without confidentiality of its own, so it must not be treated as a secure credential transport over an untrusted path merely because it is "authenticated";
- SOCKS5 itself is a proxy protocol, not an encryption protocol.

RFC1928 currently has published errata; implementations/product tests should use current RFC Editor errata rather than fossilizing known textual errors.

### SOCKS4

SOCKS4 is the older IPv4-oriented generation referenced by RFC1928 as the predecessor to SOCKS5. There is no current IETF Standards Track RFC equivalent to RFC1928 that should be falsely invented as a SOCKS4 authority.

Current maintained implementation evidence provides reliable behavior anchors:

- curl SOCKS4 mode resolves the destination hostname locally and passes an IPv4 address to the proxy;
- OpenSSH portable contains an explicit SOCKS4 decoder in `channels.c` and treats ordinary SOCKS4 destinations as a binary IPv4 address;
- classic SOCKS4 has a USERID field but does not provide the SOCKS5 method-negotiation/authentication framework;
- SOCKS4 itself provides no transport encryption.

### SOCKS4a

SOCKS4a is an extension of the SOCKS4 request format so the hostname can be passed to the proxy for remote resolution.

Current maintained behavior evidence:

- curl documents `socks4a://` / `--socks4a` as asking the proxy to resolve the hostname;
- current OpenSSH source detects SOCKS4a when the address is `0.0.0.x` with nonzero final byte, then reads a NUL-terminated hostname after USERID;
- this DNS-location distinction is the central product difference between entries 049 and 050 and must be preserved in PVNetwork canonical profiles and leak tests.

SOCKS4a does not add encryption or a modern authentication framework merely because it adds remote hostname resolution.

## Current maintained client/library implementation — curl/libcurl

Repository:

`curl/curl`

Reviewed current master:

`d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`

Reviewed tree:

`39bb285e8839dc38e3406812ecabe29723fe5063`

Reviewed head date:

2026-08-14

License:

curl permissive license in `COPYING`.

Current source evidence:

- `lib/socks.c` contains an explicit nonblocking/state-machine implementation for SOCKS4 and SOCKS5;
- the reviewed state model contains separate SOCKS4 start/resolution/send/receive stages and SOCKS5 method/auth/request/resolution stages;
- the runtime context has an explicit `socks4a` flag and a local-vs-proxy resolution decision;
- public libcurl proxy APIs/documentation distinguish `socks4://`, `socks4a://`, `socks5://` and `socks5h://`;
- SOCKS4 means client/local resolution in curl;
- SOCKS4a means proxy/remote hostname resolution;
- SOCKS5 and SOCKS5-hostname likewise expose a local-vs-proxy DNS distinction;
- curl supports proxy credentials where the selected proxy protocol/authentication mode supports them.

Build/quality evidence:

- large cross-platform C codebase with CMake/autotools and extensive GitHub Actions/CI/testing;
- active security/CVE process and current releases;
- supported on the major PVNetwork desktop/mobile platforms through libcurl/library/tool ecosystems, but source portability is not product certification.

PVNetwork reuse classification:

**`REUSE-CANDIDATE as a mature proxy client/library component where its API/platform role fits`**, subject to exact release/dependency/SBOM/security review.

## Current maintained dynamic-SOCKS reference — OpenSSH portable

Repository:

`openssh/openssh-portable`

Reviewed master:

`528055671c26962093a871bff8241a48d42dd9a0`

Reviewed tree:

`377ab7f76a7ce3751aae83e48daaad172c46d9ec`

Reviewed head date:

2026-08-13

License:

OpenSSH component licenses summarized by upstream as BSD or more permissive; `LICENCE` must be retained in any reuse analysis.

Current source evidence:

- `channels.c` has explicit `channel_decode_socks4()` and SOCKS5 parsing;
- the SOCKS4 decoder explicitly recognizes SOCKS4a hostname requests;
- OpenSSH's dynamic forwarding acts as a local SOCKS4/SOCKS5 application gateway carried over SSH;
- the reviewed SOCKS5 dynamic-forwarding code accepts NO-AUTH rather than serving as a general implementation of every RFC1928 authentication method.

Product lesson:

OpenSSH is an excellent independent implementation/reference, but an SSH dynamic-forwarding session is not the same product architecture as connecting directly to a standalone remote SOCKS proxy. Do not use it to overclaim a generic SOCKS server/client feature matrix.

## Current server/reference implementation — 3proxy

Repository:

`3proxy/3proxy`

Reviewed master:

`4fb5c957046c6011b5a0b45f48c1b854daf70bca`

Reviewed tree:

`b12b0c1a80ae44158d78c44810e387f1092f676a`

Reviewed head date:

2026-08-12

License:

`copying` contains the 3proxy 0.9 Public License Agreement with BSD-style redistribution terms and also permits use under listed compatible Apache-2.0/GPL/LGPL alternatives. Exact chosen distribution path and notices still require legal review.

Current README evidence:

- current stable master is 3proxy 0.9; devel/3proxy 10 is explicitly marked not for production use;
- Windows service install path, Linux/BSD/macOS build/install paths and Docker images are documented;
- current features include SOCKSv4/4.5, SOCKSv5, SOCKSv5 UDP and BIND, authentication, chaining, IPv4/IPv6, logging and web administration/statistics;
- Docker documentation explicitly notes UDPASSOC/BIND need ephemeral-port/network treatment;
- project is actively maintained in 2026.

PVNetwork role:

server/interoperability/administration reference and potentially reusable server-side component if ever needed; **not necessary to embed inside the consumer client merely to implement a SOCKS client adapter**.

## UI / menu / configuration references

SOCKS itself has no canonical GUI.

Real UI/product references include:

- curl/libcurl: API/CLI and application embedding, correctly `N/A` for consumer GUI;
- OpenSSH: CLI/config and dynamic forwarding, correctly `N/A` for standalone consumer proxy GUI;
- 3proxy: config/service plus web administration/statistics, useful server-side UI/control reference;
- current multi-protocol clients already studied in PVNetwork (v2rayN/v2rayNG/Clash-family/Karing/etc.) provide real profile-list, endpoint, credential, remote-DNS, routing and logging UX references under their own licenses.

PVNetwork owns the final unified GUI; it must not invent a "standard SOCKS menu" that does not exist in the protocol.

## Canonical PVNetwork profile fields

Common:

- protocol/version: SOCKS4 / SOCKS4a / SOCKS5;
- proxy endpoint and port;
- destination DNS policy where the version/mode permits choice;
- credential reference only when applicable;
- chaining/pre-proxy role if supported by product policy;
- selected implementation/capability metadata;
- routing/DNS/TUN/per-app product settings outside the SOCKS protocol object.

SOCKS5 additionally needs:

- authentication method/capability;
- username/password credential reference when RFC1929-style auth is used;
- command capability (CONNECT/BIND/UDP ASSOCIATE) instead of a single Boolean "SOCKS5 supported" flag;
- IPv4/domain/IPv6 addressing capability.

## Security/privacy rules

1. SOCKS4/4a/5 are not encryption layers. Do not market a plain SOCKS connection as an encrypted VPN.
2. RFC1929 username/password has no confidentiality by itself; credentials need a trusted/protected network path or an appropriate outer secure transport/product architecture.
3. Store reusable proxy passwords in OS secure storage; redact proxy URLs/configs/logs/support bundles.
4. Remote-vs-local DNS is an explicit privacy/leak choice and needs negative tests.
5. BIND and UDP ASSOCIATE widen firewall/ephemeral-port behavior and should be enabled/certified only when intentionally supported.
6. Do not conflate `socks5://` local DNS with `socks5h://` remote DNS in import/export.
7. No home-grown cryptography is needed for SOCKS; secure outer transport, if desired, is a separate layer.

## Shared later work — not V1 blockers

Before product support claims:

- freeze exact chosen client library/core release and SBOM/advisories;
- certify DNS-local/remote behavior and leak prevention;
- certify supported SOCKS5 auth methods and negative cases;
- certify IPv4/domain/IPv6 and CONNECT/BIND/UDP ASSOCIATE separately;
- test timeout/reconnect/chaining/TUN/routing/per-app/platform lifecycle;
- test credential redaction and secure-store migration;
- benchmark chosen implementation;
- complete V2 server installers/admin UI, exhaustive client menus, wire formats, deployment topologies and exact interoperability matrix.
