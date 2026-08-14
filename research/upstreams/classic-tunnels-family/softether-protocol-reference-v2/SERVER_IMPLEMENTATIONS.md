# SoftEther VPN Protocol — Server Implementations

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

## 1. Canonical server

Primary implementation:

- `SoftEtherVPN/SoftEtherVPN@49eb2f08641709d1af57a0d04971973ff94461db`
- Apache-2.0 root license at reviewed pin.

Relevant source families include Cedar `Server`, `Protocol`, `Connection`, `Session`, `Hub` and packet/networking components.

Role: canonical open-source native SoftEther client/server protocol implementation.

## 2. Native server architecture

Conceptual flow:

`TCP listener`

`-> TLS/native SoftEther protocol negotiation`

`-> authentication`

`-> session creation`

`-> Virtual Hub`

`-> virtual Ethernet frame forwarding`

`-> local bridge / SecureNAT / cascade / other configured server-side networking`

Virtual Hub/session/networking functions are server architecture above/below the wire protocol; keep them distinct in diagnostics and UI.

## 3. Listener model

SoftEther VPN Server can expose configurable TCP listeners. Common product defaults include 443, 992 and 5555, but exact listener set is administrator/release configuration, not a protocol invariant.

The same server may also expose compatibility services. Entry 013 support means the native SoftEther protocol path is enabled/tested, not merely that SoftEther Server is installed.

## 4. Authentication/authorization

SoftEther's Virtual Hub/user model can support multiple authentication backends/methods according to server configuration, including local user credentials/certificates and external enterprise authentication features where supported.

Separate:

- TLS server identity/trust;
- native protocol/session authentication;
- Virtual Hub user/group policy;
- RADIUS/NT-domain/external backend credentials/settings where selected.

## 5. Server networking

After authentication, native sessions exchange virtual Ethernet traffic through a Virtual Hub. The administrator may attach the hub to:

- local bridge/physical NIC;
- SecureNAT virtual NAT/DHCP;
- cascade connections;
- other SoftEther server/bridge networking features.

These change reachable networks and security boundaries but are not changes to the native protocol identity.

## 6. SoftEther VPN Bridge

SoftEther VPN Bridge uses the same project/protocol family for site/bridge roles and can connect virtual hubs/physical L2 networks. Treat it as an endpoint/server-side deployment role requiring separate privilege/bridge/loop testing.

## 7. Proprietary/third-party implementations

No independent full native SoftEther protocol implementation is promoted here. Compatibility should be assumed only for exact SoftEther-derived/native products with source/vendor evidence.

Do not infer native SoftEther protocol support from OpenVPN/SSTP/L2TP compatibility clients.

## 8. Reuse direction

PVNetwork should prefer a narrow adapter around the maintained SoftEther native components/service rather than reimplementing protocol cryptography/session framing.

Direct source reuse must preserve Apache-2.0 and third-party component obligations and avoid copying SoftEther branding/UI.

## 9. Remaining evidence

- refresh exact selected current release/tag from upstream before implementation;
- exact TLS/library/provider inventory at selected build;
- full native handshake/session source map;
- performance/resource limits;
- fuzz/security advisories;
- exact clustering/cascade behavior;
- current supported OS/package matrix.
