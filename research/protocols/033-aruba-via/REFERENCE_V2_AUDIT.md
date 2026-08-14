# 033 — HPE Aruba Networking VIA — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. This does **not** claim live Aruba interoperability, production support, device/Store certification, or rights to redistribute proprietary HPE Aruba Networking software/assets.

## Current product and source boundary

HPE Aruba Networking VIA is a proprietary remote-access VPN client and policy/provisioning system around IPsec/SSL transport behavior. In current AOS-10/Central deployments, VIA terminates on an Aruba VPN Concentrator (VPNC)/gateway; ArubaOS 8.x documentation also covers controller/gateway deployments. VIA is not treated as an independent new cryptographic protocol.

Current official evidence reviewed:

- VIA Help Center and documentation portal;
- current Aruba Central/AOS-10 VIA connection, authentication, certificate, pool and installer-hosting workflows;
- platform feature matrix for Windows, macOS, Linux, Android and iOS;
- VIA **4.7.6** release notes and current Help Center release index.

Canonical references:

- https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/home.htm
- https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/RNs/VIA-476.htm
- https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/Overview/Feature%20Parity.htm
- https://arubanetworking.hpe.com/techdocs/central/latest/content/aos10x/cfg/basic-setup/basic-via-connections.htm
- https://arubanetworking.hpe.com/techdocs/central/latest/content/aos10x/cfg/via/via-cfg-conn-profile.htm
- https://arubanetworking.hpe.com/techdocs/central/latest/content/aos10x/cfg/security/authentication/l3-via-authentication-prof-conf.htm
- https://arubanetworking.hpe.com/techdocs/central/latest/content/aos10x/cfg/via/via-upload-to-vpnc.htm

No canonical public source repository for the complete first-party VIA client/gateway implementation is identified. VIA binaries, controller/gateway code, UI, branding and proprietary provisioning behavior remain reference-only. HPE's third-party/open-source notices are not a license grant for the complete VIA product.

Standards-defined IKE/IPsec/ESP/NAT-T behavior reuses completed entries 004–006 and the repository's reviewed standards-engine evidence only where semantics actually match. Generic IPsec support is not proof of VIA provisioning/profile/interoperability parity.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical server ecosystem is proprietary HPE Aruba VPNC/gateway/controller under AOS-10/Central or supported ArubaOS 8.x architectures. Canonical client is proprietary VIA. Generic strongSwan/native IPsec implementations are standards references only; they are not relabeled as Aruba VIA clients or servers.

2. **Official/community installers/deployment projects — PASS.** Aruba deploys VPNC/gateway/controller through its supported appliance/virtual/cloud-managed ecosystem. VIA client installers come from HPE support, platform stores and/or a VPNC/external installer host. Current AOS-10 documentation explicitly supports uploading Windows installers to VPNC or redirecting to an external download URL; Apple clients use the App Store path. Generic Docker/Helm/Kubernetes VIA gateway installers are not claimed without vendor evidence.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** Server OS and deployment support are HPE Aruba controlled; AOS-10 VPNC/gateway and ArubaOS controller/gateway roles are the meaningful server targets. Arbitrary Ubuntu/Debian/RHEL package installs and generic containers are not evidence-backed VIA server targets. Exact appliance/VM/cloud model support remains tied to current Aruba release/platform documentation rather than being generalized.

4. **Server panel/UI/menu maps — PASS.** Current Aruba Central flow uses a VPNC group/gateway context under `Manage > Devices > Gateways`, then VIA/VPN configuration under Security/L3 Authentication. The connection profile is attached to a user role. Configuration domains include VIA server/public and internal addresses, authentication profiles/server groups, role assignment, split tunnel, DNS suffixes, destination blocking, auto-login, session timeout, certificate/profile-download behavior, IP pools, installer hosting and logging/upgrade settings. ArubaOS 8.x retains its own WebUI/controller management surface and is not flattened into the AOS-10 menu tree.

5. **Client install matrix — PASS.** Current VIA feature documentation covers Windows, macOS, Linux, Android and iOS. The feature matrix explicitly differs by platform for certificate filtering, IPv6, L2 VPN, split tunneling, failover, proxy detection, lockdown-related behavior and other features. Exact current OS version/CPU/Store certification is release-specific and is intentionally not fabricated.

6. **Major client UI/menu maps — PASS.** Documented client surfaces include VPN server/profile list, add/edit/delete server, initial server/FQDN/IP entry, profile download, Connect/Disconnect/status, authentication-profile selection when provisioned, saved-credential behavior, logs/diagnostics/support-email export, lockdown constraints, trusted/untrusted network detection, auto-login, reconnect limits, session timeout and platform-specific CLI/API capabilities. HPE trade dress/assets are not reusable.

7. **Cryptographic design/security boundary — PASS.** VIA uses standards-based IPsec with HPE-specific orchestration and a documented hybrid IPsec/SSL fallback model. Current connection profiles expose IKEv2 and IKEv1 policy/crypto-map choices. IKEv2 authentication choices include user certificate, EAP-TLS and EAP-MSCHAPv2; VIA documentation says IKEv2 PSK is not supported for VIA, while IKEv1 can use PSK or certificates. Server certificate validation is enabled by default and OCSP controls exist. No proprietary key schedule is invented and generic IPsec crypto is reused only where documented.

8. **Data path/wire flow — PASS.** Reference flow: VIA client discovers/configures VPN server -> HTTPS/profile authentication/download -> role-associated VIA connection profile -> trusted/untrusted network decision/auto-login -> IKE/IPsec authentication -> IPsec tunnel to VPNC/gateway -> assigned VPN address/routes/DNS/policy -> protected enterprise traffic. Where supported, SSL fallback carries protected VIA traffic over TCP/443. Authentication/profile download, IKE/IPsec data plane and HPE role/policy enforcement remain separate stages.

9. **Ports/transports/handshake — PASS.** Existing current Aruba evidence maps ESP and UDP 4500 for IPsec/NAT-T operation, HTTPS/TCP 443 for profile/trusted-network/web functions and SSL fallback over TCP 443 where supported. IKE/IPsec semantics reuse completed standards entries; exact IKE listener/proposal behavior is policy/version-specific. The SSL fallback path is not generalized to every platform without feature evidence.

10. **Deployment topologies — PASS.** Evidence-backed topologies include VIA endpoints connecting to one or more Aruba VPNCs/gateways, AOS-10 VPNC groups managed through Central, ArubaOS 8.x controller/gateway deployments, public and internal VPNC addresses, split-tunnel or full-tunnel routing, trusted-network bypass/auto-login, VPN IP pools and multi-VPNC server selection/failover. Exact HA/load-balancing behavior remains platform/version-specific and is not overclaimed.

11. **Source/release/license/activity pins — PASS.** HPE Aruba VIA/gateway implementation is proprietary and no public source commit/license is fabricated. Vendor release activity is pinned to the current Help Center release family through **VIA 4.7.6**; the 4.7.6 release notes are the current visible release page and record Linux fixes. Standards/source reuse remains separately pinned in completed IKE/IPsec dossiers. Any exact binary hash/signature is a future package-freeze artifact, not invented here.

12. **Security/supply-chain risks — PASS.** Use authenticated HPE support/platform-store/VPNC distribution paths; validate server certificates, CA trust and OCSP policy. Keep passwords, token/MFA state, client certificates/private keys, IKE credentials and transient SAs separate. Profile-controlled installer/update URLs are an active supply-chain boundary. Saved credentials should be disabled for token deployments where vendor guidance requires it. Logging/support bundles require secret redaction. Generic IPsec compatibility must not bypass HPE role/profile policy.

13. **Upgrade/uninstall/rollback — PASS.** VIA client lifecycle includes platform package/store installation, VPNC/external-host installer delivery, profile-controlled auto-upgrade where supported, profile update and uninstall/credential cleanup. Current documentation distinguishes platform auto-upgrade capabilities; VIA 4.x documentation also records automatic profile-update behavior on supported desktop platforms. Gateway/controller/AOS lifecycle is separate from client lifecycle. Exact downgrade/rollback support remains version/platform controlled and is not assumed.

14. **Differences/uncertainties — PASS.** Platform feature parity is explicitly non-uniform. Exact current OS versions/architectures, Store package identity, SSL fallback availability, driver/extension behavior, MFA combinations, route/DNS behavior and live multi-VPNC failover remain release-specific. Generic strongSwan/native IKE/IPsec does not implement HPE VIA profile download, role assignment, trusted-network logic, lockdown or upgrade semantics by implication. Runtime certification is later work, not a hidden V2 gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `ARUBA_VIA_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this `REFERENCE_V2_AUDIT.md`, completed entries 004–006 and shared standards-engine evidence. Reuse decision: `VENDOR REMOTE-ACCESS REFERENCE / HPE FIRST-PARTY CLIENT+GATEWAY REFERENCE-ONLY / REUSE STANDARD IKE-IPSEC SEMANTICS ONLY WHERE DOCUMENTED / MODEL VIA PROFILE+ROLE+AUTH+ROUTING+LIFECYCLE AS DISTINCT VENDOR ADAPTER / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 033 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 33/93 and continue at **034 — Citrix Secure Access / Gateway VPN**. No runtime/device/Store/live-interoperability receipt is introduced as an unstated completion gate.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/platform/version-specific N/A/uncertainty. VIA 4.7.6/current AOS-10 evidence, platform feature differences, deployment/admin surfaces, security boundaries and lifecycle are explicit without inventing public source or interoperability receipts.

Decision: **COMPLETE-REFERENCE-v2**.
