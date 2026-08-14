# Ivanti Connect Secure — Server / Headend Ecosystem

Reviewed: 2026-08-14 UTC

## Canonical proprietary headend

**Ivanti Connect Secure (ICS)** is the maintained proprietary appliance/virtual-appliance family formerly known as Pulse Connect Secure. Vendor source code is not public; the correct source/reuse state is `N/A-PUBLIC-SOURCE / PROPRIETARY`.

Current release/activity anchor: **ICS 25.1.2.1 build 15773**. Ivanti's current release notes describe security enhancements and bug fixes and recommend upgrading to this version.

Official current activity: https://help.ivanti.com/ps/help/en_US/ICS/25.1.x/25.1.2.1/rn/whatsnew.htm

ICS exposes multiple separately owned access features including VPN Tunneling, Web access, Secure Application Manager, terminal services and IKEv2. Entry 021's Pulse-compatible VPN tunneling must not be conflated with the appliance's separate IKEv2 feature.

## Compatible public client ecosystem

OpenConnect v9.21 supports `--protocol=pulse`; repository-wide exact pin `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1. This is a separately licensed compatible client, not an ICS server implementation and not evidence that Ivanti's proprietary software is open source.
