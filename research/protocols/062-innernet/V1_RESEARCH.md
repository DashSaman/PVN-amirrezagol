# 062 — innernet — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: WireGuard-based private-network manager/orchestration ecosystem rather than a new independent data-plane protocol.

Decision: **`WIREGUARD ORCHESTRATION REFERENCE / OPTIONAL ECOSYSTEM INTEGRATION`**.

Basic WireGuard support does not require innernet. Consider support only for joining/administering innernet-managed networks after current source/license/control-plane semantics are pinned.

Do not duplicate innernet control-plane logic inside PVNetwork without a concrete feature requirement.

Shared evidence: `research/upstreams/mesh-overlay-family/`.

Later v2 adds exact source/server/control-plane install, client/admin menus, WireGuard/control flow and deployment topology evidence.
