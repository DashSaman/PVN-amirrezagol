# 057 — ZeroTier — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: distinct overlay-network ecosystem with its own data-plane/network identity/control concepts.

Decision: **`DISTINCT OVERLAY ENGINE / OPTIONAL INTEGRATION`**.

Do not translate ZeroTier networks into WireGuard profiles. If PVNetwork later supports ZeroTier, use a maintained ZeroTier component behind a dedicated Mesh Adapter and audit the exact current component/path license because project licensing has varied across history/components.

Shared evidence: `research/upstreams/mesh-overlay-family/`.

Later v2 adds exact source/license/version, controller/server deployment, client install/menu matrix, cryptography/wire flow and topology evidence.
