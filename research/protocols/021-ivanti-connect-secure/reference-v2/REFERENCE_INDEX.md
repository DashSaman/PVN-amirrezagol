# Ivanti Connect Secure — COMPLETE-REFERENCE-v2 Index

Reviewed: 2026-08-14 UTC

Entry: **021 — Ivanti Connect Secure**.

## Current proprietary server baseline

- ICS **25.1.2.1 build 15773** — current release/activity anchor; security enhancements + bug fixes; vendor recommends upgrade: https://help.ivanti.com/ps/help/en_US/ICS/25.1.x/25.1.2.1/rn/whatsnew.htm
- tested upgrade/migration: https://help.ivanti.com/ps/help/en_US/ICS/25.1.x/25.1.2.1/rn/upgrade_and_migration.htm
- current roles/admin model: https://help.ivanti.com/ps/help/en_US/ICS/25.1.x/ag/user_roles.htm
- current VPN tunneling configuration: https://help.ivanti.com/ps/help/en_US/ICS/vNow/vtcg/configuring-vpn-tunneling.htm

Ivanti appliance/server source: `N/A-PUBLIC-SOURCE / PROPRIETARY`. No source hash/open-source license is fabricated.

## Current proprietary client baselines

- ISAC Desktop **22.8R7 build 48847**: https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-22.X/landingpage.htm
- desktop platform matrix: https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-22.X/platform-and-browser-compatibility.htm
- desktop server qualification: https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-22.X/server-platform-compatibility.htm
- reviewed mobile baseline **22.8.7**: https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-mobile-22.8.7/landingpage.htm

Important uncertainty: ISAC 22.8R7's published compatibility table qualifies ICS 25.x through **25.1.1.1**. ICS 25.1.2.1 is newer. No 22.8R7 × 25.1.2.1 qualification result is invented.

## Public compatible source

- OpenConnect **v9.21** — canonical repo-wide pin `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.
- Pulse protocol: https://www.infradead.org/openconnect/pulse.html
- shared source/API/dependency/security/frontend evidence: `research/upstreams/openconnect-family/`.

OpenConnect Pulse support remains capability-gated; it does not implement all vendor authentication methods or Pulse Host Checker/TNCC.

## Mandatory V2 files

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_INDEX.md`

Reuse decision: **CURRENT PROPRIETARY ENTERPRISE HEADEND / VENDOR CLIENT REFERENCE / OPENCONNECT PULSE CAPABILITY-GATED CLIENT REUSE**.
