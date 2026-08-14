# IKE / IPsec — Client Installation Matrix

Review date: 2026-08-14

Scope: entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP and 007 AH.

This matrix separates **user-installable/provisioned VPN clients** from the operating-system IPsec data plane. ESP and AH are normally provided by the OS/kernel/native IPsec implementation selected by the IKE/profile backend; they are not normal standalone consumer applications.

## 1. Android — native platform IKEv2

### Platform path

Modern Android exposes OS-managed platform VPN APIs through `VpnManager` and `Ikev2VpnProfile` on supported API levels.

Research facts from current Android documentation:

- `VpnManager` was added in API level 30 and lets applications provision/manage platform VPN profiles without implementing the complete VPN protocol in an app-owned `VpnService`;
- `Ikev2VpnProfile` was added in API level 30 and models IKEv2/IPsec profiles;
- current builder/API documentation includes PSK, digital-signature/certificate and username/password profile construction paths;
- platform support depends on device support such as `FEATURE_IPSEC_TUNNELS`;
- later API levels add additional routing/validation/configuration controls.

### Install/provision model

There is no separate Google Play “IKEv2 engine” required for the native path. PVNetwork would install as an Android application, provision an approved `Ikev2VpnProfile`, obtain required user/system consent, and let Android own the platform IKE/IPsec control/data plane.

### Entry applicability

- 004 IKEv2/IPsec: primary native-client candidate on supported Android versions;
- 005 IKEv1/IPsec: **not implied** by `Ikev2VpnProfile`;
- 006 ESP: provided by Android's platform IPsec stack when the IKEv2 VPN is established;
- 007 AH: do not infer support from `Ikev2VpnProfile`; exact platform/backend documentation/testing required.

### Runtime receipt still required

- clean install;
- profile provision/consent;
- start/stop;
- Always-On/Lockdown behavior if used;
- split/full routing;
- IPv4/IPv6/DNS;
- upgrade/uninstall/profile cleanup;
- OEM/Android-version matrix.

## 2. Android — strongSwan app/frontend path

Pinned source baseline:

- strongSwan 6.0.7 release commit `5973ff8e41deef4e015e1138a2de688acedf6f75`;
- Android application ID `org.strongswan.android`;
- source version recorded in the parent dossier: app version `2.6.2`, version code `96`, min SDK 21, compile/target SDK 36;
- dedicated `CharonVpnService` uses Android `VpnService` and native strongSwan components.

See:

`../ANDROID_FRONTEND_EVIDENCE_6_0_7.md`

### Role

Useful reference/advanced compatibility client, particularly when platform-native `Ikev2VpnProfile` cannot represent a required profile. It is not a drop-in closed-source PVNetwork frontend; root/project GPL obligations and application architecture remain relevant.

### Install channels

Exact current Store/F-Droid/package-channel correspondence must be verified against the release actually selected. Source availability and a buildable Android frontend do not prove that a particular Store binary corresponds to the pinned source.

## 3. Apple — iOS / iPadOS / macOS native IKEv2

### Platform path

Apple NetworkExtension provides `NEVPNProtocolIKEv2` as the native IKEv2 configuration object. Current Apple API documentation exposes separate IKE SA and CHILD SA cryptographic parameters plus IKEv2-specific controls such as DPD/PFS/MOBIKE-related behavior depending OS/API version.

`NEVPNProtocol` also uses Keychain-backed references for reusable credentials/identities such as `passwordReference` and `identityReference`.

### Install/provision model

PVNetwork installs as an ordinary app using approved NetworkExtension/VPN configuration APIs and required entitlements. The application owns canonical profile/UI/credential references; Apple owns the native IKEv2/IPsec engine and system VPN lifecycle.

### Entry applicability

- 004: preferred standard-client path where Apple's profile surface satisfies requirements;
- 005: generic IKEv1 support is **not implied** by `NEVPNProtocolIKEv2`;
- 006: ESP/data-plane behavior is owned by the native OS IPsec implementation;
- 007: AH support is not inferred from the IKEv2 API and requires exact platform evidence.

### Required Store/device receipts

- entitlement/provisioning profile;
- Keychain credential migration;
- real-device start/stop/rekey/network handover/sleep;
- App Store/TestFlight update/uninstall behavior;
- profile removal and credential cleanup;
- exact source/build/version correspondence.

## 4. Windows 10 / Windows 11 native IKEv2

### Platform path

Windows includes built-in VPN/IKEv2 capabilities. Microsoft documentation exposes several provisioning/control surfaces:

- Windows Settings VPN UI;
- PowerShell such as `Add-VpnConnection` and related VPN cmdlets;
- VPNv2 CSP / MDM/Intune-style profile provisioning;
- native IKEv2 profile configuration and route/tunnel options.

Microsoft's current VPN documentation also distinguishes user/device/Always-On/LockDown-style deployment modes and documents IKEv2-specific restrictions for some enterprise/device-tunnel scenarios.

### Crypto-policy caution

Current Microsoft VPN cryptography guidance explicitly warns that some documented Windows default IKEv2 cryptographic settings are insecure and provides mechanisms for explicit configuration. PVNetwork must therefore define/review an explicit compatible security policy rather than assuming OS defaults are automatically acceptable.

### Entry applicability

- 004: high-value native client path for standard IKEv2 profiles;
- 005: do not infer generic IKEv1 remote-access support from Windows IKEv2 APIs/profile type;
- 006: Windows native IPsec owns the negotiated data-plane processing;
- 007: exact Windows policy/profile/backend support must be independently established before exposing AH.

### Install/provision model

No third-party IKEv2 engine need be bundled for the normal native path. PVNetwork would install its own UI/service and provision/manage a documented Windows VPN profile through an approved adapter.

### Required receipts

- standard-user/admin requirements by provisioning method;
- user vs device profile;
- certificate/credential store behavior;
- start/stop/reconnect;
- Windows update compatibility;
- profile migration/removal;
- Store/MSIX/desktop distribution implications.

## 5. Linux desktop — NetworkManager / native daemon path

Linux does not expose one universal consumer IKEv2 UI/engine. Typical supported architecture choices include:

### NetworkManager + IPsec plugin/backend

Desktop environments may expose VPN configuration through NetworkManager and a strongSwan-compatible plugin/backend. Exact package names, plugin versions and desktop UI differ by distro/release.

### strongSwan `swanctl` / daemon

For advanced/operator-style Linux clients, install the reviewed strongSwan package set and use VICI/`swanctl` through a product adapter rather than treating a desktop GUI as protocol state.

### Libreswan

Libreswan is primarily a system IKE/IPsec daemon/gateway implementation but can initiate host/client connections. It is a Linux/Unix reference, not a unified cross-platform PVNetwork GUI.

### Entry applicability

- 004/005: determined by selected daemon/plugin/profile;
- 006/007: kernel/native IPsec backend capability and policy determine data-plane support.

### Required distro matrix

For each certified distro, record:

- distro/release/kernel;
- NetworkManager and plugin version if used;
- daemon/package version;
- service ownership;
- secret store;
- desktop UI version;
- install/update/remove commands;
- route/DNS/firewall cleanup.

## 6. FreeBSD / NetBSD / OpenBSD clients

StrongSwan and/or Libreswan support these operating-system families in varying forms. They are advanced/operator targets rather than first-wave consumer GUI platforms in PVNetwork.

Do not infer GUI/package parity from daemon source support. Exact package/source-build path and kernel IPsec backend must be recorded for each BSD/version if added to the certification set.

## 7. IKEv1 client policy — entry 005

IKEv1 is deprecated/legacy. The client matrix must therefore be **compatibility-driven**, not “install everywhere.”

Rules:

- never silently downgrade an IKEv2 profile;
- expose IKEv1 only on an approved backend/platform with exact server evidence;
- surface security/legacy status to advanced/admin users;
- isolate old authentication/algorithm requirements from normal IKEv2 defaults;
- do not infer IKEv1 from a native API named or documented specifically as IKEv2.

## 8. ESP client installation — entry 006

ESP is not a normal app users install separately. It is provided by:

- Linux/BSD kernel IPsec stack controlled by strongSwan/Libreswan or native policy tools;
- Windows native IPsec;
- Apple native VPN/IPsec;
- Android native platform IPsec or an approved VPN backend.

A product “ESP installed” status is therefore misleading. Report backend/data-SA capability instead.

## 9. AH client installation — entry 007

AH is likewise a native/kernel data-plane capability, not a consumer app. Because it is optional in modern IPsec architecture and problematic through NAT, it should only be considered for exact advanced backend/topology combinations.

## 10. Import/provisioning formats are backend-specific

PVNetwork canonical profiles must not simply persist native profile syntax.

Potential external inputs include:

- PVNetwork canonical profile;
- strongSwan/swanctl-oriented configuration;
- managed Android/IKEv2 app profile data;
- Apple NetworkExtension provisioning fields;
- Windows VPNv2/PowerShell/MDM fields;
- vendor/appliance configuration exports.

Normalize into typed canonical requirements, preserve unsupported fields/provenance, then generate the selected backend configuration.

## 11. Strict install/update/uninstall receipt table

The source/reference matrix above is not an execution receipt. Before strict certification, populate:

| Client target | Backend | Clean install | Provision | Connect | Update | Rollback | Uninstall/profile cleanup |
|---|---|---:|---:|---:|---:|---:|---:|
| Android API 30+ | native `VpnManager/Ikev2VpnProfile` | TODO | TODO | TODO | TODO | TODO | TODO |
| Android fallback | reviewed strongSwan-based backend | TODO | TODO | TODO | TODO | TODO | TODO |
| iPhone/iPad | NetworkExtension IKEv2 | TODO | TODO | TODO | TODO | TODO | TODO |
| macOS | NetworkExtension/native IKEv2 | TODO | TODO | TODO | TODO | TODO | TODO |
| Windows 10/11 | native IKEv2/VPNv2 | TODO | TODO | TODO | TODO | TODO | TODO |
| Ubuntu/Debian desktop | selected NM/strongSwan path | TODO | TODO | TODO | TODO | TODO | TODO |
| Fedora-family desktop | selected NM/strongSwan/Libreswan path | TODO | TODO | TODO | TODO | TODO | TODO |

All `TODO` rows are **BLOCKED_EXTERNAL execution work**, not assumed success.
