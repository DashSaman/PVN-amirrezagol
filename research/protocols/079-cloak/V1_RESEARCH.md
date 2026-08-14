# 079 — Cloak — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: optional obfuscation/transport wrapper that can carry another service; not a standalone generic VPN protocol.

Primary reference: `cbeuw/Cloak`.

Decision: **`OPTIONAL WRAPPER / COMPOSITION+LICENSE REVIEW REQUIRED`**.

PVNetwork canonical model must keep Cloak outer-layer parameters and the inner VPN/proxy profile separate. Do not flatten the chain into arbitrary engine commands or infer the wrapper's license applies to the inner service.

Shared evidence: `research/upstreams/transport-security-family/`.

Later v2 adds exact source/license/version, server/client install projects, cryptography/wire flow, menus and deployment topology.