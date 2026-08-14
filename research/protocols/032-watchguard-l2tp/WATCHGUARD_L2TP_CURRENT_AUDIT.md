# 032 — WatchGuard Mobile VPN with L2TP — Current v1 Audit

Review date: 2026-08-14

Purpose: original `COMPLETE-RESEARCH-v1` evidence for WatchGuard Firebox Mobile VPN with L2TP. This is not implementation or WatchGuard certification.

## Protocol and product identity

WatchGuard Mobile VPN with L2TP is a standards-based **L2TPv2 remote-access service**. By default WatchGuard protects L2TP with **IPsec** and explicitly recommends leaving IPsec enabled.

Current WatchGuard documentation says the service supports most L2TPv2 clients compliant with RFC 2661.

Default secure layering:

`native / standards L2TP client`

`-> IKE/IPsec tunnel authentication`

`-> IPsec-protected L2TPv2`

`-> PPP user authentication`

`-> virtual IP / network policy`

`-> Firebox resource authorization`

`-> application traffic`

WatchGuard technically allows L2TP without IPsec, using UDP 1701 alone, but states that this lacks the security of IPsec and is not recommended. PVNetwork must not treat unprotected L2TP as a normal secure fallback.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-us/Fireware/mvpn/l2tp/l2tp_vpn_about_c.html
- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-us/Fireware/mvpn/l2tp/l2tp_vpn_config_edit_c.html

Research decision direction:

`STANDARD L2TP/IPSEC INTEROPERABILITY TARGET / NATIVE-OS-FIRST WHERE CURRENT OS SUPPORT EXISTS / REUSE ENTRY-008 LAYERED STACK / WATCHGUARD AUTH+POLICY CERTIFICATION REQUIRED`

## Scope boundary

Keep entry 032 separate from:

- entry 008 generic L2TP/IPsec — reusable standards/layering reference;
- WatchGuard 030 IKEv2;
- WatchGuard 031 SSL/OpenVPN;
- WatchGuard Mobile VPN with IPSec;
- raw/unprotected L2TP as a recommended product mode.

Generic L2TP/IPsec negotiation is not proof that WatchGuard user/group/policy/MFA/address-pool behavior works.

## Firebox server/admin model

Current Fireware Web UI navigation:

`VPN > Mobile VPN > L2TP`

The first-time L2TP Setup Wizard covers:

- authentication server;
- users/groups;
- virtual IP address pool;
- IPsec tunnel authentication method.

The manual/edit configuration expands into Networking, Authentication and IPsec settings.

Current setup automatically creates or uses:

- `L2TP-Users` group;
- hidden `Allow-IKE-to-Firebox` connect policy;
- `WatchGuard L2TP` policy for UDP 1701 from `L2TP-IPSec`;
- `Allow L2TP-Users` access policy.

WatchGuard recommends restricting network resources instead of leaving broad defaults.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_config_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_policies_c.html

## IPsec tunnel authentication

WatchGuard supports two IPsec tunnel-authentication methods for L2TP:

1. **Pre-shared key (PSK)**;
2. **IPsec certificate**.

Certificate requirements include importing the certificate and matching endpoint certificate algorithm expectations. Server identity data must match expected hostname/IP in the certificate SAN where applicable.

Current L2TP IPsec configuration exposes Phase 1/Phase 2 transform sets, NAT Traversal and Dead Peer Detection. Current default Phase 1 examples include combinations such as AES-256 with SHA/SHA2 and DH groups, but exact configured transforms remain Fireware/client-version evidence.

Official references:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-us/Fireware/certificates/authentication_mvpn-l2tp_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/general/mobile_vpn_tunnel_auth_c.html

PVNetwork must keep the IPsec PSK/certificate secret/trust boundary separate from the PPP user's username/password/MFA.

## User authentication and MFA

Current WatchGuard L2TP user authentication supports:

- Firebox-DB;
- RADIUS;
- Active Directory through RADIUS architecture;
- AuthPoint MFA where the selected flow supports MS-CHAPv2.

Current WatchGuard documentation states Mobile VPN with L2TP supports MFA solutions compatible with **MS-CHAPv2**. In Fireware 12.5.3+, AuthPoint can be used with AD through NPS; current WatchGuard guidance for this path requires AuthPoint push-based authentication and does not support AuthPoint OTP in that specific L2TP/NPS flow.

Official reference:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_user_auth_c.html

MFA support is therefore an exact auth-server/client/policy capability, not a generic L2TP feature flag.

## Ports and handshake layers

With IPsec enabled, current WatchGuard setup requires the standard IPsec/L2TP network path:

- UDP 500 — IKE;
- UDP 4500 — NAT-T when NAT is detected;
- ESP IP protocol 50 where ESP is not UDP-encapsulated;
- UDP 1701 — L2TP inside the protected path.

WatchGuard's built-in policies reflect separate IKE/IPsec and L2TP layers.

Without IPsec, only UDP 1701 is required, but that insecure mode is not a PVNetwork default/recommended path.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_config_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/general/mobile_vpn_types_c.html

Connection-state model:

`IKE/IPsec tunnel`

`-> L2TP control/session`

`-> PPP / MS-CHAPv2 user auth`

`-> virtual IP assignment`

`-> effective routes / DNS`

`-> Firebox access policy`

`-> application traffic health`.

No earlier stage alone is final success.

## Reuse of entry 008 / public implementations

PVNetwork already completed the generic layered L2TP/IPsec research in entry 008, including serious public server/client components and the strict separation of IPsec, L2TP and PPP.

Entry 032 therefore does not need a WatchGuard-specific tunnel engine.

Preferred later implementation choices:

- native OS L2TP/IPsec where the target OS still exposes a supported secure client and exact WatchGuard transforms/auth match;
- existing approved IPsec/L2TP platform components behind a product-owned adapter where native client APIs are not sufficient;
- WatchGuard-specific policy/auth/certificate/profile capability handled above the standards engine.

Do not write new IPsec/L2TP cryptography or PPP authentication implementations merely to mimic WatchGuard.

## Client platform matrix

### Windows

WatchGuard documents use of the Windows built-in VPN client with `Layer 2 Tunneling Protocol with IPsec (L2TP/IPSec)`.

Current WatchGuard example config separates:

- server/Firebox address;
- MS-CHAPv2 user auth;
- PSK or certificate tunnel auth;
- native Windows Connect/Disconnect UI.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/mvpn/l2tp/l2tp_vpn_client_win10_c.html

### macOS

WatchGuard documents the native macOS VPN client using `L2TP over IPSec`, server address, account name, password and PSK/certificate tunnel auth.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_client_macosx_c.html

### iOS

WatchGuard documents native iOS L2TP configuration with server/account/password/shared secret and Send All Traffic.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/mvpn/l2tp/l2tp_vpn_client_ios_manual_c.html

### Android

WatchGuard documentation records a critical current limitation: the **built-in Android L2TP client is no longer supported in Android 12 and higher**.

Therefore PVNetwork must not claim current native Android L2TP support on modern Android. Any third-party Android alternative would require its own maintained source/license/security/platform audit.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_client_android.html

### Other clients

WatchGuard says most RFC 2661-compatible L2TPv2 clients may work if their IPsec/L2TP/auth settings match, but exact current OS support must follow the Fireware release-note compatibility table.

## Client UI / profile lifecycle

There is no mandatory WatchGuard endpoint application for the standard path. User UI is the platform's native VPN configuration surface.

PVNetwork research therefore models:

- server address/name;
- L2TP/IPsec type;
- PSK or certificate reference;
- account/user identity;
- password/credential policy;
- full/split route choice where the OS supports it;
- Connect/Disconnect/status;
- platform certificate/profile install/remove lifecycle.

For Windows pre-logon/deployment, WatchGuard points to custom IKEv2/L2TP profile deployment guidance; exact MDM/GPO/profile mechanics remain later platform implementation evidence.

This native-client architecture avoids inventing a WatchGuard-branded consumer app for entry 032.

## Routing / Internet access / authorization

WatchGuard's default recommendation for L2TP is **default-route/full tunnel**.

Split tunnel is possible on some desktop clients but requires manual client-side routes, and WatchGuard explicitly does not support those manual split-tunnel L2TP configurations. WatchGuard recommends IKEv2 or SSL if split tunneling is required.

On mobile OSes the default-route behavior may be fixed by the platform.

Firebox access policy, NAT and L2TP address pool remain separate from client route state.

Official reference:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-us/Fireware/mvpn/l2tp/l2tp_internet_access_c.html

## Virtual IP / address-pool behavior

Firebox L2TP setup requires a virtual IP pool with enough addresses for expected concurrent users. Current WatchGuard documentation notes multiple users can connect from the same external IP, but an exhausted or misconfigured virtual pool can prevent additional clients from receiving valid virtual IP addresses.

This is an explicit failure state after some earlier connection/authentication work may already have succeeded.

## Logs / diagnostics / failure ownership

Current WatchGuard troubleshooting distinguishes:

- OS compatibility/client configuration;
- IKE/IPsec negotiation;
- tunnel certificate/PSK mismatch;
- L2TP/PPP auth;
- user/group membership;
- virtual IP assignment;
- policies/authorization;
- route/DNS/network resource access.

WatchGuard explicitly notes that a client can connect but still fail to reach resources because network or policy configuration is wrong. It also notes some OS clients can appear to connect even if the user is not in the correct authorization group.

Reference:

- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_tshoot_c.html

PVNetwork must never equate OS `Connected` with authorized/healthy application data path.

## Installation / update lifecycle

Native client paths generally require no WatchGuard application install. Lifecycle instead includes:

- VPN profile creation/removal;
- PSK rotation or certificate import/renewal/removal;
- OS client compatibility after platform updates;
- managed profile deployment where used;
- Firebox configuration/profile/policy updates.

Modern Android is a negative platform capability because the built-in L2TP client was removed; this is a migration signal toward IKEv2/SSL rather than a reason to ship an abandoned L2TP app.

## Source / license / build boundary

WatchGuard Fireware is proprietary:

- source tree: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- internal build/CI/tests: N/A;
- code/UI/assets: `DO-NOT-COPY`;
- official docs/config behavior: `REFERENCE-ONLY`.

Reusable protocol source/license/build evidence is inherited from the completed generic L2TP/IPsec/strongSwan-related dossiers where exact components are selected later.

Native platform clients are platform capabilities rather than WatchGuard source reuse.

## Compatibility and security lessons converted to tests

1. **IPsec disabled** — must be visibly classified insecure/not recommended and never selected silently.
2. **Android 12+** — native L2TP is unavailable; platform capability must fail closed rather than present a dead setup path.
3. **PSK vs certificate** — tunnel identity and PPP user credentials must remain separate.
4. **certificate SAN/algorithm/trust** — client/server certificate compatibility is exact-platform evidence.
5. **MFA** — MS-CHAPv2-compatible MFA only; AuthPoint NPS flow has push/OTP restrictions.
6. **virtual pool exhaustion** — connection cannot be considered healthy without assigned client address.
7. **authorization** — wrong `L2TP-Users`/access policy can produce a misleadingly connected client with unusable traffic.
8. **split tunnel** — manual route configuration is unsupported by WatchGuard for L2TP; do not market it as first-class cross-platform behavior.
9. **transform mismatch** — Phase 1/2 settings must match native client capability exactly.
10. **NAT-T/ports** — UDP 500/4500 and ESP/L2TP reachability are distinct network failure classes.

## Security / privacy implications

- secure default requires IPsec enabled;
- keep PSK/certificate/private-key separate from PPP user credentials and MFA state;
- certificate trust/expiry/SAN/algorithm must be validated;
- MS-CHAPv2 is a compatibility boundary and should not be generalized to other protocol defaults;
- avoid obsolete/weak transform fallback unless exact legacy compatibility is explicitly approved;
- route/DNS/full-tunnel/access policy are security state;
- platform keychain/certificate stores should own reusable secrets;
- diagnostic logs must redact credentials/keys;
- unprotected L2TP must not become an automatic fallback;
- exact current OS/Fireware security support must be rechecked at release freeze.

## Reuse decision

`STANDARD L2TP/IPSEC INTEROPERABILITY TARGET / NATIVE-OS-FIRST WHERE CURRENT SUPPORT EXISTS / REUSE ENTRY-008 LAYERED STACK / WATCHGUARD AUTH+POLICY CERTIFICATION REQUIRED / MODERN-ANDROID-NATIVE-UNAVAILABLE`

Preferred future architecture:

`WatchGuard/server profile information`

`-> canonical typed L2TP/IPsec profile`

`-> native OS adapter where supported`

`or -> approved existing L2TP/IPsec components`

`-> exact WatchGuard Firebox certification`.

No WatchGuard-specific cryptographic engine is required.

## Residual after v1

Later v2/implementation/certification must resolve:

- exact current Fireware/model/OS compatibility matrix;
- exact Phase 1/2 transforms/security floor;
- exact PPP/MS-CHAPv2 behavior and MFA/RADIUS/AuthPoint matrix;
- native Windows/macOS/iOS API/profile deployment details;
- certificate/PSK provisioning/rotation;
- third-party Android replacement decision if business requirements demand it;
- full server/admin menu and deployment topology;
- virtual pool, NAT, routes/DNS/full tunnel/access policy behavior;
- packet captures and real interoperability;
- reconnect/network-change/profile-update tests;
- post-review platform deprecations/advisories.
