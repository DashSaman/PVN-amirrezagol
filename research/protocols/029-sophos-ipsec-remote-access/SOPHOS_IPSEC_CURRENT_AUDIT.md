# 029 — Sophos IPsec Remote Access — Current v1 Audit

Review date: 2026-08-14

Purpose: original `COMPLETE-RESEARCH-v1` evidence for current Sophos Firewall remote-access IPsec. This is not implementation, interoperability certification, or production support.

## Scope boundary: modern IPsec is not `IPsec (legacy)`

Current Sophos Firewall has two historically adjacent concepts that must not be conflated.

### Current remote access IPsec

Current SFOS 21.5/22.0 documentation supports **Remote access VPN > IPsec** for Sophos Connect and selected third-party clients.

The current remote-access mode uses an IPsec profile to define Phase 1 and Phase 2 and, critically, current Sophos documentation states:

- only **IKEv1** IPsec profiles can be selected for remote access;
- Dead Peer Detection must be Off or Disconnect for the selected profile;
- authentication can use a preshared key or RSA digital certificates;
- allowed users/groups and client network information are separate from tunnel cryptographic authentication;
- connection export produces `.scx` and `.tgb` artifacts.

Official references:

- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/IPsec/RAVPNIPsecSettings/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/IPsec/index.html

### Retired `IPsec (legacy)`

This is a separate historical remote-access feature.

Sophos states that **IPsec (legacy) is retired and isn't supported in SFOS 22.0 MR1 and later**. A firewall containing the legacy remote-access IPsec configuration cannot upgrade to 22.0 MR1+ until that legacy configuration is removed; restored/imported legacy configuration is not migrated.

Official reference:

- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/RAVPNIPsecLegacy/index.html

Important research rule:

`modern Sophos remote-access IPsec currently uses IKEv1` does **not** mean it is the same feature as `IPsec (legacy)`.

The legacy retirement is a product/configuration migration boundary, not proof that IKEv1 has disappeared from the current Sophos Connect remote-access implementation.

## Current first-party client baseline

Sophos Connect is the current first-party client for remote-access IPsec.

Current reviewed release evidence from Sophos release notes:

- Sophos Connect **2.5 MR1** for Windows, released 2026-06-18;
- Sophos Connect **2.0 MR1** for macOS, released 2026-05-21;
- current macOS Sophos Connect release notes list strongSwan among bundled third-party components;
- current SFOS 22.0 release notes identify the current supported Sophos Connect release lines.

Official release notes:

- https://docs.sophos.com/releasenotes/output/en-us/nsg/connect_rn.html
- https://docs.sophos.com/releasenotes/output/en-us/nsg/sf_220_rn.html

Sophos Connect source code, private build system, private CI, branding, and application assets remain proprietary/reference-only.

## Platform matrix

Current Sophos Firewall user documentation identifies Sophos Connect remote-access IPsec support on:

- Windows 10 and 11;
- macOS 13 and later.

Current docs say Sophos Connect is not the IPsec client path for Linux/mobile endpoints; third-party VPN clients may be used for those endpoints where their capabilities match the exported Sophos configuration.

Official reference:

- https://docs.sophos.com/nsg/sophos-firewall/21.5/Help/en-us/webhelp/onlinehelp/VPNAndUserPortalHelp/VPN/RemoteAccessVPN/IPsecVPNRemoteAccess/index.html

Source portability of strongSwan does not itself certify a third-party client for a Sophos remote-access policy.

## Server/admin model

Current SFOS 22.0 admin flow is:

`Remote access VPN > IPsec`

The current settings surface includes:

### General

- Enable remote access IPsec;
- WAN Interface;
- IPsec Profile;
- Authentication type;
- Preshared key OR digital certificates;
- Local certificate / Remote certificate;
- Local ID;
- Remote ID;
- Allowed users and groups.

Current certificate rules include RSA certificate support; current documentation explicitly says ECDSA certificates aren't supported for this IPsec VPN mode.

### Client information

- connection name;
- client address range (`Assign IP from`);
- DNS server fields;
- other client/network settings.

### Advanced settings

Current example/admin documentation includes:

- permitted network resources;
- Security Heartbeat through the tunnel;
- allow users to save username/password;
- default-gateway/full-tunnel behavior and related routing/firewall policy;
- other version-specific advanced controls.

### Separate authorization and firewall policy

Allowed groups/users determine who can authenticate for IPsec remote access. Sophos documents group-level remote access IPsec enablement separately under Authentication > Groups, with user/group precedence rules.

Firewall rules separately determine what VPN users can reach after the tunnel is established.

Current example documentation explicitly requires VPN-to-LAN/DMZ rules and, for full tunnel, WAN destination access as appropriate.

Official references:

- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/IPsec/RAVPNIPsecSettings/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/index.html?contextId=remote-access-VPN-sophos-connect-client-configuration
- https://docs.sophos.com/nsg/sophos-firewall/22.0/help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/IPsec/RAVPNSConAuth/index.html

Acceptance consequence:

`IKE/IPsec established` is not equivalent to `user authorized`, `client address/routes installed`, or `application traffic allowed by firewall rules`.

## Exported configuration formats

### `.scx`

Sophos's current recommended first-party configuration format for remote access IPsec.

Current documentation says:

- `.scx` is for Sophos Connect;
- it includes general **and advanced** settings;
- when advanced settings change, the updated `.scx` must be re-shared/reimported unless provisioning is used;
- current Sophos Connect can import it directly.

### `.tgb`

Third-party-compatible/historical configuration artifact.

Current documentation says:

- it contains only general settings;
- it can be used with third-party clients;
- it does not carry Sophos Connect advanced settings.

Do not confuse current `.tgb` export with the retired **IPsec (legacy)** product mode. The file extension persists as a reduced-configuration interoperability artifact in current remote-access IPsec documentation.

### `.pro`

Sophos Connect provisioning file.

Current documentation recommends provisioning where appropriate because it can automatically fetch/import updated `.scx` IPsec configuration and later changes. Current docs say IPsec provisioning requires Sophos Connect 2.1 or later.

Official references:

- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/SophosConnect/RAVPNSConClientsConfigurations/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/SophosConnect/RAVPNSConProvisioningFile/index.html

PVNetwork rule:

- `.scx`, `.tgb`, and `.pro` are vendor interoperability/provisioning inputs, not the authoritative product database;
- parse supported fields into a product-owned typed canonical profile;
- preserve unsupported/vendor-specific source fields;
- never silently discard security, identity, routing, or certificate directives.

## Authentication / MFA / SSO

Current Sophos remote-access IPsec separates several layers:

1. IKE authentication — PSK or RSA digital certificate;
2. VPN user/group authentication/authorization;
3. local/AD/Microsoft Entra ID identity backend;
4. MFA/OTP policy where configured;
5. Microsoft Entra ID browser SSO for supported current Sophos Connect/SFOS combinations;
6. profile/provisioning trust and server identity.

Current Sophos documentation says remote-access IPsec group authentication supports local, AD, and Microsoft Entra ID users/groups.

Current Sophos Connect documentation for MFA/SSO is shared with its SSL VPN client product surface. Treat TOTP/password concatenation, Call/Push MFA, browser SSO, and normal credentials as separate auth capabilities.

Official references:

- https://docs.sophos.com/nsg/sophos-firewall/22.0/help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/IPsec/RAVPNSConAuth/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/index.html

## StrongSwan evidence / engine boundary

Sophos Connect's current troubleshooting bundle identifies `charon.log` as containing:

- **strongSwan operations**;
- security/configuration changes;
- IKE;
- ESP;
- packet-flow information.

Official reference:

- https://docs.sophos.com/nsg/sophos-connect/help/en-us/Troubleshooting/TroubleshootingFiles/index.html

This is strong evidence that Sophos Connect's IPsec path is strongSwan-based at the product level.

PVNetwork's own maintained research baseline is independently newer/current:

- repository: `strongswan/strongswan`
- reviewed release: 6.0.7
- exact commit: `5973ff8e41deef4e015e1138a2de688acedf6f75`
- shared evidence: `research/upstreams/strongswan-family/`

Important boundary:

- Sophos's bundled strongSwan version is a supply-chain/architecture clue for Sophos Connect;
- PVNetwork does not copy Sophos Connect binaries/config internals;
- PVNetwork evaluates its own pinned strongSwan/native backend through a product-owned IPsec Adapter;
- Sophos-specific `.scx`/`.pro`, SSO, group auth, heartbeat, and policy semantics remain outside the generic cryptographic engine.

## IKE / data-plane model

Current Sophos remote-access IPsec admin docs explicitly require **IKEv1** profiles.

At research level, the standard path is:

`profile / provisioning`

`-> gateway identity / PSK or RSA certificate`

`-> IKEv1 Phase 1`

`-> user/auth policy as configured`

`-> IKEv1 Phase 2 / IPsec SA`

`-> ESP / NAT-T where applicable`

`-> client virtual IP / DNS / routes`

`-> firewall authorization`

`-> application traffic`

Reuse the shared IKEv1/IPsec/ESP/NAT-T model where standard semantics match.

Exact Sophos-specific configuration exchanges, XAUTH/EAP/user-auth mechanism details, vendor payloads, proposal set, and profile translation are later v2/interoperability evidence. Do not infer them solely from generic strongSwan source.

## Sophos Connect UI / user flow

Sophos Connect is a shared client for both IPsec and SSL VPN; current user-guide UI evidence from entry 028 applies at the product shell level:

- Connections page;
- Import connection;
- select profile;
- Connect/Disconnect;
- sign-in/authentication;
- per-connection settings;
- Auto-connect where provisioned;
- Delete/Rename;
- Clear credentials;
- Update policy where provisioning supports it;
- Events;
- VPN log;
- Generate technical support report;
- Force SSO re-login where supported.

For IPsec, imported `.scx`/`.pro` entries and `charon.log`/IKE status are the protocol-specific domains.

PVNetwork may learn state/navigation principles; Sophos visual assets/trade dress remain proprietary/reference-only.

## Persistence / secret model

Separate:

- `.scx` source/profile metadata;
- `.tgb` third-party source metadata;
- `.pro` provisioning metadata;
- PSK reference;
- certificate/private-key reference;
- reusable username/password according to policy;
- transient OTP;
- browser/Entra SSO tokens/session;
- strongSwan/IKE/IPsec runtime SA keys;
- assigned virtual IP/DNS/routes;
- diagnostics.

Never place PSKs/private keys/passwords/SSO tokens in ordinary unprotected product storage. Honor current Sophos advanced policy that may allow or disallow saved username/password.

## Diagnostics / support bundle

Current Sophos Connect technical support data separates protocol engines:

- `charon.log` — strongSwan/IKE/ESP/IPsec;
- `openvpn.log` — SSL/OpenVPN;
- `scvpn.log` — shared VPN lifecycle;
- `scgui.log` — client/UI/SSO detail;
- `configs.txt` — imported `.pro`, `.ovpn`, `.scx` summary;
- route/IP/system information.

This allows precise future error ownership:

- profile/provisioning;
- IKE identity/proposal;
- user/MFA/SSO;
- ESP/data SA;
- virtual IP/DNS/routes;
- firewall authorization;
- data path.

Diagnostics require PVNetwork-owned redaction/privacy policy.

## Installation / update / lifecycle

Current Sophos Connect client packaging/lifecycle is shared with entry 028:

- current Windows/macOS package lines;
- enterprise Windows deployment/GPO support;
- provisioning through GPO;
- install/update/uninstall lifecycle;
- possible VPN client coexistence concerns;
- exact installer hash/signature remains a release-freeze gate.

Current source/product support does not imply Linux/mobile Sophos Connect support; third-party IPsec clients on those platforms require independent exact configuration/capability validation.

## Issues / regressions converted to tests

High-value current behavior includes:

1. **Legacy configuration upgrade block** — `IPsec (legacy)` config must be removed before SFOS 22.0 MR1+ upgrade. Migration validation must distinguish retired legacy objects from current remote-access IPsec.
2. **Policy/config update** — changes to advanced settings require new `.scx` unless provisioning automatically refreshes it.
3. **Group-auth enablement** — imported/migrated AD groups may have remote access IPsec disabled; group/user policy precedence can disconnect or block clients.
4. **Full tunnel** — `Use as default gateway` requires correct WAN/firewall-rule policy, not just IPsec establishment.
5. **DNS/routes** — acceptance requires effective route/DNS behavior after connection.
6. **SSO provisioning timing** — Entra ID auth must be configured before exporting/downloading the VPN config for SSO to work as documented.
7. **Certificate support** — current remote-access IPsec docs require RSA certificates and explicitly reject ECDSA for this mode.
8. **Legacy cryptography** — current mode's IKEv1 requirement makes proposal/security-floor review mandatory; no weak algorithm is silently enabled merely to reproduce an old profile.

## Source / license / tests boundary

Sophos Connect and Sophos Firewall are proprietary:

- source tree: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- internal build/CI/test suite: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- client/server UI/code/assets: `DO-NOT-COPY`;
- official docs/release notes: `REFERENCE-ONLY`.

Public engine evidence:

- strongSwan 6.0.7 source/build/license/security/tests are already audited in `research/upstreams/strongswan-family/`;
- because current Sophos mode is IKEv1, entry 005's explicit legacy/vendor-compatibility security policy applies to the standards engine;
- no silent fallback/downgrade is allowed.

## Security / privacy implications

- IKEv1 is a compatibility requirement here, not a reason to weaken proposals globally;
- distinguish PSK, certificate/private key, user credentials, MFA, SSO tokens, provisioning state, and transient IPsec key material;
- server identity/local-remote IDs must be validated, not silently rewritten;
- RSA certificate limitation is exact current vendor capability, not a global PVNetwork certificate rule;
- route/DNS/full-tunnel/firewall policy are security boundaries;
- `.pro` remote provisioning is a trusted update channel and needs server/authenticity checks;
- diagnostic bundles may expose configuration/network/auth metadata and require protected export;
- exact strongSwan plugin/dependency/SBOM/advisory review is mandatory at implementation freeze;
- retired `IPsec (legacy)` must never be resurrected automatically during migration.

## Reuse decision

`CURRENT SOPHOS IPSEC COMPATIBILITY TARGET / STRONGSWAN-FIRST FOR STANDARD IKEV1-IPSEC SEMANTICS / SCX-PRO-POLICY-SSO SEPARATE / RETIRED LEGACY MODE MIGRATION-ONLY`

Preferred future architecture:

`Sophos .scx/.tgb/.pro input`

`-> vendor-specific parser/provisioning service`

`-> PVNetwork canonical IPsec profile + capability/security validation`

`-> product-owned IPsec Adapter`

`-> pinned strongSwan / approved native backend`

`-> exact Sophos Firewall remote-access IPsec certification`

Do not build a new cryptographic stack and do not claim the retired legacy mode as a supported new-deployment target.

## Residual after v1

Later v2/implementation/certification must resolve:

- exact SFOS 21.5/22.0/model/firmware matrix;
- exact IKEv1 Phase 1/2 proposals/security floor per supported firewall;
- exact user-auth mechanism/vendor payloads;
- `.scx` schema, signing/protection/field translation;
- `.tgb` third-party compatibility details;
- `.pro` provisioning protocol/trust/update behavior;
- Entra SSO/MFA/certificate/PSK permutations;
- third-party Linux/mobile client matrix;
- exact strongSwan plugin/config mapping;
- routes/DNS/full-vs-split/heartbeat/firewall authorization;
- installer hashes/signatures/update/coexistence;
- real packet captures, interop, reconnect/network-change, leak/performance/security testing;
- migration tests proving retired legacy objects are not silently recreated.
