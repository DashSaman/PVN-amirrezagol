# 030 — WatchGuard IKEv2 VPN — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: reference/research completion only. This does not claim live WatchGuard interoperability, production support, native-OS certification, or rights to redistribute proprietary WatchGuard software/assets.

## Current product and release boundary

WatchGuard Fireware supports **Mobile VPN with IKEv2** as a standards-based remote-access IPsec service. Current WatchGuard Help applies through **Fireware v2026.3 / v2026.2.1 / v12.12.1** and the current release-note catalog lists those maintained lines. Fireware/Firebox software and the optional WatchGuard IPSec Mobile VPN Client are proprietary/reference-only.

Current official client paths:

- native Windows IKEv2;
- native macOS and iOS IKEv2;
- Android via the third-party strongSwan app;
- Fireware v12.11.1+ can generate/import an IKEv2 profile for WatchGuard IPSec Mobile VPN Client for Windows v15.19+.

Canonical vendor evidence:

- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ikev2/mvpn_ikev2_about_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/ikev2/mvpn_ikev2_client_config.html
- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-us/Fireware/certificates/authentication_mvpn_ikev2.html
- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-US/Fireware/mvpn/ikev2/mvpn_ikev2_user_auth.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/firebox_v/fbv_install_overview.html
- https://www.watchguard.com/help/docs/help-center/en-us/content/en-us/Fireware/support/release_news_c.html

## Standards/source boundary

Standards-defined IKEv2/IPsec/ESP behavior reuses completed entries 004/006 and the repository strongSwan family.

Current public strongSwan pin:

- upstream: `strongswan/strongswan`
- release: **6.0.7**
- annotated tag object: `de1c5e42ac35fb6d4121d9bce095806c5f7f90a7`
- exact commit: **`5973ff8e41deef4e015e1138a2de688acedf6f75`**
- license baseline: **GPLv2**

WatchGuard itself documents strongSwan as the Android client path. This supports standards-engine reuse; it is not permission to copy WatchGuard code or evidence that every arbitrary strongSwan configuration is a certified Firebox client.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server is proprietary WatchGuard Firebox/Fireware Mobile VPN with IKEv2. Client ecosystem is intentionally standards-oriented: native Windows/macOS/iOS, strongSwan on Android, plus optional proprietary WatchGuard IPSec Mobile VPN Client on Windows from Fireware 12.11.1+. No custom WatchGuard cryptographic engine is needed or invented.

2. **Official/community installers/deployment projects — PASS.** Physical Firebox appliances and FireboxV/FireboxCloud provide the official server deployment ecosystem. Current FireboxV deployment includes VMware ESXi, Hyper-V, Linux KVM and Proxmox VE; FireboxCloud is the cloud appliance path. Native clients require profile/script/certificate deployment rather than a WatchGuard client install. Android strongSwan and optional Windows WatchGuard client are separate client-install lifecycles. Generic Docker/Helm/Kubernetes Fireware server deployment is not claimed.

3. **Server OS/container/orchestration install matrix — PASS.** Fireware is the proprietary server OS boundary. FireboxV uses the same Fireware OS and management software as physical Fireboxes and currently supports VMware ESXi, Hyper-V, Linux KVM and Proxmox VE. FireboxCloud is separately vendor-controlled. FireCluster support is platform-specific (for example KVM supports FireCluster while Hyper-V FireboxV documentation explicitly does not); therefore HA is not flattened into a universal virtual-platform claim. Generic Linux/container packages are evidence-backed N/A.

4. **Server panel/UI/menu maps — PASS.** Locally-managed Fireware Web UI and Policy Manager both expose `VPN > Mobile VPN > IKEv2`. Setup/Edit domains include server address, certificate, full/split tunnel, virtual IP pool, auth servers/users/groups, Phase 1/Phase 2 proposals, DNS/WINS, timeout, DF handling and enable state. WatchGuard Cloud exposes equivalent Mobile VPN/device configuration for cloud-managed Fireboxes. Diagnostics/logging includes IKE tracing and VPN/auth policy visibility. UI/code/assets remain proprietary.

5. **Client install matrix — PASS.** Official documented paths cover native Windows, macOS and iOS; Android uses strongSwan. Fireware v12.11.1+ also supports WatchGuard IPSec Mobile VPN Client for Windows v15.19+ IKEv2 profile import; the WatchGuard macOS IPSec Mobile VPN Client does **not** support this IKEv2 import path. OS-version compatibility remains tied to current Fireware Release Notes rather than a timeless platform promise.

6. **Major client UI/menu maps — PASS.** Native Windows uses OS VPN/profile settings; macOS/iOS use native VPN/profile management after `.mobileconfig` or manual setup; Android uses strongSwan's profile/connect UI. Optional WatchGuard Windows client imports through Mobile VPN Monitor -> `Configuration > Profiles` -> `Add / Import` -> `Profile Import`, using generated `.INI` plus `.PEM`. There is intentionally no fabricated universal WatchGuard IKEv2 consumer UI.

7. **Cryptographic design/security boundary — PASS.** Tunnel security is standards IKEv2/IPsec. Firebox tunnel identity requires a server certificate with matching SAN and `serverAuth` EKU; expiration/trust chain matters. Fireware supports EC certificates for IKEv2 from v12.5+, but client EC support varies by OS; current WatchGuard docs identify Windows partial EC support, Android strongSwan support and no EC support in the documented macOS/iOS WatchGuard interoperability matrix. User auth uses EAP/MS-CHAPv2. AES-GCM is supported in Fireware 12.2+, but the optional WatchGuard Windows client path has its own profile limitations. No proprietary crypto is invented.

8. **Data path/wire flow — PASS.** Reference flow: generated/manual client profile + CA/server identity -> native/strongSwan/WatchGuard IKEv2 client -> IKE_SA_INIT -> certificate server authentication + EAP/MS-CHAPv2 user authentication -> IKE_AUTH/CHILD_SA -> assigned virtual address/DNS/routes -> Firebox `IKEv2-Users`/policy authorization -> ESP/NAT-T protected traffic to permitted resources. Full/split tunnel and MFA reachability are policy/data-path properties, not UI-only settings.

9. **Ports/transports/handshake — PASS.** Reuse standards IKEv2/IPsec transport evidence: IKE normally UDP 500; NAT-T normally UDP 4500; ESP is the data-plane protocol when not UDP-encapsulated. IKEv2 MOBIKE support exists in modern Fireware. Exact proposals are server/client capability controlled. The EAP/MS-CHAPv2 user-auth layer and certificate-based Firebox identity are preserved separately. No WatchGuard-specific undocumented port is fabricated.

10. **Deployment topologies — PASS.** Physical Firebox, FireboxV on supported hypervisors, and FireboxCloud can terminate remote-access IKEv2. Users can connect through native or documented third-party clients; full-tunnel and split-tunnel policies are supported. FireCluster/HA behavior is platform-dependent and not generalized. Profile/certificate distribution can be manual, scripted or managed using platform MDM/GPO where appropriate. This entry remains remote access, not BOVPN/site-to-site.

11. **Source/release/license/activity pins — PASS.** WatchGuard Fireware and WatchGuard IPSec Mobile VPN Client are proprietary; no source SHA/open-source license is fabricated. Current vendor activity is pinned to the maintained Fireware v2026.3/v2026.2.1/v12.12.1 help/release lines. Open standards source is separately pinned to strongSwan 6.0.7 exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75`, GPLv2. Native OS client implementations remain platform-owned under platform terms.

12. **Security/supply-chain risks — PASS.** Use authenticated WatchGuard software/profile channels and current Fireware advisories/releases. Protect `.TGZ`, `.mobileconfig`, scripts, `.INI`, `.PEM`, credentials and private keys according to content. Validate SAN/EKU/expiry/trust. EAP/MS-CHAPv2 and MFA compatibility are explicit constraints. AuthPoint push can interact badly with full-tunnel Android/strongSwan reachability, so that limitation must be visible rather than silently blamed on crypto. StrongSwan exact plugins/SBOM/advisories require later build-freeze review.

13. **Upgrade/uninstall/rollback — PASS.** Fireware/FireboxV upgrades follow WatchGuard's Fireware lifecycle; FireboxV model upgrades may require a new VM and configuration move. Native-client lifecycle is profile/certificate installation, update and removal rather than app replacement. Certificate rotation/expiry and generated-profile replacement are first-class lifecycle events. Android strongSwan and optional WatchGuard Windows client have independent app/package lifecycles. These lifecycles must not be conflated.

14. **Differences/uncertainties — PASS.** Native OS clients, Android strongSwan and the optional WatchGuard Windows client do not have identical proposal/certificate capabilities. WatchGuard explicitly documents EC-support differences and the WatchGuard Windows client's IKEv2 profile limitations. Exact current OS-version matrix comes from Fireware Release Notes. AuthPoint/full-tunnel push behavior is topology dependent. Live endpoint interoperability/reconnect/failover remains certification work, not a hidden V2 completion gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `README.md`, `WATCHGUARD_IKEV2_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this audit, completed entries 004/006 and `research/upstreams/strongswan-family/`. Reuse decision: `STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST / STRONGSWAN FOR ANDROID+ADVANCED PORTABILITY / WATCHGUARD-SPECIFIC PROFILE+AUTH CERTIFICATION REQUIRED`. WatchGuard assets/code remain proprietary reference-only.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 030 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 30/93 and continue at **031 — WatchGuard SSL VPN**. No runtime/device/Store/live-interoperability receipt is added as an unstated gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/platform/version-specific conditions. Standards reuse, current Fireware release activity and native-client capability differences are explicit.

Decision: **COMPLETE-REFERENCE-v2**.
