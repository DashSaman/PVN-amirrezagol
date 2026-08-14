# Xray-core — Runtime API / Control / Stats Ownership Map

Research date: 2026-08-14

State: `IN-RESEARCH`; architecture/security evidence only. This file is not a deployment guide and does not authorize exposing management APIs publicly.

Pinned source:

`XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`

## Commander role

Pinned `app/commander/commander.go` describes Commander as an Xray feature that provides gRPC methods to external clients.

Its configuration contains:

- a tag;
- a listen endpoint;
- a list of registered typed services.

The implementation can serve on a configured local/network listener or through Xray's internal outbound-listener pattern.

### PVNetwork security rule

Do **not** expose Xray Commander directly to a broad network interface as a product management API.

PVNetwork should own authentication/authorization and use either:

- a process-private/local IPC boundary;
- an embedded wrapper API;
- a tightly scoped local endpoint inaccessible to untrusted callers.

The product UI/backend should talk to a PVNetwork Core Adapter, not directly to arbitrary Xray gRPC services.

## Handler / proxyman control capabilities

Pinned `app/proxyman/command/command.go` exposes runtime handler-management concepts including:

- add/remove/list inbound handlers;
- add/remove/list outbound handlers;
- alter an inbound/outbound through typed operations;
- add/remove users for compatible inbound protocols;
- inspect inbound users/counts.

This is powerful mutable runtime state.

### PVNetwork consequence

Treat runtime mutation as an administrative capability, not as ordinary UI model access.

Product rules:

- canonical profiles remain the source of intended configuration;
- runtime handler mutation must be reconciled with product state or explicitly treated as ephemeral;
- never let background API mutation silently diverge the engine from the profile shown in UI;
- authorize every product-level mutable operation;
- log administrative product actions without secret values;
- prefer restart/regenerate when a safe public runtime mutation contract is unavailable.

## Stats service capabilities

Pinned `app/stats/command/command.go` exposes capabilities including:

- retrieve/reset named counters;
- query matching counters;
- online/user-oriented statistics;
- system/runtime memory and goroutine/uptime statistics.

Some statistics can include user identifiers and observed IP information depending on enabled upstream stats features/configuration.

### PVNetwork privacy rule

Do not expose every upstream stat by default.

Separate:

1. user-facing local connection statistics;
2. developer diagnostics;
3. server/operator analytics;
4. potentially privacy-sensitive per-user/per-IP telemetry.

For a consumer PVNetwork client, default UI should use only minimum local stats necessary for the user. Do not collect/send per-user browsing/connection metadata merely because the core can expose it.

## Router runtime control

Pinned `app/router/command/command.go` provides operations for categories such as:

- inspect/override balancer targets;
- add/remove/list routing rules;
- test route selection;
- routing-stat subscriptions when enabled.

### PVNetwork consequence

Routing is dynamic engine state and can materially change privacy/security guarantees.

Requirements:

- product-owned routing rules are canonical and versioned;
- runtime rule mutation is validated against product policy;
- UI must not report one routing mode while the engine holds another;
- kill-switch/full-tunnel claims must be tested at the platform route layer, not inferred from Xray router state;
- imported full Xray configs containing runtime/routing semantics must be marked lossy if PVNetwork cannot faithfully normalize them.

## Stats/UI sampling boundary

Do not route raw engine events directly into UI rendering.

Recommended product boundary:

`Xray stats/control -> Core Adapter -> normalized sampled product state -> UI`

Benefits:

- stable UI contract across different cores;
- rate limiting/aggregation;
- secret/privacy redaction;
- easier testing;
- no engine-specific labels leaking into the product domain.

## Local API exposure risk

The pinned Commander creates a gRPC server and can listen on a configured endpoint. The files reviewed here do not establish a product-level authentication layer around Commander itself.

Therefore PVNetwork's default assumption must be:

**Xray management/control endpoint is privileged and private.**

Before any deployment/embedding design uses a listening API endpoint, review:

- exact bind scope;
- OS file/socket permissions if local IPC;
- authentication/authorization layer;
- TLS/local transport requirements;
- process ownership;
- sandbox/Store rules;
- whether the API can mutate users/inbounds/outbounds/routes;
- data exposed by stats services.

## Product adapter capability groups

PVNetwork should normalize Xray control into explicit groups such as:

### Lifecycle

- start;
- stop;
- health/state;
- version;
- crash/restart status.

### Runtime observation

- aggregate upload/download;
- connection/session state;
- core memory/process health;
- selected routing result where needed for diagnostics.

### Runtime mutation

- only capabilities deliberately supported by product architecture;
- never pass arbitrary gRPC operations from UI/plugins to core.

### Diagnostics

- sanitized engine log stream;
- effective generated config with secrets removed;
- adapter/core/platform versions;
- route/DNS/TUN product state.

## Embedded wrapper comparison

`libXray` currently offers a narrower product-style invocation boundary than exposing Xray Commander to every platform application directly.

Provisional design comparison:

- **Mobile/Apple:** libXray-style embedded native boundary may be preferable if lifecycle tests pass.
- **Desktop:** subprocess plus private IPC/control may offer stronger crash isolation and replaceability.
- **Never assume one model wins everywhere.**

The final Core Adapter should hide whether the selected platform uses Commander, a wrapper C ABI, gomobile, subprocess lifecycle or another implementation.

## Concurrency/lifecycle issue to test

libXray upstream documentation notes Xray process-wide state and warns that temporary test/ping instances can affect an active managed instance.

Therefore product test helpers must not blindly create parallel instances in-process during an active tunnel.

PVNetwork must choose one of:

- serialized in-process operations;
- separate helper process for independent tests;
- dedicated engine process per isolated operation where platform permits.

## Future regression tests

- unauthorized local process cannot invoke privileged management operations through the product boundary;
- engine runtime route mutation cannot silently diverge from canonical product policy;
- stats reset/query actions cannot corrupt UI counters unexpectedly;
- reconnect recreates intended canonical handlers/routing state;
- UI never displays stale state after engine process replacement;
- stats sampling under high load does not block data path;
- support bundle excludes sensitive user/IP/account information unless explicitly required and consented;
- local IPC/listener permissions remain correct after upgrade/reinstall;
- temporary ping/test tasks do not disturb an active session.

## Remaining gaps

- complete list of current Commander services and protobuf methods;
- exact API configuration surface and current official docs;
- exposure/authentication recommendations from upstream documentation;
- commander/proxyman/stats/router issue history;
- process-vs-libXray resource/crash-isolation comparison;
- mobile/Store feasibility of any management-listener approach;
- product Core Adapter API design approval.
