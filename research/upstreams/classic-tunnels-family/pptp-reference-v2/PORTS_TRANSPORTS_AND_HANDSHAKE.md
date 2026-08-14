# PPTP — Ports, GRE Data Channel and Session Establishment

Review date: 2026-08-14

Entry: 012 PPTP.

## 1. Two-channel architecture

PPTP uses two different transport mechanisms:

### Control channel

- TCP **1723** between client and server;
- PPTP control messages create/manage/tear down calls and exchange link/status information.

### Data channel

- enhanced GRE using IP protocol **47**;
- carries PPP frames for an established PPTP call;
- uses PPTP Call IDs plus sequence/ack information.

Critical rule:

**GRE 47 means IP protocol 47, not TCP/UDP port 47.**

A firewall that permits TCP1723 but blocks GRE cannot carry PPTP user traffic.

## 2. Initial control connection

Conceptual sequence:

1. resolve server/reachability;
2. establish TCP connection to server port 1723;
3. exchange PPTP Start-Control-Connection Request/Reply;
4. maintain the control connection with echo/control messages;
5. establish a call/session using the appropriate PPTP call-control messages;
6. both peers learn the PPTP Call IDs used to demultiplex GRE data.

Exact field/message values come from RFC2637/backend implementation; UI/business logic should not manually reimplement wire parsing.

## 3. Call setup

PPTP supports outgoing/incoming call-control concepts inherited from its original PAC/PNS architecture.

In common remote-access VPN use, client/server implementations establish a PPTP call over the control connection, after which PPP negotiation runs through the GRE data channel.

Normalize product state to:

- `TcpControlConnecting`
- `PptpControlEstablished`
- `PptpCallEstablishing`
- `GreDataReady`
- `PppNegotiating`
- `AuthenticatingUser`
- `MppeNegotiating`
- `NetworkConfiguring`
- `Connected`.

## 4. GRE/PPTP data header

PPTP uses an enhanced GRE header carrying data such as:

- payload length;
- Call ID;
- optional sequence number;
- optional acknowledgment number;
- encapsulated PPP payload.

The Call ID is essential for mapping GRE packets to the correct PPTP session, especially when multiple sessions share endpoint IP addresses.

## 5. PPP establishment

After GRE call establishment:

1. PPP LCP negotiates the link;
2. selected PPP authentication runs;
3. MPPE is negotiated if the profile/server requires it;
4. IPCP/IPv6CP or other NCP configures network-layer operation;
5. assigned address/routes/DNS become usable;
6. user traffic is carried as PPP inside PPTP GRE.

A PPP auth failure is not a PPTP TCP control failure.

## 6. Outbound user packet path

`application IP packet`

`-> PPP interface`

`-> optional PPP/MPPE encryption of payload according to negotiated profile`

`-> PPTP GRE header / Call ID`

`-> IP protocol 47`

`-> underlay`

TCP1723 remains a separate control connection and does not carry ordinary PPP data packets.

## 7. NAT and PPTP ALG/helper

PPTP is awkward through NAT because GRE has no TCP/UDP ports and multiple sessions are identified using PPTP Call IDs.

NAT devices often implement a PPTP helper/ALG that:

- inspects TCP1723 control messages;
- tracks/rewrites Call IDs as required;
- creates GRE mapping/state for the associated call.

Interoperability risks:

- ALG disabled/missing -> control may connect but GRE data fails;
- buggy ALG -> call ID collisions, one-way traffic or multi-client failures;
- nested/CGNAT -> multiple independent helpers/mappings;
- encrypted/opaque control changes would prevent helper operation, but PPTP control itself is not TLS encrypted.

Do not recommend enabling a broad legacy ALG unless the deployment actually needs PPTP and the device behavior is tested.

## 8. Multiple clients behind one NAT

A key lab case:

- two or more clients behind the same public NAT;
- independent TCP1723 control connections;
- GRE calls with separate Call IDs;
- server returns traffic to the correct internal client.

This requires correct NAT/helper behavior; successful single-client NAT does not certify multi-client NAT.

## 9. Firewall model

Legacy server edge generally needs:

- TCP1723 to the PPTP server;
- GRE IP protocol 47 between client/server;
- state/helper support when NAT is present;
- post-PPP routing/firewall access rules.

Do not open an imaginary UDP/TCP port 47.

## 10. Keepalive/control liveness

The PPTP control channel includes echo/liveness/control messages. Loss of the TCP control connection should eventually terminate related GRE/PPP session state.

A backend must clean stale GRE/Call-ID/NAT state after abnormal control loss.

## 11. Disconnect

Clean teardown conceptually:

1. stop application forwarding;
2. PPP link termination;
3. PPTP call clear/disconnect control;
4. control connection close when appropriate;
5. GRE/NAT helper state removed;
6. routes/DNS/interface removed;
7. server frees address/accounting state.

## 12. MTU

Overhead includes PPP/MPPE and PPTP GRE/IP headers. MTU issues can appear even though no UDP/TCP data header is used for user packets.

Test:

- IPv4/IPv6 if backend supports it;
- Ethernet 1500 underlay;
- MPPE enabled/disabled legacy modes;
- NAT/ALG path;
- PMTU/fragmentation;
- inner TCP MSS behavior.

## 13. Failure categories

- TCP1723 blocked/reset
- PPTP control negotiation failed
- call rejected
- GRE protocol47 blocked
- GRE Call-ID/NAT mapping failed
- multi-client NAT collision
- PPP LCP failed
- PPP user auth failed
- MPPE required/not negotiated
- address/NCP failed
- route/DNS failed
- stale ALG/session state
- MTU/fragmentation issue.

## 14. Required packet/runtime proof

- TCP1723 control capture;
- GRE protocol47 data capture;
- Call-ID mapping;
- PPP LCP/auth/MPPE/NCP sequence;
- wrong credential;
- GRE blocked while TCP control succeeds;
- single and multiple clients behind NAT;
- ALG enabled/disabled behavior;
- reconnect/teardown cleanup;
- MTU;
- exact Windows/RouterOS/Linux interop combinations retained for legacy use.
