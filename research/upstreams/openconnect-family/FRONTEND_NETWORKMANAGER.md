# NetworkManager-openconnect — Linux Front-End / Integration Dossier

Research date: 2026-08-14

Research state: `IN-RESEARCH`; not PVNetwork implementation evidence.

## Source provenance

Canonical upstream: `https://gitlab.gnome.org/GNOME/NetworkManager-openconnect`.

Official GitHub research mirror: `GNOME/NetworkManager-openconnect` (repository description explicitly identifies it as a read-only mirror of the GNOME GitLab project).

Pinned mirror commit for this dossier:

`ea97564887f897a3a9bb8edf49d4a70bebae5a4a`

Complete recursive tree reference:

`https://api.github.com/repos/GNOME/NetworkManager-openconnect/git/trees/ea97564887f897a3a9bb8edf49d4a70bebae5a4a?recursive=1`

The reviewed tree is complete (`truncated=false`).

## Technology / module layout

Primary language: C.

Important current tree areas:

- `src/` — NetworkManager VPN service integration and helper code.
- `auth-dialog/` — authentication UI and OpenConnect-facing authentication logic.
- `properties/` — NetworkManager connection editor/plugin, auth helpers and GTK UI definitions.
- `gtk4/` — GTK4 connection-dialog resources.
- `shared/` — common NetworkManager plugin utilities.
- `po/` — localization catalog.
- `appdata/` — application metadata/visual reference assets.
- `.obs/`, packaging specs and Debian/RPM material — distribution integration evidence.
- `nm-openconnect-service.conf` and sysusers/service metadata — service/privilege integration evidence.

Source files verified include:

- `src/nm-openconnect-service.c`
- `src/nm-openconnect-service-openconnect-helper.c`
- `auth-dialog/main.c`
- `auth-dialog/README`
- `properties/nm-openconnect-editor.c`
- `properties/nm-openconnect-editor-plugin.c`
- `properties/nm-openconnect-dialog.ui`
- `gtk4/nm-openconnect-dialog.ui`

## Dependency / integration evidence

Pinned `configure.ac` currently requires or supports:

- GLib/GModule/libxml;
- NetworkManager `libnm`;
- GNOME UI builds using GTK3/libnma/gcr;
- optional GTK4/libnma-gtk4 build;
- `libsecret` when GNOME/GTK UI support is built;
- `libopenconnect` for the auth dialog;
- WebKit2GTK 4.1 or 4.0 for the auth dialog;
- optional `sso-mib` support for Entra Conditional Access;
- systemd/sysusers integration.

This is strong evidence for a layered Linux architecture in which NetworkManager owns connection/service integration, a dedicated frontend owns authentication/UI, libsecret handles protected desktop secrets, and WebKit can participate in browser-style enterprise authentication.

PVNetwork should learn from the separation without copying the GNOME plugin architecture wholesale onto other operating systems.

## Authentication-dialog architecture

The pinned `auth-dialog/README` describes the authentication UI as effectively a small browser/form renderer:

1. select a server/profile endpoint;
2. render arbitrary server-provided forms and choices;
3. collect authentication/session result data;
4. hand the resulting authenticated state to the tunnel layer;
5. return remembered/non-secret state to NetworkManager storage.

The README also makes an important distinction between values used by the core tunnel connection and frontend-only remembered state such as the last server/autoconnect choice.

### PVNetwork lesson

Do not represent enterprise authentication as one fixed username/password screen. Create a generic Auth Challenge Model above libopenconnect, with frontend-only state kept distinct from engine/session secrets.

## Web/SSO evidence

The current source references `openconnect_set_webview_callback()` and WebKit web-view handling in `auth-dialog/main.c`, while `configure.ac` explicitly requires WebKit2GTK when the auth dialog is enabled.

This complements the separate OpenConnect GUI finding where missing frontend SSO handling produced a real “No SSO handler” failure: **library SSO capability is not frontend SSO capability**.

PVNetwork should therefore define browser/SSO as a first-class platform service behind the Enterprise Adapter, with explicit capability detection and bounded authentication state transitions.

## Secrets / persistence model

The project links against libsecret for GNOME/GTK builds, and the auth-dialog design uses NetworkManager's secret-storage model for remembered state.

The auth-dialog documentation distinguishes:

- real authentication/session secrets needed by the connection;
- non-secret or frontend state that is still stored through NetworkManager's “secrets” plumbing for lifecycle convenience;
- password fields which historically were not persisted in the same way as text/choice state.

PVNetwork should avoid inheriting ambiguous terminology from this architecture. Its product model should explicitly classify:

- secret credentials/tokens/cookies;
- certificates/private keys;
- non-secret remembered auth choices;
- profile metadata;
- temporary session material;
- diagnostic state.

Protected secret storage must use platform-appropriate facilities and ordinary logs must redact all secret classes.

## Service / editor separation

The full tree clearly separates:

- NetworkManager service/plugin code (`src/`);
- authentication UI (`auth-dialog/`);
- connection/profile editor (`properties/`);
- shared helpers and localization.

This is a strong reference for PVNetwork's own separation:

`Profile editor / Auth UI -> Enterprise Adapter -> platform service/network layer -> libopenconnect`

The UI should not own privileged network lifecycle directly.

## Localization / Persian evidence

The pinned tree contains a large gettext translation catalog under `po/`, including `po/fa.po` for Persian and `po/ar.po`/`po/he.po` for other RTL-language contexts.

This is useful terminology/localization evidence, but it is **not proof of correct PVNetwork RTL behavior**. PVNetwork must independently test BiDi handling for URLs, IP addresses, protocol IDs, hashes, certificate fingerprints, paths and logs.

## License / asset classification

The pinned packaging copyright manifest records:

- general project files: GPL-2+;
- `auth-dialog/main.c`: LGPL-2.1;
- appdata metadata: CC0-1.0;
- packaging files with their own listed terms.

Therefore this is a **path-level license project**, not a single-license code donor.

PVNetwork reuse classification:

- architecture/UX/Linux integration reference: **YES**;
- direct copy of general plugin/UI code into a closed product: **NO default; GPL review required**;
- selected LGPL component reuse: **possible only after exact path/dependency review**;
- `appdata/openconnect.png` and other visual material: reference only unless reuse rights and branding intent are explicitly approved.

## Linux product lessons

NetworkManager integration demonstrates a model where a desktop networking framework owns system connection state and a plugin/frontend handles vendor-specific/auth concerns.

PVNetwork Linux should evaluate two distinct product strategies rather than assuming one answer:

1. integrate with NetworkManager where appropriate for desktop/system lifecycle;
2. provide an application-managed adapter/service path for environments where NetworkManager is absent or inappropriate.

The final choice may vary by distribution/package target and should remain behind the same PVNetwork product-facing adapter contract.

## Regression requirements derived from this reference

Future Linux/enterprise tests should include:

- auth dialog can represent arbitrary form sequences without losing state;
- browser/SSO handoff returns to the same session correctly;
- auth cancellation cleans up service state;
- secret vs non-secret remembered values have explicit storage policy;
- service restart does not corrupt NetworkManager/profile state;
- editor changes and active-session state remain separate;
- connection cleanup removes routes/DNS/system artifacts after normal and failed sessions;
- NetworkManager update/libopenconnect update compatibility is tested together;
- GTK3/GTK4/frontend variants preserve equivalent semantics;
- Persian RTL and accessibility are independently tested.

## Remaining gaps

- exact secret-agent calls and storage schema in current source;
- file-to-widget map for the GTK3/GTK4 editor and auth dialog;
- current GNOME issue/MR regression review;
- service D-Bus interface/capability map;
- dependency/SBOM and path-level license audit beyond the packaging manifest;
- screenshots/assets catalog and reuse-rights review;
- package behavior across Debian/Fedora/Arch/other target distributions;
- real Linux desktop interoperability testing after PVNetwork implementation exists.