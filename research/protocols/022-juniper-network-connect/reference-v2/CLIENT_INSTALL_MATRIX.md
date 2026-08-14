# Juniper Network Connect — Client Install Matrix

Reviewed: 2026-08-14 UTC

## Proprietary vendor client

| Platform | Vendor Network Connect state |
|---|---|
| Windows | RETIRED: Ivanti documents Network Connect client unsupported from ICS 9.1R2 onward |
| macOS | EOL: Ivanti documents Network Connect support EOL from 8.3R1 onward |
| Linux | historical proprietary `ncsvc` lineage only; no current vendor client baseline selected |
| iOS/Android | no current standalone Network Connect client selected; N/A-LEGACY |

Official EOL evidence: https://help.ivanti.com/ps/help/en_us/ics/9.1rx/spg-9.1r18/client_env_contents.htm

## Current open-compatible client

OpenConnect v9.21 `--protocol=nc`, LGPL-2.1, is the selected current reusable engine reference. OpenConnect is cross-platform and its frontend/package matrix is separately researched under `research/upstreams/openconnect-family/`.

Current package availability is not equivalent to certification against every historical Juniper/Pulse appliance.
