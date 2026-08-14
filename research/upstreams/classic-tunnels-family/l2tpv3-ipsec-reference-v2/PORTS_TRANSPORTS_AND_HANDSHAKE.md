# L2TPv3/IPsec — Ports, Transports and Protected Establishment

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

## 1. Outer IPsec control/protection

Reuse the completed IPsec reference:

- UDP 500 — IKE;
- UDP 4500 — IKE/NAT-T and UDP-encapsulated ESP where applicable;
- native ESP — IP protocol 50 when not UDP encapsulated.

The selected IKE version/authentication/ESP proposal is an IPsec profile property, not an L2TPv3 field.

## 2. Protected direct-IP L2TPv3

Plain inner pseudowire transport:

- endpoint source/destination IPs;
- IP protocol **115**.

RFC3931 explicitly identifies these fields as the IPsec security selector for L2TPv3 directly over IP.

Conceptual wire sequence after protection:

`IKE UDP 500/4500 establishes ESP SA`

then

`L2TPv3 protocol-115 packet`

`-> selected by IPsec policy`

`-> ESP protocol 50 or NAT-T/UDP4500 outer packet`

`-> underlay`

Do not display TCP/UDP “port 115.”

## 3. Protected UDP L2TPv3

Plain inner pseudowire transport:

- UDP/IP;
- dynamic control begins at destination UDP 1701;
- source/response ports can differ after establishment;
- static Linux sessions can use explicitly configured local/remote UDP ports.

RFC3931 refers UDP L2TPv3 IPsec protection to the RFC3193 L2TP/UDP filtering model. The actual IPsec selector therefore must be validated against the real peer/port behavior.

Wire sequence:

`IKE -> ESP SA`

then

`L2TPv3 UDP packet`

`-> IPsec selector`

`-> ESP / NAT-T outer protection`

`-> underlay`

## 4. Protected-underlay IPsec mode

Instead of selecting only protocol 115/UDP, a site-to-site IPsec tunnel may protect the route between the L2TPv3 endpoints.

Sequence:

`IKE establishes site-to-site IPsec`

`-> protected route/interface/policy ready`

`-> L2TPv3 direct-IP or UDP control/data runs through that route`

The engineering gate is route exclusivity/no clear fallback, not just selector specificity.

## 5. Static pseudowire establishment

For Linux `ip l2tp` static mode:

1. underlay reachable;
2. IKE/ESP protection established and selector/route policy verified;
3. create local L2TPv3 tunnel;
4. create matching session/cookie/pseudowire parameters;
5. bring up attachment netdevice;
6. enable Layer-2 forwarding.

There is no RFC3931 SCCRQ/SCCRP/SCCCN control handshake in static mode.

## 6. Dynamic pseudowire establishment

For a full RFC3931 peer:

1. underlay reachable;
2. IPsec protection established;
3. L2TPv3 control connection starts inside the protected path;
4. SCCRQ/SCCRP/SCCCN establish control;
5. session/call messages negotiate pseudowire state;
6. attachment circuit becomes forwarding-ready;
7. HELLO/liveness maintains control connection.

If control authentication is configured, it is independent of IKE peer authentication.

## 7. Firewall model

For flow-selective direct-IP protection on an untrusted interface:

- allow required IKE/NAT-T/ESP outer traffic;
- do **not** generally permit clear IP protocol 115 between peers outside the protected policy;
- verify XFRM policy enforces/redirects the L2TPv3 flow.

For flow-selective UDP:

- allow required IKE/NAT-T/ESP outer traffic;
- do not expose clear L2TPv3 UDP path outside the intended protected policy;
- account for dynamic/static L2TP UDP port behavior.

For broader protected underlay:

- routes/ACLs must force endpoint traffic into IPsec;
- remove or reject alternate clear route.

## 8. NAT behavior

### IPsec NAT-T

NAT between IPsec peers may move the outer ESP protection to UDP 4500.

### Inner L2TPv3

The inner direct-IP protocol 115 or UDP packet is protected before traversing the external NAT in the common endpoint composition. Its original endpoint/port identity is visible after peer IPsec decapsulation.

Do not confuse NAT-T's outer UDP port with L2TPv3 UDP port 1701/selected ports.

## 9. Rekey

IKE/CHILD SA rekey occurs below the pseudowire. A correct implementation should keep the protected selector/route valid throughout transition.

Tests:

- active Ethernet traffic during rekey;
- no unprotected protocol115/UDP packet on underlay;
- old ESP SA cleanup;
- pseudowire tunnel/session remains coherent.

## 10. Failure states

Recommended state machine:

- `UnderlayReady`
- `IKEStarting`
- `IKEAuthenticated`
- `IpsecProtectionInstalling`
- `ProtectionReady`
- `L2tpv3StaticConfigured` or `L2tpv3ControlConnecting`
- `PseudowireSessionReady`
- `AttachmentReady`
- `Forwarding`
- `ProtectionLost`
- `ClearFallbackBlocked`
- `ControlFailed`
- `SessionFailed`
- `AttachmentFailed`

## 11. MTU

Overhead can include:

- L2TPv3 Session ID/Cookie/L2 sublayer;
- UDP if selected;
- L2TP outer IP;
- ESP IV/ICV/padding;
- NAT-T UDP;
- outer tunnel-mode IP header.

Calculate and test effective Ethernet frame size per topology.

## 12. Required packet proof

- IKE setup;
- XFRM/IPsec selector installation;
- direct protocol115 -> ESP encapsulation;
- UDP L2TPv3 -> ESP encapsulation;
- NAT-T outer UDP4500 case;
- dynamic L2TPv3 control inside protection;
- no clear protocol115/UDP after IPsec teardown;
- rekey;
- MTU/fragmentation/ECN.
