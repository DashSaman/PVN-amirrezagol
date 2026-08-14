# 027 — SonicWall Global VPN / IPsec — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Decision: **`VENDOR IPSEC INTEROPERABILITY TARGET / REUSE IPSEC MODEL WHERE STANDARD SEMANTICS MATCH`**.

Use the shared IKE/IPsec capability model for standards-based portions, but generic IKE support is not proof of all SonicWall policies, modes or vendor extensions.

Official SonicWall client/gateway behavior remains the interoperability target until exact reusable compatibility is proven.

Shared evidence: `research/upstreams/vendor-enterprise-family/`.

Later v2 adds exact gateway/client versions, IPsec modes/extensions, cryptography/wire flow, installs and menus.