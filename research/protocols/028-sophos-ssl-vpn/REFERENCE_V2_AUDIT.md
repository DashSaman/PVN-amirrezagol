# 028 — Sophos SSL VPN — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: reference/research completion only. This does not claim live Sophos Firewall interoperability, production support, device/Store certification, or rights to redistribute proprietary Sophos software.

## Evidence boundaries

Existing detailed evidence is reused from `SOPHOS_SSL_VPN_CURRENT_AUDIT.md`, `V1_GATE_RECONCILIATION.md`, completed entry 001 OpenVPN, and `research/upstreams/openvpn-family/`.

### Current proprietary Sophos boundary

Current SFOS 22.0 documentation continues to expose remote-access **SSL VPN** through Sophos Firewall. The server/firewall, Sophos Connect application, provisioning behavior and Sophos branding are proprietary/reference-only.

Current official client line reviewed:

- **Sophos Connect 2.5 MR1** for Windows — released 2026-06-18;
- **Sophos Connect 2.0 MR1 for macOS** — released 2026-05-21;
- macOS Sophos Connect **2.0** introduced SSL VPN on 2026-04-09 and bundled OpenVPN 2.6.12/OpenSSL 3.3.6; 2.0 MR1 updated OpenSSL to 3.3.7 and fixed SSL-VPN DNS/credential behavior;
- Windows 2.5 MR1 includes current SSL-VPN/SSO/reconnect fixes and OpenSSL 3.3.7.

The current June-2026 release notes and March-2026 SSL-VPN endpoint guide supersede an older VPN-portal help sentence that still said macOS SSL VPN was unsupported. The current supported matrix is therefore: Windows 10/11 and macOS 13+ through Sophos Connect; Linux and mobile use OpenVPN-compatible clients.

Canonical Sophos references:

- https://docs.sophos.com/releasenotes/output/en-us/nsg/connect_rn.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/VPNAndUserPortalHelp/VPN/RemoteAccessVPN/SSLVPNRemoteAccess/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/SSLVPN/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/SSLVPN/RAVPNSSLSettings/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/VirtualAndSoftwareAppliancesHelp/index.html

### OpenVPN source boundary

Sophos SSL VPN is OpenVPN-compatible at the `.ovpn` client/profile layer. The selected reusable standards/open-source reference remains the already-audited OpenVPN family rather than a new Sophos-specific tunnel engine.

Current repo pin for OpenVPN 3 Core:

- canonical repository: `OpenVPN/openvpn3`
- current reviewed release tag: **`release/3.11.7`**
- exact release commit: **`18edfae7e7fd8051c93bd4746ec69be91eb02dbb`**
- release commit date: 2026-07-07
- detailed immutable source-analysis SHA: `1fd271caefc9a71406afdc2ff2460999dcfdb234`
- license: upstream `LICENSE.md` states a dual choice of **AGPL-3.0-only or MPL-2.0**, with the upstream OpenSSL permission applying to the AGPL path.

OpenVPN compatibility is not proof that every generated Sophos `.ovpn`, SSO flow or `.pro` provisioning workflow is supported by every OpenVPN front end.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server is proprietary Sophos Firewall/SFOS remote-access SSL VPN. Official clients are Sophos Connect on supported Windows/macOS and OpenVPN-compatible clients on documented Linux/mobile paths. OpenVPN 3 Core is the reusable protocol-engine reference; no separate proprietary Sophos SSL cryptographic engine is proposed or fabricated.

2. **Official/community installers/deployment projects — PASS.** Sophos Firewall is available as hardware, cloud, virtual and software appliance. Official SFOS 22.0 documents AWS/Azure cloud deployment, VMware, Hyper-V, KVM, Citrix Hypervisor, Nutanix and software-appliance paths. Sophos Connect is distributed from SFOS/VPN portal/pattern updates and installed using vendor Windows/macOS packages. Linux/mobile OpenVPN client packaging is a separate client lifecycle. Docker/Helm/Kubernetes Sophos Firewall server deployment is not claimed without vendor evidence.

3. **Server OS/container/orchestration install matrix — PASS.** Server OS boundary is SFOS. Supported deployment classes include XGS hardware, cloud virtual machines, VMware, Hyper-V, KVM, Citrix Hypervisor, Nutanix, and software appliance on supported custom x86-64 hardware. HA is documented for hardware, virtual and software appliances, including active-passive and active-active modes. Generic Ubuntu/Debian/RHEL package installs and containers are not treated as supported Sophos Firewall server targets.

4. **Server panel/UI/menu maps — PASS.** Current admin flow is `Remote access VPN > SSL VPN`, `SSL VPN global settings`, Add/SSL VPN assistant or Configure manually, users/groups and permitted networks, split/full-tunnel choice, then firewall rules between VPN and protected zones. `Administration > Device access` controls SSL VPN/VPN portal exposure; `Authentication > Services` controls authentication/SSO methods. Logs and packet capture are separate troubleshooting surfaces. VPN portal is a separate end-user download/auth surface and must not be confused with the SSL VPN data service.

5. **Client install matrix — PASS.** Current official matrix: Sophos Connect 2.5+ supports 64-bit Windows 10/11 and Windows ARM; current macOS 2.0+ supports macOS 13+ on Intel and ARM through Rosetta 2; SSL VPN `.ovpn` is supported in Sophos Connect Windows 2.1+ (current 64-bit line) and macOS 2.0+. Linux and mobile do not use Sophos Connect for SSL VPN; Sophos documents OpenVPN clients for those endpoints. `.pro` provisioning remains supported on current Windows Sophos Connect but is not supported on current macOS Sophos Connect.

6. **Major client UI/menu maps — PASS.** Existing current Sophos Connect evidence maps Connections, Import connection, Connect, sign-in/MFA/SSO, multiple profiles, connection options (Auto-connect when policy allows, Delete, Rename, Clear credentials, Update policy when provisioning supports it), Events, Open VPN log, About, and technical support report generation. The current SSL-VPN user flow explicitly imports `.ovpn` then Connects. Sophos UI/branding is behavioral reference only and is not copied.

7. **Cryptographic design/security boundary — PASS.** Sophos Firewall SSL VPN is OpenVPN-compatible and uses TLS/certificate-based server authentication plus user authentication; current global settings permit TCP or UDP transport and select an SSL server certificate. Obsolete OpenVPN algorithms/directives are not silently enabled for compatibility; existing V1 evidence records newer Sophos Connect rejection of legacy BF-CBC configurations. Exact OpenVPN data-channel/TLS options remain profile/SFOS-version specific and must be parsed from the generated `.ovpn` rather than invented. `.pro` provisioning/SSO is outside the OpenVPN cryptographic engine.

8. **Data path/wire flow — PASS.** Reference flow: administrator creates global settings/policy/users/permitted networks/firewall rule -> user obtains Sophos Connect and `.ovpn` or supported Windows `.pro` -> provisioning/import produces the OpenVPN profile -> endpoint authenticates using credentials/MFA or supported Windows Entra SSO -> TLS/OpenVPN tunnel is established over configured TCP/UDP -> firewall dynamically applies permitted networks/routes and policy -> protected resource traffic flows through split/full-tunnel policy. Portal/provisioning, authentication and OpenVPN data plane remain separate stages.

9. **Ports/transports/handshake — PASS.** Current SFOS global settings support **TCP or UDP** and a configurable SSL VPN service port. Current `Administration > Device access` documentation identifies the default SSL VPN local-service port as **8443**, while the separate VPN portal defaults to HTTPS **443**. Troubleshooting explicitly recommends testing a changed combination such as TCP 443 when necessary and requires `.ovpn` redownload/reimport after port/protocol changes. Therefore portal 443 is not incorrectly recorded as the normal tunnel service port. TLS/OpenVPN session setup follows the generated `.ovpn`; no undocumented proprietary handshake is invented.

10. **Deployment topologies — PASS.** Evidence-backed server topologies include physical XGS, SFOS virtual/firewall deployments on VMware/Hyper-V/KVM/Citrix/Nutanix, AWS/Azure cloud deployments, software appliances and supported HA pairs. Remote access may be split tunnel or full tunnel, with permitted networks and firewall rules defining reachable resources. Upstream-router/public-IP/DDNS deployment is documented in global settings. Clientless SSL VPN is a related separate mode and is not silently merged into this OpenVPN-compatible entry.

11. **Source/release/license/activity pins — PASS.** Sophos Firewall/Sophos Connect source is proprietary and no public source commit/license is fabricated. Vendor activity is pinned to current 2026 Sophos Connect release notes: Windows 2.5 MR1 (2026-06-18) and macOS 2.0 MR1 (2026-05-21), plus current SFOS 22.0 documentation. Reusable OpenVPN source is separately pinned to OpenVPN 3 `release/3.11.7` commit `18edfae7e7fd8051c93bd4746ec69be91eb02dbb`, with dual AGPL-3.0-only/MPL-2.0 choice recorded from upstream. Exact shipped source/license choice requires a later build freeze.

12. **Security/supply-chain risks — PASS.** Sophos packages/configuration must come from authenticated Sophos Firewall/VPN portal/vendor update paths; track SFOS/Sophos Connect release notes/advisories and bundled third-party versions. `.ovpn` can contain sensitive certificate/key/profile material; `.pro` is an active policy-fetch/provisioning artifact and requires trusted server validation. Passwords, MFA codes, SSO tokens and private keys are separate secret classes. Debug/support bundles and OpenVPN logs require protected handling/redaction. Do not weaken certificate trust or enable obsolete ciphers silently.

13. **Upgrade/uninstall/rollback — PASS.** Sophos Connect release/install guidance and existing V1 evidence define Windows/macOS package upgrade/uninstall lifecycle; some upgrades use uninstall-old/install-new workflow. SSL VPN global changes such as port, protocol, interface/hostname or server certificate require users to redownload/reimport `.ovpn`; a supported `.pro` deployment can pull updated configuration automatically while its portal/provisioning endpoint remains reachable. Sophos explicitly warns about coexistence with other VPN client/service configurations, so client/service upgrade and rollback are distinct from the server firmware lifecycle.

14. **Differences/uncertainties — PASS.** `.ovpn` compatibility is necessary but does not prove every Sophos-generated directive, SSO/MFA workflow or policy is accepted by every OpenVPN front end. Sophos Connect and generic OpenVPN clients have different platform/UI/provisioning capabilities. `.pro` is proprietary Sophos provisioning and not a generic OpenVPN profile. An older portal-help sentence saying macOS SSL VPN was unsupported is superseded by the 2026 Sophos Connect release notes and March-2026 SSL-VPN endpoint guide; the current matrix is recorded explicitly instead of preserving stale contradiction. Live firewall/client interoperability remains certification work rather than a hidden V2 gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `README.md`, `SOPHOS_SSL_VPN_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this audit, entry 001 OpenVPN and `research/upstreams/openvpn-family/`. Reuse decision: `OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST WHEN PROFILE+AUTH CAPABILITIES MATCH / SOPHOS PROVISIONING AND SSO ARE SEPARATE CAPABILITIES`. Sophos code/assets remain proprietary reference-only; PVNetwork must own its canonical profile/UI and must surface unsupported/lossy Sophos/OpenVPN directives.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 028 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 28/93 and continue at **029 — Sophos IPsec Remote Access**. No runtime/device/Store/live-interoperability receipt is added as an unstated gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/version-specific uncertainty. The current 2026 Sophos Connect/macOS lifecycle and OpenVPN source/license boundaries are explicit. Remaining exact profile interoperability and live firewall certification belong to later implementation/certification work.

Decision: **COMPLETE-REFERENCE-v2**.
