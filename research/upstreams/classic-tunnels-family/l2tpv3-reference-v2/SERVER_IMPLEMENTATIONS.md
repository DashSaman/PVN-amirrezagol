# L2TPv3 — Peer / Pseudowire Implementations

Review date: 2026-08-14

Entry: 009 L2TPv3.

L2TPv3 endpoints are better described as LCCEs / pseudowire endpoints / PEs than as a consumer VPN “server/client.” This file maps serious endpoint implementations while preserving that role distinction.

## 1. Linux kernel L2TP subsystem

Reviewed source pin:

- `torvalds/linux@2f1baf1fc8929e6c48370be543ad028ac7ad4131`.

Relevant current source:

- `net/l2tp/l2tp_core.c` — common L2TPv2/v3 tunnel/session infrastructure, IP/UDP transport integration, v3 session tables and sequencing;
- `net/l2tp/l2tp_eth.c` — Ethernet pseudowire netdevice implementation creating `l2tpeth*` style interfaces;
- related L2TP netlink/IP/IP6 modules in the kernel tree.

### Role

Linux owns the high-speed pseudowire data plane and kernel tunnel/session objects. User space programs the objects through the generic-netlink L2TP API or sockets/control software.

### Security boundary

Kernel L2TPv3 is a tunneling data plane, not a cryptographic VPN engine. If confidentiality/integrity is required over an untrusted path, compose with approved IPsec or another protected underlay in a separately modeled deployment.

## 2. iproute2 `ip l2tp`

Reviewed source pin:

- `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- GPLv2 root COPYING; `ipl2tp.c` GPL-2.0-or-later.

Current source programs:

- local/peer tunnel ID;
- local/peer session ID;
- IPv4/IPv6 endpoints;
- UDP vs IP encapsulation;
- local/peer UDP ports;
- 4/8-byte local and peer cookies;
- sequencing and reorder timeout;
- pseudowire type;
- interface name;
- L2-specific sublayer options.

### Important control-plane limitation

Linux `ip l2tp` manual documentation says these are static unmanaged L2TPv3 Ethernet pseudowires and that no L2TP control protocol is used. Operators must provision matching tunnel/session IDs/cookies/parameters on both peers.

### Role

Primary Linux static pseudowire configuration/reference tool.

## 3. Katalix go-l2tp / ql2tpd

Reviewed source pin:

- `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- MIT.

Current README advertises L2TPv3 data plane through Linux kernel L2TP, IPv4/IPv6 endpoints, UDP/direct-IP encapsulation and `ql2tpd` for static L2TPv3 sessions.

### ql2tpd

- root-required daemon;
- configuration-driven static tunnel/session creation;
- can operate purely statically;
- optional `hello_timeout` sends periodic keepalive using a minimal RFC3931 reliable control transport, intended only when both peers run ql2tpd;
- does **not** become a full generic vendor RFC3931 control-plane implementation merely because HELLO support exists.

### Role

Useful open-source Linux orchestration/reference above the kernel data plane and a candidate test peer for static/controlled labs.

## 4. Cisco IOS XE L2TPv3

Current Cisco IOS XE 17.x documentation continues to support L2TPv3 pseudowires with:

- `pseudowire-class`;
- `encapsulation l2tpv3`;
- optional L2TPv3 signaling or `protocol none` for static/manual operation;
- local source interface;
- `xconnect` binding an attachment circuit to a remote peer/VC ID;
- manual local/remote L2TP IDs and cookies for unsignaled pseudowires;
- pseudowire attributes including sequencing, fragmentation/PMTU and payload-specific options depending platform/feature.

Cisco documentation covers Ethernet and other attachment circuits such as Frame Relay in selected IOS XE families.

### Role

Major proprietary router/PE interoperability target. No source reuse is implied. Exact router/platform/IOS XE release support matrix is mandatory before certification.

## 5. Other network-vendor endpoints

L2TPv3 historically appears in carrier/router platforms beyond Cisco. They should enter the PVNetwork matrix only with current vendor documentation for an exact platform/software release.

Do not infer current L2TPv3 support from generic “pseudowire/L2VPN” terminology because many products use MPLS, EVPN/VXLAN, GRE or other encapsulations instead.

## 6. Linux bridge / OVS / routing integration

The L2TPv3 pseudowire interface can be attached to higher-level Linux networking such as:

- Linux bridge;
- VLAN subinterfaces/filtering;
- routing interfaces where a pseudowire type exposes a suitable netdevice;
- namespaces/VRFs depending kernel/network design.

These are integration components, not L2TPv3 implementations. Each adds independent forwarding, loop, VLAN, STP and namespace behavior that must be tested.

Open vSwitch integration should only be claimed after exact tested attachment/configuration evidence; do not infer native L2TPv3 support from the ability to attach a Linux netdevice.

## 7. Control-plane categories

### RFC3931 dynamic/signaled

A full LCCE control connection negotiates capabilities/session state, cookies and pseudowire attributes and sends HELLO/liveness/control messages.

### Static/manual

Both endpoints are configured out-of-band with matching tunnel/session IDs, cookies and pseudowire parameters. Linux `ip l2tp` belongs here.

### Minimal hybrid/orchestrated

Tools such as ql2tpd can create static Linux sessions and optionally add a limited HELLO mechanism between like peers.

PVNetwork must represent these as different capabilities rather than one Boolean `l2tpv3=true`.

## 8. Pseudowire payload types

RFC3931 defines a generic pseudowire framework; separate RFCs define payload/service mappings.

High-value current reference:

- RFC 4719 — Ethernet and Ethernet VLAN frames over L2TPv3.

Other pseudowire types require their own mapping/spec/platform evidence. The existence of a numeric pseudowire type in kernel/iproute2 source is not proof that every vendor endpoint interoperates for that type.

## 9. Selection direction

### Linux-to-Linux static Ethernet pseudowire

`HIGH-VALUE LAB/ADVANCED TARGET`

Use kernel L2TP + pinned iproute2 or ql2tpd and explicit bridge/VLAN design.

### Linux-to-Cisco IOS XE

`HIGH-VALUE INTEROPERABILITY TARGET`

Test both static/manual and signaled modes only where both endpoint capabilities match.

### Consumer desktop/mobile VPN

`NOT A NORMAL ENTRY-009 USE CASE`

Do not create a consumer “Connect L2TPv3” client page unless a real product requirement exists. L2TPv3 is generally infrastructure pseudowire configuration.

## 10. Remaining implementation evidence

- exact current Linux stable/LTS distro kernel/module matrix;
- current iproute2 release pin rather than only moving main;
- ql2tpd release/build/package activity and system service patterns;
- exact Cisco platform/software support and feature caveats;
- another independent current vendor implementation for interoperability diversity;
- dynamic RFC3931 open-source full-control-plane implementation if one is selected;
- real attachment-circuit and pseudowire-type compatibility receipts.
