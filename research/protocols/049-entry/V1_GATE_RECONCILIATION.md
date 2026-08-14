# 049 — SOCKS4 — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **049 — SOCKS4**

Decision: **`COMPLETE-RESEARCH-v1 / LEGACY PLAIN PROXY / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

`research/upstreams/socks-family/V1_SHARED_EVIDENCE.md`

## Scope boundary

SOCKS4 is the older IPv4-oriented SOCKS generation. It is distinct from:

- SOCKS4a, which adds proxy-side hostname resolution;
- SOCKS5, which adds method negotiation, richer addressing and UDP/BIND semantics;
- SSH dynamic forwarding, which can expose a SOCKS4 listener but carries the resulting connection over SSH;
- encrypted VPN/proxy protocols.

SOCKS4 itself does **not** encrypt application traffic.

## Current reference implementations

### curl/libcurl

- `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`
- tree `39bb285e8839dc38e3406812ecabe29723fe5063`
- reviewed 2026-08-14
- permissive curl license
- `lib/socks.c` has an explicit SOCKS4 state machine.
- current curl docs define SOCKS4 mode as resolving the target hostname **locally** and sending the IPv4 address to the proxy.

Primary PVNetwork role: mature reusable client-library candidate.

### OpenSSH portable

- `openssh/openssh-portable@528055671c26962093a871bff8241a48d42dd9a0`
- tree `377ab7f76a7ce3751aae83e48daaad172c46d9ec`
- BSD-family/component licensing
- `channels.c` contains current SOCKS4 decoding for dynamic forwarding.

Role: independent protocol/parser/lifecycle reference. Its SOCKS listener is part of an SSH forwarding architecture, not a generic standalone remote SOCKS server.

### 3proxy

- `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`
- tree `b12b0c1a80ae44158d78c44810e387f1092f676a`
- current 2026 server project supporting SOCKSv4/4.5 and SOCKSv5
- current `copying` contains BSD-style redistribution terms and alternative listed license paths.

Role: server/interop/admin reference; not needed inside the consumer app merely to use a SOCKS4 upstream.

## Product/security model

Canonical fields:

- `protocol = SOCKS4`;
- proxy endpoint/port;
- local target-DNS result/selection policy;
- USERID only if the selected implementation/server uses it;
- routing/TUN/per-app product state outside the SOCKS4 object.

Important rules:

1. SOCKS4 target addressing is IPv4-oriented.
2. In normal SOCKS4 mode, client-side DNS may leak destination lookups outside the proxy path; this is a visible privacy/capability difference from SOCKS4a.
3. USERID is not a modern encryption/authentication framework and must not be represented as equivalent to SOCKS5 method negotiation.
4. SOCKS4 provides no confidentiality/integrity layer.
5. Any outer SSH/TLS/VPN transport is a separate composition layer and must be modeled separately.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / SOCKS4 conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | curl/libcurl client, OpenSSH dynamic gateway and 3proxy server are maintained, role-separated references. |
| 2 | Canonical sources pinned | PASS | Exact 2026 curl/OpenSSH/3proxy commits and trees are in shared evidence. Historical SOCKS4 has no modern IETF standards-track source falsely invented here. |
| 3 | Licenses reviewed | PASS | curl permissive license; OpenSSH BSD-family/component licenses; 3proxy current redistribution/license agreement documented. |
| 4 | Complete source-tree reference | PASS | Exact tree SHAs are pinned; relevant SOCKS parser/state paths and server project trees are identified. |
| 5 | Languages/build systems | PASS | curl C with CMake/autotools; OpenSSH C/autotools-style portable build; 3proxy C/CMake/Make plus service/container packaging. |
| 6 | Architecture | PASS | Direct SOCKS4 client -> proxy -> IPv4 destination is separated from SSH-carried dynamic forwarding and product TUN/routing layers. |
| 7 | Core/engine integration | PASS | libcurl is the primary reusable client candidate; OpenSSH/3proxy are optional architecture/server references. No bespoke crypto is required. |
| 8 | UI/menu map | PASS for V1 | Protocol has no canonical GUI; maintained multi-protocol clients provide endpoint/version/DNS/log profile patterns. CLI/library/server-admin N/A treatment is explicit. |
| 9 | Config/import/export | PASS | `socks4://` endpoint representation and curl/libcurl proxy API behavior are mapped; target hostname resolves locally in SOCKS4 mode. |
| 10 | Persistence/secrets | PASS | Endpoint/USERID profile state is mapped; if an implementation associates any reusable secret externally, it belongs in secure storage. Generated DNS/runtime state is transient. |
| 11 | Platform integrations | PASS for research | curl/OpenSSH/3proxy are cross-platform projects; exact PVNetwork service/TUN/Store lifecycle remains later certification. |
| 12 | Logs/diagnostics | PASS | curl/OpenSSH/3proxy provide parser/state/error logging; PVNetwork error taxonomy must separate local DNS, proxy connect, SOCKS reply and destination failures. |
| 13 | Assets/screenshots | PASS / N/A | SOCKS4 has no canonical visual assets. Third-party GUI/server dashboards are reference-only under their own rights. |
| 14 | Meaningful alternatives/forks | PASS | curl, OpenSSH and 3proxy give independent client/gateway/server implementations; SOCKS4a/SOCKS5 are separate entries rather than “forks”. |
| 15 | Issues/PRs/releases/advisories | PASS | All three selected upstreams are active in 2026 with current CI/issues/release processes. Exact production-version advisories remain release-freeze work. |
| 16 | Relevant forums/docs | PASS | RFC1928 historical predecessor reference, current curl docs, current OpenSSH source/man pages and 3proxy docs are recorded. |
| 17 | Tests/CI | PASS | curl has extensive proxy state tests/CI; OpenSSH CI/source parser coverage; 3proxy current build/release/container ecosystem. Product-specific negative/interoperability tests remain later. |
| 18 | Store/privacy/security | PASS | Plaintext transport, local-DNS leak risk, legacy IPv4 scope, log privacy, platform lifecycle and dependency/license requirements are explicit. |
| 19 | PVNetwork reuse decision | PASS | Prefer mature libcurl/approved existing proxy engine; expose SOCKS4 mainly for legacy compatibility. Do not market as encrypted VPN. |
| 20 | Uncertainties | PASS | Exact supported USERID/server behaviors, chosen production library release/SBOM, platform lifecycle, performance and V2 server/UI/wire topology remain later work. |

## Final V1 decision

All 20 V1 research gates are evidence-backed or correctly N/A-bounded. Entry 049 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **legacy/plain-proxy compatibility only, not implemented/certified**.
