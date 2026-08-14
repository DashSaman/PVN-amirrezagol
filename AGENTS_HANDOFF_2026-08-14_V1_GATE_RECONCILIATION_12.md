# PVNetwork Agent Handoff — V1 Gate Reconciliation 12

Date: 2026-08-14

## Campaign state

- Active phase: `COMPLETE-RESEARCH-v1`
- Completed: **31 / 93**
- V2 remains hard-locked until 93/93.

## Newly completed entry

### 031 — WatchGuard Mobile VPN with SSL

State: `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`

Evidence:

- `research/protocols/031-watchguard-ssl-vpn/V1_RESEARCH.md`
- `research/protocols/031-watchguard-ssl-vpn/WATCHGUARD_SSL_CURRENT_AUDIT.md`
- `research/protocols/031-watchguard-ssl-vpn/V1_GATE_RECONCILIATION.md`
- shared OpenVPN family evidence.

Decision:

`OPENVPN-COMPATIBILITY TARGET / OPENVPN3-FIRST FOR STANDARD PROFILE+WIRE SEMANTICS / WATCHGUARD FIRST-PARTY SAML+VENDOR FEATURES SEPARATE`

Critical current boundary:

- WatchGuard officially generates `client.ovpn` and supports OpenVPN clients for Mobile VPN with SSL;
- current WatchGuard SAML integration is supported on specific first-party Windows/macOS SSL client versions and explicitly not on third-party OpenVPN clients;
- AuthPoint/RADIUS/MFA, `.wgssl`, routed/bridged tunnel policy and first-party lifecycle remain separate capabilities.

## Exact next entry

**032 — WatchGuard L2TP VPN**

Required sequence:

1. Read entry 032 dossier; resolve actual numbered folder path if generic.
2. Establish current Fireware Mobile VPN with L2TP layering, current security/tunnel authentication (PSK/certificate/IPsec), user authentication, ports and native client guidance.
3. Keep 032 separate from generic L2TP/IPsec entry 008 while reusing its standards evidence where exact semantics match.
4. Determine current Windows/macOS/iOS/Android/native-client support and manual configuration flow.
5. Map Firebox server UI, tunnel auth, RADIUS/local auth, MFA capability, address pool, routing behavior, DNS, client UI/settings, config/storage/secrets, diagnostics, profile deployment/lifecycle, assets, releases/security and tests/CI.
6. Audit any WatchGuard-specific configuration artifacts; do not invent a proprietary app if native clients are the actual path.
7. Reconcile all 20 v1 gates and promote only if evidence-backed/N-A/bounded.
8. Continue 033 Aruba VIA automatically.

Fetch latest tracker/Run State before state writes and preserve concurrent progress.