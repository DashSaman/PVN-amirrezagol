# M2 Xray/VLESS Adapter — First Implementation Slice

Status: **TESTED — product-owned adapter/share-link slice only; no Xray runtime/interoperability claim**

## Research boundary reused

The final VLESS V2 audit is `COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED / NOT CERTIFIED`. It records:

- research core pin `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, tree `46ee908a9a67513d3c85bbf998be5d553a078109`, MPL-2.0;
- wrapper reference `XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`, MIT at wrapper root;
- 2026 release evidence including `v26.7.28` (`5ca6f4b`) and `v26.7.11` (`50231ea`), explicitly **not** automatic production approval.

This slice imports no Xray-core/libXray source or binary. Exact production stable release, dependency/SBOM/vulnerability, MPL obligations and subprocess-vs-wrapper platform strategy remain mandatory before dependency import.

## Product-owned source

`engines/xray-adapter` adds:

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
- product-owned runtime boundary whose capabilities come only from a concrete runtime descriptor.

No VLESS cryptography is invented; the model preserves the research distinction that VLESS is the application protocol while security/transport are separate dimensions.

## CI evidence

`.github/workflows/m2-xray-adapter-ci.yml` runs:

```bash
gradle --no-daemon :engines:xray-adapter:jvmTest --stacktrace
```

Evidence:

- GitHub Actions run `31939691385`: **SUCCESS** for the initial Xray/VLESS adapter/share-link slice.
- GitHub Actions run `31940684691`: **SUCCESS** after fail-closed unsupported-combination and REALITY-public-key regression coverage.
- GitHub Actions run `31940779078`: **SUCCESS** after UTF-8 URI decoding/literal-plus regression coverage; this is the current slice test receipt.

## Status boundary

- RESEARCHED: yes, via the closed V1/V2 research gates.
- IMPLEMENTED: **yes, for this product-owned adapter/import/model slice**.
- BUILT: **yes, scoped JVM/KMP CI gate**.
- TESTED: **yes, scoped adapter/import tests through run `31940779078`**.
- Xray-core/libXray dependency imported: **no**.
- Concrete Xray runtime connected: **no**.
- INTEROPERABILITY VERIFIED: **no**.
- DEVICE VERIFIED: **no**.
- Store verified/certified: **no**.
- PRODUCTION READY: **no**.

M2 therefore remains open. A concrete Xray runtime must not be added until the exact stable production release, dependency/SBOM/vulnerability, MPL and platform lifecycle strategy is recorded.
