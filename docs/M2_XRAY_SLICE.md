# M2 Xray/VLESS Adapter — Implementation and Runtime Slices

Status: **TESTED for product-owned adapter/share-link slice; production bundled/imported Xray release gate BLOCKED; host-supplied JVM runtime work allowed**

## Research boundary reused

The final VLESS V2 audit is `COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED / NOT CERTIFIED`. It records:

- research core pin `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0;
- wrapper reference `XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`, MIT at wrapper root;
- 2026 release evidence including `v26.7.28` (`5ca6f4b`) and `v26.7.11` (`50231ea`), explicitly **not** automatic production approval.

This slice imports no Xray-core/libXray source or binary. The current production release/import decision is documented in `docs/M2_XRAY_STABLE_RELEASE_GATE.md`.

## Stable production import gate

The current non-prerelease release returned by upstream `releases/latest`, `v26.3.27`, is rejected for PVNetwork production bundling because upstream security-advisory review places it inside an affected range that is patched beginning at `v26.7.11`. The inspected patched-line releases `v26.7.11` and `v26.7.28` are prereleases.

Therefore:

- bundled/imported Xray production dependency: **BLOCKED**;
- libXray/source embedding: **not approved**;
- downloading an unpinned `latest` runtime: **forbidden**;
- product-owned host-supplied managed-subprocess runtime engineering: **allowed to continue independently**;
- exact `v26.7.28` Linux x86_64 prerelease may be used only as a checksum-pinned ephemeral CI fixture under the restrictions in `docs/M2_XRAY_STABLE_RELEASE_GATE.md`.

## Product-owned adapter source

`engines/xray-adapter` currently provides:

- an explicit VLESS application-protocol model separate from security, flow and transport;
- VLESS share-link import with IPv4/hostname/bracketed-IPv6 endpoint parsing;
- UTF-8 percent-decoding for URI components while preserving literal `+` URI semantics;
- protected original share-link and VLESS identity references via `SecretStore`;
- TLS/REALITY/none security classification;
- RAW/TCP, WebSocket, gRPC, XHTTP and mKCP transport classification;
- Vision flow preservation without presenting arbitrary future flows as certified;
- explicit warnings for unknown security/transport/flow/query fields;
- fail-closed adapter validation so unknown security/transport/flow cannot become runtime support merely because a runtime advertises `vless`;
- REALITY validation requiring an explicit public key before runtime preparation;
- product-owned runtime-factory boundary whose capabilities come only from a concrete runtime descriptor.

No VLESS cryptography is invented; the model preserves the research distinction that VLESS is the application protocol while security/transport are separate dimensions.

## Existing CI evidence

`.github/workflows/m2-xray-adapter-ci.yml` runs the adapter JVM tests.

Evidence:

- GitHub Actions run `31939691385`: **SUCCESS** for the initial Xray/VLESS adapter/share-link slice.
- GitHub Actions run `31940684691`: **SUCCESS** after fail-closed unsupported-combination and REALITY-public-key regression coverage.
- GitHub Actions run `31940779078`: **SUCCESS** after UTF-8 URI decoding/literal-plus regression coverage; this is the current adapter-slice receipt.

## Next independent runtime slice

The active implementation work is a **host-supplied JVM managed-subprocess runtime**. It must not bundle or download Xray. The first runtime slice should be deliberately narrow and fail closed:

1. external executable discovery/probe with bounded output and timeout;
2. product-owned VLESS config generation for an explicitly supported subset only;
3. transient private config creation from `SecretStore` data;
4. upstream `run -test -c` config validation before long-lived start where the probed executable supports the inspected CLI contract;
5. `run -c` managed process launch without a shell;
6. deterministic readiness, lifecycle, stop and cleanup behavior;
7. fake-executable JVM tests first, then an optional checksum-pinned patched prerelease CI fixture for real process/interoperability evidence.

A passing CI fixture will not unblock or approve production bundling; that remains a separate release gate.

## Status boundary

- RESEARCHED: yes, via the closed V1/V2 research gates.
- IMPLEMENTED: **yes, for the product-owned adapter/import/model slice**.
- BUILT: **yes, scoped JVM/KMP adapter CI gate**.
- TESTED: **yes, scoped adapter/import tests through run `31940779078`**.
- Xray-core/libXray dependency imported into product: **no**.
- Stable bundled production Xray release gate: **BLOCKED** pending `docs/M2_XRAY_STABLE_RELEASE_GATE.md` unblock conditions.
- Host-supplied JVM managed-subprocess runtime: **next active work, not yet tested**.
- Concrete Xray interoperability: **no claim yet**.
- DEVICE VERIFIED: **no**.
- Store verified/certified: **no**.
- PRODUCTION READY: **no**.

M2 remains open. Runtime work may progress independently of the blocked bundled-production release gate, but no product dependency or production Xray claim may be inferred from adapter tests or CI-only prerelease fixtures.
