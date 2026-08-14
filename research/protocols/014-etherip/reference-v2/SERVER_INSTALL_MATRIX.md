# EtherIP — Server / Peer Install Matrix

Review date: 2026-08-14 UTC

| Target | Reference implementation | Install/deploy model | V2 conclusion |
|---|---|---|---|
| Linux / supported SoftEther host | SoftEther `Proto_EtherIP` | pinned SoftEther source/package; server/bridge role | REFERENCE-COVERED; exact production release still implementation freeze |
| Windows / supported SoftEther host | SoftEther server runtime | canonical product service/install path | REFERENCE-COVERED; service/firewall/privilege receipt later |
| OpenBSD | native `etherip(4)` | OS-native pseudo-interface + bridge | REFERENCE-COVERED |
| FreeBSD | native `gif(4)` + bridge | OS-native interface/bridge | REFERENCE-COVERED |
| OCI container | SoftEther server/bridge only if selected | topology-specific, may require bridge/raw-network privileges and persistent config | ADVANCED; no generic EtherIP image blessed |
| Kubernetes | no canonical protocol-specific deployment | gateway/node-specific design only | N/A-GENERIC / TOPOLOGY-SPECIFIC |
| Android/iOS consumer | no selected normal-user EtherIP peer | none | N/A-CONSUMER |

Protocol source portability is not runtime support certification. Exact package/image hashes and live install receipts belong to implementation freeze/certification, not the V2 reference gate.
