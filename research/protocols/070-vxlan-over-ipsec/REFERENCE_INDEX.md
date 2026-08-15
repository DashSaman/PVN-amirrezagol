# VXLAN over IPsec — Reference Index

Reviewed: 2026-08-15

Entry files: server implementations/installers/matrix/UI, client matrix/UI, cryptography, data path, ports/handshake, topologies, audit, existing V1 research.

Reused evidence:
- entry 069 VXLAN: RFC 7348, current Linux docs/source, iproute2, VTEP/FDB/port/MTU/security boundaries;
- entries 004–007 + `research/upstreams/strongswan-family/reference-v2/`: IKE/IPsec/ESP server/client/install/UI/crypto/wire/ports/topology/source/license/lifecycle.

Key boundaries: VXLAN supplies overlay encapsulation; IPsec supplies all crypto; cumulative overhead/MTU; VTEP state != SA state; no generic consumer app support inferred.

Next: entry 071 DMVPN.