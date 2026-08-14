# L2TP/IPsec — Data Path and Wire Flow

Review date: 2026-08-14

Entry: 008 L2TP/IPsec.

## Layered model

L2TP/IPsec is not one monolithic tunnel. Treat it as a composition:

`Application IP packet`

`-> PPP payload/session`

`-> L2TPv2 data message`

`-> UDP transport for L2TP`

`-> IPsec transport-mode protection (normally ESP; NAT-T outer UDP when required)`

`-> outer IP network`

The exact on-wire form depends on NAT traversal and implementation policy. Do not describe UDP/1701 as independently exposed plaintext application traffic in the protected remote-access design.

## Establishment sequence

A useful product state machine is:

1. resolve/reach peer;
2. establish IPsec/IKE protection and machine authentication;
3. establish L2TP control tunnel;
4. establish L2TP session/call;
5. negotiate PPP/LCP;
6. authenticate user through configured PPP/EAP/AAA path;
7. negotiate/assign network parameters;
8. install routes/DNS/policy;
9. carry application traffic;
10. tear down PPP/L2TP/IPsec state cleanly.

Implementations may overlap details, but UI/logging must not collapse these failure domains.

## IPsec boundary

Reuse the completed entries 004-007 IKE/IPsec dossier for IKE, ESP, NAT-T, algorithm policy and SA semantics. Entry 008 adds the L2TP/PPP composition; it does not redefine IPsec.

Important distinction:

- IKE SA / IPsec CHILD or data SA state;
- L2TP control tunnel state;
- L2TP session state;
- PPP authentication/addressing state.

All can fail independently.

## L2TP control vs data

L2TPv2 has control messages for tunnel/session lifecycle and data messages carrying PPP frames. Product telemetry should at minimum distinguish:

- control tunnel not established;
- control tunnel established, session failed;
- session established, PPP negotiation/auth failed;
- PPP up, routing/DNS/data path failed.

## NAT and encapsulation

For the IPsec layer, NAT traversal commonly moves ESP protection into UDP encapsulation on port 4500 after IKE/NAT detection. The L2TP layer remains inside the protected IPsec path.

Do not create a fake `L2TP over UDP 4500` protocol label: UDP/4500 belongs to IPsec NAT-T encapsulation, while L2TP has its own UDP/session semantics inside the protection boundary.

## Addressing and routing

The remote-access client can receive a PPP-assigned address and related parameters according to server implementation/policy. Server-side routing/NAT/firewall policy then controls access from that logical client address space.

Operational checks must include:

- address-pool exhaustion;
- duplicate/conflicting pool;
- route installation;
- DNS application;
- split/full-tunnel intent;
- server forwarding/firewall/NAT;
- cleanup after disconnect.

## MTU/MRU and overhead

L2TP + PPP + IPsec + optional NAT-T adds encapsulation overhead. A product must not hard-code one universal MTU without path testing. Record:

- physical/path MTU;
- PPP MTU/MRU policy;
- IPsec/NAT-T overhead;
- fragmentation/PMTUD symptoms;
- IPv4/IPv6 differences where supported by the selected stack.

Runtime packet captures are required before publishing exact overhead numbers for a certified platform combination.

## Failure classification

### Protection failures
- peer unreachable;
- IKE/authentication/certificate/PSK failure;
- proposal mismatch;
- NAT-T/interoperability failure;
- IPsec SA establishment/rekey failure.

### L2TP failures
- control connection timeout;
- tunnel/session rejection;
- source-port/NAT implementation incompatibility;
- daemon/control-plane failure.

### PPP/AAA failures
- LCP negotiation failure;
- unsupported auth method;
- invalid user credential;
- RADIUS/AAA unavailable;
- address assignment failure.

### Post-connect failures
- route/DNS missing;
- firewall blocks;
- egress NAT missing;
- MTU/fragmentation black hole;
- stale state after network change.

## Observability contract

A future PVNetwork adapter should normalize events from native stacks/daemons without logging secrets. Suggested correlation keys:

- product profile id;
- connection attempt id;
- platform/backend/version;
- IPsec SA identifier or safe hash where available;
- L2TP tunnel/session identifiers;
- PPP interface/session identifier;
- assigned address;
- failure layer and stable product error code.

Do not log PSKs, private keys, PPP passwords, RADIUS secrets or derived session keys.

## Security posture

L2TP itself is not the confidentiality boundary in this composition; IPsec provides the protection. Therefore a configuration that accidentally permits unprotected L2TP exposure must be treated as a policy failure, not as an equivalent deployment mode.

Entry 008 remains a legacy compatibility target. Prefer a modern approved protocol for new deployments and require explicit operator action for legacy enablement.

## Execution evidence still required

- synchronized packet captures for native Windows/Apple/Linux clients against selected server stacks;
- NAT vs non-NAT traces;
- rekey and reconnect traces;
- L2TP control/session and PPP logs correlated with IPsec logs;
- MTU/fragmentation tests;
- route/DNS cleanup tests;
- negative tests proving unprotected/incorrectly protected L2TP is not accepted by the selected deployment policy.
