# 047 — NaiveProxy — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **047 — NaiveProxy**

Decision: **`COMPLETE-RESEARCH-v1 / CHROMIUM-NETWORK-STACK PROXY / NOT IMPLEMENTED / NOT CERTIFIED`**

NaiveProxy is a Chromium-network-stack-based proxy/camouflage system using HTTP CONNECT tunnels over HTTPS/HTTP2 and, where selected, QUIC/HTTP3. It is not a synonym for generic HTTPS CONNECT (entry 053), HTTP/2 (086), HTTP/3 (087), or QUIC (083): the product identity also includes Chromium-like network/TLS behavior and Naive padding/application-fronting conventions.

## Canonical source and exact release pin

Canonical repository: `klzgrad/naiveproxy`.

Reviewed current stable source:

- release/tag: `v150.0.7871.63-1`
- release published: 2026-07-03
- commit: `3ba967e2d36cc133a896e81a36257ad4c6ea20f4`
- tree: `56158501cd8c99d6b5cf81d933d084e031de277a`
- pinned Chromium version: `150.0.7871.63`
- language: primarily C++ with Python/C/build tooling plus Android plugin code
- license detected at repository root: **BSD-3-Clause**

The tag points directly to the reviewed commit. Upstream explicitly warns downstreams **not to track `master` for updates**, because the branch is rebased from a new root for each Chrome release; stable tags/releases are the required provenance anchor.

The reviewed release publishes multi-platform binaries and Android plugin APKs with SHA-256 digests in GitHub release metadata. Release-artifact presence proves build output, not PVNetwork Store/device certification.

## Chromium dependency/source boundary

NaiveProxy deliberately carries a minimized Chromium source/network-stack subset and states that keeping the network behavior current with Chrome is part of its anti-fingerprinting model. The exact source tree is therefore a major part of the protocol/runtime identity, not an incidental dependency.

The root BSD-3-Clause file does **not** remove the need to audit third-party notices/licenses inherited through the Chromium subset and build dependencies. Before shipping, PVNetwork must freeze:

- exact Naive release/tag/commit/tree;
- `CHROMIUM_VERSION`;
- complete included-source/dependency manifest;
- all applicable third-party licenses/notices;
- reproducible build arguments/artifact digests/SBOM.

Do not summarize the entire shipping legal surface as "BSD-3-Clause" merely because GitHub detects that root license.

## Current architecture/protocol semantics

Canonical README describes:

`Browser -> Naive client -> censor/network -> frontend + Naive server -> Internet`

The client reuses Chromium networking to resemble regular Chrome traffic. The server side may be a well-known reverse proxy capable of routing HTTP/2 proxy traffic based on authorization headers; the maintained Naive fork of Caddy forwardproxy combines forward-proxy behavior with the Naive padding layer. HAProxy is also documented as a possible frontend architecture.

Naive streams are transported through HTTP/2 or HTTP/3 CONNECT tunnels. The informal padding specification defines:

- padded payload framing for the first eight reads/writes;
- random payload padding up to 255 bytes;
- H2 RST_STREAM camouflage/padding behavior;
- H2 CONNECT request/response `padding` headers;
- capability negotiation via presence of the padding header;
- interoperability with ordinary HTTP/2 proxies/clients when padding is not negotiated.

The README also documents HTTP/2/HTTP/3 CONNECT Fast Open support through a `fastopen` header. First-contact/padding negotiation constraints must be preserved; PVNetwork must not blindly send padded early payload before server capability is known.

## Client/config/runtime model

Exact reviewed `USAGE.txt` exposes:

- local listeners: `socks://`, `http://`, `redir://`;
- upstream proxy chains with `http://`, `https://`, `quic://`; SOCKS has separate chaining limitations;
- username/password embedded in proxy/listen URIs;
- `insecure-concurrency` with an explicit upstream warning that more tunnel connections improve some bad-network behavior but increase detectability/security risk;
- tunnel and idle timeouts;
- extra headers;
- host-resolver rules and synthetic DNS range for redirect mode;
- logging and Chromium NetLog;
- TLS key logging for debugging;
- opt-out of the current default post-quantum key agreement via `--no-post-quantum`.

Canonical simple client config:

```json
{
  "listen": "socks://127.0.0.1:1080",
  "proxy": "https://user:pass@example.com"
}
```

or a `quic://user:pass@example.com` upstream when appropriate.

PVNetwork canonical model must therefore separate:

- protocol/runtime = NaiveProxy;
- exact Naive/Chromium runtime version;
- upstream scheme (`https` vs `quic`) and endpoint;
- username/password as secure-store-backed credentials;
- local listener mode;
- tunnel/idle/concurrency/fast-open policy;
- extra headers and resolver settings;
- logging/netlog/TLS-key-log diagnostic policy;
- routing/TUN/per-app settings owned by the product layer;
- original imported config separately from normalized/generated runtime config.

## Server evidence and license boundary

Canonical server path documented by upstream:

- `klzgrad/forwardproxy`, branch `naive`, a fork of `caddyserver/forwardproxy`;
- repository license detected: **Apache-2.0**;
- used with Caddy/xcaddy and standard TLS certificate configuration;
- supports basic authentication, proxy-hiding/probe-resistance settings and Naive padding behavior.

PVNetwork must treat server/Caddy/forwardproxy licenses and notices independently from the Naive client binary's BSD/Chromium source surface.

## GUI/client ecosystem evidence

The official NaiveProxy README currently names Windows, Android, Linux, macOS and OpenWrt support and references Android hosts including Exclave, husi and NekoBox. It also explicitly names **v2rayN** as a GUI integration.

Current `2dust/v2rayN` source contains dedicated Naive format/import handling (`NaiveFmt.cs`), Naive protocol type/core plumbing, add-server UI/view-model paths and sing-box outbound mapping. Current v2rayN repository license is GPL-3.0.

This is enough for V1 UI/profile/import architecture evidence. v2rayN source/assets are reference-only for a closed PVNetwork GUI unless a GPL-compatible distribution strategy is deliberately chosen.

## Privacy/security diagnostics

Upstream defaults to no log output for privacy unless logging is explicitly enabled. PVNetwork should keep that privacy posture visible.

High-sensitivity outputs:

- proxy URI username/password;
- authentication headers / extra headers;
- Chromium NetLog, which can contain detailed network metadata;
- `ssl-key-log-file`, which intentionally exports TLS secrets for traffic decryption/debugging.

TLS key logs must be opt-in developer diagnostics with prominent warnings, short-lived storage and exclusion from ordinary support bundles/telemetry. They are not ordinary logs.

`insecure-concurrency` is also security-sensitive: upstream explicitly warns that increasing concurrent tunnel connections can make tunneling easier to detect. It must not be silently increased as a "performance optimization".

## Tests / CI

Current HEAD itself was introduced with a commit titled `Add continuous integration and tests`. The exact tree contains `.github/workflows/build.yml` and a top-level `tests/` directory.

`tests/basic.py` exercises startup/config-file handling, multiple listeners/proxies, SOCKS/HTTP combinations and chains, authentication including special/empty values, platform/QEMU paths and local HTTPS-backed proxy flows. The source tree additionally carries Chromium networking test support for HTTP2/SPDY/QUIC/TLS components.

These are upstream tests, not PVNetwork certification. The selected release still needs exact interop/device/lifecycle tests in the product.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / NaiveProxy conclusion |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | Canonical Naive client/runtime is primary; official README lists platform clients/Android hosts and v2rayN GUI integration; Caddy/Naive forwardproxy is the canonical server path. |
| 2 | Canonical sources pinned | PASS | Stable `v150.0.7871.63-1`, commit `3ba967...`, tree `561585...`, exact Chromium `150.0.7871.63`, server fork and current v2rayN source paths are recorded. |
| 3 | Licenses reviewed | PASS | Naive root BSD-3-Clause; Naive Caddy forwardproxy fork Apache-2.0; v2rayN GPL-3.0 reference-only by default. Chromium/third-party licenses remain required per exact shipping source tree/SBOM. |
| 4 | Complete source-tree reference/manifest captured | PASS | Exact recursive tree `561585...` is pinned; it enumerates CI, APK/plugin, source, tests, tools, README/USAGE/LICENSE and Chromium version. Full shipping third-party manifest is a freeze/build obligation, not hidden V1 research. |
| 5 | Languages/build systems mapped | PASS | Chromium-derived C++/C/Python/Starlark/Objective-C++ surface, Android Gradle/Kotlin plugin layer, GitHub build workflow; Caddy server Go/xcaddy; v2rayN C# desktop GUI. |
| 6 | Architecture mapped | PASS | Chromium client stack, HTTPS/QUIC proxy, H2/H3 CONNECT, frontend/probe resistance, Naive padding negotiation, server forward proxy and local SOCKS/HTTP/redir layers are mapped. |
| 7 | Core/engine integration mapped | PASS | PVNetwork should use a version-pinned Naive process/component adapter rather than re-create Chromium TLS/H2/H3 fingerprints in application code; server/client components remain separately versioned. |
| 8 | UI/menu map completed | PASS for V1 | v2rayN has dedicated Naive format/type/add-server/view-model/core-config paths; Android hosts named by canonical upstream provide additional behavioral reference. Exhaustive screenshots remain V2. |
| 9 | Config/import/export mapped | PASS | Official JSON/CLI URI grammar covers listeners, HTTPS/QUIC upstreams, credentials, chains, concurrency/timeouts, headers, resolver and diagnostics; original imported source is preserved separately. |
| 10 | Persistence/secrets mapped | PASS | User/password and sensitive extra headers are credentials; store in OS secure storage. NetLog/TLS key log are diagnostic artifacts, not profile persistence. Synthetic DNS mapping/runtime tunnel state is transient. |
| 11 | Platform integrations mapped | PASS for research | Current release has multi-platform artifacts plus Android plugin APKs; official README names Windows/Android/Linux/macOS/OpenWrt. Exact TUN/VPN-service/Store lifecycle remains product certification. |
| 12 | Logs/diagnostics mapped | PASS | Default no-log privacy behavior, normal log, NetLog and TLS key log are distinguished. Credential/header/key material redaction/export policy is explicit. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Canonical runtime is CLI/config-first; v2rayN and Android hosts provide real UI references. Third-party branding/assets are not required or automatically reusable. |
| 14 | Meaningful forks/alternatives reviewed | PASS | Naive Caddy forwardproxy fork is server authority; ordinary H2 proxies can interoperate without padding; HAProxy frontend, v2rayN/Android hosts and the generic HTTP/HTTPS/QUIC layers are distinguished from Naive-specific behavior. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | Latest stable release and its exact Chromium pin are current; upstream update model/rebase warning is captured. Current CI/tests change and release cadence support the rule that old Chrome-signature builds must not be silently treated as current. Vulnerability/SBOM review remains exact-build release work. |
| 16 | Relevant forums/docs reviewed | PASS | Canonical README, USAGE, padding specification, wiki-linked server/OpenWrt/performance material and Chromium proxy/network documentation are primary evidence. |
| 17 | Tests/CI reviewed | PASS | Current build workflow, top-level functional tests and Chromium-derived network/TLS/H2/H3 test surface are mapped. PVNetwork independent interop/negative/device tests remain later acceptance work. |
| 18 | Store/privacy/security implications reviewed | PASS | Chromium-update fingerprint dependency, credential URIs, no-log default, dangerous TLS key logging, NetLog privacy, concurrency detectability, TLS trust/PQ behavior, Android plugin and third-party-license surface are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Prefer exact upstream Naive releases/subprocess/component integration to preserve Chromium parity; do not rewrite the Chromium network fingerprint stack. BSD client code is potentially reusable with notices, but full Chromium third-party notices/SBOM are mandatory; GPL GUI code is reference-only by default. |
| 20 | Uncertainties explicitly listed | PASS | Exact production release/update SLA, complete third-party license/SBOM/advisory set, H2-vs-H3 server matrix, QUIC availability, application-fronting deployment, Android host lifecycle, performance, Store behavior and V2 wire/server/UI/topology evidence remain later work. |

## Security/product rules that survive handoff

1. Pin a stable release/tag; do not track rebased `master` as a production provenance source.
2. Keep Naive and Chromium versions together in every capability/support record.
3. Store upstream proxy credentials in secure storage and redact full `https://user:pass@...` / `quic://...` values.
4. Preserve TLS verification and Chromium certificate behavior; no silent insecure bypass.
5. `ssl-key-log-file` is a dangerous developer-only export, not a normal logging option.
6. Do not silently increase `insecure-concurrency`; upstream warns this can increase detectability.
7. Preserve padding capability negotiation and ordinary-proxy interoperability.
8. Do not claim root BSD license covers all Chromium third-party components without the exact notices/SBOM audit.
9. Do not copy GPL GUI code/assets into a closed app by default.

## Later acceptance work — not V1 blockers

Before a support claim: freeze exact Naive release/Chromium version/build tree/artifact digests/full SBOM/notices/advisories; verify Caddy/forwardproxy server version and TLS/auth config; test H2 and H3/QUIC paths, padding negotiation and ordinary-proxy fallback, credential failures/certificate errors, fast-open first-contact behavior, chains, DNS/redir, IPv4/IPv6, reconnect/network handover/suspend/crash cleanup, dangerous diagnostic redaction, Android/desktop lifecycle and performance/detectability-sensitive settings; then complete V2 server installers/panels, exhaustive client menus, crypto/wire flow, ports/handshake and deployment topology evidence.

## Final V1 decision

All 20 original V1 research gates are evidence-backed with Chromium-version provenance, padding/CONNECT behavior, server/client/license/security boundaries and later certification gaps explicit. Entry 047 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
