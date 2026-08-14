# L2TPv3 — Data Path and Wire Flow

Review date: 2026-08-14

Entry: 009 L2TPv3.

## 1. Product/control-to-data architecture

Recommended product model for Linux static pseudowire:

`PVNetwork operator/profile`

`-> typed pseudowire specification`

`-> privileged local Linux L2TP adapter`

`-> generic netlink / iproute2-compatible operations`

`-> kernel L2TP tunnel + session`

`-> l2tpeth/PPP/other pseudowire endpoint`

`-> bridge/VLAN/routing attachment`

The product must not put privileged netlink/kernel configuration directly in a web/mobile UI process.

## 2. Static Linux Ethernet pseudowire setup

Conceptual sequence:

1. validate local/peer underlay addresses;
2. create L2TPv3 tunnel object with local/peer tunnel IDs and IP/UDP encapsulation;
3. create session with local/peer session IDs, Ethernet pseudowire type, cookies/sequence options;
4. kernel exposes an Ethernet-style `l2tpeth*` device;
5. operator attaches that device to a Linux bridge, VLAN-aware bridge, namespace or other approved L2 context;
6. frames entering the attachment side are encapsulated and forwarded to the peer;
7. matching peer configuration decapsulates and forwards frames locally.

No RFC3931 control connection exists in this static `ip l2tp` model.

## 3. Outbound Ethernet data path

For Ethernet pseudowire:

`local Ethernet frame`

`-> bridge/VLAN forwarding decision`

`-> l2tpeth netdevice xmit`

`-> kernel L2TP session lookup`

`-> optional L2-specific sublayer / sequencing`

`-> Session ID + optional Cookie`

`-> direct IP protocol 115 OR UDP/IP`

`-> physical/routed underlay`

Current Linux `l2tp_eth.c` sends the frame through the L2TP session and maintains netdevice statistics.

## 4. Inbound Ethernet data path

`underlay packet`

`-> IP protocol 115 or UDP demux`

`-> L2TP tunnel/session parsing`

`-> Session ID lookup`

`-> cookie validation if configured`

`-> sequencing/reorder processing if configured`

`-> pseudowire payload decapsulation`

`-> l2tpeth netdevice receive`

`-> bridge/VLAN/local attachment circuit`

A packet with wrong Session ID/cookie must not leak into the wrong Layer-2 domain.

## 5. Dynamic/signaled data path

A full RFC3931 LCCE adds a control connection before/alongside data flow:

`Control connection setup/authentication`

`-> pseudowire capability/session signaling`

`-> peer session IDs/cookies/status`

`-> data session installed`

`-> Layer-2 forwarding`

HELLO/control liveness and session teardown can remove/recreate data sessions dynamically.

Data forwarding itself remains distinct from control-message processing.

## 6. ql2tpd model

Current go-l2tp ql2tpd uses Linux kernel L2TP data plane and config-driven static tunnels/sessions. Optional `hello_timeout` adds a minimal keepalive/control transport when the peer is also ql2tpd.

Flow:

`ql2tpd config`

`-> go-l2tp Linux netlink data-plane adapter`

`-> kernel tunnel/session`

`-> optional ql2tpd HELLO monitoring`

This is not equivalent to a fully negotiated vendor pseudowire control plane.

## 7. Cisco IOS XE pseudowire flow

Conceptual signaled model:

`attachment circuit`

`-> xconnect / pseudowire class`

`-> L2TPv3 control/session establishment`

`-> pseudowire encapsulation`

`-> routed IP underlay`

`-> peer pseudowire`

`-> remote attachment circuit`

Static/manual mode skips signaling and relies on manually matched IDs/cookies.

## 8. Layer-2 consequences

Ethernet pseudowire extends Layer-2 semantics across a Layer-3 underlay. Potential traffic includes:

- unicast Ethernet;
- broadcast;
- multicast;
- ARP/ND;
- DHCP;
- VLAN tags depending service mode;
- control protocols depending platform/attachment-circuit tunneling policy.

Consequences:

- broadcast storms can cross sites;
- MAC-table instability can cross the pseudowire;
- STP/loop behavior must be intentional;
- VLAN leakage/mismatch can connect unintended domains;
- DHCP or ARP spoofing can cross if not controlled.

L2TPv3 itself does not provide a Layer-3 firewall boundary.

## 9. Bridge integration

Typical Linux deployment:

`LAN interface or VLAN`

`-> Linux bridge`

`<-> l2tpeth pseudowire interface`

The bridge owns MAC learning/filtering/STP/VLAN behavior. L2TPv3 owns pseudowire encapsulation. Keep those responsibilities separate in configuration and diagnostics.

## 10. Routed service over Ethernet pseudowire

A common safer pattern is to dedicate a VLAN/subinterface or routed pair over the extended Ethernet service rather than merging entire user LANs.

Example:

`router A VLAN 400`

`-> L2TPv3 Ethernet PW`

`-> router B VLAN 400`

`-> L3 routing/firewall on each side`

This still carries Layer-2 traffic for the selected VLAN but reduces accidental whole-LAN extension.

## 11. Direct IP vs UDP path

### Direct IP

`L2TPv3 -> IP protocol 115`

Advantages:

- lower header overhead;
- no UDP port layer.

Tradeoffs:

- less NAT-friendly;
- firewalls must allow IP protocol 115;
- no UDP checksum;
- current RFC recommends control-message authentication/integrity.

### UDP

`L2TPv3 -> UDP -> IP`

Advantages:

- more NAT/firewall-friendly in some environments;
- UDP checksum option/integrity for UDP layer.

Tradeoffs:

- extra overhead;
- dynamic port behavior under full control negotiation;
- possible interaction/fallback with L2TPv2.

## 12. IPsec-protected path boundary

For entry 010:

`Layer-2 frame`

`-> L2TPv3`

`-> IP/UDP outer L2TP transport`

`-> IPsec ESP protection`

`-> underlay`

The exact order/selectors depend on the chosen composition. Entry 009 must not claim confidentiality from this optional composition until entry 010 is separately evidenced.

## 13. MTU/PMTU

Effective frame capacity is reduced by:

- L2TPv3 Session ID;
- Cookie;
- L2-specific sublayer;
- UDP when selected;
- outer IPv4/IPv6;
- IPsec overhead for entry 010.

Required design:

- calculate per-profile overhead;
- account for Ethernet VLAN tags;
- test jumbo frames only when all segments support them;
- respect PMTU/DF behavior;
- avoid silent fragmentation of large Layer-2 frames where equipment cannot reassemble safely.

## 14. ECN

RFC 9601 updates RFC3931 for ECN propagation through tunnel protocols with shim headers. The selected Linux/vendor implementation's effective behavior must be verified against current kernel/software and standards guidance.

Do not treat ECN bits as opaque forever simply because L2TPv3 is an older protocol.

## 15. Observability

Safe per-pseudowire telemetry:

- endpoint addresses;
- encapsulation IP/UDP;
- tunnel/session IDs;
- cookie length (value redacted if policy requires);
- pseudowire type;
- interface name;
- packets/bytes/errors/drops/out-of-sequence;
- control state for signaled mode;
- bridge/VLAN association;
- MTU;
- underlay reachability.

Current iproute2 source includes packet/byte/error/out-of-sequence statistics structures for L2TP objects.

## 16. Failure ownership

Separate at least:

### Underlay

- no route;
- IP protocol 115 blocked;
- UDP/port/NAT blocked;
- MTU/fragmentation.

### L2TP tunnel/session

- tunnel ID mismatch;
- session ID mismatch;
- cookie mismatch;
- pseudowire type mismatch;
- sequence/reorder issue;
- control auth/HELLO failure.

### Layer-2 integration

- bridge/VLAN mismatch;
- STP block/loop;
- MAC learning issue;
- attachment circuit down;
- VLAN MTU mismatch.

### Security composition

- unprotected entry-009 path used where entry 010 protection was required;
- IPsec SA/policy failure.

## 17. Required execution receipts

- kernel module creation/deletion;
- static direct-IP PW;
- static UDP PW;
- Ethernet bridge traffic both directions;
- VLAN isolation;
- multicast/broadcast handling;
- cookie negative case;
- sequencing/reorder;
- MTU/ECN;
- failure/restart cleanup;
- Linux-to-Cisco static interoperability;
- dynamic control-plane peer test;
- entry-010 protected path separately.
