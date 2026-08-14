# AGENTS Handoff — 2026-08-14 — SSTP/MS-SSTP v2 slice 1

Work unit: `SSTP-MS-SSTP-COMPLETE-REFERENCE-V2`

Entry: 011 SSTP / MS-SSTP

## State transition

Entry 011 now has:

`REFERENCE-V2-EVIDENCE-COMPLETE / EXACT-LINUX-CLIENT-PIN-RESIDUAL / WINDOWS+INTEROP-EXECUTION-BLOCKED / NOT IMPLEMENTED`

Strict `COMPLETE-REFERENCE-v2` remains PENDING.

## Dossier

Folder:

`research/upstreams/classic-tunnels-family/sstp-reference-v2/`

All 11 mandatory files exist:

- `SERVER_IMPLEMENTATIONS.md`
- `SERVER_INSTALLERS_AND_PROJECTS.md`
- `SERVER_INSTALL_MATRIX.md`
- `SERVER_UI_AND_MENUS.md`
- `CLIENT_INSTALL_MATRIX.md`
- `CLIENT_UI_AND_MENUS.md`
- `CRYPTOGRAPHY.md`
- `DATA_PATH_AND_WIRE_FLOW.md`
- `PORTS_TRANSPORTS_AND_HANDSHAKE.md`
- `DEPLOYMENT_TOPOLOGIES.md`
- `REFERENCE_INDEX.md`

Formal gate reconciliation:

`ENTRY_011_V2_GATE_RECONCILIATION.md`

## Key authority/implementation references

- Microsoft current [MS-SSTP] Open Specifications and current Microsoft Learn Windows VpnClient/RRAS guidance are protocol/platform authority.
- Windows native SSTP client + Windows Server RRAS are primary native client/server targets.
- SoftEther source reference reused at `SoftEtherVPN/SoftEtherVPN@49eb2f08641709d1af57a0d04971973ff94461db`.
- Canonical Linux client project: `sstp-client/sstp-client`.

## Exact source-freeze residual

The repository evidence currently does **not** contain a trustworthy immutable selected `sstp-client` release/commit SHA + license pin.

Do not invent one.

A future agent/source-freeze pass should materialize the exact maintained release/commit and root/component license, then remove only the `EXACT-LINUX-CLIENT-PIN-RESIDUAL` state. The rest of the SSTP v2 reference should not be redone.

## Key protocol/security rules

1. TLS != SSTP control/framing != PPP/EAP authentication.
2. SSTP normally uses HTTPS/TLS over TCP443, but port reachability does not prove SSTP compatibility.
3. Certificate chain/name/validity/revocation validation is mandatory; no silent insecure fallback.
4. SSTP crypto/channel binding is a protocol-security requirement handled by current MS-SSTP/backend behavior.
5. PPP/EAP user credentials are separate from TLS server identity/private keys.
6. HTTP proxy CONNECT/auth is separate; TLS interception changes the channel trust model.
7. Generic L7 TLS termination/reverse proxy is not assumed compatible/safe.
8. Windows RRAS != SoftEther; exact-version interoperability required.
9. Windows native support does not prove Linux/mobile/mac support.
10. Obsolete TLS/cipher/auth policy is not enabled automatically.

## Strict external blockers

- exact immutable `sstp-client` pin/license;
- Windows Server 2025 RRAS install/config/certificate/listener lifecycle;
- Windows 11 native -> RRAS;
- Windows -> SoftEther;
- Linux sstp-client -> RRAS/SoftEther;
- certificate invalid/name/revoked/rotation tests;
- crypto-binding success/negative;
- selected PPP/EAP auth profiles;
- HTTP proxy/TLS interception;
- L4 load balancer idle/failover;
- IPv6;
- TCP-over-TCP lossy performance;
- MTU/MSS;
- split/full routes/DNS;
- lifecycle cleanup;
- any macOS/iOS/Android engine (none selected/certified).

## Do not repeat

- do not call TCP443 ordinary HTTPS proof;
- do not treat PPP password as tunnel encryption;
- do not disable TLS certificate validation to gain compatibility;
- do not infer reverse-proxy/TLS-offload safety;
- do not fabricate Linux-client pin;
- do not promote reference closure to implementation/certification.

## Next work unit

Activate entry 012:

`PPTP-COMPLETE-REFERENCE-V2`

PPTP must be treated as an obsolete/legacy compatibility protocol, with TCP1723 control, GRE data path, PPP authentication/encryption layers, current platform deprecation/removal status, serious server/client implementation inventory, UI/install matrices and explicit migration/security guidance.

Exact next sequence:

1. read entry-012 v1 and existing classic-tunnels/SoftEther/Linux/Windows evidence;
2. establish current protocol standards/security/deprecation authority;
3. map TCP1723 control vs GRE protocol 47 data path and NAT/PPTP ALG behavior;
4. separate PPP auth and MPPE/security from PPTP transport;
5. map Windows/SoftEther/Linux implementations and current OS availability/removal status;
6. create all 11 v2 files;
7. reconcile all 16 gates;
8. preserve runtime/security/interoperability blockers;
9. checkpoint and continue without owner prompting.
