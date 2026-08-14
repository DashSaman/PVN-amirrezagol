# 039 — Trojan — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **039 — Trojan**

Decision: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT CERTIFIED`**

This file reconciles Trojan against all 20 original gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`. Shared Xray/client evidence is reused only where it genuinely applies. Trojan remains its own application-proxy protocol; TLS and other transports/security layers remain separate configuration dimensions.

## Primary protocol/core evidence

### Xray implementation candidate

- repository: `XTLS/Xray-core`
- reviewed commit: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- pinned recursive tree already recorded by the shared Xray dossier: `46ee908a9a67513d3c85bbf998be5d553a078109`
- root license: MPL-2.0
- Trojan-specific source areas at the reviewed pin include:
  - `proxy/trojan/trojan.go`
  - `proxy/trojan/config.go`
  - `proxy/trojan/client.go`
  - `proxy/trojan/server.go`
  - `proxy/trojan/protocol.go`
  - `proxy/trojan/validator.go`
  - `proxy/trojan/config.proto`
  - `infra/conf/trojan.go`
  - `proxy/trojan/protocol_test.go`
- shared evidence: `research/upstreams/xray-family/`
- wrapper candidate: `XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a` (MIT wrapper; Xray/MPL/dependency obligations remain separate)

The reviewed Xray schema models the Trojan account with a password. The reviewed implementation derives the on-wire credential key from SHA-224 of the password and supports typed TCP/UDP destinations. These details are protocol/core semantics; TLS configuration is a separate stream/security layer and must remain separate in PVNetwork's canonical profile.

### Historical protocol/reference implementation

- repository: `trojan-gfw/trojan`
- reviewed commit: `3e7bb9aecdc694f9bcae8d646fae395f773d60f8`
- recursive tree: `7d474d9099336c5e86024df3cb72f327f6594c22`
- latest GitHub release observed: `v1.16.0`, published 2020-06-10
- reviewed master head date: 2020-11-08
- license: GPLv3
- implementation/build: C++ with CMake; documented dependencies include Boost, OpenSSL and libmysqlclient
- tree includes `src/`, `docs/`, `examples/`, `Dockerfile`, CMake files, Azure pipeline config, tests/supporting source, package/build docs and protocol/config documentation.

This project is an important historical/protocol-behavior reference, but its age and GPLv3 license make it **REFERENCE-ONLY** by default for a closed commercial PVNetwork application.

### Meaningful fork/reference

- repository: `p4gefau1t/trojan-go`
- reviewed master head: `2dc60f52e79ff8b910e78e444f1e80678e936450`
- reviewed head date: 2021-09-14
- latest release observed: `v0.10.6`, published 2021-09-14
- license: GPLv3
- implementation: Go with Go modules/build tags and GitHub Actions test workflow evidence at the reviewed head.

`trojan-go` is useful for cross-implementation behavior and historical feature/test comparison, but it is also old and GPLv3; it is therefore **REFERENCE-ONLY / COMPATIBILITY-TEST REFERENCE** by default rather than the preferred embedded production engine.

## Protocol boundary

Trojan is a password-authenticated application proxy protocol designed to run inside a genuine TLS connection. The historical specification and the reviewed Xray implementation agree on a compact request header carrying a derived password token, command and destination, with TCP and UDP request modes.

PVNetwork must keep these axes separate:

- `protocol = Trojan`
- `credential = Trojan password/secret`
- `transport/security = TLS and selected stream settings`
- endpoint/server name/certificate policy
- routing/DNS/TUN ownership

A parser accepting a `trojan://` link does not prove a particular TLS/certificate/transport combination interoperates with a target server.

## Product/reuse decision

PVNetwork classification:

**`SUPPORTED-CANDIDATE / Xray-first modern engine path / historical standalone implementations reference-only`**

Preferred research direction:

1. Xray-core is the primary reusable Trojan engine candidate for the Xray family because the same maintained core can cover other selected protocol entries behind a typed adapter.
2. libXray remains a wrapper candidate where its lifecycle/platform boundary fits, with the underlying Xray/MPL and dependency obligations preserved.
3. `trojan-gfw/trojan` and `trojan-go` remain protocol/interop/test references; their GPLv3 code is not copied into a closed PVNetwork client by default.
4. Major GUI clients (v2rayN, v2rayNG and other Xray-capable references in `CLIENT_ECOSYSTEM.md`) are studied for profile import/UI/platform behavior, not treated as a source-license shortcut.

## 20-gate reconciliation

| # | V1 completion gate | Result | Evidence / Trojan-specific conclusion |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | Shared `CLIENT_ECOSYSTEM.md` maps major Xray-capable clients and roles; historical standalone Trojan/Trojan-Go references are added here for protocol/interop coverage. |
| 2 | Canonical sources pinned | PASS | Exact Xray, libXray, `trojan-gfw/trojan` and `trojan-go` pins are recorded above; historical release dates are explicit. |
| 3 | Licenses reviewed | PASS | Xray MPL-2.0; libXray MIT wrapper; original Trojan GPLv3; Trojan-Go GPLv3; GUI licenses remain separately audited in client-reference dossiers. |
| 4 | Complete source-tree reference/manifest captured | PASS | Xray recursive tree is in shared `SOURCE_ARCHITECTURE.md`; original Trojan recursive tree is pinned above and includes source/docs/examples/build/CI/package assets. |
| 5 | Languages/build systems mapped | PASS | Xray Go/Go modules; libXray wrapper boundary; original Trojan C++/CMake with documented Boost/OpenSSL/MySQL dependencies; Trojan-Go Go/modules/build tags. |
| 6 | Architecture mapped | PASS | Shared Xray source architecture covers app/core/proxy/transport/config/runtime layers; Trojan-specific client/server/protocol/validator/config source is explicitly identified. |
| 7 | Core/engine integration mapped | PASS | Managed Xray process vs libXray wrapper paths, config handoff, lifecycle, logs/stats and platform networking ownership are already mapped; standalone historical engines are reference-only. |
| 8 | UI/menu map completed | PASS for V1 | Shared client dossiers map v2rayNG/v2rayN and other major GUI flows including Trojan profile editing/import, connection, routing/settings/log/backup/update surfaces. Exhaustive screenshot/menu evidence remains V2. |
| 9 | Config/import/export mapped | PASS | Shared Xray/client config model separates typed protocol from transport/security; Trojan password, endpoint/share-link/full-config and lossy conversion boundaries are retained. |
| 10 | Persistence/secrets mapped | PASS | Shared client persistence audits cover profile/subscription stores and sensitive-field risks; Trojan password is explicitly classified as a reusable secret that PVNetwork must place in platform secure storage rather than plain profile storage. |
| 11 | Platform integrations mapped | PASS for research | Xray artifacts/wrappers and detailed Android client service/TUN lifecycle are mapped; desktop client references are mapped separately. Portability is not certification. |
| 12 | Logs/diagnostics mapped | PASS | Xray logging/stats/control evidence plus client logging/redaction risks are documented. Password/share-link/full-config redaction is mandatory. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Shared client asset/resource/localization locations and reference-only copyright policy are recorded; original Trojan repo docs/assets/tree are pinned. Exhaustive captures remain V2. |
| 14 | Meaningful forks reviewed | PASS | `trojan-go` is explicitly pinned and compared; Xray is a maintained multi-protocol implementation candidate rather than being conflated with the old standalone project. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | Historical original/Fork release inactivity is explicit; shared Xray issue/release/security-advisory dossiers cover current core regression/dependency risk, including the existing Xray advisory review. Runtime-specific interoperability remains a later certification gate. |
| 16 | Relevant forums/docs reviewed | PASS | Original Trojan protocol/config/build documentation and Xray upstream docs/source are authoritative references; shared issue/docs research includes client/core lessons. |
| 17 | Tests/CI reviewed | PASS | Xray has Trojan protocol test source at the reviewed pin and shared cross-platform test/CI evidence; original Trojan tree includes Azure pipeline/build/test evidence; Trojan-Go reviewed head includes GitHub Actions `make test`/module verification. |
| 18 | Store/privacy/security implications reviewed | PASS | TLS certificate trust, reusable password protection, log/share-link redaction, GPL-reference boundaries, Xray dependency/SBOM/advisory review and platform Store lifecycle are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Xray-first typed adapter; libXray wrapper candidate where appropriate; historical GPLv3 standalone implementations/reference GUIs are reference-only unless product licensing strategy explicitly changes. |
| 20 | Uncertainties explicitly listed | PASS | Exact production Xray pin/SBOM, current server/version matrix, certificate/TLS behavior, imported-link edge cases, real-device lifecycle, performance, interoperability and full V2 server/install/crypto/wire/topology work remain later evidence. |

## Trojan-specific canonical profile requirements

PVNetwork should preserve at least these semantic categories without flattening them into an opaque URI:

- protocol identity: Trojan;
- server endpoint/port;
- Trojan password/secret reference;
- TLS/security settings and server-name/certificate policy as separate fields;
- optional transport/stream settings supported by the selected engine;
- source format/raw import metadata for lossless diagnosis/export where safe;
- routing/DNS/TUN behavior outside the Trojan protocol object;
- selected engine/version capability metadata.

Unknown or unsupported fields must be preserved where safe and surfaced as lossy/unsupported rather than silently discarded.

## Security boundary

The historical protocol uses TLS for channel confidentiality/authentication and a password-derived token for Trojan request authentication. The reviewed Xray implementation computes the token using SHA-224 and transmits the derived hex value in the Trojan request header; this does **not** make the password safe for ordinary plaintext local persistence.

PVNetwork must therefore:

- rely on maintained TLS/crypto implementations rather than reimplementing cryptography;
- validate server identity according to the selected TLS policy;
- store reusable Trojan passwords through protected OS credential storage;
- redact passwords, derived tokens, share links and generated configs from routine logs/support exports;
- treat certificate bypass/insecure verification as an explicit dangerous option, not a hidden compatibility default;
- pin the production Xray build and audit its full dependency/SBOM/security state before shipment.

## Remaining later work — not V1 blockers

Before a PVNetwork Trojan support claim:

- choose an exact advisory-aware production Xray release/commit;
- generate per-platform SBOM/license/vulnerability evidence;
- certify `trojan://` and full-config import/export edge cases;
- test exact advertised TLS/transport combinations against known servers;
- test TCP/UDP behavior, routing/DNS/TUN, IPv4/IPv6 and network changes;
- test reconnect/crash/cleanup and Store lifecycle on target platforms;
- measure actual supported-combination performance;
- complete V2 server implementations/installers/UI, client menu/install matrix, cryptography, data path, ports/handshake and deployment topologies.

## Final V1 decision

All 20 original V1 research gates are evidence-backed or correctly bounded to later implementation/reference/certification work.

Entry 039 may be promoted to:

**`COMPLETE-RESEARCH-v1`**

while remaining:

**`NOT IMPLEMENTED / NOT CERTIFIED`**.
