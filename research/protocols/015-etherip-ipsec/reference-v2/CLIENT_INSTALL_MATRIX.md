# EtherIP/IPsec — Peer Install Matrix

Review date: 2026-08-14 UTC

This is infrastructure gateway/peer functionality, not a consumer VPN client matrix.

| Peer target | Path | State |
|---|---|---|
| SoftEther-controlled gateway | built-in EtherIP + IPsec/IKE server path | REFERENCE-COVERED |
| OpenBSD gateway | native EtherIP + native IPsec | REFERENCE-COVERED |
| Linux gateway with separate EtherIP/IPsec components | no single generic combination selected | BACKEND-SPECIFIC / UNSELECTED |
| FreeBSD gateway | EtherIP reference exists; exact protected composition not selected here | PARTIAL-REFERENCE |
| Windows consumer | no standalone normal-user EtherIP/IPsec profile selected | N/A-CONSUMER |
| Android/iOS/TV | no normal-user L2 peer selected | N/A-CONSUMER |

A future independent combination must be source/license/policy/interoperability reviewed before being added to support.
