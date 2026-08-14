# 028 — Sophos SSL VPN — Current v1 Audit

Review date: 2026-08-14

Purpose: original `COMPLETE-RESEARCH-v1` evidence for Sophos Firewall remote-access SSL VPN. This is not implementation or Sophos interoperability certification.

## Current official baseline

Current Sophos Firewall 22.0/21.5 documentation continues to support remote-access **SSL VPN** and explicitly allows Sophos Connect and OpenVPN-family clients.

Current Sophos Connect release baseline reviewed:

- **Sophos Connect 2.5 MR1** — released 2026-06-18 for current Windows line;
- **Sophos Connect 2.0 MR1 for macOS** — released 2026-05-21;
- Sophos Connect 2.0 for macOS, released 2026-04-09, introduced Sophos Connect SSL VPN support on macOS and documents bundled third-party components including **OpenVPN 2.6.12** and OpenSSL 3.3.6;
- current SFOS 22.0 MR2 release notes list support for Sophos Connect Windows 2.5 MR1 and macOS 2.0 MR1 or earlier supported versions.

Official references:

- https://docs.sophos.com/releasenotes/output/en-us/nsg/connect_rn.html
- https://docs.sophos.com/releasenotes/output/en-us/nsg/sf_220_rn.html

## Core protocol identity

Sophos Firewall SSL VPN is **OpenVPN-compatible** at the client/profile layer.

Current Sophos documentation says:

- SSL VPN configuration downloads are `.ovpn` files;
- the same `.ovpn` can be used by Sophos Connect and other SSL VPN/OpenVPN clients;
- OpenVPN Connect is the recommended/available path for Linux/mobile and some platform/version combinations;
- Sophos Connect troubleshooting explicitly refers to its **OpenVPN service**, `openvpn.log`, and a temporary file carrying connection attributes to that service.

Official references:

- https://docs.sophos.com/nsg/sophos-firewall/21.5/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/SophosConnect/RAVPNSConClientsConfigurations/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/help/en-us/webhelp/onlinehelp/VPNAndUserPortalHelp/VPN/RemoteAccessVPN/SSLVPNRemoteAccess/index.html
- https://docs.sophos.com/nsg/sophos-connect/help/en-us/Troubleshooting/General/index.html

Research classification:

`OPENVPN-COMPATIBILITY TARGET / DO NOT CREATE A NEW SOPHOS SSL ENGINE`

## PVNetwork engine direction

Reuse the existing PVNetwork OpenVPN research and adapter architecture.

Primary reusable core candidate remains OpenVPN 3 Core through the already-audited OpenVPN family, with exact source/license/platform review at implementation freeze.

Existing PVNetwork source evidence includes:

- `research/upstreams/openvpn-family/OPENVPN3_CORE.md`
- `research/upstreams/openvpn-family/SOURCE_REVISIONS.md`
- `research/upstreams/openvpn-family/DEPENDENCIES_TESTS_SECURITY.md`
- `research/upstreams/openvpn-family/SUPPORT_REUSE_DECISIONS.md`

Important limitation:

**`.ovpn` compatibility is necessary but not sufficient.** Sophos-generated profiles can contain version-specific directives, gateway certificates, authentication expectations, SSO/provisioning metadata or policy behavior that must be validated against the selected PVNetwork OpenVPN core/platform.

Never silently drop unsupported OpenVPN/Sophos directives.

## Current client/platform matrix

Current Sophos documentation has evolved during 2026; use the current release/platform pages, not older cached assumptions.

### Windows

Current Sophos Connect 2.5 MR1 line supports SSL VPN. Sophos Connect 2.5+ supports 64-bit Windows 10/11 and Windows ARM as documented in current release notes.

SSL VPN can use:

- `.ovpn` direct configuration;
- `.pro` provisioning on supported Sophos Connect versions;
- Microsoft Entra ID SSO with current supported SFOS/Sophos Connect versions where configured.

### macOS

Sophos Connect 2.0 introduced SSL VPN on macOS in April 2026. Sophos Connect 2.0 MR1 is the current reviewed macOS maintenance line as of this audit.

Current docs support `.ovpn` for SSL VPN. `.pro` provisioning remains a Windows-specific path in current Sophos documentation.

### Linux

Sophos Connect is not the SSL VPN client path. Sophos documents OpenVPN clients for Linux with downloadable `.ovpn` configuration.

### iOS / Android

Sophos Connect does not support mobile SSL VPN. Sophos documentation directs mobile users to OpenVPN-compatible clients using `.ovpn`.

Consequence: PVNetwork's own OpenVPN platform adapters are more important than copying Sophos Connect UI.

## Server/admin model

Sophos Firewall owns the remote-access SSL VPN policy and surrounding authorization/networking.

Current admin documentation separates:

- global SSL VPN settings;
- remote-access SSL VPN policy;
- users/groups permitted by the policy;
- permitted network resources;
- full vs split tunnel behavior;
- firewall rules;
- VPN portal availability/configuration;
- authentication servers and MFA;
- generated/downloadable client configuration.

Current remote-access docs expose an SSL VPN Assistant to simplify creation of global settings/policy/portal/firewall rule in supported versions.

References:

- https://docs.sophos.com/nsg/sophos-firewall/21.5/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/index.html
- https://docs.sophos.com/nsg/sophos-firewall/21.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/HowToArticles/RAVPNSSLFullTunnel/index.html

PVNetwork must not treat successful TLS/OpenVPN negotiation as proof that the user is authorized to all routed resources.

## Profile / provisioning model

### `.ovpn`

The portable SSL VPN configuration artifact. Users can download it from the VPN portal when an administrator assigns a remote-access SSL VPN policy.

The `.ovpn` may be imported into Sophos Connect and OpenVPN-family clients.

### `.pro`

Sophos provisioning file. Current docs recommend it on supported Windows Sophos Connect versions because it can automatically fetch/import the current VPN configuration and later policy changes.

Important differences:

- `.ovpn` is a snapshot; users may need to download/re-import after administrator changes;
- `.pro` can update/fetch policy automatically;
- Sophos Connect menu items such as Auto-connect / Update policy depend on provisioning-file configuration and admin policy;
- `.pro` is not a generic OpenVPN format and must not become PVNetwork canonical profile storage.

Reference:

- https://docs.sophos.com/nsg/sophos-firewall/21.5/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/SophosConnect/RAVPNSConClientsConfigurations/index.html
- https://docs.sophos.com/nsg/sophos-firewall/21.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/HowToArticles/RAVPNSSLFullTunnel/index.html

## Current Sophos Connect UI map

Current user-guide information architecture includes:

### Connections

- Connections page;
- Import connection;
- multiple imported connections;
- select/double-click connection;
- Connect;
- sign-in surface;
- successful/failed tray state;
- connection settings/options.

### Connection options

Current documented actions:

- Auto-connect, if administrator enables it;
- Delete;
- Rename;
- Clear credentials;
- Update policy, when provisioning supports it.

### Authentication

- username/password;
- MFA verification code when supported/required;
- Microsoft Entra ID SSO on supported Windows versions/current client;
- Force SSO re-login for shared endpoints.

### Events / logs / support

- Events tab with timestamp/action/result;
- menu -> Open VPN log;
- About -> Generate technical support report;
- `scvpntsr.zip` diagnostic bundle.

References:

- https://docs.sophos.com/nsg/sophos-connect/help/en-us/UserGuide/Connections/index.html
- https://docs.sophos.com/nsg/sophos-connect/help/en-us/UserGuide/Connections/ConnectionOptions/index.html
- https://docs.sophos.com/nsg/sophos-connect/help/en-us/UserGuide/Connections/Connect/index.html
- https://docs.sophos.com/nsg/sophos-connect/help/en-us/UserGuide/Events/
- https://docs.sophos.com/nsg/sophos-connect/help/en-us/Troubleshooting/SupportReport/

Behavioral lessons may be independently implemented; Sophos UI/branding/assets are proprietary and not copied.

## Authentication / MFA / SSO

Current Sophos Firewall supports MFA enforcement for SSL VPN remote access.

Important current constraint:

- Sophos Connect does not support arbitrary OTP challenge flow in the same way as an interactive challenge protocol; documentation says it sends password+OTP in `passwordotp` format for TOTP-style authentication;
- Call/Push MFA is supported in documented configurations;
- Microsoft Entra ID SSO is available in supported Sophos Connect/SFOS combinations, with current Windows client support beginning in 2.4;
- current Sophos Connect release notes include fixes for SSO/SSL provisioning/certificate-special-character behavior.

References:

- https://docs.sophos.com/nsg/sophos-firewall/21.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/Authentication/OneTimePassword/AuthenticationMFASettings/index.html
- https://docs.sophos.com/nsg/sophos-connect/help/en-us/UserGuide/Connections/Connect/index.html
- https://docs.sophos.com/releasenotes/output/en-us/nsg/connect_rn.html

PVNetwork must model `credentials`, `TOTP concatenation`, `push/call MFA`, `browser/Entra SSO` and TLS/client-cert state as separate auth capabilities rather than one password field.

## Persistence / secrets

The research separates:

1. imported `.ovpn` source;
2. Sophos `.pro` provisioning source when supported;
3. normalized PVNetwork canonical profile;
4. reusable username/password references;
5. OTP/TOTP transient data;
6. SSO/browser tokens/session state;
7. certificates/private-key references;
8. transient OpenVPN session keys/state;
9. route/DNS/runtime state;
10. protected diagnostics.

Current Sophos Connect has a Clear credentials action, and release notes document changes/fixes around credential persistence on macOS SSL VPN.

PVNetwork must use secure platform stores, honor admin/profile policy and never place passwords/private keys in ordinary unprotected profile records simply because OpenVPN supports inline/auth-file forms.

## Diagnostics / logs

Sophos Connect current troubleshooting material exposes concrete files:

- `openvpn.log` — OpenVPN connection, virtual interface, packet-flow, errors;
- `charon.log` — strongSwan/IKE/IPsec path, relevant to the separate IPsec side of Sophos Connect;
- `scvpn.log` — VPN events/sign-in/connect/disconnect;
- `scgui.log` — client/OpenVPN/WebView2 versions and SSO logging;
- `configs.txt` — imported `.pro`/`.ovpn`/`.scx` configuration summary without PII according to Sophos;
- route/IP/system info files in technical report.

Current log rotation keeps current + previous session variants for many logs. Technical support report is a ZIP.

Reference:

- https://docs.sophos.com/nsg/sophos-connect/help/en-us/Troubleshooting/TroubleshootingFiles/index.html

PVNetwork diagnostic bundles must have independent redaction/privacy review; do not copy Sophos report formats blindly.

## Important current failures converted to tests

Current Sophos documentation/release notes/known issues provide high-value test cases:

1. SSL configuration/policy changed -> old `.ovpn` may need re-download/import; provisioning can pull update.
2. OpenVPN service unavailable -> SSL VPN cannot connect.
3. temporary attribute-file creation failure -> SSL VPN-specific connection failure.
4. macOS DNS settings regression was fixed in Sophos Connect 2.0 MR1 -> internal FQDN/DNS acceptance is mandatory.
5. SSO provisioning with special characters in certificates had a 2.5 MR1 fix -> parser/certificate/SSO regression test.
6. reconnect OTP prompt behavior differed between IPsec and SSL before 2.5 MR1 -> auth-state/reconnect regression.
7. known issue: non-ASCII usernames (for example German umlauts) may not authenticate in current Sophos Connect -> Unicode/localization support cannot be inferred from display localization.
8. older BF-CBC configurations are not supported by newer Sophos Connect releases -> never silently enable obsolete crypto just to keep a legacy profile connecting.

References:

- https://docs.sophos.com/releasenotes/output/en-us/nsg/connect_rn.html
- https://docs.sophos.com/support/kil/index.html?product=connect
- https://docs.sophos.com/nsg/sophos-connect/help/en-us/Troubleshooting/General/index.html

## Install / update / coexistence

Current Sophos documentation provides Windows MSI and macOS package installation. Upgrade workflow is currently uninstall old version then install latest version.

Sophos explicitly warns that an existing SSL VPN Client must be uninstalled because Sophos Connect cannot operate with another VPN client installed in that configuration.

Reference:

- https://docs.sophos.com/nsg/sophos-connect/help/en-us/UserGuide/InstallScon/index.html

PVNetwork consequence: OpenVPN/Sophos Connect service/driver coexistence must be a real installation test; protocol compatibility does not prove safe client coexistence.

## Source / license / build boundary

Sophos Connect full application source/build/test internals are proprietary. Do not fabricate source tree or private CI.

For source-specific original gates:

- Sophos Connect source tree: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- Sophos Firewall server source: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- application code/assets: `DO-NOT-COPY`;
- official documentation/release notes: `REFERENCE-ONLY`.

The selected reusable protocol source remains the existing OpenVPN family, especially OpenVPN 3 Core, with its exact dual-license/dependency/platform review.

Sophos Connect release notes listing OpenVPN/OpenSSL/strongSwan components are behavioral/supply-chain clues, not rights to reuse Sophos client code.

## Tests / CI boundary

Sophos private source CI is unavailable/N-A. Product release notes and known-issue lists are behavioral QA evidence only.

OpenVPN3/OpenVPN family source tests/security/dependencies are already captured in the shared PVNetwork OpenVPN dossier.

Future Sophos SSL certification test pyramid:

- `.ovpn` parser/normalization;
- unsupported-directive/security-directive preservation;
- OpenVPN3 adapter tests;
- server TLS/cert/auth tests;
- password/TOTP/push/SSO flows;
- `.pro` provisioning parser/fetch/update only if product chooses to support it;
- Sophos Firewall exact-version lab;
- split/full route/DNS/firewall authorization;
- reconnect/network-change;
- Windows/macOS/Linux/mobile platform tests as applicable;
- profile-change reimport/update;
- installer/service/coexistence;
- diagnostic redaction;
- performance/leak/security tests.

## Security / privacy consequences

- server certificate trust must not be bypassed silently;
- obsolete OpenVPN ciphers/directives are not auto-enabled for compatibility;
- password, OTP, SSO tokens, private keys and provisioning secrets are separate classes;
- `.ovpn` may contain sensitive inline materials and must be handled accordingly;
- `.pro` is an active policy-fetch/provisioning mechanism and requires authenticated server/trust validation;
- SSO shared-endpoint sessions require explicit re-login/clear behavior;
- diagnostics can contain network/config/log data and require redaction/protected export;
- full-vs-split routing and DNS are security properties;
- policy updates must not silently broaden network access.

## PVNetwork reuse decision

`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST WHEN PROFILE+AUTH CAPABILITIES MATCH / SOPHOS PROVISIONING AND SSO ARE SEPARATE CAPABILITIES`

Do not create a dedicated Sophos SSL cryptographic/tunnel engine.

Preferred architecture:

`Sophos .ovpn import`

`-> PVNetwork canonical OpenVPN profile + capability validation`

`-> product-owned OpenVPN Adapter`

`-> OpenVPN3 Core / approved platform backend`

`-> exact Sophos Firewall SSL VPN lab certification`

Optional `.pro` provisioning support, if product requirements justify it, belongs in a separate vendor-provisioning adapter/service and must not be mixed into the OpenVPN wire engine.

## Residual after v1

Later v2/implementation/certification must resolve:

- exact SFOS/UTM versions and SSL VPN server implementation/deployment matrix;
- exact generated `.ovpn` variants/directives by gateway version/policy;
- exact crypto/cipher/TLS/data-channel/security floor;
- current OpenVPN3 compatibility with Sophos-generated profiles;
- `.pro` provisioning protocol/schema/trust/update behavior if retained;
- Entra SSO and MFA permutations;
- Windows/macOS/Linux/mobile exact platform behavior;
- route/DNS/full-vs-split/firewall authorization;
- server/client menus and topology evidence;
- installer hashes/signatures/update lifecycle;
- real packet/interoperability and leak/reconnect tests.
