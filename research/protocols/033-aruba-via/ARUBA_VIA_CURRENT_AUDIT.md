# 033 — HPE Aruba Networking VIA — Current Product Audit

Reviewed: 2026-08-14

Scope: research evidence for `COMPLETE-RESEARCH-v1`. This is not an implementation, interoperability, device, Store, or production certification receipt.

## Canonical current documentation

Primary HPE Aruba Networking sources reviewed:

- VIA Help Center: https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/home.htm
- VIA documentation portal: https://arubanetworking.hpe.com/techdocs/ArubaDocPortal/content/new-portal/via.html
- Current AOS10/Central VIA configuration overview: https://arubanetworking.hpe.com/techdocs/central/2.5.7/content/aos10x/cfg/via/via.htm
- AOS10 VIA connection profile: https://arubanetworking.hpe.com/techdocs/central/2.5.7/content/aos10x/cfg/via/via-cfg-conn-profile.htm
- AOS10 VIA certificate requirements: https://arubanetworking.hpe.com/techdocs/central/2.5.7/content/aos10x/cfg/via/via-loading-cert.htm
- AOS10 VIA server/authentication group: https://arubanetworking.hpe.com/techdocs/central/2.5.7/content/aos10x/cfg/via/via-create-srv-grp.htm
- AOS10 VPN IP pool: https://arubanetworking.hpe.com/techdocs/central/2.5.7/content/aos10x/cfg/via/via-cfg-vpn-ip-pool.htm
- AOS10 installer hosting/update workflow: https://arubanetworking.hpe.com/techdocs/central/2.5.7/content/aos10x/cfg/via/via-upload-to-vpnc.htm
- VIA 4.x overview: https://arubanetworking.hpe.com/techdocs/VIA/4x/Content/Overview/Preface.htm
- Current feature/platform matrix: https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/Overview/Feature%20Parity.htm
- Current prerequisites/installation requirements: https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/VIA%20Config/Before_you_Begin.htm
- Current release-index/help page, which lists VIA 4.7.6, 4.7.5, 4.7.3, 4.7.2, 4.7.0 and earlier release-note families: https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/home.htm
- VIA 4.6.0 release notes and cross-platform release behavior: https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/RNs/VIA-460.htm
- Android install/profile workflow: https://arubanetworking.hpe.com/techdocs/VIA/4x/Content/VIA%20Connection%20Manager/Android/Android_Install.htm
- Android connection workflow: https://arubanetworking.hpe.com/techdocs/VIA/4x/Content/VIA%20Connection%20Manager/Android/Android_Connect.htm
- macOS install/profile workflow: https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/VIA%20Connection%20Manager/MacOS/MacOS_Install.htm
- VPN-server/profile UI workflow: https://arubanetworking.hpe.com/techdocs/VIA/HPE-Aruba-VIA/Content/VIA%20Connection%20Manager/vpn-server-add-delete.htm

Standards layers are inherited only where exact semantics match the already-audited IKE/IPsec entries 004–007. Aruba-specific provisioning, policy, UI, authentication orchestration, trusted-network detection, upgrade, and client lifecycle are not inferred from generic IPsec.

## Current product architecture

HPE Aruba VIA is a vendor remote-access VPN solution. Current HPE documentation describes a client plus an Aruba gateway/controller role. In AOS10/Central the server side is a VPN Concentrator (VPNC)/gateway configured in Central; the connection profile is attached to a user role and downloaded after successful authentication. ArubaOS 8.x documentation describes Mobility Conductor/managed device or standalone controller deployments.

The connection profile is the authoritative policy bundle for the client. It carries public VPNC addresses, trusted-network internal address, auto-login, authentication profiles, split/full-tunnel selectors, IKE/IPsec policies, credential behavior, certificate validation, DNS suffixes, reconnect limits, MTU, session timeout, logging, upgrade source and lockdown behavior.

## Tunnel and transport boundary

VIA is not a new standalone cryptographic protocol. Current HPE documentation describes secure IPsec connectivity to the VPNC and a hybrid IPsec/SSL client model. The current connection-profile documentation provides both IKEv2 and IKEv1 policies and corresponding IPsec crypto maps. Current VIA prerequisites document normal IPsec traffic using ESP, UDP 4500 for VPN connectivity, HTTPS/TCP 443 for trusted-network/profile/web functions, and SSL fallback carrying ESP over TCP 443 where that platform supports fallback.

Therefore PVNetwork must model VIA as vendor orchestration around standards-based IPsec plus HPE-specific provisioning/policy/lifecycle behavior, not advertise generic IKE/IPsec as proven VIA compatibility.

## Authentication and trust

Current AOS10 documentation requires server certificates for HTTPS and VPN and a CA certificate when client certificate authentication is used. VIA authentication can be backed by server groups such as RADIUS and LDAP. The current connection profile exposes IKEv2 client authentication choices `user-cert`, `EAP-TLS`, and `EAP-MSCHAPv2`; it explicitly states that IKEv2 PSK is not supported for VIA. IKEv1 policy can use PSK or certificate authentication.

Server-certificate validation is enabled by default. OCSP verification controls are available. Password saving is policy controlled and documentation says successful credentials are saved securely until uninstall or until stored IKE authentication fails; token deployments should disable saving.

## Routing, DNS and policy

The profile supports full tunnel or split tunnel. With split tunnel enabled, listed VIA tunneled networks go through the VPNC while other destinations are bridged on the client. The profile also carries DNS suffixes, destination blocking rules, block-until-tunnel-up policy, allow-list traffic rules, client netmask and configurable client MTU. A VPN IP pool is configured on the VPNC. Platform-specific DNS behavior must remain an implementation/certification concern rather than be flattened into one generic policy.

## Platform/client boundary

The current VIA Help Center has dedicated Windows, Linux, Android, macOS and iOS client sections. The current feature matrix covers those five platforms and explicitly documents platform differences for split tunneling, failover, auto-upgrade, lockdown, logs, CLI, IPv6, L2 VPN and other capabilities. VIA 4.6 release notes identify Windows, macOS, iOS, Linux and Android as supported product platforms for the rebranded client. The Help Center currently links release-note families through VIA 4.7.6.

Do not convert this research into a claim that every current OS release/CPU/Store build is certified by PVNetwork. Exact current minimum OS, architecture, driver/extension and Store availability remain later platform certification work.

## Install, update and profile lifecycle

The official client is distributed through HPE support and platform stores depending on OS. HPE documents VPNC-hosted or externally hosted installers, automatic OS-sensitive installer presentation, and profile-controlled auto-upgrade. The VIA 4.x UI requires a VPN profile; a fresh client accepts a server IPv4/IPv6 address or FQDN and downloads policy from that server. VIA 4.6 added automatic VPN-profile updates for Windows/macOS/Linux without requiring reconnect or profile re-add.

## UI and operations

Documented end-user/client concepts include:

- VPN server list and add/edit/delete server details;
- profile download;
- connect/disconnect status control;
- server/profile authentication selection where provisioned;
- saved-password behavior where allowed;
- logs/diagnostics and support-email export;
- lockdown mode in which users can only connect, disconnect or send logs while ping/traceroute diagnostics remain available;
- auto-login and trusted/untrusted network detection;
- reconnect attempt limit and idle/session timeouts.

This is sufficient for a v1 product/UI contract. Full screen-by-screen client reconstruction belongs to the v2 reference layer.

## Logging, diagnostics and lifecycle

Current profile configuration has a client-side logging switch and support email destination. Lockdown documentation explicitly retains ping/traceroute diagnostics. Reconnection attempts are bounded by a configurable maximum; after exceeding it the client becomes idle, while IKE authentication failure prompts for new credentials. Max-session timeout and user-idle timeout are profile parameters. Multi-VPNC configuration and a documented controller load-balance option provide a server-selection/failover surface; exact live failover behavior still requires runtime certification.

## Source, release and licensing boundary

No canonical public HPE source repository for the complete VIA client or gateway implementation was identified in the authoritative product documentation reviewed here. Treat the first-party implementation as proprietary/reference-only. Do not invent a source commit pin and do not copy binaries/assets/branding into PVNetwork. The product Help Center exposes an "Open Source info, common licenses and third-party components" section; that documents third-party notices, not a license grant for the complete VIA product.

Release evidence is vendor release documentation rather than a source-code pin. The current Help Center lists VIA release-note families through 4.7.6, while detailed 4.6.0 notes establish cross-platform behavior and upgrade constraints. Exact downloaded binary hashes/signatures belong to later packaging/runtime certification.

## Product decision

`VENDOR REMOTE-ACCESS REFERENCE / HPE FIRST-PARTY CLIENT IS REFERENCE-ONLY / GENERIC IPSEC IS NOT VIA CERTIFICATION / REUSE STANDARD IKE-IPSEC ARCHITECTURE ONLY WHERE SEMANTICS MATCH / MODEL HPE PROFILE+ROLE+AUTH+ROUTING+LIFECYCLE AS A DISTINCT VENDOR ADAPTER / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`

V1 completion can be research-complete without possessing a proprietary source commit or a live HPE gateway. Those are not hidden research gates.