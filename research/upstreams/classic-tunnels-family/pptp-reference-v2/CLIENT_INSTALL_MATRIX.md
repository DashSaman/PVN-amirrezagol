# PPTP — Client Installation Matrix

Review date: 2026-08-14

Entry: 012 PPTP.

PPTP client support is retained only for explicit legacy interoperability. Do not add it to recommended/default protocol selection.

## 1. Windows 10 / Windows 11 native client

State: `LEGACY NATIVE / NEEDS-LAB`.

Current Windows VpnClient tooling still documents `Pptp` as a tunnel type.

PVNetwork should use supported native Windows profile/provisioning/status mechanisms rather than bundle a separate PPTP engine.

Required certification:

- exact Windows builds retained in product scope;
- profile provision/remove;
- auth/MPPE policy;
- TCP1723 + GRE47;
- direct and NAT paths;
- split/full routes/DNS;
- reconnect/sleep/network change;
- update/profile cleanup.

## 2. Windows ARM64

State: `NATIVE-PLATFORM-CAPABILITY / NEEDS DEVICE LAB`.

If the selected Windows release exposes native PPTP on ARM64, prefer that system backend and certify on actual hardware. Do not infer from x64 alone.

## 3. Android

State: `LEGACY / DEVICE+OEM+VERSION DEPENDENT / NEEDS-LAB`.

Current Android developer documentation characterizes built-in PPTP/L2TP-IPsec as legacy VPN. Actual Settings/profile availability varies by Android/OEM/version and must be tested rather than assumed.

PVNetwork policy:

- do not make PPTP a normal Android protocol choice;
- use only if a supported device still exposes a compatible native path and a temporary legacy requirement exists;
- otherwise do not build a new PPTP engine just to preserve obsolete compatibility.

## 4. macOS

State: `NO CURRENT NATIVE PPTP SUPPORT`.

Apple removed native PPTP support starting with macOS Sierra. Do not present PPTP as a native macOS protocol.

A third-party legacy client would require independent source/vendor/security/notarization review and would still be unsuitable for new deployments.

## 5. iOS / iPadOS

State: `NO CURRENT NATIVE PPTP SUPPORT`.

Apple removed native PPTP starting with iOS 10. Do not infer support from generic NetworkExtension APIs.

## 6. Linux — pptp client + pppd

State: `HISTORICAL OPEN-SOURCE PATH / EXACT SOURCE+MAINTENANCE PIN REQUIRED / NEEDS-LAB`.

Traditional composition:

- PPTP client daemon implementing control/GRE;
- pppd for PPP/auth/MPPE/NCP;
- routes/DNS scripts or NetworkManager frontend;
- kernel GRE/conntrack/helper behavior.

Before any source freeze/lab, materialize canonical repository, immutable release/commit, license, maintenance status and distro patches.

Do not add an abandoned client as a production dependency merely for feature-count parity.

## 7. Linux NetworkManager PPTP frontend

State: `COMPONENT-SPECIFIC / NEEDS PIN+LAB`.

Where a NetworkManager PPTP plugin still exists in a selected distro, treat it as a separate source/license/version from the underlying pptp client/pppd.

Map:

- gateway;
- username/domain;
- PPP auth toggles;
- MPPE/security options;
- secret/keyring behavior;
- routes/DNS;
- generated backend configuration.

## 8. MikroTik RouterOS client

State: `LEGACY BUILT-IN / SECURITY WARNING / NEEDS-LAB`.

RouterOS includes PPTP client capability in current documentation. Use only for exact legacy interop and preserve the vendor security warning.

## 9. FreeBSD / other Unix

State: `UNVERIFIED / HISTORICAL`.

Requires exact current package/source evidence. Do not infer from PPP/GRE support.

## 10. ChromeOS

State: `NO CURRENT CLAIM`.

Do not infer from Android history. Add only if current Google enterprise/device documentation and runtime evidence establish support.

## 11. Credential storage

### Windows

Use native VPN/credential store where supported.

### Android native legacy

System owns native profile credentials; PVNetwork should not duplicate plaintext secrets.

### Linux

Audit pppd secret files, NetworkManager keyring/secret flags, command-line/process exposure and temp config.

### RouterOS

Use platform-owned secrets/config; export/backup security must be reviewed separately.

## 12. Strict execution table

| Client | Backend | Provision | Connect | GRE/NAT | Auth/MPPE | Routes/DNS | Update | Cleanup |
|---|---|---:|---:|---:|---:|---:|---:|---:|
| Windows 11 | native PPTP | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected Windows 10 | native PPTP | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| selected Android/OEM legacy | native legacy | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux isolated legacy lab | pptp client + pppd | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| RouterOS selected release | built-in client | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO values are external execution gates. macOS/iOS are intentionally not listed as native runnable targets.
