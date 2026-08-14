# OpenConnect Family — Shared Enterprise Client Research

Related matrix entries: **016 Cisco AnyConnect-compatible**, **017 OpenConnect/ocserv-compatible**, **018 GlobalProtect**, **019 Fortinet FortiGate SSL VPN**, **020 Pulse Secure**, **021 Ivanti Connect Secure**, **022 Juniper Network Connect**, **023 F5 BIG-IP**, and **024 Array Networks**.

Research state: `IN-RESEARCH`; no PVNetwork implementation claim.

## Current shared evidence

- `SOURCE_PIN.md` — canonical GitLab source, stable v9.21 research baseline, current public API version evidence and license/source provenance.
- `VENDOR_COMPATIBILITY_MATRIX.md` — separate capability/limitation conclusions for entries 016–024.
- `LESSONS_AND_TESTS.md` — release/MR failure classes converted into PVNetwork regression requirements.
- `FRONTEND_OPENCONNECT_GUI.md` — current Qt/C++ standalone GUI reference, SSO/frontend gaps, credential/profile UX lessons, GPL application license and Windows/macOS packaging lessons.
- `FRONTEND_NETWORKMANAGER.md` — current GNOME/NetworkManager Linux integration reference, pinned complete source tree, GTK3/GTK4 editor, auth-dialog, WebKit/libsecret dependencies, service/plugin separation, Persian translation evidence and path-level licensing.

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

## Compatibility rule

Do not translate “OpenConnect implements this protocol mode” into “PVNetwork fully supports this vendor”. Cisco, GlobalProtect, Fortinet, Pulse/Ivanti, Juniper, F5 and Array all require separate capability/version evidence.

The vendor matrix records distinctions such as basic auth, browser/SSO, posture/host-check, reconnect, IPv6 and known upstream limitations.

## Current protocol-folder synchronization

Individual protocol dossiers 017, 018, 019, 020, 021, 022, 023 and 024 have been linked to the current shared evidence. Entry 016 Cisco remains a connector-write documentation gap: repeated materially different README updates were rejected, so do not retry the same path blindly. The Cisco-specific conclusions remain preserved in `VENDOR_COMPATIBILITY_MATRIX.md`, Project State/status snapshots and AGENTS handoff.

## Frontend / credential / SSO lessons now captured

- Library SSO capability is not enough: the product frontend must implement the required browser/webview/external-browser handoff.
- Enterprise authentication should be represented as a generic challenge/form state model rather than one username/password page.
- Product profile metadata, remembered non-secret choices, protected credentials/tokens/cookies, runtime session material and engine configuration are distinct data classes.
- Standalone desktop and NetworkManager-integrated Linux frontends can use the same core while requiring materially different service, secret-store and browser architectures.
- NetworkManager-openconnect's current tree contains `po/fa.po`, useful for Persian terminology reference; this is not proof of correct RTL behavior, which PVNetwork must test independently.
- Frontend application licenses can differ from the reusable OpenConnect library license; do not infer one from the other.

## Current release/quality lessons

Current upstream release/MR evidence demonstrates that:

- core upgrades can expose old bugs through newly used code paths;
- mature vendor compatibility still changes as servers evolve;
- SSO needs explicit progress/loop detection;
- a session may require multiple browser/auth phases;
- some vendor behavior depends on client/platform identity;
- protocol and platform regression dimensions must be tested together;
- UI/service/platform artifacts and cleanup require their own regression coverage in addition to core protocol tests.

See `LESSONS_AND_TESTS.md` and the frontend dossiers.

## Remaining research

- full machine-readable source manifest for the stable OpenConnect release;
- dependency/SBOM and LGPL distribution architecture;
- public API ownership/threading/callback mapping through a safe documentation path;
- exact NetworkManager secret-agent/storage calls and service D-Bus map;
- OpenConnect GUI file-to-screen/storage map and complete current issue triage;
- platform packaging/integration review beyond the two current desktop references;
- current issues/MRs mapped to merged fixes/releases per vendor;
- screenshot/asset catalogs and reuse-rights audit;
- complete test/CI inventory and performance evidence;
- final numbered-entry support/reuse decisions.

Nothing in this dossier means PVNetwork currently implements or production-supports any enterprise protocol.