# OpenConnect / ocserv — Deployment Topologies

Review date: 2026-08-14 UTC

Covered patterns:

1. **Internet remote-access gateway** — ocserv terminates OpenConnect clients and routes/NATs traffic into private networks.
2. **Split tunnel** — route/no-route/split-DNS policies push selected destinations.
3. **Full tunnel** — server becomes default gateway with DNS/tunnel policy as configured.
4. **Per-group/per-user policy** — authentication group selects routes, DNS, limits and access restrictions.
5. **Multi-vhost** — separate certificate/auth/network policy by virtual host where configured.
6. **TLS-only restricted network** — DTLS disabled/unreachable, CSTP carries data.
7. **TLS + DTLS normal path** — TCP control/fallback plus UDP data path.

Container/Kubernetes deployment is an advanced gateway topology requiring TUN/network privilege, firewall/routing, persistence and secret controls; it is not presumed stateless.
