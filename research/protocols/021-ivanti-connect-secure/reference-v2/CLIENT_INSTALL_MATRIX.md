# Ivanti Connect Secure — Client Install Matrix

Reviewed: 2026-08-14 UTC

## Desktop

Selected current desktop baseline: **Ivanti Secure Access Client 22.8R7 build 48847**.

Vendor-qualified 22.8R7 families include:

- Windows 11 23H2/24H2/25H2 and Windows Server 2022;
- macOS Sonoma 14.8.3, Sequoia 15.7.3, Tahoe 26.4/26.5-era references;
- Linux Fedora 42/43, Ubuntu 22.04.05/24.04.02, Debian 12/13, RHEL 9/10 and CentOS 9.

Official matrix: https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-22.X/platform-and-browser-compatibility.htm

Deployment models include vendor web/PSAL delivery, enterprise-preconfigured Windows MSI, macOS package/preconfiguration and Linux DEB/RPM/CLI according to platform guide. Exact uninstall/upgrade behavior is platform-specific.

## Mobile

Reviewed mobile baseline: **ISAC Mobile 22.8.7** for iOS/Android/ChromeOS. The vendor explicitly separates mobile Store-distributed clients from desktop clients.

Mobile guide: https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-mobile-22.8.7/landingpage.htm

Mobile release streams continue independently; exact current Store build/ICS qualification remains implementation-time certification, not a hidden V2 gate.

## Open-source compatible path

OpenConnect v9.21 is a separate Pulse-compatible client engine where the selected frontend/platform exposes it. It does not inherit ISAC platform qualification or proprietary Host Checker capability.
