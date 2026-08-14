# 069 — VXLAN — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **069 — VXLAN**

Decision: **`COMPLETE-RESEARCH-v1 / L2-OVER-UDP OVERLAY / NOT ENCRYPTED / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared evidence:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`
- `research/upstreams/linux-tunnels-family/V1_LEGAL_CONFIG_MAINTENANCE_AUDIT.md`

## Identity / source

- RFC 7348 defines VXLAN.
- Linux implementation pin: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`, `drivers/net/vxlan/vxlan_core.c`.
- iproute2 control pin: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `ip/iplink_vxlan.c`.
- Reviewed Linux source distinguishes IANA destination UDP port **4789** from Linux's historical module default **8472** for early-adopter compatibility; exact deployed port is configuration, not a universal constant.

VXLAN carries Layer-2 frames over UDP/IP using a VNI. VNI is segmentation metadata, **not cryptographic protection**.

## Product model

Typed fields include VNI, local/remote/group endpoints, physical device, UDP destination/source-port range, learning/FDB behavior, TTL/TOS/DF/flow-label where applicable, checksum/offload/metadata options, MTU, bridge/VRF/namespace association and route/multicast state. FDB and neighbor learning are first-class operational/security state.

Consumer GUI is N/A; expose only under advanced overlay/datacenter infrastructure. There is no canonical VXLAN consumer subscription URI/QR format.

## 20-gate reconciliation

|#|Gate|Result|Evidence/conclusion|
|---:|---|---|---|
|1|Top implementations|PASS|Linux kernel/iproute2 are the selected primary implementation/control references; network-switch/cloud implementations are independently pinned interoperability targets when selected.|
|2|Sources pinned|PASS|RFC7348 + exact Linux `ad8d485e...` and iproute2 `da2ccdf...` source-analysis pins; stable/pre-release tag state is explicitly separated.|
|3|Licenses|PASS|Linux `GPL-2.0 WITH Linux-syscall-note`, iproute2 GPLv2, file-level SPDX and commercial/distribution/modification/source/notice/network-use/linking/API/trademark/Store boundaries are explicitly audited; source reuse is reference-only by default.|
|4|Source tree|PASS|Pinned complete recursive Linux/iproute2 tree manifests cover `drivers/net/vxlan/`, `iplink_vxlan.c`, networking support, build/tests/packaging/docs/platform boundaries.|
|5|Languages/build|PASS|Relevant kernel/iproute2 VXLAN code is C; Kbuild/Kconfig/iproute2 build, test and package boundaries are mapped.|
|6|Architecture|PASS|Inner Ethernet frame -> VXLAN/VNI -> UDP/IP underlay -> remote VTEP -> bridge/FDB delivery; kernel data plane, bridge/FDB state and userspace control plane remain distinct.|
|7|Engine integration|PASS|Use native netlink/kernel/network-OS APIs; no custom VXLAN core or cryptographic implementation is required. Capability/offload differences are versioned.|
|8|UI/menu|PASS/N-A|No canonical consumer UI; admin overlay/peer/VNI/FDB state is bounded to advanced infrastructure UX.|
|9|Config/import|PASS|VNI/endpoints/group/device/port/learning/FDB/MTU fields are mapped. No standard VXLAN URI/QR exists; runtime FDB/kernel command output is not treated as a portable profile.|
|10|Persistence/secrets|PASS|No VXLAN cryptographic secret exists; VNI/endpoints/FDB are topology state, potentially sensitive but non-secret. Persistent desired config and learned/runtime state are separate.|
|11|Platforms|PASS for research|Linux/network infrastructure is the selected primary role; vendor/downstream implementations require their own version pin; mobile consumer role N/A absent a concrete native use case.|
|12|Logs/diagnostics|PASS|Underlay UDP, VTEP/FDB/neighbor, multicast, bridge, MTU/fragmentation, route/firewall/offload, namespace/device and cleanup failures are separated.|
|13|Assets|PASS/N-A|No canonical consumer application/store asset surface applies.|
|14|Alternatives|PASS|GRE/GRETAP, Geneve and VXLAN-over-IPsec are distinct technologies/compositions. No unsupported VXLAN-specific fork is promoted; downstream kernels/vendor NOSes are separately pinned targets.|
|15|Issues/releases|PASS|Canonical VXLAN path history was reviewed. Pinned source contains `b3797168...` (2026-08-11 ageing-timer UAF fix, stable-marked), `b9553558...` and `26bb2dd0...` (2026 transmit/network-header pull fixes). iproute2 path/release maintenance boundaries are recorded in shared evidence.|
|16|Docs/forums|PASS|RFC7348, pinned kernel source/Documentation, iproute2 source/man/development/security docs and accepted upstream maintenance discussions are primary; community tutorials are secondary.|
|17|Tests/CI|PASS|Kernel networking selftests/review and iproute2 `testsuite/` are inventoried; PVNetwork topology/device/offload/vendor tests remain later acceptance evidence, not hidden V1 gates.|
|18|Store/privacy/security|PASS|Raw VXLAN is unencrypted; VNI is not a security boundary; FDB/learning/topology manipulation can redirect/leak traffic; consumer Store role N/A.|
|19|Reuse decision|PASS|Use native infrastructure APIs only when a VXLAN use case exists; kernel/iproute2 source remains reference/API evidence and raw VXLAN must not be marketed as secure VPN.|
|20|Uncertainties|PASS|Multicast versus unicast control plane, EVPN/vendor/downstream interop, hardware offload, exact UDP port/MTU matrix and V2 installer/UI/wire/topology evidence remain explicitly later.|

## Final V1 decision

All 20 gates are evidence-backed or correctly infrastructure-N/A bounded. Entry 069 qualifies for **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
