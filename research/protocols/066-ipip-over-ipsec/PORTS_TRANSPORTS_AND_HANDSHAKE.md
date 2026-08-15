# IPIP over IPsec — Ports, Transports and Handshake

Reviewed: 2026-08-15

- IPIP layer: outer IPv4 protocol 4; no TCP/UDP port; no intrinsic handshake.
- IKEv2 security setup: commonly UDP 500; NAT traversal may use UDP 4500.
- ESP: IP protocol 50 when native; NAT-T may encapsulate ESP in UDP.
- Peer authentication, key establishment, CHILD_SA creation, rekey and DPD belong to IKE/IPsec, not IPIP.
- No IPIP-defined proxy or transport fallback exists.

A successful IPIP route/interface does not prove an authenticated/encrypted session. Conversely, a generic IPsec SA must have selectors/policy that actually cover the IPIP traffic to protect this composition.

Do not label protocol 4 as “port 4”, and do not label UDP 4500 as an IPIP port.

Evidence: RFC 2003, RFC 7296, RFC 4303, and `research/upstreams/strongswan-family/reference-v2/PORTS_TRANSPORTS_AND_HANDSHAKE.md`.
