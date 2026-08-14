# Linux Tunnel / IPsec Composition Family — Shared V1 Evidence for 063–070

Review date: 2026-08-14

Scope:

- 063 GRE
- 064 GRE over IPsec
- 065 IP-in-IP / IPIP
- 066 IPIP over IPsec
- 067 VTI/IPsec
- 068 XFRM/IPsec
- 069 VXLAN
- 070 VXLAN over IPsec

This is infrastructure/site-to-site research. Consumer GUI/client-app requirements are frequently `N/A-CONSUMER / PEER-MAPPED`; that is not a research gap when the role is inherently kernel/network-admin driven.

## 1. Primary current Linux implementation baseline

### Linux kernel

Repository:

`torvalds/linux`

Reviewed master commit:

`ad8d485e665829ecbf3c97b22ce251f8ff5f8037`

Reviewed commit date:

2026-08-14

Licensing:

- root `COPYING` identifies kernel licensing as GPL-2.0 with the documented syscall-note model;
- individual files also carry SPDX identifiers and must be respected path-by-path.

Relevant current source paths include:

- GRE: `net/ipv4/ip_gre.c` (GPL-2.0-or-later SPDX in reviewed file);
- IPIP: `net/ipv4/ipip.c` (GPL-2.0-or-later);
- VTI/IPsec: `net/ipv4/ip_vti.c` (GPL-2.0-or-later) and IPv6 counterpart where applicable;
- XFRM framework: `net/xfrm/` and IPsec transform/policy hooks throughout network stack;
- VXLAN: `drivers/net/vxlan/vxlan_core.c` (GPL-2.0-only);
- common tunnel/network namespace/ECN/PMTU/rtnetlink infrastructure.

The reviewed VTI source explicitly calls XFRM policy/state/input machinery; VTI is therefore a Linux interface architecture tied to IPsec/XFRM, not a new wire protocol.

The reviewed VXLAN source states:

- IANA-assigned UDP destination port is **4789**;
- Linux module default remains **8472** for early-adopter compatibility unless configuration overrides it.

Do not document one port as universally mandatory without recording implementation configuration.

### iproute2

Repository:

`iproute2/iproute2`

Reviewed current main commit:

`da2ccdf862cb1eab45de082cc71fcb4e5d712e78`

Reviewed commit date:

2026-08-13

License:

root `COPYING` = GPLv2; individual files include SPDX identifiers.

Relevant current user-space control paths include:

- `ip/link_gre.c` — GRE/GRETAP-style link attributes and endpoint/key/TTL/TOS/encap options;
- `ip/link_vti.c` — VTI local/remote/key/device/fwmark options;
- `ip/iplink_vxlan.c` — VNI, local/remote/group, device, UDP port, learning/FDB/checksum/metadata options;
- `ip xfrm` state/policy tooling;
- generic `ip tunnel` / route / link / address commands used by infrastructure deployments.

PVNetwork rule:

**kernel implementation and iproute2 control-plane are separate components**. A GUI wrapper should not shell out uncontrolled command strings when a structured netlink/platform API is available.

## 2. Standards authority and identity boundaries

### GRE — entry 063

Primary standards:

- RFC 2784 — Generic Routing Encapsulation (GRE)
- RFC 2890 — Key and Sequence Number Extensions to GRE

GRE encapsulates another protocol inside GRE. Base GRE does **not** provide confidentiality or cryptographic peer authentication.

Linux supports GRE-family tunnel devices and options beyond minimal RFC text; implementation-specific features must not be advertised as universal GRE behavior.

### IP-in-IP / IPIP — entry 065

Primary standard:

- RFC 2003 — IP Encapsulation within IP

The mechanism adds an outer IPv4 header around an inner IP packet. It does not encrypt/authenticate the payload.

### VXLAN — entry 069

Primary standard:

- RFC 7348 — Virtual eXtensible Local Area Network (VXLAN)

VXLAN carries Layer-2 frames over a UDP/IP underlay using a VNI. It is an overlay/tunnel, not an encryption protocol. Linux supports both IANA port 4789 and a historical default 8472 behavior as noted above.

### IPsec compositions — entries 064, 066, 067, 068, 070

Reuse completed V1 evidence from entries 004–007:

- IKEv2/IPsec;
- IKEv1/IPsec;
- ESP;
- AH.

Do not redo or merge their semantics.

Composition principle:

- GRE/IPIP/VXLAN defines inner encapsulation/topology;
- XFRM/IPsec defines policy/state/cryptographic protection;
- VTI is an interface abstraction coupled to XFRM state/policy;
- “over IPsec” means the selected tunnel traffic is protected by correctly scoped IPsec policies/states; it is not a new cryptographic protocol.

## 3. Security model

Unprotected GRE/IPIP/VXLAN:

- no payload encryption;
- no cryptographic integrity/authentication from the tunnel itself;
- should not be presented as a secure VPN on an untrusted underlay.

Protected compositions:

- reuse maintained IPsec/IKE/ESP implementations and policies;
- keep IPsec keys/certificates/PSKs in protected ownership;
- selectors/policies must cover the intended encapsulated traffic and not accidentally leave a parallel cleartext path;
- route/policy ordering, MTU/fragmentation and NAT traversal are operational security/reliability concerns;
- do not implement cryptography in PVNetwork.

VXLAN-specific:

- VNI is segmentation metadata, **not** a cryptographic secret;
- FDB/learning/remote/group configuration can leak/redirect traffic if misconfigured;
- UDP underlay/firewall/MTU behavior needs independent testing.

VTI/XFRM-specific:

- VTI keys/marks are selector/routing metadata, not encryption keys;
- XFRM state contains sensitive IPsec keying/state material and must never be dumped into ordinary diagnostics/support bundles.

## 4. UI / installation / platform treatment

Canonical Linux administration is CLI/netlink/configuration based, not a consumer VPN app.

V1 UI classification for entries 063–070:

- consumer GUI: usually **N/A-CONSUMER**;
- admin/control UI: `ip link`, `ip tunnel`, `ip xfrm`, network configuration managers, network OS/vendor UI/CLI;
- server vs client: typically **peer/site-to-site**, not asymmetric consumer client/server;
- mobile Store clients: generally N/A unless a platform exposes an appropriate native/network-extension architecture and product has a concrete use case.

PVNetwork long-term UI, if these are exposed, should place them under **Advanced / Infrastructure / Site-to-Site**, not beside ordinary end-user server cards.

## 5. Persistence / configuration ownership

Structured configuration categories:

- interface/tunnel name;
- local/remote underlay endpoints;
- GRE key/sequence/checksum flags where applicable;
- IPIP outer/inner family and endpoint state;
- VXLAN VNI/remote/group/local/device/UDP-port/FDB/learning options;
- route/table/metric/VRF/namespace binding;
- VTI marks/keys/interface binding;
- XFRM policies/states/selectors;
- IPsec identities/certificates/PSKs/keys through the IPsec layer;
- MTU/PMTU/ECN/fragmentation behavior;
- firewall/NAT rules as separate host-network state.

Runtime kernel state is not the same as persistent desired configuration. PVNetwork must not pretend `ip link show` output is a portable profile format.

## 6. Logs / diagnostics / failure taxonomy

Required failure domains:

- underlay reachability;
- tunnel/interface creation;
- route lookup/table/mark;
- GRE/IPIP/VXLAN encapsulation/decapsulation;
- FDB/neighbor/learning for VXLAN;
- XFRM policy/state match;
- IKE/SA establishment for IPsec-protected compositions;
- MTU/PMTU/fragmentation/ECN;
- firewall/NAT;
- namespace/VRF/device binding;
- link/address/race cleanup;
- peer/version/vendor interoperability.

Do not collapse all of these into “VPN failed”.

## 7. Tests / source quality evidence

Linux kernel and iproute2 are actively maintained upstreams with their own extensive kernel/selftest/build/review ecosystems. Production support still needs PVNetwork-specific lab evidence:

- Linux kernel/version matrix;
- iproute2 version matrix;
- peer/vendor interoperability where advertised;
- packet capture proving encapsulation and, for protected compositions, absence of cleartext payload on the underlay;
- route/policy teardown and restart;
- PMTU/fragmentation/IPv4/IPv6/ECN;
- namespace/VRF and concurrent network-change lifecycle;
- firewall/NAT behavior.

## 8. Reuse decision

PVNetwork should **not embed or fork Linux kernel/iproute2 simply to implement these entries**.

Preferred architecture:

- Linux: structured netlink/kernel/native APIs and approved system networking components;
- IPsec: reuse selected maintained strongSwan/native XFRM/IKE architecture from completed IPsec dossiers;
- network OS/vendors: interoperability/control integrations only when current APIs and licenses are verified;
- UI: advanced infrastructure adapter with typed model, capability checks and explicit peer/topology role.

Linux/iproute2 source is primarily behavior/reference/API evidence under GPL; invoking system facilities through stable OS interfaces is a different architectural/legal question from copying/linking GPL code.

## 9. Later V2 work — not V1 blockers

For each entry V2 must still add:

- server/peer implementations and installers;
- OS/container/orchestration matrix;
- admin/UI/menu maps;
- exact client/peer install matrix;
- cryptography/security boundary;
- data path/wire flow;
- ports/transports/handshake/selector behavior;
- deployment topologies;
- exact source/license/supply-chain and update/rollback evidence;
- live packet/interoperability receipts where strict V2 requires them.
