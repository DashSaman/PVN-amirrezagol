# L2TPv3/IPsec — Protected Peer / Operator UI

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

The v2 client UI category is a peer/operator UI for infrastructure deployments. Entry 010 should not appear as a normal consumer/mobile VPN protocol.

## 1. Recommended peer editor

### General

- Name
- Enabled
- Local Endpoint
- Remote Endpoint
- Protection Required — always true for entry 010
- Protection Mode:
  - Flow-selective IPsec
  - Protected-underlay IPsec

### L2TPv3

- Direct IP / UDP
- Static / Dynamic control backend
- Tunnel IDs
- Session IDs
- Cookies
- Sequencing/Reorder
- Pseudowire Type
- Interface Name

### IPsec

- IKE Backend
- IKE Version
- Peer Identity
- Authentication Method
- Credential Reference
- IKE Policy
- ESP Policy
- Lifetimes/Rekey
- NAT-T/effective transport

### Layer-2 Attachment

- Bridge/VLAN/port
- MTU
- STP
- VLAN policy
- MAC learning/filtering

## 2. Selector/protection preview

The UI should render a read-only policy preview so the operator can verify what is actually protected.

### Direct-IP example

`<local endpoint> -> <peer endpoint>, IP protocol 115`

### UDP example

`<local endpoint>:<actual local port> -> <peer endpoint>:<actual peer port>, UDP`

Dynamic UDP peers must show that selected ports may be negotiated/changed and that the selected backend must keep protection aligned.

### Protected-underlay example

Show:

- protected route/prefix/interface;
- IPsec gateway/peer;
- route lookup result for L2TPv3 endpoint;
- clear alternate route check.

## 3. Status dashboard

Separate columns/cards:

### Protection

- IKE: down/connecting/authenticated
- ESP/CHILD SA: down/installing/up/rekeying
- selector/route guard: invalid/valid
- NAT-T
- effective algorithms (safe names only)

### Pseudowire

- static configured / control connecting / session up
- tunnel/session IDs
- Cookie validation
- interface
- counters/errors/out-of-sequence

### Attachment

- bridge/VLAN state
- link state
- MTU
- forwarding status

### Overall

`FORWARDING` only when protection + pseudowire + attachment are all ready.

## 4. Fail-safe UX

If protection drops while pseudowire configuration remains:

- show red/amber `Protection Lost — forwarding blocked`;
- keep Layer-2 attachment blocked/detached according to backend implementation;
- offer `Repair Protection` and `Stop Protected Pseudowire`;
- never offer an automatic clear fallback.

If an admin intentionally wants plain L2TPv3, create/convert to an **entry 009** profile with an explicit security warning rather than weakening entry 010 in place.

## 5. Static peer-pair generation

A two-endpoint wizard can generate:

- local/peer tunnel and session IDs;
- 64-bit Cookies;
- matching pseudowire type/sequence options;
- endpoint addresses/UDP ports;
- separate IPsec identities/credentials/policies;
- direct protocol115 selector or protected-underlay route plan.

Export endpoint A and B configs separately with provenance and secret handling.

## 6. Dynamic control UI

For a full RFC3931 backend:

- L2TP Control State
- LCCE Peer Identity
- Control Authentication
- HELLO/liveness
- Session Negotiation
- Assigned IDs/Cookies
- Circuit Status

These remain independent from IKE peer identity/authentication.

## 7. Credential handling

### IPsec

- PSK/private key/certificate: secure-store references, write/replace only in ordinary UI.

### L2TPv3 control

- dynamic control secret: separate secure reference.

### Cookie

- static sensitive operational token; generated/randomized where appropriate, redact in ordinary logs.

### IDs

- tunnel/session IDs are ordinary technical configuration.

## 8. Validation

Block or warn on:

- `Protection Required` profile with no valid IPsec policy;
- direct mode modeled with a fake TCP/UDP port 115;
- UDP dynamic control with a selector that can only ever match fixed 1701/1701 without backend evidence;
- clear route to peer when protected-underlay mode is required;
- Cookie/ID mismatch in paired static configs;
- one-sided sequencing;
- attachment MTU exceeding effective protected MTU;
- loop-prone bridge/VLAN topology;
- weak legacy IPsec proposal outside an explicit compatibility exception.

## 9. Error categories

- IKE authentication failed
- IPsec proposal mismatch
- IPsec policy/XFRM install failed
- protected route invalid
- clear fallback detected
- L2TPv3 control/session failed
- Cookie mismatch
- pseudowire type mismatch
- attachment circuit failed
- protection lost
- rekey failed
- MTU/ECN/fragmentation issue
- vendor interoperability mismatch.

## 10. Consumer UI placement

Default consumer PVNetwork clients should not show entry 010.

Infrastructure/admin edition label:

`L2TPv3 over IPsec (Protected Layer-2 Pseudowire)`

with an `Advanced / Network Infrastructure` marker.

## 11. Persian/RTL

Keep technical values LTR:

- endpoint IPs;
- protocol 115;
- UDP ports;
- tunnel/session IDs;
- Cookies;
- IKE/ESP proposal names;
- interface/VLAN names;
- logs/XFRM policy text.

Persian labels/help remain RTL.

## 12. Remaining UI evidence

- actual Linux composed-adapter UI/state implementation;
- exact Cisco management mapping for a proven entry-010 composition;
- dynamic RFC3931 backend UI if selected;
- remote RBAC/audit implementation;
- real screenshots/version correspondence after implementation.
