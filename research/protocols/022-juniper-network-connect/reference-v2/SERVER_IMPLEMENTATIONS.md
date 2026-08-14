# Juniper Network Connect — Server / Gateway Ecosystem

Reviewed: 2026-08-14 UTC

## Proprietary lineage

The original Network Connect/oNCP server side lived in Juniper SSL VPN / IVE / SA gateway products and continued through Pulse-era appliances. Product ownership later moved to Pulse Secure/Ivanti.

Current Ivanti Connect Secure documentation describes its `VPN Tunneling` feature as **formerly called Network Connect**, but this is feature lineage. It does not by itself prove current ICS 25.1.x wire-level `nc`/oNCP compatibility.

Historical/current support boundary:

- Ivanti's ICS 9.1R18 supported-platform documentation states the proprietary **Network Connect client is not supported from 9.1R2 onward** on Windows.
- macOS Network Connect support is EOL from 8.3R1 onward.
- OpenConnect's current NC protocol documentation says Junos/Ivanti servers continued to expose NC alongside Pulse as of its documented 2023 observation unless administrators disabled it; this is not promoted into a 2026 ICS 25.1.x guarantee.

Proprietary Juniper/Pulse/Ivanti gateway source is unavailable: `N/A-PUBLIC-SOURCE / PROPRIETARY`.

## Current open-compatible implementation

OpenConnect v9.21 client/library, exact repository pin `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1, supports `--protocol=nc`.

No current open-source Network Connect server implementation is selected.
