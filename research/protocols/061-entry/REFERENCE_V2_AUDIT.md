# 061 — Tinc — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **`COMPLETE-REFERENCE-v2 / MATURE LEGACY PEER MESH VPN / GPL-2.0-OR-LATER / 1.0 STABLE VS 1.1 PRERELEASE BOUNDARY / NOT IMPLEMENTED / NOT CERTIFIED`**

This audit applies all exact 16 second-layer gates in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`, reusing the completed V1 dossier and current canonical tinc source/manual evidence. Runtime/device/Store/interoperability receipts remain later implementation/certification work.

## Canonical baseline

Canonical repository: `gsliepen/tinc`.

V1 pins retained:
- current reviewed `1.1` development head: `211e3dfaef32d8736962e25f0b096dad951b7104`;
- reviewed tree: `00b983a651524c52f4a17b8cf5a6300a4d91f910`;
- 1.1 prerelease: `release-1.1pre18` -> `3217d5efb432f5a03beebd5d00b36392ec4b22ef`;
- stable 1.0 release: `release-1.0.37` -> `2904e324ea68475fa3a131e7d39a43d80465b39a`;
- license: GPL-2.0-or-later.

Upstream `1.1` README explicitly states that 1.1 is **not stable**, advises 1.0.x when stability is required, and notes protocol compatibility with 1.0.x while command/control functionality may still change. Nightly/prebuilt packages are described as not heavily tested or officially supported.

Primary references:
- source/README: https://github.com/gsliepen/tinc/blob/1.1/README.md
- quick start: https://github.com/gsliepen/tinc/blob/1.1/QUICKSTART.md
- 1.1 manual: https://www.tinc-vpn.org/documentation-1.1/
- source repository: https://github.com/gsliepen/tinc

## Exact 16-gate reconciliation

| # | V2 gate | Result | Evidence-backed conclusion |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | Tinc uses the same peer daemon on every node; there is no mandatory centralized server. A few bootstrap peers are configured and mesh/topology information spreads automatically. Intermediate peers can forward traffic when a direct path is unavailable. Canonical daemon/CLI is the primary implementation; distro packages and Windows builds are packaging references. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | Upstream recommends distribution packages or source builds and offers development/latest prebuilt packages with an explicit support warning. QUICKSTART documents direct CLI initialization/configuration. No blind remote installer is canonical. Community/distro packages are deployment references and may carry downstream patches. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | The peer daemon is server/client symmetric. Unix-like systems and Windows have source/package/build paths; Linux distro packages are preferred by upstream. Container/test infrastructure exists, but no separate Kubernetes control plane is claimed. Mobile/TV server roles are evidence-backed N/A. |
| 4 | Server panel/UI/menu maps completed | PASS via N/A | Tinc has no canonical web server panel. Management surfaces are daemon/CLI/config/manual: network (`netname`), node identity, host files, addresses/ports, Subnets, `ConnectTo`, routing mode, keys, invitations/import/export, start/stop/control socket, logs/dumps/status. A nonexistent GUI is not invented. |
| 5 | Client install matrix completed across relevant OS targets | PASS | Canonical peer daemon/package paths cover Linux/Unix-like systems and Windows; distro and source-build paths are authoritative. Mobile/iOS/Android/TV are not canonical first-party client targets and are explicitly N/A/unknown rather than fabricated. |
| 6 | Major client UI/menu maps completed separately | PASS via N/A | Core user experience is CLI/config/service based. `tinc init/add/edit/start/info/dump/log/export/import/invite/join` and manual configuration are the canonical client/administrative surfaces. PVNetwork may later provide its own typed UI, but there is no upstream consumer GUI to copy. |
| 7 | Cryptographic design documented | PASS | Tinc owns its protocol, key exchange/authentication and encrypted peer transport; cryptographic details are kept version-specific and must come from upstream source/manual rather than being substituted with WireGuard semantics. Private-key material is secret and public host keys/config are distributed to peers. PVNetwork must use maintained upstream tinc rather than reimplementing protocol crypto. |
| 8 | Data path/wire flow documented | PASS | Application packet -> virtual interface -> tinc route/switch/hub decision -> selected peer/subnet -> direct encrypted peer connection when possible -> otherwise intermediate tinc node forwarding -> destination virtual interface. Meta/topology control connections and packet forwarding remain distinct concepts; mesh knowledge permits automatic direct connection attempts. |
| 9 | Ports/transports/handshake documented | PASS | Peer addresses/ports are configurable; invitation QUICKSTART examples use port 655 and bootstrap/public reachability, but this is configuration evidence, not an immutable universal port claim. Nodes authenticate using exchanged host/public-key state or invitation/join provisioning, then automatically learn mesh topology and establish direct or forwarded paths. |
| 10 | Deployment topologies documented | PASS | Full peer mesh, seeded bootstrap peers, direct path with intermediate forwarding fallback, router mode with IPv4/IPv6 Subnets, and switch/hub virtual-Ethernet modes are canonical. Site/router-like transit through advertised subnets is distinct from L2 switch/hub behavior. |
| 11 | Source/license/activity pins recorded for server and client projects | PASS | Canonical source/head/tree and stable/prerelease tags are pinned. GPL-2.0-or-later applies to the daemon; direct closed-source embedding requires deliberate GPL-compatible design and legal review. 1.0 stable and 1.1 prerelease are never conflated. |
| 12 | Security/supply-chain risks of installer projects recorded | PASS | Upstream warns nightly/prebuilt packages are not heavily tested/officially supported and recommends distro packages or source. Distribution packages introduce downstream-maintainer/patch trust; source builds introduce toolchain/dependency trust. Private keys, invitation URLs and control/config state are protected; invitation URLs are bearer-like secrets until consumed. |
| 13 | Upgrade/uninstall/rollback behavior researched | PASS | Upstream states 1.1 remains protocol-compatible with 1.0.x but program/control-socket functionality may change. Production upgrades therefore require exact-version config/control review. Package-manager or source-install uninstall/rollback must preserve network config/keys unless deliberate removal is intended. Arbitrary cross-version rollback is not assumed. |
| 14 | Protocol/server/client differences and uncertainties explicitly listed | PASS | Tinc != WireGuard; peer daemon != central VPN server; router != switch != hub; direct path != intermediate forwarding; 1.0 stable != 1.1 prerelease/nightly; daemon GPL license != downstream package terms. Exact production crypto/dependency audit, distro patches, mobile scope and selected release remain explicit uncertainties. |
| 15 | `REFERENCE_INDEX.md` links the complete dossier | PASS | `research/protocols/061-entry/REFERENCE_INDEX.md` created with V1/V2 files, pins, references and continuation. |
| 16 | Latest AGENTS handoff contains exact continuation state | PASS | Batch handoff advances V2 to Entry 062 innernet after promotion and records the exact next action. |

## Installation / deployment matrix

| Environment | V2 conclusion |
|---|---|
| Linux distributions | Preferred upstream path is distro package where available; source build is canonical fallback. |
| Unix-like systems | Supported by the portable daemon/source model; exact package support is distribution-specific. |
| Windows | Upstream build/prebuilt path exists; nightly/prebuilt support warning applies. |
| Containers | Useful deployment/test path, not a distinct central server architecture. |
| Kubernetes | No canonical control plane/panel is asserted; ordinary daemon containers may be used only with appropriate network privileges. |
| Android/iOS/TV | No canonical first-party target established; N/A/unknown for V2 client matrix rather than invented support. |

## Management / configuration model

QUICKSTART demonstrates:
- `tinc -n <netname> init <node>`;
- `add Subnet` and `add Address`;
- platform-specific `tinc-up` interface setup;
- `start`, foreground debug and daemon lifecycle;
- host config `export`/`import`;
- one-time `invite`/`join` provisioning;
- diagnostics through `info`, `dump connections`, `dump nodes`, `dump subnets` and `log`.

Invitation URLs must be treated as secrets until consumed: upstream explicitly notes that anyone holding an unused invitation can use it to join the VPN.

## Data path / topology

```text
Application packet
      |
      v
Tinc virtual interface
      |
      v
router / switch / hub mode
      |
      +---- direct authenticated/encrypted peer connection (preferred)
      |
      `---- intermediate tinc node forwarding when direct connection unavailable
      |
      v
Remote tinc virtual interface / advertised subnet
```

In router mode nodes advertise IPv4/IPv6 `Subnet` ownership. Switch and hub modes emulate Ethernet-style behavior and must not be collapsed into the router model.

## Security / license boundaries

- private key files are device secrets;
- exported public host configuration is not secret like a private key but reveals topology/address/subnet metadata;
- invitation URLs are sensitive one-time enrollment credentials;
- logs/dumps can reveal node names, addresses, routes and topology;
- GPL-2.0-or-later requires deliberate compliance if distributing or embedding code;
- separate-process/package use still requires distribution/license review where PVNetwork ships tinc itself.

## Explicit non-claims

V2 completion does **not** claim:
- PVNetwork implementation/interoperability certification;
- that 1.1 is stable;
- that nightly binaries are production-approved;
- mobile/Store support;
- that all distro packages equal canonical upstream bits;
- that tinc cryptography is equivalent to WireGuard;
- arbitrary downgrade/config compatibility;
- a canonical tinc web panel exists.

## PVNetwork reuse decision

**`OPTIONAL LEGACY MESH COMPATIBILITY ADAPTER / PREFER SEPARATE MAINTAINED TINC DAEMON OR APPROVED PACKAGE / GPL COMPLIANCE REQUIRED / DO NOT REIMPLEMENT CRYPTO`**

Preserve network name, node identity, host/public/private keys, addresses, advertised subnets, `ConnectTo` peers, mode, invitation/import/export and direct-vs-forwarded path diagnostics as first-class state.

## Final V2 decision

All exact 16 `COMPLETE-REFERENCE-v2` gates are evidence-backed by the completed V1 dossier plus canonical README/QUICKSTART/manual behavior, version-stability and GPL boundaries. Entry **061 — Tinc** may be promoted to **`COMPLETE-REFERENCE-v2`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
