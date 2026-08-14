# 078 — uTLS / TLS Fingerprinting — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **078 — uTLS / TLS Fingerprinting**

Decision: **`COMPLETE-RESEARCH-v1 / CLIENTHELLO-FINGERPRINT CAPABILITY / XRAY-SELECTED UTLS DEPENDENCY / NOT A STANDALONE VPN PROTOCOL / NOT A TLS SECURITY GUARANTEE / NOT IMPLEMENTED / NOT CERTIFIED`**

This reconciliation reuses completed TLS entry 077 and the existing Xray-family evidence, and closes only the uTLS/fingerprint-specific source, lifecycle and product-model gaps.

## Exact implementation and dependency pins

### Xray-selected dependency

Pinned Xray-core:

- `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- Xray tree `46ee908a9a67513d3c85bbf998be5d553a078109`
- MPL-2.0
- Go module `go 1.26`

Its exact `go.mod` selects:

`github.com/refraction-networking/utls v1.8.3-0.20260301010127-aa6edf4b11af`

That is a pseudo-version, not a fabricated release tag.

### uTLS exact selected source

- canonical repository: `refraction-networking/utls`
- project description: fork of Go standard `crypto/tls` with low-level ClientHello access/mimicry features
- default branch: `master`
- language: Go
- exact Xray-selected commit: `aa6edf4b11af82e110eea845bb2983d30138d651`
- commit date: 2026-03-01
- commit message: `feat: add safari 26.3`
- tree SHA: `f53a70a2c4297e2d8a1c4890d3091928f992a132`
- complete recursive manifest:
  `https://api.github.com/repos/refraction-networking/utls/git/trees/f53a70a2c4297e2d8a1c4890d3091928f992a132?recursive=1`
- root license: **BSD-3-Clause**
- module: `github.com/refraction-networking/utls`
- exact pin uses `go 1.24`
- direct module dependencies at that pin include Brotli, klauspost/compress and Go x/crypto/x/net/x/sys/x/text.

Tag review on 2026-08-14 found `v1.8.2` as the latest ordinary version tag returned by the reviewed tag feed, pointing to `8fe0b08e9a0e7e2d08b268f451f2c79962e6acd0` (2026-01-12). The Xray-selected March pseudo-version is therefore newer than `v1.8.2`, but older than current upstream `master` maintenance work observed through August 2026.

## Current source / maintenance drift

The uTLS security policy states that only the `master` branch is maintained and older major/minor versions do not receive security patches unless specifically requested. This matters because Xray consumes an exact March pseudo-version rather than floating current `master`.

High-signal maintenance evidence:

- `v1.8.2` fixed a missing Chrome 120 padding extension that made the non-PQ Chrome 120 fingerprint incorrect.
- Xray-selected `aa6edf4b...` added Safari 26.3 fingerprint data and changed `HelloSafari_Auto` to Safari 26.3.
- later upstream `master` commits after the selected Xray pin include:
  - `ffd9d0fc04103d67b3c5e04865af23c8ee04faea` — identifies/fixes a feature gap where `VersionInformation` GREASE was not randomized to a valid GREASE pattern per connection;
  - `23b1dac19c06c51e278468e29ac329eec605a31f` — additional QUIC/QTP GREASE feature-gap work;
  - June 2026 work adds ML-KEM named-group support.

These changes are strong evidence that fingerprint fidelity is version-sensitive. They are **not** proof that the selected Xray pin is insecure, and they do not justify silently replacing Xray's dependency without engine regression testing.

## Xray fingerprint model at the exact core pin

Current `transport/internet/tls/tls.go` in the selected Xray core directly imports `github.com/refraction-networking/utls` and exposes fingerprint selection through `GetFingerprint`.

Recommended/preset names at that exact pin include:

- `chrome`
- `firefox`
- `safari`
- `ios`
- `android`
- `edge`
- `360`
- `qq`
- `random`
- `randomized`
- `randomizednoalpn`

The same source also exposes explicit modern/older ClientHello IDs for advanced/versioned use. `random` selects one current preset at Xray process startup. `randomized`/`randomizednoalpn` use uTLS randomized ClientHello behavior with Xray-adjusted weights.

Xray's uTLS wrapper copies the actual TLS trust/server-name/ECH/ALPN configuration into uTLS. Fingerprinting therefore changes the ClientHello presentation layer; it does **not** replace certificate validation, TLS identity, or the parent TLS/REALITY security semantics.

## Upstream claim discipline

The uTLS README says the handshake is still performed by `crypto/tls` and uTLS primarily changes the ClientHello and exposes lower-level controls. It also explicitly warns:

- documentation may lag current code;
- browser parroting can be imperfect;
- there is no parroting beyond ClientHello;
- randomized fingerprints can occasionally fail;
- changing fingerprints constantly may itself look suspicious;
- manual/custom handshake control has compatibility and correctness risk.

Accordingly, PVNetwork treats “Chrome/Firefox/Safari fingerprint” as a **requested ClientHello profile**, not as proof that traffic is indistinguishable from the real browser, not as an anonymity guarantee, and not as a replacement for TLS authentication.

## 20-gate reconciliation

| # | v1 completion gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations identified | PASS | `refraction-networking/utls` is the canonical selected ClientHello-mimicry implementation because the pinned Xray engine directly depends on it. Go `crypto/tls` from completed entry 077 is the non-fingerprinting TLS baseline. Xray-family GUI clients are configuration/UI references, not independent fingerprint engines unless their exact core differs. |
| 2 | Canonical sources pinned | PASS | Xray exact commit/tree and exact uTLS pseudo-version/full commit/tree are pinned. Latest reviewed ordinary uTLS tag is `v1.8.2`, explicitly separated from Xray's newer pseudo-version and from later `master` changes. No fake `v1.8.3` release tag is claimed. |
| 3 | Licenses reviewed | PASS | uTLS exact pin is BSD-3-Clause: source/binary redistribution and modification are allowed subject to retained copyright/license/disclaimer text and no Google/contributor endorsement without permission. Xray remains MPL-2.0. Shipping still requires exact dependency/SBOM/legal review; browser/vendor trademarks are not granted by the uTLS license. |
| 4 | Complete source-tree reference / manifest captured | PASS | uTLS recursive tree manifest is pinned at `f53a70a...`; root includes README, LICENSE, SECURITY, Go module, examples, tests and CI configuration. Xray complete tree remains separately pinned. No repository is copied wholesale into PVNetwork. |
| 5 | Languages / build systems mapped | PASS | uTLS selected pin is Go 1.24/module based with exact direct dependencies; CI at the pin builds/tests on Ubuntu, Windows and macOS with Go 1.24.x. Xray is Go 1.26. Browser fingerprint data, TLS fork code, examples/tests and workflows are all source-visible. |
| 6 | Internal architecture / data flow mapped | PASS | Parent profile -> fingerprint name/ClientHello ID -> Xray TLS wrapper -> uTLS `UClient`/ClientHello generation -> normal TLS handshake/trust semantics -> parent application protocol. Fingerprint selection, TLS security configuration, application protocol, flow and outer transport remain distinct axes. |
| 7 | Core / engine integration mapped | PASS | PVNetwork should consume fingerprinting through the pinned Xray adapter by generating the supported fingerprint field/name. It should not directly rewrite TLS/ClientHello crypto logic unless a future non-Xray engine explicitly requires a separately reviewed uTLS integration. Exact client/server/core compatibility remains version-gated. |
| 8 | UI / menu map completed | PASS for v1 | Fingerprinting belongs under Advanced TLS/REALITY compatibility/ClientHello settings, not as a standalone protocol card. Simple mode may use engine defaults. UI may offer source-backed preset names and an explicit auto/random policy, with warnings that mimicry is best-effort and version-sensitive. Exhaustive client screenshots belong to V2. |
| 9 | Config / import / export / URI / QR mapped | PASS | Fingerprint is non-secret metadata embedded in the parent Xray-compatible profile/full config, not a standalone `utls://` URI or QR format. Import/export must preserve explicit fingerprint values and distinguish unspecified/default, random preset and randomized ClientHello behavior without silently rewriting them. |
| 10 | Persistence / secrets mapped | PASS | Fingerprint names/ClientHello IDs are non-secret. TLS private keys, client credentials, session secrets and REALITY credentials remain governed by their own completed entries. Randomized seed/session runtime details are not portable identity credentials and should not be exposed as reusable secrets. |
| 11 | Platform-specific implementation mapped | PASS for research | uTLS/Xray Go code can build across major desktop/mobile-supporting targets through the parent engine, while actual VPN wrapper/native network integration remains platform-specific. Fingerprint behavior is engine-version dependent, not guaranteed merely by OS support. |
| 12 | Logs / diagnostics / failure mapping | PASS | Diagnostics distinguish unknown/unsupported fingerprint name, ClientHello build failure, ALPN/application mismatch, TLS handshake/certificate/identity failure, ECH/REALITY interaction, transport failure and parent protocol failure. Routine logs do not dump full sensitive TLS state or key material. |
| 13 | Assets / screenshots / localization mapped | PASS/N-A | uTLS has a repository logo/docs/examples but no canonical consumer VPN application/store asset set. Browser/vendor logos are not granted for product reuse. Client UI imagery/localization stays reference-only under its own license; exhaustive screenshots are V2 evidence. |
| 14 | Meaningful forks / alternatives / variants reviewed | PASS | Standard Go `crypto/tls` is the baseline alternative; uTLS presets, randomized ClientHello and custom specs are capability variants. Real browser TLS stacks are interoperability/reference targets, not embeddable “fingerprints.” No unrelated fork is promoted without a concrete engine selection. |
| 15 | Important issues / PRs / releases / advisories reviewed | PASS | Latest tag/current-master drift and concrete fingerprint corrections were reviewed. uTLS explicitly maintains only `master`; selected Xray pseudo-version predates later GREASE/ML-KEM fidelity work. Mitigation: pin exact engine dependency, never claim perfect browser parity, and test any dependency update inside the exact Xray/server/transport combination before shipping. |
| 16 | Relevant official docs / forums reviewed | PASS | Canonical uTLS README/source/SECURITY/commit history and pinned Xray source are primary. The README itself warns that documentation can be stale and parroting imperfect; product claims therefore defer to source and measured certification rather than community folklore or JA3/JA4 marketing statements. |
| 17 | Tests / CI / quality evidence reviewed | PASS | uTLS pinned CI performs `go build -v ./...` and `go test -v ./...` on Ubuntu/Windows/macOS; repository contains extensive colocated tests. Xray tests/CI are already mapped. Browser-wire fidelity and real network/server combination tests remain later acceptance/certification evidence rather than hidden V1 gates. |
| 18 | Store / privacy / security implications reviewed | PASS | Fingerprint mimicry is traffic-shape metadata and can affect privacy/detectability, but it does not authenticate the server or add encryption beyond TLS. Unsafe certificate verification remains prohibited by default. Browser/vendor naming must not imply endorsement. Store/privacy review follows the parent app and actual telemetry/log behavior. |
| 19 | PVNetwork reuse / rewrite / hybrid decision documented | PASS | **XRAY-ADAPTER CAPABILITY / UTLS INDIRECT REUSE.** Prefer the exact uTLS version selected and tested by Xray rather than a separate direct fork. BSD-3-Clause permits direct reuse if ever independently needed, but that path requires its own dependency/security regression review. Do not create a separate “uTLS VPN protocol.” |
| 20 | Open uncertainties / blockers listed | PASS | Exact browser-fidelity/JA3/JA4 behavior, real browser drift, post-pin GREASE/PQ updates, compatibility of newer uTLS with the selected Xray revision, full captured ClientHello/wire reference, server/transport/ECH/REALITY combinations, performance and device/Store behavior remain V2/implementation/certification work. None blocks the V1 architectural/reuse decision. |

## Final V1 decision

Every applicable `PROTOCOL_RESEARCH_TEMPLATE.md` gate is evidence-backed or correctly bounded. uTLS/TLS Fingerprinting is complete as a **ClientHello capability research entry**. This does not claim perfect browser impersonation, anti-detection success, runtime compatibility, or security certification.

Entry 078 qualifies for **`COMPLETE-RESEARCH-v1`**.
