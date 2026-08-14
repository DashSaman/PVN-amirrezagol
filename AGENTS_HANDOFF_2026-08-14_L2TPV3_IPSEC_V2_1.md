# AGENTS Handoff — 2026-08-14 — L2TPv3/IPsec v2 slice 1

Work unit: `L2TPV3-IPSEC-COMPLETE-REFERENCE-V2`

Entry: 010 L2TPv3/IPsec

## State transition

Entry 010 source/reference state is now:

`REFERENCE-V2-SOURCE-COMPLETE / PROTECTED-PSEUDOWIRE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Strict `COMPLETE-REFERENCE-v2` tracker promotion remains PENDING because the composed Linux/XFRM/vendor/runtime/interoperability evidence listed below is not available in this agent environment.

## Completed source/reference dossier

Folder:

`research/upstreams/classic-tunnels-family/l2tpv3-ipsec-reference-v2/`

All 11 mandatory files exist:

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_INDEX.md`

Formal line-by-line gate reconciliation:

`ENTRY_010_V2_GATE_RECONCILIATION.md`

## Key reused source pins

### L2TPv3 layer

- Linux kernel: `torvalds/linux@2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- iproute2: `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- go-l2tp / ql2tpd: `katalix/go-l2tp@0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- Cisco IOS XE: current proprietary pseudowire interop reference by official documentation.

### IPsec layer

- strongSwan 6.0.7: `5973ff8e41deef4e015e1138a2de688acedf6f75`
- Libreswan v5.4: `5eb03b7772b312e705feab9ad5868678a3c007e6`
- Linux XFRM/native IPsec data plane
- completed entries 004–007 IKE/IPsec reference dossier.

## Key composition/security rules

1. Entry 010 is not a new monolithic protocol engine; it composes entry-009 pseudowire behavior with IPsec protection.
2. Direct-IP L2TPv3 protection selector is endpoint source/destination IP plus IP protocol **115**.
3. UDP L2TPv3 protection must match the real UDP endpoint/port behavior; dynamic control can move beyond initial destination 1701.
4. Two distinct architectures are modeled:
   - `FLOW-SELECTIVE-IPSEC`
   - `PROTECTED-UNDERLAY-IPSEC`
5. IPsec must become ready before production Layer-2 forwarding is enabled.
6. If protection disappears, forwarding must block; there is no clear fallback inside entry 010.
7. L2TPv3 Cookie/control authentication is separate from IKE authentication and IPsec credentials.
8. ESP supplies the cryptographic protection; AH is not automatically added.
9. Current IPsec security guidance governs proposals; old DES/3DES/SHA1 examples are not default policy.
10. IPsec does not solve Layer-2 loops, VLAN leakage, STP, MAC or broadcast risks after decapsulation.
11. A Cisco or other vendor platform is only entry-010 supported after exact protected-composition proof; feature presence alone is insufficient.
12. Consumer L2TPv2/IPsec clients are not entry-010 L2TPv3 peers.

## Files/commits created in this work unit

Known first commits:

- initial index: `9c4c69fca13541794f9e5f8789cd4585a6b661ba`
- protected implementations: `d4ecb7b7f87617eb8ee0080a078ab1b3e48935c2`
- cryptography: `af91fb5ba2732270332f19cf177244e0adab7235`

Subsequent committed files in current Git history:

- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `ENTRY_010_V2_GATE_RECONCILIATION.md`
- synchronized `REFERENCE_INDEX.md`

Repository paths are authoritative if the connector-generated commit IDs are needed later.

## Strict external blockers

Do not fabricate:

1. strongSwan/XFRM direct protocol115 selector and protected packet proof;
2. Libreswan equivalent;
3. protected UDP L2TPv3 with real static/dynamic port behavior;
4. protected-underlay route exclusivity/no-clear fallback;
5. forced IKE/ESP failure proving pseudowire forwarding blocks;
6. IKE/ESP rekey while Layer-2 frames are active;
7. synchronized packet captures showing ESP/NAT-T externally and no clear L2TPv3;
8. combined MTU/PMTU/ECN tests;
9. VLAN/bridge/STP/broadcast behavior;
10. Linux-to-Cisco exact-version protected pseudowire interop;
11. exact Cisco protected composition evidence;
12. IPv6 protected composition;
13. restart/upgrade/rollback/uninstall cleanup;
14. OCI/Kubernetes netns/capability fail-safe proof if retained.

## Do not repeat

- do not call Cookie encryption;
- do not invent port 115;
- do not infer protected Cisco support just because Cisco has separate L2TPv3 and IPsec features;
- do not remove IPsec while leaving entry-010 forwarding active;
- do not collapse Layer-2 attachment safety into cryptographic tunnel state;
- do not promote source/reference closure to runtime certification.

## Next work unit

Resolve the next entry from actual repository state. The next matrix entry after 010 is **011 SSTP / MS-SSTP** and a v1 dossier path has been checked in this run. If Run State/tree does not show a newer concurrently selected task, activate:

`SSTP-MS-SSTP-COMPLETE-REFERENCE-V2`

for entry 011.

Exact next sequence:

1. read entry-011 v1 research and any existing SSTP/SoftEther/Windows sources before duplication;
2. identify Microsoft's SSTP/MS-SSTP protocol authority and current Windows client/server behavior;
3. inventory serious open-source server/client implementations (including SoftEther where applicable) with exact pins/licenses;
4. create all 11 mandatory v2 files;
5. map TLS/HTTPS/PPP layering, port 443, certificate/authentication and data path;
6. map Windows RRAS/server UI and native client UI separately from open-source stacks;
7. record install matrices, supply-chain/update/uninstall, cryptography/TLS requirements and deployment topologies;
8. reconcile all 16 v2 gates;
9. preserve live Windows/server/device/interoperability blockers;
10. checkpoint and continue without owner prompting.
