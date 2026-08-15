# VTI/IPsec — Server UI and Menus

Reviewed: 2026-08-15

VTI/IPsec has no protocol-owned web UI. Linux exposes a route-based interface plus ordinary IPsec/IKE configuration.

Relevant VTI management fields are interface name, local/remote endpoint, inbound/outbound mark/key, link state, routes, addresses, statistics and policy-handling settings. strongSwan's route-based VPN documentation explains that packets routed to VTI are automatically marked and only matching IPsec policies/SAs protect them.

The security configuration surface remains IKE identity/authentication, credentials, proposals, traffic selectors/policies, SA status, rekey/liveness and diagnostics from the strongSwan-family V2 UI/control evidence.

Do not merge the VTI mark with a cryptographic key, and do not interpret interface-up as proof of an established IPsec SA.
