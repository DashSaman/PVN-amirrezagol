# OpenConnect Family — Shared Enterprise Client Research

Related matrix entries: **016 Cisco AnyConnect-compatible**, **017 OpenConnect/ocserv-compatible**, **018 GlobalProtect**, **019 Fortinet FortiGate SSL VPN**, **020 Pulse Secure**, **021 Ivanti Connect Secure**, **022 Juniper Network Connect**, **023 F5 BIG-IP**, and **024 Array Networks**.

Research state: `IN-RESEARCH`; no PVNetwork implementation claim.

## Current shared evidence

- `SOURCE_PIN.md` — canonical GitLab source, stable v9.21 research baseline, current public API version evidence and license/source provenance.
- `VENDOR_COMPATIBILITY_MATRIX.md` — separate capability/limitation conclusions for entries 016–024.
- `LESSONS_AND_TESTS.md` — release/MR failure classes converted into PVNetwork regression requirements.
- `ISSUE_MR_FIX_MATRIX.md` — current/high-impact vendor issue/MR/fix state mapped to PVNetwork release tests.
- `DEPENDENCIES_AND_LGPL.md` — required/optional dependencies, network-helper boundary, SBOM requirements and LGPL integration models.
- `TEST_AND_CI_INVENTORY.md` — upstream CI/test coverage and the additional PVNetwork adapter/platform/interoperability test pyramid.
- `CONFIG_STORAGE_AND_PLATFORM.md` — core config format, authentication/session separation, profile/secret/trust storage classes and platform persistence direction.
- `FRONTEND_OPENCONNECT_GUI.md` — Qt/C++ standalone GUI reference, SSO/frontend gaps, credential/profile UX lessons, GPL application license and Windows/macOS packaging lessons.
- `FRONTEND_NETWORKMANAGER.md` — GNOME/NetworkManager Linux integration reference, pinned complete source tree, GTK3/GTK4 editor, auth-dialog, WebKit/libsecret dependencies, service/plugin separation, Persian translation evidence and path-level licensing.

## Canonical source provenance

The old GitHub OpenConnect repository is archived and a mirror. Current release/source authority is the canonical OpenConnect GitLab project. Stable research baseline: **v9.21**. Current public API research shows API version **5.10**. License: LGPL-2.1.

For front ends, canonical source must also be separated from convenience mirrors:

- OpenConnect GUI: canonical GitLab project, with the old GitHub repository archived.
- NetworkManager-openconnect: canonical GNOME GitLab project; the GNOME GitHub repository explicitly identifies itself as a read-only mirror and is pinned separately for source inspection.

## Architecture direction

OpenConnect is primarily a reusable C library/core plus CLI, not a finished PVNetwork product UI. Evaluate it through its public API behind a PVNetwork Enterprise/Core Adapter.

Keep these concerns outside private library internals:

- product authentication/challenge UI;
- browser/SSO handoff;
- protected credential/certificate storage;
- platform-specific service/extension lifecycle;
- product diagnostics and redaction;
- vendor/version compatibility certification.

The two front-end references reinforce this separation:

- the standalone Qt GUI shows an application-style desktop UX and also demonstrates that a core feature such as SSO still requires explicit frontend callback/UI support;
- NetworkManager-openconnect separates service/plugin, connection editor and authentication dialog, and combines libopenconnect with NetworkManager, libsecret and WebKit on Linux.

PVNetwork should use one product-facing Enterprise Adapter contract while allowing platform-appropriate frontend, secret-store and browser integration.

## Storage/config direction now established

OpenConnect's CLI config format is an engine option file, not the PVNetwork profile format. The evidence supports four separate data classes:

1. canonical PVNetwork profile/settings;
2. protected reusable credentials and trust decisions;
3. short-lived authenticated session material such as cookies/SSO results;
4. runtime OpenConnect configuration plus platform networking state.

Authentication and tunnel establishment can be separate phases, so the Enterprise Adapter must be able to move short-lived authenticated session state between an auth/browser context and a privileged connection context without turning it into durable plaintext profile data.

## Dependency / LGPL direction now established

Official OpenConnect build documentation identifies a small mandatory core dependency set plus optional TPM/PKCS#11/proxy/token features. Traditional packages also rely on a separately versioned `vpnc-script`-compatible network configuration helper unless the product supplies an equivalent platform-native networking layer.

`libopenconnect` remains `REUSE-CANDIDATE / LGPL-DISTRIBUTION-REVIEW-REQUIRED`. The preferred engineering shape to take into legal/platform review is the public API through a replaceable shared-library boundary where technically and Store-feasible. Static linking is not approved without a deliberate LGPL compliance/relinking plan.

See `DEPENDENCIES_AND_LGPL.md`; final legal review is still required.

## Compatibility rule

Do not translate “OpenConnect implements this protocol mode” into “PVNetwork fully supports this vendor”. Cisco, GlobalProtect, Fortinet, Pulse/Ivanti, Juniper, F5 and Array all require separate capability/version evidence.

The vendor matrix and current issue/fix matrix record distinctions such as basic auth, MFA, browser/SSO, posture/host-check, reconnect, packet framing, IPv6 and known upstream limitations.

## Current issue/MR findings now recorded

`ISSUE_MR_FIX_MATRIX.md` records current or representative high-impact classes including:

- Cisco MFA continuation-state loss and evolving external-auth/SSO support;
- GlobalProtect non-progress SSO loops, empty-token handling and multi-phase portal/gateway SAML;
- Fortinet SAML/SSO work and reconnect/DPD variation;
- Pulse/Ivanti client-identity/config behavior and TLS framing/throughput concerns;
- Juniper/Pulse macOS parsing work;
- F5 form/MFA/SSO diversity and historical auth regressions;
- Array TLS-frame packet splitting/concatenation performance fixes;
- Windows abnormal-exit cleanup lessons.

All open states must be rechecked against canonical GitLab immediately before an implementation/release decision.

## Test/CI direction now established

Current upstream CI covers multiple Linux configurations, static analysis, sanitizers, Windows MinGW and Android build paths. The upstream `tests/` tree includes protocol/framing, certificate, token, TPM/SoftHSM, signal and API-symbol consistency coverage.

PVNetwork should not duplicate upstream internal tests. Add layered tests for product models, public-API adapter contracts, controlled local integration, vendor interoperability and real platform/device/package behavior. See `TEST_AND_CI_INVENTORY.md`.

## Current protocol-folder synchronization

Individual protocol dossiers 017, 018, 019, 020, 021, 022, 023 and 024 have been linked to the current shared evidence. Entry 016 Cisco remains a connector-write documentation gap: repeated materially different README updates were rejected, so do not retry the same path blindly. The Cisco-specific conclusions remain preserved in `VENDOR_COMPATIBILITY_MATRIX.md`, `ISSUE_MR_FIX_MATRIX.md`, Project State/status snapshots and AGENTS handoff.

## Frontend / credential / SSO lessons

- Library SSO capability is not enough: the product frontend must implement the required browser/webview/external-browser handoff.
- Enterprise authentication should be represented as a generic challenge/form state model rather than one username/password page.
- Product profile metadata, remembered non-secret choices, protected credentials/tokens, runtime session material and engine configuration are distinct data classes.
- Standalone desktop and NetworkManager-integrated Linux frontends can use the same core while requiring materially different service, secret-store and browser architectures.
- NetworkManager-openconnect's current tree contains `po/fa.po`, useful for Persian terminology reference; this is not proof of correct RTL behavior, which PVNetwork must test independently.
- Frontend application licenses can differ from the reusable OpenConnect library license; do not infer one from the other.

## Remaining original-research gaps

- full machine-readable source manifest for the stable v9.21 release;
- public API ownership/threading/callback mapping through a safe documentation path;
- exact NetworkManager secret-agent/storage calls and service D-Bus map;
- OpenConnect GUI file-to-screen/storage map and complete current issue triage;
- platform packaging/integration review beyond the current desktop references;
- full security-advisory/CVE and dependency-advisory review;
- screenshot/asset catalogs and reuse-rights audit;
- performance/resource evidence;
- final numbered-entry support/reuse decisions.

## Second-layer work is queued, not yet the active priority

After the original research gates are finished, every applicable protocol must also execute:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

That second layer adds server implementation/installer research, server/client install matrices, complete server/client UI menus, cryptography, wire/data path, transports/handshake and deployment topologies. Do not abandon unfinished original research to start mass v2 expansion.

Nothing in this dossier means PVNetwork currently implements or production-supports any enterprise protocol.