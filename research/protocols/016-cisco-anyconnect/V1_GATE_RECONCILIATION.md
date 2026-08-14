# 016 — Cisco AnyConnect-compatible / Cisco Secure Client — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Scope: original 20-gate research contract only. This file does **not** claim that PVNetwork implements, interoperates with, or is certified for Cisco Secure Client / AnyConnect servers.

## Scope and evidence boundary

Entry 016 is an enterprise **compatibility target**, not a claim that Cisco source is available.

Authoritative proprietary behavioral reference:

- Cisco Secure Client 5.1 desktop release notes; current reviewed recommended desktop release **5.1.18.314**.
- Cisco Secure Client 5.1 administrator/feature documentation.
- separate current Cisco iOS and Android release-note streams.
- entry-specific evidence in `PROPRIETARY_REFERENCE_CURRENT.md` and `CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`.

Public reusable/behavioral implementation reference:

- OpenConnect v9.21, canonical GitLab source commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`.
- public API version reviewed as 5.10 on current master.
- core/library license LGPL-2.1.
- shared evidence under `research/upstreams/openconnect-family/`.

Frontend/integration references:

- OpenConnect GUI v1.6.2 — GPL-2.0-or-later application; reference-only by default.
- NetworkManager-openconnect — GNOME/Linux integration reference with path-level licensing; pinned mirror commit `ea97564887f897a3a9bb8edf49d4a70bebae5a4a` and a complete recursive tree reference.

Cisco proprietary source tree, internal build system and internal test suite are explicitly **N/A-PUBLIC-SOURCE / PROPRIETARY**. They are not fabricated.

## Gate-by-gate reconciliation

### 1. Top clients / implementations identified and justified — PASS

The top reference set is explicit:

1. **Cisco Secure Client 5.1.x / AnyConnect VPN core** — authoritative proprietary client/product behavior reference; desktop current reviewed recommended release 5.1.18.314.
2. **OpenConnect v9.21** — mature public AnyConnect-compatible core/library and primary reuse candidate.
3. **OpenConnect GUI v1.6.2** — desktop UI/integration behavioral reference, GPL application.
4. **NetworkManager-openconnect** — Linux service/auth/secret/UI integration reference.

Cisco is authoritative for product/platform behavior; OpenConnect is the public implementation candidate. Neither role is conflated with the other.

Evidence:

- `PROPRIETARY_REFERENCE_CURRENT.md`
- `research/upstreams/openconnect-family/README.md`
- `research/upstreams/openconnect-family/SUPPORT_REUSE_DECISIONS.md`

### 2. Canonical sources pinned — PASS (`CISCO-PROPRIETARY-N/A`)

OpenConnect is pinned to canonical GitLab release v9.21 at:

`8b702bf2dbaf11302ed98629214b1df5d50a12aa`

OpenConnect GUI stable v1.6.2 and NetworkManager-openconnect source provenance are separately recorded.

Cisco Secure Client is proprietary, so a public source SHA is not applicable. Its official release notes/admin documentation are the canonical behavioral reference; current desktop 5.1.18.314 is recorded rather than inventing a source revision.

Evidence:

- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- `PROPRIETARY_REFERENCE_CURRENT.md`
- `research/upstreams/openconnect-family/FRONTEND_OPENCONNECT_GUI.md`
- `research/upstreams/openconnect-family/FRONTEND_NETWORKMANAGER.md`

### 3. License / legal reuse reviewed — PASS

Reuse boundaries are explicit:

- `libopenconnect`: LGPL-2.1, `REUSE-CANDIDATE / LGPL-DISTRIBUTION-REVIEW-REQUIRED`;
- OpenConnect GUI: GPL-2.0-or-later, `REFERENCE-ONLY` by default for a closed commercial PVNetwork app;
- NetworkManager-openconnect: path-level licensing; general UI/plugin code is not blanket-approved;
- Cisco Secure Client source/code/branding: proprietary, `DO-NOT-COPY`; official docs are behavioral reference only.

Dynamic/shared `libopenconnect` is the preferred engineering direction for later legal/platform review; static linking is not pre-approved.

Evidence:

- `research/upstreams/openconnect-family/DEPENDENCIES_AND_LGPL.md`
- `research/upstreams/openconnect-family/SUPPORT_REUSE_DECISIONS.md`
- `PROPRIETARY_REFERENCE_CURRENT.md`

### 4. Complete source-tree reference / manifest captured — PASS (`PUBLIC-CANDIDATE`; `CISCO-N/A`)

OpenConnect's immutable v9.21 canonical tree URL and exact commit are captured. The family dossier inventories important core/protocol/build/test/platform boundaries. The absence of a locally materialized recursive archive manifest is preserved as a tooling limitation, not hidden; the immutable release tree is still a traceable complete source reference.

NetworkManager-openconnect additionally has a complete recursive tree API reference (`truncated=false`). OpenConnect GUI tree/source areas are mapped with its current-vs-stable caveat.

Cisco proprietary source tree is N/A and is not claimed.

Evidence:

- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- `research/upstreams/openconnect-family/README.md`
- `research/upstreams/openconnect-family/FRONTEND_NETWORKMANAGER.md`
- `research/upstreams/openconnect-family/FRONTEND_OPENCONNECT_GUI.md`

### 5. Languages / build systems mapped — PASS

OpenConnect core is a native C library/application ecosystem with Autotools-style build tooling and platform dependencies documented in the shared dossier. OpenConnect GUI is C++/Qt/CMake. NetworkManager-openconnect is C with GNOME/NetworkManager/GTK build integration.

Cisco private implementation languages/build internals are not public and are N/A to the reuse-source audit; current official desktop/mobile package families are recorded without inventing internals.

Evidence:

- `research/upstreams/openconnect-family/DEPENDENCIES_AND_LGPL.md`
- `research/upstreams/openconnect-family/FRONTEND_OPENCONNECT_GUI.md`
- `research/upstreams/openconnect-family/FRONTEND_NETWORKMANAGER.md`
- `research/upstreams/openconnect-family/PACKAGING_AND_DISTRIBUTION.md`

### 6. Architecture mapped — PASS

The selected research architecture is:

`PVNetwork profile/auth UI/browser/secret services`

`-> product-owned Enterprise Adapter`

`-> public libopenconnect API`

`-> platform network service / tunnel ownership`

`-> exact Cisco-compatible server/headend behavior`

Authentication/browser SSO, tunnel/data transport, posture/host-check, route/DNS lifecycle and diagnostics are separate capability/state domains.

Cisco's own product additionally separates the AnyConnect VPN core from optional posture/diagnostic/other modules; PVNetwork does not model the whole Secure Client suite as one protocol.

Evidence:

- `research/upstreams/openconnect-family/README.md`
- `research/upstreams/openconnect-family/API_LIFETIME_AND_CALLBACKS.md`
- `PROPRIETARY_REFERENCE_CURRENT.md`

### 7. Core / engine integration mapped — PASS

OpenConnect's public API session/context ownership, auth-form callbacks, browser/SSO capability, borrowed/runtime data ownership, allocator/free rules, cancellation and conservative threading model are documented.

PVNetwork rule: one serialized adapter session owns one OpenConnect context; raw pointers/private internals never enter UI models; no protocol parser or cryptography is reimplemented.

Cisco's proprietary API/client internals are not the chosen integration engine and remain reference-only.

Evidence:

- `research/upstreams/openconnect-family/API_LIFETIME_AND_CALLBACKS.md`
- `research/upstreams/openconnect-family/SUPPORT_REUSE_DECISIONS.md`

### 8. UI / menu map completed — PASS

Two complementary UI references are mapped:

**Cisco Secure Client current official UI behavior**

- main connection state / Connect / Disconnect;
- Windows Advanced Window and AnyConnect VPN Statistics drawer;
- macOS Statistics control/application-menu diagnostics;
- Linux Details/diagnostics path;
- Preferences/profile ownership distinction;
- diagnostics/DART behavior;
- component-specific Advanced Panel concept;
- localization/customization reference.

**Open-source frontend references**

- OpenConnect GUI quick-connect, profile, disconnect, logs, tray and state lessons;
- NetworkManager-openconnect service/editor/auth-dialog separation and browser/auth flow.

No Cisco visual design is copied.

Evidence:

- `CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`
- `research/upstreams/openconnect-family/FRONTEND_OPENCONNECT_GUI.md`
- `research/upstreams/openconnect-family/FRONTEND_NETWORKMANAGER.md`
- `research/upstreams/openconnect-family/OPENCONNECT_GUI_SCREEN_STORAGE_MAP.md`

### 9. Configuration / import / export mapped — PASS

OpenConnect CLI config is documented as an engine representation, not PVNetwork canonical storage. Cisco administrator-managed VPN profiles and user/global preferences are distinct from user credentials/session state.

PVNetwork will parse/import supported enterprise configuration into a canonical profile, preserve vendor-specific fields/capability metadata, report lossy/unsupported conversion and only generate runtime OpenConnect configuration at the adapter boundary.

No promise is made that Cisco proprietary profile XML or OpenConnect GUI storage is a universal cross-vendor format.

Evidence:

- `research/upstreams/openconnect-family/CONFIG_STORAGE_AND_PLATFORM.md`
- `CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`

### 10. Persistence / secure storage mapped — PASS

The research separates:

1. durable non-secret profile metadata;
2. protected reusable credentials/certificate/key references;
3. short-lived authentication cookies/SSO/session state;
4. certificate trust decisions scoped to server context;
5. runtime/network state;
6. sanitized diagnostics.

Cisco current user/global preference paths are recorded as product behavior reference, while OpenConnect/NetworkManager evidence establishes secret/frontend/session separation. Cisco's private credential-store implementation is not guessed.

Evidence:

- `research/upstreams/openconnect-family/CONFIG_STORAGE_AND_PLATFORM.md`
- `research/upstreams/openconnect-family/NETWORKMANAGER_DBUS_AND_SECRETS.md`
- `CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`

### 11. Platform integrations mapped — PASS

Current proprietary reference covers Cisco desktop Windows/macOS/Linux plus separate iOS/iPadOS and Android release streams. The reusable implementation dossier covers Linux/Windows/Android build evidence and explicitly treats Apple integration as requiring a platform-specific Network Extension architecture rather than assuming source portability equals App Store feasibility.

NetworkManager-openconnect supplies a detailed Linux service/auth/secret reference.

PVNetwork platform support remains future implementation/certification work.

Evidence:

- `PROPRIETARY_REFERENCE_CURRENT.md`
- `research/upstreams/openconnect-family/PACKAGING_AND_DISTRIBUTION.md`
- `research/upstreams/openconnect-family/TEST_AND_CI_INVENTORY.md`
- `research/upstreams/openconnect-family/FRONTEND_NETWORKMANAGER.md`

### 12. Logs / diagnostics mapped — PASS

Cisco current official diagnostics expose session statistics, exported stats and DART-style diagnostic gathering. OpenConnect exposes progress/debug output whose raw HTTP/auth detail can contain sensitive context.

PVNetwork requirements are explicit:

- stable product error taxonomy plus raw upstream detail for protected diagnostics;
- no raw auth/body dumps in normal logs;
- cookie/token/password/private-key redaction;
- time-bounded diagnostic mode;
- route/DNS cleanup and connection state observable separately from "engine connected".

Evidence:

- `CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`
- `research/upstreams/openconnect-family/CONFIG_STORAGE_AND_PLATFORM.md`
- `research/upstreams/openconnect-family/SECURITY_AND_ADVISORIES.md`

### 13. Images / UI assets / visual references mapped — PASS

OpenConnect GUI and NetworkManager visual/resource paths are cataloged. Cisco documentation records customizable GUI icons/logos/text, but Cisco branding/assets remain proprietary/reference-only.

PVNetwork uses owner-supplied PVNetwork branding; no Cisco/OpenConnect project identity is copied without explicit file-level rights review.

Evidence:

- `research/upstreams/openconnect-family/ASSETS_AND_SCREENSHOT_CATALOG.md`
- `CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`

### 14. Meaningful forks / implementation ecosystem reviewed — PASS (`NO-CISCO-SOURCE-FORK`)

There is no public Cisco source tree whose forks can be meaningfully audited. That part is evidence-backed N/A.

For the public ecosystem, the dossier distinguishes canonical OpenConnect GitLab from the archived GitHub mirror, studies the separate OpenConnect GUI frontend and NetworkManager integration, and rejects the assumption that one frontend/source/license represents the entire ecosystem.

No abandoned fork is selected as a PVNetwork engine candidate.

Evidence:

- `research/upstreams/openconnect-family/README.md`
- `research/upstreams/openconnect-family/SOURCE_PIN.md`
- frontend dossiers.

### 15. Important issues / PRs / releases / advisories reviewed — PASS

Cisco current release notes are reviewed for the current product line and migration away from AnyConnect 4.x. OpenConnect's issue/MR matrix contains Cisco-specific current/high-impact lessons including:

- MFA continuation/opaque-state handling;
- external-browser/SSO capability advertisement and helper work;
- posture/CSD platform limitations;
- TLS timeout/cancellation;
- v9.21 high-CPU/infinite-loop fix;
- historical certificate/parser/reconnect security classes.

All are converted into explicit future regression/capability tests rather than assumed fixed forever.

Evidence:

- `PROPRIETARY_REFERENCE_CURRENT.md`
- `research/upstreams/openconnect-family/ISSUE_MR_FIX_MATRIX.md`
- `research/upstreams/openconnect-family/SECURITY_AND_ADVISORIES.md`

### 16. Relevant official docs / community lessons reviewed — PASS

Current Cisco official release/admin/feature documentation is now materialized in the entry. OpenConnect canonical docs, releases, issues and merge requests are documented in the shared family dossier.

Community/user reports are used only as regression lessons where the shared issue matrix preserves source references; proprietary behavior claims remain anchored to Cisco documentation.

Evidence:

- `PROPRIETARY_REFERENCE_CURRENT.md`
- `CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`
- `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`
- `research/upstreams/openconnect-family/ISSUE_MR_FIX_MATRIX.md`

### 17. Tests / CI reviewed — PASS

OpenConnect's canonical CI/test inventory covers Linux builds, multiple distro/configuration paths, static analysis, sanitizers, Windows MinGW, Android NDK, protocol/auth/certificate/framing/TUN-related tests and API/export checks.

Cisco internal CI is proprietary and not claimed. Current Cisco release known/resolved issues remain behavioral reference.

PVNetwork's required test pyramid is defined from pure product models through adapter tests, deterministic open-server/mock integration, exact Cisco vendor lab and real platform/package lifecycle.

Evidence:

- `research/upstreams/openconnect-family/TEST_AND_CI_INVENTORY.md`
- `research/upstreams/openconnect-family/ISSUE_MR_FIX_MATRIX.md`

### 18. Store / privacy / security implications reviewed — PASS

The dossier maps:

- LGPL distribution/relinking obligations for `libopenconnect`;
- GPL/path-level frontend restrictions;
- exact per-platform SBOM requirement;
- Android VpnService/native packaging implications;
- Apple Network Extension/signing/Store feasibility as separate work;
- Windows service/driver/MSIX/Store concerns;
- Linux packaging/service/NetworkManager choices;
- certificate trust, SSO token lifetime, secret redaction and route/DNS leakage classes.

Cisco iOS/Android distribution is proprietary behavioral reference and does not grant reuse rights.

Evidence:

- `research/upstreams/openconnect-family/DEPENDENCIES_AND_LGPL.md`
- `research/upstreams/openconnect-family/PACKAGING_AND_DISTRIBUTION.md`
- `research/upstreams/openconnect-family/SECURITY_AND_ADVISORIES.md`
- `PROPRIETARY_REFERENCE_CURRENT.md`

### 19. PVNetwork reuse decision documented — PASS

Decision:

`HIGH-PRIORITY ENTERPRISE CERTIFICATION TARGET / LIBOPENCONNECT PUBLIC-API REUSE CANDIDATE`

Implementation direction if later approved:

- product-owned Enterprise Adapter;
- pinned `libopenconnect` public API;
- product/platform-owned Auth Challenge + Browser/SSO + protected-secret services;
- platform-owned network lifecycle;
- exact Cisco server/version/auth/posture capability certification.

Cisco code/UI/branding is not copied. A successful OpenConnect mode is never marketed as blanket Cisco support without lab evidence.

Evidence:

- `research/upstreams/openconnect-family/SUPPORT_REUSE_DECISIONS.md`
- `V1_RESEARCH.md`

### 20. Uncertainties explicitly listed — PASS

Bounded uncertainties after original-v1 research completion:

- exact Cisco headend/server versions for future certification are not selected;
- Cisco proprietary source/build/internal test details are intentionally unknown/N/A;
- actual Cisco interoperability, SSO/MFA and posture/CSD combinations are not certified;
- OpenConnect current open MRs/issues must be rechecked at implementation release freeze;
- final crypto backend and per-platform dependency/SBOM set are not selected;
- final LGPL distribution model needs legal/platform review;
- exact Android/Apple product architecture remains unimplemented;
- route/DNS/kill-switch/reconnect behavior needs future real-platform evidence;
- current running screenshot/accessibility/RTL verification remains future UI implementation/reference-v2 work;
- vendor certification and runtime proof remain separate from research completion.

These are explicit implementation/certification decisions or proprietary-source boundaries, not missing original research categories.

## Formal v1 result

All 20 original-v1 research gates are now evidence-backed, evidence-backed `N/A-PROPRIETARY`, or explicitly bounded with traceable uncertainty.

**Entry 016 may be promoted to `COMPLETE-RESEARCH-v1`.**

This means research completion only. It does not mean PVNetwork implements Cisco AnyConnect-compatible VPN, passes Cisco interoperability, or is Store/production certified.