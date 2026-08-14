# L2TPv3 — Ports, Encapsulation and Control/Session Establishment

Review date: 2026-08-14

Entry: 009 L2TPv3.

## 1. Direct IP encapsulation

RFC 3931 assigns IP protocol number **115** to L2TPv3-over-IP.

Characteristics:

- no TCP/UDP port exists in this mode;
- data header begins with Session ID followed by optional Cookie and L2-specific sublayer/payload;
- control messages use reserved Session ID zero before the control-message header;
- because there is no UDP checksum, RFC3931 recommends control-message authentication/integrity checking.

Firewall UI must show **IP protocol 115**, not invent “port 115.”

## 2. UDP encapsulation

RFC 3931 also supports L2TPv3 over UDP.

For a dynamically negotiated control connection:

- destination UDP port begins at registered **1701**;
- initiator source port may be 1701 or an ephemeral port;
- responder may choose a free source port;
- subsequent control/data associated with that control connection uses the selected port pair;
- this variable-port behavior affects NAT/firewall interoperability.

L2TPv3-over-UDP shares port/header space with L2TPv2 and uses the version field to distinguish protocol versions.

## 3. Static Linux `ip l2tp`

`ip l2tp` creates static version-3 tunnel/session objects and does not run the RFC3931 control protocol.

Both peers must be provisioned consistently with:

- local and peer tunnel IDs;
- local and peer session IDs;
- endpoint IP addresses;
- encapsulation `ip` or `udp`;
- UDP local/remote ports when UDP is selected;
- local and peer cookie values/lengths when used;
- pseudowire type;
- sequencing/reorder settings;
- interface name.

There is no SCCRQ/SCCRP/SCCCN negotiation to repair mismatched static parameters.

## 4. Dynamic RFC3931 control connection

A full signaled L2TPv3 control plane uses the reliable control-message protocol derived from L2TP concepts.

High-level stages:

### Control connection setup

- SCCRQ — Start-Control-Connection-Request;
- SCCRP — Start-Control-Connection-Reply;
- SCCCN — Start-Control-Connection-Connected.

Control messages negotiate/advertise LCCE capabilities and establish the control connection.

### Session establishment

Session/call messages such as ICRQ/ICRP/ICCN or OCRQ/OCRP/OCCN create pseudowire sessions according to the service/call direction.

Session negotiation can exchange:

- pseudowire type;
- local/peer session identifiers;
- cookies;
- circuit status;
- Layer-2-specific parameters.

### Liveness

HELLO messages can maintain/check a dynamic control connection.

### Teardown

CDN terminates a session; StopCCN terminates the control connection.

## 5. ql2tpd limited control behavior

Current go-l2tp documentation states ql2tpd primarily creates static L2TPv3 sessions. With `hello_timeout`, it can send periodic keepalive through a **minimal** implementation of RFC3931 reliable control transport, intended when the peer is also ql2tpd.

Do not advertise ql2tpd as full dynamic interoperability with Cisco or arbitrary RFC3931 LCCEs without exact tests.

## 6. Session ID and cookie

### Session ID

32-bit identifier used to map incoming data packets to a session.

### Cookie

Optional maximum 64-bit value. With dynamic control, cookies can be exchanged during session establishment. Static deployments configure local/peer values manually.

Cookie validation is anti-spoof/misdirection hardening, not encryption.

## 7. Ethernet pseudowire

RFC 4719 defines Ethernet/Ethernet-VLAN frame transport over L2TPv3.

Conceptual frame path:

`Ethernet frame`

`-> optional L2-specific sublayer`

`-> L2TPv3 Session ID/Cookie header`

`-> IP protocol 115 OR UDP/IP`

`-> underlay network`

At the peer the L2TPv3 header is removed and the Ethernet frame is delivered to the attachment-circuit/netdevice context.

## 8. Sequencing / reordering

L2TPv3 may use sequencing depending pseudowire/session configuration and service mapping. Linux `ip l2tp` exposes send/receive sequence controls and reorder timeout.

Do not enable one-sided sequencing. Peer behavior and pseudowire-specific RFC requirements must match.

## 9. MTU / fragmentation

L2TPv3 adds Session ID, optional Cookie, optional L2-specific sublayer, plus outer IP and optional UDP overhead.

RFC3931 warns that fragmentation/reassembly is expensive and recommends proper MTU/PMTU engineering.

For Ethernet pseudowire, test:

- untagged and VLAN-tagged full-size frames;
- jumbo frames only if explicitly supported end-to-end;
- DF/PMTU behavior;
- direct-IP vs UDP overhead;
- IPsec overhead separately for entry 010.

## 10. NAT behavior

Direct L2TPv3 over IP protocol 115 is less NAT-friendly than UDP encapsulation. UDP mode can traverse NAT more naturally but still uses negotiated/dynamic port behavior that firewalls/NATs must track correctly.

Do not promise direct-IP traversal through arbitrary NAT.

## 11. L2TPv2/v3 fallback

RFC3931 defines an automatic L2TPv2 fallback mechanism only for the UDP case.

Product rule:

- fallback must be explicit/capability-driven;
- do not silently change a Layer-2 pseudowire into a legacy L2TPv2 remote-access/session mode;
- no deterministic automatic fallback exists from direct-IP L2TPv3 to L2TPv2/UDP merely by waiting for a timeout.

## 12. Cisco static/signaled models

Current Cisco IOS XE documentation exposes:

### Signaled

- `pseudowire-class`;
- `encapsulation l2tpv3`;
- `protocol l2tpv3 [class]`;
- L2TP class for control authentication/HELLO/cookie policy;
- `xconnect` attachment circuit to remote peer/VC.

### Static/manual

- `protocol none`;
- manual local/remote L2TP IDs;
- local/remote cookies;
- `xconnect ... manual`.

This is a strong interoperability target for Linux static and future dynamic control-plane testing.

## 13. Suggested normalized state model

- `UnderlayResolving`
- `UnderlayReachable`
- `ControlConnecting` — dynamic only
- `ControlAuthenticated` — dynamic/auth enabled
- `SessionNegotiating`
- `SessionEstablished`
- `PseudowireInterfaceReady`
- `AttachmentCircuitBound`
- `Forwarding`
- `HelloTimeout`
- `CookieMismatch`
- `SessionIdMismatch`
- `PseudowireTypeMismatch`
- `ControlAuthFailed`
- `MTUFailure`
- `UnderlayUnavailable`

Static mode can skip control states but must explicitly show `StaticConfigured` so diagnostics do not wait for control messages that will never exist.

## 14. Required packet/lab evidence

- direct IP protocol 115 Linux-to-Linux;
- UDP Linux-to-Linux with selected port behavior;
- static IDs/cookies mismatch negatives;
- Ethernet pseudowire frame capture;
- VLAN frame transport;
- sequence/reorder tests;
- MTU/PMTU/fragmentation;
- Linux static -> Cisco static;
- dynamic/signaled Cisco or another full-control peer;
- ql2tpd HELLO-only behavior and peer restriction;
- explicit proof plain L2TPv3 is not confidential;
- entry-010 IPsec-protected captures separately.
