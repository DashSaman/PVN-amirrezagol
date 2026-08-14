# 075 — XTLS

Status: `IN-RESEARCH / NOT IMPLEMENTED`.

Classification: **legacy security-layer terminology/configuration family**, not a standalone VPN protocol.

Primary reference: Xray-core historical/current configuration behavior.

Shared evidence: `research/upstreams/xray-family/`.

Pinned Xray current-main research source marks legacy `xtls` security configuration as removed and directs current designs toward Vision flow with TLS/REALITY rather than a generic legacy XTLS security toggle.

Research decision:

`LEGACY TERMINOLOGY / DO NOT ADVERTISE OLD XTLS SECURITY MODE AS A CURRENT STANDALONE FEATURE`.

PVNetwork must:

- preserve legacy imported metadata for migration/audit;
- never silently rewrite old XTLS settings into a new semantic;
- use core-version-aware validation;
- expose current supported flow/security combinations instead of an obsolete generic toggle;
- keep historical documentation separate from current capability claims.

Later `COMPLETE-REFERENCE-v2` must document historical/current cryptographic/handshake relationships, migrations, server/client deployment sources and UI/menu behavior where applicable.
