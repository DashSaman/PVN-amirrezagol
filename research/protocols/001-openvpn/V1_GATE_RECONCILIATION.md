# Entry 001 — OpenVPN — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original research layer only. This document does **not** claim PVNetwork implementation, interoperability certification, Store approval, real-device testing, or production readiness.

## Evidence baseline

Primary shared dossier: `research/upstreams/openvpn-family/`.

Primary client-core candidate:

- canonical repository: `https://github.com/OpenVPN/openvpn3`
- current reviewed release tag: `release/3.11.7`
- immutable release commit: `18edfae7e7fd8051c93bd4746ec69be91eb02dbb`
- canonical tags API: `https://api.github.com/repos/OpenVPN/openvpn3/tags?per_page=10`
- canonical release commit API: `https://api.github.com/repos/OpenVPN/openvpn3/commits/18edfae7e7fd8051c93bd4746ec69be91eb02dbb`
- the release commit is upstream-signed/verified and records OpenVPN 3 Core Library 3.11.7.

Earlier source-analysis pin `1fd271caefc9a71406afdc2ff2460999dcfdb234` remains the immutable tree used by the detailed v1 source analysis. The current-release pin above is a freshness/release reference; implementation must re-run dependency/license/security review against the exact source selected to ship.

Other source-backed reference clients and their immutable analysis pins are recorded in `research/upstreams/openvpn-family/SOURCE_REVISIONS.md`:

- OpenVPN GUI Windows;
- ics-openvpn / OpenVPN for Android;
- Tunnelblick;
- Pritunl Client.

OpenVPN Connect is treated as an official proprietary product/UX behavior reference through `https://openvpn.net/connect-docs/`; its full application UI source is not assumed public.

## 1. Top clients identified and justified — PASS

Evidence:

- `research/upstreams/openvpn-family/README.md`
- `research/upstreams/openvpn-family/SUPPORT_REUSE_DECISIONS.md`
- `research/protocols/001-openvpn/reference-v2/CLIENT_UI_AND_MENUS.md`

The dossier distinguishes OpenVPN 3 Core as the primary reusable core candidate from OpenVPN Connect, OpenVPN GUI, ics-openvpn, Tunnelblick and Pritunl as behavior/platform/source references with different reuse constraints.

## 2. Canonical sources pinned — PASS

Evidence:

- `research/upstreams/openvpn-family/SOURCE_REVISIONS.md`
- canonical OpenVPN3 tag `release/3.11.7` -> `18edfae7e7fd8051c93bd4746ec69be91eb02dbb`

Public source-analyzed clients have immutable commit pins and canonical recursive-tree URLs. Proprietary OpenVPN Connect is explicitly not misrepresented as a public source tree.

## 3. Licenses reviewed — PASS

Evidence:

- `SOURCE_REVISIONS.md`
- `OPENVPN3_CORE.md`
- `OPENVPN_GUI_WINDOWS.md`
- `ICS_OPENVPN_ANDROID.md`
- `TUNNELBLICK_MACOS.md`
- `PRITUNL_CLIENT.md`
- `SUPPORT_REUSE_DECISIONS.md`

Key result: OpenVPN3 is evaluated under its upstream dual-license choice (`AGPL-3.0-only OR MPL-2.0` at the reviewed pin); GPL-family GUI/application sources are reference-only by default for a closed product; Pritunl Client is reference-only under the reviewed restrictive license. Final commercial legal review remains separate from research completion.

## 4. Complete source-tree references captured — PASS

`SOURCE_REVISIONS.md` records recursive Git tree/API references for OpenVPN3, OpenVPN GUI, ics-openvpn, Tunnelblick and Pritunl Client. The repository follows `research/SOURCE_MIRROR_POLICY.md` rather than copying entire third-party source trees.

## 5. Languages/build systems mapped — PASS

Evidence:

- `SOURCE_REVISIONS.md`
- `OPENVPN3_CORE.md`
- `DEPENDENCIES_TESTS_SECURITY.md`
- per-client source dossiers

C++/C/Java/Kotlin/Objective-C and the relevant CMake/vcpkg/Conan/Android/Apple/Windows build boundaries are recorded at the research layer.

## 6. Architecture mapped — PASS

Evidence:

- `OPENVPN3_CORE.md`
- `OPENVPN_GUI_WINDOWS.md`
- `ICS_OPENVPN_ANDROID.md`
- `TUNNELBLICK_MACOS.md`
- `reference-v2/DATA_PATH_AND_WIRE_FLOW.md`

UI, core, platform adapter/service/extension, TUN/TAP, route/DNS and privilege boundaries are separated. No claim is made that one reference client's architecture should be copied wholesale.

## 7. Core/engine integration mapped — PASS

Evidence:

- `OPENVPN3_CORE.md`
- `DEPENDENCIES_TESTS_SECURITY.md`
- `SUPPORT_REUSE_DECISIONS.md`

OpenVPN3 is the preferred current core candidate behind a PVNetwork-owned adapter. Core lifecycle, profile handoff, TLS/backend dependency, driver/TUN/DCO and product-owned responsibilities are explicitly separated.

## 8. UI/menu map completed — PASS at research-layer granularity

Evidence:

- `OPENVPN_CONNECT.md`
- `OPENVPN_GUI_WINDOWS.md`
- `ICS_OPENVPN_ANDROID.md`
- `TUNNELBLICK_MACOS.md`
- `reference-v2/CLIENT_UI_AND_MENUS.md`

The major screens/actions/states are mapped for the selected reference clients. Exact pixel screenshots and every current-version label remain a later version-specific product-reference refinement, not an unstated v1 completion gate.

## 9. Config/import/export mapped — PASS

Evidence:

- `OPENVPN_CONNECT.md`
- `ICS_OPENVPN_ANDROID.md`
- `OPENVPN_GUI_WINDOWS.md`
- `SUPPORT_REUSE_DECISIONS.md`
- `reference-v2/CLIENT_UI_AND_MENUS.md`

`.ovpn` is treated as an interoperability format rather than the authoritative PVNetwork database. Import validation, unsupported/lossy directive handling, source preservation and runtime generation boundaries are documented.

## 10. Persistence/secrets mapped — PASS

Evidence:

- `ICS_OPENVPN_ANDROID.md`
- `OPENVPN_GUI_WINDOWS.md`
- `TUNNELBLICK_MACOS.md`
- `SUPPORT_REUSE_DECISIONS.md`

The dossier records profile/preferences persistence, Android encrypted profile storage reference, macOS Keychain/helper concepts, Windows registry/config behavior, and the PVNetwork requirement to separate protected secret references from ordinary profile metadata.

## 11. Platform integrations mapped — PASS

Evidence:

- `ICS_OPENVPN_ANDROID.md`
- `TUNNELBLICK_MACOS.md`
- `OPENVPN_GUI_WINDOWS.md`
- `reference-v2/CLIENT_INSTALL_MATRIX.md`
- `reference-v2/CLIENT_UI_AND_MENUS.md`

Windows, Android, Android TV concepts, macOS, iOS/iPadOS product-reference boundaries and Linux/NetworkManager paths are recorded. Exact device certification remains future implementation evidence.

## 12. Logs/diagnostics mapped — PASS

Evidence:

- `OPENVPN_CONNECT.md`
- `OPENVPN_GUI_WINDOWS.md`
- `ICS_OPENVPN_ANDROID.md`
- `reference-v2/CLIENT_UI_AND_MENUS.md`
- `SUPPORT_REUSE_DECISIONS.md`

Connection logs, status/statistics, diagnostic/error UX and secret-redaction requirements are covered.

## 13. Asset/screenshot references mapped — PASS

Evidence comes from source-tree/resource references in the pinned GUI/application repositories plus official Connect documentation. The policy is reference-by-link/metadata; third-party logos/icons/screenshots are **not** copied into PVNetwork without explicit rights. Exact product-version screenshot capture remains optional refinement and Store/design evidence, not a requirement to copy third-party assets.

## 14. Meaningful forks/ecosystem alternatives reviewed — PASS / no fork selected

The selected core remains canonical `OpenVPN/openvpn3`. The research intentionally compares independent maintained OpenVPN clients/frontends (OpenVPN GUI, ics-openvpn, Tunnelblick, Pritunl) instead of silently selecting an unreviewed fork. No fork has evidence strong enough to displace the canonical core; therefore no fork is promoted as a reuse candidate. Any future fork considered for shipping must receive its own source/license/activity pin.

## 15. Important issues/PRs/releases/advisories reviewed — PASS

Evidence:

- `LESSONS_AND_TESTS.md` maps network-transition, parser/import, state/UI, statistics and environment-policy issue classes to PVNetwork regression requirements.
- `DEPENDENCIES_TESTS_SECURITY.md` records security/advisory and dependency-review boundaries.
- OpenVPN3 current release tag `release/3.11.7` is pinned to `18edfae7e7fd8051c93bd4746ec69be91eb02dbb`.
- OpenVPN GUI current upstream change history is canonical at `https://github.com/OpenVPN/openvpn-gui/blob/master/CHANGES.rst`.
- Tunnelblick release/security history is canonical at `https://github.com/Tunnelblick/Tunnelblick/releases`.

Issue reports are treated as version-scoped evidence, not proof that every current release still has the same bug.

## 16. Relevant forums/docs reviewed — PASS

Canonical product/protocol documentation is linked from `reference-v2/REFERENCE_INDEX.md`, including:

- `https://openvpn.net/community-docs/`
- `https://openvpn.net/connect-docs/`
- `https://openvpn.net/as-docs/`

Maintainer issue trackers/source documentation supplement those docs. A future implementation must re-check version-specific manuals and Store policy at release time.

## 17. Tests/CI reviewed — PASS

`DEPENDENCIES_TESTS_SECURITY.md` records the OpenVPN3 `test/` tree, CI/build workflows, dependency manifests and the distinction between upstream core tests and future PVNetwork adapter/platform tests. Missing product/device execution is not research completion evidence and is not fabricated.

## 18. Store/privacy/security implications reviewed — PASS

Evidence:

- `DEPENDENCIES_TESTS_SECURITY.md`
- `SUPPORT_REUSE_DECISIONS.md`
- `reference-v2/CLIENT_INSTALL_MATRIX.md`
- `reference-v2/CRYPTOGRAPHY.md`

The dossier separates Store-safe capability policy, arbitrary script/plugin restrictions, secret handling, dependency/SBOM risk, driver/DCO/platform constraints and proprietary/copy-left application-source boundaries. Store rules are explicitly marked for re-check before release.

## 19. PVNetwork reuse decision documented — PASS

`SUPPORT_REUSE_DECISIONS.md` selects OpenVPN3 as the preferred current reusable core candidate behind a PVNetwork-owned adapter; reference GUI/client applications are primarily architecture/UX references. This is an engineering research decision, not final legal or production approval.

## 20. Uncertainties explicitly listed — PASS

Remaining uncertainties are preserved rather than converted into fake evidence:

- exact OpenVPN3 source/dependency/SBOM selected for a future shipped build;
- exact per-platform DCO/driver behavior;
- current-version exhaustive UI screenshots/control labels;
- exact device/interoperability/performance/Store outcomes;
- exact proprietary OpenVPN Connect internal implementation.

These are implementation/certification/version-freeze residuals or optional reference refinements. None is a missing item in the written 20-gate v1 completion checklist after the evidence mapping above.

# Formal result

All 20 original `research/PROTOCOL_RESEARCH_TEMPLATE.md` completion gates are evidence-backed at the research layer.

**Entry 001 may be promoted to `COMPLETE-RESEARCH-v1`.**

This promotion means research completion only. It does not mean implemented, built, tested on real devices, Store-approved, interoperable with every server, or production-ready.
