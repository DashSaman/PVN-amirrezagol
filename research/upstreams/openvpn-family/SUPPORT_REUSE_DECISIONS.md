# OpenVPN Family — PVNetwork Support / Reuse Decision Record

Decision date: 2026-08-14

State: research architecture decision only. No PVNetwork OpenVPN implementation/certification exists.

## 001 — OpenVPN

Research classification:

**`HIGH-PRIORITY CORE VPN TARGET / OPENVPN3-CORE-FIRST`**

## Primary engine candidate

### OpenVPN 3 Core

Pinned source:

`OpenVPN/openvpn3@1fd271caefc9a71406afdc2ff2460999dcfdb234`

Role: C++ client class library used by OpenVPN Connect product family.

License at pin:

**AGPL-3.0-only OR MPL-2.0** according to upstream `LICENSE.md`.

Research classification:

**`STRONG REUSE-CANDIDATE / MPL-PATH+DEPENDENCY+PLATFORM REVIEW REQUIRED`**

Preferred PVNetwork legal/architecture research direction is to evaluate the MPL-2.0 path for covered source and separately audit all bundled/transitive dependencies and required notices. This is engineering research, not final legal sign-off.

## Why OpenVPN3 is preferred over copying a GUI client

- narrow core/client library boundary;
- official upstream maintenance;
- multi-platform client-core role;
- allows PVNetwork to own branded UI/profile/storage/security model;
- avoids inheriting GPL/custom restrictions of many GUI references;
- easier to hide engine-specific types behind a stable product adapter.

## Product-owned responsibilities

OpenVPN 3 does not provide PVNetwork's complete product architecture. PVNetwork still owns:

- canonical profile schema;
- `.ovpn` import/export and normalization;
- protected credentials/certificates/private-key storage;
- Android VpnService lifecycle;
- Apple NetworkExtension lifecycle;
- Windows/Linux service/route/DNS integration;
- session state/reconnect/kill-switch policy;
- routing/DNS/per-app behavior;
- UI/localization/RTL;
- logs/support bundle redaction;
- Store/package/update behavior.

## Reference clients — source/reuse roles

### OpenVPN Connect

Role: **PRIMARY OFFICIAL UX/BEHAVIOR REFERENCE**.

Use official documentation/release behavior to understand:

- My Profiles/profile import;
- URL/file profile workflows;
- certificates/proxy/token/auth controls;
- settings and protocol/retry/DCO/IPv6/DNS-related product options;
- troubleshooting/error UX.

OpenVPN Connect full application UI source is not treated as a public code-reuse source in this repository. Use it as product behavior reference; use OpenVPN3 as the core-source candidate.

### OpenVPN GUI for Windows

Pinned source:

`OpenVPN/openvpn-gui@7295bdc155e0d8d66dd53ab9bc4eb462e77bfa7f`

Role: **WINDOWS SOURCE/INTEGRATION REFERENCE**.

Useful evidence:

- tray/menu/profile handling;
- Windows registry/preferences/config paths;
- Windows process/core integration;
- Persian translation resource exists in current pinned source.

Application license is copyleft and must be independently reviewed. Do not copy GUI code/branding into a closed product by default.

### ics-openvpn / OpenVPN for Android

Pinned source:

`schwabe/ics-openvpn@ede0aa0b334b47941407599fef3d76da8b933edf`

Role: **ANDROID ARCHITECTURE/SECURE-STORAGE/UI REFERENCE**.

Application code is GPLv2 with additional terms; reference-only by default for a closed product.

Important reusable concepts to independently implement:

- UUID profile identity;
- Android service/UI separation;
- explicit user-input-required state;
- AndroidX Security encrypted profile storage;
- `.ovpn`/remote/manual import distinctions;
- TV/shortcut/system integration concepts.

### Tunnelblick

Pinned source:

`Tunnelblick/Tunnelblick@cc3cefa77912fc103831ef8517962be438a983d2`

Role: **macOS SOURCE/UX/PRIVILEGE/LIFECYCLE REFERENCE**.

Use for macOS menu-bar/profile/config/helper architecture study. Treat application source as reference-only for a closed product unless license obligations are intentionally adopted.

### Pritunl Client

Pinned source:

`pritunl/pritunl-client-electron@69508329df8a55070d9a1758765064516bb42a3a`

Role: **UX/ARCHITECTURE REFERENCE ONLY**.

Current public license explicitly restricts commercial use/redistribution. Do not copy/vend Pritunl Client code into PVNetwork without separate rights.

## Internal profile/storage rule

OpenVPN `.ovpn` is an interoperability format, not the authoritative PVNetwork database.

PVNetwork should retain:

1. original imported `.ovpn` source when safe/useful;
2. normalized canonical profile;
3. protected credential/certificate/private-key references;
4. generated OpenVPN runtime representation;
5. transient authentication/session state.

Do not persist passwords/private keys in unprotected ordinary config files simply because OpenVPN can read inline credentials/certificates.

## Simple vs advanced UI rule

Normal user flow should expose:

- profile/server;
- connect/disconnect;
- authentication prompts;
- status/latency/basic diagnostics;
- import/subscription/account flow.

Advanced mode can expose supported OpenVPN-specific controls such as transport selection, certificate/proxy/token options, retry behavior, MTU/DCO/IPv6/DNS details only when the selected core/platform supports them.

Do not mirror every upstream config directive into a giant settings page.

## Core/version capability rule

An imported `.ovpn` profile may contain directives unsupported by OpenVPN3 or unsupported on one platform.

PVNetwork must:

- validate before connection;
- preserve unsupported directives/source;
- report exactly what is unsupported;
- never silently discard security/network directives;
- keep core-version/platform capability metadata.

## Security/release rule

OpenVPN core upgrades require:

- upstream release/security review;
- exact dependency/SBOM diff;
- parser/profile compatibility tests;
- TLS/certificate/authentication regression tests;
- reconnect/network-change tests;
- DNS/route/leak tests;
- DCO/non-DCO behavior where supported;
- Store/package regression checks.

## Final v1 reuse decision

### Core

OpenVPN3: **preferred current core candidate**.

### GUI/client code

Use OpenVPN Connect, OpenVPN GUI, ics-openvpn, Tunnelblick and Pritunl primarily as **behavior/architecture/issue references**, not as the default code base for PVNetwork UI.

### Product strategy

Build a PVNetwork-owned OpenVPN Adapter and product models around the reusable official core rather than turning PVNetwork into a reskinned existing OpenVPN client.

## Residual original-v1 gaps

- exact OpenVPN3 current release candidate and complete dependency/advisory scan;
- full adapter API mapping;
- current Android/Windows/macOS issue/release matrix;
- complete OpenVPN Connect screenshot/current product-behavior catalog;
- full UI/menu field map for all reference clients;
- exact server interoperability lab is future implementation work.

Server implementations/installers/panels, exhaustive menus, cryptography and wire/data-flow are mandatory later in `COMPLETE-REFERENCE-v2`.
