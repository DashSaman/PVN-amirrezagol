# PVNetwork Roadmap

This roadmap is intentionally **research-first**. The repository does not yet contain a production client, so early milestones focus on evidence, architecture, licensing, feasibility and exhaustive client/server reference work before implementation.

## R0 — Repository knowledge foundation

Status: **In progress**

Goals:

- Persistent master context
- Mandatory AI handoff prompt
- Agent operating rules
- Project state
- Protocol matrix
- Architecture direction
- Research log

Exit criteria:

- A new AI can continue from the repository without previous chat history.

---

## R1 — Protocol and engine research

Goals:

- Audit all 93 named scope entries
- Classify each as VPN, proxy, enterprise compatibility family, mesh/overlay, site-to-site, security layer, or transport
- Identify best open-source and commercial reference clients
- Identify reusable engines/libraries
- Record upstream repo, license, activity, platform support, and known limitations
- Separate "good reference" from "safe candidate for integration"

Exit criteria:

- No entry lacks at least a research disposition: candidate / reference-only / legacy / low priority / unresolved

---

## R2 — Licensing and commercial distribution research

Goals:

- Audit licenses for all candidate engines
- Distinguish MIT/BSD/Apache/MPL/LGPL/GPL/AGPL implications
- Determine linking/bundling/subprocess considerations
- Identify store risks and source-disclosure obligations
- Produce third-party licensing plan

Exit criteria:

- Initial core set has a documented legal/redistribution strategy

---

## R3 — Store and platform feasibility

Targets:

- Android phones/tablets/foldables
- Android TV / Google TV
- Windows
- macOS
- iPhone/iPad
- Linux

Research:

- native VPN APIs
- privilege models
- packaging/signing
- current store rules
- background execution
- TUN/TAP/Network Extension constraints
- minimum supported OS strategy

Exit criteria:

- Platform matrix identifies feasible engines and blockers per OS

---

## R4 — Competitor and upstream failure research

Study mature projects for:

- routing bugs
- DNS leaks
- reconnect storms
- network-change behavior
- core crashes
- Android battery/background issues
- iOS Network Extension issues
- Windows route cleanup
- subscription/parser bugs
- malformed configs
- UI/RTL/localization failures
- store rejection and policy issues

Exit criteria:

- High-risk known failures have explicit PVNetwork mitigation/test ideas

---

## R4.5 — Full protocol reference expansion (`COMPLETE-REFERENCE-v2`)

**Priority rule:** this phase is mandatory, but the previously defined R1–R4 research backlog remains first priority. Do not abandon unfinished original research to start mass v2 expansion.

Detailed contract:

`research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`

Goal: turn every applicable numbered dossier into a complete **client + server engineering reference**, not merely a client/core compatibility note.

For each protocol/technology, research separately:

- all serious server implementations, forks and community projects;
- official packages/build paths and major server installer/deployment projects;
- one-click installers, automation, containers, control panels and orchestration projects used by operators;
- server installation across applicable OS/distributions, containers, CPU architectures and orchestration environments;
- all server/admin UI menus, dialogs, routes/components and management workflows for each important panel;
- installation of important clients on each applicable target OS/architecture/package channel;
- every major client's UI/menu structure screen by screen;
- protocol cryptography from authoritative specification/source evidence;
- data path/wire flow from application packet to server forwarding and return path;
- transports, ports, handshake, session establishment/resumption and NAT/roaming behavior;
- deployment topologies and control-plane/data-plane relationships;
- server/client source pins, licenses, activity and supply-chain/security risks;
- update/uninstall/rollback behavior and operational side effects of installers.

Mandatory per-protocol files and granular `server-ui/` / `client-ui/` subfiles are defined in the v2 contract.

Security rule: community installer popularity is not trust. Source-review root privileges, firewall/routing changes, secret handling, exposed management interfaces, privileged containers, auto-update and remote-script supply-chain behavior. Do not recommend blind remote script execution.

Exit criteria:

- Every applicable entry reaches `COMPLETE-REFERENCE-v2` or has explicit evidence-backed blockers/`NOT-APPLICABLE` fields.
- A future engineer can understand the protocol from client, server, cryptographic, wire/data-path, installation, UI and deployment perspectives using the repository alone plus pinned external evidence.

`COMPLETE-REFERENCE-v2` does **not** mean PVNetwork implementation or production certification.

---

## R5 — Architecture decision phase

Goals:

- Select UI/shared-code strategy
- Define native platform boundaries
- Define core adapter API
- Define internal `PVProfile` model
- Define secure credential abstraction
- Define routing/DNS abstraction
- Define subscription/import architecture
- Define localization system
- Define diagnostics/logging model

Exit criteria:

- Architecture decisions are documented and implementation can begin without major unknowns

---

## R6 — Minimum viable engine set approval

Candidate baseline under research:

1. OpenVPN 3
2. Official WireGuard stack
3. AmneziaWG
4. Xray-core
5. Mihomo
6. OpenConnect
7. strongSwan and/or native IPsec APIs
8. SoftEther
9. Hysteria2 where an independent engine provides real value

Goal:

- Use the fewest cores that provide the most useful coverage
- Avoid redundant engines and unnecessary attack/maintenance surface

Exit criteria:

- Every chosen core has protocol, platform, license, and store rationale

---

# IMPLEMENTATION ROADMAP — starts only after research gates

## M0 — Application foundation

- repository structure
- build system
- CI
- shared models
- secure storage interfaces
- core adapter interfaces
- localization foundation
- PVNetwork branding foundation

## M1 — First working platform/client shell

- modern PVNetwork UI
- English + Persian
- RTL
- dark/light/system themes
- profile list
- connection state model
- diagnostics shell

## M2 — Core networking wave 1

Priority candidates:

- WireGuard
- OpenVPN
- Xray

Acceptance requires real connection tests, not only parsing.

## M3 — Modern proxy wave

- Mihomo and/or selected modern core capabilities
- VLESS/VMess/Trojan/Shadowsocks
- REALITY/XTLS/XHTTP where supported
- Hysteria2/TUIC/AnyTLS where selected

## M4 — Enterprise VPN wave

- OpenConnect families
- IKEv2/IPsec
- selected legacy/enterprise compatibility

## M5 — Universal import/subscriptions

- file detection
- QR
- clipboard
- URI schemes
- subscription refresh
- normalization to `PVProfile`

## M6 — Routing and DNS

- global/rule/direct/smart modes
- split tunnel
- per-app where OS permits
- DNS routing
- leak prevention

## M7 — Reliability

- reconnect
- network switching
- sleep/resume
- route cleanup
- core crash recovery
- kill switch where technically valid

## M8 — Android and Android TV productionization

- Android VpnService
- TV D-pad/remote UX
- TV pairing/import experience
- device compatibility

## M9 — Windows productionization

- service/helper privilege model
- Wintun/WFP/native integration as selected
- packaging/signing
- Microsoft Store feasibility

## M10 — Apple productionization

- Network Extension architecture
- iOS/iPadOS
- macOS
- signing/entitlements
- App Store requirements

## M11 — Linux productionization

- TUN/network integration
- privilege model
- packaging choice
- distro testing

## M12 — Competitive hardening

- turn researched upstream bugs into regression tests
- performance/battery/memory work
- parser fuzzing where useful
- malformed profile resilience

## M13 — Store compliance and release engineering

- Google Play
- Android TV/Google TV
- Apple App Store
- Mac distribution path
- Microsoft Store
- Linux packages
- privacy/legal metadata
- localized store assets

## M14 — Release candidates

- E2E tests
- real devices
- protocol capability matrix verification
- security review
- license review
- store review readiness

## M15 — Production release

Only declare production readiness when evidence supports it.

---

# Roadmap rules

- Research may change implementation order.
- Store or license blockers can veto a core.
- A popular open-source project is not automatically suitable for embedding.
- A popular community installer is not automatically safe to trust.
- Protocol support must be verified by PVNetwork itself.
- UI polish does not outrank networking correctness/security.
- Original research backlog remains first priority; full-reference v2 expansion follows the prior research gates.
- Every meaningful work unit must remain resumable through the latest AGENTS handoff.