# IKE / IPsec — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entries covered by this shared reference layer:

- **004 — IKEv2/IPsec**
- **005 — IKEv1/IPsec**
- **006 — IPsec ESP**
- **007 — IPsec AH**

These entries share implementation/deployment machinery but are not interchangeable protocol labels. IKEv1/IKEv2 are key-management/control protocols; ESP/AH are IPsec data-plane protocols.

## Current state

Work unit: `IKE-IPSEC-COMPLETE-REFERENCE-V2`

Source/reference state after gate reconciliation:

- 004 IKEv2/IPsec — `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 005 IKEv1/IPsec — `REFERENCE-V2-SOURCE-COMPLETE / LEGACY-EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 006 ESP — `REFERENCE-V2-SOURCE-COMPLETE / DATA-PLANE-EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 007 AH — `REFERENCE-V2-SOURCE-COMPLETE / OPTIONAL-DATA-PLANE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Strict tracker promotion remains **PENDING** because runtime/install/device/interoperability receipts listed below are not available in this environment.

Original v1 family state remains `V1-HANDOFF-READY / NOT IMPLEMENTED`.

Formal v2 reconciliation:

`ENTRY_004_007_V2_GATE_RECONCILIATION.md`

## Mandatory v2 file set

| Required file | Current state | Purpose |
|---|---|---|
| `SERVER_IMPLEMENTATIONS.md` | REFERENCE-PASS | strongSwan, Libreswan, native/kernel stacks, appliance/vendor roles |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | REFERENCE-PASS | package/source/appliance/container/config-management ownership and supply chain |
| `SERVER_INSTALL_MATRIX.md` | REFERENCE-PASS | Linux/BSD/appliance/Windows/OCI/Kubernetes server paths and external receipt table |
| `SERVER_UI_AND_MENUS.md` | REFERENCE-PASS | strongSwan/Libreswan control surfaces and OPNsense/pfSense IPsec menus/status |
| `CLIENT_INSTALL_MATRIX.md` | REFERENCE-PASS | Android native/strongSwan, Apple, Windows, Linux/BSD client paths |
| `CLIENT_UI_AND_MENUS.md` | REFERENCE-PASS | source/native UI maps and legacy/data-plane placement rules |
| `CRYPTOGRAPHY.md` | REFERENCE-PASS | IKE/ESP/AH cryptographic and SA model with current standards guidance boundaries |
| `DATA_PATH_AND_WIRE_FLOW.md` | REFERENCE-PASS | profile -> IKE -> Child/data SA -> kernel/native -> ESP/AH packet path |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | REFERENCE-PASS | UDP 500/4500, IKEv2 exchanges, IKEv1 phases, ESP 50, AH 51, NAT-T |
| `DEPLOYMENT_TOPOLOGIES.md` | REFERENCE-PASS | remote access, policy/route-based site-to-site, transport, NAT, HA, cloud, legacy/AH cases |
| `REFERENCE_INDEX.md` | synchronized | compact recovery/reference index |

## Implementation/source pins

### strongSwan

Reviewed release baseline:

- repository: `strongswan/strongswan`
- release: `6.0.7`
- release commit: `5973ff8e41deef4e015e1138a2de688acedf6f75`
- root license: GPLv2-family `COPYING`

Detailed pin/security floor:

`../RELEASE_SECURITY_PIN_2026-08-14.md`

Parent source/platform evidence:

- `../SOURCE_ARCHITECTURE.md`
- `../PLATFORM_NATIVE_COMPARISON.md`
- `../PROTOCOL_CAPABILITY_MODEL.md`
- `../DEPENDENCIES_SECURITY_TESTS.md`
- `../ANDROID_FRONTEND_EVIDENCE_6_0_7.md`
- `../SUPPORT_REUSE_DECISIONS.md`

### Libreswan

Reviewed current alternative release:

- repository: `libreswan/libreswan`
- release: `v5.4`
- annotated tag object: `0b94f1a582d979303e9e8ff1e5452cc5b2c49ec8`
- release commit: `5eb03b7772b312e705feab9ad5868678a3c007e6`
- root `COPYING`: GNU GPL version 2 text

The reviewed v5.4 release material includes current IKEv2 RFC 9370/multiple-key-exchange work, ML-KEM integration tied to an NSS dependency floor, kernel/platform fixes and refreshed test environments. These are Libreswan-version facts, not universal IKEv2 requirements.

### Management/appliance references

- OPNsense source reviewed at `opnsense/core@6f6d6fa05ec274a4b3589d33e6e4249a162993c2`, BSD-style root license;
- pfSense source reviewed at `pfsense/pfsense@9363ac5b8651a1c7a333180425ce7719070f95f9`, Apache-2.0 root repository license.

They are independent firewall/control-plane products and must not be treated as the protocol engine license or copied as PVNetwork UI.

## Standards/reference set

Primary standards used in this reference include:

- RFC 7296 — IKEv2 / STD 79
- RFC 8247 — IKEv2 algorithm implementation requirements/guidance
- RFC 9370 — multiple key exchanges for IKEv2
- RFC 9593 — supported authentication method announcements
- RFC 7383 — IKEv2 fragmentation
- RFC 9827 — current Sequence Numbers transform terminology
- RFC 9395 — IKEv1 deprecation / obsolete-algorithm updates
- RFC 2409 — historic IKEv1
- RFC 3947 / RFC 3948 — NAT traversal negotiation / UDP encapsulation of ESP
- RFC 4301 — IPsec architecture
- RFC 4303 — ESP
- RFC 4302 — AH
- RFC 8221 — ESP/AH algorithm requirements/guidance

Do not freeze old RFC example algorithms as product defaults; current guidance + exact backend/provider policy must be reviewed at release time.

## Non-negotiable separation rules

1. IKE_SA != CHILD/data SA.
2. IKEv2 != IKEv1; no silent downgrade.
3. ESP != AH; AH does not provide payload confidentiality.
4. raw ESP is IP protocol 50; AH is IP protocol 51; UDP/4500 NAT-T is an outer encapsulation/demux mechanism, not a new VPN protocol.
5. ESP/AH are not normal standalone client applications; installation belongs to the native/kernel/backend IPsec stack.
6. Native Android/Apple/Windows IKEv2 capability is not equivalent to strongSwan/Libreswan plugin capability.
7. Presence of a strongSwan plugin or an algorithm in source does not prove it is compiled, loaded, permitted or negotiated.
8. OPNsense/pfSense/vendor/cloud gateways are separate products/interoperability targets by exact version.
9. Legacy IKEv1 and AH are explicit advanced/compatibility paths, never invisible fallback mechanisms.
10. Secrets/session keys stay out of ordinary profile JSON/logs/backups.

## Strict external blockers

Remaining proof requires external environments:

- strongSwan and Libreswan install/start/upgrade/rollback/uninstall receipts on representative hosts;
- exact OPNsense/pfSense release runtime UI/config/backup/upgrade evidence;
- selected OCI/Kubernetes gateway XFRM/namespace/capability execution;
- Android native and strongSwan real-device install/provision/connect/update/uninstall;
- Apple NetworkExtension real-device provisioning/rekey/network-change/Store update;
- Windows native IKEv2 profile/version matrix and cleanup;
- Linux NetworkManager/plugin runtime UI matrix;
- synchronized interop matrix across native clients, strongSwan, Libreswan and selected appliance/vendor gateways;
- IKEv1 isolated compatibility lab;
- ESP native vs NAT-T packet/rekey/MTU receipts;
- AH intentional non-NAT interoperability lab.

These are execution blockers, not missing source/reference categories.

## Next entry boundary

Entry **008 L2TP/IPsec** is a layered composition and is **not** completed by this dossier. It must add:

- L2TP control/data/session layer;
- PPP/auth/address-assignment layer where applicable;
- separate L2TP server/client implementations and install/UI matrices;
- explicit layering over the relevant IPsec protection path.

It can reuse this IPsec reference but must remain a separate v2 work unit.

## Exact next action

Checkpoint entries 004–007 as source/reference complete but execution-blocked, update Agent/Run State, then continue **008 L2TP/IPsec COMPLETE-REFERENCE-v2** from its existing `V1-HANDOFF-READY` decision. Do not promote 004–007 in the strict tracker until their external evidence is genuinely obtained.
