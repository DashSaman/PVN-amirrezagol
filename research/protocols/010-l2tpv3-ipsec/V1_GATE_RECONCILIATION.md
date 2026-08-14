# Entry 010 — L2TPv3/IPsec — COMPLETE-RESEARCH-v1 Gate Reconciliation

Review date: 2026-08-14

Decision: **`COMPLETE-RESEARCH-v1 / ADVANCED-PROTECTED-SITE-TO-SITE-COMPOSITION / NOT IMPLEMENTED`**

This audit reconciles entry 010 against the exact 20 original research gates in `research/PROTOCOL_RESEARCH_TEMPLATE.md`. L2TPv3/IPsec is treated as a composition of the entry-009 L2TPv3 pseudowire model with the separately researched IKE/IPsec security model. Runtime/interoperability/capture receipts remain certification evidence, not hidden V1 research gates.

## Traceable evidence set

Core standards and inherited evidence:

- RFC 3931 — L2TPv3: https://www.rfc-editor.org/rfc/rfc3931.html
- RFC 4719 — Ethernet Pseudowire for L2TPv3: https://www.rfc-editor.org/rfc/rfc4719.html
- current IPsec/IKE standards and security evidence from entries 004–007 and `research/upstreams/strongswan-family/`

Pinned implementation references already recorded in the entry-010 dossier:

- Linux kernel L2TP subsystem: `2f1baf1fc8929e6c48370be543ad028ac7ad4131`
- iproute2: `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`
- go-l2tp: `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`
- strongSwan reviewed release 6.0.7 and its exact source pin in the IPsec dossier
- Libreswan reviewed v5.4 pin in the IPsec dossier
- Cisco IOS XE retained as proprietary exact-version interoperability reference, not source-reuse candidate

Repository evidence:

- `research/protocols/010-l2tpv3-ipsec/V1_RESEARCH.md`
- complete `research/upstreams/classic-tunnels-family/l2tpv3-ipsec-reference-v2/` dossier
- entry 009 L2TPv3 research/reference dossier
- entries 004–007 IKE/IPsec research/reference dossier

## Exact 20-gate result

1. **Top clients/implementations — PASS.** Linux kernel/iproute2 plus strongSwan or Libreswan are the principal composed Linux references; go-l2tp is a component/control reference; Cisco is a proprietary peer/interoperability target. Consumer VPN-client semantics are evidence-backed N/A.
2. **Canonical sources pinned — PASS.** Public implementation families use immutable reviewed revisions/releases; RFCs and proprietary vendor documentation are canonical references where source is unavailable.
3. **Licenses reviewed — PASS.** Linux/iproute2/go-l2tp/IPsec component licenses are documented in their shared dossiers; Cisco is reference-only. Component-specific obligations remain separate.
4. **Complete source-tree reference/manifest — PASS.** Pinned public trees and relevant source areas are referenced without mirroring entire third-party repositories.
5. **Languages/build systems — PASS.** Kernel/iproute2 native code, Go L2TP components and strongSwan/Libreswan build/package ownership are mapped; proprietary internals are not invented.
6. **Architecture — PASS.** L2TPv3 tunnel/session/data plane is separate from IKE/ESP/XFRM protection, route/selectors, Layer-2 attachment and OS networking ownership.
7. **Core/engine integration — PASS.** OS/kernel implementations and maintained IKE/IPsec engines are reused through typed privileged adapters. Cryptography is not reimplemented.
8. **UI/menu map — PASS / N/A-CONSUMER.** Operator/peer UI is mapped for pseudowire fields, IPsec identity/policy, selector preview, Layer-2 attachment, correlated status and fail-safe start/stop. Normal consumer protocol pickers must hide this infrastructure feature.
9. **Config/import/export — PASS.** Peer/tunnel/session IDs, direct-IP vs UDP mode, cookies/sequencing, Layer-2 attachment and typed IPsec/IKE fields are mapped. Generic consumer QR/subscription formats are N/A.
10. **Persistence/secrets — PASS.** IKE PSKs/private keys/certificates are separate from L2TPv3 cookies/control metadata and operator configuration. Secret values are protected/redacted.
11. **Platform integrations — PASS / PEER-MAPPED.** Linux is primary; Cisco/vendor peers are exact-version interop targets; containers/namespaces are infrastructure-specific. Windows/mobile/macOS consumer-client support is not invented.
12. **Logs/diagnostics — PASS.** IKE/ESP/XFRM state and L2TPv3 tunnel/session/interface state are correlated while preserving layer-specific failure causes and secret redaction.
13. **Asset/screenshot references — PASS / N/A-CONSUMER.** Relevant visual references are vendor/operator documentation and source UI/config material; no third-party branding/assets are approved for copying.
14. **Meaningful forks/alternatives — PASS.** Linux kernel/iproute2, go-l2tp, strongSwan, Libreswan and Cisco provide materially different implementation/reference roles; no arbitrary fork is promoted.
15. **Issues/PRs/releases/advisories — PASS.** The shared L2TPv3 and IPsec dossiers record security/maintenance/rekey/selector/MTU/netns/vendor exact-version risks and selected source/release baselines.
16. **Relevant forums/docs — PASS.** RFCs, official source repositories and current vendor/platform documentation are primary; community recipes cannot override them.
17. **Tests/CI — PASS.** Required future tests and missing coverage are documented: protocol-115 and UDP protected paths, forced IPsec failure/no-clear fallback, rekey, packet proof, MTU/PMTU/ECN, Layer-2 behavior, Linux/vendor interop and cleanup. No runtime PASS is fabricated.
18. **Store/privacy/security — PASS / N/A-CONSUMER.** This is an infrastructure feature, not a consumer Store protocol. Plain L2TPv3 is never presented as protected; IPsec policy and secrets remain explicit and current.
19. **PVNetwork reuse decision — PASS.** Use kernel/OS pseudowire plus an approved IPsec backend behind typed adapters; reuse the common IPsec security model; never duplicate or obscure IKE/credential fields; fail closed rather than allowing clear pseudowire fallback.
20. **Uncertainties explicit — PASS.** Exact distro/kernel/IKE-engine combinations, direct-IP/UDP selectors, NAT-T, rekey, Linux-Cisco composition, IPv6, MTU/ECN, bridge/VLAN behavior, containers/netns, upgrade/rollback and packet/runtime evidence remain implementation/certification residuals.

## V1 completion decision

All 20 original research categories are evidence-backed or evidence-backed N/A. Entry **010 — L2TPv3/IPsec** may therefore be promoted to **`COMPLETE-RESEARCH-v1`**.

This does not imply `COMPLETE-REFERENCE-v2`, implementation, successful protected pseudowire operation, packet-capture proof, device/interoperability certification or production support.
