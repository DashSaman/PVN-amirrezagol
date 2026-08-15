# Entry 065 — IP-in-IP / IPIP COMPLETE-REFERENCE-v2 Audit

Reviewed: 2026-08-15

Scope: bare IPv4-in-IPv4 encapsulation per RFC 2003 and the current Linux/iproute2 implementation. IPIP-over-IPsec is entry 066 and is deliberately excluded from bare-IPIP security claims.

## Primary evidence

- RFC 2003: https://www.rfc-editor.org/rfc/rfc2003.html
- Linux kernel `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/ipv4/ipip.c`, SPDX GPL-2.0-or-later.
- iproute2 `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `man/man8/ip-tunnel.8`.

## Exact 16 gates

1. **Server implementation/project ecosystem — PASS.** IPIP is a symmetric endpoint capability; Linux kernel/iproute2 are pinned canonical open-source references. No standalone daemon is fabricated.
2. **Official/major installer/deployment projects — PASS / N/A.** Native kernel + distribution iproute2 is the deployment path; there is no protocol-defined one-click installer/container/panel.
3. **Server OS/container/orchestration matrix — PASS.** Linux support and container/Kubernetes boundaries are documented; unproven platforms remain explicitly unpromoted.
4. **Server UI/menu map — PASS / N/A.** No protocol-owned panel; Linux `ip tunnel` fields/actions are mapped.
5. **Client install matrix — PASS.** Infrastructure-peer model and target-platform evidence boundaries are explicit.
6. **Client UI/menu maps — PASS / N/A.** No canonical consumer application exists; evidence-backed editable fields and N/A consumer concepts are recorded.
7. **Cryptographic design — PASS.** Bare IPIP has no intrinsic encryption/authentication/KEX/replay security; header checksum is not cryptographic. Entry 066 owns IPsec composition.
8. **Data path/wire flow — PASS.** Inner IPv4 -> outer IPv4 Protocol 4 -> decapsulation, MTU/fragmentation and visibility are documented.
9. **Ports/transports/handshake — PASS.** IP protocol 4, no TCP/UDP port, no connection/authentication handshake, NAT-T or proxy fallback are explicit.
10. **Deployment topologies — PASS.** Point-to-point/site-to-site and routing/plane boundaries are mapped; security composition stays separate.
11. **Source/license/activity pins — PASS.** Current-reviewed Linux and iproute2 revisions are exact; Linux `ipip.c` SPDX is recorded.
12. **Security/supply-chain risks — PASS.** Native OS paths are preferred; bare-IPIP lack of authentication/encryption and firewall protocol-4 exposure are explicit; unnecessary privileged scripts are rejected.
13. **Upgrade/uninstall/rollback — PASS.** Lifecycle belongs to kernel/iproute2/distribution and configuration owner; no separate daemon package lifecycle is invented.
14. **Differences/uncertainties — PASS.** Bare IPIP versus IPIP-over-IPsec is explicit; generic consumer platform support remains UNKNOWN/N/A without evidence.
15. **REFERENCE_INDEX — PASS.** All V2 files, specs, pins, boundaries and next action are indexed.
16. **Latest handoff exact continuation — PASS when companion handoff is committed.** Continuation is entry 066 IPIP over IPsec.

## Decision

**APPROVED: Entry 065 may be promoted to `COMPLETE-REFERENCE-v2`.**

This is research/reference completion only and does not assert implementation, device testing, Store readiness or production support.
