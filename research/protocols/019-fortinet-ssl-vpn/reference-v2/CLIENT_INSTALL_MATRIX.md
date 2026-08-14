# Fortinet FortiGate SSL VPN — Client Install Matrix

Review date: 2026-08-14 UTC

Primary proprietary client reference: **FortiClient 7.4.7**. Fortinet maintains separate platform packages and feature matrices; FortiClient source is proprietary.

| Platform | FortiClient path / status | Boundary |
|---|---|---|
| Windows x64/ARM | FortiClient 7.4.7 Windows packages; remote-access SSL VPN supported subject to edition/feature matrix | proprietary; exact ARM feature set is limited |
| macOS | FortiClient 7.4.x platform package and remote-access capability | proprietary; exact OS/extension/feature matrix required |
| Linux x86_64 | FortiClient 7.4.7 supports Ubuntu 22.04/24.04, CentOS Stream 9 and RHEL 9 with GNOME | proprietary; official docs explicitly say FortiOS 7.6.3+ no longer provides SSL tunnel mode |
| Linux free VPN-only agent | no new free VPN-only build in 7.4.4–7.4.7; Fortinet directs users to 7.4.3 free VPN-only agent | legacy package stream; do not call 7.4.7 a new free-VPN build |
| iOS / Android | FortiClient mobile remote-access capability is platform/product-stream specific | proprietary mobile product; exact feature parity not assumed |
| Open-source path | OpenConnect v9.21 Fortinet mode where supported by platform/frontend | LGPL-2.1 experimental/partial compatible alternative |

Official references:
- https://docs.fortinet.com/document/forticlient/7.4.7/linux-release-notes/136392/product-integration-and-support
- https://docs.fortinet.com/document/forticlient/7.4.7/administration-guide/459677/remote-access
- https://docs.fortinet.com/document/forticlient/7.4.7/linux-release-notes/745986

Store/device availability and exact vendor-client interoperability remain later certification evidence, not hidden V2 gates.
