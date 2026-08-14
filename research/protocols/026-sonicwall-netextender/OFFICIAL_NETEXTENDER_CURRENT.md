# 026 — SonicWall NetExtender / SSL VPN — Current Official Reference

Review date: 2026-08-14

Role: authoritative proprietary product/platform/UX/gateway reference for original v1 research. SonicWall source code is not public and is not a reuse candidate.

## Current baseline

SonicWall's current NetExtender Feature Guide is dated May 2026 and covers NetExtender 10.3. Current Windows release notes identify **NetExtender Windows 10.3.5 (May 2026)**.

Official references:

- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/
- https://www.sonicwall.com/support/technical-documentation/docs/netextender-windows_release_notes/Content/Versions/v-10.3.5/v-10.3.5-windows-releasenotes.htm

Current 10.3.x client documentation covers Windows 10/11 on x86/x86_64/arm64 and supported Linux distributions/package forms. SonicWall's current appliance-support page separately warns that Windows 10.3.x is subject to limited firewall compatibility while 10.2.x is the fully supported firewall line in that compatibility statement. Therefore existence of a current 10.3.5 client is not blanket gateway compatibility.

## Scope boundary

Entry 026 covers the **NetExtender remote-access product/SSL-VPN compatibility surface**.

Current connection-profile UI can select:

- Auto;
- TLS (TCP);
- DTLS (UDP);
- WireGuard.

This means current NetExtender is not modeled as one immutable proprietary byte stream. Each transport is a version/gateway capability. Historical/current SonicWall documentation also describes NetExtender SSL VPN as PPP-based over its encrypted connection.

Do not conflate entry 026 with:

- **027 SonicWall Global VPN / IPsec**;
- Mobile Connect on Apple/Android/macOS;
- firewall management authentication merely because the management address is reachable through the VPN.

## Gateway/admin model

Official SonicOS SSL VPN guidance separates gateway configuration into:

- zone enablement;
- SSL VPN server port/domain;
- client address pool/device profile;
- client routes;
- DNS/WINS/suffix;
- user/group membership;
- VPN Access authorization;
- optional creation of client connection profiles.

Important acceptance rule: **client routes and authorization are separate**. A pushed/installed route does not prove policy allows the destination.

References:

- https://www.sonicwall.com/support/knowledge-base/kA1VN0000000DYM0A2
- https://www.sonicwall.com/support/technical-documentation/docs/sonicos-8-0-ssl-vpn/Content/ssl-vpn-about.htm

## Authentication / SSO / certificate behavior

Current feature documentation covers username/password/domain selection plus multi-factor/auth paths including TOTP, SMS/email/RSA-style factors, SAML/browser authentication, client certificates, supported smart-card certificate flows and Duo workflows.

For SAML/certificate-authentication domains, normal username/password prompts may be replaced by the external browser/certificate flow. Successful SAML SSL-VPN authentication does not automatically authenticate the user to firewall management; management identity/authorization is separate.

References:

- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/Content/Using-NetExtender/authentication-methods.htm
- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/Content/Using-NetExtender/adding-connection-profile.htm
- https://www.sonicwall.com/support/knowledge-base/sslvpn-saml-authentication-and-firewall-management/kA1VN0000000RtD0AU

## UI / menu / diagnostics map

The current Feature Guide provides a research-level navigation map.

### Connection/profile

- profile/connection selection;
- Add/Edit Connection;
- server;
- authentication domain;
- credentials when applicable;
- protocol selector;
- Save;
- Connect/Disconnect;
- all-Windows-users profile option where supported.

### `More > Properties`

Windows documents Settings, Connection Settings, Connection Script, Proxy, Packet Capture and Diagnostics. Linux documents Proxy, Certificate Settings and Settings.

Linux Settings includes MTU control (documented default 1420), TLS 1.2-only option and administrator-controlled auto-upgrade behavior.

### Diagnostics

Current Windows diagnostics include Ping, Traceroute, DNS Lookup, Network Info, iPerf and TCP Connection, with Event Viewer/log access.

### Logs

`More > Logs` supports view/export/clear/debug logging. Current documentation identifies `NetExtender.log`, richer Windows debug logging under `C:\ProgramData\SonicWall\NetExtender\`, log rotation and transport-specific logs in current builds.

### Packet capture

Windows can explicitly enable SSL-VPN packet capture from Properties. Packet capture is a sensitive diagnostic feature, not normal telemetry.

References:

- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/Content/Using-NetExtender/diagnostics.htm
- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/Content/Using-NetExtender/viewing-the-netextender-log.htm
- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/Content/Using-NetExtender/configuring-packet-capture-settings.htm

## Proxy / network behavior

Current documentation supports HTTPS proxy behavior for SSL-VPN including automatic browser settings, WPAD/automatic configuration script and manual proxy settings/credentials. Proxy authentication is separate from VPN authentication.

Reference:

- https://www.sonicwall.com/support/technical-documentation/docs/netextender-feature_guide/Content/Overview/proxy-configuration.htm

## Install / update / uninstall

Current documentation maps Windows standalone/MSI deployment, PreLogon/network logon, Always-On behavior, Linux installs, CLI, upgrade and uninstall. Windows Add/Remove Programs and Linux DEB/RPM/TGZ uninstall paths are explicit.

Current FAQ notes MSI deployments are administrator-controlled and do not use the same automatic-upgrade mechanism. Installation lifecycle is therefore separate from tunnel compatibility.

## Current versioned feature changes

Current docs record 10.3.4-era enhancements including Windows Notification Center, activity-based idle timeout, all-Windows-users profile sharing, browser selection for SAML and QR-code TOTP binding in CLI. UI/auth/profile behavior is versioned and must not be inferred from old screenshots.

## Public implementation ecosystem

### OpenConnect

OpenConnect issue `#143` tracks SonicWall NetExtender support. The issue remains open/To do and references development MR `!496`; discussion describes initial PPP/protocol work and a later mostly-working branch, but NetExtender support is not a current merged/released OpenConnect protocol.

Decision: `NOT-A-CURRENT-MATURE-DROP-IN / WATCH-UPSTREAM`.

- https://gitlab.com/openconnect/openconnect/-/issues/143

Do not advertise OpenConnect SonicWall support or design around an unmerged branch without a fresh source/license/security audit if it lands.

### GitHub NetExtender-named projects

Repository search is dominated by packaging/install wrappers around proprietary SonicWall binaries, not maintained independent protocol engines. They are not reusable tunnel-core evidence.

Security-research emulators/URI-handler projects are adversarial/test references, not production client/server candidates.

## Proprietary source boundary

Official NetExtender source, internal build system and internal CI/test suite are proprietary.

For source-specific v1 gates:

- source tree: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- private build/test internals: `N/A-PUBLIC-SOURCE / PROPRIETARY`;
- SonicWall code reuse: `DO-NOT-COPY`;
- SonicWall branding/assets: `DO-NOT-COPY`;
- official documentation/release notes: `REFERENCE-ONLY`.

No internal architecture is guessed to fill the gap.

## Security / privacy boundary

- certificate trust must not be silently weakened;
- SAML tokens, passwords, OTP, proxy credentials, client private keys/smart-card PINs and remembered profile secrets are separate classes;
- admin policy and local preferences are separate;
- packet captures/debug logs require explicit protected handling/redaction;
- route/DNS/authorization state must be verified after connection;
- `Connected` is not equivalent to authorization to all routed networks;
- Auto transport selection must expose the actual selected protocol for diagnostics/capabilities;
- no silent downgrade to a weaker/legacy path;
- exact TLS/DTLS/WireGuard parameters remain gateway/version-specific later evidence.

## Reuse decision

`VENDOR-SPECIFIC PRODUCT COMPATIBILITY TARGET / OFFICIAL CLIENT PRIMARY / NO MATURE PUBLIC DROP-IN SELECTED`

PVNetwork should not reimplement proprietary NetExtender framing/auth from black-box behavior. If OpenConnect support is eventually merged and maintained, re-audit the exact release/source/license before considering a product-owned adapter.

Where a gateway exposes an independently supported standard transport such as WireGuard, support that through PVNetwork's standards-protocol entry and exact gateway capability rather than claiming blanket NetExtender equivalence.

## Residual after v1

Later v2/implementation/certification must add exact SonicOS/SMA versions, installer hashes/signatures, server admin menus/topologies, exact TLS/DTLS/WireGuard/legacy flow and crypto, SAML/MFA/cert/Duo combinations, route/DNS/policy behavior, PreLogon/Always-On lifecycle, update/rollback, real gateway packet/interoperability evidence and mobile/Store architecture for any separate SonicWall client family.