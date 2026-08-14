# Entry 072 — Cisco FlexVPN — COMPLETE-RESEARCH-v1 gate reconciliation

Reviewed: 2026-08-14

## Identity

Cisco FlexVPN is Cisco's IKEv2/IPsec framework and unified configuration paradigm, not a new independent cryptographic wire protocol. Cisco's current Security and VPN Configuration Guide (updated 2026-04-24) describes FlexVPN as its implementation of IKEv2 covering site-to-site, remote access, hub-and-spoke and partial-mesh topologies with a tunnel-interface model.

PVNetwork classification: **vendor IKEv2/IPsec interoperability/profile target; reuse the generic IKEv2/IPsec model, keep Cisco extensions explicit**.

## Canonical evidence

- Current Cisco introduction (updated 2026-04-24): https://www.cisco.com/c/en/us/td/docs/routers/ios-xe/security-vpn/security-vpn/m_sec-intro-ikev2-flex.html
- Cisco IOS XE 17 FlexVPN server: https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/sec-vpn/b-security-vpn/m_sec-cfg-flex-serv-0.html
- Cisco IOS XE 17 FlexVPN/IKEv2 guide: https://www.cisco.com/c/en/us/td/docs/ios-xml/ios/sec_conn_ike2vpn/configuration/xe-17/sec-flex-vpn-xe-17-book-cat8000.html
- Cisco FlexVPN spoke-to-spoke troubleshooting reference (IOS XE 17.9.4a lab): https://www.cisco.com/c/en/us/support/docs/ios-nx-os-software/ios-xe-17/222760-configure-and-troubleshoot-flexvpn-spoke.html
- IKEv2: RFC 7296 — https://www.rfc-editor.org/info/rfc7296
- IPsec architecture: RFC 4301 — https://www.rfc-editor.org/info/rfc4301
- Existing PVNetwork generic IKEv2/IPsec evidence: `research/protocols/004-ikev2-ipsec/`
- Public IKE/IPsec implementation reference: https://github.com/strongswan/strongswan pinned in current campaign at `5011838b32ac88ba9593af4b727932c34b28e127`; COPYING is GPLv2.

Cisco IOS XE/FlexVPN implementation source is proprietary; vendor documentation is therefore the canonical source for Cisco-specific behavior and source-tree/build gates are evidence-backed N/A.

## 20 V1 gates

1. **Top clients/implementations — PASS.** Cisco IOS XE FlexVPN server/client/router implementation is canonical. Cisco documents interoperability with IKEv2 remote-access clients including Cisco AnyConnect IKEv2 and Windows IKEv2 in the server guide. strongSwan is a reusable generic IKEv2/IPsec reference, not proof of complete Cisco FlexVPN extension compatibility.
2. **Canonical sources pinned — PASS.** Current Cisco 2026 guide, IOS XE 17 guide, RFC 7296/4301 and pinned strongSwan source are recorded. Cisco source pin is N/A because implementation source is proprietary.
3. **License/reuse — PASS.** Cisco code/branding is proprietary and **REFERENCE-ONLY / DO-NOT-COPY**. RFC behavior is specification reference. strongSwan is GPLv2 and is a **SEPARATE-COMPONENT REUSE CANDIDATE subject to GPL/legal architecture review**. Generic IKEv2 adapter/profile logic written by PVNetwork may be proprietary if it does not copy restricted code.
4. **Source-tree inventory — PASS via mixed evidence.** Cisco source tree is unavailable/proprietary, so N/A for direct source inventory. Public IKEv2 engine analysis is inherited from the pinned strongSwan and entry 004 dossiers; Cisco-specific behavior is documented from official guide chapters rather than fabricated source paths.
5. **Languages/build — PASS.** Cisco internal language/build is proprietary/unknown and explicitly N/A for reuse. strongSwan is primarily C; PVNetwork's own product adapter remains implementation-phase work.
6. **Architecture — PASS.** FlexVPN layers IKEv2 authentication/SA management, IPsec data protection, tunnel/virtual-template interfaces, authorization/AAA/RADIUS and route/configuration attributes. Cisco describes one unified framework spanning remote access and routed topologies. PVNetwork must represent Cisco-specific authorization/configuration attributes as extensions over the generic IKEv2/IPsec profile.
7. **Engine/core — PASS.** Use OS-native or audited IKEv2/IPsec engine adapters where appropriate; never reimplement IKE/IPsec cryptography. Cisco IOS XE is an interoperability peer/reference, not an embeddable engine. strongSwan is the primary public engine reference for non-Cisco infrastructure.
8. **UI/menu map — PASS via implementation-role N/A.** FlexVPN itself defines no portable consumer GUI. Cisco administration is IOS XE CLI/configuration and operational show/debug state. Remote-access client UI belongs to the selected IKEv2/AnyConnect client, already researched separately. Future PVNetwork UI should expose only generic IKEv2 fields plus explicitly supported Cisco extension fields, not mimic Cisco branding/UI.
9. **Config/import/export — PASS.** Cisco uses `crypto ikev2 profile`, authorization policy, IPsec profile and virtual-template/tunnel configuration. The server guide documents CFG_REQUEST/CFG_REPLY/CFG_SET/CFG_ACK and Cisco-specific configuration URL/version attributes. There is no universal `flexvpn://` profile/QR format; portable import/export is N/A unless PVNetwork defines its own canonical schema.
10. **Persistence/secrets — PASS.** Cisco router configuration/AAA/PKI persist policy and credentials; generic IKEv2 credentials/certificates belong in protected OS/engine storage. No FlexVPN-specific mobile database/keychain exists. PVNetwork must keep certificates/private keys/PSKs separate from non-secret routing and Cisco-extension metadata.
11. **Lifecycle — PASS.** IKEv2 performs peer authentication and SA establishment; authorization/configuration mode supplies addresses/routes/policy; IPsec protects traffic; tunnel interfaces and routing own the data path; reconnect/CoA/session-lifetime features are Cisco framework functions. This remains a typed IKEv2/IPsec lifecycle with Cisco policy extensions.
12. **Platform integration — PASS.** Canonical FlexVPN router implementation is Cisco IOS XE. Remote-access interoperability is IKEv2-client dependent; generic Windows/Apple/Android/Linux IKEv2 capability must not be marketed as full FlexVPN compatibility without exact testing. Android TV is not a distinct FlexVPN protocol target. Platform certification is later work.
13. **Logs/diagnostics — PASS.** Cisco operational evidence includes IKEv2/IPsec session/state and FlexVPN troubleshooting commands; the current troubleshooting guide covers IKEv2, VTI, NHRP, IPsec, routing and VRF boundaries. PVNetwork diagnostics should retain engine/IKE/IPsec/route errors separately and redact identities/secrets.
14. **Images/assets — PASS via N/A.** Cisco diagrams/screenshots/logos are copyrighted reference material. No Cisco visual asset is required for implementation; PVNetwork uses its own assets and may retain official documentation links only.
15. **Fork ecosystem — PASS.** There is no canonical open-source Cisco FlexVPN fork to adopt. strongSwan and OS-native IKEv2 stacks are generic interoperability/engine references, not forks. Avoid community projects claiming 'FlexVPN' unless exact IKEv2/Cisco-extension behavior is proven.
16. **Issues/PRs/releases/advisories — PASS.** Cisco's current guide and IOS XE 17 troubleshooting material capture version-specific restrictions and operational boundaries. Generic IKEv2/IPsec implementation issues remain tracked under entry 004/strongSwan. Required mitigation: capability/version-specific compatibility matrix rather than a Boolean `supports FlexVPN` flag.
17. **Forums/docs — PASS.** Current Cisco official documentation plus RFC 7296/4301 are authoritative for V1. No community forum claim is needed to override vendor/spec behavior.
18. **Tests/CI — PASS via evidence boundary.** Cisco internal CI is proprietary/unavailable (N/A). Public IKEv2 engine tests belong to strongSwan/generic IKEv2 evidence. Future PVNetwork acceptance must test exact Cisco IOS XE versions/topologies, authentication modes, configuration attributes, route push/pull, reconnect and failure cleanup; no runtime result is invented here.
19. **Store/privacy/security — PASS.** Router-side FlexVPN is not a consumer Store artifact. Remote-access apps inherit platform VPN entitlements/policies from their IKEv2 adapter. Certificates, PSKs, EAP/AAA identities, RADIUS-derived attributes and pushed routes/DNS are sensitive. Store readiness must be rechecked at implementation/release time and is not a V1 research gate.
20. **PVNetwork reuse decision — PASS.** **Reuse the generic IKEv2/IPsec canonical profile and engine adapters; add a Cisco FlexVPN capability/extension layer only where official evidence and later interoperability tests justify it.** Cisco IOS XE is reference-only. Do not create a separate cryptographic FlexVPN core and do not claim FlexVPN merely because generic IKEv2 connects.

## Explicit uncertainties

- Cisco implementation source/build/test internals are proprietary.
- Exact interoperability varies with IOS XE release, topology, authentication, AAA/RADIUS and Cisco-specific configuration attributes.
- Generic IKEv2 success is not proof of complete FlexVPN compatibility; this must remain an explicit later certification state.

## Completion decision

All 20 V1 research gates have traceable evidence or evidence-backed N/A treatment. Entry 072 is eligible for **`COMPLETE-RESEARCH-v1`**. This is research completion only, not implementation or Cisco interoperability certification.
