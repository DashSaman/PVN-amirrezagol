# SoftEther VPN Protocol — Data Path and Wire Flow

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## 1. Native client path

`application/IP/Ethernet traffic`

`-> SoftEther virtual network adapter`

`-> native SoftEther session`

`-> TLS-protected TCP connection(s)`

`-> SoftEther VPN Server`

`-> Virtual Hub`

`-> local bridge / SecureNAT / cascade / other configured destination`

The native protocol transports virtual Ethernet semantics through the SoftEther session architecture; routing/NAT/bridge behavior is a server/client networking feature layered around the protocol.

## 2. Outbound flow

1. OS sends traffic to the SoftEther virtual adapter according to routes/interface state;
2. client/session code converts virtual adapter traffic into native SoftEther packet/session representation;
3. connection layer sends it through the established TLS/TCP connection set;
4. server authenticates/maps the session to a Virtual Hub;
5. hub switching/policy processes the virtual Ethernet frame;
6. destination can be another hub session, local bridge, SecureNAT or cascade according to server configuration.

## 3. Inbound flow

The server reverses the same ownership chain and delivers hub traffic through the authenticated native session to the client's virtual adapter, after which the OS network stack routes/delivers it to applications.

## 4. Virtual Hub boundary

The Virtual Hub is a Layer-2/session-policy boundary. Tunnel confidentiality does not replace hub security. Test MAC learning, broadcast/multicast behavior, user/group security policy, VLAN/service segmentation and bridge exposure separately.

## 5. SecureNAT/local bridge

### SecureNAT

Provides server-side virtual NAT/DHCP/network functions. Its performance/security profile differs from local bridge.

### Local bridge

Connects Virtual Hub traffic to a physical/tap/network interface and can expose the surrounding Layer-2 domain.

These are deployment choices, not native-protocol handshake modes.

## 6. Cascade/bridge roles

Cascade or VPN Bridge connections can create site-to-site/Layer-2 topologies using SoftEther-native sessions. Avoid loops and document which component owns each Virtual Hub/bridge.

## 7. Multiple TCP connections

Where the selected native profile uses multiple parallel TCP connections, treat them as one logical VPN session with multiple transport connections. Observe per-connection failure/recovery and ensure a partial connection set does not create duplicate packet delivery or stale state.

## 8. Client routing/DNS

Native VPN connection success is not equivalent to correct OS networking. Record effective virtual adapter addresses, DHCP/static parameters, routes, DNS, metrics and IPv6 separately.

## 9. Observability

Safe state:

- server/listener;
- TLS state/version;
- native session/Virtual Hub;
- authentication method name;
- connection count;
- virtual adapter state;
- packet/byte/error counters;
- assigned network configuration;
- bridge/SecureNAT/cascade destination class;
- reconnect count.

Do not expose credentials/private keys/session secrets.

## 10. Failure ownership

Separate:

- TCP/listener failure;
- TLS/certificate failure;
- native protocol/session negotiation failure;
- user authentication failure;
- Virtual Hub authorization failure;
- virtual adapter failure;
- route/DNS failure;
- local bridge/SecureNAT/cascade failure;
- multipath/parallel-connection degradation.

## 11. Cleanup

Disconnect must close session transport, remove client virtual-adapter transient state/routes/DNS owned by the connection, release server hub/session/accounting state and leave local bridge/SecureNAT shared resources intact unless the profile owns them.

## 12. Required runtime evidence

- native packet/session trace on exact client/server version;
- Virtual Hub switching between native peers;
- local bridge and SecureNAT separately;
- parallel connection failure/recovery;
- route/DNS cleanup;
- IPv4/IPv6;
- MTU/fragmentation;
- crash/restart and server reboot.
