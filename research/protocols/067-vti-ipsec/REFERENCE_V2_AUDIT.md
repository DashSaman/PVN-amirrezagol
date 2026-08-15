# Entry 067 — VTI/IPsec COMPLETE-REFERENCE-v2 Audit

Reviewed: 2026-08-15

Scope: Linux Virtual Tunnel Interface (VTI/VTI6) as a route-based interface abstraction over existing IPsec policies/SAs. XFRM interfaces are entry 068 and are not collapsed into VTI.

## Exact 16 gates

1. **Server implementation ecosystem — PASS.** Linux kernel VTI/iproute2 are pinned; established IKE/IPsec ecosystem is reused from completed shared evidence.
2. **Installer/deployment projects — PASS.** VTI is kernel/iproute2 capability plus reviewed IKE/IPsec packages; no standalone installer is fabricated.
3. **Server install matrix — PASS.** Linux/version/address-family/container and non-Linux boundaries are documented.
4. **Server UI/menu maps — PASS / N/A.** Linux VTI interface/mark/route surface and separate IPsec security controls are mapped; no protocol-owned web panel exists.
5. **Client install matrix — PASS.** VTI is correctly scoped to Linux infrastructure peers; other platform IPsec support is not misreported as VTI.
6. **Client UI/menu maps — PASS / N/A.** No canonical portable consumer UI; interface vs security state/fields are documented separately.
7. **Cryptography — PASS.** VTI adds no crypto; all security is attributed to IKE/IPsec/ESP shared evidence; VTI marks are not crypto keys.
8. **Data path/wire flow — PASS.** Route -> VTI mark/interface -> matching XFRM policy/SA -> IPsec data plane is documented; no extra GRE-like header is invented.
9. **Ports/transports/handshake — PASS.** VTI has no independent port/handshake; negotiation/data-plane behavior belongs to IPsec/IKE.
10. **Deployment topologies — PASS.** Route-based site-to-site, broad-selector/routing control, multiple interfaces and VTI limitations are documented.
11. **Source/license/activity pins — PASS.** Current-reviewed Linux `ip_vti.c`, iproute2 and shared IKE/IPsec sources/licenses are pinned.
12. **Security/supply-chain risks — PASS.** Privileged interface/policy changes and native/package preference are explicit; no unnecessary installer is introduced.
13. **Upgrade/uninstall/rollback — PASS.** Kernel/iproute2/IKE component lifecycle plus interface/route/policy rollback is documented by ownership; no separate VTI daemon lifecycle is invented.
14. **Differences/uncertainties — PASS.** VTI vs XFRM interfaces, endpoint/address-family/mode limitations, mark semantics and non-Linux boundaries are explicit.
15. **REFERENCE_INDEX — PASS.** Entry-specific and shared evidence plus exact next action are indexed.
16. **Latest handoff exact continuation — PASS when companion handoff is committed.** Next entry is 068 XFRM/IPsec.

## Primary evidence

- Linux kernel `net/ipv4/ip_vti.c` at `15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`.
- iproute2 at `da2ccdf862cb1eab45de082cc71fcb4e5d712e78`.
- strongSwan official route-based VPN documentation.
- `research/upstreams/strongswan-family/reference-v2/` and entries 004–007.

## Decision

**APPROVED: Entry 067 may be promoted to `COMPLETE-REFERENCE-v2`.**

Research/reference completion only; no implementation, device/interoperability, Store or production certification is claimed.
