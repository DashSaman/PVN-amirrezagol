# Entry 075 — XTLS — COMPLETE-REFERENCE-v2 audit

Research date: 2026-08-15

Result: **all 16 written V2 research/reference gates PASS**, with evidence-backed N/A where a removed legacy mode has no current standalone component.

This entry is intentionally a legacy/migration reference. It is not a claim that current Xray-core supports `security: "xtls"`.

Canonical current pin: `XTLS/Xray-core@7d214f8b094f75322fa3990f8aadad1c912f24f5`, MPL-2.0. Latest observed release during this run: `v26.3.27` (2026-03-27).

## 16-gate reconciliation

1. **Server ecosystem — PASS/N/A.** Current Xray-core has no standalone generic XTLS-security server mode. The current config builder explicitly returns a removed-feature error for `security: "xtls"` and points to Vision flow with TLS/REALITY. Historical XTLS deployments belong to old Xray/core-version configurations and must be preserved as legacy import/migration metadata rather than advertised as current support.
2. **Installers/deployment projects — PASS/N/A.** No independent current XTLS package exists. Applicable lifecycle is Xray-core plus historical version-specific configurations. Third-party installers/panels are non-canonical references and require independent license/supply-chain review.
3. **Server install matrix — PASS/N/A.** There is no separate current XTLS install matrix; current Xray-core platform binaries are the relevant engine matrix. Historical generic-XTLS capability is version-sensitive.
4. **Server UI/menu map — PASS/N/A.** Canonical Xray-core has no built-in graphical XTLS administration menu. Current configuration behavior is the normative surface: `security: "xtls"` is rejected. A product UI must show legacy/unsupported or migration state rather than a working current toggle.
5. **Client install matrix — PASS/N/A.** No standalone XTLS client exists. Client behavior belongs to the selected historical/current Xray-compatible client/core/version. Existing Xray-family client research maps major desktop/mobile references and their separate licenses.
6. **Client UI/menu map — PASS.** Required product mapping is legacy metadata/import preservation plus a migration warning; current flow selection such as `xtls-rprx-vision` belongs to Entry 076. GUI labels from third-party clients are not normative.
7. **Cryptography/security — PASS by boundary.** There is no current generic-XTLS cryptographic path to describe at the reviewed pin because the mode is removed. PVNetwork must not infer current cipher/handshake semantics from the legacy label. Current connection confidentiality/authentication comes from the selected supported security layer (TLS or REALITY), while Vision flow behavior is separate Entry 076.
8. **Data path/wire flow — PASS by boundary.** No current generic-XTLS wire flow exists in the reviewed config path. Historical imports must retain original source/core-version metadata; current runtime generation must not silently translate an old XTLS mode into a semantically different current flow/security stack.
9. **Ports/transports/handshake — PASS/N/A.** XTLS has no assigned standalone port. Current source rejects the generic security mode before a current XTLS handshake/transport combination can be built. Current supported transport/security combinations must be validated independently by adapter/core version.
10. **Deployment topologies — PASS.** Historical topology is recorded as a versioned Xray security-layer choice inside an application-protocol/transport stack. Current topology replaces that generic mode with supported combinations, notably Vision flow plus TLS or REALITY where valid; no standalone XTLS tunnel topology is claimed.
11. **Source/release/license/activity pins — PASS.** Current canonical source and license are pinned above. The reviewed current source is active in August 2026 and explicitly contains the removal behavior. Release pin is recorded separately from current main.
12. **Supply-chain/security risks — PASS.** Preserve source/core-version provenance for old imported XTLS profiles; do not fetch obsolete binaries from untrusted mirrors merely to retain legacy compatibility; do not equate third-party GUI support labels with canonical engine support; independently review client/panel licenses; avoid silent migration that changes security semantics.
13. **Upgrade/uninstall/rollback — PASS.** Migration is a data/schema problem, not a separate package uninstall. Preserve legacy fields and source version, surface unsupported state, and require an explicit user/admin migration to a current validated security/flow combination. Rollback, if ever required for controlled legacy analysis, must use a pinned historical engine in an isolated/testing context rather than changing current production semantics silently.
14. **Differences/uncertainties — PASS.** Legacy XTLS security mode, current XTLS Vision flow, TLS and REALITY are distinct. This audit deliberately does not reconstruct or promote obsolete historical crypto as current capability. Historical exact behavior remains version-specific and must be consulted only when importing/migrating a specific old profile.
15. **REFERENCE_INDEX — PASS.** `REFERENCE_INDEX.md` records current canonical pins, removal evidence and migration boundary.
16. **Exact continuation — PASS.** Continue Entry 076 XTLS Vision using current `xtls-rprx-vision` flow source, keeping Vision a flow/mode capability and TLS/REALITY as separate security layers.

## Completion decision

The research obligation for Entry 075 is to establish the legacy/current boundary safely and completely. Current source makes the decisive fact explicit: generic `security: "xtls"` is removed. All applicable V2 gates are therefore backed by current canonical source or evidence-backed N/A, without inventing a current implementation that does not exist. Eligible for `COMPLETE-REFERENCE-v2`.
