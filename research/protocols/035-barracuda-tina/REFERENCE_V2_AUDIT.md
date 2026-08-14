# 035 — Barracuda TINA VPN — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. This does **not** claim live Barracuda CloudGen interoperability, production certification, device/Store validation, or redistribution rights for proprietary Barracuda software/assets.

## Current product and source boundary

TINA is Barracuda Networks' proprietary VPN protocol used by Barracuda CloudGen Firewall. Current Barracuda documentation explicitly separates TINA from standards-based IPsec and states that the Barracuda VPN Client must be used for TINA client-to-site VPN. Generic IPsec implementations therefore remain standards references only and are not treated as TINA-compatible implementations.

Current authoritative evidence reviewed includes CloudGen Firewall **10.5**, current CloudGen VPN/TINA documentation, the maintained **VPN Client & Network Access Client 5.x** documentation family, Windows **5.3.x** release notes through **5.3.9**, current macOS notes through **5.3.10**, and current CloudGen 10.5 release/migration material including Firewall Admin **10.5.0-215** (June 2026).

Canonical references:

- https://documentation.campus.barracuda.com/wiki/spaces/CGFv90/pages/9569371/Client-to-Site%2BVPN
- https://documentation.campus.barracuda.com/wiki/spaces/CGFv90/pages/9568309
- https://documentation.campus.barracuda.com/wiki/spaces/CGFv105/pages/379093721
- https://documentation.campus.barracuda.com/wiki/spaces/CGFv105/pages/379093723/Authentication%2BEncryption%2BTransport%2BIP%2BVersion%2Band%2BVPN%2BRouting
- https://documentation.campus.barracuda.com/wiki/spaces/CGFv105/pages/379093793/TINA%2BTunnel%2BSettings
- https://documentation.campus.barracuda.com/wiki/spaces/CGFv105/pages/379093919/VPN%2BSettings
- https://documentation.campus.barracuda.com/wiki/spaces/NACv50/pages/2162793/Installing%2Bthe%2BBarracuda%2BNetwork%2BAccess%2BVPN%2BClient%2Bfor%2BWindows
- https://documentation.campus.barracuda.com/wiki/spaces/NACv50/pages/2162726/How%2Bto%2BConfigure%2Bthe%2BBarracuda%2BVPN%2BClient%2Bfor%2BWindows
- https://documentation.campus.barracuda.com/wiki/spaces/NACv50/pages/2162738/Release%2BNotes%2B-%2BBarracuda%2BNAC%2BVPN%2BClient%2B5.3%2Bfor%2BWindows
- https://documentation.campus.barracuda.com/wiki/spaces/NACv50/pages/2163011/Release%2BNotes%2B-%2BBarracuda%2BVPN%2BClient%2Bfor%2BmacOS
- https://documentation.campus.barracuda.com/wiki/spaces/NACv50/pages/2162860/How%2Bto%2BUpdate%2Bthe%2BBarracuda%2BNAC%2BVPN%2BClient%2Bfor%2BWindows
- https://documentation.campus.barracuda.com/wiki/spaces/NGFEOL/pages/5505063

No canonical public source repository or open-source license for the complete TINA protocol engine, Barracuda VPN Client/Network Access Client, or CloudGen VPN service is identified in the authoritative documentation reviewed. Complete first-party implementation, UI and binaries remain proprietary/reference-only.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server/peer implementation is the proprietary Barracuda CloudGen Firewall VPN service. TINA supports client-to-site through Barracuda VPN Client and site-to-site between CloudGen Firewalls. Standard IKE/IPsec clients are a separate CloudGen service path and are not relabeled as TINA.

2. **Official/community installers/deployment projects — PASS.** CloudGen Firewall follows Barracuda appliance/virtual/cloud deployment and firmware lifecycle. Windows can install the VPN Client standalone or as a component of the Network Access Client; Barracuda also documents macOS and command-line Linux/FreeBSD-family VPN clients. Android CudaLaunch can provide TINA access for supported client-to-site configurations. iOS CudaLaunch uses the native IPsec path and is not claimed as a TINA client. No community container/gateway project is presented as a TINA server replacement.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** The meaningful TINA server is the supported CloudGen Firewall platform, not an arbitrary Linux package. The reviewed documentation does not establish a generic Ubuntu/Debian/RHEL or Kubernetes/Helm TINA server deployment; such targets are therefore not claimed. Exact CloudGen hardware/virtual/cloud model support remains version/subscription controlled.

4. **Server panel/UI/menu maps — PASS.** Current CloudGen flows expose VPN through `CONFIGURATION > Configuration Tree > Box > Assigned Services > VPN-Service` (or `VPN` in version-specific trees), including `Service Properties`, client-to-site group policies, TINA/site-to-site tunnel configuration, VPN settings, listeners, certificate/CA and routing/transport settings. Configuration uses Lock -> edit -> Send Changes -> Activate workflow. Version-specific tree labels are preserved rather than flattened.

5. **Client install matrix — PASS.** Current Barracuda client documentation covers Windows VPN Client/NAC, macOS VPN Client, command-line Linux/FreeBSD-family client, and Android CudaLaunch for supported TINA remote access. Current Windows 5.3 release notes support Windows 10/11 on x64 and ARM64. Platform capability is non-uniform; iOS is specifically not promoted as TINA merely because CudaLaunch exists there.

6. **Major client UI/menu maps — PASS.** On Windows, `Barracuda VPN Control` manages VPN profiles, adapter settings and certificates and can be opened from the system tray or Control Panel. Documented workflows cover create/import/copy profiles, server and alternate gateway/port probing, transport/proxy selection, certificate/authentication choices, Connect/Disconnect, Always On/Direct Access, route/DNS behavior, logs/system reports and troubleshooting. macOS and CLI clients expose their own platform-specific surfaces rather than being forced into the Windows UI map.

7. **Cryptographic design/security boundary — PASS.** TINA is proprietary and Barracuda describes it separately from standard IPsec. Current CloudGen 10.5 documentation exposes configurable encryption/authentication choices including modern AES/AES-CTR/AES-GCM and SHA-2-era options alongside legacy algorithms. Legacy DES/3DES/MD5/SHA1-era choices are treated as compatibility risks, not recommended defaults. X.509 client/server trust, user authentication and personal-license material remain distinct credential layers. No undisclosed TINA key schedule or proprietary handshake byte format is invented.

8. **Data path/wire flow — PASS.** Bounded client-to-site flow: Barracuda client selects/imports a VPN profile -> reaches CloudGen VPN service listener -> TINA proprietary initial/authentication exchange using configured user/certificate/license/SAML/TOTP policy -> selected TINA transport establishes -> client receives/runs configured VPN policy/routes -> protected IPv4 payload traverses tunnel -> reconnect/failover/Always-On logic applies as configured. Site-to-site TINA remains a distinct CloudGen-to-CloudGen topology. IPv6 may be used for the VPN envelope, while current client-to-site guidance limits payload traffic to IPv4; full dual-stack payload support is not inferred.

9. **Ports/transports/handshake — PASS.** Current TINA transport choices include UDP, TCP, UDP+TCP hybrid and ESP. CloudGen VPN settings document TCP **691** as the default TINA TCP transport port and optional TCP **443** for HTTP/SOCKS proxy traversal where the service can bind it. ESP uses IP protocol 50 and is unsuitable through NAT. Proxy modes include direct, HTTP, SOCKS4 and SOCKS5. The modified TINA handshake/heartbeat/failover behavior remains proprietary; only documented transport-level behavior is recorded.

10. **Deployment topologies — PASS.** Evidence-backed topologies include client-to-site Barracuda client -> CloudGen Firewall, site-to-site TINA only between CloudGen Firewalls, static/dynamic WAN endpoint arrangements, multiple transports/SD-WAN use, redundant VPN gateways and optimal-gateway/failover client behavior, IPv4/IPv6 envelope listeners, selective/full routing policy and proxy traversal. Exact HA/SD-WAN behavior remains CloudGen/version/license specific and is not generalized to all remote-access clients.

11. **Source/release/license/activity pins — PASS.** Complete TINA/CloudGen/client source is proprietary; no public source SHA or open-source license is fabricated. Current product activity is pinned to the maintained CloudGen **10.5** line and current client 5.x material; Windows 5.3 release notes expose **5.3.9**, macOS notes expose **5.3.10**, and current migration material lists Firewall Admin **10.5.0-215** in June 2026. Exact package hashes/signatures remain future package-freeze artifacts, not research inventions.

12. **Security/supply-chain risks — PASS.** Use authenticated Barracuda download/support channels and current release/migration/security guidance. Protect `.lic` personal-license files, X.509 private keys, passwords, SAML/TOTP state, proxy credentials and VPN profiles separately. Server certificate validation and current cryptographic policy must not be weakened merely to accept legacy options. Client profile import/update paths and Firewall Admin configuration distribution are supply-chain/control boundaries. Logs/system reports require secret and network metadata redaction.

13. **Upgrade/uninstall/rollback — PASS.** Windows update documentation says to review latest release notes, use a locally saved setup file, and install the new version over the existing client while preserving settings; the installer preselects the existing component mode. Current release notes state 5.3.x upgrade paths and document migration/fixes for older VPN profiles. CloudGen firmware/Firewall Admin lifecycle follows release and migration notes with version-specific migration paths. Exact downgrade support is not assumed; client uninstall must account for VPN adapter/components, profiles, credentials/certificates and optional NAC/Personal Firewall/Health Agent components.

14. **Differences/uncertainties — PASS.** TINA client/platform support, SAML/TOTP, Always On, Direct Access, CudaLaunch behavior, optimized transport mode, route/DNS handling, proxy behavior and subscription/licensing are version/platform specific. Current documentation distinguishes IPv6 envelope support from IPv4-only client-to-site payload guidance. Network Access Client endpoint-health/personal-firewall functions are not treated as TINA wire-protocol semantics. Runtime/live CloudGen interoperability remains later certification, not an unstated V2 gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `BARRACUDA_TINA_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this `REFERENCE_V2_AUDIT.md`, plus separately reviewed standards evidence only where a generic transport primitive actually applies. Reuse decision: `PROPRIETARY BARRACUDA TINA VENDOR ADAPTER/REFERENCE / CLOUDGEN+FIRST-PARTY CLIENT REFERENCE-ONLY / DO NOT SUBSTITUTE GENERIC IPSEC / MODEL TINA TRANSPORT+PROFILE+AUTH+ROUTING+FAILOVER AS DISTINCT VENDOR SEMANTICS / MODERN-CRYPTO POLICY REQUIRED / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 035 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 35/93, select **036 — Juniper Secure Connect** as the next unfinished entry, refresh foreground activity and continue immediately. No runtime/device/Store/live-interoperability receipt is introduced as a hidden completion condition.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/platform/version-specific N/A/uncertainty. Current CloudGen 10.5 and Barracuda VPN Client 5.x evidence covers server/client/deployment/admin/transport/auth/routing/lifecycle boundaries without inventing TINA source or generic-IPsec compatibility.

Decision: **COMPLETE-REFERENCE-v2**.
