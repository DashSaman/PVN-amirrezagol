# Entry 006 — IPsec ESP — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research completion only. ESP is treated as an IPsec data-plane capability, not a fictitious standalone consumer VPN application.

Primary evidence: `research/upstreams/strongswan-family/` and `reference-v2/`.

## 20-gate audit

1. Top implementations — **PASS**: OS/kernel IPsec, strongSwan-controlled backend, Libreswan and vendor stacks mapped.
2. Canonical sources pinned — **PASS**: strongSwan current release/source pin and implementation references recorded.
3. Licenses — **PASS**: component/backend license boundaries recorded.
4. Source-tree references — **PASS**: source architecture and implementation manifests referenced.
5. Languages/build systems — **PASS**: kernel/daemon/plugin/native backend boundaries documented; N/A as a separate consumer app.
6. Architecture — **PASS**: IKE/CHILD negotiation is separated from ESP packet processing/policy/SA ownership.
7. Engine integration — **PASS**: native kernel/backend capability is selected through the IPsec adapter; no ESP crypto reimplementation.
8. UI/menu map — **PASS/N-A-STANDALONE**: effective data-SA transforms/status/settings are mapped within IPsec clients/panels, not invented as a separate app.
9. Config/import/export — **PASS**: transform/policy/traffic-selector semantics live under the IPsec profile/backend model.
10. Persistence/secrets — **PASS**: negotiated SA key material is runtime-only and excluded from ordinary app persistence/logs.
11. Platform integrations — **PASS**: OS/kernel/backend ownership across Linux/native platforms documented.
12. Logs/diagnostics — **PASS**: ESP/data-SA, effective algorithms, counters/policy and route state are distinguished from IKE state.
13. Asset/screenshot references — **PASS/N-A-STANDALONE**: backend/client/panel references cover applicable status UI; no fake ESP app assets.
14. Fork/ecosystem — **PASS**: strongSwan, Libreswan, native/vendor stacks mapped as implementation families.
15. Issues/releases/advisories — **PASS**: strongSwan release/security and algorithm/backend risks documented.
16. Relevant forums/docs — **PASS**: IPsec/ESP standards and maintained implementation docs linked in reference dossier.
17. Tests/CI — **PASS**: backend tests plus required ESP/NAT-T/rekey/MTU regression classes documented.
18. Store/privacy/security — **PASS**: secrets, privileged kernel/backend ownership, algorithm policy and no custom crypto rule documented.
19. Reuse decision — **PASS**: foundational backend capability; use native/maintained IPsec implementation, never implement transforms from scratch.
20. Uncertainties — **PASS**: exact negotiated suites, kernel/provider versions, NAT-T/runtime interoperability and performance remain explicit certification residuals.

The deeper `reference-v2/ENTRY_004_007_V2_GATE_RECONCILIATION.md` records every applicable V2 source/reference category as PASS, using evidence-backed `N/A-AS-STANDALONE` where appropriate.

# Formal result

All applicable written v1 research gates are evidence-backed. **Entry 006 may be promoted to `COMPLETE-RESEARCH-v1`.**
