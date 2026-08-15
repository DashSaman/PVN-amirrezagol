# Entry 064 — GRE over IPsec COMPLETE-REFERENCE-v2 Audit

Reviewed: 2026-08-15

Scope: the explicit composition of GRE (entry 063) with IPsec/IKE security (entries 004–007 / strongSwan family evidence). It is not a new cryptographic protocol and is not DMVPN.

## Primary evidence

- `research/protocols/063-gre/` — RFC 2784/2890, Linux/iproute2 pins, GRE wire/UI/platform boundaries.
- `research/upstreams/strongswan-family/reference-v2/` — IPsec/IKE server/client/install/UI/crypto/wire/ports/topology/source/supply-chain/lifecycle evidence.
- strongSwan route-based VPN docs: https://docs.strongswan.org/docs/latest/features/routeBasedVpn.html
- strongSwan IPsec protocol docs: https://docs.strongswan.org/docs/latest/howtos/ipsecProtocol.html
- Cisco GRE-over-IPsec guide: https://www.cisco.com/c/en/us/td/docs/switches/lan/c9000/lyr3-fwd/gre/gre-configuration-guide/m-gre-over-ipsec.html

## Exact 16 gates

1. **Server ecosystem — PASS.** Linux GRE+XFRM+strongSwan/native IPsec and Cisco native GRE/IPsec are mapped; composition boundary is explicit.
2. **Installers/deployment projects — PASS.** No standalone GRE-over-IPsec installer exists; Linux composition and Cisco native configuration are documented, with strongSwan installer evidence reused rather than duplicated.
3. **Server install matrix — PASS.** Linux and documented Cisco paths are identified; containers/orchestration and unproven consumer platforms are explicitly bounded.
4. **Server UI/menu maps — PASS / N/A.** No protocol-owned panel; GRE and IKE/IPsec management surfaces are mapped separately, including Cisco workflow.
5. **Client install matrix — PASS.** Infrastructure peer model and Linux/Cisco support are documented; mobile/desktop generic composition support is not fabricated.
6. **Client UI/menu maps — PASS / N/A.** No canonical consumer UI; GRE routing fields and IPsec security fields are separated.
7. **Cryptography — PASS.** All security is correctly attributed to IKE/IPsec/ESP; GRE itself contributes none. Existing authoritative IPsec crypto dossier is reused.
8. **Data path/wire flow — PASS.** Inner packet -> GRE -> IPsec/ESP -> outer network -> IPsec validation/decryption -> GRE decapsulation is documented, including MTU and SA/tunnel-state separation.
9. **Ports/transports/handshake — PASS.** GRE protocol 47, IKE UDP 500/NAT-T UDP 4500, ESP protocol 50 and IKE-vs-GRE handshake boundaries are explicit.
10. **Deployment topologies — PASS.** Site-to-site, Linux route-based, Cisco tunnel protection, VRF and multipoint/DMVPN boundary are documented.
11. **Source/license/activity pins — PASS.** GRE open-source pins are in entry 063; strongSwan-family source/license/activity evidence is already pinned in the shared V2 dossier; Cisco is proprietary reference-only.
12. **Security/supply-chain risks — PASS.** Native OS/IKE paths are preferred; privileged networking, credential/configuration ownership and avoidance of unnecessary third-party installers are explicit.
13. **Upgrade/uninstall/rollback — PASS.** Lifecycle belongs to kernel/iproute2/IKE stack or vendor network OS; there is no separate composition package lifecycle.
14. **Differences/uncertainties — PASS.** GRE vs IPsec responsibilities, transport/tunnel modes, NAT-T, DMVPN boundary and unproven consumer-platform composition support are explicit.
15. **REFERENCE_INDEX — PASS.** The index links all entry-specific files and reused shared evidence.
16. **Latest handoff exact continuation — PASS when companion handoff is committed.** Next entry is 065 IP-in-IP/IPIP; bare IPIP must remain separate from 066 IPIP-over-IPsec.

## Decision

**APPROVED: Entry 064 may be promoted to `COMPLETE-REFERENCE-v2`.**

This is research/reference completion only. It does not claim PVNetwork implementation, device interoperability testing, Store support or production certification.
