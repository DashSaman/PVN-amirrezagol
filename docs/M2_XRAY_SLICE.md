# M2 Xray/VLESS Adapter — Implementation and Runtime Slices

Status: **INTEROPERABILITY_VERIFIED for the selected host-supplied Linux/JVM VLESS runtime scope; bundled/imported production Xray release gate remains BLOCKED**

## Research boundary reused

The final VLESS V2 audit is complete. It records the upstream Xray/VLESS reference and does not by itself imply implementation or certification. This implementation slice imports no Xray-core/libXray source into PVNetwork.

The current production release/import decision remains documented separately in `docs/M2_XRAY_STABLE_RELEASE_GATE.md`.

## Stable production import gate

The bundled/imported production dependency gate remains **BLOCKED**. The reviewed non-prerelease `v26.3.27` is inside an upstream advisory affected range patched from `v26.7.11`, while reviewed patched-line `v26.7.11` and `v26.7.28` releases are prereleases.

Therefore:

- bundled/imported Xray production dependency: **BLOCKED**;
- libXray/source embedding: **not approved**;
- dynamic download of an unpinned `latest` runtime: **forbidden**;
- product-owned host-supplied managed-subprocess runtime: **implemented and tested independently**;
- exact `v26.7.28` Linux x86_64 prerelease is permitted only as a checksum-pinned ephemeral CI interoperability fixture under `docs/M2_XRAY_STABLE_RELEASE_GATE.md`.

## Product-owned adapter source

`engines/xray-adapter` provides:

- explicit VLESS application-protocol modeling separate from security, flow and transport;
- VLESS share-link import with IPv4/hostname/bracketed-IPv6 endpoint parsing;
- UTF-8 URI decoding while preserving literal `+` URI semantics;
- protected original share-link and VLESS identity references via `SecretStore`;
- TLS/REALITY/none security classification;
- RAW/TCP, WebSocket, gRPC, XHTTP and mKCP transport classification;
- Vision flow preservation and fail-closed validation;
- REALITY validation requiring an explicit public key;
- runtime capabilities derived only from the concrete runtime descriptor.

No VLESS cryptography is reimplemented.

## Host-supplied JVM runtime

`engines/xray-adapter/src/jvmMain/kotlin/com/pvnetwork/engine/xray/JvmHostXrayRuntime.kt` now implements the selected M2 Xray runtime strategy for POSIX JVM hosts.

The boundary:

1. accepts/discovers only a host-supplied `xray` executable;
2. directly probes `xray version` with bounded timeout and no shell;
3. advertises VLESS only when the executable probe is valid;
4. resolves reusable identity material only through `SecretStore`;
5. creates mode-0700 transient runtime directories and mode-0600 JSON config files;
6. runs `xray run -test -c` fail-closed before long-lived start;
7. starts `xray run -c` without a shell;
8. maps readiness/exit/stop into canonical PVNetwork states;
9. drains output without retaining unbounded raw diagnostic material;
10. removes transient configuration on stop/failure paths.

The generated configuration supports the adapter's explicit VLESS dimensions, while real-interoperability claims remain scoped only to combinations actually exercised by retained evidence.

## CI evidence

Adapter/share-link regression history includes successful earlier runs `31939691385`, `31940684691`, and `31940779078`.

The authoritative M2 host-runtime receipt is:

- GitHub Actions run `32072138649`: **SUCCESS**;
- head SHA `d1329c252c3eb42967793e612002fa5d516ddb69`;
- job `Xray VLESS adapter/share-link contracts`: **SUCCESS**;
- job `Real Xray VLESS JVM data path`: **SUCCESS**.

The real job uses the exact-checksum CI-only fixture:

- `XTLS/Xray-core` tag `v26.7.28`;
- `Xray-linux-64.zip`;
- SHA-256 `8195d909f1109b8f3d99eefe401a3c451d7bf4af71f24d3815420f77e5dd2a40`.

It proves bidirectional bytes through:

`test client -> PVNetwork SOCKS -> PVNetwork-generated VLESS outbound -> real Xray VLESS server -> Freedom -> isolated IPv4 TCP echo origin`.

Detailed retained evidence is in `docs/M2_XRAY_HOST_RUNTIME_VALIDATION.md`.

## Scope boundary

- RESEARCHED: **yes**.
- Adapter/import/model IMPLEMENTED: **yes**.
- Host-supplied POSIX/JVM Xray runtime IMPLEMENTED: **yes**.
- BUILT/TESTED: **yes** for the scoped JVM adapter/runtime CI.
- INTEROPERABILITY VERIFIED: **yes**, specifically VLESS + RAW + `security=none` on Ubuntu 24.04 CI with the external exact-checksum Xray fixture.
- TLS/REALITY/Vision/WebSocket/gRPC/XHTTP/mKCP per-combination interoperability: **not inferred** from the RAW/no-security receipt.
- Xray-core/libXray bundled into product: **no**.
- Stable bundled production Xray release gate: **BLOCKED**.
- DEVICE VERIFIED: **no**.
- Store verified/certified: **no**.
- PRODUCTION READY: **no**.

For the roadmap M2 scope, the selected Xray product runtime is the host-supplied managed-subprocess boundary. The remaining bundled-release blocker concerns a different future distribution model and does not invalidate the verified host-supplied M2 runtime path.
