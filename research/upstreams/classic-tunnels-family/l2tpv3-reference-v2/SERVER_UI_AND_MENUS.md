# L2TPv3 — Endpoint UI, CLI and Control-Plane Map

Review date: 2026-08-14

Entry: 009 L2TPv3.

L2TPv3 infrastructure endpoints generally use CLI/config/network-management systems, not a canonical web “VPN server” UI. This file records the real control surfaces.

## 1. Linux `ip l2tp` CLI

Pinned source: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`.

### Tunnel operations

- `ip l2tp add tunnel`
- `ip l2tp del tunnel`
- `ip l2tp show tunnel`

Source-visible parameters include:

- `tunnel_id`
- `peer_tunnel_id`
- `encap ip|udp`
- `local` / `remote`
- `udp_sport` / `udp_dport`

### Session operations

- `ip l2tp add session`
- `ip l2tp del session`
- `ip l2tp show session`

Parameters include:

- `tunnel_id`
- `session_id`
- `peer_session_id`
- `cookie` / `peer_cookie`
- `seq none|send|recv|both`
- `lns_mode`
- `reorder_timeout`
- `offset`
- `l2spec_type`
- `name`

### Diagnostics

Show output/source supports tunnel/session IDs, endpoints, ports, cookies, pseudowire type, interface name, sequence state and traffic/error/out-of-sequence counters.

### Product lesson

PVNetwork should wrap these concepts in typed configuration/status operations. Do not let a remote browser run arbitrary `ip` commands.

## 2. Linux bridge/VLAN UI domain

An Ethernet pseudowire normally needs additional Linux network configuration after session creation:

- `ip link` bring-up/MTU;
- bridge create/member operations;
- VLAN filtering/PVID/tag rules;
- VRF/netns ownership;
- firewall/ebtables/nftables policy where selected.

These are separate from L2TPv3 and should appear in a `Layer-2 Attachment`/`Network Integration` section rather than hidden as side effects.

## 3. ql2tpd configuration/control

Pinned go-l2tp source: `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`.

ql2tpd is config-file driven. Operator domains include:

- named tunnels;
- peer/local endpoints;
- version/encapsulation;
- tunnel/session IDs;
- pseudowire type;
- cookies;
- optional HELLO timeout;
- interface/network integration.

No mature generic web admin UI is provided by the reviewed project. PVNetwork would need its own typed adapter if ql2tpd is selected.

## 4. Cisco IOS XE CLI

Current official Cisco IOS XE documentation exposes L2TPv3 through network CLI hierarchy.

### `l2tp-class`

Control/signaling-level settings may include:

- hostname;
- authentication/password;
- hello interval;
- cookie policy depending platform/software.

### `pseudowire-class`

Current docs include concepts such as:

- `encapsulation l2tpv3`;
- `protocol l2tpv3 [class]` or `protocol none`;
- local IP/interface;
- sequencing;
- fragmentation/DF/PMTU options depending feature set.

### Attachment circuit / xconnect

On a Layer-2 attachment interface or connect context:

- `xconnect <peer> <vcid> encapsulation l2tpv3 ...`;
- manual pseudowire mode can set local/remote L2TP IDs and cookies;
- attachment circuits can include Ethernet and other platform-supported L2 services.

### Status/diagnostics

Exact `show`/debug/OAM commands vary with IOS XE feature family/release and must be captured in the selected-device certification lab. Do not freeze command output from a different platform as universal.

## 5. Recommended PVNetwork operator UI

### Pseudowires

- Name
- Status
- Local Endpoint
- Remote Endpoint
- Encapsulation: Direct IP / UDP
- Control Mode: Static / Dynamic / Selected Backend
- Pseudowire Type
- Attachment Circuit

### Tunnel

- Local Tunnel ID
- Peer Tunnel ID
- UDP Ports if applicable
- Address Family
- Underlay VRF/Namespace
- MTU/PMTU

### Session

- Local Session ID
- Peer Session ID
- Local Cookie
- Peer Cookie
- Sequencing
- Reorder Timeout
- L2-Specific Sublayer
- Interface Name

### Layer-2 Attachment

- Bridge
- VLAN/PVID/tagging
- STP policy
- MAC learning/filtering
- Attachment MTU
- Allowed broadcast/multicast policy

### Security

- Control Authentication — dynamic backend only
- Cookie policy
- Underlay trust classification
- `Require IPsec protection` link/capability pointing to entry 010

### Status

- Tunnel/session/control state
- Bytes/packets
- drops/errors/out-of-sequence
- interface/link state
- underlay reachability
- bridge/VLAN association
- IPsec protection state if entry 010 is composed.

## 6. Static vs dynamic UI

### Static

Show both local and peer IDs/cookies as explicit values and label:

`No L2TP control protocol — both peers must match manually.`

### Dynamic

Do not ask users to manually enter negotiated remote session values unless the selected backend requires it. Show control connection/auth/capability state separately.

## 7. Secret handling

### Cookies

Treat static cookie values as sensitive operational tokens, but do not label as encryption keys.

### Control shared secret

Dynamic control-auth password/secret is a real reusable credential and must use secure-store/write-only handling.

### IPsec entry 010

PSKs/private keys/certificates are a different credential class and belong to the IPsec adapter/configuration.

## 8. Safety/validation UX

Reject or warn on:

- duplicate local tunnel/session IDs in the same namespace;
- local/peer ID values not mirrored correctly between static peers;
- one-sided cookie length/value mismatch;
- sequencing enabled only on one end;
- direct-IP mode behind a known NAT assumption;
- pseudowire MTU below required Ethernet service size;
- attaching the same Layer-2 domain in a loop-prone topology without STP/design acknowledgment;
- unprotected public-underlay deployment when policy requires confidentiality.

## 9. Audit/RBAC

State-changing operations are network-admin privileged:

- create/delete tunnel;
- create/delete session;
- change cookie/control secret;
- attach/detach bridge/VLAN;
- change peer/transport;
- enable protection;
- restart orchestration service.

Read-only monitoring roles must not inherit these controls.

## 10. Remaining UI/control evidence

- selected Cisco IOS XE exact `show`/debug/config rollback commands;
- full dynamic RFC3931 open-source control-plane UI/API if one is selected;
- Linux network-management frontend/API candidate;
- safe remote management API design;
- actual running output/screenshots;
- accessibility is relevant only if a graphical PVNetwork operator UI is built later.
