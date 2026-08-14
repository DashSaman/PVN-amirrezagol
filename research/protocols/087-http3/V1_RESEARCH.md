# 087 — HTTP/3 — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: HTTP application protocol carried over QUIC; not a standalone VPN protocol.

Decision: **`MATURE HTTP3+QUIC STACK / NO CUSTOM IMPLEMENTATION`**.

PVNetwork must record exact HTTP3/QUIC/TLS library versions per engine and keep application-protocol semantics distinct from the underlying QUIC transport.

Shared evidence: `research/upstreams/transport-security-family/`.

Later v2 adds standards, handshake/data-flow relationship, selected libraries, implementation matrices and security/performance evidence.