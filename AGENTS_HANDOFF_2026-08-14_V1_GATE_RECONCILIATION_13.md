# PVNetwork Agent Handoff — V1 Gate Reconciliation 13

Date: 2026-08-14

## Campaign state

- Active phase: `COMPLETE-RESEARCH-v1`
- Completed: **32 / 93**
- V2 remains hard-locked until 93/93.

## Newly completed entry

### 032 — WatchGuard Mobile VPN with L2TP

State: `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`

Evidence:

- `research/protocols/032-watchguard-l2tp/V1_RESEARCH.md`
- `research/protocols/032-watchguard-l2tp/WATCHGUARD_L2TP_CURRENT_AUDIT.md`
- `research/protocols/032-watchguard-l2tp/V1_GATE_RECONCILIATION.md`
- generic L2TP/IPsec entry 008 and shared IPsec evidence.

Decision:

`STANDARD L2TP/IPSEC INTEROPERABILITY TARGET / NATIVE-OS-FIRST WHERE CURRENT SUPPORT EXISTS / REUSE ENTRY-008 LAYERED STACK / WATCHGUARD AUTH+POLICY CERTIFICATION REQUIRED / MODERN-ANDROID-NATIVE-UNAVAILABLE`

Key current findings:

- secure default is L2TPv2 over IPsec;
- tunnel auth is PSK or certificate;
- user auth is separate PPP/MS-CHAPv2 with Firebox-DB/RADIUS/AuthPoint capability;
- UDP 500/4500 + ESP and UDP 1701 are separate transport layers;
- WatchGuard allows raw L2TP without IPsec but explicitly does not recommend it;
- Windows/macOS/iOS native clients are documented;
- built-in Android L2TP is unavailable on Android 12+;
- WatchGuard does not support manual split-tunnel L2TP configuration as a first-class path and recommends IKEv2/SSL when split tunnel is needed.

## Exact next entry

**033 — Aruba VIA**

Required sequence:

1. Resolve/use the real numbered folder `033-aruba-via`; do not use the placeholder `033-entry`.
2. Establish current HPE Aruba Networking VIA client/gateway product line, exact current platforms/releases and current gateway/controller architecture.
3. Determine exact tunnel protocols/security under VIA (IPsec/IKEv2/IKEv1/SSL/TLS as current product supports), and separate standards engine semantics from Aruba provisioning/policy/posture/client orchestration.
4. Identify serious public/open-source implementation candidates, if any. Do not infer generic IPsec equals VIA compatibility.
5. Audit first-party Windows/macOS/iOS/Android/Linux client behavior if current documentation supports them, plus profile provisioning, authentication/MFA/cert, UI/menu, storage/secrets, routes/DNS/split/full tunnel, diagnostics/logs, install/update lifecycle, assets, issues/releases/security and tests/CI.
6. Reuse strongSwan/native IPsec evidence only for exact standards layers.
7. Reconcile all 20 v1 gates, promote only with evidence-backed/N-A/bounded treatment.
8. Continue 034 Citrix Secure Access automatically.

Fetch latest tracker/Run State before state writes and never move concurrent work backward.