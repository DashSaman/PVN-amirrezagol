# 070 — VXLAN over IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **070 — VXLAN over IPsec**

Decision: **`COMPLETE-RESEARCH-v1 / L2-OVER-UDP + IPSEC COMPOSITION / NOT A DISTINCT WIRE STANDARD / NOT IMPLEMENTED / NOT CERTIFIED`**

Evidence reused without repeating completed research:

- completed entry 069 — VXLAN (`RFC 7348` + pinned Linux/iproute2 implementation evidence);
- completed entries 004–007 — IKEv2/IPsec, IKEv1/IPsec, ESP and AH;
- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`;
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`;
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`.

## Composition boundary

There is no separate canonical “VXLAN-over-IPsec” wire protocol established by this research. It is a composition:

1. an inner Ethernet frame is encapsulated in VXLAN/UDP/IP;
2. an IPsec policy/SA protects the intended VXLAN traffic on the underlay path;
3. the peer performs IPsec processing before VXLAN decapsulation/FDB/bridge delivery.

The selected VXLAN UDP destination port is configuration (RFC/IANA port 4789 is common; Linux also has historical 8472 behavior documented in entry 069). IPsec selectors/policies must therefore match the actual deployed endpoints/protocol/port/topology rather than an assumed universal constant.

A live VXLAN device plus an unrelated IPsec SA is **not** proof that VXLAN traffic is protected. Later implementation/certification must verify policy/packet behavior and absence of unintended cleartext fallback. That receipt is explicitly later and is not a hidden V1 research gate.

## Product model

Keep the layers independently typed:

- VXLAN: VNI, local/remote/group VTEPs, device, UDP port/range, FDB/learning, bridge/VRF/namespace, MTU and underlay routing;
- IPsec: IKE identity/authentication, credentials, proposals, selectors/policies, SAs/lifetimes and transform state;
- composition: which VXLAN traffic is expected to be protected, routing/order of operations, firewall/NAT behavior, overhead/MTU budget and failure/fallback policy.

VNI is segmentation metadata, not a cryptographic credential. IPsec credentials and runtime keying material remain owned by the completed IPsec layer.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top clients / implementations|PASS|Linux VXLAN/iproute2 plus the already completed maintained IPsec/IKE implementation evidence are the selected composition references. Vendor VTEP/IPsec implementations are separately pinned interop targets when chosen.|
|2|Repository/source identification|PASS|VXLAN is pinned through RFC7348, Linux `ad8d485e...` and iproute2 `da2ccdf...`; IPsec source/standards pins are inherited only from completed entries 004–007. No fictitious combined upstream is claimed.|
|3|Licensing / legal reuse|PASS|Linux/iproute2 GPL/syscall/source-copy/commercial/distribution/modification/notice/network-use/trademark/Store boundaries are fully recorded in shared audits; selected IPsec licenses remain separately authoritative. Composition does not erase either license boundary.|
|4|Source-tree review|PASS|Complete recursive Linux/iproute2 tree manifests cover VXLAN/control paths; completed IPsec dossiers cover IKE/XFRM/ESP. No separate combined source tree exists or is fabricated.|
|5|Language / build / packaging|PASS|Linux/iproute2 C/Kbuild/userspace build/test/package boundaries and the selected IPsec implementation toolchain are already mapped. Composition adds orchestration/configuration, not a new upstream build system.|
|6|Internal architecture / data flow|PASS|Inner Ethernet -> VXLAN/VNI -> UDP/IP -> matching XFRM/IPsec policy/ESP -> underlay, reversed at peer. VXLAN/FDB, route/firewall and IKE/SA/policy planes stay independent.|
|7|Engine integration points|PASS|Use native VXLAN/netlink plus approved IPsec/XFRM/IKE adapters. No custom cryptography or forked VXLAN core. Product must expose separate overlay and protection health/capabilities.|
|8|UI / settings map|PASS/N-A|Infrastructure/datacenter/site-to-site admin UX only; consumer VPN UI is N/A. If exposed, show VNI/VTEP state and IPsec protection state separately and visibly flag unprotected/fallback state.|
|9|Configuration / import / export / URI / QR|PASS|Typed VXLAN fields reference typed IPsec policy/credential objects. No canonical VXLAN-over-IPsec consumer URI/QR exists; raw `ip`, bridge/FDB or XFRM dumps are not portable profiles.|
|10|Persistence / secret handling|PASS|VXLAN VNI/endpoints/FDB state is non-cryptographic topology data; IPsec private keys/PSKs/cert credentials follow completed secure-storage rules. Runtime SA/key state is excluded from ordinary exports/support bundles.|
|11|Platform-specific implementation|PASS for research|Linux/network-infrastructure composition is mapped. Vendor/downstream kernels/NOSes need independent version pins; mobile consumer role is N/A absent a concrete native deployment.|
|12|Logs / diagnostics / failure mapping|PASS|Underlay UDP/VXLAN/VTEP/FDB/bridge/MTU failures are separated from IKE/auth/SA/XFRM-selector/ESP/replay/firewall/NAT failures. Diagnostic output must not leak keying material.|
|13|Assets / icons / localization|PASS/N-A|No canonical consumer asset/store/localization surface applies to this infrastructure composition.|
|14|Forks / alternatives|PASS|Geneve or raw VXLAN, GRE/IPIP-over-IPsec, VTI/XFRM route-based IPsec and vendor overlay-security architectures are distinct alternatives. Downstream kernels/vendor NOSes are not mislabeled as canonical forks.|
|15|Issues / PRs / releases / advisories|PASS|Entry 069/shared audit records current VXLAN fixes including the 2026 ageing-timer UAF and transmit header-pull fixes contained in the pinned Linux source; completed IPsec entries carry their own maintenance evidence. No claim is made that this eliminates downstream/runtime regressions.|
|16|Official docs / forums|PASS|RFC7348, pinned Linux/iproute2 source/docs and completed IPsec standards/dossiers are primary. Accepted kernel maintenance discussions are supporting upstream evidence; random tutorials are not elevated.|
|17|Tests / CI / quality evidence|PASS|Kernel networking/VXLAN selftest/review, iproute2 tests and selected IPsec upstream quality evidence are mapped. Later policy/packet/device/vendor tests must prove actual protected VXLAN operation but are implementation/certification evidence.|
|18|Store / privacy / security|PASS|Raw VXLAN is unencrypted and VNI is not a security boundary; security is supplied only when matching IPsec policy/SA protects the traffic. Topology/credentials are sensitive by layer; consumer Store role N/A.|
|19|Reuse / rewrite / hybrid decision|PASS|Compose native OS VXLAN with approved native/IPsec engines behind an infrastructure adapter. Do not copy/fork GPL tunnel code or implement new cryptography merely to create a protocol entry.|
|20|Open uncertainties / blockers|PASS|Exact selector/port choice, multicast/unicast/EVPN control plane, NAT/firewall, MTU/overhead, hardware offload, downstream/vendor interoperability and V2 installer/UI/wire/topology receipts remain explicitly later. No unresolved research fact blocks the V1 architectural decision.|

## Final V1 decision

All 20 COMPLETE-RESEARCH-v1 gates are independently reconciled through completed VXLAN/IPsec research and the hardened shared Linux tunnel evidence. Entry 070 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining **not implemented / not runtime-certified / not vendor-interoperability-certified**.
