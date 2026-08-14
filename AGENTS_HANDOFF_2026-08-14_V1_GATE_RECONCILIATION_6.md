# PVNetwork Agent Handoff — V1 Gate Reconciliation 6

Date: 2026-08-14

## Campaign state

- Hard phase lock: `COMPLETE-RESEARCH-v1`
- V1 tracker after this checkpoint: **25 / 93 COMPLETE-RESEARCH-v1**
- V2 remains locked until 93/93.
- No implementation/vendor interoperability/Store/production claim is implied by v1 completion.

## Newly completed entry

### 025 — Check Point VPN

State:

`COMPLETE-RESEARCH-v1 / NOT IMPLEMENTED`

Primary files:

- `research/protocols/025-check-point-vpn/V1_RESEARCH.md`
- `research/protocols/025-check-point-vpn/V1_GATE_RECONCILIATION.md`
- `research/protocols/025-check-point-vpn/SNX_RS_SOURCE_AUDIT.md`
- shared vendor decision: `research/upstreams/vendor-enterprise-family/SUPPORT_REUSE_DECISIONS.md`

Current public implementation reference:

- repository: `ancwrd1/snx-rs`
- release: `v6.2.4`
- exact commit: `a263c47cecdbbc019bc77c482bb77525a02e20a1`
- annotated tag object: `875a2b1237784b8add62ea808e2e057ec06afde4`
- tag signature: verified by GitHub
- license: AGPL-3.0
- full recursive source tree captured with `truncated=false`.

Important decisions:

- snx-rs is a valuable current source/interoperability reference, but direct embedding into a closed PVNetwork client is not approved without an intentional AGPL-compatible legal/product model.
- official Check Point clients/appliances/docs remain the proprietary behavior/certification authority.
- Check Point code/branding/assets are reference-only.
- standards/native IPsec should be reused where exact gateway policy proves it compatible; do not invent a proprietary cryptographic stack.
- current snx-rs source maps IPsec/SSL, SSO/MFA/certs/HSM, Windows/macOS/Linux platform integration, profile/keychain storage, GUI/tray/settings, packaging and CI.

Future certification regressions explicitly retained:

- upstream issue #217: tunnel can connect while Office Mode/internal route set is incomplete compared with official Windows client;
- upstream issue #221: persisted IKE + XFRM reconnect can report connected while traffic is dead;
- therefore `Connected` alone is never the PVNetwork success criterion; effective data path, routes, DNS and health matter.

## Exact next entry

**026 — SonicWall NetExtender / SSL VPN**

Required sequence:

1. Read entry 026 dossier and shared vendor-enterprise decision.
2. Identify current official SonicWall NetExtender / SSL VPN client and firewall/SMA authority, exact current releases/platforms, and proprietary source boundary.
3. Find any serious maintained open-source interoperability projects. Do not assume one exists.
4. Distinguish NetExtender SSL VPN from SonicWall Global VPN/IPsec (entry 027).
5. Pin canonical public source/release/license for every reusable/interoperability candidate.
6. Capture full source tree/manifests where public source exists; use evidence-backed N/A for proprietary-only source gates.
7. Map build/languages, architecture, auth/MFA/SSO/cert behavior, tunnel/data path, UI/menu, config/profile storage, secrets/keychain, platform integration, diagnostics/logs, packaging/update lifecycle, assets, forks, issues/releases/security, tests/CI, Store/privacy/security implications.
8. Write `V1_GATE_RECONCILIATION.md` and promote only if all 20 original gates are evidence-backed or evidence-backed N/A with bounded uncertainty.
9. If 026 passes, update tracker/run state and continue 027 without owner prompting.

## Concurrency rule

Before any tracker or Run State write, fetch the latest file. If another agent has advanced beyond this handoff, follow the newest repository truth and never overwrite the campaign backward.
