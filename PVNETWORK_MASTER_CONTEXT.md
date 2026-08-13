# PVNetwork Universal Super Client — Master Project Context

> **Repository:** `DashSaman/PVN-amirrezagol`  
> **Development workspace reference:** `https://fictional-space-adventure-7v9764wr7644hrvw7.github.dev/`  
> **Product name:** **PVNetwork**  
> **Purpose of this file:** persistent project memory for human developers and AI coding agents.  
> **Status:** initial master research/context document, created from the complete PVNetwork planning/research discussion up to 2026-08-14.

---

## 0. Absolute project intent

PVNetwork must become a **production-grade, multi-platform, multilingual Universal VPN / Proxy Super Client**.

The product goal is:

> **Install one PVNetwork application and use as many major VPN, proxy, tunneling, enterprise VPN and anti-censorship configurations as technically possible, without needing a different client for every protocol.**

PVNetwork must **not** become merely:

- another V2Ray skin;
- another OpenVPN-only client;
- another Clash theme;
- a UI-only prototype;
- an app with fake Connect buttons or placeholder networking;
- an unfinished demo.

The target is a real, polished, branded, tested application suitable for public distribution.

---

# 1. Branding requirements

The official product name is **PVNetwork**.

The application **MUST use the exact official PVNetwork logo supplied by the owner**.

Rules:

- Do not generate or substitute a different logo.
- Do not replace the supplied logo with an AI-created approximation.
- Do not distort proportions.
- Do not arbitrarily recolor it.
- Derivative technical assets are allowed only when required, for example Android adaptive icon, Android TV banner, iOS icon set, macOS icon, Windows icon, Linux icon, tray icon, monochrome notification icon and splash asset.
- Branding strings must be centralized, not scattered throughout source code.
- Architecture should make future white-label builds possible without changing networking logic.

Centralized brand settings should cover at least:

- Product name
- Company/owner name
- Logo
- App icon
- Splash screen
- Website
- Support URL
- Privacy Policy URL
- Terms URL
- Telegram/support information
- Copyright
- Bundle/Application identifiers
- Build channel
- Version

Recommended future document: `docs/BRAND_GUIDELINES.md`.

---

# 2. Target platforms

PVNetwork is intended to support:

1. **Android phones**
2. **Android tablets**
3. **Android foldables**
4. **Android TV / Google TV**
5. **Windows**
6. **macOS**
7. **iPhone / iOS**
8. **iPad / iPadOS**
9. **Linux**

Platform-specific networking must use the correct OS architecture. Do not pretend one TUN/VPN implementation behaves identically everywhere.

Examples:

- Android: `VpnService`
- iOS/iPadOS: `NetworkExtension`, e.g. `NEVPNManager` / `NEPacketTunnelProvider` as appropriate
- macOS: appropriate Network Extension / packet tunnel architecture
- Windows: appropriate Wintun/WFP/native Windows networking/service architecture
- Linux: TUN, netlink, XFRM, kernel WireGuard, NetworkManager integration as appropriate

---

# 3. Android compatibility requirement

The user requested support for **all Android versions as broadly as practical**.

This must be interpreted correctly:

> Support the widest technically reasonable Android version range while still satisfying current Google Play security and target-SDK requirements.

Do not equate `minSdk` with `targetSdk`.

Maintain explicit compatibility records for:

- minSdk
- compileSdk
- targetSdk
- feature availability by Android/API version
- core availability by Android/API version
- tested devices/emulators

If one networking engine requires a newer Android version, investigate modular/conditional support instead of unnecessarily raising the minimum Android version for the entire application.

**Important:** Store target-SDK rules change. Re-check official Google Play documentation at every release. As of the project discussion in August 2026, Google Play requirements had moved to Android 16/API 36 for new apps/updates while TV requirements can differ. Treat stored values as historical context, not permanent policy.

---

# 4. Android TV / Google TV

Android TV must be treated as a **separate UX target**, not a stretched mobile screen.

Mandatory TV considerations:

- D-pad/remote navigation
- clear focus indicators
- no dependency on touch
- large readable 10-foot UI
- correct Back behavior
- proper TV launcher metadata
- TV icon/banner assets based on the supplied PVNetwork logo
- simple pairing/import UX
- subscription/account synchronization where useful

TV home screen should emphasize:

- Connection status
- Connect / Disconnect
- Server
- Country/location
- Protocol
- Latency
- Favorite servers
- Recent servers
- Subscription status

Long configuration entry with a TV remote should be avoided. Prefer:

- QR pairing
- short pairing code
- account sync
- subscription sync

---

# 5. Apple / App Store requirements

PVNetwork must be designed with App Store constraints from the beginning.

Use supported Apple networking APIs/entitlements. Do not rely on hacks or assume desktop core execution automatically works on iPhone.

Investigate current official requirements for:

- Network Extension
- VPN entitlements
- `NEVPNManager`
- `NEPacketTunnelProvider`
- Keychain
- App Groups where needed
- App Review notes
- regional VPN licensing/legal requirements
- developer organization/account requirements
- privacy/data handling

Historical project note: Apple App Store Review Guideline 5.4 imposes special rules for VPN apps, including organization-account and data/privacy constraints. **Re-check the current guideline before every release.**

The app must not sell browsing data, build advertising profiles from user traffic, or silently collect browsing history.

---

# 6. macOS requirements

Treat separately:

- Mac App Store distribution
- direct signed/notarized distribution

Investigate:

- sandboxing
- entitlements
- Network Extension
- signing
- notarization
- Hardened Runtime
- updater constraints

---

# 7. Microsoft Store / Windows requirements

Windows implementation should remain suitable for legitimate Microsoft Store distribution where possible.

Investigate:

- MSIX / supported Win32 Store packaging
- package identity
- capabilities
- privacy policy requirements
- signing
- update behavior
- x64
- ARM64 where feasible
- Windows 10/11 support matrix

Privileged helpers/services must be documented and justified.

---

# 8. Linux requirements

Linux is a real target, not a token build.

Evaluate suitable packaging options based on VPN privileges/TUN/system integration:

- AppImage
- Flatpak
- Snap
- `.deb`
- `.rpm`

Test representative distributions such as Ubuntu/Debian/Fedora/Arch-based systems before claiming broad Linux support.

---

# 9. Multilingual requirements

PVNetwork must be multilingual from the start.

Mandatory initial languages:

- **Persian / فارسی**
- **English**

Architecture must allow additional reviewed translations later, e.g. Arabic, Turkish, German, French, Spanish, Russian, Chinese, Japanese, Korean, Finnish, etc.

Rules:

- No hardcoded user-visible strings.
- Use localization resources.
- `Follow System` should be the default language mode.
- Manual language switching should be available.
- Do not ship poor unreviewed machine translations as officially supported languages.

---

# 10. Persian / RTL is a first-class feature

Persian must be implemented properly, not merely translated.

Test:

- RTL layout
- right alignment
- mirrored directional UI only where appropriate
- Persian punctuation
- Persian numerals preference where appropriate
- English technical terms inside RTL text
- mixed English/Persian server names
- IP addresses inside RTL
- URLs
- ports
- file paths
- hashes
- logs
- protocol identifiers

Do **not** reverse or corrupt technical strings such as:

- `192.168.1.1:443`
- URLs
- file paths
- hashes
- `VLESS`
- `Reality`
- `XTLS`

Example mixed text that must remain readable:

`سرور آلمان - VLESS 192.168.1.1:443 Reality / XTLS`

Recommended future docs:

- `docs/LOCALIZATION.md`
- `docs/PERSIAN_RTL_TESTS.md`

---

# 11. Core architectural principle

Do not build one giant monolithic VPN engine.

Use:

```text
PVNetwork UI
      |
      v
Unified Application Layer
      |
      +-- Profile Manager
      +-- Subscription Manager
      +-- Credential Manager
      +-- Connection Manager
      +-- Routing Manager
      +-- DNS Manager
      +-- Diagnostics
      +-- Logging
      +-- Update Manager
      +-- Localization Manager
      +-- Platform Integration
      |
      v
Core Adapter Layer
      |
      +-- OpenVPN Adapter
      +-- WireGuard Adapter
      +-- AmneziaWG Adapter
      +-- Xray Adapter
      +-- Mihomo Adapter
      +-- OpenConnect Adapter
      +-- IPsec/strongSwan/native Adapter
      +-- SoftEther Adapter
      +-- Hysteria Adapter if retained separately
      +-- platform-native adapters
```

The UI must never directly depend on one VPN core.

A generic adapter should expose capabilities similar to:

```text
probeCapabilities()
validateProfile()
normalizeProfile()
generateConfig()
start()
stop()
restart()
healthCheck()
getState()
getStatistics()
getLogs()
collectDiagnostics()
supportsFeature()
getVersion()
```

---

# 12. Do not implement cryptography from scratch

Absolute rule:

Do not invent or independently implement:

- encryption algorithms
- TLS
- WireGuard cryptography
- IPsec cryptography
- VPN authentication handshakes

Prefer mature and battle-tested upstream projects.

Before integrating any dependency verify:

- current activity
- security history
- license
- supported platforms
- maintainer status
- release cadence
- binary/library integration method
- Store compatibility
- redistribution obligations

---

# 13. Universal import system

PVNetwork should intelligently import/detect appropriate forms including:

- `.ovpn`
- WireGuard `.conf`
- JSON
- YAML
- Xray JSON
- sing-box JSON when supported through an allowed path
- Clash/Mihomo YAML
- subscription URLs
- QR codes
- clipboard content
- supported URI schemes

Examples:

- `vless://`
- `vmess://`
- `trojan://`
- `ss://`
- `hysteria://`
- `hysteria2://`
- `tuic://`
- `wireguard://`
- `socks://`
- `http://`
- `https://`

Import workflow:

```text
Input
 -> format detection
 -> syntax validation
 -> protocol detection
 -> core capability detection
 -> normalization
 -> semantic validation
 -> save
```

Do not silently discard vendor-specific or unsupported fields. Warn when conversion is lossy.

PVNetwork should define its own **versioned internal profile model** (`PVProfile`) rather than storing every user profile directly in the syntax of one upstream engine.

Suggested logical model:

- identity
- endpoint
- authentication
- transport
- security
- routing
- DNS
- metadata
- subscription
- core-specific extensions

---

# 14. Subscription management

Expected features:

- add/delete subscription
- manual refresh
- automatic refresh
- last refresh status
- expiration information when available
- traffic usage when available
- profile count
- groups/tags
- favorites
- search
- sorting
- duplicate detection

Refreshing a subscription must not unexpectedly destroy user customizations.

---

# 15. Connection UX

The basic user flow should remain:

```text
Open PVNetwork
 -> choose server/profile
 -> Connect
 -> Connected
```

Dashboard should display useful state such as:

- connection status
- server
- country/location
- protocol
- public IP where appropriate
- upload/download
- session duration
- latency

Use one clear primary **Connect / Disconnect** control.

Provide separate Simple and Advanced modes.

Simple mode should not require knowledge of XTLS, ALPN, MTU, packet encoding, etc.

---

# 16. Routing / DNS / reliability

Routing modes should account for:

- Global
- Rule-based
- Direct
- Smart
- Split tunnel
- Per-app where OS permits

Rules may match:

- domain
- domain suffix
- IP/CIDR
- port
- protocol
- GeoIP
- GeoSite-like data
- application/process

Special route categories may include AI, GitHub, Google, Meta, streaming, gaming, banking, Iranian/domestic services and selected international services. Do not hardcode huge volatile IP lists in application code.

DNS should be first-class:

- system DNS
- custom DNS
- DoH
- DoT
- direct DNS
- remote DNS
- split DNS
- DNS cache control
- DNS leak prevention

Reliability requirements:

- real kill switch where promised
- auto reconnect
- network-change awareness
- reconnect backoff
- sleep/resume handling
- crash cleanup
- route cleanup
- DNS cleanup
- no reconnect storms

Suggested connection states:

```text
Disconnected
Preparing
RequestingPermission
Connecting
Authenticating
EstablishingTunnel
Connected
Reconnecting
Disconnecting
Error
```

---

# 17. Privacy and security

Never commit or log:

- passwords
- auth tokens
- private keys
- subscription secrets
- signing secrets
- API secrets
- cookies/auth headers

Use OS secure storage, such as Android Keystore, Apple Keychain, appropriate Windows secure storage and Linux secret storage.

No hidden telemetry.

No browsing-history monetization.

No silent traffic profiling.

Diagnostics/support bundles must redact secrets.

---

# 18. Store-ready requirements

At each release, re-check the **current** official policies for:

- Google Play
- Android TV / Google TV
- Apple App Store
- Mac App Store
- Microsoft Store

Store policy is a live constraint, not a one-time checklist.

Important release concerns include:

- target SDK
- VPNService usage
- foreground/background service behavior
- permissions
- Data Safety / privacy declarations
- billing rules if digital subscriptions are sold
- account deletion if required
- Apple VPN/Network Extension rules
- entitlements
- Microsoft package capabilities
- signing
- store metadata/localization
- third-party licenses

---

# 19. Learn from successful open-source projects

PVNetwork must actively study mature projects rather than developing in isolation.

Important references include:

- `2dust/v2rayN`
- `2dust/v2rayNG`
- Hiddify
- Happ
- Amnezia VPN
- Clash Verge Rev
- FlClash
- Mihomo Party
- NekoBox
- Karing
- Throne
- `SagerNet/sing-box`
- `XTLS/Xray-core`
- `MetaCubeX/mihomo`
- OpenConnect
- OpenVPN
- WireGuard
- SoftEther

Do not only study features. Also study:

- open issues
- closed bugs
- pull requests
- regressions
- release notes
- security advisories
- performance issues
- crash reports
- reconnect bugs
- DNS leaks
- route cleanup failures
- battery drain
- subscription parser issues
- localization/RTL problems
- Store rejection problems

The goal is to convert relevant competitor/upstream failures into **PVNetwork preventive tests**.

Do not visually clone another app and do not copy incompatible-license code.

---

# 20. Important competitor observations

Approximate popularity observed during research (GitHub stars fluctuate and must not be treated as user counts):

- Clash Verge Rev: very large desktop project, roughly 100k+ stars
- v2rayN: very large multi-core client, roughly 100k+ stars
- v2rayNG: major Android Xray/V2Ray client, tens of thousands of stars
- FlClash: major cross-platform Mihomo client, tens of thousands
- Hiddify: major cross-platform sing-box-based client, tens of thousands
- sing-box: major modern networking core
- NekoBox/Karing/Mihomo Party/Clash Nyanpasu/Throne: useful architectural and UX references
- Amnezia VPN: particularly important because it combines classic VPN and anti-censorship technologies in one client

Architectural lessons:

- **v2rayN:** excellent reference for Multi-Core GUI architecture.
- **Hiddify:** strong reference for simplified UX, cross-platform delivery, subscription handling and Persian-awareness.
- **Clash Verge Rev / FlClash / Mihomo Party:** routing, groups, URL-test, fallback, load balancing, subscription UX.
- **Amnezia VPN:** important reference for combining OpenVPN, WireGuard, IKEv2, AmneziaWG and Xray-style functionality.
- **OpenConnect:** important core for multiple enterprise SSL-VPN families.

---

# 21. License strategy notes

License review is mandatory before embedding or redistributing code.

Preliminary high-value candidates identified in research:

- **Mihomo:** MIT — attractive for commercial integration.
- **Xray-core:** MPL-2.0 — attractive with proper MPL compliance.
- **WireGuard / wireguard-go:** MIT.
- **AmneziaWG / amneziawg-go:** MIT.
- **SoftEtherVPN:** Apache-2.0.
- **Hysteria:** MIT.
- **shadowsocks-rust:** MIT.
- **uTLS:** BSD-3-Clause.
- **kcp-go:** MIT.
- **OpenConnect:** LGPL-2.1 family — usable with careful compliance/architecture.
- **strongSwan:** copyleft licensing considerations; design architecture carefully and perform legal review.
- **sing-box:** GPLv3 — excellent reference/capability source, but embedding in a closed/commercial app requires serious licensing analysis.
- **Clash Verge Rev and many popular GUIs:** often GPL-family; useful for study, not automatically suitable to copy/embed.
- **snx-rs (Check Point):** AGPL-3.0; technically interesting but direct embedding into closed product is problematic without a compatible licensing strategy.
- **Cloak:** GPL-3.0.

**Do not treat this section as final legal advice. Re-verify each pinned dependency and file-level license before release.**

Recommended future docs:

- `docs/THIRD_PARTY_LICENSES.md`
- `docs/DEPENDENCY_MATRIX.md`

---

# 22. Master protocol/technology scope — 93 items

Important: these **93 items are not all independent VPN protocols**. They include VPN protocols, enterprise compatibility families, proxy protocols, mesh/overlay systems, site-to-site tunnel technologies, security/obfuscation layers and transports.

For marketing, do **not** say “93 VPN protocols.” A more technically accurate claim after actual verification would be similar to:

> “70+ VPN, proxy, tunnel and enterprise networking technologies, with 90+ protocol, transport and security combinations.”

Only use marketing counts after PVNetwork itself has tested and documented them.

---

## A. Classic VPN / IPsec / L2 overlay

### 1. OpenVPN
Best/reference clients:
- OpenVPN Connect
- Pritunl Client
- Amnezia VPN
- Tunnelblick on macOS

Preferred PVNetwork core candidate:
- `OpenVPN/openvpn3`

Notes:
- mature OpenVPN client core
- strong cross-platform reference
- verify exact current licensing path and redistribution obligations before shipping

PVNetwork priority: **HIGH**

### 2. WireGuard
Best/reference clients:
- official WireGuard
- Amnezia
- Tailscale
- NetBird

Preferred core:
- official WireGuard implementations / `WireGuard/wireguard-go`
- use native kernel WireGuard where appropriate

PVNetwork priority: **HIGH**

### 3. AmneziaWG
Best/reference:
- Amnezia VPN
- official AmneziaWG clients

Preferred core:
- `amnezia-vpn/amneziawg-go`

PVNetwork priority: **HIGH** for censorship-resistant WireGuard compatibility.

### 4. IKEv2/IPsec
Best/reference:
- strongSwan
- native Apple VPN
- native Windows VPN
- strongSwan Android

Preferred approach:
- native OS IPsec where suitable
- strongSwan-style engine/adapters where required

PVNetwork priority: **HIGH/MEDIUM**

### 5. IKEv1/IPsec
Best/reference:
- strongSwan
- vendor/native legacy implementations

Priority: **legacy compatibility**; do not make it default.

### 6. IPsec ESP
Best implementation:
- OS/kernel IPsec stack
- Linux XFRM + strongSwan

Not a separate end-user UI protocol; treat as part of IPsec capabilities.

### 7. IPsec AH
Best implementation:
- OS/kernel IPsec stack / Linux XFRM

Advanced/legacy component.

### 8. L2TP/IPsec
Best/reference:
- native OS clients
- strongSwan + L2TP stack on Linux
- SoftEther ecosystem

Priority: compatibility only.

### 9. L2TPv3
Best/reference:
- Linux networking
- SoftEther
- routers

Mostly site-to-site.

### 10. L2TPv3/IPsec
Best/reference:
- SoftEther
- Linux + strongSwan

Mostly site-to-site.

### 11. SSTP / MS-SSTP
Best/reference:
- Windows native SSTP
- `sstp-client`
- SoftEther ecosystem

Preferred:
- Windows native when possible
- dedicated/open implementation on other OSes only after license/interop review

### 12. PPTP
Legacy/insecure.

Best/reference:
- legacy OS implementations
- PPTP Client Linux

PVNetwork policy:
- only optional legacy compatibility
- show security warning
- never recommend/default

### 13. SoftEther VPN Protocol
Best/reference client:
- official SoftEther VPN Client

Preferred core:
- `SoftEtherVPN/SoftEtherVPN`

License note: Apache-2.0 family.

Priority: **HIGH/MEDIUM** because it increases unique compatibility.

### 14. EtherIP
Best/reference:
- SoftEther
- platform networking

Mostly site-to-site.

### 15. EtherIP/IPsec
Best/reference:
- SoftEther + IPsec

Mostly advanced/site-to-site.

---

## B. Enterprise VPN compatibility

### 16. Cisco AnyConnect / Cisco Secure Client-compatible VPN
Best/reference clients:
- Cisco Secure Client
- OpenConnect
- NetworkManager OpenConnect

Preferred OSS core:
- `openconnect/openconnect`

Priority: **HIGH**

### 17. OpenConnect / ocserv-compatible
Best/reference:
- OpenConnect
- NetworkManager-openconnect

Preferred core:
- OpenConnect

Priority: **HIGH**

### 18. Palo Alto GlobalProtect
Best/reference:
- official GlobalProtect
- OpenConnect-compatible implementations

Preferred candidate:
- OpenConnect

Requirement:
- real E2E interoperability tests against supported gateways before marking ✅.

### 19. Fortinet FortiGate SSL VPN
Best/reference:
- FortiClient
- OpenConnect
- `openfortivpn`

Preferred PVNetwork order:
1. test OpenConnect
2. use openfortivpn as reference/fallback candidate if required

### 20. Pulse Secure
Best/reference:
- vendor client
- OpenConnect

Preferred candidate:
- OpenConnect

### 21. Ivanti Connect Secure
Best/reference:
- Ivanti client
- OpenConnect-compatible paths where applicable

Do not claim all Ivanti versions without E2E tests.

### 22. Juniper Network Connect
Best/reference:
- OpenConnect
- legacy official client

Preferred core:
- OpenConnect

### 23. F5 BIG-IP SSL VPN
Best/reference:
- F5 Access
- OpenConnect support where applicable

Preferred candidate:
- OpenConnect + interoperability matrix

### 24. Array Networks SSL VPN
Best/reference:
- official vendor client
- investigate current OpenConnect compatibility

Status for PVNetwork:
- experimental/planned until real testing proves support

### 25. Check Point VPN / SNX
Best/reference:
- Check Point official clients
- `ancwrd1/snx-rs`

Important research finding:
- `snx-rs` is a modern Rust client supporting Check Point-related SSL/IPsec scenarios on multiple desktop OSes.
- License: AGPL-3.0; very useful as a reference but direct closed-source embedding is problematic without a compatible licensing plan.

### 26. SonicWall NetExtender / SSL VPN
Best/reference:
- official NetExtender

Current PVNetwork research conclusion:
- no drop-in OSS engine as mature/universal as OpenConnect was identified.
- keep as research/experimental until a tested implementation exists.

### 27. SonicWall Global VPN / IPsec
Best/reference:
- SonicWall Global VPN Client
- strongSwan/native IPsec as interoperability candidates where standard IPsec applies

### 28. Sophos SSL VPN
Best/reference:
- Sophos Connect
- OpenVPN Connect
- Tunnelblick

Key point:
- Sophos SSL VPN commonly uses OpenVPN-compatible profiles.

Preferred PVNetwork core:
- OpenVPN 3 path.

### 29. Sophos IPsec Remote Access
Best/reference:
- Sophos Connect
- strongSwan/native IPsec candidates

### 30. WatchGuard IKEv2 VPN
Best/reference:
- WatchGuard/native IKEv2 clients
- strongSwan

Preferred:
- native/strongSwan adapter.

### 31. WatchGuard SSL VPN
Best/reference:
- WatchGuard Mobile VPN with SSL
- OpenVPN-compatible stack where profiles are standard

Preferred:
- test OpenVPN core compatibility.

### 32. WatchGuard L2TP VPN
Best/reference:
- native L2TP/IPsec
- strongSwan + L2TP stack

### 33. Aruba VIA
Best/reference:
- official Aruba VIA

Current conclusion:
- no sufficiently proven general OSS drop-in replacement identified for blanket support.
- investigate standard IPsec modes separately.

### 34. Citrix Secure Access / Gateway VPN
Best/reference:
- official Citrix Secure Access

Current conclusion:
- research/experimental until a mature reusable open implementation is verified.

### 35. Barracuda TINA VPN
Best/reference:
- official Barracuda VPN Client

Current conclusion:
- vendor-specific; no mature general reusable TINA engine identified during initial research.

### 36. Juniper Secure Connect
Best/reference:
- official Juniper Secure Connect
- standard IPsec/native/strongSwan paths where the gateway uses interoperable IKE/IPsec

---

## C. Xray / modern proxy protocols

### 37. VLESS
Best/reference clients:
- v2rayN
- v2rayNG
- Hiddify
- Clash Verge Rev
- FlClash
- Karing
- NekoBox

Preferred PVNetwork cores:
1. `XTLS/Xray-core`
2. Mihomo where useful

Priority: **VERY HIGH**

### 38. VMess
Best/reference:
- v2rayN
- v2rayNG
- Hiddify
- Clash/Mihomo clients

Preferred:
- Xray-core / Mihomo

### 39. Trojan
Best/reference:
- Hiddify
- v2rayN/v2rayNG
- Mihomo/Clash clients

Preferred:
- Xray-core or Mihomo

### 40. Shadowsocks
Best/reference:
- `shadowsocks/shadowsocks-rust`
- Hiddify
- Mihomo clients
- v2rayN/v2rayNG

Preferred:
- Mihomo and/or shadowsocks-rust depending architecture

### 41. Shadowsocks 2022
Best/reference:
- compatible modern Mihomo/sing-box/Shadowsocks implementations

Preferred:
- verify capability in the pinned Mihomo or standalone SS implementation.

### 42. Hysteria 1
Legacy predecessor.

Policy:
- only for backward compatibility if real user demand exists.
- prioritize Hysteria2.

### 43. Hysteria2
Best/reference:
- official Hysteria
- Hiddify
- Mihomo clients
- sing-box-based clients

Preferred core candidate:
- `apernet/hysteria`

License note: MIT.

Priority: **HIGH**

### 44. TUIC
Best/reference:
- Mihomo/Clash ecosystem
- sing-box ecosystem
- official/reference TUIC implementations

Preferred:
- first attempt through Mihomo to avoid unnecessary extra engines.

### 45. AnyTLS
Best/reference:
- Mihomo
- sing-box
- `anytls-go` reference implementation

Preferred:
- Mihomo if full capability is verified.

### 46. ShadowTLS
Best/reference:
- official ShadowTLS
- sing-box ecosystem
- modern compatible clients

Reference core:
- `ihciah/shadow-tls`

License note: MIT.

For PVNetwork cross-platform, prefer a compatible engine already present if it avoids a separate platform-limited process.

### 47. NaiveProxy
Best/reference:
- `klzgrad/naiveproxy`

Uses Chromium networking stack.

Before embedding:
- review binary size
- Chromium dependencies
- licensing
- platform/store impact

### 48. Snell
Best/reference:
- Surge-compatible ecosystem
- implementations in modern multi-protocol cores such as sing-box-style stacks

PVNetwork priority: lower unless actual user demand exists.

### 49. SOCKS4
Best/reference:
- standard proxy libraries
- multi-protocol cores

No separate heavy engine should be added solely for SOCKS4.

### 50. SOCKS4a
Same strategy as SOCKS4.

### 51. SOCKS5
Best/reference:
- Xray
- Mihomo
- sing-box-style stacks
- OpenSSH dynamic forwarding

Preferred:
- use existing Xray/Mihomo adapter.

### 52. HTTP Proxy
Best/reference:
- Mihomo
- Xray
- Hysteria/standard HTTP proxy stacks

Preferred:
- existing core, no dedicated engine unless necessary.

### 53. HTTPS / HTTP CONNECT
Best/reference:
- Mihomo
- Xray
- NaiveProxy
- standard platform HTTP stacks

### 54. SSH Tunnel
Best/reference:
- OpenSSH
- Hiddify-style UX as reference
- PuTTY/plink on Windows as behavior reference

Preferred reusable implementation:
- OpenSSH Portable or appropriate maintained SSH library.

### 55. Tor SOCKS
Best/reference:
- Tor daemon/Tor Browser
- Arti (Rust Tor implementation) for modern embedding research

Candidate:
- evaluate `arti-client` if feature parity and licensing fit.

---

## D. Mesh / overlay technologies

### 56. Tailscale
Best/reference:
- official Tailscale client

Repo:
- `tailscale/tailscale`

Note:
- more than a simple VPN protocol; includes control-plane/ecosystem behavior.

### 57. ZeroTier
Best/reference:
- ZeroTier One

Repo:
- `zerotier/ZeroTierOne`

License note:
- core portions use MPL-2.0 but perform path-level/license audit before embedding.

### 58. NetBird
Best/reference:
- official NetBird client

Repo:
- `netbirdio/netbird`

Note:
- client and server/control-plane licensing can differ; audit specific reused components.

### 59. Netmaker
Best/reference:
- Netmaker client/agent ecosystem

Primarily WireGuard orchestration; likely not a core requirement for consumer PVNetwork v1.

### 60. Nebula
Best/reference:
- `slackhq/nebula`

Cross-platform overlay technology.

### 61. Tinc
Best/reference:
- official tinc

Older mesh VPN; lower priority unless needed.

### 62. innernet
Best/reference:
- innernet

WireGuard-based network manager/orchestration tool.

---

## E. Router / site-to-site technologies

### 63. GRE
Best implementation:
- Linux kernel
- router OS networking stacks

Use platform adapter; not a consumer-core process.

### 64. GRE over IPsec
Best/reference:
- Linux kernel + strongSwan
- router implementations

Advanced/site-to-site only.

### 65. IP-in-IP / IPIP
Best:
- native Linux/kernel networking.

### 66. IPIP over IPsec
Best:
- Linux kernel + strongSwan.

### 67. VTI/IPsec
Best:
- Linux VTI/XFRM + strongSwan.

### 68. XFRM/IPsec
Best:
- Linux kernel XFRM + strongSwan.

### 69. VXLAN
Best:
- native Linux/network stack.

### 70. VXLAN over IPsec
Best:
- Linux VXLAN + IPsec/strongSwan.

### 71. DMVPN
Best/reference:
- Cisco IOS/IOS-XE

OSS building blocks:
- Linux mGRE
- strongSwan
- FRRouting NHRP

This is an advanced/router feature, not a normal consumer VPN mode.

### 72. Cisco FlexVPN
Best/reference:
- Cisco ecosystem

Because FlexVPN is IKEv2-based, strongSwan/native IKEv2 is an interoperability candidate.

Require Cisco E2E testing.

### 73. GETVPN
Best/reference:
- Cisco ecosystem

Current conclusion:
- no mature general end-user OSS replacement identified for PVNetwork v1.
- keep out of initial release unless a concrete use case appears.

---

## F. Security / obfuscation layers — NOT independent VPN protocols

### 74. REALITY
Best/reference:
- Xray-core
- v2rayN/v2rayNG
- Hiddify
- Mihomo-compatible clients

Preferred PVNetwork core:
- Xray-core.

### 75. XTLS
Best/reference:
- Xray-core
- v2rayN/v2rayNG

Preferred:
- Xray-core.

### 76. XTLS Vision
Best/reference:
- Xray-core
- v2rayN/v2rayNG
- compatible modern clients

Preferred:
- Xray-core.

### 77. TLS
Not a VPN protocol.

Use mature TLS stacks inside each selected engine/OS.

Never implement TLS from scratch.

### 78. uTLS / TLS fingerprinting
Best/reference library:
- `refraction-networking/utls`

License note: BSD-3-Clause.

Useful for Go-based networking cores that need ClientHello fingerprint behavior.

### 79. Cloak
Best/reference:
- official Cloak
- Amnezia usage patterns

Repo:
- `cbeuw/Cloak`

License note: GPL-3.0; direct embedding requires care.

### 80. TLS Fragmentation
Best/reference:
- capability within Xray/modern proxy cores

Treat as an engine feature, not a separate core.

---

## G. Transports — NOT independent VPN protocols

### 81. TCP
Use OS TCP stack.

### 82. UDP
Use OS UDP stack.

### 83. QUIC
Best/reference depending use-case:
- Hysteria2
- Mihomo
- mature QUIC libraries

Do not add an extra core solely because QUIC exists.

### 84. WebSocket
Best/reference:
- Xray
- Mihomo
- standard WS libraries

### 85. HTTP/1.1
Use engine/OS standard HTTP stack.

### 86. HTTP/2
Use engine/OS standard HTTP/2 stack; Xray where part of proxy transport.

### 87. HTTP/3
Use mature QUIC/HTTP3 stack; Hysteria/Mihomo where appropriate.

### 88. gRPC
Best/reference for proxy transports:
- Xray and compatible modern engines.

### 89. mKCP
Best/reference:
- Xray-core
- v2rayN/v2rayNG as client behavior references

Treat as an Xray transport feature.

### 90. KCP
Reference library:
- `xtaci/kcp-go`

License note: MIT.

Only embed directly if existing core coverage is insufficient.

### 91. XHTTP
Best/reference:
- Xray-core
- current Xray-compatible clients

Preferred:
- Xray-core.

### 92. RAW
Best/reference:
- Xray transport context where applicable

Treat as engine feature.

### 93. DTLS
Best/reference in our enterprise VPN scope:
- OpenConnect/AnyConnect data-channel implementation

Treat as part of OpenConnect stack, not a separate user-facing core.

---

# 23. Recommended core set for PVNetwork

The 93-item matrix **does not imply 93 engines**.

Current recommended architecture aims for roughly **9 major engines/adapters**:

## Tier 1 — major engines/adapters

### 1. OpenVPN 3
Coverage:
- OpenVPN TCP/UDP
- many OpenVPN-compatible vendor profiles such as Sophos SSL VPN where standard

### 2. Official WireGuard implementations
Coverage:
- WireGuard

### 3. AmneziaWG
Coverage:
- AWG/AmneziaWG

### 4. Xray-core
Coverage:
- VLESS
- VMess
- Trojan
- REALITY
- XTLS/Vision
- XHTTP
- mKCP
- multiple transports

### 5. Mihomo
Coverage:
- Clash/Mihomo ecosystem
- advanced routing/proxy groups
- many modern proxy protocols
- URL-test/fallback/load balancing/rule providers

### 6. OpenConnect
Coverage candidates:
- Cisco AnyConnect
- ocserv
- GlobalProtect
- Juniper/Pulse-family compatibility
- F5/Fortinet/other supported families depending current version and tests

### 7. strongSwan + native IPsec adapters
Coverage:
- IKEv2/IPsec
- IKEv1 where required
- standard/vendor IPsec interoperability

### 8. SoftEther
Coverage:
- SoftEther native
- some legacy/site-to-site technologies

### 9. Hysteria2 official engine, optional dedicated engine
Coverage:
- Hysteria2

Decision rule:
- If Mihomo already covers the exact required Hysteria2 capability/performance adequately, avoid unnecessary duplicate core unless dedicated Hysteria adds measurable value.

---

# 24. Tier 2 supporting libraries / references

Candidates:

- `shadowsocks-rust` — MIT
- `uTLS` — BSD-3-Clause
- `kcp-go` — MIT
- Arti / `arti-client` — evaluate for Tor embedding

---

# 25. Projects to study, not automatically embed

Important references for behavior/UI/bugs:

- v2rayN
- v2rayNG
- Hiddify
- Happ
- Clash Verge Rev
- FlClash
- Mihomo Party
- Karing
- NekoBox
- Throne
- Amnezia VPN
- sing-box
- snx-rs

Reasons not to blindly embed them include:

- GPL/AGPL obligations
- duplicated functionality
- app-specific architecture
- branding/UI copyright
- unnecessary binary size
- Store restrictions

---

# 26. Core selection rules

When choosing whether to add a core, optimize for:

```text
maximum useful protocol coverage
minimum maintenance burden
minimum binary size
minimum attack surface
maximum platform coverage
store compatibility
license compatibility
real reliability
```

Before adding any new core ask:

> Can one of the already integrated engines reliably support this capability?

---

# 27. Protocol capability matrix statuses

Future `docs/PROTOCOL_MATRIX.md` should use explicit evidence states:

- ✅ Supported & tested by PVNetwork
- 🟡 Implemented but not fully verified
- 🧪 Experimental
- 📋 Planned
- ❌ Not supported
- ⚠️ Legacy/insecure

Every row should eventually record:

- protocol/technology
- category
- core
- Windows
- Android
- Android TV
- macOS
- Linux
- iOS/iPadOS
- import support
- tested version
- real E2E status
- limitations

Never mark ✅ merely because an upstream project claims support.

---

# 28. Testing strategy

Compilation is not proof of protocol support.

Use:

## Unit tests
- parsers
- profile normalization
- validators
- state machine
- routing rules
- subscription parsing
- localization helpers

## Integration tests
- core launch
- generated configuration
- TUN creation
- DNS
- routes
- disconnect cleanup
- profile import

## End-to-end tests
Establish real connections against test endpoints where infrastructure is available.

## Regression tests
Whenever practical, every fixed production bug gets a regression test.

## Competitor-derived tests
If an upstream/competitor issue shows a bug relevant to PVNetwork, reproduce the risk and add a preventive test where possible.

---

# 29. Real device / compatibility testing

Emulators are not enough for final acceptance.

Maintain a matrix covering:

- platform
- OS version
- architecture
- device
- install
- connect
- DNS
- IPv6
- reconnect
- kill switch
- status

Particularly important:

- Android VpnService behavior
- Android TV remote/focus behavior
- iOS Network Extension
- Windows tunnel/service behavior
- macOS Network Extension

---

# 30. Store release gates

Before claiming Store Ready:

- current Store policies rechecked
- privacy policy complete
- data declarations complete
- permissions justified
- licenses reviewed
- package signed
- no test credentials
- no hidden features
- localized metadata ready
- store screenshots final
- support contact valid
- account deletion flow where required
- store-specific billing rules verified if applicable

---

# 31. Recommended persistent GitHub documentation

GitHub must be the project's durable memory.

The repository should eventually contain at least:

```text
README.md
AGENTS.md

PVNETWORK_MASTER_CONTEXT.md

docs/
  PROJECT_STATE.md
  ROADMAP.md
  ARCHITECTURE.md
  DECISIONS.md
  PROTOCOL_MATRIX.md
  FEATURE_MATRIX.md
  COMPATIBILITY_MATRIX.md
  TEST_REPORT.md
  KNOWN_ISSUES.md
  TECH_DEBT.md
  CORE_SELECTION.md
  LOCALIZATION.md
  PERSIAN_RTL_TESTS.md
  ANDROID_COMPATIBILITY.md
  ANDROID_TV.md
  SECURITY_MODEL.md
  PRIVACY_DATA_MAP.md
  PERMISSIONS.md
  DEPENDENCY_MATRIX.md
  THIRD_PARTY_LICENSES.md
  BUILD.md
  RELEASE.md
  BRAND_GUIDELINES.md

  store/
    STORE_MATRIX.md
    GOOGLE_PLAY_COMPLIANCE.md
    APPLE_STORE_COMPLIANCE.md
    MICROSOFT_STORE_COMPLIANCE.md
    STORE_REJECTIONS.md

  research/
    COMPETITOR_MATRIX.md
    COMPETITOR_LESSONS.md
```

---

# 32. AI agent operating rules

Any AI agent working on this repository must treat GitHub as persistent project memory.

## Mandatory session start

Before doing meaningful work:

1. inspect `git status`
2. inspect current branch
3. inspect recent commits
4. read `AGENTS.md`
5. read `PVNETWORK_MASTER_CONTEXT.md`
6. read `docs/PROJECT_STATE.md`
7. read `docs/ROADMAP.md`
8. read `docs/ARCHITECTURE.md`
9. read `docs/KNOWN_ISSUES.md`
10. read `docs/TEST_REPORT.md`
11. read `docs/DECISIONS.md`
12. inspect relevant code
13. run the smallest relevant verification test
14. continue from the last verified state

If those docs do not exist yet, create them during project foundation work.

---

# 33. Context-loss recovery

If an AI agent loses conversation context:

- do not restart the project
- do not invent status
- do not ask the owner to repeat everything if the repository contains the answer

Recover using:

1. Git history
2. `PVNETWORK_MASTER_CONTEXT.md`
3. `PROJECT_STATE.md`
4. roadmap
5. tests
6. known issues
7. source code
8. current diff

Repository evidence overrides chat memory.

---

# 34. No-loop policy

The agent must not repeatedly perform the same failed action without new evidence.

If the same exact fix fails twice:

- do not try it unchanged a third time
- read upstream docs/issues/source
- inspect logs
- reduce to a minimal reproduction
- instrument the code
- compare with a known working implementation
- change strategy

Record significant failures in `docs/KNOWN_ISSUES.md` with:

```text
Issue:
Expected:
Observed:
Attempt 1:
Result:
Attempt 2:
Result:
Evidence:
Hypothesis:
Next DIFFERENT strategy:
```

---

# 35. Atomic development cycle

Preferred work loop:

```text
Read state
 -> choose ONE concrete task
 -> research references if needed
 -> implement
 -> build
 -> test
 -> fix
 -> retest
 -> update docs
 -> commit
 -> continue
```

Do not make giant uncontrolled rewrites.

---

# 36. Git discipline

Use meaningful commits, e.g.:

- `feat(openvpn): add ovpn profile importer`
- `feat(wireguard): add qr profile import`
- `feat(android): add VpnService tunnel controller`
- `feat(tv): add remote-focused connection dashboard`
- `feat(i18n): add Persian RTL foundation`
- `fix(dns): prevent leak after tunnel restart`
- `test(routing): add split-tunnel regression coverage`
- `docs(store): update Play compliance matrix`

Avoid meaningless commits like `update`, `fix`, `stuff`.

---

# 37. Documentation rules

Documentation must track code reality.

When a feature changes, update the relevant persistent docs in the same work unit.

Examples:

- protocol implemented -> update protocol matrix
- architecture changed -> update architecture + decisions
- bug fixed -> update known issues + regression test
- dependency changed -> update dependency/license matrix
- tests changed -> update test report
- Store rejection -> record in Store rejection history

Do not mark a feature complete unless code and evidence justify it.

---

# 38. Suggested roadmap

## M0 — Repository Audit
- understand repository
- dependencies
- existing build/tests
- licenses
- establish persistent docs

## M1 — Foundation
- app shell
- architecture
- branding foundation
- internal profile model
- secure storage
- core adapter API
- CI basics

## M2 — Branding & Localization
- PVNetwork supplied logo
- English
- Persian
- RTL
- Light/Dark/System themes

## M3 — Core Networking
- OpenVPN
- WireGuard
- AmneziaWG
- Xray
- Mihomo

## M4 — Extended Networking
- OpenConnect
- IPsec/native/strongSwan
- SoftEther
- Hysteria2 if separate core remains justified

## M5 — Universal Import
- files
- links
- QR
- clipboard
- subscriptions
- auto detection

## M6 — Routing and DNS
- TUN
- split tunnel
- per-app where supported
- rule engine
- DNS modes
- leak prevention

## M7 — Platform Integration
- Android
- Android TV
- Windows
- Linux
- macOS
- iOS/iPadOS

## M8 — Reliability
- reconnect
- kill switch
- network switching
- sleep/resume
- crash recovery
- user data migrations

## M9 — Competitor Hardening
- convert relevant upstream/competitor bugs into PVNetwork mitigations/tests

## M10 — Store Compliance
- Google Play
- Android TV
- Apple
- Microsoft Store

## M11 — Production UI
- final UX polish
- accessibility
- responsive layouts
- no placeholders

## M12 — Release Candidates
- real-device testing
- security review
- performance measurements

## M13 — Store Submission
- signed packages
- store metadata/assets
- policy re-check

## M14 — Production Release
- final verified PVNetwork release

---

# 39. Product acceptance principles

A feature can be described at different evidence levels:

1. code exists
2. compiles
3. unit-tested
4. integration-tested
5. end-to-end-tested
6. real-device-tested
7. Store-verified
8. production-verified

These are not equivalent.

Never fake support.

---

# 40. Persian acceptance gate

Before public release:

- all core UI translated
- no broken RTL
- no clipped Persian text
- mixed Persian/English works
- IPs/URLs remain readable
- dialogs/settings/cards RTL correctly
- notifications verified
- Android TV Persian verified
- Light mode verified
- Dark mode verified

---

# 41. Brand acceptance gate

Before release:

- correct PVNetwork name everywhere
- official supplied logo used
- correct app icons
- correct TV assets
- correct splash
- no competitor logos
- no placeholder branding
- no template names/test icons

---

# 42. Network reliability acceptance scenarios

Test at minimum:

- connect
- disconnect
- repeated reconnect
- switch server
- switch protocol
- server timeout
- invalid credentials
- malformed profile
- Wi-Fi loss
- Wi-Fi change
- Wi-Fi to mobile transition
- sleep
- resume
- core crash
- DNS failure
- IPv4
- IPv6
- packet loss
- high latency
- expired subscription

Application must fail gracefully and clean routes/DNS/tunnel state.

---

# 43. Final product definition

The intended user experience is:

```text
Install PVNetwork
   -> add account/subscription/config
   -> PVNetwork detects the format
   -> select server
   -> Connect
```

The user should no longer need separate clients merely because a service uses OpenVPN, WireGuard, VLESS, VMess, Trojan, Hysteria2, TUIC, Clash/Mihomo formats, Cisco-compatible SSL VPN or another supported family.

---

# 44. Key architectural conclusion from the research

The project scope currently contains **93 protocol/technology entries**, but the preferred implementation strategy is **a small set of high-value modular engines/adapters**, not 93 separate binaries.

The strongest initial core stack identified is approximately:

```text
OpenVPN 3
+ official WireGuard
+ AmneziaWG
+ Xray-core
+ Mihomo
+ OpenConnect
+ strongSwan/native IPsec adapters
+ SoftEther
+ Hysteria2 (only if dedicated engine is justified)
```

This should cover the majority of practical PVNetwork use cases while controlling complexity, binary size, attack surface and licensing risk.

---

# 45. Mandatory future core audit record

For every core integrated into PVNetwork, create and maintain a record containing:

```text
CORE_NAME
UPSTREAM_REPOSITORY
PINNED_VERSION
LICENSE
LICENSE_OBLIGATIONS
SUPPORTED_PROTOCOLS
SUPPORTED_PLATFORMS
STORE_RISK
KNOWN_ISSUES
SECURITY_ADVISORIES
BINARY_SIZE
UPDATE_PROCESS
PVNETWORK_UNIT_TESTS
PVNETWORK_INTEGRATION_TESTS
PVNETWORK_E2E_TESTS
LAST_VERIFIED_DATE
```

No dependency should be updated blindly.

---

# 46. Final permanent engineering principle

> **Code is implementation.**  
> **Tests are evidence.**  
> **Git is history.**  
> **GitHub documentation is memory.**  
> **Store policy is a live constraint.**  
> **PROJECT_STATE.md is the handoff point.**

If context is lost: **read the repository**.

If another AI worked before: **continue validated work rather than restarting**.

If something fails: **investigate; do not loop**.

If another project already encountered the same bug: **learn from it and add a preventive test**.

If a decision is made: **document it**.

If a feature is implemented: **test it**.

If a feature is advertised: **prove it**.

If a release is produced: **re-check Store and licensing compliance**.

The project is complete only when PVNetwork is a **polished, secure, modern, multilingual, fully branded, real multi-platform Universal VPN / Proxy Super Client** ready for real users and legitimate public distribution.
