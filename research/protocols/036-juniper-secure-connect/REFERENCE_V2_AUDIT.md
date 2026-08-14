# 036 — Juniper Secure Connect — COMPLETE-REFERENCE-v2 audit

Reviewed: 2026-08-15

Scope: research/reference completion only. This does **not** claim live SRX/vSRX interoperability, production support, device/Store certification, or redistribution rights for proprietary Juniper software/assets.

## Current product and source boundary

Juniper Secure Connect is Juniper Networks' first-party remote-access VPN solution for SRX Series Firewalls and vSRX. Current Juniper documentation describes it as a client-based SSL-VPN solution while the gateway configuration is explicitly integrated with Junos IKE/IPsec remote-access policy. The solution downloads configuration from the SRX and selects transport/protocol behavior during connection establishment. Vendor orchestration, profile download, authentication, certificate and protected-network semantics therefore remain distinct from generic IKE/IPsec support.

Current authoritative pins reviewed:

- gateway prerequisite: SRX/vSRX with Junos OS **20.3R1 or later**;
- current J-Web reference family: **24.4** remote-access VPN wizard;
- current release-notes family dated **2026-02-17**;
- Windows client **25.4.14.03** released February 2026; Windows versions from 25.4.14.00 include SAML support.

Canonical references:

- https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/juniper-secure-connect-overview.html
- https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/juniper-secure-connect-system-requirements.html
- https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/download-juniper-secure-connect.html
- https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/authentication-in-secure-connect.html
- https://www.juniper.net/documentation/us/en/software/jweb-srx24.4/jweb-srx/topics/task/j-web-security-ipsec-remote-access-vpn-juniper-secure-connect-creating.html
- https://www.juniper.net/documentation/us/en/software/secure-connect/release-notes/jsc-feb-2026/index.html
- https://www.juniper.net/documentation/us/en/software/secure-connect/release-notes/jsc-feb-2026/topics/concept/introduction.html
- https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/topic-map/certificate-eap-mschapv2-authentication.html
- https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/topic-map/certificate-eap-tls-authentication.html
- https://www.juniper.net/documentation/us/en/software/junos/cli-reference/topics/ref/statement/profile-security-edit-remote-access.html

No canonical public repository or open-source license for the complete Juniper Secure Connect client or SRX Secure Connect implementation is identified in the authoritative documentation reviewed. Complete first-party code, binaries, UI and branding remain proprietary/reference-only.

## Exact 16-gate reconciliation

1. **Server implementation/project ecosystem — PASS.** Canonical gateway is an SRX Series Firewall or vSRX running supported Junos OS, with the proprietary Juniper Secure Connect application as client. Juniper's NCP Exclusive Client and legacy Dynamic VPN are separate product paths and are not merged into this entry. Generic strongSwan/native IPsec is standards reference only and is not asserted to implement Juniper profile-download/orchestration semantics.

2. **Official/community installers/deployment projects — PASS.** Juniper provides client download/install paths for Windows, macOS, Android and iOS/iPadOS and gateway functionality through supported SRX/vSRX/Junos deployments. No Linux Secure Connect client is listed in the current official system-requirements matrix. No community Docker/Helm/Kubernetes project is presented as a Secure Connect gateway/client replacement.

3. **Server OS/container/orchestration install matrix — PASS / evidence-backed N/A.** Server support is tied to SRX/vSRX and Junos, beginning at Junos OS 20.3R1 for the documented solution. Arbitrary Ubuntu/Debian/RHEL package installation and generic container orchestration are not evidence-backed Secure Connect server targets. Cloud/SD management surfaces can orchestrate SRX policy, but they do not turn the proprietary gateway into a generic container service.

4. **Server panel/UI/menu maps — PASS.** Current J-Web path is `Network > VPN > IPsec VPN` -> `Create VPN > Remote Access > Juniper Secure Connect`. The wizard maps remote users, local gateway, protected networks, client address pools, source NAT, server certificate, authentication and advanced IKE/IPsec settings. Junos CLI exposes remote-access profile, access-profile, client-config, IPsec VPN object, compliance and multi-access fields. SD Cloud/On-Prem provides equivalent managed Remote Access Juniper Secure Connect configuration/deploy flows without being flattened into J-Web labels.

5. **Client install matrix — PASS.** Current official support covers Windows 10+, macOS 10.15/11/12/13/14 including documented Apple-silicon support, Android 10+, and iOS/iPadOS 12+. Download/install paths are documented independently by platform. No Linux client is inferred from generic IPsec capability.

6. **Major client UI/menu maps — PASS.** Documented client surfaces include Gateway/connection profile selection, configuration download from SRX, connection/profile display, user authentication, Connect/Disconnect, status and platform-specific certificate/credential workflows. Supported features include Windows pre-domain logon, configuration validation before connect and OS biometric credential protection where available. SAML/browser behavior is release-specific. Exact platform UI is not falsely normalized across desktop/mobile.

7. **Cryptographic design/security boundary — PASS.** Secure Connect is configured through Junos IKE/IPsec policy and supports documented authentication variants including local/PSK, external RADIUS, certificate validation with EAP-MSCHAPv2, EAP-TLS and newer SAML-related flows. Juniper explicitly documents a proprietary IKEv2-EAP implementation for SAML-based authentication; it is not relabeled as standards-only IKEv2-EAP. EAP-TLS requires PKI, CA root material and per-user client certificates, and does not support local authentication in that scenario. The SRX must use a signed/self-signed/supported ACME certificate rather than its default system-generated certificate for the documented flows.

8. **Data path/wire flow — PASS.** Bounded flow: client reaches SRX/vSRX -> initial user/authentication path downloads/validates current remote-access configuration -> IKE/IPsec negotiation occurs with the selected Juniper authentication mode -> client address/protected-network policy is applied -> protected traffic traverses the tunnel to SRX security policy -> reconnect/disconnect/profile-refresh lifecycle follows client/gateway policy. Proprietary SAML/IKEv2-EAP and configuration-download exchanges remain vendor-specific; packet framing is not invented.

9. **Ports/transports/handshake — PASS.** Standards-level IKE/IPsec/ESP/NAT-T behavior is reused only where Juniper configuration and the completed IKE/IPsec entries document it. Juniper's product overview says the application selects effective transport protocols during establishment, while J-Web exposes IKE/IPsec policy. Exact proprietary SSL/config-download or SAML exchange ports/framing are not generalized beyond official configuration. Port values not proven for a specific path are left policy/version dependent rather than fabricated.

10. **Deployment topologies — PASS.** Evidence-backed topology is remote endpoint -> SRX/vSRX -> protected corporate networks, with local or externally authenticated users, split tunneling through specific protected networks or broad routing through `0.0.0.0/0`, client address pools and optional source NAT. SD Cloud/On-Prem can manage equivalent SRX remote-access objects. Legacy Dynamic VPN migration is a migration source, not a runtime dependency or compatibility claim.

11. **Source/release/license/activity pins — PASS.** Secure Connect client and SRX implementation are proprietary; no public source commit/license is fabricated. Vendor release activity is pinned to the February 17, 2026 Secure Connect release-note family and Windows 25.4.14.03, with gateway support rooted in Junos 20.3R1+ and current J-Web 24.4 documentation. Juniper licensing requires an active SRX-based Secure Connect license; each SRX includes two built-in concurrent-user licenses and additional users require licensing. Exact package hashes/signatures remain later package-freeze artifacts.

12. **Security/supply-chain risks — PASS.** Use authenticated Juniper download channels, supported Junos/client releases and current security guidance. Protect PSKs, RADIUS credentials, SAML tokens/session state, CA roots, user certificates/private keys and client downloaded profiles separately. External authentication is preferred over local authentication in Juniper guidance. Server certificate validation, PKI and multi-factor/SAML configuration are trust boundaries. Logs/profile exports/support bundles require secret redaction. Standards-level IPsec reuse must not bypass Juniper authentication or policy-download semantics.

13. **Upgrade/uninstall/rollback — PASS.** Gateway lifecycle follows supported Junos/SRX software and configuration migration practices; client releases are independently versioned by OS. Dynamic VPN migration guidance recommends a fresh Secure Connect deployment rather than blindly translating legacy configuration. Client upgrade/uninstall must account for downloaded profiles, certificates, pre-domain logon/credential integrations and mobile platform profiles where applicable. Exact downgrade/rollback compatibility remains release/platform specific and is not assumed.

14. **Differences/uncertainties — PASS.** Platform support and features differ across Windows/macOS/Android/iOS; SAML support is tied to newer client releases; authentication combinations have Junos/client restrictions; protected-network routing, source NAT, multi-access, compliance and realm/user-domain behavior are Junos-release dependent. Generic IKE/IPsec clients do not gain Juniper configuration download, SAML proprietary IKEv2-EAP, pre-domain logon or policy semantics by implication. Runtime/live SRX interoperability remains later certification, not a hidden V2 gate.

15. **REFERENCE_INDEX / reuse decision — PASS.** Compact index: `JUNIPER_SECURE_CONNECT_CURRENT_AUDIT.md`, `V1_RESEARCH.md`, `V1_GATE_RECONCILIATION.md`, this `REFERENCE_V2_AUDIT.md`, completed IKE/IPsec standards dossiers and current Juniper release/admin docs. Reuse decision: `VENDOR JUNIPER REMOTE-ACCESS ADAPTER/REFERENCE / SRX-vSRX + PROPRIETARY FIRST-PARTY CLIENT REFERENCE-ONLY / REUSE IKE-IPSEC ONLY FOR EXACT STANDARD LAYERS / KEEP CONFIG-DOWNLOAD+AUTH+SAML+CERT+PROTECTED-NETWORK POLICY DISTINCT / NO GENERIC IPSEC COMPATIBILITY CLAIM / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`.

16. **Latest continuation state — PASS when tracker/state are advanced.** Promotion must set entry 036 to `COMPLETE-REFERENCE-v2`, synchronize `docs/AGENT_RUN_STATE.json` to 36/93, select **037 — VLESS** as the next unfinished entry, refresh foreground activity and continue immediately. No runtime/device/Store/live-SRX receipt is introduced as an unstated completion condition.

## Completion decision

All exact 16 COMPLETE-REFERENCE-v2 gates are evidence-backed or explicitly bounded by proprietary/platform/version-specific N/A/uncertainty. Current Juniper Secure Connect/SRX/Junos/client/authentication/admin/lifecycle evidence is explicit without fabricating public source or generic IPsec parity.

Decision: **COMPLETE-REFERENCE-v2**.
