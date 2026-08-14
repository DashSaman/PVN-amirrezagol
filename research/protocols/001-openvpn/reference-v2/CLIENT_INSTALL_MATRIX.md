# OpenVPN — Client Installation Matrix

Reference layer: `COMPLETE-REFERENCE-v2`

Research date: 2026-08-14

State: `IN-RESEARCH` — primary client/platform paths are classified; exact Store/package versions must be pinned per release.

## Client families

### OpenVPN Connect

Official OpenVPN end-user client product.

Primary role:

- Windows
- macOS
- Android
- iOS/iPadOS

Use as the official product behavior/interoperability reference. Exact supported OS versions, Store availability and package identifiers are live product constraints and must be rechecked before every PVNetwork release/certification snapshot.

### OpenVPN GUI

Open-source Windows community GUI around OpenVPN Community client.

Primary role: Windows desktop source/integration reference.

### OpenVPN for Android / ics-openvpn

Open-source Android client/source reference.

Primary role: Android VpnService/profile/import/storage reference; GPL application source and therefore reference-only by default for closed PVNetwork UI code.

### Tunnelblick

Open-source macOS OpenVPN client/reference.

Primary role: macOS menu-bar/profile/helper/privilege/config behavior reference.

### Linux Community Client / NetworkManager

OpenVPN Community command-line client and desktop NetworkManager OpenVPN integration are common Linux paths.

Primary role: Linux interoperability/platform integration reference.

---

# Platform matrix

Legend:

- `PRIMARY` — high-priority official or established client path
- `REFERENCE` — important source/behavior reference
- `POSSIBLE` — technically possible but exact support/version/package must be verified
- `NOT-ASSUMED` — no support claim without evidence

| Platform | OpenVPN Connect | Community/OpenVPN GUI path | Other major OSS reference | PVNetwork v2 decision |
|---|---|---|---|---|
| Windows 11 x64 | PRIMARY | OpenVPN GUI REFERENCE/PRIMARY community path | Community CLI/service | Must certify official Connect + PVNetwork/OpenVPN3 integration |
| Windows 11 ARM64 | Product/version dependent; verify current package | Community build availability must be pinned | Native/OpenVPN3 feasibility | Required if PVNetwork Windows ARM64 ships |
| Windows 10 supported builds | Product-version dependent | OpenVPN GUI reference | Community client | Support only while PVNetwork OS policy allows |
| macOS Apple Silicon | PRIMARY product path | Community CLI possible | Tunnelblick REFERENCE | Must test NetworkExtension/system-extension/helper implications |
| macOS Intel | Product-version dependent | Community CLI possible | Tunnelblick REFERENCE | Support based on PVNetwork minimum macOS policy |
| Android phone/tablet | PRIMARY Play/product path | N/A | ics-openvpn REFERENCE | PVNetwork must own VpnService lifecycle if using OpenVPN3 directly |
| Android TV / Google TV | OpenVPN product availability/TV UX must be checked separately | N/A | ics-openvpn contains TV-specific behavior reference | Do not assume phone APK UX is TV-ready |
| iPhone/iPad | PRIMARY App Store product path | N/A | other OSS clients only as licensed references | PVNetwork uses Apple-supported NetworkExtension architecture |
| Linux desktop | Official product availability varies by current release; do not assume | Community CLI PRIMARY | NetworkManager OpenVPN | High-priority Linux certification path |
| Linux server/headless client | Community CLI PRIMARY | service/systemd path | N/A | Required for automation/lab/site connections |
| ChromeOS | Android client may run on some devices; no generic claim | N/A | OS-specific | Verify device/managed-ChromeOS behavior explicitly |
| FreeBSD/BSD | Community package/source possible | CLI | package-specific | Advanced/reference only until tested |

---

# Windows installation paths

## OpenVPN Connect

Use only official OpenVPN distribution channels/packages. Record:

- installer/package version;
- architecture;
- code-signing publisher/certificate;
- install scope;
- services/drivers installed;
- DCO/virtual adapter components;
- default install path;
- auto-update behavior;
- uninstall cleanup;
- profile storage location/security behavior.

Do not redistribute the proprietary OpenVPN Connect installer without checking terms.

## OpenVPN GUI / Community client

Record:

- Community OpenVPN package/version;
- GUI version;
- OpenVPN executable version;
- TAP/Wintun/DCO driver versions actually installed;
- service/helper components;
- per-user/system profile locations;
- update path.

A GUI version number is not enough to define the core/driver runtime.

---

# Android installation paths

## OpenVPN Connect

Primary official Store/product path. Record:

- Play Store/package version;
- min/target SDK at certification time;
- VPN permission behavior;
- notifications/background service behavior;
- Always-On/block-without-VPN behavior;
- per-app support where exposed;
- import/deep-link/file picker behavior;
- secure storage/account/profile behavior.

## ics-openvpn

Reference source pin already recorded in v1 research.

Installation/build audit later must record:

- package/application ID;
- Gradle/Android plugin versions;
- embedded native OpenVPN core version;
- native ABI packages;
- permissions;
- VpnService declaration;
- foreground-service/notification behavior;
- TV manifest/features;
- Store/F-Droid/package variants.

---

# Apple installation paths

## OpenVPN Connect

Official App Store/product path.

Record:

- exact app version/build;
- supported iOS/iPadOS/macOS versions;
- NetworkExtension type;
- profile import methods;
- certificate/keychain behavior;
- MDM/managed app configuration if relevant;
- upgrade behavior.

## Tunnelblick

macOS reference client installed through its own signed/notarized distribution path.

Audit:

- application bundle/version;
- helper/elevation components;
- OpenVPN binaries bundled;
- configuration locations;
- update mechanism;
- notarization/signing;
- uninstall/cleanup.

Do not infer Tunnelblick packaging/licensing applies to PVNetwork.

---

# Linux installation paths

## Community CLI

Common options:

- distribution package manager;
- OpenVPN official repository where current docs support it;
- source build for controlled/test scenarios.

Record exact:

- OpenVPN package version;
- OpenSSL/mbedTLS/crypto backend;
- DCO/kernel module availability;
- service/unit behavior;
- config directories;
- unprivileged/privileged operation;
- route/DNS helper integration.

## NetworkManager OpenVPN

For desktop integration record:

- NetworkManager version;
- OpenVPN plugin package/version;
- imported profile fields preserved/lost;
- secrets stored in desktop keyring;
- GUI settings exposed;
- connection state/error mapping;
- IPv6/DNS/split routing behavior.

---

# Import methods to test per client/platform

- `.ovpn` file picker;
- drag/drop where desktop supports it;
- share/open-with intent;
- URL/profile provisioning where product supports it;
- Access Server account/profile import;
- certificate import;
- username/password prompt;
- token/MFA/SSO workflow;
- managed/MDM provisioning where supported;
- profile update/replacement.

## Lossless import requirement

For every reference client, test whether it:

- preserves unknown directives;
- rejects unsupported directives;
- rewrites profile content;
- stores credentials separately;
- imports inline cert/key material;
- supports external file references;
- supports multiple remote endpoints;
- changes cipher/TLS defaults.

PVNetwork should outperform silent-loss behavior by preserving original source and reporting unsupported semantics.

---

# Client installation receipt

For every tested client/platform create:

```text
Client product:
Version/build:
Source/store/package URL identity:
OS/build/architecture:
Install package hash/signature:
Installed services/helpers/drivers:
VPN permission/entitlement:
Profile storage:
Credential/key storage:
Import methods:
Update channel:
Uninstall result:
Residual profiles/secrets/drivers:
OpenVPN server versions tested:
PVNetwork comparison result:
```

---

# Current gaps

- current exact OpenVPN Connect OS minimum/version matrix;
- current Store/package identifiers and architecture availability;
- NetworkManager plugin exact current versions;
- exact Tunnelblick current package/helper/core mapping;
- exact OpenVPN GUI installer/core/driver mapping;
- Android TV availability/remote UX verification;
- real installation/uninstall receipts.
