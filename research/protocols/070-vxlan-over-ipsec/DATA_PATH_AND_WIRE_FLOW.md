# VXLAN over IPsec — Data Path and Wire Flow

Reviewed: 2026-08-15

Typical composition: inner Ethernet -> VXLAN header/VNI -> UDP/IP VXLAN packet -> matching IPsec/XFRM processing -> protected outer network -> remote IPsec validate/decrypt -> remote VTEP decapsulates VXLAN -> bridge/deliver Ethernet.

Exact outer format depends on IPsec mode and NAT traversal. VXLAN plus IPsec adds cumulative overhead, so effective MTU must account for both encapsulations. RFC 7348's fragmentation guidance remains relevant.

VTEP/FDB/link state and IPsec policy/SA state are separate. Protected delivery is not proven merely because VXLAN forwarding works.