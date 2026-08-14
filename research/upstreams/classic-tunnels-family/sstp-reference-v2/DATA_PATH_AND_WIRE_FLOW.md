# SSTP / MS-SSTP — Data Path and Wire Flow

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## 1. Product/native architecture

Recommended Windows-native product flow:

`PVNetwork profile/UI`

`-> Windows VPN Adapter`

`-> native Windows VPN/SSTP profile`

`-> Windows TLS + SSTP + PPP/RAS stack`

`-> native VPN interface/routes/DNS`

PVNetwork should own canonical profile/provisioning/status, not reimplement native MS-SSTP crypto/framing when the Windows platform already provides it.

## 2. Windows outbound packet path

Conceptually after connection:

1. application emits an IP packet;
2. Windows route/policy sends it to the active VPN/PPP interface;
3. PPP frames the packet according to negotiated network-control state;
4. SSTP wraps PPP as an SSTP data packet;
5. TLS protects SSTP control/data bytes;
6. TLS records traverse the long-lived TCP connection to server port 443;
7. RRAS reverses TLS/SSTP/PPP processing;
8. server routing/firewall/NAT forwards the recovered packet.

The app does not handle TLS session keys or SSTP crypto-binding secret material when using native Windows SSTP.

## 3. Inbound packet path

`server-side routed packet`

`-> PPP session`

`-> SSTP data packet`

`-> TLS/TCP`

`-> Windows client TLS/SSTP`

`-> PPP decapsulation`

`-> native VPN interface`

`-> client routing/application`

Server/application reachability still depends on RRAS/SoftEther routing and access policy after SSTP has connected.

## 4. Linux sstp-client composition

Typical architecture:

`Linux route`

`-> PPP interface/pppd`

`-> sstp-client SSTP transport`

`-> TLS library`

`-> TCP443`

The selected client may coordinate with pppd through plugin/process/file-descriptor/control mechanisms. Exact selected release source must be pinned before implementation.

Failure ownership must separate:

- TLS/certificate;
- SSTP control;
- pppd/LCP/auth;
- route/DNS scripts.

## 5. SoftEther server path

Conceptually:

`TLS listener`

`-> SoftEther SSTP compatibility handler`

`-> authenticated user/session`

`-> SoftEther Virtual Hub/networking`

`-> local bridge/SecureNAT/routing`

Exact internal ownership is implementation-specific; do not assume RRAS architecture or Windows HTTP.sys semantics apply to SoftEther.

## 6. Startup transaction

Recommended product-visible states:

1. `ProfileValidated`
2. `EndpointResolved`
3. `TcpConnected`
4. `TlsValidated`
5. `SstpTransportAccepted`
6. `SstpCallNegotiating`
7. `PppLinkReady`
8. `UserAuthenticated`
9. `CryptoBindingValidated`
10. `NetworkConfigured`
11. `Connected`

A backend may order internal substeps differently according to [MS-SSTP], but UI/errors should preserve these ownership domains.

## 7. TLS/channel binding path

The TLS handshake establishes the protected server channel first. SSTP then negotiates control attributes and later validates the protocol crypto-binding/channel-binding information associated with the authenticated PPP/SSTP session.

Do not terminate/restart TLS in an unsupported intermediary and assume the binding still proves the intended server endpoint.

## 8. Proxy path

When a client uses an HTTP proxy:

`client`

`-> proxy TCP/auth`

`-> CONNECT tunnel`

`-> end-to-end TLS to SSTP server`

`-> SSTP/PPP`

If the proxy intercepts TLS rather than tunneling it transparently, server certificate/channel binding behavior changes and must be explicitly supported/tested rather than silently trusted.

## 9. TCP-over-TCP behavior

User TCP traffic can be nested inside SSTP's outer TCP stream. Under packet loss:

- outer TCP retransmission/head-of-line blocking can delay unrelated inner packets;
- inner TCP also performs retransmission/congestion control;
- latency/jitter can increase substantially on lossy paths.

Benchmark loss/reordering conditions rather than measuring only LAN throughput.

## 10. Network change/reconnect

A TCP-based SSTP session normally depends on its established outer TCP/TLS connection. Network handover/IP change can break the stream and require reconnect/re-authentication.

PVNetwork should model:

- outer connection lost;
- native backend reconnecting;
- credentials/certificate still valid;
- routes/DNS removed/reinstalled correctly;
- no stale duplicate VPN interfaces/routes.

Do not infer WireGuard/IKEv2-style mobility semantics.

## 11. Address/routing/DNS

PPP/NCP and server/platform policy can deliver address/network parameters. Product state should record effective:

- assigned client address;
- DNS servers/search policy;
- full vs split tunnel routes;
- IPv4/IPv6 capability;
- default route/metric;
- proxy settings if applicable.

Do not assume server authentication success means DNS/routes were installed.

## 12. MTU/MSS

Effective packet budget includes PPP, SSTP, TLS, TCP and IP overhead. Additional proxy/network encapsulation can further reduce available size.

Test:

- IPv4/IPv6;
- direct/proxy path;
- common Ethernet 1500 underlay;
- PMTU black-hole scenarios;
- MSS adjustment only when evidence supports it.

## 13. Server-side routing

After RRAS/SoftEther accepts PPP traffic, server policy still controls:

- access to private subnets;
- Internet egress/NAT;
- inter-client traffic;
- firewall/ACL/NPS authorization;
- DNS resolution path.

Keep these failures separate from SSTP protocol state.

## 14. Observability

Safe telemetry:

- endpoint hostname/IP class;
- proxy used yes/no;
- TCP/TLS stage;
- TLS version/cipher;
- certificate fingerprint/issuer/expiry metadata;
- SSTP control state;
- crypto-binding validated yes/no;
- PPP auth method name;
- assigned addresses/routes/DNS;
- bytes/packets/session uptime;
- reconnect count/error category.

Never collect TLS keys, private keys, passwords, RADIUS secrets or crypto-binding secret material.

## 15. Cleanup

On disconnect/failure:

1. block new user forwarding;
2. terminate SSTP/PPP according to backend;
3. close TLS/TCP;
4. remove native VPN interface/session;
5. restore routes/DNS/proxy-related temporary state;
6. clear transient credentials/session state;
7. server releases address/accounting state.

## 16. Required runtime evidence

- Windows native client <-> RRAS packet/state trace;
- Windows native client <-> SoftEther;
- Linux sstp-client <-> RRAS/SoftEther;
- certificate rotation/invalid cert;
- crypto-binding negative/success;
- PPP auth failure;
- proxy CONNECT and proxy-TLS-intercept negative;
- network handover/reconnect;
- split/full routing/DNS;
- loss/TCP-over-TCP performance;
- MTU/IPv6;
- cleanup after process/network crash.
