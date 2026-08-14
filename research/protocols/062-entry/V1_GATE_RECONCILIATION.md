# 062 — innernet — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Decision: **`COMPLETE-RESEARCH-v1 / WIREGUARD PRIVATE-NETWORK MANAGER / MIT / NOT IMPLEMENTED / NOT CERTIFIED`**

innernet is a coordination/management layer around WireGuard. It does not define a new encrypted data plane and must not be represented as a replacement WireGuard cryptographic engine.

## Exact current release
- repo: `tonarino/innernet`
- stable release: `v2.0.0`, published 2026-07-02
- annotated tag: `e0a216d3fdb08e98066dd3b37af834bda171fd89` (unsigned)
- release commit: `dbdb0097b397fa5b10566ae58d33c699142102f2`
- release tree: `d020719f54a2649d61f6ba748557bcae32d2fb62`
- release commit has valid GitHub signature verification
- exact `LICENSE`: MIT.

Upstream release notes say 2.x client/server remain fully compatible with 1.x; the major bump primarily reflects semver-incompatible Rust library API changes (`innernet-client-core`, `innernet-server`, `innernet-shared`). PVNetwork must distinguish wire/product compatibility from Rust library API compatibility.

## Architecture and current behavior
innernet provides a central coordination server plus clients that configure WireGuard interfaces/peers. It organizes peers into CIDR-based networks, supports invitations/enrollment, endpoint override, peer state fetching and controlled address allocation. WireGuard owns tunnel crypto/handshakes; innernet owns peer distribution/membership/configuration.

Canonical state: server URL/endpoint, network name/CIDR, peer identity/public key, assigned address, invitation token, endpoint/listen port and server/client version. WireGuard private keys/invitation secrets require protected storage; network/member metadata and server DB remain control-plane state.

## 20-gate reconciliation
|#|Gate|Result|Conclusion|
|---:|---|---|---|
|1|Clients|PASS|Official innernet client/server are authorities; no fake third-party GUI required.|
|2|Sources|PASS|v2.0.0 exact tag/commit/tree pinned.|
|3|License|PASS|MIT verified; dependency notices/SBOM remain build-specific.|
|4|Tree|PASS|Exact release tree pinned; client/server/shared/core/config/test/build paths represented.|
|5|Build|PASS|Rust/Cargo workspace and packaging/release assets mapped.|
|6|Architecture|PASS|Coordination server -> client config -> WireGuard data plane, CIDR/member/invite model separated.|
|7|Integration|PASS|Use innernet client/core/server APIs or process adapter; do not duplicate WireGuard crypto.|
|8|UI|PASS/N-A upstream GUI|CLI/config concepts map to typed PVNetwork network/peer/invite/status UI; exhaustive screens later.|
|9|Config/import|PASS|Server/network/CIDR/peer/invite/endpoint/listen-port fields explicit; original config preserved.|
|10|Secrets|PASS|WireGuard private keys/invites separate from public keys/address/topology and transient handshake state.|
|11|Platforms|PASS for research|Current project packages Linux/BSD-class environments and Rust portability; exact mobile/Store lifecycle later.|
|12|Diagnostics|PASS|Coordination reachability, invite/enrollment, address allocation, WireGuard interface/peer/handshake/endpoint state separable.|
|13|Assets|PASS/N-A|No canonical consumer GUI asset requirement.|
|14|Alternatives|PASS|1.x/2.x compatible products but Rust APIs changed; other WireGuard managers remain separate products.|
|15|Issues/releases|PASS|Current 2.0.0 release, endpoint override/handshake display fixes and active 2026 library evolution reviewed.|
|16|Docs|PASS|Canonical README/release/source/docs are primary.|
|17|Tests/CI|PASS|Rust workspace tests/CI/release process exist; device interop later.|
|18|Security/privacy|PASS|Central metadata visibility, invite/key secrecy, endpoint exposure, route/CIDR control and MIT reuse obligations explicit.|
|19|Reuse|PASS|Optional lightweight WireGuard coordination module; MIT code is a reuse candidate if it adds needed private-network management.|
|20|Uncertainties|PASS|Exact platform packages/SBOM, server HA/auth model, mobile lifecycle/performance and V2 server/UI/wire topology remain later.|

## Product rules
1. Call innernet a WireGuard manager, not a new encrypted protocol.
2. Keep WireGuard private key ownership local/protected.
3. Protect invitation/enrollment credentials and server state.
4. Preserve 1.x/2.x product interoperability while separately pinning Rust library API versions.
5. Do not infer mobile/full-device support from generic Rust portability.

## Final V1 decision
All 20 V1 gates are evidence-backed. Entry 062 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
