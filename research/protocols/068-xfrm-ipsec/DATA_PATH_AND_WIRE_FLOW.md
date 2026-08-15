# XFRM/IPsec — Data Path and Wire Flow

Reviewed: 2026-08-15

```text
packet
  -> route selects XFRM interface
  -> interface ID participates in XFRM policy selection
  -> matching policy/SA applies IPsec processing
  -> protected traffic crosses outer network
  -> remote IPsec endpoint validates/decrypts
  -> packet is routed/delivered according to peer policy
```

XFRM interfaces are a **local decision** and add no additional tunnel encapsulation beyond the selected IPsec mode. The remote peer does not need to use an XFRM interface.

Current strongSwan documentation states traffic routed to an XFRM interface without matching policies/SAs is dropped; policies/SAs linked to an interface ID are not operational for normal traffic until a matching interface exists. This makes interface state, route state, policy state and SA state distinct.

Unlike VTI, XFRM interfaces do not require tunnel endpoint addresses, can associate IPv4/IPv6 SAs with one interface and support IPsec modes other than tunnel mode.

Evidence: official strongSwan route-based VPN docs; pinned Linux XFRM interface source; shared IPsec V2 data-path dossier.
