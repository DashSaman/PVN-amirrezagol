# L2TPv3/IPsec — Protected Endpoint UI and Control Plane

Review date: 2026-08-14

Entry: 010 L2TPv3/IPsec.

The UI must correlate two independent backend domains: L2TPv3 pseudowire state and IPsec protection state. A single green `Connected` flag is insufficient.

## 1. Linux control surfaces

### L2TPv3

Reuse entry-009 source-backed operations:

- `ip l2tp` tunnel/session create/delete/show;
- ql2tpd configuration if selected;
- `ip link`/bridge/VLAN/VRF/network namespace configuration;
- pseudowire counters/errors.

### IPsec

Reuse entries 004–007:

- strongSwan VICI/`swanctl` or Libreswan control/configuration;
- IKE/CHILD SA state;
- Linux XFRM policy/state;
- credentials/trust;
- logs.

A PVNetwork server UI should invoke narrow typed adapters for each. It must not expose unrestricted shell/VICI/kernel operations remotely.

## 2. Recommended PVNetwork information architecture

### Protected Pseudowires

List columns:

- Name
- Peer
- L2TPv3 Status
- IPsec Protection Status
- Attachment Status
- Encapsulation
- Protection Mode
- Traffic counters

### Pseudowire

- Direct IP / UDP
- Static / Dynamic control
- Tunnel IDs
- Session IDs
- Cookies
- Sequencing/Reorder
- Pseudowire type
- Interface name

### IPsec Protection

- Protection required toggle — enabled by definition for entry 010
- Mode:
  - Flow-selective
  - Protected underlay
- IKE version/backend
- Peer identity
- Authentication method
- Credential reference
- IKE policy
- ESP policy
- NAT-T/effective state
- lifetimes/rekey

### Selector Preview

For direct IP, show a read-only effective selector preview:

`src=<local endpoint> dst=<peer endpoint> proto=115`

For UDP, show the actual configured/negotiated port policy and explicitly warn when dynamic signaling changes the selected port pair.

For protected-underlay mode, show the protected route/prefix/interface and a `No clear alternate route` validation result.

### Layer-2 Attachment

- bridge/VLAN/port
- STP policy
- MTU
- MAC learning/filtering
- broadcast/multicast controls
- namespace/VRF

### Status

#### IPsec

- IKE state
- CHILD/ESP state
- NAT-T
- rekey state
- XFRM selector/policy installed

#### L2TPv3

- control state if dynamic
- tunnel/session state
- Cookie validation state
- interface/attachment
- counters/errors/out-of-sequence

#### Composition

- Protection Ready
- Clear Route Detected/Blocked
- Forwarding Allowed/Blocked
- Effective MTU

## 3. Start/stop actions

### Start

UI/API operation should be transactional:

1. validate credentials and underlay;
2. establish/verify IPsec protection;
3. verify selector/route guard;
4. create/start L2TPv3;
5. attach/enable Layer-2 forwarding.

### Stop

Reverse safely:

1. disable Layer-2 forwarding;
2. remove L2TPv3 session/tunnel;
3. remove attachment state owned by profile;
4. remove IPsec SAs/policies/profile state;
5. restore routing/firewall state.

Do not offer a “stop IPsec only” normal action that leaves an entry-010 pseudowire forwarding clear.

## 4. Static configuration UX

Static peer pairing should generate/mirror:

- tunnel/session IDs;
- cookies;
- encapsulation;
- UDP ports where used;
- pseudowire type;
- sequencing;
- IPsec endpoint identities/selectors.

Export both endpoint configurations with clear local/peer labels to reduce transposition mistakes.

## 5. Dynamic control UX

When a full RFC3931 control backend is selected, display negotiated L2TPv3 state separately from IPsec:

- control connected/authenticated;
- peer LCCE identity;
- session negotiating/established;
- negotiated IDs/cookies/circuit status;
- HELLO/liveness.

IPsec can be fully connected while L2TP control fails, and vice versa in a misconfigured unsafe system. The UI must show both layers.

## 6. Cisco/network OS integration UI

If Cisco IOS XE becomes a managed target, PVNetwork must use exact supported CLI/API/NETCONF/RESTCONF capabilities for the selected release and preserve vendor config ownership.

Do not synthesize a generic Cisco form until the lab maps:

- L2TP class;
- pseudowire class;
- xconnect;
- IPsec/IKE policy/profile;
- route/ACL/selectors;
- show/debug/state operations;
- config commit/rollback semantics.

## 7. Security/credential UI

Separate:

- IPsec PSK/private-key/certificate credential;
- L2TPv3 dynamic control secret;
- static Cookie;
- tunnel/session IDs.

Rules:

- reusable secrets are write-only/replace-only through ordinary APIs;
- private keys remain secure-store/provider references;
- static cookies may be redacted from normal monitoring;
- IDs are non-secret operational values;
- no derived ESP/IKE keys are exposed.

## 8. Fail-safe policy UI

Entry 010 should expose a non-disableable-by-accident invariant:

`Block pseudowire forwarding when IPsec protection is unavailable.`

An advanced lab override, if ever created, must transform the profile into entry 009 plain L2TPv3 rather than silently weakening entry 010.

## 9. Error categories

- IKE authentication failed
- no matching IPsec proposal
- ESP/XFRM policy install failed
- protected route unavailable
- clear fallback route detected
- L2TP tunnel/session mismatch
- Cookie/control authentication failure
- attachment circuit down
- bridge/VLAN loop/policy issue
- protection lost during forwarding
- rekey failure
- MTU/fragmentation/ECN issue.

## 10. RBAC/audit

Privileged write operations:

- change IPsec credentials/policy;
- change peer/selectors;
- create/delete pseudowire;
- attach/detach Layer-2 domains;
- alter routes/firewall;
- disable protection guard;
- disconnect/restart.

Read-only roles may inspect safe status but not secrets/config-changing controls.

## 11. Remaining UI evidence

- running Linux composed adapter/state screens;
- exact Cisco protected-pseudowire management mapping;
- operator API authorization model;
- dynamic full-control backend UI if selected;
- screenshot/version correspondence after implementation.
