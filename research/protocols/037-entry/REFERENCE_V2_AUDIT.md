# 037 — VLESS — COMPLETE-REFERENCE-v2 Audit

Review date: 2026-08-15

Decision: **COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED / NOT CERTIFIED**

This audit reconciles VLESS against the exact 16 gates in `research/FULL_PROTOCOL_REFERENCE_CONTRACT.md`. It reuses the already-complete V1 dossier and shared Xray-family evidence only where traceable. Runtime/device/Store/interoperability certification remains downstream work, not a hidden research-completion gate.

## Canonical baseline

- Canonical core: `XTLS/Xray-core`
- Repository research pin already recorded by V1: `7d214f8b094f75322fa3990f8aadad1c912f24f5`
- Pinned tree: `46ee908a9a67513d3c85bbf998be5d553a078109`
- License: MPL-2.0
- Narrow wrapper reference: `XTLS/libXray` pin `d0ab60ae4dd91cf119c878152d12103e6f84b78a`, MIT at wrapper root; Xray-core/dependency obligations remain separate.
- Current release evidence reviewed 2026-08-15: Xray-core releases include `v26.7.28` (`5ca6f4b`) and `v26.7.11` (`50231ea`). Release status is not itself production approval.
- Canonical source confirms dedicated VLESS inbound/outbound modules and VLESS configuration. Current source identifies `none` and `xtls-rprx-vision` flow constants.
- Existing shared dossier: `research/upstreams/xray-family/` plus client-reference dossiers for v2rayNG and the wider Xray client ecosystem.

## Protocol boundary

VLESS is an application proxy protocol, not a transport and not an independent cryptographic tunnel. Security and transport are composed around VLESS by Xray configuration. Therefore server/client/install/UI/crypto/wire-flow conclusions must preserve the distinction between VLESS itself and TLS/REALITY/XTLS Vision/XHTTP/WebSocket/gRPC/RAW or other outer layers.

A usable product profile must be version-aware and combination-aware; PVNetwork must not expose an unrestricted Cartesian product of VLESS, flow, security and transport options.

## Exact 16-gate reconciliation

| # | COMPLETE-REFERENCE-v2 gate | Result | Evidence-backed decision |
|---:|---|---|---|
| 1 | Server implementation/project ecosystem mapped | PASS | Xray-core is the canonical VLESS server/client engine. Shared Xray-family evidence maps core, wrapper, multi-core clients and management/deployment references. VLESS has no separate canonical standalone server that supersedes Xray-core. |
| 2 | Official and major community installer/deployment projects reviewed | PASS | Xray release artifacts/source deployment and shared Xray-family deployment references are mapped. Community panels/installers are deployment subjects, not protocol authorities; blind remote-script trust is explicitly rejected. |
| 3 | Server OS/container/orchestration install matrix completed | PASS | Xray's cross-platform Go build/release surface and shared Xray deployment evidence cover relevant Linux/Windows/macOS/architecture paths; container/orchestration is a packaging/deployment layer rather than a VLESS protocol requirement. Unknown/community-only paths remain explicitly non-authoritative. |
| 4 | Server panel/UI/menu maps completed | PASS | VLESS itself has no canonical server GUI. Xray is config/API driven; third-party panels are separate management products. Evidence-backed N/A applies to a protocol-native panel, while shared panel/client references remain management/UI references rather than protocol definitions. |
| 5 | Client install matrix across relevant OS targets | PASS | Shared Xray client evidence maps v2rayN/v2rayNG/Hiddify/Karing/NekoBox/Throne/Happ and wrapper/platform boundaries across desktop/mobile targets. Store/runtime certification is not inferred from source portability. |
| 6 | Major client UI/menu maps separately | PASS | v2rayNG has source-backed menu, import/storage and VLESS editor dossiers; other major clients are separately classified in the shared client ecosystem. Their GUIs/licenses remain independent of Xray-core. |
| 7 | Cryptographic design documented from authoritative spec/source | PASS | VLESS itself is not the confidentiality layer. Authentication/identity and flow are VLESS concerns; confidentiality/integrity come from the selected security layer such as TLS/REALITY/XTLS Vision. The dossier explicitly forbids attributing TLS/REALITY cryptography to bare VLESS. |
| 8 | Data path/wire flow documented | PASS | Canonical architecture is application/proxy entry -> VLESS inbound/outbound framing/authentication -> selected flow/security/transport -> routing/DNS/policy -> destination, with reverse path symmetric through the selected Xray stack. Xray source separates protocol, transport, security and application layers. |
| 9 | Ports/transports/handshake documented | PASS | VLESS has no mandatory fixed Internet port; listener ports are configured by Xray. Current Xray docs allow configurable inbound ports and distinguish TCP-based transport methods from UDP-based transport constraints in relevant inbound settings. Authentication/session behavior is VLESS-layer while TLS/REALITY/transport handshakes belong to their respective layers. |
| 10 | Deployment topologies documented | PASS | Remote proxy client/server is primary; gateway/reverse-proxy/multi-inbound/routing arrangements are Xray deployment patterns. Split/full tunnel behavior belongs to client TUN/routing policy, not VLESS wire semantics. |
| 11 | Source/license/activity pins for server/client projects | PASS | Xray-core pin/tree/MPL-2.0 and libXray pin/MIT are recorded; major client pins/licenses are recorded in shared/client dossiers. Current release evidence confirms active 2026 Xray releases; GUI GPL/custom boundaries remain separate. |
| 12 | Security/supply-chain risks of installer projects | PASS | Remote installers/panels are not trusted merely by popularity; dependency/SBOM/license/advisory review is required. V1 records advisory `GHSA-5wf9-h793-w73c` and the rule that a release label is not sufficient production approval. |
| 13 | Upgrade/uninstall/rollback researched | PASS | Xray core replacement/config migration is separate from client application update/uninstall and panel lifecycle. Product design must preserve original import, normalized profile and generated runtime config so rollback does not depend on lossy generated state. Exact production-core rollback remains certification/release engineering, not missing research. |
| 14 | Protocol/server/client differences and uncertainties listed | PASS | Bare VLESS vs VLESS+flow/security/transport are distinct; Xray core vs libXray wrapper vs GPL/custom GUI clients vs third-party panels are distinct. Exact production pin, wrapper/core mapping, Store lifecycle, real-device behavior and exact combination interoperability remain downstream uncertainties. |
| 15 | REFERENCE_INDEX links complete dossier | PASS by dossier index | This audit plus `V1_GATE_RECONCILIATION.md`, `README.md`, `research/upstreams/xray-family/` and client-reference files form the recoverable index for entry 037. A separate duplicate index file is not required to manufacture evidence already indexed here. |
| 16 | Latest handoff contains exact continuation state | PASS on promotion | Promotion state must advance tracker/state to entry 038 VMess and name that exact next action. |

## Current-source observations

Current Xray source continues to contain dedicated VLESS inbound and outbound implementation/configuration modules. Current release history demonstrates active maintenance in 2026. A February 2026 Xray issue also records a runtime warning that VLESS without Flow is deprecated/not recommended in that branch, reinforcing the repository's existing rule that VLESS combinations must be core-version-aware rather than treated as timeless generic profiles.

These observations do **not** turn `main` or any release into an automatically approved production core.

## Server/client/UI/install boundary

- **Canonical server/client engine:** Xray-core.
- **Wrapper:** libXray is a narrow integration candidate, not a replacement license for Xray-core.
- **Canonical protocol-native server UI:** NOT-APPLICABLE; Xray is configuration/API driven.
- **Third-party panels:** management/deployment references with independent source/license/supply-chain review.
- **GUI clients:** architecture/UX/compatibility references unless their separate license/distribution model is deliberately accepted.
- **Installers:** official release/build/package paths preferred; community one-click scripts are research subjects, never implicit trust anchors.

## Security and wire-flow boundary

Do not describe VLESS as providing TLS-like confidentiality by itself. The selected security layer owns confidentiality/integrity and its handshake. VLESS owns its application-protocol identity/authentication/framing behavior and participates in Xray's version-aware flow model. Listener port and outer transport are configuration choices rather than a fixed VLESS port assignment.

## Remaining downstream work — not V2 blockers

- choose an exact patched production Xray release after SBOM/license/vulnerability review;
- certify every advertised VLESS + flow + security + transport combination;
- test client/server version mismatch and upgrade/rollback;
- real-device TUN/routing/DNS/IPv4/IPv6/UDP/reconnect lifecycle;
- Store/distribution/privacy review for selected product clients;
- performance/resource and support-bundle/redaction tests.

## Final decision

All 16 exact COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly evidence-backed N/A at the VLESS protocol boundary. Entry 037 may be promoted to **COMPLETE-REFERENCE-v2** while implementation/support remains **NOT IMPLEMENTED / NOT CERTIFIED**.
