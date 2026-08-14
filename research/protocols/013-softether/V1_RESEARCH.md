# 013 — SoftEther VPN Protocol — v1 Research Decision

Status: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

Classification: native SoftEther VPN client/server protocol family.

Primary upstream: `SoftEtherVPN/SoftEtherVPN`.

Root license reviewed: Apache-2.0.

Shared evidence:

- `research/upstreams/softether-family/SOURCE_ARCHITECTURE.md`
- `PROTOCOL_CAPABILITIES.md`
- `CLIENT_SERVER_CONFIG_UI.md`
- `DEPENDENCIES_TESTS_SECURITY.md`
- `SUPPORT_REUSE_DECISIONS.md`

Research decision:

**`UNIQUE NATIVE VPN TARGET / SOFTETHER-UPSTREAM PRIMARY CANDIDATE`**

PVNetwork should evaluate the smallest reusable native SoftEther client/core boundary behind a product-owned SoftEther Adapter. Do not reskin the complete existing client manager by default.

Keep separate:

- canonical PVNetwork client profile;
- protected credentials/certificates/private keys;
- generated native SoftEther runtime configuration;
- platform virtual-adapter/service lifecycle;
- server administration/Virtual Hub models;
- transient session state.

SoftEther server compatibility with OpenVPN/SSTP/L2TP/IPsec does not make SoftEther the preferred client engine for those separate protocols.

Residual gaps:

- exact final source/tag/API boundary;
- exact dependency/SBOM/security matrix;
- mobile native-client feasibility;
- full client/server manager menus/screenshots;
- real-device/E2E/Store proof;
- server installers/crypto/wire flow belong to mandatory v2.
