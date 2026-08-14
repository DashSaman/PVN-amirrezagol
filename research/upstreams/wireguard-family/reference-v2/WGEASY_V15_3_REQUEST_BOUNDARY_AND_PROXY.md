# wg-easy v15.3.0 — request-integrity and reverse-proxy boundary

Pinned upstream: `wg-easy/wg-easy` `v15.3.0` / `2dc8ba779216929c10c1998341d36963fe0eca7a`.

## What the pinned application config establishes

`src/nuxt.config.ts` configures Nuxt modules, i18n, aliases and Nitro build/external settings. In the pinned file there is no application-declared Origin allow-list, Host allow-list, forwarded-header/trusted-proxy allow-list, or CSRF module/configuration. This is a narrow source observation, **not** proof that every underlying Nuxt/Nitro/h3 behavior is absent.

The route-level guard matrix is therefore kept separate from browser request integrity: a mutation may be correctly permission-wrapped while cross-origin/request-origin behavior remains a distinct deployment/framework question.

## Reverse-proxy deployment evidence

The pinned official Caddy tutorial places Caddy and wg-easy on a shared Docker network, publishes Caddy on 80/443, sets wg-easy `PORT=80`, removes publication of the wg-easy HTTP port, and keeps only WireGuard UDP `51820` published. Caddy uses `reverse_proxy wg-easy:80` and terminates TLS.

This is a useful security deployment pattern because the management HTTP listener is not directly host-published in that example. It does **not** by itself establish an application-side trusted-proxy list or sanitize every forwarded header.

## Boundary conclusions

- **Permission/setup guards:** source-evidenced separately at handler level.
- **Application-declared CSRF token/Origin allow-list:** not established in pinned application config/source audit.
- **Application-declared trusted-proxy/forwarded-header allow-list:** not established in pinned application config/source audit.
- **Recommended upstream Caddy topology:** reverse proxy is the published HTTP/TLS boundary; wg-easy management HTTP is internal to the Docker network in the pinned tutorial.
- **Host-header/forwarded-header semantic acceptance by Nitro/h3:** still unresolved here; must not be inferred from the Caddy tutorial.
- **Operational implication for PVNetwork reference architecture:** prefer a dedicated TLS reverse-proxy boundary and avoid direct publication of the management HTTP listener unless a deployment-specific reason and compensating controls are documented. Treat forwarded headers as untrusted until the chosen proxy/runtime trust model is explicitly verified.

## Remaining evidence gap

A definitive statement about `X-Forwarded-*`, `Forwarded`, Host reconstruction, proxy IP trust, and Origin enforcement requires either pinned framework-level source/docs for the exact Nuxt/Nitro/h3 dependency set or an execution receipt behind representative proxies. This remains open and is not converted to PASS by this note.
