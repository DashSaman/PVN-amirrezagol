# SSTP / MS-SSTP — Server Implementations

Review date: 2026-08-14

Entry: 011 SSTP / MS-SSTP.

## 1. Windows Server RRAS — authoritative native server

Microsoft Routing and Remote Access Service (RRAS) is the primary platform-native SSTP server reference.

Current Microsoft documentation continues to support SSTP for remote-access VPN on Windows Server and documents the Remote Access/VPN role, RRAS configuration, certificate requirements, ports and native Windows client interoperability.

### Role separation

RRAS owns:

- SSTP HTTPS/TLS listener and protocol state;
- PPP session/authentication integration;
- address assignment and routing;
- NPS/RADIUS/local/AD-related authentication policy depending deployment;
- Windows Firewall/networking integration;
- server certificate/private-key binding through Windows certificate infrastructure.

Windows HTTP/TLS/kernel components may participate underneath RRAS; PVNetwork should manage supported RRAS/Windows APIs/configuration rather than private implementation details.

### PVNetwork status

`PRIMARY PROPRIETARY SERVER / INTEROP + NATIVE WINDOWS MANAGEMENT TARGET`

No source reuse; exact Windows Server edition/build/patch level is part of certification.

## 2. SoftEther VPN Server — open-source multiprotocol reference

Existing source pin reused from PVNetwork classic-tunnels research:

- repository: `SoftEtherVPN/SoftEtherVPN`
- reviewed source commit: `49eb2f08641709d1af57a0d04971973ff94461db`

SoftEther source contains SSTP protocol/server functionality and exposes SSTP as one of several compatibility protocols through the SoftEther server architecture and management surface.

### Important boundary

SoftEther's SSTP-compatible implementation is not Windows RRAS. Differences can exist in:

- TLS stack/certificate handling;
- PPP/authentication integration;
- SSTP control extensions/crypto binding;
- listener/port sharing;
- user database/Virtual Hub/RADIUS integration;
- routing/NAT/bridge behavior.

Every client/server pair requires exact-version interoperability tests.

### Role

`HIGH-VALUE OPEN-SOURCE SERVER / UI / INTEROP REFERENCE`

Use exact SoftEther license/source obligations from the existing family dossier; do not infer application/UI reuse rights from protocol compatibility.

## 3. Linux sstp-client is not a server

Canonical project reviewed:

`sstp-client/sstp-client`

It is a Linux SSTP client implementation, not a general SSTP server. Keep it in client matrices; do not list it as server merely because it contains protocol framing code.

## 4. Other community SSTP servers

Community projects/scripts sometimes expose SSTP services, often by combining a protocol daemon, PPP and TLS libraries. None are promoted without:

- canonical source;
- immutable release/commit;
- license;
- maintenance/activity evidence;
- TLS library/version;
- PPP/auth backend;
- certificate/private-key handling;
- HTTP/TLS parser exposure;
- fuzz/security/test evidence;
- upgrade/uninstall lifecycle.

Do not recommend an abandoned small SSTP daemon over current RRAS/SoftEther without a clear reason and security review.

## 5. Server architecture categories

### Native Windows RRAS

`Windows TLS/HTTP transport`

`-> RRAS SSTP control/data`

`-> PPP/authentication`

`-> Windows routing/addressing/firewall`

### SoftEther

`SoftEther listener/TLS`

`-> SSTP compatibility protocol`

`-> SoftEther session/user/Virtual Hub architecture`

`-> SecureNAT/bridge/routing as configured`

### Unix community composition

Potential architecture:

`TLS/HTTP SSTP daemon`

`-> PPP/pppd`

`-> RADIUS/local auth`

`-> Linux routes/firewall`

This category requires exact project-level evidence and is not automatically approved.

## 6. Authentication backends

SSTP transports PPP, so the user/authentication layer may use PPP authentication methods or EAP according to platform/server policy. RRAS can integrate with Windows/NPS/RADIUS; open-source implementations may use pppd/RADIUS/local accounts.

Do not merge:

- TLS server certificate authentication/trust;
- SSTP channel/crypto-binding validation;
- PPP/EAP user authentication.

They are different security layers.

## 7. Addressing/routing

SSTP server support also requires:

- client IP address allocation;
- IPv4/IPv6 behavior according to implementation;
- DNS delivery;
- routes/split/full tunnel policy;
- forwarding/NAT/firewall;
- per-user/access policies where applicable.

Protocol success does not prove application reachability.

## 8. Selection direction

### Windows-centric enterprise compatibility

Prefer native Windows RRAS server when SSTP is genuinely required and Windows operational ownership is acceptable.

### Open-source/multiprotocol compatibility server

SoftEther is a serious reference/candidate, but enable only required protocols/listeners and certify exact client/profile combinations.

### New deployment protocol choice

SSTP remains useful where TCP443/TLS traversal and Windows compatibility are requirements, but compare against IKEv2, WireGuard/OpenVPN and product constraints rather than selecting SSTP automatically.

## 9. Remaining implementation evidence

- exact current Windows Server release/build test matrix;
- current SoftEther release pin/activity refresh beyond reused source commit;
- exact immutable sstp-client client pin/license;
- current independent maintained Unix SSTP server, if one is to be supported;
- RRAS/SoftEther channel-binding and TLS interoperability tests;
- IPv6 and proxy behavior;
- performance/resource measurements.
