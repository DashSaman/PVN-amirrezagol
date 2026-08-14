# SSTP / MS-SSTP — Client Installation Matrix

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## 1. Windows 10 / Windows 11 native client

State: `PRIMARY NATIVE CLIENT / NEEDS-LAB`.

Current Windows VpnClient tooling exposes SSTP as a native VPN tunnel type, including PowerShell provisioning such as `Add-VpnConnection` with `TunnelType Sstp` plus supported authentication/profile options.

### Install/provision model

No third-party SSTP engine is required for normal Windows use.

PVNetwork would:

- install its product UI/service;
- provision/manage a native Windows SSTP VPN profile through supported APIs/cmdlets;
- reference native credential/certificate stores;
- start/stop/observe the Windows VPN connection;
- reconcile external changes/removal from Windows Settings.

### Required certification

- selected Windows 11 builds;
- selected Windows 10 build if still in product scope;
- direct SSTP to RRAS;
- SSTP to SoftEther;
- EAP/MSCHAPv2/selected auth profiles;
- certificate validation/rotation;
- proxy/no-proxy;
- split/full tunnel;
- IPv4/IPv6;
- sleep/network change/reconnect;
- update/profile migration/removal.

## 2. Linux — sstp-client + pppd

State: `SERIOUS OPEN-SOURCE CLIENT / EXACT-PIN-RESIDUAL / NEEDS-LAB`.

Canonical repository reviewed:

`sstp-client/sstp-client`

Architecture:

- SSTP TCP/TLS/HTTP transport in sstp-client;
- PPP link/auth/network configuration through pppd/integration;
- system routing/DNS through PPP/network scripts or NetworkManager integration.

Before source freeze, materialize the exact selected upstream release/commit and root/component license into this dossier. Do not invent an immutable SHA if current connector evidence has not persisted it.

### Distro installation matrix required

For each selected Linux distro:

- package/repository/source-build version;
- TLS library/provider;
- pppd version/plugins;
- NetworkManager plugin/frontend if used;
- CA/certificate store;
- secret storage;
- service/privilege model;
- install/update/remove commands;
- route/DNS cleanup.

## 3. Linux — NetworkManager frontend

State: `FRONTEND/INTEGRATION DEPENDENT / NEEDS-LAB`.

Where a maintained NetworkManager SSTP plugin/frontend is selected, treat it as a separate application/component from `sstp-client` and record its own source/license/version.

Do not infer plugin availability from the existence of `sstp-client` alone.

## 4. macOS

State: `NO NATIVE SSTP CLAIM / THIRD-PARTY REVIEW REQUIRED`.

Apple's native VPN stack does not provide a generic built-in SSTP profile equivalent to Windows RRAS/VpnClient. Do not present SSTP as a native macOS protocol without a separately reviewed third-party implementation.

Potential third-party clients must be audited for:

- source/license or vendor provenance;
- SSTP/MS-SSTP completeness/crypto binding;
- TLS validation;
- Network Extension/system-extension architecture;
- App Store/notarization compatibility;
- secret storage;
- update lifecycle.

## 5. iPhone / iPad

State: `NO NATIVE SSTP CLAIM / THIRD-PARTY REVIEW REQUIRED`.

Do not infer SSTP support from Apple's generic NetworkExtension VPN APIs or from L2TP/IKEv2 support. A third-party app must include/implement an allowed tunnel provider and be separately reviewed/certified.

## 6. Android

State: `NO NATIVE SSTP CLAIM / THIRD-PARTY REVIEW REQUIRED`.

Android's native/platform VPN APIs do not imply SSTP protocol implementation. Third-party SSTP apps exist in the ecosystem, but none are approved here until source/vendor/license/security/store behavior is reviewed.

If PVNetwork later needs Android SSTP, compare:

- maintained open-source SSTP core candidate;
- VPNService integration;
- TLS/certificate validation;
- PPP implementation or equivalent networking stack;
- crypto binding;
- per-app/Always-On behavior;
- Play policy;
- battery/reconnect.

## 7. Android TV / Google TV

State: `NO CURRENT CERTIFIED PATH`.

Would require the same Android SSTP engine plus TV/D-pad/Always-On/store evidence. Do not expose the protocol in TV UI before a real engine and device tests exist.

## 8. FreeBSD / other Unix

State: `SOURCE/PORT DEPENDENT / NEEDS-LAB`.

If `sstp-client` or another maintained client builds/runs, record exact package/source/TLS/pppd support for the selected OS. Do not infer from Linux source portability.

## 9. Windows ARM64

State: `NATIVE PLATFORM / NEEDS DEVICE LAB`.

If supported by the selected Windows edition/build, native VPN capability should be preferred; certify on actual ARM64 hardware and confirm PVNetwork app/provisioning architecture.

## 10. Credential storage

### Windows

Use Windows native credentials/certificate stores/profile references where supported.

### Linux

Audit pppd options, process command line, NetworkManager secret flags/keyring, TLS private/client cert files and temporary config.

### Third-party mobile/macOS

Require platform secure store/Keychain/Keystore references and no plaintext logs/backups.

## 11. Strict execution table

| Client target | Backend | Install | Provision | Connect | Cert validation | Crypto binding | Proxy | Update | Cleanup |
|---|---|---:|---:|---:|---:|---:|---:|---:|---:|
| Windows 11 | native SSTP | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Windows 10 selected build | native SSTP | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Ubuntu/Debian | sstp-client + pppd | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Fedora-family | sstp-client + pppd | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| macOS selected third-party | unselected | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| iOS/iPadOS selected third-party | unselected | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Android selected engine/app | unselected | TODO | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO entries are external execution gates.
