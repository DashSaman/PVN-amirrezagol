# 077 — TLS — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **077 — TLS**

Decision: **`COMPLETE-RESEARCH-v1 / SECURITY PROTOCOL LAYER / CURRENT STANDARD RFC 9846 (TLS 1.3) / USE MAINTAINED UPSTREAM TLS IMPLEMENTATIONS / NOT A STANDALONE VPN / NOT IMPLEMENTED / NOT CERTIFIED`**

This reconciliation closes the v1 research gates without treating runtime/server/device/Store interoperability as a hidden research requirement.

## Standards authority and current correction

- Current TLS 1.3 standards endpoint: **RFC 9846 — The Transport Layer Security (TLS) Protocol Version 1.3**, IETF Standards Track, July 2026: `https://www.rfc-editor.org/info/rfc9846/`.
- RFC 9846 explicitly **obsoletes RFC 8446** while retaining TLS version number 1.3 and remaining backward-compatible as a minor specification update.
- RFC 9846 also obsoletes RFC 5246 (TLS 1.2) and related older TLS documents while adding requirements that affect TLS 1.2 implementations. Older RFCs remain historical/interoperability references rather than the current standards endpoint.
- Identity verification guidance relevant to application protocols using TLS: RFC 9525.

Important current RFC 9846 changes include forbidding KeyShare reuse across connections, forbidding negotiation of TLS 1.0/1.1, clarifying PSK + HelloRetryRequest hashing, strengthening key-update requirements and updating other ambiguous/legacy requirements. PVNetwork must not freeze its research baseline on RFC 8446 merely because existing source comments still cite it.

## Selected implementation references

### A. Go standard library `crypto/tls` — primary reusable TLS implementation for the Xray/Go engine family

- Canonical project: Go — `https://go.dev/`
- Source repository: `https://github.com/golang/go`
- Owner/maintainer: Go project
- Default branch observed: `master`
- Current reviewed stable release: **go1.26.5**, released 2026-07-07
- Exact source pin: `c19862e5f8415b4f24b189d065ed739517c548ba`
- Tree SHA: `0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74`
- Complete recursive manifest:
  `https://api.github.com/repos/golang/go/git/trees/0bb2fb1cc06c334c36a2a92d2f0b07fea7236d74?recursive=1`
- License: BSD-3-Clause, root `LICENSE`
- TLS source: `src/crypto/tls/`
- Related trust/certificate source: `src/crypto/x509/`
- Related network/application integration: `src/net/`, `src/net/http/`, QUIC hooks exposed by `crypto/tls`
- Tests are colocated under `src/crypto/tls/*_test.go`; the tree includes BoringSSL/Bogo-style interop shim/config files such as `bogo_config.json` and `bogo_shim_*_test.go`.

The Go license allows source/binary redistribution and modification subject to retention of copyright/license/disclaimer terms and forbids using Google/contributor names for endorsement without permission. PVNetwork classifies Go `crypto/tls` as **`REUSE-CANDIDATE`** where already naturally used by an engine, subject to exact release/SBOM/security review.

### B. Xray-core TLS integration — primary behavior/integration reference for Xray-family PVNetwork entries

- Repository: `XTLS/Xray-core`
- Research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- Tree SHA: `46ee908a9a67513d3c85bbf998be5d553a078109`
- Complete manifest:
  `https://api.github.com/repos/XTLS/Xray-core/git/trees/46ee908a9a67513d3c85bbf998be5d553a078109?recursive=1`
- License: MPL-2.0
- Build language/toolchain: Go module, `go 1.26` at the pin
- Relevant current config path: `infra/conf/transport_internet.go` recognizes TLS as a security choice distinct from REALITY, flow and outer transport.
- Xray-family architecture/config/client/test/release/security evidence is already recorded under `research/upstreams/xray-family/` and is reused rather than repeated.

Xray-core is a **behavioral reference and component candidate** under its MPL obligations. PVNetwork should not replace maintained upstream TLS cryptography with a custom TLS implementation.

### Alternatives / platform implementations

OpenSSL/BoringSSL/rustls/SChannel/Secure Transport/Network.framework/other OS-native TLS stacks are meaningful ecosystem implementations, but this entry does not promote all of them as a single universal embedded dependency. Each selected PVNetwork engine keeps its own maintained TLS/native dependency boundary. When a future platform adapter selects one of these directly, its exact source/version/license/platform rules must be pinned separately rather than inferred from Go/Xray evidence.

## Product model

TLS is represented as a **security protocol layer**, not a standalone VPN product. Typical typed configuration includes:

- enabled security mode (`tls`) as part of a parent connection profile;
- `serverName` / expected reference identity;
- trust roots / certificate or SPKI pin references where the parent engine supports them;
- client certificate + private-key references for mTLS where supported;
- ALPN list;
- minimum/maximum protocol version only where there is a justified compatibility policy;
- session resumption / 0-RTT policy where exposed;
- ECH configuration where the selected engine actually supports it;
- certificate-verification policy;
- separate uTLS/fingerprint controls only through entry 078 rather than conflating fingerprinting with TLS itself.

There is no canonical standalone `tls://` subscription/profile URI or QR format for this PVNetwork matrix entry. TLS parameters are embedded in the parent protocol/full-config format.

## 20-gate reconciliation

| # | Completion gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations identified | PASS | Go `crypto/tls` is the primary reusable implementation for Go/Xray-family engines; Xray-core is the primary product-integration/behavior reference. Native/OpenSSL/BoringSSL/rustls families are acknowledged as engine/platform alternatives and require independent pins if selected directly. |
| 2 | Canonical sources pinned | PASS | RFC 9846 is the current TLS 1.3 standards endpoint. Go stable source is pinned to go1.26.5 `c19862e5...`; Xray source remains pinned to `7d214f8b...`. RFC 8446 is explicitly historical/obsolete, not silently treated as current. |
| 3 | Licenses reviewed | PASS | Go root `LICENSE` is BSD-3-Clause with redistribution/modification/notice/no-endorsement terms; Xray is MPL-2.0 with file-level source/notice obligations. RFC text is standards evidence, not a source-code license grant. Exact selected engine dependencies still require shipping SBOM/legal review. |
| 4 | Complete source-tree reference / manifest captured | PASS | Full recursive Go tree at `0bb2fb1c...` and full Xray tree at `46ee908a...` are pinned; TLS/x509/network/test/build source locations are identified. No source tree is copied wholesale into PVNetwork. |
| 5 | Languages / build systems mapped | PASS | Go TLS is implemented inside the Go standard library; Go build/test toolchain and source-integrated tests apply. Xray is a Go module using Go 1.26 at its pin. Native TLS implementations are platform-specific and not falsely collapsed into the Go build. |
| 6 | Architecture mapped | PASS | Parent reliable transport -> TLS client/server handshake -> identity/authentication + key establishment -> protected TLS record layer -> parent application protocol. Xray canonical profile/config generation, security axis, flow and outer transport remain separate. Trust, certificates, sessions and diagnostics are distinct state domains. |
| 7 | Core / engine integration mapped | PASS | Use the selected engine's maintained TLS stack (`crypto/tls` for Go/Xray paths or native/upstream TLS for other engines). PVNetwork owns validated configuration/adapters/UI and lifecycle orchestration, not TLS cryptographic reimplementation. Exact TLS library/toolchain version is part of the engine release pin. |
| 8 | UI / menu map completed | PASS for v1 | TLS has no standalone consumer app UI. Parent profile editors may expose security=TLS, server identity/SNI, certificate/trust, ALPN, client certificate, advanced version/ECH options and explicit unsafe verification overrides. Simple mode should hide risky compatibility knobs. Exhaustive per-client screenshots remain v2. |
| 9 | Config / import / export / URI / QR mapped | PASS | TLS fields are embedded in the parent protocol/full JSON/link format; no standalone TLS URI/QR is invented. Import/export must preserve server identity, ALPN, trust/pin/client-cert references and version policy without silently enabling insecure verification. Fingerprinting is a separate entry 078 field axis. |
| 10 | Persistence / secrets mapped | PASS | Private keys, client-certificate private material, TLS PSKs/session secrets/ECH server private keys are secrets; certificates/public pins and server names are generally non-secret policy metadata. Secrets use secure storage references and are excluded/redacted from ordinary exports/logs. Session caches/tickets are runtime-sensitive state and should not be portable profile material by default. |
| 11 | Platform integrations mapped | PASS for research | Go/Xray TLS is cross-platform at the engine level, while system root-store access, native certificate UI, keychain/keystore, background service packaging and Store constraints differ by Windows/Android/iOS/macOS/Linux. Exact platform trust/credential integration is implementation/V2 work, not a hidden research blocker. |
| 12 | Logs / diagnostics mapped | PASS | Failure domains include DNS/underlay connect, TLS version mismatch, cipher/group/signature mismatch, certificate chain/time/name verification, SNI/serverName, ALPN, mTLS/client cert, ECH, handshake/alert, resumption/0-RTT and parent protocol failure. Key logs/private keys/session secrets are never included in routine diagnostics. Go's `KeyLogWriter` is explicitly security-compromising debug behavior and must remain controlled. |
| 13 | Asset / screenshot references mapped | PASS/N-A | TLS has no independent canonical consumer icon/store screenshot/localization set. UI evidence belongs to the parent client/engine dossiers; third-party app imagery remains reference-only unless reuse rights are clear. |
| 14 | Meaningful forks / alternatives reviewed | PASS | Go TLS, OpenSSL/BoringSSL/rustls and OS-native stacks are distinct implementation alternatives. uTLS is a ClientHello/fingerprinting fork/extension concern and is intentionally separated into entry 078 rather than treated as the generic TLS implementation decision. |
| 15 | Important issues / releases / advisories reviewed | PASS | Current standards drift is material: RFC 9846 (July 2026) obsoletes RFC 8446. Current Go maintenance is also material: go1.26.5 (2026-07-07) contains a `crypto/tls` security fix for **CVE-2026-42505 / GO-2026-5856**, where ECH could leak PSK identities and allow passive hostname de-anonymization; versions from go1.26.0-0 before go1.26.5 are affected. Go release history also shows repeated `crypto/tls` fixes across the 1.26 line. Mitigation: pin patched maintained Go/toolchain versions and re-run exact parent-engine regression tests before shipping. |
| 16 | Relevant official docs / discussions reviewed | PASS | RFC 9846 and RFC 9525 are primary protocol/integration authorities; official Go `crypto/tls` docs/release/security records and pinned Xray source/config guidance are primary implementation authorities. Community posts do not override these sources. |
| 17 | Tests / CI reviewed | PASS | Go `src/crypto/tls` contains unit/integration and Bogo interop test support; Xray upstream test/CI surfaces are already mapped in shared evidence. PVNetwork later needs engine/server combination tests, certificate/time/ALPN/resumption/ECH negative cases and platform trust-store tests, but these are implementation/certification evidence rather than hidden V1 completion gates. |
| 18 | Store / privacy / security implications reviewed | PASS | TLS identity verification must fail closed by default; unsafe certificate bypasses must be explicit/advanced and never silently imported. 0-RTT has replay implications; ECH affects privacy but is version/support sensitive; logs may expose hostnames/certificates/ALPN and require minimization. Consumer Store policy depends on the parent VPN/network app and must be rechecked before release. |
| 19 | PVNetwork reuse decision documented | PASS | **USE MAINTAINED ENGINE/NATIVE TLS; DO NOT REIMPLEMENT TLS CRYPTOGRAPHY.** Go `crypto/tls` is a reuse candidate in Go engines; Xray TLS is consumed through the Xray adapter; other engines keep their selected maintained TLS/native stack. PVNetwork may implement only non-cryptographic profile validation/UI/adapter logic. |
| 20 | Uncertainties explicitly listed | PASS | Exact cipher/group/signature/KEM policy, PQ transition, ECH final ecosystem support, 0-RTT policy, certificate pinning semantics, platform trust-store/keychain behavior, OpenSSL/native engine pins, detailed handshake/wire references, performance and client/server/device/Store interoperability remain v2/deployment/certification work. None blocks the V1 reuse/architecture decision. |

## Final V1 decision

All 20 `COMPLETE-RESEARCH-v1` gates have evidence-backed conclusions or correctly bounded N/A/deferred implementation detail. TLS is complete as a research/security-layer entry; this does not claim runtime interoperability or security certification for any future PVNetwork build.

Entry 077 qualifies for **`COMPLETE-RESEARCH-v1`**.
