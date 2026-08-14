# EtherIP/IPsec — Ports, Transport and Handshake

Review date: 2026-08-14 UTC

## Inner encapsulation

- EtherIP: IP protocol **97**, version 3 header, no TCP/UDP port.

## Protection layer

Common IPsec/IKE wire identities documented by the completed IPsec research and reviewed SoftEther source include:

- IKE/ISAKMP: UDP **500**;
- NAT traversal / UDP-encapsulated ESP path: UDP **4500** where negotiated/used;
- native ESP: IP protocol **50** when not UDP encapsulated.

The exact on-wire path depends on backend, peer, NAT and policy. Do not expose all three as simultaneously mandatory ports.

The reviewed SoftEther `Proto_IKE.c` uses IKEv1-style Main/Aggressive/Quick Mode exchanges. Other EtherIP/IPsec compositions may use another supported IPsec control plane and must be labeled by exact backend/version.
