# Juniper Network Connect — Server / Gateway Matrix

Reviewed: 2026-08-14 UTC

| Target | V2 treatment |
|---|---|
| Historical Juniper IVE/SA SSL VPN gateways | ORIGINAL PROPRIETARY NC SERVER FAMILY / LEGACY |
| Pulse-era Connect Secure gateways with NC enabled | LEGACY COMPATIBILITY; exact version/config required |
| ICS 9.1-era environment | vendor client support already retired; server-side NC exposure must be checked per appliance/config |
| Current ICS 25.1.x | `UNVERIFIED-CURRENT-NC-WIRE`; feature lineage alone is not protocol proof |
| Generic Linux/Windows server | N/A; no selected open NC server |
| OCI/Kubernetes | N/A; no canonical NC server deployment |

This matrix intentionally distinguishes **gateway lineage** from **current oNCP wire support**.
