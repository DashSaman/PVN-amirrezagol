# PVNetwork Agent Handoff — V1 Gate Reconciliation 11

Date: 2026-08-14

## Campaign state

- Active phase: `COMPLETE-RESEARCH-v1`
- Completed: **30 / 93**
- V2 remains hard-locked until 93/93.

## Newly completed entry

### 030 — WatchGuard Mobile VPN with IKEv2

State: `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`

Evidence:

- `research/protocols/030-watchguard-ikev2/V1_RESEARCH.md`
- `research/protocols/030-watchguard-ikev2/WATCHGUARD_IKEV2_CURRENT_AUDIT.md`
- `research/protocols/030-watchguard-ikev2/V1_GATE_RECONCILIATION.md`
- shared IKEv2/strongSwan evidence.

Decision:

`STANDARD IKEV2 INTEROPERABILITY TARGET / NATIVE-OS-FIRST / STRONGSWAN FOR ANDROID+ADVANCED PORTABILITY / WATCHGUARD PROFILE+AUTH CERTIFICATION REQUIRED`

Current platform model:

- Windows native IKEv2;
- macOS native IKEv2;
- iOS native IKEv2;
- Android strongSwan;
- optional WatchGuard IPSec Mobile VPN Client Windows v15.19+ with Fireware 12.11.1+ generated IKEv2 profile.

Auth/security model:

- server/tunnel identity with certificate;
- EAP/MS-CHAPv2 user authentication;
- Firebox-DB/RADIUS/AuthPoint integration;
- MFA capability is version/client dependent;
- certificate SAN/EKU/expiry and client RSA/EC capabilities are exact compatibility gates.

## Exact next entry

**031 — WatchGuard SSL VPN**

Required sequence:

1. Read entry 031 dossier.
2. Establish current WatchGuard `Mobile VPN with SSL` server/client architecture, current Fireware support and current WatchGuard Mobile VPN with SSL client versions/platforms.
3. Determine exact relationship to OpenVPN/open-source components; do not assume `.ovpn` compatibility unless WatchGuard officially/publicly proves it.
4. Audit any serious public interoperability implementations and source/license pins.
5. Map Firebox server UI, client UI, profile/config download/install, authentication/RADIUS/AuthPoint/MFA, certificate/TLS, virtual adapter, routes/DNS/full-vs-split, logs/diagnostics, installer/update/uninstall, assets, releases/security, tests/CI and privacy/platform implications.
6. Keep entry 031 separate from IKEv2 030 and L2TP 032.
7. Reconcile all 20 v1 gates; promote only if evidence-backed/N-A/bounded.
8. Continue 032 automatically.

Fetch latest tracker/Run State before each state write.