# 034 — Citrix Secure Access / NetScaler Gateway VPN — Current Product Audit

Reviewed: 2026-08-14

Scope: V1 research evidence only. No implementation, device, Store, NetScaler interoperability, or production-certification claim.

## Canonical current sources

- Product overview: https://docs.citrix.com/en-us/citrix-secure-access
- Current system requirements: https://docs.citrix.com/en-us/citrix-secure-access/system-requirements.html
- Supported feature parity: https://docs.citrix.com/en-us/citrix-secure-access/gateway-clients-feature-parity.html
- Install guide: https://docs.citrix.com/en-us/citrix-secure-access/install-citrix-secure-access.html
- Client document/release history: https://docs.citrix.com/en-us/citrix-secure-access/document-history
- Current Windows release notes: https://docs.citrix.com/en-us/citrix-secure-access/windows-plug-in-release-notes.html
- macOS/iOS Network Extension implementation: https://docs.citrix.com/en-us/citrix-secure-access/citrix-sso-for-ios-macos-devices
- iOS setup/DNS/MDM behavior: https://docs.citrix.com/en-us/citrix-secure-access/set-up-sso-for-ios-users.html
- NetScaler full VPN setup: https://docs.netscaler.com/en-us/netscaler-gateway/current-release/vpn-user-config/configure-full-vpn-setup.html
- NetScaler split tunneling: https://docs.netscaler.com/en-us/netscaler-gateway/current-release/vpn-user-config/configure-plugin-connections/configure-split-tunneling
- NetScaler DTLS VPN virtual server: https://docs.netscaler.com/en-us/netscaler-gateway/current-release/configure-dtls-virtual-server-using-ssl-virtual-server.html
- NetScaler nFactor authentication: https://docs.netscaler.com/en-us/netscaler-gateway/current-release/authentication-authorization/nfactor-for-gateway-authentication.html

## Identity and architecture

Citrix Secure Access is the current first-party remote-access client family for NetScaler Gateway and Citrix Secure Private Access. NetScaler Gateway is the VPN termination/control side for classic full-VPN deployments. Citrix documentation explicitly recommends Citrix Secure Access for Apple platforms in place of legacy Gateway plug-in/iOS VPN clients, using Apple's public Network Extension framework.

This entry must remain a vendor family, not be flattened into a generic TLS client. Gateway policy, authentication/nFactor, EPA, intranet-application rules, split DNS/tunnel modes, Always On, MDM and platform-specific lifecycle are material product semantics.

## Current platforms and releases

Current Citrix system requirements document Windows 10/11, supported Windows Server editions, macOS, Ubuntu 22.04/24.04 plus selected thin-client OSes, iOS and Android. The document history records active 2026 client releases; examples include Windows 26.6.1.20 (2026-07-14), iOS 26.05.2 (2026-07-01), Android 26.06.1 (2026-06-23), and macOS 26.04.1.1 (2026-04-28). These are vendor release pins, not source-code pins.

## Transport/security boundary

NetScaler full VPN is SSL/TLS based and can use a DTLS VPN virtual server sharing the SSL VPN virtual server's IP/port. Current NetScaler docs state that DTLS handshake failure falls back to TLS. DTLS 1.2 has platform/build boundaries and is currently documented for Windows, macOS and iOS clients. Server certificates are bound to the VPN virtual server. Exact cipher/TLS policy is NetScaler version/profile dependent and must not be invented from client defaults.

## Authentication / endpoint posture

NetScaler nFactor supports multi-factor flows and mechanisms including LDAP, RADIUS, SAML, client certificate, OAuth/OIDC, Kerberos and others, depending on configured policy. Citrix Secure Access feature parity documents nFactor and EPA differences by platform. EPA is a separate endpoint-analysis component and is not universally available on every mobile platform; do not imply universal posture parity.

## Routing and DNS

NetScaler Gateway supports full VPN, split tunnel ON and reverse split tunnel. With split tunnel ON, the client obtains intranet-application rules from Gateway and tunnels matching protected destinations. Citrix feature parity also records split DNS REMOTE/BOTH and FQDN-based split-tunnel support with platform-specific caveats. Current Windows release behavior includes specific reverse-split DNS changes; iOS documentation has Network Extension/DNS limitations. Treat routing/DNS as policy-driven and platform-specific.

## UI/config/lifecycle

The v1 product surface includes Gateway/server profile connection, authentication/browser/nFactor flows, connect/disconnect, Always On where supported, full/split/reverse tunnel, split DNS, intranet applications/FQDN rules, local LAN/proxy/PAC where supported, EPA/posture, logs/diagnostics, client idle/forced timeout and MDM-managed Apple/mobile profiles. Full screen-by-screen UI belongs to v2.

Installation is first-party and platform-specific (desktop packages and mobile stores). Current Windows/macOS/Linux/mobile release streams are independently versioned. The macOS/iOS client uses Network Extension; Android and Apple mobile deployments have MDM/profile constraints. Upgrade regressions and DNS/Always-On reconnect defects are documented in current release notes and remain version-specific test targets.

## Source and reuse boundary

No canonical public repository for the complete Citrix Secure Access client or NetScaler Gateway VPN implementation is identified by the authoritative product documentation reviewed here. Treat complete first-party code, binaries, UI assets and branding as proprietary/reference-only. Do not invent a source SHA or redistribution right. Standards/library components, if separately published under compatible licenses, require component-specific review before reuse.

## Product decision

`VENDOR SSL/TLS-DTLS REMOTE-ACCESS REFERENCE / NETSCALER-GATEWAY POLICY+AUTH+EPA+ROUTING SEMANTICS DISTINCT / FIRST-PARTY CLIENT REFERENCE-ONLY / PLATFORM-SPECIFIC ADAPTER REQUIRED / NO GENERIC TLS COMPATIBILITY CLAIM / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`
