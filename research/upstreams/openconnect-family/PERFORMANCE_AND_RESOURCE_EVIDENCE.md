# OpenConnect Family — Performance / Resource Evidence

Research date: 2026-08-14

State: `IN-RESEARCH`; evidence and test requirements only. No performance claim for PVNetwork.

## Evidence principle

Do not reduce performance to one throughput number. For an enterprise client the useful dimensions include:

- connection/authentication latency;
- steady-state CPU and memory;
- preferred data transport vs fallback behavior;
- reconnect cost;
- packet/framing behavior;
- MTU/path effects;
- platform network-service overhead;
- browser/SSO overhead during authentication;
- logs/debug mode overhead.

Exact benchmarks must use a pinned OpenConnect build, crypto backend, vendor/server version and platform.

## v9.21 high-CPU regression lesson

The official v9.21 changelog fixes an infinite-loop/high-CPU condition in text-buffer handling that became easier to trigger after wider use of that helper in v9.20.

PVNetwork regression requirement:

- monitor CPU during failed authentication/config parsing as well as connected state;
- set bounded timeout/cancellation expectations for adapter operations;
- core upgrades need negative/error-path resource tests, not only successful throughput tests.

## TLS/UDP transport implications

OpenConnect protocol families can use a TLS-based reliable channel and, for compatible families, a preferred UDP-style data transport such as DTLS/ESP or vendor-specific equivalents. Fallback to reliable transport can materially change latency, throughput and head-of-line blocking behavior.

PVNetwork performance reports must therefore record:

- actual negotiated data transport;
- whether a preferred UDP path was available;
- whether traffic fell back to TLS/reliable transport;
- reconnect/fallback transitions.

Do not compare two runs without recording the transport actually used.

## Framing/reassembly performance lessons

Upstream test/fix history for Array, Pulse and PPP-style modes contains cases where packets can be split across records or multiple packets can arrive in one record/frame.

PVNetwork should avoid adding unnecessary copies at its adapter boundary and test:

- split/coalesced frame conversion;
- cancellation/reconnect cleanup of partial state;
- high packet-rate UI/statistics updates without blocking data-path callbacks.

## UI/statistics isolation

The product UI must not consume raw per-packet events. The adapter should expose sampled/aggregated statistics so rendering, localization, charts and logs cannot throttle the network engine.

Regression tests should cover:

- high-throughput sessions with the main window open/closed;
- logs panel open/closed;
- tray-only operation;
- reconnect while statistics are being sampled.

## Debug logging overhead

Verbose upstream logging can be expensive and can expose secrets. PVNetwork production defaults should use bounded informational logging, with sanitized diagnostic escalation only when requested.

Measure CPU/disk impact for any extended diagnostic mode before shipping it.

## Platform resource matrix required later

For each certified platform/build record at minimum:

- idle disconnected memory/CPU;
- connected idle memory/CPU;
- connection/authentication peak;
- representative throughput CPU;
- reconnect peak;
- background/battery impact on mobile;
- process/helper count;
- log growth rate;
- wakeups/background activity where measurable.

## Benchmark reproducibility fields

Every future performance result must state:

- PVNetwork build;
- OpenConnect version/commit;
- TLS/crypto backend;
- client OS/architecture;
- server/vendor/version;
- authentication mode;
- negotiated transport;
- network path/latency/loss conditions;
- MTU;
- test duration/tool;
- power mode/device state when mobile.

## Current conclusion

OpenConnect is mature enough to remain a reuse candidate, but no repository evidence supports a blanket claim such as "fastest" or a fixed throughput expectation. Performance is protocol-mode, server, transport, crypto-backend and platform dependent.

## Remaining gaps

- authoritative current benchmark data tied to v9.21 where available;
- comparative CPU/memory evidence for GnuTLS vs OpenSSL builds;
- current per-vendor transport/fallback performance evidence;
- Android/mobile power evidence;
- Apple feasibility/performance evidence;
- PVNetwork adapter overhead after implementation exists.
