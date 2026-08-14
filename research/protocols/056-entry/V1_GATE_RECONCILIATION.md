# 056 — Tailscale — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Entry: **056 — Tailscale**

Decision: **`COMPLETE-RESEARCH-v1 / WIREGUARD-BASED MESH ECOSYSTEM / OPTIONAL ACCOUNT+CONTROL-PLANE INTEGRATION / NOT IMPLEMENTED / NOT CERTIFIED`**

Shared mesh evidence:

- `research/upstreams/mesh-overlay-family/README.md`
- `research/upstreams/mesh-overlay-family/SUPPORT_REUSE_DECISIONS.md`

Tailscale is not a new substitute for entry 002 WireGuard. Its value is the ecosystem around a WireGuard-based data plane: node identity, coordination, NAT traversal/DERP, policy, DNS/naming, device/account state, subnet/exit-node roles and client UX.

## 1. Current source/release pins

Primary repository:

- `tailscale/tailscale`
- reviewed current main: `0953fd9a97e9697fb496c0e1d3a0e2a45bc264ea`
- reviewed tree: `a1703106fb225b27026d8c45964b4c8ac0a260b8`
- reviewed head date: 2026-08-14
- latest reviewed release: `v1.102.2`, published 2026-08-04
- root license: BSD-3-Clause
- implementation: Go; current README says latest Go release is required and identifies `tailscaled` daemon plus `tailscale` CLI as core open-source components.

Current recursive source tree includes, among many others:

- `cmd/tailscale`, `cmd/tailscaled` and numerous utility/server tools;
- `wgengine/` data-plane/engine layers;
- `net/`, DNS/NAT/network monitoring/traversal packages;
- `derp/` relay protocol/client/server packages;
- `control/` coordination/control-client types and transport;
- `ipn/` local node/backend/profile/state/API model;
- `tailcfg/` control-plane/network-map types;
- `types/`, `util/`, crypto/key packages;
- web client/assets;
- installer/package/container/Kubernetes/Nix material;
- extensive tests and workflows including CodeQL, govulncheck, NAT lab, SSH integration and broad Go test matrices.

The README explicitly says this repo contains **the majority**, not all, of Tailscale's open-source code and that non-open-source platform GUI wrappers exist on some platforms. PVNetwork must never treat this single repository as proof that the entire hosted service/control plane or every GUI is open source.

## 2. Android / Android TV source pin

Repository:

- `tailscale/tailscale-android`
- reviewed main: `0867f01687a3955f7c0b5c6c62b236b997d68601`
- reviewed tree: `8eaa1daf0f632e71d058dcb09300efb3b1ccb079`
- reviewed head date: 2026-08-12
- license: BSD-3-Clause style Tailscale license in root `LICENSE`.

Current Android source/build evidence:

- Kotlin/Android application wrapping Tailscale Go code;
- Android `IPNService.kt` service/VPN lifecycle and manifest VPN service evidence;
- Android phone/tablet and **Android TV** release AAB paths are first-class build outputs;
- current versionCode logic reserves different platform suffixes for phone/tablet versus Android TV;
- current release workflow integrates Play/F-Droid-oriented version/publication behavior;
- repository includes Gradle/build scripts, tests, localization/resources and platform-specific UI/service code.

This is especially valuable to PVNetwork because Android TV is an explicit product target; it is source evidence, not proof that PVNetwork should copy Tailscale UI or branding.

## 3. Architecture boundary

Conceptual Tailscale stack:

`platform GUI / CLI / API`

`-> local backend / node state`

`-> account/node identity + control/coordination/network map`

`-> DNS/policy/routes/exit/subnet capabilities`

`-> WireGuard-based peer data plane`

`-> direct NAT-traversed path when possible OR DERP relay path when needed`

Important product boundaries:

- control/coordination metadata is separate from encrypted peer payload;
- DERP relays are separate from the normal direct peer data path;
- MagicDNS/name service is separate from base WireGuard cryptography;
- exit nodes/subnet routers are network roles, not new VPN protocols;
- account/tailnet/device identity cannot be represented faithfully as one generic `server:port` profile.

## 4. Configuration / persistence / identity

Tailscale's open-source client architecture includes local node/profile state and backend APIs rather than a simple share-link format. Exact hosted-service login/account provisioning is product/control-plane state.

PVNetwork canonical mesh model should separate:

- provider/account identity;
- tailnet/network identity;
- device/node identity/key material;
- control-server/provider URL when selectable;
- advertised/accepted routes;
- exit-node selection;
- DNS/name-service policy;
- policy/ACL summary where exposed;
- node online/coordination state;
- selected data-path status: direct vs DERP;
- underlying engine/version metadata.

Keys/auth tokens/device credentials must remain protected backend/platform state rather than ordinary portable profile JSON. Logout/device removal and key rotation are lifecycle operations, not merely `disconnect`.

## 5. UI / menus / platform model

Current Tailscale product/client ecosystem includes first-party applications for Windows, macOS, Linux, Android, iOS and other platforms; the primary repo says macOS/iOS/Windows clients use the open Go code with small platform GUI wrappers, some of which are not open source.

V1 UI evidence therefore correctly separates:

- open CLI/local API/web-client behavior;
- open Android/Android TV GUI/service source;
- closed platform wrappers as behavioral references only.

Major product states that PVNetwork would need for a real Tailscale integration include:

- sign in/out/account;
- tailnet/network/device identity;
- connected/disconnected/backend state;
- peers/devices;
- exit node;
- subnet routes;
- DNS/MagicDNS;
- SSH/serve/funnel or other optional capabilities only if deliberately supported;
- update/version/diagnostics;
- direct vs relayed connectivity status.

Do not copy Tailscale icons, name, account UX or branding simply because the engine code is BSD licensed; trademark/brand rules remain separate.

## 6. Security/privacy/supply-chain lessons

- WireGuard peer encryption does not mean the coordination service sees no metadata; account/device/network-map/endpoint/relay coordination is a separate privacy surface.
- DERP relays forward encrypted peer traffic but participate in reachability/metadata; do not claim all paths are direct peer-to-peer.
- device/node keys and auth credentials need protected storage and lifecycle/rotation semantics.
- exit-node/subnet routing can intentionally change traffic scope; route/DNS kill-switch/leak behavior needs explicit tests.
- the repository has strong current CI/security evidence, including CodeQL, govulncheck and NAT lab workflows, but production still needs an exact release/SBOM/dependency/advisory review.
- hosted-service APIs/control-plane behavior can change independently of client source; compatibility claims need current provider evidence.

## 7. PVNetwork reuse decision

Classification:

**`OPTIONAL ECOSYSTEM INTEGRATION / REUSE-CANDIDATE OPEN CLIENT CORE / DO NOT SHIP MERELY TO DUPLICATE WIREGUARD`**

Recommended approach if this feature is selected:

1. use official open Tailscale client/library/backend components or a supported provider API rather than reimplementing coordination/data-plane logic;
2. place Tailscale behind a dedicated `MeshProviderAdapter`, not the ordinary single-server VPN profile adapter;
3. reuse entry 002 WireGuard research for cryptographic/data-plane principles without pretending a raw WireGuard profile can join a Tailscale tailnet;
4. preserve hosted-provider/self-hosted control-server choice only when exact supported APIs are verified;
5. keep account/device/control-plane metadata privacy visible to the user.

## 8. 20-gate V1 reconciliation

| # | V1 gate | Result | Evidence / Tailscale conclusion |
|---:|---|---|---|
| 1 | Top clients/implementations | PASS | Official open Go client/daemon/CLI and open Android/TV client are primary; first-party closed wrappers remain behavior references. |
| 2 | Canonical sources pinned | PASS | Exact current primary and Android commits/trees plus v1.102.2 release are pinned. |
| 3 | Licenses reviewed | PASS | Main and Android root licenses are BSD-3-Clause style; hosted service/other repos/branding remain separate and are not inferred from client license. |
| 4 | Complete source-tree reference | PASS | Exact recursive main tree and Android tree IDs are recorded; major architecture/build/test/installer paths are identified. |
| 5 | Languages/build systems | PASS | Main Go modules/build scripts; Android Kotlin/Gradle + Go library integration; platform/package/container/K8s/Nix build surfaces mapped. |
| 6 | Architecture | PASS | GUI/CLI -> local backend -> coordination/network map -> policy/DNS -> WireGuard peer engine -> direct/DERP data path is explicitly separated. |
| 7 | Core/engine integration | PASS | Official client/backend is the candidate; raw WireGuard is not a drop-in replacement for coordination/account semantics. |
| 8 | UI/menu map | PASS for V1 | Open Android/TV and CLI/web-client source plus official closed-wrapper behavior classes provide account/peer/exit/DNS/settings/status map. Exhaustive menus remain V2. |
| 9 | Config/import/export | PASS | Node/account/control-server/routes/exit/DNS/local backend state is mapped; this is not a generic share-link/profile format. |
| 10 | Persistence/secrets | PASS | Node/device/account credentials/keys are protected backend/platform state; route/DNS preferences and network metadata have separate ownership. |
| 11 | Platform integrations | PASS for research | Main daemon supports Linux/Windows/macOS and BSD variants; open Android source explicitly supports phone/tablet and Android TV; iOS/macOS/Windows wrappers use shared code. |
| 12 | Logs/diagnostics | PASS | Current client/backend has rich status/network/path diagnostics and test tooling; product logs must redact auth/node/private keys and sensitive network metadata. |
| 13 | Assets/screenshots | PASS for V1 | Open Android/web assets can be referenced under source/license rules, but Tailscale branding/trademarks and closed GUI assets remain separate. |
| 14 | Meaningful alternatives/forks | PASS | Raw WireGuard, Headscale-compatible/self-hosted control plane and other mesh products are architecturally distinct alternatives; not flattened into the entry. |
| 15 | Issues/PRs/releases/advisories | PASS | Main is active 2026, current v1.102.2 release pinned, active issue tracker/security workflows and Android release pipeline recorded. |
| 16 | Relevant forums/docs | PASS | Current repo README/source/docs plus provider docs/changelog are canonical behavioral references; hosted-service semantics remain versioned external evidence. |
| 17 | Tests/CI | PASS | Main CodeQL/govulncheck/NATlab/SSH/test workflows and Android build/test/release evidence are explicit. |
| 18 | Store/privacy/security | PASS | Account/control metadata, DERP/direct path, device keys, exit-node routing, DNS, Android VPN/TV packaging and proprietary GUI/provider boundaries are explicit. |
| 19 | PVNetwork reuse decision | PASS | Optional dedicated mesh adapter using official client components; do not bundle solely for WireGuard transport. |
| 20 | Uncertainties | PASS | Exact selected provider/control API, current dependency/SBOM, self-hosted compatibility, desktop/iOS GUI integration, Store/account policy, performance and full V2 server/control/UI/wire evidence remain later. |

## 9. Later acceptance work — not V1 blockers

- exact production Tailscale release/source/SBOM/advisory freeze;
- provider account/OAuth/device-auth lifecycle and logout/key rotation;
- direct vs DERP and NAT traversal under real networks;
- MagicDNS/search domains/leak behavior;
- subnet/exit-node route changes and kill-switch semantics;
- Android phone/tablet/TV and desktop/iOS background/service lifecycle;
- self-hosted compatible control server only if included in product scope;
- privacy/support-bundle redaction;
- exhaustive menu/screenshots and V2 control-plane/server installer/deployment topology research.

## Final V1 decision

All 20 original V1 research gates have traceable evidence or correct ecosystem/proprietary-boundary treatment. Entry 056 may be promoted to **`COMPLETE-RESEARCH-v1`** while remaining **`NOT IMPLEMENTED / NOT CERTIFIED`**.
