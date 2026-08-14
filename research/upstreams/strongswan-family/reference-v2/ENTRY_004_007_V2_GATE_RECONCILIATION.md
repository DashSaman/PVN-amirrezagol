# Entries 004–007 — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14

Entries:

- 004 — IKEv2/IPsec
- 005 — IKEv1/IPsec
- 006 — IPsec ESP
- 007 — IPsec AH

Purpose: reconcile the IKE/IPsec reference dossier against every completion category in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` without confusing **source/reference completeness** with **runtime certification**.

## Status vocabulary

- `REFERENCE-PASS`: the research/reference category has traceable evidence.
- `SOURCE-PASS`: a source-level question is resolved; no runtime success implied.
- `N/A-AS-STANDALONE`: the category does not make sense as a separate install/UI because the entry is a data-plane protocol owned by the IPsec backend; the dossier documents the correct backend relation instead.
- `BLOCKED_EXTERNAL`: remaining proof requires an OS/device/server/container/Store/interoperability lab not available in this agent run.

## 1. Server implementation/project ecosystem mapped

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS`
### 006 — `REFERENCE-PASS`
### 007 — `REFERENCE-PASS`

Evidence:

- `SERVER_IMPLEMENTATIONS.md`
- parent `../SOURCE_ARCHITECTURE.md`
- parent release/source pins.

Implementation families include strongSwan, Libreswan, OS/kernel native IPsec and vendor/appliance stacks as interop targets. IKE engines are explicitly separated from ESP/AH data-plane implementations.

## 2. Official and major community server installer/deployment projects reviewed

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS`
### 006 — `REFERENCE-PASS` through the selected IPsec backend/deployment
### 007 — `REFERENCE-PASS` through the selected IPsec backend/deployment

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- strongSwan official package/source model
- Libreswan v5.4 package/source/service model
- OPNsense/pfSense appliance ownership
- container/config-management supply-chain gate.

No generic community OCI image is approved by popularity alone.

## 3. Server OS/container/orchestration install matrix completed

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS`
### 006 — `REFERENCE-PASS` as backend capability
### 007 — `REFERENCE-PASS` as optional backend capability

Evidence:

- `SERVER_INSTALL_MATRIX.md`

Covers major Linux families, BSD families, appliances, Windows Server/native category, OCI and Kubernetes constraints. Representative execution receipts remain external.

## 4. Server panel/UI/control-plane maps completed

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS`
### 006 — `REFERENCE-PASS` as CHILD/data-SA/status fields
### 007 — `REFERENCE-PASS` as advanced data-SA option/status

Evidence:

- `SERVER_UI_AND_MENUS.md`

Maps strongSwan `swanctl`/VICI, Libreswan CLI/config, OPNsense IPsec menu/status model and pfSense Phase 1/Phase 2/mobile/settings/status sources. It explicitly corrects the protocol terminology difference between IKEv1 phases and IKEv2 IKE/CHILD SA exchanges.

## 5. Client install matrix completed across relevant OS targets

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS` as legacy/capability-specific paths
### 006 — `N/A-AS-STANDALONE / REFERENCE-PASS through OS IPsec backend`
### 007 — `N/A-AS-STANDALONE / REFERENCE-PASS through optional OS IPsec backend`

Evidence:

- `CLIENT_INSTALL_MATRIX.md`
- parent `PLATFORM_NATIVE_COMPARISON.md`
- parent `ANDROID_FRONTEND_EVIDENCE_6_0_7.md`

Android native/strongSwan, Apple native NetworkExtension, Windows native, Linux/NetworkManager/daemon and BSD advanced paths are separated.

## 6. Major client UI/menu maps completed separately

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS` with explicit legacy UI policy
### 006 — `REFERENCE-PASS` as effective data-SA diagnostics/settings, not separate app
### 007 — `REFERENCE-PASS` as advanced non-confidentiality setting/diagnostic

Evidence:

- `CLIENT_UI_AND_MENUS.md`
- strongSwan Android source-level UI/storage evidence
- native Android/Apple/Windows product/system UI boundary mapping
- Linux/Libreswan operator surfaces.

Current screenshots/device UI receipts remain external.

## 7. Cryptographic design documented from authoritative standards/source

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS` with deprecated/legacy classification
### 006 — `REFERENCE-PASS`
### 007 — `REFERENCE-PASS`

Evidence:

- `CRYPTOGRAPHY.md`

Reference set includes the IKEv2/IKEv1/IPsec architecture/ESP/AH algorithm-guidance and current update RFCs. No obsolete example cipher suite is promoted as a default product policy.

## 8. Data path/wire flow documented

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS`
### 006 — `REFERENCE-PASS`
### 007 — `REFERENCE-PASS`

Evidence:

- `DATA_PATH_AND_WIRE_FLOW.md`

Covers app/profile -> IKE engine -> IKE SA -> Child/data SA -> native/kernel policy/SA -> ESP/AH packet path, return path, NAT-T, rekey, route/policy modes and platform ownership differences.

## 9. Ports/transports/handshake documented

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS`
### 006 — `REFERENCE-PASS`
### 007 — `REFERENCE-PASS`

Evidence:

- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`

Separates UDP 500/4500, IKE non-ESP marker, IKEv2 exchanges, IKEv1 phases, raw ESP IP protocol 50, AH protocol 51 and UDP-encapsulated ESP/NAT-T.

## 10. Deployment topologies documented

### 004 — `REFERENCE-PASS`
### 005 — `REFERENCE-PASS` as isolated legacy compatibility topology
### 006 — `REFERENCE-PASS`
### 007 — `REFERENCE-PASS` as specialized non-NAT topology

Evidence:

- `DEPLOYMENT_TOPOLOGIES.md`

Covers road-warrior, site-to-site policy-based, VTI/XFRM route-based, transport mode, NAT-T, dual-stack, multi-Child, HA, cloud gateway, appliance, native-client-to-Linux, legacy IKEv1 and AH specialized cases.

## 11. Source/license/activity pins recorded for server and client projects

### 004–007 — `REFERENCE-PASS`

Evidence:

- strongSwan 6.0.7 release/commit/license in parent release pin;
- Libreswan v5.4 tag/commit/GPLv2 evidence in `REFERENCE_INDEX.md`/`SERVER_IMPLEMENTATIONS.md`;
- OPNsense current source/license reference in `SERVER_UI_AND_MENUS.md`;
- pfSense current source/license reference in `SERVER_UI_AND_MENUS.md`;
- Android strongSwan pinned frontend in parent evidence;
- native OS APIs treated as platform capabilities rather than open-source projects.

## 12. Security/supply-chain risks of installer projects recorded

### 004–007 — `REFERENCE-PASS`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- parent `DEPENDENCIES_SECURITY_TESTS.md`
- parent `RELEASE_SECURITY_PIN_2026-08-14.md`

Records plugin/provider/SBOM dependency, root/capability/container/XFRM risks, appliance ownership, secrets, firewall/routing changes and package/source pin requirements.

## 13. Upgrade/uninstall/rollback behavior researched

### 004–007 — `REFERENCE-PASS at reference level / BLOCKED_EXTERNAL for receipts`

Evidence:

- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `CLIENT_INSTALL_MATRIX.md`
- Libreswan pinned README upgrade/config behavior.

Strict execution still requires actual upgrade/rollback/uninstall tests with SA/policy/route/firewall/profile cleanup.

## 14. Protocol/server/client differences and uncertainties explicitly listed

### 004–007 — `REFERENCE-PASS`

Evidence:

- every v2 file uses separation rules;
- `REFERENCE_INDEX.md` defines IKEv2 != IKEv1 and IKE SA != Child/data SA;
- ESP != AH;
- native OS != strongSwan/Libreswan;
- data-plane entries are not fictitious standalone apps;
- vendor/cloud systems are interoperability targets by exact product/version.

## 15. REFERENCE_INDEX links the complete dossier

### 004–007 — `SOURCE-PASS after synchronization`

`REFERENCE_INDEX.md` exists and must be updated after this reconciliation to list all 11 mandatory files plus this gate file and current blockers.

## 16. Latest AGENTS handoff contains exact continuation state

### 004–007 — `SOURCE-PASS after checkpoint`

Create a new IKE/IPsec v2 handoff/checkpoint after synchronizing the index and machine state.

---

# Formal reference coverage result

All 16 v2 research/reference categories now have traceable evidence for entries 004–007, including evidence-backed `N/A-AS-STANDALONE` treatment where ESP/AH install/UI concepts correctly belong to the IPsec backend rather than a separate application.

This is **source/reference completeness**, not platform/server certification.

# Strict external blockers

The current environment cannot legitimately provide:

1. representative strongSwan server install -> start -> upgrade -> rollback -> uninstall receipts;
2. representative Libreswan server install/upgrade/rollback/uninstall receipts;
3. OPNsense/pfSense exact-release runtime UI/config/backup/upgrade receipts;
4. selected OCI/Kubernetes gateway execution and XFRM namespace/capability proof;
5. Android native and strongSwan clean install/provision/connect/update/uninstall on real devices;
6. Apple real-device NetworkExtension provisioning/rekey/network-change/update/uninstall evidence;
7. Windows native IKEv2 profile/provisioning/update/uninstall across selected Windows versions;
8. Linux desktop NetworkManager/plugin exact distro/UI runtime matrix;
9. synchronized interoperability matrix crossing native clients against strongSwan/Libreswan/appliance/vendor gateways;
10. legacy IKEv1 isolated compatibility lab;
11. native ESP vs NAT-T packet captures/rekey/MTU evidence;
12. AH intentional non-NAT interoperability proof.

# Promotion decision

Do **not** mark entries 004–007 `COMPLETE-REFERENCE-v2` in the strict repository tracker in this work unit.

Recommended internal states:

- 004 IKEv2/IPsec: `REFERENCE-V2-SOURCE-COMPLETE / EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 005 IKEv1/IPsec: `REFERENCE-V2-SOURCE-COMPLETE / LEGACY-EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 006 ESP: `REFERENCE-V2-SOURCE-COMPLETE / DATA-PLANE-EXECUTION-BLOCKED / NOT IMPLEMENTED`
- 007 AH: `REFERENCE-V2-SOURCE-COMPLETE / OPTIONAL-DATA-PLANE-EXECUTION-BLOCKED / NOT IMPLEMENTED`

The continuous agent should checkpoint these external blockers and immediately continue the next independent reference family rather than idling.
