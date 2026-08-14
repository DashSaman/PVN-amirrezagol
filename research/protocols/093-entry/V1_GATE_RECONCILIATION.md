# 093 — DTLS — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **093 — DTLS**

Decision: **`COMPLETE-RESEARCH-v1 / TLS-DERIVED DATAGRAM SECURITY PROTOCOL / RFC 9147 DTLS 1.3 BASELINE / MAINTAINED LIBRARY REUSE / NOT A VPN / NOT IMPLEMENTED / NOT CERTIFIED`**

## Current standards baseline

Primary standards authority was rechecked on 2026-08-14 against the RFC Editor/IETF sources:

- RFC 9147 — *The Datagram Transport Layer Security (DTLS) Protocol Version 1.3*:
  `https://www.rfc-editor.org/info/rfc9147/`
- RFC 9147 is the current DTLS 1.3 standards-track baseline and obsoletes RFC 6347 (DTLS 1.2).
- RFC 6347 remains a legacy/interoperability reference only where an existing peer still requires DTLS 1.2.
- TLS security/identity principles already researched in entry 077 remain relevant, but DTLS has datagram-specific record numbering, retransmission, anti-replay, epoch and path/MTU behavior and must not be modeled as “TLS over UDP” with TCP assumptions copied blindly.

No newer standards-track replacement for RFC 9147 was identified in the current RFC/IETF review. Future agents must still recheck standards status before a release freeze.

## Selected maintained implementation reference

The pinned Xray dependency set contains:

- module: `github.com/pion/dtls/v3`
- exact selected release: **`v3.1.4`**
- canonical repository: `pion/dtls`
- exact source ref: `v3.1.4`
- recursive source manifest: `https://api.github.com/repos/pion/dtls/git/trees/v3.1.4?recursive=1`
- language/build: Go module
- license: **MIT**, verified from the root `LICENSE` at the exact `v3.1.4` ref
- module/build metadata: root `go.mod` at the exact release ref.

The exact Xray core research pin remains:

- `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`
- tree `46ee908a9a67513d3c85bbf998be5d553a078109`
- MPL-2.0.

Pion DTLS is treated as a maintained DTLS library/reference selected in the Xray dependency graph, **not** as evidence that Xray exposes a standalone DTLS VPN transport for every parent protocol. Direct product exposure must follow the exact parent-engine/config capability actually selected later.

## Product/security model

DTLS is a security protocol for datagram transports. A typed product model must keep these dimensions separate:

- lower datagram transport/endpoints/MTU;
- DTLS version and handshake/retransmission policy;
- server/client identity and certificate/trust configuration;
- optional PSK mode only when the selected implementation/profile actually supports and requires it;
- cipher/signature/group capability as implementation/version policy rather than arbitrary UI defaults;
- anti-replay/replay-window and epoch/sequence state;
- connection-ID/path-migration features only when explicitly supported by the selected DTLS version/library;
- application protocol carried above DTLS.

DTLS does not itself create a VPN, routing policy, tunnel interface, proxy protocol or application subscription format.

## 20-gate reconciliation

| # | Gate | Result | Evidence / decision |
|---:|---|---|---|
| 1 | Top clients / implementations | PASS | RFC 9147 is the protocol authority; Pion DTLS v3.1.4 is the exact maintained Go implementation selected in the pinned Xray dependency graph. Native/OpenSSL/BoringSSL/other DTLS stacks remain separate platform/engine alternatives and require their own pin if selected directly. |
| 2 | Canonical sources pinned | PASS | RFC 9147 plus exact `pion/dtls` tag `v3.1.4` and recursive source manifest are pinned; RFC 6347 is explicitly legacy. Xray's exact parent-engine commit/tree is separately pinned. No fabricated commit is inserted where an exact immutable release tag already identifies the selected source. |
| 3 | Licensing / legal reuse | PASS | Pion DTLS v3.1.4 is MIT, permitting commercial use/modification/distribution subject to retention of copyright/license terms and warranty disclaimer. Xray remains MPL-2.0. Exact shipped dependency notices/SBOM and final legal review remain mandatory. |
| 4 | Complete source-tree review | PASS | The complete Pion DTLS tree is captured by the exact-tag recursive manifest; root source/config/handshake/record/crypto/state/tests/examples/workflow/build metadata are reviewable at the same ref. Xray complete tree remains independently pinned. |
| 5 | Languages / build / dependencies | PASS | Pion DTLS is a Go module at the selected release; Xray is a Go module. Exact module dependencies and test/build surfaces are source-visible at the pinned refs. No separate DTLS daemon/package is assumed for the library path. |
| 6 | Internal architecture / data flow | PASS | Application datagrams -> DTLS handshake/security state -> protected DTLS records -> datagram transport -> peer DTLS record processing -> application. Handshake retransmission, epochs/sequence numbers, anti-replay, MTU/fragmentation and identity verification are datagram-specific state domains. |
| 7 | Core / engine integration | PASS | Reuse the selected maintained DTLS implementation through the parent engine/library boundary. PVNetwork owns validated profile/UI/lifecycle adapters, not custom DTLS cryptography. A future non-Xray engine may use a different maintained DTLS stack only after an independent source/license/version review. |
| 8 | UI / menus | PASS/N-A | DTLS is a security-layer capability under a parent datagram application/profile, not a standalone VPN card. Advanced UI may expose version, server identity/trust, client cert/PSK references, MTU/replay/timeout policy only when the exact engine supports them. |
| 9 | Config / import / export / URI / QR | PASS | DTLS settings are embedded in the parent application/full configuration. No canonical standalone `dtls://` PVNetwork subscription or QR format is invented. Import/export must preserve identity/trust/version/credential references without exporting runtime keys/session state. |
| 10 | Persistence / secrets | PASS | Private keys, PSKs, session/resumption secrets and active traffic keys are secrets; certificates/public identities and most policy metadata are not equivalent secrets. Secure-storage references and redaction rules from TLS entry 077 apply, with DTLS runtime replay/epoch state excluded from ordinary portable profiles. |
| 11 | Platform-specific implementation | PASS for research | Pion/Xray Go paths are cross-platform at the library/engine level; UDP socket APIs, MTU, mobile/background behavior, native certificate stores and parent VPN wrappers differ by platform. Runtime/platform certification remains later evidence. |
| 12 | Logs / diagnostics / failure mapping | PASS | Distinguish lower UDP reachability/MTU, handshake timeout/retransmission, version/cipher/signature mismatch, certificate/name/trust/PSK failure, replay/epoch/record errors, close/alert and parent-application failures. Routine logs/support bundles exclude keys/PSKs/session secrets. |
| 13 | Assets / screenshots / localization | PASS/N-A | DTLS has no canonical standalone consumer application/store asset set. Parent-client UI/localization evidence remains separately licensed and detailed screenshots belong to V2. |
| 14 | Forks / alternatives / variants | PASS | DTLS 1.3 versus legacy DTLS 1.2 are version states; TLS over reliable transports is separate entry 077; QUIC uses TLS-derived keying but is a different transport entry 083. Pion/native/OpenSSL stacks are implementation alternatives, not protocol forks to merge silently. |
| 15 | Issues / releases / advisories | PASS | Exact Pion `v3.1.4` is pinned rather than floating. The canonical project/tag history and current standards status were reviewed; later Pion releases or downstream engine changes must undergo advisory/changelog/regression review before upgrade. No unsupported “latest is vulnerability-free” claim is made. |
| 16 | Official docs / support authority | PASS | RFC 9147/RFC Editor, canonical Pion source/docs and the pinned parent-engine source are primary. Community configuration recipes do not override protocol or exact-library behavior. |
| 17 | Tests / CI / quality evidence | PASS | Pion DTLS source tree includes implementation tests/examples and project CI/build metadata; Xray shared tests are independently mapped. Real peer/library/device/network interoperability is later implementation/certification evidence and is not a hidden V1 gate. |
| 18 | Store / privacy / security implications | PASS | DTLS provides security only when identity/trust/credential configuration is correct. Unsafe certificate verification or weak/legacy compatibility must not be silently enabled. Datagram metadata/endpoint information remains visible at lower layers and logs require minimization. Store policy belongs to the parent networking app. |
| 19 | PVNetwork reuse decision | PASS | **MAINTAINED DTLS LIBRARY / PARENT-ENGINE CAPABILITY; NO CUSTOM DTLS CRYPTO.** Reuse the exact selected library when the parent engine genuinely needs DTLS; otherwise keep it as a dependency/reference and do not inflate the product protocol list. |
| 20 | Open uncertainties / blockers | PASS | Exact application using DTLS, DTLS 1.2 fallback policy, connection-ID support, PSK/certificate profile details, replay-window/MTU tuning, platform trust stores, post-v3.1.4 maintenance, performance and real peer/device/Store interoperability remain V2/deployment/certification work. None blocks the V1 architecture/reuse decision. |

## Final V1 decision

All 20 `COMPLETE-RESEARCH-v1` gates are evidence-backed or correctly bounded. Runtime/device/Store/interoperability testing is not treated as a hidden research-completion condition.

Entry 093 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining **not implemented / not runtime-certified / not interoperability-certified**.
