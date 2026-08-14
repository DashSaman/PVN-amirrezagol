# wg-easy v15.3.0 — Nitro / h3 Request Boundary

Review date: 2026-08-14

Scope: close the source-only request/proxy-framework gap for pinned `wg-easy/wg-easy@2dc8ba779216929c10c1998341d36963fe0eca7a` without pretending that a framework's current repository state is the same thing as the package actually installed by wg-easy.

## 1. What wg-easy actually pins

The pinned wg-easy `src/package.json` identifies application version `15.3.0` and uses Nuxt `^3.21.5`.

Its committed `src/pnpm-lock.yaml` resolves the relevant server stack to:

- Nuxt `3.21.5`;
- `nitropack` `2.13.4`;
- h3 `1.15.11` on the Nitro 2 path.

The same lock also contains newer h3/srvx packages used by other dependency paths, including h3 `2.0.1-rc.20` and srvx `0.11.15`. Those packages must not be substituted for the Nitro-2 path merely because they coexist in the lockfile.

## 2. Upstream provenance caution

The current canonical Nitro repository has moved into Nitro 3 development while npm still publishes/supports the `nitropack` 2.x line. A GitHub ref named `v2.13.4` was inspected, but its checked package metadata presents Nitro 3 beta-era source. Therefore this research does **not** equate that Git tree with the exact `nitropack@2.13.4` package consumed by wg-easy.

The npm package page for `nitropack@2.13.4` identifies it as the v2 support line and points to the canonical Nitro repository, but package-install provenance remains governed here by wg-easy's committed pnpm lock rather than by a same-looking Git tag.

This mismatch is recorded to prevent a false source attribution.

## 3. Exact h3 1.15.11 request semantics

The h3 version on the Nitro-2 path is independently reproducible from canonical h3 tag `v1.15.11`, commit `7b9f41fda6038d26a367c2a26a07ed83ee1dbaac`.

Reviewed `src/utils/request.ts` establishes:

### Host

`getRequestHost(event)`:

- uses the ordinary HTTP `Host` header by default;
- falls back to `localhost` if absent;
- only consults `x-forwarded-host` when the caller explicitly supplies `{ xForwardedHost: true }`;
- when enabled, uses the first comma-separated forwarded host.

Therefore h3 itself does **not** automatically trust `X-Forwarded-Host` in the default `getRequestHost` call.

### Protocol

`getRequestProtocol(event)`:

- treats `x-forwarded-proto: https` as HTTPS by default unless `{ xForwardedProto: false }` is supplied;
- otherwise checks whether the underlying Node connection is encrypted;
- otherwise returns HTTP.

This is a materially different trust default from forwarded host: forwarded proto is consulted by default for the exact string `https`.

### URL

`getRequestURL(event, opts)` composes protocol + host + original/path using the two helpers above. Thus the default URL construction can be affected by `X-Forwarded-Proto`, while forwarded host requires explicit opt-in.

### Client IP

`getRequestIP(event, opts)` only uses `x-forwarded-for` when `{ xForwardedFor: true }` is explicitly requested. Its own source comment warns callers to enable that only behind a trusted CDN/reverse proxy.

### Origin

No automatic same-origin/Origin/Host authorization boundary is created by these request helpers. `Origin` remains an ordinary request header unless application/framework middleware explicitly validates it.

## 4. Node adapter behavior

Canonical h3 `src/adapters/node.ts` converts Node `IncomingMessage`/`ServerResponse` into an H3 event and dispatches the app. The reviewed adapter does not add a trusted-proxy allowlist, Host allowlist, or Origin validator by itself.

This means a deployment cannot infer administrative-origin security merely from "running behind Nitro/h3". Any such protection must be shown in wg-easy/Nuxt/Nitro middleware, a reverse proxy, or infrastructure policy.

## 5. What this means for wg-easy v15.3.0

From source available in this research:

1. wg-easy does not expose a bespoke low-level Node HTTP adapter in its app tree; the server request boundary is framework-owned.
2. The pinned dependency chain is now identified down to exact h3 1.15.11 semantics.
3. There is no basis to claim that arbitrary `X-Forwarded-Host` is automatically trusted by h3's default host helper.
4. There **is** a basis to treat `X-Forwarded-Proto: https` as potentially influential in default h3 protocol/URL derivation.
5. No source evidence reviewed here establishes an automatic Host/Origin allowlist for the wg-easy admin UI.
6. The safest deployment model remains an explicit trusted reverse-proxy boundary with controlled forwarded headers, known external origin/host, and restricted management-plane exposure.

This is a request-boundary conclusion, not a claim that a particular wg-easy endpoint is exploitable.

## 6. Residual proof limits

Source review still cannot replace runtime receipts for:

- actual headers delivered through a specific nginx/Traefik/Caddy/Cloudflare deployment;
- whether a chosen proxy overwrites or appends client-supplied forwarded headers;
- browser cookie/Origin behavior under the exact public URL;
- container-network reachability and management-port exposure;
- any runtime behavior added by the generated Nitro server bundle.

Those remain execution/deployment verification work.

## 7. Gate result

- wg-easy dependency versions: **SOURCE-COMPLETE**.
- exact h3 1.15.11 Host/proto/IP helper semantics: **SOURCE-COMPLETE**.
- automatic framework Host/Origin trust claim: **NOT ASSUMED / no evidence of implicit allowlist**.
- exact generated Nitro bundle request path for the built wg-easy image: **RUNTIME/BUILD RECEIPT REQUIRED**.
- reverse-proxy header behavior for a real deployment: **BLOCKED_EXTERNAL until a deployment is exercised**.
