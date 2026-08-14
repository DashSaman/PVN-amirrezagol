# Router / Site-to-Site — PVNetwork Support / Reuse Decisions

Decision date: 2026-08-14

State: research only; not implemented.

## Engine minimization

- 063 GRE -> OS/kernel encapsulation, advanced only.
- 064 GRE/IPsec -> GRE + existing IPsec adapter.
- 065 IPIP -> OS/kernel encapsulation, advanced only.
- 066 IPIP/IPsec -> IPIP + existing IPsec adapter.
- 067 VTI/IPsec -> platform/backend integration mode, not new crypto protocol.
- 068 XFRM/IPsec -> Linux kernel IPsec integration capability, not new protocol.
- 069 VXLAN -> OS/kernel overlay, not encrypted by itself.
- 070 VXLAN/IPsec -> VXLAN + existing IPsec adapter.
- 071 DMVPN -> advanced architecture using mGRE/NHRP/IPsec/routing; no consumer-first engine.
- 072 FlexVPN -> Cisco IKEv2/IPsec interoperability profile; reuse typed IKEv2 model and require Cisco evidence.
- 073 GETVPN -> vendor/group-encryption advanced reference; no custom implementation without a mature reusable source and demand.

## Product rule

Do not create a separate engine for every tunnel/interface composition. Prefer kernel/OS primitives and the existing approved IPsec/routing components behind a typed privileged helper.

Raw GRE/IPIP/VXLAN must never be labeled encrypted. Protected compositions inherit their security from IPsec and require exact backend/server evidence.

## Residual v1 gaps

Exact kernel/iproute2/FRR/Cisco source/version matrices, current vendor interoperability, package/platform evidence and issue/security history remain. Full install/menu/crypto/wire/topology evidence is mandatory v2.
