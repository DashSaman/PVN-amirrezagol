# DMVPN — Server / Peer Implementations

Reviewed: 2026-08-15

DMVPN is an architecture (`mGRE + NHRP + routing + normally IPsec/IKE`), not a single client/server wire protocol.

- Cisco IOS XE 17.x is the canonical proprietary behavior/interoperability reference, as pinned in the V1 reconciliation.
- Public implementation/reference path: Linux GRE + FRRouting `nhrpd` + a routing daemon + strongSwan/IKE. FRR is pinned in V1 at `FRRouting/frr@a2e9ed0521dc8456e9bb9910a826970315873d03`; strongSwan at `5011838b32ac88ba9593af4b727932c34b28e127`.
- RFC 2332 NHRP, RFC 2784/2890 GRE and RFC 4301 IPsec supply standards-layer evidence.

Cisco source is proprietary/reference-only. FRR/strongSwan are separate GPL-family components and require deliberate product/legal architecture. PVNetwork should model DMVPN as infrastructure orchestration, not a consumer VPN engine.