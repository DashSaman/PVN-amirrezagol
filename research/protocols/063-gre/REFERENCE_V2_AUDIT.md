# Entry 063 — GRE COMPLETE-REFERENCE-v2 Audit

Reviewed: 2026-08-15

Scope: **bare Generic Routing Encapsulation (GRE)** per RFC 2784 plus RFC 2890 key/sequence extensions. GRE-over-IPsec is entry 064 and is deliberately excluded from bare-GRE security claims.

## Authoritative evidence set

- RFC 2784: https://www.rfc-editor.org/rfc/rfc2784.html
- RFC 2890: https://www.rfc-editor.org/rfc/rfc2890.html
- Linux kernel `torvalds/linux@15ef2f78c49d20d53ec7c0f1c9b40b02e089f2d6`, `net/ipv4/ip_gre.c`, SPDX GPL-2.0-or-later.
- iproute2 `iproute2/iproute2@da2ccdf862cb1eab45de082cc71fcb4e5d712e78`, `man/man8/ip-tunnel.8`.
- Cisco IOS XE 17.x official tunnel configuration guide: https://www.cisco.com/c/en/us/td/docs/routers/ios/config/17-x/ip-routing/b-ip-routing/m_ir-impl-tun-xe.html
- Juniper official GRE tunnel guide: https://www.juniper.net/documentation/us/en/software/junos/interfaces-encryption/topics/topic-map/configuring-gre-tunnel-interfaces.html

## Exact 16 second-layer gates

1. **Server implementation/project ecosystem mapped — PASS.** GRE is a symmetric endpoint capability, not a server daemon. Linux kernel/iproute2 are pinned open-source endpoint references; Cisco IOS XE and Junos are proprietary interoperability references. See `SERVER_IMPLEMENTATIONS.md`.
2. **Official and major community installer/deployment projects reviewed — PASS / evidence-backed N/A.** Bare GRE has no canonical standalone installer/daemon/container/panel. Linux uses kernel + OS iproute2; network OSes provide built-in capability. Introducing third-party one-click installers is unnecessary and would add privileged supply-chain risk. See `SERVER_INSTALLERS_AND_PROJECTS.md`.
3. **Server OS/container/orchestration install matrix completed — PASS.** Linux, Cisco/Junos, container/Kubernetes boundary, and unproven Windows/macOS/mobile claims are explicitly distinguished. See `SERVER_INSTALL_MATRIX.md`.
4. **Server panel/UI/menu maps completed — PASS / N/A.** Protocol-owned panel is absent; Linux CLI/netlink and vendor configuration trees are mapped without inventing accounts/certs/UI. See `SERVER_UI_AND_MENUS.md`.
5. **Client install matrix completed across relevant OS targets — PASS.** Infrastructure peer model is explicit; Linux is supported evidence, network OSes are vendor-supported, unsupported/unproven consumer platforms are N/A/UNKNOWN rather than fabricated. See `CLIENT_INSTALL_MATRIX.md`.
6. **Major client UI/menu maps completed separately — PASS / N/A.** No canonical GRE consumer client exists; Linux CLI fields and future product-boundary guidance are documented. See `CLIENT_UI_AND_MENUS.md`.
7. **Cryptographic design documented from authoritative specifications/source — PASS.** Bare GRE has no confidentiality/authentication/KEX/PKI. Optional checksum, Key and Sequence fields are correctly classified as non-cryptographic. See `CRYPTOGRAPHY.md`.
8. **Data path/wire flow documented — PASS.** Outer IP + GRE + payload encapsulation/decapsulation, optional RFC 2890 fields, Linux path, MTU/PMTU and visible metadata are documented. See `DATA_PATH_AND_WIRE_FLOW.md`.
9. **Ports/transports/handshake documented — PASS.** IPv4 GRE uses IP protocol 47, no TCP/UDP port and no connection/authentication handshake; vendor keepalive is separated from base RFC behavior. See `PORTS_TRANSPORTS_AND_HANDSHAKE.md`.
10. **Deployment topologies documented — PASS.** Point-to-point/site-to-site infrastructure use is mapped; mGRE/DMVPN, Ethernet-over-GRE and GRE-over-IPsec are identified as separate profiles/compositions. See `DEPLOYMENT_TOPOLOGIES.md`.
11. **Source/license/activity pins recorded for server and client projects — PASS.** Linux and iproute2 revisions are exact and current as reviewed 2026-08-15; Linux source SPDX is recorded. Cisco/Juniper are proprietary reference implementations, not reusable source candidates.
12. **Security/supply-chain risks of installer projects recorded — PASS.** No independent installer is required; native OS/kernel paths are preferred. Bare GRE's lack of authentication/encryption and protocol-47 firewall exposure are explicit. See installer/security files.
13. **Upgrade/uninstall/rollback behavior researched — PASS.** Linux follows kernel/iproute2/distribution lifecycle and interface/config ownership; network OS deployments follow vendor upgrade/rollback lifecycle. There is no GRE daemon package lifecycle to fabricate.
14. **Protocol/server/client differences and uncertainties explicitly listed — PASS.** Bare GRE versus GRE-over-IPsec, DMVPN/mGRE, PPTP and Ethernet-over-GRE are separated. Generic Windows/macOS/mobile product support remains UNKNOWN/N/A absent authoritative evidence.
15. **`REFERENCE_INDEX.md` links the complete dossier — PASS.** Index contains all V2 files, specs, pins, boundaries and exact next action.
16. **Latest AGENTS handoff contains exact continuation state — PASS when companion handoff commit is present.** The handoff advances V2 from entry 063 to entry 064 GRE over IPsec and preserves the bare-GRE security boundary.

## Completion decision

**APPROVED: Entry 063 may be promoted to `COMPLETE-REFERENCE-v2`.**

This is research/reference completion only. It does not claim PVNetwork implementation, mobile/desktop support, device testing, firewall interoperability, Store readiness or production certification.
