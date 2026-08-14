# 058 — NetBird — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: WireGuard-based mesh/control-plane ecosystem.

Decision: **`OPTIONAL ECOSYSTEM INTEGRATION / PATH-LEVEL LICENSE AUDIT REQUIRED`**.

Current research records a split-license repository model: substantial client/shared code is BSD-3-Clause while several management/control-plane/server areas are AGPLv3. Never assign one license to the whole repo.

PVNetwork should integrate NetBird only for NetBird-specific account/device/network/control-plane semantics, not for basic WireGuard connectivity.

Shared evidence: `research/upstreams/mesh-overlay-family/`.

Later v2 adds exact component pins, server/relay/management deployment, client/server menus, cryptography/data path and topology matrices.
