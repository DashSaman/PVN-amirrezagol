# 061 — Tinc — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Decision: **`COMPLETE-RESEARCH-v1 / MATURE LEGACY MESH VPN / GPLv2+ / NOT IMPLEMENTED / NOT CERTIFIED`**

Tinc is a peer-to-peer encrypted VPN daemon with automatic mesh discovery and router/switch/hub modes. It is not WireGuard, ZeroTier or a generic GRE/VXLAN tunnel.

## Current source/release truth

Canonical repo: `gsliepen/tinc`, default branch `1.1`.

Current reviewed development head:
- commit `211e3dfaef32d8736962e25f0b096dad951b7104`
- tree `00b983a651524c52f4a17b8cf5a6300a4d91f910`
- date 2026-06-27
- current branch remains actively maintained in 2026.

Release tags:
- latest 1.1 prerelease tag: `release-1.1pre18` -> `3217d5efb432f5a03beebd5d00b36392ec4b22ef`;
- latest stable 1.0 tag in canonical repo: `release-1.0.37` -> `2904e324ea68475fa3a131e7d39a43d80465b39a`.

Critical upstream warning: current 1.1 README explicitly says **1.1 is NOT a stable release** and advises using 1.0.x when stable Tinc is required. It also says 1.1 remains protocol-compatible with 1.0.x while program functionality/control-socket protocol may change.

PVNetwork must not label current 1.1 development/nightly packages stable merely because development is active.

License at current source: **GPL-2.0-or-later** (`COPYING`). Direct embedding/derivative distribution in a closed proprietary client requires a deliberate GPL-compatible architecture. Using a separately installed daemon/process has different legal implications but distribution/compliance still requires review.

## Architecture

Current upstream README defines:
- peer-to-peer VPN daemon;
- nodes are seeded with locations/public keys for a few peers;
- mesh knowledge spreads automatically;
- direct peer connections are attempted; intermediate nodes forward when direct connection is unavailable;
- `router` mode associates nodes with IPv4/IPv6 subnets;
- `switch` and `hub` modes create virtual Ethernet-like behavior.

Tinc owns its own key/config/protocol/mesh behavior; do not map it to WireGuard semantics.

Canonical profile state includes network name, node name, peer/host files, public/private key material, addresses/ports, advertised Subnets, mode, ConnectTo/bootstrap peers and daemon/control-socket lifecycle. Keys/private credentials are secret; topology/host/subnet data can be privacy-sensitive.

## UI/platform/testing boundary

Upstream is primarily daemon/CLI/config/manual driven. There is no canonical consumer GUI whose menus must be copied. PVNetwork should build a typed UI around network/node/peer/subnet/mode/key/status/log concepts.

Current README recommends distro packages or source builds and warns nightly prebuilt packages are not heavily tested/officially supported. Platform support includes Unix-like systems and Windows build/package paths. Exact mobile/Store integration is not established by the canonical project and is later certification, not a hidden V1 gate.

Current branch contains test/container work and 2026 bug fixes, including packet decompression accounting. Exact production release/dependency/SBOM/security review remains freeze-time work.

## 20-gate reconciliation

| # | Gate | Result | Conclusion |
|---:|---|---|---|
|1|Top clients|PASS|Canonical tinc daemon/CLI is primary authority; distro packages/Windows builds are deployment references. No fake first-party GUI is claimed.|
|2|Canonical sources|PASS|Current 1.1 head/tree, 1.1pre18 prerelease and 1.0.37 stable tags pinned.|
|3|Licenses|PASS|GPL-2.0-or-later verified; closed-product reuse requires explicit architecture/compliance.|
|4|Source tree|PASS|Current exact tree pinned; daemon/config/crypto/network/control/test/build surfaces are canonical.|
|5|Languages/build|PASS|C-based daemon with autotools/meson/build/package/container test ecosystem.|
|6|Architecture|PASS|Automatic peer mesh, direct/intermediate forwarding and router/switch/hub modes mapped.|
|7|Core integration|PASS|Use maintained tinc daemon/component adapter; do not reimplement its protocol/crypto.|
|8|UI/menu|PASS/N-A upstream GUI|CLI/config concepts mapped to required PVNetwork UI; exhaustive screens later.|
|9|Config/import/export|PASS|Network/node/hosts/Subnets/ConnectTo/mode/key/config directory and 1.0/1.1 compatibility boundaries mapped.|
|10|Persistence/secrets|PASS|Private keys and control/runtime credentials separated from public host/subnet/topology state.|
|11|Platform integration|PASS for research|Unix/Windows build/package paths exist; mobile/Store lifecycle remains later.|
|12|Logs/diagnostics|PASS|Daemon/control, peer reachability, direct/forwarded path, key/auth, route/subnet/interface and config/version failures are distinct.|
|13|Assets/screenshots|PASS/N-A|No canonical consumer GUI/brand asset requirement; docs/config are authoritative.|
|14|Alternatives/forks|PASS|1.0 stable vs 1.1 prerelease and router/switch/hub modes are explicit; other mesh systems remain separate entries.|
|15|Issues/releases/advisories|PASS|2026 active branch, prerelease/stable distinction and current bug/test activity reviewed; production security scan remains exact-release work.|
|16|Docs/forums|PASS|Canonical README, manual, QUICKSTART/INSTALL and source are primary.|
|17|Tests/CI|PASS|Current branch has tests/container work; upstream warns nightly packages are not heavily tested, preserved as a risk.|
|18|Store/privacy/security|PASS|GPL boundary, key secrecy, topology metadata, legacy/stability status, forwarding and package/support differences explicit.|
|19|Reuse decision|PASS|Optional legacy mesh compatibility. Prefer separately managed daemon/approved package if needed; no duplicate mesh engine by default.|
|20|Uncertainties|PASS|Exact production 1.0 vs 1.1 choice, modern algorithm/dependency audit, distro patches, mobile lifecycle/performance and V2 wire/server/UI evidence remain later.|

## Product rules
1. Do not call 1.1 stable while upstream says it is pre-release.
2. Keep 1.0/1.1 capability/version explicit.
3. Do not copy GPL daemon code into a closed app without compliant legal architecture.
4. Store private keys securely and redact topology/secret config from support bundles.
5. Distinguish direct peer path from intermediate-node forwarding.
6. Do not infer WireGuard cryptography or semantics from “mesh VPN”.

## Final V1 decision
All 20 V1 gates are evidence-backed with stable-vs-prerelease and GPL boundaries explicit. Entry 061 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining not implemented/certified.
