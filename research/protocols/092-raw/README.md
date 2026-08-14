# 092 — RAW / TCP transport label

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **Xray transport label / foundational TCP transport capability**, not a standalone VPN protocol.

Shared evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`.

Pinned current configuration maps RAW/TCP naming to the TCP transport implementation and retains raw/tcp aliases/settings.

Research decision:

`FOUNDATIONAL TRANSPORT TARGET / CANONICAL ALIAS NORMALIZATION REQUIRED`.

PVNetwork should use one unambiguous internal transport identity while preserving the original imported alias/source metadata for round-trip/migration diagnostics.

Required support evidence:

- selected core/version semantics;
- header/settings behavior;
- interaction with TLS/REALITY/flow;
- protocol compatibility;
- IPv4/IPv6/routing behavior;
- import/export alias round trip;
- client/server interoperability.

Later `COMPLETE-REFERENCE-v2` must add technical TCP/data-path/handshake relationship, server/client install/deployment references, full configuration/menu maps and deployment topologies.
