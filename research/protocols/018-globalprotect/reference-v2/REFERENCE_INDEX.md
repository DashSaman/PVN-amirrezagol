# Palo Alto GlobalProtect — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14 UTC

Entry: **018 — Palo Alto GlobalProtect**.

## Proprietary vendor references / selected baselines

- PAN-OS **12.1** current admin-help baseline for `Network > GlobalProtect > Portals` and `Network > GlobalProtect > Gateways`.
- Palo Alto official GlobalProtect setup documentation for interfaces/zones/certificates and portal/gateway infrastructure.
- GlobalProtect App **6.3** compatibility/feature matrix.
- Selected Windows/macOS preferred reference baseline: **6.3.3-h11 (6.3.3-c1016)** because its official release page is marked `Preferred`; official 6.3.3-h13 maintenance pages demonstrate continuing activity and require implementation-time release refresh.
- Palo Alto GlobalProtect licensing documentation: NGFW basic Windows/macOS use and advanced/mobile/Linux/HIP/etc. license boundaries are product/subscription specific; Prisma Access has a distinct license boundary.
- Palo Alto GlobalProtect cryptography/tunnel documentation: SSL/TLS control plus SSL VPN or IPsec/ESP data mode, including current IPsec-only behavior where supported.
- Palo Alto GlobalProtect 6.3 third-party open-source software listing. This is a dependency/license disclosure and **does not make GlobalProtect open source**.

Palo Alto headend/client source code: `N/A-PUBLIC-SOURCE / PROPRIETARY`. No source hash or open-source license is fabricated.

## Public compatible source

- OpenConnect **v9.21** — canonical GitLab tag commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1; existing repository freeze under `research/upstreams/openconnect-family/SOURCE_PIN.md`.
- `research/upstreams/openconnect-family/VENDOR_COMPATIBILITY_MATRIX.md`
- `research/upstreams/openconnect-family/API_LIFETIME_AND_CALLBACKS.md`
- `research/upstreams/openconnect-family/DEPENDENCIES_AND_LGPL.md`
- `research/upstreams/openconnect-family/ISSUE_MR_FIX_MATRIX.md`
- `research/upstreams/openconnect-family/SECURITY_AND_ADVISORIES.md`
- `research/upstreams/openconnect-family/SUPPORT_REUSE_DECISIONS.md`

OpenConnect GP mode is a compatible public client and a reuse candidate through the libopenconnect API. Unmerged/current MRs or issues are activity/uncertainty evidence, not promoted supported capability.

## Mandatory V2 files

`SERVER_IMPLEMENTATIONS.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `SERVER_UI_AND_MENUS.md`, `CLIENT_INSTALL_MATRIX.md`, `CLIENT_UI_AND_MENUS.md`, `CRYPTOGRAPHY.md`, `DATA_PATH_AND_WIRE_FLOW.md`, `PORTS_TRANSPORTS_AND_HANDSHAKE.md`, `DEPLOYMENT_TOPOLOGIES.md`, `REFERENCE_INDEX.md`.

Reuse decision: **high-priority proprietary vendor compatibility target; Palo Alto stack reference-only, OpenConnect GP a separately licensed client-engine candidate.**
