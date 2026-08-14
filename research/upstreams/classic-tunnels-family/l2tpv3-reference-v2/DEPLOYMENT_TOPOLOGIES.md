# L2TPv3 — Deployment Topologies

Review date: 2026-08-14

Entry: 009 L2TPv3.

L2TPv3 extends a pseudowire/service across an IP underlay. The design question is normally “which Layer-2 circuit/service is being extended between which endpoints?” rather than “which user logs into a VPN server?”.

## 1. Linux-to-Linux static Ethernet pseudowire

### Shape

`LAN/VLAN A`

`-> Linux bridge/attachment`

`-> l2tpeth A`

`== L2TPv3 IP/UDP ==>`

`l2tpeth B`

`-> Linux bridge/attachment`

`-> LAN/VLAN B`

### Use case

- controlled Layer-2 extension between two infrastructure nodes;
- lab/inter-site dedicated service;
- migration/interoperability test.

### Requirements

- matching static IDs/cookies/encapsulation;
- routed underlay reachability;
- MTU;
- VLAN/STP/loop design;
- protection via entry 010 if underlay is untrusted.

## 2. Linux-to-Cisco static/manual pseudowire

### Shape

`Linux attachment circuit`

`-> kernel/iproute2 static PW`

`<== L2TPv3 ==>`

`Cisco IOS XE xconnect/manual PW`

`-> Cisco attachment circuit`

This is a primary heterogeneous interoperability target.

Test:

- IP vs UDP encapsulation supported by selected peers;
- local/remote session/tunnel IDs;
- 4/8-byte cookie compatibility;
- pseudowire type;
- VLAN/frame behavior;
- MTU/DF/PMTU;
- restart/reprovision.

## 3. Cisco-to-Cisco signaled pseudowire

### Shape

`attachment circuit A`

`-> xconnect / pw-class / L2TP class`

`== RFC3931 control + data ==>`

`peer xconnect`

`-> attachment circuit B`

Current IOS XE documentation supports L2TPv3 signaling classes and pseudowire configuration on selected platforms.

Use as a dynamic-control reference to compare with static Linux operation.

## 4. Linux ql2tpd-to-ql2tpd

### Shape

`Linux service A`

`-> ql2tpd -> kernel L2TP`

`<== static data + optional HELLO ==>`

`kernel L2TP <- ql2tpd`

### Note

The optional ql2tpd HELLO mode is a minimal like-peer liveness mechanism, not proof of full dynamic Cisco interoperability.

## 5. Dedicated VLAN pseudowire

Prefer extending a deliberately selected VLAN over stretching the entire physical broadcast domain.

`Switch/router VLAN X`

`-> tagged/untagged attachment`

`-> L2TPv3 Ethernet PW`

`-> remote VLAN X`

Benefits:

- reduced blast radius;
- clearer topology ownership;
- easier firewall/routing boundary above the VLAN;
- simpler loop/MAC scale expectations.

## 6. Whole-port / whole-LAN bridge extension

State: `HIGH-RISK / EXPLICIT DESIGN ONLY`.

Extending entire Layer-2 domains can transport:

- broadcast/multicast storms;
- STP/control frames depending platform behavior;
- rogue DHCP/ARP/ND;
- accidental VLANs;
- large MAC tables.

Use only with documented topology, STP/control-protocol policy, storm control and monitoring.

## 7. Carrier/attachment-circuit pseudowires

RFC3931 and Cisco implementations can support pseudowire types beyond Ethernet, and current IOS XE documentation includes examples such as Frame Relay over L2TPv3.

Each service mapping requires:

- pseudowire-specific RFC/vendor support;
- attachment-circuit parameters;
- sequencing/status/OAM behavior;
- exact peer interoperability.

Do not certify all pseudowire types because Ethernet works.

## 8. Direct IP underlay

### Shape

`Peer A IP`

`<== IP protocol 115 ==>`

`Peer B IP`

Use on controlled routed networks where protocol 115 is allowed and NAT is absent.

Benefits: lower overhead.

Risks:

- NAT/firewall incompatibility;
- no UDP checksum;
- no cryptographic confidentiality;
- protocol-115 ACL requirements.

## 9. UDP underlay

### Shape

`Peer A`

`<== UDP/L2TPv3 ==>`

`Peer B`

Useful where UDP traversal/ACL tooling is preferable. Dynamic control begins at UDP 1701 but selected port pairs may vary after establishment.

Do not hard-code both peer ports to 1701 in every signaled topology.

## 10. IPsec-protected underlay — boundary to entry 010

### Shape

`L2 service`

`-> L2TPv3`

`-> IPsec ESP`

`-> untrusted routed network`

`-> IPsec decap`

`-> L2TPv3 decap`

`-> remote L2 service`

This is the normal security direction when the underlay is public/untrusted. Exact IPsec configuration belongs to entry 010 and must not be assumed complete here.

## 11. VRF / network namespace isolation

Linux peers may place underlay and/or attachment interfaces in controlled VRFs/namespaces.

Potential benefits:

- route isolation;
- tenant separation;
- reduced accidental bridge attachment;
- controlled privilege boundary.

Test netlink/kernel object visibility and cleanup carefully because tunnel/session objects are namespace-scoped.

## 12. Containerized endpoint

State: `ADVANCED`.

Likely architecture:

`node/host kernel L2TP`

`<-> dedicated container/netns management process`

`<-> selected bridge/interfaces`

Do not assume container isolation means data-plane isolation; the host kernel owns the L2TP implementation.

## 13. HA / redundant pseudowire

Simple duplicate static tunnels do not automatically provide safe HA.

Risks:

- duplicate Layer-2 paths create loops;
- MAC flapping;
- both pseudowires forward simultaneously;
- cookies/session IDs differ after failover;
- vendor redundancy behavior differs.

Cisco documentation for other pseudowire technologies includes redundancy frameworks, but current IOS XE docs also list limitations for L2TPv3 pseudowire redundancy in some feature combinations. Certify only an exact platform/design.

PVNetwork should prefer external routing/L2 control or a tested active/standby design rather than inventing transparent dual-PW forwarding.

## 14. Migration to modern L2VPN

For new large-scale deployments, compare L2TPv3 with current alternatives such as EVPN/VXLAN/MPLS pseudowires based on actual requirements.

Migration path:

- inventory attachment circuits/VLANs;
- parallel modern service;
- verify MAC/VLAN/MTU/control traffic;
- move endpoints;
- remove old PW and underlay ACL/IPsec state.

Do not automatically replace L2TPv3 when it is a stable, bounded interoperability requirement, but do not select it by inertia either.

## 15. Required topology labs

- Linux static direct-IP Ethernet;
- Linux static UDP Ethernet;
- Linux bridge/VLAN isolation;
- Linux-to-Cisco static;
- Cisco-to-Cisco signaled reference;
- ql2tpd like-peer HELLO mode;
- direct-IP ACL/protocol-115 failure;
- UDP/NAT behavior;
- MTU/PMTU/VLAN/jumbo limits;
- broadcast/multicast/STP policy;
- restart/cleanup;
- entry-010 IPsec protection;
- active/standby topology only if product scope requires it.
