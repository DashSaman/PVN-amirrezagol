# Cisco AnyConnect / Secure Client — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14 UTC

Entry: **016 — Cisco AnyConnect**.

## Proprietary Cisco references

- `research/protocols/016-cisco-anyconnect/PROPRIETARY_REFERENCE_CURRENT.md`
- `research/protocols/016-cisco-anyconnect/CISCO_UI_STORAGE_DIAGNOSTICS_MAP.md`
- Cisco Secure Client 5.1 release notes; reviewed current recommended desktop/VPN-core release **5.1.18.314** (June 25, 2026 update).
- Cisco Secure Client 5.x Features, Licenses and OSs guide, updated June 25, 2026.
- Cisco Secure Client Administrator Guide 5.1 deployment documentation, updated June 30, 2026.
- Cisco Secure Firewall ASA VPN CLI Configuration Guide **9.24**, AnyConnect VPN Client Connections.

Cisco code/source/build internals are `N/A-PUBLIC-SOURCE / PROPRIETARY`. Cisco software/assets are not reuse-approved.

## Public compatible source references

- OpenConnect v9.21 — `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1, canonical GitLab tag/release; release date 2026-06-16 in repository evidence.
- ocserv 1.5.0 — `49f9956eeeffd613e4bcac3f6450c682ec21e75a`, GPLv2+, canonical signed GitLab tag/release, released 2026-06-07. Release includes security fixes for worker cookie parsing and DTLS MTU validation; do not deploy an older unreviewed build by default.
- shared family evidence: `research/upstreams/openconnect-family/` including source pin, dependencies/license, API lifecycle, platform/config, frontends, issues, security, CI and support decisions.

## Mandatory V2 files

`SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_INDEX.md`.

Reuse decision: **high-priority AnyConnect-compatible target through libopenconnect adapter; Cisco proprietary stack remains reference/certification-only.**
