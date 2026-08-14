# 069 — VXLAN — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **069 — VXLAN**

Decision: **`COMPLETE-RESEARCH-v1 / L2-OVER-UDP OVERLAY / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence: `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`.

## Identity / source

- RFC 7348 defines VXLAN.
- Current Linux implementation: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `drivers/net/vxlan/vxlan_core.c`.
- Current iproute2 control: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `ip/iplink_vxlan.c`.
- Linux reviewed source states IANA destination UDP port **4789** while Linux's historical module default is **8472** for early-adopter compatibility; exact deployed port is configuration, not a universal constant.

VXLAN carries Layer-2 frames over UDP/IP using a VNI. VNI is segmentation metadata, **not cryptographic protection**.

## Product model

Typed fields include VNI, local/remote/group endpoints, physical device, UDP destination/source-port range, learning/FDB behavior, TTL/TOS/DF/flow-label where applicable, checksum/offload/metadata options, MTU, bridge/VRF/namespace association and route/multicast state.

FDB and neighbor learning are first-class operational/security state. Consumer GUI is N/A; expose under advanced overlay/datacenter infrastructure only.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel/iproute2 current primary; network-switch/cloud implementations are later interop references.|
|2|Sources pinned|PASS|RFC7348 + exact current Linux/iproute2 pins.|
|3|Licenses|PASS|Kernel/iproute2 GPL source boundaries recorded.|
|4|Source tree|PASS|`vxlan_core.c` and `iplink_vxlan.c` plus shared network paths pinned.|
|5|Languages/build|PASS|Linux/iproute2 C/system tooling.|
|6|Architecture|PASS|Inner Ethernet frame -> VXLAN/VNI -> UDP/IP underlay -> remote VTEP -> bridge/FDB delivery.|
|7|Engine integration|PASS|Use native netlink/kernel/network-OS APIs; no custom VXLAN crypto/core.|
|8|UI/menu|PASS/N-A|No canonical consumer UI; admin overlay/peer/VNI/FDB state mapped.|
|9|Config/import|PASS|VNI/endpoints/group/device/port/learning/FDB/MTU options mapped; runtime FDB/kernel state not portable config.|
|10|Persistence/secrets|PASS|No VXLAN cryptographic secret; VNI/endpoint/FDB are topology state, potentially sensitive but non-secret.|
|11|Platforms|PASS for research|Linux/network infrastructure primary; mobile consumer role N/A absent concrete native use case.|
|12|Logs/diagnostics|PASS|Underlay UDP, VTEP/FDB/neighbor, multicast, bridge, MTU/fragmentation, route/firewall/offload failures separated.|
|13|Assets|PASS/N-A|No canonical consumer assets.|
|14|Alternatives|PASS|GRE/GRETAP, Geneve and VXLAN-over-IPsec are distinct technologies/compositions.|
|15|Issues/releases|PASS|Current Linux/iproute2 maintenance pinned; exact hardware/offload/vendor regressions later.|
|16|Docs/forums|PASS|RFC7348 + current Linux/iproute2 source/docs are primary.|
|17|Tests/CI|PASS|Kernel/network selftests/upstream review; PVNetwork topology/interoperability tests remain later.|
|18|Store/privacy/security|PASS|Raw VXLAN is unencrypted; VNI is not security boundary; FDB/learning/topology can redirect/leak traffic; consumer Store role N/A.|
|19|Reuse decision|PASS|Native infrastructure adapter only when needed; do not market raw VXLAN as secure VPN.|
|20|Uncertainties|PASS|Multicast vs unicast control plane, EVPN/vendor interop, hardware offload, exact UDP port/MTU matrix and V2 evidence remain later.|

## Final V1 decision

Entry 069 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
