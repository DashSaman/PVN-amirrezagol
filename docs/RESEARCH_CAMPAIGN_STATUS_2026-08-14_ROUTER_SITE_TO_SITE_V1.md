# PVNetwork Research Campaign Status — 2026-08-14 — Router / Site-to-Site v1 Closure

Repository phase: research / requirements / architecture.

Entries 063–073: **`V1-HANDOFF-READY / NOT IMPLEMENTED`**.

## Shared evidence

Under `research/upstreams/router-site-to-site-family/`:

- `SOURCE_ARCHITECTURE.md`
- `SUPPORT_REUSE_DECISIONS.md`

## Numbered evidence

Separate `V1_RESEARCH.md` files now exist for 063 GRE through 073 GETVPN.

## Key classification

- raw GRE/IPIP/VXLAN are encapsulation/overlay technologies and not encrypted by themselves;
- GRE/IPIP/VXLAN over IPsec reuse the existing IPsec security model;
- VTI/XFRM are platform/backend integration modes, not new cryptographic protocols;
- DMVPN is an architecture combining mGRE/NHRP/IPsec/routing;
- FlexVPN is vendor IKEv2/IPsec interoperability, not generic proof from IKEv2 alone;
- GETVPN is vendor/group-encryption architecture and not a normal point-to-point consumer VPN.

## Engine minimization

Prefer OS/kernel tunnel/overlay implementations, approved IPsec backend, and mature routing/NHRP components rather than eleven separate packet engines.

## Residual gaps

Exact kernel/iproute2/FRR/Cisco source/version matrices, current vendor interoperability/security/issue evidence and full package/platform support remain. Mandatory v2 later adds installs, complete admin menus, cryptography/wire flow, protocol numbers/ports/control-plane handshakes and topologies.

## Next exact action

Continue original v1 immediately with remaining enterprise/vendor entries 025–036, then remaining protocol/transport/security entries not yet handoff-ready (including 041 and 077–093 as indicated by actual tree/tracker). Do not start mass v2 until original v1 coverage is reconciled across all 93 entries.
