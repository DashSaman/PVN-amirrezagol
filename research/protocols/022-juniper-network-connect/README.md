# 022 — Juniper Network Connect

Status: `IN-RESEARCH`; not implemented by PVNetwork.

Shared research:
- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`

OpenConnect is the current shared compatibility candidate. This legacy Juniper family remains distinct from Pulse/Ivanti because authentication flow, posture behavior and protocol capabilities differ.

Current research highlights browser-like authentication complexity, possible host-check/TNCC requirements and legacy protocol limitations. PVNetwork must expose tested capability/limitations per appliance version rather than assuming basic tunnel compatibility equals complete enterprise compatibility.

Remaining gaps: version matrix, current issue/release mapping, frontend/browser/posture research, platform packaging, dependency/license review and final interoperability evidence.

Research completion is not implementation completion.