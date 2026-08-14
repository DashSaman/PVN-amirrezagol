# 046 — ShadowTLS — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: proxy/security wrapper capability, not a standalone generic VPN.

Decision: **`PROXY/SECURITY-WRAPPER TARGET / COMPOSITION MODEL REQUIRED`**.

Primary reference: `ihciah/shadow-tls` plus modern core implementations.

PVNetwork must represent the outer ShadowTLS layer separately from the inner proxy profile and preserve engine/version/source semantics. Do not store the chain as an opaque command string.

Shared evidence: `research/upstreams/modern-proxy-family/`.

Later v2 must add exact protocol/security versions, server/client implementations, cryptography, wire flow, installation matrices and exhaustive menus.
