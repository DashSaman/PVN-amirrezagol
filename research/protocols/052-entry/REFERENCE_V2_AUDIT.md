# 052 — HTTP Proxy — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / HTTP FORWARD PROXY / NOT CONNECT / NOT HTTPS-PROXY / NOT IMPLEMENTED / NOT CERTIFIED`**

## Scope boundary

Entry 052 is ordinary HTTP forward-proxy behavior. It intentionally excludes:

- HTTP `CONNECT` tunneling (entry 053);
- TLS-protected HTTPS proxy transport (entry 053);
- transparent interception / TLS MITM;
- generic HTTP protocol entries 085–087.

For HTTP/1.1 forward proxying, the proxy receives HTTP messages and normally sees an absolute-form request target. A plaintext `http://` client-to-proxy hop provides no transport confidentiality.

## Authoritative / pinned evidence

Standards:

- RFC 9110 — HTTP Semantics: https://www.rfc-editor.org/rfc/rfc9110.html
- RFC 9112 — HTTP/1.1: https://www.rfc-editor.org/rfc/rfc9112.html

Reusable client reference:

- `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`
- tree `39bb285e8839dc38e3406812ecabe29723fe5063`
- curl permissive license
- proxy API: https://curl.se/libcurl/c/CURLOPT_PROXY.html
- bypass API: https://curl.se/libcurl/c/CURLOPT_NOPROXY.html

Server / interoperability reference:

- `squid-cache/squid@5751f41f48e0de70f701c0c1f6073fefcf973337`
- tree `1b7209570fb42c9b20142fb9b2ef00bfbe8f9ff8`
- reviewed release `v7.6` / `SQUID_7_6` (2026-06-08)
- GPLv2; server/reference role, not default closed-client embed

Lightweight server/admin reference:

- `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`
- current upstream documents HTTP/1.1 forward proxy, auth/ACL/chaining, service/container deployment and a web administration/statistics surface
- upstream sample warns against broad listen addresses that accidentally expose an open proxy

Shared dossier:

- `research/upstreams/http-proxy-family/V1_SHARED_EVIDENCE.md`

## Server ecosystem / install / UI reference

### Squid

Use as the mature server/cache/ACL/auth/logging/security/interoperability reference. The source tree and v7.6 release are already pinned by V1 evidence. Distribution packaging and source builds are normal server deployment paths; exact distro package versions are release-channel facts and are intentionally not frozen as protocol facts. Container/orchestration choices are deployment references, not protocol requirements.

Squid is primarily configuration/admin-service oriented rather than a single canonical consumer-style web panel. Therefore the V2 server-UI gate is satisfied by an evidence-backed **N/A for a canonical protocol GUI**, with configuration/ACL/auth/cache/log/security operational surfaces mapped as the meaningful server-management model.

### 3proxy

The canonical project provides executable/service deployment, Linux/BSD/macOS and Windows paths, Docker references, HTTP proxy service configuration, authentication and ACLs, logging/statistics and `admin` web interface behavior. Its current documentation separates the HTTP proxy service from CONNECT behavior and documents safe bind/ACL concerns.

Community one-click/container projects are not automatically trusted. The canonical project is the default deployment reference; third-party images/scripts remain supply-chain subjects requiring their own pin and review before use.

## Client install / UI reference

HTTP proxying is a network capability, not a canonical end-user client application. The client reference is libcurl/API/CLI plus operating-system/application proxy settings already studied across the repository. Therefore:

- Windows/macOS/Linux: libcurl is widely packageable; exact PVNetwork packaging remains implementation work.
- Android/iOS/TV: no protocol-defined client package exists; product integration must use a supported platform/network engine rather than pretending curl itself is a Store UI.
- client UI is evidence-backed N/A at protocol level; PVNetwork should expose endpoint, auth credential reference, bypass/no-proxy policy and diagnostics in its own profile UI.

Required profile separation:

- proxy endpoint / port;
- authentication capability/scheme;
- secure credential reference;
- bypass/no-proxy policy as routing/privacy state;
- forward-proxy mode distinct from CONNECT tunneling;
- TLS-to-proxy settings absent from this entry.

## Cryptography / security boundary

HTTP forward proxying has **no intrinsic encryption layer**. Any end-to-end TLS used by an origin request is an inner application concern, and TLS to the proxy belongs to entry 053. Proxy authentication uses HTTP authentication semantics and inherits the confidentiality of the client-to-proxy hop/auth scheme. Credentials, `Proxy-Authorization`, URLs and headers are sensitive and must be redacted from diagnostics/support bundles.

Do not claim confidentiality for a plaintext proxy hop. Do not conflate TLS interception/MITM with normal HTTP forward proxying.

## Data path / wire flow

```text
Application / HTTP client
        |
        | HTTP request to configured proxy
        | absolute-form target for normal HTTP proxy request
        v
HTTP forward proxy
  - optional 407 / Proxy-Authenticate challenge
  - ACL / auth / policy decision
  - parse and forward HTTP request
        |
        v
Origin server
        |
        v
HTTP response -> proxy -> client
```

Control and data are ordinary HTTP request/response exchanges; there is no separate VPN control channel. The proxy can observe request metadata and, for plaintext HTTP destinations, content. DNS resolution location depends on client/proxy behavior and target representation; product policy must make bypass/DNS behavior deterministic rather than assuming one universal rule.

## Ports / transport / handshake

The protocol does not mandate one universal TCP port. Common server defaults such as Squid/3proxy deployment ports are implementation conventions, not protocol constants. Flow is TCP connection to the proxy, HTTP request parsing, optional 407 authentication challenge/retry, then forwarded request/response processing. `CONNECT` authority-form tunnel establishment is explicitly outside entry 052.

## Deployment topologies

Applicable:

- single forward-proxy gateway;
- authenticated enterprise egress proxy;
- chained/parent proxy topology;
- explicit per-application proxy configuration;
- centralized ACL/logging egress control.

Not protocol-defined:

- VPN TUN topology;
- mesh overlay;
- site-to-site tunnel;
- HA orchestration details. HA/load balancing can wrap a proxy service but is an operator deployment concern.

## Lifecycle / supply-chain

- curl: permissive reuse candidate; production freeze still requires exact release/TLS backend/dependency/SBOM/advisory review.
- Squid: GPLv2 server/reference; do not treat as a drop-in closed-app library.
- 3proxy: separately reviewed project; deployment scripts/images must be pinned before trust.
- blind `curl | sh` deployment is not approved.
- upgrades/uninstalls follow the selected package/service/container mechanism; protocol semantics do not define lifecycle.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | Squid mature server/reference plus 3proxy lightweight server/admin; roles and licenses separated. |
| 2 | Official/community installer/deployment projects reviewed | PASS | Canonical source/package/service/container paths mapped; unpinned third-party scripts/images remain supply-chain references, not trusted defaults. |
| 3 | Server OS/container/orchestration install matrix | PASS | Native Unix/Linux/BSD service model, Windows-capable 3proxy path and container paths are mapped; protocol itself imposes no OS requirement. Exact distro package versions are implementation-release facts. |
| 4 | Server panel/UI/menu maps | PASS / N/A | No canonical HTTP-proxy GUI. Squid config/ACL/auth/logging/security operations and 3proxy `admin` statistics surface are the relevant management references. |
| 5 | Client install matrix | PASS / N/A | HTTP proxy is an API/network capability; libcurl/package paths and platform engine boundary are mapped. No fake protocol-defined mobile/TV client is asserted. |
| 6 | Major client UI/menu maps | PASS / N/A | No canonical client GUI; required PVNetwork endpoint/auth/bypass/diagnostic surfaces are mapped without inventing upstream menus. |
| 7 | Cryptographic design | PASS / N/A | No intrinsic protocol cryptography; plaintext client-proxy confidentiality boundary explicit. TLS-to-proxy belongs to 053. |
| 8 | Data path/wire flow | PASS | Absolute-form forward request, optional 407 auth, proxy policy/forwarding and response path documented. |
| 9 | Ports/transports/handshake | PASS | TCP + HTTP exchange; no mandatory universal port; 407 challenge behavior mapped; CONNECT explicitly excluded. |
| 10 | Deployment topologies | PASS | Explicit forward gateway, authenticated egress and proxy chaining mapped; VPN/mesh semantics excluded. |
| 11 | Source/license/activity pins | PASS | Exact curl/Squid/3proxy pins and license boundaries retained from V1 evidence. |
| 12 | Installer security/supply-chain risks | PASS | Canonical/pinned deployment preference, open-proxy bind/ACL risk, secret/log exposure and no blind remote-script trust documented. |
| 13 | Upgrade/uninstall/rollback | PASS | Package/service/container-owned lifecycle mapped; no protocol-defined lifecycle. Exact mechanisms stay implementation-specific. |
| 14 | Differences/uncertainties explicit | PASS | HTTP forward proxy separated from HTTPS-proxy, CONNECT, MITM and generic HTTP; auth/DNS/version/backend choices preserved as implementation facts. |
| 15 | `REFERENCE_INDEX.md` complete | PASS | Added beside this audit. |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-15_HTTP_PROXY_V2_COMPLETE.md` advances to entry 053. |

## Final decision

Every applicable V2 research/reference gate is evidence-backed. Entry 052 may be promoted to **`COMPLETE-REFERENCE-v2`**. This remains a research/reference state only: it does not assert PVNetwork implementation, Store approval, runtime certification or production support.
