# GRE over IPsec — Data Path and Wire Flow

Reviewed: 2026-08-15

## Typical composition

```text
inner routed/multicast packet
  -> GRE interface encapsulates: GRE header + inner packet
  -> IPsec policy/XFRM matches GRE traffic
  -> ESP protects the GRE-bearing packet (transport mode is common; tunnel mode is also possible by implementation/configuration)
  -> outer IP network
  -> remote IPsec endpoint validates anti-replay/authentication and decrypts ESP
  -> GRE endpoint parses/decapsulates GRE
  -> inner packet routed/delivered
```

strongSwan's current route-based VPN documentation explicitly describes GRE as a portable route-based method and notes a host-to-host IPsec connection may protect only GRE traffic via selectors such as `dynamic[gre]`. Its IPsec protocol guide documents ESP transport mode as protection for tunneling protocols including GRE.

Cisco's current GRE-over-IPsec guide describes GRE encapsulation followed by IPsec encryption and validates security associations before forwarding decapsulated traffic.

## MTU / overhead

The path adds GRE overhead plus ESP/IKE-selected IPsec overhead and possibly UDP encapsulation for NAT traversal. Effective MTU must therefore account for both layers; route-based Linux deployments may set tunnel-device MTU to avoid downstream fragmentation issues.

## Visible metadata

With ESP, protected GRE/inner content is encrypted according to the selected IPsec mode. Outer IP and any NAT-T UDP framing remain visible. Exact visibility depends on transport-vs-tunnel mode and NAT traversal.

## Failure boundary

GRE tunnel interface state and IPsec SA state are distinct. A configured/up GRE interface does not prove that an IPsec SA is established or that packets are protected.

Evidence: entry 063 wire-flow dossier; strongSwan route-based/IPsec protocol docs; repository strongSwan-family V2 dossier; Cisco GRE-over-IPsec guide.
