# 022 — Juniper Network Connect

V1 status: `COMPLETE-RESEARCH-v1`.

V2 status: `COMPLETE-REFERENCE-v2` as of 2026-08-14 UTC.

Juniper Network Connect/oNCP is a **legacy/retired proprietary client protocol family**, distinct from Pulse/IF-T/TLS. Current vendor client support is not assumed: Ivanti's published support matrix states Network Connect client is unsupported on Windows from ICS 9.1R2 onward and EOL on macOS from 8.3R1 onward.

OpenConnect v9.21 `--protocol=nc` is the selected current open-compatible implementation reference. Current OpenConnect documentation says NC support is nearly complete but still experimental in the multi-protocol client, lacks IPv6, and retains browser/TNCC and gateway-version quirks.

Current ICS documentation saying VPN Tunneling was “formerly called Network Connect” is product/feature lineage, not evidence that ICS 25.1.x still exposes the legacy NC wire protocol. That current-wire claim remains explicitly unverified rather than fabricated.
