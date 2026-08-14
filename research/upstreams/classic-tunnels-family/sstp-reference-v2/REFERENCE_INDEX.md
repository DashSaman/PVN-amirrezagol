# SSTP / MS-SSTP — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **011 — SSTP / MS-SSTP**

State: `IN-RESEARCH / WINDOWS-NATIVE + INTEROP TARGET / NOT IMPLEMENTED`

Original v1 evidence:

`research/protocols/011-sstp-ms-sstp/V1_RESEARCH.md`

## Protocol identity

SSTP is Microsoft's Secure Socket Tunneling Protocol. The authoritative wire/control specification is the current Microsoft Open Specifications document **[MS-SSTP] Secure Socket Tunneling Protocol (SSTP)**.

Core layering:

`PPP / user authentication and network configuration`

`-> SSTP control/data framing`

`-> HTTPS/TLS`

`-> TCP`

`-> IP`

Default/publicly expected server transport is HTTPS/TLS on TCP **443**.

SSTP must not be reduced to “PPP over port 443.” TLS server authentication, SSTP control negotiation, PPP authentication and the SSTP crypto-binding/security mechanisms are separate layers.

## Current platform authority

### Microsoft Windows client/server

Current Microsoft documentation continues to expose SSTP as a built-in Windows VPN tunnel type and a supported RRAS protocol. Windows Server 2025 documentation deprecates/disables new PPTP/L2TP acceptance by default, while SSTP remains one of the modern RRAS remote-access options alongside IKEv2.

Use current Microsoft Learn/Open Specifications as the source of truth for:

- [MS-SSTP] wire behavior;
- Windows VPN client/profile provisioning;
- RRAS server installation/configuration;
- certificate and TLS requirements;
- PowerShell/VpnClient/RRAS administration.

### SoftEther VPN Server

Existing PVNetwork classic-tunnels research pins SoftEther source at:

`SoftEtherVPN/SoftEtherVPN@49eb2f08641709d1af57a0d04971973ff94461db`

SoftEther is an important open-source SSTP-compatible server/client interoperability reference and multiprotocol management product. Exact current-release SSTP behavior should be revalidated before certification; reuse of the existing source pin is source/reference evidence, not runtime proof.

### sstp-client

Canonical project reviewed:

`sstp-client/sstp-client`

Role: serious Linux SSTP client implementation integrating with PPP/pppd and TLS/HTTP transport.

The repository/activity/license is part of this v2 review. If the exact immutable upstream SHA is not materialized in the existing v1/current connector evidence, preserve that as `EXACT-PIN-RESIDUAL` instead of inventing a SHA; final source-freeze must pin the selected release/commit before implementation/certification.

## Mandatory v2 files

| File | State |
|---|---|
| `SERVER_IMPLEMENTATIONS.md` | started |
| `SERVER_INSTALLERS_AND_PROJECTS.md` | pending |
| `SERVER_INSTALL_MATRIX.md` | pending |
| `SERVER_UI_AND_MENUS.md` | pending |
| `CLIENT_INSTALL_MATRIX.md` | pending |
| `CLIENT_UI_AND_MENUS.md` | pending |
| `CRYPTOGRAPHY.md` | started |
| `DATA_PATH_AND_WIRE_FLOW.md` | started |
| `PORTS_TRANSPORTS_AND_HANDSHAKE.md` | started |
| `DEPLOYMENT_TOPOLOGIES.md` | pending |
| `REFERENCE_INDEX.md` | active |

## Non-negotiable rules

1. TLS transport security, SSTP framing/control and PPP authentication are separate layers.
2. TCP 443 is a transport convention/default; SSTP is not ordinary web browsing merely because it uses HTTPS/TLS.
3. Server certificate validation/SNI/name/trust policy must not be weakened to “make SSTP connect.”
4. PPP username/password/EAP/certificate auth is separate from TLS server authentication.
5. SSTP crypto binding/channel binding security must be treated according to current MS-SSTP/Windows behavior, not ignored.
6. Proxy/firewall reachability on TCP443 does not prove SSTP is usable; HTTP method/long-lived duplex/tunnel behavior can be blocked.
7. Windows native client/server support does not imply Linux/SoftEther interoperability for every auth/cipher/certificate/profile combination.
8. SoftEther's multiprotocol UI/license/architecture is separate from Windows RRAS and sstp-client.
9. New security policy follows current TLS/platform guidance; obsolete TLS versions/ciphers are not enabled by default for legacy peers.
10. Secrets/private keys belong in platform secure stores/provider references; logs/exports redact them.

## Exact next action

Complete all 11 mandatory v2 files, map Microsoft RRAS/native client and open-source interop implementations separately, reconcile all 16 v2 gates, preserve exact-pin/runtime/Windows/interoperability blockers, checkpoint and continue without owner prompting.
