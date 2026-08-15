# DMVPN — Reference Index

Reviewed: 2026-08-15

Entry files: server implementations/installers/matrix/UI, peer matrix/UI, crypto, wire flow, ports/handshake, topologies, audit, existing V1 reconciliation/research.

Canonical/reused evidence from `V1_GATE_RECONCILIATION.md`:
- RFC 2332 NHRP; RFC 2784 GRE; RFC 4301 IPsec.
- Cisco IOS XE 17 DMVPN current documentation (proprietary/reference-only).
- FRR `a2e9ed0521dc8456e9bb9910a826970315873d03`, `nhrpd`, topology/redundancy tests.
- strongSwan `5011838b32ac88ba9593af4b727932c34b28e127` plus completed IPsec V2 evidence.

Licensing boundary: FRR/strongSwan are separate GPL-family components; Cisco source unavailable/proprietary. Consumer Store/client support N/A.

Next: 072 Cisco FlexVPN.