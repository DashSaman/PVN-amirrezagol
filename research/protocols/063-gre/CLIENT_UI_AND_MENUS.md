# GRE — Client UI and Menus

Reviewed: 2026-08-15

Bare GRE has no canonical consumer client UI. On Linux the authoritative control surface is network administration through `ip tunnel`/netlink. Therefore the following consumer concepts are evidence-backed NOT-APPLICABLE to the protocol itself: login/account, subscription list, QR import, certificates/PKI, MFA, kill switch, per-app tunnel policy, traffic quota, Store onboarding and protocol-owned diagnostics UI.

A future PVNetwork infrastructure editor may expose only evidence-backed GRE fields such as local/remote endpoint, inner interface address/routing, key where used, checksum flags, TTL/TOS, PMTU behavior and interface binding. UI must explicitly warn that bare GRE provides no encryption or peer authentication and must not label it as a secure VPN by itself.

Vendor router CLIs (Cisco IOS XE, Junos) are management references, not reusable consumer UI designs.
