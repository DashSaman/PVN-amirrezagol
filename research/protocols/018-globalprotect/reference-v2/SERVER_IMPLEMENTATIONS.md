# Palo Alto GlobalProtect — Server / Headend Ecosystem

Review date: 2026-08-14 UTC

GlobalProtect has two distinct proprietary service roles:

1. **GlobalProtect Portal** — management/configuration distributor. It provides endpoint configuration, gateway information, certificates and app behavior/software policy.
2. **GlobalProtect Gateway** — VPN/security termination point. It authenticates endpoints and terminates SSL/IPsec tunnels while applying routing, security and optional HIP/posture policy.

## Authoritative proprietary headends

- Palo Alto Networks NGFW/PAN-OS GlobalProtect portal and gateway. Current administration reference baseline used by this dossier: PAN-OS 12.1 web help (`Network > GlobalProtect > Portals` and `Network > GlobalProtect > Gateways`).
- Prisma Access GlobalProtect is a vendor-managed cloud headend/deployment family with a separate subscription/management boundary.

Palo Alto server/headend source code is not public. `N/A-PUBLIC-SOURCE / PROPRIETARY` is the correct source-reuse result; vendor documentation and release/security evidence are reference-only.

## Public compatible client ecosystem

OpenConnect v9.21 supports `--protocol=gp` and contains a dedicated GlobalProtect protocol implementation. Exact canonical pin already frozen in repository: `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.

OpenConnect is a separately maintained compatible client, not a Palo Alto server implementation and not proof of proprietary feature parity.
