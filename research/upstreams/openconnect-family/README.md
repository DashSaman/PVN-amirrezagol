# OpenConnect Family — Shared Enterprise Client Research

Related matrix entries: **016 Cisco AnyConnect-compatible**, **017 OpenConnect/ocserv-compatible**, **018 GlobalProtect**, **019 Fortinet FortiGate SSL VPN**, **020 Pulse Secure**, **021 Ivanti Connect Secure**, **022 Juniper Network Connect**, **023 F5 BIG-IP**, and **024 Array Networks**.

Research state: **`V1-HANDOFF-READY / NOT IMPLEMENTED`** at the shared-family level. This is a research handoff state, not PVNetwork protocol support or production certification.

## Current shared evidence

### Core / source / compatibility

- `SOURCE_PIN.md` — canonical GitLab source, stable v9.21 baseline, public API version and license/source provenance.
- `VENDOR_COMPATIBILITY_MATRIX.md` — separate capability/limitation conclusions for entries 016–024.
- `ISSUE_MR_FIX_MATRIX.md` — representative/current vendor issue/MR/fix state mapped to future PVNetwork release tests.
- `LESSONS_AND_TESTS.md` — release/MR failure classes converted into PVNetwork regression requirements.
- `API_LIFETIME_AND_CALLBACKS.md` — public-API ownership/lifetime/callback rules for a future adapter.

### Dependencies / packaging / quality

- `DEPENDENCIES_AND_LGPL.md` — required/optional dependencies, helper boundary, SBOM requirements and LGPL integration models.
- `TEST_AND_CI_INVENTORY.md` — upstream CI/test coverage and the PVNetwork adapter/platform/interoperability test pyramid.
- `SECURITY_AND_ADVISORIES.md` — current and historical security-relevant upstream fixes/CVEs plus PVNetwork regression implications.
- `PACKAGING_AND_DISTRIBUTION.md` — core/GUI/NetworkManager packaging boundaries and platform-specific distribution concerns.
- `PERFORMANCE_AND_RESOURCE_EVIDENCE.md` — resource/performance evidence rules, current regression lessons and reproducible benchmark fields.

### Configuration / storage / frontends

- `CONFIG_STORAGE_AND_PLATFORM.md` — profile/session/secret/runtime separation and platform persistence direction.
- `FRONTEND_OPENCONNECT_GUI.md` — standalone Qt/C++ GUI reference, SSO/frontend gaps, credential/profile UX lessons, GPL app license and Windows/macOS packaging lessons.
- `OPENCONNECT_GUI_SCREEN_STORAGE_MAP.md` — source/file-to-screen/profile/storage map and current-vs-historical source caveat.
- `FRONTEND_NETWORKMANAGER.md` — GNOME/NetworkManager Linux integration reference, GTK/auth/browser/libsecret/service separation, Persian translation evidence and path-level licensing.
- `NETWORKMANAGER_DBUS_SECRETS.md` — D-Bus/service identity, plugin lifecycle, data-vs-secret ownership and libsecret/auth-dialog boundaries.

### Visual/reuse/final decision

- `ASSETS_AND_SCREENSHOT_CATALOG.md` — source-backed asset/resource inventory, screenshot policy and reuse-rights caution.
- `SUPPORT_REUSE_DECISIONS.md` — final research-stage reuse/certification priority for entries 016–024 and the shared Enterprise Adapter decision.

## Canonical source provenance

The old GitHub OpenConnect repository is archived and a mirror. Current release/source authority is the canonical OpenConnect GitLab project. Stable research baseline: **v9.21**. Public API research shows API version **5.10**. Core/library license: **LGPL-2.1**.

Frontends have separate source/licensing:

- OpenConnect GUI: canonical GitLab project; application license GPL-2.0-or-later. Current stable v1.6.2 is the reviewed product release; current main remains active.
- NetworkManager-openconnect: canonical GNOME GitLab project; GNOME GitHub mirror pinned for source inspection. The project has path-level licensing and GNOME/NetworkManager-specific architecture.

Do not infer frontend license from `libopenconnect` or vice versa.

## Architecture direction established

Evaluate `libopenconnect` behind a product-owned **Enterprise/Core Adapter** using its public API.

Keep outside private library internals:

- product authentication/challenge UI;
- browser/SSO handoff;
- protected credential/certificate/key storage;
- platform-specific network service/extension lifecycle;
- canonical PVNetwork profile storage;
- diagnostics/redaction/support bundles;
- vendor/version/capability certification.

The two frontend references reinforce this separation:

- OpenConnect GUI demonstrates standalone application UX and the fact that library SSO still needs explicit frontend handling.
- NetworkManager-openconnect separates connection/service ownership, authentication UI, browser integration and protected desktop secrets.

## Storage / secret model established

OpenConnect CLI config is an engine representation, not the PVNetwork database format.

Keep distinct:

1. canonical non-secret PVNetwork profile/settings;
2. protected reusable credentials and trust decisions;
3. certificates/private-key references and protected key material;
4. remembered non-secret auth choices/realm/group state;
5. short-lived authenticated session material such as cookies/SSO results;
6. runtime OpenConnect configuration and platform network state;
7. sanitized diagnostics/support exports.

`NETWORKMANAGER_DBUS_SECRETS.md` confirms this is not merely theoretical: the GNOME integration explicitly separates profile data, service/runtime secrets, user-context authentication and libsecret-backed remembered passwords.

## D-Bus / service ownership evidence established

Pinned NetworkManager-openconnect source identifies the service family as `org.freedesktop.NetworkManager.openconnect`, with a NetworkManager VPN plugin lifecycle around connect/need-secrets/disconnect and a separate auth-dialog/user-context path.

PVNetwork should preserve the principle of separating privileged/system network lifecycle from product authentication UI, while implementing platform-appropriate equivalents rather than cloning GNOME's topology on every OS.

## Dependency / LGPL direction established

`libopenconnect` remains **`REUSE-CANDIDATE / LGPL-DISTRIBUTION-REVIEW-REQUIRED`**.

Preferred engineering shape for legal/platform review: a replaceable shared-library boundary where technically and Store-feasible. Static linking is not the default approved path without a deliberate compliance/relinking design.

Final distribution must use the exact per-platform SBOM and dependency graph of the build actually shipped.

## Compatibility rule established

Never translate “OpenConnect has this protocol mode” into “PVNetwork fully supports this vendor.”

Cisco, GlobalProtect, Fortinet, Pulse/Ivanti, Juniper, F5 and Array require separate evidence for:

- exact server/vendor/version;
- auth mode;
- MFA/challenge;
- browser/SSO;
- posture/host-check;
- IPv4/IPv6;
- preferred/fallback transport;
- reconnect/network changes;
- tested PVNetwork platform/build;
- known limitations.

## Research-stage implementation/certification priority

Current provisional ordering from `SUPPORT_REUSE_DECISIONS.md`:

1. **017 OpenConnect/ocserv** — first controlled real-server integration baseline.
2. **016 Cisco AnyConnect-compatible** — highest-priority proprietary enterprise certification target.
3. **018 GlobalProtect** — high-value target with explicit SSO/HIP/version capability matrix.
4. **019 Fortinet** — conditional, exact FortiOS/protocol-mode certification only.
5. **020/021 Pulse/Ivanti** — appliance/auth/posture matrix.
6. **022 Juniper Network Connect** — legacy compatibility target.
7. **023 F5** — experimental/partial vendor-specific certification.
8. **024 Array** — limited/experimental, demand-driven.

This ordering is research planning, not a marketing ranking.

## Security direction established

`SECURITY_AND_ADVISORIES.md` records current/historical upstream security lessons including certificate-validation, certificate-metadata parsing, chunked/framing parsing, reconnect/MTU state and route-control/leakage classes.

PVNetwork must:

- pin maintained upstream versions;
- avoid reimplementing protocol parsers/crypto;
- keep certificate exceptions explicit/scoped;
- redact upstream debug logs;
- test route/DNS/kill-switch behavior independently from engine “connected” state;
- review exact shipped build dependencies/advisories before each release.

## Packaging direction established

A portable OpenConnect core does not imply one portable product package.

Packaging is separate for:

- core/library;
- standalone desktop GUI references;
- NetworkManager Linux integration;
- Windows service/helper/driver decisions;
- Android VpnService/native ABI packaging;
- Apple Network Extension/signing/Store feasibility;
- Linux distro/package/service choices.

See `PACKAGING_AND_DISTRIBUTION.md`.

## UI / menu / profile lessons established

The standalone GUI source/release history and GNOME frontend establish the following product needs:

- quick connect and full profile paths;
- profile management/editor;
- generic dynamic authentication challenges;
- browser/SSO handoff;
- certificate/trust decision UI;
- connect/disconnect/session state;
- logs/diagnostics;
- tray/menu-bar quick actions;
- explicit credential-retention controls.

These are references only. The later `COMPLETE-REFERENCE-v2` campaign must build full screen/menu inventories per client and server UI.

## Asset policy established

OpenConnect/OpenConnect-GUI/NetworkManager assets are research references, not PVNetwork branding. Public-repository presence does not automatically grant unrestricted asset reuse.

PVNetwork must use the owner's supplied official logo and its own UI identity. See `ASSETS_AND_SCREENSHOT_CATALOG.md`.

## Test/CI direction established

Upstream tests cover internal protocol/parser/auth/certificate/token/platform concerns. PVNetwork must test what it owns:

1. product models/capability/redaction;
2. public API adapter contract;
3. deterministic local integration;
4. exact vendor interoperability;
5. real platform/device/package lifecycle.

Do not claim enterprise support from upstream unit tests alone.

## Current protocol-folder synchronization

Entries 017–024 have shared evidence links/updates in their numbered research areas. Entry 016 Cisco remains a connector-write documentation blocker after materially different attempts; do not loop on the same README path. Cisco conclusions are fully preserved in the shared vendor/issues/support-decision files and handoff state.

## Remaining v1 evidence gaps

The family is now broad enough for a reasonable **`V1-HANDOFF-READY`** state, but the following remain explicit gaps rather than hidden assumptions:

- authoritative materialized full v9.21 source-archive manifest remains tool-blocked;
- stronger machine-readable current canonical OpenConnect GUI main/v1.6.2 tree materialization;
- current running-client screenshot catalog rather than source/resource-only references;
- exact dependency-advisory/SBOM review for the build eventually selected;
- current source-level frontend issue details can still be expanded;
- performance evidence remains framework/test-plan heavy until reproducible pinned benchmarks are available;
- vendor certification requires real server/version labs and implementation evidence.

These do not justify holding the entire original research campaign on this one family indefinitely. Preserve the gaps and move to the next high-value original-family backlog.

## Second-layer work is mandatory later

After the original `COMPLETE-RESEARCH-v1` campaign reaches its intended gates across the scope, execute:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

for every applicable entry. That second layer adds server implementations/installers, server/client OS install matrices, exhaustive server/client menu maps, cryptography, data-path/wire flow, ports/transports/handshake and deployment topologies.

Nothing in this dossier means PVNetwork currently implements or production-supports any enterprise protocol.
