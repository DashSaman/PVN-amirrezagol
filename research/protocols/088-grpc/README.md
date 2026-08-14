# 088 — gRPC

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **RPC/application transport technology**, not a standalone VPN protocol.

Shared Xray evidence: `research/upstreams/xray-family/`.

Current Xray-core research pin: `7d214f8b094f75322fa3990f8aadad1c912f24f5`.

Pinned source recognizes gRPC transport while current configuration guidance points toward newer XHTTP H2-style alternatives in some scenarios.

Research decision:

`SUPPORTED COMPATIBILITY TRANSPORT / DEPRECATION-GUIDANCE-AWARE`.

PVNetwork must:

- preserve imported gRPC profiles while supported by the selected core;
- keep service name/authority/security fields explicit;
- never silently rewrite gRPC to XHTTP;
- test exact server/client core versions and TLS certificate-validation behavior;
- include the 2026 Xray certificate-pinning advisory class in regression/security review because upstream GHSA specifically affects some gRPC cases.

PVNetwork should inherit gRPC from the selected core/runtime instead of implementing its own VPN-facing RPC stack.

Later `COMPLETE-REFERENCE-v2` must add standards/runtime sources, server/client deployment/install evidence, full UI/config menu fields, data/framing/handshake relationships and deployment topologies.
