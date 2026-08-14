# 056 — Tailscale — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: WireGuard-based mesh ecosystem with coordination, identity, policy, relay and naming layers.

Decision: **`INTEROPERATE OR OPTIONAL ECOSYSTEM MODULE / NOT REQUIRED FOR BASIC WIREGUARD`**.

Primary source: `tailscale/tailscale`; reviewed root license BSD-3-Clause.

PVNetwork should add Tailscale only for Tailscale-specific network/account/device semantics, not simply to obtain WireGuard transport.

Shared evidence: `research/upstreams/mesh-overlay-family/`.

Later v2 adds control-plane/server deployment, client install/menu matrix, cryptography/data path, relays/DNS and topology details.
