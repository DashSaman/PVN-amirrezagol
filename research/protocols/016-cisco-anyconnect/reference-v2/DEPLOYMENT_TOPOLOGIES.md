# Cisco AnyConnect — Deployment Topologies

Review date: 2026-08-14 UTC

Covered reference patterns:

1. **Cisco ASA remote access** — Secure Client / AnyConnect VPN users terminate on ASA; full or split tunnel according to group policy.
2. **Cisco FTD remote access** — Secure Client users terminate on Secure Firewall Threat Defense under Cisco management policy.
3. **Headend webdeploy** — client package/profile delivered from Cisco headend; endpoint then establishes remote-access session.
4. **Predeploy/enterprise software distribution** — client installed out-of-band, then connects to Cisco headend.
5. **Controlled open compatibility lab** — OpenConnect v9.21 client and/or ocserv 1.5.0 server used to exercise public compatible behavior, never represented as Cisco proprietary product equivalence.

Optional posture, SAML/external-browser, management-tunnel and module combinations are separate certification dimensions. VPN routing can be full tunnel, split include/exclude and policy-specific; exact headend version/profile controls the result.
