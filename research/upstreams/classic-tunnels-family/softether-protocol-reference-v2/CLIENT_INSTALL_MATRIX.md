# SoftEther VPN Protocol — Client Installation Matrix

Review date: 2026-08-14

Entry: 013 SoftEther VPN Protocol.

This matrix covers **native SoftEther clients**, not generic OS clients using the server's SSTP/L2TP/OpenVPN compatibility modes.

## 1. Windows SoftEther VPN Client

State: `PRIMARY NATIVE CLIENT / NEEDS-LAB`.

Use the exact selected SoftEther VPN Client release/source corresponding to the certified server family.

Installation/certification must record:

- installer/source provenance and signature/hash;
- architecture (x64/ARM64/etc.) actually supported by selected release;
- VPN Client service/daemon;
- virtual network adapter driver/component;
- Client Manager/vpncmd/client management surface;
- server certificate/trust behavior;
- account/profile storage;
- update/rollback/uninstall and virtual-adapter cleanup.

## 2. Linux native client

State: `SOURCE/BUILD CAPABILITY MUST BE VERIFIED / NEEDS-LAB`.

The canonical source tree contains client-side code and command-line/service components, but exact current packaged/native-client support and virtual-adapter integration must be proven on the selected distribution/release.

Do not infer polished Linux client support solely from the presence of `Client.c` or build targets.

Required proof:

- exact binary/build target;
- virtual adapter/TUN/TAP integration;
- service privilege;
- profile/credential storage;
- route/DNS handling;
- native protocol interop with selected server;
- update/uninstall.

## 3. macOS

State: `VERIFY SELECTED RELEASE / NO ASSUMED NATIVE PRODUCT PATH`.

Do not infer native SoftEther client availability from Unix portability or from Apple's L2TP/IKEv2 APIs. If a current native client build is selected, review signing/notarization, network extension/system networking architecture and secure storage separately.

## 4. iOS / iPadOS

State: `NO NATIVE SOFTETHER CLIENT CLAIM`.

SoftEther Server compatibility modes may allow Apple clients through other protocols, but that is not entry-013 native SoftEther protocol evidence.

## 5. Android / Android TV

State: `NO NATIVE SOFTETHER CLIENT CLAIM`.

Do not count L2TP/OpenVPN/SSTP-compatible Android apps as native SoftEther protocol clients. A separate native engine would require source/license/VPNService/device/store review.

## 6. VPN Bridge / infrastructure peer

State: `CANONICAL NATIVE ROLE / NEEDS-LAB`.

SoftEther VPN Bridge can participate in native SoftEther sessions for site/Layer-2 extension. Treat its install/network privileges/UI as infrastructure peer evidence, not consumer client UX.

## 7. Management tools are not VPN clients

`vpncmd`, Server Manager and Client Manager administration/control channels are not themselves proof of native VPN data-plane support.

## 8. Account/profile storage

For every native client platform record:

- server endpoint/listener;
- Virtual Hub;
- user/auth method;
- certificate/trust reference;
- connection count/transport options;
- virtual adapter binding;
- routes/DNS/network mode;
- secure credential ownership.

Do not store passwords/private keys in ordinary product JSON/logs.

## 9. Strict execution table

| Client target | Native client install | Virtual adapter | Native session | Auth/TLS | Routes/DNS | Update | Uninstall cleanup |
|---|---:|---:|---:|---:|---:|---:|---:|
| Windows selected release | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| Linux selected distro/build | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| SoftEther VPN Bridge peer | TODO | TODO | TODO | TODO | TODO | TODO | TODO |
| macOS selected native build, if any | TODO | TODO | TODO | TODO | TODO | TODO | TODO |

All TODO values are external execution gates. iOS/iPadOS/Android/TV are intentionally not claimed as native entry-013 clients.
