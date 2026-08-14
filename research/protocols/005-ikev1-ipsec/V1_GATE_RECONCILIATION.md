# Entry 005 — IKEv1/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research completion only. IKEv1 remains a legacy/vendor-compatibility target and is not a default or automatic downgrade path.

Primary evidence: `research/upstreams/strongswan-family/` and `reference-v2/`. strongSwan release baseline `6.0.7` is pinned to commit `5973ff8e41deef4e015e1138a2de688acedf6f75`.

## 20-gate audit

1. Top implementations — **PASS**: strongSwan is the primary open-source reference/candidate; native/vendor capability distinctions are mapped.
2. Canonical sources pinned — **PASS**: immutable strongSwan release/source evidence and implementation references recorded.
3. Licenses — **PASS**: strongSwan GPLv2-family and alternative implementation boundaries documented.
4. Source-tree references — **PASS**: source architecture/tree and v2 implementation references exist.
5. Languages/build systems — **PASS**: daemon/plugin/native-backend build model mapped.
6. Architecture — **PASS**: IKEv1 control phases, IPsec data SA/kernel policy and management boundaries separated.
7. Engine integration — **PASS**: explicit strongSwan/approved-backend compatibility strategy; no silent IKEv2 downgrade.
8. UI/menu map — **PASS**: legacy/capability-specific client and operator surfaces documented with warnings.
9. Config/import/export — **PASS**: IKE version, identities/auth, proposals/traffic selectors and backend mapping are explicit.
10. Persistence/secrets — **PASS**: PSK/cert/private-key/user credentials are separated from ordinary metadata/runtime SA state.
11. Platform integrations — **PASS**: platform support is capability-specific and not inferred from IKEv2 APIs.
12. Logs/diagnostics — **PASS**: version/phase/data-SA/effective-policy diagnostics are distinguished.
13. Assets/screenshots — **PASS**: official source/docs references are retained; visual assets are not copied by default.
14. Fork/ecosystem — **PASS**: strongSwan, Libreswan/native/vendor stacks are treated as separate implementations/interoperability targets.
15. Issues/releases/advisories — **PASS**: strongSwan release/security evidence and legacy algorithm/policy risks are documented.
16. Relevant forums/docs — **PASS**: standards and maintained implementation/platform docs are linked in the family/v2 dossier.
17. Tests/CI — **PASS**: upstream test/security surfaces and required legacy compatibility regressions are documented.
18. Store/privacy/security — **PASS**: legacy security policy, explicit opt-in and no weak automatic fallback are documented.
19. Reuse decision — **PASS**: legacy/vendor-compatibility only, strongSwan primary reference/candidate where justified.
20. Uncertainties — **PASS**: exact vendor modes, selected algorithms/backend/platform support and runtime interoperability remain explicit.

The deeper `reference-v2/ENTRY_004_007_V2_GATE_RECONCILIATION.md` records all 16 source/reference categories as evidence-backed for entry 005 while keeping live legacy interop testing external.

# Formal result

All 20 written v1 research gates are evidence-backed. **Entry 005 may be promoted to `COMPLETE-RESEARCH-v1`.**
