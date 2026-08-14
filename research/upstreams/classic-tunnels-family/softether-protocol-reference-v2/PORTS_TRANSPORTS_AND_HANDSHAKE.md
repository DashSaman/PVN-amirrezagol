# SoftEther VPN Protocol — Ports, Transport and Native Session Establishment

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## 1. Transport model

The canonical native SoftEther path uses TCP listeners and TLS-protected client/server transport. SoftEther VPN Server listeners are configurable; commonly shipped/default listener choices historically include TCP 443, 992 and 5555.

These are product listener defaults, not three mandatory native protocol ports. Record the actual configured listener for every profile/server.

## 2. Connection stages

Normalize the native path as:

1. resolve/connect server TCP listener;
2. establish/validate TLS channel;
3. exchange native SoftEther protocol/session negotiation;
4. authenticate client/user according to selected Virtual Hub policy;
5. select/enter Virtual Hub and create session;
6. negotiate effective connection/session parameters;
7. attach client virtual adapter/session to the hub;
8. transfer virtual Ethernet packets;
9. apply server-side local bridge/SecureNAT/cascade/network policy.

Wire field ordering/details remain backend-owned in `Protocol.c` and related source; product UI/business code must not duplicate parsers from memory.

## 3. Multiple TCP connections

SoftEther native clients can expose connection-count/parallel-connection behavior in selected configurations/releases. Treat this as a backend capability affecting throughput, NAT/firewall state and failure handling.

Certify exact selected-release limits/semantics; do not hard-code one connection count globally.

## 4. Management traffic

`vpncmd` / Server Manager / Client Manager administration/control is separate from end-user native VPN data sessions. Management ports/listeners/authentication must be documented and firewalled independently.

## 5. Compatibility listeners

SSTP, L2TP/IPsec, OpenVPN-compatible and EtherIP listeners have different ports/handshakes and belong to their own entries. Do not count successful compatibility-mode connection as entry-013 native-protocol proof.

## 6. Firewall model

- allow only selected native SoftEther TCP listener(s);
- restrict management listeners separately;
- disable unused compatibility listeners;
- apply post-session Virtual Hub/bridge/routing policy separately from listener firewall.

## 7. TLS/proxy/NAT

TCP/TLS transport can traverse common routed/NAT networks, but long-lived sessions and multiple parallel TCP connections require NAT/firewall idle-state testing. Generic HTTPS proxy/TLS interception is not assumed compatible with the native protocol without exact implementation evidence.

## 8. Keepalive/reconnect

The native client/server stack owns connection liveness/reconnect and parallel connection failure behavior. Product state should separate:

- TCP/TLS connection loss;
- native session authentication failure;
- Virtual Hub/session loss;
- virtual adapter/network install failure.

## 9. Teardown

Safe conceptual teardown:

1. stop new virtual Ethernet forwarding;
2. close native session/connection(s);
3. remove virtual-adapter/session state;
4. restore routes/DNS if product/client changed them;
5. server releases session/accounting/hub state.

## 10. MTU

Effective overhead includes virtual Ethernet framing, native SoftEther protocol framing, TLS, TCP and IP. Exact framing/aggregation/compression behavior is release/profile-specific; measure rather than using one universal MTU.

## 11. Required packet/runtime evidence

- native SoftEther client -> server on selected listener;
- TLS/certificate validation;
- successful Virtual Hub/auth/session creation;
- single vs configured multi-connection behavior;
- packet transfer through virtual adapter/hub;
- connection loss/reconnect;
- disabled compatibility listeners remain unused;
- management plane isolated;
- NAT/firewall idle behavior;
- MTU/IPv4/IPv6 according to selected build.
