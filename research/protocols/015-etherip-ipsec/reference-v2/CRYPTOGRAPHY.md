# EtherIP/IPsec — Cryptography / Security Boundary

Review date: 2026-08-14 UTC

EtherIP contributes **no native cryptography**. Protection is supplied by the selected IPsec backend.

Authoritative/reference layers already present in repository:

- RFC 3378 — EtherIP and security limitation;
- RFC 4303 — ESP data-plane semantics;
- RFC 2409 — historic IKEv1 reference relevant to the reviewed SoftEther Main/Aggressive/Quick Mode implementation;
- completed IKE/IPsec entries 004–007 / strongSwan-family evidence for modern and legacy backend boundaries.

## SoftEther pinned path

`Proto_IKE.c` at `b1f7ef...` is IKEv1-style. `Proto_IPsec.c` owns IKE/ESP service behavior and raw/UDP-encapsulated ESP demultiplexing. The source builds ESP packets with SPI/sequence/IV/encrypted payload/padding/authentication according to selected transform state.

## Security rules

- do not call this composition protected unless an IPsec SA/policy actually covers EtherIP traffic;
- do not silently enable IKEv1/legacy algorithms as a modern default merely because the reviewed SoftEther path supports them;
- credentials/private keys/PSKs belong to backend secret storage and must be redacted;
- cryptographic implementation remains upstream/backend-owned; PVNetwork should not invent replacement crypto.
