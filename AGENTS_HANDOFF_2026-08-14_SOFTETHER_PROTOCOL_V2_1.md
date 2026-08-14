# AGENTS Handoff — 2026-08-14 — SoftEther native protocol v2 slice 1

Work unit: `SOFTETHER-PROTOCOL-COMPLETE-REFERENCE-V2`

Entry: 013 SoftEther VPN Protocol

## State

`REFERENCE-V2-SOURCE-COMPLETE / NATIVE-SOFTETHER-RUNTIME-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Strict v2 tracker remains PENDING.

## Dossier

`research/upstreams/classic-tunnels-family/softether-protocol-reference-v2/`

All 11 mandatory files plus `ENTRY_013_V2_GATE_RECONCILIATION.md` are committed and `REFERENCE_INDEX.md` is synchronized.

## Source baseline

- `SoftEtherVPN/SoftEtherVPN@49eb2f08641709d1af57a0d04971973ff94461db`
- root Apache-2.0
- Cedar `Protocol.c`, `Client.c`, `Server.c` and surrounding connection/session/hub code are the reviewed native source family.

Refresh exact selected current release/dependency inventory at implementation freeze.

## Critical distinctions

- entry 013 = native SoftEther VPN Protocol only;
- SSTP/L2TP-IPsec/OpenVPN-compatible/EtherIP are separate capabilities/entries;
- configurable TCP listeners such as common 443/992/5555 values are product configuration, not fixed protocol invariants;
- management (Server Manager/vpncmd) is separate from native client VPN sessions;
- TLS trust != user authentication != Virtual Hub authorization;
- Virtual Hub/local bridge/SecureNAT/cascade are networking/session topology features;
- Windows native SoftEther client is the primary client reference; non-native mobile compatibility modes do not count as native protocol support.

## Strict external blockers

Exact current release freeze, native client/server TLS/auth/Virtual Hub sessions, Windows client virtual-adapter lifecycle, Linux native client if retained, server lifecycle, certificate/auth/AAA, parallel TCP behavior, hub forwarding, SecureNAT/local bridge/VPN Bridge/cascade, disabled compatibility listeners, management RBAC, IPv4/IPv6/MTU/NAT/reconnect, performance/security/runtime receipts.

## Next task

Activate:

`ETHERIP-COMPLETE-REFERENCE-V2`

Entry 014 EtherIP.

Exact next sequence:

1. read entry-014 v1 and existing SoftEther/EtherIP evidence;
2. establish RFC3378 EtherIP wire authority and IP protocol number/encapsulation;
3. identify current serious implementations, especially SoftEther EtherIP compatibility where source evidence exists;
4. separate EtherIP itself from EtherIP-over-IPsec entry 015;
5. create all 11 mandatory v2 files;
6. document Layer-2 Ethernet payload, ports/protocol, security/no-native-encryption, bridge/VLAN/MTU/topologies and client/server/peer semantics;
7. reconcile all 16 gates;
8. checkpoint and continue without owner prompting.
