# IKE / IPsec — Client UI and Menu Maps

Review date: 2026-08-14

Scope: entries 004 IKEv2/IPsec, 005 IKEv1/IPsec, 006 ESP and 007 AH.

There is no single canonical IKE/IPsec client UI. This dossier separates major platform/client surfaces and identifies which settings belong to the **product profile**, which belong to the **native OS**, and which are low-level backend diagnostics.

## 1. strongSwan Android frontend — pinned 6.0.7 source

Pinned source:

`strongswan/strongswan@5973ff8e41deef4e015e1138a2de688acedf6f75`

Detailed parent evidence:

`../ANDROID_FRONTEND_EVIDENCE_6_0_7.md`

### Major source-visible activities/screens

The reviewed Android manifest/source exposes major UI surfaces including:

- `MainActivity`
- `VpnProfileDetailActivity`
- `VpnProfileControlActivity`
- `VpnProfileSelectActivity`
- `VpnProfileImportActivity`
- `TrustedCertificatesActivity`
- `TrustedCertificateImportActivity`
- `SelectedApplicationsActivity`
- `SettingsActivity`
- `LogActivity`
- remediation/instruction/state screens
- Quick Settings tile (`VpnTileService`)

### Profile/configuration concepts visible in pinned storage/source

The source-level profile/database model contains fields/domains including:

- profile name/UUID;
- gateway;
- VPN type;
- username;
- credential/password-related field;
- user/trusted certificate references;
- local and remote identity;
- IKE proposal;
- ESP proposal;
- MTU/port;
- included/excluded subnets;
- split-tunneling behavior;
- selected-app include/exclude mode/list;
- NAT keepalive;
- DNS servers;
- proxy host/port/exclusions;
- flags and advanced compatibility options.

### PVNetwork learning

A good client UI should not make users edit a single opaque native config blob. Separate:

1. identity/authentication;
2. routing/split tunnel;
3. certificates/trust;
4. protocol/security compatibility;
5. per-app routing where the platform supports it;
6. connection controls/status;
7. diagnostics/logs.

The upstream Android schema contains secret-looking TEXT fields, so PVNetwork must not copy its persistence model blindly. Product secrets must live behind secure-store references.

## 2. Android native IKEv2 / VpnManager user flow

For the platform-native backend, much of the protocol UI belongs to the provisioning application while permission/connection ownership belongs to Android.

### PVNetwork app surfaces

Recommended:

- IKEv2 profile list
- Add/Edit IKEv2 Profile
  - Server/Gateway
  - Remote identity
  - Local identity where required
  - Authentication type
  - Credential/certificate selector
  - Routing/split tunnel
  - DNS
  - Advanced compatibility/security policy
- Provision to Android
- Connect / Disconnect
- Native profile status
- Remove native profile
- Diagnostics / Compatibility

### System-owned flow

`VpnManager`/platform provisioning can require a system user-consent activity before the profile is provisioned. The OS then owns ongoing platform VPN negotiation/data plane.

PVNetwork must render that as a state transition such as:

`NeedsSystemConsent -> Provisioned -> Connecting -> Connected`

rather than pretending the app itself established the tunnel.

### Not a generic IKEv1 UI

Any IKEv1 compatibility frontend must be a separate backend/capability decision. Do not add an `IKE version` dropdown to the native Android IKEv2 backend if the API cannot represent IKEv1.

## 3. Apple iOS / iPadOS / macOS native IKEv2

Apple's native backend is represented by `NEVPNProtocolIKEv2` and related NetworkExtension configuration/status APIs.

### Product-facing profile editor

A PVNetwork Apple IKEv2 screen should map product concepts rather than exposing raw NetworkExtension property names:

- Name
- Server Address
- Remote Identity
- Local Identity / Certificate
- Authentication
  - certificate/identity
  - username/password where supported profile requires it
  - shared secret only if platform/profile policy permits
- On-Demand / connection policy where allowed
- Routing / split include/exclude where platform API permits
- DNS / proxy policy where applicable
- Advanced IKE / Child SA compatibility
- Diagnostics

### Low-level advanced fields

Apple's API exposes separate IKE SA and Child SA parameter objects. Product UI should keep these in an advanced/admin compatibility area, for example:

- encryption algorithm;
- integrity algorithm;
- Diffie-Hellman / key-exchange group;
- lifetime;
- PFS / Child SA settings;
- DPD-related behavior;
- MOBIKE/redirect/revocation/MTU options depending platform/API.

Do not expose a platform property merely because it exists; capability and security policy must determine whether it is user-editable.

### Credential UI

`passwordReference` / `identityReference` style Keychain references reinforce a required design rule:

- UI chooses/creates a credential;
- canonical profile stores an opaque secure reference/provenance;
- ordinary settings/export/log views do not reveal reusable secrets.

### System UI boundary

Apple also exposes system VPN status/settings outside the PVNetwork app. PVNetwork must reconcile its app state with OS state and external disconnects/profile removal rather than assuming the UI is sole owner.

## 4. Windows 10 / Windows 11 built-in IKEv2

Windows exposes both end-user Settings UI and enterprise/automation provisioning surfaces.

### End-user Settings surface

Current Windows user experience includes:

- VPN profile/account entry in Settings;
- connection status;
- connect/disconnect from Settings and newer Quick Settings/Quick Actions surfaces depending Windows version;
- profile editing/advanced configuration through OS UI and system tools.

PVNetwork should treat this as a native profile it provisions/monitors rather than attempting to replace the Windows IKE/IPsec engine.

### PVNetwork Windows UI

Recommended product surfaces:

- Profiles
- Add/Edit IKEv2
  - server
  - authentication/credential source
  - certificate/identity
  - routing/tunnel mode
  - DNS
  - advanced IKE/IPsec policy
- Provision/Repair Native Profile
- Connect/Disconnect
- Native Windows Status
- Routes / DNS Effective State
- Certificate/Credential Status
- Diagnostics / Event Log guidance
- Remove Profile

### Enterprise admin provisioning surface

Keep Intune/MDM/VPNv2 CSP concepts separate from normal user UI. Admin-only templates may include:

- Always On;
- device/user tunnel;
- traffic filters/routes;
- LockDown-style policy;
- authentication/certificate selection;
- native profile XML/CSP settings.

A normal user's profile editor should not expose every CSP node.

### Crypto-policy warning

Because Microsoft documentation warns that some Windows default IKEv2 cryptographic settings are insecure, PVNetwork's advanced editor/diagnostics should clearly show **effective configured policy** rather than a reassuring generic “Windows default” label.

## 5. Linux — NetworkManager frontend

When a Linux desktop uses NetworkManager plus a strongSwan/IPsec plugin, the visible UI commonly belongs to the desktop/NetworkManager frontend, not to strongSwan itself.

PVNetwork must capture exact distro/desktop/plugin screenshots and field maps before claiming parity because GNOME/KDE/distribution versions differ.

### Product concepts worth preserving

- gateway/server;
- authentication method;
- username/certificate/identity;
- routes/split tunnel;
- DNS;
- IKE/ESP proposal compatibility;
- connection state/errors.

### Advanced/operator Linux surface

`swanctl`, VICI status and kernel XFRM state are operator/diagnostic surfaces. They should feed a normalized PVNetwork diagnostics page rather than being copied directly into a consumer form.

## 6. Libreswan user/admin surface

Libreswan is daemon/CLI/config oriented. Important commands/config domains in the pinned documentation include:

- connection definitions in `ipsec.conf` and includes;
- secrets/credential configuration;
- NSS certificate/key database;
- `ipsec start` / `ipsec stop`;
- `ipsec trafficstatus`;
- `ipsec briefstatus`;
- machine-readable/global status;
- certificate import and NSS initialization.

If PVNetwork ever builds an adapter, expose typed operations rather than requiring users to edit raw `ipsec.conf`.

## 7. IKEv1 UI policy

IKEv1 is a legacy compatibility capability, not a standard default-profile tab.

If a selected backend genuinely supports it, put it behind an explicit advanced/legacy mode with:

- warning that IKEv1 is deprecated;
- exact authentication/mode selection required by the server;
- no silent Aggressive/Main/Quick mode inference that weakens policy;
- algorithm warnings based on current policy;
- no silent downgrade from an IKEv2 profile.

## 8. ESP and AH UI placement

### ESP

Normal users should see ESP as an effective **data protection / Child SA** detail, not as a separate app/profile family.

Advanced status can show:

- ESP active;
- tunnel vs transport mode;
- NAT-T active/not active;
- lifetime/rekey;
- traffic selectors;
- safe algorithm names;
- inbound/outbound SA state without secrets.

### AH

AH belongs in advanced compatibility/admin configuration only. The UI must state:

- integrity/authentication only;
- no payload encryption/confidentiality;
- NAT limitations;
- optional/low-priority backend support.

Never present `AH Connected` with the same encrypted-lock semantics used for an ESP confidentiality tunnel.

## 9. Error/diagnostic UX contract

Normalize backend-specific errors into categories with expandable technical detail:

- Permission/Provisioning Required
- Credential Missing/Locked
- Certificate Invalid/Expired/Untrusted
- Authentication Failed
- IKE Version Unsupported
- No Matching IKE Proposal
- No Matching Child/ESP Proposal
- Traffic Selector Mismatch
- NAT/Reachability Problem
- Kernel/Data-SA Install Failed
- Rekey Failed
- Peer Deleted SA
- Platform Policy Blocked
- Network Unavailable

Technical details may expose safe identifiers/SPI/algorithm names, but never PSKs/private keys/session keys.

## 10. Persian/RTL requirements

PVNetwork's Persian client must keep technical values LTR inside RTL UI:

- IP addresses;
- CIDRs;
- ports;
- certificate fingerprints;
- proposal names;
- SPIs;
- interface names;
- log lines;
- file paths.

Do not mirror protocol diagrams/packet order simply because UI text is RTL.

## 11. Remaining UI evidence

Strict client UI reference/certification still needs:

- current running screenshots for each selected client/OS version;
- full NetworkManager plugin field map on selected Linux desktops;
- Windows exact per-version screen flow;
- Apple real-device provisioning/status/error flow;
- Android native provisioning consent and OEM behavior;
- accessibility, keyboard/D-pad, tablet/foldable and Android TV decisions where applicable;
- store-distributed binary version correspondence.
