# 079 — Cloak — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **079 — Cloak**

Decision: **`COMPLETE-RESEARCH-v1 / GPL-3.0 PLUGGABLE TRANSPORT / WRAPS AN UNDERLYING TCP/UDP PROXY / NOT A STANDALONE PROXY OR VPN / NOT IMPLEMENTED / NOT CERTIFIED`**

## Canonical source and release pin

- Canonical repository: `cbeuw/Cloak`
- Default branch: `master`
- Repository status at review: public, non-archived, issues enabled
- Primary language: Go
- Latest reviewed stable tag: **`v2.12.0`**
- Tag commit: `c3d5470ef76bba68d7812f5d06e4181dc1b1a5d6`
- Commit date: 2025-07-22
- Source tree: `bb1eda880af94d7a3f09a78f724cd13aaec29a55`
- Recursive manifest:
  `https://api.github.com/repos/cbeuw/Cloak/git/trees/bb1eda880af94d7a3f09a78f724cd13aaec29a55?recursive=1`
- Root license: **GPL-3.0-only as represented by the repository root GPLv3 license text/metadata**
- Build module at tag: Go `1.24.0`, toolchain `go1.24.2`
- Direct dependencies include `refraction-networking/utls v1.8.0`, Gorilla mux/WebSocket, bbolt, x/crypto and rate/log/test libraries.

Repository metadata was updated in 2026, but the reviewed `master` head/tag commit is still the 2025-07-22 v2.12.0 commit. This distinction is preserved rather than calling repository activity a newer release.

## Source-tree / architecture inventory

The complete pinned tree includes:

- `cmd/ck-client/` — client executable/lifecycle, Android-specific protection/log hooks;
- `cmd/ck-server/` — server executable, key generation and tests;
- `internal/client/` — TLS/ClientHello disguise, auth, connector, multiplexed session, direct/CDN/WebSocket handling;
- `internal/server/` — server auth/session/reverse-proxy/user handling;
- `internal/common/` — crypto, TLS and shared copy/network helpers;
- `example_config/` — `ckclient.json` and `ckserver.json` examples;
- `.github/workflows/build.yml` / `release.yml` — build/test/release automation;
- `Dockerfile`, `Makefile`, `go.mod`, `go.sum` — build/package/dependency surface;
- test files colocated through client/server/common packages.

The upstream README explicitly states that **Cloak is a pluggable transport and not a standalone proxy program**. It wraps an underlying TCP/UDP proxy such as OpenVPN, Shadowsocks or Tor, disguises traffic as ordinary web browsing, and can reverse-proxy multiple underlying services.

## Security / product boundary

Cloak configuration includes static Curve25519 server key material, user UIDs, proxy method selection, camouflage server name/browser signature, direct or CDN transport, optional traffic encryption method, connection/multiplexing settings and server-side user/account database state.

Upstream explicitly says Cloak's encryption is intended to hide underlying-proxy fingerprints and **is not intended to provide transport security**. `plain` is only appropriate when the underlying proxy already provides both encryption and authentication. Therefore PVNetwork must not market Cloak's camouflage encryption as a substitute for a secure underlying VPN/proxy.

Upstream claims that passive/active observers cannot distinguish Cloak from an innocent web server. That is retained as an **upstream design claim**, not as independently verified PVNetwork censorship-resistance or security certification.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations | PASS | `cbeuw/Cloak` is the canonical client+server implementation. The repository also points to `cbeuw/Cloak-android` for Android, while underlying OpenVPN/Shadowsocks/Tor engines remain separate parent protocols. No unrelated project with the same name is treated as this Cloak implementation. |
| 2 | Repository / source identification | PASS | Canonical repository, default branch, stable tag `v2.12.0`, exact commit/tree and recursive manifest are pinned. Repo-metadata update time is not confused with source-release date. |
| 3 | Licensing / legal reuse | PASS | Cloak is GPLv3. Running unmodified code is allowed; conveying binaries/modified covered works triggers GPLv3 corresponding-source/license/notice obligations and whole-covered-work copyleft conditions. No permissive embedding assumption is made. PVNetwork closed-product default is **external-process/reference integration**, with legal review before bundling/conveying. Upstream logos/trademarks are not licensed merely by GPL code rights. |
| 4 | Source tree | PASS | Complete recursive tree reference is pinned; client/server/common/config/build/workflow/package/test paths are inventoried. |
| 5 | Languages / build | PASS | Go 1.24/toolchain 1.24.2; Makefile/Dockerfile/Go modules; exact dependencies including uTLS 1.8.0 are pinned at the release. CI/release workflow files and tests are present. |
| 6 | Architecture | PASS | Parent proxy client -> local Cloak client -> Cloak camouflage/auth/multiplexing over direct TCP or CDN/TLS/WebSocket -> Cloak server -> selected upstream proxy in `ProxyBook`. User/account/traffic-management state is server-side and separate from parent-proxy credentials. |
| 7 | Engine integration | PASS | Cloak is a wrapper/pluggable transport around an underlying proxy. PVNetwork should orchestrate the maintained Cloak process or a separately legally reviewed integration, pass traffic to the selected parent proxy and keep parent-proxy lifecycle/errors distinct. Do not rewrite Cloak crypto/protocol merely to avoid process integration. |
| 8 | UI / menus | PASS for v1 | UI belongs under an advanced **camouflage/pluggable transport** option attached to a supported parent proxy. Expose server/public-key/UID/proxy method/direct-vs-CDN/browser signature/encryption/multiplex settings only when valid. Do not present Cloak as a one-click standalone VPN. Android app UI/screens remain V2 reference work. |
| 9 | Config / import / export / URI / QR | PASS | Upstream canonical interchange is JSON/CLI configuration, with example `ckclient.json`/`ckserver.json`. No standard standalone subscription URI/QR is identified; PVNetwork must not invent one as upstream standard. `PrivateKey`, `PublicKey`, `UID`, `ProxyMethod`, `Transport`, `BrowserSig`, `ServerName`, `AlternativeNames`, CDN fields and connection settings are typed separately. |
| 10 | Persistence / secrets | PASS | Server `PrivateKey`, admin/user authorization data and any parent-proxy secrets are sensitive. Public key/browser signature/server names are non-secret metadata. `userinfo.db` persists usage/restriction state. Ordinary exports/support bundles exclude private keys and sensitive identifiers/usage data. |
| 11 | Platforms | PASS for research | Core is Go and contains Android-specific client hooks; upstream also references a dedicated Android client. Desktop/server binaries are source/build targets. Exact Windows/macOS/Linux/Android packaging, VPN APIs and Store viability remain implementation/V2 evidence. |
| 12 | Logs / diagnostics | PASS | Separate parent-proxy failures from Cloak client config/auth/key/UID, direct/CDN/TLS/WebSocket, browser-signature, multiplex/session, server `ProxyBook`, account/credit/bandwidth and upstream forwarding failures. Sensitive keys/UIDs/account records are redacted. |
| 13 | Assets / localization | PASS for v1 | Repository README contains project graphics; Android/client resources are separate. These assets are not automatically reusable in PVNetwork branding. Full screenshot/localization inventory belongs to V2. |
| 14 | Forks / alternatives | PASS | Direct versus CDN transport, underlying OpenVPN/Shadowsocks/Tor choices, browser signatures and multiplex settings are modes, not separate protocols. GoQuiet-like behavior is obtained with `NumConn=0`. No unrelated same-name project is treated as a fork. |
| 15 | Issues / releases / advisories | PASS | Latest reviewed stable release is v2.12.0; its final commit updated uTLS to 1.8.0. Current repository has substantial open-issue backlog; issue #297 remains open and reports UDP-mode/listener behavior confusion/possible non-operation, so UDP support must be version/device tested before certification. Issue #335 (2026-07) reports broken Renovate configuration stopping dependency-update PR automation, meaning dependency freshness must not be inferred from the bot. No fake “no vulnerabilities” claim is made. |
| 16 | Docs / support authority | PASS | Canonical README/source/examples/wiki links and release/issue history are primary. Upstream anti-detection claims remain design claims requiring later measurement. Third-party install scripts are convenience references only, not authoritative implementation/security evidence. |
| 17 | Tests / CI | PASS | Pinned tree includes client/server/common tests and GitHub build/test/release workflows. Product acceptance still needs exact parent-proxy/server/direct/CDN/UDP/TCP/device combinations; those runtime receipts are not hidden V1 research requirements. |
| 18 | Store / privacy / security | PASS | Cloak can expose sensitive UID, usage database, server target/camouflage names and proxy topology. The optional Cloak encryption mode is not a replacement for authenticated transport security; `plain` requires an already secure underlying proxy. Browser/censorship-resistance claims are not guaranteed. GPL/source-delivery and mobile Store packaging need exact later review. |
| 19 | PVNetwork reuse decision | PASS | **PLUGGABLE-TRANSPORT / EXTERNAL-PROCESS OR SEPARATELY REVIEWED GPL INTEGRATION.** Pair Cloak only with a supported parent proxy; preserve GPL obligations and do not copy it into a closed core without legal/architecture review. Do not inflate protocol count by treating direct/CDN/browser signatures as standalone VPN engines. |
| 20 | Open uncertainties / blockers | PASS | Exact modern-maintenance status after v2.12.0, UDP behavior, current dependency advisories, CDN/domain-fronting provider policies, Android/client release parity, browser-signature fidelity, exact cryptographic design/wire behavior, censorship effectiveness, performance and device/Store interoperability remain V2/implementation/certification work. They do not block the V1 integration/reuse classification. |

## Final V1 decision

All 20 research gates are evidence-backed or correctly bounded. Cloak is open-source GPLv3, not proprietary, but its copyleft boundary and “not standalone” architecture materially constrain how PVNetwork should integrate it.

Entry 079 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
