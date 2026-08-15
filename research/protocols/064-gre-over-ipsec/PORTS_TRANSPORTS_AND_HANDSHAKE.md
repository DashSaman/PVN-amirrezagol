# GRE over IPsec — Ports, Transports and Handshake

Reviewed: 2026-08-15

## Layered network behavior

- GRE layer: IPv4 GRE uses IP protocol 47 and has no TCP/UDP port or connection handshake (entry 063).
- IKEv2: commonly UDP 500; when NAT traversal is used, IKE/ESP may use UDP 4500 according to IKE/IPsec standards and implementation negotiation.
- ESP: IP protocol 50 when carried natively. NAT-T wraps ESP in UDP when negotiated/required.
- Authentication/key establishment occurs in IKE, not GRE.
- GRE packets are carried only after routing selects the GRE interface; IPsec policy/SA then protects the GRE traffic.

strongSwan documents using transport-mode IPsec with GRE-specific traffic selectors (`dynamic[gre]`). Cisco's GRE-over-IPsec workflow configures IKEv2 keyring/profile, ESP transform/IPsec profile, then applies that profile as tunnel protection.

## Failure / retry

IKE defines SA establishment/rekey/DPD behavior; GRE itself has no equivalent. A GRE keepalive, when an implementation offers one, does not replace IKE/ESP liveness/security state.

## Proxy/fallback

No GRE-defined proxy or alternate transport fallback exists. NAT traversal belongs to IKE/IPsec. Do not describe UDP 4500 as a GRE port.

Evidence: RFC 7296, RFC 4303, strongSwan-family V2 ports/handshake dossier, strongSwan route-based VPN docs, Cisco GRE-over-IPsec guide.
