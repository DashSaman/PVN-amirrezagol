# 088 — gRPC — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **088 — gRPC**

Decision: **`COMPLETE-RESEARCH-v1 / RPC APPLICATION LAYER OVER HTTP/2 / Xray transport integration reference / NOT A VPN OR SECURITY PROTOCOL / NOT IMPLEMENTED / NOT CERTIFIED`**

## Canonical authority and exact selected implementation

- Official project/docs: `https://grpc.io/` and `https://grpc.io/docs/what-is-grpc/core-concepts/`.
- Canonical Go implementation: `https://github.com/grpc/grpc-go`.
- Exact Xray-selected dependency: `google.golang.org/grpc v1.83.0`, proven by pinned Xray core `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5` `go.mod`.
- Exact grpc-go tag/commit: `v1.83.0` -> `4c226daff88f54441d70f710815e07b81fb162b2`.
- Complete recursive source-tree reference: `https://api.github.com/repos/grpc/grpc-go/git/trees/4c226daff88f54441d70f710815e07b81fb162b2?recursive=1`.
- grpc-go license at the selected commit: Apache-2.0, `LICENSE`; `NOTICE.txt` is also present and redistribution obligations must be preserved.
- Parent integration: Xray core MPL-2.0. The selected Xray config exposes `grpcSettings` with `authority`, `serviceName`, `multiMode`, idle/health-check timeouts, `permit_without_stream`, initial window size and user agent. Xray source currently emits a non-removal deprecation warning recommending XHTTP stream-up H2 instead of gRPC transport; this is a product-support decision signal, not evidence that generic gRPC is obsolete.
- Completed entry 086 is the repository HTTP/2 evidence layer. gRPC adds RPC/channel/message semantics over HTTP/2; TLS/REALITY or other security is a separate layer.

## Architecture boundary

gRPC is a general RPC framework, not a VPN, proxy security protocol or encryption primitive. A service definition normally uses Protocol Buffers; generated client/server stubs invoke unary, client-streaming, server-streaming or bidirectional-streaming RPCs. Channels carry RPCs; in the normal transport model RPCs map onto HTTP/2 streams and messages onto framed HTTP/2 DATA. Metadata may carry authentication/application context and therefore must be treated as sensitive. Deadlines, cancellation, status/trailers, keepalive and flow control are lifecycle concerns. Transport security and peer identity come from the selected credentials/TLS/parent security layer, not from gRPC framing itself.

## 20-gate reconciliation

| # | Gate | Result | Evidence / entry-specific decision |
|---:|---|---|---|
|1|Top implementations|PASS|grpc-go is the exact implementation selected by pinned Xray; canonical gRPC also maintains C/C++, Java, Kotlin, Python, Node, C#, Objective-C and other language ecosystems. For PVNetwork/Xray this entry is anchored to grpc-go, not an arbitrary cross-language port.|
|2|Canonical sources pinned|PASS|grpc-go `v1.83.0` / `4c226daff88f54441d70f710815e07b81fb162b2`; Xray `7d214f8b094f75322fa3990f8aadad1c912f24f5`; official grpc.io concepts documentation; completed HTTP/2 entry 086.|
|3|Licenses reviewed|PASS|grpc-go selected tree is Apache-2.0 with LICENSE and NOTICE; commercial reuse is permitted subject to Apache notice/license/modified-file obligations. Xray remains MPL-2.0 separately. Branding/trademark rights are not granted merely by the code license.|
|4|Complete source-tree reference|PASS|Recursive grpc-go tree URL pinned above. Important areas include `internal/transport`, credentials, resolver/balancer, health, keepalive, metadata, stats/channelz, xDS, examples, interop, benchmark and extensive tests plus `.github/workflows`. Xray's own complete pinned tree is already captured by the Xray family dossier.|
|5|Languages/build systems|PASS|Selected grpc-go is Go modules; Xray is Go modules and directly requires grpc-go v1.83.0 plus protobuf. Generated service code is normally produced from `.proto` definitions using protobuf/gRPC tooling; PVNetwork must not confuse generated application stubs with the transport adapter itself.|
|6|Architecture mapped|PASS|Service definition/stub -> gRPC channel/RPC/message/metadata/status -> grpc-go client/server transport -> HTTP/2 streams/frames -> TCP; optional TLS/credentials is a separate security boundary. Resolver, balancer, keepalive, flow control and observability are framework subsystems.|
|7|Core/engine integration|PASS|For the current Xray family, use Xray's own gRPC transport integration and its exact grpc-go dependency rather than embedding a second independently-versioned grpc-go transport. Xray owns startup/shutdown/config/error mapping. Generic non-Xray RPC features are not automatically exposed as VPN features.|
|8|UI/menu map|PASS/N-A|gRPC itself has no consumer VPN UI. Parent profile editors may expose only source-backed transport fields: authority, service name, multi-mode and health/idle/window/user-agent controls where the chosen core supports them. Security fields belong to TLS/REALITY sections. Do not create a standalone “gRPC VPN” product card.|
|9|Config/import/export|PASS|Generic gRPC uses service definitions and channel/server options; Xray's entry-specific JSON boundary is `network: grpc` plus `grpcSettings`. Parent profile/share schemas own serialization. No canonical standalone gRPC VPN URI/QR/subscription format exists.|
|10|Persistence/secrets|PASS|Service/authority/transport preferences are non-secret profile state. Metadata authorization tokens and TLS/REALITY credentials are sensitive and must use the parent secure-storage policy. Stream/channel/flow-control state is runtime-only. Logs/exports must redact metadata credentials.|
|11|Platform integration|PASS for research|grpc-go targets Go-supported desktop/server platforms; canonical gRPC has language/platform implementations including mobile ecosystems. In PVNetwork, OS tunnel/VPN integration remains owned by the parent core/platform adapter, not gRPC. Android TV/iOS VPN entitlements are therefore not hidden gRPC research gates.|
|12|Logs/diagnostics|PASS|Preserve distinct diagnostics for DNS/resolver, TCP, TLS/credentials, HTTP/2 transport, channel connectivity, keepalive/health, RPC method/status/deadline and parent Xray errors. grpc-go includes channelz/stats/logging facilities. Metadata and payloads require redaction.|
|13|Assets/screenshots|PASS/N-A|grpc-go is a framework/library and has no reusable consumer VPN screen set. Official grpc.io diagrams/docs are reference assets only; do not copy project branding/assets without separate rights review. Parent client UI evidence remains in the Xray/client dossiers.|
|14|Fork ecosystem|PASS|The selected implementation is canonical grpc-go, not a fork. Other official language implementations are interoperability/architecture references rather than drop-in replacements for Xray's Go dependency. PVNetwork should not swap implementations solely for feature parity without parent-engine regression review.|
|15|Issues/PRs/releases/advisories|PASS|grpc-go v1.83.0 release includes security hardening for HTTP/2 frame flooding/resource exhaustion and xDS/RBAC/parser issues plus malformed ALTS-frame validation. Recent 1.79.3 also hardened malformed `:path` authorization handling. Xray has historical gRPC transport configuration/service-name failure reports, reinforcing validation and regression tests.|
|16|Official docs/forums|PASS|Primary evidence is grpc.io concepts/introduction/docs plus canonical grpc-go source/releases. Official docs define unary/server-stream/client-stream/bidi RPCs, metadata, channels, deadlines/cancellation and status lifecycle. Community material is secondary.|
|17|Tests/CI|PASS|Pinned grpc-go tree contains extensive package/unit/interop/benchmark tests and GitHub workflows including testing, PR validation, coverage and CodeQL. Parent Xray integration must still have adapter/profile regression tests; real network/device tests are later certification evidence.|
|18|Store/privacy/security|PASS|gRPC is not a Store entitlement by itself. Security depends on credentials/TLS and application authorization. Metadata can contain authentication data; diagnostics must redact it. HTTP/2 resource limits, malformed path handling, compression/message limits, keepalive abuse and dependency updates are security/reliability concerns. Apache-2.0/NOTICE obligations apply if redistributed.|
|19|PVNetwork reuse decision|PASS|**REUSE THROUGH PARENT ENGINE / REFERENCE grpc-go DIRECTLY / NO CUSTOM gRPC OR HTTP/2 IMPLEMENTATION.** Keep Xray's grpc-go version coupled to the pinned engine. Because Xray now warns that its gRPC transport has unnecessary costs and points toward XHTTP H2, preserve gRPC for compatibility rather than making it the preferred new-profile transport without current performance/compatibility evidence.|
|20|Uncertainties explicitly listed|PASS|Future parent-engine deprecation/removal policy, XHTTP migration equivalence, proxy/CDN interoperability, keepalive tuning, metadata/header limits, mobile network switching, performance and server/device interoperability remain V2/implementation/certification work. None is silently promoted to a hidden V1 runtime gate.|

## Entry-specific product lessons

1. Model gRPC as a **transport/RPC capability of a parent engine**, not a VPN protocol or cryptographic security layer.
2. Keep `serviceName`/authority and health/idle/window settings versioned with the selected core schema; do not assume fields from another gRPC implementation map losslessly.
3. Keep TLS/REALITY configuration and credentials outside the gRPC transport object in the canonical PVNetwork profile model.
4. Treat authorization metadata as secret material in logs, diagnostics and profile export.
5. Track Xray's gRPC-to-XHTTP guidance during implementation; compatibility support and preferred transport are separate product decisions.
6. Regression-test resource exhaustion limits, malformed paths, reconnect/keepalive and parent-core upgrades before shipping, but do not mislabel those future runtime receipts as research completion.

## Final V1 decision

All exact 20 V1 research gates are evidence-backed or correctly application/parent-layer N/A bounded. Entry 088 qualifies for **`COMPLETE-RESEARCH-v1`**. This is research completion only; it is not an implementation, interoperability, device, Store or production certification claim.
