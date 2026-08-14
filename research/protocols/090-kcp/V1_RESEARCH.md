# 090 — KCP — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: reliable-UDP transport/library family, not a standalone VPN protocol.

Primary reusable reference: `xtaci/kcp-go`.

Decision: **`TRANSPORT LIBRARY CAPABILITY / USE MATURE IMPLEMENTATION`**.

Do not confuse generic KCP with Xray mKCP framing/configuration. Only add direct KCP integration if a higher-level protocol actually requires it and an existing approved core cannot provide the needed behavior.

Shared evidence: `research/upstreams/transport-security-family/`.

Later v2 adds exact source/license/version, congestion/FEC/wire-flow behavior, implementation matrices and performance/security evidence.