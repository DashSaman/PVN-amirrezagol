# 029 — Sophos IPsec Remote Access — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: reference/research completion only. This does not claim live Sophos interoperability, production support, device/Store certification, or rights to redistribute proprietary Sophos code/assets.

## Current lifecycle boundary

Current SFOS 22 remote-access **IPsec** and the retired feature named **IPsec (legacy)** are different products/configuration paths and must not be conflated.

Current `Remote access VPN > IPsec`:

- remains supported in SFOS 22;
- uses Sophos Connect on supported Windows/macOS endpoints;
- current remote-access settings accept **IKEv1 profiles only**, with DPD Off or Disconnect;
- supports PSK or RSA digital-certificate gateway authentication plus separate VPN user/group authentication/authorization;
- exports `.scx` and `.tgb`; `.pro` is the recommended Sophos Connect provisioning path where supported.

Retired `IPsec (legacy)`:

- is not supported in **SFOS 22.0 MR1 and later**;
- must be deleted before an upgrade to 22.0 MR1+ if present;
- restored/imported legacy configuration is not migrated.

Therefore current IKEv1 usage is not evidence that the retired legacy feature is still active.

Canonical vendor evidence:

- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/IPsec/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/IPsec/RAVPNIPsecSettings/index.html
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/RAVPNIPsecLegacy/
- https://docs.sophos.com/nsg/sophos-firewall/22.0/Help/en-us/webhelp/onlinehelp/AdministratorHelp/RemoteAccessVPN/IPsecSSL/SophosConnect/RAVPNSConClientsConfigurations/index.html
- https://docs.sophos.com/releasenotes/output/en-us/nsg/connect_rn.html

Sophos Firewall, Sophos Connect, `.scx`/`.pro` proprietary behavior, client UI and branding are proprietary/reference-only.

## Standards-engine pin

Standards-defined IKE/IPsec/ESP semantics reuse the repository strongSwan family rather than inventing IPsec cryptography.

Current reviewed public pin:

- canonical upstream: `strongswan/strongswan`
- release: **6.0.7**
- annotated tag object: `de1c5e42ac35fb6d4121d9bce095806c5f7f90a7`
- exact commit: **`5973ff8e41deef4e015e1138a2de688acedf6f75`**
- license baseline: **GPLv2** from pinned `COPYING`

Sophos Connect's own support bundle identifies `charon.log` as strongSwan/IKE/ESP activity. That is product-architecture/supply-chain evidence, not permission to copy Sophos Connect or proof that a stock strongSwan build implements Sophos provisioning/SSO/profile semantics.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server is proprietary Sophos Firewall/SFOS current remote-access IPsec. Canonical first-party client is Sophos Connect. Linux/mobile may use third-party clients only where the exported configuration and capabilities match. strongSwan is the selected standards-engine/source reference, not an official Sophos client and not proof of `.scx`/`.pro` compatibility.

2. **Official/community installers/deployment projects — PASS.** Server deployment reuses the SFOS 22 hardware/cloud/virtual/software-appliance matrix from entry 028: XGS hardware, AWS/Azure, VMware, Hyper-V, KVM, Citrix Hypervisor, Nutanix and software appliance. Sophos Connect uses vendor Windows/macOS packages and can be deployed/provisioned through supported AD GPO flows. Generic containers/Helm/Kubernetes are not fabricated as Sophos Firewall deployment targets.

3. **Server OS/container/orchestration install matrix — PASS.** SFOS is the server OS boundary. Supported physical, cloud, hypervisor and software-appliance forms are explicit in current SFOS documentation. HA applies to supported hardware/virtual/software deployments. Generic Linux package/container server rows are evidence-backed N/A for the proprietary SFOS server. strongSwan deployment capabilities remain a separate generic IPsec implementation and are not relabeled as Sophos Firewall.

4. **Server panel/UI/menu maps — PASS.** Current admin flow is `Remote access VPN > IPsec`: Enable, WAN interface, IPsec profile, PSK or certificates, Local/Remote IDs, allowed users/groups, client address/DNS information, permitted network resources, Security Heartbeat, save-credential policy, full/default-gateway behavior, Export connection, Download client, Logs, Reset. `Administration > Device access` enables IPsec on WAN; `Authentication > Groups` controls remote-access IPsec enablement and precedence; firewall rules separately authorize VPN-to-LAN/DMZ/WAN traffic. `Profiles > IPsec profiles` owns Phase 1/2 proposals.

5. **Client install matrix — PASS.** Current Sophos Connect supports remote-access IPsec on Windows 10/11 and macOS 13+. Current release baseline is Windows Sophos Connect 2.5 MR1 (2026-06-18) and macOS 2.0 MR1 (2026-05-21). Sophos Connect is not the Linux/mobile client path; current user docs permit third-party VPN clients for those endpoints. Third-party portability is not treated as official Sophos Connect support.

6. **Major client UI/menu maps — PASS.** Sophos Connect shared shell from entry 028 applies: Connections, Import connection, Connect/Disconnect, authentication/SSO/MFA, per-connection options, Auto-connect when provisioned, Delete/Rename/Clear credentials/Update policy, Events, VPN log and technical support report. IPsec-specific artifacts are `.scx`/`.pro` profiles and `charon.log`/IKE status. Sophos trade dress/assets remain reference-only.

7. **Cryptographic design/security boundary — PASS.** Current remote-access IPsec explicitly requires IKEv1 profiles. Phase 1/Phase 2 proposals are configured in the SFOS IPsec profile. Gateway authentication can use PSK or RSA certificates; current docs reject ECDSA certificates for this mode. Generic IKEv1/IPsec/ESP/NAT-T semantics reuse completed entries 005/006 and the pinned strongSwan family. IKEv1 is a versioned compatibility requirement, not permission to enable weak proposals globally. No proprietary key exchange or undocumented payload cryptography is invented.

8. **Data path/wire flow — PASS.** Reference flow: `.scx`/`.tgb` or `.pro` provisioning -> gateway/profile selection -> PSK/RSA gateway identity -> IKEv1 Phase 1 -> user/group authentication/authorization as configured -> Phase 2/IPsec child SA(s) -> ESP/NAT-T data plane -> assigned client virtual IP/DNS/routes -> firewall-rule authorization -> application traffic. Current SFOS notes that split tunnel can create multiple ESP SAs, one per permitted subnet. Tunnel establishment is not equated with network authorization.

9. **Ports/transports/handshake — PASS.** Standard IKE/IPsec ports/transports are reused from completed IPsec research: IKE normally UDP 500; NAT-T normally UDP 4500; ESP otherwise carries the protected data plane. Current Sophos remote-access profile is IKEv1-only. Exact authentication/vendor payload/provisioning exchanges remain profile/SFOS-specific and are not guessed from generic strongSwan. Device access must allow IPsec on the WAN zone.

10. **Deployment topologies — PASS.** Remote endpoint -> Internet -> physical/virtual/cloud/software Sophos Firewall -> VPN zone -> permitted LAN/DMZ resources is the primary topology. Split tunnel uses configured permitted networks; `Use as default gateway` sends all traffic through the firewall and requires a VPN-to-WAN firewall rule for Internet access. Supported HA firewalls are a server deployment topology; exact session failover behavior remains later certification. The retired legacy configuration is migration-only and not a valid current topology.

11. **Source/release/license/activity pins — PASS.** Sophos Firewall/Sophos Connect source is proprietary; no fake public source SHA/license is produced. Current first-party activity is pinned to Sophos Connect Windows 2.5 MR1 and macOS 2.0 MR1 in 2026 plus current SFOS 22 docs. Standards source is separately pinned to strongSwan 6.0.7 commit `5973ff8e41deef4e015e1138a2de688acedf6f75`, GPLv2. Sophos's bundled strongSwan version and PVNetwork's own reviewed strongSwan release are not conflated.

12. **Security/supply-chain risks — PASS.** IKEv1 compatibility requires explicit strong proposal/security-floor policy; retired legacy objects must not be resurrected during migration. Use authenticated Sophos package/update/provisioning channels. `.scx`, `.tgb`, `.pro`, PSK, passwords, certificates/private keys, MFA/SSO tokens and transient SA keys are separate sensitive classes. `.pro` is an active remote configuration/update channel. strongSwan exact plugins/dependencies/SBOM/advisories require later build-freeze review. Diagnostic bundles can contain network/config/auth metadata and require protected handling.

13. **Upgrade/uninstall/rollback — PASS.** Sophos Connect uses vendor package/pattern-update and supported enterprise deployment lifecycle. Changes to current general/advanced IPsec settings require new `.scx`/`.tgb` distribution unless `.pro` provisioning pulls updates. SFOS 22.0 MR1+ upgrade is explicitly blocked while retired `IPsec (legacy)` configuration exists; that legacy config must be deleted and is not migrated from restore/import. Current remote-access IPsec objects remain a separate supported configuration. Rollback/migration must preserve that distinction.

14. **Differences/uncertainties — PASS.** Modern remote-access IPsec is not `IPsec (legacy)`. `.scx` includes general+advanced Sophos Connect settings; `.tgb` contains reduced/general settings for third-party compatibility; `.pro` is proprietary provisioning. Generic strongSwan IKEv1 compatibility does not prove support for Sophos `.scx`, SSO, group policy, Security Heartbeat or every exported third-party configuration. Exact live third-party interoperability and failover behavior remain certification tasks, not hidden V2 gates.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `README.md`, `SOPHOS_IPSEC_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this audit, completed entries 005/006 and `research/upstreams/strongswan-family/`. Reuse decision: `CURRENT SOPHOS IPSEC COMPATIBILITY TARGET / STRONGSWAN-FIRST FOR STANDARD IKEV1-IPSEC SEMANTICS / SCX-PRO-POLICY-SSO SEPARATE / RETIRED LEGACY MODE MIGRATION-ONLY`. Sophos code/assets remain proprietary reference-only; any strongSwan product reuse requires GPL-compatible architecture/legal review.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 029 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 29/93 and continue at **030 — WatchGuard IKEv2 VPN**. No runtime/device/Store/live-interoperability receipt is added as an unstated gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/version-specific uncertainty. Current Sophos remote-access IPsec is preserved as a modern IKEv1-based Sophos Connect capability while the separately named retired legacy configuration is correctly migration-only.

Decision: **COMPLETE-REFERENCE-v2**.
