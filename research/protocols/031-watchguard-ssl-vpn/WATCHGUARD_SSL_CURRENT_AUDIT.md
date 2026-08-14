# 031 — WatchGuard Mobile VPN with SSL — Current v1 Audit

Review date: 2026-08-14

Purpose: original `COMPLETE-RESEARCH-v1` evidence for WatchGuard Firebox Mobile VPN with SSL. This is not implementation or WatchGuard certification.

## Protocol/product identity

Current WatchGuard documentation explicitly establishes **OpenVPN-compatible client profile behavior** for Mobile VPN with SSL.

The Firebox can generate/download `client.ovpn`, and WatchGuard documents import into OpenVPN Connect on Android/iOS and other OpenVPN clients.

Current official first-party WatchGuard Mobile VPN with SSL clients are also available for Windows and macOS.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-US/Fireware/mvpn/ssl/mvpn_ssl_ovpn_profile_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ssl/mvpn_ssl_client-install_c.html

Research decision direction:

`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST FOR STANDARD PROFILE+WIRE SEMANTICS / WATCHGUARD FIRST-PARTY CLIENT REQUIRED FOR VENDOR-SPECIFIC SAML FEATURES`

## Scope boundary

Entry 031 is separate from:

- 030 Mobile VPN with IKEv2;
- 032 Mobile VPN with L2TP;
- older/separate Mobile VPN with IPSec;
- Firebox management SSL/TLS;
- OpenVPN server deployments unrelated to WatchGuard Fireware policy.

Generic `client.ovpn` compatibility does not prove every WatchGuard first-party authentication/provisioning feature is available to a third-party OpenVPN client.

## Current Fireware baseline / lifecycle

Current WatchGuard Help Center shows active Fireware 2026 release lines including Fireware v2026.2.1/v12.12.1 and v2026.3 product updates.

Current Mobile VPN with SSL behavior includes important Fireware version transitions:

- Fireware 12.11+ removes the Mobile VPN with SSL client download page from the Firebox; first-party client software is obtained from Software Downloads/WatchGuard Cloud instead;
- Fireware 12.11+ client no longer prompts users when an update is available;
- Fireware 12.11+ Windows first-party SSL client supports SAML;
- Fireware 12.11.2+ macOS first-party SSL client supports SAML;
- current client/server SSL VPN requires TLS 1.2 or higher.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/support/release_news_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ssl/mvpn_ssl_client-install_c.html

Exact latest downloadable client build/hash/signature remains a package-freeze requirement because the current Software Downloads portal is the authoritative distribution channel.

## OpenVPN standards/source reuse

PVNetwork already has completed OpenVPN research and a preferred OpenVPN3-based adapter direction.

Shared source evidence:

- `research/upstreams/openvpn-family/`
- completed entry 001 OpenVPN.

WatchGuard official `client.ovpn` support is strong evidence for reusing the shared OpenVPN profile/tunnel model.

PVNetwork should not create a separate WatchGuard SSL cryptographic engine.

Important boundary: WatchGuard first-party SAML and other proprietary client behaviors are outside generic OpenVPN profile compatibility unless explicitly supported by the selected backend/profile/auth flow.

## Server/admin model

Current Fireware Web UI uses:

`VPN > Mobile VPN`

then the SSL section, with wizard or manual configuration.

Current configuration planning/admin concepts include:

- primary server IP/domain;
- port and protocol;
- dynamic IP address considerations;
- authentication servers/domains;
- users/groups;
- policies/resources;
- tunnel traffic mode;
- Routed vs Bridged VPN traffic;
- name resolution/DNS;
- client address pool;
- client download/profile distribution;
- reconnection/data channel/tunnel security/DNS advanced settings.

The Firebox automatically creates a default `SSLVPN-Users` group in standard workflows, but firewall policies still determine access to resources.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/mvpn/ssl/configure_fb_for_mpvpn_ssl_c_before.html
- https://www.watchguard.com/help/docs/help-center/en-US/content/en-US/Fireware/mvpn/ssl/configure_fb_for_mvpn_ssl_c_wizard.html
- https://www.watchguard.com/help/docs/help-center/en-US/content/en-us/Fireware/authentication/connections_mvpn_ssl_c.html

Acceptance chain:

`TLS/OpenVPN tunnel established`

`!= user/MFA/SAML authenticated`

`!= virtual IP/routes/DNS correct`

`!= firewall policy authorizes destination`

`!= application data path healthy`.

## Port / protocol / transport

WatchGuard Mobile VPN with SSL uses TLS to secure the connection. Default service/download connection is commonly TCP 443 unless a custom port is configured.

Current WatchGuard configuration also exposes protocol/data-channel/tunnel-security controls, and OpenVPN profile behavior can include standards OpenVPN transports/settings.

Exact current Fireware-generated `client.ovpn`, cipher, data-channel, TLS version, UDP/TCP, and tunnel-security defaults are later exact-version evidence.

Current minimum compatibility requirement from WatchGuard client docs is TLS 1.2 or higher.

## Authentication / MFA / SAML

Current Mobile VPN with SSL supports multiple auth architectures through the Firebox, including Firebox/local, RADIUS, AuthPoint, and current SAML deployments depending on Fireware/client path.

### RADIUS / PAP

Current WatchGuard RADIUS documentation identifies Mobile VPN with SSL authentication as using PAP between the Firebox and the RADIUS server.

### AuthPoint MFA

Current Fireware 12.7+ can integrate Mobile VPN with SSL directly with AuthPoint. OTP and push behaviors are supported in documented configurations.

WatchGuard's AuthPoint integration specifically says Auto reconnect after connection loss should not be selected because it does not work with MFA in that flow.

### SAML

Fireware 12.11+ supports SAML integration for the WatchGuard Mobile VPN with SSL client:

- Windows first-party client: v12.11+;
- macOS first-party client: v12.11.2+.

WatchGuard explicitly states **third-party OpenVPN clients are not supported for this SAML integration**.

This is a critical product-capability boundary:

- standard OpenVPN tunnel/profile support can use PVNetwork's OpenVPN Adapter;
- vendor SAML browser/session integration requires separate exact WatchGuard/client capability evidence and may remain first-party-client-only.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-us/Fireware/authentication/radius_server_auth_about_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/Integration-Guides/AuthPoint/firebox-ssl-vpn-radius_authpoint.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/Integration-Guides/General/azure-saml_ssl-vpn.html

## Client profile/configuration formats

### `client.ovpn`

Current Fireware 12.11+ Web UI path:

`VPN > Mobile VPN > SSL > Download Profile`

The downloaded file is `client.ovpn`.

Before 12.11, users could also download it from the Firebox SSLVPN web page.

This file is the standards/OpenVPN interoperability artifact and can be imported into OpenVPN Connect.

### `sslvpn-client.wgssl`

WatchGuard first-party client configuration file used for manual distribution/update. Current manual distribution docs instruct users to double-click `sslvpn-client.wgssl` to configure the WatchGuard Mobile VPN with SSL application.

This is a WatchGuard product configuration artifact and is not treated as generic OpenVPN canonical storage.

PVNetwork rule:

- retain original source profile;
- normalize OpenVPN-capable fields into the product-owned OpenVPN profile model;
- keep WatchGuard proprietary/SAML/provisioning metadata separate;
- never silently drop security/auth/routing directives.

## Current first-party client UI map

Current WatchGuard Windows/macOS client tray/menu controls include:

- Connect / Disconnect;
- Status;
- View Logs;
- Properties;
- About;
- Exit/Quit;
- macOS Show Time Connected;
- macOS Show Status While Connecting.

Windows Properties can control Launch program on startup and Log level. macOS Properties exposes connection details/log level.

Connection UI requires server, username, password/domain as configured; MFA can add OTP/push/challenge steps.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/WG-Cloud/Devices/managed/mvpn_client_ssl.html

PVNetwork can learn state/information architecture but must not copy WatchGuard branding/assets/trade dress.

## Platform matrix

### Windows

First-party WatchGuard Mobile VPN with SSL client is current. Installation/update requires administrator privileges for upgrades.

### macOS

First-party client is current. Installation requires administrator privileges. Current WatchGuard docs warn macOS 13+ rejects SSL connections to untrusted self-signed certificates and can have client/system-extension compatibility issues if versions do not match.

### Android / iOS

WatchGuard officially documents OpenVPN Connect using `client.ovpn` as a supported client path.

### Other OS / Linux

OpenVPN-capable clients may use `client.ovpn` subject to actual profile/backend compatibility; exact OS support is not inferred unless current WatchGuard Fireware release notes/client guidance support the combination.

## Installation / update / uninstall lifecycle

First-party client distribution currently includes:

- Windows `WG-MVPN-SSL.exe`;
- macOS `WG-MVPN-SSL.dmg`;
- manual distribution of client/config;
- platform administrator privilege requirements;
- silent installation options;
- client configuration update/download behavior;
- version compatibility tied to Fireware release notes.

Current lifecycle rules:

- Fireware 12.11+ no longer serves the client application download page from the Firebox;
- client profiles can still be downloaded from Fireware admin UI;
- major client/Fireware version mismatch can prevent connection, while some minor mismatch can remain connectable;
- in 12.11+ clients no longer display the old update prompt;
- macOS client/OS extension compatibility must be tested after OS updates.

Exact installer package signatures/hashes remain later package-freeze evidence.

## OpenVPN source/license boundary

The standards/reusable core remains the existing OpenVPN family and OpenVPN3 candidate, with source/license/dependency/security already audited.

WatchGuard Fireware and first-party SSL client are proprietary:

- source tree: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- internal build/CI/tests: N/A;
- code/assets: `DO-NOT-COPY`;
- official docs/profile behavior: `REFERENCE-ONLY`.

No rights are inferred from the fact that WatchGuard publishes OpenVPN-compatible profiles.

## Routing / DNS / tunnel mode

Current WatchGuard configuration explicitly distinguishes:

- Routed VPN traffic;
- Bridged VPN traffic;
- virtual IP pool;
- full vs split Internet access behavior;
- DNS/name resolution;
- authentication groups;
- firewall resource policies.

OpenVPN client connection does not replace WatchGuard authorization/firewall policy.

PVNetwork must expose actual effective routes, DNS, virtual IP, and tunnel mode as runtime state.

## Logs / diagnostics

First-party client provides View Logs and configurable log level.

WatchGuard troubleshooting documentation also references client logs, Firebox logging, and PSInfo/support collection.

Current high-diagnostic server logging can be resource-intensive; PVNetwork should not run verbose packet/protocol logging continuously.

Diagnostic state must separate:

- profile/config fetch;
- TLS/server certificate;
- user/domain auth;
- MFA/SAML;
- OpenVPN negotiation/data channel;
- virtual IP;
- routes/DNS;
- policy authorization;
- reconnect/config update;
- app data path.

## Current compatibility / regression lessons

Convert current WatchGuard guidance into tests:

1. **SAML first-party-only** — third-party OpenVPN client must not be advertised as WatchGuard SAML-compatible.
2. **AuthPoint + reconnect** — automatic reconnect documented as incompatible with MFA in a current AuthPoint flow.
3. **macOS self-signed cert** — macOS Ventura 13+ rejects untrusted self-signed server SSL; server trust/cert provisioning must be explicit.
4. **macOS extension/client mismatch** — OS/client version mismatch can cause install failure, disconnects, or blocked extensions.
5. **major version mismatch** — current WatchGuard docs say a major client update can be required to connect.
6. **configuration fallback** — first-party client can ask to connect using the most recent configuration if new config download fails; stale-config behavior must be visible rather than silent.
7. **route/address pool exhaustion** — server pool/policy failures can yield tunnel/client connection failures distinct from credentials.
8. **TLS minimum** — current client requirement is TLS 1.2+; do not enable older TLS merely for compatibility.

## Security / privacy implications

- validate TLS/server certificate and avoid trust bypass;
- TLS 1.2 minimum remains current WatchGuard product floor;
- profile `.ovpn` can contain sensitive certificates/metadata;
- WatchGuard `.wgssl` config is vendor input, not product database;
- password/domain/RADIUS/AuthPoint/OTP/SAML cookies/tokens remain distinct secret classes;
- third-party OpenVPN clients do not inherit first-party SAML capability;
- route/DNS/full/split/bridged policy is security-sensitive;
- verbose logs/support bundles require redaction/protected export;
- exact OpenVPN3 dependency/license/SBOM/advisory review is required at implementation freeze;
- exact WatchGuard package signatures/update lifecycle require release freeze.

## Reuse decision

`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST FOR STANDARD PROFILE+WIRE SEMANTICS / WATCHGUARD FIRST-PARTY SAML+VENDOR FEATURES SEPARATE`

Preferred architecture:

`WatchGuard client.ovpn`

`-> canonical OpenVPN profile + capability/security validation`

`-> PVNetwork OpenVPN Adapter`

`-> OpenVPN3/approved backend`

`-> exact Firebox profile/auth certification`

For WatchGuard SAML or other proprietary first-party-only flows, do not fake compatibility. Either implement a separately researched supported authentication integration or retain official-client-only status.

## Residual after v1

Later v2/implementation/certification must resolve:

- exact current Fireware/client version compatibility matrix;
- current generated `client.ovpn` variants and exact OpenVPN3 directive compatibility;
- exact TLS/data-channel/cipher/protocol defaults;
- `.wgssl` configuration semantics if import is required;
- first-party SAML/browser/session behavior;
- AuthPoint/RADIUS/MFA permutations;
- Windows/macOS installer hashes/signatures/extensions;
- Android/iOS OpenVPN Connect profile behavior;
- routes/DNS/routed/bridged/full/split/firewall policy;
- server/client full menus/topologies;
- packet/interoperability/reconnect/stale-config tests;
- performance/leak/security/advisory tests.
