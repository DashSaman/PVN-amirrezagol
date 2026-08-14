# Entry 007 — IPsec AH — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research completion only. AH is an advanced integrity/authentication data-plane mode, not encrypted payload protection and not a normal standalone consumer VPN profile.

Primary evidence: `research/upstreams/strongswan-family/` and `reference-v2/`.

## 20-gate audit

1. Top implementations — **PASS**: OS/kernel IPsec, strongSwan, Libreswan and vendor stacks mapped.
2. Canonical sources pinned — **PASS**: strongSwan current release/source pin and maintained implementation references recorded.
3. Licenses — **PASS**: backend/component licensing recorded without family-wide inference.
4. Source-tree references — **PASS**: source architecture and implementation manifests referenced.
5. Languages/build systems — **PASS**: backend/kernel/plugin model mapped; N/A as a separate consumer app.
6. Architecture — **PASS**: AH is separated from IKE negotiation and ESP; non-confidentiality semantics are explicit.
7. Engine integration — **PASS**: AH is an optional backend capability through native/maintained IPsec stacks; no custom crypto implementation.
8. UI/menu map — **PASS/N-A-STANDALONE**: advanced data-SA option/status belongs to IPsec client/panel surfaces, not a fictitious AH app.
9. Config/import/export — **PASS**: AH selection/policy is represented as an advanced IPsec backend capability with no automatic ESP fallback.
10. Persistence/secrets — **PASS**: runtime SA key material is not ordinary app persistence; identity/credential references remain separate.
11. Platform integrations — **PASS**: platform/backend support is capability-specific and NAT limitations are preserved.
12. Logs/diagnostics — **PASS**: AH/data-SA state and integrity semantics are distinguishable from ESP/IKE state.
13. Asset/screenshot references — **PASS/N-A-STANDALONE**: applicable backend/panel references exist; no fake consumer-client assets are invented.
14. Fork/ecosystem — **PASS**: maintained IPsec implementation families and vendor/native distinctions are mapped.
15. Issues/releases/advisories — **PASS**: strongSwan security/release evidence and AH-specific deployment limitations are documented.
16. Relevant forums/docs — **PASS**: AH/IPsec standards and maintained implementation documentation are referenced in the family/v2 dossier.
17. Tests/CI — **PASS**: backend quality evidence and required AH/non-NAT interoperability regression classes are documented.
18. Store/privacy/security — **PASS**: AH is explicitly not marketed as encryption; optional/advanced policy and privileged backend boundaries are recorded.
19. Reuse decision — **PASS**: advanced low-priority compatibility capability only through maintained/native IPsec backends.
20. Uncertainties — **PASS**: exact platform availability, intentional non-NAT interoperability and runtime behavior remain explicit certification residuals.

The deeper `reference-v2/ENTRY_004_007_V2_GATE_RECONCILIATION.md` records every applicable V2 source/reference category as evidence-backed, with `N/A-AS-STANDALONE` treatment where appropriate.

# Formal result

All applicable written v1 research gates are evidence-backed. **Entry 007 may be promoted to `COMPLETE-RESEARCH-v1`.**
