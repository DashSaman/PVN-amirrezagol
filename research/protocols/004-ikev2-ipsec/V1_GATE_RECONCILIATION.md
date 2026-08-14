# Entry 004 — IKEv2/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research completion only. No runtime/device/interoperability/production claim.

Primary evidence: `research/upstreams/strongswan-family/` and its `reference-v2/` dossier. Current strongSwan release baseline is pinned to `6.0.7` -> commit `5973ff8e41deef4e015e1138a2de688acedf6f75`; security/release provenance is recorded in `RELEASE_SECURITY_PIN_2026-08-14.md`.

## 20-gate audit

1. Top clients/implementations — **PASS**: native Apple/Windows/Android IKEv2 paths, strongSwan/Linux and interop implementations are mapped.
2. Canonical sources pinned — **PASS**: strongSwan 6.0.7 immutable pin plus source/OS references.
3. Licenses reviewed — **PASS**: strongSwan GPLv2-family and other open-source implementation/license boundaries are recorded.
4. Complete source-tree reference — **PASS**: strongSwan source architecture/tree references and v2 implementation dossier.
5. Languages/build systems — **PASS**: strongSwan C/plugin/build architecture plus platform-native ownership documented.
6. Architecture — **PASS**: IKE daemon/control plane, kernel IPsec, VICI, plugin and native platform boundaries mapped.
7. Core/engine integration — **PASS**: native-IKEv2-first / strongSwan advanced/Linux strategy documented in `SUPPORT_REUSE_DECISIONS.md`.
8. UI/menu map — **PASS**: native client and strongSwan/operator UI/control surfaces in `reference-v2/CLIENT_UI_AND_MENUS.md` and server UI dossier.
9. Config/import/export — **PASS**: canonical IKE/IPsec profile semantics, backend config mapping and provisioning distinctions documented.
10. Persistence/secrets — **PASS**: identities, PSK/cert/private-key references and runtime SA material separation documented.
11. Platform integration — **PASS**: Apple NetworkExtension, Windows native, Android native/strongSwan and Linux/NetworkManager/daemon paths mapped.
12. Logs/diagnostics — **PASS**: IKE SA vs CHILD/data SA, effective algorithms, route/policy and backend status distinctions mapped.
13. Asset/screenshot references — **PASS**: official/platform source/docs are reference sources; no third-party visual assets are copied without rights.
14. Fork/ecosystem review — **PASS**: strongSwan, Libreswan and native/vendor implementations are compared without treating one as universal.
15. Issues/PRs/releases/advisories — **PASS**: current strongSwan 6.0.7 security floor, CVEs and dependency/test evidence are recorded.
16. Relevant forums/docs — **PASS**: standards/source/platform docs are linked throughout the strongSwan v2 index and protocol files.
17. Tests/CI — **PASS**: strongSwan test/dependency/security surfaces and future regression requirements are documented.
18. Store/privacy/security — **PASS**: native-vs-daemon privilege, secret handling, plugin minimization and Store/platform constraints are recorded.
19. PVNetwork reuse decision — **PASS**: native IKEv2 first where capable; strongSwan for Linux/advanced compatibility behind a typed adapter.
20. Uncertainties — **PASS**: exact shipped plugin/SBOM, runtime interoperability/device behavior and release-time Store/security checks remain explicit.

`reference-v2/ENTRY_004_007_V2_GATE_RECONCILIATION.md` independently records all 16 deeper source/reference categories as evidence-backed for entry 004. External execution receipts are implementation/certification residuals, not hidden v1 gates.

# Formal result

All 20 original research gates are evidence-backed. **Entry 004 may be promoted to `COMPLETE-RESEARCH-v1`.**
