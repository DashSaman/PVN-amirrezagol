# EtherIP — Peer Install Matrix

Review date: 2026-08-14 UTC

EtherIP is symmetric infrastructure encapsulation, not a consumer client/server application protocol. The correct “client” matrix is a peer matrix.

| Peer target | Path | State |
|---|---|---|
| SoftEther-controlled host | SoftEther server/bridge EtherIP capability | REFERENCE-COVERED |
| OpenBSD gateway | native `etherip(4)` | REFERENCE-COVERED |
| FreeBSD gateway | native `gif(4)` + bridge | REFERENCE-COVERED |
| Generic Linux independent peer | no independent implementation selected by this dossier | UNSELECTED; do not invent support |
| Windows consumer | no standalone EtherIP consumer client selected | N/A-CONSUMER |
| Android/iOS/TV | no normal-user EtherIP peer selected | N/A-CONSUMER |

A future product that deliberately adds another peer implementation must receive its own source/release/license/security freeze.
