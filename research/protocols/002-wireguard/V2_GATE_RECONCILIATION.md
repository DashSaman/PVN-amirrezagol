# Entry 002 — WireGuard — COMPLETE-REFERENCE-v2 Gate Reconciliation

Review date: 2026-08-14 UTC

Scope: second-layer research/reference completion only. This is not an implementation, runtime, Store, device, interoperability, performance, or production-certification claim.

Primary evidence base: `research/upstreams/wireguard-family/` and `research/upstreams/wireguard-family/reference-v2/`.

Governing rule: the exact completion gate is `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. Runtime/device/Store/interoperability receipts are not hidden `COMPLETE-REFERENCE-v2` gates. Earlier WireGuard-family notes that withheld tracker promotion solely for those later certification receipts are superseded for V2 completion by the current contract and `AGENTS.md` §16; the underlying certification gaps remain preserved rather than fabricated.

## Exact 16-gate result

| # | Official V2 gate | Result | Evidence / boundary |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | `reference-v2/SERVER_IMPLEMENTATIONS.md`; Linux kernel WireGuard, `wireguard-go`, official platform families, operational peer/server distinction and separate control planes are mapped. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | `reference-v2/SERVER_INSTALLERS_AND_PROJECTS.md`; official install/tooling paths plus separately-audited third-party management/deployment projects including pinned wg-easy evidence. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | `reference-v2/SERVER_INSTALL_MATRIX.md`; distro/OS/package/source and deployment distinctions are evidence-backed, with documentation evidence kept separate from runtime receipts. |
| 4 | Server panel/UI/menu maps completed | PASS | `reference-v2/SERVER_UI_AND_MENUS.md` plus wg-easy v15.3 security/request-boundary files. WireGuard has no canonical protocol web panel; that absence is explicitly documented and third-party control planes are mapped separately rather than invented as protocol UI. |
| 5 | Client install matrix completed across relevant OS targets | PASS | `reference-v2/CLIENT_INSTALL_MATRIX.md`, Apple entitlement/build provenance files, and parent Windows/Android/Apple dossiers. |
| 6 | Major client UI/menu maps completed separately | PASS | `reference-v2/CLIENT_UI_AND_MENUS.md` plus parent `WINDOWS_CLIENT.md`, `ANDROID_CLIENT.md`, and `APPLE_CLIENT.md`; platform differences are preserved. |
| 7 | Cryptographic design documented from authoritative specifications/source | PASS | `reference-v2/CRYPTOGRAPHY.md`, canonical WireGuard protocol references, and pinned upstream source. |
| 8 | Data path/wire flow documented | PASS | `reference-v2/DATA_PATH_AND_WIRE_FLOW.md`; kernel/userspace/TUN/routing/roaming/return-flow boundaries are covered. |
| 9 | Ports/transports/handshake documented | PASS | `reference-v2/PORTS_TRANSPORTS_AND_HANDSHAKE.md`; UDP transport, Noise_IK-derived handshake, keepalive/endpoint behavior and operational port choice are kept distinct. |
| 10 | Deployment topologies documented | PASS | `reference-v2/DEPLOYMENT_TOPOLOGIES.md`; remote access, routing peer/site-to-site, split/full tunnel and management-plane separation are covered. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | `../SOURCE_REVISIONS.md` pins `wireguard-go@ecfc5a8d54462e18e13c72173e2623d16d8e25a0`, Windows `4e6726c23ae9c5cb58e0c9910f3b7515621d133d`, Android `e7b3a3c118836e112620b1302a8ba1873ad4daac`, Apple `2fec12a6e1f6e3460b6ee483aa00ad29cddadab1`, records canonical `git.zx2c4.com` provenance and component-specific licenses/activity. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | `SERVER_INSTALLERS_AND_PROJECTS.md` and the pinned `WGEASY_V15_3_*` audits cover privilege, auth/request/proxy boundary, dependency/OCI pinning and supply-chain cautions. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | `SERVER_INSTALL_MATRIX.md`, `SERVER_INSTALLERS_AND_PROJECTS.md`, and `CLIENT_INSTALL_MATRIX.md` record lifecycle models and explicitly distinguish researched procedures from later execution receipts. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | `REFERENCE_INDEX.md`, implementation/client files and cryptography/data-path files preserve peer-vs-server semantics, platform differences, control-plane separation, source-to-Store provenance uncertainty and later runtime-certification gaps. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `reference-v2/REFERENCE_INDEX.md` indexes every mandatory V2 responsibility, source pins, deep evidence and the shared gate reconciliation. |
| 16 | Latest AGENTS handoff contains the exact continuation state | PASS | `AGENTS_HANDOFF_2026-08-14_WIREGUARD_AWG_V2_COMPLETE.md` is committed in the same checkpoint and names entry 004 IKEv2/IPsec as the next unfinished V2 entry. |

## Reuse decision

WireGuard remains a **HIGH-PRIORITY CORE VPN TARGET / OFFICIAL-STACK-FIRST** reference. Use the best maintained official/native implementation per platform behind a PVNetwork-owned adapter; do not force one userspace engine everywhere and do not treat import `.conf` files as the internal secret-storage format. Component licenses and platform secure-storage/privilege boundaries remain separate review items.

## Preserved non-gating certification work

Useful future evidence still includes representative live install/upgrade/rollback/uninstall receipts, real-device behavior, Store/source correspondence, performance/power tests, reverse-proxy execution and interoperability matrices. None is claimed here and none is silently promoted into the written V2 gate.

# Formal result

All 16 applicable requirements in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md` have traceable repository evidence.

**Entry 002 — WireGuard: `COMPLETE-REFERENCE-v2`.**
