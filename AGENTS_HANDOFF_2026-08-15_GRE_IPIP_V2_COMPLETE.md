# PVNetwork Handoff — GRE / GRE-over-IPsec / IPIP V2 Completion

Date: 2026-08-15

## Authoritative progress

- COMPLETE-RESEARCH-v1: **93/93** (unchanged; complete).
- COMPLETE-REFERENCE-v2: entries **063 GRE**, **064 GRE over IPsec**, and **065 IP-in-IP / IPIP** have evidence-backed 16-gate audits and are approved for tracker promotion.
- This handoff is the companion continuation record required by gate 16 for those audits.

## Entry 063 — GRE

Complete dossier: `research/protocols/063-gre/`.

Canonical evidence includes RFC 2784, RFC 2890, Linux `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6` (`net/ipv4/ip_gre.c`, GPL-2.0-or-later) and iproute2 `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78` (`ip-tunnel.8`). Bare GRE is IP protocol 47 and has no intrinsic encryption/authentication or connection handshake. GRE key/checksum/sequence are not cryptographic security.

## Entry 064 — GRE over IPsec

Complete dossier: `research/protocols/064-gre-over-ipsec/`.

The entry reuses the completed GRE dossier plus `research/upstreams/strongswan-family/reference-v2/` / entries 004–007 for IKE/IPsec/ESP. strongSwan's current route-based VPN documentation and Cisco's current GRE-over-IPsec guide provide composition-specific evidence. GRE supplies encapsulation/routing flexibility; IPsec supplies confidentiality/integrity/authentication. Generic consumer platform support is not inferred.

## Entry 065 — IP-in-IP / IPIP

Complete dossier: `research/protocols/065-ipip/`.

Canonical evidence includes RFC 2003, Linux `net/ipv4/ipip.c` at the same pinned kernel revision and iproute2 `mode ipip`. Bare IPv4-in-IPv4 uses IP protocol 4, no TCP/UDP port, no connection/authentication handshake and no intrinsic cryptography. IPIP-over-IPsec remains a separate composition.

## Exact continuation

Next required V2 entry: **066 — IPIP over IPsec**.

For entry 066:
1. reuse entry 065 only for the bare IPIP endpoint/data-path/install/UI boundary;
2. reuse the already-completed strongSwan/IKE/IPsec V2 evidence for the security layer;
3. add composition-specific server/client/install/UI/data-path/ports/topology conclusions;
4. keep IPIP protocol 4 distinct from ESP protocol 50 and IKE UDP 500 / NAT-T UDP 4500;
5. do not infer generic Android/iOS/macOS/Windows consumer support from standalone native IPsec support;
6. evaluate all exact 16 V2 gates independently and promote only if all applicable gates are evidence-backed.

After 066, continue **067 VTI/IPsec**, **068 XFRM/IPsec**, **069 VXLAN**, and **070 VXLAN over IPsec** while reusing shared Linux/XFRM/IPsec evidence only where traceable.

Research completion remains distinct from implementation, device/interoperability testing, Store verification and production certification.
