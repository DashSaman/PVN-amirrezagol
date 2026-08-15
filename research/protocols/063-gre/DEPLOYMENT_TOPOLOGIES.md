# GRE — Deployment Topologies

Reviewed: 2026-08-15

## Evidence-backed topologies

- **Point-to-point routed tunnel:** two GRE endpoints with routable outer addresses; each endpoint encapsulates and decapsulates traffic. This is the base model described by RFC 2784 and current Cisco/Juniper documentation.
- **Site-to-site routed overlay:** common infrastructure use, with routing over a GRE interface between sites. Cisco documents IPv4 GRE tunnel interfaces and routing; Juniper documents GRE tunnel interfaces and use with routing protocols.
- **Hub-and-spoke / multipoint GRE:** related implementation capability, but multipoint behavior and NHRP/DMVPN policy are not part of bare RFC 2784 GRE. DMVPN is entry 071 and must remain separate.
- **L2/Ethernet over GRE:** Cisco documents Ethernet-over-GRE, but it is an extension/profile and not the default bare IPv4 payload model of this entry.
- **GRE protected by IPsec:** security composition is entry 064, not bare GRE.

## Control/management/data plane

Bare GRE has no protocol-defined management or control plane. Endpoint configuration/routing is the management/control boundary; the data plane is GRE encapsulation over outer IP.

## Split/full tunnel

Those are route-policy outcomes rather than GRE protocol modes. A deployment can route selected prefixes or broader traffic over a GRE interface; PVNetwork must not imply that GRE itself supplies consumer split-tunnel controls.

## HA/load balancing

No protocol-defined HA cluster exists. Redundancy depends on routing, platform configuration or higher-level control systems.

Evidence: RFC 2784/2890; Cisco IOS XE 17 tunnel guide; Juniper GRE tunnel documentation.
