# 048 — Snell — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **048 — Snell**

Decision: **`COMPLETE-RESEARCH-v1 / PROPRIETARY SURGE COMPATIBILITY TARGET / PERMISSION-BOUND REUSE / NOT IMPLEMENTED / NOT CERTIFIED`**

Snell is a custom encrypted proxy protocol developed by the Surge team. This dossier deliberately does **not** treat third-party reverse-engineered implementations as proof that the official implementation is open source or freely reusable.

## Authoritative vendor evidence

Primary authorities:

- Surge Knowledge Base — Snell release/download page;
- Surge Manual — Proxy Policy / Snell parameters and current client-generation support;
- Surge Mac/iOS release notes for version-specific features.

Current authoritative conclusions:

- Surge describes Snell as a lean encrypted proxy protocol developed by its team;
- the public Snell server is distributed as prebuilt Linux binaries rather than official source;
- the official Snell page explicitly says the protocol is intended for Surge users and asks others not to reverse-analyze it or make compatible clients;
- the publicly documented stable server line is **Snell Server v5.0.1**;
- v5 server is documented backward-compatible with v4 clients;
- current Surge client documentation also exposes **Snell v6** as a beta-generation option and warns that beta protocol changes may be incompatible, so client/server must stay mutually compatible;
- Surge supports UDP relay for v4/v5/v6, with generation-specific behavior.

The current public sources do **not** provide an official source-code repository or an open-source license grant for the Snell implementation/protocol. Publicly downloadable binaries are not treated as source-reuse permission.

## Generation boundary

PVNetwork must explicitly model Snell generations rather than a single timeless "Snell" capability.

### v4

Current Surge manual documents:

- required `psk` and `version=4`;
- optional connection `reuse`;
- optional HTTP obfuscation plus host/URI controls;
- UDP relay support;
- compatibility with current v5 server.

### v5

Official Snell v5 server release notes add:

- Dynamic Record Sizing for latency behavior under loss;
- QUIC Proxy Mode for detected QUIC traffic, using UDP-over-UDP behavior rather than ordinary UDP-over-TCP for that case;
- server egress-interface/systemd socket-activation controls;
- backward compatibility with v4 clients.

The official notes state that in QUIC Proxy Mode the QUIC handshake portion is protected/authenticated while later QUIC packets—already encrypted by QUIC—are forwarded raw. PVNetwork must not simplify this into a claim that all v5 UDP packets are always encrypted identically.

### v6

Current Surge proxy documentation says Snell v6 is available in recent Surge iOS/Mac builds and is **currently beta**. It derives a deployment-specific protocol profile from the PSK, removes v5 QUIC Proxy Mode, and adds server-side address-family/listen controls.

Because the vendor warns that v6 beta may change incompatibly, PVNetwork must not freeze a long-lived v6 support claim without an exact mutually compatible client/server release pair. V6 beta status is an explicit uncertainty, not a hidden V1 blocker.

## Official source / license boundary

Official source tree: **N/A — not publicly released**.

Official open-source license: **N/A / no public open-source grant located in reviewed authoritative material**.

This gate is not "missing research": proprietary/non-published source is itself the evidence-backed result. PVNetwork must not invent a source pin or license.

The official binary's redistribution/commercial-use rights require authoritative vendor terms/permission before bundling or redistribution. Merely having a download URL does not establish those rights.

## Third-party implementation evidence — not an authority substitute

### `missuo/opensnell`

Current independent implementation/reference:

- repository: `missuo/opensnell`
- current reviewed HEAD: `3100984fd7c3a2bd7b41e292ad41f10d928bfb2d`
- tree: `6f50b79961f4e8eb6c630940e230af755a37b78e`
- current project release: `v1.0.4`, published 2026-05-29
- language: Go
- repository license: GPL-3.0
- project describes itself as a Snell v4/v5 implementation with server/client, TCP, UDP-over-TCP and obfuscation support.

Its current installer/history also tracks official v6 server binaries, but that is third-party observation and not substituted for vendor release authority.

### `icpz/open-snell`

Historical independent port:

- Go;
- GPL-3.0;
- repository describes itself as an unofficial open-source port;
- last pushed in 2022, so it is historical architecture evidence, not a current v5/v6 production recommendation.

### Legal/reuse rule for reverse-engineered implementations

Even when a third-party repository has its own GPL license, that license only speaks to the contributor's copyright grant for that repository. It does **not** establish that protocol reverse engineering, interoperability, trademarks, patents, vendor terms or redistribution are cleared for PVNetwork's intended jurisdictions/product model.

Given Surge's explicit public request against reverse engineering/compatible third-party clients, PVNetwork defaults to:

**`REFERENCE/INTEROP EVIDENCE ONLY — NO DIRECT COMPATIBLE IMPLEMENTATION OR BUNDLING WITHOUT LEGAL/RIGHTS REVIEW OR VENDOR AUTHORIZATION`**.

## Client / UI / config evidence

The authoritative Surge manual provides the typed user-facing configuration model:

`Proxy-Snell = snell, host, port, psk=password, version=...`

Relevant controls include:

- endpoint/port;
- PSK;
- explicit version;
- `reuse` where applicable;
- v4 HTTP obfuscation and host/URI controls;
- underlying proxy/chaining at the Surge product layer;
- UDP relay capability by generation;
- optional ShadowTLS composition in current Surge clients;
- current v6 generation selection.

Surge Mac runtime details/menu documentation provides first-party operational UI behavior. Closed Surge UI/assets are behavioral references only; no source or asset reuse is inferred.

Third-party clients such as Exclave and other compatible cores are ecosystem evidence, not vendor-authorized source authority.

## Persistence / secrets / diagnostics

The PSK is a reusable credential and must be stored in platform secure credential storage and redacted from profile export/logs/telemetry/support bundles unless the user explicitly exports a secret-bearing configuration.

Version, reuse, obfuscation, UDP mode and server/network controls are capability/config state, not secrets.

Vendor server release notes document startup/error reporting and remote errors to clients. PVNetwork diagnostics must separate:

- version mismatch;
- PSK/auth/decryption failures;
- DNS/address-family failures;
- UDP-over-TCP vs v5 QUIC Proxy path;
- connection reuse failures;
- server egress/listen issues;
- optional outer ShadowTLS failures;
- product routing/DNS/TUN failures.

## 20-gate reconciliation

| # | V1 gate | Result | Evidence / Snell conclusion |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | Surge iOS/Mac is the first-party client authority; official Linux `snell-server` is server authority. Independent opensnell/other clients are clearly labeled third-party compatibility evidence. |
| 2 | Canonical sources pinned | PASS | Current Surge Manual/Knowledge Base are authoritative; stable official server generation is v5.0.1, while current client docs separately expose v6 beta. Third-party current source is pinned only as independent evidence. |
| 3 | Licenses reviewed | PASS | Official implementation has no public open-source grant located; binary redistribution rights remain vendor-term/permission dependent. `missuo/opensnell` and historical `open-snell` are GPL-3.0 and remain legal-review/reference-only by default. |
| 4 | Complete source-tree reference/manifest captured | N/A — evidence-backed proprietary boundary | Official source tree is not published. No fabricated official source manifest is created. Independent GPL implementations have separately pin-able source trees but do not replace this N/A result. |
| 5 | Languages/build systems mapped | PASS where public/applicable | Official implementation language/build is not publicly evidenced and remains N/A. Independent current implementation is Go; Surge clients are proprietary applications. Unknown official internals are not guessed. |
| 6 | Architecture mapped | PASS | Encrypted PSK proxy, TCP connection/reuse, UDP relay, v5 QUIC Proxy mode, v6 beta generation, optional obfuscation/ShadowTLS composition and server egress controls are mapped from official docs. |
| 7 | Core/engine integration mapped | PASS | Preferred safe path is vendor-authorized/official component use where terms permit. Reverse-engineered GPL engines are not silently embedded as substitutes. Adapter boundary is explicit. |
| 8 | UI/menu map completed | PASS for V1 | First-party Surge proxy syntax, generation controls, runtime details and current configuration parameters provide authoritative UI/behavior model; closed-source screens/assets remain reference-only. |
| 9 | Config/import/export mapped | PASS | Endpoint, port, PSK, version, reuse, v4 obfs fields, UDP-generation behavior, optional ShadowTLS and v6 controls are typed; secret-bearing exports require explicit handling. |
| 10 | Persistence/secrets mapped | PASS | PSK is secure-store material; non-secret generation/capability fields are separate. Runtime reuse/UDP sessions/errors are transient. |
| 11 | Platform integrations mapped | PASS for research | Official server downloads target Linux architectures; first-party clients are Surge iOS/Mac. Third-party platform support is not treated as official certification. |
| 12 | Logs/diagnostics mapped | PASS | Vendor release/server behavior plus current configuration defines remote error/DNS/version/reuse/UDP diagnostics; PSK and secret-bearing profile output must be redacted. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Surge manuals/runtime UI are first-party reference locations. Proprietary assets/branding are not copied; exhaustive screenshot capture remains V2. |
| 14 | Meaningful forks/alternatives reviewed | PASS | Current GPL `missuo/opensnell`, historical `icpz/open-snell`, compatible clients and generation v4/v5/v6 differences are reviewed with explicit non-authority/legal boundaries. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | Official v5.0.1 crash fix, v5.0.0 feature changes, v4.1.x UDP/DNS/libuv fixes, current v6-beta compatibility warning and vendor community release problems are captured without fabricating a CVE/advisory feed. |
| 16 | Relevant forums/docs reviewed | PASS | Surge Manual, Snell Knowledge Base, Surge release notes and vendor community reports are primary; third-party reverse-engineering material is secondary only. |
| 17 | Tests/CI reviewed | PASS with proprietary N/A boundary | Official internal tests/CI are not public and are recorded N/A. Independent open implementations publish build/test/interoperability evidence, useful as secondary research but not official certification. |
| 18 | Store/privacy/security implications reviewed | PASS | PSK secrecy, proprietary terms, explicit anti-reverse-engineering request, version compatibility, v5 partial QUIC protection semantics, v6 beta instability, GPL third-party code and proprietary client/server packaging are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Do not implement/bundle a reverse-engineered Snell-compatible core by default. Prefer vendor authorization/officially licensed components; otherwise keep Snell as compatibility research with implementation blocked pending rights review. |
| 20 | Uncertainties explicitly listed | PASS | Exact official source/license/redistribution rights, v6 stable server release/pin, v6 wire stability, legal interoperability rights by jurisdiction, exact client/server matrix, performance/device lifecycle and V2 cryptographic/wire/server evidence remain explicit. |

## Product/legal rules that survive handoff

1. Never label Snell official source as open source; it is not publicly provided in reviewed authoritative sources.
2. Never infer redistribution/commercial-use rights from a binary download URL.
3. Treat Surge's stated restriction/request against reverse engineering/compatible third-party clients as a mandatory legal/product review trigger.
4. Store PSK securely and redact it everywhere by default.
5. Keep `version` explicit; v4/v5/v6 are not interchangeable feature labels.
6. Treat v6 as beta while the vendor says so; no durable production claim without an exact compatible client/server pair.
7. Preserve v5 QUIC Proxy's generation-specific security semantics; do not claim every packet is re-encrypted identically.
8. Third-party GPL source is not a shortcut around vendor/IP analysis.

## Later acceptance work — not V1 blockers

Before a PVNetwork support claim: obtain authoritative rights/legal determination for chosen client/server implementation; pin exact official server/client generation and downloadable artifact hashes; verify v4/v5/v6 interoperability only where supported; test PSK/version failures, connection reuse, TCP/UDP-over-TCP, v5 QUIC Proxy, DNS/IPv4/IPv6, obfuscation/ShadowTLS combinations, reconnect/network changes and platform lifecycle; freeze selected implementation SBOM/license/advisory evidence; then complete V2 server installer/UI, exact cryptography/wire flow, handshake/ports and deployment-topology evidence.

## Final V1 decision

All applicable 20 V1 research gates are evidence-backed, including explicit N/A treatment for unavailable proprietary source/build/test internals and explicit legal uncertainty instead of invented open-source rights. Entry 048 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`IMPLEMENTATION BLOCKED PENDING RIGHTS/ENGINE DECISION / NOT CERTIFIED`**.
