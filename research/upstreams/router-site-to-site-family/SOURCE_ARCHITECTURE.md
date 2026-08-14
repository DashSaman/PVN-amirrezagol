# Router / Site-to-Site Family — Source / Capability Architecture

Research date: 2026-08-14

State: `IN-RESEARCH / NOT IMPLEMENTED`.

Entries:

- 063 GRE
- 064 GRE over IPsec
- 065 IP-in-IP / IPIP
- 066 IPIP over IPsec
- 067 VTI/IPsec
- 068 XFRM/IPsec
- 069 VXLAN
- 070 VXLAN over IPsec
- 071 DMVPN
- 072 Cisco FlexVPN
- 073 GETVPN

## Critical classification rule

These entries mix multiple architectural layers:

### Raw encapsulation / overlays

- GRE
- IPIP
- VXLAN

These do not automatically provide confidentiality/authentication.

### IPsec-protected compositions

- GRE over IPsec
- IPIP over IPsec
- VXLAN over IPsec

Reuse the already documented PVNetwork IPsec/IKE security model rather than inventing duplicate crypto/profile fields.

### Linux interface/policy integration

- VTI/IPsec
- XFRM/IPsec

These are primarily ways to integrate IPsec policy/SAs with routing/interfaces rather than end-user remote-access protocols.

### Multi-component/vendor frameworks

- DMVPN
- Cisco FlexVPN
- GETVPN

These combine control-plane, routing, peer-discovery/group-key or vendor interoperability semantics beyond one raw tunnel header.

PVNetwork must not count all 11 as equivalent consumer VPN protocols.

## Linux kernel / iproute2 direction

For GRE, IPIP, VXLAN, VTI and XFRM-style Linux deployments, use mature Linux kernel/networking capabilities and typed userspace configuration rather than building packet encapsulation engines from scratch.

Primary technical references include:

- Linux kernel networking/tunnel/XFRM documentation/source;
- iproute2 tunnel/link/XFRM tooling/source;
- strongSwan/kernel IPsec for protected compositions;
- FRRouting for dynamic routing/NHRP/control-plane integration where applicable.

## 063 GRE

GRE is generic routing encapsulation. It can carry routed/other payloads but is not encrypted by itself.

Likely PVNetwork product role: advanced/site-to-site/router module only.

## 064 GRE over IPsec

Composition:

`GRE encapsulation`

`+ IPsec protection`

Canonical model should hold GRE tunnel/interface parameters separately from IKE/IPsec authentication/security policy.

## 065 IPIP

Simple IP-in-IP encapsulation. No confidentiality by itself.

Low consumer priority; advanced/site-to-site only.

## 066 IPIP over IPsec

Composition of IPIP encapsulation with IPsec protection. Reuse IPsec adapter/security model.

## 067 VTI/IPsec

Virtual Tunnel Interface is an OS/router integration model that provides a route-able interface associated with IPsec policy/state.

PVNetwork should model it as an advanced platform backend mode, not a separate cryptographic protocol.

## 068 XFRM/IPsec

Linux XFRM is the kernel policy/SA transformation framework used for IPsec processing. XFRM interfaces provide route-able integration without duplicating the IKE/IPsec protocol layer.

This is a Linux implementation/backend capability, not a normal mobile VPN protocol.

## 069 VXLAN

VXLAN is an overlay encapsulation technology commonly used for Layer-2/virtualized networks over UDP/IP.

It is not encrypted by itself.

Likely role: data-center/site-to-site/advanced networking reference, not consumer VPN onboarding.

## 070 VXLAN over IPsec

Composition:

`VXLAN overlay`

`+ IPsec protection`

Keep overlay/VNI/endpoint fields separate from IPsec security/authentication.

## 071 DMVPN

DMVPN is a multi-component dynamic VPN architecture rather than one standalone wire protocol.

Typical architectural building blocks include:

- multipoint GRE;
- NHRP peer/next-hop discovery;
- IPsec protection;
- dynamic routing according to deployment.

Open-source reference directions include Linux mGRE/kernel networking, strongSwan/IPsec and FRRouting NHRP/routing components, while Cisco implementations remain important interoperability references.

PVNetwork should not attempt DMVPN in a normal consumer mobile client first. Treat as advanced/router/site-to-site capability.

## 072 Cisco FlexVPN

FlexVPN is Cisco's IKEv2-centric VPN framework/profile architecture rather than a wholly independent cryptographic protocol.

PVNetwork should reuse the IKEv2/IPsec model and treat FlexVPN as a vendor interoperability/capability profile requiring exact Cisco/strongSwan/native backend testing.

Do not advertise generic FlexVPN support merely because an engine supports IKEv2.

## 073 GETVPN

GETVPN is a Cisco group-encryption architecture aimed at private WAN/group environments and differs substantially from ordinary point-to-point remote access.

Research classification should remain vendor/advanced/reference-first unless a reusable implementation and real user demand are found.

Do not build a custom group-key/crypto implementation from incomplete public descriptions.

## Product UI classification

### Advanced site-to-site

- GRE
- GRE/IPsec
- IPIP
- IPIP/IPsec
- VTI/IPsec
- XFRM/IPsec
- VXLAN
- VXLAN/IPsec

### Router/vendor frameworks

- DMVPN
- FlexVPN
- GETVPN

These should not clutter normal server selection on phones/TVs.

## Privilege/security boundary

Linux/router tunnel/interface management can alter system-wide routes, interfaces, firewall/XFRM policy and bridging.

Use a privileged helper/service with typed operations rather than letting GUI/plugins run arbitrary `ip`, `iptables`, `nft`, routing or shell commands.

## Reuse rule

- raw tunnel/overlay data plane: OS/kernel first;
- IPsec protection: existing approved IPsec backend;
- routing/NHRP: mature routing stack/reference;
- Cisco-specific frameworks: interoperability reference/candidate only after exact support evidence.

Do not create eleven new packet engines.

## Later v2 requirements

Mandatory v2 must add:

- kernel/router/vendor/server implementations;
- exact install commands/packages/projects across Linux/router/container environments;
- complete admin UI/menu maps;
- cryptography/security classification;
- packet/wire/data flow;
- protocol numbers/UDP ports/handshake/control plane;
- topology diagrams;
- Cisco/vendor version interoperability.
