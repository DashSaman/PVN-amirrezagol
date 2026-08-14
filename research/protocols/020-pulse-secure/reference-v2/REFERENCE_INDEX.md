# 020 — Pulse Secure — COMPLETE-REFERENCE-v2

Reviewed: 2026-08-14 UTC

## Scope boundary

Entry 020 preserves the historic **Pulse Secure / Pulse Connect Secure** product and wire-protocol identity. Current vendor documentation uses **Ivanti Connect Secure (ICS)** for the gateway and **Ivanti Secure Access Client (ISAC)** for the rebranded Pulse Secure Client. Entry 021 remains the current Ivanti-product entry and must not be collapsed into this historical compatibility entry.

The gateway/client are proprietary products. No public source commit or open-source license is claimed for them. OpenConnect is a separate compatible open-source client implementation and is not evidence that the vendor product is open source.

## Current vendor anchors

- Ivanti Connect Secure 22.8R2.3: build 18655; release-notes revision dated February 2026. Official release notes: https://help.ivanti.com/ps/help/en_US/ICS/22.x/22.8R2.2/rn/whatsnew.htm
- ISAC desktop current guide family covers 22.1R1–22.8R7; current supported-platform table includes ISAC 22.8R7 build 48847 and compatibility with ICS 22.8R2.4 / 25.1.x. https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-22.X/server-platform-compatibility.htm
- ISAC mobile 22.8.7 supports current iOS/Android/ChromeOS matrices and ICS 22.8R2.4/22.8R2.3/25.1.x gateway families. https://help.ivanti.com/ps/help/en_US/ISAC/22.X/spg-mobile-22.8.7/client_compatibility.htm
- Pulse Secure Client was rebranded as Ivanti Secure Access Client from the 9.1R16/22.x transition; Classic and New UI are both documented. https://help.ivanti.com/ps/help/en_US/ISAC/22.X/ag-22.X/using_ui.htm

## Open-source compatibility anchor

OpenConnect supports `--protocol=pulse` as experimental Pulse/Ivanti Connect Secure support. The project documents Pulse support since OpenConnect 8.04, IF-T/TLS + EAP/EAP-TTLS over TCP and UDP ESP, IPv6 support, and explicit limitations including incomplete Pulse authentication options and no Pulse Host Checker/TNCC support. Canonical project: https://gitlab.com/openconnect/openconnect ; documentation: https://www.infradead.org/openconnect/pulse.html . Repository-wide shared pin used by adjacent enterprise entries: OpenConnect v9.21 / commit `8b702bf2dbaf11302ed98629214b1df5d50a12aa`, LGPL-2.1.

## Exact 16-gate reconciliation

| Gate | Result | Evidence / boundary |
|---|---|---|
| 1 Server ecosystem | PASS | Proprietary Pulse Connect Secure -> Ivanti Connect Secure lineage; current ICS gateway is the canonical maintained vendor implementation. OpenConnect `ocserv` is **not** a Pulse server and is not substituted. |
| 2 Installers/deployment projects | PASS | Vendor hardware/virtual appliance deployment is authoritative; current 22.8R2 platform docs cover ISA6000/ISA8000 and VMware-qualified VA paths. No community installer is promoted for proprietary ICS. |
| 3 Server install matrix | PASS | ICS 22.8R2 supports ISA6000/ISA8000 and qualified VMware VA; other cloud/platform support is release-specific. Treat generic Linux/container/Kubernetes rows as N/A for the proprietary appliance unless vendor documentation explicitly adds them. |
| 4 Server UI/menu map | PASS | Current ICS admin docs expose System > Configuration, Licensing, Security, Log/Monitoring, Users > User Roles, Users > Resource Policies > VPN Tunneling, authentication realms, clustering, dashboard/reports, admin roles, maintenance and troubleshooting. |
| 5 Client install matrix | PASS | ISAC desktop supports current Windows/macOS/Linux matrices; mobile has separate iOS/Android/ChromeOS Store-distributed clients. Android TV is not documented as a qualified target and is not inferred. |
| 6 Client UI/menu map | PASS | Vendor guide documents Classic/New UI, add/edit connection, Name + Server URL, connection list and launch/import tooling. Mobile is a separate UI/product cadence. No undocumented screen is invented. |
| 7 Cryptography | PASS | Pulse data transport is TLS/IF-T with EAP/EAP-TTLS authentication in OpenConnect's documented interoperable path and UDP ESP where negotiated. Vendor TLS/cipher policy is gateway-version controlled; current ICS exposes granular inbound/outbound OpenSSL cipher configuration. |
| 8 Data path/wire flow | PASS | HTTPS/authentication -> cookie/session -> Pulse IF-T/TLS tunnel; optional UDP ESP data path; gateway decapsulates and applies VPN resource policy/routing/DNS. IPv4/IPv6 are documented. |
| 9 Ports/transports/handshake | PASS | HTTPS/TLS control/authentication and tunnel establishment; configurable gateway URL/port; IF-T/TLS TCP path plus optional UDP ESP. Do not hard-code a non-authoritative ESP UDP port. |
| 10 Deployment topologies | PASS | Remote-access gateway, split/full tunnel, clustered/HA gateway and virtual/hardware appliance patterns are documented. This is not a generic mesh protocol. |
| 11 Source/license/activity pins | PASS | Vendor gateway/client proprietary and release-pinned; OpenConnect separately pinned to v9.21/`8b702...`, LGPL-2.1. |
| 12 Supply-chain/security | PASS | Vendor downloads require Ivanti portal/support channels; proprietary packages must be signature/vendor-source verified. OpenConnect source/build chain is separate. Current ICS release notes/security updates are mandatory review inputs. |
| 13 Upgrade/uninstall/rollback | PASS | ICS 22.8R2.x has explicit tested upgrade/migration paths and SecureBoot rollback constraints; ISAC has vendor-managed desktop/mobile update paths. Current release notes supersede generic assumptions. |
| 14 Differences/uncertainties | PASS | Historic Pulse naming vs current Ivanti naming preserved; Pulse protocol differs substantially from Juniper `nc`; OpenConnect Pulse support is experimental and lacks Pulse Host Checker/TNCC and some auth methods. |
| 15 Reference index | PASS | This file is the index and links the authoritative anchors; supporting topic files in this directory keep server/client/wire/lifecycle evidence recoverable. |
| 16 Handoff state | PASS | Tracker/state/handoff are updated with entry 020 completion and entry 021 as the next unit. |

## Reuse decision

- **Vendor Pulse/ICS/ISAC code:** reference/interop target only; proprietary.
- **OpenConnect Pulse mode:** candidate compatibility adapter under LGPL-2.1 obligations, but feature gaps must be capability-gated and must never be marketed as equivalent to the official client.
- **PVNetwork architecture:** model this as an enterprise SSL-VPN compatibility capability behind an adapter. Keep vendor-specific authentication, Host Checker/posture and policy behavior outside a generic TLS tunnel abstraction.

## Research completion vs certification

No real appliance login, customer portal download, device interoperability, Store submission or production test receipt is claimed. Those are implementation/certification work, not hidden V2 research gates.