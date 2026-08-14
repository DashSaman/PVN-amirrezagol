# wg-easy v15.3.0 — pinned auth/setup/control-plane audit

Status: source-backed research evidence; **not a production certification** and **not COMPLETE-REFERENCE-v2 by itself**.

Audit date: 2026-08-14  
Upstream: `wg-easy/wg-easy`  
Stable tag: `v15.3.0`  
Pinned commit: `2dc8ba779216929c10c1998341d36963fe0eca7a`  
Pinned tree: `ad7f098478c283332f585bf68ae90269322873be`

## Why this audit exists

Earlier PVNetwork evidence inspected v15.0.0 and later release notes showed session/setup/API changes in v15.2.x. This file re-checks the **current stable v15.3.0 source** instead of silently projecting v15.0.0 behavior forward.

## Route authorization boundary

`src/server/utils/handler.ts` defines `definePermissionEventHandler(resource, action, handler)`. It resolves the current user through `getCurrentUser(event)`, evaluates `hasPermissionsWithData`, forces boolean permission checks before the route body, and fails with HTTP 500 if a data-dependent permission check was never performed. This is the reusable authorization wrapper for protected resource handlers.

Important limitation: the existence of this wrapper does **not** prove every API route uses it. Route-by-route certification still requires checking each sensitive handler.

Pinned source: <https://github.com/wg-easy/wg-easy/blob/2dc8ba779216929c10c1998341d36963fe0eca7a/src/server/utils/handler.ts>

## Setup API state machine

The same pinned `handler.ts` defines a setup state machine rather than relying only on browser redirects. `ValidSetupSteps` permits setup step `2` only from database setup state `1`, and permits step `4` or `migrate` only from state `3`. `defineSetupEventHandler` rejects requests after setup is complete, rejects unknown setup states, and rejects an endpoint invoked out of sequence.

`src/server/api/setup/2.post.ts` uses that wrapper, validates the request body against `UserSetupSchema`, creates the first user, then advances the database setup state to step 3. Therefore the current-stable setup API has a server-side **state/order guard** even though the separate browser setup middleware is not itself an API authorization boundary.

This does not prove CSRF resistance or origin enforcement for pre-auth setup POSTs; those are distinct controls.

Pinned sources:
- <https://github.com/wg-easy/wg-easy/blob/2dc8ba779216929c10c1998341d36963fe0eca7a/src/server/utils/handler.ts>
- <https://github.com/wg-easy/wg-easy/blob/2dc8ba779216929c10c1998341d36963fe0eca7a/src/server/api/setup/2.post.ts>

## Session construction and invalidation

`src/server/utils/session.ts` remains materially similar to the earlier v15-generation observation:

- session data is a partial object containing `userId`;
- cookie name is `wg-easy`;
- cookie `secure` is enabled unless `WG_ENV.INSECURE` is true;
- remembered login sets cookie `maxAge` from database `sessionTimeout`;
- the source still contains `TODO: add session expiration`, so independent server-side idle/absolute expiry is **not established** by this source;
- `getCurrentUser` accepts either session-backed identity or HTTP Basic authorization;
- the Basic path still carries the source TODO that timing can enumerate usernames.

`src/server/api/session.delete.ts` obtains the current session and calls `session.clear()`. This establishes explicit **current-session logout invalidation**. It does not establish global logout-all-devices, credential-change revocation of all existing sessions, or a server-side session registry.

Pinned sources:
- <https://github.com/wg-easy/wg-easy/blob/2dc8ba779216929c10c1998341d36963fe0eca7a/src/server/utils/session.ts>
- <https://github.com/wg-easy/wg-easy/blob/2dc8ba779216929c10c1998341d36963fe0eca7a/src/server/api/session.delete.ts>

## CSRF / Origin / reverse-proxy trust disposition

The audited current-stable files above establish permission, setup-state, session-cookie and logout behavior. They do **not** contain an explicit application-level CSRF token check, `Origin`/`Referer` allow-list, or trusted-forwarded-header/reverse-proxy allow-list.

This is intentionally recorded as **NOT ESTABLISHED BY THE AUDITED SOURCE SET**, not as a claim that the entire Nuxt/Nitro dependency stack has no implicit protection. `src/nuxt.config.ts` does not declare an application-specific CSRF/origin/trusted-proxy module in its module list or Nitro configuration, but framework/runtime defaults and reverse-proxy deployment behavior require separate verification before a negative security claim is made.

Operational consequence for PVNetwork: do not expose the management plane on the public Internet on the assumption that same-origin browser behavior alone is a complete CSRF boundary. Production evidence should include explicit TLS/reverse-proxy topology, forwarded-header trust configuration, cross-origin negative tests, and authenticated state-changing request tests.

Pinned config: <https://github.com/wg-easy/wg-easy/blob/2dc8ba779216929c10c1998341d36963fe0eca7a/src/nuxt.config.ts>

## Current-stable conclusions

| Control | v15.3.0 source disposition |
|---|---|
| Immutable source pin | PASS — tag resolved to full commit/tree above |
| Permission wrapper | PRESENT — `definePermissionEventHandler` |
| Setup order/state guard | PRESENT — `defineSetupEventHandler` + `ValidSetupSteps` |
| Setup complete rejection | PRESENT |
| Session cookie secure flag | PRESENT unless `INSECURE` disables it |
| Remember-me cookie maxAge | PRESENT |
| Independent server-side session expiry | NOT ESTABLISHED; TODO remains |
| Current-session logout clear | PRESENT — `session.clear()` |
| Global session revocation | NOT ESTABLISHED |
| HTTP Basic API auth path | PRESENT |
| Basic username-enumeration timing hardening | NOT ESTABLISHED; upstream TODO remains |
| Explicit app-level CSRF token in audited set | NOT ESTABLISHED |
| Explicit Origin/Referer allow-list in audited set | NOT ESTABLISHED |
| Explicit trusted-proxy allow-list in audited config | NOT ESTABLISHED |

## Remaining execution/security gates

1. Enumerate every state-changing v15.3.0 API handler and prove whether it is permission-wrapped, setup-state-wrapped, intentionally public, or otherwise guarded.
2. Inspect Nitro/h3 dependency behavior and deployment docs for origin/host/forwarded-header defaults; do not infer framework guarantees.
3. Execute cross-origin negative tests against representative deployment topology.
4. Test password change, account disable, 2FA reset and logout for existing-session revocation behavior.
5. Pin the actually deployed `ghcr.io/wg-easy/wg-easy:15.3.0` OCI digest and retain registry/SBOM/provenance plus install/update/rollback receipts.
6. Keep entries 002/003 `PENDING` until every applicable FULL_PROTOCOL_REFERENCE_CONTRACT gate is evidenced or explicitly dispositioned.
