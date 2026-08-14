# 051 — SOCKS5 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **051 — SOCKS5**

Decision: **`COMPLETE-RESEARCH-v1 / GENERAL SOCKS PROXY / NOT ENCRYPTED BY ITSELF / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

`research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`

## Standards authority

Primary standards:

- RFC 1928 — SOCKS Protocol Version 5;
- RFC 1929 — Username/Password Authentication for SOCKS V5;
- RFC 1961 — GSS-API Authentication Method for SOCKS Version 5, where selected implementations support it.

Current RFC Editor errata are part of the standards evidence and must be consulted when implementing tests.

## Protocol boundaries

SOCKS5 adds capabilities not present in SOCKS4/4a:

- authentication-method negotiation;
- IPv4, domain-name and IPv6 address types;
- CONNECT, BIND and UDP ASSOCIATE commands;
- method-specific authentication/encapsulation possibilities;
- explicit choice in common implementations between local DNS and proxy-side hostname resolution.

SOCKS5 itself is **not** a confidentiality protocol. RFC1929 username/password authentication carries the password without confidentiality of its own, so it must not be advertised as a secure credential channel on an untrusted path without an appropriate protected outer architecture.

## Current maintained implementations

### curl/libcurl

- `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`
- tree `39bb285e8839dc38e3406812ecabe29723fe5063`
- permissive curl license
- current `lib/socks.c` implements SOCKS5 negotiation/auth/request/resolution state transitions;
- public proxy modes distinguish `socks5://` (client/local hostname resolution in curl) from `socks5h://` (proxy-side hostname resolution);
- current curl supports username/password and GSSAPI-related SOCKS5 options when built/configured appropriately.

Primary role: mature reusable client-library candidate.

### 3proxy

- `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`
- tree `b12b0c1a80ae44158d78c44810e387f1092f676a`
- actively maintained in 2026
- current README documents SOCKSv5 plus UDP/BIND, authentication, IPv4/IPv6, chaining, logging, Docker and web administration/statistics.

Role: server/interoperability/admin reference and optional server-side component.

### OpenSSH portable

- `openssh/openssh-portable@528055671c26962093a871bff8241a48d42dd9a0`
- tree `377ab7f76a7ce3751aae83e48daaad172c46d9ec`
- current dynamic-forwarding parser supports SOCKS5 but the reviewed source accepts NO-AUTH for that local SOCKS gateway.

Role: independent parser/gateway reference, not proof of every RFC1928 method/command.

## Canonical PVNetwork model

- `protocol = SOCKS5`;
- proxy endpoint/port;
- DNS mode: local/IP vs proxy-hostname mode;
- authentication method/capability;
- username/password secure-store reference when RFC1929-style auth is used;
- command capabilities: CONNECT / BIND / UDP ASSOCIATE separately;
- address capabilities: IPv4 / domain / IPv6 separately;
- routing/TUN/per-app outside the SOCKS object;
- selected engine/version capability metadata.

Do not flatten `socks5://` and `socks5h://` into one import mode because that can change DNS privacy behavior.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / SOCKS5 conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | curl/libcurl client, 3proxy server/admin and OpenSSH dynamic gateway are current independent references. |
| 2 | Canonical sources pinned | PASS | RFC1928/1929 authority plus exact 2026 source pins/trees for curl, 3proxy and OpenSSH. |
| 3 | Licenses reviewed | PASS | curl permissive; 3proxy current BSD-style/alternative-license agreement; OpenSSH BSD-family/component licenses. |
| 4 | Complete source-tree reference | PASS | Exact trees and SOCKS5 parser/state/server project source references are pinned in shared evidence. |
| 5 | Languages/build systems | PASS | curl/OpenSSH/3proxy C codebases and current build/packaging/container systems are mapped. |
| 6 | Architecture | PASS | Method negotiation -> optional method auth -> typed command/address request -> proxy relay is separated from DNS/product TUN/outer security layers. |
| 7 | Core/engine integration | PASS | libcurl/approved existing engine is preferred for client use; 3proxy optional server; OpenSSH dynamic gateway is composition reference only. |
| 8 | UI/menu map | PASS for V1 | Protocol has no canonical GUI; product must expose endpoint, DNS mode, auth and relevant capabilities. Existing clients/admin tools are behavioral references. |
| 9 | Config/import/export | PASS | `socks5://`/`socks5h://`, credentials and local-vs-proxy DNS behavior are mapped; command/address capability is version/engine metadata rather than hidden Boolean state. |
| 10 | Persistence/secrets | PASS | RFC1929 credentials are reusable secrets in OS secure storage; endpoint/method/DNS settings remain non-secret profile state; session/UDP association is transient. |
| 11 | Platform integrations | PASS for research | Current libraries/tools are cross-platform; exact mobile VPN-service/TUN/Store lifecycle remains later certification. |
| 12 | Logs/diagnostics | PASS | Product errors must distinguish method negotiation/auth failure, DNS/address type, request command/reply, UDP/BIND setup and destination failure; credentials are redacted. |
| 13 | Assets/screenshots | PASS / N/A | SOCKS5 has no canonical UI identity; third-party GUI/server dashboards are reference-only. |
| 14 | Meaningful alternatives/forks | PASS | curl, 3proxy and OpenSSH provide independent implementation styles; multi-protocol clients already audited supply UI/import references. |
| 15 | Issues/PRs/releases/advisories | PASS | Selected upstreams are active in 2026 with current security/release/CI processes; RFC errata are explicitly included in standards review. |
| 16 | Relevant forums/docs | PASS | RFC1928/1929/1961 and current curl/OpenSSH/3proxy documentation/source are primary evidence. |
| 17 | Tests/CI | PASS | curl extensive CI/proxy tests, OpenSSH CI and 3proxy build/release/container ecosystem are mapped; product-specific UDP/BIND/auth/IPv6 tests remain later. |
| 18 | Store/privacy/security | PASS | SOCKS5 is not encryption; RFC1929 cleartext credential risk, DNS mode, UDP/BIND firewall behavior, secure storage/log redaction and platform lifecycle are explicit. |
| 19 | PVNetwork reuse decision | PASS | Prefer mature libcurl/approved engine for client functionality; enable only tested auth/address/command subsets; do not implement home-grown crypto or market plain SOCKS as VPN encryption. |
| 20 | Uncertainties | PASS | Exact production library pin/SBOM, supported auth methods, BIND/UDP server matrix, platform lifecycle/performance and V2 full wire/server/UI evidence remain later work. |

## Security/product requirements

1. RFC1929 username/password is not confidential by itself; protect the path or use an appropriate outer secure architecture.
2. Store passwords only through platform secure credential storage and redact full proxy URLs/configs.
3. Preserve local-vs-proxy DNS mode explicitly and test for DNS leaks.
4. Certify CONNECT/BIND/UDP ASSOCIATE independently; do not advertise all three because CONNECT works.
5. Certify IPv4/domain/IPv6 independently.
6. No implicit claim that SOCKS5 encrypts traffic.

## Final V1 decision

All 20 V1 research gates are evidence-backed or correctly N/A-bounded. Entry 051 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **plain-proxy capability only, not implemented/certified**.
