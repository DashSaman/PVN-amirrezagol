# IKE / IPsec — Ports, Transports and Handshake / SA Establishment

Review date: 2026-08-14

Scope: entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP, 007 AH.

This file separates IKE control traffic from IPsec data-plane traffic and records NAT traversal explicitly.

## 1. IKEv2 transport — entry 004

RFC 7296 defines IKEv2 over UDP. Normal IKE traffic uses UDP port 500; IKE also uses UDP port 4500 with the NAT-T/demultiplexing format defined by the IKEv2/NAT traversal specifications.

Important wire rule:

- UDP/500: IKE message follows the UDP header;
- UDP/4500: IKE messages use a four-zero-byte non-ESP marker before the IKE header so they can share the port with UDP-encapsulated ESP;
- IKE is datagram-oriented and includes its own retransmission/message-ID behavior rather than assuming a reliable stream transport.

Firewall/product UI should therefore show IKE control ports separately from raw ESP/AH IP protocol numbers.

## 2. IKEv2 initial establishment

At the base RFC 7296 level, the initial IKEv2 setup is conceptually:

### IKE_SA_INIT

Negotiates the IKE SA cryptographic proposal and exchanges key-exchange/nonces. NAT-detection notifications and extensions may be carried here.

### IKE_AUTH

Authenticates peers and establishes the first CHILD_SA/IPsec data SA, including traffic selectors and data-plane transform negotiation.

The product state machine should therefore distinguish at least:

`Starting -> IKE_SA_INIT -> Authenticating/IKE_AUTH -> IKE established -> CHILD/data SA established -> Connected`

A failure to install a CHILD SA is not identical to failure to establish/authenticate the IKE SA.

## 3. IKEv2 ongoing exchanges

### CREATE_CHILD_SA

Used to create/rekey CHILD SAs and to rekey the IKE SA according to RFC 7296 semantics.

### INFORMATIONAL

Carries liveness/delete/error/status-related protected control information according to the protocol.

### Extensions

- RFC 7383: encrypted IKEv2 message fragmentation for large protected exchanges;
- RFC 9370: multiple key exchanges, including IKE_INTERMEDIATE / follow-up key-exchange mechanisms;
- RFC 9593: supported-authentication-method announcements.

Capability detection must be extension-aware; do not assume every IKEv2 server supports each extension.

## 4. NAT detection and UDP/4500

IKEv2 is designed to operate through NAT. RFC 7296 defines NAT detection and port agility; when NAT traversal is in use, UDP/4500 can carry both IKE and UDP-encapsulated ESP.

Key distinction:

- IKE packet on UDP/4500 -> begins with the four-zero-byte non-ESP marker;
- UDP-encapsulated ESP -> ESP SPI follows UDP without that IKE non-ESP marker;
- NAT detection and the chosen encapsulation are negotiated/handled by the endpoints/backend, not by PVNetwork rewriting protocol packets itself.

Do not confuse UDP/4500 with a separate VPN protocol.

## 5. ESP transport — entry 006

Without UDP encapsulation, ESP is an IP-layer protocol identified by IP protocol number **50**. It is not TCP/UDP and does not have a TCP/UDP port.

RFC 4303 packet format includes fields/concepts such as:

- Security Parameters Index (SPI);
- sequence number;
- payload data;
- padding/pad length;
- next-header indication;
- integrity/authentication data depending transform mode.

ESP can operate in transport mode or tunnel mode.

### NAT-T / UDP-encapsulated ESP

RFC 3948 defines encapsulation of ESP inside UDP for NAT traversal. The normal NAT-T path shares the UDP port used for NAT-T IKE traffic (commonly 4500) and distinguishes ESP by packet format/SPI from IKE's non-ESP marker.

When NAT is detected in an IKEv2 NAT-T exchange, UDP encapsulation is required by the IKEv2 NAT-traversal behavior described in RFC 7296.

This changes the outer transport encapsulation; it does not turn ESP into an application-layer UDP VPN protocol.

## 6. AH transport — entry 007

AH is identified by IP protocol number **51**. It is also not TCP/UDP and has no TCP/UDP port.

RFC 4302 defines the AH header including:

- next header;
- payload length;
- SPI;
- sequence number;
- Integrity Check Value.

AH authenticates/protects its specified coverage but does not encrypt payload. Because AH's integrity coverage includes selected IP-header fields, address translation is fundamentally problematic for normal NAT deployment. Do not promise generic NAT-T for AH by analogy with ESP.

## 7. IKEv1 establishment — entry 005

RFC 2409 is historic and IKEv1 is deprecated by RFC 9395.

Legacy IKEv1 uses a phase/mode model distinct from IKEv2:

### Phase 1

- Main Mode or Aggressive Mode establishes/authenticates the ISAKMP/IKE SA.

### Phase 2

- Quick Mode negotiates IPsec SAs/keying material for ESP/AH/data protection.

This terminology must remain IKEv1-specific. Do not call IKEv2 IKE_AUTH/CREATE_CHILD_SA “Phase 1/Phase 2” internally because it causes protocol/state confusion.

IKEv1 NAT traversal is defined by older NAT-T specifications such as RFC 3947/3948 and uses the UDP/4500/non-ESP-marker pattern after NAT-T negotiation.

## 8. Traffic selectors and SA directionality

IPsec data SAs are directional security associations. A working bidirectional tunnel involves state/policy in both directions. IKE negotiates traffic selectors and data-SA parameters; the OS/backend installs corresponding policy/SA state.

PVNetwork diagnostics should preserve:

- local/remote traffic selectors;
- effective tunnel/transport mode;
- inbound/outbound SPI/state where safely exposable;
- data-SA lifetime/rekey state;
- whether NAT-T/UDP encapsulation is active;
- backend/kernel install status.

Do not expose raw secret keying material.

## 9. Firewall/network requirements model

A generic checklist must distinguish:

- UDP 500 — IKE;
- UDP 4500 — IKE NAT-T / UDP-encapsulated ESP where used;
- IP protocol 50 — native ESP when not UDP encapsulated;
- IP protocol 51 — AH if intentionally used;
- routes/firewall policies/forwarding/NAT beyond the cryptographic protocol;
- certificate/EAP/identity infrastructure as separate dependencies.

Do not tell operators that opening only “port 500” completes IPsec reachability.

## 10. Fragmentation and MTU

Large IKE_AUTH messages, particularly certificate-heavy exchanges, can exceed path MTU. RFC 7383 defines IKEv2 message fragmentation for protected IKE messages to reduce reliance on IP fragmentation.

Separately, ESP/AH/tunnel headers reduce effective payload MTU. Deployment testing must include PMTU/fragmentation/IPv6 behavior rather than simply lowering MTU globally without evidence.

## 11. State/diagnostic normalization for PVNetwork

Suggested protocol-aware diagnostics:

- `ResolvingEndpoint`
- `IKETransportReady`
- `IKE_SA_INIT`
- `IKE_AUTH`
- `IKEEstablished`
- `ChildSAInstalling`
- `DataSAEstablished`
- `Connected`
- `RekeyingIKE`
- `RekeyingChild`
- `NatTraversalActive`
- `AuthFailed`
- `NoProposalChosen`
- `TrafficSelectorMismatch`
- `KernelPolicyInstallFailed`
- `PeerDeletedSA`
- `NetworkUnavailable`

For IKEv1, use legacy-mode details in diagnostics while mapping to equivalent product-level stages; do not fake IKEv2 stages.

## 12. Reference set

- RFC 7296 — IKEv2
- RFC 7383 — IKEv2 message fragmentation
- RFC 9370 — multiple IKEv2 key exchanges
- RFC 9593 — supported authentication methods
- RFC 2409 — historic IKEv1
- RFC 3947 — IKE NAT traversal negotiation
- RFC 3948 — UDP encapsulation of ESP
- RFC 4303 — ESP
- RFC 4302 — AH
- RFC 4301 — IPsec architecture

## 13. Remaining execution evidence

Before platform/server certification:

- packet captures from selected server/client combinations showing UDP/500 -> 4500/NAT-T behavior where applicable;
- IKEv2 extension negotiation receipts;
- IKEv1 legacy-mode captures only in isolated compatibility labs;
- ESP native vs UDP-encapsulated data-path verification;
- AH reachability tests only on intentionally non-NAT topologies;
- MTU/fragmentation and rekey/failover receipts;
- exact firewall/routing cleanup on disconnect/uninstall.
