# Cisco AnyConnect — Client Install Matrix

Review date: 2026-08-14 UTC

Authoritative proprietary baseline: **Cisco Secure Client 5.1.18.314 / AnyConnect VPN core 5.1.18.314**, recommended 5.1 release in current Cisco notes reviewed 2026-08-14.

| Platform | Cisco packaging / status | Boundary |
|---|---|---|
| Windows x64/ARM64 | predeploy ZIP and webdeploy package families | proprietary Cisco package; current OS matrix in 5.1 feature/release docs |
| macOS | predeploy DMG and webdeploy; MDM/admin-extension constraints apply to upgrade paths | proprietary |
| Linux x86_64 | current DEB/RPM predeploy; webdeploy package | proprietary; Linux script installer discontinued in 5.1.15+ path |
| Linux ARM64 | current Cisco package/webdeploy path with module limitations | proprietary |
| iOS/iPadOS | separate Cisco Secure Client 5 mobile release stream / Network Extension | proprietary App Store/mobile product |
| Android | separate Cisco Secure Client 5 Android release stream | proprietary mobile product |
| UWP | distinct Cisco variant/release stream | do not assume desktop feature parity |
| Open platforms | OpenConnect v9.21 where platform frontend/package supports it | public compatible alternative, not Cisco client binary |

Install availability does not certify every Cisco module, posture flow or headend combination.
