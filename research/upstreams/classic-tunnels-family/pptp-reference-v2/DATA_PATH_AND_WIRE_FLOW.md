# PPTP — Data Path and Wire Flow

Review date: 2026-08-14

Entry: 012 PPTP.

## 1. Client architecture

A legacy native/client flow is layered:

`PVNetwork legacy profile/UI`

`-> OS or selected PPTP adapter`

`-> TCP1723 control + GRE protocol47 call`

`-> PPP link/authentication`

`-> optional MPPE`

`-> native tunnel interface/routes/DNS`

PPTP control, GRE data and PPP security are independent state domains.

## 2. Outbound data path

After connection:

1. application IP packet follows the VPN route;
2. PPP encapsulates network-layer traffic;
3. if negotiated, MPPE processes the PPP payload according to legacy PPP policy;
4. PPTP GRE encapsulates the PPP packet using the call's Call ID and sequence/ack state;
5. the packet leaves as IP protocol 47;
6. NAT/PPTP helper may translate/track Call IDs and endpoint state;
7. server receives GRE, maps the Call ID to a PPTP/PPP session;
8. server decapsulates PPP/MPPE and forwards/routes the recovered network packet.

TCP1723 is not in this ordinary user-data path after the call is established; it remains the control/liveness channel.

## 3. Inbound data path

`server routed packet`

`-> PPP session`

`-> optional MPPE`

`-> PPTP GRE Call ID`

`-> IP protocol47`

`-> client GRE call lookup`

`-> PPP/MPPE decapsulation`

`-> tunnel interface`

`-> client application route`

One-way GRE/NAT/helper failures can therefore produce a connection that looks partially established but carries no usable traffic.

## 4. Windows native path

For native Windows PPTP profiles:

- Windows owns PPTP control/GRE/PPP/MPPE integration;
- PVNetwork should manage supported native profile/status interfaces rather than reimplement legacy protocol code;
- Windows route/DNS/interface state is system-owned and can change externally.

Product state must reconcile profile deletion/disconnect from Windows UI.

## 5. Linux historical composition

Typical legacy Linux client/server stacks split components:

### Client

`pptp client process`

`-> GRE/control`

`-> pppd`

`-> auth/MPPE/network scripts`

### Server

`pptpd`

`-> pppd`

`-> Linux routing/NAT/firewall`

Exact daemon/process/file-descriptor integration depends on the selected historical implementation and must be audited before any lab use.

## 6. RouterOS path

MikroTik RouterOS owns the entire PPTP/PPP client or server lifecycle inside the network OS:

- PPTP control/GRE;
- PPP profiles/auth;
- address assignment;
- MPPE policy;
- routes/firewall/NAT;
- active session status.

Do not infer Linux or Windows internal behavior from RouterOS success.

## 7. NAT/PPTP helper path

For a client behind NAT:

`TCP1723 control`

`-> NAT helper inspects call setup`

`-> helper creates Call-ID/GRE mapping`

`-> GRE protocol47 packets mapped to correct internal client`

This creates failure modes absent from normal TCP/UDP tunnels:

- helper unavailable;
- helper sees only one client correctly;
- stale Call-ID mapping;
- nested NAT/CGNAT conflicts;
- symmetric/asymmetric route issues.

## 8. No security inference from control success

A successful TCP1723 control connection proves only that control traffic is reachable. It does not prove:

- GRE passes;
- PPP auth succeeds;
- MPPE is active;
- address/routes/DNS work;
- data is acceptably secure.

UI must show the exact layer.

## 9. Reconnect/network change

PPTP's TCP control + GRE call state is tied to endpoint/NAT mappings. IP/network changes commonly require full call reconnect.

On reconnect:

- old TCP control must close/time out;
- old GRE Call-ID/helper state must expire/remove;
- PPP session/address is released;
- new control/call IDs/auth/network config are established;
- duplicate stale routes/interfaces must not remain.

## 10. Address/DNS/routes

PPP NCP/server policy delivers or establishes client networking. Record effective:

- assigned address;
- peer/gateway;
- DNS;
- full/split routes;
- route metric;
- IPv6 status if selected implementation supports it.

Do not infer these from the PPTP control state.

## 11. Observability

Safe telemetry can include:

- TCP control state;
- server endpoint;
- local/peer Call IDs;
- GRE packets/bytes/errors;
- PPP LCP state;
- auth method;
- MPPE active/mode identifiers where backend safely exposes them;
- assigned address/DNS/routes;
- NAT/helper suspected/known state;
- reconnect count.

Never log passwords or MPPE/runtime key material.

## 12. Cleanup

Disconnect/crash cleanup should:

1. block new tunnel forwarding;
2. terminate PPP;
3. clear PPTP call/control state;
4. close TCP1723;
5. remove GRE/helper/NAT state owned by endpoint/network device where possible;
6. remove tunnel interface/routes/DNS;
7. server releases addresses/accounting.

## 13. Legacy migration telemetry

Because PPTP should be retired, an operator dashboard should additionally track:

- active PPTP users/devices;
- last successful connection;
- target replacement protocol/profile;
- migration status;
- last required legacy dependency.

Avoid collecting unnecessary personal browsing metadata.

## 14. Required runtime evidence

- control + GRE + PPP packet/state correlation;
- Windows native to legacy server;
- RouterOS combinations retained in scope;
- Linux historical client/server only if required;
- GRE-blocked and TCP-only partial-connect failure;
- NAT/helper multi-client behavior;
- MPPE/auth negotiation;
- routes/DNS;
- reconnect/stale-state cleanup;
- migration cutover to modern protocol.
