# PVNetwork Agent Handoff — V1 Gate Reconciliation 10

Date: 2026-08-14

## Campaign state

- Active phase: `COMPLETE-RESEARCH-v1`
- Completed: **29 / 93**
- V2 remains hard-locked until 93/93.

## Newly completed entry

### 029 — Sophos IPsec Remote Access

State: `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`

Evidence:

- `research/protocols/029-sophos-ipsec-remote-access/V1_RESEARCH.md`
- `research/protocols/029-sophos-ipsec-remote-access/SOPHOS_IPSEC_CURRENT_AUDIT.md`
- `research/protocols/029-sophos-ipsec-remote-access/V1_GATE_RECONCILIATION.md`
- shared strongSwan evidence under `research/upstreams/strongswan-family/`

Decision:

`CURRENT SOPHOS IPSEC COMPATIBILITY TARGET / STRONGSWAN-FIRST FOR STANDARD IKEV1-IPSEC SEMANTICS / SCX-PRO-POLICY-SSO SEPARATE / RETIRED LEGACY MODE MIGRATION-ONLY`

Critical distinction:

- current SFOS 22.0 remote-access IPsec still requires IKEv1 profiles for the modern Sophos Connect remote-access mode;
- the separately named `IPsec (legacy)` feature is retired in SFOS 22.0 MR1+ and must be removed before upgrade;
- therefore `modern current mode uses IKEv1` and `legacy mode retired` are not contradictory and must not be collapsed.

Current profile/provisioning artifacts:

- `.scx` — current Sophos Connect config with general + advanced settings;
- `.tgb` — reduced third-party-client config;
- `.pro` — Sophos provisioning/update artifact.

Sophos Connect diagnostics identify `charon.log` as strongSwan/IKE/ESP/IPsec, but PVNetwork uses its independently pinned strongSwan 6.0.7 baseline and product-owned adapter rather than copying Sophos binaries.

## Exact next entry

**030 — WatchGuard IKEv2 VPN**

Required sequence:

1. Read entry 030 dossier and shared strongSwan/native IPsec evidence.
2. Establish current WatchGuard Fireware Mobile VPN with IKEv2 server/gateway model, supported Fireware versions and current native-client platform guidance.
3. Distinguish WatchGuard IKEv2 from WatchGuard Mobile VPN with SSL (031) and L2TP (032).
4. Determine exact standards IKEv2/EAP/certificate semantics that can use native OS or strongSwan, and any WatchGuard-specific profile/provisioning/certificate/policy behavior.
5. Audit current client profile downloads/imports for Windows/macOS/iOS/Android/Linux where officially documented; do not infer a custom WatchGuard client if native clients are the current path.
6. Map server admin UI, client UI/native settings, config/profile formats, cert/credential storage, MFA/auth backends, routes/DNS, logs/diagnostics, installers/profile deployment, assets, issues/releases/security, tests/CI and Store/privacy implications.
7. Reconcile all 20 original v1 gates; promote only if evidence-backed/N-A/bounded.
8. Continue 031 automatically.

Fetch latest tracker/Run State before every state write and never overwrite concurrent progress backward.