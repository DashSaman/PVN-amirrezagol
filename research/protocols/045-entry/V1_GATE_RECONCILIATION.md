# 045 — AnyTLS — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **045 — AnyTLS**

Decision: **`COMPLETE-RESEARCH-v1 / PROTOCOL-CANDIDATE / REFERENCE-CODE-LICENSE-UNCLEAR / NOT IMPLEMENTED / NOT CERTIFIED`**

AnyTLS is its own multiplexed proxy protocol over TLS. It is not generic TLS (entry 077) and is not ShadowTLS (entry 046). Its protocol parameters explicitly exclude TLS parameters, which belong in a separate TLS configuration section.

## 1. Canonical/reference source

Reference repository:

- `anytls/anytls-go`
- reviewed commit: `fd6167acd6d73b9fa3e607659951847fbc9e6c50`
- reviewed tree: `59c373e406e4781ec4ae06d893c873dc29325ef8`
- reviewed latest release: `v0.0.13`, published 2026-06-27
- current reviewed head date: 2026-08-03
- language/build: Go / Go modules / GoReleaser
- release assets observed for Windows x64/ARM64, Linux x64/ARM64 and macOS x64/ARM64.

The reviewed tree contains:

- `cmd/client/`, `cmd/server/` example programs;
- protocol/session/padding/pipe implementation under `proxy/`;
- protocol, URI, FAQ and client-metadata docs;
- Go module/lock material and GoReleaser configuration.

### Critical license finding

At the reviewed commit:

- there is **no repository LICENSE file in the tree**;
- GitHub's repository-license endpoint returns 404;
- the README describes the project as a reference implementation but does not grant a source-code license in the reviewed material.

Therefore public source availability is **not a commercial reuse grant**.

PVNetwork classification for `anytls-go` code:

**`REFERENCE-ONLY / DO-NOT-COPY UNTIL AN EXPLICIT COMPATIBLE LICENSE IS VERIFIED`**.

Protocol interoperability can still be researched from the published specification/documentation; implementation/reuse licensing must remain separate.

## 2. Protocol semantics from current canonical docs

The current protocol document describes:

1. TLS handshake first.
2. Client authentication immediately after TLS: `sha256(password)` plus a variable padding block.
3. A multiplexed session layer above TLS with framed commands, stream IDs and payload lengths.
4. Stream lifecycle commands including SYN/PSH/FIN, settings, alerts, padding updates and protocol-v2 SYNACK/heartbeat/server-settings commands.
5. Session reuse/pooling so multiple proxied streams can share TLS-backed sessions.
6. TCP target addressing using SOCKS-style addresses.
7. UDP carried using sing-box udp-over-tcp v2 semantics through a special target.
8. Dynamically updateable padding schemes.

Current protocol version metadata in `cmdSettings` is `v=2`. Version-2 behavior adds stream-open acknowledgement, heartbeat and server settings to mitigate stuck tunnels/timeouts while retaining compatibility fallback with v1 peers.

### Metadata/privacy issue

The current protocol includes a `client` metadata field identifying client software/version. A 2026-08-03 canonical document says it is sent **inside the encrypted connection**, is self-declared/spoofable, is intended for diagnostics/statistics and should be user-configurable. It must not be treated as a trustworthy identity/authentication signal.

PVNetwork should expose a privacy-aware policy for this metadata rather than silently using it as an immutable device identifier.

## 3. URI/config format

Canonical URI document:

`anytls://[auth@]hostname[:port]/?[key=value]...#display-name`

Key reviewed semantics:

- `auth` contains the password and requires percent encoding when necessary;
- default port is 443 when omitted;
- `sni` is a TLS option;
- `insecure=1/0` explicitly controls insecure TLS verification behavior;
- fragment is display name;
- third-party extension parameters are not guaranteed interoperable.

The URI therefore contains a reusable password and must be redacted in logs/clipboard history/support bundles and converted into a secure-store reference on import.

## 4. Serious current third-party implementations/clients

### sing-box

- repository: `SagerNet/sing-box`
- reviewed current testing commit: `db1053f8bc16c860225afc97ac6417e42a81dc64`
- reviewed head date: 2026-08-13
- license: GPLv3-or-later plus an additional name/association condition in the reviewed LICENSE
- current tree contains AnyTLS inbound/outbound protocol code, typed option schema, client-metadata handling and English/Chinese configuration docs.

Classification: **excellent current interoperability/source reference; direct closed-product embedding requires a GPL-compatible architecture/legal decision**.

### mihomo

The AnyTLS reference README lists mihomo as a server+client implementation. Existing PVNetwork research has already identified its current copyleft licensing boundary; treat it as implementation/interop/reference unless the product licensing architecture explicitly permits reuse.

### Apple/closed clients

The canonical reference README lists Shadowrocket 2.2.65+ plus Stash/Loon as compatible client examples. These are behavioral/UI/interoperability references only; no source reuse is inferred.

## 5. Canonical PVNetwork model

Keep separately typed:

- `protocol = AnyTLS`;
- protocol version/capability negotiation;
- endpoint/port;
- reusable password as secure-store reference;
- session reuse/pool parameters;
- padding scheme/capability metadata;
- client-name metadata policy;
- TLS/SNI/certificate/insecure options as a **separate TLS object**;
- routing/DNS/TUN/per-app outside AnyTLS;
- original URI/raw import metadata where safe.

Do not merge AnyTLS with generic TLS or ShadowTLS because all use TLS-related mechanisms.

## 6. 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / AnyTLS-specific conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations identified | PASS | Canonical README identifies anytls-go reference, sing-box, mihomo and Apple clients; roles/source visibility are separated. |
| 2 | Canonical sources pinned | PASS | anytls-go exact commit/tree/release plus current sing-box commit are pinned. |
| 3 | Licenses reviewed | PASS | Critical result: anytls-go has no explicit license in reviewed tree/API and is DO-NOT-COPY pending license clarification; sing-box GPLv3+ with additional naming condition; closed clients reference-only. |
| 4 | Complete source-tree reference captured | PASS | anytls-go recursive tree is pinned and fully enumerates cmd/docs/proxy/util/module/release files; sing-box AnyTLS paths are source-indexed. |
| 5 | Languages/build systems mapped | PASS | anytls-go Go/modules/GoReleaser; sing-box Go/modules; platform release assets and example binaries mapped. |
| 6 | Architecture mapped | PASS | TLS -> auth -> multiplexed session -> streams -> target proxy; settings/padding/pool/error layers are separated from product routing/TUN. |
| 7 | Core/engine integration mapped | PASS | anytls-go is reference example code not legally reusable yet; sing-box is a current full implementation under GPL; adapter/subprocess/approved-engine decision remains product-level. |
| 8 | UI/menu map completed | PASS for V1 | Reference implementation is CLI/config and correctly treated as N/A consumer GUI; third-party current clients provide profile/connect/settings references; PVNetwork owns final UI. Exhaustive menus remain V2. |
| 9 | Config/import/export mapped | PASS | Canonical URI, protocol settings, TLS separation, extension/lossy-conversion rules and reference CLI/config behavior are documented. |
| 10 | Persistence/secrets mapped | PASS | Password is explicit reusable secret and can appear in URI; secure-store reference/redaction requirements are mandatory. Client metadata is non-secret but privacy-relevant. |
| 11 | Platform integrations mapped | PASS for research | Reference release assets cover major desktops; sing-box/third-party ecosystem covers broader platforms. Exact TUN/mobile lifecycle remains certification. |
| 12 | Logs/diagnostics mapped | PASS | Protocol alert/server-settings/heartbeat behaviors plus implementation logs are mapped; password/URI/cert/API secret redaction and client-metadata privacy are explicit. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Reference repo is code/docs-oriented with no reusable GUI identity; third-party app assets/screenshots remain reference-only under their own rights. |
| 14 | Meaningful forks/alternatives reviewed | PASS | sing-box/mihomo are explicit current third-party implementations; reference project itself states it is not a full production client. |
| 15 | Issues/PRs/releases/advisories reviewed | PASS | v0.0.13 release and 2026 head reviewed; protocol-v2 timeout/recovery changes and 2026 client-metadata compatibility/privacy dispute are captured as regression/product requirements. |
| 16 | Relevant forums/docs reviewed | PASS | Canonical protocol, FAQ, URI and client-name docs are pinned; upstream implementation docs are primary evidence. |
| 17 | Tests/CI reviewed | PASS | Reference tree contains no substantial dedicated test/CI suite at the reviewed pin beyond build/release config; this missing coverage is explicitly documented. Current sing-box implementation/test ecosystem is supplemental; PVNetwork must create its own protocol-version/interop regression tests. |
| 18 | Store/privacy/security implications reviewed | PASS | insecure TLS default/reference warning, password URI exposure, certificate verification, client metadata, dynamic padding, copyleft/unknown licenses and platform lifecycle are explicit. |
| 19 | PVNetwork reuse decision documented | PASS | Do not copy unlicensed anytls-go; evaluate an already-approved licensed engine or an independently implemented non-crypto adapter against published semantics; no custom TLS cryptography. |
| 20 | Uncertainties explicitly listed | PASS | Source-code licensing clarification, exact production engine, current dependency/SBOM/advisory state, protocol-v1/v2 interop, third-party metadata defaults, real-device lifecycle/performance and V2 deployment/UI/wire evidence remain later work. |

## 7. Security/product requirements

1. TLS verification ON by default; `insecure=1` must be explicit/dangerous and never silently imported without warning.
2. Store reusable password in OS secure storage; redact full `anytls://` URIs.
3. Keep TLS config separate from AnyTLS protocol config as upstream specifies.
4. Treat `client` field as user-configurable metadata, never strong identity/authentication.
5. Validate protocol-version negotiation and stuck-session recovery behavior.
6. Server-delivered padding scheme is connection/profile scoped data; validate size/syntax and never treat it as trusted executable configuration.
7. Do not copy unlicensed reference code or reimplement TLS crypto.

## 8. Later acceptance work — not V1 blockers

- obtain/verify explicit licensing for anytls-go before any code reuse, or select a separately licensed engine;
- freeze exact engine/release/SBOM/advisory state;
- certify protocol v1/v2 negotiation, SYNACK/heartbeat recovery and session pooling;
- test URI parsing/encoding/redaction/insecure behavior;
- test TLS certificates/SNI/rotation/fallback;
- test TCP/UDP-over-TCP, DNS/routing/TUN, IPv4/IPv6 and network changes;
- test metadata privacy controls and compatibility;
- test mobile/desktop lifecycle, Store packaging and performance;
- complete V2 server/installers/panels, exhaustive UI, cryptography/wire, ports/handshake and deployment topologies.

## Final V1 decision

All 20 original V1 research gates are evidence-backed, including an explicit **license gap rather than a fabricated permissive license**.

Entry 045 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
