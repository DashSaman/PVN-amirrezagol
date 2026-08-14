# AGENTS Handoff — 2026-08-14 — IKE / IPsec v2 slice 1

Work unit: `IKE-IPSEC-COMPLETE-REFERENCE-V2`

Entries:

- 004 IKEv2/IPsec
- 005 IKEv1/IPsec
- 006 IPsec ESP
- 007 IPsec AH

## State transition

The source/reference research for entries 004–007 is now:

- 004: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 005: `REFERENCE-V2-SOURCE-COMPLETE / LEGACY-EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 006: `REFERENCE-V2-SOURCE-COMPLETE / DATA-PLANE-EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 007: `REFERENCE-V2-SOURCE-COMPLETE / OPTIONAL-DATA-PLANE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Strict `COMPLETE-REFERENCE-v2` tracker promotion remains forbidden because representative install/device/interoperability/runtime receipts are not available in this environment.

## Source/reference evidence completed

Shared v2 folder:

`research/upstreams/strongswan-family/reference-v2/`

All 11 mandatory dossier categories now exist:

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

Formal line-by-line reconciliation:

`ENTRY_004_007_V2_GATE_RECONCILIATION.md`

## Key implementation/source pins

### strongSwan

- reviewed release: `6.0.7`
- release commit: `5973ff8e41deef4e015e1138a2de688acedf6f75`
- GPLv2-family root license evidence in parent dossier
- Linux/advanced interoperability primary candidate; native OS first for standard IKEv2 where capable.

### Libreswan

New v2 implementation evidence:

- release: `v5.4`
- annotated tag object: `0b94f1a582d979303e9e8ff1e5452cc5b2c49ec8`
- release commit: `5eb03b7772b312e705feab9ad5868678a3c007e6`
- root GPLv2 text
- current Linux/BSD package/source/service/NSS installation model
- v5.4 release-specific IKEv2 multiple-key-exchange / ML-KEM integration and platform/build/test changes recorded as implementation-version facts, not generic protocol requirements.

### Management/control-plane references

- OPNsense source reviewed at `6f6d6fa05ec274a4b3589d33e6e4249a162993c2`, BSD-style root license;
- pfSense source reviewed at `9363ac5b8651a1c7a333180425ce7719070f95f9`, Apache-2.0 root repo license.

Their current IPsec menu/status structure is mapped separately from the underlying IKE/IPsec engines.

## Critical semantic rules preserved

1. IKEv2/IKEv1 are control/auth/key-management protocols; ESP/AH are IPsec data-plane protocols.
2. IKE_SA and CHILD/data SA are distinct states.
3. No silent IKEv2 -> IKEv1 downgrade.
4. ESP can provide confidentiality/integrity services according to its SA; AH does not encrypt payload.
5. ESP is raw IP protocol 50 unless UDP-encapsulated for NAT traversal; AH is IP protocol 51.
6. UDP/4500 NAT-T is an outer encapsulation/demultiplexing mechanism, not a separate VPN protocol.
7. ESP/AH are not fictitious standalone consumer apps; their install/capability belongs to the selected native/kernel/backend IPsec stack.
8. Product algorithm policy is not copied from old RFC examples or OS defaults.
9. Native Apple/Android/Windows IKEv2 capability is not the same as strongSwan/Libreswan capability.
10. Secrets and derived session keys must stay outside ordinary product profile JSON/logs/backups.

## Important current standards covered

The dossier uses the current authoritative IKEv2/IPsec standards family including RFC 7296, RFC 8247, RFC 9370, RFC 9593, RFC 7383, RFC 9827, RFC 9395, historic RFC 2409, RFC 3947/3948, RFC 4301/4303/4302 and RFC 8221.

## External blockers — do not fabricate

Strict completion still requires:

1. strongSwan representative server install/start/upgrade/rollback/uninstall receipts;
2. Libreswan representative server lifecycle receipts;
3. exact-release OPNsense/pfSense runtime UI/config/backup/upgrade evidence;
4. selected OCI/Kubernetes IPsec gateway XFRM/namespace/capability execution;
5. Android native + strongSwan real-device install/provision/connect/update/uninstall;
6. Apple real-device NetworkExtension provisioning/rekey/network-change/update/uninstall;
7. Windows native IKEv2 version/profile/provision/update/uninstall matrix;
8. Linux NetworkManager/plugin exact distro/UI runtime matrix;
9. native-client x strongSwan/Libreswan/appliance/vendor interoperability matrix;
10. IKEv1 isolated legacy compatibility lab;
11. ESP native vs NAT-T synchronized packet/rekey/MTU evidence;
12. AH intentional non-NAT interoperability lab.

These are external execution blockers, not missing source/reference categories.

## Important evidence commits from this work unit

- v2 index creation: `b09974b2453d03077b1a1924f62857387b870207`
- server implementations: `c81f4028bf0ac057adb73426929c90f1d698d719`
- cryptography: `f6ac01939e9b2fa4a08fe3987d47ad75d9822c74`
- ports/handshake: `f0d7918d6bedeadc7549e621c6320cb827de84a8`
- server UI/control planes: `ba1a7f7234e32a9a8756a597447c6c28082c8562`
- server installers/projects: `413de3d17fc554f657c462ddcf2ecf3525d69658`
- server install matrix: `b700d3e6638ab22903e87f9e875fcf9cd3cfacaa`
- client install matrix: `1aaa8cee7d7eceab2d60930ae29bc2dcac7b0d8e`
- client UI/menu map: `57a279c6460cf1e04b9615353220a66605d6f80b`
- data path/wire flow: `024e6740ba7dc1497d4990a822f33a536ac01831`
- deployment topologies: `dd478b41cb54aa76e02bd77a3277cb752e3c935c`
- gate reconciliation: `30c71e37589726d7740ae40dee0b5d79e0376e4c`
- final index synchronization: `8e7b6c804241a78b6a177753f90f8bbf6f6051cb`

## Failed/unsafe approaches not to repeat

- do not merge IKE and ESP/AH into one flat “IPsec protocol” state;
- do not infer IKEv1 from a native API that specifically implements IKEv2;
- do not infer AH from generic IPsec support;
- do not install competing unmanaged IKE daemons over OPNsense/pfSense appliance ownership;
- do not recommend generic privileged Docker images without source/image/XFRM review;
- do not treat an algorithm mentioned in source or an OS default as approved policy;
- do not promote source/reference completeness to runtime certification.

## Next work unit

Activate:

**`L2TP-IPSEC-COMPLETE-REFERENCE-V2`**

Entry:

- 008 L2TP/IPsec

Existing v1 decision:

`V1-HANDOFF-READY / LEGACY COMPOSED COMPATIBILITY TARGET / NOT IMPLEMENTED`

### Exact next action

1. read existing classic-tunnel/L2TP/strongSwan evidence before duplicating work;
2. preserve layered model:
   - IKE/IPsec protection layer;
   - L2TP control/data/session layer;
   - PPP/user authentication/addressing layer where applicable;
3. identify serious server/client implementations and native OS stacks;
4. create all 11 mandatory v2 reference files for entry 008;
5. map server and client installation by OS;
6. map server UI and client UI separately;
7. document L2TP/IPsec crypto/wire/data flow and UDP/ports/encapsulation sequence;
8. record security/legacy status and downgrade/migration guidance;
9. reconcile all 16 v2 gates and preserve runtime blockers;
10. checkpoint and continue next independent family without owner prompting.
