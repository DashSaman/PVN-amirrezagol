# Palo Alto GlobalProtect — Client Install Matrix

Review date: 2026-08-14 UTC

Primary proprietary client reference family: **GlobalProtect App 6.3** with official platform compatibility matrix and platform-specific release streams.

Selected desktop reference baseline for this dossier: **6.3.3-h11 (6.3.3-c1016)** for Windows/macOS because Palo Alto's official release page marks it `Preferred`. Newer 6.3.3-h13 maintenance documentation exists, so implementation freeze must always refresh exact preferred/current status instead of assuming h11 is forever latest.

| Platform | Proprietary vendor path | Boundary |
|---|---|---|
| Windows | GlobalProtect app 6.3 supported Windows matrix | proprietary package; exact OS/build support vendor-version-specific |
| macOS | GlobalProtect app 6.3 supported macOS matrix | proprietary package; system-extension/MDM/privacy controls version-specific |
| Linux x86_64 | official GlobalProtect Linux package/support matrix | proprietary; supported distro/UI matrix must match release |
| Linux ARM64 | official Linux CLI support beginning in the documented 6.2.6+ line; exact 6.3 feature parity is matrix-specific | proprietary; do not infer GUI parity |
| iOS/iPadOS | separate Palo Alto mobile app/release stream | proprietary store/mobile product |
| Android | separate Palo Alto mobile app/release stream | proprietary store/mobile product |
| Open platforms | OpenConnect v9.21 `--protocol=gp` where the platform frontend/package exposes it | LGPL compatible client; not Palo Alto app parity |

Install availability does not prove HIP/posture, embedded browser/SAML, split-tunnel, device-management or every gateway-version combination.
