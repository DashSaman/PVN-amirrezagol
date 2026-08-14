# OpenVPN — COMPLETE-REFERENCE-v2 Gate Reconciliation

Reviewed: 2026-08-14 UTC

Scope: research/reference completion only. This does **not** assert PVNetwork implementation, device certification, Store approval, interoperability certification, performance qualification, or production readiness.

Authoritative contract: `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`.

## Exact 16-gate reconciliation

| # | V2 gate | Result | Traceable dossier evidence |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | `SERVER_IMPLEMENTATIONS.md`; Community OpenVPN, Access Server, Pritunl and deployment ecosystem are separated by role/reuse boundary. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | `SERVER_INSTALLERS_AND_PROJECTS.md`; official/package paths plus Angristan, Nyr, PiVPN, Docker and control-plane references, with trust caveats. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | `SERVER_INSTALL_MATRIX.md`; supported/community/unknown boundaries and deployment prerequisites are explicit. |
| 4 | Server panel/UI/menu maps completed | PASS | `SERVER_UI_AND_MENUS.md`; Community/no-panel boundary, Access Server and third-party management surfaces are kept distinct. |
| 5 | Client install matrix completed across relevant OS targets | PASS | `CLIENT_INSTALL_MATRIX.md`; Windows, Android, iOS/iPadOS, macOS and Linux packaging/permission paths are mapped where applicable. |
| 6 | Major client UI/menu maps completed separately | PASS | `CLIENT_UI_AND_MENUS.md`; Connect, OpenVPN GUI, ics-openvpn/OpenVPN for Android, Tunnelblick and other studied clients are not flattened into a fictional generic UI. |
| 7 | Cryptographic design documented from authoritative specifications/source | PASS | `CRYPTOGRAPHY.md`; TLS control channel, data-channel crypto, AEAD, PKI, tls-auth/tls-crypt, replay/rekey and legacy boundaries are documented. |
| 8 | Data path/wire flow documented | PASS | `DATA_PATH_AND_WIRE_FLOW.md`; TUN/TAP, userspace/DCO, control/data paths, routing/DNS and return path are mapped. |
| 9 | Ports/transports/handshake documented | PASS | `PORTS_TRANSPORTS_AND_HANDSHAKE.md`; configurable UDP/TCP transport, conventional 1194 usage, TLS/control sequencing, retry/NAT behavior and product boundaries are explicit. |
| 10 | Deployment topologies documented | PASS | `DEPLOYMENT_TOPOLOGIES.md`; remote-access, site-to-site, split/full tunnel, HA/cloud/container and management/data-plane relationships are covered. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | `REFERENCE_INDEX.md`, V1 upstream dossiers under `research/upstreams/openvpn-family/`, and per-file source sections. Immutable pins exist for the principal reusable/reference client sources; moving product documentation is explicitly identified rather than fabricated as source code. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | `SERVER_INSTALLERS_AND_PROJECTS.md` and `REFERENCE_INDEX.md`; root/network side effects, floating artifacts, remote-script trust, secrets and redistribution risks are called out. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | `SERVER_INSTALLERS_AND_PROJECTS.md`, `SERVER_INSTALL_MATRIX.md`, `CLIENT_INSTALL_MATRIX.md`; lifecycle behavior and unknown/product-specific boundaries are explicit. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | `REFERENCE_INDEX.md` plus all component files; Community vs Access Server vs Connect vs third-party UI/source/license boundaries are explicit. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `REFERENCE_INDEX.md` inventories all required V2 files and upstream evidence. |
| 16 | Latest AGENTS handoff contains exact continuation state | PASS | `AGENTS_HANDOFF_2026-08-14_OPENVPN_V2_COMPLETE.md` records promotion and exact next entry 002 WireGuard. |

## Completion decision

**QUALIFIES: `COMPLETE-REFERENCE-v2`.**

The earlier `REFERENCE_INDEX.md` list of real install receipts, packet captures, interoperability/performance and Store/device evidence is retained as future implementation/certification work, but those are not hidden requirements of the written V2 research contract. They therefore do not block this research/reference completion.

Exact next action: continue Entry 002 — WireGuard under the same 16-gate contract, reusing existing WireGuard evidence only where traceable and filling real V2 gaps without inventing certification receipts.
