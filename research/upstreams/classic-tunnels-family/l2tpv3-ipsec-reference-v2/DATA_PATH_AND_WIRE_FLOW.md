# L2TPv3/IPsec — Protected Data Path and Wire Flow

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

This file documents the composed path. L2TPv3 owns pseudowire encapsulation; IPsec owns cryptographic protection. Product state must expose both layers independently.

## 1. Flow-selective direct-IP composition

Conceptual Linux outbound path:

1. an Ethernet/other approved Layer-2 frame enters the local attachment circuit/bridge;
2. the Linux L2TPv3 session encapsulates the pseudowire payload with Session ID, optional Cookie and L2-specific sublayer;
3. the resulting outer packet is L2TPv3 directly over IP protocol 115 between the configured tunnel endpoint addresses;
4. Linux XFRM output policy matches the endpoint pair plus protocol 115;
5. the selected ESP SA protects that L2TPv3 packet;
6. ESP (native protocol 50 or NAT-T/UDP encapsulation according to the IPsec path) crosses the underlay;
7. the peer IPsec stack validates/decrypts the packet;
8. the recovered protocol-115 packet enters the peer L2TPv3 session;
9. Session ID/Cookie/sequencing checks run;
10. the pseudowire frame is delivered to the remote attachment circuit.

The exact internal kernel hook order is implementation-specific; the engineering invariant is that the clear L2TPv3 packet is selected by XFRM before it can leave over an unprotected public route.

## 2. Flow-selective UDP composition

Conceptual outbound path:

`Layer-2 frame`

`-> L2TPv3 session`

`-> UDP-encapsulated L2TPv3`

`-> IPsec/XFRM policy matching the intended endpoint/UDP flow`

`-> ESP protection`

`-> underlay`

For static UDP peers, local/remote UDP ports are configured explicitly. For dynamically signaled RFC3931 peers, selected source/response ports may differ from the initial destination 1701, so policy must be proven against the actual implementation rather than assuming permanent 1701/1701 traffic.

## 3. Broader protected-underlay composition

Alternative architecture:

1. establish a site-to-site IPsec tunnel between endpoint networks/gateways;
2. route the L2TPv3 endpoint addresses only through the protected IPsec route/domain;
3. create the L2TPv3 pseudowire over that routed protected underlay.

This can protect both direct-IP and UDP L2TPv3 without a narrow protocol-specific selector, but the security invariant becomes **route exclusivity**: no alternate clear route may carry the same L2TPv3 endpoint traffic if IPsec disappears.

Record this as `PROTECTED-UNDERLAY-IPSEC`, not as the same policy as `FLOW-SELECTIVE-IPSEC`.

## 4. Inbound processing

For a protected direct-IP flow:

`ESP/NAT-T packet arrives`

`-> IPsec SA lookup, anti-replay, integrity and decryption`

`-> recovered L2TPv3 protocol-115 packet`

`-> L2TPv3 tunnel/session lookup`

`-> Cookie/sequence validation`

`-> pseudowire decapsulation`

`-> l2tpeth/attachment circuit`

`-> bridge/VLAN/local Layer-2 forwarding`

The same layered logic applies to UDP mode after IPsec reveals the protected UDP/L2TPv3 packet.

## 5. Startup ordering

Recommended transaction state:

1. `UnderlayValidated`
2. `IKEStarting`
3. `IKEAuthenticated`
4. `IpsecDataSAInstalling`
5. `ProtectionReady`
6. create/enable L2TPv3 tunnel/session or start dynamic control
7. `PseudowireReady`
8. bind/enable attachment circuit
9. `Forwarding`

A product should not enable production Layer-2 forwarding before `ProtectionReady` if the profile requires IPsec.

Static kernel L2TP objects may be pre-created for operational reasons, but their attachment interface should remain down/detached or policy-blocked until the required XFRM protection is active.

## 6. Dynamic L2TPv3 control under IPsec

For a dynamic RFC3931 deployment:

1. establish IPsec protection for the intended direct-IP/UDP control and data flow or for the complete underlay route;
2. establish/authenticate the L2TPv3 control connection inside that protected path;
3. negotiate pseudowire session/cookies/circuit state;
4. create/activate the data session;
5. enable attachment forwarding.

Control authentication remains an L2TPv3 protocol property even when IPsec already authenticates the outer endpoints.

## 7. Rekey behavior

IPsec IKE/CHILD-SA rekey occurs below the L2TPv3 session. A healthy implementation should let the pseudowire remain logically established while old/new ESP SAs transition according to the IPsec backend.

Required test assertions:

- no cleartext L2TPv3 packet during SA replacement;
- no stale XFRM policy that bypasses the new SA;
- no unnecessary pseudowire ID/Cookie regeneration merely because ESP rekeyed;
- traffic interruption remains within an approved limit;
- old ESP SAs disappear after rekey completion.

## 8. IPsec outage/failure

If IKE/ESP protection fails:

- stop or block pseudowire forwarding;
- keep static L2TP objects only if they cannot emit unprotected traffic;
- do not route protocol 115/UDP over a clear fallback interface;
- clearly report `ProtectionLost` separately from `PseudowireConfigPresent`.

Recovery should restore IPsec first, then resume L2TP forwarding.

## 9. MTU and encapsulation overhead

Effective payload size must account for:

- Ethernet/VLAN payload;
- L2TPv3 Session ID;
- Cookie;
- L2-specific sublayer;
- optional UDP;
- outer IP;
- ESP transform/IV/ICV/padding;
- optional NAT-T UDP;
- possible outer tunnel-mode IP header.

A protected pseudowire can exceed an underlay MTU much sooner than plain L2TPv3. Calculate profile-specific overhead and test PMTU/fragmentation rather than copying a fixed MTU.

## 10. ECN

Entry 009 records RFC9601's current L2TPv3 ECN update. Entry 010 adds IPsec tunnel behavior on top. Effective ECN propagation must be validated across both tunnel layers and the selected kernel/vendor implementation.

Do not assume two individually compliant tunnel layers compose correctly without testing.

## 11. Layer-2 risks after decryption

After IPsec decapsulation, the remote pseudowire still delivers Layer-2 traffic. IPsec does not prevent:

- Layer-2 loops;
- broadcast storms;
- rogue DHCP/ARP/ND;
- VLAN leakage;
- MAC-table exhaustion;
- unauthorized local attachment-circuit traffic.

Bridge/VLAN/STP/storm-control/access policy remains a separate responsibility.

## 12. Observability

Expose correlated but separate objects:

### IPsec

- IKE state/version/backend;
- CHILD/ESP SA state;
- NAT-T active;
- safe negotiated algorithm identifiers;
- rekey counters;
- XFRM policy/state presence;
- protection mode (`FLOW-SELECTIVE` or `PROTECTED-UNDERLAY`).

### L2TPv3

- encapsulation IP/UDP;
- tunnel/session IDs;
- Cookie length/state;
- pseudowire type;
- interface/attachment;
- packet/byte/error/out-of-sequence counters;
- dynamic control state if applicable.

### Composition

- `ProtectionRequired`;
- `ProtectionReady`;
- selector/route-policy validation status;
- cleartext-bypass guard status;
- effective MTU.

Never expose IKE/ESP keys, private keys or PSKs.

## 13. Failure ownership

Separate:

- `IKE_AUTH_FAILED`
- `IPSEC_NO_PROPOSAL`
- `IPSEC_POLICY_INSTALL_FAILED`
- `IPSEC_PROTECTION_LOST`
- `CLEAR_ROUTE_DETECTED`
- `L2TP_TUNNEL_OR_SESSION_MISMATCH`
- `COOKIE_MISMATCH`
- `CONTROL_AUTH_FAILED`
- `PSEUDOWIRE_TYPE_MISMATCH`
- `ATTACHMENT_CIRCUIT_DOWN`
- `BRIDGE_VLAN_LOOP_OR_POLICY_ERROR`
- `MTU_FRAGMENTATION_FAILURE`

## 14. Required runtime evidence

- XFRM policy/state before pseudowire forwarding;
- packet capture showing ESP/NAT-T on the untrusted interface and no clear protocol-115/UDP L2TPv3;
- direct-IP selective protection;
- UDP selective protection with actual port behavior;
- protected-underlay route model;
- wrong credential/no-proposal negatives;
- forced IPsec teardown proving no clear fallback;
- IKE/ESP rekey during active pseudowire;
- MTU/ECN;
- bridge/VLAN/security behavior after decapsulation;
- Linux-to-Cisco or another heterogeneous protected peer.
