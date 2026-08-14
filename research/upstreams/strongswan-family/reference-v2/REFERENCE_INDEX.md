# IKE / IPsec — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entries covered by this shared reference layer:

- **004 — IKEv2/IPsec**
- **005 — IKEv1/IPsec**
- **006 — IPsec ESP**
- **007 — IPsec AH**

These entries share implementation and deployment machinery but are not interchangeable protocol labels. IKEv1/IKEv2 are key-management/control protocols; ESP/AH are IPsec data-plane protocols. The dossier must preserve that split in every file.

## Current state

- Work unit: `IKE-IPSEC-COMPLETE-REFERENCE-V2`
- State: `IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED`
- Original v1 family state: `V1-HANDOFF-READY / NOT IMPLEMENTED`
- Main existing shared evidence: parent `strongswan-family/` files and numbered `V1_RESEARCH.md` decisions.

## Existing immutable/current implementation pins

### strongSwan

Reviewed release baseline:

- project: `strongswan/strongswan`
- release: `6.0.7`
- release commit: `5973ff8e41deef4e015e1138a2de688acedf6f75`
- root license text: GPLv2 family (`COPYING`)

See `../RELEASE_SECURITY_PIN_2026-08-14.md` for the exact release/advisory evidence and its limitations.

### Libreswan

Current alternative implementation reviewed in this v2 slice:

- project: `libreswan/libreswan`
- release: `v5.4`
- annotated tag object: `0b94f1a582d979303e9e8ff1e5452cc5b2c49ec8`
- release commit: `5eb03b7772b312e705feab9ad5868678a3c007e6`
- release publication observed: 2026-08-14
- root `COPYING`: GNU GPL version 2 text

The v5.4 release notes include IKEv2 RFC 9370 work, ML-KEM support through the implementation's NSS dependency, kernel/platform fixes, configuration/build/test updates and current test-domain refreshes. Treat this as version-specific Libreswan evidence, not as a generic IPsec requirement.

## Protocol authority

Primary standards/reference set for this slice:

- IKEv2: RFC 7296 (Internet Standard / STD 79) plus current update RFCs listed by the RFC Editor;
- IKEv1: RFC 2409 (historic/obsolete, superseded by IKEv2);
- ESP: RFC 4303;
- AH: RFC 4302;
- UDP encapsulation of ESP for NAT traversal: RFC 3948;
- IPsec architecture: RFC 4301;
- algorithm requirements/guidance must be tracked separately from base packet/exchange RFCs rather than copying obsolete defaults from old examples.

## Mandatory v2 files

| File | State |
|---|---|
| `SERVER_IMPLEMENTATIONS.md` | started |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | pending |
| `SERVER_INSTALL_MATRIX.md` | pending |
| `SERVER_UI_AND_MENUS.md` | pending |
| `CLIENT_INSTALL_MATRIX.md` | pending |
| `CLIENT_UI_AND_MENUS.md` | pending |
| `CRYPTOGRAPHY.md` | started |
| `DATA_PATH_AND_WIRE_FLOW.md` | pending |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | started |
| `DEPLOYMENT_TOPOLOGIES.md` | pending |
| `REFERENCE_INDEX.md` | active |

## Separation rules

1. **IKE_SA != CHILD_SA/IPsec SA.** Product state and diagnostics must not flatten authentication/control state into data-SA state.
2. **IKEv2 != IKEv1.** No silent downgrade or generic “IPsec supports both” assumption.
3. **ESP != AH.** ESP can provide confidentiality/integrity services according to the selected SA; AH does not provide payload confidentiality.
4. **NAT-T changes outer transport, not ESP semantics.** UDP encapsulation of ESP is a NAT traversal mechanism and must remain distinct from the ESP transform itself.
5. **Native OS IPsec != strongSwan/Libreswan.** Platform-native clients and open-source daemons have different capability, lifecycle, credential and packaging surfaces.
6. **Presence in source != enabled capability.** strongSwan plugin set, kernel backend, crypto provider and OS policy all matter.
7. **Legacy algorithm examples are not policy.** Current algorithm requirements/guidance and implementation defaults must be checked before any product policy.

## Existing parent evidence reused

- `../SOURCE_ARCHITECTURE.md`
- `../PLATFORM_NATIVE_COMPARISON.md`
- `../PROTOCOL_CAPABILITY_MODEL.md`
- `../DEPENDENCIES_SECURITY_TESTS.md`
- `../ANDROID_FRONTEND_EVIDENCE_6_0_7.md`
- `../RELEASE_SECURITY_PIN_2026-08-14.md`
- `../SUPPORT_REUSE_DECISIONS.md`

This v2 layer links and extends those files instead of duplicating them.

## Current exact next action

1. finish server implementation/deployment inventory with strongSwan, Libreswan, OS-native IPsec and serious management products;
2. complete crypto and handshake/wire separation for 004–007;
3. build server and client install matrices;
4. map strongSwan/Libreswan control surfaces plus native/Android client UI surfaces;
5. document full data path and topologies;
6. reconcile all 16 v2 contract gates per entry;
7. preserve runtime/device/interoperability blockers rather than fabricating receipts.
