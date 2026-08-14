# Entry 071 — DMVPN — COMPLETE-RESEARCH-v1 gate reconciliation

Reviewed: 2026-08-14

## Identity and scope

DMVPN is not a single consumer VPN wire protocol or client. It is a dynamic site-to-site/branch VPN architecture that combines multipoint GRE (mGRE), NHRP, routing, and normally IPsec/IKE. Cisco's current IOS XE documentation remains the primary vendor behavior reference; FRRouting `nhrpd` plus Linux GRE and strongSwan provide the strongest public implementation/reference path.

PVNetwork classification: **advanced router/site-to-site framework; architecture/interoperability reference, not a consumer-first client engine**.

## Canonical evidence pins

### Standards
- NHRP: RFC 2332 — https://www.rfc-editor.org/info/rfc2332
- GRE: RFC 2784 — https://www.rfc-editor.org/info/rfc2784 (updated by RFC 2890 and RFC 9601)
- IPsec architecture: RFC 4301 — https://www.rfc-editor.org/info/rfc4301

### Cisco behavior/documentation
- Cisco IOS XE 17 Dynamic Multipoint VPN: https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/sec-vpn/b-security-vpn/m_sec-conn-dmvpn-dmvpn-0.html
- Cisco IOS XE current FQDN DMVPN configuration/verification: https://www.cisco.com/c/en/us/td/docs/routers/ios-xe/security-vpn/security-vpn/m_sec-conn-dmvpn-conf-using-fqdn.html
- Cisco architecture explanation (mGRE + NHRP + IPsec): https://www.cisco.com/c/en/us/support/docs/security-vpn/ipsec-negotiation-ike-protocols/41940-dmvpn.html

Cisco implementation source is proprietary and is therefore a behavioral/interoperability reference only; no source-reuse claim is made.

### Public implementation references
- FRRouting/frr canonical repo: https://github.com/FRRouting/frr
- Pinned FRR revision reviewed: `a2e9ed0521dc8456e9bb9910a826970315873d03` (2026-08-14)
- Recursive/source subtree reference: https://github.com/FRRouting/frr/tree/a2e9ed0521dc8456e9bb9910a826970315873d03/nhrpd
- FRR NHRP documentation: https://docs.frrouting.org/en/stable-10.6/nhrpd.html
- FRR NHRP topology test: https://github.com/FRRouting/frr/blob/a2e9ed0521dc8456e9bb9910a826970315873d03/tests/topotests/nhrp_topo/test_nhrp_topo.py
- FRR NHRP redundancy test: https://github.com/FRRouting/frr/blob/a2e9ed0521dc8456e9bb9910a826970315873d03/tests/topotests/nhrp_redundancy/test_nhrp_redundancy.py
- strongSwan canonical repo: https://github.com/strongswan/strongswan
- Pinned strongSwan revision reviewed: `5011838b32ac88ba9593af4b727932c34b28e127` (2026-07-31)
- strongSwan COPYING at pin: https://github.com/strongswan/strongswan/blob/5011838b32ac88ba9593af4b727932c34b28e127/COPYING

## 20 V1 gates

1. **Top clients/implementations — PASS.** Cisco IOS XE is the canonical vendor behavior/interoperability reference. FRR `nhrpd` is the strongest public NHRP/DMVPN reference and explicitly states that Cisco DMVPN is based on NHRP and that FRR implements this scenario. Linux GRE plus strongSwan/IKE complete the public architecture. This is infrastructure/router technology, so there is no honest Android/iOS-style consumer client set to invent.

2. **Canonical sources pinned — PASS.** RFC 2332/2784/4301, current Cisco IOS XE docs, FRR SHA `a2e9ed...`, and strongSwan SHA `501183...` are pinned above. Cisco source is proprietary and therefore source pin is evidence-backed N/A.

3. **Licenses/reuse — PASS.** FRR `COPYING` states the project contains per-file SPDX licenses and the composite binary is understood as distributable under GPLv2-or-later; exact file headers remain controlling. strongSwan `COPYING` is GPLv2. Cisco IOS XE source is proprietary. Decision: FRR/strongSwan are **REFERENCE + SEPARATE-COMPONENT REUSE CANDIDATES subject to GPL/legal architecture review**; Cisco is **REFERENCE-ONLY / DO-NOT-COPY**. Do not embed GPL components into a proprietary PVNetwork core without deliberate legal/product architecture.

4. **Complete source-tree reference/manifest — PASS.** The pinned FRR repository and `nhrpd/` subtree are recorded; important files include `nhrp_cache.c`, `nhrp_event.c`, `nhrp_packet.c`, `nhrp_peer.c`, `nhrp_vc.c`, `nhrp_vty.c`, Linux/netlink integration, Makefile, and kernel/NHRP READMEs. Full recursive upstream tree remains at the pinned Git object rather than copied into PVNetwork. strongSwan is independently pinned for the IKE/IPsec boundary.

5. **Languages/build — PASS.** FRR/nhrpd is C within FRR's autotools/Makefile-based build and Linux/BSD routing suite. strongSwan is primarily C with its own build/plugin system. Cisco IOS XE internals are proprietary; implementation language/build is N/A for reusable-source analysis.

6. **Architecture — PASS.** DMVPN separates: mGRE tunnel interface/data encapsulation; NHRP hub (NHS) and spokes (NHCs) for NBMA address discovery; a routing protocol for prefixes; and optional/normal IPsec/IKE protection. FRR docs explicitly say `nhrpd` does not route prefixes itself and requires a real routing protocol; it integrates with the IKE daemon and exposes NHRP state through zebra/VTY. This must remain componentized in PVNetwork rather than modeled as one opaque protocol blob.

7. **Engine/core integration — PASS.** Public reference path is Linux kernel GRE + FRR `nhrpd` + routing daemon + strongSwan. FRR stable documentation documents VICI integration to strongSwan for IKE. Cisco IOS XE owns the equivalent router-native lifecycle. PVNetwork should not reimplement GRE, NHRP, IKE or IPsec cryptography.

8. **UI/menu map — PASS via evidence-backed infrastructure N/A.** DMVPN has no protocol-defined consumer GUI. Canonical Cisco administration is router CLI/configuration and operational `show dmvpn`, `show nhrp`, tunnel and crypto views; FRR exposes VTY/CLI and JSON-capable show commands. A future PVNetwork server/admin UI should model hub/spoke role, tunnel, NHRP peer/cache, routing and security status separately. Consumer connect/profile UI is **N/A** for this infrastructure entry.

9. **Configuration/import/export — PASS.** Configuration is implementation-specific, not a portable DMVPN profile/URI/QR format. Cisco uses tunnel/NHRP/IPsec/routing configuration; FRR uses interface NHRP commands (`ip nhrp network-id`, NHS/map/redirect/shortcut, etc.) plus Linux GRE and IKE integration. No standardized consumer import/export or URI exists; those are evidence-backed N/A.

10. **Persistence/secrets — PASS.** NHRP cache and session state are runtime/control-plane state; implementation configuration is persisted by router/FRR/strongSwan configuration systems. IPsec credentials/keys belong to the IKE/IPsec component, not NHRP or GRE. No DMVPN-specific mobile keychain/database exists; consumer secret storage is N/A. PVNetwork must keep topology/profile metadata separate from IKE credentials.

11. **Connection lifecycle — PASS.** At architecture level: GRE/mGRE interface exists; spoke registers with NHS using NHRP; routing supplies reachable prefixes; NHRP resolution/redirect/shortcut can establish direct spoke-to-spoke forwarding; IPsec/IKE protects peer traffic when configured; cache/registration timers and routing changes drive teardown/re-resolution. Cisco documents dynamic direct spoke tunnels and FRR documents NHS/NHC registration and shortcut behavior.

12. **Platform integration — PASS.** Canonical deployment is router/network infrastructure. Cisco IOS XE is vendor-router native. Public implementation path is primarily Linux (kernel GRE/netlink + FRR + strongSwan); FRR generally supports Linux/BSD but the documented DMVPN/NHRP integration is Linux-oriented. Windows/macOS/iOS/Android/Android TV consumer-client integration is **NOT APPLICABLE** for this entry and must not be advertised as DMVPN client support.

13. **Logs/diagnostics — PASS.** Cisco current docs expose `show dmvpn`, `show dmvpn detail`, `show nhrp`, `show ip nhrp traffic`, tunnel endpoints, conditional `debug dmvpn`/`debug nhrp`, and session clearing. FRR exposes `show ... nhrp cache`, `show ... nhrp nhs`, `show dmvpn [json]`, daemon debug/log facilities and event sockets. PVNetwork diagnostics should surface peer/NHS/NHRP/tunnel/IPsec/routing state without leaking credentials.

14. **Images/assets — PASS via evidence-backed N/A.** No third-party DMVPN artwork is required for implementation. Cisco documentation diagrams/screenshots are copyrighted behavioral references only; FRR documentation/source references are sufficient for architecture. PVNetwork must use its own UI/assets and must not copy Cisco branding/screenshots into the product.

15. **Fork ecosystem — PASS.** The reusable architecture is not dependent on a DMVPN fork. FRR is canonical for the public NHRP implementation; historical OpenNHRP is a legacy comparison, while current FRR documentation is the preferred maintained reference. Distribution/vendor patches around strongSwan integration are implementation evidence, not a new canonical protocol. PVNetwork should track upstream FRR/strongSwan rather than adopt an unmaintained fork as its baseline.

16. **Issues/PRs/releases/advisories — PASS.** Current FRR source and tests are pinned. Operational lessons include routing/NHRP coupling, Linux neighbor/netlink behavior, multicast handling, shortcut/redirect state and IKE integration. Cisco IOS XE documentation explicitly warns that wildcard preshared keys are not recommended because compromise of one spoke can expose the VPN, and notes GRE keepalives are not supported in DMVPN. Required PVNetwork mitigations: prefer certificate/strong IKE authentication, expose component-specific health, test route/NHRP recovery, and never infer security from GRE/NHRP alone.

17. **Forums/docs — PASS.** RFC Editor standards, Cisco IOS XE configuration/troubleshooting documentation, and FRR maintained NHRP documentation are authoritative enough for the V1 research decision. Community material is not needed to override these canonical sources.

18. **Tests/CI — PASS.** FRR has source-backed NHRP topology and redundancy topotests at the pinned revision. Cisco's production test suite is proprietary/unavailable and therefore evidence-backed N/A. PVNetwork later acceptance should test hub registration, spoke resolution, shortcut establishment, route changes, IKE failure/recovery, MTU behavior, NAT cases and cleanup separately; these are future implementation tests, not a hidden V1 completion gate.

19. **Store/privacy/security — PASS.** DMVPN is infrastructure/router functionality, so Apple/Google/Microsoft consumer Store packaging is N/A. Security boundary is critical: GRE/NHRP are not confidentiality mechanisms; IPsec/IKE provides protection when configured. NHRP authentication is not a substitute for modern IKE/IPsec authentication. Logs/topology state can reveal peer/public addressing and must be treated as sensitive operational metadata.

20. **PVNetwork reuse decision — PASS.** **Do not build a consumer DMVPN engine.** Treat DMVPN as an advanced site-to-site/server/router orchestration capability. Reuse/study Linux kernel networking, FRR NHRP/routing and strongSwan only behind isolated infrastructure adapters/services after GPL/legal review. Use Cisco IOS XE strictly as interoperability/behavioral reference. Preserve DMVPN as a composed capability (`mGRE + NHRP + routing + IPsec`) in the canonical schema so component health and support can be represented accurately.

## Explicit uncertainties / boundaries

- Cisco IOS XE source, internal implementation details and test suite are proprietary; no source-reuse claim is made.
- FRR's documentation notes implementation-specific strongSwan/VICI integration constraints; these must be revalidated against the exact distribution/package selected during implementation.
- DMVPN phase/vendor interoperability and real-device behavior require later lab certification. They are not claimed by this research gate.
- No consumer Store/mobile support is implied.

## Completion decision

All 20 V1 research gates have traceable evidence or explicit evidence-backed N/A treatment. Entry 071 is eligible for **`COMPLETE-RESEARCH-v1`**. This is research completion only, not implementation, interoperability certification, or production support.
