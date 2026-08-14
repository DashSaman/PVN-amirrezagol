# L2TPv3/IPsec — Protected Pseudowire Implementations

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

This entry is a composition, not a new monolithic implementation. A valid endpoint must combine a proven L2TPv3 pseudowire implementation with a proven IPsec implementation and correctly bind IPsec policy to the L2TPv3 transport.

## 1. Linux kernel L2TP + Linux XFRM + strongSwan

Primary open-source architecture direction.

### L2TPv3 layer

Pinned entry-009 evidence:

- Linux kernel `2f1baf1fc8929e6c48370be543ad028ac7ad4131`;
- iproute2 `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`;
- Ethernet pseudowire through kernel L2TP/netlink.

### IPsec/IKE layer

Pinned strongSwan baseline:

- strongSwan `6.0.7` commit `5973ff8e41deef4e015e1138a2de688acedf6f75`;
- Linux XFRM/kernel IPsec data plane.

### Composition

strongSwan negotiates/authenticates IKE and installs ESP SAs/policies into Linux XFRM. Linux L2TP independently creates the pseudowire tunnel/session/netdevice. The XFRM policy must select the L2TPv3 outer transport so that pseudowire packets cross the underlay only inside the intended ESP protection.

### Direct-IP selector

RFC3931 explicitly defines the direct-IP protection selector as:

- local tunnel endpoint IP;
- remote tunnel endpoint IP;
- IP protocol 115.

### UDP selector

For L2TPv3-over-UDP, RFC3931 says to follow the RFC3193 recommendation used for L2TP/UDP protection, meaning endpoint/UDP selector binding is required and port behavior must match the actual control/data transport.

### Role

Primary advanced Linux protected-pseudowire candidate, subject to exact strongSwan/XFRM selector and interop tests.

## 2. Linux kernel L2TP + Linux XFRM + Libreswan

Alternative Linux architecture.

Pinned Libreswan baseline:

- Libreswan v5.4 commit `5eb03b7772b312e705feab9ad5868678a3c007e6`.

Libreswan owns IKE/IPsec control and Linux XFRM policy; kernel/iproute2/go-l2tp owns L2TPv3.

### Role

Major alternative/interoperability implementation. Do not assume strongSwan configuration syntax or selector behavior applies identically; certify the actual Libreswan policy/configuration path.

## 3. ql2tpd + Linux IPsec

Pinned ql2tpd/go-l2tp:

- `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`.

Composition:

`ql2tpd`

`-> Linux kernel L2TP`

plus

`strongSwan/Libreswan -> XFRM ESP`

This can simplify static L2TPv3 tunnel/session orchestration, but ql2tpd's minimal HELLO behavior does not replace IKE/IPsec and does not create full generic dynamic L2TPv3 signaling.

## 4. Cisco IOS XE protected pseudowire target

Current Cisco documentation establishes L2TPv3 pseudowire capability, but this work unit did not find a current IOS XE page explicitly certifying a generic L2TPv3-over-IPsec recipe for all platforms/releases.

Therefore:

- Cisco remains a strong L2TPv3 peer target;
- Cisco IPsec capability is a separate network-OS feature family;
- **do not infer entry-010 support from the presence of both features**;
- exact Cisco platform/release/config must demonstrate that the L2TPv3 flow is selected/protected by IPsec and still interoperates with the peer.

Potential topology evidence may use an IPsec-protected routed underlay between L2TPv3 endpoints or explicit flow-selective IPsec, but the exact product design must be documented and tested.

## 5. Linux-to-Cisco composition

High-value lab target:

`Linux kernel L2TPv3 + strongSwan/Libreswan XFRM`

`<== ESP-protected underlay ==>`

`Cisco L2TPv3 + selected Cisco IPsec configuration`

Required proof:

- IKE/auth algorithms;
- ESP SAs and selectors;
- L2TPv3 session/cookie/pseudowire parameters;
- protected packet captures;
- no unprotected protocol-115/UDP bypass;
- restart/rekey behavior;
- MTU/PMTU.

## 6. Site-to-site IPsec underlay carrying L2TPv3

An alternative composition is to establish a broader site-to-site IPsec tunnel between endpoint networks and route the L2TPv3 endpoint addresses through that protected tunnel.

Security effect:

- L2TPv3 packet stream is protected because its underlay route is inside IPsec;
- selector may be wider than just protocol 115/UDP;
- policy/route design must still guarantee there is no alternate clear route.

This may be operationally simpler on some appliances, but it is a different composition from narrowly selecting only L2TPv3 traffic.

PVNetwork must record which model is used:

- `FLOW-SELECTIVE-IPSEC`
- `PROTECTED-UNDERLAY-IPSEC`

Do not merge them into one generic protected flag.

## 7. Proprietary carrier/router implementations

Other network OS products may combine IPsec and L2TPv3, but entry-010 support must be based on current product documentation plus packet/interoperability receipts.

Generic claims such as “router supports IPsec and L2TPv3” are insufficient.

## 8. Static vs dynamic L2TPv3 under protection

IPsec is independent of whether the L2TPv3 session is static or dynamically signaled.

### Static

- IPsec establishes protection;
- tunnel/session IDs/cookies configured manually;
- kernel/Cisco static pseudowire forwards once both layers are ready.

### Dynamic

- IPsec establishes protection for control and data path according to selectors/underlay design;
- RFC3931 control connection/session signaling occurs inside the protected path;
- control authentication may still be used as defense-in-depth/peer protocol auth but is not a substitute for IPsec.

## 9. Separation from plain site-to-site IPsec

Entry 010 exists only when L2TPv3 adds a real Layer-2 pseudowire/service requirement.

If the requirement is only Layer-3 site-to-site routing, use direct IKEv2/IPsec route/policy-based design rather than adding L2TPv3 without need.

## 10. Selection direction

### New protected Layer-2 Linux pseudowire

`ADVANCED / SELECTIVE USE`

Primary candidate:

- Linux kernel L2TPv3;
- strongSwan + XFRM;
- explicit static pseudowire or selected control backend;
- bridge/VLAN design;
- exact IPsec selectors/protected underlay.

### Existing Cisco interop

`LAB-CERTIFY EXACT PLATFORM`

Do not advertise generic Cisco protected L2TPv3 support until an exact configuration is proven.

## 11. Remaining implementation evidence

- exact strongSwan config syntax/policy receipt for direct protocol-115 selector;
- exact strongSwan UDP L2TPv3 selector/dynamic-port handling;
- Libreswan equivalents;
- Linux xfrm policy/state packet captures;
- exact Cisco IPsec + L2TPv3 supported composition;
- another independent current vendor implementation if needed;
- rekey/failover/HA behavior;
- IPv6 underlay/protected path.
