# DMVPN — Data Path and Wire Flow

Reviewed: 2026-08-15

Control/data lifecycle: spoke has mGRE tunnel -> registers NBMA/tunnel mapping with NHS via NHRP -> routing advertises/reaches prefixes -> traffic initially may traverse hub -> NHRP resolution/redirect/shortcut can establish direct spoke-to-spoke forwarding -> IKE/IPsec protects traffic when configured.

FRR documentation states `nhrpd` does not itself route prefixes and requires a routing protocol. This component boundary is mandatory.

mGRE/NHRP/routing state and IKE/IPsec SA state are separate. Dynamic shortcut failure/recovery, cache ageing and route changes can alter forwarding without redefining the cryptographic layer.