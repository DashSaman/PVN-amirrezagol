# 016 — Cisco Secure Client / AnyConnect — Current Proprietary Reference

Review date: 2026-08-14

Role in entry 016: **proprietary behavioral/platform reference only**. Cisco source code is not public and is not a reuse candidate.

## Current official product baseline

Cisco's current desktop release notes identify **Cisco Secure Client 5.1.18.314** as the primary recommended 5.1 release for Windows, macOS and Linux. The same release notes identify the AnyConnect VPN core as 5.1.18.314 and document current package naming for desktop deployment.

Official release notes:

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/release/notes/release-notes-cisco-secure-client-5-1.html

Cisco's current administrator documentation records the product rename from **AnyConnect Secure Mobility Client** to **Cisco Secure Client** in release 5, while some management/documentation surfaces still retain the AnyConnect name.

Official administrator guide:

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/admin/guide/cisco-secure-client-admin-guide-new/deploy-anyconnect-intro/before-you-begin.html

Cisco's AnyConnect 4.10 release notes state that the 4.x product line is end-of-life and that ongoing fixes/enhancements move to Cisco Secure Client 5.1.x.

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/anyconnect410/release/notes/release-notes-anyconnect-4-10.html

## Platform references

### Windows / macOS / Linux

The current 5.1 desktop release notes cover Windows, macOS and Linux. Cisco publishes separate predeploy/webdeploy package forms and current Linux RPM/DEB packaging. This is proprietary package/platform evidence only; PVNetwork must not copy Cisco package contents, UI, branding or private protocol implementation.

### Apple iOS / iPadOS

Cisco maintains a separate current release-note stream for Apple iOS. The page identifies Cisco Secure Client 5 as the current/recommended mobile family and documents use of Apple's Network Extension framework for VPN behavior.

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/release/notes/release-notes-apple-ios-cisco-secure-client-release-5-0.html

### Android

Cisco maintains a separate current Android release-note stream for Cisco Secure Client 5.

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/release/notes/release-notes-secure-client-for-android-release-5-0.html

### UWP / other variants

Cisco also retains a separate Universal Windows Platform release-note stream. Treat it as a distinct packaging/capability variant rather than evidence that all desktop features are identical.

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/release/notes/universal-windows-platform-release-5-0-x.html

## Feature / capability boundary

Cisco's current feature/OS documentation separates the AnyConnect VPN core from optional modules and capability areas such as posture, diagnostics, network access, visibility and Zero Trust components.

- https://www.cisco.com/c/en/us/td/docs/security/vpn_client/anyconnect/Cisco-Secure-Client-5/feature/guide/cisco-secure-client-features-licenses-and-oss-release-5.html

PVNetwork consequence:

- entry 016 means **AnyConnect-compatible VPN behavior**, not wholesale emulation of the entire Cisco Secure Client suite;
- posture/HostScan/Secure Firewall Posture remains a separate capability decision;
- SSO/browser/MFA behavior is capability/version specific;
- DART and Cisco-specific diagnostics are behavioral references only, not assets/code to copy;
- a tunnel handshake succeeding does not prove posture, SSO, management-tunnel or all enterprise policy features work.

## Source / license / reuse treatment

Cisco Secure Client is proprietary. For original-v1 research gates that ask for source-tree/build/internal code evidence:

- Cisco proprietary source tree: **N/A-PUBLIC-SOURCE / PROPRIETARY**;
- Cisco code reuse: **DO-NOT-COPY**;
- Cisco branding/assets: **DO-NOT-COPY**;
- Cisco official documentation/release notes: **REFERENCE-ONLY**;
- reusable protocol implementation candidate: pinned public `libopenconnect`, evaluated separately under `research/upstreams/openconnect-family/`.

Do not fabricate Cisco source visibility, private build system details, internal storage implementation, test suite or code-level architecture. Where Cisco does not publish implementation details, use official product behavior/platform documentation and keep the unknown explicit.

## Current top-client set for entry 016

1. **Cisco Secure Client 5.1.x / AnyConnect VPN core** — authoritative proprietary behavioral/product reference; desktop current reviewed release 5.1.18.314.
2. **OpenConnect v9.21** — public AnyConnect-compatible engine/reference and possible LGPL reuse candidate through its public API.
3. **OpenConnect GUI** — UI/desktop integration reference only; separate GPL application license.
4. **NetworkManager-openconnect** — Linux service/auth/secret/UI architecture reference; path-level licensing.

## Research closure rule

Current Cisco release/platform documentation is enough to identify the authoritative proprietary reference and its supported packaging families. It is **not** implementation/interoperability certification.

Future certification still requires exact Cisco headend/server version, auth mode, SSO/MFA/posture policy, transport behavior and PVNetwork platform/build evidence.