# VTI/IPsec — Data Path and Wire Flow

Reviewed: 2026-08-15

## Architectural flow

```text
application/forwarded packet
  -> route selects VTI interface
  -> VTI applies configured mark / routes into XFRM policy path
  -> matching IPsec policy + SA protects packet
  -> ESP/IPsec crosses outer network
  -> remote IPsec endpoint validates/decrypts
  -> packet is delivered/routed according to peer configuration
```

strongSwan documents VTI devices as wrappers around existing IPsec policies: routing to the VTI alone is insufficient unless negotiated policies match; marks bind routed packets to matching policies. VTI is a local decision and does **not** add GRE-like extra encapsulation, so the remote peer does not need a VTI.

VTI/IPsec therefore differs from GRE-over-IPsec and IPIP-over-IPsec: VTI itself is an interface/policy abstraction, not another on-wire tunnel header. IPsec mode and negotiated SAs determine actual wire protection and overhead.

Interface state, route state, XFRM policy state and SA state are distinct observability dimensions.

Evidence: official strongSwan route-based VPN documentation; pinned Linux `net/ipv4/ip_vti.c`; shared strongSwan-family V2 data-path dossier.
