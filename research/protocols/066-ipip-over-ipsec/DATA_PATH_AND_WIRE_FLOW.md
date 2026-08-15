# IPIP over IPsec — Data Path and Wire Flow

Reviewed: 2026-08-15

```text
inner IPv4 packet
  -> IPIP encapsulation adds outer IPv4 header (Protocol 4)
  -> XFRM/IPsec policy matches the IPIP-bearing traffic
  -> ESP authenticates/encrypts according to negotiated SA
  -> outer network (native ESP or NAT-T encapsulation as negotiated)
  -> remote IPsec endpoint validates/replay-checks/decrypts
  -> remote IPIP endpoint removes the IPIP outer header
  -> original inner IPv4 packet routed/delivered
```

The exact outer packet shape depends on IPsec transport-vs-tunnel mode and NAT traversal. IPIP and IPsec add cumulative overhead, so effective MTU/PMTU handling must account for both layers.

The IPIP interface may exist/up while IPsec SAs are absent; product state must not conflate interface state with protected-SA state.

Visible metadata depends on IPsec mode: outer delivery addressing and NAT-T framing may remain visible while the IPIP-bearing packet is protected by ESP.

Evidence: entry 065 RFC 2003/Linux/IPIP data-path dossier; RFC 4303; strongSwan-family V2 data-path and ports/handshake dossiers.
