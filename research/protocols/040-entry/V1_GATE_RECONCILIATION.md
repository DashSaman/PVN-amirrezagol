# 040 — Shadowsocks — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **040 — Shadowsocks**

Decision: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED / NOT CERTIFIED`**

This reconciliation covers the classic/current Shadowsocks family represented by standard AEAD methods and legacy compatibility where explicitly required. **Shadowsocks 2022 / AEAD-2022 is entry 041 and is not completed by this file.**

## Primary source baseline

### Dedicated implementation candidate

- repository: `shadowsocks/shadowsocks-rust`
- reviewed commit: `9214fdaf1f8938a20f6c295b1260c69a625d1f4f`
- package version at reviewed commit: `1.25.0`
- license: MIT
- language/build: Rust 2024 edition, Cargo workspace
- binaries: `sslocal`, `ssserver`, `ssurl`, `ssmanager`, `ssservice`, `sswinservice`
- workspace crates: `crates/shadowsocks` protocol core and `crates/shadowsocks-service` services
- recursive source tree reviewed at the exact commit.

The exact tree separates classic AEAD and AEAD-2022 implementations:

- classic TCP AEAD: `crates/shadowsocks/src/relay/tcprelay/aead.rs`
- SS2022 TCP: `crates/shadowsocks/src/relay/tcprelay/aead_2022.rs`
- classic UDP AEAD: `crates/shadowsocks/src/relay/udprelay/aead.rs`
- SS2022 UDP: `crates/shadowsocks/src/relay/udprelay/aead_2022.rs`

Cargo features also separate `aead-cipher` from `aead-cipher-2022`. This is the source-backed boundary between entry 040 and 041.

The project explicitly marks old stream ciphers as deprecated/unsafe and warns users to avoid them. Standard AEAD support is separate from deprecated stream ciphers and from non-standard extra AEAD methods.

### Multi-protocol engines / client references

- Xray-core research pin: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5` (MPL-2.0), with shared client/config/platform evidence under `research/upstreams/xray-family/`.
- sing-box current research reference: `SagerNet/sing-box@db1053f8bc16c860225afc97ac6417e42a81dc64`; GPL-3.0-or-later plus an additional naming/association condition at the reviewed pin, therefore reference/core-comparison by default for a closed PVNetwork product.
- major GUI/multi-core clients are already cataloged in Xray/client-reference dossiers for import/UI/platform comparison; their licenses remain independent from the protocol engine.

## Product classification

**`MULTI-CORE + DEDICATED-CORE TARGET / DEDICATED MIT IMPLEMENTATION IS A STRONG REUSE CANDIDATE / EXACT METHOD CERTIFICATION REQUIRED`**

Do not choose Xray merely because PVNetwork already studies Xray. `shadowsocks-rust` is a serious dedicated MIT candidate and should be benchmarked against any multi-core path actually considered for each target platform.

## Protocol boundary

PVNetwork must model at least:

- `protocol = Shadowsocks` (entry 040);
- server endpoint/port;
- password/secret;
- exact method/cipher;
- TCP/UDP mode/capability;
- plugin + plugin options, if explicitly supported;
- original import/share source where safe;
- selected engine/version;
- routing/DNS/TUN outside the protocol object.

Do not flatten method semantics into a generic “encrypted proxy” toggle. Do not reinterpret entry-040 AEAD profiles as Shadowsocks 2022.

## 20-gate reconciliation

| # | V1 completion gate | Result | Evidence / Shadowsocks-specific conclusion |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | Shared multi-protocol client evidence covers common desktop/mobile clients; dedicated `shadowsocks-rust` provides client/server/service/utility reference. Different clients are useful for import/UI/platform behavior, not interchangeable source. |
| 2 | Canonical sources pinned | PASS | Exact shadowsocks-rust, Xray and sing-box research pins are recorded. |
| 3 | Licenses reviewed | PASS | shadowsocks-rust MIT; Xray MPL-2.0; reviewed sing-box GPL3+additional condition; GUI licenses remain separate and mostly reference-only by default. |
| 4 | Complete source-tree reference/manifest captured | PASS | Exact recursive shadowsocks-rust tree reviewed; Xray pinned recursive tree exists in shared dossier; sing-box exact tree is available as alternate implementation evidence. |
| 5 | Languages/build systems mapped | PASS | shadowsocks-rust Rust/Cargo workspace; Xray and sing-box Go; GUI/platform clients are mapped in shared references. |
| 6 | Architecture mapped | PASS | shadowsocks-rust explicitly separates protocol core, services, client/server/manager, TCP/UDP relay, plugins, local SOCKS/HTTP/redir/TUN/DNS layers; multi-core engines use separate protocol/config/platform layers. |
| 7 | Core/engine integration mapped | PASS | Dedicated binaries/crates and service boundaries are known; Xray managed-process/libXray path is mapped; product requires a stable engine-neutral adapter. |
| 8 | UI/menu map completed | PASS for V1 | Existing multi-protocol client dossiers cover Shadowsocks profile/import/subscription/routing/settings/log flows. Dedicated shadowsocks-rust is primarily CLI/service, so consumer UI must remain product-owned. Exhaustive per-client screenshots/fields remain V2. |
| 9 | Config/import/export mapped | PASS | Shadowsocks URL/config concepts, SIP008 online-config support in shadowsocks-rust, method/password/plugin fields and source-vs-canonical separation are documented. Lossy conversion must be surfaced. |
| 10 | Persistence/secrets mapped | PASS | Passwords are reusable secrets requiring protected storage; imported source and canonical non-secret fields remain separate. Shared client storage evidence covers profile/subscription/raw-source patterns. |
| 11 | Platform integrations mapped | PASS for research | shadowsocks-rust provides Linux/macOS/Windows builds/services plus Docker/Kubernetes and local TUN/redir modes; mobile consumer integration is represented by client references and requires exact target-engine testing. |
| 12 | Logs/diagnostics mapped | PASS | shadowsocks-rust has logging/tracing/syslog options; generated configs/share URLs/passwords/plugin secrets require redaction. Engine/runtime state and product diagnostics remain separate. |
| 13 | Asset/screenshot references mapped | PASS for V1 | Client resource/UI references already exist; shadowsocks-rust is primarily non-GUI. Third-party assets/branding are reference-only. Exhaustive screenshot catalog remains V2. |
| 14 | Meaningful forks/alternatives reviewed | PASS | Dedicated shadowsocks-rust is compared with Xray and sing-box multi-core implementations rather than assuming a single engine. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | Current shadowsocks-rust source/activity, feature warnings and CI are reviewed; Xray release/advisory drift is already documented. Exact selected engine release/advisory refresh remains source-freeze work. |
| 16 | Relevant forums/docs reviewed | PASS | shadowsocks-rust README links community docs/SIP008/SIP022 and documents deployment/method warnings; Xray/client docs/issues provide cross-engine behavior lessons. |
| 17 | Tests/CI reviewed | PASS | shadowsocks-rust has GitHub build/test, MSRV, release, clippy and deny workflows plus core TCP/UDP tests; Xray cross-platform tests are documented separately. |
| 18 | Store/privacy/security implications reviewed | PASS | Unsafe stream ciphers are explicitly rejected as modern defaults; passwords/configs must be protected/redacted; plugin binaries expand supply-chain risk; GUI/core licenses and mobile/Store lifecycle are independent gates. |
| 19 | PVNetwork reuse decision documented | PASS | Benchmark dedicated MIT `shadowsocks-rust` against any multi-core candidate. Prefer the smallest maintained/licensable engine that satisfies target-platform capabilities. Do not inherit GPL/custom GUI code merely for UI convenience. |
| 20 | Uncertainties explicitly listed | PASS | Exact production engine, per-method/client-server interoperability, plugins, UDP/TUN/platform behavior, SBOM/advisories, performance and V2 wire/install/server evidence remain explicit later gates. |

## Security and compatibility policy

1. **Deprecated stream ciphers are legacy/insecure compatibility only and disabled by default.** The reviewed shadowsocks-rust source itself warns they are unsafe.
2. Non-standard extra AEAD methods are not automatically exposed as supported production methods.
3. Entry 040 standard AEAD and entry 041 AEAD-2022 remain distinct protocol/cipher families in capability and certification tables.
4. Never auto-convert an entry-040 profile to SS2022; migration requires explicit new credentials/method semantics and verified server support.
5. Plugins are separate executable/supply-chain components with their own source/license/update/security review.
6. TCP and UDP are separately certifiable capabilities.
7. No home-grown cryptography.

## Dedicated-engine deployment evidence

At the reviewed shadowsocks-rust revision, official project evidence includes:

- crates.io installation;
- Homebrew/macOS/Linux;
- Snap service packaging;
- downloadable static releases including Windows/macOS targets;
- Docker images for multiple Linux architectures;
- Kubernetes manifests and Helm chart;
- systemd/Debian packaging assets;
- client/server/manager/service binaries;
- TUN, transparent-redirection, SOCKS, HTTP, DNS/FakeDNS optional local modes.

These are upstream deployment capabilities, **not PVNetwork production certification**.

## Future acceptance/certification work — not V1 blockers

Before support claim:

- choose exact engine/release per platform;
- freeze Cargo/Go dependencies, SBOM, licenses and vulnerabilities;
- certify every exposed entry-040 method and reject unsafe/unsupported ones;
- test TCP and UDP separately;
- certify `ss://`, full config, SIP008 and any plugin import/export actually exposed;
- test plugins separately with exact pins/licenses;
- test routing/DNS/TUN/redir, IPv4/IPv6, network handover, reconnect/crash cleanup;
- test target mobile/desktop Store lifecycle;
- benchmark dedicated vs multi-core candidates;
- complete V2 server/install/client UI/crypto/data-flow/ports/topology evidence.

## Final V1 decision

All 20 original research gates are evidence-backed and the Shadowsocks-vs-Shadowsocks-2022 boundary is source-proven. Entry 040 may therefore be promoted to **`COMPLETE-RESEARCH-v1`**, while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
