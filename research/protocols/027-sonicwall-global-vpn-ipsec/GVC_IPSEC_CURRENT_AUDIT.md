# 027 — SonicWall Global VPN / IPsec — Current v1 Audit

Review date: 2026-08-14

Purpose: current official/vendor + standards/public-engine research for original `COMPLETE-RESEARCH-v1`. This is not implementation or vendor certification.

## Scope boundary

Entry 027 is **SonicWall Global VPN Client (GVC) / GroupVPN remote-access IPsec**.

It is separate from:

- entry 026 NetExtender / SSL VPN;
- SonicWall Mobile Connect;
- L2TP/IPsec fallback;
- generic site-to-site IPsec;
- any assumption that a standards IKE/IPsec engine automatically implements SonicWall GroupVPN policy provisioning.

## Current official product position

SonicWall's current VPN-client page continues to publish **Global VPN Client for Windows** and describes it as a traditional client-based VPN using IPsec. Current compatible firewall families shown by SonicWall include TZ, NSa, NSsp and NSv.

Primary public page:

- https://www.sonicwall.com/products/remote-access/vpn-clients/

SonicWall's public documentation still exposes the **4.10** GVC documentation line. A public release-note artifact exists for 4.10.7, and SonicWall's 2026 support KB tells users to install the latest released GVC from MySonicWall but does not expose an unambiguous exact latest package number in the public HTML reviewed here.

Therefore research records:

`CURRENT-PRODUCT-LINE-ACTIVE / WINDOWS / EXACT-LATEST-DOWNLOAD-PACKAGE-REQUIRES-MYSONICWALL-FREEZE`

Do **not** invent a 4.10.x package beyond evidence. Exact installer version/hash/signature is a later package-freeze requirement.

Current May 2026 SonicWall KB explicitly treats GVC on Windows 10/11 as an active product troubleshooting target and instructs customers to use the latest released GVC.

Reference:

- https://www.sonicwall.com/support/knowledge-base/kA1VN0000000LHO0A2

## Gateway architecture: GroupVPN and provisioning

Current SonicOS 7.1/8 documentation defines **GroupVPN** as automatic policy provisioning for Global VPN Client. The firewall owns policy that is downloaded to the client.

Key server-side layers:

1. WAN/WLAN GroupVPN policy;
2. IKE authentication method, typically preshared secret or certificate variants;
3. Phase 1 proposal;
4. Phase 2 / IPsec proposal;
5. XAUTH user authentication policy;
6. client password-caching policy;
7. virtual-adapter policy;
8. DHCP-over-VPN / address assignment;
9. split/full-tunnel/default-route policy;
10. user/group VPN Access authorization;
11. optional client-policy export/provisioning.

Current GroupVPN reference:

- https://www.sonicwall.com/support/technical-documentation/docs/sonicos-7-1-ipsec_vpn/Content/site-to-site-vpns-groupvpn-manage.htm
- https://www.sonicwall.com/support/technical-documentation/docs/sonicos-8-0-ipsec_vpn/Content/site-to-site-vpns-groupvpn-manage-ike-preshared-secret.htm

### Important vendor-specific boundary

GroupVPN is more than `IKEv1 + ESP`:

- SonicWall explicitly describes automatic client-policy provisioning;
- current firewall configuration includes `Use Default Key for Simple Client Provisioning`, which SonicWall documents as using **Aggressive Mode** for initial exchange with a default preshared key when enabled;
- XAUTH credential policy may be Never / Single Session / Always;
- virtual-adapter/DHCP policy is pushed/managed by the gateway;
- user/group VPN Access controls what networks are actually authorized;
- exported/imported RCF client policy is a SonicWall-specific provisioning artifact.

Thus a generic IPsec client that negotiates IKE/ESP is not automatically GVC-compatible.

## IKE / IPsec standards core

The generic standards portion reuses the already-reviewed PVNetwork strongSwan/IKEv1 evidence.

Current reviewed strongSwan baseline:

- repository: `strongswan/strongswan`
- release: `6.0.7`
- exact release commit: `5973ff8e41deef4e015e1138a2de688acedf6f75`
- license baseline: GPLv2 family / exact distribution review required
- source/build/security evidence: `research/upstreams/strongswan-family/`

strongSwan source supports IKEv1 authentication rounds including XAUTH, standard IKE/IPsec proposals, NAT traversal and kernel/user-space integration through its existing architecture/plugins.

This makes strongSwan a **standards-engine/interoperability candidate**, not a certified GVC replacement.

### Compatibility decision

`REUSE-IKE/IPSEC-WHERE-SEMANTICS-MATCH / SONICWALL-GROUPVPN-PROVISIONING-MUST-BE-CERTIFIED-SEPARATELY`

Do not implement new IKE/IPsec cryptography from scratch.

## Public SonicWall-specific implementation ecosystem

Searches for a maintained public SonicWall GVC drop-in did not identify a canonical modern independent implementation comparable to OpenConnect for AnyConnect or snx-rs for Check Point.

Relevant ecosystem is instead:

- strongSwan — maintained standards IKE/IPsec/XAUTH engine/reference;
- Libreswan — alternative maintained IPsec implementation/operator reference from the broader IPsec dossier;
- historical/general IPsec clients capable of IKEv1/XAUTH may provide behavioral comparison, but are not selected as production GVC engines without exact GroupVPN proof;
- random public configuration snippets/scripts are not treated as canonical protocol implementations.

A GitHub repository search for SonicWall-specific strongSwan/GVC projects did not surface a serious maintained dedicated engine. This negative finding is recorded instead of promoting an incidental repository.

## Current official connection workflow / UI map

SonicWall's support KBs expose a stable research-level GVC workflow.

### New connection

Typical current documented flow:

`File > New Connection`

then:

- remote access;
- firewall WAN IP / DNS name;
- Finish;
- select/right-click connection;
- Enable;
- enter preshared secret when required;
- enter username/password when XAUTH is required;
- virtual adapter acquires an IP;
- state changes to Connected.

Reference:

- https://www.sonicwall.com/support/knowledge-base/how-can-i-configure-ipsec-client-based-vpn-for-remote-users/kA1VN0000000HnN0AU

### Connection Properties

SonicWall documents three ways to open Connection Properties: File > Properties, context menu Properties, or toolbar. Documented tabs include:

- General;
- User Authentication;
- Peers;
- Status.

General includes reconnect-after-sleep/hibernation behavior. User Authentication behavior can be disabled by gateway policy when password caching is not permitted.

Reference:

- https://www.sonicwall.com/support/knowledge-base/kA1VN0000000Fyd0AE

### Global options

SonicWall documents `View > Options`, including launch-at-login, warning before a connection blocks local Internet, and remembering last window state.

Reference:

- https://www.sonicwall.com/support/knowledge-base/how-to-specify-global-vpn-client-launch-options/kA1VN0000000Gkj0AE

### Diagnostics / report

SonicWall documents:

`Help > Generate Report`

The report can contain:

- client version;
- drivers;
- system information;
- IP addresses;
- route table;
- current log messages.

It can be saved as a text file.

Reference:

- https://www.sonicwall.com/support/knowledge-base/how-to-generate-global-vpn-client-gvc-report/kA1VN0000000OyQ0AU

PVNetwork lesson: a connection-state indicator alone is insufficient; routes, virtual-adapter state and data-path health are first-class diagnostics.

## Profile import / export and persistence

### Firewall -> client policy export

SonicOS can export a GroupVPN client policy in **RCF** format; the exported file can optionally be password-protected. GVC imports it through File > Import.

References:

- https://www.sonicwall.com/support/knowledge-base/how-to-export-the-rcf-configuration-file-from-sonicwall-and-import-it-into-global-vpn-client/kA1VN0000000Ihh0AE
- https://www.sonicwall.com/support/video-tutorials/how-to-export-the-vpn-client-configuration-and-import-it-on-the-global-vpn-client/5420366777001/

### Local client profile files

SonicWall documents GVC connection profiles in the Windows user's roaming profile, including:

- `Connections.rcf`
- `Backup.rcf`

under the SonicWall Global VPN Client AppData folder. The product recreates one from the other in some missing-file cases.

Reference:

- https://www.sonicwall.com/support/knowledge-base/how-can-i-export-import-connection-profiles-in-global-vpn-client-gvc/170505546228318

PVNetwork must not make proprietary RCF its canonical cross-protocol storage model. If future import is implemented, parse into typed product-owned fields and retain unsupported/vendor-specific data deliberately.

## Secret ownership

The research identifies distinct secret classes:

- GroupVPN preshared secret / initial provisioning key;
- XAUTH username/password;
- optional user certificate/private key;
- cached XAUTH credentials subject to gateway Never/Single Session/Always policy;
- optional password used to protect an exported RCF file;
- transient IKE/IPsec SA key material;
- administrator credentials, which are separate from VPN-user authorization.

PVNetwork rules:

- use platform secure storage for reusable secrets;
- never persist transient IKE/IPsec key material in generic profile records;
- respect administrator policy that forbids credential caching;
- do not silently weaken PSK/certificate/XAUTH policy for convenience.

## Virtual adapter / DHCP / routes

Current SonicWall documentation makes GVC's virtual-adapter lifecycle explicit.

- Virtual Adapter can obtain an address through DHCP-over-VPN.
- SonicOS can use internal or external DHCP servers/relay behavior.
- split tunnel is a common/default GroupVPN client setting;
- a policy may set the VPN gateway as default route for full-tunnel behavior;
- VPN Access ACL/user authorization controls destination access.

A documented failure case shows IKE Phase 1 and Phase 2 can complete while the client remains stuck at **Acquiring IP** due to DHCP/virtual-adapter configuration.

Reference:

- https://www.sonicwall.com/support/knowledge-base/client-vpn-hanging-at-acquiring-ip-using-sonicwall-dhcp-drop-code-bad-output-source-ip/kA1VN0000000MGh0AM

This becomes a mandatory future state model:

`IKE established != IPsec usable != virtual adapter configured != routes authorized != application data path healthy`.

## Policy authorization and failure semantics

SonicWall has a current troubleshooting article where GVC reports:

`Policy downloaded from the firewall is invalid or incomplete`

when the user/group has no required VPN Access permissions.

Reference:

- https://www.sonicwall.com/support/knowledge-base/global-vpn-client-logs-show-the-policy-downloaded-from-the-firewall-is-invalid-or-incomplete/kA1VN0000000G3K0AU

This proves a downloaded/pushed policy and user authorization are meaningful GVC-specific layers beyond generic IKE/ESP negotiation.

## Platform / installation lifecycle

Current SonicWall product page publishes GVC for **Windows**.

Installer workflow is documented as a self-extracting `GVCSetupXX.exe` with 32/64-bit variants in historic/current documentation. Installation includes the GVC virtual/network driver; uninstall may require the GVC Cleaner tool / DNE cleanup if prior bindings remain.

References:

- https://www.sonicwall.com/support/knowledge-base/how-do-i-install-or-uninstall-global-vpn-client-gvc/kA1VN0000000Jyj0AE
- https://www.sonicwall.com/support/knowledge-base/how-to-resolve-global-vpn-client-virtual-adapter-not-found-error/kA1VN0000000ImY0AU

A current May 2026 KB explicitly says its RSC workaround also applies to Windows 11 and can remain necessary even after GVC disconnect until reboot in affected Windows/driver cases.

Reference:

- https://www.sonicwall.com/support/knowledge-base/kA1VN0000000LHO0A2

### Coexistence risk

SonicWall documents runtime/install conflicts when GVC coexists with third-party IPsec VPN clients because of client/port/driver conflicts.

Reference:

- https://www.sonicwall.com/support/knowledge-base/gvc-encounters-run-time-conflicts-when-it-co-exists-with-any-3rd-party-ipsec-vpn-clients/kA1VN0000000Mi70AE

PVNetwork must therefore treat engine/driver coexistence as an installation/certification gate, not assume strongSwan/native and GVC can safely coexist on Windows.

## Security / release history

SonicWall published a security notice for Global VPN Client installer/application DLL search-order issues affecting older 4.10.7.1117-and-earlier installer components/selected 32-bit builds. The notice documents mitigations and a 4.10.7.1424 32-bit remediation context for one class.

Reference:

- https://www.sonicwall.com/support/product-notification/kA1VN0000000RBY0A2

Research consequences:

- exact GVC installer version/hash/signature matters;
- installer/updater security is separate from IKE/IPsec protocol security;
- old 4.10 package assumptions cannot be used as a production baseline without a current MySonicWall package freeze;
- strongSwan current baseline is separately pinned to 6.0.7 because its recent security fixes matter if it is evaluated later.

## Ports / protocol stages at v1 level

Use the shared IKE/IPsec model for standard transport:

- IKE typically UDP 500;
- NAT-T commonly UDP 4500;
- ESP data plane where not UDP-encapsulated;
- IKEv1 Phase 1;
- XAUTH / client authentication where configured;
- client-policy / configuration handling;
- IKEv1 Quick Mode / IPsec SA;
- virtual-adapter address acquisition / DHCP-over-VPN where configured;
- effective routes/authorization;
- application data.

Exact SonicWall vendor payloads/provisioning wire details are later v2/certification work. Do not infer them from generic strongSwan code.

## Images / assets

Official SonicWall KB/admin pages contain product screenshots for GVC and SonicOS GroupVPN configuration. These are useful navigation references only.

SonicWall branding, icons, installer assets and screenshots are proprietary/reference-only. PVNetwork uses independently designed UI and owner-supplied branding.

## Tests / CI boundary

GVC is proprietary; internal source tests/CI are unavailable and not fabricated.

Public strongSwan CI/test/security evidence is already captured under `research/upstreams/strongswan-family/` for the standards engine.

Future PVNetwork certification must include:

1. profile/parser pure tests;
2. IKE proposal/XAUTH/capability mapping tests;
3. strongSwan/native adapter tests where used;
4. controlled IPsec interoperability tests;
5. exact SonicOS GroupVPN lab with PSK/certificate/XAUTH variants;
6. policy provisioning/import/export tests;
7. virtual-adapter/DHCP/route/DNS tests;
8. split/full-tunnel tests;
9. sleep/hibernate/reconnect;
10. Windows driver/coexistence/install/update/uninstall/cleanup;
11. real application data-path health, not just Connected.

## Reuse decision

`VENDOR IPSEC INTEROPERABILITY TARGET / REUSE STRONGSWAN OR APPROVED IPSEC BACKEND FOR STANDARD SEMANTICS / DO NOT CLAIM GVC POLICY COMPATIBILITY WITHOUT LAB PROOF`

Do not clone proprietary GVC or GroupVPN provisioning from black-box observations.

If future product requirements need SonicWall GVC compatibility:

- start with standards IKE/IPsec/XAUTH capability matching;
- treat GroupVPN provisioning, RCF, virtual-adapter/DHCP behavior and vendor-specific identity/policy as separate certification features;
- maintain explicit exact-gateway/software capability records;
- if required vendor semantics cannot be legally/robustly reproduced, retain official-GVC-only status for that configuration.

## Residual after original v1

Later v2/implementation/certification must resolve:

- exact current downloadable GVC installer version/hash/signature from MySonicWall;
- exhaustive SonicOS 7/8/model/firmware GroupVPN matrix;
- exact IKEv1 modes/vendor payloads/provisioning wire behavior;
- exact crypto proposals and security floor per supported gateway;
- RCF format/protection semantics if import is implemented;
- certificate/XAUTH/RADIUS/LDAP permutations;
- Windows 10/11 virtual-adapter/driver lifecycle;
- real strongSwan/native interoperability vs official GVC;
- NAT-T/firewall/coexistence behavior;
- route/DHCP/full-vs-split policy and authorization;
- performance/reconnect/sleep/failover;
- release/advisory recheck at source/package freeze.
