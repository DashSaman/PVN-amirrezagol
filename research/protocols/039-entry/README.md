# 039 — Trojan

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: application proxy protocol supported by multiple modern cores.

Primary Xray evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5` (MPL-2.0).

Research decision:

`MULTI-CORE COMPATIBILITY TARGET / CORE-SELECTION BENCHMARK REQUIRED`.

Xray is a candidate engine, not automatically PVNetwork's default Trojan engine. Before engine selection compare approved alternatives for protocol/transport coverage, TLS behavior, platform integration, performance/resource cost, dependency/license burden and maintenance/regression history.

PVNetwork canonical Trojan profile should remain engine-neutral wherever semantics permit.

Later `COMPLETE-REFERENCE-v2` must add server projects/installers, server/client OS install matrices, complete menus, cryptography, wire/data-flow and deployment files required by `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.
