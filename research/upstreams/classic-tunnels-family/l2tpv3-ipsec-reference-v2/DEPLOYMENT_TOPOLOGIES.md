# L2TPv3/IPsec — Protected Pseudowire Deployment Topologies

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

## 1. Linux-to-Linux flow-selective direct-IP protection

`LAN/VLAN A -> Linux L2TPv3 peer A`

`-> protocol 115 packet`

`-> XFRM selector src/dst + proto115`

`-> ESP-protected underlay`

`-> peer XFRM decap`

`-> L2TPv3 peer B -> LAN/VLAN B`

This topology most directly follows RFC3931's direct-IP selector guidance.

Required proof:

- selector only matches the intended endpoint pair/protocol;
- no clear protocol115 fallback;
- pseudowire remains operational across rekey;
- MTU/Layer-2 behavior is acceptable.

## 2. Linux-to-Linux flow-selective UDP protection

`Layer-2 service`

`-> L2TPv3/UDP`

`-> IPsec selector matching actual endpoint/port behavior`

`-> ESP/NAT-T underlay`

Dynamic control requires special care because the control connection starts at destination UDP 1701 but selected ports may differ. Static peers with fixed ports are simpler to bind.

## 3. Protected-underlay site-to-site IPsec

`L2TPv3 peer A`

`-> route through site-to-site IPsec tunnel`

`== protected routed underlay ==`

`-> L2TPv3 peer B`

The IPsec endpoints may be the L2TPv3 peers themselves or separate gateway devices.

Security condition: the L2TPv3 endpoint route must not have an alternate clear path when protection fails.

## 4. Separate IPsec gateways

`L2TPv3 endpoint A -> IPsec gateway A`

`<== ESP tunnel ==>`

`IPsec gateway B -> L2TPv3 endpoint B`

Benefits:

- network appliances can own IPsec while Linux/router endpoints own L2TPv3;
- endpoint hosts need less IKE credential exposure.

Risks:

- L2TPv3 traffic is clear on the local segments between endpoint and IPsec gateway unless those segments are trusted/protected;
- endpoint may not directly know IPsec state;
- routing failover can accidentally expose a clear alternate path.

Record the true cryptographic protection boundary in UI/topology diagrams.

## 5. Linux-to-Cisco protected pseudowire

High-value heterogeneous lab target:

`Linux kernel L2TPv3 + strongSwan/Libreswan`

`<== protected IP underlay ==>`

`Cisco IOS XE L2TPv3 + exact Cisco IPsec design`

Do not claim generic support until the exact Cisco hardware/software/config proves both features compose safely.

Test static pseudowire first; dynamic RFC3931 signaling adds another compatibility layer.

## 6. Cisco-to-Cisco protected pseudowire

Potential architecture:

`Cisco attachment circuit A`

`-> L2TPv3 PW`

`-> selected Cisco IPsec protection / protected routed underlay`

`-> remote Cisco PW`

This must be backed by current product-specific documentation or exact lab configuration. Presence of both feature families on IOS XE is not sufficient evidence.

## 7. Dedicated VLAN protected extension

Preferred bounded Layer-2 use:

`VLAN X A -> protected L2TPv3 -> VLAN X B`

Keep only the required VLAN/service on the pseudowire. This reduces the blast radius compared with extending an entire trunk/LAN.

Still validate:

- STP/loop behavior;
- MAC scale;
- DHCP/ARP/ND trust;
- MTU after ESP overhead;
- VLAN tag preservation.

## 8. Whole-LAN protected bridge

State: `HIGH-RISK / EXPLICIT ONLY`.

IPsec protects confidentiality/integrity across the underlay, but it does not prevent Layer-2 loops, broadcast storms or rogue local peers. Use only with a deliberate topology and bridge controls.

## 9. Dynamic L2TPv3 control inside IPsec

`IPsec protection ready`

`-> RFC3931 control connection inside protected path`

`-> session negotiation`

`-> Layer-2 forwarding`

Control authentication can remain enabled as protocol-level defense-in-depth. It is independent from IKE authentication.

## 10. Direct-IP vs UDP choice

### Direct IP

Prefer on controlled routed networks without NAT when protocol115 ACL/support is available.

### UDP

Use when UDP traversal or platform interoperability requires it. Account for selected ports and additional overhead.

### Protected-underlay

Can hide the transport-specific selector issue but introduces routing-policy dependency and potentially broader protected traffic.

The product should record the chosen composition, not auto-switch silently.

## 11. NAT

IPsec NAT-T may encapsulate ESP in UDP4500. This is independent of whether the protected inner L2TPv3 transport is direct protocol115 or UDP.

If the L2TPv3 peers are behind separate IPsec gateways, local NAT/routing between peer and gateway must also be analyzed; do not assume the inner endpoint addresses remain unchanged.

## 12. IPv6 underlay

L2TPv3 and IPsec can both operate in IPv6-capable implementations, but the composition must be tested separately:

- IPv6 endpoint selectors;
- direct-IP protocol115 over IPv6;
- UDP over IPv6;
- IKEv2/ESP IPv6 transport;
- PMTU/fragmentation;
- firewall;
- current vendor capability.

No IPv6 certification from IPv4 success.

## 13. HA / failover

Protected pseudowire HA combines two stateful systems:

- IKE/ESP SAs;
- L2TPv3 control/session/Layer-2 path.

Potential hazards:

- duplicate active pseudowires create Layer-2 loops;
- IPsec failover can expose clear route;
- new peer has different session/Cookie state;
- MAC flapping after failover;
- SA synchronization may not exist.

Prefer tested active/standby or explicit reconnection rather than assuming seamless HA.

## 14. Container/node gateway

A protected L2TPv3 peer in a container is a node-level network function:

- host kernel L2TP/XFRM;
- strict netns ownership;
- CAP_NET_ADMIN;
- stable endpoint addresses;
- bridge/VLAN access;
- fail-safe forwarding if IKE or container dies.

A dedicated VM is often operationally simpler.

## 15. Migration / alternatives

For new Layer-2 extension projects, compare L2TPv3/IPsec with EVPN/VXLAN, MPLS pseudowires or other modern fabrics according to requirements.

Migration should preserve:

- VLAN/service identity;
- MTU;
- Layer-2 control behavior;
- cryptographic protection;
- rollback.

Do not replace a stable legacy interop service without a business/technical reason, but do not choose entry010 merely because it already exists somewhere.

## 16. Required topology labs

- Linux-Linux direct protocol115 with flow-selective IPsec;
- Linux-Linux UDP with flow-selective IPsec;
- Linux-Linux protected-underlay IPsec;
- separate IPsec gateway topology with clear-segment analysis;
- Linux-Cisco exact-version protected interop;
- dynamic control inside IPsec;
- VLAN/bridge/STP/broadcast behavior;
- IKE rekey during forwarding;
- IPsec loss/no-clear fallback;
- IPv6;
- MTU/ECN;
- HA only if product scope requires it.
