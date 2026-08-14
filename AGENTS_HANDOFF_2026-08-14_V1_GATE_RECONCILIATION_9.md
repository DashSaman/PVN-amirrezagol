# PVNetwork Agent Handoff — V1 Gate Reconciliation 9

Date: 2026-08-14

## Campaign state

- Active phase: `COMPLETE-RESEARCH-v1`
- Completed: **28 / 93**
- V2 remains hard-locked until 93/93.

## Newly completed entry

### 028 — Sophos SSL VPN

State: `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`

Evidence:

- `research/protocols/028-sophos-ssl-vpn/V1_RESEARCH.md`
- `research/protocols/028-sophos-ssl-vpn/SOPHOS_SSL_VPN_CURRENT_AUDIT.md`
- `research/protocols/028-sophos-ssl-vpn/V1_GATE_RECONCILIATION.md`
- shared OpenVPN family dossier under `research/upstreams/openvpn-family/`

Decision:

`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST WHEN PROFILE+AUTH CAPABILITIES MATCH / SOPHOS PROVISIONING+SSO SEPARATE`

Key current evidence:

- Sophos Connect Windows 2.5 MR1 (2026-06-18);
- Sophos Connect macOS 2.0 MR1 (2026-05-21);
- current Sophos SSL VPN uses `.ovpn` and supports OpenVPN-family clients;
- Sophos Connect SSL VPN exposes an OpenVPN service/logging domain separate from its strongSwan/IPsec domain;
- `.pro` is a Sophos provisioning/update artifact, not generic OpenVPN configuration;
- current Entra SSO/MFA/platform/profile semantics remain exact-version capabilities.

## Exact next entry

**029 — Sophos IPsec Remote Access**

Required sequence:

1. Read entry 029 dossier.
2. Establish current Sophos Firewall remote-access IPsec direction in current SFOS 21.5/22.0 and distinguish **modern remote access IPsec** from the legacy IPsec mode retired in SFOS 22.0 MR1.
3. Use current Sophos Connect 2.5 MR1 Windows / 2.0 MR1 macOS behavior and exact configuration formats (`.scx`, `.pro`, legacy `.tgb` where still relevant only).
4. Reuse current strongSwan 6.0.7 family evidence for standards IKE/IPsec only where exact semantics apply; Sophos provisioning/group-auth/SSO/policy remains separate.
5. Map authentication/MFA/Entra SSO, cert/PSK, proposals, routes/DNS, UI/config/storage, platform install/update, diagnostics (`charon.log`), issues/releases/security and legacy migration.
6. Treat retired legacy IPsec as migration/history, not a new-deployment target.
7. Reconcile all 20 original v1 gates; promote if evidence-backed/N-A/bounded.
8. Continue 030 WatchGuard IKEv2 automatically.

Fetch latest tracker/Run State before writes and preserve concurrent progress.