# 080 — TLS Fragmentation — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: advanced engine/transport behavior capability, not a standalone VPN protocol.

Decision: **`ENGINE FEATURE / NO DEDICATED ENGINE`**.

PVNetwork should expose fragmentation behavior only when the selected core/version implements it safely and the exact semantics are known. It must not be represented as a separate VPN account/profile type.

Keep it separate from certificate validation, TLS version and application protocol semantics.

Shared evidence: `research/upstreams/transport-security-family/`.

Later v2 adds exact implementation/version behavior, packet/handshake effects, compatibility/performance evidence and UI settings.