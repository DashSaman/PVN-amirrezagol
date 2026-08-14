# 077 — TLS — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: foundational security protocol/layer, not a standalone VPN.

Decision: **`MATURE PLATFORM/ENGINE TLS STACK / NEVER CUSTOM CRYPTO`**.

PVNetwork must record exact TLS backend/version, certificate validation, server-name/SNI/ALPN and trust policy per engine/platform. Obfuscation/fingerprinting settings must not weaken certificate validation silently.

Shared evidence: `research/upstreams/transport-security-family/`.

Later v2 adds standards/versions, handshake/crypto details, library implementations and platform/security matrices.