# OpenVPN Family — Pinned Source Revisions

Research date baseline: 2026-08-14. Reverify before implementation or release.

## OpenVPN 3
- Repository: `OpenVPN/openvpn3`
- Reviewed branch: `master`
- Pinned SHA: `1fd271caefc9a71406afdc2ff2460999dcfdb234`
- Complete recursive tree reference: `https://api.github.com/repos/OpenVPN/openvpn3/git/trees/1fd271caefc9a71406afdc2ff2460999dcfdb234?recursive=1`
- Root contents reference: `https://api.github.com/repos/OpenVPN/openvpn3/contents?ref=1fd271caefc9a71406afdc2ff2460999dcfdb234`
- Language mix at review: overwhelmingly C++, plus C, CMake and small supporting languages.
- License file: `LICENSE.md`; dual choice stated as AGPL-3.0-only or MPL-2.0, with the project’s documented OpenSSL permission for the AGPL path.

## OpenVPN GUI for Windows
- Repository: `OpenVPN/openvpn-gui`
- Reviewed branch: `master`
- Pinned SHA: `7295bdc8739a007d099aa590be678c756d02def4`
- Complete recursive tree reference: `https://api.github.com/repos/OpenVPN/openvpn-gui/git/trees/7295bdc8739a007d099aa590be678c756d02def4?recursive=1`
- Root contents reference: `https://api.github.com/repos/OpenVPN/openvpn-gui/contents?ref=7295bdc8739a007d099aa590be678c756d02def4`
- Main language: C; small C++/CMake/Make/M4/Shell components.
- Important currently reviewed files: `tray.c`, `registry.c`, `options.*`, `openvpn.*`, `openvpn_config.*`, localization/resources and build/change files.
- `tray.c` headers state GPL v2 or later. Exact repository-wide licensing must remain tied to upstream notices/COPYING when reuse is considered.

## OpenVPN for Android / ics-openvpn
- Repository: `schwabe/ics-openvpn`
- Reviewed branch: `master`
- Pinned SHA: `ede0aa0b334b47941407599fef3d76da8b933edf`
- Complete recursive tree reference: `https://api.github.com/repos/schwabe/ics-openvpn/git/trees/ede0aa0b334b47941407599fef3d76da8b933edf?recursive=1`
- Root contents reference: `https://api.github.com/repos/schwabe/ics-openvpn/contents?ref=ede0aa0b334b47941407599fef3d76da8b933edf`
- Main languages include C, Java, Kotlin and C++ plus bundled/build tooling.
- License/documentation reference: `README.md`, `doc/LICENSE.txt`, per-file notices. README explicitly warns derivative app builders about GPL source-publication obligations and says the project is an app, not a library intended for other apps.
- Important reviewed paths: `main/src/main/java/de/blinkt/openvpn/VpnProfile.java`, `LaunchVPN.java`, `core/`, `api/`, Android resources, managed restrictions and build files.

## Tunnelblick
- Repository: `Tunnelblick/Tunnelblick`
- Reviewed branch: `main`
- Pinned SHA: `46db6d5dd490379f3da6acc1253ed8d182614f96`
- Complete recursive tree reference: `https://api.github.com/repos/Tunnelblick/Tunnelblick/git/trees/46db6d5dd490379f3da6acc1253ed8d182614f96?recursive=1`
- Root contents reference: `https://api.github.com/repos/Tunnelblick/Tunnelblick/contents?ref=46db6d5dd490379f3da6acc1253ed8d182614f96`
- Main language: Objective-C, with substantial Shell and supporting C/HTML/AppleScript.
- Important top-level areas observed include `tunnelblick/`, `third_party/`, `vendor/`, OpenVPN-related bundled/reference material, tools/scripts, build docs and release notes.
- License classification currently treated as GPL-family/open-source reference; exact notices and third-party subdirectory licenses must be audited path by path before copying anything.

## Pritunl Client
- Canonical repository after redirect: `pritunl/pritunl-client`
- Previous/redirected naming encountered: `pritunl/pritunl-client-electron`
- Reviewed branch: `master`
- Pinned SHA: `9c6a0823abb4edcc1ba913a9fdac0d8323b6cc30`
- Complete recursive tree reference: `https://api.github.com/repos/pritunl/pritunl-client/git/trees/9c6a0823abb4edcc1ba913a9fdac0d8323b6cc30?recursive=1`
- License file: `LICENSE`
- Reuse classification: **REFERENCE-ONLY / DO-NOT-COPY for a commercial PVNetwork build unless separately licensed**. The pinned license states non-commercial use only and says source/binary products cannot be resold or distributed, and modified source cannot be distributed.

## OpenVPN Connect
OpenVPN Connect is used here as the official product/behavior reference. Do not assume the complete application source/UI is available merely because OpenVPN 3 is public. Public official documentation is the primary source for its current profile/import/settings/UI behavior; OpenVPN 3 is analyzed separately as the reusable core candidate.

## Source-tree preservation rule
The recursive-tree URLs above are the authoritative complete file-list references for the pinned revisions. Do not copy every file into PVNetwork simply to preserve a snapshot. If a future engineering/legal decision approves vendoring a component, preserve license/copyright notices and create a deliberate pinned vendor import with modification records.