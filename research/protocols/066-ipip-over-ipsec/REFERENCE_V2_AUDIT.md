# Entry 066 — IPIP over IPsec COMPLETE-REFERENCE-v2 Audit

Reviewed: 2026-08-15

Scope: research/reference reconciliation of the IPIP + IPsec composition. Bare IPIP evidence is in entry 065. Security-layer evidence is in completed entries 004–007 and `research/upstreams/strongswan-family/reference-v2/`. VTI/IPsec and XFRM/IPsec remain separate entries 067 and 068.

## Exact 16-gate reconciliation

1. Server implementation ecosystem — PASS: Linux/native IPIP and established IPsec/IKE ecosystem mapped with component boundaries.
2. Installer/deployment projects — PASS: native/package lifecycle evidence reused; no standalone composition installer is invented.
3. Server install matrix — PASS: relevant Linux/infrastructure path and unsupported/unproven environments are explicit.
4. Server UI/menu maps — PASS/N/A: tunnel/interface management and security-policy management are treated as separate implementation surfaces.
5. Client install matrix — PASS: infrastructure-peer scope and target-platform uncertainties are explicit.
6. Client UI/menu maps — PASS/N/A: no canonical consumer application is claimed; composition-specific field separation is documented.
7. Cryptography — PASS: cryptographic properties are attributed only to the IPsec/IKE layer; IPIP itself remains non-cryptographic.
8. Data path/wire flow — PASS: encapsulation, security processing, decapsulation, overhead and state separation are documented at architectural level.
9. Ports/transports/handshake — PASS: IPIP transport identity and the separate IKE/IPsec negotiation/data-plane identities are documented without treating them as one protocol.
10. Deployment topologies — PASS: point-to-point/site-to-site infrastructure composition is mapped and adjacent route-based technologies are separated.
11. Source/license/activity pins — PASS: entry 065 contains current Linux/iproute2 pins; the shared IPsec dossier contains security-layer pins and licenses.
12. Security/supply-chain risks — PASS: privileged networking/configuration ownership and preference for reviewed native/package paths are explicit.
13. Upgrade/uninstall/rollback — PASS: lifecycle is inherited from the selected kernel/network/IKE components and configuration owner.
14. Differences/uncertainties — PASS: IPIP vs IPsec responsibilities, interface vs security-association state, platform uncertainty, and VTI/XFRM distinctions are explicit.
15. REFERENCE_INDEX — PASS: all entry-specific and reused shared evidence is indexed.
16. Latest handoff exact continuation — PASS when companion handoff is committed: next entry is 067 VTI/IPsec.

## Decision

**APPROVED: Entry 066 may be promoted to `COMPLETE-REFERENCE-v2`.**

This is research/reference completion only. It does not claim implementation, interoperability/device testing, Store readiness or production certification.
