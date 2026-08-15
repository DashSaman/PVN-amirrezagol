# IPIP — Client UI and Menus

Reviewed: 2026-08-15

Bare IPIP has no canonical consumer client application or protocol-defined UI. On Linux the authoritative control surface is `ip tunnel`/netlink plus ordinary interface/address/route management.

A future PVNetwork infrastructure editor may expose only evidence-backed fields such as local/remote outer endpoint, inner interface addressing/routes, TTL/TOS, PMTU/DF behavior and physical-device binding where the platform supports them.

The following are protocol-level NOT-APPLICABLE: account/login, QR/subscription import, certificates/PSK, cipher selection, MFA, kill switch, per-app controls, traffic quotas, protocol-owned logs/backup UI and Store onboarding.

The UI must state that bare IPIP provides encapsulation only and does not encrypt or authenticate traffic. IPIP-over-IPsec is entry 066 and must remain a distinct secure composition.
