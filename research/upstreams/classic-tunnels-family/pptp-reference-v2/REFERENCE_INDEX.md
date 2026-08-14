# PPTP — COMPLETE-REFERENCE-v2 Index

Review date: 2026-08-14

Entry: **012 — PPTP**

State: `IN-RESEARCH / OBSOLETE-SECURITY-COMPATIBILITY ONLY / NOT IMPLEMENTED`

Original evidence:

`research/protocols/012-pptp/V1_RESEARCH.md`

## Protocol identity

PPTP is the Point-to-Point Tunneling Protocol standardized historically by RFC 2637.

Core layering:

`PPP payload / user authentication / optional MPPE`

`-> PPTP GRE data channel`

plus a separate

`PPTP TCP control connection on TCP 1723`

The data path uses GRE **IP protocol 47** with PPTP-specific call IDs/sequence/ack fields. It is not TCP/UDP port 47.

## Current security/product classification

`OBSOLETE / LEGACY INTEROPERABILITY ONLY`

PPTP must not be recommended for new deployments. Its common security profile depends on legacy PPP authentication and MPPE mechanisms, historically including MS-CHAPv2-derived keys. Even when MPPE encrypts PPP payloads, the overall authentication/key-derivation design is not an acceptable modern default compared with IKEv2, WireGuard, OpenVPN or other approved modern protocols.

Product rule:

- no silent fallback to PPTP;
- no “secure because encrypted” marketing claim;
- hide it from normal recommended protocol selection;
- expose only under explicit legacy/compatibility policy if a business requirement remains.

## Standards/security references

- RFC 2637 — PPTP protocol/control/GRE framing;
- RFC 1661 — PPP;
- RFC 2759 — MS-CHAPv2 where used;
- RFC 3078 — Microsoft Point-to-Point Encryption (MPPE);
- RFC 3079 — MPPE key derivation from MS-CHAP/MS-CHAPv2 where applicable.

These RFCs document the legacy mechanism; they are not a recommendation to deploy it today.

## Current platform direction

### Windows

Current Windows client tooling still documents `Pptp` as a native VPN tunnel type. Windows Server RRAS documentation still discusses PPTP, but new Windows Server 2025 RRAS setups do not accept PPTP/L2TP by default; enabling them is an explicit legacy action.

### Apple

Apple removed native PPTP support starting with iOS 10 and macOS Sierra. Do not claim native current Apple PPTP support.

### Android

Android documentation treats built-in PPTP/L2TP-IPsec as legacy VPN. Actual current device/OEM profile availability requires exact runtime evidence and must not be assumed universal.

### MikroTik RouterOS

Current MikroTik documentation continues to expose PPTP client/server functionality but warns that PPTP has known security issues and is not recommended for secure use. Treat RouterOS as a major proprietary interoperability target, not a recommendation for new deployment.

## Implementation categories

### Windows native/RRAS

Primary proprietary historical client/server interoperability target.

### MikroTik RouterOS

Current proprietary server/client/router interop target with explicit insecure/legacy warning.

### Linux Poptop/pptpd and pptp client projects

Historically important open-source implementations. Before any source freeze, exact maintained/archived canonical repository, immutable commit/release and license must be materialized. Do not pretend old packages are actively maintained if they are not.

### PPP/pppd

Major lower-layer PPP implementation; exact component/plugin licenses differ and must be reviewed separately.

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

1. TCP1723 is PPTP control only; user data uses GRE IP protocol 47.
2. GRE protocol 47 is not “port 47”.
3. PPTP transport, PPP authentication and MPPE encryption are separate layers.
4. MPPE presence does not make PPTP a modern secure VPN.
5. No silent downgrade/fallback to PPTP.
6. NAT/firewall/PPTP-ALG behavior is part of interoperability because GRE call state cannot be treated like an ordinary TCP/UDP 5-tuple.
7. Apple native PPTP is removed; Android availability is legacy/device-specific; Windows/RouterOS support is explicit compatibility evidence only.
8. Weak authentication/encryption modes must not be enabled by default to gain legacy compatibility.
9. Secrets/passwords/key material are redacted and stored through platform/secure references.
10. Migration guidance to a modern protocol is part of every supported legacy PPTP deployment.

## Exact next action

Complete all 11 mandatory v2 files, explicitly document obsolete security status and current OS removal/deprecation, reconcile all 16 v2 gates, preserve exact old-source/runtime/interoperability blockers, checkpoint and continue without owner prompting.
