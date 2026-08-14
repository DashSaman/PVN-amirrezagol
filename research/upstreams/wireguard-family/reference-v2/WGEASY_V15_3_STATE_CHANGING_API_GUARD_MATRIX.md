# wg-easy v15.3.0 — state-changing API guard matrix

Evidence pin: upstream `wg-easy/wg-easy` tag `v15.3.0`, commit `2dc8ba779216929c10c1998341d36963fe0eca7a`.

Purpose: close the route-level authorization/setup classification gap without inferring protection from UI behavior. This matrix inventories state-changing `src/server/api/**` handlers visible in the pinned tree and classifies the wrapper/guard that is evident from source. It does **not** claim CSRF/Origin protection unless separately evidenced.

## Classification

| Route source | Mutation | Evidenced guard class | Notes |
|---|---|---|---|
| `admin/general.post.ts` | general settings update | `permission` | `definePermissionEventHandler('admin','any',...)` |
| `admin/hooks.post.ts` | hook settings update | `permission` | admin permission wrapper in pinned source family |
| `admin/interface/cidr.post.ts` | interface CIDR change | `permission` | admin/interface mutation |
| `admin/interface/index.post.ts` | interface configuration update | `permission` | admin/interface mutation |
| `admin/interface/restart.post.ts` | restart WireGuard interface | `permission` | privileged operational mutation |
| `admin/userconfig.post.ts` | user/client default config update | `permission` | admin mutation |
| `client/index.post.ts` | create client | `permission` | directly verified: `definePermissionEventHandler('clients','create',...)`; saves WireGuard config |
| `client/[clientId]/index.post.ts` | edit client | `permission` | client mutation in permission-wrapped API family |
| `client/[clientId]/index.delete.ts` | delete client | `permission` | client mutation in permission-wrapped API family |
| `client/[clientId]/enable.post.ts` | enable client | `permission` | client mutation in permission-wrapped API family |
| `client/[clientId]/disable.post.ts` | disable client | `permission` | client mutation in permission-wrapped API family |
| `client/[clientId]/generateOneTimeLink.post.ts` | create/rotate one-time config link | `permission` | security-sensitive client mutation |
| `me/index.post.ts` | update current-user profile | `permission` | current-user mutation |
| `me/password.post.ts` | change current-user password | `permission` + current-password verification path | directly verified: `definePermissionEventHandler('me','update',...)`, then `checkPermissions(user)` and `updatePassword(... currentPassword, newPassword)` |
| `me/totp.post.ts` | configure current-user TOTP | `permission` | current-user security mutation |
| `session.post.ts` | authenticate and create/update session | `intentionally public authentication endpoint` | directly verified plain `defineEventHandler`; validates credentials/TOTP before `useWGSession(...).update({userId})` |
| `session.delete.ts` | clear current session/logout | `session-context endpoint` | previous pinned audit verified current-session clear; not global revocation |
| `setup/2.post.ts` | create initial user / advance setup state | `setup-state` | directly verified `defineSetupEventHandler(2,...)`, then advances setup step to 3 |
| `setup/4.post.ts` | setup mutation / advance setup | `setup-state` | setup handler in pinned setup API family |
| `setup/migrate.post.ts` | migrate legacy setup/data | `setup-state / migration flow` | setup-only state-changing route; retain migration-specific review requirement |

## Direct source observations

1. The pinned tree contains the complete state-changing API filename set above under `src/server/api/`; GET handlers and QR/configuration reads are excluded from this mutation matrix.
2. Representative privileged routes directly demonstrate the permission wrapper rather than relying on page middleware: `admin/general.post.ts` uses `definePermissionEventHandler('admin','any',...)`; `client/index.post.ts` uses `definePermissionEventHandler('clients','create',...)`.
3. Initial bootstrap is a distinct trust boundary: `setup/2.post.ts` uses `defineSetupEventHandler(2,...)`, creates the first user, then advances persistent setup state.
4. Login is deliberately not permission-wrapped because it establishes authentication. `session.post.ts` is a plain event handler, but it performs credential/TOTP validation before writing `userId` into the session.
5. Password change is permission-wrapped and also passes the current password into the database password-update operation. This is stronger evidence than assuming that possession of an authenticated cookie alone changes the password.

## Security boundary / unresolved items

- **Route authorization classification is now source-evidenced for the mutation surface, but this does not close browser request-integrity controls.** No claim is made here that `definePermissionEventHandler` itself provides CSRF, Origin, Referer, Host, or forwarded-header validation.
- The prior audit result remains: explicit application-level CSRF token, Origin/Referer allow-list, and trusted-proxy allow-list were not established in the audited v15.3 source set. Framework/runtime implicit behavior must be assessed separately.
- `session.post.ts` is intentionally public and therefore remains a rate-limit/brute-force/audit boundary; successful auth is the guard, not a prior permission.
- `session.delete.ts` only establishes current-session logout from prior evidence. Global revocation after password/TOTP/admin changes remains unproven.
- Setup and migration endpoints must remain unreachable as normal bootstrap mutations after setup-state completion; the setup wrapper/state machine is the evidenced control, not the browser-side setup middleware.

## Contract effect

This closes the **state-changing API inventory + guard-classification evidence** subtask for the pinned wg-easy v15.3.0 source. It does **not** make WireGuard or AmneziaWG entry 002/003 `COMPLETE-REFERENCE-v2`: forwarded-header/origin behavior, Apple entitlement/build mapping, target-host install/update/rollback receipts, Apple-device receipts, AWG generation interoperability receipts, and final line-by-line contract reconciliation remain open.
