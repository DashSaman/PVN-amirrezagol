# V1 gate reconciliation — entries 016–024

Research date: 2026-08-14. Scope: original `research/PROTOCOL_RESEARCH_TEMPLATE.md` only. This is research completion, not implementation or vendor certification.

## Immutable baseline and evidence map

OpenConnect v9.21 is the reusable/open implementation baseline, pinned to exact commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa` by the canonical GitLab tag API. Core/library license is LGPL-2.1. Proprietary vendor clients/appliances are behavioral/interoperability references only; lack of their source is not represented as reusable source visibility.

Shared evidence files:

- `SOURCE_PIN.md` — canonical source/release/commit/tree identity.
- `DEPENDENCIES_AND_LGPL.md` — license, dependencies, linking/distribution boundary.
- `API_LIFETIME_AND_CALLBACKS.md` — architecture, public API, lifecycle/error ownership.
- `CONFIG_STORAGE_AND_PLATFORM.md` — configuration, persistence, secrets, lifecycle and platform integration.
- `FRONTEND_OPENCONNECT_GUI.md`, `OPENCONNECT_GUI_SCREEN_STORAGE_MAP.md`, `FRONTEND_NETWORKMANAGER.md`, `NETWORKMANAGER_DBUS_SECRETS.md` — UI/navigation, storage, service/privilege and frontend behavior references.
- `ASSETS_AND_SCREENSHOT_CATALOG.md` — visual/asset references and reuse caution.
- `ISSUE_MR_FIX_MATRIX.md`, `SECURITY_AND_ADVISORIES.md`, `LESSONS_AND_TESTS.md` — issues/MRs/releases/security/community lessons.
- `TEST_AND_CI_INVENTORY.md` — tests/CI and missing runtime certification coverage.
- `PERFORMANCE_AND_RESOURCE_EVIDENCE.md` — evidence/no-invented-benchmark rule and known resource lessons.
- `PACKAGING_AND_DISTRIBUTION.md` — Store/package/platform implications.
- `VENDOR_COMPATIBILITY_MATRIX.md` — entry-specific vendor capability boundaries.
- `SUPPORT_REUSE_DECISIONS.md` — entry-specific final research decisions.

## Exact 20-gate audit

| # | Original V1 completion gate | Result | Evidence / treatment |
|---:|---|---|---|
| 1 | Top clients identified and justified | PASS | OpenConnect core plus OpenConnect-GUI and NetworkManager frontends are the reusable/reference set; proprietary vendor clients remain reference-only. |
| 2 | Canonical sources pinned | PASS | `SOURCE_PIN.md`; v9.21 exact commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`. |
| 3 | Licenses reviewed | PASS | `DEPENDENCIES_AND_LGPL.md`; frontend licenses are kept separate from libopenconnect. |
| 4 | Complete source-tree reference/manifest captured | PASS | Immutable v9.21 tree reference is captured in `SOURCE_PIN.md`; important source/build/test/platform subtrees are inventoried across the family evidence. A locally downloaded recursive archive manifest is explicitly deferred to build/SBOM work and is not falsely claimed. |
| 5 | Languages/build systems mapped | PASS | shared source/dependency/frontend dossiers. |
| 6 | Architecture mapped | PASS | `API_LIFETIME_AND_CALLBACKS.md`, frontend/service dossiers. |
| 7 | Core/engine integration mapped | PASS | public libopenconnect adapter boundary and ownership rules are explicit. |
| 8 | UI/menu map completed | PASS | OpenConnect-GUI + NetworkManager UI/storage maps provide source-backed reusable/reference UX; proprietary UI is not claimed as source-visible. |
| 9 | Config/import/export mapped | PASS | `CONFIG_STORAGE_AND_PLATFORM.md` and frontend maps. |
| 10 | Persistence/secrets mapped | PASS | `CONFIG_STORAGE_AND_PLATFORM.md`, `NETWORKMANAGER_DBUS_SECRETS.md`. |
| 11 | Platform integrations mapped | PASS | shared platform/packaging/frontend evidence. |
| 12 | Logs/diagnostics mapped | PASS | config/platform/frontend and security evidence, including redaction requirements. |
| 13 | Asset/screenshot references mapped | PASS | `ASSETS_AND_SCREENSHOT_CATALOG.md`; no third-party asset reuse is inferred. |
| 14 | Meaningful forks reviewed | PASS/N-A | maintained canonical OpenConnect and its maintained frontends are the decision basis; archived GitHub mirror is explicitly rejected as release authority. No fork is promoted merely to fill the gate. |
| 15 | Important issues/PRs/releases/advisories reviewed | PASS | `ISSUE_MR_FIX_MATRIX.md`, `SECURITY_AND_ADVISORIES.md`, v9.20/v9.21 release review. |
| 16 | Relevant forums/docs reviewed | PASS | official OpenConnect protocol/docs/release material and maintainer issue/MR guidance are referenced throughout. |
| 17 | Tests/CI reviewed | PASS | `TEST_AND_CI_INVENTORY.md`, `LESSONS_AND_TESTS.md`. |
| 18 | Store/privacy/security implications reviewed | PASS | `PACKAGING_AND_DISTRIBUTION.md`, `SECURITY_AND_ADVISORIES.md`, dependency/license evidence. |
| 19 | PVNetwork reuse decision documented | PASS | `SUPPORT_REUSE_DECISIONS.md` with per-entry ordering and capability boundaries. |
| 20 | Uncertainties explicitly listed | PASS | vendor/version/auth/SSO/posture/runtime certification gaps are explicit below and in compatibility matrix; they are not hidden completion gates. |

## Entry-specific conclusions

- **016 Cisco AnyConnect-compatible — COMPLETE-RESEARCH-v1.** Reuse candidate is libopenconnect behind a PVNetwork-owned adapter; Cisco Secure Client/ASA/FTD behavior is proprietary reference/certification scope. AnyConnect is the mature/high-priority compatibility target, but SSO/posture/version claims require later exact-server tests.
- **017 OpenConnect / ocserv-compatible — COMPLETE-RESEARCH-v1.** Preferred controlled integration baseline because both client and compatible server can be exercised without proprietary appliance dependence. Reuse libopenconnect; treat ocserv as test/server reference, not proof of Cisco compatibility.
- **018 Palo Alto GlobalProtect — COMPLETE-RESEARCH-v1.** OpenConnect protocol mode is reusable research basis. Portal/gateway, SAML/external-browser, HIP/posture and server-version differences remain explicit certification dimensions. Current upstream SSO work means upgrade regression tracking is mandatory.
- **019 Fortinet FortiGate SSL VPN — COMPLETE-RESEARCH-v1.** Conditional/partial compatibility target. OpenConnect's Fortinet support and limitations are documented; newer/alternate Fortinet tunnel behavior must not be inferred supported. Exact FortiOS/auth/reconnect testing remains later certification.
- **020 Pulse Secure — COMPLETE-RESEARCH-v1.** OpenConnect Pulse mode is the open implementation basis. Authentication and Host Checker/TNCC limitations are explicit; no automatic equivalence with Juniper mode is claimed.
- **021 Ivanti Connect Secure — COMPLETE-RESEARCH-v1.** Treated as current vendor/product lineage around Pulse/legacy Juniper compatibility, with appliance/version/auth/posture matrix required. No proprietary source reuse claim.
- **022 Juniper Network Connect — COMPLETE-RESEARCH-v1.** Legacy compatibility target. Browser-like authentication/TNCC and IPv6 limitations are explicit. OpenConnect is the reusable implementation reference; vendor UI/source is reference-only.
- **023 F5 BIG-IP SSL VPN — COMPLETE-RESEARCH-v1.** Experimental/partial OpenConnect target with browser/JavaScript/auth and DTLS/server-generation limitations preserved. No broad F5 support claim.
- **024 Array Networks SSL VPN — COMPLETE-RESEARCH-v1.** Limited/experimental, demand-driven target. Basic authentication and DTLS/security-policy constraints are explicit; unsupported authentication is not generalized away.

## Explicit uncertainties that remain after research completion

1. Exact proprietary server/client versions, auth policies, posture modules and UI behavior vary and require later certification matrices.
2. Real-server interoperability, device/package lifecycle, Store acceptance, packet captures and performance benchmarks are implementation/certification evidence, not hidden V1 research gates.
3. A materialized local full v9.21 archive manifest was unavailable in the current connector; immutable source identity and source-area inventory are captured, while any shipped build must generate its own exact SBOM/file manifest and verify release signatures.
4. Current upstream development contains active SSO/vendor work; every library upgrade requires issue/release review and vendor regression testing.
5. Proprietary vendor code/assets are not reusable merely because protocol behavior is documented.

**Result:** entries 016–024 satisfy the original 20 V1 research gates with evidence-backed boundaries and explicit uncertainties. This result does not claim PVNetwork implementation, production support, or vendor certification.