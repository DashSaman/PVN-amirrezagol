# 030 — WatchGuard Mobile VPN with IKEv2 — Current v1 Audit

Review date: 2026-08-14

Purpose: original `COMPLETE-RESEARCH-v1` evidence for WatchGuard Firebox Mobile VPN with IKEv2. This is not implementation or WatchGuard interoperability certification.

## Product/protocol identity

WatchGuard Fireware supports **Mobile VPN with IKEv2** as a standards-based remote-access IPsec service.

Current WatchGuard documentation says:

- Firebox can host IKEv2 sessions;
- users in the configured IKEv2 authentication groups use an IKEv2 client;
- native Windows, macOS, and iOS IKEv2 clients are supported configuration paths;
- Android uses the third-party strongSwan app;
- Fireware v12.11.1+ can also generate/import a profile for the WatchGuard IPSec Mobile VPN Client for Windows;
- generic support still depends on exact Fireware/client OS compatibility.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-us/Fireware/authentication/connections_mvpn_ikev2_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ikev2/mvpn_ikev2_client_config.html

Research decision direction:

`STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST / STRONGSWAN-ANDROID+ADVANCED REFERENCE`

No custom WatchGuard-specific cryptographic engine is required.

## Scope boundary

Do not conflate entry 030 with:

- entry 031 WatchGuard Mobile VPN with SSL;
- entry 032 WatchGuard L2TP;
- older WatchGuard Mobile VPN with IPSec profile formats such as generic legacy `.WGX` where they refer to the distinct non-IKEv2 IPSec client configuration;
- BOVPN/site-to-site IKE/IPsec;
- WatchGuard management-plane authentication.

The optional WatchGuard IPSec Mobile VPN Client for Windows can import IKEv2 profiles in current Fireware, but that does not make its legacy/non-IKEv2 profile formats equivalent to the native IKEv2 client package.

## Server/admin model

Current locally-managed Fireware Web UI navigation:

`VPN > Mobile VPN > IKEv2`

The Setup Wizard and edit page cover:

- server address/domain/IP;
- Firebox/server certificate;
- full vs split tunnel networking;
- virtual IP address pool;
- authentication servers;
- users/groups and access enforcement;
- Phase 1 and Phase 2 settings;
- DNS/WINS;
- authentication timeout;
- DF bit handling;
- enable/disable state.

Policy Manager exposes equivalent IKEv2 configuration under `VPN > Mobile VPN > IKEv2`.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-US/Fireware/mvpn/ikev2/mvpn_ikev2_config_wiz.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ikev2/mvpn_ikev2_config_edit.html

## Tunnel authentication / certificate model

WatchGuard requires endpoint identity validation with a certificate for Mobile VPN with IKEv2 tunnel establishment.

Current certificate requirements include:

- server FQDN or IP in Subject Alternative Name;
- `serverAuth` EKU;
- non-expired trust chain;
- Firebox-generated or supported third-party certificate choices according to management mode;
- EC/ECDSA support from Fireware 12.5+ subject to client OS capability.

Client EC support differs by OS. WatchGuard current docs specifically note Windows partial ECDSA support, Android strongSwan support, and lack of EC support in macOS/iOS for this WatchGuard client-interoperability context.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/certificates/authentication_mvpn_ikev2.html
- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-US/WG-Cloud/Devices/managed/mvpn_certs.html

PVNetwork consequence: certificate capability is exact backend/OS evidence, not one global Boolean.

## User authentication / MFA

Current WatchGuard Mobile VPN with IKEv2 user authentication supports:

- Firebox-DB/local users;
- RADIUS;
- AuthPoint in supported current Fireware integrations;
- Active Directory-backed flows through RADIUS/compatible auth architecture;
- other exact authentication-server behavior according to Fireware management mode/documentation.

WatchGuard states IKEv2 user authentication uses **EAP and MS-CHAPv2**.

MFA support requires solutions compatible with MS-CHAPv2. WatchGuard AuthPoint is the first-party MFA path. Current docs also preserve a notable Android/strongSwan limitation: AuthPoint push notifications can fail under full-tunnel strongSwan because the notification path is unavailable; split tunneling is needed for that push-notification behavior.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/mvpn/ikev2/mvpn_ikev2_user_auth.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/general/mobile_vpn_types_c.html

This becomes a future acceptance test: `VPN full tunnel` and `MFA push reachability` are interacting capabilities, not independent checkboxes.

## Standard IKEv2/IPsec engine reuse

PVNetwork already has a completed standards dossier for IKEv2/IPsec and current strongSwan evidence.

Current public engine baseline:

- `strongswan/strongswan`
- release 6.0.7
- exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75`
- shared evidence under `research/upstreams/strongswan-family/`

For Windows/macOS/iOS, native platform IKEv2 APIs/clients are the preferred first research path because WatchGuard itself publishes native-client configuration instructions.

For Android, strongSwan is an explicit WatchGuard-documented client path.

Generic IKEv2 interoperability is still not blanket WatchGuard certification: certificate profile, EAP/MS-CHAPv2, proposals, address/DNS/routes, AuthPoint and exact Fireware policy all require version/platform validation.

## Client configuration/profile package

Current Fireware can generate a compressed **`.TGZ` client profile/instructions package** from the IKEv2 Client Profile page.

The extracted package contains per-platform folders with some combination of:

- README/instructions;
- scripts;
- certificates;
- platform profiles/configuration.

Current examples:

### Windows native

WatchGuard can provide an automatic Windows configuration script. Manual IKEv2 configuration is also supported.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-us/Fireware/mvpn/ikev2/mvpn_ikev2_windows_client.html

### macOS / iOS native

WatchGuard supplies a preconfigured **`.mobileconfig`** profile, or users can configure native IKEv2 manually.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/mvpn/ikev2/mvpn_ikev2_mac_client.html

### Android

WatchGuard directs users to strongSwan and supplies Android-specific instructions/profile material in the downloaded package.

### WatchGuard IPSec Mobile VPN Client for Windows

In Fireware v12.11.1+, the generated package contains a WatchGuard client folder. Current documented import path uses an `.INI` IKEv2 profile plus a `.PEM` certificate with WatchGuard IPSec Mobile VPN Client Windows v15.19+.

Important compatibility constraint: WatchGuard documents that this client path does not support IKEv2 configurations that include AES-GCM 192-bit encryption.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-US/Fireware/mvpn/ikev2/mvpn_ikev2_mobile_vpn_client.html

## Canonical profile/storage rule

The WatchGuard `.TGZ`, `.mobileconfig`, scripts, `.INI`, and certificates are deployment inputs, not PVNetwork's authoritative cross-platform database.

PVNetwork should retain:

1. original imported/generated source where useful;
2. normalized canonical IKEv2 profile;
3. server identity/certificate requirements;
4. protected reusable user credential/certificate references;
5. platform-generated runtime configuration;
6. transient IKE/IPsec/EAP session state.

Never silently ignore unsupported proposals/certificates/auth methods during import.

## Native client UI / product UI map

Because WatchGuard intentionally relies on platform IKEv2 clients for many endpoints, entry 030 does not invent a universal WatchGuard consumer UI.

### Windows native

Use Windows VPN settings/native connection state and profile management. WatchGuard provides automatic script or manual settings instructions.

### macOS/iOS native

Use the OS VPN/profile management UI after `.mobileconfig` installation or manual profile creation.

### Android

Use strongSwan app/profile UI as the current documented third-party path.

### WatchGuard Windows IPSec Mobile VPN Client

Current IKEv2 import workflow uses:

- Mobile VPN Monitor;
- `Configuration > Profiles`;
- `Add / Import`;
- `Profile Import`;
- imported `.INI` and `.PEM` data.

This client UI is an optional proprietary WatchGuard-specific reference, not required for the native-OS-first architecture.

## Routing / address / DNS / authorization

Current WatchGuard configuration separates:

- full vs split tunnel;
- virtual IP pool;
- DNS/WINS;
- authenticated users/groups;
- policies controlling which IKEv2 VPN users may send traffic.

Fireware creates/usefully references an `IKEv2-Users` group and an `Allow IKEv2-Users` policy in standard configuration workflows, but administrators can narrow access.

Acceptance chain:

`IKEv2 established`

`!= EAP/user auth success`

`!= virtual IP assigned`

`!= routes/DNS correct`

`!= policy permits destination`

`!= application data path healthy`.

## Diagnostics / operational state

Fireware server-side visibility and platform-client diagnostics are separate.

Research requirements:

- IKE negotiation/proposal failure;
- certificate identity/trust/expiry;
- EAP/MS-CHAPv2/auth backend;
- AuthPoint/MFA;
- CHILD_SA/ESP/NAT-T;
- assigned virtual IP;
- routes/DNS;
- policy authorization;
- reconnect/network-change;
- client profile/version mismatch.

WatchGuard native client strategy means diagnostic collection must be platform-specific rather than assuming one vendor log file exists on all endpoints.

## Installation / deployment lifecycle

There is no required WatchGuard VPN application install for native Windows/macOS/iOS IKEv2 paths.

Instead lifecycle includes:

- profile/script/certificate distribution;
- OS profile installation/removal;
- certificate rotation/expiry;
- VPN profile update/replacement;
- managed-device/MDM/GPO deployment where used;
- optional strongSwan install/update on Android;
- optional WatchGuard IPSec Mobile VPN Client install/update/reboot lifecycle on Windows.

This is a key product advantage of standards/native-first support and must remain separate from 031 SSL client installation.

## Source / license / build boundary

WatchGuard Fireware and WatchGuard IPSec Mobile VPN Client are proprietary:

- private source: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- internal build/CI/tests: N/A/publicly unavailable;
- branding/assets/client code: `DO-NOT-COPY`;
- official docs/profile behavior: `REFERENCE-ONLY`.

Reusable code/platform candidates:

- native OS IKEv2 APIs/clients — platform terms/capabilities;
- strongSwan 6.0.7 — public source/license/security/test evidence already audited.

No WatchGuard-specific cryptographic stack should be recreated.

## Issues / compatibility lessons converted to tests

Current documentation provides material compatibility cases:

1. Android strongSwan full-tunnel can block AuthPoint push notification reachability; split tunnel may be required.
2. EC certificate support varies by OS even when Firebox supports EC.
3. WatchGuard Windows IPSec Mobile VPN client IKEv2 import does not support AES-GCM-192 profile configurations.
4. certificate SAN/EKU/expiry mismatch prevents server trust.
5. user/group policy is distinct from tunnel establishment.
6. native OS support must follow the current Fireware release-note OS compatibility table.
7. profile/certificate replacement and expiry are lifecycle risks.

These become future capability-regression tests.

## Security / privacy implications

- validate server certificate SAN/EKU/expiry and trust;
- MS-CHAPv2/EAP user authentication is a compatibility fact, not permission to weaken other protocol defaults;
- MFA push/full-tunnel reachability must be modeled/tested;
- credential/certificate/private-key/profile/secrets remain distinct;
- use secure OS credential/certificate stores;
- route/DNS/full-vs-split policy is a security property;
- proposal/cipher capability must be exact client/Fireware evidence;
- profile packages may contain certificates/configuration and require controlled distribution;
- logs/support bundles must be redacted according to platform;
- exact strongSwan dependency/SBOM/security review is required at implementation freeze.

## Reuse decision

`STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST / STRONGSWAN FOR ANDROID+ADVANCED PORTABILITY / WATCHGUARD-SPECIFIC PROFILE+AUTH CERTIFICATION REQUIRED`

Preferred architecture:

`WatchGuard/client profile import`

`-> PVNetwork canonical IKEv2 profile + capability validation`

`-> native OS IKEv2 adapter where available`

`or -> pinned strongSwan adapter where justified`

`-> exact WatchGuard Firebox certification`

Do not create a separate WatchGuard cryptographic engine.

## Residual after v1

Later v2/implementation/certification must resolve:

- exact current Fireware release/model/OS compatibility matrix;
- complete Phase 1/2 proposal defaults and admin choices;
- exact EAP/MS-CHAPv2 exchanges and AuthPoint/RADIUS combinations;
- `.TGZ` generated contents by Fireware version;
- profile script/mobileconfig/INI schema and update lifecycle;
- native Windows/macOS/iOS API mapping;
- Android strongSwan exact version/config;
- WatchGuard Windows client exact installer/version/license if retained;
- certificate chain/EC/RSA matrix;
- routes/DNS/full-vs-split/policy;
- packet captures/interoperability/reconnect/network-change;
- MDM/GPO/profile cleanup/update;
- performance/leak/security/release-advisory tests.
