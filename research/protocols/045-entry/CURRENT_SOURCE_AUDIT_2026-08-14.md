# Entry 045 — AnyTLS — current source audit (2026-08-14)

This is evidence for the V1 gate reconciliation. It does **not** by itself promote the entry to `COMPLETE-RESEARCH-v1`.

## Canonical protocol/reference implementation

- Canonical organization/repository: `anytls/anytls-go`.
- Default branch: `main`.
- Current reviewed repository HEAD: `fd6167acd6d73b9fa3e607659951847fbc9e6c50` (`Update documentation`, 2026-08-03).
- Current reviewed release: `v0.0.13`, release commit `9666872946857b50a74fdb692896d77b53773cb2` (2026-06-27).
- Repository tree at reviewed HEAD contains `.goreleaser.yaml`, `cmd/`, `docs/`, `go.mod`, `go.sum`, `proxy/`, `readme.md`, and `util/`; it is a Go reference implementation with release packaging via GoReleaser.
- **License gate is not cleared for direct code reuse:** the reviewed repository root has no `LICENSE` file. Do not infer a permissive license merely because the repository is public. Until an authoritative license grant is found, classify direct copying/vendoring/linking of `anytls-go` code as `NEEDS-LEGAL-REVIEW / REFERENCE-ONLY`.

Canonical source: https://github.com/anytls/anytls-go
Pinned tree: https://github.com/anytls/anytls-go/tree/fd6167acd6d73b9fa3e607659951847fbc9e6c50
Reviewed release commit: https://github.com/anytls/anytls-go/commit/9666872946857b50a74fdb692896d77b53773cb2

## Protocol semantics that must remain distinct from generic TLS

The canonical protocol documentation describes AnyTLS as a session/framing protocol carried over TLS. After the TLS handshake, the client authenticates with `sha256(password)` plus padding. A session frame contains command, stream ID, data length, and optional data. Version-1 commands include padding/waste, SYN, PSH, FIN, settings, alert, and padding-scheme update. Protocol version 2 adds SYNACK, heartbeat request/response, and server settings.

AnyTLS protocol parameters are explicitly separate from TLS parameters. The client-side protocol parameters include `password`, `idleSessionCheckInterval`, `idleSessionTimeout`, and `minIdleSession`; server-side protocol state includes `paddingScheme`. Therefore PVNetwork must not flatten TLS certificate/SNI/verification settings into the AnyTLS protocol schema.

Protocol v2 exists to improve stuck-tunnel detection/recovery and server-state feedback while retaining fallback behavior with v1 peers. The documentation also specifies SagerNet UDP-over-TCP handling for the special UDP-over-TCP target; UDP behavior must therefore be modeled as an interoperability layer rather than invented as native AnyTLS UDP datagrams.

Protocol documentation: https://github.com/anytls/anytls-go/blob/fd6167acd6d73b9fa3e607659951847fbc9e6c50/docs/protocol.md

## Current implementation/client set

The canonical reference README identifies `sing-box` and `mihomo` as implementations containing AnyTLS server and client support. These are serious integration candidates because PVNetwork is already studying multi-protocol cores and should prefer an existing maintained core when it avoids introducing another independently managed engine.

Additional current evidence to evaluate during the full V1 gate pass:

- `anytls/sing-anytls`: Go library/implementation advertised as compatible with `anytls-go`; useful for architecture/API comparison.
- `ssrlive/anytls-rs`: Rust implementation with unit/build and local smoke/integration-test documentation; useful as an independent interoperability/reference implementation, not automatically a preferred engine.
- `cfal/shoes`: Rust multi-protocol server includes AnyTLS and SagerNet UDP-over-TCP; useful server-side interoperability evidence.

Do not treat third-party client support as evidence that `anytls-go` itself supplies a production GUI, secure profile store, OS VPN/TUN lifecycle, Store packaging, or platform-native UI. Those gates need evidence from the selected host client/core.

### Pinned sing-box integration evidence

`SagerNet/sing-box` is a concrete maintained multi-protocol host candidate. Reviewed default branch `testing` at commit `db1053f8bc16c860225afc97ac6417e42a81dc64` (2026-08-13). At that pin:

- `protocol/anytls/outbound.go` contains the AnyTLS outbound engine integration.
- `option/anytls.go` defines inbound and outbound schema rather than treating AnyTLS as an opaque generic TLS profile.
- Outbound schema includes `Password`, `IdleSessionCheckInterval`, `IdleSessionTimeout`, `MinIdleSession`, and `ClientMetadata`, alongside a distinct `OutboundTLSOptionsContainer`.
- Inbound schema includes users/passwords and `PaddingScheme`, alongside a distinct `InboundTLSOptionsContainer`.
- This is strong source-level confirmation that PVNetwork should preserve the protocol/TLS separation described by the canonical AnyTLS specification.
- sing-box's reviewed `LICENSE` is GPL-3.0-or-later and adds a naming/association restriction for derivative work. Treat embedding/derivative distribution as a legal/product-architecture decision, not as automatically permissive reuse.

Pinned source references:

- https://github.com/SagerNet/sing-box/tree/db1053f8bc16c860225afc97ac6417e42a81dc64
- https://github.com/SagerNet/sing-box/blob/db1053f8bc16c860225afc97ac6417e42a81dc64/protocol/anytls/outbound.go
- https://github.com/SagerNet/sing-box/blob/db1053f8bc16c860225afc97ac6417e42a81dc64/option/anytls.go
- https://github.com/SagerNet/sing-box/blob/db1053f8bc16c860225afc97ac6417e42a81dc64/LICENSE

## Release/maintenance evidence

`v0.0.13` added a client option to disable connection reuse. The release note states that optimized client/server combinations have no known reuse issue but exposes strict 1:1 behavior for users who prefer it. PVNetwork should therefore model reuse as an engine capability/advanced option only where the selected implementation exposes it, rather than assuming reuse is mandatory protocol behavior.

The repository remained active after the release: reviewed HEAD is dated 2026-08-03. This is evidence of current maintenance as of this audit, not a guarantee of future support.

## PVNetwork preliminary reuse boundary

1. Treat AnyTLS as a distinct protocol adapter over a separately modeled TLS layer.
2. Prefer evaluating existing maintained multi-protocol cores (`sing-box`, `mihomo`) before adding the standalone reference binary as another lifecycle/update surface.
3. Keep reference-implementation code `REFERENCE-ONLY / NEEDS-LEGAL-REVIEW` until an explicit authoritative license is found.
4. Preserve protocol-version negotiation and v1/v2 fallback semantics; do not expose v2-only health/SYNACK behavior as universally available.
5. Add regression cases for authentication failure, version fallback, SYN/FIN lifecycle, stuck-session recovery, connection reuse on/off, padding updates, and UDP-over-TCP interoperability.
6. UI/persistence/platform/diagnostics/store gates must be satisfied from the actual selected host client/core; the reference protocol repository is not evidence for those product layers.
7. If sing-box is selected as the AnyTLS host engine, explicitly resolve GPL-3.0-or-later distribution obligations and the additional naming/association language before deciding how the engine is packaged with PVNetwork.

## Remaining V1 work

Before promotion, reconcile all 20 gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`, including pinned selected-client/core trees and licenses, UI/menu evidence, profile import/URI behavior, persistence/secrets, platform integrations, logs/diagnostics, assets, meaningful forks, issues/PRs, tests/CI, Store/privacy/security implications, and the final PVNetwork engine decision. Any unresolved license uncertainty must be explicitly carried into the final reuse classification rather than guessed away.
