# Juniper Network Connect — COMPLETE-REFERENCE-v2 Index

Reviewed: 2026-08-14 UTC

Entry: **022 — Juniper Network Connect / oNCP**.

Reuse decision: **LEGACY / RETIRED VENDOR CLIENT / OPENCONNECT-COMPATIBILITY ONLY**.

## Vendor lifecycle/reference evidence

- Ivanti ICS 9.1R18 supported-platform guide: Network Connect client unsupported from 9.1R2 onward on Windows; macOS Network Connect EOL from 8.3R1: https://help.ivanti.com/ps/help/en_us/ics/9.1rx/spg-9.1r18/client_env_contents.htm
- Current ICS VPN Tunneling docs describe the feature lineage as “formerly called Network Connect”: https://help.ivanti.com/ps/help/en_US/ICS/22.x/vtcg/landingpage.htm
- Current ICS 25.1.x product evidence is maintained under entry 021 but is **not** used as proof of current NC wire support.

Juniper/Pulse/Ivanti legacy NC client/gateway source: `N/A-PUBLIC-SOURCE / PROPRIETARY`.

## Current open source/release/activity evidence

- OpenConnect **v9.21**, canonical exact pin `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1; released 2026-06-16 in repository source freeze.
- NC protocol behavior/limitations: https://www.infradead.org/openconnect/juniper.html
- current manual/protocol selector: https://www.infradead.org/openconnect/manual.html
- current changelog includes historical NC/oNCP fixes and confirms current active maintenance: https://www.infradead.org/openconnect/changelog.html
- shared dependency/API/frontend/security/license evidence: `research/upstreams/openconnect-family/`.

Key uncertainty: OpenConnect's protocol page records Junos/Ivanti servers continuing to expose NC as of its 2023 observation; no current official evidence found here upgrades that to an ICS 25.1.x support guarantee.

## Mandatory V2 files

`SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_INDEX.md`.
