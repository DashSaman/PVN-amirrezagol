# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 2

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG  
State: **IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED**

## New durable evidence

- `research/upstreams/wireguard-family/reference-v2/CLIENT_UI_AND_MENUS.md`
  - pinned official WireGuard Android source UI anchors at `e7b3a3c...`;
  - pinned WireGuard Windows UI anchors at `4e6726c...`;
  - pinned Amnezia cross-platform QML/settings/server-list/kill-switch anchors at `e643fa0...`;
  - Persian upstream translation evidence recorded as reference only;
  - protocol profile fields separated from product UI and platform backend choice;
  - AWG generation-specific UI rule and import/deep-link residual gaps.

- `research/upstreams/wireguard-family/reference-v2/SERVER_UI_AND_MENUS.md`
  - explicit rule that WireGuard has no canonical web server UI;
  - pinned `wg-easy/wg-easy` source anchors at `3d9eab1...` for peer list/create/detail/search/QR/admin/application persistence;
  - management-plane authentication, persistence, privilege, exposed-interface and supply-chain review requirements separated from protocol claims.

- `research/upstreams/wireguard-family/reference-v2/DEPLOYMENT_TOPOLOGIES.md`
  - remote-access hub, site-to-site, roaming, mesh, userspace, kernel-backed Linux, AWG and self-hosted provisioning topologies;
  - failure-domain and observability model;
  - provisioning credentials separated from tunnel keys and runtime state.

- `REFERENCE_INDEX.md` synchronized: all mandatory v2 filenames are now present, but file presence is explicitly **not** treated as completion.

## Commits

- `b98835bb4def71283e3c77554a6a703fee344d2d` — client UI/menu source map.
- `ba3cd46bf3f30438a88af9798dde076554427958` — deployment topologies and failure domains.
- `3ac6ebcfba41310fac03c25ed38472faebd7e1b8` — server management UI/control-plane boundary.
- `f0350775845dd795d1ec177b8657655cd6e52e2e` — synchronized v2 reference index.

## Checks

- mandatory v2 filename structure: PRESENT.
- WireGuard Android/Windows UI claims: pinned source-path evidence present.
- Amnezia UI/settings/localization claims: pinned source-path evidence present.
- management UI separated from protocol engine: PASS as research architecture rule.
- entries 002/003 `COMPLETE-REFERENCE-v2`: **NOT ALLOWED YET**.
- implementation/build/device/store claims: NOT MADE.

## Residual gates

1. deepen selected server installer/panel license/dependency/container/auth/default-exposure review;
2. exact install/update/uninstall/rollback receipts on representative targets;
3. pin Apple WireGuard/AWG UI and import/export lifecycle evidence;
4. exact QR/file/deep-link behavior per major client/platform;
5. generation-specific AWG interoperability/test receipts;
6. issue/advisory reconciliation against selected pins;
7. entry-specific 002/003 checklist against every `FULL_PROTOCOL_REFERENCE_CONTRACT.md` gate.

## Exact next action

Continue the same work unit. Close server-management supply-chain/authentication evidence first, then Apple/import-export client evidence and AWG generation interoperability. Reconcile 002/003 only after those are traceable; keep both v2 tracker rows `PENDING` until strict gates pass. Then checkpoint and continue the next required v2 family without owner prompting.
