# 013 — SoftEther VPN Protocol — v1 Research Decision

Status: **`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`**.

Classification: native SoftEther VPN client/server protocol family.

Primary upstream: `SoftEtherVPN/SoftEtherVPN`.

Root license reviewed: Apache-2.0.

Formal gate reconciliation:

- `research/protocols/013-softether/V1_GATE_RECONCILIATION.md`

Shared evidence:

- `research/upstreams/softether-family/SOURCE_ARCHITECTURE.md`
- `research/upstreams/softether-family/SOURCE_CLIENT_ETHERIP_IPSEC_EVIDENCE.md`
- `research/upstreams/softether-family/CLIENT_CONFIG_LICENSE_MODEL.md`
- `research/upstreams/softether-family/PROTOCOL_CAPABILITIES.md`
- `research/upstreams/softether-family/CLIENT_SERVER_CONFIG_UI.md`
- `research/upstreams/softether-family/DEPENDENCIES_TESTS_SECURITY.md`
- `research/upstreams/softether-family/RELEASE_SECURITY_ISSUE_REVIEW.md`
- `research/upstreams/softether-family/SUPPORT_REUSE_DECISIONS.md`
- incidental detailed reference evidence under `research/upstreams/classic-tunnels-family/softether-protocol-reference-v2/`

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

Research-complete uncertainties retained for later implementation/release work:

- no production-safe Developer Edition release is blessed while the reviewed high-severity advisory remains unresolved;
- exact final implementation source/tag/API boundary is not selected;
- exact dependency/SBOM/NOTICE bundle is not generated until a build is selected;
- native mobile feasibility is not claimed;
- selected-release screenshots/pixel inventory is not frozen;
- real-device/E2E/Store/performance certification remains separate evidence.

These are not hidden original-v1 research gates. The 20-item original research template is reconciled in `V1_GATE_RECONCILIATION.md`.
