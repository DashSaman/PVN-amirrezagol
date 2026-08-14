# 044 — TUIC — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: modern QUIC-based proxy protocol.

Decision: **`MODERN QUIC PROXY TARGET / DIRECT-UPSTREAM VS EXISTING-CORE BENCHMARK REQUIRED`**.

Primary upstream reference: `EAimTY/tuic`.

PVNetwork must compare direct upstream integration with already-approved modern cores before adding another engine. Keep TUIC protocol/auth/session fields separate from generic QUIC/TLS settings.

Shared evidence: `research/upstreams/modern-proxy-family/`.

Later v2 must add server implementations/installers, exact cryptography/wire flow, ports/handshake, install matrices and exhaustive menus.
