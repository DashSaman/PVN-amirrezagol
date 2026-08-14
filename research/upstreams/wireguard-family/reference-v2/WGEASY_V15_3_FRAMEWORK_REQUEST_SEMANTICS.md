# wg-easy v15.3.0 — pinned framework request semantics

Status: source-evidence slice for entries 002/003; **not an execution receipt and not COMPLETE-REFERENCE-v2**.

## Pinned dependency chain

The audited wg-easy release is `v15.3.0` / commit `2dc8ba779216929c10c1998341d36963fe0eca7a`.

At that tag, `src/package.json` declares `nuxt: ^3.21.5`. The committed `src/pnpm-lock.yaml` resolves the application to:

- Nuxt `3.21.5`
- Nitro (`nitropack`) `2.13.4`
- h3 `1.15.11`
- an additional `h3-next` resolution at `h3 2.0.1-rc.20` exists in the dependency graph, but the stable h3 runtime dependency shown by the lock is `1.15.11`; it must not be conflated with the stable h3 API audited below.

This closes the previous ambiguity where request-header behavior was discussed without an exact framework dependency set.

## h3 1.15.11 request semantics from pinned source

Pinned source: `unjs/h3` tag `v1.15.11`, `src/utils/request.ts`.

### Host

`getRequestHost(event, opts={})` uses `event.node.req.headers.host` by default. It only consults `x-forwarded-host` when the caller explicitly passes `xForwardedHost: true`; when enabled, it takes the first comma-separated value after trimming. There is no trusted-proxy allow-list inside this helper.

Implication: a caller that treats the returned host as a security identity must provide its own validation/upstream sanitization. The helper itself is extraction logic, not a trust policy.

### X-Forwarded-Proto

In h3 `1.15.11`, `getRequestProtocol(event, opts={})` treats `x-forwarded-proto: https` as HTTPS unless the caller explicitly sets `xForwardedProto: false`. Otherwise it falls back to whether the Node connection is encrypted.

This is an important version-specific result: current h3 documentation may describe newer defaults, so wg-easy v15.3.0 analysis must use the pinned 1.15.11 source rather than silently importing later semantics.

### X-Forwarded-For / client IP

`getRequestIP(event, opts={})` first returns `event.context.clientAddress` if present. It only reads `x-forwarded-for` when `xForwardedFor: true`; when enabled it takes the first comma-separated address. The source comment explicitly warns that the header must be trusted (for example because the application is behind a CDN/reverse proxy) before enabling this behavior.

### URL construction

`getRequestURL` composes the URL from `getRequestProtocol`, `getRequestHost`, and the request path. Therefore any security-sensitive use of generated origin/host inherits the Host and forwarded-header choices above.

## What this establishes for wg-easy

The previous wg-easy audit found no application-declared Origin/Host allow-list, CSRF policy, or trusted-proxy allow-list in the audited `src/nuxt.config.ts`. The pinned framework audit now establishes the lower-level helper semantics, but it still does **not** prove which helpers/options every Nuxt/Nitro internal path invokes at runtime.

Safe deployment inference remains narrow:

1. do not expose the management HTTP service directly when a sanitizing TLS reverse proxy can front it;
2. the proxy should overwrite/normalize Host and forwarding headers rather than append untrusted client values;
3. do not use Host/X-Forwarded-* as an authorization signal without explicit validation;
4. execution tests are still required to prove effective runtime behavior through the built wg-easy image and chosen proxy topology.

## Evidence

- wg-easy `v15.3.0` `src/package.json` — Nuxt range `^3.21.5`.
- wg-easy `v15.3.0` `src/pnpm-lock.yaml` — exact Nuxt `3.21.5`, nitropack `2.13.4`, h3 `1.15.11`, h3-next `2.0.1-rc.20`.
- unjs/h3 `v1.15.11` `src/utils/request.ts` — exact Host, X-Forwarded-Proto, X-Forwarded-For and URL helper behavior.

## Remaining gate

Still unresolved without execution: effective built-image behavior across Nitro adapter/runtime, reverse-proxy configuration, CSRF/origin behavior of state-changing requests, and whether a chosen deployment sanitizes forwarded headers as intended. These remain blockers; no PASS is fabricated.
