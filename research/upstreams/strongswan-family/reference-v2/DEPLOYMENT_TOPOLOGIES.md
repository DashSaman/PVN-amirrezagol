# IKE / IPsec — Deployment Topologies

Review date: 2026-08-14

Scope: entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP, 007 AH.

This file maps deployment patterns without treating every product/vendor as the same implementation. Each topology still requires an exact server/client/backend/version certification matrix.

## 1. Remote-access IKEv2 — road warrior to gateway

### Shape

`mobile/desktop client`

`-- IKEv2 UDP 500/4500 -->`

`VPN gateway`

`-- routed/forwarded traffic -->`

`private networks / Internet egress`

### Typical client choices

- Apple native NetworkExtension IKEv2;
- Windows native IKEv2;
- Android native `Ikev2VpnProfile` on supported releases;
- Linux NetworkManager/strongSwan-style client;
- approved strongSwan Android/native compatibility backend where needed.

### Typical server choices

- strongSwan/Linux;
- Libreswan/Linux/Unix;
- OPNsense/pfSense appliance;
- vendor/cloud gateway after exact interoperability review.

### Required configuration domains

- server identity/certificate/trust;
- client auth (certificate/EAP/PSK according to approved policy);
- address pool;
- DNS;
- split/full tunnel routes;
- IKE/Child proposal policy;
- NAT-T/firewall;
- server forwarding and egress NAT where Internet access is desired.

### Failure distinction

A fully authenticated IKEv2 client can still have no application connectivity because the gateway lacks forwarding/routes/firewall/NAT/DNS. Diagnose those layers separately.

## 2. Site-to-site policy-based tunnel

### Shape

`Site A LAN -> Gateway A`

`<== IKE + tunnel-mode ESP ==>`

`Gateway B -> Site B LAN`

Traffic selectors define protected networks.

### Characteristics

- no dedicated route-based tunnel interface is necessarily present;
- IPsec SPD/selectors decide which traffic enters the SA;
- local firewall/routing still determine packet reachability;
- multiple subnet pairs may create multiple policy/Child-SA relationships depending implementation/config.

### Risks

- selector mismatch;
- overlapping networks;
- asymmetric routing;
- NAT accidentally applied before/after policy;
- rekey causing stale policies;
- HA peer failover changing endpoint/SA state.

## 3. Route-based IPsec — VTI / XFRM interface

### Shape

`router/host routes`

`-> virtual tunnel anchor (VTI/XFRM-style)`

`-> IPsec policy/SA`

`-> ESP/NAT-T`

### Benefits

- routing protocols/static routes can use an interface-like abstraction;
- simpler mental model for many routed subnets;
- firewall zones/policies can attach to an interface abstraction on some platforms;
- useful for dynamic routing and cloud/vendor gateway interop where supported.

### Caveat

The VTI/XFRM interface is not the encryption protocol. ESP/IPsec still owns data protection. Exact interface semantics differ by OS/product.

## 4. Host-to-host transport mode

### Shape

`Host A IP`

`<== ESP/AH transport mode ==>`

`Host B IP`

### Use cases

- infrastructure service protection;
- specialized host-level security associations;
- compositions where preserving original IP header semantics is desired.

### Requirements

- host policy/credential management;
- exact selector/protocol/port policy;
- NAT constraints;
- firewall integration;
- operational tooling for per-host SAs.

This is not the normal consumer remote-access VPN topology.

## 5. Client behind NAT — IKEv2 NAT-T

### Shape

`Client private address`

`-> NAT gateway`

`-> Internet UDP 500/4500`

`-> VPN gateway`

### Behavior

IKE detects NAT conditions and moves to NAT traversal behavior. Protected ESP traffic is normally UDP-encapsulated for NAT traversal.

### Test dimensions

- symmetric/CGNAT behavior;
- UDP timeout/keepalive;
- network handover;
- multiple clients behind same NAT;
- IPv4 and IPv6 differences;
- rekey after NAT mapping changes.

Do not use AH as an assumed NAT alternative; its integrity coverage conflicts with ordinary address translation.

## 6. Dual-stack remote access / site-to-site

### Shape

Outer transport may be IPv4 or IPv6 while traffic selectors/inner traffic may include IPv4 and/or IPv6 depending backend/profile.

Required matrix:

- v4 outer / v4 inner;
- v4 outer / v6 inner;
- v6 outer / v4 inner;
- v6 outer / v6 inner;
- DNS and Happy Eyeballs behavior;
- PMTU/fragmentation;
- firewall/selectors for both families.

A single successful IPv4 tunnel is not IPv6 certification.

## 7. Multi-CHILD / segmented policy topology

One IKEv2 peer relationship can manage multiple Child/data SAs for different network segments/policies.

Example:

- corporate routes;
- management routes;
- restricted service subnet;
- optional Internet egress.

PVNetwork must model and display per-child state where needed. One failed Child SA should not necessarily make every other established Child disappear from diagnostics.

## 8. High-availability gateway pair

### Shape

`clients/sites`

`-> virtual/managed endpoint`

`-> Gateway A / Gateway B`

### Design dimensions

- shared/floating IP or DNS failover;
- certificate/identity consistency;
- synchronized user/PSK/certificate/policy configuration;
- address pool ownership;
- routes/firewall/state;
- SA state synchronization if the implementation/product actually supports it;
- reconnect timing if SAs are not synchronized.

Do not promise seamless HA merely because both gateways have identical config. IKE/ESP session state is cryptographic/sequence-sensitive and product-specific HA support must be proven.

## 9. Cloud-managed VPN gateway interoperability

### Shape

`on-prem strongSwan/Libreswan/appliance`

`<== IKEv2/IPsec ==>`

`managed cloud VPN gateway`

### Evidence required per provider/product

- supported IKE version;
- route-based vs policy-based support;
- proposal/algorithm constraints;
- BGP/static routing;
- tunnel count/HA model;
- DPD/rekey/lifetime;
- NAT-T;
- IPv6;
- diagnostics/logs;
- maintenance/upgrade behavior;
- tested exact provider SKU/version/API date.

Cloud product documentation changes; re-check immediately before deployment.

## 10. OPNsense / pfSense gateway topology

Use each appliance as an integrated firewall/IPsec control plane:

`web UI/config DB`

`-> appliance-owned IKE/IPsec daemon/config`

`-> BSD kernel IPsec/data plane`

`-> appliance firewall/routing/VTI`

PVNetwork should integrate as a remote client/interoperability tester or through documented administrative APIs in a future server-management product. Do not install competing unmanaged daemon/config files.

## 11. Linux gateway with strongSwan

Recommended operator architecture:

`PVNetwork operator/control service`

`-> narrow privileged adapter`

`-> VICI / swanctl`

`-> charon`

`-> Linux XFRM/IPsec`

`-> routes/firewall/NAT`

### Security boundary

- VICI remains local/private and permission-restricted;
- UI/business service should not run with raw kernel/network privileges if avoidable;
- secret material stays in approved secure files/stores/providers;
- adapter validates allowed operations.

## 12. Linux/Unix gateway with Libreswan

Conceptual:

`operator config/adapter`

`-> Libreswan ipsec/pluto tooling`

`-> NSS credentials + config`

`-> OS kernel IPsec`

Keep the adapter separate from strongSwan/VICI because control/config semantics differ.

## 13. Android / Apple / Windows native-client to Linux gateway

This is a critical PVNetwork interoperability topology:

- native OS client lifecycle;
- standard IKEv2 gateway implementation;
- server certificate/identity compatibility;
- client auth differences;
- route/DNS delivery;
- NAT-T;
- rekey/network changes.

Required certification matrix should cross at least:

`Apple native / Windows native / Android native / Linux selected client`

against:

`strongSwan gateway / Libreswan gateway / selected appliance`

for the approved profile classes.

## 14. Legacy IKEv1 compatibility enclave — entry 005

If IKEv1 is required for a legacy vendor/server:

- isolate it as a distinct profile/backend capability;
- mark deprecated/legacy in UI;
- keep security policy narrowly compatible with that server;
- do not use IKEv1 as automatic fallback;
- test in a controlled compatibility lab;
- plan migration to IKEv2 where feasible.

Do not normalize IKEv1 modes into a standard consumer IKEv2 experience.

## 15. AH specialized non-NAT topology — entry 007

Potential shape:

`static host/gateway A`

`<== AH protocol 51 ==>`

`static host/gateway B`

with no address-translating NAT in the authenticated path.

Use only where integrity/authentication without payload confidentiality is explicitly required and supported. This is not a recommended general Internet remote-access design.

## 16. Container/node gateway

A containerized IKE daemon can still depend on the host kernel/network namespace for XFRM/IPsec.

Possible architecture:

`management container / IKE daemon`

`-> host or dedicated network namespace`

`-> node kernel XFRM`

`-> host interfaces/firewall`

### Risks

- excessive capabilities/privileged mode;
- unclear SA/policy ownership;
- namespace mismatch;
- cleanup after crash;
- moving pod to a different node loses kernel SA state;
- secrets mounted into privileged workloads;
- HA semantics unlike stateless services.

Do not deploy as a generic horizontally scaled Kubernetes Deployment without an explicit node/gateway architecture.

## 17. L2TP/IPsec boundary — entry 008, not folded into 004–007

L2TP/IPsec uses IKE/IPsec to protect a separate L2TP layer. It will reuse portions of the IPsec reference, but its L2TP session/control/data semantics and client UI belong to entry 008.

Do not mark L2TP/IPsec v2 complete merely because entries 004–007 have a complete IPsec reference.

## 18. Required topology lab matrix

Before strict certification, retain packet/config/test receipts for:

- IKEv2 road warrior behind NAT;
- IKEv2 full tunnel;
- IKEv2 split tunnel;
- site-to-site policy-based;
- route-based VTI/XFRM;
- native ESP where no NAT;
- UDP-encapsulated ESP with NAT-T;
- dual-stack;
- rekey;
- server restart/failover;
- native mobile/Windows clients against strongSwan and Libreswan;
- selected appliance;
- explicit IKEv1 legacy case if product scope keeps it;
- explicit AH non-NAT case if product scope keeps it.

All are external execution gates; no success is inferred from the diagrams.
