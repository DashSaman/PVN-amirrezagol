# Juniper Network Connect — Deployment Topologies

Reviewed: 2026-08-14 UTC

Covered legacy compatibility patterns:

1. **Historical Juniper SSL VPN remote access** — Network Connect client to IVE/SA gateway.
2. **Pulse/ICS legacy NC endpoint** — only where the exact appliance/version still exposes NC and administrator has not disabled it.
3. **OpenConnect legacy compatibility** — OpenConnect v9.21 `--protocol=nc` against an explicitly verified NC-capable gateway.
4. **TLS-only fallback** — HTTPS/oNCP data path when ESP acceleration cannot be established.
5. **HTTPS + ESP** — accelerated legacy data path where negotiated.
6. **Host Checker/TNCC-gated access** — external/helper posture flow required before tunnel authorization.
7. **Browser/external-auth sequence** — customized HTML/JavaScript authentication where OpenConnect's native form parser is insufficient.
8. **Migration** — move from retired NC endpoint/client dependence to maintained ISAC/Pulse or another supported VPN mode rather than creating new NC deployments.

IPv6 is not a supported NC capability in the OpenConnect implementation and must not be inherited from Pulse mode.
