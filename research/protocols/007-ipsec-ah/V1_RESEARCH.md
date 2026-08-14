# 007 — IPsec AH — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: IPsec Authentication Header data-plane protocol.

Research decision:

**`ADVANCED / LOW REMOTE-ACCESS PRIORITY / NON-ENCRYPTING INTEGRITY MODE`**

AH provides integrity/authentication but not payload encryption. PVNetwork must never market AH as an encrypted VPN and must never silently use AH as fallback from ESP.

Support, if ever implemented, requires exact OS/kernel/backend/server evidence and belongs primarily to advanced/IPsec completeness scenarios.

Shared evidence: `research/upstreams/strongswan-family/`.

Later v2 must document AH wire format, integrity semantics, NAT/deployment constraints, implementations/install matrices and technical data path.
