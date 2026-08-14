# L2TPv3 — Peer / Operator UI and Menu Map

Review date: 2026-08-14

Entry: 009 L2TPv3.

For this infrastructure protocol, the v2 “client UI” category is correctly interpreted as **peer/operator UI**. There is no normal consumer mobile L2TPv3 profile screen.

## 1. Linux static peer UI — CLI-backed

Primary source-backed interface: iproute2 `ip l2tp` plus Linux link/bridge tooling.

Recommended PVNetwork infrastructure UI groups:

### General

- Pseudowire Name
- Enabled
- Local Peer
- Remote Peer
- Transport: Direct IP / UDP
- Control Mode: Static
- Pseudowire Type

### Tunnel

- Local Tunnel ID
- Peer Tunnel ID
- Address Family
- Local/Remote Address
- UDP Source/Destination Ports when selected
- Underlay VRF/Namespace

### Session

- Local Session ID
- Peer Session ID
- Local Cookie
- Peer Cookie
- Sequence Send/Receive
- Reorder Timeout
- L2-Specific Sublayer
- Interface Name

### Attachment

- Bridge / Port / VLAN
- PVID / Tagged VLANs
- MTU
- STP policy
- MAC learning/filtering

### Security

- Underlay classification: trusted/private vs untrusted
- Cookie length/status
- `Require IPsec` composed option linking to entry 010
- warning: plain L2TPv3 has no payload encryption.

### Status

- kernel tunnel/session exists
- interface up/down
- peer reachability
- packets/bytes/errors/drops/out-of-sequence
- bridge/VLAN membership
- protection state if composed with entry 010.

## 2. ql2tpd peer UI

The reviewed go-l2tp project is config-file/daemon driven, not graphical.

A PVNetwork adapter may expose:

- named static tunnels;
- named sessions;
- endpoint/encapsulation;
- IDs/cookies;
- pseudowire type;
- HELLO timeout;
- daemon state/logs.

If `hello_timeout` is enabled, UI must state that the reviewed ql2tpd implementation expects a compatible ql2tpd peer for that minimal control behavior.

## 3. Cisco IOS XE operator surface

Current Cisco L2TPv3 configuration is hierarchical CLI rather than a consumer UI.

Product concepts to map from Cisco include:

### L2TP Class

- hostname/control identity;
- control authentication/password;
- hello/liveness;
- cookie/control parameters depending release.

### Pseudowire Class

- L2TPv3 encapsulation;
- signaling protocol/class or `protocol none`;
- local source interface;
- sequencing;
- PMTU/fragmentation features;
- payload-specific options.

### Attachment Circuit

- physical/subinterface/port-channel/Frame Relay or other supported L2 circuit;
- remote peer;
- VC ID;
- manual local/remote L2TP IDs/cookies when static.

### Operational Status

- pseudowire up/down;
- control/session state;
- attachment-circuit state;
- counters/errors;
- reachability/OAM according to exact IOS XE platform/release.

PVNetwork must capture exact selected-release show/debug commands in the certification lab before automating parsing.

## 4. Static peer pairing UX

Static L2TPv3 is especially error-prone because values are mirrored asymmetrically.

Recommended two-peer editor feature:

- generate local tunnel/session IDs for Peer A;
- generate different local IDs for Peer B;
- automatically populate each peer's `peer_*` values from the other;
- generate 64-bit random cookies and mirror local/peer cookie fields;
- validate same encapsulation/pseudowire/sequencing/MTU;
- export both endpoint configs together with provenance.

This reduces manual transposition mistakes without inventing protocol negotiation.

## 5. Dynamic peer UX

If a full dynamic RFC3931 backend is added later, expose:

- Control Connection State
- Peer LCCE identity
- Authentication state
- Negotiated capabilities
- Session setup state
- Assigned session IDs/cookies
- Circuit status
- HELLO/liveness
- Stop/Delete session/control connection.

Do not reuse static form fields as writable “negotiated values” unless the backend permits an explicit override.

## 6. Error categories

Normalize:

- Underlay unreachable
- IP protocol 115 blocked
- UDP control/port blocked
- Tunnel ID conflict/mismatch
- Session ID mismatch
- Cookie mismatch
- Pseudowire type mismatch
- Control authentication failed
- HELLO/liveness timeout
- Sequence/reorder failure
- Attachment circuit down
- Bridge/VLAN mismatch
- MTU/fragmentation problem
- Layer-2 loop detected/likely
- Required IPsec protection absent.

## 7. Layer-2 safety UX

Before bridging an L2TPv3 pseudowire to a production LAN, an admin UI should show/validate:

- source and destination broadcast domains;
- VLAN set;
- STP/loop topology;
- DHCP/ARP/ND trust implications;
- MTU;
- expected MAC scale;
- whether L2 control protocols should traverse;
- firewall/bridge filtering if applicable.

Do not use a generic “Connect” button with no topology context.

## 8. Consumer PVNetwork UI

Entry 009 should normally be hidden from consumer/mobile protocol selection.

If exposed in an infrastructure edition, label:

`L2TPv3 Pseudowire (Advanced / Network Infrastructure)`

not simply `L2TP`, which users will confuse with L2TP/IPsec remote access.

## 9. Secret/sensitive data handling

- dynamic control shared secret -> secure write-only credential reference;
- static cookies -> sensitive operational values, redacted from ordinary logs/export previews when policy requires;
- IPsec credentials -> separate entry-010 secure references;
- tunnel/session IDs -> non-secret operational identifiers.

## 10. Persian/RTL

Persian prose/menu labels may be RTL, but keep these LTR:

- IP addresses;
- UDP ports;
- protocol number 115;
- tunnel/session IDs;
- hex cookies;
- interface names;
- VLAN IDs;
- CLI commands;
- logs/counters.

Do not mirror network topology direction semantically when local/peer roles must remain clear.

## 11. Remaining UI evidence

- real Linux output from selected distro/iproute2 version;
- current Cisco selected-platform show/debug/menu equivalents;
- full dynamic open-source LCCE UI/API if selected;
- network-controller candidate UI/API;
- accessibility/responsive behavior only after a PVNetwork graphical infrastructure UI exists.
