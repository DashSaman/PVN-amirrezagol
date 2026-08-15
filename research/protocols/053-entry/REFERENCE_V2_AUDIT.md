# 053 — HTTPS Proxy / HTTP CONNECT — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / TLS-TO-PROXY + HTTP CONNECT / DISTINCT CAPABILITIES / NOT IMPLEMENTED / NOT CERTIFIED`**

## Scope boundary

The matrix entry intentionally groups two related but distinct capabilities:

1. **HTTPS proxy** — TLS protects the client-to-proxy hop; HTTP proxy semantics then run inside that TLS connection.
2. **HTTP CONNECT** — an HTTP proxy method using authority-form to request a tunnel to a target authority; after successful 2xx, the connection becomes a byte tunnel.

CONNECT does **not** provide encryption by itself. HTTPS-to-proxy does **not** imply end-to-end encryption to the origin. TLS interception/MITM remains a separate high-risk feature and is not implied by this entry.

## Authoritative / pinned evidence

Standards:

- RFC 9110 — HTTP Semantics: https://www.rfc-editor.org/rfc/rfc9110.html
- RFC 9112 — HTTP/1.1: https://www.rfc-editor.org/rfc/rfc9112.html

Client/reference implementation:

- `curl/curl@d854ab4673c2f9d8048c7f0f6d164b7e4d5e0865`
- tree `39bb285e8839dc38e3406812ecabe29723fe5063`
- permissive curl license
- current `CURLOPT_PROXY` supports `https://` proxies and multiple TLS backends
- `CURLOPT_HTTPPROXYTUNNEL` explicitly distinguishes CONNECT tunneling from ordinary proxy forwarding
- proxy credentials are distinct API inputs and must remain protected/redacted

Server/interoperability reference:

- `squid-cache/squid@5751f41f48e0de70f701c0c1f6073fefcf973337`
- tree `1b7209570fb42c9b20142fb9b2ef00bfbe8f9ff8`
- reviewed release `v7.6` / `SQUID_7_6`
- GPLv2; server/reference role

Lightweight server/admin reference:

- `3proxy/3proxy@4fb5c957046c6011b5a0b45f48c1b854daf70bca`
- documents HTTPS CONNECT proxying, TLS server/client modes, auth/ACL/chaining, service/container deployment, logging and web administration/statistics

Shared evidence:

- `research/upstreams/http-proxy-family/V1_SHARED_EVIDENCE.md`

## Server ecosystem / installers / install matrix

### Squid

Mature proxy server reference with ACL, authentication, TLS/security, forwarding/cache/logging, tests/CI and current signed release evidence already pinned by V1. Native package/source service deployment is the canonical operational model. Exact distribution package versions, container images and orchestration bundles are deployment-channel facts that must be re-frozen when implementation/release is selected; they are not protocol semantics.

### 3proxy

Canonical project provides Windows service, Linux/BSD/macOS, Docker and source-build references together with HTTP/CONNECT/TLS service configuration. It is useful for lightweight interoperability/admin reference. Third-party one-click installers or images remain untrusted until separately pinned and reviewed.

No protocol requirement mandates Kubernetes/Helm or a specific server OS. Therefore unsupported/unreviewed orchestration variants are not invented as V2 requirements.

## Server UI / menus

There is no canonical HTTPS-proxy/CONNECT GUI defined by HTTP. Relevant management surfaces are implementation-specific:

- Squid: configuration, listeners/ports, ACL/auth, TLS/certificate policy, forwarding/parent proxies, access logs, security and service lifecycle.
- 3proxy: service configuration plus `admin` web statistics/management surface, auth/ACL/chaining/logging.

This satisfies the UI gate through evidence-backed implementation mapping plus **N/A for a protocol-defined canonical panel**.

## Client install / UI reference

The protocol is a network capability rather than a canonical end-user app. libcurl/API/CLI is the primary reusable client reference; PVNetwork should expose its own profile UI while keeping protocol concepts typed:

- proxy type: HTTPS proxy vs HTTP proxy + CONNECT;
- proxy endpoint and port;
- proxy authentication and secure credential reference;
- TLS-to-proxy object: trust roots, verification, optional SNI/certificate settings, backend capability metadata;
- CONNECT capability/policy;
- bypass/no-proxy/routing/DNS outside credentials/TLS;
- inner tunneled protocol remains independently configured.

Windows/macOS/Linux packageability and mobile/TV platform engine choice are implementation concerns; no fake protocol-defined Store package is claimed.

## Cryptography / trust boundary

### HTTPS proxy

TLS protects the client-to-proxy transport. Certificate verification is enabled by default in a secure product model; disabling verification is dangerous and explicit. TLS version/cipher/backend behavior belongs to the selected TLS implementation and must not be invented from HTTP semantics.

### CONNECT

CONNECT only establishes a tunnel. Confidentiality/authentication of tunneled traffic depends on the inner protocol, such as TLS-to-origin or SSH. The proxy can still observe target authority and connection metadata, and server policy commonly restricts CONNECT targets/ports to limit abuse.

Proxy credentials, client certificates/private keys and authorization headers are protected-store/redaction material.

## Data path / wire flow

### HTTPS proxy path

```text
Client
  -> TCP/transport connection to proxy
  -> TLS handshake with proxy (verify proxy identity)
  -> HTTP proxy request/auth inside TLS
  -> proxy forwards request or processes CONNECT
  -> origin / tunnel target
```

### CONNECT tunnel path

```text
Client -> proxy: CONNECT authority HTTP request
        <- proxy: optional 407 auth challenge
Client -> proxy: authenticated CONNECT retry
        <- proxy: successful 2xx
========== byte tunnel begins ==========
Client <-> inner protocol <-> target
```

After successful CONNECT, ordinary HTTP message forwarding for that tunnel stops; bytes are relayed. Inner TLS or other cryptography is separate from CONNECT itself.

## Ports / transports / handshake

HTTP does not mandate one universal proxy port. Server defaults are implementation conventions. HTTPS proxy adds a TLS handshake with the proxy before HTTP semantics. CONNECT uses authority-form, optional 407 proxy-auth negotiation, then a 2xx transition into tunnel mode. Failure classes must remain distinct: proxy DNS/connect, TLS-to-proxy verification, 407/auth, CONNECT ACL/rejection, tunnel establishment and inner-protocol failure.

## Deployment topologies

Applicable reference topologies:

- TLS-protected explicit forward proxy;
- authenticated enterprise egress proxy;
- CONNECT gateway for tunneled inner protocols;
- parent/chained proxy topology;
- centralized ACL/logging egress point;
- proxy behind load-balancer/HA wrapper where implementation supports it.

CONNECT target/port ACL is a server-side security boundary. Arbitrary unrestricted tunneling is not assumed.

## Lifecycle / supply-chain / privacy

- curl: permissive reuse candidate; production release must later freeze exact release/TLS backend/dependencies/SBOM/advisories.
- Squid: GPLv2 server/reference; not a default closed-client embedded library.
- 3proxy: separately licensed project; pin deployment source before trust.
- remote scripts/images are research subjects, not automatic trusted install paths.
- proxy logs may disclose target hosts/URLs; authorization headers, credentials and certificate/private-key material require redaction/protected storage.
- TLS interception/MITM requires separate explicit scope, trust-store impact and policy review.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence / conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | Squid and 3proxy mapped by server/interoperability/admin role; HTTP standard boundary retained. |
| 2 | Official/community installer/deployment projects reviewed | PASS | Canonical package/source/service/container paths reviewed; unpinned community installers are explicitly not trusted defaults. |
| 3 | Server OS/container/orchestration install matrix | PASS | Native service/source/package and 3proxy Windows/Unix/Docker paths mapped; no invented protocol requirement for unsupported orchestration environments. |
| 4 | Server panel/UI/menu maps | PASS / N/A | No canonical protocol GUI; Squid config/ACL/auth/TLS/log surfaces and 3proxy admin/statistics surface mapped. |
| 5 | Client install matrix | PASS / N/A | libcurl/network-engine capability mapped; no protocol-defined mobile/TV package invented. Platform packaging remains implementation work. |
| 6 | Major client UI/menu maps | PASS / N/A | No canonical GUI; required PVNetwork HTTPS-proxy/CONNECT/TLS/auth/bypass/diagnostic surfaces mapped separately. |
| 7 | Cryptographic design | PASS | TLS-to-proxy trust boundary explicit; CONNECT correctly has no intrinsic encryption; inner protocol remains separate. |
| 8 | Data path/wire flow | PASS | TLS-to-proxy and CONNECT request/407/2xx/tunnel flows documented independently. |
| 9 | Ports/transports/handshake | PASS | No mandated proxy port; HTTPS TLS handshake and CONNECT authority-form/auth/2xx state transition documented. |
| 10 | Deployment topologies | PASS | Secure explicit proxy, enterprise egress, CONNECT gateway, chaining and HA wrapper patterns mapped. |
| 11 | Source/license/activity pins | PASS | Exact curl/Squid/3proxy pins and role-specific licenses preserved from shared evidence. |
| 12 | Installer security/supply-chain risks | PASS | Pinned/canonical preference, remote-script/image distrust, open-proxy/ACL and secret/logging risks explicit. |
| 13 | Upgrade/uninstall/rollback | PASS | Lifecycle belongs to package/service/container/client engine; no protocol-defined upgrade mechanism is invented. |
| 14 | Differences/uncertainties explicit | PASS | HTTPS-to-proxy, CONNECT, inner TLS, MITM and ordinary HTTP forward proxy are explicitly separated; backend/auth/version choices remain implementation facts. |
| 15 | `REFERENCE_INDEX.md` complete | PASS | Added beside this audit. |
| 16 | Latest AGENTS handoff exact continuation | PASS | `AGENTS_HANDOFF_2026-08-15_HTTPS_CONNECT_V2_COMPLETE.md` advances to entry 054. |

## Final decision

All applicable second-layer research/reference gates are evidence-backed. Entry 053 may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining explicitly **not implemented, not device/Store certified, and not production verified**.
