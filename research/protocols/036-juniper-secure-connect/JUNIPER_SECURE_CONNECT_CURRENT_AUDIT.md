# 036 — Juniper Secure Connect — Current Product Audit

Reviewed: 2026-08-14

Scope: V1 research evidence only. No implementation, live SRX/vSRX interoperability, Store/device, or production-certification claim.

## Canonical Juniper sources

- Overview: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/juniper-secure-connect-overview.html
- System requirements: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/juniper-secure-connect-system-requirements.html
- Download and license: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/download-juniper-secure-connect.html
- Authentication overview: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/authentication-in-secure-connect.html
- Get started / certificate prerequisite: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/get-started-with-juniper-secure-connect.html
- Local/PSK authentication: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/topic-map/local-authentication-with-local-ip-pool.html
- External RADIUS authentication: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/topic-map/secure-connect-vpn-using-ikev1-radius.html
- EAP-MSCHAPv2 certificate flow: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/topic-map/certificate-eap-mschapv2-authentication.html
- EAP-TLS certificate flow: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/topic-map/certificate-eap-tls-authentication.html
- J-Web 24.4 remote-access VPN wizard: https://www.juniper.net/documentation/us/en/software/jweb-srx24.4/jweb-srx/topics/task/j-web-security-ipsec-remote-access-vpn-juniper-secure-connect-creating.html
- Migration from Dynamic VPN: https://www.juniper.net/documentation/us/en/software/secure-connect/secure-connect-user-guide/topics/concept/migrate-users-to-juniper-secure-connect.html
- Secure Connect software licenses: https://www.juniper.net/documentation/us/en/software/license/juniper-licensing-user-guide/topics/concept/licenses-for-jsc.html

## Identity and architecture

Juniper Secure Connect is Juniper Networks' first-party remote-access VPN solution. Current Juniper documentation models the solution as an SRX Series Firewall or vSRX gateway plus the Juniper Secure Connect client application. The application downloads remote-access configuration from the SRX and establishes an encrypted tunnel to protected networks.

Juniper product documentation calls Secure Connect a client-based SSL-VPN solution, while its J-Web configuration is explicitly under `Network > VPN > IPsec VPN` and exposes IKE/IPsec settings. Therefore PVNetwork must preserve the product's vendor orchestration/configuration semantics while reusing standards-level IKE/IPsec knowledge only where exact behavior matches.

## Current platforms and gateway requirements

Current system requirements state that the gateway is an SRX Series Firewall or vSRX instance running Junos OS 20.3R1 or later. Current supported client OS families are:

- Windows 10 and later;
- macOS 10.15 and later through documented newer releases, including Apple-designed ARM processors on supported macOS releases;
- Android 10 and later;
- iOS/iPadOS 12 and later.

No Linux Secure Connect client is listed in the current official system-requirements table reviewed here; do not infer one from generic IPsec support.

## Authentication and certificates

Juniper documents local and external user authentication. External authentication, especially RADIUS, is recommended over local authentication. Configuration variants include local/PSK and external RADIUS paths, certificate-based EAP-MSCHAPv2, and EAP-TLS.

EAP-TLS requires PKI, a CA root certificate on each client, and a user-specific client certificate; local authentication is not supported in that EAP-TLS scenario. EAP-MSCHAPv2 certificate validation is paired with external/RADIUS user authentication. The SRX must use a signed, self-signed, or supported ACME/Let's Encrypt certificate rather than the default system-generated certificate.

## Routing and policy

The J-Web wizard exposes protected networks. `0.0.0.0/0` is the broad/default network; defining specific protected networks enables split tunneling. Source NAT is enabled by default in documented flows and can be disabled when the protected network has an explicit return route to the client address pool. Address pools and firewall policy remain SRX configuration surfaces.

Dynamic VPN migration documentation maps old remote-protected-resources into Secure Connect protected networks and recommends a fresh Secure Connect deployment rather than blindly converting legacy configuration.

## Client configuration and UI boundary

The current application is configured with a Gateway address/URL supplied by the SRX/J-Web remote-access configuration. Juniper documents remote-access configuration download from the SRX and client-side connect/disconnect workflows. Administrative setup is available in J-Web under IPsec VPN and through Junos CLI. The J-Web wizard exposes remote users, local gateway, protected networks, address pools, source NAT, server certificate, authentication and advanced IKE/IPsec settings.

The client also supports features documented in Juniper overview/guides such as pre-domain logon on Windows, configuration validation before connection, biometric credential protection where supported, MFA/SSO/SAML-related deployment surfaces and platform-specific client behavior. Exact screen-by-screen UI belongs to V2.

## Credentials and certificate storage

Juniper documentation identifies platform-specific certificate import/store paths for client certificates/CA material. Windows and macOS use Juniper Secure Connect application certificate directories in the documented certificate workflows; Android/iOS use their platform file/import flows. Exact proprietary password/token storage internals are not source-public and must not be invented.

## Install, download and licensing

Official Secure Connect downloads/install instructions are provided separately for Windows, macOS, Android and iOS/iPadOS. An active SRX-based license is required. Current Juniper docs state that each SRX includes two built-in concurrent-user licenses and additional concurrent users require purchased licenses; subscription SKUs are documented separately.

## Reconnect, lifecycle and diagnostics

Secure Connect is policy/configuration driven by SRX/vSRX. Juniper documentation covers connection establishment, configuration validation, migration, client lifecycle and endpoint/certificate prerequisites. Exact reconnect timing, roaming behavior and live failure recovery are platform/version test targets and are not hidden V1 gates.

## Source/version/license boundary

No canonical public repository for the complete Juniper Secure Connect client or SRX Secure Connect implementation was identified in the authoritative Juniper documentation reviewed here. Treat complete first-party code, binaries, UI assets and branding as proprietary/reference-only. The current documentation provides product/Junos release requirements and license SKUs rather than a reusable source commit; do not invent a source SHA.

## Product decision

`VENDOR JUNIPER REMOTE-ACCESS ADAPTER/REFERENCE / SRX-vSRX GATEWAY + PROPRIETARY FIRST-PARTY CLIENT / REUSE IKE-IPSEC COMPONENTS ONLY FOR EXACT STANDARD LAYERS / JUNIPER CONFIG-DOWNLOAD+AUTH+CERT+PROTECTED-NETWORK POLICY DISTINCT / NO GENERIC IPSEC COMPATIBILITY CLAIM / NO PUBLIC COMPLETE-SOURCE REUSE CLAIM`
