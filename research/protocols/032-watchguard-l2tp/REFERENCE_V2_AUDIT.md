# 032 — WatchGuard Mobile VPN with L2TP — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. This does **not** claim live Firebox interoperability, production support, native-device certification, or rights to redistribute proprietary WatchGuard software/assets.

## Current product and standards boundary

WatchGuard Mobile VPN with L2TP is a standards-based **L2TPv2 remote-access** service. The secure/default architecture protects L2TP with IPsec; WatchGuard explicitly recommends keeping IPsec enabled. Unprotected UDP/1701 L2TP exists only as an insecure/non-recommended compatibility mode and must never become an automatic PVNetwork fallback.

Current WatchGuard Help applies through maintained Fireware v2026.3 / v2026.2.1 / v12.12.1 lines. Fireware/Firebox software, management UI and branding are proprietary/reference-only.

Canonical vendor references include:

- https://www.watchguard.com/help/docs/help-center/en-US/content/en-us/Fireware/mvpn/l2tp/l2tp_vpn_about_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_config_c.html
- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-us/Fireware/mvpn/l2tp/l2tp_vpn_config_edit_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_user_auth_c.html
- https://www.watchguard.com/help/docs/help-center/en-US/content/en-us/Fireware/mvpn/l2tp/l2tp_internet_access_c.html
- https://www.watchguard.com/help/docs/help-center/en-us/Content/en-US/Fireware/mvpn/l2tp/l2tp_vpn_tshoot_c.html

Standards/source behavior reuses completed entry 008 and its immutable reference index rather than duplicating or inventing WatchGuard-specific source pins:

- RFC 2661 — L2TPv2;
- RFC 3193 — L2TP secured with IPsec;
- RFC 1661 — PPP;
- RFC 2759 — MS-CHAPv2;
- xl2tpd v1.3.20, commit `07b3063e2b6870fad16366bc8d7c52a6f2a4292f`, GPL-2.0-or-later family;
- Accel-PPP NG reviewed commit `9654bb66fa129fc3c20b24612ea91fb43dd14f38`, GPLv2;
- ppp-project reviewed commit `86c240ea75d48205310a4d0761784cb11f0b086e`, per-file licensing;
- NetworkManager-l2tp 1.52.4, commit `ef970e2f3bf3e219d99c949b7a91a6bb55ab6ef7`, GPLv2;
- Katalix go-l2tp/kl2tpd v0.1.8, commit `0f3bb650da44ce8565d1ff0e62d5cef000d36c65`, MIT;
- IPsec/IKE cryptography and NAT-T evidence from already-completed entries 004–008 and the reviewed strongSwan family where applicable.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server is proprietary WatchGuard Firebox/Fireware Mobile VPN with L2TP. Normal endpoint path uses standards/native L2TP/IPsec clients rather than a WatchGuard-branded endpoint app. Public implementations from entry 008 are reusable standards references only; they are not relabeled as Fireware servers.

2. **Official/community installers/deployment projects — PASS.** Server deployment follows the same WatchGuard physical Firebox, FireboxV and FireboxCloud lifecycle already mapped for entries 030/031. Current FireboxV deployment includes supported VMware ESXi, Hyper-V, Linux KVM and Proxmox VE environments. Native Windows/macOS/iOS clients generally require profile/certificate/PSK configuration rather than a WatchGuard client installer. Generic Docker/Helm/Kubernetes Fireware deployment is not claimed.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** Fireware is the proprietary server OS boundary. Supported physical/virtual/cloud targets are vendor-controlled; HA/FireCluster support is platform-specific and therefore not generalized. Arbitrary Ubuntu/Debian/RHEL packages and generic containers are N/A for the WatchGuard server. Generic Linux L2TP/IPsec projects remain separate standards implementations from entry 008.

4. **Server panel/UI/menu maps — PASS.** Current admin path is `VPN > Mobile VPN > L2TP`. Setup Wizard covers authentication server, users/groups, virtual-IP pool and IPsec tunnel authentication. Manual/edit configuration expands Networking, Authentication and IPsec settings. Fireware creates/uses `L2TP-Users`, hidden `Allow-IKE-to-Firebox`, `WatchGuard L2TP` UDP/1701 policy from `L2TP-IPSec`, and `Allow L2TP-Users`; resource restriction and policy ownership remain explicit. Logs/troubleshooting distinguish IKE, L2TP, PPP/auth, pool, routing and policy failures.

5. **Client install matrix — PASS.** WatchGuard documents native Windows L2TP/IPsec, native macOS L2TP over IPsec and native iOS L2TP configuration. Android is an explicit negative capability: the built-in Android L2TP client is no longer available/supported on Android 12+. Other RFC 2661-compatible clients are compatibility candidates only when exact IPsec/L2TP/auth capabilities match. No unsupported modern-Android native row is fabricated.

6. **Major client UI/menu maps — PASS.** Client UI is intentionally OS-native: server/Firebox address, L2TP/IPsec type, PSK or certificate reference, account/user identity, password, Connect/Disconnect/status and platform routing/profile controls. Windows/macOS/iOS use their native VPN surfaces. No fictional WatchGuard consumer UI is introduced for this standards path.

7. **Cryptographic design/security boundary — PASS.** L2TP itself is not the confidentiality boundary; IPsec protects the tunnel. Fireware supports PSK or IPsec certificate tunnel authentication, with Phase 1/Phase 2 transforms, NAT Traversal and DPD under policy control. PPP/MS-CHAPv2 user authentication is a separate credential/security layer. Certificate trust, SAN/identity, algorithm compatibility and expiry remain exact-platform controls. Historical weak IKE/IPsec assumptions are not reused as modern defaults, and unprotected L2TP is not silently enabled.

8. **Data path/wire flow — PASS.** Secure reference flow is: native L2TP client -> IKE/IPsec tunnel authentication -> ESP/NAT-T protected path -> L2TPv2 control/session -> PPP/MS-CHAPv2/RADIUS/MFA user authentication -> virtual IP assignment -> routes/DNS -> `L2TP-Users`/Firebox access policy -> application traffic. Each layer owns distinct failure state; OS `Connected` alone is not application-path success.

9. **Ports/transports/handshake — PASS.** Secure standard path uses UDP 500 for IKE, UDP 4500 for NAT-T when required, ESP IP protocol 50 when not UDP-encapsulated, and UDP 1701 for L2TP inside the protected path. Raw/public UDP 1701 without IPsec is the documented insecure mode and is not a secure fallback. Exact IKE/Phase-2 transforms remain policy/client-version specific and are not fabricated.

10. **Deployment topologies — PASS.** Physical Firebox, FireboxV on supported virtualization platforms and FireboxCloud can terminate the service where the product/version supports it. Remote clients normally use full/default-route behavior; manual desktop split-tunnel routes are possible at OS level but WatchGuard explicitly does not provide support for those L2TP split configurations and recommends IKEv2/SSL when split tunneling is required. Multiple clients can connect from one external/NAT address when capacity/policy permits. Virtual-IP pool exhaustion is a first-class failure condition.

11. **Source/release/license/activity pins — PASS.** Fireware has no public reusable source tree/license and remains proprietary/reference-only. Current vendor activity is bounded by maintained Fireware v2026.3/v2026.2.1/v12.12.1 documentation/release lines. Reusable standards implementations are not duplicated; entry 008 immutable pins above remain authoritative. Native OS clients are platform capabilities under platform terms. No WatchGuard source SHA or open-source license is invented.

12. **Security/supply-chain risks — PASS.** Keep IPsec PSK, certificate/private key, PPP username/password, MFA state and transient IKE/IPsec/PPP state as separate secret classes. Use authenticated WatchGuard firmware/support channels and platform-native certificate/secure stores. Validate certificate trust/expiry/SAN/algorithm and maintain an explicit transform security floor. AuthPoint/RADIUS/MS-CHAPv2 support is exact-flow dependent. Diagnostic logs must redact secrets. Modern Android native unavailability must fail closed rather than trigger an abandoned third-party client choice.

13. **Upgrade/uninstall/rollback — PASS.** Server upgrades follow Fireware/Firebox lifecycle. Endpoint lifecycle is primarily native VPN profile create/update/remove plus PSK rotation or certificate import/renewal/removal; managed GPO/MDM/profile deployment is platform-owned. OS updates can remove/deprecate capabilities, as Android 12+ demonstrates. Migration toward IKEv2/SSL is an explicit option when platform/security requirements make L2TP unsuitable. No stale/unprotected automatic rollback is allowed.

14. **Differences/uncertainties — PASS.** Exact current Windows/macOS/iOS version compatibility follows Fireware Release Notes and is not generalized beyond vendor evidence. Exact Phase 1/2 proposal matrix, certificate algorithms, PPP/MS-CHAPv2/RADIUS/AuthPoint combinations and OS-managed profile APIs remain version/policy dependent. Manual split tunnel is unsupported by WatchGuard. A client may show Connected while authorization/resource access fails. Live NAT/reconnect/HA/device interoperability remains later certification, not a hidden V2 gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `WATCHGUARD_L2TP_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this `REFERENCE_V2_AUDIT.md`, entry 008 `V2_GATE_RECONCILIATION.md`, and `research/upstreams/classic-tunnels-family/l2tp-ipsec-reference-v2/REFERENCE_INDEX.md`. Reuse decision: `STANDARD L2TP/IPSEC LEGACY-COMPATIBILITY TARGET / NATIVE-OS-FIRST WHERE CURRENT SUPPORT EXISTS / REUSE ENTRY-008 LAYERED STACK / WATCHGUARD AUTH+POLICY CERTIFICATION REQUIRED / MODERN-ANDROID-NATIVE-UNAVAILABLE`. No WatchGuard-specific cryptographic engine is required.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 032 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 32/93 and continue at **033 — Aruba VIA**. No runtime/device/Store/live-interoperability receipt is introduced as an unstated completion gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/platform/version-specific N/A/uncertainty. Runtime and device certification remain later work under the repository contract.

Decision: **COMPLETE-REFERENCE-v2**.
