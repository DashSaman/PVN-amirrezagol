# Palo Alto GlobalProtect — Deployment Topologies

Review date: 2026-08-14 UTC

Covered reference patterns:

1. **Single external portal + gateway on PAN-OS** — small/standard remote-access deployment.
2. **Portal with multiple external gateways** — portal distributes gateway choices/config; endpoint selects/connects according to policy/location/priority.
3. **Internal + external gateway design** — different on-network/off-network policy and tunnel behavior where configured.
4. **Split tunnel** — selected routes/domains/apps where licensed/platform-supported; exact capability matrix is release-specific.
5. **Full tunnel** — gateway carries default-route traffic according to policy.
6. **IPsec primary + SSL fallback** — common GlobalProtect design when IPsec is enabled and fallback permitted.
7. **IPsec-only policy** — current 6.3-era feature can enforce no SSL fallback on supported headend/client combinations.
8. **Prisma Access** — cloud-managed GlobalProtect service/gateway topology with distinct subscription/control plane.
9. **OpenConnect compatibility path** — OpenConnect v9.21 client to a compatible GlobalProtect portal/gateway for separately certified cases; not equivalent to the proprietary app feature set.

Certificate, SAML/browser, HIP/posture, gateway-version and platform combinations remain explicit certification dimensions rather than hidden assumptions.
