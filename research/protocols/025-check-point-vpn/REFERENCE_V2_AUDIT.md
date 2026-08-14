# 025 — Check Point VPN / SNX — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. This is not a claim of live Check Point interoperability, vendor certification, production support, or a right to redistribute proprietary Check Point software.

## Evidence and source boundaries

### Proprietary Check Point authority

Current vendor authority reviewed:

- R82.10 Remote Access VPN / Installation & Upgrade documentation for Security Gateway, SmartConsole, Remote Access VPN community, deployment and upgrade lifecycle;
- current SSL Network Extender (SNX) Administration Guide;
- SNX version matrix: R82/R81.20/R81.10 -> SNX build **80008409** (older R81/R80.40 -> 80008407);
- current Windows E89.x Remote Access VPN client downloads/release notes, including E89.20 standalone package published 2026-05-14 and the E89.x release-note line;
- current macOS E89.x release notes, with E89.23 released 2026-06-03;
- official support pages publish checksums for downloadable client artifacts.

Check Point Security Gateway, Gaia, SmartConsole, SNX, Endpoint Security VPN, Check Point Mobile and SecuRemote are proprietary/vendor-controlled. No source license or redistribution/rebranding right is inferred.

Canonical vendor references:

- https://sc1.checkpoint.com/documents/R82.10/WebAdminGuides/EN/CP_R82.10_RemoteAccessVPN_AdminGuide/
- https://sc1.checkpoint.com/documents/R82.10/WebAdminGuides/EN/CP_R82.10_Installation_and_Upgrade_Guide/
- https://sc1.checkpoint.com/documents/SSL_Network_Extender_AdminGuide/Default.htm
- https://sc1.checkpoint.com/documents/SSL_Network_Extender_AdminGuide/Content/Topics-SNX-Admin-Guide/SNX-Versions-and-Requirements.htm
- https://sc1.checkpoint.com/documents/E89.x/EN/Remote_Access_VPN_Clients_for_Windows_RN/
- https://sc1.checkpoint.com/documents/E89.x/EN/Remote_Access_VPN_Clients_for_macOS_RN/
- https://support.checkpoint.com/results/download/137575
- https://support.checkpoint.com/results/download/143173

### Public interoperability implementation

Existing repository evidence is reused from `SNX_RS_SOURCE_AUDIT.md` and `V1_GATE_RECONCILIATION.md`.

Canonical public source reference:

- repository: `ancwrd1/snx-rs`
- release: **v6.2.4**
- published: 2026-08-12
- annotated tag object: `875a2b1237784b8add62ea808e2e057ec06afde4`
- exact commit: **`a263c47cecdbbc019bc77c482bb77525a02e20a1`**
- license: **AGPL-3.0**

`snx-rs` is an interoperability/source/behavior reference, not an official Check Point implementation and not approved for direct embedding in a closed PVNetwork product without an intentionally AGPL-compatible legal/product model.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server side is proprietary Check Point Security Gateway/Cluster running Gaia and the IPsec VPN and/or Mobile Access blades. Remote Access VPN, SNX and Mobile Access are vendor features. `snx-rs` is client/interoperability-side only and is not misclassified as a server.

2. **Official/community installers/deployment projects — PASS.** Check Point officially supports appliance/Open Server installation plus virtual/cloud Security Gateway deployment under R82.10. Security Gateway/management installation is vendor-image/CPUSE/Central Deployment controlled. No generic Docker/Helm/Kubernetes Check Point gateway is invented. `snx-rs` separately provides Linux DEB/RPM/.run/tar, Windows MSI and macOS package paths in the pinned source tree.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** Current R82.10 supports Check Point appliances, certified Open Servers, virtual machines and documented public/private cloud deployments; Gaia is the server OS boundary. R82.10 documentation lists supported Security Gateway/cluster/open-server/VM/cloud families. Arbitrary Ubuntu/Debian/RHEL containers, Docker and Kubernetes are not claimed as supported Check Point server targets without vendor evidence.

4. **Server panel/UI/menu maps — PASS.** Authoritative admin surface is SmartConsole plus Gaia/CLI. For SNX/Remote Access the documented map includes: `Gateways & Servers` -> gateway object -> `General Properties` / IPsec VPN or Mobile Access blade; `VPN Clients` -> `Other` -> `SSL Network Extender`; `IPsec VPN`; `Network Management` -> `VPN Domain`; Remote Access community under VPN Communities; Global Properties -> `Remote Access` -> `SSL Network Extender`; Mobile Access -> SSL Clients; Access Control/Mobile Access policy; monitoring/debug through vendor VPN tools. Exact menu availability is version/blade dependent and is not flattened into a fabricated generic panel.

5. **Client install matrix — PASS.** Official Remote Access client families include Endpoint Security VPN, Check Point Mobile and SecuRemote on supported Windows/macOS lines, while SNX Network Mode supports Windows, Linux and macOS. SNX can be downloaded/installed on demand from the gateway/portal in supported flows; Linux/macOS command-line SNX can be downloaded manually for Remote Access VPN. Mobile Access portal/clientless access is distinct from SNX desktop support. `snx-rs` independently supports Linux/Windows/macOS; no native Android/iOS `snx-rs` client is inferred.

6. **Major client UI/menu maps — PASS.** Official client families remain proprietary and version-specific. SNX exposes portal/on-demand connect flow plus Linux/macOS CLI parameters (`-s`, `-u`, `-c`, `-l`, `-p`, `-g`, `-d` and `.snxrc`). Existing pinned `snx-rs` source maps status/profile/server/auth/tunnel, Connect/Disconnect, live routing/DNS/statistics, General/Advanced settings, certificate/routing/DNS/misc/UI groups and tray menu. Open-source UI is not treated as Check Point trade dress or exact official-client parity.

7. **Cryptographic design/security boundary — PASS.** Two distinct boundaries are preserved: modern Check Point Remote Access clients use IKE/IPsec (E89 introduced IKEv2 and stronger DH/integrity options with R82 prerequisites), while SNX is an SSL/TLS remote-access client. Official SNX documentation exposes SSL/TLS client/gateway behavior and certificate authentication; legacy cipher configuration exists in SNX documentation and must not be normalized into a recommendation to enable weak crypto. `snx-rs` separately implements IPsec, NAT-T, TCPT-over-TCP and legacy SSL paths. No proprietary key schedule or unsupported cipher claim is invented.

8. **Data path/wire flow — PASS.** Remote Access flow is endpoint -> Internet -> Check Point Security Gateway/cluster -> authentication/authorization/Remote Access community or Mobile Access policy -> Office Mode/tunnel routing -> protected enterprise resource. SNX is downloaded/started from the portal or invoked directly, authenticates to the SSL-enabled gateway, receives policy/addressing and carries authorized application/network traffic. `snx-rs` source confirms a separate auth/controller phase, default IPsec path, Linux XFRM/userspace TUN-ESP choices, NAT-T, TCPT and legacy SSL fallback.

9. **Ports/transports/handshake — PASS.** SNX official default HTTPS port is **TCP 443**, configurable with `snx -p`; Visitor Mode tunnels client-to-gateway communication over regular TCP 443 when IPsec is blocked. Modern Remote Access IPsec uses IKE/IPsec/NAT-T semantics; exact network policy and gateway version remain authoritative. `snx-rs` documents UDP 4500 NAT-T, Check Point TCPT over TCP 443 and legacy SSL fallback as interoperability behavior. Internal daemon-instance ports from Gaia implementation documentation are not confused with Internet-facing standard IKE/NAT-T ports.

10. **Deployment topologies — PASS.** Reference topologies include single Security Gateway, ClusterXL/VRRP/other supported cluster forms, appliance/Open Server/VM, and documented public/private cloud Security Gateways; management may be separate or supported standalone. Remote endpoints may use IPsec Remote Access clients, SNX via the IPsec VPN blade, or SNX via Mobile Access. Clientless Mobile Access is a related but separate access mode. No generic site-to-site capability is inferred from this remote-access entry.

11. **Source/release/license/activity pins — PASS.** Check Point is proprietary/reference-only. Current server reference is R82.10 documentation; current SNX version matrix pins build 80008409 for R82/R81.20/R81.10. Current vendor client activity is evidenced by 2026 Windows/macOS E89.x releases and published package checksums. Public source is pinned to `snx-rs` v6.2.4 exact commit `a263c47cecdbbc019bc77c482bb77525a02e20a1`, AGPL-3.0. These boundaries are not conflated.

12. **Security/supply-chain risks — PASS.** Use authenticated Check Point support/download channels and verify vendor-published checksums. Track vendor hotfix/Jumbo/client release notes and security advisories. Preserve TLS certificate validation; treat SSO cookies, passwords, MFA, certificate private keys/PINs and persisted IKE state as separate secrets. `snx-rs` trace output can expose sensitive request/response data and must not be a normal logging default. AGPL obligations are a legal/supply-chain constraint, not merely a build choice.

13. **Upgrade/uninstall/rollback — PASS.** Check Point gateway upgrades follow supported R82.10 upgrade paths and vendor methods such as Central Deployment/CPUSE; cluster upgrades require backup and vendor-supported procedures. SNX first installation, upgrade and uninstall can require administrator privileges in Network Mode. Windows packages have vendor automatic-upgrade artifacts; macOS/Windows client lifecycles are release-note driven. `snx-rs` packaging/upgrades remain an independent lifecycle and cannot be substituted for vendor rollback/certification behavior.

14. **Differences/uncertainties — PASS.** Official Check Point clients, SNX and `snx-rs` are not feature-equivalent. Exact SCV/posture, SSO/MFA/certificate, machine-certificate, Office Mode, routing/DNS, Visitor Mode and gateway-policy combinations remain version/policy dependent. `snx-rs` has known route/reconnect/interoperability edge cases recorded in V1 evidence. No blanket compatibility claim is made, and live gateway testing remains later certification rather than a hidden V2 completion gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `README.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, `SNX_RS_SOURCE_AUDIT.md`, this `REFERENCE_V2_AUDIT.md`, plus existing generic IKEv2/IPsec/ESP/XFRM evidence where standards overlap. Reuse decision: official Check Point code/assets are proprietary reference-only; `snx-rs` is AGPL-3.0 source/behavior/test reference and must not be directly embedded in a closed product without an explicit AGPL-compatible legal architecture. Prefer standards/native IPsec only where the exact gateway policy demonstrably supports it.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 025 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 25/93 and move to **026 — SonicWall NetExtender / SSL VPN**. No live-device/store/interoperability receipt is added as an unstated gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/version-specific N/A/unknown conditions. Remaining live gateway interoperability, packet capture and exact policy-combination certification belong to later implementation/certification work.

Decision: **COMPLETE-REFERENCE-v2**.
