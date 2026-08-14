# 012 — PPTP — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: legacy/insecure VPN compatibility protocol.

Research decision:

**`LEGACY / INSECURE / OPTIONAL COMPATIBILITY ONLY`**

PVNetwork policy:

- never recommend PPTP as a modern secure VPN;
- never make it an automatic fallback;
- hide/disable by default unless a deliberate legacy-compatibility option/build enables it;
- show a prominent security/legacy warning;
- do not implement PPTP cryptography/protocol stack from scratch;
- do not promise support on platforms that removed/deprecated the stack.

If future customer demand justifies implementation, first pin and audit an actual maintained/historical client/backend and exact OS support package. Prefer existing OS/components rather than adding new high-risk legacy code.

Shared evidence:

- `research/upstreams/classic-tunnels-family/`

Later v2 must add exact historical/current source implementations, platform install matrix, cryptographic weakness/security history, GRE/control/data path, ports/handshake and server/client menu/install evidence.
