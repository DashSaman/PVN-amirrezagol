# Linux tunnel family — shared V1 evidence

Review date: 2026-08-14

Scope: 063 GRE, 064 GRE over IPsec, 065 IPIP, 066 IPIP over IPsec, 067 VTI/IPsec, 068 XFRM/IPsec, 069 VXLAN, 070 VXLAN over IPsec.

## Standards
- GRE: RFC 2784; Key/Sequence extensions RFC 2890.
- IPv4-in-IPv4: RFC 2003.
- VXLAN: RFC 7348, UDP overlay with 24-bit VNI; IANA standard UDP destination port 4789.
- IPsec security composition reuses the repository's current strongSwan-family/IPsec evidence and current 6.0.7 release pin.

## Current Linux/iproute2 authority
Official kernel.org iproute2 release index reviewed 2026-08-14:
- current published release: **iproute2 7.1.0**, 2026-06-15;
- official `.tar.xz` and `.tar.sign` are published;
- current `ip-link` supports `gre`, `gretap`, `ipip`, `vti`, `vti6`, `xfrm`, `vxlan` and their typed parameters.

Linux kernel current documentation provides native VXLAN and XFRM implementation/offload documentation. `ip xfrm` manages Security Associations/policies; XFRM interfaces use interface IDs to bind routing to policies/SAs. Kernel XFRM offload documentation separates crypto offload from packet offload.

## Security boundary
- GRE: encapsulation, **no confidentiality/authentication by itself**.
- IPIP: encapsulation, **no confidentiality/authentication by itself**.
- VXLAN: L2-over-UDP overlay, **no confidentiality/authentication by itself**.
- “over IPsec” entries are typed compositions: outer/selected tunnel traffic is protected by IPsec SAs/policies. Security properties come from the selected IPsec/IKE configuration, not from GRE/IPIP/VXLAN.
- VTI and XFRM interfaces are Linux route-based IPsec integration mechanisms, not standalone encryption protocols.

## Implementation / license boundary
Linux kernel and iproute2 are mature open-source system components with their own GPL licensing and distribution obligations. PVNetwork should normally configure the OS facilities rather than copy kernel/iproute2 code into its GUI. StrongSwan remains a separately licensed/versioned key-management/IKE candidate where used.

## Common product model
Keep separately typed:
- interface/tunnel kind;
- local/remote underlay addresses;
- inner/overlay addressing and routes;
- keys/VNI/tunnel identifiers where applicable;
- MTU/TTL/TOS/DF/checksum/sequence options where supported;
- IPsec composition / SA-policy ownership;
- selected Linux/kernel/iproute2/strongSwan versions;
- interface, routing, firewall and policy health.

Do not treat a created interface as proof that traffic is encrypted, routed correctly, leak-free or accepted by the remote peer.

## Common diagnostics
Separate:
1. interface creation/link state;
2. underlay reachability;
3. encapsulation counters/errors;
4. route/FDB state;
5. MTU/fragmentation;
6. IPsec SA/policy/XFRM state when composed;
7. firewall/NAT/rp_filter;
8. hardware offload state;
9. peer interoperability.

## Common V1 gate treatment
For these infrastructure protocols, canonical consumer GUI/screens/assets are generally N/A. V1 UI evidence is the authoritative typed `ip`/kernel/strongSwan configuration surface and the PVNetwork-required UI model. Device/runtime certification is later work, not a hidden research gate.

## Shared uncertainties for later acceptance/V2
Exact target kernel/iproute2 distro backports, hardware offloads, MTU, NAT traversal, firewall/rp_filter, multicast/FDB behavior, IPsec proposals/reauth/rekey, HA, cross-vendor interop and exact deployment topologies remain later acceptance/reference evidence.
