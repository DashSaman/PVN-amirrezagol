# 083 — QUIC — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: foundational modern transport with integrated TLS semantics, not a standalone VPN protocol.

Decision: **`MATURE QUIC LIBRARY OR ENGINE / NO CUSTOM QUIC STACK`**.

PVNetwork should use the QUIC implementation owned by the selected Hysteria/TUIC/HTTP3/other engine or a mature approved library. Record exact QUIC library/version because loss recovery, migration, TLS and performance behavior can differ.

Do not infer Hysteria2/TUIC compatibility merely because both use QUIC.

Shared evidence: `research/upstreams/transport-security-family/`.

Later v2 adds exact QUIC version/handshake/data flow, selected libraries, platform behavior and performance/security evidence.