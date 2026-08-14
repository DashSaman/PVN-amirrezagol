# SSTP / MS-SSTP — Ports, Transport and Session Establishment

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## 1. TCP/TLS transport

SSTP is carried over an HTTPS/TLS connection, normally to TCP **443** on the server.

Conceptual stack:

`PPP frame/control`

`-> SSTP control or data packet`

`-> HTTP/HTTPS tunnel semantics`

`-> TLS`

`-> TCP 443`

`-> IP`

TCP443 reachability alone does not prove SSTP works. Firewalls/proxies can allow ordinary web browsing but reject or terminate the long-lived SSTP duplex request/tunnel.

## 2. TLS establishment

1. resolve server hostname;
2. establish TCP connection to the SSTP listener, normally 443;
3. perform TLS handshake;
4. validate server certificate/name/trust/revocation according to platform policy;
5. only after TLS succeeds, establish the SSTP HTTP/tunnel request and protocol control exchange.

If TLS fails, no SSTP/PPP session should proceed.

## 3. SSTP HTTP request boundary

MS-SSTP uses a dedicated SSTP duplex HTTP request rather than ordinary stateless web GET traffic. Implementations must follow the current Microsoft Open Specification for request method/URI/header semantics and response behavior.

Do not duplicate the method/URI from memory in product business logic. Keep wire formatting inside the backend/protocol implementation.

## 4. SSTP control messages

The MS-SSTP control protocol includes call establishment/teardown and liveness/control messages such as:

- Call Connect Request;
- Call Connect Acknowledgment;
- Call Connect Negative Acknowledgment;
- Call Connected;
- Call Abort;
- Call Disconnect;
- Call Disconnect Acknowledgment;
- Echo Request / Echo Response.

The exact message values, attributes and transition rules come from [MS-SSTP].

## 5. High-level connection sequence

Typical client sequence:

### Stage A — network/TLS

`DNS -> TCP443 -> TLS validated`

### Stage B — SSTP HTTP/tunnel setup

`SSTP duplex request -> server accepts tunnel transport`

### Stage C — SSTP call negotiation

`Call Connect Request/Ack/NAK and negotiated attributes`

### Stage D — PPP link

PPP LCP establishes the logical link inside SSTP.

### Stage E — PPP/EAP authentication

The selected user authentication method runs.

### Stage F — SSTP crypto binding / Call Connected

The protocol performs the required channel/crypto-binding validation and transitions the SSTP call into connected state according to the selected implementation/spec behavior.

### Stage G — PPP NCP/network configuration

IP-layer configuration, address, DNS/routes and other PPP/network settings become usable.

### Stage H — user traffic

PPP frames are carried as SSTP data packets through the TLS connection.

## 6. Ordering caution

Exact PPP/authentication/crypto-binding message timing is specification/backend defined. The product should model independent observable stages rather than reimplement a guessed state machine in UI code.

Recommended normalized states:

- `Resolving`
- `TcpConnecting`
- `TlsHandshaking`
- `TlsValidated`
- `SstpTransportStarting`
- `SstpCallNegotiating`
- `PppLinkNegotiating`
- `AuthenticatingUser`
- `CryptoBindingValidating`
- `NetworkConfiguring`
- `Connected`

## 7. Data packet path

Once connected:

`application IP packet`

`-> platform VPN/PPP interface`

`-> PPP frame`

`-> SSTP data packet`

`-> TLS record`

`-> TCP segment(s)`

`-> server`

The server reverses the stack and forwards/routes the resulting PPP/network traffic.

## 8. Proxy traversal

If an HTTP proxy is used, connection setup may include a proxy CONNECT/authentication stage before end-to-end TLS reaches the SSTP server.

State model should separate:

- `ProxyConnecting`
- `ProxyAuthenticated`
- `TlsHandshakingToSstpServer`

Do not label proxy-auth failure as SSTP or PPP authentication failure.

A TLS-intercepting enterprise proxy can alter the certificate/channel security model and must be tested explicitly; generic MITM acceptance is not allowed by default.

## 9. Teardown

A clean disconnect can include:

1. stop application forwarding;
2. SSTP Call Disconnect / acknowledgment according to state;
3. PPP link teardown;
4. close SSTP/TLS/TCP transport;
5. remove routes/DNS/native VPN interface state;
6. release address/session/accounting state on server.

Abnormal TCP/TLS loss requires timeout/cleanup on both peers.

## 10. TCP behavior implications

Because SSTP uses TCP as its outer transport:

- packet loss/retransmission is handled by TCP;
- tunneling traffic that itself uses TCP can experience TCP-over-TCP performance/pathology under loss or congestion;
- head-of-line blocking can affect multiplexed user traffic;
- proxy/firewall idleness timeouts can kill the tunnel;
- keepalive/echo behavior must be tested.

Do not market TCP443 traversal as automatically higher performance or reliability than UDP-based VPNs.

## 11. MTU/MSS

Overhead includes:

- PPP;
- SSTP framing;
- TLS records;
- TCP;
- IP.

Effective tunnel MTU/MSS must be tested on IPv4/IPv6, PPP auth/profile combinations and proxied/non-proxied paths. Avoid hard-coded universal values.

## 12. Firewall/listener model

### Server edge

Normally allow TCP443 to the SSTP service or supported fronting architecture.

If the same IP/443 also serves HTTPS websites or another product, listener sharing/HTTP.sys/SNI/reverse-proxy behavior must be product-specific and proven. Do not assume ordinary reverse proxy routing can safely demultiplex SSTP.

### Internal

Server PPP/addressed client traffic is governed by RRAS/SoftEther/Linux routing/firewall policy separately from the TCP443 listener.

## 13. Failure categories

- DNS failure
- TCP443 blocked/reset
- proxy authentication failed
- TLS certificate untrusted/name mismatch/expired/revocation error
- TLS policy/cipher mismatch
- SSTP HTTP/tunnel request rejected
- SSTP call rejected/control error
- PPP LCP failed
- PPP/EAP authentication failed
- SSTP crypto binding failed
- PPP address/network config failed
- route/DNS install failed
- TCP idle/proxy timeout
- MTU/MSS/congestion failure.

## 14. Required packet/runtime proof

- direct TCP443 TLS connection to Windows RRAS;
- native Windows client SSTP control/PPP establishment;
- SoftEther SSTP interop;
- Linux sstp-client interop;
- proxy/no-proxy path;
- invalid certificate negatives;
- crypto-binding verification/negative case;
- long-lived tunnel/echo behavior;
- reconnect/network loss;
- MTU and TCP-over-TCP loss behavior.
