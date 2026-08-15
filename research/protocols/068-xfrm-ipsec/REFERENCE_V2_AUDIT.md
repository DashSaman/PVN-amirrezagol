# Entry 068 — XFRM/IPsec COMPLETE-REFERENCE-v2 Audit

Reviewed: 2026-08-15

Scope: Linux XFRM virtual interfaces as route-based local interfaces for existing IPsec policies/SAs. VTI/IPsec is entry 067; XFRM hardware offload is an optimization boundary, not a separate VPN protocol.

## Exact 16 gates

1. **Server ecosystem — PASS.** Linux XFRM interface kernel/iproute2 implementation and strongSwan IKE/IPsec integration are mapped and pinned.
2. **Installer/deployment projects — PASS.** Kernel/iproute2 plus reviewed IKE/IPsec packages; minimum versions and `xfrmi` fallback are documented; no standalone installer invented.
3. **Server install matrix — PASS.** Linux version, IPv4/IPv6, namespaces, VRF and non-Linux applicability boundaries are explicit.
4. **Server UI/menu maps — PASS / N/A.** Interface IDs/link/routes plus separate IKE/IPsec controls are mapped; no protocol-owned web panel.
5. **Client install matrix — PASS.** Linux-local infrastructure scope and non-Linux boundaries are explicit.
6. **Client UI/menu maps — PASS / N/A.** No portable consumer UI; interface-ID and security-state separation is documented.
7. **Cryptography — PASS.** XFRM interface adds no crypto; security properties are attributed to IKE/IPsec/ESP shared evidence; interface IDs are not secrets.
8. **Data path/wire flow — PASS.** Route -> XFRM interface ID -> matching policy/SA -> IPsec data plane is documented, including no-policy/no-interface behavior.
9. **Ports/transports/handshake — PASS.** XFRM interface has no independent port/handshake or extra wire header; IKE/IPsec owns negotiation and transport.
10. **Deployment topologies — PASS.** Shared/per-direction interfaces, namespaces, VRFs and multiple IPsec modes are documented.
11. **Source/license/activity pins — PASS.** Current-reviewed Linux XFRM source, iproute2 and shared IKE/IPsec source/license evidence are pinned.
12. **Security/supply-chain risks — PASS.** Privileged network/policy administration, native/package preference and offload boundary are explicit.
13. **Upgrade/uninstall/rollback — PASS.** Kernel/iproute2/IKE lifecycle plus interface/route/policy rollback are covered; no separate daemon lifecycle invented.
14. **Differences/uncertainties — PASS.** XFRM vs VTI endpoints/marks, address-family/mode flexibility, namespace/VRF and non-Linux limitations are explicit.
15. **REFERENCE_INDEX — PASS.** Complete dossier and authoritative/shared evidence are indexed with next action.
16. **Latest handoff exact continuation — PASS when companion handoff is committed.** Next entry is 069 VXLAN.

## Decision

**APPROVED: Entry 068 may be promoted to `COMPLETE-REFERENCE-v2`.**

Research/reference completion only; no implementation/device/interoperability/Store/production certification is claimed.
