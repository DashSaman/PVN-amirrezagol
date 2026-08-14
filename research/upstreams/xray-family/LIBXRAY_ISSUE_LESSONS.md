# libXray — Issue / Lifecycle / Reliability Lessons

Research date: 2026-08-14

State: `IN-RESEARCH`; issue-derived engineering evidence only. Open issue reports are not treated as universally proven bugs unless source/fix/release evidence corroborates them.

Pinned wrapper baseline:

`XTLS/libXray@d0ab60ae4dd91cf119c878152d12103e6f84b78a`

## Evidence rule

For each issue distinguish:

- closed with fix/completion evidence;
- closed discussion/configuration issue;
- open reproducible report;
- open unverified report;
- wrapper/core version-coupling request.

PVNetwork converts these into regression requirements rather than copying issue claims into marketing/security conclusions.

## Issue #127 — metrics panic / process-global runtime state

Status reviewed: **closed/completed** on 2026-06-30.

Reported environment included libXray on Windows with a 2026 Xray-core build. Enabling the metrics/statistics configuration triggered a Go `expvar` duplicate-name panic (`Reuse of exported var name: stats`).

### Engineering class

This aligns with the broader process-global-state warning already documented by libXray: optional Xray runtime subsystems can share process-wide registrations/state and may not behave like isolated independent objects.

### PVNetwork requirements

- do not enable optional metrics merely to drive UI counters unless needed;
- test start/stop/restart of metrics/stats services repeatedly in the same process;
- test temporary config validation/ping and production session interactions;
- isolate independent engine experiments in a separate process where process-wide state cannot be safely reset;
- process panic/crash must be contained from the main product UI where architecture permits;
- crash recovery must recreate canonical product state rather than trusting partially retained wrapper globals.

## Issue #104 — possible long-running memory growth on iOS

Status reviewed: **open**.

The report describes memory increasing over long sessions on iOS clients using Xray-core/libXray with routing policies, eventually causing app termination in the reporter's environment.

### Evidence classification

`OPEN / UNVERIFIED AS A UNIVERSAL LEAK`.

Do not record this as a proven libXray/Xray-wide memory leak without upstream diagnosis/fix/reproduction evidence.

### PVNetwork test requirement

Regardless of root cause, this is a valid long-duration test class:

- iOS real-device soak tests lasting hours;
- route-policy changes during active session;
- network handover during soak;
- memory baseline/peak/growth-rate capture;
- background/foreground transitions;
- repeated reconnect without app restart;
- compare wrapper and process-isolated architectures where possible.

A mobile VPN client must not be certified only from short connection tests.

## Issue #118 — iOS `testXray` input/contract ambiguity

Status reviewed: **open**.

The reporter attempted to validate an Xray configuration on a real iOS device and received a JSON parsing error after passing a file-derived/base64-style string through the wrapper API.

### Engineering class

Even if the ultimate cause is caller misuse rather than a wrapper defect, it exposes an important API-contract problem: loosely typed string parameters can make it unclear whether a method expects:

- raw JSON;
- a file path;
- a file URL;
- base64 text;
- share-link content.

### PVNetwork requirements

- typed product methods must not accept one ambiguous `String` for multiple semantic input types;
- distinguish `validateConfigJson`, `importFile`, `parseShareLink`, `validateCanonicalProfile` at the product layer;
- validate request schema before crossing FFI;
- return actionable error categories rather than raw parser fragments;
- run real-device iOS tests for all wrapper operations used by PVNetwork.

## Issue #123 — wrapper vs Xray-core version lag

Status reviewed: **closed/completed** in 2026.

The request asked for libXray to update its embedded/depended Xray-core because newer Xray versions exposed useful TUN capabilities.

### Engineering class

Wrapper and engine release cadence are separate. A wrapper can be healthy while lagging required core fixes/features, or can update the core faster than the product is ready to certify.

### PVNetwork requirements

Track at least two independent pins:

- libXray wrapper version/commit/API version;
- embedded Xray-core version/commit.

Upgrade approval must compare:

- wrapper API diff;
- underlying core diff;
- security advisory state;
- configuration/default changes;
- product capability changes;
- platform artifact changes;
- regression results.

Do not display only “Xray version” in diagnostics if the product actually uses a separate wrapper build.

## Shared lifecycle lessons

The current wrapper architecture and issue history reinforce these product rules:

### One owner per in-process engine state

A process-wide wrapper/core instance requires one explicit session owner. UI, background workers, ping tests and diagnostics must not all independently instantiate/reset core state.

### Measurement must not destabilize connection

Latency/config-test helpers are lower priority than the active tunnel. If they cannot be proven isolated, move them to a helper process or serialize them safely.

### Optional services are still production code

Metrics/stats/observability can crash or retain global state just like transport code. Each optional feature requires lifecycle tests.

### Mobile certification needs soak tests

Short “connected” checks cannot reveal memory growth, background transitions, network handover or process-state corruption.

### Wrapper input contract must be product-typed

PVNetwork should hide the generic JSON invocation envelope behind typed product services and validate semantics before native calls.

## Future regression suite derived from libXray

- repeated `Run -> Stop -> Run` without process restart;
- repeated config validation/ping while disconnected;
- attempted test/ping while connected;
- stats/metrics enable-disable cycles;
- active session plus UI statistics polling;
- wrapper/core mismatch detection;
- malformed/oversized invocation requests;
- wrong API version;
- native call cancellation/timeout;
- C ABI allocation/free leak test;
- Android process death and service recreation;
- iOS multi-hour soak and background/foreground;
- wrapper update without canonical-profile migration;
- crash recovery without stale product “connected” state.

## Remaining issue research

- inspect comments/fix commits for #127 to identify exact fixed component/version;
- inspect current open issues by Android/Apple/Windows/Linux category;
- map wrapper release tags to exact Xray-core dependency pins;
- determine whether #104 receives an upstream reproduction/fix;
- identify current concurrency/process-global-state tests in upstream CI;
- add exact memory/performance benchmarks once reproducible evidence exists.
