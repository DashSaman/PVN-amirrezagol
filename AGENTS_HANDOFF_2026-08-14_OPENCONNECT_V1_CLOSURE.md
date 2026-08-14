# AGENTS Handoff — 2026-08-14 — OpenConnect v1 Closure Progress

This is the newest mandatory continuation checkpoint for `DashSaman/PVN-amirrezagol`.

## Priority and phase

The repository remains in research / requirements / architecture. No protocol implementation or production support is claimed.

The original `COMPLETE-RESEARCH-v1` campaign remains the active priority. The owner's later `COMPLETE-REFERENCE-v2` expansion is mandatory only after the original research gates are closed for an entry/family.

Full v2 contract:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

Commit: `2e3ba6f23b18a3ff0a967fae400eb94f8bc91582`

Roadmap was updated with R4.5 Full Protocol Reference Expansion:

commit `63e5edb452ef48370094365ba5a8efc1202d1820`

## OpenConnect / Enterprise — previous-priority work completed since the last handoff

### Dependency / LGPL integration dossier

File:

`research/upstreams/openconnect-family/DEPENDENCIES_AND_LGPL.md`

Commit: `d5031368b533d97eac5335fed101c6c46c23acfe`

Recorded findings:
- OpenConnect core/library is LGPL-2.1; frontend application licenses remain separate audits.
- Official build requirements include libxml2, zlib and a selected TLS/crypto backend such as GnuTLS or OpenSSL.
- Optional PKCS#11/TPM/proxy/token dependencies materially change the shipped dependency graph.
- Traditional OpenConnect packaging treats a `vpnc-script`-compatible network helper as a separately versioned runtime/platform component unless PVNetwork replaces it with product-owned native networking integration.
- Current preferred engineering architecture to take into legal/platform review is the public API behind a replaceable shared-library boundary where platform/Store architecture permits.
- Static linking is not approved without a deliberate LGPL compliance/relinking design.
- Final SBOM must be generated from the exact per-platform build, not inferred from repository-level license alone.

### Vendor issue / MR / fix matrix

File:

`research/upstreams/openconnect-family/ISSUE_MR_FIX_MATRIX.md`

Commit: `f6f91989e83f6e9c6449a3e5c11727ec8812ce8e`

High-impact research now separated by vendor and status. Captured examples include:
- OpenConnect v9.21 high-CPU/infinite-loop regression fix and current TLS-handshake timeout work.
- Cisco MFA continuation-state and evolving external-auth/SSO work.
- GlobalProtect SSO non-progress loops, empty-token handling and multi-phase portal/gateway SAML.
- Fortinet SAML/SSO and server/version-dependent reconnect behavior.
- Pulse/Ivanti client-identity/config behavior and TLS/framing/throughput concerns.
- Pulse/Juniper macOS parsing work.
- F5 authentication form/MFA/SAML diversity and historical auth regressions.
- Array TLS-frame packet-boundary/performance fixes.
- Windows abnormal-exit cleanup as a cross-platform lifecycle regression class.

Every active/open MR must be rechecked against canonical upstream immediately before implementation/release.

### Upstream test and CI inventory

File:

`research/upstreams/openconnect-family/TEST_AND_CI_INVENTORY.md`

Commit: `c147b4b8531a85782e7613f2cb4d7f089ace47d6`

Recorded:
- current upstream CI coverage across multiple Linux builds/configurations, static analysis, sanitizer paths, Windows MinGW and Android NDK build paths;
- protocol/framing, Pulse, PPP/TLS, ESP/replay, certificate/non-ASCII, TPM/SoftHSM, TUN/TAP, signal and public API symbol-consistency tests;
- recommended PVNetwork test pyramid: pure product-model tests -> public API adapter contract -> deterministic local integration -> exact vendor interoperability -> real platform/device/package tests.

PVNetwork should not duplicate mature upstream internal unit tests; it must test its own adapter/UI/platform/interoperability boundaries.

### Configuration / persistence / secrets / platform boundary

File:

`research/upstreams/openconnect-family/CONFIG_STORAGE_AND_PLATFORM.md`

Commit: `e50e31df0f289a751d16cec84401e39d766c48ce`

Architecture decision recorded from current evidence:

Keep four data classes separate:
1. canonical PVNetwork profile/settings;
2. protected reusable credentials and trust decisions;
3. short-lived authenticated session material such as cookies/SSO results;
4. OpenConnect runtime configuration plus platform network state.

OpenConnect CLI configuration is an engine format, not the PVNetwork canonical profile format. Authentication and tunnel establishment can be separate security/process phases.

### Public API lifetime / callback contract

File:

`research/upstreams/openconnect-family/API_LIFETIME_AND_CALLBACKS.md`

Commit: `612d8efb5140e03cbed2d3fb0f3763f672281c5c`

This is the safe smaller replacement for the earlier connector-blocked giant API adapter-map attempt.

Recorded binding rules:
- one adapter session owns one `openconnect_info` context;
- raw library pointers never enter shared/UI models;
- auth forms are converted into a generic PVNetwork Auth Challenge Model;
- callback results such as cancel/group changes are part of the protocol state machine;
- short-lived secret values must not be retained beyond their purpose;
- borrowed/library-owned runtime structures must be copied before reconnect/rekey can invalidate them;
- library-allocated certificate buffers must use the library's matching free API;
- use a conservative serialized per-session executor until exact upstream thread-safety guarantees are established;
- browser/SSO capability is API-version and frontend-capability dependent.

### Shared OpenConnect index synchronized

File:

`research/upstreams/openconnect-family/README.md`

Latest commit: `c01f2b65d644d9dfb4d3e1385b42171f6c7f5b47`

It now indexes source pin, vendor matrix, issue/fix matrix, dependency/LGPL, test/CI, config/storage, API lifetime/callbacks and both frontend dossiers.

## Existing frontend evidence

- `FRONTEND_OPENCONNECT_GUI.md` — commit `35628002c8597f4ee5d7005362e528282c55c251`
- `FRONTEND_NETWORKMANAGER.md` — commit `b185bde202684b48e3085a161d0451f6e2ddea89`

Important conclusion: core SSO capability and frontend SSO capability are separate. PVNetwork must keep Enterprise Core Adapter, generic Auth Challenge UI and platform Browser/SSO service as separate layers.

## Numbered enterprise dossiers

017–024 have been linked to shared evidence. Entry 016 Cisco AnyConnect-compatible remains a connector-write documentation blocker after two materially different README update attempts. Do not repeat the same path. Cisco conclusions are preserved in shared evidence and handoffs.

## Source archive manifest blocker

A full machine-readable local manifest/hash verification of the official OpenConnect v9.21 source archive was attempted. The official release source/signature location was identified, but the current container download path refused to materialize the archive because of tool URL-view restrictions.

Do not loop on the same download attempt. Keep this as a primary-source materialization blocker until another safe route is available. Do not promote a secondary mirror hash to authoritative source-of-truth status.

## Original OpenConnect v1 gaps still remaining

1. exact current NetworkManager D-Bus/service/secret ownership map;
2. exact OpenConnect GUI file-to-screen/profile-storage map and remaining current issue triage;
3. platform packaging/integration beyond the current desktop references;
4. security advisory/CVE and dependency-advisory review;
5. screenshot/asset reference catalog and reuse-rights classification;
6. performance/resource evidence where authoritative evidence exists;
7. final numbered-entry support/reuse decisions;
8. stable-release source archive manifest remains a tool/materialization blocker.

## Queued second-layer scope — do not make it the active campaign yet

After original research gates, execute `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` for every applicable protocol. It requires separate server implementation/installer research, server/client install matrices, detailed server/client UI menus, cryptography, data-path/wire-flow, transports/handshake and deployment topology files.

## Exact next action

1. Continue OpenConnect original v1 closure with NetworkManager D-Bus/service/secret ownership and OpenConnect GUI profile/screen/storage mapping.
2. Add security/advisory and packaging evidence where primary sources permit.
3. Update a dated project-status snapshot and `docs/PROJECT_STATE.md` after this closure work.
4. Once OpenConnect reaches a reasonable v1 handoff, continue the next highest-value unfinished **original R1–R4** family/entry from actual repository state.
5. Do not start mass `COMPLETE-REFERENCE-v2` work until the original research campaign reaches its intended gates.
6. After every meaningful work unit, create/update the newest `AGENTS_HANDOFF_*.md` and update `AGENTS.md` to point to it.

No item here is implementation or production certification.