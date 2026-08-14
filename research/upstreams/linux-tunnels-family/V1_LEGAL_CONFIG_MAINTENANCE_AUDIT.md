# Linux Tunnel / IPsec Composition Family — V1 Legal, Config, Fork and Maintenance Audit

Review date: 2026-08-14
Scope: reusable V1 evidence for entries 063–070. Protocol-specific RFC/architecture/security conclusions remain in each numbered reconciliation.

This supplement closes reusable gaps in gates 3, 9, 14, 15 and 16 of `research/PROTOCOL_RESEARCH_TEMPLATE.md` for the Linux kernel/iproute2 implementation candidates.

## 1. Exact license and reuse boundary

### Linux kernel

Reviewed source pin: `torvalds/linux@ad8d485e665829ecbf3c97b22ce251f8ff5f8037`.

Pinned root `COPYING` states:

- `GPL-2.0 WITH Linux-syscall-note`;
- GPL version 2 only is the root kernel license basis;
- the Linux syscall exception applies;
- additional licenses can apply and file-level SPDX/license rules must be checked.

Practical V1 legal conclusions for PVNetwork:

- **Commercial/internal use:** running the kernel or using its system-call/netlink interfaces is not prohibited by GPLv2. This is distinct from copying/linking kernel source into PVNetwork.
- **Redistribution:** if PVNetwork redistributes covered kernel binaries/source, the applicable GPLv2/source-offer and notice obligations must be satisfied for the exact distributed work.
- **Modification:** distributed derivative modifications to GPL-covered kernel source remain subject to the applicable GPL terms/file SPDX expression.
- **Source disclosure trigger:** distribution of covered object/executable work is the relevant GPLv2 trigger; ordinary remote network use is not an AGPL-style source-disclosure trigger.
- **Linking/API boundary:** the root license expressly includes `Linux-syscall-note`; PVNetwork's planned design is to use OS/netlink/syscall interfaces, not copy/link tunnel implementation files into the application. Any kernel module/direct source incorporation requires separate exact legal review.
- **Attribution/notices:** preserve applicable copyright/SPDX/license notices when redistributing covered source or binaries; do not strip warranty/license notices.
- **Copyright ownership:** the kernel is a multi-contributor work; there is no evidence basis to attribute all code to one copyright holder. File history/notices govern exact attribution.
- **Trademark:** GPL does not grant a trademark license. Do not imply Linux Foundation/kernel-project endorsement or certification; trademark review is separate if Linux marks are used in product branding.
- **App Store/distribution-store effect:** no consumer Store package is required for entries 063–070. If a future product bundles GPL-covered kernel code in a distribution channel, store terms must not add restrictions inconsistent with the applicable GPL obligations; legal review is required at that packaging boundary.

### iproute2

Reviewed source pin: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`.

The reviewed root `COPYING` contains GNU GPL version 2 terms; repository metadata reports `GPL-2.0`. Individual source files may use SPDX expressions and remain the exact authority for file-level reuse.

Practical V1 legal conclusions:

- **Commercial use:** GPLv2 permits use and distribution for a fee, subject to GPL obligations when covered copies/derivatives are distributed.
- **Redistribution/source:** distribution of GPL-covered object/executable forms requires the corresponding-source mechanism allowed by GPLv2 and preservation of applicable notices/license terms.
- **Modification:** distributed derivative modifications must satisfy GPLv2 conditions, including change notices and licensing of the covered derivative as required by GPLv2.
- **Linking/copying:** there is no Linux-syscall-note equivalent established here for copying/linking iproute2 application code. PVNetwork therefore classifies iproute2 source as **REFERENCE-ONLY** and should use structured netlink/native APIs instead of embedding or linking iproute2 code unless separately reviewed.
- **Network use:** GPLv2 has no AGPL network-interaction source-disclosure clause; network service use alone is not treated as redistribution evidence.
- **Attribution/notices:** preserve copyright/license/warranty notices for any distributed covered material.
- **Trademark:** no trademark permission is inferred from GPLv2 or the GitHub mirror.
- **Store effect:** consumer Store distribution is N/A for this infrastructure control tool; any future bundling still requires GPL/store-term compatibility review.

### Reuse decision

For entries 063–070 the research-safe architecture is:

- use Linux networking facilities through structured OS/netlink APIs;
- use selected maintained IPsec/IKE components from already completed entries 004–007 for protected compositions;
- treat kernel/iproute2 source as behavioral/API/reference evidence;
- do not fork/copy Linux tunnel source or iproute2 merely to increase protocol count.

This is a technical research classification, not legal advice; a shipping binary/package still needs exact component-level legal review.

## 2. Configuration / import / export / URI / QR boundaries

GRE, IPIP, VTI/XFRM and VXLAN do not have a canonical consumer subscription URI or QR interchange format comparable to end-user proxy/VPN clients.

The Linux implementation's authoritative control surface is typed kernel/netlink state exposed through tools such as `ip link`, `ip tunnel`, `ip xfrm`, route/bridge/FDB commands and network-manager/distribution configuration layers.

V1 product conclusions:

- no invented `gre://`, `ipip://`, `vti://`, `xfrm://` or `vxlan://` URI is treated as a standard;
- QR import/export is **N/A** unless PVNetwork later defines its own explicitly versioned product schema;
- `ip ... show` output and raw XFRM state dumps are not portable profile formats;
- persistent desired configuration must be modeled separately from ephemeral kernel/runtime state;
- imports, if implemented, should parse a typed PVNetwork schema or a specifically selected system/network-manager format rather than arbitrary shell text;
- exports must exclude XFRM/IPsec keying material and other secrets by default;
- GRE/IPIP/VXLAN metadata such as keys, VNI, endpoints, marks and FDB state must not be mislabeled as cryptographic secrets.

For protected compositions (064, 066, 067, 068, 070), IPsec credential/profile import and secret storage inherit the already completed entries 004–007; the tunnel layer only references that protected credential/policy object.

## 3. Meaningful forks / downstreams / alternatives

No GRE-, IPIP-, VTI-, XFRM- or VXLAN-specific maintained fork was selected as a second canonical implementation candidate during this V1 pass.

Important distinction:

- Linux distribution kernels, appliance kernels and vendor network OS implementations are downstream packaging/interoperability targets, not automatically protocol-specific forks of equal authority;
- if PVNetwork later ships against a distribution/vendor kernel, that exact downstream source/package/version must be pinned separately because backports and defaults can differ;
- GRETAP, Geneve, GRE-over-IPsec, IPIP-over-IPsec, VTI, XFRM interfaces and VXLAN-over-IPsec are alternative modes/compositions/technologies, not evidence that one entry's semantics should be merged into another.

Thus Gate 14 is satisfied by explicitly bounding the upstream choice and identifying when downstream/vendor implementations become separate evidence targets rather than fabricating a fork comparison.

## 4. Current maintenance / high-impact fixes / release review

The reviewed Linux source pin (`ad8d485e...`, 2026-08-14) is newer than the following relevant fixes and therefore contains them. These are evidence that exact deployment kernel versions matter; they are not runtime certification receipts.

### GRE — `net/ipv4/ip_gre.c`

Recent canonical path history reviewed:

- `675ed582c1aa4d919dd535490de08c015005c653` — 2026-07 — fixes an LLTX regression for GRE tunnels using SEQ/CSUM; commit explains possible lock-order/deadlock risk with the regression.
- `8165f7ff57d9667d2bb477ef6af83ede7fed4ad7` — 2026-06 — requires `CAP_NET_ADMIN` in the tunnel link namespace for GRE changelink operations, fixing cross-network-namespace authorization behavior; marked for stable.
- `80a7e3507d86051e7c3c9438a4f1b4858d263622` — 2026-06 — annotates lockless tunnel error counters to address data races shared by GRE/IPIP paths.

iproute2 `ip/link_gre.c` history was also reviewed. Notable recent path fixes include:

- `2c3ebb2ae08a634615e56303d784ddb366e47f04` — 2023 — fixes a memory leak in GRE endpoint parsing/control flow.
- `e6e5f774f433f66ab5b2363434295dccf82bd785` — 2023 — cleanup of failed-path control flow.

No claim is made that these are all historical bugs; they are the current high-signal path-history items used to prove maintenance review.

### IPIP — `net/ipv4/ipip.c`

Recent canonical path history reviewed:

- `8211a26324667980a463c069469a818e71207e02` — 2026-06 — requires `CAP_NET_ADMIN` in the tunnel link namespace for IPIP changelink, marked for stable.
- `80a7e3507d86051e7c3c9438a4f1b4858d263622` — 2026-06 — tunnel error-counter data-race annotations affecting IPIP/GRE error paths.
- current history also includes continuing IPIP datapath/offload work, reinforcing that kernel/version behavior is not frozen forever.

### VTI — `net/ipv4/ip_vti.c`

Recent canonical path history reviewed:

- `95cceadbfd52d7239bd730afdda0655287d77425` — 2026-06 — requires `CAP_NET_ADMIN` in the tunnel link namespace for VTI changelink operations, marked for stable.
- subsequent/nearby common tunnel lifecycle and race-hardening changes are part of the same current kernel maintenance surface.

### XFRM — `net/xfrm/`

Recent canonical path history reviewed:

- `f38f8cce2f7e79775b3db7e8a5eacda04ac908e4` — 2026-07 — fixes XFRM policy hash rebuild preallocation logic that could otherwise lead to a poisoned list / kernel fault under allocation failure.
- `430ea57d6daf765e88f90046afbfd1e071cb7200` — 2026-07 — propagates shared-fragment state in IPTFS/XFRM handling to avoid unsafe in-place ESP behavior and memory corruption/panic scenarios.
- `2538bd3cd1ff5af655908469544ac7b7ae259386` — 2026-07 — clears XFRM mode callbacks after failed setup to avoid stale callback use after module unload.

The reviewed source pin is later than these commits. XFRM remains security-sensitive runtime state; keys/SAs must not be copied into support bundles.

### VXLAN — `drivers/net/vxlan/vxlan_core.c`

Recent canonical path history reviewed:

- `b37971686ec59fb027fa4910ba16805e68fddb97` — 2026-08-11 — prevents arming VXLAN ageing timer while a device is down; commit describes a user/network-namespace reachable slab use-after-free scenario and is marked for stable.
- `b9553558b48db54ac9273e6b98d7263ef5c1a329` — 2026-07 — fixes network-header pull validation on VXLAN transmit paths.
- `26bb2dd0a8839617e2c79ffbbe1923f8e4bab9fb` — 2026-07 — fixes a related route-shortcircuit network-header pull issue; marked for stable.

The reviewed source-analysis pin is after these changes.

### Release / issue / advisory surface

- Linux repository metadata shows GitHub Issues disabled; therefore an empty GitHub issue search must not be misrepresented as proof that no bugs exist. Kernel mailing lists, stable commits, release history and path commit history are the authoritative maintenance surface used here.
- Linux tag review observed stable `v7.1` plus newer `v7.2-rc*` pre-releases at review time. The V1 source analysis is pinned to an exact post-v7.1 commit rather than calling an RC a stable release.
- iproute2 tag review observed `v7.1.0` as the latest reviewed release; GitHub repository metadata describes the GitHub repository as a publish-only mirror and gives the canonical kernel.org upstream location.
- iproute2 GitHub Issues are disabled in the reviewed repository metadata; source history, release tags and development/security documentation are therefore used instead of fabricating issue/PR evidence.
- No claim of “no security advisories” is made. The maintenance review records concrete fixes that materially affect safe deployment and leaves exact downstream CVE/advisory/package review to the selected shipping kernel/distribution version.

## 5. Documentation / forum authority

Primary authorities for this family are:

- IETF RFCs for GRE (2784/2890), IPIP (2003), VXLAN (7348), and the already completed IKE/IPsec/ESP/AH standards stack for protected compositions;
- exact Linux kernel source and in-tree `Documentation/` at the pinned commit;
- exact iproute2 source, `man/`, `README`, `README.devel` and `SECURITY.md` at the pinned commit;
- kernel networking mailing-list links embedded in accepted commits for fix discussions/reports.

Community tutorials, random blogs and product forums are not elevated above these primary sources. Vendor documents become authoritative only for a specifically selected vendor implementation/interoperability target.

## 6. V1 closure effect

Combined with:

- `research/upstreams/linux-tunnels-family/V1_SHARED_EVIDENCE.md`,
- `research/upstreams/linux-tunnels-family/V1_SOURCE_TREE_AUDIT.md`,
- each numbered entry's `V1_GATE_RECONCILIATION.md`,
- and completed IPsec entries 004–007 where a protected composition reuses them,

this supplement supplies the previously weak reusable evidence for licensing/reuse, configuration/import/export boundaries, forks/downstreams, maintenance/issues/releases and documentation authority.

It does not make runtime/device/vendor interoperability a hidden V1 completion requirement and does not claim implementation/certification.
