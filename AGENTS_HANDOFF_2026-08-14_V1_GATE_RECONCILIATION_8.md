# PVNetwork Agent Handoff — V1 Gate Reconciliation 8

Date: 2026-08-14

## Campaign state

- Active phase: `COMPLETE-RESEARCH-v1`
- Completed: **27 / 93**
- V2 remains hard-locked until 93/93.

## Newly completed entry

### 027 — SonicWall Global VPN / IPsec

State: `COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`

Evidence:

- `research/protocols/027-sonicwall-global-vpn-ipsec/V1_RESEARCH.md`
- `research/protocols/027-sonicwall-global-vpn-ipsec/GVC_IPSEC_CURRENT_AUDIT.md`
- `research/protocols/027-sonicwall-global-vpn-ipsec/V1_GATE_RECONCILIATION.md`
- shared strongSwan evidence under `research/upstreams/strongswan-family/`

Decision:

`VENDOR IPSEC INTEROPERABILITY TARGET / REUSE APPROVED IKE-IPSEC BACKEND WHERE STANDARD SEMANTICS MATCH / SONICWALL GROUPVPN CERTIFICATION REQUIRED`

Key findings:

- current SonicWall product evidence continues to expose GVC as a Windows traditional IPsec client;
- SonicOS GroupVPN owns policy provisioning, XAUTH, client credential-caching policy, virtual adapter/DHCP-over-VPN, split/full route behavior and VPN Access authorization;
- RCF export/import is a SonicWall provisioning artifact;
- IKE Phase 1/2 success is not final connection success; adapter address acquisition/routes/authorization/data path are separate states;
- strongSwan 6.0.7 exact commit `5973ff8e41deef4e015e1138a2de688acedf6f75` is the current public standards-engine baseline;
- exact latest downloadable GVC package/hash/signature remains a later MySonicWall package-freeze requirement and was not guessed.

## Exact next entry

**028 — Sophos SSL VPN**

Required sequence:

1. Read entry 028 dossier.
2. Establish current Sophos Firewall remote-access SSL VPN product/client direction, exact current official client families, platform support and current lifecycle/deprecation/migration guidance.
3. Determine whether the current SSL VPN is OpenVPN-compatible/standards-derived and exactly which profile/config semantics are standard vs Sophos-specific.
4. Identify current official Sophos Connect behavior and any legacy standalone SSL VPN client boundaries.
5. Reuse the existing OpenVPN/OpenVPN3 family source evidence only where exact semantics match; do not infer full Sophos compatibility from generic OpenVPN support.
6. Search serious public implementations/integrations and exact source/license pins where relevant.
7. Map server admin menus, client UI, profile import/export, credentials/cert/OTP/MFA, platform storage, logs/diagnostics, installer/update lifecycle, assets, issues/releases/security, tests/CI and privacy/Store implications.
8. Reconcile all 20 original v1 gates and promote only with evidence-backed/N-A/bounded treatment.
9. If complete, update tracker/Run State and continue 029 Sophos IPsec Remote Access automatically.

Always fetch latest tracker/Run State before write and never move concurrent progress backward.