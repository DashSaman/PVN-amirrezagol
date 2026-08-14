# 045 — AnyTLS — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: modern TLS-based proxy protocol/capability; distinct from generic TLS.

Decision: **`MODERN TLS-BASED PROXY TARGET / PREFER EXISTING APPROVED CORE WHEN PARITY EXISTS`**.

Primary reference: `anytls/anytls-go` plus implementations in modern multi-protocol cores.

PVNetwork canonical model must separate AnyTLS endpoint/auth/session fields from TLS trust/server-name/fingerprint policy.

Shared evidence: `research/upstreams/modern-proxy-family/`.

Later v2 must add server/client implementations, exact security/handshake/data flow, installation matrices and complete menus.
