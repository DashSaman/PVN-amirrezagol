# AGENTS Handoff — 2026-08-14 — WireGuard / AmneziaWG v2 slice 7

Work unit: `WIREGUARD-AWG-COMPLETE-REFERENCE-V2`  
Entries: 002 WireGuard, 003 AmneziaWG  
State: **IN-RESEARCH / NOT COMPLETE-REFERENCE-v2 / NOT IMPLEMENTED**

## Durable evidence added

### State-changing wg-easy v15.3 API guard matrix

New file: `research/upstreams/wireguard-family/reference-v2/WGEASY_V15_3_STATE_CHANGING_API_GUARD_MATRIX.md`.

The pinned `v15.3.0` tree was enumerated for state-changing `src/server/api/**` handlers. Mutating admin/client/me routes are classified separately from bootstrap and authentication routes. Representative source was directly checked: admin general mutation uses `definePermissionEventHandler('admin','any',...)`; client creation uses `definePermissionEventHandler('clients','create',...)`; password change is permission-wrapped and passes the current password to the update operation; setup step 2 uses `defineSetupEventHandler(2,...)`; login is intentionally a plain authentication endpoint and only writes the session after credential/TOTP validation.

Research commit: `81cd64f88a906542e8072534f92c001d5f83e1d5`.

### Request-integrity / reverse-proxy boundary

New file: `research/upstreams/wireguard-family/reference-v2/WGEASY_V15_3_REQUEST_BOUNDARY_AND_PROXY.md`.

Pinned `src/nuxt.config.ts` does not establish an application-declared Origin/Host/trusted-proxy/CSRF configuration in the audited config. This remains a narrow source result, not a claim about all implicit Nitro/h3 behavior. The pinned official Caddy tutorial was also checked: Caddy terminates/publishes HTTP(S), wg-easy management HTTP remains internal on the shared Docker network, and only WireGuard UDP is host-published in the example. This supports a safer deployment boundary but does not prove forwarded-header sanitization/trust semantics.

Research commit: `fe586a319c096c68e406f8a9acb606bc99d13563`.

## Strict checks

- entries 002/003 COMPLETE-REFERENCE-v2: **NO**.
- state-changing API inventory: **YES, pinned tree**.
- representative permission/setup/auth route classification: **YES**.
- explicit application CSRF/origin/trusted-proxy config established: **NO**.
- official reverse-proxy topology that avoids direct management-port publication: **YES**.
- exact Nitro/h3 forwarded-header trust semantics: **UNRESOLVED**.
- deployed-image execution receipt: **NO**.
- Apple-device / AWG generation interop receipts: **NO**.

## External blockers retained

No representative container host, Apple device/App Store binary environment, or AWG multi-generation peer matrix is available. Execution-only receipts remain blocked and must not be fabricated.

## Exact next action

Continue the same work unit. Inspect the pinned Nuxt/Nitro/h3 dependency versions and authoritative framework source/docs for Host/Forwarded/X-Forwarded-* trust semantics if determinable without execution. In parallel inventory WireGuard and standalone AmneziaWG Apple entitlements, app groups, Network Extension identifiers, and shipped Store/build revision mapping. Then reconcile entries 002/003 line-by-line against `FULL_PROTOCOL_REFERENCE_CONTRACT.md`; keep them PENDING where execution-only or provenance gates remain open.
