# WireGuard / AmneziaWG — Server UI and Control-Plane Menus

Status: **REFERENCE / NOT IMPLEMENTED / NOT COMPLETE-REFERENCE-v2**

## Critical boundary

WireGuard itself has no canonical web “server panel.” The protocol/engine exposes peer/interface configuration; web dashboards are separate management/control-plane projects with their own source, license, authentication, persistence, update and supply-chain risk.

Accordingly, PVNetwork must not describe a third-party panel menu as a WireGuard protocol feature.

## Current concrete management reference — wg-easy

Repository: `wg-easy/wg-easy`
Observed source revision from GitHub code search: `3d9eab1565a1a68a58745651e07573dac0b4f295`.

Source-level UI/control anchors:

- `src/app/components/Clients/List.vue` — client/peer list presentation.
- `src/app/components/Clients/New.vue` — create-client/peer workflow.
- `src/app/components/Clients/Search.vue` — client search.
- `src/app/components/Clients/Sort.vue` — client sorting.
- `src/app/pages/clients/[id].vue` — individual client detail page.
- `src/app/stores/clients.ts` — client state/data ownership in the application layer.
- `src/cli/clients/list.ts` — CLI client listing.
- `src/cli/clients/qr.ts` — CLI QR/config distribution surface.
- `docs/content/guides/clients.md` — documented client-management workflow.
- `docs/content/guides/admin.md` — admin guidance.
- `src/server/utils/WireGuard.ts` — application-to-WireGuard control boundary.
- `src/server/database/sqlite.ts` — management application persistence boundary.

This is useful because it proves the management plane includes application state and persistence beyond the WireGuard engine. It must be threat-modeled as a privileged administration service.

## Menu/function taxonomy for a WireGuard management plane

A safe product/reference taxonomy should separate:

1. **Overview / health** — interface state, peer counts, service health.
2. **Peers / clients** — list, search, sort, create, disable/delete, detail.
3. **Configuration distribution** — downloadable config and QR representation; secrets require access controls.
4. **Network settings** — interface address, listen port, DNS defaults, AllowedIPs/routing policy.
5. **Administration** — authentication, users/roles if supported, session/security policy.
6. **Diagnostics** — handshake recency, counters, service logs, backend/version identity.
7. **Backup/export** — management database/config backup, with secret-handling warnings.
8. **Update/maintenance** — image/package version, migration, rollback/uninstall path.

Not every reference panel implements every category, and absence must not be filled by assumption.

## Security review requirements

A management UI can be more exposed than the WireGuard UDP data plane. For every selected panel/installer, record:

- whether the admin UI binds publicly by default;
- authentication bootstrap and default credentials/secrets;
- TLS termination assumptions;
- CSRF/session/cookie policy where applicable;
- database/config secret storage;
- Docker socket, host networking, `NET_ADMIN`, privileged mode or host mounts;
- firewall/NAT changes;
- update channel and image/tag pinning;
- backup/restore and uninstall behavior;
- vulnerability/advisory history.

Popularity is not evidence of safety.

## AmneziaWG control-plane distinction

Amnezia's self-hosted workflow can provision protocol containers from the client after receiving server administration credentials. That provisioning UX is a product control plane layered above AWG/WireGuard, not a protocol server menu. AWG generation/version parameters must remain explicit when configuration is generated or edited.

The official Amnezia client source is therefore a useful provisioning/control reference, while `amneziawg-go`, the Linux kernel module and tools remain engine/runtime references. These roles must not be collapsed.

## PVNetwork architecture decision

- do not embed a third-party server dashboard into the client as if it were protocol UI;
- model remote server provisioning/management as a separate privileged service capability;
- require explicit authentication and authorization contracts before any remote administration feature;
- keep downloadable/QR client configuration handling separate from panel administrator credentials;
- expose engine version, management-plane version and configuration schema version independently.

## Reuse decision

`wg-easy` is a strong architecture/operations reference for peer lifecycle and configuration distribution. Any code reuse requires independent license/dependency review; this document does not approve redistribution. Amnezia provisioning flows are likewise reference evidence, not blanket reuse approval.

## Residual evidence before strict v2 completion

- pin license/dependency/container-image evidence for each management candidate selected in `SERVER_INSTALLERS_AND_PROJECTS.md`;
- inspect exact authentication/bootstrap defaults and exposed ports from pinned deployment files;
- record install/update/uninstall/rollback receipts;
- map at least one AWG-aware management/provisioning path at pinned source level;
- add issue/advisory evidence for selected panels;
- reconcile this management-plane evidence into entries 002/003.

Entries 002/003 remain `PENDING` in the v2 tracker.