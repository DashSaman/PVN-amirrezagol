# EtherIP/IPsec — Server / Peer Install Matrix

Review date: 2026-08-14 UTC

| Target | Composed path | State / boundary |
|---|---|---|
| SoftEther-supported Linux/Unix host | SoftEther EtherIP + built-in IPsec/IKE service | REFERENCE-COVERED; exact release/runtime certification later |
| SoftEther-supported Windows host | SoftEther EtherIP + built-in IPsec/IKE service | REFERENCE-COVERED; OS IPsec-service conflict/restore behavior is source-documented and must be certified |
| OpenBSD | native `etherip(4)` + native IPsec policy/flow | REFERENCE-COVERED |
| FreeBSD | EtherIP peer exists, but a complete selected entry-015 native IPsec composition is not frozen here | PARTIAL-REFERENCE / NOT SELECTED |
| Linux with separate IPsec backend | possible architecture using completed IPsec ecosystem research | BACKEND-SPECIFIC; not automatically certified |
| OCI container | only if selected runtime can safely own required L2/raw/ESP/UDP privileges and persistence | ADVANCED; no generic image blessed |
| Kubernetes | no generic stateless model | N/A-GENERIC / GATEWAY-SPECIFIC |
| Mobile consumer | no selected normal-user L2 EtherIP/IPsec peer | N/A-CONSUMER |

The matrix distinguishes researched architecture from deployment support claims.
