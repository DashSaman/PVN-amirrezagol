# 026 — SonicWall NetExtender / SSL VPN — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. No live SonicWall interoperability, production support, device certification, or redistribution right is implied.

## Current evidence boundary

Existing V1 evidence is reused from `OFFICIAL_NETEXTENDER_CURRENT.md` and `V1_GATE_RECONCILIATION.md`.

Authoritative current vendor baseline:

- SonicWall NetExtender Feature Guide, May 2026, NetExtender 10.3;
- NetExtender Windows **10.3.5**, May 2026;
- NetExtender Linux **10.3.5** current release line;
- current SonicOS 8 SSL VPN documentation;
- current SMA 100 compatibility evidence from vendor release notes.

Current vendor evidence explicitly distinguishes client availability from gateway compatibility: NetExtender 10.3.x can be limited-support on some SonicOS firmware and fully supported on others. This dossier therefore uses a version/capability matrix rather than claiming blanket NetExtender compatibility.

Official SonicWall code, appliance firmware, UI, installers and branding are proprietary/reference-only. No public SonicWall source tree or reusable source license is inferred.

Canonical vendor references:

- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/
- https://www.sonicwall.com/support/technical-documentation/docs/netextender-windows_release_notes/Content/Versions/v-10.3.5/v-10.3.5-windows-releasenotes.htm
- https://www.sonicwall.com/support/technical-documentation/docs/netextender-linux_release_notes/Content/Versions/v-10.3.5/v-10.3.5-releasenotes.htm
- https://www.sonicwall.com/support/technical-documentation/docs/sonicos-8-0-ssl-vpn/Content/ssl-vpn-about.htm
- https://www.sonicwall.com/support/technical-documentation/docs/sonicos-8-0-ssl-vpn/Content/ssl-vpn-configuring-client.htm

OpenConnect NetExtender work remains an unmerged development issue/MR in existing repo evidence and is **not** selected as a current production/open-source implementation.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical servers are proprietary SonicWall firewalls exposing SonicOS SSL VPN and Secure Mobile Access (SMA) 100/500v appliances where supported. NetExtender is the proprietary remote-access client. Mobile Connect and entry 027 Global VPN/IPsec remain separate families. No independent mature open-source NetExtender server/client is invented.

2. **Official/community installers/deployment projects — PASS.** Official clients use Windows MSI/EXE and Linux DEB/RPM/TGZ forms. Server deployment is SonicWall appliance/NSv/SMA/500v lifecycle, including supported virtualized/cloud SMA 500v variants where vendor release notes list them. Public wrapper repositories around proprietary binaries are not promoted into protocol engines. Docker/Helm/Kubernetes server deployment is not claimed without evidence.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** SonicOS firewalls and SMA are vendor appliance/virtual-appliance operating environments, not arbitrary Linux packages. Current vendor compatibility evidence includes physical firewalls/NSv plus SMA 100 and SMA 500v forms for supported hypervisors/clouds. Generic Ubuntu/Debian/RHEL, container and Kubernetes server rows are N/A unless SonicWall publishes such a supported target.

4. **Server panel/UI/menu maps — PASS.** Current SonicOS 8 administration maps SSL VPN under `Network > SSL VPN`, including Server Settings, Client Settings/Default Device Profile, SSL VPN access on zones, address objects/pools, Client Routes, DNS/NetExtender settings, Web Portal/Virtual Office and session views. Authorization also requires user/group VPN Access and/or access rules. SMA-specific certificate/domain flows are separately preserved. A pushed client route is not equated with authorization.

5. **Client install matrix — PASS.** NetExtender 10.3.x officially covers Windows 10/11 x86/x86_64/ARM64 and supported Linux distributions including Ubuntu, Debian, Fedora, CentOS and RHEL with documented package forms. Current 10.3.5 release lines exist for Windows and Linux. Mobile Connect is not used to fabricate NetExtender macOS/iOS/Android support.

6. **Major client UI/menu maps — PASS.** Existing current official evidence maps profile selection, Add/Edit Connection, server/domain/authentication, protocol selector, Save, Connect/Disconnect, Windows `More > Properties` sections, Linux Proxy/Certificate/Settings, Logs, Diagnostics, Packet Capture, CLI, PreLogon/Network Logon and Always-On where supported. Exact UI is version/platform specific and SonicWall branding/trade dress is not reusable.

7. **Cryptographic design/security boundary — PASS.** Current client profile exposes distinct `Auto`, `TLS (TCP)`, `DTLS (UDP)` and `WireGuard` transport choices. These are version/gateway capabilities, not one opaque protocol. TLS/DTLS certificate validation and server trust remain security boundaries; WireGuard must be treated through its own standards evidence when selected. Legacy PPP-over-encrypted-tunnel history is retained only as historical/product-architecture evidence. No proprietary key schedule or unverified cipher set is invented.

8. **Data path/wire flow — PASS.** Flow: NetExtender profile/authentication -> selected transport -> SonicWall firewall/SMA SSL VPN service -> assigned client address/device profile -> pushed routes and DNS -> separate user/group/access-rule authorization -> protected network. SAML/browser, certificate/smart-card or OTP workflows remain authentication subflows. HTTPS proxy mode is a separate path in which NetExtender connects to the proxy and the proxy forwards to SMA while the VPN TLS session remains end-to-end from the client's perspective.

9. **Ports/transports/handshake — PASS.** SonicOS SSL VPN server port is configurable; current SonicOS documentation shows default **4433**. TLS uses TCP, DTLS uses UDP, and current client profile can also select WireGuard where supported. `Auto` chooses a supported transport and must expose the selected result for diagnostics. Exact WireGuard listener/crypto settings are gateway/version-specific; no fixed undocumented port is fabricated. HTTPS proxy is supported for SSL-VPN/SMA flows.

10. **Deployment topologies — PASS.** Evidence-backed topologies include SonicOS firewall remote-access termination, virtual NSv where supported, SMA 100 appliances and SMA 500v virtual/cloud deployments in vendor matrices. Client access can be direct or through supported HTTPS proxy configuration; endpoint policy may use split routes or tunnel-all modes. HA/failover details remain appliance-family/version-specific and are not generalized beyond vendor documentation.

11. **Source/release/license/activity pins — PASS.** Proprietary SonicWall source has no public commit/source license and remains reference-only. Product behavior is release-pinned to NetExtender 10.3 Feature Guide and Windows/Linux 10.3.5 lines; current vendor release notes show active May-2026 maintenance and detailed firmware compatibility. OpenConnect SonicWall support is unmerged and therefore not selected/pinned as a usable implementation. No false open-source reuse right is created.

12. **Security/supply-chain risks — PASS.** Use official SonicWall/MySonicWall distribution/update channels, preserve installer signing/checksum verification where published, track vendor release notes/advisories, and do not use arbitrary binary wrappers. Passwords, SAML sessions, OTP, proxy credentials, certificate private keys/smart-card PINs and remembered profile secrets are separate secret classes. Packet captures/debug logs require explicit protected handling. Transport Auto must not hide downgrade/selection state.

13. **Upgrade/uninstall/rollback — PASS.** Current docs explicitly cover Windows standalone/MSI lifecycle, Linux package lifecycle, client upgrade and uninstall, plus administrator-controlled MSI and auto-upgrade distinctions. Server firmware/SMA lifecycle is separate from client lifecycle. Compatibility must be rechecked before client or gateway upgrade because 10.3.x support differs by SonicOS/SMA version; rollback must follow vendor-supported package/firmware procedures rather than assuming protocol compatibility.

14. **Differences/uncertainties — PASS.** Current 10.3.5 availability is not blanket compatibility: vendor matrices mark 10.3.x supported or limited-support depending on firmware. Certificate/smart-card features can be SMA-only rather than firewall-supported. Exact SAML/MFA/Duo/certificate combinations, route/DNS/authorization behavior and per-transport parameters remain gateway/policy/version specific. OpenConnect NetExtender support is not a released capability. Live gateway testing remains certification work, not a hidden V2 gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `README.md`, `OFFICIAL_NETEXTENDER_CURRENT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, and this audit. Reuse decision remains `VENDOR-SPECIFIC PRODUCT COMPATIBILITY TARGET / OFFICIAL CLIENT PRIMARY / NO MATURE PUBLIC DROP-IN SELECTED`. SonicWall code/assets are proprietary reference-only; standards transports such as WireGuard must be supported through their own independently audited adapters only when the exact gateway exposes them.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 026 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 26/93 and continue at **027 — SonicWall Global VPN / IPsec**. No runtime/device/Store/live-interoperability evidence is added as an unstated gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or bounded by explicit proprietary/version-specific N/A/unknown conditions. Remaining exact appliance interoperability and packet-level certification are later implementation/certification work.

Decision: **COMPLETE-REFERENCE-v2**.
