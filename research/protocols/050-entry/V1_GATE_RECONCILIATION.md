# 050 — SOCKS4a — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **050 — SOCKS4a**

Decision: **`COMPLETE-RESEARCH-v1 / LEGACY REMOTE-DNS PROXY / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

`research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`

## Scope boundary

SOCKS4a extends the SOCKS4 request so a hostname can be sent to the proxy and resolved remotely. This distinction matters for DNS privacy and capability testing. It does **not** add modern authentication, IPv6 support or transport encryption.

Current maintained evidence:

- curl/libcurl current docs distinguish `socks4a://` / `--socks4a` and explicitly say the proxy resolves the hostname;
- current curl `lib/socks.c` has explicit SOCKS4a state/flag behavior;
- current OpenSSH `channels.c` recognizes SOCKS4a when the IPv4 field uses the `0.0.0.x` marker and then parses a NUL-terminated hostname after USERID;
- current 3proxy supports SOCKSv4/4.5 server behavior and is an active interoperability/server reference.

## Canonical PVNetwork model

- `protocol = SOCKS4a`;
- proxy endpoint/port;
- target hostname preserved for **proxy-side** DNS resolution;
- USERID only if actually used by the selected implementation/server;
- no implicit encryption/authentication claim;
- product routing/TUN/per-app settings remain outside the SOCKS4a object.

Import/export must preserve `socks4://` versus `socks4a://`; silently converting 4a to 4 can create a DNS leak.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / SOCKS4a conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | curl/libcurl, OpenSSH and 3proxy are current independent client/gateway/server references. |
| 2 | Canonical sources pinned | PASS | Exact current commits/trees are pinned in shared evidence; behavior is source-backed rather than relying on an invented standards-track RFC. |
| 3 | Licenses reviewed | PASS | curl permissive, OpenSSH BSD-family/component licenses, 3proxy current redistribution/license agreement. |
| 4 | Complete source-tree reference | PASS | Exact source trees and relevant SOCKS4a parser/state paths are recorded. |
| 5 | Languages/build systems | PASS | curl/OpenSSH/3proxy C codebases with their current build/packaging systems are mapped. |
| 6 | Architecture | PASS | SOCKS4a client sends hostname to proxy for remote resolution; this is separated from outer secure transports and product TUN/routing. |
| 7 | Core/engine integration | PASS | libcurl/approved existing proxy engines are preferred; OpenSSH/3proxy are reference/server alternatives. |
| 8 | UI/menu map | PASS for V1 | No canonical GUI; final PVNetwork UI must expose version/DNS behavior clearly. Existing clients provide behavioral references. |
| 9 | Config/import/export | PASS | `socks4a://` is a distinct import/export mode and remote-DNS semantics are explicit. |
| 10 | Persistence/secrets | PASS | Endpoint/hostname/USERID fields are mapped; no secret is invented. Any external credential must use secure storage. |
| 11 | Platform integrations | PASS for research | Current reference implementations are cross-platform; exact mobile/desktop lifecycle remains certification. |
| 12 | Logs/diagnostics | PASS | Diagnostics must distinguish proxy connect, remote-name resolution/rejection, SOCKS reply and destination connection. Hostnames may be privacy-sensitive in logs. |
| 13 | Assets/screenshots | PASS / N/A | Protocol has no canonical UI assets; third-party assets remain reference-only. |
| 14 | Meaningful alternatives/forks | PASS | curl/OpenSSH/3proxy provide independent behavior; SOCKS4 and SOCKS5 remain separate protocol entries. |
| 15 | Issues/PRs/releases/advisories | PASS | Selected upstreams are actively maintained in 2026; exact selected release advisories are a later production freeze. |
| 16 | Relevant forums/docs | PASS | Current curl documentation and current OpenSSH parser source give direct remote-DNS semantics; historical SOCKS4a extension context is retained without fabricating standards status. |
| 17 | Tests/CI | PASS | curl extensive CI/proxy tests, OpenSSH CI/parser implementation and 3proxy build/release ecosystem provide upstream quality evidence. |
| 18 | Store/privacy/security | PASS | Remote DNS improves one leak class but SOCKS4a itself is still unencrypted; hostname/log privacy and platform lifecycle are explicit. |
| 19 | PVNetwork reuse decision | PASS | Support only as a typed legacy mode through mature library/engine, preserving remote DNS; do not implement as a separate crypto core. |
| 20 | Uncertainties | PASS | Exact server compatibility, chosen release/SBOM, DNS failure behavior, platform lifecycle/performance and V2 wire/server/UI evidence remain later. |

## Final V1 decision

All 20 V1 research gates are evidence-backed or correctly N/A-bounded. Entry 050 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **legacy/plain-proxy compatibility only, not implemented/certified**.
